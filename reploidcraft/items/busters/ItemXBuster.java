package zornco.reploidcraft.items.busters;

import java.util.ArrayList;
import java.util.List;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.IPowerModule;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.MuseItemUtils;
import net.machinemuse.api.moduletrigger.IRightClickModule;
import net.machinemuse.general.MuseStringUtils;
import net.machinemuse.powersuits.item.ItemElectricTool;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import zornco.reploidcraft.RepliodCraft;
import zornco.reploidcraft.items.IKeyBound;
import zornco.reploidcraft.sounds.Sounds;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemXBuster extends ItemElectricTool implements IKeyBound, IModularItem
{
	public int color;
	public boolean enhanced = false;
	@SideOnly(Side.CLIENT)
	private Icon iconBusterOverlay;

	public ItemXBuster(int i, int col, boolean enhance)
	{
		super(i, 0, EnumToolMaterial.EMERALD, new Block[0]);
		this.maxStackSize = 1;
		this.setCreativeTab(RepliodCraft.reploidTab);
		this.color = col;
		this.enhanced = enhance;
	}
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
	@Override
	@SideOnly(Side.CLIENT)
    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public Icon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return par2 == 1 && this.enhanced ? this.iconBusterOverlay : this.iconIndex;
    }

    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
    	iconIndex =  par1IconRegister.registerIcon("ReploidCraft:"+this.getUnlocalizedName().substring(5));
        if(this.enhanced )this.iconBusterOverlay = par1IconRegister.registerIcon("ReploidCraft:"+this.getUnlocalizedName().substring(5)+"_overlay");
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		//return ReploidCraft.busterAction;
		return EnumAction.bow;
	}
	@Override
	/**
	 * Called when the right click button is pressed
	 */
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		for (IRightClickModule module : ModuleManager.getRightClickModules()) {
			if (module.isValidForItem(itemStack, player) && MuseItemUtils.itemHasActiveModule(itemStack, module.getName())) {
				((IRightClickModule) module).onRightClick(player, world, itemStack);
			}
		}
		return itemStack;
	}
	/*public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}*/
	@Override
	/**
	 * Called each tick while using an item.
	 * @param stack The Item being used
	 * @param player The Player using the item
	 * @param count The amount of time in tick the item has been used for continuously
	 */
	public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count) 
	{
		//System.out.println(count);
		if(count == getMaxItemUseDuration(stack))
			player.worldObj.playSoundAtEntity(player, Sounds.CHARGEUP, 1.0F, 1.0F);
		else if(count <= getMaxItemUseDuration(stack)-40 && count%40 == 0)
			player.worldObj.playSoundAtEntity(player, Sounds.CHARGECONT, 1.0F, 1.0F);
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int slot, boolean isSelected) 
	{

	}

	/*public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
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

	}*/
	public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int par4) {
		String mode = MuseItemUtils.getActiveMode(itemStack);
		IPowerModule module = ModuleManager.getModule(mode);
		if (module instanceof IRightClickModule) {
			((IRightClickModule) module).onPlayerStoppedUsing(itemStack, world, player, par4);
		}
	}
	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		return itemstack;
	}

	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 72000;
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		String mode = MuseItemUtils.getActiveMode(itemStack);
		IPowerModule module = ModuleManager.getModule(mode);
		if (module instanceof IRightClickModule) {
			((IRightClickModule) module).onItemUse(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
			return false;
		}
		return false;
	}
	
    public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player)
    {
        return true;
    }
    
    public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
    	String mode = MuseItemUtils.getActiveMode(itemStack);
		IPowerModule module = ModuleManager.getModule(mode);
		if (module instanceof IRightClickModule) {
			((IRightClickModule) module).onItemUseFirst(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
			return false;
		}
		return false;
    }
    
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        return true;
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
				var3 = 0x0060f8;
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
	@Override
	public void doKeyBindingAction(EntityPlayer thePlayer, ItemStack itemStack, String keyBinding) {

		if (keyBinding.equals("key.menu")) {
			openWeaponMenu(thePlayer);
		}
		else if (keyBinding.equals("key.charge")) {
			if (!thePlayer.isSneaking()) {
				if (getCurrentWeaponType(itemStack) == 8) {
					//failed
					//thePlayer.worldObj.playSoundAtEntity(thePlayer, Sounds.CHARGE_FAIL, 0.5F, 0.5F + (0.5F * (getCharge(itemStack) * 1.0F / maxChargeLevel)));
				}
				else {
					nextWeaponType(itemStack);
					thePlayer.sendChatToPlayer("" + num);
					//thePlayer.worldObj.playSoundAtEntity(thePlayer, Sounds.CHARGE_UP, 0.5F, 0.5F + (0.5F * (getCharge(itemStack) * 1.0F / maxChargeLevel)));
				}
			}
			else {
				if (getCurrentWeaponType(itemStack) == 0) {
					//failed
					//thePlayer.worldObj.playSoundAtEntity(thePlayer, Sounds.CHARGE_FAIL, 0.5F, 0.5F + (0.5F * (getCharge(itemStack) * 1.0F / maxChargeLevel)));
				}
				else {
					previousWeaponType(itemStack);
					thePlayer.sendChatToPlayer("" + num);
					//thePlayer.worldObj.playSoundAtEntity(thePlayer, Sounds.CHARGE_DOWN, 0.5F, 1.0F - (0.5F - (0.5F * (getCharge(itemStack) * 1.0F / maxChargeLevel))));
				}
			}
		}

	}
	private int num = 1;
	private void previousWeaponType(ItemStack itemStack) {
		num--;
		System.out.println(num);
	}
	private void openWeaponMenu(EntityPlayer thePlayer) {

	}
	private void nextWeaponType(ItemStack itemStack) {
		num++;
		System.out.println(num);
	}
	private int getCurrentWeaponType(ItemStack itemStack) {
		
		return num;
	}
	public static String formatInfo(String string, double value) {
		return string + "\t" + MuseStringUtils.formatNumberShort(value);
	}
	@Override
	public List<String> getLongInfo(EntityPlayer player, ItemStack stack) {
		List<String> info = new ArrayList();
		NBTTagCompound itemProperties = MuseItemUtils.getMuseItemTag(stack);
		info.add("Detailed Summary");
		info.add(formatInfo("Energy Storage", getMaxJoules(stack)) + "J");
		info.add(formatInfo("Weight", MuseCommonStrings.getTotalWeight(stack)) + "g");
		return info;
	}
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List currentTipList, boolean advancedToolTips) {
		MuseCommonStrings.addInformation(stack, player, currentTipList, advancedToolTips);
	}
}
