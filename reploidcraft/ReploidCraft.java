package zornco.reploidcraft;



import java.util.Random;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraftforge.common.EnumHelper;
import zornco.reploidcraft.core.CommonProxy;
import zornco.reploidcraft.core.Config;
import zornco.reploidcraft.core.EventBus;
import zornco.reploidcraft.core.TabReploid;
import zornco.reploidcraft.network.PacketHandler;
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

@Mod(modid="Reploid", name="Reploid", version="0.0.1", dependencies = "required-after:mmmPowersuits", acceptedMinecraftVersions = "[1.5,)")
@NetworkMod(packetHandler = PacketHandler.class, channels={"Reploid"}, clientSideRequired=true, serverSideRequired=false)
public class ReploidCraft {

	// The instance of your mod that Forge uses.
	@Instance("Reploid")
	public static ReploidCraft instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="zornco.reploidcraft.client.ClientProxy", serverSide="zornco.reploidcraft.core.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs reploidTab = new TabReploid("Reploid");

	public static Logger logger = Logger.getLogger("Reploid");

	public static Random rand = new Random();

	public static Config config = new Config();

	public static EventBus events;
	
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

	public static Item reploidHelm;
	public static Item reploidChest;
	public static Item reploidBelt;
	public static Item reploidBoots;

	public static Item platformPlacer;
	public static Item doorBossItem;

	public static Block spikes;
	public static Block doorBossBlock;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		logger.setParent(FMLLog.getLogger());
		config.loadConfig(event);
		proxy.registerKeyBindingHandler();
		proxy.registerSounds();
	}

	@Init
	public void load(FMLInitializationEvent event) {
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