

/**
 *
 * @author dshaffer4
 * -----------------------------------------------------------------------------------
 * NOTES:
 * KINGS HAVE BEEN RANKED AS "13" instead of "0", but ACES still hold the rank of "1".
 */
public class Card implements Comparable{//<Card>{
    
    private static final String[] CARD_RANK = {"Ace", "Two", "Three", "Four", 
                                               "Five", "Six", "Seven", "Eight", 
                                               "Nine", "Ten", "Jack", "Queen", 
                                               "King"};
    private static final String[] CARD_SUIT = 
                                {"Spades", "Hearts", "Diamonds", "Clubs"};

    private int cardNum;
    private int rank;
    private int suit;
    
    Card(int cardNum)
    {
        this.cardNum =  cardNum;
        this.rank    =  (cardNum % 13);
        
        if(rank == 0) // If KING, rank as 13 (to make the code easy)
        {
            rank = 13;
        }
        
        this.suit    = (cardNum - 1) / 13;
    }
    

    public int getCardNum() { return cardNum; }
    public int getRank()    { return rank; }
    public int getSuit()    { return suit; }
    
    public boolean isAce() { return (rank == 1) ;}
    public boolean isKing(){ return (rank == 13);}
    
    public String displayInfo(){
        return(CARD_RANK[rank - 1] + " of " + CARD_SUIT[suit]);
    }
    
    @Override
    public String toString(){ return (CARD_RANK[rank - 1] + " of " + CARD_SUIT[suit]);}

    @Override
    public int compareTo(Object o) {
        Card c = (Card)o;
        return (c.getCardNum() - this.cardNum);
    }

//    @Override
//    public int compareTo(Card o) {
//        return (o.cardNum - this.cardNum);
//    }
}
