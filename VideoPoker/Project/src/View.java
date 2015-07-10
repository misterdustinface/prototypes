
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author dshaffer4
 */
public class View 
{
    //private static EpicPanel epicPanel;
    private static JApplet appReference; // Useable by both japplet and jpanel
    private static JFrame  frameReference;
    
    private static int screenWidth;
    private static int screenHeight;
    
    private static States currentState;
    
    ///////////////////////////////////////////////////////////////////////////
    
    private static long actionStartTime;
    private static long accumulatedEventTime;
    
    private static int NUMBER_OF_CARDS_IN_FULL_HAND = 5;
    
    private static int CARD_DISPLAY_WIDTH  = 80;  //  80
    private static int CARD_DISPLAY_HEIGHT = 120; // 120
    
    private static final String   PAYOFF_CHART_URL = "http://cim.saddleback.edu/~dshaffer4/payoutTable.jpg";
    private static final String   COOKIE_IMAGE_URL = "http://cim.saddleback.edu/~dshaffer4/cookie.gif";
    private static final String   CARD_BACKING_URL = "http://cim.saddleback.edu/casino/card/b2fv.png"; //b1/b2
    
    private static final Color HIGHLIGHT_COLOR = new Color(225, 225, 0); // Color.YELLOW
    private static final Color NORMAL_COLOR    = new Color(255, 200, 0); // Color.ORANGE
    
    private static final Color HIGHLIGHT_TEXT = Color.RED;  // Color.RED
    private static final Color NORMAL_TEXT    = Color.BLACK; // Color.BLUE
    
    private static final Color HOLDING        = new Color(0, 100, 255); // 0,120,255
    private static final Color DISCARDING     = Color.RED;
    
    private static final Color BACKGROUND     = new Color(0, 30, 220);
    
    
    ////////////////////////////////////////////////////////////////////////////
    /******************
     *   BASIC DECK   * 
     ******************/
    private static ImageIcon cardBacking;    // The card backing image
    
    private static ImageIcon[] cardImages;   // The loaded set of ALL card Images
    
    private static ImageIcon cookieImage;    // The cookie image
    
    private static ImageIcon payoffChart;    // The loaded payoffChart image
    
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    /******************
     *     DISPLAY    *
     ******************/    
    private static JPanel   mainPanel;
    private static JPanel   payoffPanel;
    private static JPanel   gamePanel;
    private static JPanel   basePanel;
    
    private static JPanel   outcomePanel;
    private static JLabel   currentHandOutcome;
    
    private static JPanel   winningsPanel;
    private static JLabel   currentWinnings;
    
    // NEW SETUP
    private static WagerButton     wagerButton;
    private static ProgressButton  progressButton;
    private static DiscardButton[] discardButtons;
    // END NEW
    
    private static JPanel[] handDisplay;
    
    private static JLabel[] handLabels;
    
    ////////////////////////////////////////////////////////////////////////////
    
    public View(JFrame f, States s)
    {
        frameReference = f;
        screenWidth  = f.getWidth();
        screenHeight = f.getHeight();
        
        currentState = s;
        
        appReference = null;
        
        init();
    }
    
    public View(JApplet app, States s)
    {
        //epicPanel = new EpicPanel();
        
        appReference = app;
        screenWidth  = app.getWidth();
        screenHeight = app.getHeight();
        
        currentState = s;
        
        frameReference = null;
        
        init();
    }
    
    private static void init(){
        
        accumulatedEventTime = 0;
        
        actionStartTime = System.nanoTime();
        System.out.println("Loading Card Images...");
        loadCardImages();
        System.out.println( (System.nanoTime() - actionStartTime) + " NANOS" );
        accumulatedEventTime += (System.nanoTime() - actionStartTime);
        
        actionStartTime = System.nanoTime();
        System.out.println("Loading Payout Chart Image...");
        loadPayoffChartImage();
        System.out.println( (System.nanoTime() - actionStartTime) + " NANOS" );
        accumulatedEventTime += (System.nanoTime() - actionStartTime);
        
        actionStartTime = System.nanoTime();
        System.out.println("Loading Cookie Image...");
        loadCookieImage();
        System.out.println( (System.nanoTime() - actionStartTime) + " NANOS" );
        accumulatedEventTime += (System.nanoTime() - actionStartTime);
        
        actionStartTime = System.nanoTime();
        System.out.println("Initializing Main Display...");
        initDisplay();
        System.out.println( (System.nanoTime() - actionStartTime) + " NANOS" );
        accumulatedEventTime += (System.nanoTime() - actionStartTime);
        
        actionStartTime = System.nanoTime();
        System.out.println("Initializing Hand Display...");
        setHandDisplay();
        System.out.println( (System.nanoTime() - actionStartTime) + " NANOS" );
        accumulatedEventTime += (System.nanoTime() - actionStartTime);
        
        System.out.println("Finished Initialization!");
        System.out.println(accumulatedEventTime + " TOTAL NANOS TO LOAD");
        System.out.println(accumulatedEventTime/1000000 + " TOTAL MILLIS TO LOAD");
        System.out.println(accumulatedEventTime/1000000000 + " TOTAL SEC TO LOAD");
        System.out.println("::::::::::::::::::::::::");
        
    }
    
