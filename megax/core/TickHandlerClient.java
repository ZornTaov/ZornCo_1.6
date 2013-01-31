package zornco.megax.core;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import zornco.megax.MegaX;
import zornco.megax.items.busters.ItemXBuster;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class TickHandlerClient implements ITickHandler {


	private boolean currentItemIsXBuster;
	private int prevCurItem;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if (type.equals(EnumSet.of(TickType.RENDER))){
			if (Minecraft.getMinecraft().theWorld != null){
				preRenderTick(Minecraft.getMinecraft(), Minecraft.getMinecraft().theWorld, ((Float)tickData[0]).floatValue());
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if (type.equals(EnumSet.of(TickType.PLAYER)))
		{
			EntityPlayer player = (EntityPlayer)tickData[0];

			if(player != null && player instanceof EntityOtherPlayerMP && player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemXBuster)
				if(player.getItemInUseCount() < player.getHeldItem().getItem().getMaxItemUseDuration(player.getHeldItem())) 
				{ 
					player.clearItemInUse(); 
					player.setItemInUse(player.getHeldItem(), player.getHeldItem().getItem().getMaxItemUseDuration(player.getHeldItem())); 
				}
		}
	}
	public void preRenderTick(Minecraft mc, World world, float renderTick)
	{
		ItemStack currentInv = mc.thePlayer.getCurrentEquippedItem();
		if(currentInv != null)
		{
			if(currentInv.getItem() instanceof ItemXBuster)
			{
				mc.playerController.resetBlockRemoving();
				if(prevCurItem == mc.thePlayer.inventory.currentItem)
				{
					try
					{
						ObfuscationReflectionHelper.setPrivateValue(ItemRenderer.class, mc.entityRenderer.itemRenderer, 1.0F, new String[] { "d", "equippedProgress" });
						ObfuscationReflectionHelper.setPrivateValue(ItemRenderer.class, mc.entityRenderer.itemRenderer, 1.0F, new String[] { "e", "prevEquippedProgress" });
						ObfuscationReflectionHelper.setPrivateValue(ItemRenderer.class, mc.entityRenderer.itemRenderer, mc.thePlayer.inventory.getCurrentItem(), new String[] { "c", "itemToRender" });
						ReflectionHelper.setPrivateValue(ItemRenderer.class, mc.entityRenderer.itemRenderer, mc.thePlayer.inventory.currentItem, new String[] { "g", "equippedItemSlot" });
					}
					catch(Exception e)
					{
						MegaX.logger.warning("Forgot to update obfuscation!");
						e.printStackTrace();
					}
					/*if(!currentItemIsGraviGun && GraviGun.getSettings("equipGraviGunSound") == 1)
					{
						mc.sndManager.playSound("gravigun.equip", (float)mc.thePlayer.posX, (float)(mc.thePlayer.posY - mc.thePlayer.yOffset), (float)mc.thePlayer.posZ, 0.3F, 1.0F);
					}*/
				}
				mc.thePlayer.isSwingInProgress = false;
				mc.thePlayer.swingProgressInt = 0;
				mc.thePlayer.swingProgress = 0;
			}
		}
		currentItemIsXBuster = currentInv != null && currentInv.getItem() instanceof ItemXBuster;
		if(prevCurItem != mc.thePlayer.inventory.currentItem)
		{
			if(mc.thePlayer.inventory.currentItem >= 0 && mc.thePlayer.inventory.currentItem <= 9)
			{
				try
				{
					if((Float)(ObfuscationReflectionHelper.getPrivateValue(ItemRenderer.class, mc.entityRenderer.itemRenderer, new String[] { "d", "equippedProgress" })) >= 1.0F)
					{
						prevCurItem = mc.thePlayer.inventory.currentItem;
					}
				}
				catch(Exception e)
				{
					MegaX.logger.warning("Forgot to update obfuscation!");
					e.printStackTrace();
				}
			}
			currentItemIsXBuster = false;
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER, TickType.RENDER);
	}

	@Override
	public String getLabel() {
		return "MegaX - TickHandler";
	}

}