package zornco.megax.gui;


import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.opengl.GL11;

import zornco.megax.blocks.TileUpgradeStation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiUpgradeStation extends GuiContainer
{
    private TileUpgradeStation stationInventory;

    public GuiUpgradeStation(InventoryPlayer par1InventoryPlayer, TileUpgradeStation par2TileEntityFurnace)
    {
        super(new ContainerUpgradeStation(par1InventoryPlayer, par2TileEntityFurnace));
        this.stationInventory = par2TileEntityFurnace;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.furnace"), 60, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ForgeHooksClient.bindTexture("/zornco/megax/textures/UpgradeStation.png", 0);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        int var7;

        if (this.stationInventory.hasCharge())
        {
            var7 = this.stationInventory.getChargeRemainingScaled(12);
            this.drawTexturedModalRect(var5 + 56, var6 + 36 + 12 - var7, 176, 12 - var7, 14, var7 + 2);
        }

        var7 = this.stationInventory.getCookProgressScaled(24);
        this.drawTexturedModalRect(var5 + 79, var6 + 34, 176, 14, var7 + 1, 16);
    }
}
