package com.jaaviir.rpgtextgame;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Game {

    // Deklarasi java swing
    JFrame window;
    JPanel titleNamePanel, startButtonPanel, mainTextPanel, choiceButtonPanel, playerPanel;
    JLabel titleNameLabel, hpLabel, hpLabelNumber, weaponLabel, weaponLabelName, goldLabel, goldLabelNumber;
    JButton startButton, choice1, choice2, choice3, choice4;
    JTextArea mainTextArea;
    ImageIcon logo = new ImageIcon(".//res//jackfrost.jpg");
    // Deklarasi awt
    Container con;
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 90);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 28);
    // Java biasa
    String playerName;
    public Boolean firstWakeUp = true;
    public Boolean firstForest = true;
    public Boolean firstBandit = true;
    public Boolean banditJumps = false;
    public Boolean firstTown = true;
    public Boolean firstShop = true;
    public Boolean firstHealersDen = true;
    public Boolean firstDragon = true;
    public Boolean defeatDragon= false;
    
    protected static int numActions = 0;

    // Deklarasi kelas sebelah
    Player player;
    LocationManager locationManager;
    SpecialEvents specialEvents;

    TitleScreenHandler tsHandler = new TitleScreenHandler();
    // khusus map
    MinimapPanel minimapPanel;
    int[][] map = Map.map;

    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        window = new JFrame();
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);
        window.setIconImage(logo.getImage());
        con = window.getContentPane();

        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(100, 100, 600, 150);
        titleNamePanel.setBackground(Color.black);
        titleNameLabel = new JLabel("ADVENTURE");
        titleNameLabel.setForeground(Color.white);
        titleNameLabel.setFont(titleFont);

        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(300, 400, 200, 100);
        startButtonPanel.setBackground(Color.black);

        startButton = new JButton("START");
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.white);
        startButton.setFont(normalFont);
        startButton.addActionListener(tsHandler);
        startButton.setFocusPainted(false);

        titleNamePanel.add(titleNameLabel);
        startButtonPanel.add(startButton);

        con.add(titleNamePanel);
        con.add(startButtonPanel);

        minimapPanel = new MinimapPanel(map);

        window.setVisible(true);
    }

    public void createGameScreen() {
        titleNamePanel.setVisible(false);
        startButtonPanel.setVisible(false);

        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 100, 600, 200);
        mainTextPanel.setBackground(Color.black);

        con.add(mainTextPanel);
        mainTextArea = new JTextArea("bismillah 100 (text ini harusnya gk terliat)");
        mainTextArea.setBounds(100, 100, 600, 200);
        mainTextArea.setBackground(Color.black);
        mainTextArea.setForeground(Color.white);
        mainTextArea.setFont(normalFont);
        mainTextArea.setLineWrap(true);
        mainTextArea.setWrapStyleWord(true);
        mainTextArea.setEditable(false);
        mainTextPanel.add(mainTextArea);

        mainTextArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.white, 2)));

        // tombol opsi
        choiceButtonPanel = new JPanel();
        choiceButtonPanel.setBounds(250, 350, 300, 150);
        choiceButtonPanel.setBackground(Color.black);
        choiceButtonPanel.setLayout(new GridLayout(4, 1));
        con.add(choiceButtonPanel);
        // c1
        choice1 = new JButton("Choice 1");
        choice1.setBackground(Color.black);
        choice1.setForeground(Color.white);
        choice1.setFont(normalFont);
        choice1.setFocusPainted(false);
        choice1.setActionCommand("c1");
        // c2
        choice2 = new JButton("Choice 2");
        choice2.setBackground(Color.black);
        choice2.setForeground(Color.white);
        choice2.setFont(normalFont);
        choice2.setFocusPainted(false);
        choice2.setActionCommand("c2");
        // c3
        choice3 = new JButton("Choice 3");
        choice3.setBackground(Color.black);
        choice3.setForeground(Color.white);
        choice3.setFont(normalFont);
        choice3.setFocusPainted(false);
        choice3.setActionCommand("c3");
        // c4
        choice4 = new JButton("Choice 4");
        choice4.setBackground(Color.black);
        choice4.setForeground(Color.white);
        choice4.setFont(normalFont);
        choice4.setFocusPainted(false);
        choice4.setActionCommand("c4");

        playerPanel = new JPanel();
        playerPanel.setBounds(100, 15, 600, 50);
        playerPanel.setBackground(Color.black);
        playerPanel.setLayout(new GridLayout(1, 4));
        con.add(playerPanel);
        // label hp ++
        hpLabel = new JLabel("HP:");
        hpLabel.setFont(normalFont);
        hpLabel.setForeground(Color.white);
        playerPanel.add(hpLabel);
        hpLabelNumber = new JLabel();
        hpLabelNumber.setFont(normalFont);
        hpLabelNumber.setForeground(Color.white);
        playerPanel.add(hpLabelNumber);
        // label weapon >>
        weaponLabel = new JLabel("Weapon:");
        weaponLabel.setFont(normalFont);
        weaponLabel.setForeground(Color.white);
        playerPanel.add(weaponLabel);
        weaponLabelName = new JLabel();
        weaponLabelName.setFont(normalFont);
        weaponLabelName.setForeground(Color.white);
        playerPanel.add(weaponLabelName);
        // label gold $$
        goldLabel = new JLabel("Gold:");
        goldLabel.setFont(normalFont);
        goldLabel.setForeground(Color.white);
        playerPanel.add(goldLabel);
        goldLabelNumber = new JLabel();
        goldLabelNumber.setFont(normalFont);
        goldLabelNumber.setForeground(Color.white);
        playerPanel.add(goldLabelNumber);

        // inisiasi minimap
        minimapPanel = new MinimapPanel(map);
        minimapPanel.setBounds(100, 355, 126, 101);
        con.add(minimapPanel);
        playerSetup();

        System.out.println(firstWakeUp);
        System.out.println("game ke-run");
        specialEvents.wakeUp();
    }

    public void updatePlayerPosition(int x, int y) {
        minimapPanel.setPlayerPosition(x, y);
    }

    // ini setup
    public void playerSetup() {
        player = new Player(15, 5, 0, "Knife", this);
        specialEvents = new SpecialEvents(this);
        locationManager = new LocationManager(this, player, specialEvents);
        updateUI();
    }

    public void updateUI() {
        weaponLabelName.setText(player.getWeapon());
        hpLabelNumber.setText("" + player.getHealth() + "/" + player.getMaxHealth());
        goldLabelNumber.setText("" + player.getGold());
    }

    public void setChoices(String... choices) {
        JButton[] buttons = { choice1, choice2, choice3, choice4 };
        for (int i = 0; i < buttons.length; i++) {
            // clearActionListeners(buttons[i]); Clear existing listeners
            if (i < choices.length && choices[i] != null) {
                buttons[i].setText(choices[i]);
                buttons[i].setVisible(true);
            } else {
                buttons[i].setText("");
                buttons[i].setVisible(false);
            }
        }
    }

    // action listeners

    public void clearActionListeners() {
        JButton[] buttons = { choice1, choice2, choice3, choice4 };
        for (JButton button : buttons) {
            for (ActionListener actionListeners : button.getActionListeners()) {
                button.removeActionListener(actionListeners);
            }
        }
        choice1.addActionListener(e -> actionTracker());
        choice2.addActionListener(e -> actionTracker());
        choice3.addActionListener(e -> actionTracker());
        choice4.addActionListener(e -> actionTracker());
    }

    public void actionTracker() {
        numActions++;
        System.out.println("Number of actions taken: " + numActions);
    }

    public class TitleScreenHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            createGameScreen();
        }
    }
}
