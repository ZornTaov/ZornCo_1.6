package zornco.bedcraftbeyond.blocks;

import static net.minecraftforge.common.ForgeDirection.DOWN;
import java.util.List;
import java.util.Random;

import zornco.bedcraftbeyond.BedCraftBeyond;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRug extends Block {

	private Icon[] iconArray;

	public BlockRug(int par1) {
		super(par1, Material.cloth);
		this.setCreativeTab(BedCraftBeyond.bedCraftBeyondTab);
	}

	/**
	 * checks to see if you can place this block can be placed on that side of a
	 * block: BlockLever overrides
	 */
	@Override
	public boolean canPlaceBlockOnSide(World par1World, int x, int y, int z,
			int par5) {
		return par1World.doesBlockHaveSolidTopSurface(x, y - 1, z)
				|| this.isBlockValid(par1World.getBlockId(x, y - 1, z));

	}

	/**
	 * Checks to see if its valid to put this block at the specified
	 * coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int x, int y, int z) {
		return par1World.doesBlockHaveSolidTopSurface(x, y - 1, z)
				|| this.isBlockValid(par1World.getBlockId(x, y - 1, z));

	}

	private boolean isBlockValid(int blockId) {
		return (Block.blocksList[blockId] instanceof BlockStairs) || (Block.blocksList[blockId] instanceof BlockHalfSlab);
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which
	 * neighbor changed (coordinates passed are their own) Args: x, y, z,
	 * neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int x, int y, int z,
			int par5) {

		int valid = 0;

		if (par1World.isBlockSolidOnSide(x, y - 1, z, DOWN)|| this.isBlockValid(par1World.getBlockId(x, y - 1, z))) {
			valid++;
		}

		if (valid == 0) {
			this.dropBlockAsItem(par1World, x, y, z,
					par1World.getBlockMetadata(x, y, z), 0);
			par1World.setBlockToAir(x, y, z);
		}

	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this
	 * box can change after the pool has been cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x,
			int y, int z) {
		return null;

	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y,
			int z) {
		float var9 = 0F;
		float var10 = 1F;
		float var11 = 0F;
		float var12 = 0.0625F;
		float var13 = 0F;
		float var14 = 1F;
		/*int ID = world.getBlockId(x, y-1, z);
		if(ID == Block.stoneSingleSlab.blockID || ID == Block.woodSingleSlab.blockID && ((world.getBlockMetadata(x, y -1, z) & 0x8) == 0))
			this.setBlockBounds(var9, var11-0.5F, var13, var10, var12-0.5F, var14);
		else*/
			this.setBlockBounds(var9, var11, var13, var10, var12, var14);

	}

	/**
	 * Return whether an adjacent block can connect to a wall.
	 */
	public static boolean canConnectRugTo(IBlockAccess par1IBlockAccess,
			int par2, int par3, int par4) {
		int var5 = par1IBlockAccess.getBlockId(par2, par3, par4);

		return (var5 == BedCraftBeyond.rugBlock.blockID)&& !par1IBlockAccess.isAirBlock(par2, par3, par4) ? true : false;

	}
	@Override
	@SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        return Block.cloth.getIcon(par1, par2);
    }
	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This is
	 * the only chance you get to register icons.
	 */
	public void registerIcons(IconRegister par1IconRegister) {
	}

	@SideOnly(Side.CLIENT)
	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public Icon getBlockTextureFromSideAndMetadata(int par1, int par2) {
		return this.iconArray[par2];
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and
	 * wood.
	 */
	@Override
	public int damageDropped(int par1) {
		return par1;
	}

	/**
	 * Takes a dye damage value and returns the block damage value to match
	 */
	public static int getBlockFromDye(int par0) {
		return ~par0 & 15;
	}

	/**
	 * Takes a block damage value and returns the dye damage value to match
	 */
	public static int getDyeFromBlock(int par0) {
		return ~par0 & 15;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return BedCraftBeyond.rugItem.itemID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
	 */
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return BedCraftBeyond.rugItem.itemID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs,
			List par3List) {
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess,
			int par2, int par3, int par4, int par5) {
		return true;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return BedCraftBeyond.rugRI;
	}
}
