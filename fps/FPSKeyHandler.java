package zornco.fps;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.src.ModLoader;

public class FPSKeyHandler extends KeyHandler {
	static KeyBinding show = new KeyBinding("Show FPS Menu", Keyboard.KEY_Y);

	private GuiFriendFoe guiFF;
	public FPSKeyHandler() {
		//the first value is an array of KeyBindings, the second is whether or not the call
		//keyDown should repeat as long as the key is down
		super(new KeyBinding[]{show}, new boolean[]{false});
	}

	@Override
	public String getLabel() {
		return "fpskeybindings";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,
			boolean tickEnd, boolean isRepeat) {
		if (kb == this.show) {
			if(Minecraft.getMinecraft().currentScreen == null)
			{
				this.guiFF = new GuiFriendFoe(
						FPS.instance,
						FPS.instance.currentPlayerList, 
						FPS.instance.friendList, FPS.instance.neutralList, FPS.instance.enemyList,
						FPS.instance.friendArrowColor, FPS.instance.neutralArrowColor, FPS.instance.enemyArrowColor,
						FPS.instance.showHudInfo, FPS.instance.showOtherPos
						);
				ModLoader.openGUI(Minecraft.getMinecraft().thePlayer, this.guiFF);
			}

		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		//do whatever
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
		//I am unsure if any different TickTypes have any different effects.
	}
}
