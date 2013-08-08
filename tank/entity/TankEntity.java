package zornco.tank.entity;

import java.util.List;

import zornco.tank.Tank;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TankEntity extends Entity {
	public double roundTo(double yourdouble, int decimalplaces) {
		return Math.round(yourdouble * Math.pow(10, decimalplaces))
				/ Math.pow(10, decimalplaces);
	}

	public TankEntity(World world) {
		super(world);
		tankCurrentDamage = 0;
		tankTimeSinceHit = 0;
		tankRockDirection = 1;
		preventEntitySpawning = true;
		setSize(2.5F, 1.75F);
		yOffset = height / 2.0F;
		// entityWalks = false;
		// MAX_HEALTH = 200;
	}

	protected void entityInit() {
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	public AxisAlignedBB getCollisionBox(Entity entity) {
		return entity.boundingBox;
	}

	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	public boolean canBePushed() {
		return true;
	}

	public TankEntity(World world, double d, double d1, double d2) {
		this(world);
		setPosition(d, d1 + (double) yOffset, d2);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = d;
		prevPosY = d1;
		prevPosZ = d2;
	}

	public double getMountedYOffset() {
		return (double) height * 0.0D - 0.30000001192092896D;
	}

	public boolean attackEntityFrom(DamageSource damagesource, int i) {
		Entity entity = damagesource.getEntity();
		if (riddenByEntity != null && riddenByEntity != entity
				|| riddenByEntity == null) {
			if (worldObj.isRemote || isDead) {
				return true;
			}
			tankRockDirection = -tankRockDirection;
			tankTimeSinceHit = 10;
			tankCurrentDamage += i * 10;
			setBeenAttacked();
			if (tankCurrentDamage > 80) {

				dropItemWithOffset(Block.furnaceIdle.blockID, 1, 0.0F);

				for (int k = 0; k < 4; k++) {
					dropItemWithOffset(Item.ingotIron.itemID, 1, 0.0F);
				}

				setDead();
			}
			return true;
		} else if (riddenByEntity != null && riddenByEntity == entity) {
			if (tankCanShootMachineGun == 1) {
				tankCanShootMachineGun = 0;
				return false;
			}
		}
		return false;
	}

	public void performHurtAnimation() {
		tankRockDirection = -tankRockDirection;
		tankTimeSinceHit = 10;
		tankCurrentDamage += tankCurrentDamage * 10;
	}

	public boolean canBeCollidedWith() {
		return !isDead;
	}

	public void setPositionAndRotation2(double d, double d1, double d2,
			float f, float f1, int i) {
		tankX = d;
		tankY = d1;
		tankZ = d2;
		tankYaw = f;
		tankPitch = f1;
		tankPosRotationIncrements = i + 4;
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;
	}

	public void setVelocity(double d, double d1, double d2) {
		velocityX = motionX = d;
		velocityY = motionY = d1;
		velocityZ = motionZ = d2;
	}

	public void onUpdate() {
		super.onUpdate();
		if (tankTimeSinceHit > 0) {
			tankTimeSinceHit--;
		}
		if (tankCurrentDamage > 0) {
			tankCurrentDamage--;
		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		int i = 5;
		double d = 0.0D;
		for (int j = 0; j < i; j++) {
			double d4 = (boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double) (j + 0))
					/ (double) i) - 0.125D;
			double d8 = (boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double) (j + 1))
					/ (double) i) - 0.125D;
			AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(
					boundingBox.minX, d4, boundingBox.minZ, boundingBox.maxX,
					d8, boundingBox.maxZ);
			if (worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
				d += 1.0D / (double) i;
			}
		}

		if (worldObj.isRemote) {
			if (tankPosRotationIncrements > 0) {
				double d1 = posX + (tankX - posX)
						/ (double) tankPosRotationIncrements;
				double d5 = posY + (tankY - posY)
						/ (double) tankPosRotationIncrements;
				double d9 = posZ + (tankZ - posZ)
						/ (double) tankPosRotationIncrements;
				double d12;
				for (d12 = tankYaw - (double) rotationYaw; d12 < -180D; d12 += 360D) {
				}
				for (; d12 >= 180D; d12 -= 360D) {
				}
				rotationYaw += d12 / (double) tankPosRotationIncrements;
				rotationPitch += (tankPitch - (double) rotationPitch)
						/ (double) tankPosRotationIncrements;
				tankPosRotationIncrements--;
				setPosition(d1, d5, d9);
				setRotation(rotationYaw, rotationPitch);
			} else {
				double d2 = posX + motionX;
				double d6 = posY + motionY;
				double d10 = posZ + motionZ;
				setPosition(d2, d6, d10);

				motionX *= 0.99000000953674316D;
				motionY *= 0.94999998807907104D;
				motionZ *= 0.99000000953674316D;
			}
			return;
		}
		double d3 = d * 2D - 1.0D;
		motionY += 0.039999999105930328D * d3;
		if (riddenByEntity != null) {
			stepHeight = riddenByEntity.stepHeight + 1F;
			// INTERESTING TIDBIT: if player runs first before getting into the
			// tank, tank goes faster,
			// if in creative mode and press spacespace to fly, can move even
			// faster to a maximum of .445
			motionX += riddenByEntity.motionX;// * 0.40000000000000001D;
			motionZ += riddenByEntity.motionZ;// * 0.40000000000000001D;
		} else {
			stepHeight = 0;
			motionX = 0;// * 0.40000000000000001D;
			motionZ = 0;// * 0.40000000000000001D;
		}
		double d7 = .50000000000000002D;
		if (motionX < -d7) {
			motionX = -d7;
		}
		if (motionX > d7) {
			motionX = d7;
		}
		if (motionZ < -d7) {
			motionZ = -d7;
		}
		if (motionZ > d7) {
			motionZ = d7;
		}
		moveEntity(motionX, motionY, motionZ);
		tankSpeed = Math.sqrt(motionX * motionX + motionZ * motionZ);
		// System.out.println((new
		// StringBuilder("tankspeed ")).append(tankSpeed).append(" motionX ").append(motionX).append(" motionZ ").append(motionZ).toString());
		boolean flag = handleWaterMovement();
		boolean flag1 = handleLavaMovement();

		if (isCollidedHorizontally) {
			if (flag) {
				motionY += 0.1D;
			} else if (flag1) {
				motionY += 0.1D;
			}
			// if(onGround)
			// {
			// motionY = 0.40999998688697815D;
			// }
		} else {
			motionX *= 0.90000000953674316D;
			motionY *= 0.94999998807907104D;
			motionZ *= 0.90000000953674316D;
		}
		rotationPitch = 0.0F;
		double d14 = rotationYaw;
		double d16 = prevPosX - posX;
		double d17 = prevPosZ - posZ;
		if (d16 * d16 + d17 * d17 > 0.001D) {
			d14 = (float) ((Math.atan2(d17, d16) * 180D) / 3.1415926535897931D);
		}
		double d19;
		for (d19 = d14 - (double) rotationYaw; d19 >= 180D; d19 -= 360D) {
		}
		for (; d19 < -180D; d19 += 360D) {
		}
		if (d19 > 20D) {
			d19 = 20D;
		}
		if (d19 < -20D) {
			d19 = -20D;
		}
		rotationYaw += d19;
		setRotation(rotationYaw, rotationPitch);
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
				boundingBox.expand(0.20000000298023224D, 0.0D,
						0.20000000298023224D));// collision
		// detection
		if (list != null && list.size() > 0) {
			for (int j1 = 0; j1 < list.size(); j1++) {
				Entity entity = (Entity) list.get(j1);
				if (entity != riddenByEntity && entity.canBePushed()
						&& (entity instanceof TankEntity)) {
					entity.applyEntityCollision(this);
				}
			}

		}
		if (riddenByEntity != null && riddenByEntity.isDead)// kill code
		{
			riddenByEntity = null;
		}
		wheelRotation -= tankSpeed * Math.PI;

		if (tankCanShootMachineGun == 3) {
			TankBulletEntity arrow = new TankBulletEntity(worldObj, 20);
			/*currentPos.rotateAroundY(-currentAngle.angleY * 2
					- (float) Math.toRadians(rotationYaw));
			currentEndPos.rotateAroundY((float) -currentAngle.angleY * 2
					- (float) Math.toRadians(rotationYaw));*/
			currentEndPos = currentPos.subtract(currentEndPos);
			double muzzX = posX + currentPos.xCoord * 0.125D;
			double muzzY = posY - currentPos.yCoord * 0.125D;
			double muzzZ = posZ + currentPos.zCoord * 0.125D;
			// System.out.println(muzzX + " = " + posX + " = " +
			// currentPos.xCoord);
			// System.out.println(muzzY + " = " + posY + " = " +
			// currentPos.yCoord);
			// System.out.println(muzzZ + " = " + posZ + " = " +
			// currentPos.zCoord);
			arrow.setPosition(muzzX, muzzY, muzzZ);
			arrow.setTankBulletHeading(currentEndPos.xCoord,
					-currentEndPos.yCoord, currentEndPos.zCoord, 5F, 0);
			// System.out.println(rotationYaw + " + " +
			// riddenByEntity.rotationYaw + " + " +
			// Math.toDegrees(currentAngle.angleY) + " + " + arrow.rotationYaw);
			if (!worldObj.isRemote) {
				worldObj.spawnEntityInWorld(arrow);
			}
		}

		if (tankCanShoot == 120
				|| (rider!=null&&rider.capabilities.isCreativeMode && tankCanShoot == 3)) {
			TankBulletEntity arrow = new TankBulletEntity(worldObj, bulletType);
			/*currentPos.rotateAroundY(-currentAngle.angleY * 2
					- (float) Math.toRadians(rotationYaw));
			currentEndPos.rotateAroundY((float) -currentAngle.angleY * 2
					- (float) Math.toRadians(rotationYaw));*/
			currentEndPos = currentPos.subtract(currentEndPos);
			double muzzX = posX + currentPos.xCoord * 0.125D;
			double muzzY = posY - currentPos.yCoord * 0.125D;
			double muzzZ = posZ + currentPos.zCoord * 0.125D;
			// System.out.println(muzzX + " = " + posX + " = " +
			// currentPos.xCoord);
			// System.out.println(muzzY + " = " + posY + " = " +
			// currentPos.yCoord);
			// System.out.println(muzzZ + " = " + posZ + " = " +
			// currentPos.zCoord);
			arrow.setPosition(muzzX, muzzY, muzzZ);
			arrow.setTankBulletHeading(currentEndPos.xCoord,
					-currentEndPos.yCoord, currentEndPos.zCoord, 5F, 0);
			// System.out.println(rotationYaw + " + " +
			// riddenByEntity.rotationYaw + " + " +
			// Math.toDegrees(currentAngle.angleY) + " + " + arrow.rotationYaw);
			if (!worldObj.isRemote) {
				worldObj.spawnEntityInWorld(arrow);
			}
		}
		if (tankCanShootMachineGun > 1) {
			tankCanShootMachineGun--;
		}
		if (tankCanShoot > 1) {
			tankCanShoot--;
		}
	}

	// THIS IS ALL TO FIGURE OUT how to angle the tank to go up and down,
	// possibly side to side
	/*
	 * public Vec3 func_515_a(double d, double d1, double d2, double d3) { int
	 * i = MathHelper.floor_double(d); int j = MathHelper.floor_double(d1); int
	 * k = MathHelper.floor_double(d2); if(worldObj.getBlockId(i, j - 1, k) ==
	 * Block.minecartTrack.blockID) { j--; } if(worldObj.getBlockId(i, j, k) ==
	 * Block.minecartTrack.blockID) { int l = worldObj.getBlockMetadata(i, j,
	 * k); d1 = j; if(l >= 2 && l <= 5) { d1 = j + 1; } int ai[][] =
	 * field_855_j[l]; double d4 = ai[1][0] - ai[0][0]; double d5 = ai[1][2] -
	 * ai[0][2]; double d6 = Math.sqrt(d4 * d4 + d5 * d5); d4 /= d6; d5 /= d6; d
	 * += d4 * d3; d2 += d5 * d3; if(ai[0][1] != 0 && MathHelper.floor_double(d)
	 * - i == ai[0][0] && MathHelper.floor_double(d2) - k == ai[0][2]) { d1 +=
	 * ai[0][1]; } else if(ai[1][1] != 0 && MathHelper.floor_double(d) - i ==
	 * ai[1][0] && MathHelper.floor_double(d2) - k == ai[1][2]) { d1 +=
	 * ai[1][1]; } return func_514_g(d, d1, d2); } else { return null; } }
	 * 
	 * public Vec3 func_514_g(double d, double d1, double d2) { int i =
	 * MathHelper.floor_double(d); int j = MathHelper.floor_double(d1); int k =
	 * MathHelper.floor_double(d2); if(worldObj.getBlockId(i, j - 1, k) ==
	 * Block.minecartTrack.blockID) { j--; } if(worldObj.getBlockId(i, j, k) ==
	 * Block.minecartTrack.blockID) { int l = worldObj.getBlockMetadata(i, j,
	 * k); d1 = j; if(l >= 2 && l <= 5) { d1 = j + 1; } int ai[][] =
	 * field_855_j[l]; double d3 = 0.0D; double d4 = (double)i + 0.5D +
	 * (double)ai[0][0] * 0.5D; double d5 = (double)j + 0.5D + (double)ai[0][1]
	 * * 0.5D; double d6 = (double)k + 0.5D + (double)ai[0][2] * 0.5D; double d7
	 * = (double)i + 0.5D + (double)ai[1][0] * 0.5D; double d8 = (double)j +
	 * 0.5D + (double)ai[1][1] * 0.5D; double d9 = (double)k + 0.5D +
	 * (double)ai[1][2] * 0.5D; double d10 = d7 - d4; double d11 = (d8 - d5) *
	 * 2D; double d12 = d9 - d6; if(d10 == 0.0D) { d = (double)i + 0.5D; d3 = d2
	 * - (double)k; } else if(d12 == 0.0D) { d2 = (double)k + 0.5D; d3 = d -
	 * (double)i; } else { double d13 = d - d4; double d14 = d2 - d6; double d15
	 * = (d13 * d10 + d14 * d12) * 2D; d3 = d15; } d = d4 + d10 * d3; d1 = d5 +
	 * d11 * d3; d2 = d6 + d12 * d3; if(d11 < 0.0D) { d1++; } if(d11 > 0.0D) {
	 * d1 += 0.5D; } return Vec3.createVector(d, d1, d2); } else { return null;
	 * } }
	 */

	public void updateRiderPosition() {
		if (riddenByEntity == null) {
			return;
		} else {
			double d = Math
					.cos(((double) rotationYaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D;
			double d1 = Math
					.sin(((double) rotationYaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D;
			riddenByEntity.setPosition(posX + d, posY + getMountedYOffset()
					+ riddenByEntity.getYOffset(), posZ + d1);
			return;
		}
	}

	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
	}

	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
	}

	public float getShadowSize() {
		return 1.0F;
	}

	public boolean interact(EntityPlayer entityplayer) {
		rider = entityplayer;
		if (riddenByEntity != null && (riddenByEntity instanceof EntityPlayer)
				&& riddenByEntity != rider) {
			return true;
		}
		if (!worldObj.isRemote && tankCanShoot == 1) {
			wrongItem = true;
			ItemStack itemstack = rider.inventory.getCurrentItem();
			for (int i = 0; i < 4; i++) {
				if (itemstack != null
						&& itemstack.itemID == Tank.tankBullet[i].itemID
						&& riddenByEntity == rider) {
					if (!rider.capabilities.isCreativeMode
							&& --itemstack.stackSize == 0) {
						rider.inventory.setInventorySlotContents(
								rider.inventory.currentItem, null);
					}
					bulletType = i;
					wrongItem = false;
					tankCanShoot = 0;
				}
			}
			if (wrongItem) {
				rider.mountEntity(this);
			}
		}
		return true;
	}

	// private static final int field_855_j[][][] ={{{ 0, 0, -1 },{ 0, 0, 1 }
	// },{{ -1, 0, 0 },{ 1, 0, 0 } },{{ -1, -1, 0 },{ 1, 0, 0 } },{{ -1, 0, 0
	// },{ 1, -1, 0 } },{{ 0, 0, -1 },{ 0, -1, 1 } },{{ 0, -1, -1 },{ 0, 0, 1 }
	// },{{ 0, 0, 1 },{ 1, 0, 0 } },{{ 0, 0, 1 },{ -1, 0, 0 } },{{ 0, 0, -1 },{
	// -1, 0, 0 } },{{ 0, 0, -1 },{ 1, 0, 0 } } };

	public EntityPlayer rider;
	private boolean wrongItem;
	public int bulletType;
	//public Angle3D currentAngle;
	//public Angle3D currentAngle2;
	public Vec3 currentPos;
	public Vec3 currentEndPos;
	public int tankCanShoot = 1;
	public int tankCanShootMachineGun = 1;
	public double tankSpeed;
	public float wheelRotation;
	public int tankCurrentDamage;
	public int tankTimeSinceHit;
	public int tankRockDirection;
	private int tankPosRotationIncrements;
	private double tankX;
	private double tankY;
	private double tankZ;
	private double tankYaw;
	private double tankPitch;
	private double velocityX;
	private double velocityY;
	private double velocityZ;
}
