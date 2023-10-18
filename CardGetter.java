package com.company;
import java.util.*;
public class CardGetter {
  public static ArrayList<Integer> deck = new ArrayList<Integer>(Arrays.asList(1,1,1,1,
        2,2,2,2,
        3,3,3,3,
        4,4,4,4,
        5,5,5,5,
        6,6,6,6,
        7,7,7,7,
        8,8,8,8,
        9,9,9,9,
        10,10,10,10,
        10,10,10,10,
        10,10,10,10,
        10,10,10,10));
    public static List<Integer> deckArray = Arrays.asList(1,1,1,1,
            2,2,2,2,
            3,3,3,3,
            4,4,4,4,
            5,5,5,5,
            6,6,6,6,
            7,7,7,7,
            8,8,8,8,
            9,9,9,9,
            10,10,10,10,
            10,10,10,10,
            10,10,10,10,
            10,10,10,10);

    public static int getCard() {
        int chosenCard;
        int index = (int)(Math.random() * (deck.size() - 1));
        chosenCard = deck.get(index);
        deck.remove(index);
        return chosenCard;
    }
    public static int getDeckSize() {
        return deck.size();
    }

    public static void shuffle() {
        deck.clear();
        deck.addAll(deckArray);
    }

    public static boolean deck_needs_shuffle() {
        if(deck.size() < 21) {
            return true;
        } else {
            return false;
        }

    }

    }

