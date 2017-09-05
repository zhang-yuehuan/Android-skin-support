package skin.support.widget;

import android.content.res.TypedArray;
import android.support.v4.widget.CompoundButtonCompat;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import skin.support.R;
import skin.support.SkinCompatManager;
import skin.support.content.res.SkinCompatResources;
import skin.support.content.res.SkinCompatTypedValue;

/**
 * Created by ximsfei on 17-1-14.
 */
public class SkinCompatCompoundButtonHelper extends SkinCompatHelper {
    private final CompoundButton mView;
    private SkinCompatTypedValue mButtonTypedValue = new SkinCompatTypedValue();
    private SkinCompatTypedValue mButtonTintTypedValue = new SkinCompatTypedValue();

    public SkinCompatCompoundButtonHelper(CompoundButton view) {
        mView = view;
    }

    void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        SkinCompatTypedValue.getValue(
                attrs,
                R.styleable.CompoundButton,
                R.styleable.CompoundButton_android_button,
                mButtonTypedValue);
        SkinCompatTypedValue.getValue(
                attrs,
                R.styleable.CompoundButton,
                R.styleable.CompoundButton_buttonTint,
                mButtonTintTypedValue);
        if (SkinCompatManager.getInstance().isCompatibleMode()) {
            TypedArray a = mView.getContext().obtainStyledAttributes(attrs, R.styleable.CompoundButton,
                    defStyleAttr, INVALID_ID);
            if (a.hasValue(R.styleable.CompoundButton_android_button)) {
                mButtonTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
                mButtonTypedValue.data = a.getResourceId(R.styleable.CompoundButton_android_button, INVALID_ID);
            }
            if (a.hasValue(R.styleable.CompoundButton_buttonTint)) {
                mButtonTintTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
                mButtonTintTypedValue.data = a.getResourceId(R.styleable.CompoundButton_buttonTint, INVALID_ID);
            }
            a.recycle();
        }
        applySkin();
    }

    public void setButtonDrawable(int resId) {
        mButtonTintTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
        mButtonTintTypedValue.data = resId;
        applySkin();
    }

    @Override
    public void applySkin() {
        if (!mButtonTypedValue.isDataInvalid() && !mButtonTypedValue.isTypeNull()) {
            if (mButtonTypedValue.isTypeAttr()) {
                TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(
                        mView.getContext(), new int[]{mButtonTypedValue.data});
                mView.setButtonDrawable(a.getDrawable(0));
                a.recycle();
            } else if (mButtonTypedValue.isTypeRes()) {
                mView.setButtonDrawable(SkinCompatResources.getInstance().getDrawable(mButtonTypedValue.data));
            }
        }
        if (!mButtonTintTypedValue.isDataInvalid() && !mButtonTintTypedValue.isTypeNull()) {
            if (mButtonTintTypedValue.isTypeAttr()) {
                TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(
                        mView.getContext(), new int[]{mButtonTintTypedValue.data});
                CompoundButtonCompat.setButtonTintList(mView, a.getColorStateList(0));
                a.recycle();
            } else if (mButtonTintTypedValue.isTypeRes()) {
                CompoundButtonCompat.setButtonTintList(mView,
                        SkinCompatResources.getInstance().getColorStateList(mButtonTintTypedValue.data));
            }
        }
    }
}
