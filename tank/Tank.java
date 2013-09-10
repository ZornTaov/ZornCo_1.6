package zornco.tank;

import java.util.logging.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import zornco.tank.client.TabTank;
import zornco.tank.core.CommonProxy;
import zornco.tank.core.Config;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="Tank", name="Tank", version="0.0.1")
@NetworkMod(/*packetHandler = PacketHandler.class, */channels={"Tank"}, clientSideRequired=true, serverSideRequired=false)
public class Tank {

	// The instance of your mod that Forge uses.
	@Instance("Tank")
	public static Tank instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="zornco.tank.client.ClientProxy", serverSide="zornco.tank.core.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs tanksTab = new TabTank("Tanks");

	public static Logger logger = Logger.getLogger("Tank");
	
	public static Config config = new Config();

	public static Item tankItem;
	public static final int bullettypes = 4;
	public static Item tankBullet[] = new Item[bullettypes];
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger.setParent(FMLLog.getLogger());
		config.loadConfig(event);
		proxy.registerKeyBindingHandler();
		proxy.registerSounds();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		config.addItems();
		config.addBlocks();
		config.addNames();
		Config.recipes.registerRecipes();
		proxy.registerRenderInformation();
		//events = new EventBus();
		//NetworkRegistry.instance().registerGuiHandler(this, proxy);

		config.registerEntities();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		//proxy.initTickHandlers();
	}
}
