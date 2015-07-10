
import java.awt.Color;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 *
 * @author Dustin
 */
public class VideoPoker5 extends JApplet
{
    private static States currentState;
    
    private static Controller controller;
    private static View       view;

    @Override
    public void init()
    {        
        currentState = new States(States.gameStates.WAGER);
        view         = new View(this, currentState);
        controller   = new Controller(view, currentState);
    }
    
    @Override
    public void start()
    {
        controller.start();
    }
    
 
    ///////////////////////////////////////////////////////////////////////////
    
    // RUNNING AS AN EXE
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Video Poker");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setBounds(120, 20, 540, 400);
        frame.setResizable(false);
        frame.setVisible(true);
        
        
        currentState = new States(States.gameStates.WAGER);
        view         = new View(frame, currentState);
        controller   = new Controller(view, currentState);
        
        System.out.println("============\n"
                         + "GAME STARTED\n"
                         + "============");
        
        controller.start();
    }

}
