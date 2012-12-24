package zornco.fps;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.*;
import net.minecraft.util.StringTranslate;

public class GuiFriendFoe extends GuiScreen {

	public static int selectedNameId;
	public static int selectedListId = 0;

	/**
	 * Foe, Neutral, Friend
	 */
	public float[] friendRGB = {0,0,0};
	public float[] enemyRGB = {0,0,0};
	public float[] neutralRGB = {0,0,0};
	public final String[] colorRGBName = {"R","G","B"};
	protected String screenTitle = "FPS Options";

	public List<EntityPlayer> currentPlayerList; 

	public HashMap neutralList;
	public HashMap friendList;
	public HashMap enemyList;

	public GuiSlotFriendFoe neutralSlot;
	public GuiSlotFriendFoe friendSlot;
	public GuiSlotFriendFoe enemySlot;
	
	public GuiCheckBox showHudInfoCheckBox;
	private boolean showHudInfo;
	private static final int showHudInfoId = 500;
	public GuiCheckBox showOtherPosCheckBox;
	private boolean showOtherPos;
	private static final int showOtherPosId = 501;

	private static final int doneButtonId = 100;
	private static final int friendButtonId = 200;
	private static final int friendRGBSliderId = 201;
	public static final int friendListId = 250;
	private static final int neutralButtonId = 300;
	private static final int neutralRGBSliderId = 301;
	public static final int neutralListId = 350;
	private static final int enemyButtonId = 400;
	private static final int enemyRGBSliderId = 401;
	public static final int enemyListId = 450;

	private FPS mod;

	public GuiFriendFoe(FPS myMod_FPS, 
			List<EntityPlayer> currentPlayerList, HashMap friend, HashMap neutral, HashMap enemy, 
			int fColor, int nColor, int eColor, 
			boolean showHudInfo, boolean showOtherPos) 
	{
		this.mod = myMod_FPS;
		this.currentPlayerList = currentPlayerList;
		this.friendList = friend;
		this.enemyList = enemy;
		this.neutralList = neutral;
		hex2RGB(fColor,this.friendRGB);
		hex2RGB(eColor,this.enemyRGB);
		hex2RGB(nColor,this.neutralRGB);
		this.showHudInfo = showHudInfo;
		this.showOtherPos = showOtherPos;

	}
	@Override
	public boolean doesGuiPauseGame()
	{
		return true;
	}

