package zornco.fps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.client.registry.KeyBindingRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;

public class ClientProxy extends CommonProxy {
	Minecraft mc; 
	
	public void init() {
		this.mc = ModLoader.getMinecraftInstance();

		KeyBindingRegistry.registerKeyBinding(new FPSKeyHandler());
	}
	/**
	 * Player List Sorter
	 */
	public void refresh() {
		FPS.currentPlayerList.clear();
		FPS.neutralList.clear();
		for (Entity entity : (List<Entity>) ModLoader.getMinecraftInstance().theWorld.getLoadedEntityList()) {
			if (
					entity instanceof EntityPlayer && 
					!entity.isDead && 
					((EntityPlayer)entity).username != this.mc.thePlayer.username) {
				FPS.currentPlayerList.add((EntityPlayer) entity);
				if(FPS.friendList.containsKey(((EntityPlayer)entity).username)) {
					FPS.friendList.put(((EntityPlayer)entity).username, true);
					continue;
				}
				if(FPS.enemyList.containsKey(((EntityPlayer)entity).username)) {
					FPS.enemyList.put(((EntityPlayer)entity).username, true);
					continue;
				}
				FPS.neutralList.put(((EntityPlayer)entity).username, true);
			}
		}
		Collections.sort(FPS.currentPlayerList, new Comparator<EntityPlayer>() {
			@Override
			public int compare(EntityPlayer o1, EntityPlayer o2) {
				int pl1 = (int)o1.getDistanceToEntity(ModLoader.getMinecraftInstance().thePlayer);
				int pl2 = (int)o2.getDistanceToEntity(ModLoader.getMinecraftInstance().thePlayer);
				if (pl1 < pl2)
					return -1;
				if (pl1 > pl2)
					return 1;
				return o1.username.compareToIgnoreCase(o2.username);
				//return 0;
			}
		});
	}
	public void loadList(String filename, HashMap list)
	{
		File f;	
		try
		{
			list.clear();
			f = new File(this.mc.mcDataDir + "\\config", "mod_fps." + filename );
			BufferedReader inFile = new BufferedReader(new FileReader(f));
			String buf = "";
			int line = 0;
			while ((buf = inFile.readLine()) != null)
			{
				list.put(buf.trim(),false);
			}

			inFile.close();
		}
		catch (Exception e)
		{
			System.out.println("Failed to load " + "mod_fps." + filename + " : " + e);
		}
	}

	public void saveList(String filename, HashMap list)
	{
		File f;	
		try
		{
			f = new File(this.mc.mcDataDir + "\\config", "mod_fps." + filename );
			PrintWriter outFile = new PrintWriter(new FileWriter(f, false));
			Iterator items = list.keySet().iterator();

			while (items.hasNext())
			{
				outFile.println((String)(items.next()));
			}

			outFile.close();
		}
		catch (Exception e)
		{
			System.out.println("Failed to save " + "mod_fps." + filename + " : " + e);
		}
	}

	public void loadOptions()
	{
		File f;	
		try 
		{
			f = new File(this.mc.mcDataDir + "\\config", "mod_fps." + FPS.optionsFilename );
			BufferedReader inFile = new BufferedReader(new FileReader(f));
			String buf = "";
			buf = inFile.readLine();
			FPS.friendArrowColor = (Integer.valueOf(buf.trim()));
			buf = inFile.readLine();
			FPS.enemyArrowColor = (Integer.valueOf(buf.trim()));
			buf = inFile.readLine();
			FPS.neutralArrowColor = (Integer.valueOf(buf.trim()));
			buf = inFile.readLine();
			FPS.useOldSkinServ = buf.toLowerCase().equals("true");
			buf = inFile.readLine();
			FPS.showHudInfo = buf.toLowerCase().equals("true");
			buf = inFile.readLine();
			FPS.showOtherPos = buf.toLowerCase().equals("true");
			inFile.close();
		}
		catch (Exception e)
		{
			System.out.println("Failed to load " + "mod_fps." + FPS.optionsFilename + " : " + e);
		}
	}

