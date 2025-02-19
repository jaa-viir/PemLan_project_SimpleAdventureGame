package com.jaaviir.rpgtextgame;

public class Map {
    public static final int[][] map = {
            { 0, 0, 1, 0, 0 },
            { 3, 2, 10, 7, 8 },
            { 0, 4, 5, 0, 9 },
            { 0, 0, 6, 0, 0 }
    };

    // Constants for locations
    public static final int CASTLE_GATE = 1;
    public static final int FOREST = 2;
    public static final int BANDIT_HIDEOUT = 3;
    public static final int HEALER = 4;
    public static final int TOWN = 5;
    public static final int SHOP = 6;
    public static final int CAVE_ENTRANCE = 7;
    public static final int CAVE = 8;
    public static final int DEEP_CAVE = 9;
    public static final int CROSSROAD = 10;

    // Method to get the description of each location
    public static String getLocationDescription(int x, int y) {
        switch (map[y][x]) {
            case CASTLE_GATE:
                return "Castle Gate";
            case FOREST:
                return "Forest";
            case BANDIT_HIDEOUT:
                return "Bandit Hideout";
            case HEALER:
                return "Healer's Den";
            case TOWN:
                return "Town";
            case SHOP:
                return "Shop";
            case CAVE_ENTRANCE:
                return "Cave Entrance";
            case CAVE:
                return "Cave";
            case DEEP_CAVE:
                return "Deep Cave";
            case CROSSROAD:
                return "Cross Road";
            default:
                return "Unknown Location";
        }
    }
}
