
package com.eagle.hacks.activity;

import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.eagle.hacks.R;
import com.eagle.hacks.Utils;

import java.util.Random;

public class AnimationSwitcher extends BaseActivity {

    private TextSwitcher mTextSwitcher;
    private ImageSwitcher mImageSwitcher;
    private Context mContext;
    private int mDrawableId = R.drawable.photo;
    private Random mRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.anim_switcher);
        mRandom = new Random();
        mTextSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
        mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        initTextSwitcher();
        initImageSwitcher();
    }

    private void initTextSwitcher() {
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        mTextSwitcher.setFactory(new ViewFactory() {

            @Override
            public View makeView() {
                TextView tv = new TextView(mContext);
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundColor(Color.WHITE);
                tv.setTextSize(30);
                tv.setLayoutParams(new TextSwitcher.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.BLUE);
                return tv;
            }
        });
        mTextSwitcher.setInAnimation(in);
        mTextSwitcher.setOutAnimation(out);
    }

    private void initImageSwitcher() {
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        mImageSwitcher.setFactory(new ViewFactory() {

            @Override
            public View makeView() {
                ImageView image = new ImageView(mContext);
                image.setScaleType(ScaleType.CENTER);
                image.setBackgroundColor(Color.WHITE);
                image.setLayoutParams(new TextSwitcher.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                return image;
            }
        });
        mImageSwitcher.setInAnimation(in);
        mImageSwitcher.setOutAnimation(out);
    }

    public void onTextSwitch(View view) {
        Utils.logd(TAG, "onTextSwitch");
        mTextSwitcher.setText("Text ramdon value : " + mRandom.nextInt(100));
    }

    public void onImageSwitch(View view) {
        Utils.logd(TAG, "onImageSwitch");
        if (mDrawableId == R.drawable.photo1) {
            mDrawableId = R.drawable.photo;
        } else {
            mDrawableId = R.drawable.photo1;
        }
        mImageSwitcher.setImageResource(mDrawableId);
    }
}
