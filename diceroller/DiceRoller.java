package zornco.diceroller;

import java.util.logging.Logger;

import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="DiceRoller", name="DiceRoller", version="0.1")
public class DiceRoller {

	@Instance("DiceRoller")
	public static DiceRoller instance;

	public static Logger logger = Logger.getLogger("DiceRoller");
	public Parser equ;
	
	@PostInit
	public void load(FMLPostInitializationEvent event) {
		equ = new Parser();
		logger.info("DiceRoller has been enabled!");
	}
	
	@ServerStarting
	public void registerCommands(FMLServerStartingEvent event) {
		MinecraftServer server = event.getServer();
		((ServerCommandManager) server.getCommandManager()).registerCommand(new CommandDice());
	}
}
