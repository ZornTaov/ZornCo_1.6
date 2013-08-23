package zornco.bedcraftbeyond.client;

import zornco.bedcraftbeyond.BedCraftBeyond;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

public class TabBedCraftBeyond extends CreativeTabs {

	public TabBedCraftBeyond(String label) 
	{
		super(label);
	}

	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex()
	{
		return BedCraftBeyond.rugItem.itemID;
	}

}