     private static void loadPayoffChartImage(){
        try {
            payoffChart = new ImageIcon(new URL(PAYOFF_CHART_URL));
        } catch (MalformedURLException ex) {
            Logger.getLogger(VideoPoker5.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void loadCardImages(){
        cardImages = new ImageIcon[52];
        // INITIALIZES CARD IMAGES
        for(int cardCounter = 0; cardCounter < 52; cardCounter++)
        {
            try {
                cardImages[cardCounter] = new ImageIcon(new URL("http://cim.saddleback.edu/casino/card/" + (cardCounter + 1) + ".png"));
            } catch (MalformedURLException ex) {
                System.out.println(" FAILED TO LOAD IMAGES FROM URL AT IMAGE NUMBER: " + cardCounter);
                Logger.getLogger(VideoPoker5.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            // LOADS THE CARD BACKING IMAGE
            cardBacking = new ImageIcon(new URL(CARD_BACKING_URL));
        } catch (MalformedURLException ex) {
            Logger.getLogger(VideoPoker5.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void loadCookieImage()
    {
        try {
            cookieImage = new ImageIcon(new URL(COOKIE_IMAGE_URL));
        } catch (MalformedURLException ex) {
            Logger.getLogger(VideoPoker5.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void initDisplay()
    {
        
        //frame = new JFrame("Video Poker");
        //frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        // set frame component orientation
        //frame.setBounds(120, 20, 540, 400);
        //frame.setResizable(false);
        //frame.setVisible(true);
        
        mainPanel = new JPanel();
        mainPanel.setSize(screenWidth,screenHeight);
        mainPanel.setBackground(Color.BLUE);
        
        if(frameReference == null)
        {
            appReference.add(mainPanel);
        }
        else
        {
            frameReference.add(mainPanel);
        }
        
 
        // TOP
        //////////////////////////////////////////////////////////////////////
        
        payoffPanel = new JPanel();
        payoffPanel.setBackground(BACKGROUND);
        
        payoffPanel.add(new JLabel(payoffChart));

        // CENTER
        //////////////////////////////////////////////////////////////////////
        
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout());
        gamePanel.setBackground(BACKGROUND);
        
        // BASE
        //////////////////////////////////////////////////////////////////////
        
        basePanel = new JPanel();
        basePanel.setLayout(new GridLayout());
        basePanel.setBackground(BACKGROUND);
        
        
        progressButton = new ProgressButton(currentState);
        
        
        //////////////////////////////////////////////////////////////////////

        currentHandOutcome = new JLabel();
        
        outcomePanel = new JPanel();
        outcomePanel.setLayout(new BorderLayout());
        outcomePanel.setBackground(NORMAL_COLOR);
        outcomePanel.setForeground(NORMAL_TEXT);
        
        outcomePanel.add(currentHandOutcome, BorderLayout.CENTER); // WHY WON'T IT CENTER?!?!? 

        
        currentWinnings = new JLabel();
        
        updateCurrentWinnings(0);
        winningsPanel = new JPanel();
        winningsPanel.setBackground(NORMAL_COLOR);
        winningsPanel.setForeground(NORMAL_TEXT);
        
        winningsPanel.add(currentWinnings);
        winningsPanel.add(new JLabel(cookieImage));
        
        //////////////////////////////////////////////////////////////////////
        
        wagerButton    = new WagerButton();
        
        
        JPanel b1 = new JPanel();
        b1.setLayout(new GridLayout());
        
        b1.add(wagerButton);
        b1.add(winningsPanel);
        
        basePanel.add(outcomePanel);
        basePanel.add(progressButton);
        basePanel.add(b1);

        //////////////////////////////////////////////////////////////////////
        
        // Add all to mainPanel
        
        mainPanel.setLayout(new BorderLayout());
        
        mainPanel.add(payoffPanel, BorderLayout.NORTH);
        mainPanel.add(gamePanel,   BorderLayout.CENTER);
        mainPanel.add(basePanel,   BorderLayout.SOUTH);

    }
    
    private static void setHandDisplay(){
        
        Card[] tempHand;
        
        // Make pre-hand to init and test
        tempHand = new Card[NUMBER_OF_CARDS_IN_FULL_HAND];
        for(int i = 0; i < tempHand.length; i++)
        {
            tempHand[i] = new Card(1);
        }
        // End fake hand
        
        handDisplay = new JPanel[NUMBER_OF_CARDS_IN_FULL_HAND]; 
        handLabels  = new JLabel[NUMBER_OF_CARDS_IN_FULL_HAND];
        
        discardButtons = new DiscardButton[NUMBER_OF_CARDS_IN_FULL_HAND];
        
        for(int i = 0; i < NUMBER_OF_CARDS_IN_FULL_HAND; i++){
            handDisplay[i] = new JPanel();
            handDisplay[i].setSize(CARD_DISPLAY_WIDTH, CARD_DISPLAY_HEIGHT);

            handDisplay[i].setBackground(BACKGROUND);

            handLabels[i] = new JLabel(cardImages[tempHand[i].getCardNum() - 1]);
            handDisplay[i].add(handLabels[i]);
            
            discardButtons[i] = new DiscardButton();
            
            handDisplay[i].add(discardButtons[i]);
        }
        
        gamePanel.repaint();
    }
    
    
    //////////////////////////////////////////////////////////////////////////
    //                                                                      //
    //           END INITIALIZATION - START TRUE UPDATABLE DISPLAYS         //
    //                                                                      //
    //////////////////////////////////////////////////////////////////////////
    
    public static void displayHand(Hand hand)
    {
        gamePanel.removeAll();        
        
        for(int i = 0; i< handDisplay.length; i++)
        {
            //gamePanel.remove(handDisplay[i]);
            
            //handDisplay[i].remove(handLabels[i]);
            
            handDisplay[i].removeAll();
            
            
            handDisplay[i] = new JPanel();
            handDisplay[i].setLayout(new BorderLayout());
            handDisplay[i].setBackground(BACKGROUND);
            
            handLabels[i] = new JLabel(cardImages[hand.getCard(i).getCardNum() - 1]);
            handDisplay[i].add(handLabels[i], BorderLayout.NORTH);
            
            //discardButtons[i].resetButton();
            
            // button
            handDisplay[i].add(discardButtons[i], BorderLayout.SOUTH);
            
        }
        
        gamePanel.add(handDisplay[0]);
        gamePanel.add(handDisplay[1]);
        gamePanel.add(handDisplay[2]);
        gamePanel.add(handDisplay[3]);
        gamePanel.add(handDisplay[4]);
    }
    
    public static void displayCardBackings()
    {
        gamePanel.removeAll();        
        
        for(int i = 0; i< handDisplay.length; i++)
        {
            handDisplay[i].removeAll();
            
            
            handDisplay[i] = new JPanel();
            handDisplay[i].setLayout(new BorderLayout());
            handDisplay[i].setBackground(BACKGROUND);
            

            handDisplay[i].add(new JLabel(cardBacking), BorderLayout.NORTH);
        }
        
        gamePanel.add(handDisplay[0]);
        gamePanel.add(handDisplay[1]);
        gamePanel.add(handDisplay[2]);
        gamePanel.add(handDisplay[3]);
        gamePanel.add(handDisplay[4]);
    }
    
    public static void updateCurrentWinnings(int score)
    {
        currentWinnings.setText(Integer.toString(score));
//        currentWinnings.setText(Integer.toString(score) + " Cookies!");
    }
    
    public static void updateCurrentHandOutcome(String result, int payout)
    { 
       currentHandOutcome.setText ( result + 
                             " [+ " + Integer.toString(payout) + "]");
    }
    public static void updateCurrentHandOutcome(String current)
    {
        //updateScore();
        currentHandOutcome.setText(current);
    }
    
    //////////////////////////////////////////////////////////////////////////
    //                                                                      //
    //                          CONTROL SECTION                             //
    //                                                                      //
    //////////////////////////////////////////////////////////////////////////

    public static void clearGamePanel()
    {
        gamePanel.removeAll();
        gamePanel.repaint();
    }
    
    public static void repaint()
    {
        gamePanel.repaint();
    }
    
//    public void incapacitateWagerButton()
//    {
//        wagerButton.incapacitate();
//    }
    
    
    public static void setWagerStateDisplay()
    {   
        progressButton.resetButton();
        
        wagerButton.setAsCurrentPriority();
    }
    
    public static void setPlayStateDisplay()
    {
        wagerButton.incapacitate();
        
        for(int i = 0; i < discardButtons.length; i++)
        {
            discardButtons[i].resetButton();
            discardButtons[i].setAsCurrentPriority();
        }
    }
    
    public static void setContinueStateDisplay()
    {
        for(int i = 0; i < discardButtons.length; i++)
        {
            discardButtons[i].incapacitate();
        }
    }
    
    public static int getWagerAmount()
    {
        return wagerButton.getWagerAmount();
    }
    
    public static States progressButtonState()
    {
        return progressButton.getState();
    }
    
    ////////////////////////////////////////////////////////////////
    // 
    //     FOR BUTTON AND ACTIONLISTENER ACCESS
    
    public static ProgressButton getProgressButton()
    {
        return progressButton;
    }
    
    public static WagerButton getWagerButton()
    {
        return wagerButton;
    }
    
    public static DiscardButton getDiscardButton(int i)
    {
        return discardButtons[i];
    }
}
