package zornco.ModJam.core;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import zornco.ModJam.ModJam;
import zornco.ModJam.entities.EntityFollower;
import cpw.mods.fml.common.registry.EntityRegistry;

public class Config {

	public void registerEntities() {
		EntityRegistry.registerGlobalEntityID(EntityFollower.class, "Follower", EntityRegistry.findGlobalUniqueEntityId(), 0xFFFF00, 0x0F0000);
		EntityRegistry.registerModEntity(EntityFollower.class, "Follower", 2, ModJam.instance, 80, 3, true);
	}

}
