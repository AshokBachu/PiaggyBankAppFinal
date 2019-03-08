package hexadots.in.piaggybankappfinal.view;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class SnappyRecyclerView extends RecyclerView {
    public SnappyRecyclerView(Context context) {
        super(context);
    }

    public SnappyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SnappyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public int getFirstVisibleItemPosition() {
        return ((LinearLayoutManager) getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    public boolean fling(int velocityX, int velocityY) {
        SnappyRecyclerView snappyRecyclerView = this;
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        View lastView = linearLayoutManager.findViewByPosition(linearLayoutManager.findLastVisibleItemPosition());
        View firstView = linearLayoutManager.findViewByPosition(linearLayoutManager.findFirstVisibleItemPosition());
        int leftMargin = (screenWidth - lastView.getWidth()) / 2;
        int rightMargin = ((screenWidth - firstView.getWidth()) / 2) + firstView.getWidth();
        int leftEdge = lastView.getLeft();
        int rightEdge = firstView.getRight();
        int scrollDistanceLeft = leftEdge - leftMargin;
        int scrollDistanceRight = rightMargin - rightEdge;
        if (Math.abs(velocityX) < 1000) {
            if (leftEdge > screenWidth / 2) {
                smoothScrollBy(-scrollDistanceRight, 0);
            } else if (rightEdge < screenWidth / 2) {
                smoothScrollBy(scrollDistanceLeft, 0);
            } else if (velocityX > 0) {
                smoothScrollBy(-scrollDistanceRight, 0);
            } else {
                smoothScrollBy(scrollDistanceLeft, 0);
            }
            return true;
        }
        if (velocityX > 0) {
            smoothScrollBy(scrollDistanceLeft, 0);
        } else {
            smoothScrollBy(-scrollDistanceRight, 0);
        }
        return true;
    }

    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == 0) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
            int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
            View lastView = linearLayoutManager.findViewByPosition(linearLayoutManager.findLastVisibleItemPosition());
            View firstView = linearLayoutManager.findViewByPosition(linearLayoutManager.findFirstVisibleItemPosition());
            int leftMargin = (screenWidth - lastView.getWidth()) / 2;
            int rightMargin = ((screenWidth - firstView.getWidth()) / 2) + firstView.getWidth();
            int leftEdge = lastView.getLeft();
            int rightEdge = firstView.getRight();
            int scrollDistanceLeft = leftEdge - leftMargin;
            int scrollDistanceRight = rightMargin - rightEdge;
            if (leftEdge > screenWidth / 2) {
                smoothScrollBy(-scrollDistanceRight, 0);
            } else if (rightEdge < screenWidth / 2) {
                smoothScrollBy(scrollDistanceLeft, 0);
            }
        }
    }
}
