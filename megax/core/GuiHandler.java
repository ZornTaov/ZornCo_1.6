package zornco.megax.core;

import cpw.mods.fml.common.network.IGuiHandler;
import zornco.megax.blocks.TileUpgradeStation;
import zornco.megax.gui.ContainerUpgradeStation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	
	public Object getGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if (!world.blockExists(x, y, z))
			return null;

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (!(tile instanceof TileUpgradeStation))
			return null;

		TileUpgradeStation engine = (TileUpgradeStation) tile;

		switch (ID) {

		case GuiIds.UPGRADE_STATION:
			return new ContainerUpgradeStation(player.inventory, engine);
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
