package zornco.diceroller;

import java.util.logging.Logger;

import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid="DiceRoller", name="DiceRoller", version="0.1")
public class DiceRoller {

	@Instance("DiceRoller")
	public static DiceRoller instance;

	public static Logger logger = Logger.getLogger("DiceRoller");
	public Parser equ;
	
	@EventHandler
	public void load(FMLPostInitializationEvent event) {
		equ = new Parser();
		logger.info("DiceRoller has been enabled!");
	}
	
	@EventHandler
	public void registerCommands(FMLServerStartingEvent event) {
		MinecraftServer server = event.getServer();
		((ServerCommandManager) server.getCommandManager()).registerCommand(new CommandDice());
	}
}
