package zornco.tntwarn;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="TNTWarn", name="TNTWarn", version="0.0.1")
public class TNTWarn {

	// The instance of your mod that Forge uses.
	@Instance("TNTWarn")
	public static TNTWarn instance;

    private static FMLLogFormatter formatter;
	public FileHandler handler;
	public static Logger logger = Logger.getLogger("TNTWarn");
	public static Logger logger2 = Logger.getLogger("TNTWarnLog");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// Stub Method
		logger.setParent(FMLLog.getLogger());
		try {
			makeLogFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {

		registerEvent();
	}

	public void registerEvent () {
		MinecraftForge.EVENT_BUS.register(this);
		TNTWarn.logger.info("subscribed");
		TNTWarn.logger2.info("subscribed");
	}
	@ForgeSubscribe
	public void blockevnt(HarvestDropsEvent event)
	{
		String s = "";
		if(event.block instanceof BlockTNT)
		{
			s += "Break TNT " + event.harvester.username + " " + event.harvester.dimension + " "  + event.x + " " + event.y + " " + event.z;
		}

		if(s != "")
		{
		TNTWarn.logger.warning(s);
		TNTWarn.logger2.warning(s);
		}

	}
	@ForgeSubscribe
	public void playerInteract(PlayerInteractEvent event)
	{
		String s = "";
		if(event.action == Action.RIGHT_CLICK_BLOCK) // Works perfectly
		{
			if(event.entityPlayer.getCurrentEquippedItem() != null)
			{
				if(event.entityPlayer.getCurrentEquippedItem().getItem().itemID == Block.tnt.blockID)
				{ 
					s += ("Place TNT " + event.entityPlayer.username + " " + event.entityPlayer.dimension + " " + event.x + " " + event.y + " " + event.z);
				}
				else if(event.entityPlayer.getCurrentEquippedItem().getItem().itemID == Item.bucketLava.itemID)
				{
					s += ("Place LAVA " + event.entityPlayer.username + " " + event.entityPlayer.dimension + " " + event.x + " " + event.y + " " + event.z);
				}
				if(s != "")
				{
				TNTWarn.logger.warning(s);
				TNTWarn.logger2.warning(s);
				}
			}
		}
	}
	
	public void makeLogFile() throws Exception
	{
		handler = new FileHandler("tnt-%g.log", 0, 3);
		handler.setFormatter(new FMLLogFormatter());
		logger2.addHandler(handler);
		
	}
	final class FMLLogFormatter extends Formatter
	{
	    final String LINE_SEPARATOR = System.getProperty("line.separator");
	    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	    public String format(LogRecord record)
	    {
	        StringBuilder msg = new StringBuilder();
	        msg.append(this.dateFormat.format(Long.valueOf(record.getMillis())));
	        Level lvl = record.getLevel();

	        String name = lvl.getLocalizedName();
	        if ( name == null )
	        {
	            name = lvl.getName();        	
	        }

	        if ( ( name != null ) && ( name.length() > 0 ) )
	        {
	            msg.append(" [" + name + "] ");
	        }
	        else
	        {
	            msg.append(" ");
	        }

	        if (record.getLoggerName() != null)
	        {
	            msg.append("["+record.getLoggerName()+"] ");
	        }
	        else
	        {
	            msg.append("[] ");
	        }
	        msg.append(record.getMessage());
	        msg.append(LINE_SEPARATOR);
	        Throwable thr = record.getThrown();

	        if (thr != null)
	        {
	            StringWriter thrDump = new StringWriter();
	            thr.printStackTrace(new PrintWriter(thrDump));
	            msg.append(thrDump.toString());
	        }

	        return msg.toString();
	    }
	}
}