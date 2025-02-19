package com.jaaviir.rpgtextgame;

public class LocationManager {
    private final Game game;
    private final Player player;
    private int playerX;
    private int playerY;
    public SpecialEvents specialEvents;

    public LocationManager(Game game, Player player, SpecialEvents specialEvents) {
        this.game = game;
        this.player = player;
        this.playerX = 2; // Starting position
        this.playerY = 0; // Starting position
        this.specialEvents = specialEvents;
    }

    public void move(String direction) {
        boolean moved = false;
        switch (direction) {
            case "north":
                if (playerY > 0 && Map.map[playerY - 1][playerX] != 0) {
                    playerY--;
                    moved = true;
                }
                break;
            case "south":
                if (playerY < Map.map.length - 1 && Map.map[playerY + 1][playerX] != 0) {
                    playerY++;
                    moved = true;
                }
                break;
            case "east":
                if (playerX < Map.map[0].length - 1 && Map.map[playerY][playerX + 1] != 0) {
                    playerX++;
                    moved = true;
                }
                break;
            case "west":
                if (playerX > 0 && Map.map[playerY][playerX - 1] != 0) {
                    playerX--;
                    moved = true;
                }
                break;
        }
        if (moved) {
            game.updatePlayerPosition(playerX, playerY);
            enterLocation();
        }
    }

    public void enterLocation() {
        setDefaultButton();
        int location = Map.map[playerY][playerX]; // karena array kebalik
        System.out.println("x = " + playerX + ",y = " + playerY + ", location = " + location + " ("
                + Map.getLocationDescription(playerX, playerY) + ")");
        switch (location) {
            case Map.CASTLE_GATE:
                castleGate();
                break;
            case Map.CROSSROAD:
                crossRoad();
                break;
            case Map.FOREST:
                forest();
                break;
            case Map.BANDIT_HIDEOUT:
                banditHideout();
                break;
            case Map.HEALER:
                healer();
                break;
            case Map.TOWN:
                town();
                break;
            case Map.SHOP:
                shop();
                break;
            case Map.CAVE_ENTRANCE:
                caveEntrance();
                break;
            case Map.CAVE:
                cave();
                break;
            case Map.DEEP_CAVE:
                deepCave();
                break;
        }
        game.updateUI();
    }

    // sbg default movement, ini bisa kepake klo map lebih besar
    public void setDefaultButton() {
        game.setChoices("Move North (↑)", "Move South (↓)", "Move East (→)", "Move West (←)");
        game.clearActionListeners();
        game.choice1.addActionListener(e -> move("north"));
        game.choice2.addActionListener(e -> move("south"));
        game.choice3.addActionListener(e -> move("east"));
        game.choice4.addActionListener(e -> move("west"));
    }

    public void castleGate() {
        if (game.defeatDragon) {
            specialEvents.guardEnd();
        } else {
            if (game.firstWakeUp) {
                game.firstWakeUp = false;
                game.mainTextArea
                        .setText(
                                "It seems like you are in front of a castle's gate.\nThe guard stands there, his eyes hollow and lifeless.\n\nWhat do you do?");
            } else {
                game.mainTextArea
                        .setText(
                                "You are back at the castle gate. The guard's eerie stare follows you.\n\nWhat do you do?");
            }
            game.setChoices("Talk to the guard", "Attack the guard", "Move South (↓)", null);

            game.clearActionListeners();
            game.choice1.addActionListener(e -> specialEvents.guardTalk());
            game.choice2.addActionListener(e -> specialEvents.guardAttack());
            game.choice3.addActionListener(e -> move("south"));
        }
    }

    public void crossRoad() {
        game.mainTextArea.setText(
                "You've walked into a crossroads\nYou see 4 paths before you.\n\nWhere would you like to go?");
        game.setChoices("The castle's gate (↑)", "The town center (↓)", "The forest (→)", "The cave (←)");
    }

    public void town() {
        if (game.firstTown) {
            game.firstTown = false;
            game.mainTextArea.setText(
                    "You've managed to find the town center\n There seems to be barely any people around\n\nWhere would you like to go?");
        } else {
            game.mainTextArea.setText(
                    "You're back to the town center\n The empty ambiance still makes you uneasy.\n\nWhere would you like to go?");
        }
        game.setChoices("To the crossroads (↑)", "The shop (↓)", "The healer's den (←)", null);
        game.clearActionListeners();
        game.choice1.addActionListener(e -> move("north"));
        game.choice2.addActionListener(e -> move("south"));
        game.choice3.addActionListener(e -> move("west"));
    }

    public void forest() {
        game.clearActionListeners();
        game.choice1.addActionListener(e -> move("west"));
        game.choice2.addActionListener(e -> move("east"));
        if (game.firstForest) {
            game.firstForest = false;
            game.mainTextArea.setText(
                    "You've found youeself in a dark forest. You tried to get used to the surroundings, but suddenly, a bandit jumped at you\n");
            Bandit bandit = new Bandit(10, 3);
            combat(bandit);
            game.setChoices("Go deeper (←)", "Go back out (→)", null, null);
        } else if (game.banditJumps) {
            game.banditJumps = false;
            game.mainTextArea.setText(
                    "Greed is an insidious killer, your blood debt makes your head a target for another bandit. Fight is initiated.\n");
            Bandit bandit = new Bandit(10, 3);
            combat(bandit);
            game.setChoices("Back to the hideout (←)", "Go back out (→)", null, null);
        } else {
            game.mainTextArea.setText(
                    "You thought this forest place would get less eerie the more you go to it. Guess again.\n\nWhere would you like to go?");
            game.setChoices("Go to the hideout (←)", "Go back out (→)", null, null);
        }
    }

