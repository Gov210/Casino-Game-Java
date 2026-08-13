//import classes
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//slots class with added listeners
public class Menu extends JPanel implements KeyListener, ActionListener, MouseListener {

    //add bank class and Jframe class
    private Bank bank;
    private JFrame frame;

    //images
    private Image mainText;
    private Image casinoBg;

    //variables
    private Timer timer;
    private int delay = 8;

    //buttons
    private JButton slotsButton;
    private JButton blackjackButton;
    private JButton doubleButton;

    //constructor class, add bank class and frame class to switch between classes: google gemini
    public Menu(JFrame frame, Bank bank) {
        this.frame = frame;
        this.bank = bank;

        //to render images smoothly
        setDoubleBuffered(true);

        //background color
        setBackground(Color.black);

        //important for absolute positioning: https://stackoverflow.com/questions/1313974/what-does-frame-setlayoutnulldoframe-is-a-jframe
        setLayout(null);

        //load images
        ImageIcon mT = new ImageIcon(getClass().getResource("/casinoText.png"));
        casinoBg = new ImageIcon(getClass().getResource("/casinobg.jpg")).getImage();

        //scale images
        mainText = mT.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);

        //create buttons
        slotsButton = new JButton("Slots");
        slotsButton.setBounds(300, 350, 200, 50);
        //create border of thickness 2: google gemini
        slotsButton.setBorder(BorderFactory.createLineBorder(Color.decode("#8C000F"), 6));

        blackjackButton = new JButton("Blackjack");
        blackjackButton.setBounds(300, 430, 200, 50);
        blackjackButton.setBorder(BorderFactory.createLineBorder(Color.decode("#8C000F"), 6));

        doubleButton = new JButton("Double or Nothing");
        doubleButton.setBounds(300, 510, 200, 50);
        doubleButton.setBorder(BorderFactory.createLineBorder(Color.decode("#8C000F"), 6));

        //style buttons
        slotsButton.setBackground(Color.decode("#DAA520"));
        slotsButton.setForeground(Color.black);

        blackjackButton.setBackground(Color.decode("#DAA520"));
        blackjackButton.setForeground(Color.black);

        doubleButton.setBackground(Color.decode("#DAA520"));
        doubleButton.setForeground(Color.black);

        //add buttons
        add(slotsButton);
        add(blackjackButton);
        add(doubleButton);

        //listeners
        slotsButton.addActionListener(this);
        blackjackButton.addActionListener(this);
        doubleButton.addActionListener(this);

        addKeyListener(this);
        addMouseListener(this);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        //create timer object
        timer = new Timer(delay, this);
        timer.start();
    }

    //create paint method
    public void paintComponent(Graphics g) {

        //clear old drawings
        super.paintComponent(g);

        //bg
        g.drawImage(casinoBg,0,0,800,800,this);

        //draw images
        g.drawImage(mainText, 200, 100, this);

        //draw balance
        g.setColor(Color.green);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Balance: $" + bank.cash(), 500, 70);
    }

    //****** Necessary Methods *****************
    @Override
    public void actionPerformed(ActionEvent e) {
        //button click events
        //slots
        if (e.getSource() == slotsButton) {
            //switch to slots class: google gemini
            //remove everything inside current window
            frame.getContentPane().removeAll();

            //create new slots panel and add to window
            frame.add(new Slots(frame,bank));

            //recalculate layout
            frame.revalidate();

            //redraw window
            frame.repaint();
        }

        //blackjack
        if (e.getSource() == blackjackButton) {
            frame.getContentPane().removeAll();
            frame.add(new BlackJack(frame,bank));
            frame.revalidate();
            frame.repaint();
        }

        //double or nothing
        if (e.getSource() == doubleButton) {
            frame.getContentPane().removeAll();
            frame.add(new Double(frame,bank));
            frame.revalidate();
            frame.repaint();
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
