package zornco.tank.client;

import zornco.tank.client.render.TankBulletRender;
import zornco.tank.client.render.TankModel;
import zornco.tank.client.render.TankRender;
import zornco.tank.core.CommonProxy;
import zornco.tank.entity.TankBulletEntity;
import zornco.tank.entity.TankEntity;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	public void registerSounds() {
		// TODO Auto-generated method stub
		
	}

	public void registerKeyBindingHandler() {
		// TODO Auto-generated method stub
		
	}

	public void registerRenderInformation() {
		RenderingRegistry.registerEntityRenderingHandler(TankEntity.class, new TankRender());
		RenderingRegistry.registerEntityRenderingHandler(TankBulletEntity.class, new TankBulletRender()); 
	}
}
