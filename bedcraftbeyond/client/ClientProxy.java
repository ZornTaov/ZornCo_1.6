package zornco.bedcraftbeyond.client;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;
import zornco.bedcraftbeyond.BedCraftBeyond;
import zornco.bedcraftbeyond.client.render.BlockBedRenderer;
import zornco.bedcraftbeyond.client.render.BlockRugRenderer;
import zornco.bedcraftbeyond.core.CommonProxy;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderInformation()
	{
		BedCraftBeyond.rugRI = RenderingRegistry.getNextAvailableRenderId();
		BedCraftBeyond.bedRI = RenderingRegistry.getNextAvailableRenderId();

		RenderingRegistry.registerBlockHandler(new BlockRugRenderer());
		RenderingRegistry.registerBlockHandler(new BlockBedRenderer());

	}
}
