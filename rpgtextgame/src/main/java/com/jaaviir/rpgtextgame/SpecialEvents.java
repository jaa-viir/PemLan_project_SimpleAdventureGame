package com.jaaviir.rpgtextgame;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JTextField;

public class SpecialEvents {
    private final Game game;

    JTextField nameInputField;
    JButton proceedButton;

    public SpecialEvents(Game game) {
        this.game = game;
    }

    public void wakeUp() {
        game.mainTextArea.setText(
                "You wake up lightheaded. You see someone's foot infront of you\n\nGuard : Hey!! what is your name?");

        // ini utk kyk deklarasi
        nameInputField = new JTextField(10);
        proceedButton = new JButton(">");
        proceedButton.setBackground(Color.black);
        proceedButton.setForeground(Color.white);
        proceedButton.setFont(game.normalFont);
        proceedButton.setFocusPainted(false);

        // ini utk masukkin ke choicebuttonpanel
        game.choiceButtonPanel.removeAll();
        game.choiceButtonPanel.add(nameInputField);
        game.choiceButtonPanel.add(proceedButton);
        game.choiceButtonPanel.revalidate();
        game.choiceButtonPanel.repaint();

        proceedButton.addActionListener(e -> confirmWakeUp());
    }

    public void confirmWakeUp() {
        String playerName = nameInputField.getText();
        if (playerName.length() > 10 || playerName.length() == 0) {
            game.mainTextArea.setText("!!Notice!!\nName must be 10 characters or less.\nPlease enter your name again.");
            return;
        }
        game.mainTextArea.setText("Guard : Huh, are you sure \"" + playerName + "\" is your name?");
        game.choiceButtonPanel.removeAll(); // Clear the nameInputField and proceedButton
        game.choiceButtonPanel.add(game.choice1);
        game.choiceButtonPanel.add(game.choice2);
        game.choiceButtonPanel.add(game.choice3);
        game.choiceButtonPanel.add(game.choice4);
        game.choiceButtonPanel.revalidate();

        game.setChoices("Yes", "No", null, null); // Re-add the usual buttons
        game.choiceButtonPanel.repaint();

        game.clearActionListeners();
        game.choice1.addActionListener(e -> wakeUp2(playerName));
        game.choice2.addActionListener(e -> wakeUp());
    }

    public void wakeUp2(String playerName) {
        game.playerName = playerName; // Store the player's name in the Game class

        game.mainTextArea.setText("Guard : Sure whatever you say " + playerName
                + ". Don't bother me till this dragon thing is solved. SCRAM!!");
        game.setChoices(">", null, null, null);

        game.clearActionListeners();
        game.choice1.addActionListener(e -> game.locationManager.castleGate());
    }

    public void banditAttack() {
        game.clearActionListeners();
        game.choice1.addActionListener(e -> banditAttack());
        game.choice2.addActionListener(e -> game.locationManager.banditHideout());
        if (game.firstBandit) {
            game.firstBandit = false;
            game.mainTextArea.setText(
                    "Curious, you find yourself deeper in their stead. Their eyes are not wary, you were spotted immediately\n");
            game.setChoices("Chase more bandits", "Leave the area", null, null);
        } else if (game.banditJumps) {
            game.banditJumps = false;
            game.mainTextArea.setText(
                    "The blood in your hands has yet to stop you to find some more prey. How petty.\n");
            game.setChoices("\"Too late to stop now\"", "\"We're done for now\"", null, null);
        } else {
            game.mainTextArea.setText(
                    "They forgot about your existence for a while. You, in the other hand, remembers everything.\n\nWhere would you like to go?");
            game.setChoices("Chase more bandits", "Leave", null, null);
        }
        game.banditJumps = true;
        Bandit bandit = new Bandit(10, 3);
        game.locationManager.combat(bandit);
    }

    public void guardTalk() {
        game.mainTextArea.setText(
                "The guard's voice is a chilling whisper. \n\"You again... Why do you linger? The dragon in the cave will be your end. Turn back before it's too late.\"");
        game.setChoices(">", null, null, null);

        game.clearActionListeners();
        game.choice1.addActionListener(e -> game.locationManager.castleGate());
    }

    public void guardAttack() {
        game.mainTextArea.setText(
                "The guard's expression darkens. \"You dare attack? Foolish!\"\n\nThe guard retaliates with eerie strength. (You receive 3 damage)");

        game.clearActionListeners();
        game.setChoices(">", null, null, null);
        game.choice1.addActionListener(e -> game.locationManager.castleGate());
        game.player.takeDamage(3);
        game.updateUI();
    }

    public void guardEnd() {
        game.mainTextArea.setText(
                "Guard: You have done it " + game.playerName + ". The dragon is no more. The kingdom owes you a debt it can never repay.\n\n" +
                        "He steps aside, allowing you to pass through the gate into the unknown, with a haunting smile on his face.\n\n");

        game.setChoices(">", null, null, null);
        game.clearActionListeners();
        game.choice1.addActionListener(e -> winSequnce());
    }

