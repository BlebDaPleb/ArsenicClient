package blebdapleb.arsenic.arsenic.event.events;

import net.minecraft.network.packet.Packet;
import blebdapleb.arsenic.arsenic.event.Event;

/**
 * @author sl
 */
public class EventPacket extends Event {

	private Packet<?> packet;

	public EventPacket(Packet<?> packet) {
		this.packet = packet;
	}

	public Packet<?> getPacket() {
		return packet;
	}

	public void setPacket(Packet<?> packet) {
		this.packet = packet;
	}
	
	public static class Read extends EventPacket {

		public Read(Packet<?> packet) {
			super(packet);
		}
		
	}
	
	public static class Send extends EventPacket {

		public Send(Packet<?> packet) {
			super(packet);
		}
		
	}
}
