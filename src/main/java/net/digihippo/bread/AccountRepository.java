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
        Account newAccount = new Account(id, events);
        accounts.put(id, newAccount);
    }

    public void deposit(int accountId, int creditAmount) {
        Account account = accounts.get(accountId);
        if (account != null) {
            account.deposit(creditAmount);
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
                account.deposit(-cost);
                events.orderPlaced(accountId, amount);
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


        events.orderCancelled(accountId, orderId);
        account.deposit(cancelledQuantity * priceOfBread);
    }
}