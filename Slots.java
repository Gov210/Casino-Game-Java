//import classes
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

//slots class with added listeners
public class Slots extends JPanel implements KeyListener,ActionListener,MouseListener {
    //add bank class and frame class
    private Bank bank;
    private JFrame frame;

    //create random object
    private Random rand = new Random();

    //images
    private Image slotsBar;
    private Image slotsbg;

    //create list for slot items
    private Image[] items;

    //variables
    //bet amt
    private int betAmount = 0;

    //string to store result mssg
    private String resultMessage = "";

    //button creation
    private JButton roll;
    private JButton back;
    private JButton restartBtn;

    //add image items
    private Image item1;
    private Image item2;
    private Image item3;

    //booleans
    private boolean gameOver = false;
    private boolean gameOverTrigger = false;


    //constructor class
    public Slots(JFrame frame, Bank bank){
        //create bank object and frame object
        this.frame = frame;
        this.bank = bank;
        //to render images smoothly
        setDoubleBuffered(true);

        //to set absolute positions
        setLayout(null);

        //upload images
        ImageIcon cherry = new ImageIcon(getClass().getResource("/casinoCherry.png"));
        ImageIcon grape = new ImageIcon(getClass().getResource("/casinoGrape.png"));
        ImageIcon lemon = new ImageIcon(getClass().getResource("/casinoLemon.png"));
        ImageIcon seven = new ImageIcon(getClass().getResource("/casino7.png"));
        ImageIcon gApple = new ImageIcon(getClass().getResource("/casinoGApple.png"));
        ImageIcon diamond = new ImageIcon(getClass().getResource("/casinoDiamond.png"));
        ImageIcon bell = new ImageIcon(getClass().getResource("/casinoBell.png"));
        ImageIcon horseshoe = new ImageIcon(getClass().getResource("/casinoHorseshoe.png"));
        ImageIcon clover = new ImageIcon(getClass().getResource("/casinoClover.png"));

        //bg
        slotsBar = new ImageIcon(getClass().getResource("/slotMachinebg.png")).getImage();
        slotsbg = new ImageIcon(getClass().getResource("/slotsbg.png")).getImage();


        //store images in items array
        items = new Image[]{
                cherry.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH),
                grape.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH),
                lemon.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH),
                seven.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH),
                gApple.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH),
                diamond.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH),
                bell.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH),
                horseshoe.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH),
                clover.getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH),

        };

        //initialize images
        item1 = items[rand.nextInt(items.length)];
        item2 = items[rand.nextInt(items.length)];
        item3 = items[rand.nextInt(items.length)];

        //buttons text
        roll = new JButton("Roll");
        roll.setBounds(580,380,70,50);
        back = new JButton("Back");
        back.setBounds(50,50,100,50);
        restartBtn = new JButton("Restart");
        restartBtn.setBounds(325,450,100,50);

        //button styles
        roll.setBackground(Color.decode("#c21807"));
        roll.setForeground(Color.black);
        back.setForeground(Color.black);
        back.setBackground(Color.decode("#DAA520"));
        back.setBorder(BorderFactory.createLineBorder(Color.decode("#8C000F"), 6));
        restartBtn.setForeground(Color.black);
        restartBtn.setVisible(false);

        //buttons add
        add(roll);
        add(back);
        add(restartBtn);

        //set background
        setBackground(Color.black);

        //add listeners
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);

        //btn action listeners
        roll.addActionListener(this);
        back.addActionListener(this);
        restartBtn.addActionListener(this);
        
    }

    //paint component method
    public void paintComponent(Graphics g){
        //clear old drawings
        super.paintComponent(g);
        //bg
        g.drawImage(slotsbg,-200,0,1200,800,this);

        //result
        //draw result message
        g.setColor(Color.decode("#efbf04"));
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.drawString(resultMessage, 250, 550);

        //display slot items
        g.setColor(Color.green);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        //container for items
        g.drawImage(slotsBar,190,300,380,200,this);
        g.drawImage(item1, 250,380,this);
        g.drawImage(item2, 350,380,this);
        g.drawImage(item3, 450,380,this);

        //gameOver Screen
        if(gameOver){
            g.setColor(Color.black);
            g.fillRect(0,0,800,800);
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD,30));
            g.drawString("Game Over!", 300,300);
            g.drawString("Your balance is $0", 250,350);

            restartBtn.setVisible(true);
        }

        //draw balance
        g.setColor(Color.black);
        g.fillRect(480,32,200,100);
        g.setColor(Color.green);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Balance: $" + bank.cash(), 500, 70);
        //bet
        g.drawString("Bet: $" + betAmount, 500,110);
    }


    //****** Necessary Methods *****************
    @Override
    public void actionPerformed(ActionEvent e) {
        //roll btn
        if(e.getSource()==roll){
            evaluateBet();
        }

        //back btn
        if(e.getSource() == back){
            //remove everything inside current window
            frame.getContentPane().removeAll();

            //show main menu
            frame.add(new Menu(frame,bank));

            //recalculate layout
            frame.revalidate();

            //redraw
            frame.repaint();

        }

        //restart btn
        if(e.getSource() == restartBtn){
            //remove everything inside current window
            frame.getContentPane().removeAll();

            //show main menu
            frame.add(new Menu(frame,bank));

            //recalculate layout
            frame.revalidate();

            //redraw
            frame.repaint();

            //reset balance
            bank.resetBalance();
        }

        //end game if balance = 0 after a 1 second timer: timer source: google gemini
        if(bank.cash() == 0 && !gameOverTrigger){
            //set game over trigger to true
            gameOverTrigger = true;

            //create timer
            //wait 1000ms and then trigger event
            Timer gameOverTimer = new Timer(1000,evt ->{
                gameOver = true;
                roll.setVisible(false);
                back.setVisible(false);
                repaint();
            });

            //make sure it only runs once
            gameOverTimer.setRepeats(false);
            gameOverTimer.start();
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    //******************************************

    //METHODS
    //print result
    public void jackPot(Image item1, Image item2, Image item3){
        //jackpot
        if(item1 == item2 && item1 == item3){
            //if triple 7, Jackpot, x7 multiplier. otherwise, x5
            if(item1 == items[3]){
                resultMessage = "Jackpot! 7x: + $"+betAmount*7;
                bank.addCash(betAmount * 7);
            } else{
                resultMessage = "Triple! 5x: + $"+betAmount*5;
                bank.addCash(betAmount * 5);
            }
            //3x
        } else if(item1 == item2 || item1 == item3 || item2 == item3){
            resultMessage = "Close! 3x: + $"+betAmount*3;
            bank.addCash(betAmount * 3);
            //otherwise, nothing
        } else {
            resultMessage = "You didn't win! - $" + betAmount;
        }
    }
    //ask for bet amt
    public void evaluateBet(){
        //ask user for bet amount on screen. Source: google gemini
        String input = JOptionPane.showInputDialog(frame, "Enter bet amount: ", "Bet",JOptionPane.PLAIN_MESSAGE);

        //continue if user types a number
        if(input != null){
            //try and catch statement for valid number
            try{
                //convert text user typed to integer
                betAmount = Integer.parseInt(input);

                //validate bet
                if(betAmount > bank.cash()){
                    JOptionPane.showMessageDialog(frame, "Not Enough Money");
                    betAmount = 0;
                    return;
                } else if(betAmount <= 0){
                    JOptionPane.showMessageDialog(frame, "Invalid Bet");
                    betAmount = 0;
                    return;
                }
                //take money from balance
                bank.removeCash(betAmount);


                //roll slots
                item1 = items[rand.nextInt(items.length)];
                item2 = items[rand.nextInt(items.length)];
                item3 = items[rand.nextInt(items.length)];

                //call jackpot method
                jackPot(item1,item2,item3);
            } catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(frame, "Please enter a valid number!");
                resultMessage = "";
            }
        }
    }

}
