package net.digihippo.bread;

import java.util.HashMap;
import java.util.Map;

public class AccountRepository {
    private final Map<Integer, Account> accounts = new HashMap<Integer, Account>();
    private final OutboundEvents events;
    private final int priceOfBread;

    public AccountRepository(OutboundEvents events, int priceOfBread) {
        this.events = events;
        this.priceOfBread = priceOfBread;
    }

    public void createAccount(int id) {
        Account newAccount = new Account();
        accounts.put(id, newAccount);
        events.accountCreatedSuccessfully(id);
    }

    public void deposit(int accountId, int creditAmount) {
        Account account = accounts.get(accountId);
        if (account != null) {
            final int newBalance = account.deposit(creditAmount);
            events.newAccountBalance(accountId, newBalance);
        } else {
            events.accountNotFound(accountId);
        }
    }

    public void placeOrder(int accountId, int orderId, int amount) {
        Account account = accounts.get(accountId);
        if (account != null) {
            int cost = amount * priceOfBread;
            if (account.getBalance() >= cost) {
                account.addOrder(orderId, amount);
                int newBalance = account.deposit(-cost);
                events.orderPlaced(accountId, amount);
                events.newAccountBalance(accountId, newBalance);
            } else {
                events.orderRejected(accountId);
            }
        } else {
            events.accountNotFound(accountId);
        }
    }

    public void cancelOrder(int accountId, int orderId) {
        Account account = accounts.get(accountId);
        if (account == null)
        {
            events.accountNotFound(accountId);
            return;
        }

        Integer cancelledQuantity = account.cancelOrder(orderId);
        if (cancelledQuantity == null)
        {
            events.orderNotFound(accountId, orderId);
            return;
        }

        int newBalance = account.deposit(cancelledQuantity * priceOfBread);
        events.orderCancelled(accountId, orderId);
        events.newAccountBalance(accountId, newBalance);
    }
}