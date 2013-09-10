package zornco.bedcraftbeyond.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileColoredBed extends TileEntity {
	public int colorCombo;
	private boolean firstRun = true;
	public TileColoredBed() {
		super();
	}
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("colorCombo", colorCombo);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		this.colorCombo = nbttagcompound.getInteger("colorCombo");
	}
	public void setColorCombo(int combo)
	{
		this.colorCombo = combo;
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote && (worldObj.getWorldTime() % 20 == 0 || firstRun )) {
			firstRun = false;
			updateClients();
		}
	}
	@Override
	public final Packet132TileEntityData getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new Packet132TileEntityData(this.xCoord, this.yCoord,
				this.zCoord, 1, nbt);
	}

	@Override
	public final void onDataPacket(INetworkManager net,
			Packet132TileEntityData packet) {
		NBTTagCompound nbt = packet.data;
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
