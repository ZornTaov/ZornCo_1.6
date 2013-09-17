package zornco.tank.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.WatchableObject;
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

public class TankEntity extends Entity {
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

	public TankEntity(World par1World)
	{
		super(par1World);
		this.field_70279_a = true;
		this.speedMultiplier = 0.07D;
		this.preventEntitySpawning = true;
		setSize(2.5F, 3F);
		this.yOffset = 0.3F;
		this.stepHeight = 1F;
	}

	public TankEntity(World par1World, double par2, double par4, double par6)
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
		this.dataWatcher.addObject(6, Float.valueOf(1.0F));
		this.dataWatcher.addObject(17, new Integer(0));
		this.dataWatcher.addObject(18, new Integer(1));
		this.dataWatcher.addObject(19, new Float(0.0F));
		this.dataWatcher.addObject(25, new Float(0.0F));
		this.dataWatcher.addObject(26, new Integer(0));
		this.dataWatcher.addObject(27, new Integer(0));
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
		if (this.field_70279_a)
		{
			this.tankPosRotationIncrements = par9 + 5;
		}
		else
		{
			double d3 = par1 - this.posX;
			double d4 = par3 - this.posY;
			double d5 = par5 - this.posZ;
			double d6 = d3 * d3 + d4 * d4 + d5 * d5;

			if (d6 <= 1.0D)
			{
				return;
			}

			this.tankPosRotationIncrements = 3;
		}

		this.tankX = par1;
		this.tankY = par3;
		this.tankZ = par5;
		this.tankYaw = par7;
		this.tankPitch = par8;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
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

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		//this.setDead();

		if (this.getTimeSinceHit() > 0)
		{
			this.setTimeSinceHit(this.getTimeSinceHit() - 1);
		}

