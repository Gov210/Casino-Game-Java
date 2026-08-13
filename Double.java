//import classes
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

//double class with added listeners
public class Double extends JPanel implements KeyListener,ActionListener,MouseListener {
    //add bank and frame classes
    private Bank bank;
    private JFrame frame;

    //create random object
    private Random rand = new Random();

    //bg
    private Image doublebg;

    //bet
    private int betAmount = 0;

    //x-value for double amt button
    private int doubleAmtX = 39;

    //win or lose list
    private int[] winOrLose = {0,1};

    //buttons
    private JButton startBtn;
    private JButton back;
    private JButton restartBtn;
    private JButton doubleAmt;
    private JButton withdraw;

    //booleans
    private boolean gameOver = false;
    private boolean start = true;

    //constructor
    public Double(JFrame frame, Bank bank){

        //bg
        doublebg = new ImageIcon(getClass().getResource("/doublebg.jpg")).getImage();
        //create frame and bank objects
        this.bank = bank;
        this.frame = frame;
        //to render images smoothly
        setDoubleBuffered(true);

        //for absolute positioning
        setLayout(null);

        //background
        setBackground(Color.black);

        //button dimensions
        startBtn = new JButton("Start Round");
        startBtn.setBounds(290,350,200,50);
        doubleAmt = new JButton("Double?");
        doubleAmt.setBounds(doubleAmtX, 400,80,30);
        withdraw = new JButton("Withdraw");
        withdraw.setFont(new Font("Arial", Font.BOLD, 20));
        withdraw.setBounds(280,580,210,90);

        back = new JButton("Back");
        back.setBounds(50,50,100,50);
        restartBtn = new JButton("Restart");
        restartBtn.setBounds(325,450,100,50);


        //button styles
        doubleAmt.setForeground(Color.black);
        doubleAmt.setBackground(Color.decode("#efbf04"));
        withdraw.setBackground(Color.decode("#006400"));
        withdraw.setForeground(Color.white);
        startBtn.setBackground(Color.decode("#c21807"));

        back.setForeground(Color.black);
        back.setBackground(Color.decode("#DAA520"));
        back.setBorder(BorderFactory.createLineBorder(Color.decode("#8C000F"), 6));
        restartBtn.setForeground(Color.black);
        restartBtn.setVisible(false);

        startBtn.setVisible(false);
        doubleAmt.setVisible(false);
        withdraw.setVisible(false);

        //action listeners
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        requestFocusInWindow();

        //button listeners
        back.addActionListener(this);
        startBtn.addActionListener(this);
        restartBtn.addActionListener(this);
        doubleAmt.addActionListener(this);
        withdraw.addActionListener(this);

        //add to frame
        add(back);
        add(restartBtn);
        add(startBtn);
        add(doubleAmt);
        add(withdraw);
    }

    //paint component class
    public void paintComponent(Graphics g){
        //clear old drawings
        super.paintComponent(g);

        //bg
        g.drawImage(doublebg,0,0,800,800,this);


        //squares for double or nothing
        g.setColor(Color.white);
        for(int i = 30; i <= 660; i+=105){
            g.fillRect(i,300,95,95);
        }
        //text
        g.setFont(new Font("Arial", Font.BOLD,26));
        g.setColor(Color.black);

        //create variables for text value and x-distance
        int value = 2;
        int x = 67;

        for(int i = 0; i < 7; i++){
            g.drawString(value+"x",x,355);
            //double value each loop and increase x-distance by 80
            value *= 2;
            x +=100;
        }

        //gameOver Screen
        if(gameOver){
            g.setColor(Color.black);
            g.fillRect(0,0,800,800);
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD,30));
            g.drawString("Game Over!", 300,300);
            g.drawString("Your balance is $0.", 250,350);


            restartBtn.setVisible(true);
            withdraw.setVisible(false);
            doubleAmt.setVisible(false);
        }

        //start screen
        if(start){
            g.setColor(Color.black);
            g.fillRect(0,0,800,800);
            //make start button visible
            startBtn.setVisible(true);
            doubleAmt.setVisible(false);
            withdraw.setVisible(false);
            back.setVisible(true);

            //initialize double button
            doubleAmtX = 39;
            doubleAmt.setBounds(doubleAmtX, 400,80,30);
            betAmount = 0;
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
    //necessary methods*****************
    @Override
    public void actionPerformed(ActionEvent e) {
        //if start is pressed, start the game
        if(e.getSource() == startBtn){
            //start game
            startGame();
        }

        //if double button is pressed, increase its x-value to move it
        if(e.getSource() == doubleAmt){
            //double or nothing method
            doubleOrNothing();
            doubleAmtX += 105;
            //update button dimensions only if button is less than 765
            if(doubleAmtX < 765){
                doubleAmt.setBounds(doubleAmtX,400,80,30);
                //otherwise, if max reached, withdraw for user and print message
            } else {
                JOptionPane.showMessageDialog(frame, "Max Multiplier reached! You earned: $"+betAmount);
                bank.addCash(betAmount);
                start = true;
            }

        }

        //if withdraw button is pressed, add bet to balance and return to start screen
        if(e.getSource() == withdraw){
            JOptionPane.showMessageDialog(frame,"You withdrew: $"+betAmount);
            bank.addCash(betAmount);
            //reset bet amt
            betAmount = 0;
            start = true;
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
    //**********************************************

    //methods
    //start game: activated when user presses start btn
    public void startGame(){
        //ask user for bet
        String input = JOptionPane.showInputDialog(frame,"Enter bet amount: ", "Bet", JOptionPane.PLAIN_MESSAGE);

        //continue if user enters a number
        if(input != null){
            //try and catch for validating number
            try{
                //convert input to int
                betAmount = Integer.parseInt(input);

                //validate bet
                if(betAmount > bank.cash()){
                    //show message and exit function
                    JOptionPane.showMessageDialog(frame,"Not enough money");
                    betAmount = 0;
                    return;
                } else if(betAmount <= 0){
                    JOptionPane.showMessageDialog(frame, "Invalid Bet");
                    betAmount = 0;
                    return;
                }

                //take money from user account
                bank.removeCash(betAmount);

                //remove start screen and button
                start = false;
                startBtn.setVisible(false);

                //make back button invisible
                back.setVisible(false);

                //make double and withdraw button visible
                doubleAmt.setVisible(true);
                withdraw.setVisible(true);

                //exception for if user doesnt enter a number
            } catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(frame, "Please enter a valid number!");
            }
        }
    }

    //double or nothing method that either doubles bet or user gets
    public void doubleOrNothing(){
        //50/50 odds of win/lose
        //collect either a 1 or 0 from win/lose list
        int randomIndex = winOrLose[rand.nextInt(winOrLose.length)];

        //if 0, user wins, money is doubled
        if(randomIndex == 0){
            betAmount *=2;
            JOptionPane.showMessageDialog(frame, "Double! Your bet is now: $" + betAmount,"Double or nothing",JOptionPane.PLAIN_MESSAGE);
            //otherwise, money is lost and user returns to start screen
        } else {
            JOptionPane.showMessageDialog(frame, "Nothing! You lost: $" + betAmount,"Double or nothing",JOptionPane.PLAIN_MESSAGE);

            //end game if balance = 0
            if(bank.cash() == 0){
                gameOver = true;
                back.setVisible(false);
                return;
            }
            //reset game
            start = true;
        }
    }

}
