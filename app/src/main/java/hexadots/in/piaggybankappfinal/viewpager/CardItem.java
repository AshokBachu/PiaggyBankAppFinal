package hexadots.in.piaggybankappfinal.viewpager;

public class CardItem {
    private boolean canVisible = true;
    private int imageResource;
    private String mTitleResource;
    private boolean playCoin;

    public boolean isCanVisible() {
        return this.canVisible;
    }

    public void setCanVisible(boolean canVisible) {
        this.canVisible = canVisible;
    }

    public boolean isPlayCoin() {
        return this.playCoin;
    }

    public void setPlayCoin(boolean playCoin) {
        this.playCoin = playCoin;
    }

    public boolean canVisible() {
        return this.canVisible;
    }

    public void setVisibility(boolean canVisible) {
        this.canVisible = canVisible;
    }

    public CardItem(String title, int imageResource, boolean isCoin) {
        this.mTitleResource = title;
        this.imageResource = imageResource;
        this.playCoin = isCoin;
    }

    public int getImageResource() {
        return this.imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getmTitleResource() {
        return this.mTitleResource;
    }

    public void setmTitleResource(String mTitleResource) {
        this.mTitleResource = mTitleResource;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ");
        stringBuilder.append(this.mTitleResource);
        stringBuilder.append(" ");
        return stringBuilder.toString();
    }
}
