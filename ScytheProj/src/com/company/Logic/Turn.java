package com.company.Logic;

import java.util.Objects;

public class Turn {
    private String topRow; // 'move' : 'gain' : 'trade' : 'tradePop: 'produce' : 'bolster' : 'combatCard'
    private String bottomRow; //null if not taking an action
    private int resource1 = -1; //0 == food, 1 ==wood, 2 == metal, 3 == oil ;-1 for nothing
    private int resource2 = -1;
    private int enlistOrBuild;
    private int numResource; //can be negative if paying for a bottom row action
    private boolean isStarred;
    private int winInt; // 0=upgrade,1==enlist,2== deploy, 3==build, 4 == popularity, 5==powa

    public int getWinInt() {
        return winInt;
    }
    public void addInt(int x) {
        winInt = x;
    }
    public void setWinInt(int winInt) {
        this.winInt = winInt;
    }

    public Turn(String topRow, String bottomRow, int resource1, int resource2, int numResource) {
        this.topRow = topRow;
        this.bottomRow = bottomRow;
        this.resource1 = resource1;
        this.resource2 = resource2;
        this.numResource = numResource;
        winInt = -1;
    }

    public void delete() {
        topRow = null;
        bottomRow = null;
    }

    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    public Turn(String topRow, String bottomRow) {
        this.topRow = topRow;
        this.bottomRow = bottomRow;
        if(this.topRow.equals("trade")) {
            resource1 = (int) (Math.random()*4);
            resource2 = (int) (Math.random()*4);
            numResource = 2;
        }
    }

    public String getTopRow() {
        return topRow;
    }

    public void setTopRow(String topRow) {
        this.topRow = topRow;
    }

    public String getBottomRow() {
        return bottomRow;
    }

    public void setBottomRow(String bottomRow) {
        this.bottomRow = bottomRow;
    }

    public int getResource1() {
        return resource1;
    }

    public void setResource1(int resource1) {
        this.resource1 = resource1;
    }

    public void setResource2(int resource2) {this.resource2 = resource2;}

    public int getResource2() {
        return resource2;
    }

    public int getNumResource() {
        return numResource;
    }

    public void setNumResource(int numResource) {
        this.numResource = numResource;
    }

    @Override
    public String toString() {
        return "Turn{" +
                "topRow='" + topRow + '\'' +
                ", resource1=" + Logic.resourceString[resource1+1]+
                ", resource2=" + Logic.resourceString[resource2+1] +
                ", bottomRow='" + bottomRow + '\'' +
                (bottomRow.equals("upgrade") || bottomRow.equals("enlist") || bottomRow.equals("build") ?
                        (bottomRow + " the " + -1) : "") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turn turn = (Turn) o;
        return Objects.equals(topRow, turn.topRow) ||
                Objects.equals(bottomRow, turn.bottomRow) || topRow.contains(turn.topRow)
                || turn.topRow.contains(topRow) || (topRow.equals("bolster") && turn.topRow.equals("combatCard"))
                || (turn.topRow.equals("bolster") && topRow.equals("combatCard")) || (turn.topRow.equals("gain") && topRow.equals("move"))
                || (topRow.equals("gain") && turn.topRow.equals("move"));
    }

    @Override
    public int hashCode() {
        return Objects.hash(topRow, bottomRow);
    }
}
