
// toggles - allows for discard

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author dshaffer4
 */
public class DiscardButton extends JButton{
    
    private  boolean holding;
    
    private  boolean active;
   
    private static final Color HOLDING        = new Color(0, 100, 255); // 0,120,255
    private static final Color DISCARDING     = Color.RED;
    private static final Color INACTIVE       = Color.BLACK;
   
    private static final Color BUTTON_HIGHLIGHT_COLOR = new Color(225, 225, 0); // Color.YELLOW
    private static final Color BUTTON_NORMAL_COLOR    = new Color(255, 200, 0); // Color.ORANGE
    
    
    public DiscardButton()
    {
        setToHolding();
        this.setBackground(BUTTON_NORMAL_COLOR);
        active = true;
    }
    
    // GETTERS
    
    public  boolean getState()
    {
        return holding;
    }
    
    public  boolean shouldWeDiscard()
    {
        return !holding;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    
    //////////////////////////
    // FOR ACTION LISTENERS //
    //////////////////////////
    
    public boolean isActive()
    {
        return active;
    }
    
    public void toggleButton()
    {
        if(holding)
        {
            setToDiscarding();
        }
        else
        {
            setToHolding();
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    
    //////////////////////////
    // FOR CHANGES OF STATE //
    //////////////////////////
    
    public void setAsCurrentPriority()
    {
         this.setBackground(BUTTON_HIGHLIGHT_COLOR);
    }
    

    
    public void incapacitate()
    {
        if(holding)
        {
            this.setText("- HELD -");
        }
        else
        {
            this.setText("~ DREW ~");
        }
        this.setForeground(INACTIVE);
        this.setBackground(BUTTON_NORMAL_COLOR);
        
        active = false;
    }
    
    public void resetButton()
    {
        setToHolding();

        active = true;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    
    public void setToHolding()
    {
        holding = true;
        this.setText("HOLDING");
        this.setForeground(HOLDING);
    }
    
    private void setToDiscarding()
    {
        holding = false;
        this.setText("DISCARDING");
        this.setForeground(DISCARDING);
    }
    
    @Override
    public void addActionListener(ActionListener l) {
        super.addActionListener(l);
    }
}
