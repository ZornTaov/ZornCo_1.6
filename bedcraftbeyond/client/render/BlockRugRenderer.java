package zornco.bedcraftbeyond.client.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import zornco.bedcraftbeyond.BedCraftBeyond;
import zornco.bedcraftbeyond.blocks.BlockRug;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

class RugStruct {

	public boolean air = false;
	public boolean rug = false;
	public boolean slab = false;
	public boolean stair = false;
	public boolean upsidedown = false;
	public byte direction = -1;
}

public class BlockRugRenderer implements ISimpleBlockRenderingHandler {
	private RugStruct[][][] byteList = new RugStruct[3][3][3];

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
		/**
		 * 1 	0x1		metadata (stair direction)
		 * 2 	0x2		metadata (stair direction)
		 * 4 	0x4		metadata (stair upsidedown flag)
		 * 8 	0x8		metadata (slab upsidedown flag)
		 * 16 	0x10	air
		 * 32	0x20	rug
		 * 64	0x40	slab
		 * 128	0x80	stair
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
		int ID = 0;
		int META = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				for (int k = -1; k < 2; k++) {
					byteList[i+1][j+1][k+1] = new RugStruct();
					ID = world.getBlockId(x + i, y + j, z + k);
					META = world.getBlockMetadata(x + i, y + j, z + k);
					byteList[i+1][j+1][k+1].direction = -1;
					if(world.isAirBlock(x + i, y + j, z + k))
					{
						byteList[i+1][j+1][k+1].air = true;
					}
					else if(ID == BedCraftBeyond.rugBlock.blockID)
					{
						byteList[i+1][j+1][k+1].rug = true;
					}
					else if(ID > 0 && Block.blocksList[ID] instanceof BlockHalfSlab)
					{
						byteList[i+1][j+1][k+1].slab = true;
						byteList[i+1][j+1][k+1].upsidedown = ((META & 0x8) == 8);
					}
					else if(BlockStairs.isBlockStairsID(ID))
					{
						byteList[i+1][j+1][k+1].stair = true;
						byteList[i+1][j+1][k+1].upsidedown = ((META & 0x4) == 4);
						byteList[i+1][j+1][k+1].direction = (byte)(META & 0x3);
					}
				}
			}
		}

		int light = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
		tess.setBrightness(light);
		tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);

		if (byteList[2][2][1].rug && byteList[1][2][1].air)
		{
			if(byteList[2][1][1].stair && !((byteList[2][1][1].direction == 0) || (byteList[2][1][1].direction == 1)) && !byteList[2][1][1].upsidedown )
			{
				if(byteList[2][1][1].direction == 2)
				{
					renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.0+sideOffset, 1.0, 0.5-getDownOffset(byteList[2][1][1], 0), 0.5);
					renderer.renderStandardBlock(block, x, y, z);


					renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.5, 1.0, 1.0-getDownOffset(byteList[2][1][1], 0), 1.0-sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}
				else if(byteList[2][1][1].direction == 3)
				{
					renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.0+sideOffset, 1.0, 1.0-getDownOffset(byteList[2][1][1], 0), 0.5);
					renderer.renderStandardBlock(block, x, y, z);


					renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.5, 1.0, 0.5-getDownOffset(byteList[2][1][1], 0), 1.0-sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			else
			{
				renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1), sideOffset, 1.0, 1.0-getDownOffset(byteList[2][1][1], 0), 1.0-sideOffset);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
		if (byteList[0][2][1].rug && byteList[1][2][1].air)
		{
			if(byteList[0][1][1].stair && !((byteList[0][1][1].direction == 0) || (byteList[0][1][1].direction == 1)) && !byteList[0][1][1].upsidedown )
			{
				if(byteList[0][1][1].direction == 2)
				{
					renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.0+sideOffset, thickness, 0.5-getDownOffset(byteList[0][1][1], 1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);


					renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.5, thickness, 1.0-getDownOffset(byteList[0][1][1], 1), 1.0-sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}
				else if(byteList[0][1][1].direction == 3)
				{
					renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.0+sideOffset, thickness, 1.0-getDownOffset(byteList[0][1][1], 1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);


					renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.5, thickness, 0.5-getDownOffset(byteList[0][1][1], 1), 1.0-sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			else
			{
				renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1), sideOffset, thickness, 1.0-getDownOffset(byteList[0][1][1], 1), 1.0-sideOffset);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
		if (byteList[1][2][2].rug && byteList[1][2][1].air)
		{
			if(byteList[1][1][2].stair && !((byteList[1][1][2].direction == 2) || (byteList[1][1][2].direction == 3)) && !byteList[1][1][2].upsidedown )
			{
				if(byteList[1][1][2].direction == 0)
				{
					renderer.setRenderBounds(0.0+sideOffset, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 1.0-thickness, 0.5, 0.5-getDownOffset(byteList[1][1][2], 2), 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 1.0-thickness, 1.0-sideOffset, 1.0-getDownOffset(byteList[1][1][2], 2), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
				else if(byteList[1][1][2].direction == 1)
				{
					renderer.setRenderBounds(0.0+sideOffset, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 1.0-thickness, 0.5, 1.0-getDownOffset(byteList[1][1][2], 2), 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 1.0-thickness, 1.0-sideOffset, 0.5-getDownOffset(byteList[1][1][2], 2), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			else
			{
				renderer.setRenderBounds(sideOffset, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 1.0-thickness, 1.0-sideOffset, 1.0-getDownOffset(byteList[1][1][2], 2), 1.0);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
		if (byteList[1][2][0].rug && byteList[1][2][1].air)
		{
			if(byteList[1][1][0].stair && !((byteList[1][1][0].direction == 2) || (byteList[1][1][0].direction == 3)) && !byteList[1][1][0].upsidedown )
			{
				if(byteList[1][1][0].direction == 0)
				{
					renderer.setRenderBounds(0.0+sideOffset, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.0, 0.5, 0.5-getDownOffset(byteList[1][1][0], 3), thickness);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.0, 1.0-sideOffset, 1.0-getDownOffset(byteList[1][1][0], 3), thickness);
					renderer.renderStandardBlock(block, x, y, z);
				}
				else if(byteList[1][1][0].direction == 1)
				{
					renderer.setRenderBounds(0.0+sideOffset, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.0, 0.5, 1.0-getDownOffset(byteList[1][1][0], 3), thickness);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.0, 1.0-sideOffset, 0.5-getDownOffset(byteList[1][1][0], 3), thickness);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			else
			{
				renderer.setRenderBounds(sideOffset, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.0, 1.0-sideOffset, 1.0-getDownOffset(byteList[1][1][0], 3), thickness);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
		/**
		 * SLABS
		 * ----------------------------------------------------------------------------------------------------------------------------------------------------
		 */
		if (byteList[1][0][1].slab && !byteList[1][0][1].upsidedown)
		{
			if (byteList[2][1][1].rug)
			{
				if(byteList[2][0][1].stair && !byteList[2][0][1].upsidedown )
				{
					if(byteList[2][0][1].direction == 1)
					{
						renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1), sideOffset, 1.0, 0.0-getDownOffset(byteList[2][0][1], -1), 1.0-sideOffset);
						renderer.renderStandardBlock(block, x, y, z);
					}
					else if(byteList[2][0][1].direction == 2)
					{
						renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.5, 1.0, 0.0-getDownOffset(byteList[2][0][1], 0), 1.0-sideOffset);
						renderer.renderStandardBlock(block, x, y, z);
					}
					else if(byteList[2][0][1].direction == 3)
					{
						renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.0+sideOffset, 1.0, 0.0-getDownOffset(byteList[2][0][1], 0), 0.5);
						renderer.renderStandardBlock(block, x, y, z);
					}
				}
				else if (!(byteList[2][0][1].slab && !byteList[2][0][1].upsidedown))
				{
					renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1), sideOffset, 1.0, 0.0-getDownOffset(byteList[2][1][1], -1), 1.0-sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			if (byteList[0][1][1].rug)
			{
				if(byteList[0][0][1].stair && !byteList[0][0][1].upsidedown )
				{
					if(byteList[0][0][1].direction == 0)
					{
						renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1), sideOffset, thickness, 0.0-getDownOffset(byteList[0][0][1], -1), 1.0-sideOffset);
						renderer.renderStandardBlock(block, x, y, z);
					}
					else if(byteList[0][0][1].direction == 2)
					{
						renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.5, thickness, 0.0-getDownOffset(byteList[0][0][1], 1), 1.0-sideOffset);
						renderer.renderStandardBlock(block, x, y, z);
					}
					else if(byteList[0][0][1].direction == 3)
					{
						renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.0+sideOffset, thickness, 0.0-getDownOffset(byteList[0][0][1], 1), 0.5);
						renderer.renderStandardBlock(block, x, y, z);
					}
				}
				else if (!(byteList[0][0][1].slab && !byteList[0][0][1].upsidedown))
				{
					renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1), sideOffset, thickness, 0.0-getDownOffset(byteList[0][1][1], -1), 1.0-sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			if (byteList[1][1][2].rug)
			{
				if(byteList[1][0][2].stair && !byteList[1][0][2].upsidedown )
				{
					if(byteList[1][0][2].direction == 0)
					{
						renderer.setRenderBounds(0.5, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 1.0-thickness, 1.0-sideOffset, 0.0-getDownOffset(byteList[1][0][2], 2), 1.0);
						renderer.renderStandardBlock(block, x, y, z);
					}
					else if(byteList[1][0][2].direction == 1)
					{
						renderer.setRenderBounds(0.0+sideOffset, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 1.0-thickness, 0.5, 0.0-getDownOffset(byteList[1][0][2], 2), 1.0);
						renderer.renderStandardBlock(block, x, y, z);
					}
					else if(byteList[1][0][2].direction == 3)
					{
						renderer.setRenderBounds(sideOffset, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 1.0-thickness, 1.0-sideOffset, 0.0-getDownOffset(byteList[1][0][2], -1), 1.0);
						renderer.renderStandardBlock(block, x, y, z);
					}
				}
				else if (!(byteList[1][0][2].slab && !byteList[1][0][2].upsidedown))
				{
					renderer.setRenderBounds(sideOffset, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 1.0-thickness, 1.0-sideOffset, 0.0-getDownOffset(byteList[1][1][2], -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			if (byteList[1][1][0].rug)
			{
				if(byteList[1][0][0].stair && !byteList[1][0][0].upsidedown )
				{
					if(byteList[1][0][0].direction == 0)
					{
						renderer.setRenderBounds(0.5, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.0, 1.0-sideOffset, 0.0-getDownOffset(byteList[1][0][0], 3), thickness);
						renderer.renderStandardBlock(block, x, y, z);
					}
					else if(byteList[1][0][0].direction == 1)
					{
						renderer.setRenderBounds(0.0+sideOffset, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.0, 0.5, 0.0-getDownOffset(byteList[1][0][0], 3), thickness);
						renderer.renderStandardBlock(block, x, y, z);
					}
					else if(byteList[1][0][0].direction == 2)
					{
						renderer.setRenderBounds(sideOffset, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.0, 1.0-sideOffset, 0.0-getDownOffset(byteList[1][0][0], -1), thickness);
						renderer.renderStandardBlock(block, x, y, z);
					}
				}
				else if (!(byteList[1][0][0].slab && !byteList[1][0][0].upsidedown))
				{
					renderer.setRenderBounds(sideOffset, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.0, 1.0-sideOffset, 0.0-getDownOffset(byteList[1][1][0], -1), thickness);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
		}

		/**
		 * STAIRS
		 * ----------------------------------------------------------------------------------------------------------------------------------------------------
		 * 
		 *      y
		 * 220 221 222
		 * 120 121 122
		 * 020 021 022
		 *      x
		 * 210 211 212
		 -z110 rug 112z
		 * 010 011 012
		 *     -x
		 * 200 201 202
		 * 100 101 102
		 * 000 001 002
		 *     -y
		 * 
		 */
		if (byteList[1][0][1].stair && !byteList[1][0][1].upsidedown)
		{
			boolean flag = func_82542_g(renderer);
			renderer.renderStandardBlock(block, x, y, z);
			if (flag && func_82544_h(renderer))
			{
				renderer.renderStandardBlock(block, x, y, z);
			}
			//upsidedown

			//front
			/*switch(byteList[1][0][1].direction)
			{
			default:
			{
				renderer.setRenderBounds(0.5, 0.0-getDownOffset(byteList[1][0][1], -1), 0.0, 1.0, offset-getDownOffset(byteList[1][0][1], -1), 1.0);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.0, -0.5-getDownOffset(byteList[1][0][1], -1), 0.0, 0.5, offset-0.5-getDownOffset(byteList[1][0][1], -1), 1.0);
				renderer.renderStandardBlock(block, x, y, z);

				if ((byteList[0][1][1].rug || byteList[0][2][1].rug))
				{
					if (byteList[0][0][1].stair)
					{
						if(byteList[0][0][1].direction == 0)
						{
							renderer.setRenderBounds(0.0, -0.5+offset-getDownOffset(byteList[1][0][1], -1), sideOffset, thickness, 0.0-getDownOffset(byteList[0][0][1], -1), 1.0-sideOffset);
							renderer.renderStandardBlock(block, x, y, z);

							renderer.setRenderBounds(0.5-thickness, -0.5+offset-getDownOffset(byteList[1][0][1], -1), sideOffset, 0.5, 0.0, 1.0-sideOffset);
							renderer.renderStandardBlock(block, x, y, z);
						}
						else if(byteList[0][0][1].direction == 1)
						{
							renderer.setRenderBounds(0.5-thickness, -0.5+offset-getDownOffset(byteList[1][0][1], -1), sideOffset, 0.5, 0.0, 1.0-sideOffset);
							renderer.renderStandardBlock(block, x, y, z);
						}
						else if(byteList[0][0][1].direction == 2)
						{
							renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1), 0.5, thickness, 0.0-getDownOffset(byteList[0][0][1], 1), 1.0-sideOffset);
							renderer.renderStandardBlock(block, x, y, z);
						}
						else if(byteList[0][0][1].direction == 3)
						{
							renderer.setRenderBounds(0.0, -0.5+offset-getDownOffset(byteList[1][0][1], -1), 0.0+sideOffset, thickness, 0.0-getDownOffset(byteList[0][0][1], 1), 0.5);
							renderer.renderStandardBlock(block, x, y, z);
						}
					}
				}
				else
				{
					renderer.setRenderBounds(0.5-thickness, -0.5+offset-getDownOffset(byteList[1][0][1], -1), sideOffset, 0.5, 0.0, 1.0-sideOffset);
					renderer.renderStandardBlock(block, x, y, z);
				}

				break;
			}
			case 1:
			{
				renderer.setRenderBounds(0.5, -0.5-getDownOffset(byteList[1][0][1], -1), 0.0, 1.0, offset-0.5-getDownOffset(byteList[1][0][1], -1), 1.0);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.0, 0.0-getDownOffset(byteList[1][0][1], -1), 0.0, 0.5, offset-getDownOffset(byteList[1][0][1], -1), 1.0);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.5, -0.5+offset-getDownOffset(byteList[1][0][1], -1), sideOffset, 0.5+thickness, 0.0, 1.0-sideOffset);
				renderer.renderStandardBlock(block, x, y, z);
				break;
			}
			case 2:
			{
				renderer.setRenderBounds(0.0, 0.0-getDownOffset(byteList[1][0][1], -1), 0.5, 1.0, offset-getDownOffset(byteList[1][0][1], -1), 1.0);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.0, -0.5-getDownOffset(byteList[1][0][1], -1), 0.0, 1.0, offset-0.5-getDownOffset(byteList[1][0][1], -1), 0.5);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(sideOffset, -0.5+offset-getDownOffset(byteList[1][0][1], -1), 0.5-thickness, 1.0-sideOffset, 0.0-getDownOffset(byteList[1][1][2], 2), 0.5);
				renderer.renderStandardBlock(block, x, y, z);

				break;
			}
			case 3:
			{
				renderer.setRenderBounds(0.0, -0.5-getDownOffset(byteList[1][0][1], -1), 0.5, 1.0, offset-0.5-getDownOffset(byteList[1][0][1], -1), 1.0);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(0.0, 0.0-getDownOffset(byteList[1][0][1], -1), 0.0, 1.0, offset-getDownOffset(byteList[1][0][1], -1), 0.5);
				renderer.renderStandardBlock(block, x, y, z);

				renderer.setRenderBounds(sideOffset, -0.5+offset-getDownOffset(byteList[1][0][1], -1), 0.5, 1.0-sideOffset, 0.0-getDownOffset(byteList[1][1][2], 2), 0.5+thickness);
				renderer.renderStandardBlock(block, x, y, z);

				break;
			}
			}*/

			//back
			//left
			//right

		}
		else 
		{
			renderer.setRenderBounds(0.0, 0.0-getDownOffset(byteList[1][0][1], -1), 0.0, 1.0, offset-getDownOffset(byteList[1][0][1], -1), 1.0);
			renderer.partialRenderBounds = false;
			renderer.renderStandardBlock(block, x, y, z);
		}
		return true;
	}
	private boolean isBlockStairsSame(int x, int z)
	{
		return byteList[x][0][z].upsidedown == byteList[1][0][1].upsidedown && byteList[x][0][z].direction == byteList[1][0][1].direction;
	}
	public boolean func_82542_g(RenderBlocks renderer)
	{
		float bottomEdge = 0.5F;
		float topEdge = 1.0F;
		float backEdge = 0.0F;
		float frontEdge = 1.0F;
		float leftEdge = 0.0F;
		float rightEdge = 0.5F;
		boolean flag = true;

		if (byteList[1][0][1].direction == 0)
		{
			backEdge = 0.5F;
			rightEdge = 1.0F;

			if (byteList[2][0][1].stair && !byteList[2][0][1].upsidedown)
			{				
				if (byteList[2][0][1].direction == 3 && !isBlockStairsSame(1,2))
				{
					rightEdge = 0.5F;
					flag = false;
				}
				else if (byteList[2][0][1].direction == 2 && !isBlockStairsSame(1,0))
				{
					leftEdge = 0.5F;
					flag = false;
				}
			}
		}
		else if (byteList[1][0][1].direction == 1)
		{
			frontEdge = 0.5F;
			rightEdge = 1.0F;

			if (byteList[0][0][1].stair && !byteList[0][0][1].upsidedown)
			{
				if (byteList[0][0][1].direction == 3 && !isBlockStairsSame(1,2))
				{
					rightEdge = 0.5F;
					flag = false;
				}
				else if (byteList[0][0][1].direction == 2 && !isBlockStairsSame(1,0))
				{
					leftEdge = 0.5F;
					flag = false;
				}
			}
		}
		else if (byteList[1][0][1].direction == 2)
		{
			leftEdge = 0.5F;
			rightEdge = 1.0F;

			if (byteList[1][0][2].stair && !byteList[1][0][2].upsidedown)
			{
				if (byteList[1][0][2].direction == 1 && !isBlockStairsSame(2,1))
				{
					frontEdge = 0.5F;
					flag = false;
				}
				else if (byteList[1][0][2].direction == 0 && !isBlockStairsSame(0,1))
				{
					backEdge = 0.5F;
					flag = false;
				}
			}
		}
		else if (byteList[1][0][1].direction == 3)
		{
			if (byteList[1][0][0].stair && !byteList[1][0][0].upsidedown)
			{
				if (byteList[1][0][0].direction == 1 && !isBlockStairsSame(2,1))
				{
					frontEdge = 0.5F;
					flag = false;
				}
				else if (byteList[1][0][0].direction == 0 && !isBlockStairsSame(0,1))
				{
					backEdge = 0.5F;
					flag = false;
				}
			}
		}
		
		renderer.setRenderBounds(backEdge, bottomEdge, leftEdge, frontEdge, topEdge, rightEdge);
		return flag;
	}

	public boolean func_82544_h(RenderBlocks renderer)
	{
		float bottomEdge = 0.5F;
		float topEdge = 1.0F;
		float backEdge = 0.0F;
		float frontEdge = 0.5F;
		float leftEdge = 0.5F;
		float rightEdge = 1.0F;
		boolean flag = false;

		if (byteList[1][0][1].direction == 0)
		{
			if (byteList[0][0][1].stair && !byteList[0][0][1].upsidedown)
			{
				if (byteList[0][0][1].direction == 3 && !isBlockStairsSame(1,0))
				{
					leftEdge = 0.0F;
					rightEdge = 0.5F;
					flag = true;
				}
				else if (byteList[0][0][1].direction == 2 && !isBlockStairsSame(1,2))
				{
					leftEdge = 0.5F;
					rightEdge = 1.0F;
					flag = true;
				}
			}
		}
		else if (byteList[1][0][1].direction == 1)
		{
			if (byteList[2][0][1].stair && !byteList[2][0][1].upsidedown)
			{
				backEdge = 0.5F;
				frontEdge = 1.0F;
				if (byteList[2][0][1].direction == 3 && !isBlockStairsSame(1,0))
				{
					leftEdge = 0.0F;
					rightEdge = 0.5F;
					flag = true;
				}
				else if (byteList[2][0][1].direction == 2 && !isBlockStairsSame(1,2))
				{
					leftEdge = 0.5F;
					rightEdge = 1.0F;
					flag = true;
				}
			}
		}
		else if (byteList[1][0][1].direction == 2)
		{
			if (byteList[1][0][0].stair && !byteList[1][0][0].upsidedown)
			{
				leftEdge = 0.0F;
				rightEdge = 0.5F;
				if (byteList[1][0][0].direction == 1 && !isBlockStairsSame(0,1))
				{
					flag = true;
				}
				else if (byteList[1][0][0].direction == 0 && !isBlockStairsSame(2,1))
				{
					backEdge = 0.5F;
					frontEdge = 1.0F;
					flag = true;
				}
			}
		}
		else if (byteList[1][0][1].direction == 3)
		{
			if (byteList[1][0][2].stair && !byteList[1][0][2].upsidedown)
			{
				if (byteList[1][0][2].direction == 1 && !isBlockStairsSame(0,1))
				{
					flag = true;
				}
				else if (byteList[1][0][2].direction == 0 && !isBlockStairsSame(2,1))
				{
					backEdge = 0.5F;
					frontEdge = 1.0F;
					flag = true;
				}
			}
		}

		if (flag)
		{
			renderer.setRenderBounds(backEdge, bottomEdge, leftEdge, frontEdge, topEdge, rightEdge);
		}

		return flag;
	}
	public double getDownOffset(RugStruct checkBlock, int side)
	{
		if(checkBlock.slab && !checkBlock.upsidedown) return 0.5;
		if(checkBlock.stair && side == checkBlock.direction && !checkBlock.upsidedown) return 0.5;
		return 0.0;
	}
	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return BedCraftBeyond.rugRI;
	}
}
