package zornco.bedcraftbeyond.blocks;

import java.util.Random;

import zornco.bedcraftbeyond.BedCraftBeyond;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Direction;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStoneBed extends BlockColoredBed {

	public BlockStoneBed(int par1) {
		super(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return Block.stoneSingleSlab.getIcon(par1, par2);
	}

	@Override
	public int getRenderType() {
		return BedCraftBeyond.stoneBedRI;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta, int pass, IBlockAccess par1World, int par2, int par3, int par4) 
	{
		if (side == 0)
		{
			return Block.planks.getIcon(side, getColorFromInt(getColorFromTile(par1World, par2, par3, par4), 0));
		}
		else
		{
			int k = getDirection(meta);
			int l = Direction.bedDirection[k][side];
			int i1 = isBlockHeadOfBed(meta) ? 1 : 0;
			return (i1 != 1 || l != 2) && (i1 != 0 || l != 3) ? (l != 5 && l != 4 ? bedTopIcons[pass][i1] : bedSideIcons[pass][i1]) : bedEndIcons[pass][i1];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) 
	{
		this.bedTopIcons = new Icon[][] {{par1IconRegister.registerIcon("bedcraftbeyond:bed_feet_top_0"), par1IconRegister.registerIcon("bedcraftbeyond:bed_head_top_0")},
				{par1IconRegister.registerIcon("bedcraftbeyond:bed_feet_top_1"), par1IconRegister.registerIcon("bedcraftbeyond:bed_head_top_1")},
				{par1IconRegister.registerIcon("bedcraftbeyond:bed_feet_top_2"), par1IconRegister.registerIcon("bedcraftbeyond:bed_head_top_2")}};
		this.bedEndIcons = new Icon[][] {{par1IconRegister.registerIcon("bedcraftbeyond:bed_feet_end_0"), par1IconRegister.registerIcon("bedcraftbeyond:bed_head_end_0")},
				{par1IconRegister.registerIcon("bedcraftbeyond:bed_feet_end_1"), par1IconRegister.registerIcon("bedcraftbeyond:bed_head_end_1")},
				{par1IconRegister.registerIcon("bedcraftbeyond:bed_feet_end_2"), par1IconRegister.registerIcon("bedcraftbeyond:bed_head_end_2")}};
		this.bedSideIcons = new Icon[][] {{par1IconRegister.registerIcon("bedcraftbeyond:bed_feet_side_0"), par1IconRegister.registerIcon("bedcraftbeyond:bed_head_side_0")},
				{par1IconRegister.registerIcon("bedcraftbeyond:bed_feet_side_1"), par1IconRegister.registerIcon("bedcraftbeyond:bed_head_side_1")},
				{par1IconRegister.registerIcon("bedcraftbeyond:bed_feet_side_2"), par1IconRegister.registerIcon("bedcraftbeyond:bed_head_side_2")}};
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) 
	{
		return BedCraftBeyond.stoneBedItem.itemID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4) 
	{
		return super.idPicked(par1World, par2, par3, par4);
	}

}
