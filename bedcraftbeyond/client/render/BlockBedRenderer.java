package zornco.bedcraftbeyond.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Direction;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import zornco.bedcraftbeyond.BedCraftBeyond;
import zornco.bedcraftbeyond.blocks.BlockColoredBed;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockBedRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block1, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		BlockColoredBed block = (BlockColoredBed)block1; 
		int i1 = block.getBedDirection(world, x, y, z);
		boolean flag = block.isBedFoot(world, x, y, z);
		float f = 0.5F;
		float f1 = 1.0F;
		float f2 = 0.8F;
		float f3 = 0.6F;
		int red, green, blue;
		//Render Bottom
		int j1 = block.getMixedBrightnessForBlock(world, x, y, z);
		tessellator.setBrightness(j1);
		tessellator.setColorOpaque_F(f, f, f);
		Icon icon = block.getIcon(0, world.getBlockMetadata(x, y, z), 0, world, x, y, z);
		if (renderer.hasOverrideBlockTexture()) icon = renderer.overrideBlockTexture; //BugFix Proper breaking texture on underside
		double d0 = icon.getMinU();
		double d1 = icon.getMaxU();
		double d2 = icon.getMinV();
		double d3 = icon.getMaxV();
		double d4 = x + renderer.renderMinX;
		double d5 = x + renderer.renderMaxX;
		double d6 = y + renderer.renderMinY + 0.1875D;
		double d7 = z + renderer.renderMinZ;
		double d8 = z + renderer.renderMaxZ;
		tessellator.addVertexWithUV(d4, d6, d8, d0, d3);
		tessellator.addVertexWithUV(d4, d6, d7, d0, d2);
		tessellator.addVertexWithUV(d5, d6, d7, d1, d2);
		tessellator.addVertexWithUV(d5, d6, d8, d1, d3);
		
		//Render Top
		tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y + 1, z));
		tessellator.setColorOpaque_F(f1, f1, f1);
		for (int i = 0; i < 3; i++) {
			tessellator.setColorOpaque_I(BlockColoredBed.getColorFromTilePerPass(world, x, y, z, i));
			icon = block.getIcon(1, world.getBlockMetadata(x, y, z), i, world, x, y, z);
			if (renderer.hasOverrideBlockTexture()) icon = renderer.overrideBlockTexture; //BugFix Proper breaking texture on underside
			d0 = icon.getMinU();
			d1 = icon.getMaxU();
			d2 = icon.getMinV();
			d3 = icon.getMaxV();
			d4 = d0;
			d5 = d1;
			d6 = d2;
			d7 = d2;
			d8 = d0;
			double d9 = d1;
			double d10 = d3;
			double d11 = d3;

			if (i1 == 0)
			{
				d5 = d0;
				d6 = d3;
				d8 = d1;
				d11 = d2;
			}
			else if (i1 == 2)
			{
				d4 = d1;
				d7 = d3;
				d9 = d0;
				d10 = d2;
			}
			else if (i1 == 3)
			{
				d4 = d1;
				d7 = d3;
				d9 = d0;
				d10 = d2;
				d5 = d0;
				d6 = d3;
				d8 = d1;
				d11 = d2;
			}

			double d12 = x + renderer.renderMinX;
			double d13 = x + renderer.renderMaxX;
			double d14 = y + renderer.renderMaxY;
			double d15 = z + renderer.renderMinZ;
			double d16 = z + renderer.renderMaxZ;
			tessellator.addVertexWithUV(d13, d14, d16, d8, d10);
			tessellator.addVertexWithUV(d13, d14, d15, d4, d6);
			tessellator.addVertexWithUV(d12, d14, d15, d5, d7);
			tessellator.addVertexWithUV(d12, d14, d16, d9, d11);
		}
		//Render Sides
		int k1 = Direction.directionToFacing[i1];

		if (flag)
		{
			k1 = Direction.directionToFacing[Direction.rotateOpposite[i1]];
		}

		byte b0 = 4;

		switch (i1)
		{
		case 0:
			b0 = 5;
			break;
		case 1:
			b0 = 3;
		case 2:
		default:
			break;
		case 3:
			b0 = 2;
		}

		if (k1 != 2 && (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y, z - 1, 2)))
		{
			tessellator.setBrightness(renderer.renderMinZ > 0.0D ? j1 : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1));
			tessellator.setColorOpaque_F(f2, f2, f2);
			for (int i = 0; i < 3; i++) {
				tessellator.setColorOpaque_I(BlockColoredBed.getColorFromTilePerPass(world, x, y, z, i));
				renderer.flipTexture = b0 == 2;
				renderer.renderFaceZNeg(block, x, y, z, block.getIcon(2, world.getBlockMetadata(x, y, z), i, world, x, y, z));
			}
		}
		if (k1 != 3 && (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y, z + 1, 3)))
		{
			tessellator.setBrightness(renderer.renderMaxZ < 1.0D ? j1 : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1));
			tessellator.setColorOpaque_F(f2, f2, f2);
			for (int i = 0; i < 3; i++) {
				tessellator.setColorOpaque_I(BlockColoredBed.getColorFromTilePerPass(world, x, y, z, i));
				renderer.flipTexture = b0 == 3;
				renderer.renderFaceZPos(block, x, y, z, block.getIcon(3, world.getBlockMetadata(x, y, z), i, world, x, y, z));
			}
		}

		if (k1 != 4 && (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x - 1, y, z, 4)))
		{
			tessellator.setBrightness(renderer.renderMinZ > 0.0D ? j1 : block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z));
			tessellator.setColorOpaque_F(f3, f3, f3);
			for (int i = 0; i < 3; i++) {
				tessellator.setColorOpaque_I(BlockColoredBed.getColorFromTilePerPass(world, x, y, z, i));
				renderer.flipTexture = b0 == 4;
				renderer.renderFaceXNeg(block, x, y, z, block.getIcon(4, world.getBlockMetadata(x, y, z), i, world, x, y, z));
			}
		}

		if (k1 != 5 && (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x + 1, y, z, 5)))
		{
			tessellator.setBrightness(renderer.renderMaxZ < 1.0D ? j1 : block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z));
			tessellator.setColorOpaque_F(f3, f3, f3);
			for (int i = 0; i < 3; i++) {
				tessellator.setColorOpaque_I(BlockColoredBed.getColorFromTilePerPass(world, x, y, z, i));
				renderer.flipTexture = b0 == 5;
				renderer.renderFaceXPos(block, x, y, z, block.getIcon(5, world.getBlockMetadata(x, y, z), i, world, x, y, z));
			}
		}

		renderer.flipTexture = false;
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return BedCraftBeyond.bedRI;
	}
}
