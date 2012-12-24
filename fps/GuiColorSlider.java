package zornco.fps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;
import net.minecraft.src.*;

public class GuiColorSlider extends GuiButton
{
    /** The value of this slider control. */
    public float sliderValue = 1.0F;
 
    /** Is this slider control being dragged. */
    public boolean dragging = false;

	public int sliderColor;
	
	float[] rgb;

	private String sliderName;
	
    public GuiColorSlider(float[] rgbIn, int id, int top, int left, String text, float val, int row)
    {
        super(id, top, left, 100, 10, text);
        this.sliderValue = val;
        this.sliderName = text;
        this.displayString = (sliderName + (int)(sliderValue * 255.0F));
        this.sliderColor = row;
        this.rgb = rgbIn;
    }
    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean par1)
    {
    	return 0; // don't draw the button bumper
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            if (this.dragging)
            {
                this.sliderValue = (float)(par2 - (this.xPosition + 4)) / (float)(this.width - 8);

                if (this.sliderValue < 0.0F)
                {
                    this.sliderValue = 0.0F;
                }

                if (this.sliderValue > 1.0F)
                {
                    this.sliderValue = 1.0F;
                }
                this.displayString = (this.sliderName + (int)(this.sliderValue * 255.0F));
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 10);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (float)(this.width - 8)) + 4, this.yPosition, 196, 66, 4, 10);
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */   
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
    {
        if (super.mousePressed(par1Minecraft, par2, par3))
        {
            this.sliderValue = (float)(par2 - (this.xPosition + 4)) / (float)(this.width - 8);

            if (this.sliderValue < 0.0F)
            {
                this.sliderValue = 0.0F;
            }

            if (this.sliderValue > 1.0F)
            {
                this.sliderValue = 1.0F;
            }

            this.dragging = true;
            
            return true;
        }
        else
        {
        	this.dragging = false;
            return false;
        }
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int par1, int par2)
    {
        this.displayString = (this.sliderName + (int)(this.sliderValue * 255.0F));
        this.rgb[this.sliderColor] = this.sliderValue;
        this.dragging = false;
    }
}
