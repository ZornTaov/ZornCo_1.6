package zornco.bedcraftbeyond.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import zornco.bedcraftbeyond.BedCraftBeyond;
import zornco.bedcraftbeyond.item.ItemScissors;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockColoredChestBed extends BlockColoredBed {

	private Random random;

	public BlockColoredChestBed(int par1) {
		super(par1);
		random = new Random();
        this.isBlockContainer = true;
	}

	@Override
	public boolean onBlockActivated(World world, int par2, int par3, int par4, EntityPlayer player, int par6, float par7, float par8, float par9) 
	{
		if (player.getHeldItem() == null || (player.getHeldItem() != null && player.getHeldItem().getItem() != null && !(player.getHeldItem().getItem() instanceof ItemScissors)))
		{
			super.onBlockActivated( world, par2, par3, par4, player, par6, par7, par8, par9);
			return true;
		}
		TileColoredChestBed tile = (TileColoredChestBed)world.getBlockTileEntity(par2, par3, par4);

        if (tile == null)
        {
            return true;
        }

        if (world.isRemote)
        {
            return true;
        }

		BedCraftBeyond.logger.info(tile.colorCombo+"");
        player.openGui(BedCraftBeyond.instance, 0, world, par2, par3, par4);
		return true;
	}
	@Override
	public void onBlockAdded(World world, int i, int j, int k)
	{
		super.onBlockAdded(world, i, j, k);
		world.markBlockForUpdate(i, j, k);
	}
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileColoredChestBed();
	}
	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return isBlockHeadOfBed(par1) ? 0 : BedCraftBeyond.chestBedItem.itemID;
	}
	@SideOnly(Side.CLIENT)

	/**
	 * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
	 */
	public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return BedCraftBeyond.chestBedItem.itemID;
	}
	@Override
    public void breakBlock(World world, int i, int j, int k, int i1, int i2)
    {
		TileColoredChestBed tileentitychest = (TileColoredChestBed) world.getBlockTileEntity(i, j, k);
        if (tileentitychest != null)
        {
            dropContent(0, tileentitychest, world, tileentitychest.xCoord, tileentitychest.yCoord, tileentitychest.zCoord);
        }
        super.breakBlock(world, i, j, k, i1, i2);
    }

    public void dropContent(int newSize, IInventory chest, World world, int xCoord, int yCoord, int zCoord)
    {
        for (int l = newSize; l < chest.getSizeInventory(); l++)
        {
            ItemStack itemstack = chest.getStackInSlot(l);
            if (itemstack == null)
            {
                continue;
            }
            float f = random.nextFloat() * 0.8F + 0.1F;
            float f1 = random.nextFloat() * 0.8F + 0.1F;
            float f2 = random.nextFloat() * 0.8F + 0.1F;
            while (itemstack.stackSize > 0)
            {
                int i1 = random.nextInt(21) + 10;
                if (i1 > itemstack.stackSize)
                {
                    i1 = itemstack.stackSize;
                }
                itemstack.stackSize -= i1;
                EntityItem entityitem = new EntityItem(world, (float) xCoord + f, (float) yCoord + (newSize > 0 ? 1 : 0) + f1, (float) zCoord + f2,
                        new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
                float f3 = 0.05F;
                entityitem.motionX = (float) random.nextGaussian() * f3;
                entityitem.motionY = (float) random.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float) random.nextGaussian() * f3;
                if (itemstack.hasTagCompound())
                {
                    entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                }
                world.spawnEntityInWorld(entityitem);
            }
        }
    }

    /**
     * Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
     * entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
     */
    public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
        return tileentity != null ? tileentity.receiveClientEvent(par5, par6) : false;
    }
}
