package zornco.reploidcraft.client.renderers;

import org.lwjgl.opengl.GL11;

import zornco.reploidcraft.RepliodCraft;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;

public class BlockSpikesRenderer extends RenderBlocks
implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		if (modelID == RepliodCraft.config.spikesRI)
		{
			renderer.setRenderBounds(0.0F, 0.25F, 0.0F, 1.0F, 0.3125F, 1.0F);
			renderDo(renderer, block, metadata);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {

		Tessellator tess = Tessellator.instance;
		Icon icon = getBlockIconFromSide(block, 0);
		double uLeft = (double)icon.getMinU();
		double uRight = (double)icon.getMaxU();
		double vTop = (double)icon.getMinV();
		double vBottom = (double)icon.getMaxV(); 
		double offset = 0.0625F;
		double i, j;
		int light = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
		tess.setBrightness(light);
		tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		if (renderer.blockAccess.isBlockNormalCube(x, y + 1, z) || (renderer.blockAccess.getBlockMaterial(x, y + 1, z) == Material.piston && renderer.blockAccess.getBlockId(x, y + 1, z) != Block.pistonMoving.blockID))
		{
			renderer.setRenderBounds(0.0, 1.0-offset, 0.0, 1.0, 1.0, 1.0);
			renderer.renderStandardBlock(block, x, y, z);
			tess.setNormal(0.0F, -1F, 0.0F);

			drawY1(tess, x, y, z, uLeft, uRight, vTop, vBottom, light);

		}
		if (renderer.blockAccess.isBlockNormalCube(x, y - 1, z) || (renderer.blockAccess.getBlockMaterial(x, y - 1, z) ==  Material.piston && renderer.blockAccess.getBlockId(x, y - 1, z) != Block.pistonMoving.blockID))
		{
			renderer.setRenderBounds(0.0, 0.0, 0.0, 1.0, offset, 1.0);
			renderer.renderStandardBlock(block, x, y, z);
			tess.setNormal(0.0F, 1.0F, 0.0F);

			drawY2(tess, x, y, z, uLeft, uRight, vTop, vBottom, light);

		}
		if (renderer.blockAccess.isBlockNormalCube(x + 1, y, z) || (renderer.blockAccess.getBlockMaterial(x + 1, y, z) == Material.piston && renderer.blockAccess.getBlockId(x + 1, y, z) != Block.pistonMoving.blockID))
		{
			//PROBLEM SIDE
			renderer.setRenderBounds(1.0-offset, 0.0, 0.0, 1.0, 1.0, 1.0);
			renderer.renderStandardBlock(block, x, y, z);
			tess.setNormal(-1F, 0.0F, 0.0F);

			drawX1(tess, x, y, z, uLeft, uRight, vTop, vBottom, light);

		}
		if (renderer.blockAccess.isBlockNormalCube(x - 1, y, z) || (renderer.blockAccess.getBlockMaterial(x - 1, y, z) == Material.piston && renderer.blockAccess.getBlockId(x - 1, y, z) != Block.pistonMoving.blockID))
		{
			renderer.setRenderBounds(0.0, 0.0, 0.0, offset, 1.0, 1.0);
			renderer.renderStandardBlock(block, x, y, z);
			tess.setNormal(1.0F, 0.0F, 0.0F);

			drawX2(x, y, z, tess, uLeft, uRight, vTop, vBottom, light);

		}
		if (renderer.blockAccess.isBlockNormalCube(x, y, z + 1) || (renderer.blockAccess.getBlockMaterial(x, y, z + 1) == Material.piston && renderer.blockAccess.getBlockId(x, y, z + 1) != Block.pistonMoving.blockID))
		{
			renderer.setRenderBounds(0.0, 0.0, 1.0-offset, 1.0, 1.0, 1.0);
			renderer.renderStandardBlock(block, x, y, z);
			tess.setNormal(0.0F, 0.0F, -1F);

			drayZ1(tess, x, y, z, uLeft, uRight, vTop, vBottom, light);

		}
		if (renderer.blockAccess.isBlockNormalCube(x, y, z - 1) || (renderer.blockAccess.getBlockMaterial(x, y, z - 1) == Material.piston && renderer.blockAccess.getBlockId(x, y, z - 1) != Block.pistonMoving.blockID))
		{
			renderer.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, offset);
			renderer.renderStandardBlock(block, x, y, z);
			tess.setNormal(0.0F, 0.0F, 1.0F);

			drawZ2(tess, x, y, z, uLeft, uRight, vTop, vBottom, light);

		}
		float var13 = 1.0F;

		if (Block.lightValue[block.blockID] > 0)
		{
			var13 = 1.0F;
		}
		return true;
	}

	private void drawX1(Tessellator tess, int x, int y, int z, double uLeft, 
			double uRight, double vTop, double vBottom, int light)
	{
		//int mode = tess.instance.drawMode;
		//tess.draw();
		//tess.startDrawing(4);
		tess.setBrightness(light);
		tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.1 ), (double)(z + 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.1 ), (double)(z + 0.9 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.6), (double)(y + 0.5 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.1 ),	(double)(z + 0.1 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.1 ), (double)(z + 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.1 ), (double)(z + 0.1 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.6), (double)(y + 0.5 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.9 ),	(double)(z + 0.1 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.9 ), (double)(z + 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.9 ), (double)(z + 0.1 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.6), (double)(y + 0.5 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.9 ),	(double)(z + 0.9 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.9 ), (double)(z + 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.9 ), (double)(z + 0.9 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.6), (double)(y + 0.5 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 1.0), (double)(y + 0.1 ),	(double)(z + 0.9 ), uRight, vTop);

		//tess.draw();
		//tess.startDrawing(mode);
	}

	private void drawX2(int x, int y, int z, Tessellator tess, double uLeft,
			double uRight, double vTop, double vBottom, int light) {
		//int mode = tess.instance.drawMode;
		//tess.draw();
		//tess.startDrawing(4);
		tess.setBrightness(light);
		tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.9 ), (double)(z + 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.9 ), (double)(z + 0.1 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.4), (double)(y + 0.5 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.1 ),	(double)(z + 0.1 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.1 ), (double)(z + 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.1 ), (double)(z + 0.1 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.4), (double)(y + 0.5 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.1 ),	(double)(z + 0.9 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.1 ), (double)(z + 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.1 ), (double)(z + 0.9 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.4), (double)(y + 0.5 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.9 ),	(double)(z + 0.9 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.9 ), (double)(z + 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.9 ), (double)(z + 0.9 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.4), (double)(y + 0.5 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.0), (double)(y + 0.9 ),	(double)(z + 0.1 ), uRight, vTop);

		//tess.draw();
		//tess.startDrawing(mode);
	}

	private void drayZ1(Tessellator tess, int x, int y, int z, double uLeft,
			double uRight, double vTop, double vBottom, int light) {
		//int mode = tess.instance.drawMode;
		//tess.draw();
		//tess.startDrawing(4);
		tess.setBrightness(light);
		tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.9 ), (double)(z + 1.0 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.9 ), (double)(z + 1.0 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.5), (double)(y + 0.5 ), (double)(z + 0.6 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.1 ),	(double)(z + 1.0 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.1 ), (double)(z + 1.0 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.1 ), (double)(z + 1.0 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.5), (double)(y + 0.5 ), (double)(z + 0.6 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.1 ),	(double)(z + 1.0 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.1 ), (double)(z + 1.0 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.1 ), (double)(z + 1.0 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.5), (double)(y + 0.5 ), (double)(z + 0.6 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.9 ),	(double)(z + 1.0 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.9 ), (double)(z + 1.0 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.9 ), (double)(z + 1.0 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.5), (double)(y + 0.5 ), (double)(z + 0.6 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.9 ),	(double)(z + 1.0 ), uRight, vTop);

		//tess.draw();
		//tess.startDrawing(mode);
	}

	private void drawZ2(Tessellator tess, int x, int y, int z, double uLeft,
			double uRight, double vTop, double vBottom, int light) {
		//int mode = tess.instance.drawMode;
		//tess.draw();
		//tess.startDrawing(4);
		tess.setBrightness(light);
		tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		tess.setNormal(0.0F, -1F, 0.0F);
		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.1 ), (double)(z + 0.0 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.1 ), (double)(z + 0.0 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.5), (double)(y + 0.5 ), (double)(z + 0.4 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.1 ),	(double)(z + 0.0 ), uRight, vTop);

		tess.setNormal(-1F, 0.0F, 0.0F);
		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.1 ), (double)(z + 0.0 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.1 ), (double)(z + 0.0 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.5), (double)(y + 0.5 ), (double)(z + 0.4 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.9 ),	(double)(z + 0.0 ), uRight, vTop);

		tess.setNormal(0.0F, 1F, 0.0F);
		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.9 ), (double)(z + 0.0 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.1), (double)(y + 0.9 ), (double)(z + 0.0 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.5), (double)(y + 0.5 ), (double)(z + 0.4 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.9 ),	(double)(z + 0.0 ), uRight, vTop);

		tess.setNormal(1F, 0.0F, 0.0F);
		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.9 ), (double)(z + 0.0 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.9 ), (double)(z + 0.0 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.5), (double)(y + 0.5 ), (double)(z + 0.4 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.9), (double)(y + 0.1 ),	(double)(z + 0.0 ), uRight, vTop);

		//tess.draw();
		//tess.startDrawing(mode);
	}

	private void drawY1(Tessellator tess, int x, int y, int z, double uLeft,
			double uRight, double vTop, double vBottom, int light){
		//int mode = tess.instance.drawMode;
		//tess.draw();
		//tess.startDrawing(4);
		tess.setBrightness(light);
		tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 1.0 ), (double)(z + 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 1.0 ), (double)(z + 0.1 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.5 ), (double)(y + 0.6 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 1.0 ), (double)(z + 0.1 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 1.0 ), (double)(z + 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 1.0 ), (double)(z + 0.1 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.5 ), (double)(y + 0.6 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 1.0 ), (double)(z + 0.9 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 1.0 ), (double)(z + 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 1.0 ), (double)(z + 0.9 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.5 ), (double)(y + 0.6 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 1.0 ), (double)(z + 0.9 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 1.0 ), (double)(z + 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 1.0 ), (double)(z + 0.9 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.5 ), (double)(y + 0.6 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 1.0 ), (double)(z + 0.1 ), uRight, vTop);

		//tess.draw();
		//tess.startDrawing(mode);
	}

	private void drawY2(Tessellator tess, int x, int y, int z, double uLeft,
			double uRight, double vTop, double vBottom, int light) {
		//int mode = tess.instance.drawMode;
		//tess.draw();
		//tess.startDrawing(4);
		tess.setBrightness(light);
		tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 0.0 ), (double)(z + 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 0.0 ), (double)(z + 0.9 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.5 ), (double)(y + 0.4 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 0.0 ), (double)(z + 0.1 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 0.0 ), (double)(z + 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 0.0 ), (double)(z + 0.1 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.5 ), (double)(y + 0.4 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 0.0 ), (double)(z + 0.1 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 0.0 ), (double)(z + 0.1 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 0.0 ), (double)(z + 0.1 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.5 ), (double)(y + 0.4 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 0.0 ), (double)(z + 0.9 ), uRight, vTop);

		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 0.0 ), (double)(z + 0.9 ), uLeft, vTop);
		tess.addVertexWithUV((double)(x + 0.9 ), (double)(y + 0.0 ), (double)(z + 0.9 ), uLeft, vBottom);
		tess.addVertexWithUV((double)(x + 0.5 ), (double)(y + 0.4 ), (double)(z + 0.5 ), uRight, vBottom);
		tess.addVertexWithUV((double)(x + 0.1 ), (double)(y + 0.0 ), (double)(z + 0.9 ), uRight, vTop);

		//tess.draw();
		//tess.startDrawing(mode);
	}
	
	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return RepliodCraft.config.spikesRI;
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
		Icon icon = block.getBlockTextureFromSide(0);
		double uLeft = (double)icon.getMinU();
		double uRight = (double)icon.getMaxU();
		double vTop = (double)icon.getMinV();
		double vBottom = (double)icon.getMaxV(); 
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
