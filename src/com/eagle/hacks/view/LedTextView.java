
package com.eagle.hacks.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class LedTextView extends TextView {

    public LedTextView(Context context) {
        this(context, null);
    }

    public LedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                "digital-7_mono.ttf");
        setTypeface(typeface);
    }

}
