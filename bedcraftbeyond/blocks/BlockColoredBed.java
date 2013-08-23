package zornco.bedcraftbeyond.blocks;

import java.util.Iterator;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumStatus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import zornco.bedcraftbeyond.BedCraftBeyond;
import zornco.bedcraftbeyond.item.ItemColoredBed;

public class BlockColoredBed extends BlockBed implements ITileEntityProvider
{

	/** Maps the head-of-bed block to the foot-of-bed block. */
	/** Maps the foot-of-bed block to the head-of-bed block. */
	public static final int[][] footBlockToHeadBlockMap = new int[][] {{0, 1}, { -1, 0}, {0, -1}, {1, 0}};
	@SideOnly(Side.CLIENT)
	private static Icon[][] bedEndIcons;
	@SideOnly(Side.CLIENT)
	private static Icon[][] bedSideIcons;
	@SideOnly(Side.CLIENT)
	private static Icon[][] bedTopIcons;
	public int woodTex;
	public int pillowCol;
	public int sheetCol;

	public BlockColoredBed(int par1)
	{
		// was 134
		super(par1);
	}
	public BlockColoredBed(int par1, int woodTex, int pillowCol, int sheetCol)
	{
		this(par1);
		System.out.println("BlockColoredBed Sheet:"+sheetCol+" Pillow:"+pillowCol+" Wood:"+woodTex);
		this.woodTex = woodTex;
		this.pillowCol = pillowCol;
		this.sheetCol = sheetCol;
	}
	@Override
	public boolean isBed(World world, int x, int y, int z, EntityLivingBase player)
    {
        return blockID == BedCraftBeyond.bedBlock.blockID;
    }
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
    	super.onBlockActivated( par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
    	/*if (par1World.isRemote)
        {
            return true;
        }
        else
        {*/
        	TileColoredBed tile = (TileColoredBed)par1World.getBlockTileEntity(par2, par3, par4);
        	if (tile != null)
        	{
        	    BedCraftBeyond.logger.info(tile.colorCombo+"");
        	}
        	return true;
        //}
    }
	public int damageDropped(int par1)
	{
		System.out.println("damageDropped:"+par1);
		return par1;
	}

	public static int getColorFromInt(int meta, int color)
	{
		switch (color)
		{
		case 0:
			return meta >> 8 & 0xF;
		case 1:
			return meta >> 4 & 0xF;
		case 2:
			return meta & 0xF;
		}
		return 0;
	}
	
	@SideOnly(Side.CLIENT)

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public Icon getIcon(int par1, int par2)
	{
		//if (par1 == 0)
		// {
		return Block.planks.getIcon(par1, 0);
		//}
		/*else
        {
            int k = getDirection(par2);
            int l = Direction.bedDirection[k][par1];
            int i1 = isBlockHeadOfBed(par2) ? 1 : 0;
            return (i1 != 1 || l != 2) && (i1 != 0 || l != 3) ? (l != 5 && l != 4 ? this.bedTopIcons[i1] : this.bedSideIcons[i1]) : this.field_94472_b[i1];
        }*/
	}

	public static int getColorFromTile(IBlockAccess par1World, int par2, int par3, int par4) {
		TileColoredBed tile = (TileColoredBed)par1World.getBlockTileEntity(par2, par3, par4);
    	if (tile != null)
    	{
    	    return tile.colorCombo;
    	}
    	return 0;
	}
	
	public static int getColorFromTilePerPass(IBlockAccess par1World, int par2, int par3, int par4, int pass)
	{
		int combo = getColorFromTile(par1World, par2, par3, par4);
		switch(pass)
		{
		case 0:
			return ItemDye.dyeColors[getColorFromInt(combo, 2)];
		case 1:
			return ItemDye.dyeColors[getColorFromInt(combo, 1)];
		case 2:
			return ItemColoredBed.woodColors[getColorFromInt(combo, 0)];
		}
		return 0;
	}
	
	@SideOnly(Side.CLIENT)

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public static Icon getIcon(int side, int meta, int pass, IBlockAccess par1World, int par2, int par3, int par4)
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

	@SideOnly(Side.CLIENT)

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
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
	/*
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	/*public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        if (par1 == 0)
        {
        	//Todo change to use the wood color value
            return 3;
        }
        else
        {
            int var3 = getDirection(par2);
            int var4 = Direction.bedDirection[var3][par1];
            return isBlockFootOfBed(par2) ? (
            			var4 == 2 ? this.blockIndexInTexture + 2 + 16 : ( // x + 2, y + 1
            					var4 != 5 && var4 != 4 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture + 1 + 16
            				)
            			) : (
            				var4 == 3 ? this.blockIndexInTexture - 1 + 16 : (
            					var4 != 5 && var4 != 4 ? this.blockIndexInTexture : this.blockIndexInTexture + 16
            			)
            		);
        }
    }*/

	/**
	 * The type of render function that is called for this block
	 */
	public int getRenderType()
	{
		return BedCraftBeyond.bedRI;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return isBlockHeadOfBed(par1) ? 0 : BedCraftBeyond.bedBlock.blockID;
	}

	/*
    public void onBlockRemoval(World par1World, int par2, int par3, int par4)
    {


		float var8 = par1World.rand.nextFloat() * 0.8F + 0.1F;
		float var9 = par1World.rand.nextFloat() * 0.8F + 0.1F;
		float var10 = par1World.rand.nextFloat() * 0.8F + 0.1F;

		int var11 = par1World.rand.nextInt(21) + 10;

		EntityItem var12 = new EntityItem(par1World,
				(double) ((float) par2 + var8), (double) ((float) par3 + var9),
				(double) ((float) par4 + var10), new ItemStack(mod_RainbowBed.itemRBed,
						var11, par1World.getItemDamage()));

		float var13 = 0.05F;
		var12.motionX = (double) ((float) par1World.rand.nextGaussian() * var13);
		var12.motionY = (double) ((float) par1World.rand.nextGaussian() * var13 + 0.2F);
		var12.motionZ = (double) ((float) par1World.rand.nextGaussian() * var13);
		par1World.spawnEntityInWorld(var12);

        super.onBlockRemoval(par1World, par2, par3, par4);
    }
	 */
	@SideOnly(Side.CLIENT)

	/**
	 * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
	 */
	public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return BedCraftBeyond.bedItem.itemID;
	}

    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDamageValue(World par1World, int i, int j, int k)
    {
    	TileColoredBed tile = (TileColoredBed)par1World.getBlockTileEntity(i, j, k);
    	if (tile != null)
    	{
    	    return tile.colorCombo;
    	}
        return 241;
    }
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileColoredBed();
	}

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeBlockTileEntity(par2, par3, par4);
    }
}
