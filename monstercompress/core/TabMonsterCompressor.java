package zornco.monstercompress.core;

import net.minecraft.creativetab.CreativeTabs;
import zornco.monstercompress.MonsterCompressor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TabMonsterCompressor extends CreativeTabs
{
	public TabMonsterCompressor(String label) 
	{
		super(label);
	}

	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex()
	{
		return MonsterCompressor.compressor.blockID;
	}
}
