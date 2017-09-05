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
import skin.support.SkinCompatManager;
import skin.support.content.res.SkinCompatResources;
import skin.support.content.res.SkinCompatTypedValue;
import skin.support.utils.SkinLog;

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
        SkinCompatTypedValue.getValue(attrs,
                R.styleable.SkinCompatImageView,
                R.styleable.SkinCompatImageView_srcCompat,
                mSrcTypedValue);
        if (mSrcTypedValue.isDataInvalid()) {
            SkinCompatTypedValue.getValue(attrs,
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
        if (mSrcTypedValue.isDataInvalid() || mSrcTypedValue.isTypeNull()) {
            return;
        }
        if (mSrcTypedValue.isTypeAttr()) {
            TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(
                    mView.getContext(), new int[]{mSrcTypedValue.data});
            mView.setImageDrawable(a.getDrawable(0));
            a.recycle();
        } else {
            String typeName = mView.getResources().getResourceTypeName(mSrcTypedValue.data);
            if ("color".equals(typeName)) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    int color = SkinCompatResources.getInstance().getColor(mSrcTypedValue.data);
                    Drawable drawable = mView.getDrawable();
                    if (drawable != null && drawable instanceof ColorDrawable) {
                        ((ColorDrawable) drawable.mutate()).setColor(color);
                    } else {
                        mView.setImageDrawable(new ColorDrawable(color));
                    }
                } else {
                    ColorStateList colorStateList = SkinCompatResources.getInstance().getColorStateList(mSrcTypedValue.data);
                    Drawable drawable = mView.getDrawable();
                    if (drawable != null) {
                        DrawableCompat.setTintList(drawable, colorStateList);
                        mView.setImageDrawable(drawable);
                    } else {
                        ColorDrawable colorDrawable = new ColorDrawable();
                        colorDrawable.setTintList(colorStateList);
                        mView.setImageDrawable(colorDrawable);
                    }
                }
            } else if ("drawable".equals(typeName)) {
                Drawable drawable = SkinCompatResources.getInstance().getDrawable(mSrcTypedValue.data);
                mView.setImageDrawable(drawable);
            } else if ("mipmap".equals(typeName)) {
                Drawable drawable = SkinCompatResources.getInstance().getMipmap(mSrcTypedValue.data);
                mView.setImageDrawable(drawable);
            }
        }
    }

}
