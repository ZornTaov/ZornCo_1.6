package zornco.reploidcraft.sounds;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.common.FMLCommonHandler;

public class Sounds {
	private static final String SOUND_RESOURCE_LOCATION = "zornco/reploidcraft/sounds/";
	private static final String SOUND_PREFIX = "zornco.reploidcraft.sounds.";
	public static String[] soundFiles = { "zornco/reploidcraft/sounds/bit.ogg", "zornco/reploidcraft/sounds/byte.ogg", "zornco/reploidcraft/sounds/chargeCont.ogg", "zornco/reploidcraft/sounds/chargeUp.ogg" };
	public static final String BIT = "zornco.reploidcraft.sounds.bit";
	public static final String BYTE = "zornco.reploidcraft.sounds.byte";
	public static final String CHARGEUP = "zornco.reploidcraft.sounds.chargeUp";
	public static final String CHARGECONT = "zornco.reploidcraft.sounds.chargeCont";
	
	public Sounds()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}
	@ForgeSubscribe
	public void onSoundLoad(SoundLoadEvent event)
	{
		// For each custom sound file we have defined in Sounds
		for (String soundFile : Sounds.soundFiles) {
			// Try to add the custom sound file to the pool of sounds
			try {
				event.manager.soundPoolSounds.addSound(soundFile, this.getClass().getResource("/" + soundFile));
			}
			// If we cannot add the custom sound file to the pool, log the exception
			catch (Exception e) {
				FMLCommonHandler.instance().getFMLLogger().log(Level.WARNING, "Failed loading sound file: " + soundFile);
			}
		}
		//System.out.println("ReploidCraft Sounds Loaded!");
	} 
}
