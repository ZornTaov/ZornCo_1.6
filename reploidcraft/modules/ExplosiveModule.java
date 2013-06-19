package zornco.reploidcraft.modules;

import java.util.List;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseItemUtils;
import net.machinemuse.utils.ElectricItemUtils;
import net.machinemuse.api.moduletrigger.IRightClickModule;
import net.machinemuse.powersuits.common.Config;
import net.machinemuse.powersuits.network.packets.MusePacketPlasmaBolt;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.machinemuse.powersuits.powermodule.PropertyModifierLinearAdditive;
import net.machinemuse.powersuits.powermodule.misc.CitizenJoeStyle;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import zornco.reploidcraft.ReploidCraft;
import zornco.reploidcraft.bullets.EntityBusterBullet;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class ExplosiveModule extends PowerModuleBase implements IRightClickModule {
	public static final ItemStack gunpowder = new ItemStack(Item.gunpowder);
	public static final String MODULE_EXPLOSION = "Explosion";
	public static final String ENERGY_USED_PER_EXPLOSION = "Energy Used Per Explosion";
	public static final String EXPLOSION_SIZE_AT_FULL_CHARGE = "Explosion Size At Full Charge";

	public ExplosiveModule(List<IModularItem> validItems) {
		super(validItems);
		addBaseProperty(ENERGY_USED_PER_EXPLOSION, 250, "J");
		addBaseProperty(EXPLOSION_SIZE_AT_FULL_CHARGE, .25, "Creeper");
		addTradeoffProperty("Size", ENERGY_USED_PER_EXPLOSION, 1750, "J");
		addTradeoffProperty("Size", EXPLOSION_SIZE_AT_FULL_CHARGE, 1.75, "Creeper");
		addInstallCost(MuseItemUtils.copyAndResize(new ItemStack(ReploidCraft.weaponChip, 1, 3), 1));
	}
	@Override
	public String getCategory() {
		return MuseCommonStrings.CATEGORY_WEAPON;
	}

	@Override
	public String getName() {
		return MODULE_EXPLOSION;
	}

	@Override
	public String getDescription() {
		return "asadfUse the weapon energy contained in the buster to generate a spread shot of arrows, shooting more the longer you charge.";
	}

	@Override
	public void onRightClick(EntityPlayer player, World world, ItemStack item) {
		if (ElectricItemUtils.getPlayerEnergy(player) > 500) {
			player.setItemInUse(item, 72000);
		}
	}

	@Override
	public Icon getIcon(ItemStack item) {
		return gunpowder.getIconIndex();
	}

	@Override
	public String getTextureFile() {
		return null;
	}

	@Override
	public void onItemUse(ItemStack itemStack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {

	}

	@Override
	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		return false;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemStack, World world,
			EntityPlayer player, int par4) {
		int chargeTicks = Math.max(itemStack.getMaxItemUseDuration() - par4, 10);

		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			double energyConsumption = ModuleManager.computeModularProperty(itemStack, ExplosiveModule.ENERGY_USED_PER_EXPLOSION)
					* chargeTicks;
			float explosiveness = (int)ModuleManager.computeModularProperty(itemStack, ExplosiveModule.EXPLOSION_SIZE_AT_FULL_CHARGE);
			float f = (float)chargeTicks / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if ((double)f < 0.1D)
			{
				return;
			}
			if (f > 3.0F)
			{
				f = 3.0F;
			}
			if (ElectricItemUtils.getPlayerEnergy(player) > energyConsumption) {
				
				ElectricItemUtils.drainPlayerEnergy(player, energyConsumption);
				boolean fire = MuseItemUtils.itemHasModule(itemStack, EnhancedFormModule.ENHANCED_FORM);
				if(f == 3.0F)
					world.newExplosion(player, player.posX, player.posY, player.posZ, explosiveness * 3, fire, true);

			}
		}
	}
}