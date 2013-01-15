package zornco.megax.client.renderers;

import org.lwjgl.opengl.GL11;

import zornco.megax.MegaX;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;

public class BlockSpikesRenderer extends RenderBlocks
implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub
		if (modelID == MegaX.spikesRI)
		{
			renderer.setRenderMinMax(0.0F, 0.25F, 0.0F, 1.0F, 0.3125F, 1.0F);
			renderDo(renderer, block, metadata);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		// TODO Auto-generated method stub

		Tessellator tess = Tessellator.instance;
		int textureIndex = block.getBlockTextureFromSide(0);
		int texU = (textureIndex & 15) << 4;
		int texV = textureIndex & 240;
		double uLeft = (double)((float)texU / 256.0F);
		double uRight = (double)(((float)texU + 8F) / 256.0F);
		double vTop = (double)((float)texV / 256.0F); 
		double vBottom = (double)(((float)texV + 16F) / 256.0F); 
		double offset = 0.0625F;
		double i, j;
		tess.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
		tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		if (renderer.blockAccess.isBlockNormalCube(x, y + 1, z))
		{
			renderer.setRenderMinMax(0.0, 1.0-offset, 0.0, 1.0, 1.0, 1.0);
			renderer.renderStandardBlock(block, x, y, z);
			tess.setNormal(0.0F, -1F, 0.0F);

			drawY1(tess, x, y, z, uLeft, uRight, vTop, vBottom);

		}
		if (renderer.blockAccess.isBlockNormalCube(x, y - 1, z))
		{
			renderer.setRenderMinMax(0.0, 0.0, 0.0, 1.0, offset, 1.0);
			renderer.renderStandardBlock(block, x, y, z);
			tess.setNormal(0.0F, 1.0F, 0.0F);

			drawY2(tess, x, y, z, uLeft, uRight, vTop, vBottom);

		}
		if (renderer.blockAccess.isBlockNormalCube(x + 1, y, z))
		{
			//PROBLEM SIDE
			renderer.setRenderMinMax(1.0-offset, 0.0, 0.0, 1.0, 1.0, 1.0);
			renderer.renderStandardBlock(block, x, y, z);
			tess.setNormal(-1F, 0.0F, 0.0F);

			drawX1(tess, x, y, z, uLeft, uRight, vTop, vBottom);

		}
		if (renderer.blockAccess.isBlockNormalCube(x - 1, y, z))
		{
			renderer.setRenderMinMax(0.0, 0.0, 0.0, offset, 1.0, 1.0);
			renderer.renderStandardBlock(block, x, y, z);
			tess.setNormal(1.0F, 0.0F, 0.0F);

			drawX2(x, y, z, tess, uLeft, uRight, vTop, vBottom);

		}
		if (renderer.blockAccess.isBlockNormalCube(x, y, z + 1))
		{
			renderer.setRenderMinMax(0.0, 0.0, 1.0-offset, 1.0, 1.0, 1.0);
			renderer.renderStandardBlock(block, x, y, z);
			tess.setNormal(0.0F, 0.0F, -1F);

			drayZ1(tess, x, y, z, uLeft, uRight, vTop, vBottom);

		}
		if (renderer.blockAccess.isBlockNormalCube(x, y, z - 1))
		{
			renderer.setRenderMinMax(0.0, 0.0, 0.0, 1.0, 1.0, offset);
			renderer.renderStandardBlock(block, x, y, z);
			tess.setNormal(0.0F, 0.0F, 1.0F);

			drawZ2(tess, x, y, z, uLeft, uRight, vTop, vBottom);

		}
		float var13 = 1.0F;

		if (Block.lightValue[block.blockID] > 0)
		{
			var13 = 1.0F;
		}
		return true;
	}

	private void drawZ2(Tessellator tess, int x, int y, int z, double uLeft,
			double uRight, double vTop, double vBottom) {
		int mode = tess.instance.drawMode;
		tess.draw();
		tess.startDrawing(4);
		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.1 ), (double)(z + 0.0 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.5), (double)(y + 0.5 ), (double)(z + 0.4 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.1 ),	(double)(z + 0.0 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.1 ), (double)(z + 0.0 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.5), (double)(y + 0.5 ), (double)(z + 0.4 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.9 ),	(double)(z + 0.0 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.9 ), (double)(z + 0.0 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.5), (double)(y + 0.5 ), (double)(z + 0.4 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.9 ),	(double)(z + 0.0 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.9 ), (double)(z + 0.0 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.5), (double)(y + 0.5 ), (double)(z + 0.4 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.1 ),	(double)(z + 0.0 ), uRight, vTop);

		tess.draw();
		tess.startDrawing(mode);
	}

	private void drayZ1(Tessellator tess, int x, int y, int z, double uLeft,
			double uRight, double vTop, double vBottom) {
		int mode = tess.instance.drawMode;
		tess.draw();
		tess.startDrawing(4);
		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.9 ), (double)(z + 1.0 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.5), (double)(y + 0.5 ), (double)(z + 0.6 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.1 ),	(double)(z + 1.0 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.1 ), (double)(z + 1.0 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.5), (double)(y + 0.5 ), (double)(z + 0.6 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.1 ),	(double)(z + 1.0 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.1 ), (double)(z + 1.0 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.5), (double)(y + 0.5 ), (double)(z + 0.6 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.9 ),	(double)(z + 1.0 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.9 ), (double)(z + 1.0 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.5), (double)(y + 0.5 ), (double)(z + 0.6 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.9 ),	(double)(z + 1.0 ), uRight, vTop);

		tess.draw();
		tess.startDrawing(mode);
	}

	private void drawX2(int x, int y, int z, Tessellator tess, double uLeft,
			double uRight, double vTop, double vBottom) {
		int mode = tess.instance.drawMode;
		tess.draw();
		tess.startDrawing(4);
		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.9 ), (double)(z + 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.4), (double)(y + 0.5 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.1 ),	(double)(z + 0.1 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.1 ), (double)(z + 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.4), (double)(y + 0.5 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.1 ),	(double)(z + 0.9 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.1 ), (double)(z + 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.4), (double)(y + 0.5 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.9 ),	(double)(z + 0.9 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.9 ), (double)(z + 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.4), (double)(y + 0.5 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.9 ),	(double)(z + 0.1 ), uRight, vTop);

		tess.draw();
		tess.startDrawing(mode);
	}

	private void drawY2(Tessellator tess, int x, int y, int z, double uLeft,
			double uRight, double vTop, double vBottom) {
		int mode = tess.instance.drawMode;
		tess.draw();
		tess.startDrawing(4);
		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 0.0 ), (double)(z + 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.5 ), (double)(y + 0.4 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 0.0 ), (double)(z + 0.1 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 0.0 ), (double)(z + 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.5 ), (double)(y + 0.4 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 0.0 ), (double)(z + 0.1 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 0.0 ), (double)(z + 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.5 ), (double)(y + 0.4 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 0.0 ), (double)(z + 0.9 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 0.0 ), (double)(z + 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.5 ), (double)(y + 0.4 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 0.0 ), (double)(z + 0.9 ), uRight, vTop);

		tess.draw();
		tess.startDrawing(mode);
	}

	private void drawY1(Tessellator tess, int x, int y, int z, double uLeft,
			double uRight, double vTop, double vBottom){
		int mode = tess.instance.drawMode;
		tess.draw();
		tess.startDrawing(4);
		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 1.0 ), (double)(z + 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.5 ), (double)(y + 0.6 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 1.0 ), (double)(z + 0.1 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 1.0 ), (double)(z + 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.5 ), (double)(y + 0.6 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 1.0 ), (double)(z + 0.9 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 1.0 ), (double)(z + 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.5 ), (double)(y + 0.6 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 1.0 ), (double)(z + 0.9 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 1.0 ), (double)(z + 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.5 ), (double)(y + 0.6 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 1.0 ), (double)(z + 0.1 ), uRight, vTop);

		tess.draw();
		tess.startDrawing(mode);
	}

	private void drawX1(Tessellator tess, int x, int y, int z, double uLeft, double uRight, double vTop, double vBottom)
	{
		int mode = tess.instance.drawMode;
		tess.draw();
		tess.startDrawing(4);
		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.1 ), (double)(z + 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.6), (double)(y + 0.5 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.1 ),	(double)(z + 0.1 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.1 ), (double)(z + 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.6), (double)(y + 0.5 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.9 ),	(double)(z + 0.1 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.9 ), (double)(z + 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.6), (double)(y + 0.5 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.9 ),	(double)(z + 0.9 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.9 ), (double)(z + 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.6), (double)(y + 0.5 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.1 ),	(double)(z + 0.9 ), uRight, vTop);

		tess.draw();
		tess.startDrawing(mode);
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

	public static void renderDo(RenderBlocks renderblocks, Block block, int meta)
	{
		Tessellator tess = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tess.startDrawingQuads();
		tess.setNormal(0.0F, -1F, 0.0F);
		renderblocks.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(0, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 1.0F, 0.0F);
		renderblocks.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(1, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 0.0F, -1F);
		renderblocks.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(2, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 0.0F, 1.0F);
		renderblocks.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(3, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(-1F, 0.0F, 0.0F);
		renderblocks.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(4, meta));
		tess.draw();
		tess.startDrawingQuads();
		tess.setNormal(1.0F, 0.0F, 0.0F);
		renderblocks.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(5, meta));
		tess.draw();
		int textureIndex = block.getBlockTextureFromSide(0);       
		int texU = (textureIndex & 15) << 4;                       
		int texV = textureIndex & 240;                             
		double uLeft = (double)((float)texU / 256.0F);             
		double uRight = (double)(((float)texU + 15.99F) / 256.0F); 
		double vTop = (double)((float)texV / 256.0F);              
		double vBottom = (double)(((float)texV + 15.99F) / 256.0F);
		tess.startDrawingQuads();
		tess.addVertexWithUV((double)( 0.1 ), (double)( 0.25 ), (double)( 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)( 0.1 ), (double)( 0.25 ), (double)( 0.9 ), uLeft, vBottom);
		tess.addVertexWithUV((double)( 0.5 ), (double)( 1.00 ), (double)( 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)( 0.1 ), (double)( 0.25 ), (double)( 0.1 ), uRight, vTop);

		tess.addVertexWithUV((double)( 0.1 ), (double)( 0.25 ), (double)( 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)( 0.1 ), (double)( 0.25 ), (double)( 0.1 ), uLeft, vBottom);
		tess.addVertexWithUV((double)( 0.5 ), (double)( 1.00 ), (double)( 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)( 0.9 ), (double)( 0.25 ), (double)( 0.1 ), uRight, vTop);

		tess.addVertexWithUV((double)( 0.9 ), (double)( 0.25 ), (double)( 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)( 0.9 ), (double)( 0.25 ), (double)( 0.1 ), uLeft, vBottom);
		tess.addVertexWithUV((double)( 0.5 ), (double)( 1.00 ), (double)( 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)( 0.9 ), (double)( 0.25 ), (double)( 0.9 ), uRight, vTop);

		tess.addVertexWithUV((double)( 0.9 ), (double)( 0.25 ), (double)( 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)( 0.9 ), (double)( 0.25 ), (double)( 0.9 ), uLeft, vBottom);
		tess.addVertexWithUV((double)( 0.5 ), (double)( 1.00 ), (double)( 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)( 0.1 ), (double)( 0.25 ), (double)( 0.9 ), uRight, vTop);
		tess.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
