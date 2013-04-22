package zornco.reploidcraft.core;

import net.minecraft.creativetab.CreativeTabs;
import zornco.reploidcraft.ReploidCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TabReploid extends CreativeTabs
{
	public TabReploid(String label) 
	{
		super(label);
	}

	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex()
	{
		return ReploidCraft.reploidHelm.itemID;
	}
}
