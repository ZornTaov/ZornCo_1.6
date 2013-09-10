package zornco.tank.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import zornco.tank.Tank;
import zornco.tank.entity.TankEntity;

 
public class TankItem extends Item
{

    private Icon tankIcon;
	private String tankName = "tank";

	public TankItem(int i)
    {
        super(i);
        this.maxStackSize = 16;
        this.setCreativeTab(Tank.tanksTab);
    }

    @Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        float f = 1.0F;
        float f1 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        float f2 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        double d0 = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * f;
        double d1 = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * f + 1.62D - entityplayer.yOffset;
        double d2 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * f;
        Vec3 vec3 = world.getWorldVec3Pool().getVecFromPool(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
        MovingObjectPosition movingobjectposition = world.clip(vec3, vec31, true);

        if (movingobjectposition == null)
        {
            return itemstack;
        }
        else
        {
            Vec3 vec32 = entityplayer.getLook(f);
            boolean flag = false;
            float f9 = 1.0F;
            List list = world.getEntitiesWithinAABBExcludingEntity(entityplayer, entityplayer.boundingBox.addCoord(vec32.xCoord * d3, vec32.yCoord * d3, vec32.zCoord * d3).expand(f9, f9, f9));
            int i;

            for (i = 0; i < list.size(); ++i)
            {
                Entity entity = (Entity)list.get(i);

                if (entity.canBeCollidedWith())
                {
                    float f10 = entity.getCollisionBorderSize();
                    AxisAlignedBB axisalignedbb = entity.boundingBox.expand(f10, f10, f10);

                    if (axisalignedbb.isVecInside(vec3))
                    {
                        flag = true;
                    }
                }
            }

            if (flag)
            {
                return itemstack;
            }
            else
            {
                if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
                {
                    i = movingobjectposition.blockX;
                    int j = movingobjectposition.blockY;
                    int k = movingobjectposition.blockZ;

                    if (world.getBlockId(i, j, k) == Block.snow.blockID)
                    {
                        --j;
                    }

                    TankEntity entitytank = new TankEntity(world, i + 0.5F, j + 1.0F, k + 0.5F);
                    entitytank.rotationYaw = ((MathHelper.floor_double(entityplayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) - 1) * 90;

                    if (!world.getCollidingBoundingBoxes(entitytank, entitytank.boundingBox.expand(-0.1D, -0.1D, -0.1D)).isEmpty())
                    {
                        return itemstack;
                    }

                    if (!world.isRemote)
                    {
                        world.spawnEntityInWorld(entitytank);
                    }

                    if (!entityplayer.capabilities.isCreativeMode)
                    {
                        --itemstack.stackSize;
                    }
                }

                return itemstack;
            }
        }
    }
    @Override
	@SideOnly(Side.CLIENT)

	/**
	 * Gets an icon index based on an item's damage value
	 */
	public Icon getIconFromDamage(int par1)
	{
		return this.tankIcon;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
			this.tankIcon = par1IconRegister.registerIcon("tank:"+tankName );
		
	}
}
