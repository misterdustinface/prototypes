
// Allows for game progression
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author dshaffer4
 */
public class ProgressButton extends JButton{
    
    private static ActionListener listener;
    
    private static States currentState;
   
    private static final Color HIGHLIGHTED       = Color.RED;
    private static final Color NORMAL_TEXT       = Color.BLACK;
   
    private static final Color BUTTON_HIGHLIGHT_COLOR = new Color(225, 225, 0); // Color.YELLOW
    private static final Color BUTTON_NORMAL_COLOR    = new Color(255, 200, 0); // Color.ORANGE
    
    
    
    
    public ProgressButton(States s)
    {
        currentState = s;
        setStartHand();
        this.setForeground(NORMAL_TEXT);
        this.setBackground(BUTTON_NORMAL_COLOR);
    }
    
    // GETTERS
    
    public States getState()
    {
        return currentState;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    
    //////////////////////////
    // FOR ACTION LISTENERS //
    //////////////////////////
    
//    public void  pressButton()
//    {
//        // CAUSE THE NEXT STEP!
//        switch(currentState.getCurrentState())
//        {
//            case WAGER:    setPlayHand();
//                break;
//            case PLAY:     setContinue();
//                break;
//            case CONTINUE: setStartHand();
//                break;
//        }
//    }
    
    ///////////////////////////////////////////////////////////////////////////
    
    //////////////////////////
    // FOR CHANGES OF STATE //
    //////////////////////////
    
    public void setAsCurrentPriority()
    {
         this.setForeground(HIGHLIGHTED);
         this.setBackground(BUTTON_HIGHLIGHT_COLOR);
    }
    
    
    public void resetButton()
    {
        setStartHand();
        this.setForeground(NORMAL_TEXT);
        this.setBackground(BUTTON_NORMAL_COLOR);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    
    public void setStartHand()
    {
        currentState.setState(States.gameStates.WAGER);
        this.setText("DEAL HAND");
        this.setForeground(NORMAL_TEXT);
        this.setBackground(BUTTON_NORMAL_COLOR);
    }
    
    public void setPlayHand()
    {
        currentState.setState(States.gameStates.PLAY);
        this.setText("PLAY HAND");
    }
    
    public void setContinue()
    {
        currentState.setState(States.gameStates.CONTINUE);
        this.setText("CONTINUE");
        
        setAsCurrentPriority();
    }

    @Override
    public void addActionListener(ActionListener l) {
        super.addActionListener(l);
        
        listener = l;
    }
    

}