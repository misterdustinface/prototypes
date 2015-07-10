
/**
 *
 * @author Dustin
 */
public class States {
    
    public  static enum gameStates {WAGER, PLAY, CONTINUE, NONE};
    
    private static gameStates state;
    
    public States()
    {
        state = gameStates.NONE;
    }
    
    public States(gameStates s)
    {
        state = s;
    }
    
    public gameStates getCurrentState()
    {
        return state;
    }
    
    public void setState(gameStates s)
    {
        state = s;
    }
}
