package zornco.megax.blocks;

import static net.minecraftforge.common.ForgeDirection.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockSpikes extends Block {

	public BlockSpikes(int par1) {
		super(par1, Material.iron);
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

    public int func_85104_a(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        int var11 = par9 & 8;
        int var10 = par9 & 7;
        var10 = -1;

        if (par5 == 0 && par1World.isBlockSolidOnSide(par2, par3 + 1, par4, DOWN))
        {
            var10 = par1World.rand.nextBoolean() ? 0 : 7;
        }

        if (par5 == 1 && par1World.isBlockSolidOnSide(par2, par3 - 1, par4, UP))
        {
            var10 = 5 + par1World.rand.nextInt(2);
        }

        if (par5 == 2 && par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH))
        {
            var10 = 4;
        }

        if (par5 == 3 && par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH))
        {
            var10 = 3;
        }

        if (par5 == 4 && par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST))
        {
            var10 = 2;
        }

        if (par5 == 5 && par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST))
        {
            var10 = 1;
        }

        return var10 + var11;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (this.checkIfAttachedToBlock(par1World, par2, par3, par4))
        {
            int var6 = par1World.getBlockMetadata(par2, par3, par4) & 7;
            boolean var7 = false;

            if (!par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST) && var6 == 1)
            {
                var7 = true;
            }

            if (!par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST) && var6 == 2)
            {
                var7 = true;
            }

            if (!par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH) && var6 == 3)
            {
                var7 = true;
            }

            if (!par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH) && var6 == 4)
            {
                var7 = true;
            }

            if (!par1World.isBlockSolidOnSide(par2, par3 - 1, par4, UP) && var6 == 5)
            {
                var7 = true;
            }

            if (!par1World.isBlockSolidOnSide(par2, par3 - 1, par4, UP) && var6 == 6)
            {
                var7 = true;
            }

            if (!par1World.isBlockSolidOnSide(par2, par3 + 1, par4, DOWN) && var6 == 0)
            {
                var7 = true;
            }

            if (!par1World.isBlockSolidOnSide(par2, par3 + 1, par4, DOWN) && var6 == 7)
            {
                var7 = true;
            }

            if (var7)
            {
                this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
                par1World.setBlockWithNotify(par2, par3, par4, 0);
            }
        }
    }

    /**
     * Checks if the block is attached to another block. If it is not, it returns false and drops the block as an item.
     * If it is it returns true.
     */
    private boolean checkIfAttachedToBlock(World par1World, int par2, int par3, int par4)
    {
        if (!this.canPlaceBlockAt(par1World, par2, par3, par4))
        {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        float var5 = 0.0625F;
        return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)par2 + var5), (double)par3, (double)((float)par4 + var5), (double)((float)(par2 + 1) - var5), (double)((float)(par3 + 1) - var5), (double)((float)(par4 + 1) - var5));
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        float var5 = 0.0625F;
        return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)par2 + var5), (double)par3, (double)((float)par4 + var5), (double)((float)(par2 + 1) - var5), (double)(par3 + 1), (double)((float)(par4 + 1) - var5));
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
        return 13;
    }

}