	@Override
	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 10, 0x00FFFFFF);
		this.friendSlot.drawScreen(par1, par2, par3);
		this.neutralSlot.drawScreen(par1, par2, par3);
		this.enemySlot.drawScreen(par1, par2, par3);
		super.drawScreen(par1, par2, par3);
	}
	@Override
	public void initGui()
	{
		StringTranslate translation = StringTranslate.getInstance();

		// "slot" listers for the three lists
		this.friendSlot  = new GuiSlotFriendFoe(this, 30, this.width / 2 - 155, 100, 100, this.friendList,  friendListId, "Friends");
		this.neutralSlot = new GuiSlotFriendFoe(this, 30, this.width / 2 - 50 , 100, 100, this.neutralList, neutralListId, "Neutral");
		this.enemySlot   = new GuiSlotFriendFoe(this, 30, this.width / 2 + 55 , 100, 100, this.enemyList,   enemyListId, "Enemies");

		// make friend button
		this.controlList.add(
				new GuiButton(
						this.friendButtonId,
						this.width / 2 - 155, 132,
						100, 20,
						"Make Friend"));

		// make neutral button
		this.controlList.add(
				new GuiButton(
						this.neutralButtonId,
						this.width / 2 - 50, 132,
						100, 20,
						"Make Neutral"));

		// make enemy button
		this.controlList.add(
				new GuiButton(
						this.enemyButtonId,
						this.width / 2 + 55, 132,
						100, 20,
						"Make Enemy"));



		// RGB sliders for Friend/Enemy/Neutral colors

		this.addRGBSliders(friendRGBSliderId , this.friendRGB , this.width / 2 - 155, 154);
		this.addRGBSliders(neutralRGBSliderId, this.neutralRGB, this.width / 2 - 50 , 154);
		this.addRGBSliders(enemyRGBSliderId  , this.enemyRGB  , this.width / 2 + 55 , 154); 

		// checkbox for show hud info
		this.showHudInfoCheckBox = new GuiCheckBox(
				this.showHudInfoId,
				this.width / 2 - 155, 186,
				"Show your info",
				this.showHudInfo);
		this.controlList.add(this.showHudInfoCheckBox);
		this.showOtherPosCheckBox = new GuiCheckBox(
				this.showOtherPosId,
				this.width / 2 , 186,
				"Show other player position",
				this.showOtherPos);
		this.controlList.add(this.showOtherPosCheckBox);
		
		
		// done button
		this.controlList.add(
				new GuiButton(
						this.doneButtonId,
						this.width / 2 - 100,
						this.height / 6 + 168,
						translation.translateKey("gui.done")));

	}

	@Override
	protected void keyTyped(char par1, int par2)
	{
		if (par2 == 1)
		{
			this.mod.loadLists();
			this.mod.loadOptions();
		}
		super.keyTyped(par1, par2);
	}

	public void addRGBSliders(int id, float[] RGB, int x, int y) {
		for (int row = 0; row < 3; ++row)
			this.controlList.add(
					new GuiColorSlider(
							RGB,
							id + row, 
							x,
							y + 10 * row, 
							colorRGBName[row]+" ",
							RGB[row],
							row
							)
					);

	}


	public void saveData()
	{
		this.mod.friendArrowColor = RGB2Hex(this.friendRGB);
		this.mod.neutralArrowColor = RGB2Hex(this.neutralRGB);
		this.mod.enemyArrowColor = RGB2Hex(this.enemyRGB);
		this.mod.showHudInfo = this.showHudInfoCheckBox.getState();
		this.mod.showOtherPos = this.showOtherPosCheckBox.getState();
		this.mod.saveLists();
		this.mod.saveOptions();
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton clickedButton)
	{
		if (clickedButton.enabled)
		{
			switch(clickedButton.id){
			case doneButtonId:
				this.saveData();
				this.mc.displayGuiScreen((GuiScreen)null);
				this.mc.setIngameFocus();
				break;

			case friendButtonId:
				if(getSelectedListId() != 0)
					if(getSelectedListId() != friendListId)
						moveSelectedName(getSelectedSlot(), friendSlot);
				break;

			case neutralButtonId:
				if(getSelectedListId() != 0)
					if(getSelectedListId() != neutralListId)
						moveSelectedName(getSelectedSlot(), neutralSlot);
				break;

			case enemyButtonId:
				if(getSelectedListId() != 0)
					if(getSelectedListId() != enemyListId)
						moveSelectedName(getSelectedSlot(), enemySlot);
				break;

			}

		}
	}


	private GuiSlotFriendFoe getSelectedSlot()
	{
		switch(getSelectedListId())
		{
		case enemyListId:	
			return enemySlot;
		case friendListId:
			return friendSlot;
		case neutralListId:
			return neutralSlot;
		default:
			return null;
		}
	}
	private void moveSelectedName(GuiSlotFriendFoe source, GuiSlotFriendFoe target)
	{
		String name;
		if((name = source.getSelectedName()) != null)
		if(!target.getHashMap().containsKey(name))
		{
			target.getHashMap().put(name, source.getHashMap().get(name));
			source.getHashMap().remove(name);
			target.updateList();
			source.updateList();
		}
	}
	/**
	 * called whenever an element in this gui is selected
	 */
	public void onElementSelected(int slotId, int nameId)
	{
		this.selectedListId = slotId;
		this.selectedNameId = nameId;
	}

	/**
	 * returns the name currently selected
	 */
	public int getSelectedNameId()
	{
		return this.selectedNameId;
	}
	public int getSelectedListId()
	{
		return this.selectedListId;
	}


	private void hex2RGB(int hex, float[] RGB)
	{
		RGB[0] = (float)(hex >> 16 & 255) / 255.0F; //red  
		RGB[1] = (float)(hex >> 8 & 255) / 255.0F;  //green
		RGB[2] = (float)(hex & 255) / 255.0F;       //blue 
	}

	public static int RGB2Hex(float red, float green, float blue)
	{
		return 
				((int)(red * 255.0F) << 16)  | 
				((int)(green * 255.0F) << 8) | 
				(int)(blue * 255.0F);
	}

	public static int RGB2Hex(float[] colors)
	{
		return
				((int)(colors[0] * 255.0F) << 16) | //red
				((int)(colors[1] * 255.0F) << 8)  | //green
				(int)(colors[2] * 255.0F);			//blue
	}
}
