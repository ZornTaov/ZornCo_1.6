package zornco.modularrugs.client;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;
import zornco.modularrugs.ModularRugs;
import zornco.modularrugs.client.render.BlockRugRenderer;
import zornco.modularrugs.core.CommonProxy;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderInformation()
	{
		ModularRugs.rugRI = RenderingRegistry.getNextAvailableRenderId();
		
		RenderingRegistry.registerBlockHandler(new BlockRugRenderer());

	}
}
