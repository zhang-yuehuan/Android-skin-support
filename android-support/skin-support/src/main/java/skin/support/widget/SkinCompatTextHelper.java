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

    SkinCompatTypedValue mDrawableBottomTypedValue = new SkinCompatTypedValue();
    SkinCompatTypedValue mDrawableLeftTypedValue = new SkinCompatTypedValue();
    SkinCompatTypedValue mDrawableRightTypedValue = new SkinCompatTypedValue();
    SkinCompatTypedValue mDrawableTopTypedValue = new SkinCompatTypedValue();

    public SkinCompatTextHelper(TextView view) {
        mView = view;
    }

    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        final Context context = mView.getContext();

        SkinCompatTypedValue.getValue(
                context,
                attrs,
                defStyleAttr,
                R.styleable.SkinCompatTextHelper,
                R.styleable.SkinCompatTextHelper_android_drawableLeft,
                mDrawableLeftTypedValue);
        SkinCompatTypedValue.getValue(
                context,
                attrs,
                defStyleAttr,
                R.styleable.SkinCompatTextHelper,
                R.styleable.SkinCompatTextHelper_android_drawableRight,
                mDrawableRightTypedValue);
        SkinCompatTypedValue.getValue(
                context,
                attrs,
                defStyleAttr,
                R.styleable.SkinCompatTextHelper,
                R.styleable.SkinCompatTextHelper_android_drawableTop,
                mDrawableTopTypedValue);
        SkinCompatTypedValue.getValue(
                context,
                attrs,
                defStyleAttr,
                R.styleable.SkinCompatTextHelper,
                R.styleable.SkinCompatTextHelper_android_drawableBottom,
                mDrawableBottomTypedValue);

        SkinCompatTypedValue.getValue(
                context,
                attrs,
                defStyleAttr,
                R.styleable.SkinCompatTextHelper,
                R.styleable.SkinCompatTextHelper_android_textAppearance,
                mTextAppearanceTypedValue);
        SkinCompatTypedValue.getValue(
                context,
                attrs,
                defStyleAttr,
                R.styleable.SkinTextAppearance,
                R.styleable.SkinTextAppearance_android_textColor,
                mTextColorTypedValue);
        SkinCompatTypedValue.getValue(
                context,
                attrs,
                defStyleAttr,
                R.styleable.SkinTextAppearance,
                R.styleable.SkinTextAppearance_android_textColorHint,
                mTextColorHintTypedValue);
        applySkin();
    }

    public void onSetTextAppearance(Context context, int resId) {
        mTextAppearanceTypedValue.setData(resId);
        mTextColorTypedValue.reset();
        mTextColorHintTypedValue.reset();
        applyTextAppearanceResource();
    }

    private void applyTextAppearanceResource() {
        if (mTextColorTypedValue.isTypeNull() || mTextColorHintTypedValue.isTypeNull()) {
            TypedArray a = mTextAppearanceTypedValue.obtainStyledAttributes(R.styleable.SkinTextAppearance);
            if (mTextColorTypedValue.isTypeNull() && a.hasValue(R.styleable.SkinTextAppearance_android_textColor)) {
                mView.setTextColor(a.getColorStateList(R.styleable.SkinTextAppearance_android_textColor));
            }
            if (mTextColorHintTypedValue.isTypeNull() && a.hasValue(R.styleable.SkinTextAppearance_android_textColorHint)) {
                mView.setHintTextColor(a.getColorStateList(R.styleable.SkinTextAppearance_android_textColorHint));
            }
            a.recycle();
        }
    }

    private void applyTextColorHintResource() {
        ColorStateList colorStateList = mTextColorHintTypedValue.getColorStateList();
        if (colorStateList != null) {
            mView.setHintTextColor(colorStateList);
        }
    }

    private void applyTextColorResource() {
        ColorStateList colorStateList = mTextColorTypedValue.getColorStateList();
        if (colorStateList != null) {
            mView.setTextColor(colorStateList);
        }
    }

    public void onSetCompoundDrawablesRelativeWithIntrinsicBounds(
            @DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        mDrawableLeftTypedValue.setData(start);
        mDrawableRightTypedValue.setData(end);
        mDrawableTopTypedValue.setData(top);
        mDrawableBottomTypedValue.setData(bottom);
        applyCompoundDrawablesRelativeResource();
    }

    public void onSetCompoundDrawablesWithIntrinsicBounds(
            @DrawableRes int left, @DrawableRes int top, @DrawableRes int right, @DrawableRes int bottom) {
        mDrawableLeftTypedValue.setData(left);
        mDrawableRightTypedValue.setData(right);
        mDrawableTopTypedValue.setData(top);
        mDrawableBottomTypedValue.setData(bottom);
        applyCompoundDrawablesResource();
    }

    protected void applyCompoundDrawablesRelativeResource() {
        applyCompoundDrawablesResource();
    }

    protected void applyCompoundDrawablesResource() {
        Drawable drawableLeft = mDrawableLeftTypedValue.getDrawable();
        Drawable drawableTop = mDrawableTopTypedValue.getDrawable();
        Drawable drawableRight = mDrawableRightTypedValue.getDrawable();
        Drawable drawableBottom = mDrawableBottomTypedValue.getDrawable();

        if (drawableLeft != null
                || drawableTop != null
                || drawableRight != null
                || drawableBottom != null) {
            mView.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
        }
    }

    public SkinCompatTypedValue getTextColorTypedValue() {
        return mTextColorTypedValue;
    }

    public SkinCompatTypedValue getTextAppearanceTypedValue() {
        return mTextAppearanceTypedValue;
    }

    public void applySkin() {
        applyTextAppearanceResource();
        applyTextColorResource();
        applyTextColorHintResource();
        applyCompoundDrawablesResource();
    }
}
