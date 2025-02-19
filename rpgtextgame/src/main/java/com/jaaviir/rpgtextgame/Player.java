package com.jaaviir.rpgtextgame;

public class Player extends Entity {
    private int gold;
    private String weapon;
    private int maxHealth;
    Game game;

    public Player(int health, int attackDamage, int gold, String weapon, Game game) {
        super(health, attackDamage);
        this.gold = gold;
        this.weapon = weapon;
        this.maxHealth = health;
        this.game = game;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void deadOrAlive(){
        if(health <=0){
            health = 0;
            game.specialEvents.deathSequence(); 
        }
    }

    @Override
    public void attack(Entity target) {
        target.takeDamage(this.attackDamage);
    }

    @Override
    public void takeDamage(int damage) {
        this.health -= damage;
        deadOrAlive();
    }

    @Override
    public int getHealth(){
        deadOrAlive();
        return health;
    }
}