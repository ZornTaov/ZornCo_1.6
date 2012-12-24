package zornco.fps;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.HashMap;
import java.util.logging.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.src.ModLoader;

public class GuiSlotFriendFoe  {
	public static final Logger myLogger = Logger.getLogger("Minecraft");

	private GuiFriendFoe parentGuiFF;
	private ArrayList myList = new ArrayList();
	private HashMap myHashMap;
	private int slotId;
	private int nameId;

	private final Minecraft mc;

	public int top;
	public int bottom;
	public int right;
	public int left;
	public int width;
	public int height;

	/** The height of a slot. */
	private static final int slotHeight = 9;

	protected int mouseX;
	protected int mouseY;

	/** how far down this slot has been scrolled */
	private float amountScrolled = 0.0F;
	private int scrollBarTop = 0;
	private boolean scrolling = false;

	/** the element in the list that was selected */
	private int selectedElement = -1;

	/** the time when this button was last clicked. */
	private long lastClicked = 0L;
	private boolean showSelectionBox = true;

	/** the title of the scroll box */
	private String title;

	/**
	 * GuiSlotFriendFoe
	 */
	public GuiSlotFriendFoe(GuiFriendFoe guiFF, int top, int left, int width, int height, HashMap list, int id, String title) {

		this.mc =  ModLoader.getMinecraftInstance().getMinecraft();

		this.width = width;
		this.height = height;
		this.top = top;
		this.bottom = top + height;
		this.left = left;
		this.right = left + width;

		this.title = title;
		
		this.slotId = id;

		this.parentGuiFF = guiFF;
		this.myHashMap = list;
		this.updateList();
	}
	
	/*
	 * Updazte internal list
	 */
	public void updateList() {
		this.myList.clear();
		Iterator item = this.myHashMap.keySet().iterator();

		while (item.hasNext())
		{
			String name = (String)item.next();
			this.myList.add(name);
		}
		this.nameId = -1;
		
	}

	protected int getSize() {
		return this.myList.size();
	}	

	public int getNameId() {
		return this.nameId;
	}
	public int getSlotId() {
		return this.slotId;
	}
	public String getSelectedName()
	{
		if(getNameId() == -1)
			return null;
		return myList.get(getNameId()).toString();
	}
	public HashMap getHashMap()
	{
		return this.myHashMap;
	}
	/**
	 * the element in the slot that was clicked, boolean for whether it was double clicked or not
	 */
	protected void elementClicked(int selectedId, boolean doubleClicked)
	{
		this.parentGuiFF.onElementSelected(this.slotId, selectedId);
		this.nameId = selectedId;
	}

	/**
	 * returns true if the element passed in is currently selected
	 */
	protected boolean isSelected(int selectedID)
	{
		return (selectedID == this.nameId) && (this.slotId == parentGuiFF.getSelectedListId());
	}

	/**
	 * return the height of the content being scrolled
	 */
	protected int getContentHeight()
	{
		return this.getSize() * this.slotHeight;
	}

	/**
	 * return the height of the content less the hight of the box
	 * 
	 */
	public int getAdjustedContentHeight()
	{
		int h = this.getContentHeight() - (this.height - 4);
		if( h < 1 )
			h = 1;
		return h;
	}

	/**
	 * draw one item
	 */
	protected void drawSlot(int slotID, int slotX, int slotY, Tessellator tess)
	{
		int scrollbarLeft = this.getScrollbarLeft() - this.left;		
		int color =  0x00808080;
		if((Boolean)this.myHashMap.get(this.myList.get(slotID)))
			color = 0x00FFFFFF;
		String n = (String)this.myList.get(slotID);
		int c = n.length();
		while(scrollbarLeft < this.mc.fontRenderer.getStringWidth(n))
			n = n.substring(0,--c);
		this.parentGuiFF.drawString(this.mc.fontRenderer, n, slotX + 2, slotY + 1, color);

	}

	protected void drawBackground() {
		Tessellator tess = Tessellator.instance;
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/background.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float texture = 32.0F;
		tess.startDrawingQuads();
		tess.setColorOpaque_I(0x00202020); // dark grey
		tess.addVertexWithUV((double)this.left, (double)this.bottom, 0.0D, (double)((float)this.left / texture), (double)((float)(this.bottom + (int)this.amountScrolled) / texture));
		tess.addVertexWithUV((double)this.right, (double)this.bottom, 0.0D, (double)((float)this.right / texture), (double)((float)(this.bottom + (int)this.amountScrolled) / texture));
		tess.addVertexWithUV((double)this.right, (double)this.top, 0.0D, (double)((float)this.right / texture), (double)((float)(this.top + (int)this.amountScrolled) / texture));
		tess.addVertexWithUV((double)this.left, (double)this.top, 0.0D, (double)((float)this.left / texture), (double)((float)(this.top + (int)this.amountScrolled) / texture));
		tess.draw();

	}

