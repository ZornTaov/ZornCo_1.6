package zornco.tank.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.WatchableObject;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import zornco.tank.Tank;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityTankBase extends Entity {
	private boolean field_70279_a;
	private double speedMultiplier;
	private int tankPosRotationIncrements;
	private double tankX;
	private double tankY;
	private double tankZ;
	private double tankYaw;
	private double tankPitch;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;
	public double tankSpeed;
	public double wheelRotation;
	private int shootimer = 1;
	private int bulletType = -1;

	public EntityTankBase(World par1World)
	{
		super(par1World);
		this.field_70279_a = true;
		this.speedMultiplier = 0.07D;
		this.preventEntitySpawning = true;
		setSize(2.5F, 2F);
		this.yOffset = 0.0F;
		this.stepHeight = 1F;
	}

	public EntityTankBase(World par1World, double par2, double par4, double par6)
	{
		this(par1World);
		this.setPosition(par2, par4 + this.yOffset, par6);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = par2;
		this.prevPosY = par4;
		this.prevPosZ = par6;
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
	protected void entityInit()
	{
		this.dataWatcher.addObject(6, Float.valueOf(1.0F));//Health
		this.dataWatcher.addObject(17, new Integer(0));
		this.dataWatcher.addObject(18, new Integer(1));
		this.dataWatcher.addObject(19, new Float(0.0F));
		this.dataWatcher.addObject(25, new Float(0.0F));
		this.dataWatcher.addObject(26, new Integer(0));
		this.dataWatcher.addObject(27, new Integer(0));
		this.dataWatcher.addObject(28, new Float(0.0F));//rotationYaw
	}

	/**
	 * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
	 * pushable on contact, like tanks or minecarts.
	 */
	@Override
	public AxisAlignedBB getCollisionBox(Entity par1Entity)
	{
		return par1Entity.boundingBox;
	}

	/**
	 * returns the bounding box for this entity
	 */
	@Override
	public AxisAlignedBB getBoundingBox()
	{
		return this.boundingBox;
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities when colliding.
	 */
	@Override
	public boolean canBePushed()
	{
		return true;
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding this one.
	 */
	@Override
	public double getMountedYOffset()
	{
		return this.height * 0.0D + 0.6D;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if (this.isEntityInvulnerable())
		{
			return false;
		}
		else if (!this.worldObj.isRemote && !this.isDead && (par1DamageSource.getEntity() != this.riddenByEntity))
		{
			this.setForwardDirection(-this.getForwardDirection());
			this.setTimeSinceHit(10);
			this.setDamageTaken(this.getDamageTaken() + par2 * 10.0F);
			this.setBeenAttacked();
			boolean flag = par1DamageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer)par1DamageSource.getEntity()).capabilities.isCreativeMode;

			if (flag || this.getDamageTaken() > 40.0F)
			{
				if (this.riddenByEntity != null && par1DamageSource.getEntity() != this.riddenByEntity)
				{
					this.riddenByEntity.mountEntity(this);
				}

				if (!flag)
				{
					this.dropItemWithOffset(Tank.tankItem.itemID, 1, 0.0F);
				}

				if (par1DamageSource.getEntity() != this.riddenByEntity)
					this.setDead();
			}

			return true;
		}
		else
		{
			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Setups the entity to do the hurt animation. Only used by packets in multiplayer.
	 */
	public void performHurtAnimation()
	{
		this.setForwardDirection(-this.getForwardDirection());
		this.setTimeSinceHit(10);
		this.setDamageTaken(this.getDamageTaken() * 11.0F);
	}

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	@Override
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
	 * posY, posZ, yaw, pitch
	 */
	public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		/*this.tankX = par1;
		this.tankY = par3;
		this.tankZ = par5;
		this.tankYaw = par7;
		this.tankPitch = par8;
		this.tankPosRotationIncrements = par9;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
		this.moveEntity(motionX, motionY, motionZ);*/
		setPosition(par1, par3, par5);
	}

	@Override
	public void setPositionAndRotation(double par1, double par3, double par5,
			float par7, float par8) {
		super.setPositionAndRotation(par1, par3, par5, par7, par8);
		//this.moveEntity(motionX, motionY, motionZ);
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	public void setVelocity(double par1, double par3, double par5)
	{
		this.velocityX = this.motionX = par1;
		this.velocityY = this.motionY = par3;
		this.velocityZ = this.motionZ = par5;
	}
	int count = 0;
	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		//this.setDead();
		if (count-- < 0)
		{
			//Tank.logger.info(this.worldObj.isRemote + " " + this.serverPosX/32.0 + " " + ((int)(this.posX*32))/32.0 + " " + this.serverPosZ/32.0 + " " + this.posZ );
			//Tank.logger.info(this.rotationYaw + " " + (riddenByEntity != null? riddenByEntity.rotationYaw : 0));
			count = 50;
		}
		if (this.getTimeSinceHit() > 0)
		{
			this.setTimeSinceHit(this.getTimeSinceHit() - 1);
		}

		if (this.getDamageTaken() > 0.0F)
		{
			this.setDamageTaken(this.getDamageTaken() - 1.0F);
		}
		if (this.tankPosRotationIncrements > 0)
		{
			double d0 = this.posX + (this.tankX - this.posX) / (double)this.tankPosRotationIncrements;
			double d1 = this.posY + (this.tankY - this.posY) / (double)this.tankPosRotationIncrements;
			double d2 = this.posZ + (this.tankZ - this.posZ) / (double)this.tankPosRotationIncrements;
			double d3 = MathHelper.wrapAngleTo180_double(this.tankYaw - (double)this.rotationYaw);
			this.rotationYaw = (float)((double)this.rotationYaw + d3 / (double)this.tankPosRotationIncrements);
			this.rotationPitch = (float)((double)this.rotationPitch + (this.tankPitch - (double)this.rotationPitch) / (double)this.tankPosRotationIncrements);
			--this.tankPosRotationIncrements;
			this.setPosition(d0, d1, d2);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}
		else if (!this.worldObj.isRemote)
		{
			this.motionX *= 0.91D;
			this.motionY *= 0.98D;
			this.motionZ *= 0.91D;
		}

		if (Math.abs(this.motionX) < 0.01D)
		{
			this.motionX = 0.0D;
		}

		if (Math.abs(this.motionY) < 0.01D)
		{
			this.motionY = 0.0D;
		}

		if (Math.abs(this.motionZ) < 0.01D)
		{
			this.motionZ = 0.0D;
		}

		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer)
		{
			if(getShootTimer() == 30)
			{
				Tank.logger.info(riddenByEntity.rotationYaw+" "+this.rotationYaw);

				Vec3 tankPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
				Vec3 tankLook = this.getLookVec();
				Vec3 riderLook = getPlayerLook();
				//tankLook.rotateAroundY(-90 * (float)Math.PI / 180.0F);

				Vec3 tankOriginLook = tankLook; 
				tankOriginLook.yCoord = 0;
				tankOriginLook.normalize();
				Vec3 turretOrigin = tankPos.addVector(tankOriginLook.xCoord * 1.4, .75, tankOriginLook.zCoord * 1.4);
				turretOrigin = turretOrigin.addVector(riderLook.xCoord*2, riderLook.yCoord*2, riderLook.zCoord*2);
				//Tank.logger.info(riderLook+" "+turretOrigin);
				//Vec3 bulletLook = tankOriginLook.addVector(0, riderLook.yCoord, 0);
				//bulletLook.rotateAroundY(this.rotationYaw-riddenByEntity.rotationYaw * (float)Math.PI / 180.0F);
				//Vec3 bulletOrigin = turretOrigin.addVector(bulletLook.xCoord , bulletLook.yCoord , bulletLook.zCoord );

				//Vec3 vec32 = tankPos.addVector(riderLook.xCoord * 3, riderLook.yCoord * 3, riderLook.zCoord * 3);
				if(riderLook.yCoord < -0.3)Tank.logger.info(riderLook.toString());
				EntityTankBullet arrow = new EntityTankBullet(this.worldObj, getBulletType());
				arrow.setPosition(turretOrigin.xCoord, turretOrigin.yCoord, turretOrigin.zCoord);
				arrow.setThrowableHeading(riderLook.xCoord, riderLook.yCoord, riderLook.zCoord, 2F, 0);
				//arrow.setThrowableHeading(0, 0, 0, 5F, 0);
				if (!worldObj.isRemote) {
					worldObj.playSoundAtEntity(this, "fireworks.blast", 1.0F, 0.25F);
					worldObj.spawnEntityInWorld(arrow);
				}
				//shootimer = 20;
			}

			double riderStrafing = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5F;
			double riderForward = ((EntityLivingBase)this.riddenByEntity).moveForward;
			if (riderForward <= 0.0F)
			{
				riderForward *= 0.25F;
			}

			double riderSpeed = riderStrafing * riderStrafing + riderForward * riderForward;
			float friction = 0.546F;
			float friction2 = 0.16277136F/ (friction * friction * friction);
			float speed = 0.1F * friction2;
			if (riderSpeed >= 1.0E-4F)
			{
				riderSpeed = MathHelper.sqrt_double(riderSpeed);

				if (riderSpeed < 1.0F)
				{
					riderSpeed = 1.0F;
				}

				riderSpeed = speed / riderSpeed;
				riderStrafing *= riderSpeed;
				riderForward *= riderSpeed;
				float f4 = MathHelper.sin(this.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F);
				float f5 = MathHelper.cos(this.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F);
				this.motionX += riderStrafing * f5 - riderForward * f4;
				this.motionZ += riderForward * f5 + riderStrafing * f4;
			}
		}

		double speed = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		double maxSpeed = 0.35;
		if (speed > maxSpeed )
		{
			double friction = maxSpeed / speed;
			this.motionX *= friction;
			this.motionZ *= friction;
			speed = maxSpeed;
		}
		
		if (this.isCollidedHorizontally)
		{
			this.motionY = 0.2D;
		}

		if (this.worldObj.isRemote && (!this.worldObj.blockExists((int)this.posX, 0, (int)this.posZ) || !this.worldObj.getChunkFromBlockCoords((int)this.posX, (int)this.posZ).isChunkLoaded))
		{
			if (this.posY > 0.0D)
			{
				this.motionY = -0.1D;
			}
			else
			{
				this.motionY = 0.0D;
			}
		}
		else
		{
			this.motionY -= 0.08D;
		}
		setSpeed(getSpeed() - (float)speed);
		if (speed > 0.26249999999999996D && !this.isDead)
		{
			double d4 = Math.cos(this.rotationYaw * Math.PI / 180.0D);
			double d5 = Math.sin(this.rotationYaw * Math.PI / 180.0D);


			int j1 = MathHelper.floor_double(this.posX);
			int i1 = MathHelper.floor_double(this.posY - 0.20000000298023224D - this.yOffset);
			int k1 = MathHelper.floor_double(this.posZ);
			int l = this.worldObj.getBlockId(j1, i1, k1);
			for (int j = 0; j < 1.0D + speed * 60.0D; ++j)
			{
				double d6 = this.rand.nextFloat() * 1.0F - 1.0F;
				double d7 = (this.rand.nextInt(2) * 2 - 1) * 1.2D;
				double d8;
				double d9;
				if (l != 0)
				{
					d8 = this.posX - d4 * d6 * 1.2D + d5 * d7;
					d9 = this.posZ - d5 * d6 * 1.2D - d4 * d7;
					this.worldObj.spawnParticle("tilecrack_" + l + "_" + this.worldObj.getBlockMetadata(j1, i1, k1), d8, this.posY - 0.8D, d9, this.motionX, this.motionY, this.motionZ);
				}
			}
		}

		this.moveEntity(this.motionX, this.motionY, this.motionZ);

		double d6 = this.prevPosX - this.posX;
		double d7 = this.prevPosZ - this.posZ;

		if (d6 * d6 + d7 * d7 > 0.001D)
		{
			this.rotationYaw = MathHelper.wrapAngleTo180_float((float)(Math.atan2(d7, d6) * 180.0D / Math.PI + 90F));
		}
		//setYaw(rotationYaw);
		this.setRotation(this.rotationYaw, this.rotationPitch);

		if (!this.worldObj.isRemote)
		{
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
			int l;

			if (list != null && !list.isEmpty())
			{
				for (l = 0; l < list.size(); ++l)
				{
					Entity entity = (Entity)list.get(l);

					if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityTankBase)
					{
						entity.applyEntityCollision(this);
					}
				}
			}

			for (l = 0; l < 4; ++l)
			{
				int i1 = MathHelper.floor_double(this.posX + (l % 2 - 0.5D) * 0.8D);
				int j1 = MathHelper.floor_double(this.posZ + (l / 2 - 0.5D) * 0.8D);

				for (int k1 = -1; k1 < 1; ++k1)
				{
					int l1 = MathHelper.floor_double(this.posY) + k1;
					int i2 = this.worldObj.getBlockId(i1, l1, j1);

					if (i2 == Block.snow.blockID)
					{
						this.worldObj.setBlockToAir(i1, l1, j1);
					}
					else if (i2 == Block.waterlily.blockID)
					{
						this.worldObj.destroyBlock(i1, l1, j1, true);
					}
					else if (i2 == Block.grass.blockID || i2 == Block.tilledField.blockID)
					{
						this.worldObj.setBlock(i1, l1, j1, Block.dirt.blockID);
					}
				}
			}

			if (this.riddenByEntity != null && this.riddenByEntity.isDead)
			{
				this.riddenByEntity = null;
			}
		}


		if (getShootTimer() > -1)
			setShootTimer(getShootTimer()-1);
	}

	@Override
	public void updateRiderPosition()
	{
		if (this.riddenByEntity != null)
		{
			double d0 = Math.cos(this.rotationYaw * Math.PI / 180.0D) * 0.0D;
			double d1 = Math.sin(this.rotationYaw * Math.PI / 180.0D) * 0.0D;
			this.riddenByEntity.setPosition(this.posX + d0, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + d1);
		}
	}
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	@Override
	public boolean interactFirst(EntityPlayer par1EntityPlayer)
	{
		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != par1EntityPlayer)
		{
			return true;
		}
		else
		{
			if (/*!this.worldObj.isRemote && */getShootTimer() == -1)
			{
				boolean wrongItem = true;
				ItemStack itemstack = par1EntityPlayer.getHeldItem();
				for (int i = 0; i < 4; i++) {
					if (itemstack != null && itemstack.itemID == Tank.tankBullet[i].itemID && riddenByEntity == par1EntityPlayer) {
						if (!par1EntityPlayer.capabilities.isCreativeMode && --itemstack.stackSize == 0) {
							par1EntityPlayer.inventory.setInventorySlotContents(
									par1EntityPlayer.inventory.currentItem, null);
						}
						//
						//if(MathHelper.wrapAngleTo180_double(riddenByEntity.rotationYaw - this.rotationYaw) > 45 && MathHelper.wrapAngleTo180_double(riddenByEntity.rotationYaw - this.rotationYaw) < 135 && MathHelper.wrapAngleTo180_double(riddenByEntity.rotationPitch - this.rotationPitch) < 25 && MathHelper.wrapAngleTo180_double(riddenByEntity.rotationPitch - this.rotationPitch) > -65 )
						//{
						setBulletType(i);
						setShootTimer(30);
						//}
						wrongItem = false;
					}
				}
				if (wrongItem) {
					par1EntityPlayer.mountEntity(this);
					if (this.ridingEntity != null && this.isDead)
					{
						if (!this.worldObj.isRemote)
						{
							this.func_70270_d(true);
						}

						if (this.ridingEntity != null)
						{
							this.ridingEntity.riddenByEntity = null;
						}

						this.ridingEntity = null;
					}
					else
					{
						this.func_70270_d(false);
					}
				}
			}

			return true;
		}
	}
	@Override
	public Vec3 getLookVec()
	{
		return this.getLook(1.0F);
	}

	/**
	 * interpolated look vector
	 */
	public Vec3 getLook(float par1)
	{
		float f1;
		float f2;
		float f3;
		float f4;

		if (par1 == 1.0F)
		{
			f1 = MathHelper.cos(-this.rotationYaw * 0.017453292F - (float)Math.PI);
			f2 = MathHelper.sin(-this.rotationYaw * 0.017453292F - (float)Math.PI);
			f3 = -MathHelper.cos(-this.rotationPitch * 0.017453292F);
			f4 = MathHelper.sin(-this.rotationPitch * 0.017453292F);
			return this.worldObj.getWorldVec3Pool().getVecFromPool(f2 * f3, f4, f1 * f3);
		}
		else
		{
			f1 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * par1;
			f2 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * par1;
			f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
			f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
			float f5 = -MathHelper.cos(-f1 * 0.017453292F);
			float f6 = MathHelper.sin(-f1 * 0.017453292F);
			return this.worldObj.getWorldVec3Pool().getVecFromPool(f4 * f5, f6, f3 * f5);
		}
	}



    /**
     * Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and
     * third parameters
     */
    public static float clamp_float(float par0, float par1, float par2)
    {
    	if(par1 > par2)
    		return par0 > par1 && par0 <= 180 || par0 < par2 && par0 > -180 ? par0 : (par0 < par1 && par0 >= 0.0F? par1 : par2);
        return par0 < par1 ? par1 : (par0 > par2 ? par2 : par0);
    }
	/**
	 * interpolated look vector
	 */
	public Vec3 getPlayerLook()
	{
		float f1;
		float f2;
		float f3;
		float f4;
		float pitch, yaw;
		if (this.riddenByEntity == null)
		{
			pitch = this.rotationPitch;
			yaw = this.rotationYaw;
		}
		else
		{
			pitch = MathHelper.clamp_float(MathHelper.wrapAngleTo180_float(this.riddenByEntity.rotationPitch), -65.0F, 25.0F);
			yaw = this.clamp_float(MathHelper.wrapAngleTo180_float(this.riddenByEntity.rotationYaw), MathHelper.wrapAngleTo180_float(this.rotationYaw - 15), MathHelper.wrapAngleTo180_float(this.rotationYaw + 15));
		}
		Tank.logger.info(riddenByEntity.rotationYaw +" "+ this.rotationYaw +" "+ yaw);
		f1 = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
		f2 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
		f3 = -MathHelper.cos(-pitch * 0.017453292F);
		f4 = MathHelper.sin(-pitch * 0.017453292F);
		return this.worldObj.getWorldVec3Pool().getVecFromPool(f2 * f3, f4, f1 * f3);
	}
	@Override
	public void applyEntityCollision(Entity par1Entity) {
		 if (!this.worldObj.isRemote)
	        {
	            if (par1Entity != this.riddenByEntity)
	            {
	                if (par1Entity instanceof EntityLivingBase && !(par1Entity instanceof EntityPlayer) && !(par1Entity instanceof EntityIronGolem)/* && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01D */&& this.riddenByEntity == null && par1Entity.ridingEntity == null)
	                {
	                    par1Entity.mountEntity(this);
	                }
	            }
	        }
		 
		super.applyEntityCollision(par1Entity);
	}

	/**
	 * Called when a player mounts an entity. e.g. mounts a pig, mounts a boat.
	 */
	public void mountEntity(Entity par1Entity)
	{
		super.mountEntity(par1Entity);
		if(!this.worldObj.isRemote)
		{
			field_70279_a = riddenByEntity != null;
		}
	}
	/**
	 * Sets the damage taken from the last hit.
	 */
	public void setDamageTaken(float par1)
	{
		this.dataWatcher.updateObject(19, Float.valueOf(par1));
	}

	/**
	 * Gets the damage taken from the last hit.
	 */
	public float getDamageTaken()
	{
		return this.dataWatcher.getWatchableObjectFloat(19);
	}

	/**
	 * Sets the time to count down from since the last time entity was hit.
	 */
	public void setTimeSinceHit(int par1)
	{
		this.dataWatcher.updateObject(17, Integer.valueOf(par1));
	}

	/**
	 * Gets the time since the last hit.
	 */
	public int getTimeSinceHit()
	{
		return this.dataWatcher.getWatchableObjectInt(17);
	}

	/**
	 * Sets the forward direction of the entity.
	 */
	public void setForwardDirection(int par1)
	{
		this.dataWatcher.updateObject(18, Integer.valueOf(par1));
	}

	/**
	 * Gets the forward direction of the entity.
	 */
	public int getForwardDirection()
	{
		return this.dataWatcher.getWatchableObjectInt(18);
	}

	@SideOnly(Side.CLIENT)
	public void func_70270_d(boolean par1)
	{
		this.field_70279_a = par1;
	}
	/**
	 * Sets the damage taken from the last hit.
	 */
	public void setSpeed(float par1)
	{
		this.dataWatcher.updateObject(25, Float.valueOf(par1));
	}

	/**
	 * Gets the damage taken from the last hit.
	 */
	public float getSpeed()
	{
		return this.dataWatcher.getWatchableObjectFloat(25);
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
	/**
	 * Sets the Shoot Timer.
	 */
	public void setShootTimer(int par1)
	{
		this.dataWatcher.updateObject(27, Integer.valueOf(par1));
	}

	/**
	 * Gets the Shoot Timer count.
	 */
	public int getShootTimer()
	{
		return this.dataWatcher.getWatchableObjectInt(27);
	}
	/**
	 * Sets the damage taken from the last hit.
	 */
	public void setYaw(float par1)
	{
		this.dataWatcher.updateObject(28, Float.valueOf(par1));
	}

	/**
	 * Gets the damage taken from the last hit.
	 */
	public float getYaw()
	{
		return this.dataWatcher.getWatchableObjectFloat(28);
	}
}
