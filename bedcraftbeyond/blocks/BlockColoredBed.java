package zornco.bedcraftbeyond.blocks;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
	protected Icon[][] bedEndIcons;
	@SideOnly(Side.CLIENT)
	protected Icon[][] bedSideIcons;
	@SideOnly(Side.CLIENT)
	protected Icon[][] bedTopIcons;
	
	private int colorCombo = 241;

	public BlockColoredBed(int par1)
	{
		// was 134
		super(par1);
	}
	@Override
	public boolean isBed(World world, int x, int y, int z, EntityLivingBase player)
    {
        return Block.blocksList[blockID] instanceof BlockColoredBed;
    }
    @Override
	public int damageDropped(int par1)
	{
		return par1;
	}
    @Override 
    public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z)
    {

    	TileColoredBed tile = (TileColoredBed)world.getBlockTileEntity(x, y, z);
    	if (tile != null)
    	{
    		colorCombo = tile.colorCombo;
    	}
        return world.setBlockToAir(x, y, z);
    }
    @Override
    /**
     * This returns a complete list of items dropped from this block.
     *
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @param metadata Current metadata
     * @param fortune Breakers fortune level
     * @return A ArrayList containing all items this block drops
     */
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        int count = quantityDropped(metadata, fortune, world.rand);
        int combo = 241;
        for(int i = 0; i < count; i++)
        {
            int id = idDropped(metadata, world.rand, fortune);
            if (id > 0)
            {
                ret.add(new ItemStack(id, 1, colorCombo));
            }
        }
        return ret;
    }
    /**
     * Sets whether or not the bed is occupied.
     */
    public static void setBedOccupied(World par0World, int par1, int par2, int par3, boolean par4)
    {
        int l = par0World.getBlockMetadata(par1, par2, par3);

        if (par4)
        {
            l |= 4;
        }
        else
        {
            l &= -5;
        }

        par0World.setBlockMetadataWithNotify(par1, par2, par3, l, 4);
    }
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		boolean flag = super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
		
			par1World.markBlockForUpdate(par2, par3, par4);
		return flag;
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
	
	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public Icon getIcon(int par1, int par2)
	{
		return Block.planks.getIcon(par1, 0);
	}

	public static int getColorFromTile(IBlockAccess par1World, int par2, int par3, int par4) {

		TileColoredBed tile = (TileColoredBed)par1World.getBlockTileEntity(par2, par3, par4);
    	if (tile != null)
    	{
    	    return tile.colorCombo;
    	}
    	return 241;
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

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return BedCraftBeyond.bedRI;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return isBlockHeadOfBed(par1) ? 0 : BedCraftBeyond.bedItem.itemID;
	}

	@Override
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
    @Override
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
    @Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeBlockTileEntity(par2, par3, par4);
    }
}
