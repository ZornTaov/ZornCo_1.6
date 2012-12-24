package zornco.megax.core;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import zornco.megax.MegaX;
import zornco.megax.items.ItemHPEnergy;
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
		if(item.func_92014_d().itemID == MegaX.healthBit.shiftedIndex || item.func_92014_d().itemID == MegaX.healthByte.shiftedIndex )
		{
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
}
