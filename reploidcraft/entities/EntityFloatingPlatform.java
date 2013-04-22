package zornco.reploidcraft.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import zornco.reploidcraft.ReploidCraft;
import zornco.reploidcraft.core.GuiIds;
import zornco.reploidcraft.utils.*;
public class EntityFloatingPlatform extends Entity
{
	/**
	 * List of absolute int coords starting from where the platform was placed
	 */
	public List<PlatformPathPoint> currentFlightTargets = new ArrayList<PlatformPathPoint>();
	public PlatformPathPoint prevFlightTarget;

	/** Used to create the rotation animation when rendering the propeller. */
	public int innerRotation;
	public double timeSinceStart = 0;
	public double time = 0;
	public double nextRenderPosX;
	public double nextRenderPosY;
	public double nextRenderPosZ;

	public EntityFloatingPlatform(World par1World)
	{
		super(par1World);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.preventEntitySpawning = true;
		this.setSize(1.0F, 0.5F);
		this.yOffset = /*this.height / 2*/.0F;
		this.innerRotation = this.rand.nextInt(100000);
	}
	public EntityFloatingPlatform(World par1World, double par2, double par4, double par6)
	{
		this(par1World);
		this.setPosition(par2, par4-0.5F/* + (double)this.yOffset*/, par6);
		this.currentFlightTargets.add(new PlatformPathPoint(this.posX, this.posY, this.posZ, 0.05F));
		//this.currentFlightTargets.add(new PlatformPathPoint(this.posX, this.posY + 3, this.posZ, 0.05F));
		this.currentFlightTargets.add(new PlatformPathPoint(this.posX - 15, this.posY, this.posZ, 0.05F));

		this.motionX = this.motionY = this.motionZ = 0.0D;
		this.prevPosX = par2;
		this.prevPosY = par4;
		this.prevPosZ = par6;
	}
	protected void entityInit()
	{
		this.dataWatcher.addObject(16, new Integer((int)0));
		this.dataWatcher.addObject(17, new Byte((byte)0));
	}
	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{ 
		super.onUpdate();
		//this.setDead();
		//System.out.println("alive");
		if(worldObj.isRemote)
			++this.innerRotation;
		/*this.motionX = 0D;
		this.motionY = 0D;
		this.motionZ = 0D;
		this.rotationYaw = 0;*/
		if(this.currentFlightTargets.size() > 0)
		{
			if(this.getPointPosition() >= this.currentFlightTargets.size())
			{
				this.setPointPosition(0); //sanity check
			}
			if (this.currentFlightTargets.get(getPointPosition()) != null && this.currentFlightTargets.get(getPointPosition()).getDistanceSquared(this.posX, this.posY, this.posZ) < 0.1F)
			{
				//setNextPointPosition();
			}
			else if(this.currentFlightTargets.get(getPointPosition()) == null)
			{
				this.currentFlightTargets.add(new PlatformPathPoint(this.posX, this.posY, this.posZ, 1)); //
			}
			if(this.prevFlightTarget == null)
			{//System.out.println("alive");
				this.prevFlightTarget = (new PlatformPathPoint(this.posX, this.posY, this.posZ, 1)); //
			}
			double targetPosX = this.currentFlightTargets.get(getPointPosition()).posX;
			double targetPosY = this.currentFlightTargets.get(getPointPosition()).posY;
			double targetPosZ = this.currentFlightTargets.get(getPointPosition()).posZ;
			//ReploidCraft.logger.warning("" + targetPosX + " " + targetPosY + " " + targetPosZ);
			float speed = this.currentFlightTargets.get(getPointPosition()).speed;
			double delta_x = targetPosX - this.prevFlightTarget.posX;
			double delta_y = targetPosY - this.prevFlightTarget.posY;
			double delta_z = targetPosZ - this.prevFlightTarget.posZ;
			double goal_dist = Math.sqrt( (delta_x * delta_x) + (delta_y * delta_y) + (delta_z * delta_z) );
			/*if (goal_dist > speed)
			{
			    double ratio = speed / goal_dist;
			    motionX = ratio * delta_x;
			    motionY = ratio * delta_y;
			    motionZ = ratio * delta_z;
				this.prevPosX = this.posX;
				this.prevPosY = this.posY;
				this.prevPosZ = this.posZ;
			    this.setPosition(this.posX + motionX, this.posY + motionY, this.posZ + motionZ);
			}
			else
			{
				this.setPosition(targetPosX + Math.signum(targetPosX) * 0.5D, 
						targetPosY + 0.5D, 
						targetPosZ + Math.signum(targetPosZ) * 0.5D);

				setNextPointPosition();
			}*/
			time= goal_dist*20D;
			double nextPosX = lerp(this.prevFlightTarget.posX, targetPosX, time, timeSinceStart);
			double nextPosY = lerp(this.prevFlightTarget.posY, targetPosY, time, timeSinceStart);
			double nextPosZ = lerp(this.prevFlightTarget.posZ, targetPosZ, time, timeSinceStart);
			this.setPosition(nextPosX, nextPosY, nextPosZ);
			//ReploidCraft.logger.warning("" + nextPosX + " " + nextPosY + " " + nextPosZ);

			if (timeSinceStart >= time)
			{
				setNextPointPosition();
				timeSinceStart = 0;
			}
			else /*if (ticksExisted%5 == 0)*/
				timeSinceStart++;

			nextRenderPosX = lerp(this.prevFlightTarget.posX, targetPosX, time, timeSinceStart);
			nextRenderPosY = lerp(this.prevFlightTarget.posY, targetPosY, time, timeSinceStart);
			nextRenderPosZ = lerp(this.prevFlightTarget.posZ, targetPosZ, time, timeSinceStart);
		}
		//TODO: move the platform smoothly
	}
	public double lerp(double start, double target, double duration, double timeSinceStart)
	{
		double value = start;
		if (timeSinceStart > 0.0f && timeSinceStart < duration)
		{
			double range = target - start;
			double percent = timeSinceStart / duration;
			value = start + (range * percent);
		}
		else if (timeSinceStart >= duration)
		{
			value = target;
		}
		return value;
	}

