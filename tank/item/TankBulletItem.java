package zornco.tank.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import zornco.tank.Tank;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class TankBulletItem extends Item
{
	@SideOnly(Side.CLIENT)
	private Icon[] bulletIconList;

	public int bulletType;
	
	public static String[] bulletNames = {"a", "b", "c", "d"};
	
	public TankBulletItem(int i, int type) 
	{
		super(i);
		bulletType = type;
		if(bulletType == 0){maxStackSize = 32;}
		if(bulletType == 1){maxStackSize = 64;}
		if(bulletType == 2){maxStackSize = 32;}
		if(bulletType == 3){maxStackSize = 64;}

		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(Tank.tanksTab);
	}
	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Gets an icon index based on an item's damage value
	 */
	public Icon getIconFromDamage(int par1)
	{
		int j = MathHelper.clamp_int(par1, 0, 3);
		return this.bulletIconList[j];
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.bulletIconList = new Icon[bulletNames.length];

		for (int i = 0; i < bulletNames.length; ++i)
		{
			this.bulletIconList[i] = par1IconRegister.registerIcon("tank:"+bulletNames[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, bulletType));
		
	}
}
