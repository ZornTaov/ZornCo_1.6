package zornco.megax.bullets;

import zornco.megax.MegaX;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;


public class EntityBusterBullet extends EntityBulletBase {
	public static final float hBounce = -0.5F;
	public static final float hBounceEnt = -0.2F;
	public static final float vBounce = -0.5F;
	public static final float vBounceEnt = 0.3F;
	public static final String hurtSound = "damage.fallbig";
	public static final double minBounce = 0.3D;
	public static double damageFactor = 60.D/7.D;
	private int charge = 1;
	private boolean blockHit = false;
	private int ttlInAir = 12;

	public EntityBusterBullet(World par1World) {
		super(par1World);
	}
	public EntityBusterBullet(World par1World, double xPos, double yPos, double zPos) {
		super(par1World, xPos, yPos, zPos);
	}
	public EntityBusterBullet(World world, EntityLiving attacker, EntityLiving target) {
		super(world, attacker, target);
	}
	public EntityBusterBullet(World par1World, EntityLiving ent) {
		super(par1World, ent);
	}
	public void entityInit() {
		super.entityInit();
		this.speed = 0.9f;
		this.hitBox = 0.3f;
		this.precision = 0.9f;
		this.setSize(0.25f, 0.25f);
		this.renderDistanceWeight = 10D;
		this.curvature = 0.0f;
	}
	public void onUpdate()
	{
		super.onUpdate();

		if (this.worldObj.isRemote)
		{
			if(getCharge() == 0)
			{
				MegaX.proxy.busterShot(this.worldObj, this.posX, this.posY, this.posZ, 0.2F, getCharge());
			}
			if(getCharge() == 1)
			{
				MegaX.proxy.busterShot(this.worldObj, this.posX, this.posY, this.posZ, 0.4F, getCharge());
				for (int a = 0; a < 3; a++) {

					double x2 = (this.posX + this.prevPosX) / 2.0D + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.1F;
					double y2 = (this.posY + this.prevPosY) / 2.0D + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.1F;
					double z2 = (this.posZ + this.prevPosZ) / 2.0D + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.1F;
					MegaX.proxy.busterShot(this.worldObj, x2, y2, z2, 0.2F, getCharge());
				}
			}
			if(getCharge() == 2)
			{
				MegaX.proxy.busterShot(this.worldObj, this.posX, this.posY, this.posZ, 1F, getCharge());
				for (int a = 0; a < 3; a++) {

					double x2 = (this.posX + this.prevPosX) / 2.0D + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.1F;
					double y2 = (this.posY + this.prevPosY) / 2.0D + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.1F;
					double z2 = (this.posZ + this.prevPosZ) / 2.0D + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.1F;
					MegaX.proxy.busterShot(this.worldObj, x2, y2, z2, 0.2F, getCharge());
				}
			}
		}
	}
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}
	public int getCharge()
	{
		return this.dataWatcher.getWatchableObjectByte(16);
	}
	@Override
	public float[] getTexturePlacement()
	{
		//6, 8, 0, 44, 44 and 12,25,7,32,32 and 32,44,9,0,0
		int type = this.dataWatcher.getWatchableObjectByte(16);
		//if (type == 0)
		//	return new float[]{6F, 8F, 0F, 44F, 44F, 64F, 64F};
		//else if(type == 1)
		//	return new float[]{12F, 25F, 7F, 32F, 32F, 64F, 64F};
		//else if(type == 2)
		//	return new float[]{32F, 44F, 9F, 0F, 0F, 64F, 64F};
		return new float[]{0,0,0,0,0,0,0};
	}
	@Override
	public String getTexture()
	{
		return "buster_0";
	}
	public double getSpeed() {
		return Math.sqrt(motionX*motionX + motionY*motionY + motionZ*motionZ);
	}

	public double getDamage() {
		double v = this.getSpeed();
		this.damage = (int)Math.floor(v * damageFactor);
		//System.out.format("%.2f -> %f", v, damage);
		return this.damage;
	}

	@Override
	public boolean onHitTarget(Entity ent, DamageSource source) {
		if (inGround||ent == this.shootingEntity)
			return false;

		dmg = (int)getDamage();
		String sound = "random.wood click";
		boolean hit = false;
		if(dmg > 0) {
			hit = ent.attackEntityFrom(source, dmg);
			//System.out.println(dmg);

			if(hit)
			{
				sound = hurtSound;
				worldObj.playSoundAtEntity(this, sound, 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
				setDead();
			}
			else dmg = 0;
		}
		worldObj.playSoundAtEntity(this, sound, 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));

		motionX *= vBounceEnt;
		motionY *= hBounceEnt;
		motionZ *= hBounceEnt;
		return false;
	}

	@Override
	public boolean onHitBlock(MovingObjectPosition mop) {
		if (getSpeed() < minBounce && mop.sideHit == 1) {
			/*if (!true) {
				yOffset = height / 2f;
				return true;
			} else {*/
			//setDead();
			//}
		}
		switch(mop.sideHit) {
		case 0: // '\0'
		case 1: // '\001'
			//ticksInAir += 580;
			motionY *= vBounce;
			motionX *= Math.abs(hBounce);
			motionZ *= Math.abs(hBounce);
			break;
		case 2: // '\002'
		case 3: // '\003'
			//ticksInAir += 580;
			motionZ *= hBounce;
			motionX *= Math.abs(hBounce);
			motionY *= Math.abs(vBounce);
			break;
		case 4: // '\004'
		case 5: // '\005'
			//ticksInAir += 580;
			motionX *= hBounce;
			motionZ *= Math.abs(hBounce);
			motionY *= Math.abs(vBounce);
			break;
		}

		Block block = Block.blocksList[inTile];
		if(block != null) {/*
			if((block instanceof BlockBreakable)
					|| block.blockMaterial == Material.leaves) {
				worldObj.setBlockAndMetadataWithNotify(xTile, yTile, zTile, 0, 0);
			}*/

			worldObj.playSoundAtEntity(this,
					block.stepSound.getStepSound() ,
					1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
		}
		blockHit = true;
		return false;
	}
	/**
	 * Called once a tick when the projectile is in the air.
	 */
	public void tickFlying() {
		if(blockHit)
		{
			ttlInAir--;
			if(ttlInAir == 0) setDead();
		}
		if(ticksInAir >= 600|| getSpeed() < 0.3F)
			setDead();
	}
	public void onCollideWithPlayer(EntityPlayer entityplayer) {
		if(worldObj.isRemote || !inGround) {
			return;
		}

		int i = item.stackSize;

		if(entityplayer.inventory.addItemStackToInventory(item)) {
			worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			entityplayer.onItemPickup(this, i);
			setDead();
		}
	}
	@Override
	public void setTypeOfAttack(int type)
	{
		if (type == 0)
		{
			//this.setSize(0.25F, 0.25F);
		}
		else if(type == 1)
		{
			//this.setSize(0.5F, 0.50F);
			this.setDamage(this.getDamage()+2);
		}
		else if(type == 2)
		{
			//this.setSize(1F, 1F);
			this.setDamage(this.getDamage()+4);
		}
		super.setTypeOfAttack(type);
	}
	@Override
	public boolean isCharged()
	{
		byte crit = this.dataWatcher.getWatchableObjectByte(16);
		return crit == 2 ? true : crit == 1 ? true : false;
	}
}
