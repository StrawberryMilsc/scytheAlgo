package com.company.Logic;

import java.time.temporal.ValueRange;
import java.util.ArrayList;

public class State implements Cloneable {
    private Boolean isLeaf;
    private ArrayList<State> children;
    private int score;
    private int highestPosScore;
    private Variables currVar;
    private Turn[] routeTo;
    public int childIndex;

    public State(Variables currVar) {
        this.currVar = currVar;
        routeTo = new Turn[0];
    }

    public int[] findRoute() {
        return null;
    }

    public boolean isLeaf() {
        return isLeaf;
    }
    public State() {

    }
    public Turn[] getRouteTo() {
        return routeTo;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void delete() {
        currVar.delete();
        currVar = null;
        for(Turn turn : routeTo) {
            if(turn != null) {
                turn.delete();
            }
        }
        if(!isLeaf) {
            for (State child : children) {
                child.delete();
            }
        }
        isLeaf = null;
        children = null;
        routeTo = null;
    }

    public State(Variables currVars, Turn turn) {
        this.isLeaf = true;
        this.children = new ArrayList<>();
        this.currVar = new Variables(turn,currVars);
        score = calcScore();
        highestPosScore = score;
        this.routeTo = new Turn[1];
        this.routeTo[0] = turn;
    }

    public State(Variables currVars, Turn[] routeTo,int childIndex) {
        this.isLeaf = true;
        this.children = new ArrayList<>();
        this.currVar = currVars;
        score = calcScore();
        highestPosScore = score;
        this.routeTo = new Turn[routeTo.length+ 1];
        for(int i = 0; i < routeTo.length; i++) {
            this.routeTo[i] = routeTo[i];
        }
        this.routeTo[routeTo.length] = currVars.getLastTurn();
        this.childIndex = childIndex;
    }
    void findHighest() {
        if(isLeaf) {
           if(currVar.calcInternalScore() > (Logic.gHighest != null ? Logic.gHighest.currVar.calcInternalScore() : 0 )) {
               Logic.gHighest = this;
           }
        } else {
            for(int i = 0; i < children.size(); i++) {
                children.get(i).findHighest();
            }
        }
    }

    public int getNumStars() {
        return currVar.getNumStars();
    }
    private int calcScore() {
        return currVar.calcScore();
    }

    public void grow(Turn[] turns, int numChild,int depth) {
        if(currVar.getNumStars() < 6 && depth <= 5) {
            createChildren(turns, numChild);
            for (State child : children) {
                Turn[] childTurn = Logic.generateTurns(child);
                child.grow(childTurn, childTurn.length, depth + 1);
            }
        }
    }
    public void createChildren(Turn[] turn,int numChild) {
        isLeaf = false;
        for(int i = 0; i < numChild; i++) {
            if(!turn[i].equals(currVar.getLastTurn()) && (turn[i] != null)) {
                children.add(i, new State(new Variables(turn[i], currVar), routeTo,i));
            }
        }
    }

    public Variables getCurrVar() {
        return currVar;
    }

    public Boolean getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public ArrayList<State> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<State> children) {
        this.children = children;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighestPosScore() {
        return highestPosScore;
    }


    @Override
    public String toString() {
        return "State{" + "\n" +
                isLeaf + "\n" +
                score +"\n" +
                currVar.toString() +"\n" +
                '}';
    }
}
