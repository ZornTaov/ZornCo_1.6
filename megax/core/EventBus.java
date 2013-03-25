package zornco.megax.core;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
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

	@ForgeSubscribe
	public void onEntityDrop(LivingDropsEvent event) {
		EntityLiving victim = event.entityLiving;
		if(MegaX.instance.rand.nextInt(64) == 0) {
			System.out.println("DING");
			if (victim instanceof EntityBlaze) {
				event.drops.add(new EntityItem(victim.worldObj, victim.posX, victim.posY+0.2, victim.posZ, new ItemStack(MegaX.weaponChip, 1, 1)));
			}
			if (victim instanceof EntityCreeper) {
				event.drops.add(new EntityItem(victim.worldObj, victim.posX, victim.posY+0.2, victim.posZ, new ItemStack(MegaX.weaponChip, 1, 3)));
			}
			if (victim instanceof EntityEnderman) {
				event.drops.add(new EntityItem(victim.worldObj, victim.posX, victim.posY+0.2, victim.posZ, new ItemStack(MegaX.weaponChip, 1, 4)));
			}
			if (victim instanceof EntityDragon) {
				event.drops.add(new EntityItem(victim.worldObj, victim.posX, victim.posY+0.2, victim.posZ, new ItemStack(MegaX.weaponChip, 1, 5)));
			}
			if (victim instanceof EntityGhast) {
				event.drops.add(new EntityItem(victim.worldObj, victim.posX, victim.posY+0.2, victim.posZ, new ItemStack(MegaX.weaponChip, 1, 6)));
			}
			if (victim instanceof EntityMagmaCube) {
				event.drops.add(new EntityItem(victim.worldObj, victim.posX, victim.posY+0.2, victim.posZ, new ItemStack(MegaX.weaponChip, 1, 7)));
			}
			if (victim instanceof EntityPigZombie) {
				event.drops.add(new EntityItem(victim.worldObj, victim.posX, victim.posY+0.2, victim.posZ, new ItemStack(MegaX.weaponChip, 1, 8)));
			}
			if (victim instanceof EntitySkeleton) {
				if(((EntitySkeleton)victim).getSkeletonType() == 1)
					event.drops.add(new EntityItem(victim.worldObj, victim.posX, victim.posY+0.2, victim.posZ, new ItemStack(MegaX.weaponChip, 1, 13)));
				else
					event.drops.add(new EntityItem(victim.worldObj, victim.posX, victim.posY+0.2, victim.posZ, new ItemStack(MegaX.weaponChip, 1, 9)));
			}
			if (victim instanceof EntitySlime) {
				event.drops.add(new EntityItem(victim.worldObj, victim.posX, victim.posY+0.2, victim.posZ, new ItemStack(MegaX.weaponChip, 1, 10)));
			}
			if (victim instanceof EntitySpider) {
				event.drops.add(new EntityItem(victim.worldObj, victim.posX, victim.posY+0.2, victim.posZ, new ItemStack(MegaX.weaponChip, 1, 11)));
			}
			if (victim instanceof EntityWither) {
				event.drops.add(new EntityItem(victim.worldObj, victim.posX, victim.posY+0.2, victim.posZ, new ItemStack(MegaX.weaponChip, 1, 12)));
			}
			if (victim instanceof EntityZombie) {
				event.drops.add(new EntityItem(victim.worldObj, victim.posX, victim.posY+0.2, victim.posZ, new ItemStack(MegaX.weaponChip, 1, 14)));
			}
		}
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
		if(item.getEntityItem().itemID == MegaX.healthBit.itemID 
				|| item.getEntityItem().itemID == MegaX.healthByte.itemID 
				//|| item.getEntityItem().itemID == MegaX.weaponBit.shiftedIndex 
				//|| item.getEntityItem().itemID == MegaX.weaponByte.shiftedIndex 
				)
		{
			if(event.entityLiving instanceof EntityPlayerMP && !playerEnt.isSneaking())
			{
				ItemHPEnergy bit = (ItemHPEnergy) item.getEntityItem()
						.getItem();
				switch (bit.type) {
				case 0:
					playerEnt.worldObj.playSoundAtEntity(playerEnt,
							Sounds.BIT, 1.0F, 1.0F);
					break;
				case 1:
					playerEnt.worldObj.playSoundAtEntity(playerEnt,
							Sounds.BYTE, 1.0F, 1.0F);
					break;
				}
				if (event.entityLiving.getHealth() == event.entityLiving.getMaxHealth())
				{
					processBit((EntityPlayerMP)event.entityLiving, item);
				}
				else {
					bit.applyEffect(playerEnt, item.getEntityItem().stackSize);
				}

				item.getEntityItem().stackSize = 0;
				item.setDead();
				return;
			}
		}
		/*
		 * Events may offer further fields and methods. Just read them, it should be obvious.
		 */
	}
	public void processBit(EntityPlayerMP player, EntityItem item)
	{
		for (int i = 0; i < 36; i++)
		{
			ItemStack is = player.inventory.getStackInSlot(i);

			if (is == null) {
				continue;
			}
			if (is.getItem().equals(MegaX.healthTank)) {
				if (ItemTank.getType(is).isEmpty())
				{
					ItemTank.setType(is, "HP");
					is.setItemDamage(is.getItemDamage() <= 0 ? 0 : is.getItemDamage() - item.getEntityItem().stackSize*bitSize(item));
					break;
				}
				if ((!ItemTank.getType(is).equals("HP")))
					continue;
				if (is.getItemDamage() == 0)
					continue;
				is.setItemDamage(is.getItemDamage() <= 0 ? 0 : is.getItemDamage() - item.getEntityItem().stackSize*bitSize(item));
				break;
			}
		}
	}
	private int bitSize(EntityItem item) {
		if(item.getEntityItem().itemID == MegaX.healthBit.itemID)// || item.getEntityItem().itemID == MegaX.weaponBit.shiftedIndex )
			return 3;
		else if(item.getEntityItem().itemID == MegaX.healthByte.itemID)// || item.getEntityItem().itemID == MegaX.weaponByte.shiftedIndex )
			return 6;
		else 
			return 0;
	}
}
