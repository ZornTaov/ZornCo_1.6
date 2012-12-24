package zornco.megax.client;


import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import zornco.megax.MegaX;
import zornco.megax.blocks.TileUpgradeStation;
import zornco.megax.bullets.EntityBulletBase;
import zornco.megax.bullets.EntityBusterBullet;
import zornco.megax.bullets.EntityMetBullet;
import zornco.megax.client.bullets.RenderBulletBase;
import zornco.megax.client.entities.*;
import zornco.megax.client.items.busters.XBusterRender;
import zornco.megax.core.CommonProxy;
import zornco.megax.core.GuiIds;
import zornco.megax.entities.*;
import zornco.megax.gui.ContainerUpgradeStation;
import zornco.megax.gui.GuiUpgradeStation;
import zornco.megax.sounds.Sounds;

public class ClientProxy extends CommonProxy {

	Sounds sounds;
	public ClientProxy()
	{
		//MegaX.buster.setIconIndex(7);
		//ModLoader.addName(MegaX.buster, "X Buster");
		MinecraftForgeClient.registerItemRenderer(4300 + 256, new XBusterRender());
		sounds = new Sounds();
	}
	@Override
	public void registerRenderInformation()
	{
        RenderingRegistry.registerEntityRenderingHandler(EntityMet.class, new RenderMet(new ModelMet(), new ModelMetHat(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBusterBullet.class, new RenderBulletBase());  
		RenderingRegistry.registerEntityRenderingHandler(EntityMetBullet.class, new RenderBulletBase());  
	}
	@Override
	public int addArmor(String path) {
		return RenderingRegistry.addNewArmourRendererPrefix(path);
	}
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (!(tile instanceof TileUpgradeStation))
			return null;

		switch (ID) {

		case GuiIds.UPGRADE_STATION:
			return new GuiUpgradeStation(player.inventory, (TileUpgradeStation) tile);
		default:
			return null;
		}
	}
}