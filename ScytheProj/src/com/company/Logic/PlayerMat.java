package com.company.Logic;

import com.company.Main;

import java.util.Arrays;
import java.util.Scanner;

public class PlayerMat {
    private boolean[] recruits; //0==upgrade, 1==enlist, 2==deploy,3==build
    private boolean[] buildings; // 0 == mine, 1 == armory, 2 == mill, 3 == monument
    private boolean[] upgradesTop; //0==move,1==gain,2==trade,3==produce,4==bolster,5==combat card
    private boolean[] upgradesBottom;
    private int[] upgradesCost;
    public int[] costs;
    private int[] alignment; // index is top row and number is bottom row (0==move,1==gain,2==trade, 3==tradePop,4 == produce,5==bolster,6==combatCard)
                             // (0==upgrade,1==enlist,2==deploy,3==build)
    private int numMechs;
    PlayerMat(int[] x, int[] costs) {
        upgradesCost = x;
        this.costs = costs;
    }

    public PlayerMat(boolean[] recruits, boolean[] buildings, boolean[] upgradesTop, boolean[] upgradesBottom, int[] upgradesCost, int[] alignment, int numMechs
    ,int[] costs) {
        this.recruits = new boolean[recruits.length];
        System.arraycopy(recruits,0,this.recruits,0,recruits.length);
        this.buildings = new boolean[buildings.length];
        System.arraycopy(buildings,0,this.buildings,0,buildings.length);
        this.upgradesTop = new boolean[upgradesTop.length];
        System.arraycopy(upgradesTop,0,this.upgradesTop,0,upgradesTop.length);
        this.upgradesBottom = new boolean[upgradesBottom.length];
        System.arraycopy(upgradesBottom,0,this.upgradesBottom,0,upgradesBottom.length);
        this.upgradesCost = new int[upgradesCost.length];
        System.arraycopy(upgradesCost,0,this.upgradesCost,0,upgradesCost.length);
        this.alignment = new int[alignment.length];
        System.arraycopy(alignment,0,this.alignment,0,alignment.length);
        this.costs = new int[costs.length];
        System.arraycopy(costs,0,this.costs,0,costs.length);
        this.numMechs = numMechs;
    }

    public int getNumBuildings() {
        int num =0;
        for(boolean b : buildings) {
            if(b) {
                num++;
            }
        }

        return num;
    }

    public int getNumRecruits() {
        int num =0;
        for(boolean b : recruits) {
            if(b) {
                num++;
            }
        }

        return num;
    }

    public int getNumUpgrades() {
        int num =0;
        for(boolean b : upgradesTop) {
            if(b) {
                num++;
            }
        }

        return num;
    }

    public boolean[] getRecruits() {
        return recruits;
    }

    public boolean[] getBuildings() {
        return buildings;
    }

    public boolean[] getUpgradesTop() {
        return upgradesTop;
    }

    public boolean[] getUpgradesBottom() {
        return upgradesBottom;
    }

    public int[] getUpgradesCost() {
        return upgradesCost;
    }

    PlayerMat() {
        upgradesCost = new int[6];
        Scanner scan = new Scanner(System.in);
        for(int i = 0; i < 6; i++) {
            System.out.println("Select which are upgradeable: (0==upgrade,1==enlist,2==deploy,3==build: ");
            upgradesCost[i] = scan.nextInt();
        }
        Logic.upgradesCost = upgradesCost;
        costs = Main.copyArray(Logic.costs);
        numMechs = 0;
        recruits = new boolean[4];
        buildings = new boolean[4];
        upgradesTop = new boolean[6];
        upgradesBottom = new boolean[6];
       // System.out.println(recruits[0]);
        alignment = new int[7];
        Scanner playerMatScan = new Scanner(System.in);
        System.out.println("Input alignment: index is top row and number is bottom row (0==move,1==gain,2==trade, 3==tradePop,4 == produce,5==bolster,6==combatCard)\n" +
                "                             (0==upgrade,1==enlist,2==deploy,3==build)");
        alignment[0] = playerMatScan.nextInt();
        alignment[1] = playerMatScan.nextInt();
        alignment[2] = playerMatScan.nextInt();
        alignment[3] = playerMatScan.nextInt();
        alignment[4] = playerMatScan.nextInt();
        alignment[5] = playerMatScan.nextInt();
        alignment[6] = playerMatScan.nextInt();


    }

    public boolean allUpgraded() {
        for (boolean b : upgradesTop) {
            if(!b) {
                return false;
            }
        }
        return true;
    }
    public boolean allEnlist() {
        for(boolean b : recruits) {
            if(!b) {
                return false;
            }
        }
        return true;
    }

    public void upgrade(int top, int bottom) { // Add exception for already upgraded ting
        upgradesBottom[bottom] = true; // need to be able to customise
        upgradesTop[top] = true;
        costs[upgradesCost[bottom]]--;
    }

    public int getNumMechs() {
        return numMechs;
    }

    public boolean allBuilt() {
        for(boolean b : buildings) {
            if(!b) {
                return false;
            }
        }
        return true;
    }
    public void deploy() {
        numMechs++;
    }

    public boolean build(int x) {// Add exceptions in case of already built
        if(!buildings[x]) {
            buildings[x] = true;
            return true;
        }

        return false;
    }

    public int[] getAlignment() {
        return alignment;
    }

    public void enlist(int x) {
        recruits[x] = true;
    }

    public boolean getRecruits(int x) {
        return recruits[x];
    }

    public boolean getBuildings(int x) {
        return buildings[x];
    }

    public boolean getUpgradesTop(int x) {
        return upgradesTop[x];
    }

    public boolean getUpgradesBottom(int x) {
        return upgradesBottom[x];
    }

    public void delete() {
        upgradesTop = null;
        upgradesBottom = null;
        upgradesCost = null;
        alignment = null;
        buildings = null;
        recruits = null;
    }

    public void setRecruits(boolean[] recruits) {
        this.recruits = recruits;
    }

    public void setBuildings(boolean[] buildings) {
        this.buildings = buildings;
    }

    public void setUpgradesTop(boolean[] upgradesTop) {
        this.upgradesTop = upgradesTop;
    }

    public void setUpgradesBottom(boolean[] upgradesBottom) {
        this.upgradesBottom = upgradesBottom;
    }

    public void setUpgradesCost(int[] upgradesCost) {
        this.upgradesCost = upgradesCost;
    }

    public void setAlignment(int[] alignment) {
        this.alignment = alignment;
    }

    public void setNumMechs(int numMechs) {
        this.numMechs = numMechs;
    }

    @Override
    public String toString() {
        return "PlayerMat{" + "\n" +
                Arrays.toString(recruits) + "\n" +
                Arrays.toString(buildings) + "\n" +
                Arrays.toString(upgradesTop) + "\n" +
                Arrays.toString(upgradesBottom) + "\n" +
                Arrays.toString(alignment) + "\n" +
                '}';
    }
}
