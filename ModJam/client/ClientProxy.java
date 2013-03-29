package zornco.ModJam.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import zornco.ModJam.client.render.ModelFollower;
import zornco.ModJam.client.render.RenderFollower;
import zornco.ModJam.core.CommonProxy;
import zornco.ModJam.entities.EntityFollower;

public class ClientProxy extends CommonProxy {
	public void registerRenderInformation() {
		RenderingRegistry.registerEntityRenderingHandler(EntityFollower.class, new RenderFollower(new ModelFollower()));
		
	}
}
