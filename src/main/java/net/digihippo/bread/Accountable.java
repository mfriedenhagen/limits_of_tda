package net.digihippo.bread;

/**
 * Created by mirko on 09.10.14.
 */
public interface Accountable {
    int getBalance();

    void deposit(int creditAmount);

    void addOrder(int orderId, int amount);

    void placeOrder(int orderId, int amount, int cost);

    void cancelOrder(int orderId, int priceOfBread);
}
