package zornco.reploidcraft.bullets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;



public class EntityMetBullet extends EntityBulletBase {
	public static final float hBounce = -0.5F;
	public static final float hBounceEnt = -0.2F;
	public static final float vBounce = -0.5F;
	public static final float vBounceEnt = 0.3F;
	public static final String hurtSound = "damage.fallbig";
	public static final double minBounce = 0.3D;
	public static double damageFactor = 60.D/7.D;

	public EntityMetBullet(World par1World) {
		super(par1World);
	}
	public EntityMetBullet(World par1World, double xPos, double yPos, double zPos) {
		super(par1World, xPos, yPos, zPos);
	}
	public EntityMetBullet(World world, EntityLiving attacker, EntityLiving target) {
		super(world, attacker, target);
	}
	public EntityMetBullet(World par1World, EntityLiving ent) {
		super(par1World, ent);
	}
	public void entityInit() {
		super.entityInit();
		speed = 0.9f;
		hitBox = 0.3f;
		precision = 5.9f;
		setSize(0.25f, 0.25f);
		renderDistanceWeight = 10D;
		ttlInGround = 12;
		curvature = 0.0f;
	}
	@Override
	public float[] getTexturePlacement()
	{
		//6, 8, 0, 44, 44 and 12,25,7,32,32 and 32,44,9,0,0
		return new float[]{6F, 8F, 0F, 44F, 44F, 64F, 64F};
	}
	@Override
	public String getTexture()
	{
		return "buster_0";
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	public double getSpeed() {
		return Math.sqrt(motionX*motionX + motionY*motionY + motionZ*motionZ);
	}

	public double getDamage() {
		double v = this.getSpeed();
		double damage = 2/*Math.floor(v * damageFactor)*/;
		//System.out.format("%.2f -> %d", v, damage);
		return damage;
	}

	@Override
	public boolean onHitTarget(Entity ent, DamageSource source) {
		if (inGround)
			return false;

		int dmg = (int)getDamage();
		String sound = "random.wood click";

		if(dmg > 0) {
			ent.attackEntityFrom(source, dmg);
			//System.out.println(dmg);
			sound = hurtSound;
		}

		worldObj.playSoundAtEntity(this, sound, 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
		setDead();
		ent.moveEntity(motionX, motionY, motionZ);
		motionX *= vBounceEnt;
		motionY *= hBounceEnt;
		motionZ *= hBounceEnt;
		return false;
	}

	@Override
	public boolean onHitBlock(MovingObjectPosition mop) {
		
			setDead();
		

		return false;
	}
	/**
	 * Called once a tick when the projectile is in the air.
	 */
	public void tickFlying() {
		if(ticksInAir >= 600|| getSpeed() < 0.3F)
			setDead();
	}
	public void onCollideWithPlayer(EntityPlayer entityplayer) {
		/*if(worldObj.isRemote || !inGround) {
			return;
		}

		int i = item.stackSize;

		if(entityplayer.inventory.addItemStackToInventory(item)) {
			worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			entityplayer.onItemPickup(this, i);
			setDead();
		}*/
	}
}
