package zornco.megax.client;


import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import zornco.megax.MegaX;
import zornco.megax.blocks.TileUpgradeStation;
import zornco.megax.bullets.*;
import zornco.megax.client.fx.BusterFX;
import zornco.megax.client.renderers.*;
import zornco.megax.core.CommonProxy;
import zornco.megax.core.GuiIds;
import zornco.megax.core.helper.KeyBindingHelper;
import zornco.megax.entities.*;
import zornco.megax.gui.ContainerUpgradeStation;
import zornco.megax.gui.GuiUpgradeStation;
import zornco.megax.sounds.Sounds;

public class ClientProxy extends CommonProxy {

	Sounds sounds;
	public ClientProxy()
	{
		sounds = new Sounds();
	}
	@Override
	public void registerRenderInformation()
	{
		MinecraftForgeClient.registerItemRenderer(MegaX.buster.shiftedIndex, new RenderXBuster());
		MinecraftForgeClient.preloadTexture( "/zornco/megax/textures/MegaXItemTextures.png" );
		MinecraftForgeClient.preloadTexture( "/zornco/megax/textures/UpgradeStation.png" );
		MinecraftForgeClient.preloadTexture( "/zornco/megax/textures/burst.png" );
		MinecraftForgeClient.preloadTexture( "/zornco/megax/textures/buster0.png" );
		MinecraftForgeClient.preloadTexture( "/zornco/megax/textures/buster1.png" );
		MinecraftForgeClient.preloadTexture( "/zornco/megax/textures/buster2.png" );
		MinecraftForgeClient.preloadTexture( "/zornco/megax/textures/X1LightBusterDetailed.png" );
		RenderingRegistry.registerEntityRenderingHandler(EntityMet.class, new RenderMet(new ModelMet(), new ModelMetHat(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBusterBullet.class, new RenderBulletBase());  
		RenderingRegistry.registerEntityRenderingHandler(EntityMetBullet.class, new RenderBulletBase());

		MegaX.spikesRI = RenderingRegistry.getNextAvailableRenderId();

		RenderingRegistry.registerBlockHandler(new BlockSpikesRenderer());

	}
	@Override
	public void registerKeyBindingHandler() {

		KeyBindingRegistry.registerKeyBinding(new KeyBindingHandler());
	}
	@Override
	public void setKeyBinding(String name, int value) {

		KeyBindingHelper.addKeyBinding(name, value);
		KeyBindingHelper.addIsRepeating(false);
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
	@Override
	public void busterShot(World worldObj, double sx, double sy, double sz, float size, int type)
	{
		BusterFX ef = new BusterFX(worldObj, sx, sy, sz, size, type);
		ModLoader.getMinecraftInstance().effectRenderer.addEffect(ef);
	}

}