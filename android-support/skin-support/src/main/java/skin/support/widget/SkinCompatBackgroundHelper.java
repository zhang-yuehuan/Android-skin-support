package skin.support.widget;

import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import skin.support.R;
import skin.support.content.res.SkinCompatTypedValue;

/**
 * Created by ximsfei on 2017/1/10.
 */

public class SkinCompatBackgroundHelper extends SkinCompatHelper {
    private final View mView;

    private SkinCompatTypedValue mBackgroundTypedValue = new SkinCompatTypedValue();

    public SkinCompatBackgroundHelper(View view) {
        mView = view;
    }

    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        SkinCompatTypedValue.getValue(
                mView.getContext(),
                attrs,
                defStyleAttr,
                R.styleable.SkinBackgroundHelper,
                R.styleable.SkinBackgroundHelper_android_background,
                mBackgroundTypedValue);
        applySkin();
    }

    public void onSetBackgroundResource(int resId) {
        mBackgroundTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
        mBackgroundTypedValue.data = resId;
        // Update the default background tint
        applySkin();
    }

    public void applySkin() {
        Drawable drawable = mBackgroundTypedValue.getDrawable();
        if (drawable != null) {
            ViewCompat.setBackground(mView, drawable);
        }
    }
}
