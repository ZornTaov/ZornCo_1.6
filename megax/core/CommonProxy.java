package zornco.megax.core;


import zornco.megax.blocks.TileUpgradeStation;
import zornco.megax.gui.ContainerUpgradeStation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

	// Client stuff
	public void registerRenderInformation () {
		// Nothing here as this is the server side proxy
	}
	public void registerKeyBindingHandler() {

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
	public void burst(World worldObj, double sx, double sy, double sz, float size)
	  {
	}
	public void setKeyBinding(String name, int value) {
		// TODO Auto-generated method stub
		
	}
}