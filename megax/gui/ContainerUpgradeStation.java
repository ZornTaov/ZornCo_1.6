package zornco.megax.gui;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import zornco.megax.blocks.TileUpgradeStation;
import zornco.megax.items.ItemChip;
import zornco.megax.items.armors.ItemMegaXArmorBase;
import zornco.megax.items.busters.ItemXBuster;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerUpgradeStation extends Container
{
	private TileUpgradeStation furnace;
	private int lastCookTime = 0;
	private int lastBurnTime = 0;
	private int lastItemBurnTime = 0;

	public ContainerUpgradeStation(InventoryPlayer par1InventoryPlayer, TileUpgradeStation engine)
	{
		this.furnace = engine;
		int i = 0;
		this.addSlotToContainer(new SlotUpgradeStation(par1InventoryPlayer.player, engine, i++, 35, 17));
		for (int e = 0; e<3; e++) {
			this.addSlotToContainer(new Slot(engine, i++, e*18 +62, 21){
				@Override
				public boolean isItemValid(ItemStack stack){
					return stack != null && inventory.getStackInSlot(0) != null && stack.getItem() instanceof ItemChip;
				}
			});
		}
		for (int e = 0; e<8; e++) {
			this.addSlotToContainer(new Slot(engine, i++, e*18 +17, 52){
				@Override
				public boolean isItemValid(ItemStack stack){
					return stack != null && (inventory.getStackInSlot(0) != null && inventory.getStackInSlot(0).getItem() instanceof ItemXBuster) && stack.getItem() instanceof ItemChip;
				}
			});
		}
		int var3;

		for (var3 = 0; var3 < 3; ++var3)
		{
			for (int var4 = 0; var4 < 9; ++var4)
			{
				this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}

		for (var3 = 0; var3 < 9; ++var3)
		{
			this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
		}
	}

	public void addCraftingToCrafters(ICrafting par1ICrafting)
	{
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, this.furnace.upgradeStationProgress);
		par1ICrafting.sendProgressBarUpdate(this, 1, this.furnace.upgradeStationCharge);
		par1ICrafting.sendProgressBarUpdate(this, 2, this.furnace.currentItemCharge);
	}

	/**
	 * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
	 */
	 public void updateCraftingResults()
	{
		super.updateCraftingResults();

		for (int var1 = 0; var1 < this.crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting)this.crafters.get(var1);

			if (this.lastCookTime != this.furnace.upgradeStationProgress)
			{
				var2.sendProgressBarUpdate(this, 0, this.furnace.upgradeStationProgress);
			}

			if (this.lastBurnTime != this.furnace.upgradeStationCharge)
			{
				var2.sendProgressBarUpdate(this, 1, this.furnace.upgradeStationCharge);
			}

			if (this.lastItemBurnTime != this.furnace.currentItemCharge)
			{
				var2.sendProgressBarUpdate(this, 2, this.furnace.currentItemCharge);
			}
		}

		this.lastCookTime = this.furnace.upgradeStationProgress;
		this.lastBurnTime = this.furnace.upgradeStationCharge;
		this.lastItemBurnTime = this.furnace.currentItemCharge;
	}

	 @SideOnly(Side.CLIENT)
	 public void updateProgressBar(int par1, int par2)
	 {
		 if (par1 == 0)
		 {
			 this.furnace.upgradeStationProgress = par2;
		 }

		 if (par1 == 1)
		 {
			 this.furnace.upgradeStationCharge = par2;
		 }

		 if (par1 == 2)
		 {
			 this.furnace.currentItemCharge = par2;
		 }
	 }

	 public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	 {
		 return this.furnace.isUseableByPlayer(par1EntityPlayer);
	 }

	 /**
	  * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	  */
	 public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	 {
		 ItemStack var3 = null;
		 Slot var4 = (Slot)this.inventorySlots.get(par2);

		 if (var4 != null && var4.getHasStack())
		 {
			 ItemStack var5 = var4.getStack();
			 var3 = var5.copy();

			 if (par2 == 2)
			 {
				 if (!this.mergeItemStack(var5, 3, 39, true))
				 {
					 return null;
				 }

				 var4.onSlotChange(var5, var3);
			 }
			 else if (par2 != 1 && par2 != 0)
			 {
				 if (FurnaceRecipes.smelting().getSmeltingResult(var5) != null)
				 {
					 if (!this.mergeItemStack(var5, 0, 1, false))
					 {
						 return null;
					 }
				 }
				 else if (TileEntityFurnace.isItemFuel(var5))
				 {
					 if (!this.mergeItemStack(var5, 1, 2, false))
					 {
						 return null;
					 }
				 }
				 else if (par2 >= 3 && par2 < 30)
				 {
					 if (!this.mergeItemStack(var5, 30, 39, false))
					 {
						 return null;
					 }
				 }
				 else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(var5, 3, 30, false))
				 {
					 return null;
				 }
			 }
			 else if (!this.mergeItemStack(var5, 3, 39, false))
			 {
				 return null;
			 }

			 if (var5.stackSize == 0)
			 {
				 var4.putStack((ItemStack)null);
			 }
			 else
			 {
				 var4.onSlotChanged();
			 }

			 if (var5.stackSize == var3.stackSize)
			 {
				 return null;
			 }

			 var4.onPickupFromSlot(par1EntityPlayer, var5);
		 }

		 return var3;
	 }
}
