public class Bank {
    //create balance
    private int startingBalance = 1000;
    private int balance = startingBalance;

    //method to show balance
    public int cash(){
        return balance;
    }

    //method to add cash
    public void addCash(int amount){
        balance += amount;
    }

    //method to reset balance
    public void resetBalance(){
        balance = startingBalance;
    }

    //method to remove cash
    public boolean removeCash(int amount){
        //only remove if balance is more than amt
        if(balance >= amount){
            balance -= amount;
            return true;
        }
        return false;
    }
}
