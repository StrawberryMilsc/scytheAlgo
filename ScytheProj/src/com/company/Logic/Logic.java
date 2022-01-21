package com.company.Logic;

import com.company.Logic.State;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class Logic {
    public static int[] upgradesCost;
    public static final File rootSave = new File("C:\\Users\\aniru\\Desktop\\ScytheProj\\src\\com\\company\\Logic\\rootSave.txt");
    public static int gCounter;
    public State root;
    public State curr;
    public static String[] resourceString = {"Nothing","Food", "Wood", "Metal", "Oil"};
    public static String[] possibleWins = {"Upgrade","Enlist","Deploy","Build","Popularity","Power"};
    public static int[] costs;
    public static String[] bottomRow = {"upgrade","enlist","deploy","build"};
    public static String[] topRows = {"move","gain","trade","tradePop","produce","bolster","combatCard"};
    public static int[] board = {2,0,4,1,3,4,
            -1,3,-1,3,2,0,0,
            1,2,1,-1,1,4,
            0,4,-1,-1,2,3,2,
            1,1,0,3,-1,4,-1,
            2,4,4,3,1,2,3,
            3,-1,0,2,4,0};
    public static State gHighest;
    public Logic() {
        costs = new int[4];
        Scanner scan = new Scanner(System.in);
        for(int i = 0; i < 4; i++) {
            System.out.println("Input cost of " + bottomRow[i]);
            costs[i] = scan.nextInt();
        }
        int[] Batle = {2};
        int[] territories= {3,4};
        root = new State(new Variables(2,4,5,Batle,territories) , new Turn[1],-1);
    }
    public int[] findRoute(int x) {
        root.findHighest();

        return root.findRoute();
    }
    public Turn[] findRoute() {
        root.findHighest();
        return gHighest.getRouteTo();
    }
    public void grow() {
        Turn[] turns = generateTurns(root);

        root.grow(turns,turns.length,0);
    }

    public void setRoot(State newState) {
        //State save = new State(new Variables(new Turn(" "," "),Logic.gHighest.getCurrVar()),new Turn[1]);
        save(newState);
        //System.out.println(save.getCurrVar().getPlayerMat() != null);
        root.delete();
        //System.out.println(save.getCurrVar().getPlayerMat() != null);
        root = load();
        gHighest = root;
        Scanner scan = new Scanner(System.in);
        scan.nextInt();
        //System.out.println(root.getCurrVar().getPlayerMat() != null);
        //System.out.println(root.getCurrVar() != null);

    }

    private void save(State toSave) {
        try {
            costs = toSave.getCurrVar().getPlayerMat().costs;
            System.out.println("Recruits: " + Arrays.toString(toSave.getCurrVar().getPlayerMat().getRecruits()));
            System.out.println("Buildings: " + Arrays.toString(toSave.getCurrVar().getPlayerMat().getBuildings()));
            FileWriter fileWrite = new FileWriter(rootSave.toString());
            Writer write = new BufferedWriter(fileWrite);
            write.write(rootToString(toSave));
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String rootToString(State toSave) {
        return toSave.toString();
    }

    private State load() {
        State state;
        try {
            FileReader fileReader = new FileReader(rootSave.toString());
            BufferedReader read = new BufferedReader(fileReader);
            String s = "";
            String line;
            Variables currVar = new Variables();
            PlayerMat playerMat = new PlayerMat(upgradesCost,costs);
            int index = 0;
            int score = 0;
            while ((line = read.readLine()) != null) {
                switch(index) {
                    case 2: score = (Integer.parseInt(line));
                    break;
                    case 4: currVar.setNumStars(Integer.parseInt(line));
                    break;
                    case 5: currVar.setPopularity(Integer.parseInt(line));
                    break;
                    case 6: currVar.setPower(Integer.parseInt(line));
                    break;
                    case 7: currVar.setMoney(Integer.parseInt(line));
                    break;
                    case 8: currVar.setBattleCards(stringToArray(line));
                    break;
                    case 9: currVar.setNumTerritory(Integer.parseInt(line));
                    break;
                    case 10: currVar.setTerritories(stringToArray(line));
                    break;
                    case 11: currVar.setWorkerTerritories(stringToArray(line));
                    break;
                    case 12: currVar.setNumWood(Integer.parseInt(line));
                    break;
                    case 13: currVar.setNumFood(Integer.parseInt(line));
                    break;
                    case 14: currVar.setNumMetal(Integer.parseInt(line));
                    break;
                    case 15: currVar.setNumOil(Integer.parseInt(line));
                    break;
                    case 17: playerMat.setRecruits(stringToBoolean(line));
                    break;
                    case 18: playerMat.setBuildings(stringToBoolean(line));
                    break;
                    case 19: playerMat.setUpgradesTop(stringToBoolean(line));
                    break;
                    case 20: playerMat.setUpgradesBottom(stringToBoolean(line));
                    break;
                    case 21: playerMat.setAlignment(stringToArray(line));
                    break;
                }

                s = s.concat("\n" + line);
                index++;
            }
            currVar.setPlayerMat(playerMat);
            state = new State(currVar,new Turn[0],-1);
            state.setScore(score);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return state;
    }
    private boolean[] stringToBoolean(String line) {
        line = line.replace("[","");
        line = line.replace("]","");
        String[] bools = line.split(", ");
        boolean[] returnBools = new boolean[bools.length];
        for(int i = 0; i < bools.length; i++) {

            returnBools[i] = Boolean.parseBoolean(bools[i]);
        }
        return returnBools;
    }
    private int[] stringToArray(String line) {
        line = line.replace("[","");
        line = line.replace("]","");
        String[] strings = line.split(", ");
        /*
        line = line.replace(", ","");
        line = line.replace(" ", "");
        */
        int[] array = new int[strings.length];
        for(int i =0; i < array.length; i++) {
            if(!strings[i].equals("")) {
                array[i] = Integer.parseInt(strings[i]);
            }
        }


        return array;


    }

    public static Turn[] generateTurns(State curr) {

        PlayerMat temp = curr.getCurrVar().getPlayerMat();
        Turn[] reTurn = new Turn[7];
        reTurn[0] = new Turn(topRows[0], bottomRow[temp.getAlignment()[0]]);
        reTurn[1] = new Turn(topRows[1], bottomRow[temp.getAlignment()[1]]);
        reTurn[2] = new Turn(topRows[2], bottomRow[temp.getAlignment()[2]]);
        reTurn[3] = new Turn(topRows[3], bottomRow[temp.getAlignment()[3]]);
        reTurn[4] = new Turn(topRows[4], bottomRow[temp.getAlignment()[4]]);
        reTurn[5] = new Turn(topRows[5], bottomRow[temp.getAlignment()[5]]);
        reTurn[6] = new Turn(topRows[6], bottomRow[temp.getAlignment()[6]]);
        return reTurn;
    }

    public static int getTerritoryType(int territory) {
        return board[territory];
    }

    public static int[] getTerritoryType(int[] territories) {
        //System.out.println(territories != null);
        int[] resources = new int[territories.length];
        for(int i = 0; i < territories.length; i++) {
            resources[i] = -1;
        }
        for(int i = 0; i < territories.length; i++) {
            resources[i] = board[territories[i]];
        }


        return resources;
    }

    @Override
    public String toString() {
        String childString = "";
        for (int i = 0; i < 4;i++) {
            if (root.getChildren().get(i) != null) {
                childString = childString.concat(root.getChildren().get(i).toString());
            }
        }
        return  "root =  " + root.toString() + "\n" + "----------------------" + childString;
    }
}
