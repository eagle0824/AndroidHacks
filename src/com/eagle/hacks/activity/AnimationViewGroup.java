
package com.eagle.hacks.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.eagle.hacks.R;

public class AnimationViewGroup extends BaseActivity {

    private ListView mList;
    private ArrayAdapter<String> mAdapter;
    String[] dataStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_viewgroup);
        mList = (ListView) findViewById(R.id.list);
        dataStrings = getResources().getStringArray(R.array.datas);
        mAdapter = new ArrayAdapter<String>(this, com.android.internal.R.layout.simple_list_item_1,
                dataStrings);
        mList.setAdapter(mAdapter);
        AnimationSet set = new AnimationSet(true);
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(50);
        set.addAnimation(animation);
        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(100);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(
                set, 0.5f);
        mList.setLayoutAnimation(controller);

    }

    public void onDisplayClick(View view) {
        mHandler.sendEmptyMessage(2);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            mList.setAdapter(null);
            mList.setAdapter(mAdapter);
        }

    };
}
