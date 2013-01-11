package zornco.megax.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.lang.ref.Reference;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import zornco.megax.network.packet.PacketMegaX;
import zornco.megax.network.packet.PacketKeyPressed;

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

    private Class<? extends PacketMegaX> clazz;

    PacketTypeHandler(Class<? extends PacketMegaX> clazz) {

        this.clazz = clazz;
    }

    public static PacketMegaX buildPacket(byte[] data) {

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        int selector = bis.read();
        DataInputStream dis = new DataInputStream(bis);

        PacketMegaX packet = null;

        try {
            packet = values()[selector].clazz.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }

        packet.readPopulate(dis);

        return packet;
    }

    public static PacketMegaX buildPacket(PacketTypeHandler type) {

        PacketMegaX packet = null;

        try {
            packet = values()[type.ordinal()].clazz.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return packet;
    }

    public static Packet populatePacket(PacketMegaX packetMegaX) {

        byte[] data = packetMegaX.populate();

        Packet250CustomPayload packet250 = new Packet250CustomPayload();
        packet250.channel = "MegaX";
        packet250.data = data;
        packet250.length = data.length;
        packet250.isChunkDataPacket = packetMegaX.isChunkDataPacket;

        return packet250;
    }
}
