package zornco.megax.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import zornco.megax.sounds.Sounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemTank extends ItemMegaXBase {

	public ItemTank(int par1) {
		super(par1);
		setMaxDamage(30);
		this.canRepair = false;
	}

	public ItemStack onItemRightClick(ItemStack is, World par2World, EntityPlayer player)
	{

		if (!par2World.isRemote)
		{
		//if playerHP is full, return false
		//else heal player till full and remove difference from tank
		while(player.getHealth() != player.getMaxHealth())
		{
			if( is.getItemDamage() == is.getMaxDamage())
				break;
			par2World.playSoundAtEntity(player, Sounds.BYTE, 1.0F, 1.0F);
			is.setItemDamage(is.getItemDamage() + 1);
			player.heal(1);
		}

		}
		return is;
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
