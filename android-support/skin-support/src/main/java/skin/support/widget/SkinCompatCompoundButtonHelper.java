package skin.support.widget;

import android.content.pm.PackageInfo;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CompoundButtonCompat;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import skin.support.R;
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
                mView.getContext(),
                attrs,
                defStyleAttr,
                R.styleable.CompoundButton,
                R.styleable.CompoundButton_android_button,
                mButtonTypedValue);
        SkinCompatTypedValue.getValue(
                mView.getContext(),
                attrs,
                defStyleAttr,
                R.styleable.CompoundButton,
                R.styleable.CompoundButton_buttonTint,
                mButtonTintTypedValue);
        applySkin();
    }

    public void setButtonDrawable(int resId) {
        mButtonTintTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
        mButtonTintTypedValue.data = resId;
        applySkin();
    }

    @Override
    public void applySkin() {
        Drawable buttonDrawable = mButtonTypedValue.getDrawable();
        if (buttonDrawable != null) {
            mView.setButtonDrawable(buttonDrawable);
        }
        ColorStateList tintList = mButtonTintTypedValue.getColorStateList();
        if (tintList != null) {
            CompoundButtonCompat.setButtonTintList(mView, tintList);
        }
    }
}
