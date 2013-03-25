package zornco.megax.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	
	public Object getGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		/*if (!world.blockExists(x, y, z))
			return null;

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (!(tile instanceof TileUpgradeStation))
			return null;

		TileUpgradeStation engine = (TileUpgradeStation) tile;

		switch (ID) {

		case GuiIds.UPGRADE_STATION:
			return new ContainerUpgradeStation(player.inventory, engine);
		default:*/
			return null;
		//}
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}
}
