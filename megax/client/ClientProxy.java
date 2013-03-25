package zornco.megax.client;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import zornco.megax.MegaX;
import zornco.megax.bullets.EntityBusterBullet;
import zornco.megax.bullets.EntityMetBullet;
import zornco.megax.client.fx.BusterFX;
import zornco.megax.client.renderers.BlockBossDoorRenderer;
import zornco.megax.client.renderers.BlockSpikesRenderer;
import zornco.megax.client.renderers.ModelMet;
import zornco.megax.client.renderers.ModelMetHat;
import zornco.megax.client.renderers.RenderBulletBase;
import zornco.megax.client.renderers.RenderFloatingPlatform;
import zornco.megax.client.renderers.RenderMet;
import zornco.megax.client.renderers.RenderXBuster;
import zornco.megax.core.CommonProxy;
import zornco.megax.core.helper.KeyBindingHelper;
import zornco.megax.entities.EntityFloatingPlatform;
import zornco.megax.entities.EntityMet;
import zornco.megax.sounds.Sounds;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {

	Sounds sounds;
	public ClientProxy()
	{
		
	}
	public void registerSounds() {
		sounds = new Sounds();
	}
	@Override
	public void initTickHandlers()
	{

		TickRegistry.registerTickHandler(new TickHandlerClient(), Side.CLIENT);
	}
	@Override
	public void registerRenderInformation()
	{
		MinecraftForgeClient.registerItemRenderer(MegaX.buster.itemID, new RenderXBuster());
		//MinecraftForgeClient.preloadTexture( "/zornco/megax/textures/MegaXItemTextures.png" );
		MinecraftForgeClient.preloadTexture( "/mods/megax/textures/gui/UpgradeStation.png" );
		MinecraftForgeClient.preloadTexture( "/mods/megax/textures/fx/buster0.png" );
		MinecraftForgeClient.preloadTexture( "/mods/megax/textures/fx/buster1.png" );
		MinecraftForgeClient.preloadTexture( "/mods/megax/textures/fx/buster2.png" );
		MinecraftForgeClient.preloadTexture( "/mods/megax/textures/models/X1LightBusterDetailed.png" );
		//MinecraftForgeClient.preloadTexture( "/zornco/megax/textures/MetHat.png" );
		MinecraftForgeClient.preloadTexture( "/mods/megax/textures/models/FloatingPlatform.png" );
		RenderingRegistry.registerEntityRenderingHandler(EntityMet.class, new RenderMet(new ModelMet(), new ModelMetHat(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBusterBullet.class, new RenderBulletBase());  
		RenderingRegistry.registerEntityRenderingHandler(EntityFloatingPlatform.class, new RenderFloatingPlatform());  
		RenderingRegistry.registerEntityRenderingHandler(EntityMetBullet.class, new RenderBulletBase());

		MegaX.config.spikesRI = RenderingRegistry.getNextAvailableRenderId();
		MegaX.config.bossDoorRI = RenderingRegistry.getNextAvailableRenderId();

		RenderingRegistry.registerBlockHandler(new BlockSpikesRenderer());
		RenderingRegistry.registerBlockHandler(new BlockBossDoorRenderer());

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
		/*if (!world.blockExists(x, y, z))
			return null;

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (!(tile instanceof TileUpgradeStation))
			return null;

		switch (ID) {

		case GuiIds.UPGRADE_STATION:
			return new GuiUpgradeStation(player.inventory, (TileUpgradeStation) tile);
		default:
			return null;
		}*/
		return null;
		
	}
	@Override
	public void busterShot(World worldObj, double sx, double sy, double sz, float size, int type)
	{
		BusterFX ef = new BusterFX(worldObj, sx, sy, sz, size, type);
		ModLoader.getMinecraftInstance().effectRenderer.addEffect(ef);
	}

}