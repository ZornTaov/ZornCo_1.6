package zornco.reploidcraft.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class PlatformPathPoint implements Comparable
{
    public double posX;

    /** the y coordinate */
    public double posY;

    /** the z coordinate */
    public double posZ;

	public float speed;
	
    /**
     * A NBTTagMap containing data about an ItemStack. Can only be used for non stackable items
     */
    public NBTTagCompound stackTagCompound;

    public PlatformPathPoint() {}

    public PlatformPathPoint(double x, double y, double z, float speed)
    {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.speed = speed;
    }

    public PlatformPathPoint(PlatformPathPoint par1platformPathPoint)
    {
        this.posX = par1platformPathPoint.posX;
        this.posY = par1platformPathPoint.posY;
        this.posZ = par1platformPathPoint.posZ;
    }

    public boolean equals(Object par1Obj)
    {
        if (!(par1Obj instanceof PlatformPathPoint))
        {
            return false;
        }
        else
        {
            PlatformPathPoint var2 = (PlatformPathPoint)par1Obj;
            return this.posX == var2.posX && this.posY == var2.posY && this.posZ == var2.posZ;
        }
    }

    /**
     * Compare the coordinate with another coordinate
     */
    public int comparePlatformPathPoint(PlatformPathPoint par1PlatformPathPoint)
    {
        return (int)(this.posY == par1PlatformPathPoint.posY ? (this.posZ == par1PlatformPathPoint.posZ ? this.posX - par1PlatformPathPoint.posX : this.posZ - par1PlatformPathPoint.posZ) : this.posY - par1PlatformPathPoint.posY);
    }

    public void set(float par1, float par2, float par3, float speed)
    {
        this.posX = par1;
        this.posY = par2;
        this.posZ = par3;
        this.speed = speed;
    }

    /**
     * Returns the squared distance between this coordinates and the coordinates given as argument.
     */
    public double getDistanceSquared(double posX2, double posY2, double posZ2)
    {
    	double var4 = this.posX - posX2;
    	double var5 = this.posY - posY2;
    	double var6 = this.posZ - posZ2;
        return (var4 * var4 + var5 * var5 + var6 * var6);
    }

    /**
     * Return the squared distance between this coordinates and the ChunkCoordinates given as argument.
     */
    public double getDistanceSquaredToChunkCoordinates(PlatformPathPoint par1ChunkCoordinates)
    {
        return this.getDistanceSquared(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ);
    }

    public int compareTo(Object par1Obj)
    {
        return this.comparePlatformPathPoint((PlatformPathPoint)par1Obj);
    }
    /**
     * Write the stack fields to a NBT object. Return the new NBT object.
     */
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setDouble("X", (float)this.posX);
        par1NBTTagCompound.setDouble("Y", (float)this.posY);
        par1NBTTagCompound.setDouble("Z", (float)this.posZ);
        par1NBTTagCompound.setFloat("speed", (float)this.posZ);

        if (this.stackTagCompound != null)
        {
            par1NBTTagCompound.setTag("tag", this.stackTagCompound);
        }

        return par1NBTTagCompound;
    }

    /**
     * Read the stack fields from a NBT object.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        this.posX = par1NBTTagCompound.getDouble("X");
        this.posY = par1NBTTagCompound.getDouble("Y");
        this.posZ = par1NBTTagCompound.getDouble("Z");
        this.speed = par1NBTTagCompound.getFloat("speed");

        if (par1NBTTagCompound.hasKey("tag"))
        {
            this.stackTagCompound = par1NBTTagCompound.getCompoundTag("tag");
        }
    }
    
    public static PlatformPathPoint loadPointFromNBT(NBTTagCompound par0NBTTagCompound)
    {
    	PlatformPathPoint var1 = new PlatformPathPoint();
        var1.readFromNBT(par0NBTTagCompound);
        return var1;
    }
}
