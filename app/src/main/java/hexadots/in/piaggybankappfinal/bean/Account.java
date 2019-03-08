package hexadots.in.piaggybankappfinal.bean;

public class Account {
    private String accountId;
    private String accountName;
    private Long accountNumber;
    private String accountType;
    private double balance;
    private PiggyDetails piggyDetails;

    public Long getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public PiggyDetails getPiggyDetails() {
        return this.piggyDetails;
    }

    public void setPiggyDetails(PiggyDetails piggyDetails) {
        this.piggyDetails = piggyDetails;
    }
}
