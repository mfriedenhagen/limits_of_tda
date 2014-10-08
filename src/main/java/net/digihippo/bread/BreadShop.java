package net.digihippo.bread;

public class BreadShop {
    private static int PRICE_OF_BREAD = 12;

    private final OutboundEvents events;
    private final AccountRepository accountRepository;

    public BreadShop(OutboundEvents events) {
        this.events = events;
        accountRepository= new AccountRepository(events, PRICE_OF_BREAD);
    }

    public void createAccount(int id) {
        accountRepository.createAccount(id);
    }

    public void deposit(int accountId, int creditAmount) {
        accountRepository.deposit(accountId, creditAmount);
    }

    public void placeOrder(int accountId, int orderId, int amount) {
        accountRepository.placeOrder(accountId, orderId, amount);
    }

    public void cancelOrder(int accountId, int orderId) {
        accountRepository.cancelOrder(accountId, orderId);
    }

    public void placeWholesaleOrder() {
        throw new UnsupportedOperationException("Implement me in Objective A");
    }

    public void onWholesaleOrder(int quantity) {
        throw new UnsupportedOperationException("Implement me in Objective B");
    }
}
