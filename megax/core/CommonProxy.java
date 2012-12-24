package zornco.megax.core;


import zornco.megax.blocks.TileUpgradeStation;
import zornco.megax.gui.ContainerUpgradeStation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	public static String ITEMS_PNG = "/tutorial/generic/items.png";
	public static String BLOCK_PNG = "/tutorial/generic/block.png";

	// Client stuff
	public void registerRenderInformation () {
		// Nothing here as this is the server side proxy
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}
	public int addArmor(String path) {
		return 0;
	}
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if (!world.blockExists(x, y, z))
			return null;

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (!(tile instanceof TileUpgradeStation))
			return null;

		switch (ID) {

		case GuiIds.UPGRADE_STATION:
			return new ContainerUpgradeStation(player.inventory, (TileUpgradeStation) tile);
		default:
			return null;
		}
	}
}