package zornco.reploidcraft.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemChip extends ItemReploidCraftBase {

	/** List of dye color names */
	public static final String[] chipTypeNames = new String[] {"Blank Chip", "Blaze Chip", "Char Chip", "Creeper Chip", "Enderman Chip", "Enderdragon Chip", "Ghast Chip", "Magma Slime Chip", "Zombie Pigman Chip", "Skeleton Chip", "Slime Chip", "Spider Chip", "Wither Chip", "Wither Skeleton Chip", "Zombie Chip"};
	public static final String[] chipIconNames = new String[] {"chip",			"chip_blaze", "chip_char", "chip_creeper", "chip_ender", "chip_enderdragon", 	"chip_ghast", "chip_magma", 		"chip_pigman", 		"chip_skeleton", "chip_slime", "chip_spider", "chip_wither", "chip_witherskeleton", "chip_zombie"};
	public static final int[] chipColors = new int[] {1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 2651799, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};
	@SideOnly(Side.CLIENT)
	private Icon[] iconChip;
    public static final int typeAmmount = chipTypeNames.length;

	public ItemChip(int par1)
	{
		super(par1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@SideOnly(Side.CLIENT)

	/**
	 * Gets an icon index based on an item's damage value
	 */
	public Icon getIconFromDamage(int par1)
	{
		int j = MathHelper.clamp_int(par1, 0, typeAmmount);
		return this.iconChip[j];
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
	 * different names based on their damage or NBT.
	 */
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, typeAmmount);
		return super.getUnlocalizedName() + "." + chipTypeNames[i];
	}

	@SideOnly(Side.CLIENT)

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int var4 = 0; var4 < typeAmmount; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}

	@SideOnly(Side.CLIENT)
	public void updateIcons(IconRegister par1IconRegister)
	{
		this.iconChip = new Icon[chipIconNames.length];

		for (int i = 0; i < chipIconNames.length; ++i)
		{
			this.iconChip[i] = par1IconRegister.registerIcon("ReploidCraft:" + chipIconNames[i]);
		}
	}
}
