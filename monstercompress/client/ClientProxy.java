package zornco.monstercompress.client;


import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import zornco.monstercompress.MonsterCompressor;
import zornco.monstercompress.blocks.TileCompressor;
import zornco.monstercompress.core.CommonProxy;
import zornco.monstercompress.core.GuiIds;
import zornco.monstercompress.gui.ContainerCompressor;
import zornco.monstercompress.gui.GuiCompressor;

public class ClientProxy extends CommonProxy {

	public ClientProxy()
	{
		
	}
	@Override
	public void registerRenderInformation()
	{
		MonsterCompressor.compressorRI = RenderingRegistry.getNextAvailableRenderId();
		
	}
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (!(tile instanceof TileCompressor))
			return null;

		switch (ID) {

		case GuiIds.UPGRADE_STATION:
			return new GuiCompressor(player.inventory, (TileCompressor) tile);
		default:
			return null;
		}
	}
}