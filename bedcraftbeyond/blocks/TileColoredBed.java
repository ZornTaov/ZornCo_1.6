package zornco.bedcraftbeyond.blocks;

import zornco.bedcraftbeyond.BedCraftBeyond;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileColoredBed extends TileEntity {
	public int colorCombo;
	@Override
	public void writeToNBT(NBTTagCompound par1)
	{
	   super.writeToNBT(par1);
	   par1.setInteger("colorCombo", colorCombo);
	}

	@Override
	public void readFromNBT(NBTTagCompound par1)
	{
	   super.readFromNBT(par1);
	   this.colorCombo = par1.getInteger("colorCombo");
	}
	public void setColorCombo(int combo)
	{
		this.colorCombo = combo;
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote == false && worldObj.getWorldTime() % 20 == 0) {
			updateClients();
		}
	}
	public final Packet132TileEntityData getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new Packet132TileEntityData(this.xCoord, this.yCoord,
				this.zCoord, 1, nbt);
	}

	public final void onDataPacket(INetworkManager net,
			Packet132TileEntityData packet) {
		NBTTagCompound nbt = packet.customParam1;
		if (nbt != null) {
			this.readFromNBT(nbt);
		}
	}

	public final void updateClients() {
		if (worldObj.isRemote)
			return;
		Packet132TileEntityData packet = this.getDescriptionPacket();
		PacketDispatcher.sendPacketToAllInDimension(packet,
				this.worldObj.provider.dimensionId);
	}



}
