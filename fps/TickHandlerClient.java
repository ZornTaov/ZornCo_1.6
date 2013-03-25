package zornco.fps;

import java.util.EnumSet;


import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandlerClient implements ITickHandler {


	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if (type.equals(EnumSet.of(TickType.RENDER)))
        {
            GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
            if (guiscreen == null)
            {
                onTickInGUI(guiscreen);
            } else {
                onTickInGame();
            }
        }

	}

	private void onTickInGame() {
	}

	private void onTickInGUI(GuiScreen guiscreen) {
		if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer.isDead)
			return;
		ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight); 
		int width = scaledresolution.getScaledWidth(); 
		int height = scaledresolution.getScaledHeight();

		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		FPS.instance.clientPlayer = Minecraft.getMinecraft().thePlayer;

		if (Minecraft.getMinecraft().currentScreen == null ) {

			// do we need to refresh the currentPlayerList?
			if (FPS.instance.currentPlayerList.isEmpty() || 
					FPS.instance.needsToRefresh || 
					Minecraft.getMinecraft().theWorld.getWorldInfo().getWorldTime() % 20 == 0)
				FPS.instance.refresh();
			// lets draw our stuff

			if(FPS.instance.showHudInfo) {
				fontRenderer.drawString((new StringBuilder() + Minecraft.getMinecraft().debug).toString(), 2, 10,0xe0e0e0);
			}
			double playerX = FPS.instance.clientPlayer.posX;
			double playerY = FPS.instance.clientPlayer.posY;
			double playerZ = FPS.instance.clientPlayer.posZ;
			Vec3 vecPlayer = Vec3.createVectorHelper(playerX, 0, playerZ);

			// are there players to draw?
			if (FPS.instance.currentPlayerList.size() > 0) {
				if(FPS.instance.showHudInfo) {
					fontRenderer.drawString("Pos: " + ((int) playerX) + ", "
							+ ((int) playerY) + ", " + ((int) playerZ)
							+ " Players in Range: " + (FPS.instance.getPlayerCount()), 2, 18,
							0xe0e0e0);
				}


				int lineCount = 0;
				for (EntityPlayer multiPlayer : FPS.instance.currentPlayerList) {
					if (multiPlayer.isDead)
						FPS.instance.needsToRefresh = true;
					else if (multiPlayer instanceof EntityOtherPlayerMP) {


						// plot out player list
						int scale = Minecraft.getMinecraft().gameSettings.guiScale;
						// if((scale == 0||scale == 2a)&&lineCount == 10)break;
						lineCount++;
						// where are you?
						double multiX = multiPlayer.posX;
						double multiY = multiPlayer.posY;
						double multiZ = multiPlayer.posZ;
						// where you might be
						double prevMultiX = ((EntityOtherPlayerMP) multiPlayer).prevPosX;
						double prevMultiY = ((EntityOtherPlayerMP) multiPlayer).prevPosY;
						double prevMultiZ = ((EntityOtherPlayerMP) multiPlayer).prevPosZ;


						// distance to target
						Vec3 vecMulti = Vec3.createVectorHelper(multiX, 0, multiZ);
						double dis = Math.sqrt(((playerX - multiX) * (playerX - multiX))
								+ ((playerY - multiY) * (playerY - multiY))
								+ ((playerZ - multiZ) * (playerZ - multiZ)));

						// direction to target
						Vec3 vecOffset = vecPlayer.subtract(vecMulti);
						vecOffset = vecOffset.normalize();
						Vec3 vecForward = Vec3.createVectorHelper(0, 0, 1);
						double playerRotYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
						double vecFrwdOffset = vecOffset.dotProduct(vecForward);
						double angleACos = ((Math.acos(vecFrwdOffset) * 180D) / Math.PI);

						// fix angle
						if (multiX > playerX)
							angleACos *= -1;
						double angleTrunc;
						for (angleTrunc = (angleACos - playerRotYaw)*( Math.PI/180D); angleTrunc >=  Math.PI; angleTrunc -=  Math.PI*2)
							;
						for (; angleTrunc < - Math.PI; angleTrunc +=  Math.PI*2)
							;

						// draw arrow
						Minecraft.getMinecraft().renderEngine.bindTexture(FPS.instance.ARROW_LOCATION);
						int arrowColor = FPS.instance.neutralArrowColor;
						if(FPS.instance.friendList.containsKey(multiPlayer.username))
							arrowColor = FPS.instance.friendArrowColor;
						if(FPS.instance.enemyList.containsKey(multiPlayer.username))
							arrowColor = FPS.instance.enemyArrowColor;
						FPS.instance.drawRotatedTexturedModalRect(7,
								(lineCount * 10) + 23, 0, 0, 7,
								7, angleTrunc, arrowColor, Minecraft.getMinecraft());

						// draw faceplate
						boolean loaded = FPS.instance.loadDownloadableImageTexture(multiPlayer.skinUrl, "/mob/char.png");
						if(!loaded){
							Minecraft.getMinecraft().renderEngine.bindTexture("/mob/char.png");
						}
						FPS.instance.drawTexturedHeadModalRect(
								14, (lineCount * 10) + 20, 
								8, 8,
								8, 8, 
								Minecraft.getMinecraft());

						String s = multiPlayer.username;
						if (FPS.instance.showOtherPos) {
							s += " P: " + (int) multiX + ", " + (int) multiY + ", " + (int) multiZ;
						}
						s += " D: " + (int)dis;
						fontRenderer.drawString(s, 25, (lineCount * 10) + 20, 0xFFFFFF);
					}
				}
			} else {
				if(FPS.instance.showHudInfo) {
					// if single player, show position too
					fontRenderer.drawString("Pos: " + (int) playerX + ", " + (int) playerY + ", " + (int) playerZ, 2, 18, 0xe0e0e0);
				}
			}
		}
		return;
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "FPS - Player update tick";
	}

}