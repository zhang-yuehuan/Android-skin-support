package skin.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

import skin.support.content.res.SkinCompatResources;
import skin.support.content.res.SkinCompatTypedValue;
import skin.support.design.R;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;

/**
 * Created by ximsfei on 17-1-14.
 */

public class SkinMaterialTabLayout extends TabLayout implements SkinCompatSupportable {
    private SkinCompatTypedValue mTabTextAppearanceTypeValue = new SkinCompatTypedValue();
    private SkinCompatTypedValue mTabIndicatorColorTypeValue = new SkinCompatTypedValue();
    private SkinCompatTypedValue mTabTextColorsTypeValue = new SkinCompatTypedValue();
    private SkinCompatTypedValue mTabSelectedTextColorTypeValue = new SkinCompatTypedValue();

    public SkinMaterialTabLayout(Context context) {
        this(context, null);
    }

    public SkinMaterialTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinMaterialTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SkinCompatTypedValue.getValue(
                context,
                attrs,
                defStyleAttr,
                R.styleable.TabLayout,
                R.styleable.TabLayout_tabIndicatorColor,
                mTabIndicatorColorTypeValue);
        SkinCompatTypedValue.getValue(
                context,
                attrs,
                defStyleAttr,
                R.styleable.TabLayout,
                R.styleable.TabLayout_tabTextAppearance,
                mTabTextAppearanceTypeValue);
        SkinCompatTypedValue.getValue(
                context,
                attrs,
                defStyleAttr,
                R.styleable.TabLayout,
                R.styleable.TabLayout_tabTextColor,
                mTabTextColorsTypeValue);
        SkinCompatTypedValue.getValue(
                context,
                attrs,
                defStyleAttr,
                R.styleable.TabLayout,
                R.styleable.TabLayout_tabSelectedTextColor,
                mTabSelectedTextColorTypeValue);
        applySkin();
    }

    @Override
    public void applySkin() {
        int indicatorColor = mTabIndicatorColorTypeValue.getColor();
        if (indicatorColor != 0) {
            setSelectedTabIndicatorColor(indicatorColor);
        }
        if (mTabTextColorsTypeValue.isTypeNull()) {
            TypedArray a = mTabTextAppearanceTypeValue.obtainStyledAttributes(R.styleable.SkinTextAppearance);
            if (a.hasValue(R.styleable.SkinTextAppearance_android_textColor)) {
                setTabTextColors(a.getColorStateList(R.styleable.SkinTextAppearance_android_textColor));
            }
            a.recycle();
        } else {
            ColorStateList textColors = mTabTextColorsTypeValue.getColorStateList();
            if (textColors != null) {
                setTabTextColors(textColors);
            }
        }
        int selectedTextColor = mTabSelectedTextColorTypeValue.getColor();
        if (getTabTextColors() != null) {
            setTabTextColors(getTabTextColors().getDefaultColor(), selectedTextColor);
        }
    }

}
