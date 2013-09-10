package zornco.fps;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class GuiCheckBox extends GuiButton {
	
	private boolean state;

	public GuiCheckBox(int par1, int par2, int par3, String strPar1, boolean par4) {
		super(par1, par2, par3, 16, 16, strPar1);
		this.state = par4;
	}

    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        par1Minecraft.renderEngine.bindTexture(new ResourceLocation("fps:gui/checkbox.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float texUPixel = 0.03125F; // 1 / width of png
		float texVPixel = 0.0625F; // 1 / height of png
		float texU = 0.0F;
		float texV = 0.0F;
		if(this.state)
			texU = 16.0F;
		FontRenderer fr = par1Minecraft.fontRenderer;
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV((double)(this.xPosition + 0),          (double)(this.yPosition + this.height), 0.0F, (double)((float)(texU + 0)          * texUPixel), (double)((float)(texV + this.height) * texVPixel));
		tess.addVertexWithUV((double)(this.xPosition + this.width), (double)(this.yPosition + this.height), 0.0F, (double)((float)(texU + this.width) * texUPixel), (double)((float)(texV + this.height) * texVPixel));
		tess.addVertexWithUV((double)(this.xPosition + this.width), (double)(this.yPosition + 0),           0.0F, (double)((float)(texU + this.width) * texUPixel), (double)((float)(texV + 0)           * texVPixel));
		tess.addVertexWithUV((double)(this.xPosition + 0),          (double)(this.yPosition + 0),           0.0F, (double)((float)(texU + 0)          * texUPixel), (double)((float)(texV + 0)           * texVPixel));
		tess.draw();
        this.drawString(fr, this.displayString, this.xPosition + 18, this.yPosition + 5, 0xFFFFFF);

    }
    
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
    {

    	if(
    			this.enabled && 
    			this.drawButton && 
    			par2 >= this.xPosition && 
    			par3 >= this.yPosition && 
    			par2 < this.xPosition + this.width && 
    			par3 < this.yPosition + this.height
        ) {
    		this.state = !this.state;
    	
    		return true;
    	}
		return false;
    }
    
    public boolean getState() {
    	return this.state;
    }
    
}
