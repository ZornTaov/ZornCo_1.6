package zornco.megax.core;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import zornco.megax.MegaX;
import zornco.megax.items.ItemHPEnergy;
import zornco.megax.items.ItemTank;
import zornco.megax.sounds.Sounds;
/**
 * Name and cast of this class are irrelevant
 */
public class EventBus
{

	public EventBus()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}
	/**
	 * The key is the @ForgeSubscribe annotation and the cast of the Event you put in as argument.
	 * The method name you pick does not matter. Method signature is public void, always.
	 */
	@ForgeSubscribe
	public void entityAttacked(EntityItemPickupEvent event)
	{
		/*
		 * You can then proceed to read and change the Event's fields where possible
		 */
		if(event.entityLiving == null || event.item == null) return;
		EntityLiving playerEnt = event.entityLiving;
		EntityItem item = event.item;
		/*
		 * Note this possibility to interrupt certain (not all) events
		 */
		/*if (event.isCancelable())
		{
			event.setCanceled(true);
		}*/
		if(item.func_92014_d().itemID == MegaX.healthBit.shiftedIndex 
				|| item.func_92014_d().itemID == MegaX.healthByte.shiftedIndex 
				|| item.func_92014_d().itemID == MegaX.weaponBit.shiftedIndex 
				|| item.func_92014_d().itemID == MegaX.weaponByte.shiftedIndex )
		{
			if ((event.entityLiving instanceof EntityPlayerMP) && (event.entityLiving.getHealth() == event.entityLiving.getMaxHealth()) && (!playerEnt.isSneaking()))
			{
				processBit((EntityPlayerMP)event.entityLiving, item);
				item.func_92014_d().stackSize = 0;
				item.setDead();
				return;
			}
			EntityPlayer player = (EntityPlayer)playerEnt;
			if(!player.isSneaking())
			{
				ItemHPEnergy bit = (ItemHPEnergy) item.func_92014_d().getItem();
				bit.applyEffect(playerEnt, item.func_92014_d().stackSize);
				switch(bit.type)
				{
				case 0:
					playerEnt.worldObj.playSoundAtEntity(playerEnt, Sounds.BIT, 1.0F, 1.0F);
					break;
				case 1:
					playerEnt.worldObj.playSoundAtEntity(playerEnt, Sounds.BYTE, 1.0F, 1.0F);
					break;
				}
				item.func_92014_d().stackSize = 0;
				item.setDead();
			}
		}

		/*
		 * Events may offer further fields and methods. Just read them, it should be obvious.
		 */
	}
	public void processBit(EntityPlayerMP player, EntityItem item)
	{
		for (int i = 0; i < 10; i++)
		{
			ItemStack is = player.inventory.getStackInSlot(i);

			if (is == null) {
				continue;
			}
			if (is.getItem().equals(MegaX.healthTank)) {
				if (ItemTank.getType(is).isEmpty())
				{
					ItemTank.setType(is, "HP");
					is.setItemDamage(is.getItemDamage() <= 0 ? 0 : is.getItemDamage() - item.func_92014_d().stackSize*bitSize(item));
					break;
				}
				if ((!ItemTank.getType(is).equals("HP")))
					continue;
				is.setItemDamage(is.getItemDamage() <= 0 ? 0 : is.getItemDamage() - item.func_92014_d().stackSize*bitSize(item));
				break;
			}
		}
	}
	private int bitSize(EntityItem item) {
		if(item.func_92014_d().itemID == MegaX.healthBit.shiftedIndex || item.func_92014_d().itemID == MegaX.weaponBit.shiftedIndex )
			return 3;
		else if(item.func_92014_d().itemID == MegaX.healthByte.shiftedIndex || item.func_92014_d().itemID == MegaX.weaponByte.shiftedIndex )
			return 6;
		else 
			return 0;
	}
}