    public void heal() {
        Player player = game.player;
        if (player.getGold() >= 2) {
            player.setGold(player.getGold() - 2);
            player.setHealth(player.getHealth() + 5);
            game.updateUI();
            game.mainTextArea.setText(
                    "The healer chants ancient words and applies a pungent salve. You feel vitality returning.\n\nYou are healed for 5 health.");
        } else {
            game.mainTextArea.setText(
                    "The healer looks at you with disdain. \"The spirits demand payment in gold, which you lack,\" they say coldly.");
        }
        game.setChoices(">", null, null, null);
        game.clearActionListeners();
        game.choice1.addActionListener(e -> game.locationManager.healer());
    }

    public void healerTalk() {
        game.mainTextArea.setText(
                "The healer's eyes glint with a strange light. \"Many have sought refuge here, but few dare the cave. They whisper of a dragon... a creature of nightmares. Will you face it?\"");
        game.setChoices(">", null, null, null);

        game.clearActionListeners();
        game.choice1.addActionListener(e -> game.locationManager.healer());
    }

    public void buyWeapon() {
        Player player = game.player;

        if (player.getWeapon().equals("Sword")) {
            game.mainTextArea.setText(
                    "The shopkeeper's dead face mock's you. The upgrade means nothing as you already had the sword");
        }
        if (player.getGold() >= 5) {
            player.setGold(player.getGold() - 10);
            player.setWeapon("Sword");
            player.setAttackDamage(25);
            game.updateUI();
            game.mainTextArea.setText(
                    "The shopkeeper hands you a finely crafted weapon. \"May it spill the blood of your foes,\" he whispers.\n\nYour attack increases by 2.");
        } else {
            game.mainTextArea.setText(
                    "The shopkeeper's smile is a thin line. \"Your pockets are too light for such a purchase,\" he sneers.");
        }
        game.setChoices(">", null, null, null);
        game.clearActionListeners();
        game.choice1.addActionListener(e -> game.locationManager.shop());
    }

    public void buyArmor() {
        Player player = game.player;

        if (player.getMaxHealth() > 20) {
            game.mainTextArea.setText(
                    "The shopkeeper's eyes glint with amusement. \"Greedy,\" he chuckles.");
        } else if (player.getGold() >= 10) {
            player.setGold(player.getGold() - 5);
            player.setMaxHealth(30);
            player.setHealth(player.getHealth() + 5);
            game.updateUI();
            game.mainTextArea.setText(
                    "The shopkeeper presents you with sturdy armor. \"May it shield you from the horrors to come,\" he says solemnly.\n\nYour defense increases by 2.");
        } else {
            game.mainTextArea.setText(
                    "The shopkeeper's eyes glint with amusement. \"Return when your coin matches your ambition,\" he chuckles.");
        }
        game.setChoices(">", null, null, null);
        game.clearActionListeners();
        game.choice1.addActionListener(e -> game.locationManager.shop());
    }

    public void shopkeeperTalk() {
        game.mainTextArea.setText(
                "The shopkeeper leans in close, his voice a harsh whisper. \"Brave adventurer, beware the cave. The dragon within is unlike any foe you have faced. It is a beast born of nightmares. Prepare well, or meet your doom.\"");
        game.setChoices(">", null, null, null);

        game.clearActionListeners();
        game.choice1.addActionListener(e -> game.locationManager.shop());
    }

    public void deathSequence() {
        game.mainTextArea.setText(
                "You have succumbed to your injuries.\n\nActions taken: " + game.numActions
                        + "\n\nWould you like to play a new game or exit?");
        game.setChoices("New Game", "Exit", null, null);
        game.setChoices("New Game", "Exit", null, null);

        game.clearActionListeners();
        game.choice1.addActionListener(e -> {
            game.window.dispose();
            Game.main(null);
        });
        game.choice2.addActionListener(e -> {
            System.exit(0);
            System.exit(0);
        });
    }

    private void winSequnce() {
        game.mainTextArea.setText(
                "You have completed the game, " + game.playerName + "!\n\n" +
                        "Actions taken: " + game.numActions + "\n\n" +
                        "Would you like to play a new game or exit?");
        game.setChoices("New Game", "Exit", null, null);

        game.clearActionListeners();
        game.choice1.addActionListener(e -> {
            game.window.dispose();
            Game.main(null);
        });
        game.choice2.addActionListener(e -> {
            System.exit(0);
        });
    }

    public void dragonFight() {
        if (game.firstDragon) {
            game.firstDragon = false;
            game.mainTextArea.setText("As you venture deeper, a huge dragon stands before you! Prepare for battle!");
        } else {
            game.mainTextArea.setText("The dragon roars, ready to attack you again!");
        }
        Entity dragon = new Entity(50, 10); // Dragon entity
        game.locationManager.combatDragon(dragon);

        game.setChoices("Continue the fight", "Cower", null, null);
        game.clearActionListeners();
        game.choice1.addActionListener(e -> continueDragonFight(dragon));
        game.choice2.addActionListener(e -> game.locationManager.deepCave());
    }

    public void continueDragonFight(Entity dragon) {
        game.mainTextArea.setText("The air reeks sweat, brawl has yet to end.");
        game.locationManager.combatDragon(dragon);

        game.setChoices("Finish the job", "Cower", null, null);
        game.clearActionListeners();
        game.choice1.addActionListener(e -> continueDragonFight(dragon));
        game.choice2.addActionListener(e -> game.locationManager.deepCave());
    }
}