	public double ease(double start, double target, double duration, double timeSinceStart)
	{
		double value = start;
		if (timeSinceStart > 0.0f && timeSinceStart < duration)
		{
			final double range = target - start;
			final double percent = timeSinceStart / (duration / 2.0f);
			if (percent < 1.0f)
			{
				value = start + ((range / 2.0f) * percent * percent * percent);
			}
			else
			{
				final double shiftedPercent = percent - 2.0f;
				value = start + ((range / 2.0f) *
						((shiftedPercent * shiftedPercent * shiftedPercent) + 2.0f));
			}
		}
		else if (timeSinceStart >= duration)
		{
			value = target;
		}
		return value;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		if (!this.worldObj.isRemote)
		{
			ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();

			if (var2 != null && var2.itemID == Item.bucketEmpty.itemID)
			{
				//par1EntityPlayer.openGui(ReploidCraft.instance, GuiIds.UPGRADE_STATION, par1World, i, j, k);

				//open gui
			}
		}
		return true;

	}
	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		if (this.isEntityInvulnerable())
		{
			return false;
		}
		else
		{
			if (!this.isDead && !this.worldObj.isRemote)
			{

				this.setDead();

				if (!this.worldObj.isRemote)
				{
					//this.worldObj.createExplosion((Entity)null, this.posX, this.posY, this.posZ, 1.0F, true);
				}

			}

			return true;
		}
	}
	/**
	 * Return whether this entity should NOT trigger a pressure plate or a tripwire.
	 */
	public boolean doesEntityNotTriggerPressurePlate()
	{
		return true;
	}
	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	 * prevent them from trampling crops
	 */
	protected boolean canTriggerWalking()
	{
		return false;
	}
	/**
	 * Returns true if this entity should push and be pushed by other entities when colliding.
	 */
	public boolean canBePushed()
	{
		return false;
	}
	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}
	/**
	 * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
	 * pushable on contact, like boats or minecarts.
	 */
	public AxisAlignedBB getCollisionBox(Entity par1Entity)
	{
		return par1Entity.boundingBox;
	}
	/**
	 * returns the bounding box for this entity
	 */
	public AxisAlignedBB getBoundingBox()
	{
		/*if(!this.worldObj.isRemote)
			return AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);*/
		return this.boundingBox;
	}
	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}
	/**
	 * Called when the mob is falling. Calculates and applies fall damage.
	 */
	protected void fall(float par1) {}
	/**
	 * Takes in the distance the entity has fallen this tick and whether its on the ground to update the fall distance
	 * and deal fall damage if landing on the ground.  Args: distanceFallenThisTick, onGround
	 */
	protected void updateFallState(double par1, boolean par3) {}
	/**
	 * Returns true if the flag is active for the entity. Known flags: 0) is direction; 1) is circle(pingpong false); 2) is riding
	 * something; 3) is sprinting; 4) is eating
	 */
	protected boolean getPathFlag(int par1)
	{
		return (this.dataWatcher.getWatchableObjectByte(17) & 1 << par1) != 0;
	}
	/**
	 * Enable or disable a entity flag, see getEntityFlag to read the know flags.
	 */
	protected void setPathFlag(int par1, boolean par2)
	{
		byte var3 = this.dataWatcher.getWatchableObjectByte(17);

		if (par2)
		{
			this.dataWatcher.updateObject(17, Byte.valueOf((byte)(var3 | 1 << par1)));
		}
		else
		{
			this.dataWatcher.updateObject(17, Byte.valueOf((byte)(var3 & ~(1 << par1))));
		}
	}
	public int getPointPosition()
	{
		return this.dataWatcher.getWatchableObjectInt(16);
	}
	public void setPointPosition(int point)
	{
		this.dataWatcher.updateObject(16, Integer.valueOf((int)point));
	}
	public int setNextPointPosition()
	{
		int dataPoint = this.getPointPosition();
		if(currentFlightTargets.size() > 1)
		{
			prevFlightTarget = currentFlightTargets.get(dataPoint);
			if (getPathFlag(1))//circle
			{
				if(getPathFlag(0))//direction
				{
					dataPoint++;
					if(dataPoint == currentFlightTargets.size())
					{
						dataPoint = 0;
					}
				}
				else
				{
					dataPoint--;
					if(dataPoint < 0)
					{
						dataPoint = currentFlightTargets.size();
					}
				}
				this.setPointPosition(dataPoint);
			}
			else//pingPong
			{
				if(getPathFlag(0))//direction
				{
					dataPoint++;
					if(dataPoint == currentFlightTargets.size())
					{
						dataPoint = currentFlightTargets.size() - 1;
						setPathFlag(0, false);
					}
				}
				else
				{
					dataPoint--;
					if(dataPoint < 0)
					{
						dataPoint = 1;
						setPathFlag(0, true);
					}
				}
				this.setPointPosition(dataPoint);
			}
		}
		return this.getPointPosition();
	}
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setInteger("Point", this.getPointPosition());
		NBTTagList nbttaglist = new NBTTagList();
		Iterator it=currentFlightTargets.iterator();
		byte i = 0;
		while(it.hasNext())
		{
			NBTTagCompound entry = new NBTTagCompound();
			entry = new NBTTagCompound();
			entry.setByte("point", (byte)i);
			((PlatformPathPoint) it.next()).writeToNBT(entry);
			nbttaglist.appendTag(entry);
			i++;
		}
		nbttagcompound.setTag("CordList", nbttaglist);
	}
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		this.setPointPosition(nbttagcompound.getInteger("Point"));
		NBTTagList nbttaglist = nbttagcompound.getTagList("CordList");
		this.currentFlightTargets.clear();
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			int var5 = nbttagcompound1.getByte("point") & 255;
			this.currentFlightTargets.add(var5, PlatformPathPoint.loadPointFromNBT(nbttagcompound1));
		}
	}
}
