package zornco.reploidcraft.items.armors;

import java.util.ArrayList;
import java.util.List;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseItemUtils;
import net.machinemuse.utils.ElectricItemUtils;
import net.machinemuse.utils.MuseStringUtils;
import net.machinemuse.powersuits.common.Config;
import net.machinemuse.powersuits.item.ItemElectricArmor;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraftforge.common.IArmorTextureProvider;
import net.minecraftforge.common.ISpecialArmor;
import zornco.reploidcraft.ReploidCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemReploidArmorBase extends ItemElectricArmor implements ISpecialArmor, //
IModularItem, IArmorTextureProvider {


	/** The EnumArmorMaterial used for this ItemArmor */
	private final EnumArmorMaterial material;
	@SideOnly(Side.CLIENT)
	private Icon theIcon;
	public ItemReploidArmorBase(int par1, EnumArmorMaterial par2EnumArmorMaterial,
			int par3, int par4) {
		super(par1, par2EnumArmorMaterial, par3, par4);
		this.material = par2EnumArmorMaterial;
		this.setCreativeTab(ReploidCraft.reploidTab);

	}
	@SideOnly(Side.CLIENT)
	public String getTextureFile()
	{
		return "/zornco/ReploidCraft/textures/armors_0.png";
	}
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
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
				var3 = 16777215;
			}

			return var3;
		}
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
	@SideOnly(Side.CLIENT)
    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public Icon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return par2 == 1 ?theIcon : this.itemIcon;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
    	itemIcon = par1IconRegister.registerIcon("ReploidCraft:"+this.getUnlocalizedName().substring(5));
        this.theIcon = par1IconRegister.registerIcon("ReploidCraft:"+this.getUnlocalizedName().substring(5)+"_overlay");
    }

	@Override
	public String getArmorTextureFile(ItemStack itemstack)
	{
		if (itemstack.itemID == ReploidCraft.reploidBelt.itemID) {
			return "/mods/ReploidCraft/textures/models/ReploidBasic_2.png";
		}
		return "/mods/ReploidCraft/textures/models/ReploidBasic_1.png";
	}
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List currentTipList, boolean advancedToolTips) {
		MuseCommonStrings.addInformation(stack, player, currentTipList, advancedToolTips);
	}

	public String formatInfo(String string, double value) {
		return string + "\t" + MuseStringUtils.formatNumberShort(value);
	}

	@Override
	public List<String> getLongInfo(EntityPlayer player, ItemStack stack) {
		List<String> info = new ArrayList();
		NBTTagCompound itemProperties = MuseItemUtils.getMuseItemTag(stack);
		info.add("Detailed Summary");
		info.add(formatInfo("Armor", getArmorDouble(player, stack)));
		info.add(formatInfo("Energy Storage", getMaxJoules(stack)) + "J");
		info.add(formatInfo("Weight", MuseCommonStrings.getTotalWeight(stack)) + "g");
		return info;
	}
	@Override
	public ArmorProperties getProperties(EntityLiving player, ItemStack armor, DamageSource source, double damage, int slot) {
		// Order in which this armor is assessed for damage. Higher(?) priority
		// items take damage first, and if none spills over, the other items
		// take no damage.
		int priority = 1;

		double armorDouble;

		if (player instanceof EntityPlayer) {
			armorDouble = getArmorDouble((EntityPlayer) player, armor);
		} else {
			armorDouble = 2;
		}

		// How much of incoming damage is absorbed by this armor piece.
		// 1.0 = absorbs all damage
		// 0.5 = 50% damage to item, 50% damage carried over
		double absorbRatio = 0.04 * armorDouble;

		// Maximum damage absorbed by this piece. Actual damage to this item
		// will be clamped between (damage * absorbRatio) and (absorbMax). Note
		// that a player has 20 hp (1hp = 1 half-heart)
		int absorbMax = (int) armorDouble * 75; // Not sure why this is
												// necessary but oh well

		return new ArmorProperties(priority, absorbRatio, absorbMax);
	}
	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return (int) getArmorDouble(player, armor);
	}

	public double getArmorDouble(EntityPlayer player, ItemStack stack) {
		double totalArmor = 0;
		NBTTagCompound props = MuseItemUtils.getMuseItemTag(stack);

		double energy = ElectricItemUtils.getPlayerEnergy(player);
		double physArmor = ModuleManager.computeModularProperty(stack, MuseCommonStrings.ARMOR_VALUE_PHYSICAL);
		double enerArmor = ModuleManager.computeModularProperty(stack, MuseCommonStrings.ARMOR_VALUE_ENERGY);
		double enerConsum = ModuleManager.computeModularProperty(stack, MuseCommonStrings.ARMOR_ENERGY_CONSUMPTION);

		totalArmor += physArmor;

		if (energy > enerConsum) {
			totalArmor += enerArmor;
		}
		// Make it so each armor piece can only contribute reduction up to the configured amount.
		// Defaults to 6 armor points, or 24% reduction.
		totalArmor = Math.min(Config.getMaximumArmorPerPiece(), totalArmor);
		return totalArmor;
	}
	@Override
	public void damageArmor(EntityLiving entity, ItemStack stack, DamageSource source, int damage, int slot) {
		NBTTagCompound itemProperties = MuseItemUtils.getMuseItemTag(stack);
		double enerConsum = ModuleManager.computeModularProperty(stack, MuseCommonStrings.ARMOR_ENERGY_CONSUMPTION);
		double drain = enerConsum * damage;
		if (entity instanceof EntityPlayer) {
			ElectricItemUtils.drainPlayerEnergy((EntityPlayer) entity, drain);
		} else {
			drainEnergyFrom(stack, drain);
		}
	}
}
