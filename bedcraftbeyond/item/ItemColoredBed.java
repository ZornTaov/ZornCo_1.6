package zornco.bedcraftbeyond.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import zornco.bedcraftbeyond.BedCraftBeyond;
import zornco.bedcraftbeyond.blocks.BlockColoredBed;
import zornco.bedcraftbeyond.blocks.BlockRug;
import zornco.bedcraftbeyond.blocks.TileColoredBed;

public class ItemColoredBed extends Item
{

	public static final int[] woodColors = new int[] {0xaf8f58, 0x745733, 0xd0c084, 0xac7c58};
	public static final String[] woodType = new String[] {"Oak", "Spruce", "Birch", "Jungle"};
	public static final String[] colorNames = new String[] {"Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White"};
	
	@SideOnly(Side.CLIENT)
	protected Icon[] bedIcon;
	
	public ItemColoredBed(int par1)
	{
		super(par1);
		this.setHasSubtypes(true);
	}

	@Override
	public int getRenderPasses(int metadata)
	{
		return requiresMultipleRenderPasses() ? 3 : 1;
	}

	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		switch (par2)
		{
		case 0:
			return ItemDye.dyeColors[this.getColorFromInt(par1ItemStack.getItemDamage(), 2)];
		case 1:
			return ItemDye.dyeColors[this.getColorFromInt(par1ItemStack.getItemDamage(), 1)];
		case 2:
			return this.woodColors[this.getColorFromInt(par1ItemStack.getItemDamage(), 0)];
		}
		return 0xFF00FF;
	}

	public static int getColorFromInt(int meta, int color)
	{
		switch (color)
		{
		case 0:
			return meta >> 8 & 0xF;
		case 1:
			return meta >> 4 & 0xF;
		case 2:
			return meta & 0xF;
		}
		return 0;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Gets an icon index based on an item's damage value and the given render pass
	 */
	public Icon getIconFromDamageForRenderPass(int par1, int par2)
	{
		return this.bedIcon[par2];
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.bedIcon = new Icon[3];
		for (int i = 0; i < 3; i++) {
			this.bedIcon[i] = par1IconRegister.registerIcon("bedcraftbeyond:bed_"+i);
		}
	}
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int var4 = 0; var4 < 1024; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}

	@SideOnly(Side.CLIENT)
	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add(this.colorNames[getColorFromInt(par1ItemStack.getItemDamage(), 2)]+" Blanket");
		par3List.add(this.colorNames[getColorFromInt(par1ItemStack.getItemDamage(), 1)]+" Sheet");
		par3List.add(this.woodType[getColorFromInt(par1ItemStack.getItemDamage(), 0)]+" Frame");

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
			BlockColoredBed blockbed = (BlockColoredBed)BedCraftBeyond.bedBlock;
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

		        	TileColoredBed tile = (TileColoredBed)par3World.getBlockTileEntity(par4, par5, par6);
		        	if (tile != null)
		        	{
		        		tile.setColorCombo(par1ItemStack.getItemDamage());
		        	    //BedCraftBeyond.logger.info(tile.colorCombo+"");
		        	}
		        	TileColoredBed tile2 = (TileColoredBed)par3World.getBlockTileEntity(par4 + b0, par5, par6 + b1);
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
