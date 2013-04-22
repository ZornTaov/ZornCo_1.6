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
			
			// TODO figure out render pattern
			boolean right = BlockRug.canConnectRugTo(world, x + 1, y, z);
			boolean left = BlockRug.canConnectRugTo(world, x - 1, y, z);
			boolean front = BlockRug.canConnectRugTo(world, x, y, z + 1);
			boolean back = BlockRug.canConnectRugTo(world, x, y, z - 1);
			boolean airTop = world.isAirBlock(x, y + 1, z);
			boolean airTopRight = world.isAirBlock(x + 1, y + 1, z);
			boolean airTopLeft = world.isAirBlock(x - 1, y + 1, z);
			boolean airTopFront = world.isAirBlock(x, y + 1, z + 1);
			boolean airTopBack = world.isAirBlock(x, y + 1, z - 1);
			boolean topRight = airTop && !right && BlockRug.canConnectRugTo(world, x + 1, y + 1, z);
			boolean topLeft = airTop && !left && BlockRug.canConnectRugTo(world, x - 1, y + 1, z);
			boolean topFront = airTop && !front && BlockRug.canConnectRugTo(world, x, y + 1, z + 1);
			boolean topBack = airTop && !back && BlockRug.canConnectRugTo(world, x, y + 1, z - 1);
			
			boolean frontLeft = (front || topFront) && (left || topLeft) && BlockRug.canConnectRugTo(world, x - 1, y, z + 1);
			boolean frontRight = (front || topFront) && (right || topRight) && BlockRug.canConnectRugTo(world, x + 1, y, z + 1);
			boolean backLeft = (back || topBack) && (left || topLeft) && BlockRug.canConnectRugTo(world, x - 1, y, z - 1);
			boolean backRight = (back || topBack) && (right || topRight) && BlockRug.canConnectRugTo(world, x + 1, y, z - 1);
			
			boolean topFrontLeft = airTop && (airTopFront || airTopLeft) && (front || topFront) && (left || topLeft) && !frontLeft && BlockRug.canConnectRugTo(world, x - 1, y + 1, z + 1);
			boolean topFrontRight = airTop && (airTopFront || airTopRight) && (front || topFront) && (right || topRight) && !frontRight && BlockRug.canConnectRugTo(world, x + 1, y + 1, z + 1);
			boolean topBackLeft = airTop && (airTopBack || airTopLeft) && (back || topBack) && (left || topLeft) && !backLeft && BlockRug.canConnectRugTo(world, x - 1, y + 1, z - 1);
			boolean topBackRight = airTop && (airTopBack || airTopRight) && (back || topBack) && (right || topRight) && !backRight && BlockRug.canConnectRugTo(world, x + 1, y + 1, z - 1);
			if (topRight)
			{
				renderer.setRenderBounds(1.0-sideOffset, 0.0, sideOffset, 1.0+sideOffset, 1.0+offset, 1.0-sideOffset);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if (topLeft)
			{
				renderer.setRenderBounds(0.0-sideOffset, 0.0, sideOffset, sideOffset, 1.0+offset, 1.0-sideOffset);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if (topFront)
			{
				renderer.setRenderBounds(sideOffset, 0.0, 1.0-sideOffset, 1.0-sideOffset, 1.0+offset, 1.0+sideOffset);
				renderer.renderStandardBlock(block, x, y, z);
			}
			if (topBack)
			{
				renderer.setRenderBounds(sideOffset, 0.0, 0.0-sideOffset, 1.0-sideOffset, 1.0+offset, sideOffset);
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
