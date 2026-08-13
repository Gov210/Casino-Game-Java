//import classes
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;

//blackjack class
public class BlackJack extends JPanel implements KeyListener,ActionListener,MouseListener{
    //random object
    private Random rand = new Random();

    //images
    private Image blackJackBg;

    //classes
    private Bank bank;
    private JFrame frame;

    //list of card images
    private static ImageIcon[] cardsI;

    //hand variables
    private int playerHand = 0;
    private int dealerHand = 0;

    //card list variables to store cards drawn
    private ArrayList <ImageIcon> playerCards = new ArrayList<>();
    private ArrayList <ImageIcon> dealerCards = new ArrayList<>();

    //winner text
    private String winnerText;

    //bet
    private int betAmount = 0;

    //buttons
    private JButton startBtn;
    private JButton back;
    private JButton restartBtn;
    private JButton hitBtn;
    private JButton standBtn;
    private JButton doubleBtn;
    private JButton playAgain;
    private JButton rulesBtn;

    //booleans
    private boolean gameOver = false;
    private boolean win = false;
    private boolean rules = false;
    private boolean gameOverTrigger = false;


    //constructor
    public BlackJack(JFrame frame, Bank bank){
        //initialize layout
        setLayout(null);
        setDoubleBuffered(true);

        //class objects
        this.bank = bank;
        this.frame = frame;

        //background color
        setBackground(Color.black);

        //load background image
        blackJackBg = new ImageIcon(getClass().getResource("/blackjackbackground.png")).getImage();

        //load images only if blackjack is loaded. this is for faster resolution: google gemini
        //Card images source: https://www.kenney.nl/assets/playing-cards-pack
        if(cardsI == null){
            ImageIcon casinoA = new ImageIcon(getClass().getResource("/card_diamonds_A.png"));
            ImageIcon casino2 = new ImageIcon(getClass().getResource("/card_diamonds_02.png"));
            ImageIcon casino3 = new ImageIcon(getClass().getResource("/card_diamonds_03.png"));
            ImageIcon casino4 = new ImageIcon(getClass().getResource("/card_diamonds_04.png"));
            ImageIcon casino5 = new ImageIcon(getClass().getResource("/card_diamonds_05.png"));
            ImageIcon casino6 = new ImageIcon(getClass().getResource("/card_diamonds_06.png"));
            ImageIcon casinocard7 = new ImageIcon(getClass().getResource("/card_diamonds_07.png"));
            ImageIcon casino8 = new ImageIcon(getClass().getResource("/card_diamonds_08.png"));
            ImageIcon casino9 = new ImageIcon(getClass().getResource("/card_diamonds_09.png"));
            ImageIcon casino10 = new ImageIcon(getClass().getResource("/card_diamonds_10.png"));
            ImageIcon casinoJ = new ImageIcon(getClass().getResource("/card_diamonds_J.png"));
            ImageIcon casinoQ = new ImageIcon(getClass().getResource("/card_diamonds_Q.png"));
            ImageIcon casinoK = new ImageIcon(getClass().getResource("/card_diamonds_K.png"));

            //add images to card list
            cardsI = new ImageIcon[]{
                    new ImageIcon(casinoA.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH)),
                    new ImageIcon(casino2.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH)),
                    new ImageIcon(casino3.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH)),
                    new ImageIcon(casino4.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH)),
                    new ImageIcon(casino5.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH)),
                    new ImageIcon(casino6.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH)),
                    new ImageIcon(casinocard7.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH)),
                    new ImageIcon(casino8.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH)),
                    new ImageIcon(casino9.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH)),
                    new ImageIcon(casino10.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH)),
                    new ImageIcon(casinoJ.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH)),
                    new ImageIcon(casinoQ.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH)),
                    new ImageIcon(casinoK.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH)),
            };
        }


        //button dimensions
        startBtn = new JButton("Start Round");
        startBtn.setBounds(290,350,200,50);

        rulesBtn = new JButton("Rules");
        rulesBtn.setBounds(600,700,100,50);

        back = new JButton("Back");
        back.setBounds(50,50,100,50);
        restartBtn = new JButton("Restart");
        restartBtn.setBounds(325,450,100,50);

        hitBtn = new JButton("Hit");
        hitBtn.setBounds(180,540,80,50);
        standBtn = new JButton("Stand");
        standBtn.setBounds(330,540,100,50);
        doubleBtn = new JButton("Double");
        doubleBtn.setBounds(490,540,100,50);

        playAgain = new JButton("Play Again?");
        playAgain.setBounds(325,325,100,50);

        //button styles
        hitBtn.setBackground(Color.decode("#efbf04"));
        standBtn.setBackground(Color.decode("#efbf04"));
        doubleBtn.setBackground(Color.decode("#efbf04"));
        startBtn.setBackground(Color.decode("#efbf04"));

        hitBtn.setForeground(Color.black);
        standBtn.setForeground(Color.black);
        doubleBtn.setForeground(Color.black);

        playAgain.setForeground(Color.black);

        back.setForeground(Color.black);
        back.setBackground(Color.decode("#DAA520"));
        back.setBorder(BorderFactory.createLineBorder(Color.decode("#8C000F"), 6));
        restartBtn.setForeground(Color.black);
        restartBtn.setVisible(false);
        hitBtn.setVisible(false);
        standBtn.setVisible(false);
        doubleBtn.setVisible(false);
        playAgain.setVisible(false);


        //add to frame
        add(startBtn);
        add(back);
        add(restartBtn);
        add(rulesBtn);

        add(hitBtn);
        add(standBtn);
        add(doubleBtn);

        add(playAgain);

        //add action listeners
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
        requestFocusInWindow();


        //button listeners
        startBtn.addActionListener(this);
        back.addActionListener(this);
        restartBtn.addActionListener(this);
        rulesBtn.addActionListener(this);

        hitBtn.addActionListener(this);
        standBtn.addActionListener(this);
        doubleBtn.addActionListener(this);

        playAgain.addActionListener(this);
    }

    //paint component class
    public void paintComponent(Graphics g){
        //clear old drawings
        super.paintComponent(g);

        //display bg
        g.drawImage(blackJackBg,0,0,800,800, this);

        //win screen
        if(win){
            g.setColor(Color.black);
            g.fillRect(0,0,800,800);
            g.setColor(Color.white);
            //display winner
            g.setFont(new Font("Arial", Font.BOLD,30));
            g.drawString(winnerText, 250,300);

            //button visibility
            //play again will only be visible if balance is greater than zero
            if(bank.cash() > 0){
                playAgain.setVisible(true);
            }

            hitBtn.setVisible(false);
            standBtn.setVisible(false);
            doubleBtn.setVisible(false);
        }

        //player and dealer hands
        //player
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Player: "+playerHand, 300,650);

        //dealer
        g.drawString("Dealer: "+dealerHand, 300, 100);

        //draw player and dealer cards on screen
        int playerX = 100;

        //iterate through every card in players card list and draw on screen side by side
        for (ImageIcon card : playerCards) {
            g.drawImage(card.getImage(), playerX, 425, 100, 100, this);
            //separation distance for each card display
            playerX += 85;
        }

        //dealer
        int dealerX = 100;

        for (ImageIcon card : dealerCards) {

            g.drawImage(card.getImage(), dealerX, 150, 100, 100, this);
            //separation distance for each card display
            dealerX += 85;
        }

        //rules screen
        if(rules){
            g.setColor(Color.black);
            g.fillRect(0,0,800,800);

            //rules text
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD,30));
            g.drawString("Rules", 350,50);

            startBtn.setVisible(false);
            rulesBtn.setVisible(false);

            //rules
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            String[] rulesText = {
                    "Objective:",
                    "   Beat the dealer by having a hand value closer to 21 than the dealer.",
                    "",
                    "Initial Deal:",
                    "   Each player receives 2 cards.",
                    "",
                    "Blackjack:",
                    "   An Ace and a 10-value card as the first two cards.",
                    "",
                    "Payout:",
                    "   - Blackjack pays 3x.",
                    "   - If both have Blackjack, it is a tie.",
                    "   - Normal win pays 2x",
                    "",
                    "Player Turn: ",
                    "   - Hit - Take one additional card.",
                    "   - Stand - Take no more cards.",
                    "",
                    "Double Down: ",
                    "   - Double your original bet.",
                    "   - Receive exactly one more card.",
                    "   - Turn ends immediately.",
                    "",
                    "Dealer Turn:",
                    "   - Hit on 16 or less.",
                    "   - Stand on 17 or more."
            };

            int y = 150;
            for(String line : rulesText){
                g.drawString(line, 30, y);
                y += 20;
            }
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
            playAgain.setVisible(false);
            rulesBtn.setVisible(false);
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

    //listener methods**********************
    @Override
    public void actionPerformed(ActionEvent e) {
        //when start clicked, start game and evaluate winner
        if(e.getSource() == startBtn){
            startGame();
            evaluateWinOnPlay();
        }

        //when hit is pressed, use hit method and evaluate winner
        if(e.getSource() == hitBtn){
            hitMethod();
        }

        //when stand is pressed, use stand method.
        if(e.getSource() == standBtn){
            standMethod();
        }

        //double button
        if(e.getSource() == doubleBtn){
            doubleMethod();
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

        //activate rules when rules btn is clicked
        if(e.getSource() == rulesBtn){
            rules=true;
        }

        //if user chooses to play again, reset blackjack
        if(e.getSource() == playAgain && win){
            resetBlackJack();
        }

        //end game if balance = 0 after a 1 second timer: timer source: google gemini
        if(bank.cash() == 0 && !gameOverTrigger){
            //set game over trigger to true
            gameOverTrigger = true;
            playAgain.setVisible(false);
            startBtn.setVisible(false);

            //create timer
            //wait 1000ms and then trigger event
            Timer gameOverTimer = new Timer(1000,evt ->{
                gameOver = true;
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
    //**************************************

    //methods
    //random card generator
    public ImageIcon randomCard(){
        ImageIcon card = cardsI[rand.nextInt(cardsI.length)];
        return card;
    }

    //assign values to cards and add to hand
    public int handValue(ImageIcon card, int currentHand){
        //card value system
        if(card == cardsI[0]){ // Ace
            return aceValue(currentHand);
        }
        else if(card == cardsI[1]){ // 2
            return 2;
        }
        else if(card == cardsI[2]){ // 3
            return 3;
        }
        else if(card == cardsI[3]){ // 4
            return 4;
        }
        else if(card == cardsI[4]){ // 5
            return 5;
        }
        else if(card == cardsI[5]){ // 6
            return 6;
        }
        else if(card == cardsI[6]){ // 7
            return 7;
        }
        else if(card == cardsI[7]){ // 8
            return 8;
        }
        else if(card == cardsI[8]){ // 9
            return 9;
        }
        else { // 10, J, Q, K
            return 10;
        }
    }

    //add ace logic
    public int aceValue(int currentHand){
        //if value of currenthand(player or dealer +11 <= 21, then return 11 for ace. otherwise ace = 1;
        if(currentHand + 11 <= 21){
            return 11;
        }
        return 1;
    }

    //start game method
    public void startGame(){
        //ask user for bet amount on screen.
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

                //make start btn invisible
                startBtn.setVisible(false);
                //store player cards and add to player hand
                ImageIcon card = randomCard();
                playerCards.add(card);
                //show player card: source: google gemini for how to display image
                JOptionPane.showMessageDialog(frame,"", "Card display", JOptionPane.PLAIN_MESSAGE, card);
                playerHand += handValue(card, playerHand);

                //store dealer card and add to dealer hand
                card = randomCard();
                dealerCards.add(card);
                dealerHand += handValue(card, dealerHand);

                //draw another card for player and dealer and add to hand values
                card = randomCard();
                playerCards.add(card);
                JOptionPane.showMessageDialog(frame,"", "Card display", JOptionPane.PLAIN_MESSAGE, card);
                playerHand += handValue(card, playerHand);

                //store dealer card and add to dealer hand
                card = randomCard();
                dealerCards.add(card);
                dealerHand += handValue(card, dealerHand);

                //display hit/stand/double buttons
                hitBtn.setVisible(true);
                standBtn.setVisible(true);
                doubleBtn.setVisible(true);

                //make back button invisible
                back.setVisible(false);

            } catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(frame, "Please enter a valid number!");
            }
        }

    }

    //evaluate win method for starting, 21, and above 21 only.
    public void evaluateWinOnPlay(){

        //if both go above 21, reset game and show message
        if(playerHand > 21 && dealerHand > 21) {
            JOptionPane.showMessageDialog(frame, "Draw, both went above 21! Bet successfully returned. + $"+betAmount);
            resetBlackJack();
            //if user reaches 21 first or dealer goes above 21, user wins
        }else if(playerHand == 21 || dealerHand > 21){
            win = true;
            winnerText = "BlackJack! 3x. + $" + betAmount*3;
            //triple bet and add to balance
            bank.addCash(betAmount * 3);
            //if dealer reaches 21 first, or user goes above 21, dealer wins
        } else if(dealerHand == 21 || playerHand > 21) {
            win = true;
            winnerText = "You Lose! - $" + betAmount;
            //take money from balance
            bank.removeCash(betAmount);
        }
    }

    //check hands method to compare who has a greater value
    public void checkHands(){
        //if both go above 21, draw
        if(playerHand > 21 && dealerHand > 21) {
            JOptionPane.showMessageDialog(frame, "Draw, both went above 21! Bet successfully returned. +$" + betAmount);
            resetBlackJack();
            //user wins if player > dealer or dealer goes above 21, or user gets 21
            //otherwise, if both have same value, draw
        } else if(playerHand == dealerHand) {
                JOptionPane.showMessageDialog(frame, "Draw! Bet successfully returned. +$"+betAmount);
                resetBlackJack();

        }else if((playerHand > dealerHand && playerHand <= 21) || (dealerHand > 21 && playerHand <= 21) || playerHand == 21){
            win = true;
            winnerText = "You Win! 2x. + $" + betAmount*2;
            //triple bet and add to balance
            bank.addCash(betAmount * 2);

            //dealer wins if dealer > player or player goes above 21, or dealer gets 21
        } else if((dealerHand > playerHand && dealerHand <= 21) || (playerHand > 21 && dealerHand <= 21) || dealerHand == 21){
            win = true;
            winnerText = "You Lose! - $" + betAmount;
            //take money from balance
            bank.removeCash(betAmount);

        }
    }

    //reset blackjack method
    public void resetBlackJack(){
        //make start btn visible, other buttons invisible
        startBtn.setVisible(true);
        hitBtn.setVisible(false);
        standBtn.setVisible(false);
        doubleBtn.setVisible(false);
        playAgain.setVisible(false);
        back.setVisible(true);

        //set win to false
        win = false;
        winnerText = "";
        betAmount = 0;

        //set hands to zero
        playerHand = 0;
        dealerHand = 0;

        //reset arrays
        playerCards = new ArrayList<>();
        dealerCards = new ArrayList<>();
    }

    //hit method
    public void hitMethod(){
        //only player draws
        //store player card and add to player hand
        ImageIcon card = randomCard();
        playerCards.add(card);
        JOptionPane.showMessageDialog(frame,"", "Card display", JOptionPane.PLAIN_MESSAGE, card);
        playerHand += handValue(card, playerHand);

        //evaluate if anyone gets 21 or goes over();
        //if user reaches 21 first, user wins
        if(playerHand == 21){
            win = true;
            winnerText = "You win! 2x. + $" + betAmount*2;
            //double bet and add to balance
            bank.addCash(betAmount * 2);
            //user goes above 21, dealer wins
        } else if(playerHand > 21) {
            win = true;
            winnerText = "You Lose! - $" + betAmount;
            //take money from balance
            bank.removeCash(betAmount);
        }
    }

    //stand method
    public void standMethod(){
        //dealer only draws if players hand is more
        if(playerHand >= dealerHand){
            //dealer draws until they get 17 or more
            while(dealerHand <= 17){
                ImageIcon card = randomCard();
                dealerCards.add(card);
                dealerHand += handValue(card, dealerHand);
                repaint();
            }
        }
        //resolve game method
        checkHands();
    }

    //double method
    public void doubleMethod(){
        //check if user has enough money to double bet
        if(bank.cash() < betAmount*2){
            JOptionPane.showMessageDialog(frame, "Not enough money!");
            //exit function
            return;
        }
        //double bet amt
        betAmount *= 2;
        //draw only one card for user
        ImageIcon card = randomCard();
        playerCards.add(card);
        JOptionPane.showMessageDialog(frame,"", "Card display", JOptionPane.PLAIN_MESSAGE, card);
        playerHand += handValue(card, playerHand);


        //if dealer hand is less than  17, dealer keeps drawing
        while(dealerHand <= 17){
            card = randomCard();
            dealerCards.add(card);
            dealerHand += handValue(card, dealerHand);
        }

        //4x return if user wins
        if((playerHand > dealerHand && playerHand <= 21) || (dealerHand > 21 && playerHand <= 21)){
            win = true;
            winnerText = "You Win! + $" + betAmount*2;
            //add to balance
            bank.addCash(betAmount*2);
            //if user loses, remove 2x bet
        } else if((playerHand < dealerHand && dealerHand <= 21) || (playerHand > 21 && dealerHand <= 21)){
            win = true;
            winnerText = "You Lose! - $" + betAmount;
            //remove from balance
            bank.removeCash(betAmount);
            //otherwise draw and return amt
        } else {
            JOptionPane.showMessageDialog(frame, "Draw! Bet successfully returned. +$"+betAmount);
            resetBlackJack();
        }
    }


}
