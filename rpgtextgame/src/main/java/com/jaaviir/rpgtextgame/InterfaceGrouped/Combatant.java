package com.jaaviir.rpgtextgame.InterfaceGrouped;

import com.jaaviir.rpgtextgame.Entity;

public interface Combatant {
    void attack(Entity target);
    void takeDamage(int damage);
}
