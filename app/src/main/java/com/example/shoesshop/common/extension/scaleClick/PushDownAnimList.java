package com.example.shoesshop.common.extension.scaleClick;


import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

public class PushDownAnimList implements PushDown {

    private final List<PushDownAnim> pushDownList = new ArrayList<>();


    PushDownAnimList(View... views) {
        for (View view : views) {
            PushDownAnim pushDown = PushDownAnim.setPushDownAnimTo(view);
            pushDown.setOnTouchEvent(null);
            this.pushDownList.add(pushDown);
        }
    }

    @Override
    public PushDownAnimList setScale(float scale) {
        for (PushDownAnim pushDown : pushDownList) {
            pushDown.setScale(scale);
        }
        return this;
    }

    @Override
    public PushDown setScale(int mode, float scale) {
        for (PushDownAnim pushDown : pushDownList) {
            pushDown.setScale(mode, scale);
        }
        return this;
    }

    @Override
    public PushDownAnimList setDurationPush(long duration) {
        for (PushDownAnim pushDown : pushDownList) {
            pushDown.setDurationPush(duration);
        }
        return this;
    }

    @Override
    public PushDownAnimList setDurationRelease(long duration) {
        for (PushDownAnim pushDown : pushDownList) {
            pushDown.setDurationRelease(duration);
        }
        return this;
    }

    @Override
    public PushDownAnimList setInterpolatorPush(AccelerateDecelerateInterpolator interpolatorPush) {
        for (PushDownAnim pushDown : pushDownList) {
            pushDown.setInterpolatorPush(interpolatorPush);
        }
        return this;
    }

    @Override
    public PushDownAnimList setInterpolatorRelease(AccelerateDecelerateInterpolator interpolatorRelease) {
        for (PushDownAnim pushDown : pushDownList) {
            pushDown.setInterpolatorRelease(interpolatorRelease);
        }
        return this;
    }


    @Override
    public PushDownAnimList clickWithAnimationDebounce(View.OnClickListener clickListener) {
        for (PushDownAnim pushDown : pushDownList) {
            if (clickListener != null) {
                pushDown.clickWithAnimationDebounce(clickListener);
            }
        }
        return this;
    }

    @Override
    public PushDown setOnLongClickListener(View.OnLongClickListener clickListener) {
        for (PushDownAnim pushDown : pushDownList) {
            if (clickListener != null) {
                pushDown.setOnLongClickListener(clickListener);
            }
        }
        return this;
    }

    public PushDownAnimList setOnTouchEvent(final View.OnTouchListener eventListener) {
        for (PushDownAnim pushDown : pushDownList) {
            if (eventListener != null) {
                pushDown.setOnTouchEvent(eventListener);
            }
        }
        return this;
    }

}

