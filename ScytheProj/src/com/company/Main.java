package com.company;

import com.company.Logic.Logic;
import com.company.Logic.State;
import com.company.Logic.Turn;
import com.company.Logic.Variables;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Main{
    public static Turn lastTurn;
    public static void main(String[] args) throws Exception {

            Logic logic = new Logic();
            Turn[] steps;


            logic.grow();
            logic.findRoute(-1);
        while(logic.root != null && logic.root.getNumStars() < 6) {
            logic.grow();
            logic.curr = logic.root;
            steps = logic.findRoute();
            Turn nextTurn = null;
            for (Turn t : steps) {
                if (t != null) {
                    nextTurn = t;
                    System.out.println(t.toString());
                    break;
                }
            }
            if (nextTurn == null) {
                throw new Exception("Everything broke");
            }
            Variables variables = new Variables(nextTurn, logic.curr.getCurrVar());
            setVars(variables);
            logic.curr = new State(variables);
            logic.curr.getCurrVar().setLastTurn(nextTurn);
            logic.setRoot(logic.curr);
        }
        /*
        while(logic.curr.getNumStars() < 6 ) {//&& scan.nextInt() > 0) {
            System.out.println("Calculating route");
            steps = logic.findRoute();
            int first = -1;
            for (int i = 0; i < steps.length; i++) {
                if (steps[i] != null) {
                    System.out.println(steps[i].toString());
                    first = first < 0 ? i : first;
                    if(steps[i].isStarred()) {
                        System.out.println("Got a star for " + Logic.possibleWins[steps[i].getWinInt()]);
                    }
                }
            }
            logic.curr = new State(logic.root.getCurrVar(),steps[first]);
            lastTurn = steps[first];
            setVars(logic.curr.getCurrVar());
            logic.setRoot(logic.curr);
            logic.grow();

        }
        System.out.println(Logic.gHighest.getScore());
        System.out.println("stars: " + Logic.gHighest.getNumStars());

        */
    }

    private static void setVars(Variables curr) {
        Scanner scan = new Scanner(System.in);
        System.out.println("input num money gained: ");
        curr.setMoney(curr.getMoney()+scan.nextInt());
        System.out.println("input num Power gained: ");
        curr.setPower(curr.getPower()+scan.nextInt());
        System.out.println("input num popularity gained: ");
        curr.setPopularity(curr.getPopularity()+scan.nextInt());

    }

    public static int[] copyArray(int[] toCopy) {
        int[] toReturn = new int[toCopy.length];
        for(int i =0; i < toCopy.length; i++) {
            toReturn[i] = toCopy[i];
        }
        return toReturn;
    }

    public static boolean[] copyArray(boolean[] toCopy) {
        boolean[] toReturn = new boolean[toCopy.length];
        for(int i =0; i < toCopy.length; i++) {
            toReturn[i] = toCopy[i];
        }
        return toReturn;
    }

    public static String arrayToStringable(int[] o) {
        String s = "";
        for(Object obj : o) {
            s = s.concat(obj.toString());
        }

        return s;
    }
    public static String arrayToStringable(boolean[] o) {
        String s = "";
        for (Object obj : o) {
            s = s.concat(obj.toString());
        }

        return s;
    }

}
