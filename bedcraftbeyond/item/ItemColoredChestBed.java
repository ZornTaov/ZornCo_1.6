package zornco.bedcraftbeyond.item;

import zornco.bedcraftbeyond.BedCraftBeyond;
import zornco.bedcraftbeyond.blocks.BlockColoredBed;
import zornco.bedcraftbeyond.blocks.BlockColoredChestBed;
import zornco.bedcraftbeyond.blocks.TileColoredBed;
import zornco.bedcraftbeyond.blocks.TileColoredChestBed;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemColoredChestBed extends ItemColoredBed {

	public ItemColoredChestBed(int par1) {
		super(par1);
	}
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.bedIcon = new Icon[3];
		for (int i = 0; i < 3; i++) {
			this.bedIcon[i] = par1IconRegister.registerIcon("bedcraftbeyond:bed_chest_"+i);
		}
	}
	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
	 */
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if (par3World.isRemote)
		{
			return true;
		}
		else if (par7 != 1)
		{
			return false;
		}
		else
		{
			++par5;
			BlockColoredChestBed blockbed = (BlockColoredChestBed)BedCraftBeyond.chestBedBlock;
			int i1 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			byte b0 = 0;
			byte b1 = 0;

			if (i1 == 0)
			{
				b1 = 1;
			}

			if (i1 == 1)
			{
				b0 = -1;
			}

			if (i1 == 2)
			{
				b1 = -1;
			}

			if (i1 == 3)
			{
				b0 = 1;
			}

			if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4 + b0, par5, par6 + b1, par7, par1ItemStack))
			{
				if (par3World.isAirBlock(par4, par5, par6) && par3World.isAirBlock(par4 + b0, par5, par6 + b1) && par3World.doesBlockHaveSolidTopSurface(par4, par5 - 1, par6) && par3World.doesBlockHaveSolidTopSurface(par4 + b0, par5 - 1, par6 + b1))
				{
					par3World.setBlock(par4, par5, par6, blockbed.blockID, i1, 3);

					if (par3World.getBlockId(par4, par5, par6) == blockbed.blockID)
					{
						par3World.setBlock(par4 + b0, par5, par6 + b1, blockbed.blockID, i1 + 8, 3);
					}

		        	TileColoredChestBed tile = (TileColoredChestBed)par3World.getBlockTileEntity(par4, par5, par6);
		        	if (tile != null)
		        	{
		        		tile.setColorCombo(par1ItemStack.getItemDamage());
		        		tile.setOwnerName(par2EntityPlayer.getEntityName());
		        	    //BedCraftBeyond.logger.info(tile.colorCombo+"");
		        	}
		        	TileColoredChestBed tile2 = (TileColoredChestBed)par3World.getBlockTileEntity(par4 + b0, par5, par6 + b1);
		        	if (tile2 != null)
		        	{
		        		tile2.setColorCombo(par1ItemStack.getItemDamage());
		        		tile2.setOwnerName(par2EntityPlayer.getEntityName());
		        	    //BedCraftBeyond.logger.info(tile2.colorCombo+"");
		        	}
					--par1ItemStack.stackSize;
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
	}
}
