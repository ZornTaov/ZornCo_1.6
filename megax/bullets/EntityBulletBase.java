package zornco.megax.bullets;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class EntityBulletBase extends Entity
{
	public float speed;
	/** multiplied slowdown each flying tick */
	public float slowdown;
	/** subtracted y motion each flying tick */
	public float curvature;
	/** magnitude of random error, 12 for skeletons, 1 for the player */
	public float precision;
	/** bounding box is expanded by this for determinining collision with entities */
	public float hitBox;
	/** damage in halfhearts to deal if onHitTarget returns true */
	public int dmg;
	/** itemstack to offer the player upon collision. null if no item is to be given */
	public ItemStack item;
	/** ticks until the projectile dies after being in ground */
	public int ttlInGround;

	public int xTile = -1;
	public int yTile = -1;
	public int zTile = -1;
	public int inTile = 0;
	public int inData = 0;
	public boolean inGround = false;

	/** 1 if the player can pick up the bullet */
	public int canBePickedUp = 0;

	/** Seems to be some sort of timer for animating an bullet. */
	public int bulletShake = 0;

	/** The owner of this bullet. */
	public Entity shootingEntity;
	public int ticksInGround;
	public int ticksInAir = 0;
	public double damage = 2.0D;

	/** The amount of knockback an bullet applies when it hits a mob. */
	public int knockbackStrength;

	public EntityBulletBase(World par1World)
	{
		super(par1World);
		this.setSize(0.5F, 0.5F);
	}

	public EntityBulletBase(World par1World, double xPos, double yPos, double zPos)
	{
		super(par1World);
		this.setPosition(xPos, yPos, zPos);
	}

	/**
	 * Method to shoot between one entity to another
	 * @param world
	 * @param attacker attacking entity
	 * @param target target entity
	 */
	public EntityBulletBase(World world, EntityLiving attacker, EntityLiving target)
	{
		super(world);
		this.shootingEntity = attacker;

		if (attacker instanceof EntityPlayer)
		{
			this.canBePickedUp = 1;
		}
		this.posY = attacker.posY + (double)attacker.getEyeHeight() - 0.10000000149011612D;
		double xDis = target.posX - attacker.posX;
		double yDis = target.posY + (double)target.getEyeHeight() - 0.699999988079071D - this.posY;
		double zDis = target.posZ - attacker.posZ;
		double distance = (double)MathHelper.sqrt_double(xDis * xDis + zDis * zDis);

		if (distance >= 1.0E-7D)
		{
			float var14 = (float)(Math.atan2(zDis, xDis) * 180.0D / Math.PI) - 90.0F;
			float var15 = (float)(-(Math.atan2(yDis, distance) * 180.0D / Math.PI));
			double var16 = xDis / distance;
			double var18 = zDis / distance;
			this.setLocationAndAngles(attacker.posX + var16, this.posY, attacker.posZ + var18, var14, var15);
			float var20 = (float)distance * 0.2F;
			this.setBulletHeading(xDis, yDis/* + (double)var20*/, zDis, speed);
		}
	}

	public EntityBulletBase(World par1World, EntityLiving par2EntityLiving)
	{
		super(par1World);
		this.shootingEntity = par2EntityLiving;

		if (par2EntityLiving instanceof EntityPlayer)
		{
			this.canBePickedUp = 1;
		}

		this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + (double)par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
		this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.posY -= 0.10000000149011612D;
		this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
		this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
		this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
		this.setBulletHeading(this.motionX, this.motionY, this.motionZ, speed * 1.5F);
	}

	protected void entityInit()
	{
		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
		setSize(0.5F, 0.5F);
		yOffset = 0.0F;
		hitBox = 0.3f;
		speed = 1f;
		slowdown = 0.99f;
		curvature = 0.03f;
		dmg = 4;
		precision = 1f;
		ttlInGround = 1200;
		item = null;
	}

	/**
	 * Uses the provided coordinates as a heading and determines the velocity from it with the set speed and random
	 * variance. Args: x, y, z, speed
	 */
	public void setBulletHeading(double x, double y, double z, float speed)
	{
		float dis = MathHelper.sqrt_double(x * x + y * y + z * z);
		x /= (double)dis;
		y /= (double)dis;
		z /= (double)dis;
		x *= (double)speed;
		y *= (double)speed;
		z *= (double)speed;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float disXZ = MathHelper.sqrt_double(x * x + z * z);
		this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(x, z) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(y, (double)disXZ) * 180.0D / Math.PI);
		this.ticksInGround = 0;
	}

	@SideOnly(Side.CLIENT)

	/**
	 * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
	 * posY, posZ, yaw, pitch
	 */
	public void setPositionAndRotation2(double xPos, double yPos, double zPos, float yaw, float pitch, int par9)
	{
		this.setPosition(xPos, yPos, zPos);
		this.setRotation(yaw, pitch);
	}

	@SideOnly(Side.CLIENT)

	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	public void setVelocity(double x, double y, double z)
	{
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float disXZ = MathHelper.sqrt_double(x * x + z * z);
			this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(x, z) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(y, (double)disXZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			this.ticksInGround = 0;
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		super.onUpdate();
		double prevVelX = motionX;
		double prevVelY = motionY;
		double prevVelZ = motionZ;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float distanceXZ = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)distanceXZ) * 180.0D / Math.PI);
		}

		int blockAtPoint = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);

		if (blockAtPoint > 0)
		{
			Block.blocksList[blockAtPoint].setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
			AxisAlignedBB blockAtPointAABB = Block.blocksList[blockAtPoint].getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

			if (blockAtPointAABB != null && blockAtPointAABB.isVecInside(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ)))
			{
				this.inGround = true;
			}
		}

		if (this.bulletShake > 0)
		{
			--this.bulletShake;
		}

		if (this.inGround)
		{
			int tileID = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
			int tileMeta = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);

			if (tileID == this.inTile && tileMeta == this.inData)
			{

				++this.ticksInGround;
                tickInGround();
				if (this.ticksInGround == ttlInGround)
				{
					this.setDead();
				}
			}
			else
			{
				this.inGround = false;
				//Vec3 motionVec3 = new Vec3(this.motionX,this.motionY,this.motionZ);
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			}
		}
		else
		{
			++this.ticksInAir;
			tickFlying();
			Vec3 vecPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			Vec3 vecPosMotion = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition bulletMOP = this.worldObj.rayTraceBlocks_do_do(vecPos, vecPosMotion, false, true);
			vecPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			vecPosMotion = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

			if (bulletMOP != null)
			{
				vecPosMotion = this.worldObj.getWorldVec3Pool().getVecFromPool(bulletMOP.hitVec.xCoord, bulletMOP.hitVec.yCoord, bulletMOP.hitVec.zCoord);
			}

			Entity entityHit = null;
			List entitiesInAABBExThis = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double var7 = 0.0D;
			Iterator entitiesInAABBExThisIterator = entitiesInAABBExThis.iterator();

			while (entitiesInAABBExThisIterator.hasNext())
			{
				Entity entityIterator = (Entity)entitiesInAABBExThisIterator.next();

				if (entityIterator.canBeCollidedWith() && (entityIterator != this.shootingEntity || this.ticksInAir >= 5))
				{
					if (!canBeShot(entityIterator))
		                continue;
					hitBox = 0.3F;
					AxisAlignedBB entityIteratorAABB = entityIterator.boundingBox.expand((double)hitBox, (double)hitBox, (double)hitBox);
					MovingObjectPosition entityIteratorMOB = entityIteratorAABB.calculateIntercept(vecPos, vecPosMotion);

					if (entityIteratorMOB != null)
					{
						double var14 = vecPos.distanceTo(entityIteratorMOB.hitVec);

						if (var14 < var7 || var7 == 0.0D)
						{
							entityHit = entityIterator;
							var7 = var14;
						}
					}
				}
			}

			if (entityHit != null)
			{
				bulletMOP = new MovingObjectPosition(entityHit);
			}

			float disXYZ;

			if (bulletMOP != null && onHit())
			{
				if (bulletMOP.entityHit != null)
				{
					disXYZ = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					int modifiedDamage = MathHelper.ceiling_double_int((double)disXYZ * this.damage);

					if (this.isCharged())
					{
						modifiedDamage += this.rand.nextInt(modifiedDamage / 2 + 2);
					}

					DamageSource damageSource = null;

					if (this.shootingEntity == null)
					{
						/*if(StringTranslate.getInstance().getCurrentLanguage().equalsIgnoreCase("en_US"))
                    	{
                    		String s = DamageSource.cactus.damageType;
                    		DamageSource.cactus.damageType = "moon";
                    		var4.entityHit.attackEntityFrom(DamageSource.cactus, 20);
                    		DamageSource.cactus.damageType = s;
                    	}
                    	else
                    	{
                    		var4.entityHit.attackEntityFrom(DamageSource.generic, 20);
                    	}*/

						damageSource = new EntityDamageSource("bullet", this.shootingEntity);
					}
					else
					{
						damageSource = new EntityDamageSource("bullet", this.shootingEntity);
					}

					if (this.isBurning())
					{
						bulletMOP.entityHit.setFire(5);
					}
					if(onHitTarget(bulletMOP.entityHit, damageSource)){
						if (bulletMOP.entityHit.attackEntityFrom(damageSource, modifiedDamage) )
						{
							if (bulletMOP.entityHit instanceof EntityLiving)
							{
								if (!this.worldObj.isRemote)
	                            {
	                                EntityLiving var24 = (EntityLiving)bulletMOP.entityHit;
	                                var24.setArrowCountInEntity(var24.getArrowCountInEntity() + 1);
	                            }

								if (this.knockbackStrength > 0)
								{
									float disXZ = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

									if (disXZ > 0.0F)
									{
										bulletMOP.entityHit.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)disXZ , 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)disXZ );
									}
								}
							}

							//this.worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
							this.setDead();
						}
					}
					else if ( this.ticksInAir >= 5)
					{
						this.setDead();
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
					this.xTile = bulletMOP.blockX;
					this.yTile = bulletMOP.blockY;
					this.zTile = bulletMOP.blockZ;
					this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
					this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
					if (onHitBlock(bulletMOP)) {
						this.motionX = (double)((float)(bulletMOP.hitVec.xCoord - this.posX));
						this.motionY = (double)((float)(bulletMOP.hitVec.yCoord - this.posY));
						this.motionZ = (double)((float)(bulletMOP.hitVec.zCoord - this.posZ));
						disXYZ = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
						this.posX -= this.motionX / (double)disXYZ * 0.05000000074505806D;
						this.posY -= this.motionY / (double)disXYZ * 0.05000000074505806D;
						this.posZ -= this.motionZ / (double)disXYZ * 0.05000000074505806D;
						//this.worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
						this.inGround = true;
						this.bulletShake = 7;
						//this.typeOfAttack(false);
					} else {
						inTile = 0;
						inData = 0;
					}
				}
			}

			if (this.isCharged())
			{
				for (int var21 = 0; var21 < 4; ++var21)
				{
					//this.worldObj.spawnParticle("crit", this.posX + this.motionX * (double)var21 / 4.0D, this.posY + this.motionY * (double)var21 / 4.0D, this.posZ + this.motionZ * (double)var21 / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
				}
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			handleMotionUpdate();
			float disXZ = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

			for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)disXZ) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
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
			
			this.setPosition(this.posX, this.posY, this.posZ);
			this.doBlockCollisions();
		}
	}

	/**
     * Handles updating of motion fields.
     */
    public void handleMotionUpdate() {
        float slow = slowdown;

        if(handleWaterMovement()) {
            for(int k = 0; k < 4; k++) {
                float f6 = 0.25F;
                worldObj.spawnParticle("bubble", posX - motionX * f6, posY - motionY * f6, posZ - motionZ * f6, motionX, motionY, motionZ);
            }

            slow *= 0.8F;
        }

        motionX *= slow;
        motionY *= slow;
        motionZ *= slow;
        motionY -= curvature;
    }
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setShort("xTile", (short)this.xTile);
		par1NBTTagCompound.setShort("yTile", (short)this.yTile);
		par1NBTTagCompound.setShort("zTile", (short)this.zTile);
		par1NBTTagCompound.setByte("inTile", (byte)this.inTile);
		par1NBTTagCompound.setByte("inData", (byte)this.inData);
		par1NBTTagCompound.setByte("shake", (byte)this.bulletShake);
		par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
		par1NBTTagCompound.setByte("pickup", (byte)this.canBePickedUp);
		par1NBTTagCompound.setDouble("damage", this.damage);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		this.xTile = par1NBTTagCompound.getShort("xTile");
		this.yTile = par1NBTTagCompound.getShort("yTile");
		this.zTile = par1NBTTagCompound.getShort("zTile");
		this.inTile = par1NBTTagCompound.getByte("inTile") & 255;
		this.inData = par1NBTTagCompound.getByte("inData") & 255;
		this.bulletShake = par1NBTTagCompound.getByte("shake") & 255;
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
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
    {
    	if (item == null) {
    		return;
    	}
        if (!this.worldObj.isRemote && this.inGround && this.bulletShake <= 0)
        {
            boolean pickUpAble = this.canBePickedUp == 1 || this.canBePickedUp == 2 && par1EntityPlayer.capabilities.isCreativeMode;

            if (this.canBePickedUp == 1 && !par1EntityPlayer.inventory.addItemStackToInventory(item.copy()))
            {
                pickUpAble = false;
            }

            if (pickUpAble)
            {
                this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                par1EntityPlayer.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }
	/**
	 * Determines whether a given entity is a candidate for being hit.
	 */
	public boolean canBeShot(Entity ent) {
		return ent.canBeCollidedWith()
				&& !(ent == shootingEntity && ticksInAir < 5)
				&& !(ent instanceof EntityLiving && ((EntityLiving)ent).deathTime > 0);
	}

	/**
	 * Called when the projectile collides with anything, either block or entity.
	 *
	 * @return whether to further process this collision
	 */
	public boolean onHit() {
		return true;
	}

	/**
	 * Called when the projectile collides with an entity.
	 *
	 * @param target The collided entity.
	 * @param source 
	 * @return if the projectile should be destroyed and the entity hurt by dmg
	 */
	public boolean onHitTarget(Entity target, DamageSource source) {
		worldObj.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
		return true;
	}

	/**
	 * Called once a tick when the projectile is in the air.
	 */
	public void tickFlying() {}

	/**
	 * Called once a tick when the projectile is stuck in the ground.
	 */
	public void tickInGround() {}

	/**
	 * Called when the projectile collides with a block.
	 *
	 * @param mop MovingObjectPosition object of the collision
	 * @return true if the projectile should get stuck inground.
	 *
	 * By default calls onHitBlock().
	 */
	public boolean onHitBlock(MovingObjectPosition mop) {
		return onHitBlock();
	}

	/**
	 * Called when the projectile collides with a block.
	 *
	 * @return true if the projectile should get stuck inground.
	 */
	public boolean onHitBlock() {
		worldObj.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
		return true;
	}
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
	 * Sets the amount of knockback the bullet applies when it hits a mob.
	 */
	public void setKnockbackStrength(int par1)
	{
		this.knockbackStrength = par1;
	}

	/**
	 * If returns false, the item will not inflict any damage against entities.
	 */
	public boolean canAttackWithItem()
	{
		return false;
	}

	public void setTypeOfAttack(int par1)
	{
		this.dataWatcher.updateObject(16, Byte.valueOf((byte)(par1)));
	}
	public byte getTypeOfAttack()
	{
		return this.dataWatcher.getWatchableObjectByte(16);
	}

	public boolean isCharged()
	{
		return false;
	}

	public float[] getTexturePlacement() {
		return new float[]{0F,0F,0F,0F,0F,0F,0F};
	}
	public String getTexture()
	{
		return "";
	}
}
