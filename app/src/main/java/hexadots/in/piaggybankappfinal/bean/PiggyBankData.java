package hexadots.in.piaggybankappfinal.bean;

import java.util.HashMap;
import java.util.Map;

public class PiggyBankData {
    private Map<String, Account> accounts;
    private String loginPin;
    private String mobileNumber;
    private String name;
    private String transactionPin;
    private Map<String, TransactionsDetails> transactions = new HashMap();

    public Map<String, Account> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    public String getLoginPin() {
        return this.loginPin;
    }

    public void setLoginPin(String loginPin) {
        this.loginPin = loginPin;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransactionPin() {
        return this.transactionPin;
    }

    public void setTransactionPin(String transactionPin) {
        this.transactionPin = transactionPin;
    }

    public Map<String, TransactionsDetails> getTransactions() {
        return this.transactions;
    }

    public void addTransaction(TransactionsDetails transactions) {
        this.transactions.put(transactions.getTransactionId(), transactions);
    }
}
