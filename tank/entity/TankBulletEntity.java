package zornco.tank.entity;

import java.util.List;

import zornco.tank.Tank;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TankBulletEntity extends Entity implements IProjectile
{

	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private int inTile;
	private int inData;
	private boolean inGround;

	/** 1 if the player can pick up the tankBullet */
	public int canBePickedUp;

	/** Seems to be some sort of timer for animating an tankBullet. */
	public int tankBulletShake;

	/** The owner of this tankBullet. */
	public Entity shootingEntity;
	private int ticksInGround;
	private int ticksInAir;
	private double damage = 2.0D;

	/** The amount of knockback an tankBullet applies when it hits a mob. */
	private int knockbackStrength;

	private float[] explosionRadius = {2, 1, 4, 3};
	private boolean[] fire = {false, false, true, true};

	public TankBulletEntity(World par1World, int type)
	{
		this(par1World);
		setBulletType( type);
	}
	public TankBulletEntity(World par1World)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
	}

	public TankBulletEntity(World par1World, double par2, double par4, double par6)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
		this.setPosition(par2, par4, par6);
		this.yOffset = 0.0F;
	}

	public TankBulletEntity(World par1World, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase, float par4, float par5)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = par2EntityLivingBase;

		if (par2EntityLivingBase instanceof EntityPlayer)
		{
			this.canBePickedUp = 1;
		}

		this.posY = par2EntityLivingBase.posY + par2EntityLivingBase.getEyeHeight() - 0.10000000149011612D;
		double d0 = par3EntityLivingBase.posX - par2EntityLivingBase.posX;
		double d1 = par3EntityLivingBase.boundingBox.minY + par3EntityLivingBase.height / 3.0F - this.posY;
		double d2 = par3EntityLivingBase.posZ - par2EntityLivingBase.posZ;
		double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);

		if (d3 >= 1.0E-7D)
		{
			float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
			float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			double d4 = d0 / d3;
			double d5 = d2 / d3;
			this.setLocationAndAngles(par2EntityLivingBase.posX + d4, this.posY, par2EntityLivingBase.posZ + d5, f2, f3);
			this.yOffset = 0.0F;
			float f4 = (float)d3 * 0.2F;
			this.setThrowableHeading(d0, d1 + f4, d2, par4, par5);
		}
	}

	public TankBulletEntity(World par1World, EntityLivingBase par2EntityLivingBase, float par3)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = par2EntityLivingBase;

		if (par2EntityLivingBase instanceof EntityPlayer)
		{
			this.canBePickedUp = 1;
		}

		this.setSize(0.5F, 0.5F);
		this.setLocationAndAngles(par2EntityLivingBase.posX, par2EntityLivingBase.posY + par2EntityLivingBase.getEyeHeight(), par2EntityLivingBase.posZ, par2EntityLivingBase.rotationYaw, par2EntityLivingBase.rotationPitch);
		this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI);
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI);
		this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, par3 * 1.5F, 1.0F);
	}

	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
		this.dataWatcher.addObject(26, Integer.valueOf(0));
	}

	/**
	 * Similar to setTankBulletHeading, it's point the throwable entity to a x, y, z direction.
	 */
	 @Override
	public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
		par1 /= f2;
		par3 /= f2;
		par5 /= f2;
		par1 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
		par3 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
		par5 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;
		float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
		this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, f3) * 180.0D / Math.PI);
		this.ticksInGround = 0;
	}

	 @Override
	@SideOnly(Side.CLIENT)

	 /**
	  * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
	  * posY, posZ, yaw, pitch
	  */
	 public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	 {
		 this.setPosition(par1, par3, par5);
		 this.setRotation(par7, par8);
	 }

	 @Override
	@SideOnly(Side.CLIENT)

	 /**
	  * Sets the velocity to the args. Args: x, y, z
	  */
	 public void setVelocity(double par1, double par3, double par5)
	 {
		 this.motionX = par1;
		 this.motionY = par3;
		 this.motionZ = par5;

		 if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		 {
			 float f = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			 this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
			 this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, f) * 180.0D / Math.PI);
			 this.prevRotationPitch = this.rotationPitch;
			 this.prevRotationYaw = this.rotationYaw;
			 this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			 this.ticksInGround = 0;
		 }
	 }

	 /**
	  * Called to update the entity's position/logic.
	  */
	 @Override
	public void onUpdate()
	 {
		 super.onUpdate();
		 //this.setDead();
		 if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		 {
			 float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			 this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			 this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, f) * 180.0D / Math.PI);
		 }

		 int i = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);

		 if (i > 0)
		 {
			 Block.blocksList[i].setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
			 AxisAlignedBB axisalignedbb = Block.blocksList[i].getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

			 if (axisalignedbb != null && axisalignedbb.isVecInside(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ)))
			 {
				 this.inGround = true;
			 }
		 }

		 if (this.tankBulletShake > 0)
		 {
			 --this.tankBulletShake;
		 }

		 if (this.inGround)
		 {
			 worldObj.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
			 if(!worldObj.isRemote)
			 {
				 worldObj.newExplosion(this, posX, posY, posZ, explosionRadius[getBulletType()], fire[getBulletType()], true);
				 setDead();
			 }
			 int j = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
			 int k = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);

			 if (j == this.inTile && k == this.inData)
			 {
				 ++this.ticksInGround;

				 if (this.ticksInGround == 1200)
				 {
					 this.setDead();
				 }
			 }
			 else
			 {
				 this.inGround = false;
				 this.motionX *= this.rand.nextFloat() * 0.2F;
				 this.motionY *= this.rand.nextFloat() * 0.2F;
				 this.motionZ *= this.rand.nextFloat() * 0.2F;
				 this.ticksInGround = 0;
				 this.ticksInAir = 0;
			 }
		 }
		 else
		 {
			 ++this.ticksInAir;
			 Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			 Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			 MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks_do_do(vec3, vec31, false, true);
			 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

			 if (movingobjectposition != null)
			 {
				 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			 }

			 Entity entity = null;
			 List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			 double d0 = 0.0D;
			 int l;
			 float f1;

			 for (l = 0; l < list.size(); ++l)
			 {
				 Entity entity1 = (Entity)list.get(l);

				 if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5))
				 {
					 f1 = 0.3F;
					 AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f1, f1, f1);
					 MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3, vec31);

					 if (movingobjectposition1 != null)
					 {
						 double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

						 if (d1 < d0 || d0 == 0.0D)
						 {
							 entity = entity1;
							 d0 = d1;
						 }
					 }
				 }
			 }

			 if (entity != null)
			 {
				 movingobjectposition = new MovingObjectPosition(entity);
			 }

			 if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
			 {
				 EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;

				 if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))
				 {
					 movingobjectposition = null;
				 }
			 }

			 float f2;
			 float f3;

			 if (movingobjectposition != null)
			 {
				 if (movingobjectposition.entityHit != null)
				 {
					 f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					 int i1 = MathHelper.ceiling_double_int(f2 * this.damage);

					 if (this.getIsCritical())
					 {
						 i1 += this.rand.nextInt(i1 / 2 + 2);
					 }

					 DamageSource damagesource = null;

					 if (this.shootingEntity == null)
					 {
						 damagesource = DamageSource.causeThrownDamage(this, this);
					 }
					 else
					 {
						 damagesource = DamageSource.causeThrownDamage(this, this.shootingEntity);
					 }

					 if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman))
					 {
						 movingobjectposition.entityHit.setFire(5);
					 }

					 if (movingobjectposition.entityHit.attackEntityFrom(damagesource, i1))
					 {
						 worldObj.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
						 if(!worldObj.isRemote)
						 {
							 worldObj.newExplosion(this, posX, posY, posZ, explosionRadius[getBulletType()], fire[getBulletType()], true);
							 setDead();
						 }
					 }
					 else
					 {
						 this.motionX *= -0.10000000149011612D;
						 this.motionY *= -0.10000000149011612D;
						 this.motionZ *= -0.10000000149011612D;
						 this.rotationYaw += 180.0F;
						 this.prevRotationYaw += 180.0F;
						 this.ticksInAir = 0;
					 }
				 }
				 else
				 {
					 this.xTile = movingobjectposition.blockX;
					 this.yTile = movingobjectposition.blockY;
					 this.zTile = movingobjectposition.blockZ;
					 this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
					 this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
					 this.motionX = ((float)(movingobjectposition.hitVec.xCoord - this.posX));
					 this.motionY = ((float)(movingobjectposition.hitVec.yCoord - this.posY));
					 this.motionZ = ((float)(movingobjectposition.hitVec.zCoord - this.posZ));
					 f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					 this.posX -= this.motionX / f2 * 0.05000000074505806D;
					 this.posY -= this.motionY / f2 * 0.05000000074505806D;
					 this.posZ -= this.motionZ / f2 * 0.05000000074505806D;
					 this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
					 this.inGround = true;
					 this.tankBulletShake = 7;
					 this.setIsCritical(false);

					 if (this.inTile != 0)
					 {
						 Block.blocksList[this.inTile].onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
					 }
				 }
			 }

			 if (this.getIsCritical())
			 {
				 for (l = 0; l < 4; ++l)
				 {
					 this.worldObj.spawnParticle("crit", this.posX + this.motionX * l / 4.0D, this.posY + this.motionY * l / 4.0D, this.posZ + this.motionZ * l / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
				 }
			 }

			 this.posX += this.motionX;
			 this.posY += this.motionY;
			 this.posZ += this.motionZ;
			 f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			 this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

			 for (this.rotationPitch = (float)(Math.atan2(this.motionY, f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
			 {
				 ;
			 }

			 while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
			 {
				 this.prevRotationPitch += 360.0F;
			 }

			 while (this.rotationYaw - this.prevRotationYaw < -180.0F)
			 {
				 this.prevRotationYaw -= 360.0F;
			 }

			 while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
			 {
				 this.prevRotationYaw += 360.0F;
			 }

			 this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			 this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			 float f4 = 0.99F;
			 f1 = 0.05F;

			 if (this.isInWater())
			 {
				 for (int j1 = 0; j1 < 4; ++j1)
				 {
					 f3 = 0.25F;
					 this.worldObj.spawnParticle("bubble", this.posX - this.motionX * f3, this.posY - this.motionY * f3, this.posZ - this.motionZ * f3, this.motionX, this.motionY, this.motionZ);
				 }

				 f4 = 0.8F;
			 }

			 this.motionX *= f4;
			 this.motionY *= f4;
			 this.motionZ *= f4;
			 this.motionY -= f1;
			 this.setPosition(this.posX, this.posY, this.posZ);
			 this.doBlockCollisions();
		 }
	 }

	 /**
	  * (abstract) Protected helper method to write subclass entity data to NBT.
	  */
	 @Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	 {
		 par1NBTTagCompound.setShort("xTile", (short)this.xTile);
		 par1NBTTagCompound.setShort("yTile", (short)this.yTile);
		 par1NBTTagCompound.setShort("zTile", (short)this.zTile);
		 par1NBTTagCompound.setByte("inTile", (byte)this.inTile);
		 par1NBTTagCompound.setByte("inData", (byte)this.inData);
		 par1NBTTagCompound.setByte("shake", (byte)this.tankBulletShake);
		 par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
		 par1NBTTagCompound.setByte("pickup", (byte)this.canBePickedUp);
		 par1NBTTagCompound.setDouble("damage", this.damage);
	 }

	 /**
	  * (abstract) Protected helper method to read subclass entity data from NBT.
	  */
	 @Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	 {
		 this.xTile = par1NBTTagCompound.getShort("xTile");
		 this.yTile = par1NBTTagCompound.getShort("yTile");
		 this.zTile = par1NBTTagCompound.getShort("zTile");
		 this.inTile = par1NBTTagCompound.getByte("inTile") & 255;
		 this.inData = par1NBTTagCompound.getByte("inData") & 255;
		 this.tankBulletShake = par1NBTTagCompound.getByte("shake") & 255;
		 this.inGround = par1NBTTagCompound.getByte("inGround") == 1;

		 if (par1NBTTagCompound.hasKey("damage"))
		 {
			 this.damage = par1NBTTagCompound.getDouble("damage");
		 }

		 if (par1NBTTagCompound.hasKey("pickup"))
		 {
			 this.canBePickedUp = par1NBTTagCompound.getByte("pickup");
		 }
		 else if (par1NBTTagCompound.hasKey("player"))
		 {
			 this.canBePickedUp = par1NBTTagCompound.getBoolean("player") ? 1 : 0;
		 }
	 }

	 /**
	  * Called by a player entity when they collide with an entity
	  */
	 @Override
	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
	 {
		 if (!this.worldObj.isRemote && this.inGround && this.tankBulletShake <= 0)
		 {
			 boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && par1EntityPlayer.capabilities.isCreativeMode;

			 if (this.canBePickedUp == 1 && !par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Tank.tankBullet[getBulletType()], 1)))
			 {
				 flag = false;
			 }

			 if (flag)
			 {
				 this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				 par1EntityPlayer.onItemPickup(this, 1);
				 this.setDead();
			 }
		 }
	 }

	 /**
	  * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	  * prevent them from trampling crops
	  */
	 @Override
	protected boolean canTriggerWalking()
	 {
		 return false;
	 }

	 @Override
	@SideOnly(Side.CLIENT)
	 public float getShadowSize()
	 {
		 return 0.0F;
	 }

	 public void setDamage(double par1)
	 {
		 this.damage = par1;
	 }

	 public double getDamage()
	 {
		 return this.damage;
	 }

	 /**
	  * Sets the amount of knockback the tankBullet applies when it hits a mob.
	  */
	 public void setKnockbackStrength(int par1)
	 {
		 this.knockbackStrength = par1;
	 }

	 /**
	  * If returns false, the item will not inflict any damage against entities.
	  */
	 @Override
	public boolean canAttackWithItem()
	 {
		 return false;
	 }

	 /**
	  * Whether the tankBullet has a stream of critical hit particles flying behind it.
	  */
	 public void setIsCritical(boolean par1)
	 {
		 byte b0 = this.dataWatcher.getWatchableObjectByte(16);

		 if (par1)
		 {
			 this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 1)));
		 }
		 else
		 {
			 this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & -2)));
		 }
	 }

	 /**
	  * Whether the tankBullet has a stream of critical hit particles flying behind it.
	  */
	 public boolean getIsCritical()
	 {
		 byte b0 = this.dataWatcher.getWatchableObjectByte(16);
		 return (b0 & 1) != 0;
	 }
	 /**
	  * Sets the next Bullet Type to shoot.
	  */
	 public void setBulletType(int par1)
	 {
		 this.dataWatcher.updateObject(26, Integer.valueOf(par1));
	 }

	 /**
	  * Gets the next Bullet Type to shoot.
	  */
	 public int getBulletType()
	 {
		 return this.dataWatcher.getWatchableObjectInt(26);
	 }

	 /*
	public int type;
	private int xTile;
	private int yTile;
	private int zTile;
	private int inTile;
	private int inData;
	private boolean inGround;
	public int TankBulletShake;
	public EntityLiving entityBullet;
	private int ticksInGround;
	private int ticksInAir;

	public TankBulletEntity(World world, int i)
	{
		this(world);
		type = i;
	}
	public TankBulletEntity(World world)
	{
		super(world);
		xTile = -1;
		yTile = -1;
		zTile = -1;
		inTile = 0;
		inData = 0;
		inGround = false;
		TankBulletShake = 0;
		ticksInAir = 0;
		setSize(0.5F, 0.5F);
	}

	public TankBulletEntity(World world, double d, double d1, double d2)
	{ 
		super(world);
		xTile = -1;
		yTile = -1;
		zTile = -1;
		inTile = 0;
		inData = 0;
		inGround = false;
		TankBulletShake = 0;
		ticksInAir = 0;
		setSize(0.5F, 0.5F);
		setPosition(d, d1, d2);
		yOffset = 0.0F;
	}

	public TankBulletEntity(World world, EntityLiving entityliving)
	{
		super(world);
		xTile = -1;
		yTile = -1;
		zTile = -1;
		inTile = 0;
		inData = 0;
		inGround = false;
		TankBulletShake = 0;
		ticksInAir = 0;
		entityBullet = entityliving;
		setSize(0.5F, 0.5F);
		setLocationAndAngles(entityliving.posX, entityliving.posY + (double)entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
		posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
		motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
		setTankBulletHeading(motionX, motionY, motionZ, 1.5F, 1.0F);
	}

	protected void entityInit()
	{
	}

	public void setTankBulletHeading(double d, double d1, double d2, float f, 
			float f1)
	{
		float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
		d /= f2;
		d1 /= f2;
		d2 /= f2;
		d += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
		d1 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
		d2 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
		d *= f;
		d1 *= f;
		d2 *= f;
		motionX = d;
		motionY = d1;
		motionZ = d2;
		float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
		prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
		prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
		ticksInGround = 0;
	}

	public void setVelocity(double d, double d1, double d2)
	{
		motionX = d;
		motionY = d1;
		motionZ = d2;
		if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(d * d + d2 * d2);
			prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
			prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f) * 180D) / 3.1415927410125732D);
		}
	}

	public void onUpdate()
	{
		super.onUpdate();
		if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			prevRotationYaw = rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
			prevRotationPitch = rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D);
		}
		if(TankBulletShake > 0)
		{
			TankBulletShake--;
		}
		if(inGround)
		{
			if(type == 0)
			{
				worldObj.newExplosion(this, posX, posY, posZ, 2F, false, true);
				setDead();
			}
			if(type == 1)
			{
				worldObj.newExplosion(this, posX, posY, posZ, 1F, false, true);
				setDead();
			}
			if(type == 2)
			{
				worldObj.newExplosion(this, posX, posY, posZ, 4F, true, true);
				setDead();
			}
			if(type == 3)
			{
				worldObj.newExplosion(this, posX, posY, posZ, 3F, true, true);
				setDead();
			}
			int i = worldObj.getBlockId(xTile, yTile, zTile);
			if(i != inTile)
			{
				inGround = false;
				motionX *= rand.nextFloat() * 0.2F;
				motionY *= rand.nextFloat() * 0.2F;
				motionZ *= rand.nextFloat() * 0.2F;
				ticksInGround = 0;
				ticksInAir = 0;
			} else
			{
				ticksInGround++;
				if(ticksInGround == 120)
				{
					setDead();
				}
				return;
			}
		} else
		{
			ticksInAir++;
		}
		Vec3 vec3d = Vec3.createVectorHelper(posX, posY, posZ);
		Vec3 vec3d1 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
		MovingObjectPosition movingobjectposition = worldObj.clip(vec3d, vec3d1);
		vec3d = Vec3.createVectorHelper(posX, posY, posZ);
		vec3d1 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
		if(movingobjectposition != null)
		{
			vec3d1 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
		}
		Entity entity = null;
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
		double d = 0.0D;
		for(int j = 0; j < list.size(); j++)
		{
			Entity entity1 = (Entity)list.get(j);
			if(!entity1.canBeCollidedWith() || entity1 == entityBullet && ticksInAir < 5)
			{
				continue;
			}
			float f4 = 0.3F;
			AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f4, f4, f4);
			MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);
			if(movingobjectposition1 == null)
			{
				continue;
			}
			double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);
			if(d1 < d || d == 0.0D)
			{
				entity = entity1;
				d = d1;
			}
		}

		if(entity != null)
		{
			movingobjectposition = new MovingObjectPosition(entity);
		}
		if(movingobjectposition != null)
		{
			if(movingobjectposition.entityHit != null)
			{
				if(movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, entityBullet), 4))
				{
					worldObj.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
					if(type == 0)
					{
						worldObj.newExplosion(this, posX, posY, posZ, 2F, false, true);
						setDead();
					}
					if(type == 1)
					{
						worldObj.newExplosion(this, posX, posY, posZ, 1F, false, true);
						setDead();
					}
					if(type == 2)
					{
						worldObj.newExplosion(this, posX, posY, posZ, 4F, false, true);
						setDead();
					}
					if(type == 3)
					{
						worldObj.newExplosion(this, posX, posY, posZ, 3F, false, true);
						setDead();
					}
					if(type == 20)
					{
						worldObj.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
						setDead();
					}

				} else
				{
					motionX *= -0.10000000149011612D;
					motionY *= -0.10000000149011612D;
					motionZ *= -0.10000000149011612D;
					rotationYaw += 180F;
					prevRotationYaw += 180F;
					ticksInAir = 0;
				}
			} else
			{
				xTile = movingobjectposition.blockX;
				yTile = movingobjectposition.blockY;
				zTile = movingobjectposition.blockZ;
				inTile = worldObj.getBlockId(xTile, yTile, zTile);
				inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
				motionX = (float)(movingobjectposition.hitVec.xCoord - posX);
				motionY = (float)(movingobjectposition.hitVec.yCoord - posY);
				motionZ = (float)(movingobjectposition.hitVec.zCoord - posZ);
				float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
				posX -= (motionX / (double)f1) * 0.05000000074505806D;
				posY -= (motionY / (double)f1) * 0.05000000074505806D;
				posZ -= (motionZ / (double)f1) * 0.05000000074505806D;
				worldObj.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
				inGround = true;
				TankBulletShake = 7;
			}
		}
		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		float f2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
		for(rotationPitch = (float)((Math.atan2(motionY, f2) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
		for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
		for(; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
		for(; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
		rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
		float f3 = 0.99F;
		float f5 = 0.05F;
		if(isInWater())
		{
			for(int k = 0; k < 4; k++)
			{
				float f6 = 0.25F;
				worldObj.spawnParticle("bubble", posX - motionX * (double)f6, posY - motionY * (double)f6, posZ - motionZ * (double)f6, motionX, motionY, motionZ);
			}

			f3 = 0.8F;
		}
		motionX *= f3;
		motionY *= f3;
		motionZ *= f3;
		motionY -= f5;
		setPosition(posX, posY, posZ);
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setShort("xTile", (short)xTile);
		nbttagcompound.setShort("yTile", (short)yTile);
		nbttagcompound.setShort("zTile", (short)zTile);
		nbttagcompound.setByte("inTile", (byte)inTile);
		nbttagcompound.setByte("inData", (byte)inData);
		nbttagcompound.setByte("shake", (byte)TankBulletShake);
		nbttagcompound.setByte("inGround", (byte)(inGround ? 1 : 0));
		nbttagcompound.setShort("type", (short)type);
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		xTile = nbttagcompound.getShort("xTile");
		yTile = nbttagcompound.getShort("yTile");
		zTile = nbttagcompound.getShort("zTile");
		inTile = nbttagcompound.getByte("inTile") & 0xff;
		inData = nbttagcompound.getByte("inData") & 0xff;
		TankBulletShake = nbttagcompound.getByte("shake") & 0xff;
		inGround = nbttagcompound.getByte("inGround") == 1;
		type = nbttagcompound.getShort("type");
	}

	public void onCollideWithPlayer(EntityPlayer entityplayer)
	{
		if(worldObj.isRemote)
		{
			return;
		}
		if(inGround && entityBullet == entityplayer && TankBulletShake <= 0 && entityplayer.inventory.addItemStackToInventory(new ItemStack(mod_tank.tankBullet[type], 1)))
        {
            worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.onItemPickup(this, 1);
            setDead();
        }
	}

	public float getShadowSize()
	{
		return 0.0F;
	}
	  */}