    public void banditHideout() {
        game.clearActionListeners();
        game.choice1.addActionListener(e -> specialEvents.banditAttack());
        game.choice2.addActionListener(e -> move("east"));
        if (game.firstBandit) {
            game.mainTextArea.setText(
                    "The air reeks of blood and sweat. Under a cover of tree, you see a group of Bandits. \"This is their nest huh?\" you say to yourself\n\nWhat's your call?");
            game.setChoices("Attack them", "Back out (→)", null, null);
        } else if (game.banditJumps) {
            game.mainTextArea.setText(
                    "Their demise was simply foretold in their incompetence. Not meeting them would've been a mercy.\n\nWhere to next?");
            game.setChoices("\"Let's find some more\"", "Go back out (→)", null, null);
        } else {
            game.mainTextArea.setText(
                    "The bandit's hideout don't seem to be any less empty. These petty fools have nothing on you.\n\nWhere would you like to go?");
            game.setChoices("Get back to fighting", "Go back out (→)", null, null);
        }
    }

    public void healer() {
        if (game.firstHealersDen) {
            game.firstHealersDen = false;
            game.mainTextArea.setText(
                    "A heavy scent of herbs and incense fills the air. The healer's den offers solace for a price.\n\n\"You look weary, traveler. I can mend your wounds... for a price.\"");
        } else {
            game.mainTextArea.setText(
                    "The healer's den is as dim and foreboding as ever.\n\n\"Back again? The spirits whisper of your plight. What will it be this time?\"");
        }
        game.setChoices("Heal (2 gold)", "Talk", "Leave (→)", null);

        game.clearActionListeners();
        game.choice1.addActionListener(e -> specialEvents.heal());
        game.choice2.addActionListener(e -> specialEvents.healerTalk());
        game.choice3.addActionListener(e -> game.locationManager.move("east"));
    }

    public void shop() {
        if (game.firstShop) {
            game.firstShop = false;
            game.mainTextArea.setText(
                    "Dusty wares line the shelves, each item whispering promises of power and protection.\n\"Welcome, stranger. Care to purchase something to aid you in your journey?\"");
        } else {
            game.mainTextArea.setText(
                    "The shopkeeper eyes you with a cold, calculating gaze. His wares await your inspection.\n\n\"Back for more, are we? Choose wisely, for your journey grows ever darker.\"");
        }
        game.setChoices("Buy Weapon (10 gold)", "Buy Armor (10 gold)", "Talk", "Leave");

        game.clearActionListeners();
        game.choice1.addActionListener(e -> specialEvents.buyWeapon());
        game.choice2.addActionListener(e -> specialEvents.buyArmor());
        game.choice3.addActionListener(e -> specialEvents.shopkeeperTalk());
        game.choice4.addActionListener(e -> game.locationManager.move("north"));
    }

    private void caveEntrance() {
        game.mainTextArea.setText("You stand at the entrance of a dark cave. Do you dare enter?");
        game.setChoices("Enter Cave (→)", "Leave (←)", null, null);

        game.clearActionListeners();
        game.choice1.addActionListener(e -> cave());
        game.choice2.addActionListener(e -> game.locationManager.move("west"));
    }

    private void cave() {
        if (game.firstDragon) {
            game.mainTextArea.setText("The cave is cold and dark. You feel a presence nearby.");
        } else {
            game.mainTextArea.setText("The cave remains as foreboding as ever, shadows dancing on the walls.");
        }
        game.setChoices("Go Deeper (↓)", "Leave (←)");

        game.clearActionListeners();
        game.choice1.addActionListener(e -> deepCave());
        game.choice2.addActionListener(e -> caveEntrance());
    }

    protected void deepCave() {
        if (game.defeatDragon) {
            game.mainTextArea
                    .setText("The remnants of the fierce battle with the dragon lie around you. The path is clear.");
            game.setChoices("Leave (←)", null, null, null);

            game.clearActionListeners();
            game.choice1.addActionListener(e -> cave());
        } else {
            game.specialEvents.dragonFight();
        }
    }

    protected void combat(Entity enemy) {
        while (player.getHealth() > 0 && enemy.getHealth() > 0) {
            player.attack(enemy);
            if (enemy.getHealth() > 0) {
                // Enemy attacks
                enemy.attack(player);
            }
        }
        if (player.getHealth() > 0) {
            if (enemy instanceof Bandit) {
                game.mainTextArea.append(">> You managed to beat the bandit");
                int goldReward = (int) (Math.random() * 4) + 1;
                player.setGold(player.getGold() + goldReward);
                game.mainTextArea.append(", and found " + goldReward + " gold on them.");
            } else {
                game.mainTextArea.append(">> You've found yourself a victor. The people will cheer for your guts.");
            }
        } else {
            game.mainTextArea.append("\nYou have been defeated.");
        }
        game.updateUI();
    }

    protected void combatDragon(Entity dragon) {
        int playerDamage = (int) (Math.random() * (player.getAttackDamage() / 2)) + (player.getAttackDamage() / 2);
        int dragonDamage = (int) (Math.random() * (dragon.getAttackDamage() / 2)) + (dragon.getAttackDamage() / 2);
        dragon.setHealth(dragon.getHealth() - playerDamage);
        game.mainTextArea.append(">> You hit the dragon for " + playerDamage + " damage.\n");

        if (dragon.getHealth() > 0) {
            player.setHealth(player.getHealth() - dragonDamage);
            game.mainTextArea.append(">> The dragon hits you for " + dragonDamage + " damage.\n");
        }
        if (dragon.getHealth() <= 0) {
            game.mainTextArea.append("\n>> You managed to defeat the dragon!");
            game.defeatDragon = true;
            deepCave();
        } else if (player.getHealth() <= 0) {
            game.mainTextArea.append("\nYou have been defeated by the dragon.\n");
        }

        game.updateUI();
    }
}