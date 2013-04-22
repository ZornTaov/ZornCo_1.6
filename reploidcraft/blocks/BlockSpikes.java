package zornco.reploidcraft.blocks;

import static net.minecraftforge.common.ForgeDirection.*;
import zornco.reploidcraft.ReploidCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockSpikes extends Block {

	public BlockSpikes(int par1) {
		super(par1, Material.iron);
		this.setCreativeTab(ReploidCraft.reploidTab);
		//this.blockIndexInTexture = 22;
	}

	/**
	 * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
	 */
	public boolean canPlaceBlockOnSide(World par1World, int x, int y, int z, int par5)
	{
		ForgeDirection dir = ForgeDirection.getOrientation(par5);
		return (dir == DOWN  && (par1World.isBlockSolidOnSide(x, y + 1, z, DOWN ) || par1World.getBlockMaterial(x, y + 1, z) == Material.piston)) ||
				(dir == UP    && (par1World.isBlockSolidOnSide(x, y - 1, z, UP   ) || par1World.getBlockMaterial(x, y - 1, z) == Material.piston)) ||
				(dir == NORTH && (par1World.isBlockSolidOnSide(x, y, z + 1, NORTH) || par1World.getBlockMaterial(x, y, z + 1) == Material.piston)) ||
				(dir == SOUTH && (par1World.isBlockSolidOnSide(x, y, z - 1, SOUTH) || par1World.getBlockMaterial(x, y, z - 1) == Material.piston)) ||
				(dir == WEST  && (par1World.isBlockSolidOnSide(x + 1, y, z, WEST ) || par1World.getBlockMaterial(x + 1, y, z) == Material.piston)) ||
				(dir == EAST  && (par1World.isBlockSolidOnSide(x - 1, y, z, EAST ) || par1World.getBlockMaterial(x - 1, y, z) == Material.piston));
	}
	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	public boolean canPlaceBlockAt(World par1World, int x, int y, int z)
	{
		return (par1World.isBlockSolidOnSide(x - 1, y, z, EAST ) || par1World.getBlockMaterial(x - 1, y, z) == Material.piston) ||
				(par1World.isBlockSolidOnSide(x + 1, y, z, WEST ) || par1World.getBlockMaterial(x + 1, y, z) == Material.piston) ||
				(par1World.isBlockSolidOnSide(x, y, z - 1, SOUTH) || par1World.getBlockMaterial(x, y, z - 1) == Material.piston) ||
				(par1World.isBlockSolidOnSide(x, y, z + 1, NORTH) || par1World.getBlockMaterial(x, y, z + 1) == Material.piston) ||
				(par1World.isBlockSolidOnSide(x, y - 1, z, UP   ) || par1World.getBlockMaterial(x, y - 1, z) == Material.piston) ||
				(par1World.isBlockSolidOnSide(x, y + 1, z, DOWN ) || par1World.getBlockMaterial(x, y + 1, z) == Material.piston);
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	public void onNeighborBlockChange(World par1World, int x, int y, int z, int par5)
	{

		int valid = 0;

		if (par1World.isBlockSolidOnSide(x - 1, y, z, EAST) || par1World.getBlockMaterial(x - 1, y, z) == Material.piston)
		{
			valid++;
		}

		if (par1World.isBlockSolidOnSide(x + 1, y, z, WEST) || par1World.getBlockMaterial(x + 1, y, z) == Material.piston)
		{
			valid++;
		}

		if (par1World.isBlockSolidOnSide(x, y, z - 1, SOUTH) || par1World.getBlockMaterial(x, y, z - 1) == Material.piston)
		{
			valid++;
		}

		if (par1World.isBlockSolidOnSide(x, y, z + 1, NORTH) || par1World.getBlockMaterial(x, y, z + 1) == Material.piston)
		{
			valid++;
		}

		if (par1World.isBlockSolidOnSide(x, y - 1, z, UP) || par1World.getBlockMaterial(x, y - 1, z) == Material.piston)
		{
			valid++;
		}

		if (par1World.isBlockSolidOnSide(x, y + 1, z, DOWN) || par1World.getBlockMaterial(x, y + 1, z) == Material.piston)
		{
			valid++;
		}

		if (valid == 0)
		{
			this.dropBlockAsItem(par1World, x, y, z, par1World.getBlockMetadata(x, y, z), 0);
			par1World.setBlockToAir(x, y, z);
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
		return AxisAlignedBB.getAABBPool().getAABB((double)((float)x + var9), (double)((float)y + var11), (double)((float)z + var13),(double)((float)x + var10), (double)((float)y + var12), (double)((float)z + var14));

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
			return var6 != null && var6.blockMaterial.isOpaque() && (var6.renderAsNormalBlock() || ( var6.blockMaterial == Material.piston && var6.blockID != Block.pistonMoving.blockID)) ? var6.blockMaterial != Material.pumpkin : false;
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

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return this.blockIcon;
    }

    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("blockIron");
    }

	/**
	 * The type of render function that is called for this block
	 */
	public int getRenderType()
	{
		return ReploidCraft.config.spikesRI;
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
