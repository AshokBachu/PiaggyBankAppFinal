package hexadots.in.piaggybankappfinal.viewpager;

import android.support.v7.widget.CardView;

public interface CardAdapter {
    public static final int MAX_ELEVATION_FACTOR = 10;

    float getBaseElevation();

    CardView getCardViewAt(int i);

    int getCount();
}
