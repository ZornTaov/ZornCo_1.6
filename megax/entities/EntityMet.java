package zornco.megax.entities;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import zornco.megax.MegaX;
import zornco.megax.entities.AIs.EntityAIBulletAttack;
import zornco.megax.entities.AIs.EntityAIMetHide;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityMet extends EntityTameable {


	/**
	 * Holds the RGB table of the sheep colors - in OpenGL glColor3f values - used to render the sheep colored fleece.
	 */
	public static final float[][] fleeceColorTable = new float[][] {{1.0F, 1.0F, 1.0F}, {0.95F, 0.7F, 0.2F}, {0.9F, 0.5F, 0.85F}, {0.6F, 0.7F, 0.95F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.7F, 0.8F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.6F, 0.7F}, {0.7F, 0.4F, 0.9F}, {0.2F, 0.4F, 0.8F}, {0.5F, 0.4F, 0.3F}, {0.4F, 0.5F, 0.2F}, {0.8F, 0.3F, 0.3F}, {0.1F, 0.1F, 0.1F}};

	/**
	 * Used to control movement as well as wool regrowth. Set to 40 on handleHealthUpdate and counts down with each
	 * tick.
	 */
	private int hideTimer;

	/** The eat grass AI task for this mob. */
	private EntityAIMetHide aiMetHide = new EntityAIMetHide(this);

	public EntityMet(World par1World)
	{
		super(par1World);
		this.texture = "/zornco/megax/textures/MetHat.png";
		this.setSize(0.75F, 0.75F);
		float var2 = 0.23F;
		this.moveSpeed = 0.3F;
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		//this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(2, this.aiMetHide);
		this.tasks.addTask(3, new EntityAITempt(this, 0.25F, Item.ingotGold.itemID, false));
		this.tasks.addTask(4, new EntityAIBulletAttack(this, this.moveSpeed, 1, 60));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
		this.tasks.addTask(5, new EntityAIFollowOwner(this, this.moveSpeed, 10.0F, 2.0F));
		this.tasks.addTask(6, new EntityAIMate(this, var2));
		this.tasks.addTask(7, new EntityAIWander(this, var2));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
		//this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntityAnimal.class, 16.0F, 200, false));
		this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));

	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	protected boolean isAIEnabled()
	{
		return true;
	}
	/**
	 * Sets the active target the Task system uses for tracking
	 */
	public void setAttackTarget(EntityLiving par1EntityLiving)
	{
		super.setAttackTarget(par1EntityLiving);

		if (par1EntityLiving instanceof EntityPlayer)
		{
			//this.setAngry(true);
		}
	}
	/**
	 * main AI tick function, replaces updateEntityActionState
	 */
	protected void updateAITick()
	{
		this.dataWatcher.updateObject(21, Integer.valueOf(this.getHealth()));
	}
	protected void updateAITasks()
	{
		this.hideTimer = this.aiMetHide.getHide();
		super.updateAITasks();
	}
	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		Entity var3 = par1DamageSource.getEntity();
		if(isSitting()||isHiding())
		{
			worldObj.playSoundAtEntity(this, "random.wood click", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
			return false;
		}

		this.aiSit.setSitting(false);
		if (var3 != null && !(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow))
		{
			par2 = (par2 + 1) / 2;
		}
		// System.out.println(par2 + "b");

		return super.attackEntityFrom(par1DamageSource, par2);
	}
	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	public void onLivingUpdate()
	{
		if (this.worldObj.isRemote)
		{
			this.hideTimer = Math.max(0, this.hideTimer - 1);
		}

		super.onLivingUpdate();
	}

	public int getMaxHealth()
	{
		return this.isTamed() ? 20 : 8;
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(20, new Byte((byte)0));
		this.dataWatcher.addObject(21, new Integer(this.getHealth()));
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	protected void dropFewItems(boolean par1, int par2)
	{
		if (!this.getIsHatNotWorn())
		{
			//this.entityDropItem(new ItemStack(Block.cloth.blockID, 1, this.getMetHatType()), 0.0F);
		}
		int i = getDropItemId();
		if(i > 0)
			this.dropItem(i, 1);
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	protected int getDropItemId()
	{
		int i = rand.nextInt(10)+1;
		switch(i)
		{
		case 3:
			return MegaX.healthBit.itemID;
		case 4:
			return MegaX.healthByte.itemID;
		/*
		case 7:
			return MegaX.energyBit.shiftedIndex;
		case 8:
			return MegaX.energyByte.shiftedIndex;
		case 9:
			return MegaX.extraMan.shiftedIndex;*/
		default:
			return 0;
		
		}
	}

	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte par1)
	{
		if (par1 == 10)
		{
			this.hideTimer = 40;
		}
		else
		{
			super.handleHealthUpdate(par1);
		}
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	 */
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();

		if (this.isTamed())
		{
			if (isWheat(var2))
			{
				if (this.dataWatcher.getWatchableObjectInt(21) < 20)
				{
					if (!par1EntityPlayer.capabilities.isCreativeMode)
					{
						--var2.stackSize;
					}

					this.heal(5);

					if (var2.stackSize <= 0)
					{
						par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
					}
					//feed me?
							return true;
				}
			}
			//make me sit
			if (par1EntityPlayer.username.equalsIgnoreCase(this.getOwnerName()) && !this.worldObj.isRemote && !this.isWheat(var2))
			{
				this.aiSit.setSitting(!this.isSitting());
				this.isJumping = false;
				this.setPathToEntity((PathEntity)null);
			}
		}
		else if (var2 != null && var2.itemID == Item.goldNugget.itemID && !this.isHiding())
		{
			if (!par1EntityPlayer.capabilities.isCreativeMode)
			{
				--var2.stackSize;
			}

			if (var2.stackSize <= 0)
			{
				par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
			}

			if (!this.worldObj.isRemote)
			{
				if (this.rand.nextInt(3) == 0)
				{
					this.setTamed(true);
					this.setPathToEntity((PathEntity)null);
					this.setAttackTarget((EntityLiving)null);
					this.aiMetHide.enable(false);
					this.aiMetHide.setHiding(false);
					this.aiSit.setSitting(true);
					this.setEntityHealth(20);
					this.setOwner(par1EntityPlayer.username);
					this.playTameEffect(true);
					this.worldObj.setEntityState(this, (byte)7);
				}
				else
				{
					this.playTameEffect(false);
					this.worldObj.setEntityState(this, (byte)6);
				}
			}

			return true;
		}

		return super.interact(par1EntityPlayer);
	}
	public boolean isHiding()
	{
		return this.aiMetHide.getHide() > 0;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("HatWorn", this.getIsHatNotWorn());
		par1NBTTagCompound.setByte("Type", (byte)this.getMetHatType());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		this.setIsHatWorn(par1NBTTagCompound.getBoolean("HatWorn"));
		this.setMetHatType(par1NBTTagCompound.getByte("Type"));
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 *//*
    protected String getLivingSound()
    {
        return "mob.sheep";
    }

	  *//**
	  * Returns the sound this mob makes when it is hurt.
	  *//*
    protected String getHurtSound()
    {
        return "mob.sheep";
    }

	   *//**
	   * Returns the sound this mob makes on death.
	   *//*
    protected String getDeathSound()
    {
        return "mob.sheep";
    }*/
	/**
	 * Checks if the parameter is an wheat item.
	 */
	public boolean isWheat(ItemStack par1ItemStack)
	{
		return par1ItemStack != null && par1ItemStack.itemID == Item.ingotGold.itemID;
	}
	public int getMetHatType()
	{
		return this.dataWatcher.getWatchableObjectByte(20) & 15;
	}

	public void setMetHatType(int newType)
	{
		byte type = this.dataWatcher.getWatchableObjectByte(20);
		this.dataWatcher.updateObject(20, Byte.valueOf((byte)(type & 240 | newType & 5)));
	}

	/**
	 * returns true if a sheeps wool has been sheared
	 */
	public boolean getIsHatNotWorn()
	{
		return (this.dataWatcher.getWatchableObjectByte(20) & 16) != 0;
	}

	/**
	 * make a sheep sheared if set to true
	 */
	public void setIsHatWorn(boolean par1)
	{
		byte var2 = this.dataWatcher.getWatchableObjectByte(20);

		if (par1)
		{
			this.dataWatcher.updateObject(20, Byte.valueOf((byte)(var2 | 16)));
		}
		else
		{
			this.dataWatcher.updateObject(20, Byte.valueOf((byte)(var2 & -17)));
		}
	}

	/**
	 * This method is called when a sheep spawns in the world to select the color of sheep fleece.
	 */
	public static int getRandomHatType(Random par0Random)
	{
		int randColor = par0Random.nextInt(100);
		return randColor < 5 ? 15 : (randColor < 10 ? 7 : (randColor < 15 ? 8 : (randColor < 18 ? 12 : (par0Random.nextInt(500) == 0 ? 6 : 0))));
	}

	/**
	 * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
	 */
	public EntityAnimal spawnBabyAnimal(EntityAnimal par1EntityAnimal)
	{
		EntityMet metMale = (EntityMet)par1EntityAnimal;
		EntityMet metBaby = new EntityMet(this.worldObj);

		if (this.rand.nextBoolean())
		{
			metBaby.setMetHatType(this.getMetHatType());
		}
		else
		{
			metBaby.setMetHatType(metMale.getMetHatType());
		}

		return metBaby;
	}
	@Override
	public void onUpdate()
	{

		boolean hat = getIsHatNotWorn();
		int halfhp = (getMaxHealth()/2)+1;
		if(health < halfhp)
			this.setIsHatWorn(true);
		else if (health >= halfhp)
			this.setIsHatWorn(false);
		super.onUpdate();
	}
	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	public boolean canMateWith(EntityAnimal par1EntityAnimal)
	{
		if (par1EntityAnimal == this)
		{
			return false;
		}
		else if (!this.isTamed())
		{
			return false;
		}
		else if (!(par1EntityAnimal instanceof EntityMet))
		{
			return false;
		}
		else
		{
			EntityMet var2 = (EntityMet)par1EntityAnimal;
			return !var2.isTamed() ? false : (var2.isSitting() ? false : this.isInLove() && var2.isInLove());
		}
	}

	@Override
	public EntityAgeable createChild(EntityAgeable var1) {
		return this.spawnBabyAnimal(var1);
	}

	public EntityMet spawnBabyAnimal(EntityAgeable par1EntityAgeable)
    {
        return new EntityMet(this.worldObj);
    }
}
