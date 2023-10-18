package com.company;
import java.util.*;
public class Player {
    public static ArrayList<Integer> hand = new ArrayList<Integer>();
    public static List<Integer> dealerHand = new ArrayList<Integer>();
    public static int running_count = 0;
    public static double money = 10000;
    public static double wager = 0;



    public static void dealCards() {
        hand.clear();
        dealerHand.clear();

        Player.addPlayerCard(CardGetter.getCard());
        Player.addPlayerCard(CardGetter.getCard());

        int dealerCard1 = CardGetter.getCard();
        int dealerCard2 = CardGetter.getCard();

        Player.addDealerCard(dealerCard1);
        addCardToCount(dealerCard1);
        Player.addDealerCard(dealerCard2);


    }

    public static void addPlayerCard(int card) {

        System.out.println("Adding player card. Card value is: " + card);


        hand.add(card);
        addCardToCount(card);
        System.out.println("The players hand is now: " + hand);

    }

    public static void splitHand() {
        if(hand.size() == 2 && hand.get(0) == hand.get(1)) {
            List<Integer> newHand = new ArrayList<>();
            newHand.add(hand.remove(1));

            addPlayerCard(CardGetter.getCard());
            int secondHandCard = CardGetter.getCard();
            addCardToCount(secondHandCard);
            newHand.add(secondHandCard);

        }
    }

