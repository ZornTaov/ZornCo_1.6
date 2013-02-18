package zornco.megax;



import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.potion.Potion;
import net.minecraft.src.ModLoader;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import zornco.megax.blocks.BlockBossDoor;
import zornco.megax.blocks.BlockSpikes;
import zornco.megax.blocks.BlockUpgradeStation;
import zornco.megax.blocks.TileUpgradeStation;
import zornco.megax.bullets.EntityBusterBullet;
import zornco.megax.bullets.EntityMetBullet;
import zornco.megax.core.CommonProxy;
import zornco.megax.core.EventBus;
import zornco.megax.core.TabMegaX;
import zornco.megax.core.TickHandlerClient;
import zornco.megax.entities.EntityFloatingPlatform;
import zornco.megax.entities.EntityMet;
import zornco.megax.items.ItemBossDoor;
import zornco.megax.items.ItemChip;
import zornco.megax.items.ItemComponent;
import zornco.megax.items.ItemHPEnergy;
import zornco.megax.items.ItemPlatformPlacer;
import zornco.megax.items.ItemReploidPlate;
import zornco.megax.items.ItemTank;
import zornco.megax.items.ItemMegaXBase;
import zornco.megax.items.armors.ItemMegaX1Armor;
import zornco.megax.items.armors.ItemMegaX1ArmorEnhanced;
import zornco.megax.items.busters.ItemXBuster;
import zornco.megax.network.PacketHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid="MegaX", name="MegaX", version="0.0.1")
@NetworkMod(packetHandler = PacketHandler.class, channels={"MegaX"}, clientSideRequired=true, serverSideRequired=false)
public class MegaX {

	// The instance of your mod that Forge uses.
	@Instance("MegaX")
	public static MegaX instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="zornco.megax.client.ClientProxy", serverSide="zornco.megax.core.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs megaXTab = new TabMegaX("MegaX");

	public static Logger logger = Logger.getLogger("MegaX");

	public static Item buster;
	public static Item weaponChip;

	public static Item healthBit;
	public static Item healthByte;
	public static Item weaponBit;
	public static Item weaponByte;
	public static Item healthTank;
	public static Item weaponTank;
	public static Item extraMan;
	public static Item component;
	public static Item reploidPlate;

	public static Item megaX1Helm;
	public static Item megaX1Chest;
	public static Item megaX1Belt;
	public static Item megaX1Boots;
	public static Item megaX1HelmEnhanced;
	public static Item megaX1ChestEnhanced;
	public static Item megaX1BeltEnhanced;
	public static Item megaX1BootsEnhanced;

	public static Item platformPlacer;
	public static Item doorBossItem;

	public static Block upgradeStation;
	public static Block spikes;
	public static Block doorBossBlock;

	public static EventBus events;
	public static EnumAction busterAction = new EnumHelper().addAction("buster");
	public static EnumArmorMaterial enumMegaX1Armor = EnumHelper.addArmorMaterial("MegaX1Armor", 16, new int[]{2,7,6,3}, 20);
	public static EnumArmorMaterial enumMegaX1ArmorEnhanced = EnumHelper.addArmorMaterial("MegaX1ArmorEnhanced", 20, new int[]{3,8,7,4}, 30);

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
		upgradeStationID = config.get(config.CATEGORY_BLOCK,"Upgrade Station", blockID++).getInt();
		spikesID = config.get(config.CATEGORY_BLOCK,"Metal Spikes", blockID++).getInt();
		doorBossBlockID = config.get(config.CATEGORY_BLOCK,"Boss Door Block", blockID++).getInt();

		//Keys
		proxy.setKeyBinding("Weapon Change", config.get("Keybinds", "key.change", 48).getInt(48));
		proxy.setKeyBinding("Buster Menu", config.get("Keybinds", "key.menu", 48).getInt(49));

