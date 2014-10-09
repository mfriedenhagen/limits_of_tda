package net.digihippo.bread;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private final int id;
    private final OutboundEvents events;
    private int balance = 0;
    private final Map<Integer, Integer> orders = new HashMap<Integer, Integer>();

    public Account(int id, OutboundEvents events) {
        this.id = id;
        this.events = events;
        this.events.accountCreatedSuccessfully(id);
    }

    public int getBalance() {
        return balance;
    }

    public void deposit(int creditAmount) {
        balance += creditAmount;
        events.newAccountBalance(id, balance);
    }

    public void addOrder(int orderId, int amount) {
        orders.put(orderId, amount);
    }

    public Integer cancelOrder(int orderId) {
        return orders.remove(orderId);
    }
}
