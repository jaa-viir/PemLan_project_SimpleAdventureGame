package com.jaaviir.rpgtextgame;

import com.jaaviir.rpgtextgame.InterfaceGrouped.Combatant;

public class Entity implements Combatant{
    protected int health;
    protected int attackDamage;

    public Entity(int health, int attackDamage) {
        this.health = health;
        this.attackDamage = attackDamage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
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
