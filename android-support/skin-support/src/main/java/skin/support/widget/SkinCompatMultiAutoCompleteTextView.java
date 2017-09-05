package skin.support.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.util.AttributeSet;

import skin.support.R;
import skin.support.SkinCompatManager;
import skin.support.content.res.SkinCompatResources;
import skin.support.content.res.SkinCompatTypedValue;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;

/**
 * Created by ximsfei on 17-1-14.
 */

public class SkinCompatMultiAutoCompleteTextView extends AppCompatMultiAutoCompleteTextView implements SkinCompatSupportable {
    private static final int[] TINT_ATTRS = {
            android.R.attr.popupBackground
    };
    private SkinCompatTypedValue mDropDownBackgroundTypedValue = new SkinCompatTypedValue();
    private SkinCompatTextHelper mTextHelper;
    private SkinCompatBackgroundHelper mBackgroundTintHelper;

    public SkinCompatMultiAutoCompleteTextView(Context context) {
        this(context, null);
    }

    public SkinCompatMultiAutoCompleteTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public SkinCompatMultiAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SkinCompatTypedValue.getValue(attrs, TINT_ATTRS, 0, mDropDownBackgroundTypedValue);
        if (SkinCompatManager.getInstance().isCompatibleMode() && !mDropDownBackgroundTypedValue.isTypeRes()) {
            TypedArray a = context.obtainStyledAttributes(attrs, TINT_ATTRS, defStyleAttr, 0);
            if (a.hasValue(0)) {
                mDropDownBackgroundTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
                mDropDownBackgroundTypedValue.data = a.getResourceId(0, INVALID_ID);
            }
            a.recycle();
        }
        applyDropDownBackgroundResource();
        mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
        mTextHelper = SkinCompatTextHelper.create(this);
        mTextHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override
    public void setDropDownBackgroundResource(@DrawableRes int resId) {
        super.setDropDownBackgroundResource(resId);
        mDropDownBackgroundTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
        mDropDownBackgroundTypedValue.data = resId;
        applyDropDownBackgroundResource();
    }

    private void applyDropDownBackgroundResource() {
        if (mDropDownBackgroundTypedValue.isTypeNull()
                || mDropDownBackgroundTypedValue.isDataInvalid()) {
            return;
        }

        if (mDropDownBackgroundTypedValue.isTypeAttr()) {
            TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(
                    getContext(), new int[]{mDropDownBackgroundTypedValue.data});
            setDropDownBackgroundDrawable(a.getDrawable(0));
            a.recycle();
        } else if (mDropDownBackgroundTypedValue.isTypeRes()) {
            String typeName = getResources().getResourceTypeName(mDropDownBackgroundTypedValue.data);
            if ("color".equals(typeName)) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    int color = SkinCompatResources.getInstance().getColor(mDropDownBackgroundTypedValue.data);
                    setDrawingCacheBackgroundColor(color);
                } else {
                    ColorStateList colorStateList =
                            SkinCompatResources.getInstance().getColorStateList(mDropDownBackgroundTypedValue.data);
                    Drawable drawable = getDropDownBackground();
                    if (drawable != null) {
                        DrawableCompat.setTintList(drawable, colorStateList);
                        setDropDownBackgroundDrawable(drawable);
                    } else {
                        ColorDrawable colorDrawable = new ColorDrawable();
                        colorDrawable.setTintList(colorStateList);
                        setDropDownBackgroundDrawable(colorDrawable);
                    }
                }
            } else if ("drawable".equals(typeName)) {
                Drawable drawable = SkinCompatResources.getInstance().getDrawable(mDropDownBackgroundTypedValue.data);
                setDropDownBackgroundDrawable(drawable);
            } else if ("mipmap".equals(typeName)) {
                Drawable drawable = SkinCompatResources.getInstance().getMipmap(mDropDownBackgroundTypedValue.data);
                setDropDownBackgroundDrawable(drawable);
            }
        }
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
        super.setBackgroundResource(resId);
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.onSetBackgroundResource(resId);
        }
    }

    @Override
    public void setTextAppearance(int resId) {
        setTextAppearance(getContext(), resId);
    }

    @Override
    public void setTextAppearance(Context context, int resId) {
        super.setTextAppearance(context, resId);
        if (mTextHelper != null) {
            mTextHelper.onSetTextAppearance(context, resId);
        }
    }

    @Override
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(
            @DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        if (mTextHelper != null) {
            mTextHelper.onSetCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(
            @DrawableRes int left, @DrawableRes int top, @DrawableRes int right, @DrawableRes int bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        if (mTextHelper != null) {
            mTextHelper.onSetCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
    }

    @Override
    public void applySkin() {
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.applySkin();
        }
        if (mTextHelper != null) {
            mTextHelper.applySkin();
        }
        applyDropDownBackgroundResource();
    }

}
