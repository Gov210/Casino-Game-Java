//import classes
import javax.swing.*;

public class Main{
    public static void main(String[] args){
        //create frame object
        JFrame frame = new JFrame("Casino");

        //create other class objects
        Bank bank = new Bank();
        BlackJack blackJack = new BlackJack(frame, bank);
        Slots slots = new Slots(frame, bank);
        Double dN = new Double(frame,bank);
        Menu menu = new Menu(frame,bank);

        frame.add(menu);

        //set screen size
        frame.setSize(800,800);

        //add functions and operators
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