		if (this.getDamageTaken() > 0.0F)
		{
			this.setDamageTaken(this.getDamageTaken() - 1.0F);
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		byte b0 = 5;
		double d0 = 0.0D;

		for (int i = 0; i < b0; ++i)
		{
			double d1 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (i + 0) / b0 - 0.125D;
			double d2 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (i + 1) / b0 - 0.125D;
			AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, d1, this.boundingBox.minZ, this.boundingBox.maxX, d2, this.boundingBox.maxZ);

			if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water))
			{
				d0 += 1.0D / b0;
			}
		}

		double d3 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		double d4;
		double d5;

		if (d3 > 0.26249999999999996D && !this.isDead)
		{
			d4 = Math.cos(this.rotationYaw * Math.PI / 180.0D);
			d5 = Math.sin(this.rotationYaw * Math.PI / 180.0D);


			int j1 = MathHelper.floor_double(this.posX);
			int i1 = MathHelper.floor_double(this.posY - 0.20000000298023224D - this.yOffset);
			int k1 = MathHelper.floor_double(this.posZ);
			int l = this.worldObj.getBlockId(j1, i1, k1);
			for (int j = 0; j < 1.0D + d3 * 60.0D; ++j)
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

		double d10;
		double d11;

		if (this.worldObj.isRemote && this.field_70279_a)
		{
			if (this.tankPosRotationIncrements > 0)
			{
				d4 = this.posX + (this.tankX - this.posX) / this.tankPosRotationIncrements;
				d5 = this.posY + (this.tankY - this.posY) / this.tankPosRotationIncrements;
				d11 = this.posZ + (this.tankZ - this.posZ) / this.tankPosRotationIncrements;
				d10 = MathHelper.wrapAngleTo180_double(this.tankYaw - this.rotationYaw);
				this.rotationYaw = (float)(this.rotationYaw + d10 / this.tankPosRotationIncrements);
				this.rotationPitch = (float)(this.rotationPitch + (this.tankPitch - this.rotationPitch) / this.tankPosRotationIncrements);
				--this.tankPosRotationIncrements;
				this.setPosition(d4, d5, d11);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			}
			else
			{
				d4 = this.posX + this.motionX;
				d5 = this.posY + this.motionY;
				d11 = this.posZ + this.motionZ;
				this.setPosition(d4, d5, d11);

				/*if (!this.onGround)
				{
					this.motionX *= 0.5D;
					this.motionY *= 0.5D;
					this.motionZ *= 0.5D;
				}*/


				this.motionX *= 0.91D;
				this.motionY *= 0.99D;
				this.motionZ *= 0.91D;
			}
		}
		else
		{
			if (d0 < 1.0D)
			{
				d4 = d0 * 2.0D - 1.0D;
				this.motionY += 0.03999999910593033D * d4;
			}
			else
			{
				if (this.motionY < 0.0D)
				{
					this.motionY /= 2.0D;
				}

				this.motionY += 0.007000000216066837D;
			}

			if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer)
			{
				if(getShootTimer() == 30)
				{
					Tank.logger.info(riddenByEntity.rotationYaw+" "+this.rotationYaw);

					Vec3 tankPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
					Vec3 tankLook = this.getLookVec();
					Vec3 riderLook = getPlayerLook();
					tankLook.rotateAroundY(-90 * (float)Math.PI / 180.0F);

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
					TankBulletEntity arrow = new TankBulletEntity(this.worldObj, getBulletType());
					arrow.setPosition(turretOrigin.xCoord, turretOrigin.yCoord, turretOrigin.zCoord);
					arrow.setThrowableHeading(riderLook.xCoord, riderLook.yCoord, riderLook.zCoord, 5F, 0);
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

				double f3 = riderStrafing * riderStrafing + riderForward * riderForward;
				float friction = 0.546F;
				float friction2 = 0.16277136F/ (friction * friction * friction);
				float speed = 0.1F * friction2;
				if (f3 >= 1.0E-4F)
				{
					f3 = MathHelper.sqrt_double(f3);

					if (f3 < 1.0F)
					{
						f3 = 1.0F;
					}

					f3 = speed / f3;
					riderStrafing *= f3;
					riderForward *= f3;
					float f4 = MathHelper.sin(this.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F);
					float f5 = MathHelper.cos(this.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F);
					this.motionX += riderStrafing * f5 - riderForward * f4;
					this.motionZ += riderForward * f5 + riderStrafing * f4;
				}
				/*d4 = (double)((EntityLivingBase)this.riddenByEntity).moveForward;

				if (d4 > 0.0D)
				{
					d5 = -Math.sin((double)(this.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F));
					d11 = Math.cos((double)(this.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F));
					this.motionX += d5 * this.speedMultiplier * 0.05000000074505806D;
					this.motionZ += d11 * this.speedMultiplier * 0.05000000074505806D;
				}*/
			}

			d4 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			if (d4 > 0.35D)
			{
				d5 = 0.35D / d4;
				this.motionX *= d5;
				this.motionZ *= d5;
				d4 = 0.35D;
			}

			if (d4 > d3 && this.speedMultiplier < 0.35D)
			{
				this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;

				if (this.speedMultiplier > 0.35D)
				{
					this.speedMultiplier = 0.35D;
				}
			}
			else
			{
				this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;

				if (this.speedMultiplier < 0.07D)
				{
					this.speedMultiplier = 0.07D;
				}
			}

			setSpeed(getSpeed() - (float)d4);
			/*if (!this.onGround)
			{
				this.motionX *= 0.5D;
				this.motionY *= 0.5D;
				this.motionZ *= 0.5D;
			}*/

			this.moveEntity(this.motionX, this.motionY, this.motionZ);

			if (this.isCollidedHorizontally && onGround)
			{
				//this.motionY = 0.42D;
				this.posY += stepHeight;
				if (!this.worldObj.isRemote && !this.isDead)
				{
					//this.setDead();
					/*int k;

					for (k = 0; k < 3; ++k)
					{
						this.dropItemWithOffset(Block.planks.blockID, 1, 0.0F);
					}

					for (k = 0; k < 2; ++k)
					{
						this.dropItemWithOffset(Item.stick.itemID, 1, 0.0F);
					}*/
				}
			}
			else
			{
				this.motionX *= 0.91D;
				this.motionY *= 0.99D;
				this.motionZ *= 0.91D;
			}

			this.rotationPitch = 0.0F;
			d5 = this.rotationYaw;
			d11 = this.prevPosX - this.posX;
			d10 = this.prevPosZ - this.posZ;

			if (d11 * d11 + d10 * d10 > 0.001D)
			{
				d5 = ((float)(Math.atan2(d10, d11) * 180.0D / Math.PI));
			}

			double d12 = MathHelper.wrapAngleTo180_double(d5 - this.rotationYaw);

			if (d12 > 5.0D)
			{
				d12 = 5.0D;
			}

			if (d12 < -5.0D)
			{
				d12 = -5.0D;
			}

			this.rotationYaw = (float)(this.rotationYaw + d12);
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

						if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof TankEntity)
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
						//Tank.logger.info(riddenByEntity.rotationPitch +"");
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
			yaw = MathHelper.clamp_float(this.riddenByEntity.rotationYaw, this.rotationYaw + 45, this.rotationYaw + 135);
		}
		Tank.logger.info(this.rotationYaw + " " + this.rotationPitch + " " + this.riddenByEntity.rotationYaw + " " + this.riddenByEntity.rotationPitch + " " + yaw + " " + pitch);
		f1 = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
		f2 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
		f3 = -MathHelper.cos(-pitch * 0.017453292F);
		f4 = MathHelper.sin(-pitch * 0.017453292F);
		return this.worldObj.getWorldVec3Pool().getVecFromPool(f2 * f3, f4, f1 * f3);
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
}
