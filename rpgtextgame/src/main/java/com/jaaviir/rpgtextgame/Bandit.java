package com.jaaviir.rpgtextgame;

import com.jaaviir.rpgtextgame.InterfaceGrouped.Combatant;

public class Bandit extends Entity implements Combatant {
    public Bandit(int health, int attackDamage) {
        super(health, attackDamage);
    }

    @Override
    public void attack(Entity target) {
        target.takeDamage(this.attackDamage);
    }

    @Override
    public void takeDamage(int damage) {
        this.health -= damage;
    }
}
