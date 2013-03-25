package zornco.megax.core;

import net.minecraft.creativetab.CreativeTabs;
import zornco.megax.MegaX;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TabMegaX extends CreativeTabs
{
	public TabMegaX(String label) 
	{
		super(label);
	}

	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex()
	{
		return MegaX.megaX1Helm.itemID;
	}
}
