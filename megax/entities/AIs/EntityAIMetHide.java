package zornco.megax.entities.AIs;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;
import zornco.megax.entities.EntityMet;

public class EntityAIMetHide extends EntityAIBase {

	private EntityMet theEntity;

    /** If the EntityTameable is hiding. */
    private boolean isHiding = false;
    /** A decrementing tick used for the sheep's head offset and animation. */
    int hide = 0;
	private World theWorld;

	private boolean enabled;

    public EntityAIMetHide(EntityMet par1EntityMet)
    {
        this.theEntity = par1EntityMet;
        this.theWorld = par1EntityMet.worldObj;
        this.setMutexBits(5);
    	this.enable(true);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.theEntity.isTamed()
        		||this.theEntity.isInWater()
        		||!this.theEntity.onGround
        		||this.theEntity.getRNG().nextInt(50) != 0
        		||!this.theEntity.getIsHatWorn()
        	)
        {
            return false;
        }
        else
        {
            return enabled;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
    	if(enabled)
    	{
    		this.theEntity.getNavigator().clearPathEntity();
    		this.hide = theWorld.rand.nextInt(120);
    		this.theWorld.setEntityState(this.theEntity, (byte)10);
    		this.theEntity.setSitting(true);
    	}
    }
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return this.hide > 0 && enabled;
    }
    public int getHide()
    {
        return this.hide;
    }
    /**
     * Resets the task
     */
    public void resetTask()
    {
    	this.hide = 0;
        this.theEntity.setSitting(false);
    }

    /**
     * Sets the hiding flag.
     */
    public void setHiding(boolean par1)
    {
        this.isHiding = par1;
    }

	public void enable(boolean b) {
		this.enabled = b; 
	}
	public void updateTask()
    {
        this.hide = Math.max(0, this.hide - 1);
    }
}
