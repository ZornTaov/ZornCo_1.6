package zornco.bedcraftbeyond.client.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import zornco.bedcraftbeyond.BedCraftBeyond;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

class RugStruct {

	public boolean air = false;
	public boolean rug = false;
	public boolean slab = false;
	public boolean stair = false;
	public boolean upsidedown = false;
	public byte direction = -1;
	public byte corner = 0;
}

public class BlockRugRenderer implements ISimpleBlockRenderingHandler {
	private RugStruct[][][] byteList = new RugStruct[3][3][3];
	/*private static double[][][] blah = {{
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0}},{
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0.5,0,0.5,0,0,0,0},
			{0,0,0,0,0,0,0,0,0}}};*/
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
		int corner = 0;
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
						corner = stairMainCube(world, x+i, y+j, z+k);
						byteList[i+1][j+1][k+1].corner = (byte) (corner != 0 ? corner : stairSecondCube(world, x+i, y+j, z+k)) ;
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
					renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, 1.0-getDownOffset(byteList[2][1][1], 0, 0), 0.5);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 1.0, 1.0-getDownOffset(byteList[2][1][1], 0, 1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
				else if(byteList[2][1][1].direction == 3)
				{
					renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, 1.0-getDownOffset(byteList[2][1][1], 0, 0), 0.5);
					renderer.renderStandardBlock(block, x, y, z);
					
					renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 1.0, 1.0-getDownOffset(byteList[2][1][1], 0, 1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			else
			{
				renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, 1.0-getDownOffset(byteList[2][1][1], 0, 0), 1.0);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
		if (byteList[0][2][1].rug && byteList[1][2][1].air)
		{
			if(byteList[0][1][1].stair && !((byteList[0][1][1].direction == 0) || (byteList[0][1][1].direction == 1)) && !byteList[0][1][1].upsidedown )
			{
				if(byteList[0][1][1].direction == 2)
				{
					renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, thickness, 1.0-getDownOffset(byteList[0][1][1], 1, 0), 0.5);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, thickness, 1.0-getDownOffset(byteList[0][1][1], 1, 1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
				else if(byteList[0][1][1].direction == 3)
				{
					renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, thickness, 1.0-getDownOffset(byteList[0][1][1], 1, 0), 0.5);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, thickness, 1.0-getDownOffset(byteList[0][1][1], 1, 1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			else
			{
				renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, thickness, 1.0-getDownOffset(byteList[0][1][1], 1, 0), 1.0);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
		if (byteList[1][2][2].rug && byteList[1][2][1].air)
		{
			if(byteList[1][1][2].stair && !((byteList[1][1][2].direction == 2) || (byteList[1][1][2].direction == 3)) && !byteList[1][1][2].upsidedown )
			{
				if(byteList[1][1][2].direction == 0)
				{
					renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0-thickness, 0.5, 1.0-getDownOffset(byteList[1][1][2], 2, 0), 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0-thickness, 1.0, 1.0-getDownOffset(byteList[1][1][2], 2, 1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
				else if(byteList[1][1][2].direction == 1)
				{
					renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0-thickness, 0.5, 1.0-getDownOffset(byteList[1][1][2], 2, 0), 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0-thickness, 1.0, 1.0-getDownOffset(byteList[1][1][2], 2, 1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			else
			{
				renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0-thickness, 1.0, 1.0-getDownOffset(byteList[1][1][2], 2, 0), 1.0);
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
		if (byteList[1][2][0].rug && byteList[1][2][1].air)
		{
			if(byteList[1][1][0].stair && !((byteList[1][1][0].direction == 2) || (byteList[1][1][0].direction == 3)) && !byteList[1][1][0].upsidedown )
			{
				if(byteList[1][1][0].direction == 0)
				{
					renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, 1.0-getDownOffset(byteList[1][1][0], 3, 0), thickness);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, 1.0-getDownOffset(byteList[1][1][0], 3, 1), thickness);
					renderer.renderStandardBlock(block, x, y, z);
				}
				else if(byteList[1][1][0].direction == 1)
				{
					renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, 1.0-getDownOffset(byteList[1][1][0], 3, 0), thickness);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, 1.0-getDownOffset(byteList[1][1][0], 3, 1), thickness);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			else
			{
				renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, 1.0-getDownOffset(byteList[1][1][0], 3, 0), thickness);
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
						renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, 0.0-getDownOffset(byteList[2][0][1], -1, -1), 1.0);
						renderer.renderStandardBlock(block, x, y, z);
					}
					else if(byteList[2][0][1].direction == 2 && byteList[2][0][1].corner != 2)
					{
						if(byteList[2][0][1].corner == 8)
						{
							renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, 0.0-getDownOffset(byteList[2][0][1], 0, -1), 0.5);
							renderer.renderStandardBlock(block, x, y, z);
						}
						renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 1.0, 0.0-getDownOffset(byteList[2][0][1], 0, -1), 1.0);
						renderer.renderStandardBlock(block, x, y, z);
					}
					else if(byteList[2][0][1].direction == 3 && byteList[2][0][1].corner != 1)
					{
						if(byteList[2][0][1].corner == 7)
						{
							renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 1.0, 0.0-getDownOffset(byteList[2][0][1], 0, -1), 1.0);
							renderer.renderStandardBlock(block, x, y, z);
						}
						renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, 0.0-getDownOffset(byteList[2][0][1], 0, -1), 0.5);
						renderer.renderStandardBlock(block, x, y, z);
					}
				}
				else if (!(byteList[2][0][1].slab && !byteList[2][0][1].upsidedown))
				{
					renderer.setRenderBounds(1.0-thickness, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, 0.0-getDownOffset(byteList[2][1][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			if (byteList[0][1][1].rug)
			{
				if(byteList[0][0][1].stair && !byteList[0][0][1].upsidedown )
				{
					if(byteList[0][0][1].direction == 0)
					{
						renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, thickness, 0.0-getDownOffset(byteList[0][0][1], -1, -1), 1.0);
						renderer.renderStandardBlock(block, x, y, z);
					}
					else if(byteList[0][0][1].direction == 2 && byteList[0][0][1].corner != 4)
					{
						if(byteList[0][0][1].corner == 6)
						{
							renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, thickness, 0.0-getDownOffset(byteList[0][0][1], 1, -1), 0.5);
							renderer.renderStandardBlock(block, x, y, z);
						}
						renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, thickness, 0.0-getDownOffset(byteList[0][0][1], 1, -1), 1.0);
						renderer.renderStandardBlock(block, x, y, z);
					}
					else if(byteList[0][0][1].direction == 3 && byteList[0][0][1].corner != 3)
					{
						if(byteList[0][0][1].corner == 5)
						{
							renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, thickness, 0.0-getDownOffset(byteList[0][0][1], 1, -1), 1.0);
							renderer.renderStandardBlock(block, x, y, z);
						}
						renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, thickness, 0.0-getDownOffset(byteList[0][0][1], 1, -1), 0.5);
						renderer.renderStandardBlock(block, x, y, z);
					}
				}
				else if (!(byteList[0][0][1].slab && !byteList[0][0][1].upsidedown))
				{
					renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, thickness, 0.0-getDownOffset(byteList[0][1][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			if (byteList[1][1][2].rug)
			{
				if(byteList[1][0][2].stair && !byteList[1][0][2].upsidedown )
				{
					if(byteList[1][0][2].direction == 0 && byteList[1][0][2].corner != 2)
					{
						if( byteList[1][0][2].corner == 5)
						{
							renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0-thickness, 0.5, 0.0-getDownOffset(byteList[1][0][2], 2, -1), 1.0);
							renderer.renderStandardBlock(block, x, y, z);
						}
						renderer.setRenderBounds(0.5, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0-thickness, 1.0, 0.0-getDownOffset(byteList[1][0][2], 2, -1), 1.0);
						renderer.renderStandardBlock(block, x, y, z);
					}
					else if(byteList[1][0][2].direction == 1 && byteList[1][0][2].corner != 4)
					{
						if( byteList[1][0][2].corner == 7)
						{
							renderer.setRenderBounds(0.5, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0-thickness, 1.0, 0.0-getDownOffset(byteList[1][0][2], 2, -1), 1.0);
							renderer.renderStandardBlock(block, x, y, z);
						}
						renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0-thickness, 0.5, 0.0-getDownOffset(byteList[1][0][2], 2, -1), 1.0);
						renderer.renderStandardBlock(block, x, y, z);
					}
					else if(byteList[1][0][2].direction == 3)
					{
						renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0-thickness, 1.0, 0.0-getDownOffset(byteList[1][0][2], -1, -1), 1.0);
						renderer.renderStandardBlock(block, x, y, z);
					}
				}
				else if (!(byteList[1][0][2].slab && !byteList[1][0][2].upsidedown))
				{
					renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0-thickness, 1.0, 0.0-getDownOffset(byteList[1][1][2], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			if (byteList[1][1][0].rug)
			{
				if(byteList[1][0][0].stair && !byteList[1][0][0].upsidedown )
				{
					if(byteList[1][0][0].direction == 0 && byteList[1][0][0].corner != 1)
					{
						if(byteList[1][0][0].corner == 6)
						{
							renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, 0.0-getDownOffset(byteList[1][0][0], 3, -1), thickness);
							renderer.renderStandardBlock(block, x, y, z);
						}
						renderer.setRenderBounds(0.5, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, 0.0-getDownOffset(byteList[1][0][0], 3, -1), thickness);
						renderer.renderStandardBlock(block, x, y, z);
					}
					else if(byteList[1][0][0].direction == 1 && byteList[1][0][0].corner != 3)
					{
						if(byteList[1][0][0].corner == 8)
						{
							renderer.setRenderBounds(0.5, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, 0.0-getDownOffset(byteList[1][0][0], 3, -1), thickness);
							renderer.renderStandardBlock(block, x, y, z);
						}
						renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, 0.0-getDownOffset(byteList[1][0][0], 3, -1), thickness);
						renderer.renderStandardBlock(block, x, y, z);
					}
					else if(byteList[1][0][0].direction == 2)
					{
						renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, 0.0-getDownOffset(byteList[1][0][0], -1, -1), thickness);
						renderer.renderStandardBlock(block, x, y, z);
					}
				}
				else if (!(byteList[1][0][0].slab && !byteList[1][0][0].upsidedown))
				{
					renderer.setRenderBounds(0.0, 0.0+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, 0.0-getDownOffset(byteList[1][1][0], -1, -1), thickness);
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
			if (byteList[1][0][1].corner == 0)
			{
				switch(byteList[1][0][1].direction)
				{
				default:
				{
					renderer.setRenderBounds(0.5, 0.0-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.0, -0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, offset-0.5-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
					
					renderer.setRenderBounds(0.5-thickness, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, 0.0, 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					break;
				}
				case 1:
				{
					renderer.setRenderBounds(0.0, 0.0-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
				
					renderer.setRenderBounds(0.5, -0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, offset-0.5-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5+thickness, 0.0, 1.0);
					renderer.renderStandardBlock(block, x, y, z);
					break;
				}
				case 2:
				{
					renderer.setRenderBounds(0.0, 0.0-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 1.0, offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.0, -0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, offset-0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.0, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5-thickness, 1.0, 0.0-getDownOffset(byteList[1][1][2], 2, -1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);

					break;
				}
				case 3:
				{
					renderer.setRenderBounds(0.0, 0.0-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.0, -0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 1.0, offset-0.5-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.0, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 1.0, 0.0-getDownOffset(byteList[1][1][2], 2, -1), 0.5+thickness);
					renderer.renderStandardBlock(block, x, y, z);

					break;
				}
				}
			}
			else
			{
				switch(byteList[1][0][1].corner)
				{
				case 1:
				{
					renderer.setRenderBounds(0.5, 0.0-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.0, -0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, offset-0.5-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.setRenderBounds(0.5, -0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 1.0, offset-0.5-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5-thickness, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, 0.0, 0.5);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.setRenderBounds(0.5, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 1.0, 0.0-getDownOffset(byteList[1][1][2], 2, -1), 0.5+thickness);
					renderer.renderStandardBlock(block, x, y, z);
					break;
				}
				case 2:
				{
					renderer.setRenderBounds(0.5, 0.0-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 1.0, offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.0, -0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, offset-0.5-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.setRenderBounds(0.5, -0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, offset-0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5-thickness, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 0.5, 0.0, 1.0);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.setRenderBounds(0.5, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5-thickness, 1.0, 0.0-getDownOffset(byteList[1][1][2], 2, -1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);
					break;
				}
				case 3:
				{
					renderer.setRenderBounds(0.0, 0.0-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, -0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, offset-0.5-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.setRenderBounds(0.0, -0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 0.5, offset-0.5-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5+thickness, 0.0, 0.5);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.setRenderBounds(0.0, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 0.5, 0.0-getDownOffset(byteList[1][1][2], 2, -1), 0.5+thickness);
					renderer.renderStandardBlock(block, x, y, z);

					break;
				}
				case 4:
				{
					renderer.setRenderBounds(0.0, 0.0-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 0.5, offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, -0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, offset-0.5-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.setRenderBounds(0.0, -0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, offset-0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 0.5+thickness, 0.0, 1.0);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.setRenderBounds(0.0, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5-thickness, 0.5, 0.0-getDownOffset(byteList[1][1][2], 2, -1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);

					break;
				}
				case 5:
				{
					renderer.setRenderBounds(0.0, -0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 0.5, offset-0.5-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.setRenderBounds(0.0, getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5-thickness, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 0.5, 0.0, 1.0);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.setRenderBounds(0.0, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 0.5, 0.0-getDownOffset(byteList[1][1][2], 2, -1), 0.5+thickness);
					renderer.renderStandardBlock(block, x, y, z);
					break;
				}
				case 6:
				{
					renderer.setRenderBounds(0.0, -0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, offset-0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.setRenderBounds(0.0, getDownOffset(byteList[1][0][1], -1, -1), 0.5, 0.5, offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5-thickness, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, 0.0, 0.5);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.setRenderBounds(0.0, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5-thickness, 0.5, 0.0-getDownOffset(byteList[1][1][2], 2, -1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);

					break;
				}
				case 7:
				{
					renderer.setRenderBounds(0.5, -0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 1.0, offset-0.5-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.0, getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.setRenderBounds(0.5, getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 0.5+thickness, 0.0, 1.0);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.setRenderBounds(0.5, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5, 1.0, 0.0-getDownOffset(byteList[1][1][2], 2, -1), 0.5+thickness);
					renderer.renderStandardBlock(block, x, y, z);
					break;
				}
				case 8:
				{
					renderer.setRenderBounds(0.5, -0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, offset-0.5-getDownOffset(byteList[1][0][1], -1, -1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.0, getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5, offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.setRenderBounds(0.5, getDownOffset(byteList[1][0][1], -1, -1), 0.5, 1.0, offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
					renderer.renderStandardBlock(block, x, y, z);

					renderer.setRenderBounds(0.5, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 0.5+thickness, 0.0, 0.5);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.setRenderBounds(0.5, -0.5+offset-getDownOffset(byteList[1][0][1], -1, -1), 0.5-thickness, 1.0, 0.0-getDownOffset(byteList[1][1][2], 2, -1), 0.5);
					renderer.renderStandardBlock(block, x, y, z);
					break;
				}
				}
			}
			/**
			 * 4a = 11a = 3b = 6b
			 * 5a = 9a  = 4b = 8b
			 * 6a = 10a = 1b = 5b
			 * 7a = 8a  = 2b = 7b
			 * 
			 * 1b = 5b
			 * 2b = 7b
			 * 3b = 6b
			 * 4b = 8b
			 */
			//upsidedown

			//front
			

			//back
			//left
			//right

		}
		else 
		{
			renderer.setRenderBounds(0.0, 0.0-getDownOffset(byteList[1][0][1], -1, -1), 0.0, 1.0, offset-getDownOffset(byteList[1][0][1], -1, -1), 1.0);
			renderer.partialRenderBounds = true;
			renderer.renderStandardBlock(block, x, y, z);
		}
		return true;
	}
	private boolean isBlockStairsSame(int x, int z)
	{
		return byteList[x][0][z].upsidedown == byteList[1][0][1].upsidedown && byteList[x][0][z].direction == byteList[1][0][1].direction;
	}
	/**
	 * 4a = 11a = 3b = 6b
	 * 5a = 9a  = 4b = 8b
	 * 6a = 10a = 1b = 5b
	 * 7a = 8a  = 2b = 7b
	 * 
	 * 1b = 5b
	 * 2b = 7b
	 * 3b = 6b
	 * 4b = 8b
	 */
	public int stairMainCube(RenderBlocks renderer)
	{
		int flag = 0;

		if (byteList[1][0][1].direction == 0)
		{
			
			if (byteList[2][0][1].stair && !byteList[2][0][1].upsidedown)
			{				
				if (byteList[2][0][1].direction == 3 && !isBlockStairsSame(1,2))
				{
					flag = 1;
				}
				else if (byteList[2][0][1].direction == 2 && !isBlockStairsSame(1,0))
				{
					flag = 2;
				}
			}
		}
		else if (byteList[1][0][1].direction == 1)
		{
			if (byteList[0][0][1].stair && !byteList[0][0][1].upsidedown)
			{
				if (byteList[0][0][1].direction == 3 && !isBlockStairsSame(1,2))
				{
					flag = 3;
				}
				else if (byteList[0][0][1].direction == 2 && !isBlockStairsSame(1,0))
				{
					flag = 4;
				}
			}
		}
		else if (byteList[1][0][1].direction == 2)
		{
			if (byteList[1][0][2].stair && !byteList[1][0][2].upsidedown)
			{
				if (byteList[1][0][2].direction == 1 && !isBlockStairsSame(2,1))
				{
					flag = 4;
				}
				else if (byteList[1][0][2].direction == 0 && !isBlockStairsSame(0,1))
				{
					flag = 2;
				}
			}
		}
		else if (byteList[1][0][1].direction == 3)
		{
			if (byteList[1][0][0].stair && !byteList[1][0][0].upsidedown)
			{
				if (byteList[1][0][0].direction == 1 && !isBlockStairsSame(2,1))
				{
					flag = 3;
				}
				else if (byteList[1][0][0].direction == 0 && !isBlockStairsSame(0,1))
				{
					flag = 1;
				}
			}
		}
		
		//renderer.setRenderBounds(backEdge, bottomEdge, leftEdge, frontEdge, topEdge, rightEdge);
		return flag;
	}

	public int stairSecondCube(RenderBlocks renderer)
	{
		int flag = 0;

		if (byteList[1][0][1].direction == 0)
		{
			if (byteList[0][0][1].stair && !byteList[0][0][1].upsidedown)
			{
				if (byteList[0][0][1].direction == 3 && !isBlockStairsSame(1,0))
				{
					flag = 5;
				}
				else if (byteList[0][0][1].direction == 2 && !isBlockStairsSame(1,2))
				{
					flag = 6;
				}
			}
		}
		else if (byteList[1][0][1].direction == 1)
		{
			if (byteList[2][0][1].stair && !byteList[2][0][1].upsidedown)
			{
				if (byteList[2][0][1].direction == 3 && !isBlockStairsSame(1,0))
				{
					flag = 7;
				}
				else if (byteList[2][0][1].direction == 2 && !isBlockStairsSame(1,2))
				{
					flag = 8;
				}
			}
		}
		else if (byteList[1][0][1].direction == 2)
		{
			if (byteList[1][0][0].stair && !byteList[1][0][0].upsidedown)
			{
				if (byteList[1][0][0].direction == 1 && !isBlockStairsSame(0,1))
				{
					flag = 8;
				}
				else if (byteList[1][0][0].direction == 0 && !isBlockStairsSame(2,1))
				{
					flag = 6;
				}
			}
		}
		else if (byteList[1][0][1].direction == 3)
		{
			if (byteList[1][0][2].stair && !byteList[1][0][2].upsidedown)
			{
				if (byteList[1][0][2].direction == 1 && !isBlockStairsSame(0,1))
				{
					flag = 7;
				}
				else if (byteList[1][0][2].direction == 0 && !isBlockStairsSame(2,1))
				{
					flag = 5;
				}
			}
		}

		return flag;
	}
	public int stairMainCube(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        int i1 = l & 3;
        int flag = 0;
        int ID;
        int meta;
        int dir;

        if (i1 == 0)
        {
            ID = par1IBlockAccess.getBlockId(par2 + 1, par3, par4);
            meta = par1IBlockAccess.getBlockMetadata(par2 + 1, par3, par4);

            if (BlockStairs.isBlockStairsID(ID) && (l & 4) == (meta & 4))
            {
                dir = meta & 3;

                if (dir == 3 && !this.isBlockStairs(par1IBlockAccess, par2, par3, par4 + 1, l))
                {
                    flag = 1;
                }
                else if (dir == 2 && !this.isBlockStairs(par1IBlockAccess, par2, par3, par4 - 1, l))
                {
                    flag = 2;
                }
            }
        }
        else if (i1 == 1)
        {
            ID = par1IBlockAccess.getBlockId(par2 - 1, par3, par4);
            meta = par1IBlockAccess.getBlockMetadata(par2 - 1, par3, par4);

            if (BlockStairs.isBlockStairsID(ID) && (l & 4) == (meta & 4))
            {
                dir = meta & 3;

                if (dir == 3 && !this.isBlockStairs(par1IBlockAccess, par2, par3, par4 + 1, l))
                {
                    flag = 3;
                }
                else if (dir == 2 && !this.isBlockStairs(par1IBlockAccess, par2, par3, par4 - 1, l))
                {
                    flag = 4;
                }
            }
        }
        else if (i1 == 2)
        {
            ID = par1IBlockAccess.getBlockId(par2, par3, par4 + 1);
            meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4 + 1);

            if (BlockStairs.isBlockStairsID(ID) && (l & 4) == (meta & 4))
            {
                dir = meta & 3;

                if (dir == 1 && !this.isBlockStairs(par1IBlockAccess, par2 + 1, par3, par4, l))
                {
                    flag = 4;
                }
                else if (dir == 0 && !this.isBlockStairs(par1IBlockAccess, par2 - 1, par3, par4, l))
                {
                    flag = 2;
                }
            }
        }
        else if (i1 == 3)
        {
            ID = par1IBlockAccess.getBlockId(par2, par3, par4 - 1);
            meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4 - 1);

            if (BlockStairs.isBlockStairsID(ID) && (l & 4) == (meta & 4))
            {
                dir = meta & 3;

                if (dir == 1 && !this.isBlockStairs(par1IBlockAccess, par2 + 1, par3, par4, l))
                {
                    flag = 3;
                }
                else if (dir == 0 && !this.isBlockStairs(par1IBlockAccess, par2 - 1, par3, par4, l))
                {
                    flag = 1;
                }
            }
        }

        return flag;
    }

    public int stairSecondCube(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        int i1 = l & 3;
        int flag = 0;
        int j1;
        int k1;
        int l1;

        if (i1 == 0)
        {
            j1 = par1IBlockAccess.getBlockId(par2 - 1, par3, par4);
            k1 = par1IBlockAccess.getBlockMetadata(par2 - 1, par3, par4);

            if (BlockStairs.isBlockStairsID(j1) && (l & 4) == (k1 & 4))
            {
                l1 = k1 & 3;

                if (l1 == 3 && !this.isBlockStairs(par1IBlockAccess, par2, par3, par4 - 1, l))
                {
                    flag = 5;
                }
                else if (l1 == 2 && !this.isBlockStairs(par1IBlockAccess, par2, par3, par4 + 1, l))
                {
                    flag = 6;
                }
            }
        }
        else if (i1 == 1)
        {
            j1 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4);
            k1 = par1IBlockAccess.getBlockMetadata(par2 + 1, par3, par4);

            if (BlockStairs.isBlockStairsID(j1) && (l & 4) == (k1 & 4))
            {
                l1 = k1 & 3;

                if (l1 == 3 && !this.isBlockStairs(par1IBlockAccess, par2, par3, par4 - 1, l))
                {
                    flag = 7;
                }
                else if (l1 == 2 && !this.isBlockStairs(par1IBlockAccess, par2, par3, par4 + 1, l))
                {
                    flag = 8;
                }
            }
        }
        else if (i1 == 2)
        {
            j1 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1);
            k1 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 - 1);

            if (BlockStairs.isBlockStairsID(j1) && (l & 4) == (k1 & 4))
            {
                l1 = k1 & 3;

                if (l1 == 1 && !this.isBlockStairs(par1IBlockAccess, par2 - 1, par3, par4, l))
                {
                    flag = 8;
                }
                else if (l1 == 0 && !this.isBlockStairs(par1IBlockAccess, par2 + 1, par3, par4, l))
                {
                    flag = 6;
                }
            }
        }
        else if (i1 == 3)
        {
            j1 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1);
            k1 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 + 1);

            if (BlockStairs.isBlockStairsID(j1) && (l & 4) == (k1 & 4))
            {
                l1 = k1 & 3;

                if (l1 == 1 && !this.isBlockStairs(par1IBlockAccess, par2 - 1, par3, par4, l))
                {
                    flag = 7;
                }
                else if (l1 == 0 && !this.isBlockStairs(par1IBlockAccess, par2 + 1, par3, par4, l))
                {
                    flag = 5;
                }
            }
        }

        return flag;
    }
    private boolean isBlockStairs(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        int i1 = par1IBlockAccess.getBlockId(par2, par3, par4);
        return BlockStairs.isBlockStairsID(i1) && par1IBlockAccess.getBlockMetadata(par2, par3, par4) == par5;
    }
	public double getDownOffset(RugStruct checkBlock, int dir, int side)
	{
		//if(side != -1) BedCraftBeyond.logger.info(side+"s "+dir+"d1 "+checkBlock.direction+"d2 "+checkBlock.corner+"c ");
		float[][][][] blah = {
				{
					{
						{0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.5f, 0.0f, 0.5f, 0.0f, 0.5f, 0.0f, 0.5f, 0.0f, 0.0f},
						{0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}
					},                                                       
					{                                                        
						{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.5f, 0.0f, 0.0f, 0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.5f, 0.0f, 0.5f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.5f},
						{0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}
					},                                                       
					{                                                        
						{0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f},
						{0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.5f, 0.0f, 0.5f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}
					},                                                       
					{                                                        
						{0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f},
						{0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.5f, 0.5f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}
					}                                                        
				},                                                           
				{                                                            
					{                                                        
						{0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.5f, 0.5f, 0.0f, 0.5f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f}
					},                                                       
					{                                                        
						{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.5f, 0.5f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f}
					},                                                       
					{                                                        
						{0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.5f, 0.0f, 0.0f, 0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.5f},
						{0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}
					},                                                       
					{                                                        
						{0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.5f, 0.0f, 0.0f, 0.5f, 0.5f, 0.0f, 0.0f, 0.5f, 0.0f},
						{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
						{0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}
					}
				}
		};
		
		if(checkBlock.slab && !checkBlock.upsidedown) return 0.5;
		if(checkBlock.stair && !checkBlock.upsidedown) 
		{
			if(side != -1)
				return blah[side][dir][checkBlock.direction][checkBlock.corner];
		}
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
