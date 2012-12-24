package zornco.megax.core;

import java.util.EnumSet;

import zornco.megax.items.busters.XBusterItem;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandlerClient implements ITickHandler {


	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {

		EntityPlayer player = (EntityPlayer)tickData[0];
		
		if(player instanceof EntityOtherPlayerMP && player.getHeldItem() != null && player.getHeldItem().getItem() instanceof XBusterItem)
				if(player.getItemInUseCount() < player.getHeldItem().getItem().getMaxItemUseDuration(player.getHeldItem())) 
				{ 
					player.clearItemInUse(); 
					player.setItemInUse(player.getHeldItem(), player.getHeldItem().getItem().getMaxItemUseDuration(player.getHeldItem())); 
				}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		return "BuildCraft - Player update tick";
	}

}