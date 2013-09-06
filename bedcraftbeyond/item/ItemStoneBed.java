package zornco.bedcraftbeyond.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import zornco.bedcraftbeyond.BedCraftBeyond;
import zornco.bedcraftbeyond.blocks.BlockStoneBed;
import zornco.bedcraftbeyond.blocks.TileStoneBed;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemStoneBed extends Item {

	private Icon[] stoneBedIcon;
	private int bedKinds = 1;
	public ItemStoneBed(int par1) {
		super(par1);
		this.setHasSubtypes(true);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.stoneBedIcon = new Icon[bedKinds];
		for (int i = 0; i < bedKinds; i++) {
			this.stoneBedIcon[i] = par1IconRegister.registerIcon("bedcraftbeyond:bed_stone_"+i);
		}
	}
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		if (par1 >= 0 && par1 < bedKinds)
			return stoneBedIcon[par1];
		return stoneBedIcon[0];
	}
	@SideOnly(Side.CLIENT)
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int var4 = 0; var4 < bedKinds; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
	 */
	@Override
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
			BlockStoneBed blockbed = (BlockStoneBed)BedCraftBeyond.stoneBedBlock;
			int i1 = MathHelper.floor_double(par2EntityPlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
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

					TileStoneBed tile = (TileStoneBed)par3World.getBlockTileEntity(par4, par5, par6);
		        	if (tile != null)
		        	{
		        		tile.setColorCombo(par1ItemStack.getItemDamage());
		        	    //BedCraftBeyond.logger.info(tile.colorCombo+"");
		        	}
		        	TileStoneBed tile2 = (TileStoneBed)par3World.getBlockTileEntity(par4 + b0, par5, par6 + b1);
		        	if (tile2 != null)
		        	{
		        		tile2.setColorCombo(par1ItemStack.getItemDamage());
		        	    //BedCraftBeyond.logger.info(tile2.colorCombo+"");
		        	}
		        	if(!par2EntityPlayer.capabilities.isCreativeMode)
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
