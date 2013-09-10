package zornco.bedcraftbeyond.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import zornco.bedcraftbeyond.blocks.ContainerColoredChestBed;

public class GuiColoredChestBed extends GuiContainer {

	public final ResourceLocation location = new ResourceLocation("bedcraftbeyond", "textures/gui/beddrawers.png");
	//private static int xSize = 176;
	private static int ySize = 133;
	private IInventory playerInventory;
    private IInventory chestInventory;
	
	GuiColoredChestBed(IInventory player, IInventory chest)
	{
		super(new ContainerColoredChestBed(player, chest));

        this.playerInventory = player;
        this.chestInventory = chest;
		this.allowUserInput = false;
		
	}
	/**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(this.chestInventory.isInvNameLocalized() ? this.chestInventory.getInvName() : I18n.getStringParams(this.chestInventory.getInvName()), 8, 6+ 17 , 4210752);                  
        this.fontRenderer.drawString(this.playerInventory.isInvNameLocalized() ? this.playerInventory.getInvName() : I18n.getStringParams(this.playerInventory.getInvName()), 8, GuiColoredChestBed.ySize - 96 + 2 + 17 , 4210752);
    }
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		// new "bind tex"
		this.mc.getTextureManager().bindTexture(location);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
