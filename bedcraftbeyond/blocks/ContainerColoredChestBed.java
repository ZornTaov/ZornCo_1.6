package zornco.bedcraftbeyond.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerColoredChestBed extends Container {
    private EntityPlayer player;
    private IInventory chest;
    private int numRows;
    
	public ContainerColoredChestBed(IInventory playerInventory, IInventory chestInventory)
    {
        chest = chestInventory;
        player = ((InventoryPlayer) playerInventory).player;
        chestInventory.openChest();
        layoutContainer(playerInventory, chestInventory);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return chest.isUseableByPlayer(player);
    }
    public int getSizeInventory() {
		return 9;
	}
    @Override
    public ItemStack transferStackInSlot(EntityPlayer p, int i)
    {

        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(i);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (i < this.numRows * 9)
            {
                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public void onContainerClosed(EntityPlayer entityplayer)
    {
        super.onContainerClosed(entityplayer);
        chest.closeChest();
    }

    protected void layoutContainer(IInventory playerInventory, IInventory chestInventory)
    {
        this.numRows = chestInventory.getSizeInventory() / 9;
        int i = (this.numRows - 4) * 18;
        int j;
        int k;

        byte b0 = 51;

        for (i = 0; i < chestInventory.getSizeInventory(); ++i)
        {
            this.addSlotToContainer(new Slot(chestInventory, i, 8 + i * 18, 36));
        }

        for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, i * 18 + 16 + b0));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 58 + 16 + b0));
        }
    }

    public EntityPlayer getPlayer()
    {
        return player;
    }
}
