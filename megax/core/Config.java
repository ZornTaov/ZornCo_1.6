package zornco.megax.core;

import java.util.Arrays;
import java.util.List;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.IPowerModule;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.electricity.ElectricItemUtils;
import net.machinemuse.powersuits.powermodule.PowerModule;
import net.machinemuse.powersuits.powermodule.ToggleablePowerModule;
import net.machinemuse.powersuits.powermodule.movement.ShockAbsorberModule;
import net.minecraft.block.StepSound;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import zornco.megax.MegaX;
import zornco.megax.blocks.*;
import zornco.megax.bullets.*;
import zornco.megax.crafting.RecipeHandler;
import zornco.megax.entities.*;
import zornco.megax.items.*;
import zornco.megax.items.armors.*;
import zornco.megax.items.busters.ItemXBuster;
import zornco.megax.modules.BusterModule;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
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

	private int megaX1HelmID;
	private int megaX1ChestID;
	private int megaX1BeltID;
	private int megaX1BootsID;
	private int megaX1HelmEnhancedID;
	private int megaX1ChestEnhancedID;
	private int megaX1BeltEnhancedID;
	private int megaX1BootsEnhancedID;
	private int platformPlacerID;
	private int doorBossItemID;

	private int upgradeStationID;
	private int spikesID;
	private int doorBossBlockID;
	public static EnumArmorMaterial enumMegaX1Armor = EnumHelper.addArmorMaterial("MegaX1Armor", 16, new int[]{2,7,6,3}, 20);
	public static EnumArmorMaterial enumMegaX1ArmorEnhanced = EnumHelper.addArmorMaterial("MegaX1ArmorEnhanced", 20, new int[]{3,8,7,4}, 30);

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
		megaX1HelmID = config.getItem(config.CATEGORY_ITEM,"MegaX Helm", itemID++).getInt();
		megaX1ChestID = config.getItem(config.CATEGORY_ITEM,"MegaX Chest", itemID++).getInt();
		megaX1BeltID = config.getItem(config.CATEGORY_ITEM,"MegaX Belt", itemID++).getInt();
		megaX1BootsID = config.getItem(config.CATEGORY_ITEM,"MegaX Boots", itemID++).getInt();
		megaX1HelmEnhancedID = config.getItem(config.CATEGORY_ITEM,"MegaX1 Helm Enhanced", itemID++).getInt();
		megaX1ChestEnhancedID = config.getItem(config.CATEGORY_ITEM,"MegaX1 Chest Enhanced", itemID++).getInt();
		megaX1BeltEnhancedID = config.getItem(config.CATEGORY_ITEM,"MegaX1 Belt Enhanced", itemID++).getInt();
		megaX1BootsEnhancedID = config.getItem(config.CATEGORY_ITEM,"MegaX1 Boots Enhanced", itemID++).getInt();
		doorBossItemID = config.getItem(config.CATEGORY_ITEM,"Boss Door Item", itemID++).getInt();
		platformPlacerID = config.getItem(config.CATEGORY_ITEM,"Platform Placer", itemID++).getInt();

		//Blocks
		int blockID = 2500;
		spikesID = config.get(config.CATEGORY_BLOCK,"Metal Spikes", blockID++).getInt();
		doorBossBlockID = config.get(config.CATEGORY_BLOCK,"Boss Door Block", blockID++).getInt();

		//Keys
		MegaX.instance.proxy.setKeyBinding("Weapon Change", config.get("Keybinds", "key.change", 48).getInt(48));
		MegaX.instance.proxy.setKeyBinding("Buster Menu", config.get("Keybinds", "key.menu", 48).getInt(49));

		config.save();
	}
	/**
	 * Load all the modules in the config file into memory. Eventually. For now, they are hardcoded.
	 */
	public static void loadPowerModules() {
		IPowerModule module;
		List<IModularItem> ALLITEMS = Arrays.asList((IModularItem) MegaX.buster, (IModularItem) MegaX.megaX1Helm, (IModularItem) MegaX.megaX1Chest, (IModularItem) MegaX.megaX1Belt, (IModularItem) MegaX.megaX1Boots);
		List<IModularItem> ARMORONLY = Arrays.asList((IModularItem) MegaX.megaX1Helm, (IModularItem) MegaX.megaX1Chest, (IModularItem) MegaX.megaX1Belt, (IModularItem) MegaX.megaX1Boots);
		List<IModularItem> HEADONLY = Arrays.asList((IModularItem) MegaX.megaX1Helm);
		List<IModularItem> CHESTONLY = Arrays.asList((IModularItem) MegaX.megaX1Chest);
		List<IModularItem> LEGSONLY = Arrays.asList((IModularItem) MegaX.megaX1Belt);
		List<IModularItem> FEETONLY = Arrays.asList((IModularItem) MegaX.megaX1Boots);
		List<IModularItem> BUSTERONLY = Arrays.asList((IModularItem) MegaX.buster);


		addModule(new BusterModule(BUSTERONLY));

		module = new PowerModule(MuseCommonStrings.MODULE_BASIC_PLATING, ARMORONLY, "basicplating2", MuseCommonStrings.CATEGORY_ARMOR)
		.setDescription("Basic plating is heavy but protective.").addInstallCost(copyAndResize(net.machinemuse.powersuits.item.ItemComponent.basicPlating, 1))
		.addTradeoffProperty("Plating Thickness", MuseCommonStrings.ARMOR_VALUE_PHYSICAL, 5, " Points")
		.addTradeoffProperty("Plating Thickness", MuseCommonStrings.WEIGHT, 10000, "g");
		addModule(module);

		module = new PowerModule(MuseCommonStrings.MODULE_DIAMOND_PLATING, ARMORONLY, "advancedplating2", MuseCommonStrings.CATEGORY_ARMOR)
		.setDescription("Advanced plating is lighter, harder, and more protective than Basic but much harder to make.")
		.addInstallCost(copyAndResize(net.machinemuse.powersuits.item.ItemComponent.advancedPlating, 1))
		.addTradeoffProperty("Plating Thickness", MuseCommonStrings.ARMOR_VALUE_PHYSICAL, 6, " Points")
		.addTradeoffProperty("Plating Thickness", MuseCommonStrings.WEIGHT, 6000, "g");
		addModule(module);

		module = new PowerModule(MuseCommonStrings.MODULE_ENERGY_SHIELD, ARMORONLY, "energyshield", MuseCommonStrings.CATEGORY_ARMOR)
		.setDescription("Energy shields are much lighter than plating, but consume energy.")
		.addInstallCost(copyAndResize(net.machinemuse.powersuits.item.ItemComponent.fieldEmitter, 2))
		.addTradeoffProperty("Field Strength", MuseCommonStrings.ARMOR_VALUE_ENERGY, 6, " Points")
		.addTradeoffProperty("Field Strength", MuseCommonStrings.ARMOR_ENERGY_CONSUMPTION, 500, "J");
		addModule(module);
		module = new PowerModule(MuseCommonStrings.MODULE_BATTERY_BASIC, ALLITEMS, "lvbattery", MuseCommonStrings.CATEGORY_ENERGY)
		.setDescription("Integrate a battery to allow the item to store energy.").addInstallCost(copyAndResize(net.machinemuse.powersuits.item.ItemComponent.lvcapacitor, 1))
		.addBaseProperty(ElectricItemUtils.MAXIMUM_ENERGY, 20000, "J").addBaseProperty(MuseCommonStrings.WEIGHT, 2000, "g")
		.addTradeoffProperty("Battery Size", ElectricItemUtils.MAXIMUM_ENERGY, 80000)
		.addTradeoffProperty("Battery Size", MuseCommonStrings.WEIGHT, 8000);
		addModule(module);

		module = new PowerModule(MuseCommonStrings.MODULE_BATTERY_ADVANCED, ALLITEMS, "mvbattery", MuseCommonStrings.CATEGORY_ENERGY)
		.setDescription("Integrate a more advanced battery to store more energy.")
		.addInstallCost(copyAndResize(net.machinemuse.powersuits.item.ItemComponent.mvcapacitor, 1)).addBaseProperty(ElectricItemUtils.MAXIMUM_ENERGY, 100000, "J")
		.addBaseProperty(MuseCommonStrings.WEIGHT, 2000, "g").addTradeoffProperty("Battery Size", ElectricItemUtils.MAXIMUM_ENERGY, 400000)
		.addTradeoffProperty("Battery Size", MuseCommonStrings.WEIGHT, 8000);
		addModule(module);

		module = new PowerModule(MuseCommonStrings.MODULE_BATTERY_ELITE, ALLITEMS, "crystalcapacitor", MuseCommonStrings.CATEGORY_ENERGY)
		.setDescription("Integrate a the most advanced battery to store an extensive amount of energy.")
		.addInstallCost(copyAndResize(net.machinemuse.powersuits.item.ItemComponent.hvcapacitor, 1)).addBaseProperty(ElectricItemUtils.MAXIMUM_ENERGY, 750000, "J")
		.addBaseProperty(MuseCommonStrings.WEIGHT, 2000, "g").addTradeoffProperty("Battery Size", ElectricItemUtils.MAXIMUM_ENERGY, 4250000)
		.addTradeoffProperty("Battery Size", MuseCommonStrings.WEIGHT, 8000);
		addModule(module);
		
		module = new ToggleablePowerModule(MuseCommonStrings.MODULE_TINT, ALLITEMS, "netherstar", MuseCommonStrings.CATEGORY_COSMETIC)
		.setDescription("Give your armor some coloured tinting to customize your armor's appearance.")
		.addInstallCost(copyAndResize(net.machinemuse.powersuits.item.ItemComponent.laserHologram, 1))
		.addTradeoffProperty("Red Intensity", MuseCommonStrings.RED_TINT, 1, "%")
		.addTradeoffProperty("Green Intensity", MuseCommonStrings.GREEN_TINT, 1, "%")
		.addTradeoffProperty("Blue Intensity", MuseCommonStrings.BLUE_TINT, 1, "%");
		addModule(module);
		module = new PowerModule(MuseCommonStrings.CITIZEN_JOE_STYLE, ARMORONLY, "greendrone", MuseCommonStrings.CATEGORY_COSMETIC)
		.setDescription("An alternative armor texture, c/o CitizenJoe of IC2 forums.");

		addModule(module);
		// Head ======================================
		// Torso =====================================
		// Legs =======================================
		// Feet =======================================
		addModule(new ShockAbsorberModule(FEETONLY));
	}

	public void addNames() {
		/** Names **/
		LanguageRegistry.addName(MegaX.buster, "X Buster");
		LanguageRegistry.addName(MegaX.healthBit, "Health Bit");
		LanguageRegistry.addName(MegaX.healthByte, "Health Byte");
		//LanguageRegistry.addName(MegaX.weaponBit, "Weapon Bit");
		//LanguageRegistry.addName(MegaX.weaponByte, "Weapon Byte");
		LanguageRegistry.addName(MegaX.healthTank, "Health Tank");
		//LanguageRegistry.addName(MegaX.weaponTank, "Weapon Tank");
		//LanguageRegistry.addName(MegaX.extraMan, "Extra Man");
		for(String a : ItemReploidPlate.plateColorNames)
		{
			LanguageRegistry.instance().addStringLocalization("item.reploidPlate."+a+".name", "en_US", a+" Reploid Plate");
		}
		for(String a : ItemComponent.componentNames)
		{
			LanguageRegistry.instance().addStringLocalization("item.component."+a+".name", "en_US", a );
		}

		LanguageRegistry.addName(MegaX.megaX1Helm, "MegaX Helm");
		LanguageRegistry.addName(MegaX.megaX1Chest, "MegaX Chest");
		LanguageRegistry.addName(MegaX.megaX1Belt, "MegaX Belt");
		LanguageRegistry.addName(MegaX.megaX1Boots, "MegaX Boots");

		LanguageRegistry.addName(MegaX.megaX1HelmEnhanced, "MegaX1 Helm Enhanced");
		LanguageRegistry.addName(MegaX.megaX1ChestEnhanced, "MegaX1 Chest Enhanced");
		LanguageRegistry.addName(MegaX.megaX1BeltEnhanced, "MegaX1 Belt Enhanced");
		LanguageRegistry.addName(MegaX.megaX1BootsEnhanced, "MegaX1 Boots Enhanced");

		LanguageRegistry.addName(MegaX.platformPlacer, "Platform Placer");
		//LanguageRegistry.addName(MegaX.doorBossItem, "Boss Door");

		//LanguageRegistry.addName(MegaX.upgradeStation, "Upgrade Station");
		LanguageRegistry.addName(MegaX.spikes, "Metal Spikes");
		//LanguageRegistry.addName(MegaX.doorBossBlock, "Boss Door");

		LanguageRegistry.instance().addStringLocalization("entity.Met.name", "en_US", "Met");
		LanguageRegistry.instance().addStringLocalization("entity.FloatingPlatform.name", "en_US", "Floating Platform");
		LanguageRegistry.instance().addStringLocalization("death.bullet", "en_US", "Pewpew Dead!");
		LanguageRegistry.instance().addStringLocalization("itemGroup.MegaX", "en_US", "MegaX");
		for(String a : ItemChip.chipTypeNames)
		{
			LanguageRegistry.instance().addStringLocalization("item.chip."+a+".name", "en_US", a);
		}
	}

	public void registerEntities() {
		/** Registers **/

		EntityRegistry.registerGlobalEntityID(EntityMet.class, "Met", EntityRegistry.findGlobalUniqueEntityId(), 0xFFFF00, 0x0F0000);
		EntityRegistry.registerModEntity(EntityMet.class, "Met", 2, MegaX.instance, 80, 3, true);
		EntityRegistry.addSpawn("Met", 3, 2, 4, EnumCreatureType.monster, BiomeGenBase.beach);
		EntityRegistry.registerGlobalEntityID(EntityFloatingPlatform.class, "FloatingPlatform", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityFloatingPlatform.class, "FloatingPlatform", 3, MegaX.instance, 80, 1, true);
		EntityRegistry.registerGlobalEntityID(EntityBusterBullet.class, "lemonBullet", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityBusterBullet.class, "lemonBullet", 1, MegaX.instance, 250, 1, true);
		EntityRegistry.registerGlobalEntityID(EntityMetBullet.class, "metBullet", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityMetBullet.class, "metBullet", 4, MegaX.instance, 250, 1, true);
	}

	public void addBlocks() {
		/** Blocks **/
		MegaX.spikes = new BlockSpikes(spikesID).setStepSound(new StepSound("stone", 1.0F, 1.5F)).setUnlocalizedName("spikes").setHardness(3.5F);
		//doorBossBlock = (new BlockBossDoor(doorBossBlockID, Material.iron)).setHardness(5.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("doorBoss").setRequiresSelfNotify();

		GameRegistry.registerBlock(MegaX.spikes, "spikes");
		//GameRegistry.registerBlock(doorBossBlock, "doorBoss");
	}
	public void addItems() {
		/** Items **/
		MegaX.buster = new ItemXBuster(busterID, 1, false).setUnlocalizedName("XBuster").setFull3D();
		// add weapon types
		// Guts type weapon that would act like a macerator
		// cut type weapon that act like shears
		// fire type weapon that acts like flint and steel
		// water type weapon that places a water(non-source) block
		MegaX.weaponChip = new ItemChip(weaponChipID).setUnlocalizedName("chip");

		MegaX.healthBit = new ItemHPEnergy(healthBitID, 0, 3, 0.5F).setPotionEffect(Potion.heal.id, 1, 0, 1.0F).setAlwaysEdible().setUnlocalizedName("healthBit").setMaxStackSize(1);
		MegaX.healthByte = new ItemHPEnergy(healthByteID, 1, 8, 0.8F).setPotionEffect(Potion.heal.id, 1, 1, 1.0F).setAlwaysEdible().setUnlocalizedName("healthByte").setMaxStackSize(1);
		//weaponBit = new ItemWPEnergy(weaponBitID).setItemName("Weapon Bit").setMaxStackSize(1);
		//weaponByte = new ItemWPEnergy(weaponByteID).setItemName("Weapon Byte").setMaxStackSize(1);
		MegaX.healthTank = new ItemTank(healthTankID).setUnlocalizedName("Health Tank").setMaxStackSize(1); //use EventBus to add to this, then use some other method to heal the player per heart
		//weaponTank = new ItemTank(weaponTankID).setItemName("Weapon Tank").setMaxStackSize(1);
		//extraMan = new ItemLife(extraManID).setItemName("Extra Man").setMaxStackSize(9); //possibly use ticker to instantly heal player?

		MegaX.reploidPlate = new ItemReploidPlate(reploidPlateID).setUnlocalizedName("reploidPlate");
		MegaX.component = new ItemComponent(componentID).setUnlocalizedName("component");


		MegaX.megaX1Helm = new ItemMegaX1Armor(megaX1HelmID, enumMegaX1Armor, MegaX.instance.proxy.addArmor("MegaXBasic"),0).setUnlocalizedName("MegaX1Helm");
		MegaX.megaX1Chest = new ItemMegaX1Armor(megaX1ChestID, enumMegaX1Armor, MegaX.instance.proxy.addArmor("MegaXBasic"),1).setUnlocalizedName("MegaX1Chest");
		MegaX.megaX1Belt = new ItemMegaX1Armor(megaX1BeltID, enumMegaX1Armor, MegaX.instance.proxy.addArmor("MegaXBasic"),2).setUnlocalizedName("MegaX1Belt");
		MegaX.megaX1Boots = new ItemMegaX1Armor(megaX1BootsID, enumMegaX1Armor, MegaX.instance.proxy.addArmor("MegaXBasic"),3).setUnlocalizedName("MegaX1Boots");

		MegaX.megaX1HelmEnhanced = new ItemMegaX1ArmorEnhanced(megaX1HelmEnhancedID, enumMegaX1ArmorEnhanced, MegaX.instance.proxy.addArmor("MegaX1ArmorEnhanced"),0).setUnlocalizedName("MegaX1HelmEnhanced");
		MegaX.megaX1ChestEnhanced = new ItemMegaX1ArmorEnhanced(megaX1ChestEnhancedID, enumMegaX1ArmorEnhanced, MegaX.instance.proxy.addArmor("MegaX1ArmorEnhanced"),1).setUnlocalizedName("MegaX1ChestEnhanced");
		MegaX.megaX1BeltEnhanced = new ItemMegaX1ArmorEnhanced(megaX1BeltEnhancedID, enumMegaX1ArmorEnhanced, MegaX.instance.proxy.addArmor("MegaX1ArmorEnhanced"),2).setUnlocalizedName("MegaX1BeltEnhanced");
		MegaX.megaX1BootsEnhanced = new ItemMegaX1ArmorEnhanced(megaX1BootsEnhancedID, enumMegaX1ArmorEnhanced, MegaX.instance.proxy.addArmor("MegaX1ArmorEnhanced"),3).setUnlocalizedName("MegaX1BootsEnhanced");

		MegaX.platformPlacer = new ItemPlatformPlacer(platformPlacerID).setUnlocalizedName("platformPlacer");
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
