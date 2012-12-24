package zornco.megax.items;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import zornco.megax.MegaX;
import zornco.megax.sounds.Sounds;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHPEnergy extends ItemFood {
	/**
	 * represents the potion effect that will occurr upon eating this food. Set by setPotionEffect
	 */
	private int potionId; 

	/** set by setPotionEffect */
	private int potionDuration;

	/** set by setPotionEffect */
	private int potionAmplifier;

	/** probably of the set potion effect occurring */
	private float potionEffectProbability;
	public int type;
	public ItemHPEnergy(int id, int type, int ammount, float sat) {
		super(id, ammount, sat, false);
		this.type = type;
		this.setCreativeTab(MegaX.megaXTab);
	}
	@SideOnly(Side.CLIENT)
	int getIndex() {
		return this.iconIndex;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public String getTextureFile() {
		return "/zornco/megax/textures/items.png";
	}
	@Override
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
	public int getIconFromDamageForRenderPass(int par1, int par2)
	{
		return par2 == 1 ? this.iconIndex + 1 : super.getIconFromDamageForRenderPass(par1, par2);
	}

	/**
	 * sets a potion effect on the item. Args: int potionId, int duration (will be multiplied by 20), int amplifier,
	 * float probability of effect happening
	 */
	public ItemFood setPotionEffect(int par1, int par2, int par3, float par4)
	{
		this.potionId = par1;
		this.potionDuration = par2;
		this.potionAmplifier = par3;
		this.potionEffectProbability = par4;
		return this;
	}
	@Override
	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (!par3EntityPlayer.capabilities.isCreativeMode)
		{
			//damage energy tank 
			--par1ItemStack.stackSize;
		}

		if (!par2World.isRemote)
		{

			this.applyEffect(par3EntityPlayer, 1);
			switch(type)
			{
			case 0:
				par3EntityPlayer.worldObj.playSoundAtEntity(par3EntityPlayer, Sounds.BIT, 1.0F, 1.0F);
				break;
			case 1:
				par3EntityPlayer.worldObj.playSoundAtEntity(par3EntityPlayer, Sounds.BYTE, 1.0F, 1.0F);
				break;
			}
			par3EntityPlayer.getFoodStats().addStats(this);
		}

		return par1ItemStack;
	}
	public void applyEffect(EntityLiving playerEnt, int stackSize)
	{
		if (this.potionId > 0)
		{
			playerEnt.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration, this.potionAmplifier*stackSize));
		}
	}
	@Override
	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.none;
	}
}
