
package com.eagle.hacks.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TableLayout.LayoutParams;

import com.eagle.hacks.R;
import com.eagle.hacks.Utils;

import java.util.Random;

public class AnimationSlidShow extends BaseActivity implements AnimatorListener {

    private static final int[] PHOTOS = {
            R.drawable.photo,
            R.drawable.photo1,
    };
    public static final int ANIM_COUNT = 4;
    private FrameLayout mContainer;
    private View mView;
    private int mIndex;
    private Random mRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRandom = new Random();
        mContainer = new FrameLayout(this);
        mContainer.setLayoutParams(new LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT));
        mView = createNewView();
        mContainer.addView(mView);
        setContentView(mContainer);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        nextAnimation();
    }

    private ImageView createNewView() {
        ImageView ret = new ImageView(this);
        ret.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        ret.setScaleType(ScaleType.FIT_XY);
        ret.setImageResource(PHOTOS[mIndex]);
        mIndex = (mIndex + 1 < PHOTOS.length) ? mIndex + 1 : 0;
        return ret;
    }

    private void nextAnimation() {
        Utils.logd(TAG, "next animation!");
        AnimatorSet animator = new AnimatorSet();
        final int index = mRandom.nextInt(ANIM_COUNT);
        switch (index) {
            case 0:
                animator.playTogether(ObjectAnimator.ofFloat(mView, "scaleX", 1.5f, 1f),
                        ObjectAnimator.ofFloat(mView, "scaleY", 1.5f, 1f));
                break;
            case 1:
                animator.playTogether(ObjectAnimator.ofFloat(mView, "translationX", 0f, 40f),
                        ObjectAnimator.ofFloat(mView, "translationY", 0f, 40f));
                break;
            case 3:
                animator.playTogether(ObjectAnimator.ofFloat(mView, "alpha", 0f, 1.0f),
                        ObjectAnimator.ofFloat(mView, "alpha", 0f, 1.0f));
                break;
            case 4:
            default:
                animator.playTogether(ObjectAnimator.ofFloat(mView, "scaleX", 1.5f, 1f),
                        ObjectAnimator.ofFloat(mView, "scaleY", 1.5f, 1f));
                break;
        }
        animator.addListener(this);
        animator.setDuration(1000);
        animator.start();
    }

    @Override
    public void onAnimationStart(Animator animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mContainer.removeView(mView);
        mView = createNewView();
        mContainer.addView(mView);
        nextAnimation();
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        // TODO Auto-generated method stub

    }

}
