package zornco.megax.items.armors;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IArmorTextureProvider;
import zornco.megax.MegaX;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMegaXArmorBase extends ItemArmor implements IArmorTextureProvider{


	/** The EnumArmorMaterial used for this ItemArmor */
	private final EnumArmorMaterial material;
	public ItemMegaXArmorBase(int par1, EnumArmorMaterial par2EnumArmorMaterial,
			int par3, int par4) {
		super(par1, par2EnumArmorMaterial, par3, par4);
		this.material = par2EnumArmorMaterial;
		this.setCreativeTab(MegaX.megaXTab);

	}
	@SideOnly(Side.CLIENT)
	public String getTextureFile()
	{
		return "/zornco/megax/textures/armors_0.png";
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
	@SideOnly(Side.CLIENT)

	/**
	 * Gets an icon index based on an item's damage value and the given render pass
	 */
	public int getIconFromDamageForRenderPass(int par1, int par2)
	{
		return par2 == 1 ? this.iconIndex + 1 : super.getIconFromDamageForRenderPass(par1, par2);
	}
	@Override
	public String getArmorTextureFile(ItemStack itemstack) {
		// TODO Auto-generated method stub
		return null;
	}
}
