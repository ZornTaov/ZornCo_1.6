package zornco.megax.blocks;

import static net.minecraftforge.common.ForgeDirection.*;
import zornco.megax.MegaX;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockSpikes extends Block {

	public BlockSpikes(int par1) {
		super(par1, Material.iron);
		this.setCreativeTab(MegaX.megaXTab);
		this.blockIndexInTexture = 22;
		// TODO Auto-generated constructor stub
	}

	/**
	 * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
	 */
	public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
	{
		ForgeDirection dir = ForgeDirection.getOrientation(par5);
		return (dir == DOWN  && par1World.isBlockSolidOnSide(par2, par3 + 1, par4, DOWN )) ||
				(dir == UP    && par1World.isBlockSolidOnSide(par2, par3 - 1, par4, UP   )) ||
				(dir == NORTH && par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH)) ||
				(dir == SOUTH && par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH)) ||
				(dir == WEST  && par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST )) ||
				(dir == EAST  && par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST ));
	}
	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST ) ||
				par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST ) ||
				par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH) ||
				par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH) ||
				par1World.isBlockSolidOnSide(par2, par3 - 1, par4, UP   ) ||
				par1World.isBlockSolidOnSide(par2, par3 + 1, par4, DOWN );
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{

		int valid = 0;

		if (par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST))
		{
			valid++;
		}

		if (par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST))
		{
			valid++;
		}

		if (par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH))
		{
			valid++;
		}

		if (par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH))
		{
			valid++;
		}

		if (par1World.isBlockSolidOnSide(par2, par3 - 1, par4, UP))
		{
			valid++;
		}

		if (par1World.isBlockSolidOnSide(par2, par3 - 1, par4, UP))
		{
			valid++;
		}

		if (par1World.isBlockSolidOnSide(par2, par3 + 1, par4, DOWN))
		{
			valid++;
		}

		if (par1World.isBlockSolidOnSide(par2, par3 + 1, par4, DOWN))
		{
			valid++;
		}

		if (valid == 0)
		{
			this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockWithNotify(par2, par3, par4, 0);
		}

	}
	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		int var1 = 0;
		boolean var3 = (canConnectSpikesTo(world, x - 1, y, z));
		boolean var4 = (canConnectSpikesTo(world, x + 1, y, z));
		boolean var5 = (canConnectSpikesTo(world, x, y - 1, z));
		boolean var6 = (canConnectSpikesTo(world, x, y + 1, z));
		boolean var7 = (canConnectSpikesTo(world, x, y, z - 1));
		boolean var8 = (canConnectSpikesTo(world, x, y, z + 1));

		float var9 = 0F; 
		float var10 = 1F;
		float var11 = 0F;
		float var12 = 1F;
		float var13 = 0F;
		float var14 = 1F;

		if (var3) var1++;
		if (var4) var1++;
		if (var5) var1++;
		if (var6) var1++;
		if (var7) var1++;
		if (var8) var1++;

		if (var3) var10 = 0.5F; 
		if (var4) var9 = 0.5F;
		if (var5) var12 = 0.5F;
		if (var6) var11 = 0.5F;
		if (var7) var14 = 0.5F;
		if (var8) var13 = 0.5F;
		if (var1 > 1)
		{
			var9 = 0F; 
			var10 = 1F;
			var11 = 0F;
			var12 = 1F;
			var13 = 0F;
			var14 = 1F;
		}
		return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)x + var9), (double)((float)y + var11), (double)((float)z + var13),(double)((float)x + var10), (double)((float)y + var12), (double)((float)z + var14));

	}
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		int var1 = 0;
		boolean var3 = (canConnectSpikesTo(world, x - 1, y, z));
		boolean var4 = (canConnectSpikesTo(world, x + 1, y, z));
		boolean var5 = (canConnectSpikesTo(world, x, y - 1, z));
		boolean var6 = (canConnectSpikesTo(world, x, y + 1, z));
		boolean var7 = (canConnectSpikesTo(world, x, y, z - 1));
		boolean var8 = (canConnectSpikesTo(world, x, y, z + 1));

		float var9 = 0F; 
		float var10 = 1F;
		float var11 = 0F;
		float var12 = 1F;
		float var13 = 0F;
		float var14 = 1F;

		if (var3) var1++;
		if (var4) var1++;
		if (var5) var1++;
		if (var6) var1++;
		if (var7) var1++;
		if (var8) var1++;

		if (var3) var10 = 0.5F; 
		if (var4) var9 = 0.5F;
		if (var5) var12 = 0.5F;
		if (var6) var11 = 0.5F;
		if (var7) var14 = 0.5F;
		if (var8) var13 = 0.5F;
		if (var1 > 1)
		{
			var9 = 0F; 
			var10 = 1F;
			var11 = 0F;
			var12 = 1F;
			var13 = 0F;
			var14 = 1F;
		}

		this.setBlockBounds(var9, var11, var13, var10, var12, var14);

	}

	/**
	 * Return whether an adjacent block can connect to a wall.
	 */
	public boolean canConnectSpikesTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockId(par2, par3, par4);

		if (var5 != this.blockID)
		{
			Block var6 = Block.blocksList[var5];
			return var6 != null && var6.blockMaterial.isOpaque() && var6.renderAsNormalBlock() ? var6.blockMaterial != Material.pumpkin : false;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
	 */
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		par5Entity.attackEntityFrom(DamageSource.cactus, 1);
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	public int getRenderType()
	{
		return MegaX.spikesRI;
	}
	@SideOnly(Side.CLIENT)

	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}

}
