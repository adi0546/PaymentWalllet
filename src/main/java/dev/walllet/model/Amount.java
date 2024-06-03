package dev.wallet.model;

public class Amount {
    private String amount;
    private String currency;
    private DebitCredit debitOrCredit;

    public Amount(String amount, String currency, DebitCredit debitOrCredit) {
        this.amount = amount;
        this.currency = currency;
        this.debitOrCredit = debitOrCredit;
    }

    // getters and setters
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public DebitCredit getDebitOrCredit() {
        return debitOrCredit;
    }

    public void setDebitOrCredit(DebitCredit debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }

    public int compareTo(Amount transactionAmount) {
        if(Double.parseDouble(this.amount) > Double.parseDouble(transactionAmount.getAmount())) {
            return 1;
        } else if(Double.parseDouble(this.amount) < Double.parseDouble(transactionAmount.getAmount())) {
            return -1;
        } else {
            return 0;
        }
    }

    public Amount subtract(Amount transactionAmount) {
        double newAmount = Double.parseDouble(this.amount) - Double.parseDouble(transactionAmount.getAmount());
        return new Amount(String.valueOf(newAmount), this.currency, this.debitOrCredit);
    }

    public Amount add(Amount transactionAmount) {
        double newAmount = Double.parseDouble(this.amount) + Double.parseDouble(transactionAmount.getAmount());
        return new Amount(String.valueOf(newAmount), this.currency, this.debitOrCredit);
    }
}
