

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author dshaffer4
 */
public class Controller {
    
    private static final String[] MESSAGE = {"   [ Set Your Wager Then Deal ]",
                                             "  < Select Discards Then Play >"};
    
    private static final String[] PAIRS = {"One", "Two", "Three", "Four"};
    
    private static final String[] CARD_RANK = {"Ace", "Two", "Three", "Four", 
                                               "Five", "Six", "Seven", "Eight", 
                                               "Nine", "Ten", "Jack", "Queen", 
                                               "King"};
    private static final String[] CARD_SUIT = 
                                      {"Spades", "Hearts", "Diamonds", "Clubs"};
    
    private static int NUMBER_OF_CARDS_IN_FULL_HAND = 5;
    
    private static final int[] PAYOUTS = {0, 1, 2, 3, 4, 6, 9, 25, 50, 250};
    private static final int MAX_PAYOUT = 4000;
    
    private static final int MAX_WAGER = 5;
    private static final int MIN_WAGER = 1;
    
    private static ArrayList<Integer> deck;
    
     ////////////////////////////////////////////////////////////////////////////
    /******************
     *  GAME CONTROL  *  
     ******************/ 
    private static States currentState;
    
    private static int numberOfCardsLegalToDiscard;
    
    ////////////////////////////////////////////////////////////////////////////
    
    /******************
     *  PLAYER STUFF  *  
     ******************/    
    private static Hand      hand;           // The player's hand
    
    private static final int STARTING_COOKIES = 9999; //50
    
    private static int     wager;
    
    ////////////////////////////////////////////////////////////////////////////
    /******************
     *  CALCULATIONS  * 
     ******************/    
    private static int []  handRanks;     // The ranks of all the cards in the player's hand
    private static int     highestCard;   // The Highest Ranked Card in the Hand
    private static boolean straightFound; // TRUE if a straight was found
    private static boolean flushFound;    // TRUE if a flush was found
    private static boolean pairCaseFound; // TRUE if a good hand was found (pairs, multi of a kind, full house)
        
    private static int payoutMultiplier;
    private static int currentPayout;
    
    ////////////////////////////////////////////////////////////////////////////
    /******************
     *     OUTPUT     *
     ******************/
    private static String myResult;    // The hand's "spoken" result is stored here
    private static int score;
    
    ////////////////////////////////////////////////////////////////////////////
    /******************
     *  VIEW CONTROL  *
     ******************/
    
    private static View view;
    
