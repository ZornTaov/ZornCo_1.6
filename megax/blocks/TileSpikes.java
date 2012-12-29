package zornco.megax.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileSpikes extends TileEntity {

	public int sidesPlaced = 0;
	public TileSpikes() {
	}
	public boolean canUpdate()
	{
		return false;
	}
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		this.sidesPlaced = nbttagcompound.getShort("Amount");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("Amount", (short)this.sidesPlaced);
	}
}
