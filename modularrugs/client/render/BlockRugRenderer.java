package zornco.modularrugs.client.render;

import net.minecraft.block.Block;
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
		
		int light = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
		tess.setBrightness(light);
		tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		if(world.doesBlockHaveSolidTopSurface(x, y - 1, z))
		{
			renderer.setRenderBounds(sideOffset, 0.0, sideOffset, 1.0-sideOffset, offset, 1.0-sideOffset);
			renderer.renderStandardBlock(block, x, y, z);
			boolean[][][] boolList = new boolean[3][3][3];
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					for (int k = -1; k < 2; k++) {
						boolList[i+1][j+1][k+1] = BlockRug.canConnectRugTo(world, x + i, y + j, z + k);
					}
				}
			}
			System.out.println("setstartA");
			int counter = 0;
			for (boolean[][] bs : boolList) {
				for (boolean[] bs2 : bs) {
					for (boolean b : bs2) {
						System.out.println((counter++)+" "+b);
					}
				}
			}
			System.out.println("setendB");
			// TODO figure out render pattern
			boolean right = BlockRug.canConnectRugTo(world, x + 1, y, z);
			boolean left = BlockRug.canConnectRugTo(world, x - 1, y, z);
			boolean front = BlockRug.canConnectRugTo(world, x, y, z + 1);
			boolean back = BlockRug.canConnectRugTo(world, x, y, z - 1);
			boolean frontLeft = front && left && BlockRug.canConnectRugTo(world, x - 1, y, z + 1);
			boolean frontRight = front && right && BlockRug.canConnectRugTo(world, x + 1, y, z + 1);
			boolean backLeft = back && left && BlockRug.canConnectRugTo(world, x - 1, y, z - 1);
			boolean backRight = back && right && BlockRug.canConnectRugTo(world, x + 1, y, z - 1);
			
			boolean underRight = !right && BlockRug.canConnectRugTo(world, x + 1, y - 1, z);
			boolean underLeft = !left && BlockRug.canConnectRugTo(world, x - 1, y - 1, z);
			boolean underFront = !front && BlockRug.canConnectRugTo(world, x, y - 1, z + 1);
			boolean underBack = !back && BlockRug.canConnectRugTo(world, x, y - 1, z - 1);
			boolean underFrontLeft = !frontLeft && BlockRug.canConnectRugTo(world, x - 1, y - 1, z + 1);
			boolean underFrontRight = !frontRight && BlockRug.canConnectRugTo(world, x + 1, y - 1, z + 1);
			boolean underBackLeft = !backLeft && BlockRug.canConnectRugTo(world, x - 1, y - 1, z - 1);
			boolean underBackRight = !backRight && BlockRug.canConnectRugTo(world, x + 1, y - 1, z - 1);
			
			boolean top = world.isAirBlock(x, y + 1, z);
			boolean topRight = !top && !right && BlockRug.canConnectRugTo(world, x + 1, y - 1, z);
			boolean topLeft = !top && !left && BlockRug.canConnectRugTo(world, x - 1, y - 1, z);
			boolean topFront = !top && !front && BlockRug.canConnectRugTo(world, x, y - 1, z + 1);
			boolean topBack = !top && !back && BlockRug.canConnectRugTo(world, x, y - 1, z - 1);
			boolean topFrontLeft = !top && !frontLeft && BlockRug.canConnectRugTo(world, x - 1, y - 1, z + 1);
			boolean topFrontRight = !top && !frontRight && BlockRug.canConnectRugTo(world, x + 1, y - 1, z + 1);
			boolean topBackLeft = !top && !backLeft && BlockRug.canConnectRugTo(world, x - 1, y - 1, z - 1);
			boolean topBackRight = !top && !backRight && BlockRug.canConnectRugTo(world, x + 1, y - 1, z - 1);
			
			
			if (right)
			{
				renderer.setRenderBounds(1.0-sideOffset, 0.0, sideOffset, 1.0, offset, 1.0-sideOffset);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if (left)
			{
				renderer.setRenderBounds(0.0, 0.0, sideOffset, sideOffset, offset, 1.0-sideOffset);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if (front)
			{
				renderer.setRenderBounds(sideOffset, 0.0, 1.0-sideOffset, 1.0-sideOffset, offset, 1.0);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if (back)
			{
				renderer.setRenderBounds(sideOffset, 0.0, 0.0, 1.0-sideOffset, offset, sideOffset);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if (frontLeft)
			{
				renderer.setRenderBounds(0.0, 0.0, 1.0-sideOffset, sideOffset, offset, 1.0);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if (frontRight)
			{
				renderer.setRenderBounds(1.0-sideOffset, 0.0, 1.0-sideOffset, 1.0, offset, 1.0);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if (backLeft)
			{
	
				renderer.setRenderBounds(0.0, 0.0, 0.0, sideOffset, offset, sideOffset);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if (backRight)
			{
	
				renderer.setRenderBounds(1.0-sideOffset, 0.0, 0.0, 1.0, offset, sideOffset);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
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
