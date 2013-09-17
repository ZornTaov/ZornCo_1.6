package zornco.tank.item;

import zornco.tank.entity.EntityTankBullet;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public final class DispenserBehaviorBullet extends BehaviorDefaultDispenseItem
{
	/**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
    {
        EnumFacing enumfacing = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
        double d0 = par1IBlockSource.getX() + (double)enumfacing.getFrontOffsetX();
        double d1 = (double)((float)par1IBlockSource.getYInt() + 0.2F);
        double d2 = par1IBlockSource.getZ() + (double)enumfacing.getFrontOffsetZ();
        IPosition iposition = BlockDispenser.getIPositionFromBlockSource(par1IBlockSource);
        EntityTankBullet entityfireworkrocket = new EntityTankBullet(par1IBlockSource.getWorld(), iposition.getX(), iposition.getY(), iposition.getZ(), par2ItemStack);
        entityfireworkrocket.setThrowableHeading((double)enumfacing.getFrontOffsetX(), (double)((float)enumfacing.getFrontOffsetY() + 0.01F), (double)enumfacing.getFrontOffsetZ(), this.func_82500_b(), this.func_82498_a());

        par1IBlockSource.getWorld().spawnEntityInWorld(entityfireworkrocket);
        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }

    /**
     * Play the dispense sound from the specified block.
     */
    protected void playDispenseSound(IBlockSource par1IBlockSource)
    {
        par1IBlockSource.getWorld().playSoundEffect( par1IBlockSource.getX(), par1IBlockSource.getY(), par1IBlockSource.getZ(), "fireworks.blast", 1.0F, 0.25F);
    }
    protected float func_82498_a()
    {
        return 4.0F;
    }

    protected float func_82500_b()
    {
        return 5.1F;
    }
}
