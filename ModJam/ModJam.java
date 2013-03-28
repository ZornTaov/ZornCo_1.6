package zornco.modjam;



import java.util.Random;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraftforge.common.EnumHelper;
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

@Mod(modid="ModJam", name="ModJam", version="0.0.1", acceptedMinecraftVersions = "[1.5,)")
@NetworkMod(channels={"ModJam"}, clientSideRequired=true, serverSideRequired=false)
public class ModJam {

	// The instance of your mod that Forge uses.
	@Instance("ModJam")
	public static ModJam instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="zornco.modjam.client.ClientProxy", serverSide="zornco.modjam.core.CommonProxy")
	public static CommonProxy proxy;

	public static Logger logger = Logger.getLogger("ModJam");

	public static Random rand = new Random();

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		logger.setParent(FMLLog.getLogger());
	}

	@Init
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderInformation();
		config.registerEntities();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
	}
}