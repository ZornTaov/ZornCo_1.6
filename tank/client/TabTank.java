package zornco.tank.client;

import zornco.tank.Tank;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

public class TabTank extends CreativeTabs {

	public TabTank(String label) 
	{
		super(label);
	}

	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex()
	{
		return Tank.tankItem.itemID;
	}

}
