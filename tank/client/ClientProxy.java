package zornco.tank.client;

import zornco.tank.client.render.TankBulletRender;
import zornco.tank.client.render.TankRender;
import zornco.tank.core.CommonProxy;
import zornco.tank.entity.TankBulletEntity;
import zornco.tank.entity.TankEntity;
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
		RenderingRegistry.registerEntityRenderingHandler(TankEntity.class, new TankRender());
		RenderingRegistry.registerEntityRenderingHandler(TankBulletEntity.class, new TankBulletRender()); 
	}
}
