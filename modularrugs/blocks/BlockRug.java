package zornco.modularrugs.blocks;

import static net.minecraftforge.common.ForgeDirection.DOWN;
import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.UP;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.util.List;
import java.util.Random;

import zornco.modularrugs.ModularRugs;
import zornco.modularrugs.core.TabModularRugs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockRug extends Block {

	private Icon[] iconArray;

	public BlockRug(int par1) {
		super(par1, Material.cloth);
		this.setCreativeTab(ModularRugs.modularRugsTab);
	}

	/**
	 * checks to see if you can place this block can be placed on that side of a
	 * block: BlockLever overrides
	 */
	public boolean canPlaceBlockOnSide(World par1World, int x, int y, int z,
			int par5) {
		/*
		 * ForgeDirection dir = ForgeDirection.getOrientation(par5); return (dir
		 * == DOWN && (par1World.isBlockSolidOnSide(x, y - 1, z, DOWN ))) ||
		 * (dir == UP && (par1World.isBlockSolidOnSide(x, y - 1, z, UP ))) ||
		 * (dir == NORTH && (par1World.isBlockSolidOnSide(x, y - 1, z, NORTH)))
		 * || (dir == SOUTH && (par1World.isBlockSolidOnSide(x, y - 1, z,
		 * SOUTH))) || (dir == WEST && (par1World.isBlockSolidOnSide(x, y - 1,
		 * z, WEST ))) || (dir == EAST && (par1World.isBlockSolidOnSide(x, y -
		 * 1, z, EAST )));
		 */
		return par1World.doesBlockHaveSolidTopSurface(x, y - 1, z)
				|| this.isIdAValid(par1World.getBlockId(x, y - 1, z));

	}

	/**
	 * Checks to see if its valid to put this block at the specified
	 * coordinates. Args: world, x, y, z
	 */
	public boolean canPlaceBlockAt(World par1World, int x, int y, int z) {
		return par1World.doesBlockHaveSolidTopSurface(x, y - 1, z)
				|| this.isIdAValid(par1World.getBlockId(x, y - 1, z));

	}

	private boolean isIdAValid(int blockId) {
		return blockId == Block.stairCompactCobblestone.blockID
				|| blockId == Block.stairCompactPlanks.blockID
				|| blockId == Block.stairsBrick.blockID
				|| blockId == Block.stairsNetherBrick.blockID
				|| blockId == Block.stairsSandStone.blockID
				|| blockId == Block.stairsStoneBrickSmooth.blockID
				|| blockId == Block.stairsWoodBirch.blockID
				|| blockId == Block.stairsWoodJungle.blockID
				|| blockId == Block.stairsWoodSpruce.blockID
				|| blockId == Block.stoneSingleSlab.blockID
				|| blockId == Block.woodSingleSlab.blockID;
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which
	 * neighbor changed (coordinates passed are their own) Args: x, y, z,
	 * neighbor blockID
	 */
	public void onNeighborBlockChange(World par1World, int x, int y, int z,
			int par5) {

		int valid = 0;

		if (par1World.isBlockSolidOnSide(x, y - 1, z, DOWN)|| this.isIdAValid(par1World.getBlockId(x, y - 1, z))) {
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
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x,
			int y, int z) {
		/*float var9 = 0F;
		float var10 = 1F;
		float var11 = 0F;
		float var12 = 0.0F;
		float var13 = 0F;
		float var14 = 1F;

		return AxisAlignedBB.getAABBPool().getAABB((double) ((float) x + var9),
				(double) ((float) y + var11), (double) ((float) z + var13),
				(double) ((float) x + var10), (double) ((float) y + var12),
				(double) ((float) z + var14));*/
		return null;

	}

	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y,
			int z) {
		float var9 = 0F;
		float var10 = 1F;
		float var11 = 0F;
		float var12 = 0.0625F;
		float var13 = 0F;
		float var14 = 1F;

		this.setBlockBounds(var9, var11, var13, var10, var12, var14);

	}

	/**
	 * Return whether an adjacent block can connect to a wall.
	 */
	public static boolean canConnectRugTo(IBlockAccess par1IBlockAccess,
			int par2, int par3, int par4) {
		int var5 = par1IBlockAccess.getBlockId(par2, par3, par4);

		return (var5 == ModularRugs.rugBlock.blockID)&& par1IBlockAccess.isAirBlock(par2, par3, par4) ? true : false;

	}

	@SideOnly(Side.CLIENT)

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This is
	 * the only chance you get to register icons.
	 */
	public void registerIcons(IconRegister par1IconRegister) {
		this.iconArray = new Icon[16];

		for (int i = 0; i < this.iconArray.length; ++i) {
			this.iconArray[i] = par1IconRegister.registerIcon("cloth_" + i);
		}
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
	public int idDropped(int par1, Random par2Random, int par3) {
		return ModularRugs.rugItem.itemID;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
	 */
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return ModularRugs.rugItem.itemID;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		for (int var4 = 0; var4 < 16; ++var4) {
			// par3List.add(new ItemStack(par1, 1, var4));
		}
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube() {
		return false;
	}

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
	public int getRenderType() {
		return ModularRugs.rugRI;
	}
}
