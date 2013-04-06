package zornco.reploidcraft.modules;

import java.util.List;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.MuseItemUtils;
import net.machinemuse.api.electricity.ElectricItemUtils;
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
import zornco.reploidcraft.RepliodCraft;
import zornco.reploidcraft.bullets.EntityBusterBullet;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class SpreadArrowModule extends PowerModuleBase implements IRightClickModule {
	public static final ItemStack arrow = new ItemStack(Item.arrow);
	public static final String MODULE_SPREAD_SHOT = "Spread Shot";
	public static final String ENERGY_USED_PER_ARROW = "Energy Used Per Arrow";
	public static final String ARROWS_SHOT_AT_FULL_CHARGE = "Arrows Shot At Full Charge";
	public static final String ARROWS_SPREAD = "Spread Of Arrows";

	public SpreadArrowModule(List<IModularItem> validItems) {
		super(validItems);
		addBaseProperty(ENERGY_USED_PER_ARROW, 10, "J");
		addBaseProperty(ARROWS_SHOT_AT_FULL_CHARGE, 3, "arrows");
		addBaseProperty(ARROWS_SPREAD, 30, "arrows");
		addIntTradeoffProperty("Count", ENERGY_USED_PER_ARROW, 150, "J", 1, 0);
		addIntTradeoffProperty("Count", ARROWS_SHOT_AT_FULL_CHARGE, 9, "arrows", 2, 1);
		addIntTradeoffProperty("Spread", ARROWS_SPREAD, 120, "degrees", 10, 0);
		addIntTradeoffProperty("Spread", ENERGY_USED_PER_ARROW, 30, "J", 1, 0);
		addInstallCost(MuseItemUtils.copyAndResize(new ItemStack(RepliodCraft.weaponChip, 1, 9), 1));
	}
	public PowerModuleBase addIntTradeoffProperty(String tradeoffName, String propertyName, double multiplier, String unit, int roundTo, int offset) {
		double propFromConfig = Config.getConfig().get("Properties", getName() + '.' + propertyName + '.' + tradeoffName + ".multiplier", multiplier)
				.getDouble(multiplier);
		units.put(propertyName, unit);
		return addPropertyModifier(propertyName, new PropertyModifierIntLinearAdditive(tradeoffName, propFromConfig, roundTo, offset));
	}
	@Override
	public String getCategory() {
		return MuseCommonStrings.CATEGORY_WEAPON;
	}

	@Override
	public String getName() {
		return MODULE_SPREAD_SHOT;
	}

	@Override
	public String getDescription() {
		return "Use the weapon energy contained in the buster to generate a spread shot of arrows, shooting more the longer you charge.";
	}

	@Override
	public void onRightClick(EntityPlayer player, World world, ItemStack item) {
		if (ElectricItemUtils.getPlayerEnergy(player) > 500) {
			player.setItemInUse(item, 72000);
		}
	}

	@Override
	public Icon getIcon(ItemStack item) {
		return arrow.getIconIndex();
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
			double energyConsumption = ModuleManager.computeModularProperty(itemStack, SpreadArrowModule.ENERGY_USED_PER_ARROW)
					* chargeTicks;
			int cluster = (int)ModuleManager.computeModularProperty(itemStack, SpreadArrowModule.ARROWS_SHOT_AT_FULL_CHARGE);
			int spread = (int)ModuleManager.computeModularProperty(itemStack, SpreadArrowModule.ARROWS_SPREAD);
			float f = (float)chargeTicks / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if ((double)f < 0.1D)
			{
				return;
			}
			if (f > 1.0F)
			{
				f = 1.0F;
			}
			energyConsumption = energyConsumption * (f == 1.0F ? cluster : 1.0F);
			if (ElectricItemUtils.getPlayerEnergy(player) > energyConsumption) {
				
				ElectricItemUtils.drainPlayerEnergy(player, energyConsumption);
				
				world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / ((float) Math.random() * 0.4F + 0.8F));
				boolean fire = MuseItemUtils.itemHasModule(itemStack, EnhancedFormModule.ENHANCED_FORM);
				createArrowWithOffset(world, player, f, 0, fire);
				if(f == 1.0F) {
					for (int i = 1; i <= cluster/2; i++) {
						createArrowWithOffset(world, player, f, ((spread/2)/(cluster/2))*i, fire);
						createArrowWithOffset(world, player, f, 0-((spread/2)/(cluster/2))*i, fire);
					}
				}
				//MusePacketPlasmaBolt packet = new MusePacketPlasmaBolt((Player) player, entityarrow.entityId, 2.0D);
				//PacketDispatcher.sendPacketToAllPlayers(packet.getPacket250());
			}
		}
	}

	private void createArrowWithOffset(World world, EntityPlayer player, float f, int spread, boolean fire)
	{
		double x = player.posX, y = player.posY, z = player.posZ;
		EntityArrow entityarrow = new EntityArrow(world, x, y, z);
		entityarrow.shootingEntity = player;

		entityarrow.setLocationAndAngles(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ, player.rotationYaw, player.rotationPitch);
		entityarrow.posX -= (double)(MathHelper.cos((entityarrow.rotationYaw+spread) / 180.0F * (float)Math.PI) * 0.16F);
		entityarrow.posY -= 0.10000000149011612D;
		entityarrow.posZ -= (double)(MathHelper.sin((entityarrow.rotationYaw+spread) / 180.0F * (float)Math.PI) * 0.16F);
		entityarrow.setPosition(entityarrow.posX, entityarrow.posY, entityarrow.posZ);
		entityarrow.motionX = (double)(-MathHelper.sin((entityarrow.rotationYaw+spread) / 180.0F * (float)Math.PI) * MathHelper.cos(entityarrow.rotationPitch / 180.0F * (float)Math.PI));
		entityarrow.motionZ = (double)(MathHelper.cos((entityarrow.rotationYaw+spread) / 180.0F * (float)Math.PI) * MathHelper.cos(entityarrow.rotationPitch / 180.0F * (float)Math.PI));
		entityarrow.motionY = (double)(-MathHelper.sin(entityarrow.rotationPitch / 180.0F * (float)Math.PI));
		entityarrow.setThrowableHeading(entityarrow.motionX, entityarrow.motionY, entityarrow.motionZ, f * 2.0F * 1.5F, 1.0F);

		if(f == 1.0F)entityarrow.setIsCritical(true);
		if (fire) entityarrow.setFire(100);
		entityarrow.canBePickedUp = 2;
		world.spawnEntityInWorld(entityarrow);
	}
}