	/**
	 * draws the slot to the screen, pass in mouse's current x and y and partial ticks
	 */
	public void drawScreen(int mouseX, int mouseY, float par3)
	{
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.drawBackground();
		int scrollbarLeft = this.getScrollbarLeft();
		int scrollbarRight = scrollbarLeft + 6;
		int slotX;
		int slotY = -1;
		int clickedSlot;
		boolean scrollHover;
		boolean hoveringOver;
		int scrollBarHeight;

		scrollBarHeight = (int)((float)((this.height) * (this.height)) / (float)this.getContentHeight());
		if (scrollBarHeight < 32)
		{
			scrollBarHeight = 32;
		}

		if (scrollBarHeight > this.height)
		{
			scrollBarHeight = this.height;
		}
//		scrollbarHeight = 16;

		int scrollMax = this.height - scrollBarHeight;

		clickedSlot = -1;
		hoveringOver = false;
		scrollHover = false;

		// is the mouse within our boundry?
		if (mouseX >= this.left && mouseX <= this.right && 
				mouseY >= this.top && mouseY <= this.bottom)
		{

			hoveringOver = true;
			if (mouseX >= scrollbarLeft && mouseX <= scrollbarRight)
				scrollHover = true;

			// was the mouse clicked? (button 0)
			if (Mouse.isButtonDown(0))
			{


				// did we click on a slot?
				if(mouseX >= this.left && mouseX < scrollbarLeft) {
					if(!this.scrolling) {
						// which slot is it?
						slotY = (mouseY - this.top) + (int)(this.amountScrolled * this.getAdjustedContentHeight()) - 4;
						clickedSlot = slotY / this.slotHeight;

						// did the user just click on a slot twice
						if (clickedSlot >= 0 && slotY >= 0 && clickedSlot < this.getSize())
						{
							boolean doubleClicked = clickedSlot == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L;
							this.elementClicked(clickedSlot, doubleClicked);
							this.selectedElement = clickedSlot;
							this.lastClicked = Minecraft.getSystemTime();
						} else {
							this.elementClicked(-1, false);
						}
					}

				} 
				// clicked within the scroll bar?
				if (mouseX >= scrollbarLeft && mouseX <= scrollbarRight)
				{
					this.scrolling = true;


					this.scrollBarTop = mouseY - this.top - scrollBarHeight / 2;
					if (this.scrollBarTop < 0)
					{
						this.scrollBarTop = 0;
					}
					if(this.scrollBarTop > scrollMax)
						this.scrollBarTop = scrollMax;

					this.amountScrolled = (float)this.scrollBarTop / (float)scrollMax;

				}
			}
			else
			{
				this.scrolling = false;
			}

/*
			// did the wheel scroll
			int mouseWheel = Mouse.getEventDWheel();

			if (mouseWheel != 0)
			{
				if (mouseWheel > 0)
				{
					mouseWheel = -1;
				}
				else if (mouseWheel < 0)
				{
					mouseWheel = 1;
				}

				this.amountScrolled += (float)(mouseWheel * 1.0F / this.getContentHeight());
			}
*/

		}


		// draw the background
		//this.bindAmountScrolled();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator tess = Tessellator.instance;
		
		// draw the title
		this.parentGuiFF.drawCenteredString(this.mc.fontRenderer,this.title, this.left + this.width / 2, this.top - 10, 0x00FFFFFF);

		slotX = this.left;
		slotY = this.top + 4 - (int)(this.amountScrolled * this.getAdjustedContentHeight());

		// visible slots
		for (int drawSlot = 0; drawSlot < this.getSize(); ++drawSlot)
		{
			int slotYTop = slotY + drawSlot * this.slotHeight;
			int slotYBottom = this.slotHeight - 4;

			if (
					slotYTop <= (this.bottom - this.slotHeight) && 
					slotYTop > this.top
					)
			{
				if (this.showSelectionBox && this.isSelected(drawSlot))
				{
					int selectionBoxLeft = this.left;
					int selectionBoxRight = scrollbarLeft;
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					tess.startDrawingQuads();
					tess.setColorOpaque_I(0x00008080);
					if(hoveringOver)
						tess.setColorOpaque_I(0x00FFFF00); // 
					tess.addVertexWithUV((double)selectionBoxLeft  , (double)(slotYTop + slotYBottom + 2), 0.0D, 0.0D, 1.0D);
					tess.addVertexWithUV((double)selectionBoxRight , (double)(slotYTop + slotYBottom + 2), 0.0D, 1.0D, 1.0D);
					tess.addVertexWithUV((double)selectionBoxRight , (double)(slotYTop - 2), 0.0D, 1.0D, 0.0D);
					tess.addVertexWithUV((double)selectionBoxLeft  , (double)(slotYTop - 2), 0.0D, 0.0D, 0.0D);
					tess.setColorOpaque_I(0x00000000);
					tess.addVertexWithUV((double)(selectionBoxLeft  + 1), (double)(slotYTop + slotYBottom + 1), 0.0D, 0.0D, 1.0D);
					tess.addVertexWithUV((double)(selectionBoxRight - 1), (double)(slotYTop + slotYBottom + 1), 0.0D, 1.0D, 1.0D);
					tess.addVertexWithUV((double)(selectionBoxRight - 1), (double)(slotYTop - 1), 0.0D, 1.0D, 0.0D);
					tess.addVertexWithUV((double)(selectionBoxLeft  + 1), (double)(slotYTop - 1), 0.0D, 0.0D, 0.0D);
					tess.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}

				this.drawSlot(drawSlot, slotX, slotYTop - 2, tess);
			}
		}

		// draw top and bottom edge gradients
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		// draw scrollbar

		if (this.getAdjustedContentHeight() > 0)
		{


			// scrollbar back
			tess.startDrawingQuads();
			tess.setColorRGBA_I(0, 255);
			tess.addVertexWithUV((double)scrollbarLeft , (double)this.bottom, 0.0D, 0.0D, 1.0D);
			tess.addVertexWithUV((double)scrollbarRight, (double)this.bottom, 0.0D, 1.0D, 1.0D);
			tess.addVertexWithUV((double)scrollbarRight, (double)this.top, 0.0D, 1.0D, 0.0D);
			tess.addVertexWithUV((double)scrollbarLeft , (double)this.top, 0.0D, 0.0D, 0.0D);
			tess.draw();
			// scrollbar, derp
			tess.startDrawingQuads();
			tess.setColorRGBA_I(0x00808080, 255);
			if(scrollHover)
				tess.setColorRGBA_I(0x00808000, 255);
			tess.addVertexWithUV((double)scrollbarLeft , (double)(this.top + this.scrollBarTop + scrollBarHeight), 0.0D, 0.0D, 1.0D);
			tess.addVertexWithUV((double)scrollbarRight, (double)(this.top + this.scrollBarTop + scrollBarHeight), 0.0D, 1.0D, 1.0D);
			tess.addVertexWithUV((double)scrollbarRight, (double) this.top + this.scrollBarTop, 0.0D, 1.0D, 0.0D);
			tess.addVertexWithUV((double)scrollbarLeft , (double) this.top + this.scrollBarTop, 0.0D, 0.0D, 0.0D);
			tess.draw();
			// scrollbar highlight
			tess.startDrawingQuads();
			tess.setColorRGBA_I(0x00C0C0C0, 255);
			if(scrollHover)
				tess.setColorRGBA_I(0x00C0C000, 255);
			tess.addVertexWithUV((double)(scrollbarLeft     ), (double)(this.top + this.scrollBarTop + scrollBarHeight - 1), 0.0D, 0.0D, 1.0D);
			tess.addVertexWithUV((double)(scrollbarRight - 1), (double)(this.top + this.scrollBarTop + scrollBarHeight - 1), 0.0D, 1.0D, 1.0D);
			tess.addVertexWithUV((double)(scrollbarRight - 1), (double) this.top + this.scrollBarTop, 0.0D, 1.0D, 0.0D);
			tess.addVertexWithUV((double)(scrollbarLeft     ), (double) this.top + this.scrollBarTop, 0.0D, 0.0D, 0.0D);
			tess.draw();
		}


		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
	}

	protected int getScrollbarLeft()
	{
		return this.right - 6;
	}


}


