package zornco.modularrugs.client;

import zornco.modularrugs.ModularRugs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

public class TabModularRugs extends CreativeTabs {

	public TabModularRugs(String label) 
	{
		super(label);
	}

	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex()
	{
		return ModularRugs.rugItem.itemID;
	}

}
