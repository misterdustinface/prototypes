
// Allows for wagering of cats

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author dshaffer4
 */
public class WagerButton extends JButton{
    
    public final static int MAX_WAGER = 5;
    public final static int MIN_WAGER = 1;
    
    private static String wagerString;
    
    private static boolean active;
    
    private static int wagerAmount;
    
    private static final Color ACTIVE         = Color.RED;
    private static final Color INACTIVE       = Color.BLACK;
   
    private static final Color BUTTON_HIGHLIGHT_COLOR = new Color(225, 225, 0); // Color.YELLOW
    private static final Color BUTTON_NORMAL_COLOR    = new Color(255, 200, 0); // Color.ORANGE
    
    public WagerButton()
    {
        wagerAmount = MIN_WAGER;
        wagerString = "Wager: " + Integer.toString(wagerAmount);
        
        this.setText(wagerString);
        
        active = true;
    }
    
    // GETTERS
    
    public static int getWagerAmount()
    {
        return wagerAmount;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    
    //////////////////////////
    // FOR ACTION LISTENERS //
    //////////////////////////
    
    public void pressButton()
    {
        if(active)
        {
            wagerAmount++;

            if(wagerAmount > MAX_WAGER)
            {
                wagerAmount = MIN_WAGER;
            }

            wagerString = "Wager: " + Integer.toString(wagerAmount);

            this.setText(wagerString);
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    
    //////////////////////////
    // FOR CHANGES OF STATE //
    //////////////////////////
    
    public void setAsCurrentPriority()
    {
        this.setForeground(ACTIVE);
        this.setBackground(BUTTON_HIGHLIGHT_COLOR);
        
        active = true;
    }
    
    public void incapacitate()
    {
        this.setForeground(INACTIVE);
        this.setBackground(BUTTON_NORMAL_COLOR);
        
        active = false;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    
    @Override
    public void addActionListener(ActionListener l) {
        super.addActionListener(l);
    }
}
