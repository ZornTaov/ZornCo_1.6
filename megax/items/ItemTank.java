package zornco.megax.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemTank extends ItemMegaXBase {

	public ItemTank(int par1) {
		super(par1);
		setMaxDamage(50);
		this.canRepair = false;
	}

	public boolean onItemUse(ItemStack is, EntityPlayer player, World w, int x, int y, int z, int face, float par8, float par9, float par10)
	{
		if (!w.isRemote) {
			//if playerHP is full, return false
			//else heal player till full and remove difference from tank
			if(player.getHealth() == player.getMaxHealth())
				return false;
			else
			{
				int playerRemainingHP = player.getMaxHealth() - player.getHealth();
				Integer itemMaxDmg = Integer.valueOf(getMaxDamage());
				Integer charges = Integer.valueOf(itemMaxDmg.intValue() - is.getItemDamage());

				Integer i = Integer.valueOf(is.getItemDamage() + playerRemainingHP	);
				is.setItemDamage(i.intValue() >= itemMaxDmg ? itemMaxDmg : i.intValue());
			}

		}
		return false;
	}
	public static void setType(ItemStack is, String t) {
		NBTTagCompound tag = initTags(is);
		tag.setString("tanktype", t);
	}

	public static String getType(ItemStack is) {
		NBTTagCompound tag = initTags(is);
		return tag.getString("tanktype");
	}

	public boolean getShareTag()
	{
		return true;
	}

	public static NBTTagCompound initTags(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();

		if (tag == null)
		{
			tag = new NBTTagCompound();
			stack.setTagCompound(tag);
			tag.setString("tanktype", "");
		}

		return tag;
	}

	public String getTextureFile()
	{
		return "/zornco/megax/textures/MegaXItemTextures.png";
	}
	public ItemStack getContainerItemStack(ItemStack itemStack)
	{
		ItemStack rIS = new ItemStack(getContainerItem());
		rIS.setItemDamage(itemStack.getItemDamage() + 1);
		return rIS;
	}
}
