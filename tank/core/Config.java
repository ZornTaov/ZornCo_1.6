package zornco.tank.core;

import net.minecraft.block.BlockDispenser;
import net.minecraftforge.common.Configuration;
import zornco.tank.Tank;
import zornco.tank.crafting.RecipeHandler;
import zornco.tank.entity.EntityTankBullet;
import zornco.tank.entity.EntityTankBase;
import zornco.tank.item.ItemTankBullet;
import zornco.tank.item.ItemTank;
import zornco.tank.item.DispenserBehaviorBullet;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Config {

	public static RecipeHandler recipes = new RecipeHandler();

	//ID's
	private static int tankItemID;

	private static int bulletItemID[] = new int[Tank.bullettypes];

	public void loadConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		//Items
		int itemID = 20250;
		tankItemID = config.getItem(Configuration.CATEGORY_ITEM,"Tank", itemID++).getInt();

		for (int i = 0; i < Tank.bullettypes; i++) {
			bulletItemID[i] = config.getItem(Configuration.CATEGORY_ITEM,ItemTankBullet.bulletNames[i], itemID++).getInt();
		}

		config.save();
	}

	public void addItems() {
		Tank.tankItem = new ItemTank(tankItemID).setUnlocalizedName("Tank");
		for (int i = 0; i < Tank.bullettypes; i++) {
			Tank.tankBullet[i] = (new ItemTankBullet(bulletItemID[i], i)).setUnlocalizedName("Round" + i);			
		}
	}

	public void addBlocks() {

	}

	public void addNames() {
		/** Names **/
		LanguageRegistry.instance().addStringLocalization("item.Tank.name", "en_US", "Tank");
		for (int i = 0; i < Tank.bullettypes; i++) {

			LanguageRegistry.instance().addStringLocalization("item.Round"+i+".name", "en_US", ItemTankBullet.bulletNames[i]);
		}
		LanguageRegistry.instance().addStringLocalization( "itemGroup.Tanks", "Tanks" );

	}

	public void registerEntities() {
		int entityID = EntityRegistry.findGlobalUniqueEntityId();
		//EntityRegistry.registerGlobalEntityID(TankEntity.class, "Tank", entityID);
		//EntityList.addMapping(TankEntity.class, "Tank", entityID);
		EntityRegistry.registerModEntity(EntityTankBase.class, "Tank", 3, Tank.instance, 100, 1, true);

		entityID = EntityRegistry.findGlobalUniqueEntityId();
		//EntityRegistry.registerGlobalEntityID(TankBulletEntity.class, "tankBullet", entityID);
		EntityRegistry.registerModEntity(EntityTankBullet.class, "tankBullet", 1, Tank.instance, 150, 100, true);
		//EntityList.addMapping(TankBulletEntity.class, "tankBullet", entityID);
		for(int i = 0; i < ItemTankBullet.bulletNames.length; i++)
			BlockDispenser.dispenseBehaviorRegistry.putObject(Tank.tankBullet[i], new DispenserBehaviorBullet());
	}

}
