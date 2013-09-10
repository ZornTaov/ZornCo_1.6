package zornco.bedcraftbeyond.item;

import zornco.bedcraftbeyond.BedCraftBeyond;
import zornco.bedcraftbeyond.blocks.TileColoredBed;
import zornco.bedcraftbeyond.blocks.TileColoredChestBed;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemScissors extends Item {

	public ItemScissors(int par1) {
		super(par1);
	}

	/**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		BedCraftBeyond.logger.info(""+par4+" "+par5+" "+par6);
		BedCraftBeyond.logger.info(""+(par3World.getClass().toString()));
		BedCraftBeyond.logger.info(""+par3World.getBlockId(par4, par5, par6));
		BedCraftBeyond.logger.info(""+par3World.getBlockMetadata(par4, par5, par6));
		TileEntity tile = par3World.getBlockTileEntity(par4, par5, par6);
		if (tile != null)
		{
			if(tile instanceof TileColoredBed)
			{
				TileColoredBed tilebed = (TileColoredBed)tile;
				BedCraftBeyond.logger.info(tilebed.colorCombo+"");
			}
			if(tile instanceof TileColoredChestBed)
			{
				TileColoredChestBed tilebed = (TileColoredChestBed)tile;
				BedCraftBeyond.logger.info(tilebed.ownerName+"");
			}
		}
		else
		{
			return false;
		}
		return true;

	}

	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = par1IconRegister.registerIcon("bedcraftbeyond:scissors");
	}
}
