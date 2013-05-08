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
		double sideOffset = 0.125F;
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
						byteList[i+1][j+1][k+1] += 4 + world.getBlockMetadata(x + i, y + j, z + k)<< 3;
					if(BlockStairs.isBlockStairsID(ID))
						byteList[i+1][j+1][k+1] += 8 + world.getBlockMetadata(x + i, y + j, z + k)<< 3;
				}
			}
		}
		byte count = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = -1; k < 2; k++) {
				if( (byteList[i+1][j][k+1] & 2) == 1)
					;
				}
			}
		}
		
		double downOffset = (byteList[1][0][1] == 4) ? 0.5 : 0.0;
		int light = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
		tess.setBrightness(light);
		tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		//if(world.doesBlockHaveSolidTopSurface(x, y - 1, z))
		//{
			// TODO figure out render pattern
			boolean right = BlockRug.canConnectRugTo(world, x + 1, y, z);
			boolean left = BlockRug.canConnectRugTo(world, x - 1, y, z);
			boolean front = BlockRug.canConnectRugTo(world, x, y, z + 1);
			boolean back = BlockRug.canConnectRugTo(world, x, y, z - 1);
			
			boolean airTop = byteList[1][2][1] == 1;
			boolean airTopRight = world.isAirBlock(x + 1, y + 1, z);
			boolean airTopLeft = world.isAirBlock(x - 1, y + 1, z);
			boolean airTopFront = world.isAirBlock(x, y + 1, z + 1);
			boolean airTopBack = world.isAirBlock(x, y + 1, z - 1);
			
			boolean airRight = world.isAirBlock(x + 1, y, z);
			boolean airLeft = world.isAirBlock(x - 1, y, z);
			boolean airFront = world.isAirBlock(x, y, z + 1);
			boolean airBack = world.isAirBlock(x, y, z - 1);
			
			boolean bottomRight = airRight && BlockRug.canConnectRugTo(world, x + 1, y - 1, z);
			boolean bottomLeft = airLeft && BlockRug.canConnectRugTo(world, x - 1, y - 1, z);
			boolean bottomFront = airFront && BlockRug.canConnectRugTo(world, x, y - 1, z + 1);
			boolean bottomBack = airBack && BlockRug.canConnectRugTo(world, x, y - 1, z - 1);
			
			boolean topRight = airTop && !right && BlockRug.canConnectRugTo(world, x + 1, y + 1, z);
			boolean topLeft = airTop && !left && BlockRug.canConnectRugTo(world, x - 1, y + 1, z);
			boolean topFront = airTop && !front && BlockRug.canConnectRugTo(world, x, y + 1, z + 1);
			boolean topBack = airTop && !back && BlockRug.canConnectRugTo(world, x, y + 1, z - 1);
			
			boolean frontLeft = (front || topFront) && (left || topLeft) && BlockRug.canConnectRugTo(world, x - 1, y, z + 1);
			boolean frontRight = (front || topFront) && (right || topRight) && BlockRug.canConnectRugTo(world, x + 1, y, z + 1);
			boolean backLeft = (back || topBack) && (left || topLeft) && BlockRug.canConnectRugTo(world, x - 1, y, z - 1);
			boolean backRight = (back || topBack) && (right || topRight) && BlockRug.canConnectRugTo(world, x + 1, y, z - 1);
			
			boolean topFrontLeft = 	airTop && (airTopFront 	|| airTopLeft) 	&& (front || topFront) && (left  || topLeft)  && !frontLeft  && BlockRug.canConnectRugTo(world, x - 1, y + 1, z + 1);
			boolean topFrontRight = airTop && (airTopFront 	|| airTopRight)	&& (front || topFront) && (right || topRight) && !frontRight && BlockRug.canConnectRugTo(world, x + 1, y + 1, z + 1);
			boolean topBackLeft = 	airTop && (airTopBack 	|| airTopLeft) 	&& (back  || topBack)  && (left  || topLeft)  && !backLeft   && BlockRug.canConnectRugTo(world, x - 1, y + 1, z - 1);
			boolean topBackRight = 	airTop && (airTopBack 	|| airTopRight) && (back  || topBack)  && (right || topRight) && !backRight  && BlockRug.canConnectRugTo(world, x + 1, y + 1, z - 1);
			
			boolean flat = false;
			
			
			if((front || bottomFront) && (left || bottomLeft) && (right || bottomRight) && (back || bottomBack))
			{
				flat = true;
				renderer.setRenderBounds(0.0, 0.0-downOffset, 0.0, 1.0, offset-downOffset, 1.0);
			}
			else
			{
				renderer.setRenderBounds(sideOffset, 0.0-downOffset, sideOffset, 1.0-sideOffset, offset-downOffset, 1.0-sideOffset);
			}
			renderer.renderStandardBlock(block, x, y, z);
			
			if(!flat)
			{
				if (topRight)
				{
					renderer.setRenderBounds(1.0-sideOffset, 0.0, sideOffset, 1.0, 1.0+offset, 1.0-sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if (topLeft)
				{
					renderer.setRenderBounds(0.0, 0.0, sideOffset, sideOffset, 1.0+offset, 1.0-sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if (topFront)
				{
					renderer.setRenderBounds(sideOffset, 0.0, 1.0-sideOffset, 1.0-sideOffset, 1.0+offset, 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if (topBack)
				{
					renderer.setRenderBounds(sideOffset, 0.0, 0.0, 1.0-sideOffset, 1.0+offset, sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if (topFrontLeft)
				{
					renderer.setRenderBounds(0.0, 0.0, 1.0-sideOffset, sideOffset, 1.0+offset, 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if (topFrontRight)
				{
					renderer.setRenderBounds(1.0-sideOffset, 0.0, 1.0-sideOffset, 1.0, 1.0+offset, 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if (topBackLeft)
				{

					renderer.setRenderBounds(0.0, 0.0, 0.0, sideOffset, 1.0+offset, sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if (topBackRight)
				{

					renderer.setRenderBounds(1.0-sideOffset, 0.0, 0.0, 1.0, 1.0+offset, sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}

				if (right)
				{
					renderer.setRenderBounds(1.0-sideOffset, 0.0-downOffset, sideOffset, 1.0, offset-downOffset, 1.0-sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if (left)
				{
					renderer.setRenderBounds(0.0, 0.0-downOffset, sideOffset, sideOffset, offset-downOffset, 1.0-sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if (front)
				{
					renderer.setRenderBounds(sideOffset, 0.0-downOffset, 1.0-sideOffset, 1.0-sideOffset, offset-downOffset, 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if (back)
				{
					renderer.setRenderBounds(sideOffset, 0.0-downOffset, 0.0, 1.0-sideOffset, offset-downOffset, sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}

				if (frontLeft)
				{
					renderer.setRenderBounds(0.0, 0.0, 1.0-sideOffset, sideOffset, ((topFront||topLeft)?1.0:0.0)+offset, 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if (frontRight)
				{
					renderer.setRenderBounds(1.0-sideOffset, 0.0, 1.0-sideOffset, 1.0, ((topFront||topRight)?1.0:0.0)+offset, 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if (backLeft)
				{

					renderer.setRenderBounds(0.0, 0.0, 0.0, sideOffset, ((topBack||topLeft)?1.0:0.0)+offset, sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if (backRight)
				{

					renderer.setRenderBounds(1.0-sideOffset, 0.0, 0.0, 1.0, ((topBack||topRight)?1.0:0.0)+offset, sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
		//}
		return true;
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
