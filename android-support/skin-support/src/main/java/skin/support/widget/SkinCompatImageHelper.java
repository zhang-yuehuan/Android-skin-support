package skin.support.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import skin.support.R;
import skin.support.content.res.SkinCompatResources;
import skin.support.content.res.SkinCompatTypedValue;

/**
 * Created by ximsfei on 2017/1/12.
 */
public class SkinCompatImageHelper extends SkinCompatHelper {
    private static final String TAG = SkinCompatImageHelper.class.getSimpleName();
    private final ImageView mView;
    private SkinCompatTypedValue mSrcTypedValue = new SkinCompatTypedValue();

    public SkinCompatImageHelper(ImageView imageView) {
        mView = imageView;
    }

    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        SkinCompatTypedValue.getValue(
                mView.getContext(),
                attrs,
                defStyleAttr,
                R.styleable.SkinCompatImageView,
                R.styleable.SkinCompatImageView_srcCompat,
                mSrcTypedValue);
        if (mSrcTypedValue.isDataInvalid()) {
            SkinCompatTypedValue.getValue(
                    mView.getContext(),
                    attrs,
                    defStyleAttr,
                    R.styleable.SkinCompatImageView,
                    R.styleable.SkinCompatImageView_android_src,
                    mSrcTypedValue);
        }
        applySkin();
    }

    public void setImageResource(int resId) {
        mSrcTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
        mSrcTypedValue.data = resId;
        applySkin();
    }

    public void applySkin() {
        Drawable drawable = mSrcTypedValue.getDrawable();
        if (drawable != null) {
            mView.setImageDrawable(drawable);
        }
    }

}