    ////////////////////////////////////////////////////////////////////////////
   
    
    public Controller(View view, States s)
    {
        score         = STARTING_COOKIES;
        wager         = MIN_WAGER;
        currentPayout = PAYOUTS[0];
        
        this.view = view;
        currentState = s;
        
        setActionListeners();
    }

    
    private static void setActionListeners()
    {
        
        view.getProgressButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                switch(currentState.getCurrentState())
                {
                    case WAGER:    prePlayAction();
                                   view.getProgressButton().setPlayHand();
                                   view.setPlayStateDisplay();
                    break;
                        
                    case PLAY:     discard();
                                   preContinueAction();
                                   view.getProgressButton().setContinue();
                                   view.setContinueStateDisplay();
                    break;
                        
                    case CONTINUE: preWagerAction();
                                   view.getProgressButton().setStartHand();
                                   view.setWagerStateDisplay();
                    break;
                }

            }
            
        });
        
        ///////////////////////////////////////////////////////////////        

        view.getWagerButton().addActionListener( new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                view.getWagerButton().pressButton();
                
            }
        });

        ///////////////////////////////////////////////////////////////

        view.getDiscardButton(0).addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(view.getDiscardButton(0).isActive())
                {
                    view.getDiscardButton(0).toggleButton();
                    hand.toggleDiscard(0);
                    discardCountControl(0);
                }
            }
        });
        view.getDiscardButton(1).addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(view.getDiscardButton(1).isActive())
                {
                    view.getDiscardButton(1).toggleButton();
                    hand.toggleDiscard(1);
                    discardCountControl(1);
                }
            }
        });
        view.getDiscardButton(2).addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(view.getDiscardButton(2).isActive())
                {                
                    view.getDiscardButton(2).toggleButton();
                    hand.toggleDiscard(2);
                    discardCountControl(2);
                }
            }
        });
        view.getDiscardButton(3).addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(view.getDiscardButton(3).isActive())
                {                
                    view.getDiscardButton(3).toggleButton();
                    hand.toggleDiscard(3);
                    discardCountControl(3);
                }
            }
        });
        view.getDiscardButton(4).addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(view.getDiscardButton(4).isActive())
                {
                    view.getDiscardButton(4).toggleButton();
                    hand.toggleDiscard(4);
                    discardCountControl(4);
                }
            }
        });
    }
    
    public static void start()
    {
        newDeck();
        view.clearGamePanel();
        view.displayCardBackings();
        view.setWagerStateDisplay();
        view.updateCurrentWinnings(score);
        view.updateCurrentHandOutcome(MESSAGE[0]);
                
        System.out.println("============\n"
                         + " GAME ENDED \n"
                         + "============");
    }
    
    
    public static void preWagerAction() // RIGHT AFTER CONTINUE IS PRESSED
    {
        // Clear card display / show card backings
        view.clearGamePanel();

        view.displayCardBackings();
     
        view.updateCurrentHandOutcome(MESSAGE[0]);
        
        if(deck.size() < (NUMBER_OF_CARDS_IN_FULL_HAND * 2))
        {
            newDeck();
        }
    }
    
    public static void prePlayAction() // RIGHT AFTER WAGER IS PRESSED
    {
        //view.displayHand(hand);
        startNewHand();
        view.displayHand(hand);
        
        spendWager();

        view.updateCurrentWinnings(score);// show how much we spent on wager
        
        view.updateCurrentHandOutcome(MESSAGE[1]);
    }
    
    public static void preContinueAction() // RIGHT AFTER PLAY IS PRESSED
    {

        view.displayHand(hand);

        sortHand(); // NEEDED FOR DETERMINING HIGHEST HAND

        setHandRanks();
        determineHighestHand();

        view.updateCurrentHandOutcome(myResult, getTotalPayout());
        updateScore();
        view.updateCurrentWinnings(score); 
    }
    
    
    private static void discardCountControl(int latestDiscardButtonPressed)
    {
        int i;
        int currentAmountDiscarding = 0;
        
        for(i = 0; i < NUMBER_OF_CARDS_IN_FULL_HAND; i++)
        {
            if(hand.shouldDiscardCard(i))
            {
                currentAmountDiscarding++;
            }
        }
        
        i = (int)(Math.random() * 5);
        
        while(currentAmountDiscarding > numberOfCardsLegalToDiscard)
        {
            if(hand.shouldDiscardCard(i) && i != latestDiscardButtonPressed)
            {
                hand.hold(i);
                view.getDiscardButton(i).setToHolding();
                
                currentAmountDiscarding--;
            }
            
            if(i < NUMBER_OF_CARDS_IN_FULL_HAND - 1){
                i++;
            } else { 
                i = 0;
            }
        }
    }
    
    
