package hexadots.in.piaggybankappfinal.bean;

import java.io.Serializable;

public class TransactionsDetails implements Serializable {
    private Double amount;
    private String dateAndTime;
    private String dateAndTimeAsNumber;
    private String description;
    private String fromAccountId;
    private String fromAccountName;
    private Long fromAccountNumber;
    private Boolean isUpdatedOnPiggy;
    private String toAccountId;
    private String toAccountName;
    private Long toAccountNumber;
    private String transactionId;

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDateAndTime() {
        return this.dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getDateAndTimeAsNumber() {
        return this.dateAndTimeAsNumber;
    }

    public void setDateAndTimeAsNumber(String dateAndTimeAsNumber) {
        this.dateAndTimeAsNumber = dateAndTimeAsNumber;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFromAccountId() {
        return this.fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getFromAccountName() {
        return this.fromAccountName;
    }

    public void setFromAccountName(String fromAccountName) {
        this.fromAccountName = fromAccountName;
    }

    public Long getFromAccountNumber() {
        return this.fromAccountNumber;
    }

    public void setFromAccountNumber(Long fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public Boolean getUpdatedOnPiggy() {
        return this.isUpdatedOnPiggy;
    }

    public void setUpdatedOnPiggy(Boolean updatedOnPiggy) {
        this.isUpdatedOnPiggy = updatedOnPiggy;
    }

    public String getToAccountId() {
        return this.toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }

    public String getToAccountName() {
        return this.toAccountName;
    }

    public void setToAccountName(String toAccountName) {
        this.toAccountName = toAccountName;
    }

    public Long getToAccountNumber() {
        return this.toAccountNumber;
    }

    public void setToAccountNumber(Long toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
