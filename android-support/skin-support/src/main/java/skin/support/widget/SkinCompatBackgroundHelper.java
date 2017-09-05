package skin.support.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import skin.support.R;
import skin.support.SkinCompatManager;
import skin.support.content.res.SkinCompatResources;
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
                attrs,
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
        if (mBackgroundTypedValue.isTypeNull() || mBackgroundTypedValue.isDataInvalid()) {
            return;
        }

        if (mBackgroundTypedValue.isTypeAttr()) {
            TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(
                    mView.getContext(), new int[]{mBackgroundTypedValue.data});
            ViewCompat.setBackground(mView, a.getDrawable(0));
            a.recycle();
        } else if (mBackgroundTypedValue.isTypeRes()) {
            String typeName = mView.getResources().getResourceTypeName(mBackgroundTypedValue.data);
            if ("color".equals(typeName)) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    int color = SkinCompatResources.getInstance().getColor(mBackgroundTypedValue.data);
                    mView.setBackgroundColor(color);
                } else {
                    ColorStateList colorStateList = SkinCompatResources.getInstance().getColorStateList(mBackgroundTypedValue.data);
                    Drawable drawable = mView.getBackground();
                    if (drawable != null) {
                        DrawableCompat.setTintList(drawable, colorStateList);
                        ViewCompat.setBackground(mView, drawable);
                    } else {
                        ColorDrawable colorDrawable = new ColorDrawable();
                        colorDrawable.setTintList(colorStateList);
                        ViewCompat.setBackground(mView, colorDrawable);
                    }
                }
            } else if ("drawable".equals(typeName)) {
                Drawable drawable = SkinCompatResources.getInstance().getDrawable(mBackgroundTypedValue.data);
                ViewCompat.setBackground(mView, drawable);
            } else if ("mipmap".equals(typeName)) {
                Drawable drawable = SkinCompatResources.getInstance().getMipmap(mBackgroundTypedValue.data);
                ViewCompat.setBackground(mView, drawable);
            }
        }
    }
}
