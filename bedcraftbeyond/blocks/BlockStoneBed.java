package zornco.bedcraftbeyond.blocks;

import java.util.Random;

import zornco.bedcraftbeyond.BedCraftBeyond;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
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
		return -1;
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) 
	{
		return BedCraftBeyond.stoneBedItem.itemID;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileStoneBed();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4) 
	{
		return super.idPicked(par1World, par2, par3, par4);
	}

}
