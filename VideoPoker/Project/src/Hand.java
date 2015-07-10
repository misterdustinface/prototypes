
import java.util.Arrays;


/**
 *
 * @author Dustin
 */
public class Hand 
{
    public final int length;
    
    private Card[]    cards;
    
    private boolean[] discard;
    
    public Hand(int numberOfCardsInFullHand)
    {
        length = numberOfCardsInFullHand;
        
        discard = new boolean[numberOfCardsInFullHand];
        initDiscard();
        
        cards = new Card[numberOfCardsInFullHand];
    }
    
    public Card getCard(int index)
    {
        return cards[index];
    }
    
    public void setCard(int index, int number)
    {
        cards[index] = new Card(number);
    }
    
    public void initDiscard()
    {
        for(int i = 0; i < length; i++)
        {
            discard[i] = false;
        }
    }
    
    public void toggleDiscard(int i)
    {
        if(discard[i])
        {
            discard[i] = false;
        }
        else
        {
            discard[i] = true;
        }
    }
    
    public void hold(int index)
    {
        discard[index] = false;
    }
    
    public boolean shouldDiscardCard(int index)
    {
        return discard[index];
    }
    
    public void sort()
    {
        Arrays.sort(cards);
    }
}
