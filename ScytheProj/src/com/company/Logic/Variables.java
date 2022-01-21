package com.company.Logic;

import com.company.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Variables {
    private int numStars;
    private int popularity;
    private int power;
    private int money;
    private int[] battleCards;
    private int numTerritory;
    private int[] units; //The values will be the index into territories and worker territories
    private int[] territories;
    private int[] workerTerritories;
    private int numWood;
    private int numFood;
    private int numMetal;
    private int numOil;
    private PlayerMat playerMat;
    private Turn lastTurn;

    public Variables() {

    }
    Variables(int popularity,int power, int money,int[] battleCards,int[] territories) {
        units= new int[2];
        numTerritory = 2;
        numStars = 0;
        this.popularity = popularity;
        this.power = power;
        this.money = money;
        this.battleCards = battleCards;
        this.territories = territories;
        workerTerritories = territories;
        units[0] = 0;
        units[1] = 1;
        numWood = 0;
        numOil =0;
        numFood = 0;
        numMetal=0;
        playerMat = new PlayerMat();
    }

    private void setVars(Variables toCopy) {
        this.numStars = toCopy.getNumStars();
        this.popularity = toCopy.getPopularity();
        this.power = toCopy.getPower();
        this.money = toCopy.getMoney();
        this.battleCards = toCopy.getBattleCards();
        this.numTerritory = toCopy.getNumTerritory();
        this.territories = new int[toCopy.territories.length];
        System.arraycopy(toCopy.getTerritories(),0,this.territories,0,toCopy.getTerritories().length);
        this.workerTerritories = toCopy.getWorkerTerritories();
        this.numWood = toCopy.getNumWood();
        this.numFood = toCopy.getNumFood();
        this.numMetal = toCopy.getNumMetal();
        this.numOil = toCopy.getNumOil();
        this.playerMat = new PlayerMat( toCopy.getPlayerMat().getRecruits(),
                                        toCopy.getPlayerMat().getBuildings(),
                                        toCopy.getPlayerMat().getUpgradesTop(),
                                        toCopy.getPlayerMat().getUpgradesBottom(),
                                        toCopy.getPlayerMat().getUpgradesCost(),
                                        toCopy.getPlayerMat().getAlignment(),
                                        toCopy.getPlayerMat().getNumMechs(),
                                        toCopy.getPlayerMat().costs);
    }

    private void trade(Turn turn) {
        if(money <= 0 ) {
            lastTurn.setTopRow("Nothing");
            return;
        }
        money--;
        switch(turn.getResource1()) {
            case 0 :numFood++;
                break;
            case 1: numWood++;
                break;
            case 2: numMetal++;
                break;
            case 3: numOil++;
                break;
        }
        switch(turn.getResource2()) {
            case 0 :numFood++;
                break;
            case 1: numWood++;
                break;
            case 2: numMetal++;
                break;
            case 3: numOil++;
                break;
        }
        if(playerMat.getBuildings(1) && power >= 0) {
            power++;
            if(power >= 16) {
                power = -1;
                numStars++;
                lastTurn.setStarred(true);
                lastTurn.setWinInt(5);
            }
        }
    }

    private void tradePop() {
        if(money <= 0 || popularity < 0) {
            lastTurn.setTopRow("Nothing");
            return;
        }
        money--;
        popularity++;
        if(playerMat.getUpgradesTop(5)) {
            popularity++;
        }
        if(playerMat.getBuildings(1) && power >= 0) {
            power++;
            if(power >= 16) {
                power = -1;
                lastTurn.setWinInt(5);
                lastTurn.setStarred(true);
                numStars++;
            }
        }

        if(popularity >= 18) {
            popularity = -1;
            lastTurn.setWinInt(4);
            lastTurn.setStarred(true);
            numStars++;
        }
    }

    private void move() {
        //to do
    }

    private void gain() {
        money++;
        if(playerMat.getUpgradesTop(1)) {
            money++;
        }
    }

    private void handleWorkerProd() {
        for (int i : workerTerritories) {
            if(Logic.getTerritoryType(i) == 4) {
            //todo
            }
        }
    }

    private void produce() {
        int[] resources = Logic.getTerritoryType(workerTerritories);
        int index = 0;
        for(int i : resources) {
            switch(i) {
                case 0:numFood++;
                if (index == 0) {
                    lastTurn.setResource1(i);
                    index++;
                } else {
                    lastTurn.setResource2(i);
                }
                break;
                case 1:numWood++;
                    if (index == 0) {
                        lastTurn.setResource1(i);
                        index++;
                    } else {
                        lastTurn.setResource2(i);
                    }
                break;
                case 2:numMetal++;
                    if (index == 0) {
                        lastTurn.setResource1(i);
                        index++;
                    } else {
                        lastTurn.setResource2(i);
                    }
                break;
                case 3:numOil++;
                    if (index == 0) {
                        lastTurn.setResource1(i);
                        index++;
                    } else {
                        lastTurn.setResource2(i);
                    }
                break;
                case 4: handleWorkerProd();
                break;
            }
        }
    }

    private void bolster() {
        if(money <= 0 || power < 0) {
            lastTurn.setTopRow("Nothing");
            return;
        }
        money--;
        power += 2;
        if (playerMat.getUpgradesTop(4)) {
            power++;
        }

        if(playerMat.getBuildings(3) && popularity >= 0) {
            popularity++;
            if(popularity >= 18) {
                numStars++;
                lastTurn.setWinInt(4);
                lastTurn.setStarred(true);
                popularity = -1;
            }
        }


        if(power >= 16) {
            power = -1;
            numStars++;
            lastTurn.setWinInt(5);
            lastTurn.setStarred(true);
        }

    }

    private void combatCard() {
        if(money <= 0) {
            lastTurn.setTopRow("Nothing");
            return;
        }
        int[] newBattle = new int[battleCards.length + 2];
        int index = 0;
        for(int i = 0 ; i < battleCards.length;i++) {

            if(battleCards[i] != 0) {
                newBattle[index] = battleCards[i];
                index++;
            }
        }
       // Scanner scan = new Scanner(System.in);
        newBattle[index] = 3;//scan.nextInt();

        if(playerMat.getBuildings(3) && popularity >= 0) {
            popularity++;
            if(popularity >= 18) {
                numStars++;
                lastTurn.setWinInt(4);
                lastTurn.setStarred(true);
                popularity = -1;
            }
        }

        if(playerMat.getUpgradesTop(5)) {
            newBattle[index+1] = 3;
        }
        battleCards = newBattle;
    }

    public Turn getLastTurn() {
        return lastTurn;
    }

    public Variables(Turn turn, Variables currVar) {
        lastTurn = turn;
        setVars(currVar);
        if(turn.equals(Main.lastTurn)) {
            return;
        }
        switch (turn.getTopRow()) {
            case"trade" : trade(turn);
            break;
            case "tradePop": tradePop();
            break;
            case "move" : move();
            break;
            case "gain" : gain();
            break;
            case "produce" : produce();
            break;
            case "bolster" : bolster();
            break;
            case "combatCard" : combatCard();
            break;
        }

        switch(turn.getBottomRow()) {
            case "upgrade" : upgrade();
            break;
            case "enlist": enlist();
            break;
            case  "deploy": deploy();
            break;
            case "build" : build();
        }
    }

    private void upgrade() {
        if(numOil < playerMat.costs[0]) {
            lastTurn.setBottomRow("Nothing");
            return;
        }
        int bottom = -1;
        int top = -1;
        for(int i = 0; i < 6; i++) {
            if(!playerMat.getUpgradesBottom(i)) {
                bottom = i;
                break;
            }
        }
        for(int i = 0; i < 6; i++) {
            if(!playerMat.getUpgradesTop(i)) {
                top = i;
                break;
            }
        }

        if(top ==-1 && bottom == -1) {
            lastTurn.setBottomRow("Nothing");
        } else {
            if(playerMat.getRecruits(0) && power >= 0) {
                power++;
                if(power >= 16) {
                    lastTurn.setStarred(true);
                    lastTurn.setWinInt(5);
                    power = -1;
                    numStars++;
                }
            }
            numOil-=playerMat.costs[0];
            playerMat.upgrade(top, bottom);
            if(playerMat.allUpgraded()) {
                lastTurn.setWinInt(0);
                numStars++;
                lastTurn.setStarred(true);
            }
        }
    }

    private void enlist() {
        if(numFood < playerMat.costs[1]) {
            lastTurn.setBottomRow("Nothing");
            return;
        }
        if(!playerMat.allEnlist()) {
            for(int i = 0; i< 4; i++) {
                if (!playerMat.getRecruits(i)) {
                    playerMat.enlist(i);
                    numFood -= playerMat.costs[1];
                    break;
                }
            }
            if (playerMat.allEnlist()) {
                numStars++;
                lastTurn.setWinInt(1);
                lastTurn.setStarred(true);
            }
        } else  {
            lastTurn.setBottomRow("Nothing");
        }
    }

    private void deploy() {
        if(numMetal < playerMat.costs[2]) {
            lastTurn.setBottomRow("Nothing");
            return;
        }
        if(playerMat.getNumMechs() < 4) {
            numMetal -= playerMat.costs[2];
            playerMat.deploy();
            if(playerMat.getNumMechs() >= 4) {
                numStars++;
                lastTurn.setWinInt(2);
                lastTurn.setStarred(true);
            }
        } else {
            lastTurn.setBottomRow("Nothing");
        }
        if (playerMat.getRecruits(2)) {
            money++;
        }
    }

    private void build() {
        if (numWood < playerMat.costs[3]) {
            lastTurn.setBottomRow("Nothing");
            return;
        }
        if (!playerMat.allBuilt()) {
            for (int i = 0; i < 4; i++) {
                if (playerMat.build(i)) {
                    numWood -= playerMat.costs[3];
                    break;
                }
            }
            if(playerMat.getRecruits(3) && popularity >= 0) {
                popularity++;
                if(popularity >= 18) {
                    lastTurn.setWinInt(4);
                    lastTurn.setStarred(true);
                    popularity = -1;
                    numStars++;
                }
            }
            if (playerMat.allBuilt()) {
                numStars++;
                lastTurn.setWinInt(3);
                lastTurn.setStarred(true);
            }

        } else {
            lastTurn.setBottomRow("Nothing");
        }
    }

    public void delete() {
        if(lastTurn != null) {
            lastTurn.delete();
        }
        lastTurn = null;
        battleCards = null;
        units = null; //The values will be the index into territories and worker territories
        territories = null;
        workerTerritories =null;
        playerMat.delete();
        playerMat = null;
}

    public int calcScore() {
        int popMult = (popularity <= 6 ? 3 : (popularity <= 12 ? 4 : 5));
        int score = 0;
        score += numStars*popMult*5;
        score += numTerritory*(popMult-1);
        score += (numWood+numFood+numMetal+numOil)*(popMult-2);
        score += money;
        //internalScore = score + (numStars*10);
        return score;
    }

    public int calcInternalScore() {
        int internalScore = (popularity < 0 ? 18 : popularity)*5;
        internalScore += numStars*15;
        internalScore += numTerritory;
        internalScore += (numWood+numFood+numMetal+numOil);
        internalScore += playerMat.getNumMechs();
        internalScore += playerMat.getNumUpgrades()*5;
        internalScore += playerMat.getNumRecruits()*10;
        internalScore += playerMat.getNumBuildings()*5;

        return internalScore;
    }

    public int getNumStars() {
        return numStars;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getPower() {
        return power;
    }

    public int getMoney() {
        return money;
    }

    public int[] getBattleCards() {
        return battleCards;
    }

    public int getNumTerritory() {
        return numTerritory;
    }

    public int[] getTerritories() {
        return territories;
    }

    public int[] getWorkerTerritories() {
        return workerTerritories;
    }

    public int getNumWood() {
        return numWood;
    }

    public int getNumFood() {
        return numFood;
    }

    public int getNumMetal() {
        return numMetal;
    }

    public int getNumOil() {
        return numOil;
    }


    public PlayerMat getPlayerMat() {
        return playerMat;
    }

    public void setNumStars(int numStars) {
        this.numStars = numStars;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setNumTerritory(int numTerritory) {
        this.numTerritory = numTerritory;
    }

    public void setNumWood(int numWood) {
        this.numWood = numWood;
    }

    public void setNumFood(int numFood) {
        this.numFood = numFood;
    }

    public void setNumMetal(int numMetal) {
        this.numMetal = numMetal;
    }

    public void setNumOil(int numOil) {
        this.numOil = numOil;
    }

    public void setPlayerMat(PlayerMat playerMat) {
        this.playerMat = playerMat;
    }

    public void setLastTurn(Turn lastTurn) {
        this.lastTurn = lastTurn;
    }

    public void setBattleCards(int[] battleCards) {
        this.battleCards = battleCards;
    }

    public void setUnits(int[] units) {
        this.units = units;
    }

    public void setTerritories(int[] territories) {
        this.territories = territories;
    }

    public void setWorkerTerritories(int[] workerTerritories) {
        this.workerTerritories = workerTerritories;
    }

    @Override
    public String toString() {
        return "Variables{" + "\n" +
                numStars + "\n" +
                popularity + "\n" +
                power + "\n" +
                money + "\n" +
                Arrays.toString(battleCards) + "\n" +
                numTerritory + "\n" +
                Arrays.toString(territories) + "\n" +
                Arrays.toString(workerTerritories) + "\n" +
                numWood + "\n" +
                numFood + "\n" +
                numMetal + "\n" +
                numOil + "\n" +
                playerMat.toString() + "\n" +
                '}';
    }
}