    public static boolean checkDealerBlackjack() {
        if(dealerHand.size() == 2 && (dealerHand.contains(1) && dealerHand.contains(10))) {
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean checkPlayerBlackjack() {
        if(hand.size() == 2 && (hand.contains(1) && hand.contains(10))) {
            return true;
        }
        else{
            return false;
        }
    }

    private static int evalHand(List<Integer> hand) {
        int sum = 0;
        for(Integer i: hand) {
            sum += i;
        }
        return sum;
    }

    public static void addCardToCount(int card){
        if (card == 10 || card == 1){
            running_count--;
            return;
        }
        if (card < 7){
            running_count++;
            return;
        }

    }

    public static void place_bet() {
        double count = running_count;
        double tableMin = 20;
        double spread = 12;
        double maxBet = tableMin * spread;
        double bet = 0;

        System.out.println("Count is: " + count);
        if(count <= 1) {
            bet = tableMin;
        }
        if( count == 2) {
            bet = tableMin * 4;

        }
        if( count == 3) {
            bet = tableMin * 8;
        }
        if(count >= 4) {
            bet = maxBet;
        }

        wager = bet;
        money -= bet;
        System.out.println("Wager is: " + wager);
        System.out.println("Starting money from this hand is: " + money);
    }



    public static void playHand(List<Integer> hand) {
        boolean keepPlaying = true;

        while(keepPlaying) {
            if(shouldSurrender(hand)) {
                surrender();
                return;
            }
            else if(shouldDouble(hand)) {
                doubleHand(hand);
                return;
            }
             else if(shouldHit(hand)) {
                Player.addPlayerCard(CardGetter.getCard());
                if(evalHand(hand) > 21) {
                    return;
                }
            }
            else{
                return;
            }
        }
    }


    public static boolean shouldDouble(List<Integer> hand) {
        int value = evalHand(hand);
        int dealer = dealerHand.get(0);
        if(hand.size() > 2) {
            return false;
        }
        if(hand.contains(1) && value < 12) {
            int sum = value + 10;

            if((sum == 13 || sum == 14) && (dealer == 5 || dealer ==6)) {
                return true;
            }
            if ((sum == 15 || sum == 16) && (dealer == 4 || dealer == 5 || dealer == 6)){
                return true;
            }
            if ((sum == 17 || sum == 18) && (dealer == 3 || dealer == 4 || dealer == 5 || dealer == 6)){
                return true;
            }


        }
        else {
            if (value == 9){
                if (dealer == 3 || dealer == 4 || dealer == 5 || dealer == 6){
                    return true;
                }
                if (dealer == 2 && running_count >= 1){
                    return true;
                }
                if (dealer == 7 && running_count >= 4){
                    return true;
                }
            }
            if (value == 10){
                if (dealer != 10 && dealer != 1){
                    return true;
                }
                if (value >= 4){

                    return true;
                }
            }
            if(value == 11) {
                if(running_count < 1 && dealer ==1) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }
    public static boolean shouldHit(List<Integer> hand) {
        int value = evalHand(hand);
        int dealer = dealerHand.get(0);

        if(hand.contains(1) && value < 12) {
            if(value < 8) {
                return true;
            }
            if(value == 8 && (dealer == 9 || dealer == 10 || dealer ==1)) {
                return true;
            }
            return false;
        }
        if(value < 12) {return true;}
        if(value == 12) {
            //Stand 12 into 2 at count >= 3 otherwise hit
            if(dealer == 2) {
                if (running_count >= 3) {
                    return false;
                }
                else{
                    return true;
                }
            }
            //Stand 12 into 3 at count >= 2 otherwise hit
            else if(dealer == 3) {
                if(running_count >= 2) {
                    return false;
                }
                else{
                    return true;
                }
            }
            //Hit 12 into 4 if count < 0 otherwise stand
            else if(dealer == 4){
                if(running_count < 0){
                    return true;
                }
                else{return false;}
            }
            //Hit 12 into 5 if count <= -2 otherwise stand
            else if(dealer == 5){
                if(running_count <= -2){
                    return true;
                }
                else{
                    return false;
                }
            }
            //Hit 12 into 6 if count <= -1 otherwise stand
            else if(dealer == 6){
                if(running_count <= -1){
                    return true;
                }
                else{
                    return false;
                }
            }
            //Always hit 12 on dealer 7 and higher
            else{
                return true;
            }
        }
        if(value== 13 || value == 14) {
            if(dealer > 6 || dealer == 1) {
                return true;
            }
            return false;
        }
        if(value==15) {
            if(running_count >= 4 && dealer == 10) {
                return false;
            }
            if(dealer > 6 || dealer == 1) {
                return true;
            }
            return false;
        }
        if(value ==16) {
            if(running_count > 0 && dealer == 10) {
                return false;
            }
            if(running_count >= 5 && dealer == 9) {
                return false;
            }
            if(dealer > 6 || dealer == 1) {
                return true;
            }
            return false;
        }
        if(value >= 17) {
            return false;
        }
        return false;
    }

    public static boolean shouldSurrender(List<Integer> hand) {
        if(hand.size() > 2) {
            return false;
        }

        if(hand.contains(1) && evalHand(hand) < 12) {
            return false;
        }

        int playerHandValue = evalHand(hand);
        int dealerHandValue = evalDealerHand(dealerHand);

        if(playerHandValue == 14 && dealerHandValue == 10 && running_count > 3) {
            return true;
        }

        if(playerHandValue == 15) {
            if(dealerHandValue == 10 && running_count >= 0) {
                return true;
            }
            if(dealerHandValue == 1 && running_count >= 1) {
                return true;
            }
            if(dealerHandValue == 9 && running_count >= 2) {
                return true;
            }

        }

        if(playerHandValue== 16 && (dealerHandValue == 9 || dealerHandValue == 10 || dealerHandValue ==1)) {
            return true;
        }


        return false;
    }

    public static void surrender() {
        hand.clear();
        money += wager/2;
    }

    public static void doubleHand(List<Integer> hand) {
        Player.addPlayerCard(CardGetter.getCard());
        money -= wager;
        wager = wager * 2;
    }



    public static void addDealerCard(int card) {
        //card = CardGetter.getCard();
        System.out.println("Adding dealer card. Card value is: " + card);


        dealerHand.add(card);

        System.out.println("The players hand is now: " + dealerHand);

    }
    private static int evalDealerHand(List<Integer> dealerHand) {
        int sum = 0;
        for(Integer i: dealerHand) {
            sum += i;
        }
        return sum;
    }



    public static void dealerAction() {
        int card1 = dealerHand.get(0);
        int card2 = dealerHand.get(1);


        boolean checkSoft = (card1 == 1) || (card2 == 1);
        // if the dealer's hand doesn't have an ace...
        if(!checkSoft) {
            while(evalDealerHand(dealerHand) < 17) {
                Player.addDealerCard(CardGetter.getCard());
                if(dealerHand.get(dealerHand.size() - 1) == 1) {
                    checkSoft = true;
                    break;
                }
            }
        }
        if(checkSoft) {
            while(evalDealerHand(dealerHand) < 7) {
                Player.addDealerCard(CardGetter.getCard());
            }
            int dealerSum = evalDealerHand(dealerHand);
            if(dealerSum <= 11) {
                return;
            } else{
                while(evalDealerHand(dealerHand) < 17) {
                    Player.addDealerCard(CardGetter.getCard());
                }
                return;
            }

        }
        return;
    }
    public static void payOut() {
        int player_sum = evalHand(hand);
        int dealer_sum = evalDealerHand(dealerHand);

        if (player_sum > 21) {
            return;
        }
        if (player_sum < 12 && hand.contains(1)) {
            player_sum += 10;
        }

        if (dealer_sum > 21) {

            money += 2 * wager;

            if (checkPlayerBlackjack()) {
                money += 0.5 * wager;
            }

        }
        if (dealer_sum < 12 && dealerHand.contains(1)) {
            dealer_sum += 10;
        }
        if (dealer_sum > player_sum) {
            return;
        } else if (dealer_sum < player_sum) {
            money += 2 * wager;
            if (checkPlayerBlackjack()) {
                money += 0.5 * wager;
            }
        } else if (dealer_sum == player_sum) {
            money += wager;
        }
    }
    public static void playRounds() {
        int handsPlayed = 0;
        while(handsPlayed < 1000) {
            wager = 0;
            hand.clear();
            dealerHand.clear();
            if(CardGetter.deck_needs_shuffle()) {
                CardGetter.shuffle();
                running_count = 0;
            }
            place_bet();

            dealCards();

            if(!checkDealerBlackjack()) {
                playHand(hand);

                dealerAction();

                payOut();
            }
            handsPlayed++;


        }
        System.out.println("Final money is: " + money);
    }
    }




