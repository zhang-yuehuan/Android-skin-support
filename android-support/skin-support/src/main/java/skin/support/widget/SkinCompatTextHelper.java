package skin.support.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.TextView;

import skin.support.R;
import skin.support.content.res.SkinCompatResources;
import skin.support.content.res.SkinCompatTypedValue;

/**
 * Created by ximsfei on 2017/1/10.
 */

public class SkinCompatTextHelper extends SkinCompatHelper {
    private SkinCompatTypedValue mTextAppearanceTypedValue = new SkinCompatTypedValue();
    private SkinCompatTypedValue mTextColorTypedValue = new SkinCompatTypedValue();
    private SkinCompatTypedValue mTextColorHintTypedValue = new SkinCompatTypedValue();

    public static SkinCompatTextHelper create(TextView textView) {
        if (Build.VERSION.SDK_INT >= 17) {
            return new SkinCompatTextHelperV17(textView);
        }
        return new SkinCompatTextHelper(textView);
    }

    final TextView mView;

    protected int mDrawableBottomResId = INVALID_ID;
    protected int mDrawableLeftResId = INVALID_ID;
    protected int mDrawableRightResId = INVALID_ID;
    protected int mDrawableTopResId = INVALID_ID;

    public SkinCompatTextHelper(TextView view) {
        mView = view;
    }

    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        final Context context = mView.getContext();

