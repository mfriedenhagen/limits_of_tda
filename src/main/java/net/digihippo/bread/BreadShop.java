package net.digihippo.bread;

public class BreadShop {

    private static final int PRICE_OF_BREAD = 12;

    private final AccountRepository accountRepository;

    public BreadShop(OutboundEvents events) {
        accountRepository= new AccountRepository(events);
    }

    public void createAccount(int id) {
        accountRepository.createAccount(id);
    }

    public void deposit(int accountId, int creditAmount) {
        withAccount(accountId).deposit(creditAmount);
    }

    public void placeOrder(int accountId, int orderId, int amount) {
        withAccount(accountId).placeOrder(orderId, amount, amount * PRICE_OF_BREAD);
    }

    public void cancelOrder(int accountId, int orderId) {
        withAccount(accountId).cancelOrder(orderId, PRICE_OF_BREAD);
    }

    public void placeWholesaleOrder() {
        throw new UnsupportedOperationException("Implement me in Objective A");
    }

    public void onWholesaleOrder(int quantity) {
        throw new UnsupportedOperationException("Implement me in Objective B");
    }

    private Accountable withAccount(int accountId) {
        return accountRepository.withAccount(accountId);
    }
}
