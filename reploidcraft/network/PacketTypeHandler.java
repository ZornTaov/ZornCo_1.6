package zornco.reploidcraft.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.lang.ref.Reference;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import zornco.reploidcraft.network.packet.PacketKeyPressed;
import zornco.reploidcraft.network.packet.PacketReploidCraft;

/**
 * PacketTypeHandler
 * 
 * Handler that routes packets to the appropriate destinations depending on what
 * kind of packet they are
 * 
 * @author pahimar
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public enum PacketTypeHandler {
    KEY(PacketKeyPressed.class);

    private Class<? extends PacketReploidCraft> clazz;

    PacketTypeHandler(Class<? extends PacketReploidCraft> clazz) {

        this.clazz = clazz;
    }

    public static PacketReploidCraft buildPacket(byte[] data) {

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        int selector = bis.read();
        DataInputStream dis = new DataInputStream(bis);

        PacketReploidCraft packet = null;

        try {
            packet = values()[selector].clazz.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }

        packet.readPopulate(dis);

        return packet;
    }

    public static PacketReploidCraft buildPacket(PacketTypeHandler type) {

        PacketReploidCraft packet = null;

        try {
            packet = values()[type.ordinal()].clazz.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return packet;
    }

    public static Packet populatePacket(PacketReploidCraft packetReploidCraft) {

        byte[] data = packetReploidCraft.populate();

        Packet250CustomPayload packet250 = new Packet250CustomPayload();
        packet250.channel = "ReploidCraft";
        packet250.data = data;
        packet250.length = data.length;
        packet250.isChunkDataPacket = packetReploidCraft.isChunkDataPacket;

        return packet250;
    }
}
