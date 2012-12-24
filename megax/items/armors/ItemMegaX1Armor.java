package zornco.megax.items.armors;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemStack;
import zornco.megax.MegaX;

public class ItemMegaX1Armor extends ItemMegaXArmorBase {

	public ItemMegaX1Armor(int par1, EnumArmorMaterial par2EnumArmorMaterial,
			int par3, int par4) {
		super(par1, par2EnumArmorMaterial, par3, par4);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getArmorTextureFile(ItemStack itemstack)
	{
		if (itemstack.itemID == MegaX.megaX1Belt.shiftedIndex) {
			return "/zornco/megax/textures/armor/MegaX1_2.png";
		}
		return "/zornco/megax/textures/armor/MegaX1_1.png";
	}
}
