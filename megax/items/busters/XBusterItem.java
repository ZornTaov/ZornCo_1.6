package zornco.megax.items.busters;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import zornco.megax.MegaX;
import zornco.megax.bullets.EntityBusterBullet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class XBusterItem extends Item
{
	int c;
	private Minecraft mc;
	private FontRenderer fr;

	public XBusterItem(int i, int col)
	{
		super(i);
		this.maxStackSize = 1;
		this.setCreativeTab(MegaX.megaXTab);
		this.c = col;
	}
	@SideOnly(Side.CLIENT)
	int getIndex() {
		return this.iconIndex;
	}
	@SideOnly(Side.CLIENT)
	public String getTextureFile() {
		return "/zornco/megax/textures/XBusterDetailed.png";
	}
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
	@SideOnly(Side.CLIENT)
	/**
	 * Gets an icon index based on an item's damage value and the given render pass
	 */
	public int getIconFromDamageForRenderPass(int par1, int par2)
	{
		return par2 == 1 ? this.iconIndex + 1 : super.getIconFromDamageForRenderPass(par1, par2);
	}
	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		//return MegaX.busterAction;
		return EnumAction.bow;
	}
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
//		par3EntityPlayer.setEating(false);
		return par1ItemStack;
	}
	public int ticksCharged = 0;
	public boolean stopCount = false;
	@SideOnly(Side.CLIENT)
    @Override
	/**
     * Called each tick while using an item.
     * @param stack The Item being used
     * @param player The Player using the item
     * @param count The amount of time in tick the item has been used for continuously
     */
    public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count) 
    {
    	if(player.isEating())
    		System.out.println(count);
    		player.setEating(false);
    }
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		int useTime = this.getMaxItemUseDuration(par1ItemStack) - par4;

		ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer, par1ItemStack, useTime);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
		{
			return; 
		}

		float chargeLevel = (float)event.charge / 20.0F;
		chargeLevel = (chargeLevel * chargeLevel + chargeLevel * 2.0F) / 3.0F;

		if ((double)chargeLevel < 0.01D)
		{
			return;  //don't fire, didn't hold it long enough
		}

		if (chargeLevel > 2.0F)
		{
			chargeLevel = 2.0F; // max charge
		}

		EntityBusterBullet lemon = new EntityBusterBullet(par2World, par3EntityPlayer); // entity shot

		if (chargeLevel >= 0.50F && chargeLevel < 2.0F)
		{
			lemon.setTypeOfAttack(1);
		}
		if (chargeLevel == 2.0F)
		{
			lemon.setTypeOfAttack(2); //crit attack
		}
		lemon.canBePickedUp = 2;
		
		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + chargeLevel * 0.5F);


		if (!par2World.isRemote)
		{
			par2World.spawnEntityInWorld(lemon);
		}

	}
	@Override
	public ItemStack onFoodEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		return itemstack;
	}

	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 72000;
	}

	/**
	 * Return whether the specified armor ItemStack has a color.
	 */
	public boolean hasColor(ItemStack par1ItemStack)
	{
		return !par1ItemStack.hasTagCompound() ? 
				false : (!par1ItemStack.getTagCompound().hasKey("display") ? 
						false : par1ItemStack.getTagCompound().getCompoundTag("display").hasKey("color")
						);
	}
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		if (par2 > 0)
		{
			return 16777215;
		}
		else
		{
			int var3 = this.getColor(par1ItemStack);

			if (var3 < 0)
			{
				var3 = 16777215;
			}

			return var3;
		}
	}
	public int getColorFromDamage(int par1)
	{
		return par1;
	}
	/**
	 * Return the color for the specified armor ItemStack.
	 */
	public int getColor(ItemStack par1ItemStack)
	{
		NBTTagCompound var2 = par1ItemStack.getTagCompound();

		if (var2 == null)
		{
			return 0x0060f8;
		}
		else
		{
			NBTTagCompound var3 = var2.getCompoundTag("display");
			return var3 == null ? 0x0060f8 : (var3.hasKey("color") ? var3.getInteger("color") : 0x0060f8);
		}

	}
	/**
	 * Remove the color from the specified armor ItemStack.
	 */
	public void removeColor(ItemStack par1ItemStack)
	{
		NBTTagCompound var2 = par1ItemStack.getTagCompound();

		if (var2 != null)
		{
			NBTTagCompound var3 = var2.getCompoundTag("display");

			if (var3.hasKey("color"))
			{
				var3.removeTag("color");
			}
		}

	}

	public void func_82813_b(ItemStack par1ItemStack, int par2)
	{
		NBTTagCompound var3 = par1ItemStack.getTagCompound();

		if (var3 == null)
		{
			var3 = new NBTTagCompound();
			par1ItemStack.setTagCompound(var3);
		}

		NBTTagCompound var4 = var3.getCompoundTag("display");

		if (!var3.hasKey("display"))
		{
			var3.setCompoundTag("display", var4);
		}

		var4.setInteger("color", par2);

	}
}
