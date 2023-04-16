package blebdapleb.arsenic.arsenic.event.events;

import blebdapleb.arsenic.arsenic.event.Event;
import net.minecraft.entity.Entity;

public class EventEntity extends Event {
    private final Entity entity;

    public EventEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public static class Spawn extends EventEntity {
        public Spawn(Entity entity) {
            super(entity);
        }
    }

    public static class Remove extends EventEntity {
        public Remove(Entity entity) {
            super(entity);
        }
    }
}
