package skin.support.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;

import skin.support.SkinCompatManager;
import skin.support.content.res.SkinCompatResources;
import skin.support.content.res.SkinCompatTypedValue;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;

/**
 * Created by ximsfei on 17-1-14.
 */

public class SkinCompatCheckedTextView extends AppCompatCheckedTextView implements SkinCompatSupportable {

    private static final int[] TINT_ATTRS = {
            android.R.attr.checkMark
    };
    private SkinCompatTypedValue mCheckMarkTypedValue = new SkinCompatTypedValue();

    private SkinCompatTextHelper mTextHelper;
    private SkinCompatBackgroundHelper mBackgroundTintHelper;

    public SkinCompatCheckedTextView(Context context) {
        this(context, null);
    }

    public SkinCompatCheckedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.checkedTextViewStyle);
    }

    public SkinCompatCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
        mTextHelper = SkinCompatTextHelper.create(this);
        mTextHelper.loadFromAttributes(attrs, defStyleAttr);

        SkinCompatTypedValue.getValue(attrs, TINT_ATTRS, 0, mCheckMarkTypedValue);
        if (SkinCompatManager.getInstance().isCompatibleMode()
                && !mCheckMarkTypedValue.isTypeRes()) {
            TypedArray a = context.obtainStyledAttributes(attrs, TINT_ATTRS, defStyleAttr, 0);
            if (a.hasValue(0)) {
                mCheckMarkTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
                mCheckMarkTypedValue.data = a.getResourceId(0, INVALID_ID);
            }
            a.recycle();
        }
        applyCheckMark();
    }

    @Override
    public void setCheckMarkDrawable(@DrawableRes int resId) {
        mCheckMarkTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
        mCheckMarkTypedValue.data = resId;
        applyCheckMark();
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
        applyCheckMark();
    }

    private void applyCheckMark() {
        if (mCheckMarkTypedValue.isDataInvalid() || mCheckMarkTypedValue.isTypeNull()) {
            return;
        }
        if (mCheckMarkTypedValue.isTypeAttr()) {
            TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(
                    getContext(), new int[]{mCheckMarkTypedValue.data});
            setCheckMarkDrawable(a.getDrawable(0));
            a.recycle();
        } else if (mCheckMarkTypedValue.isTypeRes()) {
            setCheckMarkDrawable(SkinCompatResources.getInstance().getDrawable(mCheckMarkTypedValue.data));
        }
    }
}
