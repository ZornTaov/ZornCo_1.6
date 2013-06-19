package zornco.reploidcraft.core;

import java.util.Arrays;
import java.util.List;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.IPowerModule;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.utils.MuseItemUtils;
import net.machinemuse.powersuits.powermodule.armor.*;
import net.machinemuse.powersuits.powermodule.energy.*;
import net.machinemuse.powersuits.powermodule.misc.TintModule;
import net.machinemuse.powersuits.powermodule.movement.ShockAbsorberModule;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import zornco.reploidcraft.ReploidCraft;
import zornco.reploidcraft.blocks.*;
import zornco.reploidcraft.bullets.*;
import zornco.reploidcraft.crafting.RecipeHandler;
import zornco.reploidcraft.entities.*;
import zornco.reploidcraft.items.*;
import zornco.reploidcraft.items.armors.ItemReploidArmorBase;
import zornco.reploidcraft.items.busters.ItemXBuster;
import zornco.reploidcraft.modules.*;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Config {
	//ID's
	public static int spikesRI = -1;
	public static int bossDoorRI = -1;

	private int busterID;
	private int weaponChipID;
	private int healthBitID;
	private int healthByteID;
	//private int weaponBitID;
	//private int weaponByteID;
	private int healthTankID;
	//private int weaponTankID;
	//private int extraManID;
	private int reploidPlateID;
	private int componentID;

	private int reploidHelmID;
	private int reploidChestID;
	private int reploidBeltID;
	private int reploidBootsID;
	private int platformPlacerID;
	private int doorBossItemID;

	private int upgradeStationID;
	private int spikesID;
	private int doorBossBlockID;
	public static EnumArmorMaterial enumReploidArmor = EnumHelper.addArmorMaterial("ReploidArmor", 16, new int[]{2,7,6,3}, 20);

	public static RecipeHandler recipes = new RecipeHandler();
	public static void addModule(IPowerModule module) {
		ModuleManager.addModule(module);
	}
	public void loadConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		//Items
		int itemID = 22000;
		busterID = config.getItem(config.CATEGORY_ITEM,"X Buster", itemID++).getInt();
		weaponChipID = config.getItem(config.CATEGORY_ITEM,"Wweapon Chips", itemID++).getInt();
		healthBitID = config.getItem(config.CATEGORY_ITEM,"Health Bit", itemID++).getInt();
		healthByteID = config.getItem(config.CATEGORY_ITEM,"Health Byte", itemID++).getInt();
		//weaponBitID = config.getItem(config.CATEGORY_ITEM,"Weapon Bit", itemID++).getInt();
		//weaponByteID = config.getItem(config.CATEGORY_ITEM,"Weapon Byte", itemID++).getInt();
		healthTankID = config.getItem(config.CATEGORY_ITEM,"Health Tank", itemID++).getInt();
		//weaponTankID = config.getItem(config.CATEGORY_ITEM,"Weapon Tank", itemID++).getInt();
		//extraManID = config.getItem(config.CATEGORY_ITEM,"Extra Man", itemID++).getInt();
		reploidPlateID = config.getItem(config.CATEGORY_ITEM,"Reploid Plates", itemID++).getInt();
		componentID = config.getItem(config.CATEGORY_ITEM,"Components", itemID++).getInt();
		reploidHelmID = config.getItem(config.CATEGORY_ITEM,"Reploid Helm", itemID++).getInt();
		reploidChestID = config.getItem(config.CATEGORY_ITEM,"Reploid Chest", itemID++).getInt();
		reploidBeltID = config.getItem(config.CATEGORY_ITEM,"Reploid Belt", itemID++).getInt();
		reploidBootsID = config.getItem(config.CATEGORY_ITEM,"Reploid Boots", itemID++).getInt();
		doorBossItemID = config.getItem(config.CATEGORY_ITEM,"Boss Door Item", itemID++).getInt();
		platformPlacerID = config.getItem(config.CATEGORY_ITEM,"Platform Placer", itemID++).getInt();

		//Blocks
		int blockID = 2500;
		spikesID = config.get(config.CATEGORY_BLOCK,"Metal Spikes", blockID++).getInt();
		doorBossBlockID = config.get(config.CATEGORY_BLOCK,"Boss Door Block", blockID++).getInt();

		//Keys
		ReploidCraft.instance.proxy.setKeyBinding("Weapon Change", config.get("Keybinds", "key.change", 48).getInt(48));
		ReploidCraft.instance.proxy.setKeyBinding("Buster Menu", config.get("Keybinds", "key.menu", 48).getInt(49));

		config.save();
	}
	/**
	 * Load all the modules in the config file into memory. Eventually. For now, they are hardcoded.
	 */
	public static void loadPowerModules() {
		IPowerModule module;
		List<IModularItem> ALLITEMS = Arrays.asList((IModularItem) ReploidCraft.buster, (IModularItem) ReploidCraft.reploidHelm, (IModularItem) ReploidCraft.reploidChest, (IModularItem) ReploidCraft.reploidBelt, (IModularItem) ReploidCraft.reploidBoots);
		List<IModularItem> ARMORONLY = Arrays.asList((IModularItem) ReploidCraft.reploidHelm, (IModularItem) ReploidCraft.reploidChest, (IModularItem) ReploidCraft.reploidBelt, (IModularItem) ReploidCraft.reploidBoots);
		List<IModularItem> HEADONLY = Arrays.asList((IModularItem) ReploidCraft.reploidHelm);
		List<IModularItem> CHESTONLY = Arrays.asList((IModularItem) ReploidCraft.reploidChest);
		List<IModularItem> LEGSONLY = Arrays.asList((IModularItem) ReploidCraft.reploidBelt);
		List<IModularItem> FEETONLY = Arrays.asList((IModularItem) ReploidCraft.reploidBoots);
		List<IModularItem> BUSTERONLY = Arrays.asList((IModularItem) ReploidCraft.buster);


		addModule(new BusterModule(BUSTERONLY));
		addModule(new SpreadArrowModule(BUSTERONLY));
		addModule(new ExplosiveModule(BUSTERONLY));

		// Armor
		addModule(new BasicPlatingModule(ARMORONLY));
		addModule(new DiamondPlatingModule(ARMORONLY));
		addModule(new EnergyShieldModule(ARMORONLY));
		// Energy
		addModule(new BasicBatteryModule(ALLITEMS));
		addModule(new AdvancedBatteryModule(ALLITEMS));
		addModule(new EliteBatteryModule(ALLITEMS));

		// Cosmetic
		addModule(new TintModule(ALLITEMS));
		addModule(new EnhancedFormModule(ARMORONLY).
				addInstallCost(MuseItemUtils.copyAndResize(new ItemStack(Item.netherStar, 1), 1)));
		addModule(new EnhancedFormModule(BUSTERONLY).
				addInstallCost(MuseItemUtils.copyAndResize(new ItemStack(Block.dragonEgg, 1), 1)));

		// Head ======================================
		// Torso =====================================
		// Legs =======================================
		// Feet =======================================
		addModule(new ShockAbsorberModule(FEETONLY));
	}

	public void addNames() {
		/** Names **/
		LanguageRegistry.addName(ReploidCraft.buster, "X Buster");
		LanguageRegistry.addName(ReploidCraft.healthBit, "Health Bit");
		LanguageRegistry.addName(ReploidCraft.healthByte, "Health Byte");
		//LanguageRegistry.addName(Reploid.weaponBit, "Weapon Bit");
		//LanguageRegistry.addName(Reploid.weaponByte, "Weapon Byte");
		LanguageRegistry.addName(ReploidCraft.healthTank, "Health Tank");
		//LanguageRegistry.addName(Reploid.weaponTank, "Weapon Tank");
		//LanguageRegistry.addName(Reploid.extraMan, "Extra Man");
		for(String a : ItemReploidPlate.plateColorNames)
		{
			LanguageRegistry.instance().addStringLocalization("item.reploidPlate."+a+".name", "en_US", a+" Reploid Plate");
		}
		for(String a : ItemComponent.componentNames)
		{
			LanguageRegistry.instance().addStringLocalization("item.component."+a+".name", "en_US", a );
		}

		LanguageRegistry.addName(ReploidCraft.reploidHelm, "Reploid Helm");
		LanguageRegistry.addName(ReploidCraft.reploidChest, "Reploid Chest");
		LanguageRegistry.addName(ReploidCraft.reploidBelt, "Reploid Belt");
		LanguageRegistry.addName(ReploidCraft.reploidBoots, "Reploid Boots");

		LanguageRegistry.addName(ReploidCraft.platformPlacer, "Platform Placer");
		//LanguageRegistry.addName(Reploid.doorBossItem, "Boss Door");

		//LanguageRegistry.addName(Reploid.upgradeStation, "Upgrade Station");
		LanguageRegistry.addName(ReploidCraft.spikes, "Metal Spikes");
		//LanguageRegistry.addName(Reploid.doorBossBlock, "Boss Door");

		LanguageRegistry.instance().addStringLocalization("entity.Met.name", "en_US", "Met");
		LanguageRegistry.instance().addStringLocalization("entity.FloatingPlatform.name", "en_US", "Floating Platform");
		LanguageRegistry.instance().addStringLocalization("death.bullet", "en_US", "Pewpew Dead!");
		LanguageRegistry.instance().addStringLocalization("itemGroup.Reploid", "en_US", "Reploid");
		for(String a : ItemChip.chipTypeNames)
		{
			LanguageRegistry.instance().addStringLocalization("item.chip."+a+".name", "en_US", a);
		}
	}

	public void registerEntities() {
		/** Registers **/

		EntityRegistry.registerGlobalEntityID(EntityMet.class, "Met", EntityRegistry.findGlobalUniqueEntityId(), 0xFFFF00, 0x0F0000);
		EntityRegistry.registerModEntity(EntityMet.class, "Met", 2, ReploidCraft.instance, 80, 3, true);
		EntityRegistry.addSpawn("Met", 3, 2, 4, EnumCreatureType.monster, BiomeGenBase.beach);
		EntityRegistry.registerGlobalEntityID(EntityFloatingPlatform.class, "FloatingPlatform", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityFloatingPlatform.class, "FloatingPlatform", 3, ReploidCraft.instance, 80, 1, true);
		EntityRegistry.registerGlobalEntityID(EntityBusterBullet.class, "lemonBullet", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityBusterBullet.class, "lemonBullet", 1, ReploidCraft.instance, 250, 1, true);
		EntityRegistry.registerGlobalEntityID(EntityMetBullet.class, "metBullet", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityMetBullet.class, "metBullet", 4, ReploidCraft.instance, 250, 1, true);
	}

	public void addBlocks() {
		/** Blocks **/
		ReploidCraft.spikes = new BlockSpikes(spikesID).setStepSound(new StepSound("stone", 1.0F, 1.5F)).setUnlocalizedName("spikes").setHardness(3.5F);
		//doorBossBlock = (new BlockBossDoor(doorBossBlockID, Material.iron)).setHardness(5.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("doorBoss").setRequiresSelfNotify();

		GameRegistry.registerBlock(ReploidCraft.spikes, "spikes");
		//GameRegistry.registerBlock(doorBossBlock, "doorBoss");
	}
	public void addItems() {
		/** Items **/
		ReploidCraft.buster = new ItemXBuster(busterID, 1, false).setUnlocalizedName("XBuster").setFull3D();
		// add weapon types
		// Guts type weapon that would act like a macerator
		// cut type weapon that act like shears
		// fire type weapon that acts like flint and steel
		// water type weapon that places a water(non-source) block
		ReploidCraft.weaponChip = new ItemChip(weaponChipID).setUnlocalizedName("chip");

		ReploidCraft.healthBit = new ItemHPEnergy(healthBitID, 0, 3, 0.5F).setPotionEffect(Potion.heal.id, 1, 0, 1.0F).setAlwaysEdible().setUnlocalizedName("healthBit").setMaxStackSize(1);
		ReploidCraft.healthByte = new ItemHPEnergy(healthByteID, 1, 8, 0.8F).setPotionEffect(Potion.heal.id, 1, 1, 1.0F).setAlwaysEdible().setUnlocalizedName("healthByte").setMaxStackSize(1);
		//weaponBit = new ItemWPEnergy(weaponBitID).setItemName("Weapon Bit").setMaxStackSize(1);
		//weaponByte = new ItemWPEnergy(weaponByteID).setItemName("Weapon Byte").setMaxStackSize(1);
		ReploidCraft.healthTank = new ItemTank(healthTankID).setUnlocalizedName("Health Tank").setMaxStackSize(1); //use EventBus to add to this, then use some other method to heal the player per heart
		//weaponTank = new ItemTank(weaponTankID).setItemName("Weapon Tank").setMaxStackSize(1);
		//extraMan = new ItemLife(extraManID).setItemName("Extra Man").setMaxStackSize(9); //possibly use ticker to instantly heal player?

		ReploidCraft.reploidPlate = new ItemReploidPlate(reploidPlateID).setUnlocalizedName("reploidPlate");
		ReploidCraft.component = new ItemComponent(componentID).setUnlocalizedName("component");


		ReploidCraft.reploidHelm = new ItemReploidArmorBase(reploidHelmID, enumReploidArmor, ReploidCraft.instance.proxy.addArmor("ReploidBasic"),0).setUnlocalizedName("ReploidHelm");
		ReploidCraft.reploidChest = new ItemReploidArmorBase(reploidChestID, enumReploidArmor, ReploidCraft.instance.proxy.addArmor("ReploidBasic"),1).setUnlocalizedName("ReploidChest");
		ReploidCraft.reploidBelt = new ItemReploidArmorBase(reploidBeltID, enumReploidArmor, ReploidCraft.instance.proxy.addArmor("ReploidBasic"),2).setUnlocalizedName("ReploidBelt");
		ReploidCraft.reploidBoots = new ItemReploidArmorBase(reploidBootsID, enumReploidArmor, ReploidCraft.instance.proxy.addArmor("ReploidBasic"),3).setUnlocalizedName("ReploidBoots");

		ReploidCraft.platformPlacer = new ItemPlatformPlacer(platformPlacerID).setUnlocalizedName("platformPlacer");
		//doorBossItem = (new ItemBossDoor(doorBossItemID, Material.iron)).setIconCoord(12, 2).setItemName("doorBoss");
	}
	/**
	 * Helper function for making recipes. Returns a copy of the itemstack with the specified stacksize.
	 * 
	 * @param stack
	 *            Itemstack to copy
	 * @param number
	 *            New Stacksize
	 * @return A new itemstack with the specified properties
	 */
	public static ItemStack copyAndResize(ItemStack stack, int number) {
		ItemStack copy = stack.copy();
		copy.stackSize = number;
		return copy;
	}
}
