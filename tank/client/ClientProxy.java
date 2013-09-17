package zornco.tank.client;

import zornco.tank.client.render.RenderTankBullet;
import zornco.tank.client.render.RenderTank;
import zornco.tank.core.CommonProxy;
import zornco.tank.entity.EntityTankBullet;
import zornco.tank.entity.EntityTankBase;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerSounds() {
		
	}

	@Override
	public void registerKeyBindingHandler() {
		
	}

	@Override
	public void registerRenderInformation() {
		RenderingRegistry.registerEntityRenderingHandler(EntityTankBase.class, new RenderTank());
		RenderingRegistry.registerEntityRenderingHandler(EntityTankBullet.class, new RenderTankBullet()); 
	}
}