	public void saveOptions()
	{
		File f;
		try
		{
			f = new File(this.mc.mcDataDir + "\\config", "mod_fps." + FPS.optionsFilename );
			PrintWriter outFile = new PrintWriter(new FileWriter(f, false));
			outFile.println(FPS.friendArrowColor);
			outFile.println(FPS.enemyArrowColor);
			outFile.println(FPS.neutralArrowColor);
			outFile.println(FPS.useOldSkinServ?"true":"false");
			outFile.println(FPS.showHudInfo?"true":"false");
			outFile.println(FPS.showOtherPos?"true":"false");
			outFile.close();
		}
		catch (Exception e)
		{
			System.out.println("Failed to save " + "mod_fps." + FPS.optionsFilename + " : " + e);
		}
	}
	public void drawRotatedTexturedModalRect(int posX, int posY, int texU, int texV, int width, int height, double angle, int colorIndex, Minecraft minecraft)
	{
		float texUPixel = 0.125F;
		float texVPixel = 0.125F;
	
		double x1, x2, x3, x4, y1, y2, y3, y4, left, right, top, bottom;
		left = -(double)width*.6D;
		right = (double)width*.6D;
		top = -(double)height*.6D;
		bottom = (double)height*.6D;
	
		x1= left*Math.cos(angle)-bottom*Math.sin(angle);
	
		y1=left*Math.sin(angle)+bottom*Math.cos(angle);
	
		x2=right*Math.cos(angle)-bottom*Math.sin(angle);
	
		y2=right*Math.sin(angle)+bottom*Math.cos(angle);
	
		x3=left*Math.cos(angle)-top*Math.sin(angle);
	
		y3=left*Math.sin(angle)+top*Math.cos(angle);
	
		x4=right*Math.cos(angle)-top*Math.sin(angle);
	
		y4=right*Math.sin(angle)+top*Math.cos(angle);
	
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setColorOpaque_I(colorIndex);
		tess.addVertexWithUV(x1+(double)posX,y1+(double)posY, 0.0F, (double)((float)(texU + 0) * texUPixel), (double)((float)(texV + height) * texVPixel));
		tess.addVertexWithUV(x2+(double)posX,y2+(double)posY, 0.0F, (double)((float)(texU + width) * texUPixel), (double)((float)(texV + height) * texVPixel));
		tess.addVertexWithUV(x4+(double)posX,y4+(double)posY, 0.0F, (double)((float)(texU + width) * texUPixel), (double)((float)(texV + 0) * texVPixel));
		tess.addVertexWithUV(x3+(double)posX,y3+(double)posY, 0.0F, (double)((float)(texU + 0) * texUPixel), (double)((float)(texV + 0) * texVPixel));
		tess.draw();
	}
	public void drawTexturedHeadModalRect(int posX, int posY, int texU, int texV, int width, int height, Minecraft minecraft)
	{
		float texUPixel = 0.015625F;
		float texVPixel = 0.03125F;
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV((double)(posX + 0), (double)(posY + height), 0.0F/*(double)minecraft.ingameGUI.zLevel*/, (double)((float)(texU + 0) * texUPixel), (double)((float)(texV + height) * texVPixel));
		tess.addVertexWithUV((double)(posX + width), (double)(posY + height),0.0F, (double)((float)(texU + width) * texUPixel), (double)((float)(texV + height) * texVPixel));
		tess.addVertexWithUV((double)(posX + width), (double)(posY + 0), 0.0F, (double)((float)(texU + width) * texUPixel), (double)((float)(texV + 0) * texVPixel));
		tess.addVertexWithUV((double)(posX + 0), (double)(posY + 0), 0.0F, (double)((float)(texU + 0) * texUPixel), (double)((float)(texV + 0) * texVPixel));
		tess.draw();
	}
}
