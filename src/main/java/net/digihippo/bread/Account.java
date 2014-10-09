package net.digihippo.bread;

import java.util.HashMap;
import java.util.Map;

public class Account implements Accountable {
    private final int accountId;
    private final OutboundEvents events;
    private int balance = 0;
    private final Map<Integer, Integer> orders = new HashMap<Integer, Integer>();

    public Account(int accountId, OutboundEvents events) {
        this.accountId = accountId;
        this.events = events;
        this.events.accountCreatedSuccessfully(accountId);
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void deposit(int creditAmount) {
        balance += creditAmount;
        events.newAccountBalance(accountId, balance);
    }

    @Override
    public void addOrder(int orderId, int amount) {
        orders.put(orderId, amount);
    }

    @Override
    public void placeOrder(int orderId, int amount, int cost) {
        if (getBalance() >= cost) {
            addOrder(orderId, amount);
            deposit(-cost);
            events.orderPlaced(accountId, amount);
        } else {
            events.orderRejected(accountId);
        }
    }

    @Override
    public void cancelOrder(int orderId, int priceOfBread) {
        Integer cancelledQuantity = orders.remove(orderId);
        if (cancelledQuantity == null)
        {
            events.orderNotFound(accountId, orderId);
        } else {

            events.orderCancelled(accountId, orderId);
            deposit(cancelledQuantity * priceOfBread);
        }
    }
}