//     private void discard(boolean[] discarding, Card[] hand){
     private static void discard(){
        int currentDrawnCard;        
        
        for(int i = 0; i < NUMBER_OF_CARDS_IN_FULL_HAND; i++)
        {
            if(hand.shouldDiscardCard(i))
            {
                System.out.print("DISCARDING: " + hand.getCard(i).displayInfo());
                
                currentDrawnCard = deck.get(deck.size() - 1);
                deck.remove(deck.size() - 1);
                hand.setCard(i, currentDrawnCard);
            }
            
            System.out.println();
        }
    }
    
     
     
     
    private static void startNewHand(){
        
        int currentDeckCard;
        hand = new Hand(NUMBER_OF_CARDS_IN_FULL_HAND);
        
        numberOfCardsLegalToDiscard = 3;
        
         // get cards         
        for(int currentHandCard = 0; currentHandCard < hand.length; currentHandCard++)
        {
            currentDeckCard = deck.get(deck.size() - 1); // Takes a card from the top of the deck
            deck.remove(deck.size() - 1);                       // The card is removed from the deck 
            hand.setCard(currentHandCard, currentDeckCard);
            
            //view.setCardImage(currentHandCard, currentDeckCard - 1);
            
            if(hand.getCard(currentHandCard).isAce())
            {
                numberOfCardsLegalToDiscard = 4;
            }
        }

    }
    
    
    
    
    
    private static void newDeck(){
       
        deck = new ArrayList<Integer>();
        //deck.ensureCapacity(52);
        int index;
        
        for(int i = 0; i < 52; i++)
        {
            deck.add(0);
        }
        
        for(int card = 1; card <= 52; card++)
        {
            index = (int)Math.floor(Math.random() * 52);
            
            while(deck.get(index) != 0) // linear probe if collision
            {
                index++;
                
                if(index >= 52)
                {
                    index = 0;
                }
            }
            
            deck.set(index, card);
        }
    }
    
    
    private static int getTotalPayout()
    {
        return (currentPayout == PAYOUTS[PAYOUTS.length - 1] ? 
               (wager == MAX_WAGER ? MAX_PAYOUT 
                                   : (currentPayout * wager))
                                                             : (currentPayout * wager));   
    }
    
    
    private static void spendWager()
    {
        wager = view.getWagerAmount();
        
        score -= wager;
    }
    
    private static void updateScore()
    {
        if(currentPayout == PAYOUTS[PAYOUTS.length - 1] && wager == MAX_WAGER)
        {
            score += MAX_PAYOUT;
        }
        else
        {
            score += (currentPayout * wager);
        }   
    }
    
    
    private static void print(){ 
        System.out.println(myResult);
    }
    
    private static void printHand(){
        System.out.println("========================================================"
                         + "=========================");
        for(int i = 0; i < hand.length; i++)
        {
            System.out.print(hand.getCard(i) + "  ");
        }
        System.out.println();
    }
    
    
    /////////////////////////////////////////////////////////////////////////
    //                                                                     //
    //                             POKER LOGIC                             // 
    //                                                                     //
    /////////////////////////////////////////////////////////////////////////
    
    
    private static void sortHand(){ hand.sort(); }
    
    /******************************************
     * determineHighestHand
     * ---------------------------------------
     * Determines the highest hand value
     ******************************************/
    private static void determineHighestHand(){

        currentPayout = PAYOUTS[0];
        
        flushFound    = checkFlush();
        straightFound = checkStraight();

        if(!flushFound && !straightFound)
        {
            pairCaseFound = checkOfAKind(); // CHECK PAIRS (2,22,3,4) && FULL HOUSE

            if(!pairCaseFound)
            {
                findHighCard();            // CHECK HIGH CARD
            }
        }
        else
        {
            if(flushFound && straightFound){
                if(checkRoyalFlush()){
                    myResult = ("Royal Flush");
                    currentPayout = PAYOUTS[9];
                }else{
                    myResult = ("Straight Flush");
                    currentPayout = PAYOUTS[8];
                }
            }else if(flushFound){
                myResult = ("Flush");
                currentPayout = PAYOUTS[5];
            }else{
                myResult = ("Straight");
                currentPayout = PAYOUTS[4];
            }
        }
    }
    
    /******************************************
     * checkFlush
     * ----------------------------------------
     * Checks to see if the hand is a flush
     ******************************************/
    private static boolean checkFlush(){
        
        boolean escape = false;
        int i = 0;
        
        int cardSuit = hand.getCard(i).getSuit();
        
        while(i < hand.length && !escape)
        {
            if(cardSuit != hand.getCard(i).getSuit())
            {
                escape = true;
            }
            i++;
        }
        
        return (!escape);
    }
    
    /******************************************
     * checkStraight
     * ----------------------------------------
     * Checks to see if the hand is a straight
     ******************************************/
    private static boolean checkStraight(){
        
        boolean aceMarker;
        boolean escape = false;
        int i = 1;
        int previousCardRank = handRanks[0];
        
        while(i < hand.length && !escape)
        {   
            if(previousCardRank == 1){
                aceMarker = true;
            } else {
                aceMarker = false;
            }

            if(previousCardRank != 13)// If our previous is not a king
            {
                if(handRanks[i] == (previousCardRank + 1))  // If sequential     
                {
                    previousCardRank = handRanks[i];
                }
                else if(aceMarker && handRanks[i] == 10) // IF WE HAVE AN ACE AND If there are high cards following (Ace is our highest, but ordered as lowest)
                {
                    previousCardRank = handRanks[i];
                }
                else
                {   
                    escape = true;
                }
            }
            else // If our previous WAS a king
            {
                if(handRanks[i] == 1) // Check if our new card is an ACE
                {
                    previousCardRank = handRanks[i];
                }
                else
                {
                    escape = true;
                }
            }
            
            i++;
        }
        
        return (!escape);
    }
    
    /************************************************
     * checkRoyalFlush
     * ----------------------------------------------
     * PRECONDITIONS: Must be a straight and a flush
     * Checks if the straight flush is a royal flush
     ************************************************/
    private static boolean checkRoyalFlush(){
        boolean returnValue = false;
        
        if(handRanks[0] == 1 
//        && handRanks[1] == 10
//        && handRanks[2] == 11
//        && handRanks[3] == 12
        && handRanks[4] == 13)
        {
            returnValue = true;
        }
        
        return returnValue;
    }
    
    /*******************************************************
     * checkOfAKind
     * ----------------------------------------------------
     * Finds multiples of the same RANK of card in the current
     * hand.  This method can conclude with:
     * One Pair, Two Pair, Three of a Kind, Four of a Kind,
     * Full House, or NONE (returns false)
     *******************************************************/
    private static boolean checkOfAKind(){
        
        int currentCardRank;          // The current rank (used for check)
        int highestOfAKind       = 1; // The highest found "kind" amount
        int secondHighestOfAKind = 1; // Used for checking full house
        int currentOfAKind       = 1; // accumulator
        
        boolean highestOfAKindRankIsJacksOrBetter = false;
        
        currentCardRank = handRanks[0];
        
        for(int i = 1; i < handRanks.length; i++)
        {
            if( currentCardRank == handRanks[i] )
            {
                currentOfAKind++;
            }
            else
            {
                if(currentOfAKind >= highestOfAKind)
                {
                   secondHighestOfAKind = highestOfAKind;
                   highestOfAKind       = currentOfAKind;
                   
                    // if the rank is a king or ace || if the rank is a jack or queen
                    if(currentCardRank % 13 <= 1 || currentCardRank % 13 >= 11)
                    {
                        highestOfAKindRankIsJacksOrBetter = true;
                    }
                    else
                    {
                        highestOfAKindRankIsJacksOrBetter = false;
                    }
                }

                currentOfAKind  = 1;
                currentCardRank = handRanks[i];
                
        
            }
        }
        
        // What do we have left over?? DO STUFF WITH IT.
        if(currentOfAKind > 1)
        {
            if(currentOfAKind >= highestOfAKind)
            {
               secondHighestOfAKind = highestOfAKind;
               highestOfAKind       = currentOfAKind;
               
                // if the rank is a king or ace || if the rank is a jack or queen
                if(currentCardRank % 13 <= 1 || currentCardRank % 13 >= 11)
                {
                    highestOfAKindRankIsJacksOrBetter = true;
                }
                else
                {
                    highestOfAKindRankIsJacksOrBetter = false;
                }
            }
            else if(currentOfAKind > secondHighestOfAKind)
            {
                secondHighestOfAKind = currentOfAKind;
            }
        }
        // Check End
        
        if(highestOfAKind > 1)
        {
            if(highestOfAKind == 3 && secondHighestOfAKind == 2)
            {
                myResult = "Full House";
                currentPayout = PAYOUTS[6];
            }
            else if(highestOfAKind == 2 && secondHighestOfAKind == 2)
            {
                myResult = ("Two Pair");
                currentPayout = PAYOUTS[2];
            }
            else if(highestOfAKind == 2)
            {
                myResult = ("One Pair");
                
                if(highestOfAKindRankIsJacksOrBetter)
                {
                    currentPayout = PAYOUTS[1];
                }
                
            }
            else
            {
                myResult = (PAIRS[highestOfAKind - 1] + " of a Kind");
                
                if(highestOfAKind == 4)
                {
                    currentPayout = PAYOUTS[7];
                }
                else
                {
                    currentPayout = PAYOUTS[3];
                }
            }
            
            return true; 
        } else {return false;}
    }// END CHECK OF A KIND
    
    
    /***************************************************
     * findHighCard
     * -------------------------------------------------
     * Finds the highest card rank for the current hand
     ***************************************************/
    private static void findHighCard(){

        int i = 0;                 // LCV 1: Index of Array
        boolean instaExit = false; // LCV 2: Exit on condition
        
        if(handRanks[0] == 1) // CHECK IF WE HAVE AN ACE [ALWAYS FIRST IN OUR SORTED RANKS]
        {
            // look for the ace
            while(i < hand.length && !instaExit)
            {
                if(hand.getCard(i).isAce()) // Check if the current card has a rank of ace
                {
                    highestCard = hand.getCard(i).getCardNum(); // Set that card as our highest
                    instaExit = true;                   // EXIT
                }
                i++; // Update
            }        
        }
        else
        {
            // look for our highest card
            while(i < hand.length && !instaExit)
            {
                // IF [               THIS                      ] == [         THIS           ]
                if(  (hand.getCard(i).isKing() ? 13 : hand.getCard(i).getRank() ) == handRanks[(hand.length)-1]) // Check if the current card has a rank equal to our highest in our hand
                {
                    highestCard = hand.getCard(i).getCardNum(); // Set that card as our highest
                    instaExit = true;                   // EXIT
                }

                i++; // Update
            }
        }
        
        myResult = CARD_RANK[( highestCard % 13 == 0 ? (  CARD_RANK.length - 1 ) 
                                                     : ( (highestCard % 13) - 1) )] 
        + " of " + CARD_SUIT[(highestCard - 1) / 13]  +  " High";
        
//        if(highestCard % 13 <= 1 || highestCard % 13 >= 11)
//        {
//            currentPayout = PAYOUTS[1];
//        }
    }// END HIGH CARD
    
    /***************************************
     * setHandRanks
     * ------------------------------------
     * Gets each individual card rank for the
     * current hand and then SORTS them by rank.
     ***************************************/
    private static void setHandRanks(){
        handRanks = new int[hand.length];
        
        for(int i = 0; i < hand.length; i++)
        {
            handRanks[i] = hand.getCard(i).getRank();
        }
        
        Arrays.sort(handRanks);
    }
    
    
}
