package zornco.monstercompress.core;

import cpw.mods.fml.common.network.IGuiHandler;
import zornco.monstercompress.blocks.TileCompressor;
import zornco.monstercompress.gui.ContainerCompressor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	
	public Object getGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if (!world.blockExists(x, y, z))
			return null;

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (!(tile instanceof TileCompressor))
			return null;

		TileCompressor engine = (TileCompressor) tile;

		switch (ID) {

		case GuiIds.UPGRADE_STATION:
			return new ContainerCompressor(player.inventory, engine);
		default:
			return null;
		}
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}
}
