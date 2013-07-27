package zornco.modularrugs.client.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import zornco.modularrugs.ModularRugs;
import zornco.modularrugs.blocks.BlockRug;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockRugRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {

		Tessellator tess = Tessellator.instance;
		Icon textureIndex = block.getBlockTextureFromSide(0);
		/*int texU = (textureIndex. & 15) << 4;
		int texV = textureIndex & 240;
		double uLeft = (double)((float)texU / 256.0F);
		double uRight = (double)(((float)texU + 8F) / 256.0F);
		double vTop = (double)((float)texV / 256.0F); 
		double vBottom = (double)(((float)texV + 16F) / 256.0F); */
		double offset = 0.0625F;
		double thickness = 0.0625F;
		double sideOffset = 0.0F;
		byte[][][] byteList = new byte[3][3][3];
		int ID = 0;
		
		/**
		 * 1 air
		 * 1 rug
		 * 1 slab
		 * 1 stair
		 * 1 metadata
		 * 1 metadata
		 * 1 metadata
		 * 1 metadata
		 * 
		 *      y
		 * 220 221 222
		 * 120 121 122
		 * 020 021 022
		 *      x
		 * 210 211 212
		 -z110 111 112z
		 * 010 011 012
		 *     -x
		 * 200 201 202
		 * 100 101 102
		 * 000 001 002
		 *     -y
		 * 
		 */
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				for (int k = -1; k < 2; k++) {
					ID = world.getBlockId(x + i, y + j, z + k);
					if(world.isAirBlock(x + i, y + j, z + k))
						byteList[i+1][j+1][k+1] += 1;
					if(ID == ModularRugs.rugBlock.blockID)
						byteList[i+1][j+1][k+1] += 2;
					if(ID == Block.stoneSingleSlab.blockID || ID == Block.woodSingleSlab.blockID)
						byteList[i+1][j+1][k+1] += 4 + (world.getBlockMetadata(x + i, y + j, z + k)<< 3);
					if(BlockStairs.isBlockStairsID(ID))
						byteList[i+1][j+1][k+1] += 8 + (world.getBlockMetadata(x + i, y + j, z + k)<< 3);
				}
			}
		}
		
		double downOffset = (byteList[1][0][1] == 4) ? 0.5 : 0.0;
		int light = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
		tess.setBrightness(light);
		tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);


		renderer.setRenderBounds(0.0, 0.0-getDownOffset(byteList[1][0][1]), 0.0, 1.0, offset-getDownOffset(byteList[1][0][1]), 1.0);
		renderer.renderStandardBlock(block, x, y, z);

		if (byteList[2][2][1] == 2 && byteList[1][2][1] == 1)
		{
			renderer.setRenderBounds(1.0-thickness, 0.0-getDownOffset(byteList[1][0][1]), sideOffset, 1.0, 1.0+offset-getDownOffset(byteList[2][1][1]), 1.0-sideOffset);
			renderer.renderStandardBlock(block, x, y, z);
		}
		if (byteList[0][2][1] == 2 && byteList[1][2][1] == 1)
		{
			renderer.setRenderBounds(0.0, 0.0-getDownOffset(byteList[1][0][1]), sideOffset, thickness, 1.0+offset-getDownOffset(byteList[0][1][1]), 1.0-sideOffset);
			renderer.renderStandardBlock(block, x, y, z);
		}
		if (byteList[1][2][2] == 2 && byteList[1][2][1] == 1)
		{
			renderer.setRenderBounds(sideOffset, 0.0-getDownOffset(byteList[1][0][1]), 1.0-thickness, 1.0-sideOffset, 1.0+offset-getDownOffset(byteList[1][1][2]), 1.0);
			renderer.renderStandardBlock(block, x, y, z);
		}
		if (byteList[1][2][0] == 2 && byteList[1][2][1] == 1)
		{
			renderer.setRenderBounds(sideOffset, 0.0-getDownOffset(byteList[1][0][1]), 0.0, 1.0-sideOffset, 1.0+offset-getDownOffset(byteList[1][1][0]), thickness);
			renderer.renderStandardBlock(block, x, y, z);
		}
		
		if (byteList[1][0][1] == 4)
		{
			if (byteList[2][1][1] == 2)
			{
				renderer.setRenderBounds(1.0-thickness, 0.0-getDownOffset(byteList[1][0][1]), sideOffset, 1.0, 0.0+offset-getDownOffset(byteList[2][1][1]), 1.0-sideOffset);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if (byteList[0][1][1] == 2)
			{
				renderer.setRenderBounds(0.0, 0.0-getDownOffset(byteList[1][0][1]), sideOffset, thickness, 0.0+offset-getDownOffset(byteList[0][1][1]), 1.0-sideOffset);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if (byteList[1][1][2] == 2)
			{
				renderer.setRenderBounds(sideOffset, 0.0-getDownOffset(byteList[1][0][1]), 1.0-thickness, 1.0-sideOffset, 0.0+offset-getDownOffset(byteList[1][1][2]), 1.0);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if (byteList[1][1][0] == 2)
			{
				renderer.setRenderBounds(sideOffset, 0.0-getDownOffset(byteList[1][0][1]), 0.0, 1.0-sideOffset, 0.0+offset-getDownOffset(byteList[1][1][0]), thickness);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
		
		return true;
	}
	
	public double getDownOffset(byte checkBlock)
	{
		if(checkBlock == 4) return 0.5;
		return 0.0;
	}
	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return ModularRugs.rugRI;
	}
}
