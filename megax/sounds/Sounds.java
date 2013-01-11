package zornco.megax.sounds;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.common.FMLCommonHandler;

public class Sounds {
	private static final String SOUND_RESOURCE_LOCATION = "zornco/megax/sounds/";
	private static final String SOUND_PREFIX = "zornco.megax.sounds.";
	public static String[] soundFiles = { "zornco/megax/sounds/bit.ogg", "zornco/megax/sounds/byte.ogg", "zornco/megax/sounds/chargeCont.ogg", "zornco/megax/sounds/chargeUp.ogg" };
	public static final String BIT = "zornco.megax.sounds.bit";
	public static final String BYTE = "zornco.megax.sounds.byte";
	public static final String CHARGEUP = "zornco.megax.sounds.chargeUp";
	public static final String CHARGECONT = "zornco.megax.sounds.chargeCont";
	
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
				event.manager.soundPoolSounds.addSound(soundFile, new File(this.getClass().getResource("/" + soundFile).toURI()));
			}
			// If we cannot add the custom sound file to the pool, log the exception
			catch (Exception e) {
				FMLCommonHandler.instance().getFMLLogger().log(Level.WARNING, "Failed loading sound file: " + soundFile);
			}
		}
		System.out.println("MegaX Sounds Loaded!");
	} 
}
