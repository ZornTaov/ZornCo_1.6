package zornco.megax.client.renderers;

import org.lwjgl.opengl.GL11;

import zornco.megax.MegaX;
import zornco.megax.blocks.TileSpikes;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;

public class BlockSpikesRenderer extends RenderBlocks
implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub
	      GL11.glTranslatef(-0.3F, -0.75F, -0.5F);
	      TileEntityRenderer.instance.renderTileEntityAt(new TileSpikes(), 0.0D, 0.0D, 0.0D, 0.0F);
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return MegaX.spikesRI;
	}

}
