package zornco.bedcraftbeyond.blocks;

import java.util.Iterator;
import java.util.List;

import zornco.bedcraftbeyond.BedCraftBeyond;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;

public class TileColoredChestBed extends TileColoredBed implements IInventory {
    private int ticksSinceSync = -1;
	public ItemStack[] chestContents;
	public float prevLidAngle;
	public float lidAngle;
	private int numUsingPlayers;
	private boolean inventoryTouched;
	private boolean hadStuff;
	public String ownerName = "";

	public TileColoredChestBed() {
		super();
		this.chestContents = new ItemStack[getSizeInventory()];
	}
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setString("OwnerName", ownerName);
		NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < chestContents.length; i++)
        {
            if (chestContents[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                chestContents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        nbttagcompound.setTag("Items", nbttaglist);
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		ownerName = nbttagcompound.getString("OwnerName");
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        chestContents = new ItemStack[getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 0xff;
            if (j >= 0 && j < chestContents.length)
            {
                chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		 if (worldObj != null && !this.worldObj.isRemote && this.numUsingPlayers != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0)
	        {
	            this.numUsingPlayers = 0;
	            float var1 = 5.0F;
	            List var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB(this.xCoord - var1, this.yCoord - var1, this.zCoord - var1, this.xCoord + 1 + var1, this.yCoord + 1 + var1, this.zCoord + 1 + var1));
	            Iterator var3 = var2.iterator();

	            while (var3.hasNext())
	            {
	                EntityPlayer var4 = (EntityPlayer)var3.next();

	                if (var4.openContainer instanceof ContainerColoredChestBed)
	                {
	                    ++this.numUsingPlayers;
	                }
	            }
	        }

	        if (worldObj != null && !worldObj.isRemote && ticksSinceSync < 0)
	        {
	            worldObj.addBlockEvent(xCoord, yCoord, zCoord, BedCraftBeyond.bedBlock.blockID, 3, ((numUsingPlayers << 3) & 0xF8));
	        }
	        if (!worldObj.isRemote && inventoryTouched)
	        {
	            inventoryTouched = false;
	        }

	        this.ticksSinceSync++;
	        prevLidAngle = lidAngle;
	        float f = 0.1F;
	        if (numUsingPlayers > 0 && lidAngle == 0.0F)
	        {
	            double d = xCoord + 0.5D;
	            double d1 = zCoord + 0.5D;
	            worldObj.playSoundEffect(d, yCoord + 0.5D, d1, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
	        }
	        if (numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0 && lidAngle < 1.0F)
	        {
	            float f1 = lidAngle;
	            if (numUsingPlayers > 0)
	            {
	                lidAngle += f;
	            }
	            else
	            {
	                lidAngle -= f;
	            }
	            if (lidAngle > 1.0F)
	            {
	                lidAngle = 1.0F;
	            }
	            float f2 = 0.5F;
	            if (lidAngle < f2 && f1 >= f2)
	            {
	                double d2 = xCoord + 0.5D;
	                double d3 = zCoord + 0.5D;
	                worldObj.playSoundEffect(d2, yCoord + 0.5D, d3, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
	            }
	            if (lidAngle < 0.0F)
	            {
	                lidAngle = 0.0F;
	            }
	        }
	}
	@Override
	public boolean receiveClientEvent(int par1, int par2)
    {
        if (par1 == 1)
        {
            this.numUsingPlayers = par2;
            return true;
        }
        else
        {
            return super.receiveClientEvent(par1, par2);
        }
    }
	
	public ItemStack[] getContents() {
	    return chestContents;
	}
	@Override
	public int getSizeInventory() {
		return 9;
	}
	@Override
	public ItemStack getStackInSlot(int i) {
	    inventoryTouched = true;
	    return chestContents[i];
	}
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (chestContents[i] != null)
	    {
	        if (chestContents[i].stackSize <= j)
	        {
	            ItemStack itemstack = chestContents[i];
	            chestContents[i] = null;
	            onInventoryChanged();
	            return itemstack;
	        }
	        ItemStack itemstack1 = chestContents[i].splitStack(j);
	        if (chestContents[i].stackSize == 0)
	        {
	            chestContents[i] = null;
	        }
	        onInventoryChanged();
	        return itemstack1;
	    }
	    else
	    {
	        return null;
	    }
	}
	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
	    if (this.chestContents[i] != null)
	    {
	        ItemStack var2 = this.chestContents[i];
	        this.chestContents[i] = null;
	        return var2;
	    }
	    else
	    {
	        return null;
	    }
	}
	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		chestContents[i] = itemstack;
	    if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
	    {
	        itemstack.stackSize = getInventoryStackLimit();
	    }
	    onInventoryChanged();
	}
	@Override
	public String getInvName() {
		if (ownerName != "")
			return ownerName + "'s Drawers";
		return "Drawers";
	}
	@Override
	public boolean isInvNameLocalized() {
		return false;
	}
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
	    if (worldObj == null)
	    {
	        return true;
	    }
	    if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
	    {
	        return false;
	    }
	    //if (entityplayer.username != ownerName && ownerName != "")
	    //	return false;
	    return entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64D;
	
	}
	@Override
	public void openChest() {
	    if (worldObj == null) return;
	    numUsingPlayers++;
	    worldObj.addBlockEvent(xCoord, yCoord, zCoord, BedCraftBeyond.bedBlock.blockID, 1, numUsingPlayers);
	}
	@Override
	public void closeChest() {
	    if (worldObj == null) return;
	    numUsingPlayers--;
	    worldObj.addBlockEvent(xCoord, yCoord, zCoord, BedCraftBeyond.bedBlock.blockID, 1, numUsingPlayers);
	}
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

}
