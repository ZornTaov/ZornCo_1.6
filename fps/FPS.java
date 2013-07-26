package zornco.fps;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// central controller for mod
@Mod(modid="FPS", name="FPS", version="1.0.0") // uncomment this to compile
public class FPS {
	
	@SidedProxy(clientSide="zornco.fps.ClientProxy", serverSide="zornco.fps.CommonProxy")
	public static CommonProxy proxy;
	
	public static List<EntityPlayer> currentPlayerList = new ArrayList<EntityPlayer>();
	public boolean needsToRefresh;

	public static boolean useOldSkinServ = false;
	public static HashMap neutralList = new HashMap();
	public static HashMap friendList = new HashMap();
	public static HashMap enemyList = new HashMap();
	public static final String friendFilename = "friends.list";
	public static final String enemyFilename = "enemies.list";
	public static final String optionsFilename = "conf";

	public static int friendArrowColor = 0x0000FF00; // the green
	public static int enemyArrowColor = 0x00FF0000; // the red
	public static int neutralArrowColor = 0x00FFFF00; // the yellow

	public static boolean showHudInfo = true;
	public static boolean showOtherPos = true;

	// The instance of your mod that Forge uses.
	@Instance("FPS")
	public static FPS instance;

	


	public FPS() {
	}

	public int getPlayerCount() {
		return currentPlayerList.size();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {

		proxy.init();
		this.needsToRefresh = false;
		this.proxy.loadOptions();
		this.loadLists();
		this.proxy.saveOptions();
		this.saveLists();
		System.out.println("FPS Loaded");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		TickRegistry.registerTickHandler(new TickHandlerClient(), Side.CLIENT);
		System.out.println("FPS post");
	}
	public void saveLists()
	{
		proxy.saveList(this.friendFilename,this.friendList);
		proxy.saveList(this.enemyFilename,this.enemyList);
	}
	public void loadLists()
	{
		proxy.loadList(this.friendFilename,this.friendList);
		proxy.loadList(this.enemyFilename,this.enemyList);
	}
}