		config.save();
	}

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		// Stub Method
		logger.setParent(FMLLog.getLogger());
		loadConfig(event);
		proxy.registerKeyBindingHandler();

		//RenderPlayerAPI.register("MegaX", MegaXRenderPlayerBase.class);
		//ModelPlayerAPI.register("MegaX", MegaXModelPlayerBase.class);
		//TODO: add in crafting bench to add weapon modules to the buster

	}

	@Init
	public void load(FMLInitializationEvent event) {
		//PlayerAPI.register("MegaX", MegaXPlayerBase.class);
		//ServerPlayerAPI.register("MegaX", MegaXPlayerBaseServer.class);

		/** Items **/
		buster = new ItemXBuster(busterID, 1).setItemName("X Buster").setIconIndex(14).setFull3D();
		// add weapon types
		// Guts type weapon that would act like a macerator
		// cut type weapon that act like shears
		// fire type weapon that acts like flint and steel
		// water type weapon that places a water(non-source) block
		weaponChip = new ItemChip(weaponChipID).setItemName("chip").setIconIndex(13);

		healthBit = new ItemHPEnergy(healthBitID, 0, 3, 0.5F).setPotionEffect(Potion.heal.id, 1, 0, 1.0F).setAlwaysEdible().setItemName("healthBit").setIconIndex(14).setMaxStackSize(1);
		healthByte = new ItemHPEnergy(healthByteID, 1, 8, 0.8F).setPotionEffect(Potion.heal.id, 1, 1, 1.0F).setAlwaysEdible().setItemName("healthByte").setIconIndex(15).setMaxStackSize(1);
		//weaponBit = new ItemWPEnergy(weaponBitID).setItemName("Weapon Bit").setIconIndex(30).setMaxStackSize(1);
		//weaponByte = new ItemWPEnergy(weaponByteID).setItemName("Weapon Byte").setIconIndex(31).setMaxStackSize(1);
		healthTank = new ItemTank(healthTankID).setItemName("Health Tank").setIconIndex(46).setMaxStackSize(1); //use EventBus to add to this, then use some other method to heal the player per heart
		//weaponTank = new ItemTank(weaponTankID).setItemName("Weapon Tank").setIconIndex(47).setMaxStackSize(1);
		//extraMan = new ItemLife(extraManID).setItemName("Extra Man").setIconIndex(62).setMaxStackSize(9); //possibly use ticker to instantly heal player?

		reploidPlate = new ItemReploidPlate(reploidPlateID).setItemName("reploidPlate").setIconIndex(1);
		component = new ItemComponent(componentID).setItemName("component").setIconIndex(12);


		megaX1Helm = new ItemMegaX1Armor(megaX1HelmID, enumMegaX1Armor, proxy.addArmor("MegaXBasic"),0).setItemName("MegaX1Helm").setIconIndex(32);
		megaX1Chest = new ItemMegaX1Armor(megaX1ChestID, enumMegaX1Armor, proxy.addArmor("MegaXBasic"),1).setItemName("MegaX1Chest").setIconIndex(48);
		megaX1Belt = new ItemMegaX1Armor(megaX1BeltID, enumMegaX1Armor, proxy.addArmor("MegaXBasic"),2).setItemName("MegaX1Belt").setIconIndex(64);
		megaX1Boots = new ItemMegaX1Armor(megaX1BootsID, enumMegaX1Armor, proxy.addArmor("MegaXBasic"),3).setItemName("MegaX1Boots").setIconIndex(80);

		megaX1HelmEnhanced = new ItemMegaX1ArmorEnhanced(megaX1HelmEnhancedID, enumMegaX1ArmorEnhanced, proxy.addArmor("MegaX1ArmorEnhanced"),0).setItemName("MegaX1HelmEnhanced").setIconIndex(34);
		megaX1ChestEnhanced = new ItemMegaX1ArmorEnhanced(megaX1ChestEnhancedID, enumMegaX1ArmorEnhanced, proxy.addArmor("MegaX1ArmorEnhanced"),1).setItemName("MegaX1ChestEnhanced").setIconIndex(50);
		megaX1BeltEnhanced = new ItemMegaX1ArmorEnhanced(megaX1BeltEnhancedID, enumMegaX1ArmorEnhanced, proxy.addArmor("MegaX1ArmorEnhanced"),2).setItemName("MegaX1BeltEnhanced").setIconIndex(66);
		megaX1BootsEnhanced = new ItemMegaX1ArmorEnhanced(megaX1BootsEnhancedID, enumMegaX1ArmorEnhanced, proxy.addArmor("MegaX1ArmorEnhanced"),3).setItemName("MegaX1BootsEnhanced").setIconIndex(82);

		platformPlacer = new ItemPlatformPlacer(platformPlacerID).setItemName("platformPlacer").setIconIndex(3);
		//doorBossItem = (new ItemBossDoor(doorBossItemID, Material.iron)).setIconCoord(12, 2).setItemName("doorBoss");

		/** Blocks **/
		upgradeStation = new BlockUpgradeStation(upgradeStationID, false).setHardness(3.5F).setStepSound(new StepSound("stone", 1.0F, 1.5F)).setBlockName("upgradeStation").setRequiresSelfNotify();
		spikes = new BlockSpikes(spikesID).setStepSound(new StepSound("stone", 1.0F, 1.5F)).setBlockName("spikes").setHardness(3.5F);
		//doorBossBlock = (new BlockBossDoor(doorBossBlockID, Material.iron)).setHardness(5.0F).setStepSound(Block.soundMetalFootstep).setBlockName("doorBoss").setRequiresSelfNotify();


		GameRegistry.registerBlock(upgradeStation, "upgradeStation");
		GameRegistry.registerTileEntity(TileUpgradeStation.class, "tileupgradeStation");
		GameRegistry.registerBlock(spikes, "spikes");
		//GameRegistry.registerBlock(doorBossBlock, "doorBoss");

		/** Names **/
		LanguageRegistry.addName(buster, "X Buster");
		LanguageRegistry.addName(healthBit, "Health Bit");
		LanguageRegistry.addName(healthByte, "Health Byte");
		//LanguageRegistry.addName(weaponBit, "Weapon Bit");
		//LanguageRegistry.addName(weaponByte, "Weapon Byte");
		LanguageRegistry.addName(healthTank, "Health Tank");
		//LanguageRegistry.addName(weaponTank, "Weapon Tank");
		//LanguageRegistry.addName(extraMan, "Extra Man");
		for(String a : ItemReploidPlate.plateColorNames)
		{
			LanguageRegistry.instance().addStringLocalization("item.reploidPlate."+a+".name", "en_US", a+" Reploid Plate");
		}
		for(String a : ItemComponent.componentNames)
		{
			LanguageRegistry.instance().addStringLocalization("item.component."+a+".name", "en_US", a );
		}

		LanguageRegistry.addName(megaX1Helm, "MegaX Helm");
		LanguageRegistry.addName(megaX1Chest, "MegaX Chest");
		LanguageRegistry.addName(megaX1Belt, "MegaX Belt");
		LanguageRegistry.addName(megaX1Boots, "MegaX Boots");

		LanguageRegistry.addName(megaX1HelmEnhanced, "MegaX1 Helm Enhanced");
		LanguageRegistry.addName(megaX1ChestEnhanced, "MegaX1 Chest Enhanced");
		LanguageRegistry.addName(megaX1BeltEnhanced, "MegaX1 Belt Enhanced");
		LanguageRegistry.addName(megaX1BootsEnhanced, "MegaX1 Boots Enhanced");

		LanguageRegistry.addName(platformPlacer, "Platform Placer");
		//LanguageRegistry.addName(doorBossItem, "Boss Door");

		LanguageRegistry.addName(upgradeStation, "Upgrade Station");
		LanguageRegistry.addName(spikes, "Metal Spikes");
		//LanguageRegistry.addName(doorBossBlock, "Boss Door");

		LanguageRegistry.instance().addStringLocalization("entity.Met.name", "en_US", "Met");
		LanguageRegistry.instance().addStringLocalization("entity.FloatingPlatform.name", "en_US", "Floating Platform");
		LanguageRegistry.instance().addStringLocalization("death.bullet", "en_US", "Pewpew Dead!");
		for(String a : ItemChip.chipTypeNames)
		{
			LanguageRegistry.instance().addStringLocalization("item.chip."+a+".name", "en_US", a+"-Chip");
		}

		/** Recipes **/
		//GameRegistry.addRecipe(new ItemStack(Item.monsterPlacer, 1, metID), new Object[] { "   ", " # ", "XXX", Character.valueOf('#'), Block.dirt , Character.valueOf('X'), Block.planks });

		//TEMP! will make a block that will infuse the ingots with the material, might keep and have this be an option
		GameRegistry.addShapelessRecipe(new ItemStack(reploidPlate, 1, 16), // basic plate
				new Object[]{ Item.ingotIron, Item.redstone } );
		String[] dyes = 
			{
				"dyeBlack",
				"dyeRed",
				"dyeGreen",
				"dyeBrown",
				"dyeBlue",
				"dyePurple",
				"dyeCyan",
				"dyeLightGray",
				"dyeGray",
				"dyePink",
				"dyeLime",
				"dyeYellow",
				"dyeLightBlue",
				"dyeMagenta",
				"dyeOrange"
			};
		for(int i = 0; i < 15; i++)
			addShapelessOreRecipe(new ItemStack(reploidPlate, 1, i), // blue plate
					new Object[]{new ItemStack(reploidPlate, 1, 16), dyes[i] } );

		GameRegistry.addShapelessRecipe(new ItemStack(reploidPlate, 1, 15), // white plate	
				new Object[]{ new ItemStack(component, 1, 0), new ItemStack(reploidPlate, 1, 4) } );

		GameRegistry.addRecipe(new ItemStack(component, 4, 0), //diamond dust
				new Object[] { 
			"ttt", "tdt", "ttt", 
			Character.valueOf('t'), Block.tnt, 
			Character.valueOf('d'), Item.diamond // diamond dust\
		}
				);

		GameRegistry.addRecipe(new ItemStack(component, 1, 1), // AEDS
				new Object[] { 
			"rdr", "dpd", "rdr", 
			Character.valueOf('r'), Item.redstone, 
			Character.valueOf('d'), new ItemStack(component, 1, 0), // diamond dust
			Character.valueOf('p'), Item.enderPearl // use quartz block
		}
				);

		GameRegistry.addRecipe(new ItemStack(healthTank, 1, 30), 
				new Object[] { 
			"ihi", "bHb", "bdb", 
			Character.valueOf('i'), Item.ingotIron, 
			Character.valueOf('b'), new ItemStack(reploidPlate, 1, 4),
			Character.valueOf('h'), healthBit,
			Character.valueOf('H'), healthByte,
			Character.valueOf('d'), Item.diamond
		}
				);

		GameRegistry.addRecipe(new ItemStack(upgradeStation), 
				new Object[] { 
			"pcp", "pbp", "pdp",  
			Character.valueOf('p'), new ItemStack(reploidPlate, 1, 4),
			Character.valueOf('b'), Block.blockSteel,
			Character.valueOf('c'), weaponChip,
			Character.valueOf('d'), Item.redstone
		}
				);

		GameRegistry.addRecipe(new ItemStack(megaX1Helm), 
				new Object[] { 
			"pep", "d d",  
			Character.valueOf('p'), new ItemStack(reploidPlate, 1, 4),
			Character.valueOf('e'), new ItemStack(reploidPlate, 1, 1),
			Character.valueOf('d'), new ItemStack(reploidPlate, 1, 12)
		}
				);

		GameRegistry.addRecipe(new ItemStack(megaX1Chest), 
				new Object[] { 
			"p p", "pep", "ddd",  
			Character.valueOf('p'), new ItemStack(reploidPlate, 1, 4),
			Character.valueOf('e'), new ItemStack(component, 1, 1),
			Character.valueOf('d'), new ItemStack(reploidPlate, 1, 12)
		}
				);
		
		GameRegistry.addRecipe(new ItemStack(megaX1Belt), 
				new Object[] { 
			"ppp", "d d", "d d",  
			Character.valueOf('p'), new ItemStack(reploidPlate, 1, 4),
			Character.valueOf('d'), new ItemStack(reploidPlate, 1, 12)
		}
				);
		
		GameRegistry.addRecipe(new ItemStack(megaX1Boots), 
				new Object[] { 
			"p p", "d d",   
			Character.valueOf('p'), new ItemStack(reploidPlate, 1, 4),
			Character.valueOf('d'), new ItemStack(reploidPlate, 1, 12)
		}
				);
		
		GameRegistry.addRecipe(new ItemStack(buster), 
				new Object[] { 
			"pcp", "dep", "ppp",  
			Character.valueOf('p'), new ItemStack(reploidPlate, 1, 4),
			Character.valueOf('e'), new ItemStack(component, 1, 1),
			Character.valueOf('c'), Item.lightStoneDust,
			Character.valueOf('d'), new ItemStack(reploidPlate, 1, 8)
		}
				);

		GameRegistry.addRecipe(new ItemStack(spikes, 8), 
				new Object[] { 
			" i ", " i ", "bbb", 
			Character.valueOf('i'), new ItemStack(reploidPlate, 1, 7), 
			Character.valueOf('b'), Block.blockSteel
		}
				);

		/*GameRegistry.addRecipe(new ItemStack(doorBossItem, 1), 
				new Object[] { 
			"ipi", "i i", "ipi", 
			Character.valueOf('i'), Item.ingotIron, 
			Character.valueOf('b'), Block.blockSteel
			}
		);*/

		/*GameRegistry.addRecipe(new ItemStack(weaponTank), 
		new Object[] { 
			"iwi", "bWb", "bdb", 
			Character.valueOf('i'), Item.ingotIron, 
			Character.valueOf('b'), blueReploidPlate,
			Character.valueOf('w'), weaponBit,
			Character.valueOf('W'), weaponByte,
			Character.valueOf('d'), Item.diamond
			}
		);*/



		/** Registers **/
		proxy.registerRenderInformation();
		events = new EventBus();
		NetworkRegistry.instance().registerGuiHandler(this, proxy);

		EntityRegistry.registerGlobalEntityID(EntityMet.class, "Met", EntityRegistry.findGlobalUniqueEntityId(), 0xFFFF00, 0x0F0000);
		EntityRegistry.registerModEntity(EntityMet.class, "Met", 2, this, 80, 3, true);
		EntityRegistry.addSpawn("Met", 3, 2, 4, EnumCreatureType.monster, BiomeGenBase.beach);
		EntityRegistry.registerGlobalEntityID(EntityFloatingPlatform.class, "FloatingPlatform", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityFloatingPlatform.class, "FloatingPlatform", 3, this, 80, 1, true);
		EntityRegistry.registerGlobalEntityID(EntityBusterBullet.class, "lemonBullet", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityBusterBullet.class, "lemonBullet", 1, this, 250, 1, true);
		EntityRegistry.registerGlobalEntityID(EntityMetBullet.class, "metBullet", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityMetBullet.class, "metBullet", 4, this, 250, 1, true);
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		proxy.initTickHandlers();
	}
	public static void addOreRecipe(ItemStack output, Object[] input) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, new Object[] { Boolean.valueOf(true), input }));
	}

	public static void addShapelessOreRecipe(ItemStack output, Object[] input)
	{
		CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, input));
	}
}