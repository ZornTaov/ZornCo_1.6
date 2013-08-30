package zornco.bedcraftbeyond.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import zornco.bedcraftbeyond.BedCraftBeyond;
import zornco.bedcraftbeyond.blocks.TileColoredChestBed;
import zornco.bedcraftbeyond.client.render.BlockBedRenderer;
import zornco.bedcraftbeyond.client.render.BlockChestBedRenderer;
import zornco.bedcraftbeyond.client.render.BlockRugRenderer;
import zornco.bedcraftbeyond.core.CommonProxy;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderInformation()
	{
		BedCraftBeyond.rugRI = RenderingRegistry.getNextAvailableRenderId();
		BedCraftBeyond.bedRI = RenderingRegistry.getNextAvailableRenderId();
		BedCraftBeyond.chestBedRI = RenderingRegistry.getNextAvailableRenderId();

		RenderingRegistry.registerBlockHandler(new BlockRugRenderer());
		RenderingRegistry.registerBlockHandler(new BlockBedRenderer());
		RenderingRegistry.registerBlockHandler(new BlockChestBedRenderer());

	}
	@Override
    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getBlockTileEntity(x, y, z);
        if (te != null && te instanceof TileColoredChestBed)
        {
            return new GuiColoredChestBed(player.inventory, (TileColoredChestBed) te);
        }
        else
        {
            return null;
        }
    }
}
