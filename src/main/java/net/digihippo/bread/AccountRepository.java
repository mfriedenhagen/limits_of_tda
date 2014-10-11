package net.digihippo.bread;

import java.util.HashMap;
import java.util.Map;

public class AccountRepository {
    private final Map<Integer, Accountable> accounts = new HashMap<Integer, Accountable>();
    private final OutboundEvents events;

    public AccountRepository(OutboundEvents events) {
        this.events = events;
    }

    public void createAccount(int id) {
        Accountable newAccount = new Account(id, events);
        accounts.put(id, newAccount);
    }

    public Accountable withAccount(int accountId) {
        final Accountable accountable = accounts.get(accountId);
        if (accountable == null) {
            events.accountNotFound(accountId);
            return NullAccount.INSTANCE;
        } else {
            return accountable;
        }
    }

    private static class NullAccount implements Accountable {

        final static NullAccount INSTANCE = new NullAccount();

        @Override
        public int getBalance() {
            return 0;
        }

        @Override
        public void deposit(int creditAmount) {}

        @Override
        public void addOrder(int orderId, int amount) {}

        @Override
        public void placeOrder(int orderId, int amount, int cost) {}

        @Override
        public void cancelOrder(int orderId, int priceOfBread) {}
    }
}