        // First read the TextAppearance style id
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SkinCompatTextHelper, defStyleAttr, 0);

        if (a.hasValue(R.styleable.SkinCompatTextHelper_android_drawableLeft)) {
            mDrawableLeftResId = a.getResourceId(R.styleable.SkinCompatTextHelper_android_drawableLeft, INVALID_ID);
        }
        if (a.hasValue(R.styleable.SkinCompatTextHelper_android_drawableTop)) {
            mDrawableTopResId = a.getResourceId(R.styleable.SkinCompatTextHelper_android_drawableTop, INVALID_ID);
        }
        if (a.hasValue(R.styleable.SkinCompatTextHelper_android_drawableRight)) {
            mDrawableRightResId = a.getResourceId(R.styleable.SkinCompatTextHelper_android_drawableRight, INVALID_ID);
        }
        if (a.hasValue(R.styleable.SkinCompatTextHelper_android_drawableBottom)) {
            mDrawableBottomResId = a.getResourceId(R.styleable.SkinCompatTextHelper_android_drawableBottom, INVALID_ID);
        }
        a.recycle();

        SkinCompatTypedValue.getValue(attrs,
                R.styleable.SkinCompatTextHelper,
                R.styleable.SkinCompatTextHelper_android_textAppearance,
                mTextAppearanceTypedValue);
        SkinCompatTypedValue.getValue(
                attrs,
                R.styleable.SkinTextAppearance,
                R.styleable.SkinTextAppearance_android_textColor,
                mTextColorTypedValue);
        SkinCompatTypedValue.getValue(
                attrs,
                R.styleable.SkinTextAppearance,
                R.styleable.SkinTextAppearance_android_textColorHint,
                mTextColorHintTypedValue);
        applySkin();
    }

    public void onSetTextAppearance(Context context, int resId) {
        mTextAppearanceTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
        mTextAppearanceTypedValue.data = resId;
        applyTextAppearanceResource(context);
    }

    private void applyTextAppearanceResource(Context context) {
        if (mTextAppearanceTypedValue.isTypeNull() || mTextAppearanceTypedValue.isDataInvalid()) {
            return;
        }
        if (mTextAppearanceTypedValue.isTypeAttr()) {
            TypedArray a = context.obtainStyledAttributes(new int[]{mTextAppearanceTypedValue.data});
            int ap = a.getResourceId(0, INVALID_ID);
            a.recycle();
            if (ap != INVALID_ID) {
                applyTextAppearance(context, ap);
            }
        } else if (mTextAppearanceTypedValue.isTypeRes()) {
            applyTextAppearance(context, mTextAppearanceTypedValue.data);
        }
    }

    private void applyTextAppearance(Context context, int ap) {
        if (ap == INVALID_ID)
            return;

        TypedArray a = SkinCompatResources.getInstance()
                .obtainStyledAttributes(context, ap, R.styleable.SkinTextAppearance);
        if (a.hasValue(R.styleable.SkinTextAppearance_android_textColor)) {
            mView.setTextColor(a.getColorStateList(R.styleable.SkinTextAppearance_android_textColor));
        }
        if (a.hasValue(R.styleable.SkinTextAppearance_android_textColorHint)) {
            mView.setHintTextColor(a.getResourceId(R.styleable.SkinTextAppearance_android_textColorHint, INVALID_ID));
        }
        a.recycle();
    }

    private void applyTextColorHintResource() {
        if (mTextColorHintTypedValue.isDataInvalid() || mTextColorHintTypedValue.isTypeNull()) {
            return;
        }
        if (mTextColorHintTypedValue.isTypeAttr()) {
            TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(
                    mView.getContext(), new int[]{mTextColorHintTypedValue.data});
            mView.setHintTextColor(a.getColorStateList(0));
            a.recycle();
        } else if (mTextColorHintTypedValue.isTypeRes()) {
            ColorStateList color = SkinCompatResources.getInstance().getColorStateList(mTextColorHintTypedValue.data);
            mView.setHintTextColor(color);
        }
    }

    private void applyTextColorResource() {
        if (mTextColorTypedValue.isDataInvalid() || mTextColorTypedValue.isTypeNull()) {
            return;
        }
        if (mTextColorTypedValue.isTypeAttr()) {
            TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(
                    mView.getContext(), 0, new int[]{mTextColorTypedValue.data});
            mView.setTextColor(a.getColorStateList(0));
            a.recycle();
        } else if (mTextColorTypedValue.isTypeRes()) {
            ColorStateList color = SkinCompatResources.getInstance().getColorStateList(mTextColorTypedValue.data);
            mView.setTextColor(color);
        }
    }

    public void onSetCompoundDrawablesRelativeWithIntrinsicBounds(
            @DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        mDrawableLeftResId = start;
        mDrawableTopResId = top;
        mDrawableRightResId = end;
        mDrawableBottomResId = bottom;
        applyCompoundDrawablesRelativeResource();
    }

    public void onSetCompoundDrawablesWithIntrinsicBounds(
            @DrawableRes int left, @DrawableRes int top, @DrawableRes int right, @DrawableRes int bottom) {
        mDrawableLeftResId = left;
        mDrawableTopResId = top;
        mDrawableRightResId = right;
        mDrawableBottomResId = bottom;
        applyCompoundDrawablesResource();
    }

    protected void applyCompoundDrawablesRelativeResource() {
        applyCompoundDrawablesResource();
    }

    protected void applyCompoundDrawablesResource() {
        Drawable drawableLeft = null, drawableTop = null, drawableRight = null, drawableBottom = null;
        mDrawableLeftResId = checkResourceId(mDrawableLeftResId);
        if (mDrawableLeftResId != INVALID_ID) {
            drawableLeft = SkinCompatResources.getInstance().getDrawable(mDrawableLeftResId);
        }
        mDrawableTopResId = checkResourceId(mDrawableTopResId);
        if (mDrawableTopResId != INVALID_ID) {
            drawableTop = SkinCompatResources.getInstance().getDrawable(mDrawableTopResId);
        }
        mDrawableRightResId = checkResourceId(mDrawableRightResId);
        if (mDrawableRightResId != INVALID_ID) {
            drawableRight = SkinCompatResources.getInstance().getDrawable(mDrawableRightResId);
        }
        mDrawableBottomResId = checkResourceId(mDrawableBottomResId);
        if (mDrawableBottomResId != INVALID_ID) {
            drawableBottom = SkinCompatResources.getInstance().getDrawable(mDrawableBottomResId);
        }
        if (mDrawableLeftResId != INVALID_ID
                || mDrawableTopResId != INVALID_ID
                || mDrawableRightResId != INVALID_ID
                || mDrawableBottomResId != INVALID_ID) {
            mView.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
        }
    }

    public int getTextColorResId() {
        return 0;
    }

    public void applySkin() {
        applyTextAppearanceResource(mView.getContext());
        applyTextColorResource();
        applyTextColorHintResource();
        applyCompoundDrawablesResource();
    }
}
