package org.minigamzreborn.bytelyplay.hub.events;

import net.minestom.server.entity.Entity;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import org.minigamzreborn.bytelyplay.hub.NPCs.NPC;

public class NPCInteractionEvents {
    public static void playerEntityInteractEvent(PlayerEntityInteractEvent e) {
        Entity clickedEntity = e.getTarget();

        if (clickedEntity instanceof NPC npc) {
            npc.playerInteract(e);
        }
    }
    public static void entityAttackEvent(EntityAttackEvent e) {
        Entity victim = e.getTarget();

        if (victim instanceof NPC npc) {
            npc.entityAttack(e);
        }
    }
}
