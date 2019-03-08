package hexadots.in.piaggybankappfinal.bean;

public class ShowAmountRight {
    private String amount;
    private boolean canshowSymbol;

    public ShowAmountRight(String amount, boolean canshowSymbol) {
        this.amount = amount;
        this.canshowSymbol = canshowSymbol;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isCanshowSymbol() {
        return this.canshowSymbol;
    }

    public void setCanshowSymbol(boolean canshowSymbol) {
        this.canshowSymbol = canshowSymbol;
    }
}
