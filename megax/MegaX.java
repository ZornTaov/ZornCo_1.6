package zornco.megax;



import java.util.Random;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraftforge.common.EnumHelper;
import zornco.megax.core.CommonProxy;
import zornco.megax.core.Config;
import zornco.megax.core.EventBus;
import zornco.megax.core.TabMegaX;
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

@Mod(modid="MegaX", name="MegaX", version="0.0.1", dependencies = "required-after:mmmPowersuits", acceptedMinecraftVersions = "[1.5,)")
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

	public static Config config = new Config();

	public static Random rand = new Random();
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

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		// Stub Method
		logger.setParent(FMLLog.getLogger());
		config.loadConfig(event);
		proxy.registerKeyBindingHandler();
		proxy.registerSounds();
		//RenderPlayerAPI.register("MegaX", MegaXRenderPlayerBase.class);
		//ModelPlayerAPI.register("MegaX", MegaXModelPlayerBase.class);
		//TODO: add in crafting bench to add weapon modules to the buster

	}

	@Init
	public void load(FMLInitializationEvent event) {
		//PlayerAPI.register("MegaX", MegaXPlayerBase.class);
		//ServerPlayerAPI.register("MegaX", MegaXPlayerBaseServer.class);
		config.addItems();
		config.addBlocks();
		config.addNames();
		config.recipes.registerRecipes();
		config.loadPowerModules();
		proxy.registerRenderInformation();
		events = new EventBus();
		NetworkRegistry.instance().registerGuiHandler(this, proxy);

		config.registerEntities();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		proxy.initTickHandlers();
	}
}