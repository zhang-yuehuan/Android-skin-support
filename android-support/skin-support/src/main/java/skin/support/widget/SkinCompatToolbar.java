package skin.support.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import skin.support.R;
import skin.support.SkinCompatManager;
import skin.support.content.res.SkinCompatResources;
import skin.support.content.res.SkinCompatTypedValue;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;

/**
 * Created by ximsfei on 17-1-12.
 */

public class SkinCompatToolbar extends Toolbar implements SkinCompatSupportable {
    private SkinCompatTypedValue mTitleTextAppearanceTypedValue = new SkinCompatTypedValue();
    private SkinCompatTypedValue mSubtitleTextAppearanceTypedValue = new SkinCompatTypedValue();
    private SkinCompatTypedValue mTitleTextColorTypedValue = new SkinCompatTypedValue();
    private SkinCompatTypedValue mSubtitleTextColorTypedValue = new SkinCompatTypedValue();
    private SkinCompatTypedValue mNavigationIconTypedValue = new SkinCompatTypedValue();
    private SkinCompatBackgroundHelper mBackgroundTintHelper;

    public SkinCompatToolbar(Context context) {
        this(context, null);
    }

    public SkinCompatToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.toolbarStyle);
    }

    public SkinCompatToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
        SkinCompatTypedValue.getValue(attrs, R.styleable.Toolbar, R.styleable.Toolbar_navigationIcon, mNavigationIconTypedValue);
        SkinCompatTypedValue.getValue(attrs, R.styleable.Toolbar, R.styleable.Toolbar_titleTextAppearance, mTitleTextAppearanceTypedValue);
        SkinCompatTypedValue.getValue(attrs, R.styleable.Toolbar, R.styleable.Toolbar_subtitleTextAppearance, mSubtitleTextAppearanceTypedValue);
        SkinCompatTypedValue.getValue(attrs, R.styleable.Toolbar, R.styleable.Toolbar_titleTextColor, mTitleTextColorTypedValue);
        SkinCompatTypedValue.getValue(attrs, R.styleable.Toolbar, R.styleable.Toolbar_subtitleTextColor, mSubtitleTextColorTypedValue);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Toolbar, defStyleAttr, 0);
        int titleAp = a.getResourceId(R.styleable.Toolbar_titleTextAppearance, INVALID_ID);
        int subtitleAp = a.getResourceId(R.styleable.Toolbar_subtitleTextAppearance, INVALID_ID);
        a.recycle();

        if (SkinCompatManager.getInstance().isCompatibleMode()) {
            if (!mNavigationIconTypedValue.isTypeRes()) {
                a = context.obtainStyledAttributes(attrs, R.styleable.Toolbar, defStyleAttr, 0);
                mNavigationIconTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
                mNavigationIconTypedValue.data = a.getResourceId(R.styleable.Toolbar_navigationIcon, INVALID_ID);
                a.recycle();
            }
            if (titleAp != INVALID_ID && !mTitleTextColorTypedValue.isTypeRes()) {
                a = context.obtainStyledAttributes(titleAp, R.styleable.SkinTextAppearance);
                int resId = a.getResourceId(R.styleable.SkinTextAppearance_android_textColor, INVALID_ID);
                if (resId != INVALID_ID) {
                    mTitleTextColorTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
                    mTitleTextColorTypedValue.data = resId;
                }
                a.recycle();
            }
            if (subtitleAp != INVALID_ID && !mSubtitleTextColorTypedValue.isTypeRes()) {
                a = context.obtainStyledAttributes(subtitleAp, R.styleable.SkinTextAppearance);
                int resId = a.getResourceId(R.styleable.SkinTextAppearance_android_textColor, INVALID_ID);
                if (resId != INVALID_ID) {
                    mSubtitleTextColorTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
                    mSubtitleTextColorTypedValue.data = resId;
                }
                a.recycle();
            }
            a = context.obtainStyledAttributes(attrs, R.styleable.Toolbar, defStyleAttr, 0);
            int titleResId = a.getResourceId(R.styleable.Toolbar_titleTextColor, INVALID_ID);
            if (titleResId != INVALID_ID) {
                mTitleTextColorTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
                mTitleTextColorTypedValue.data = titleResId;
            }
            int subtitleResId = a.getResourceId(R.styleable.Toolbar_subtitleTextColor, INVALID_ID);
            if (subtitleResId != INVALID_ID) {
                mSubtitleTextColorTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
                mSubtitleTextColorTypedValue.data = subtitleResId;
            }
            a.recycle();
        } else {
            applyTitleTextAppearanceResource();
            applySubtitleTextAppearanceResource();
        }

        applyTitleTextColor();
        applySubtitleTextColor();

        applyNavigationIcon();
    }

    private void applyTitleTextAppearanceResource() {
        if (mTitleTextAppearanceTypedValue.isTypeNull() || mTitleTextAppearanceTypedValue.isDataInvalid()) {
            return;
        }
        if (mTitleTextAppearanceTypedValue.isTypeAttr()) {
            TypedArray a = getContext().obtainStyledAttributes(new int[]{mTitleTextAppearanceTypedValue.data});
            int ap = a.getResourceId(0, INVALID_ID);
            a.recycle();
            if (ap != INVALID_ID) {
                applyTitleTextAppearance(ap);
            }
        } else if (mTitleTextAppearanceTypedValue.isTypeRes()) {
            applyTitleTextAppearance(mTitleTextAppearanceTypedValue.data);
        }
    }

    private void applyTitleTextAppearance(int ap) {
        if (ap != INVALID_ID) {
            final TypedArray a = SkinCompatResources.getInstance()
                    .obtainStyledAttributes(getContext(), ap, R.styleable.SkinTextAppearance);
            int titleTextColor = a.getColor(R.styleable.SkinTextAppearance_android_textColor, 0);
            if (titleTextColor != 0) {
                setTitleTextColor(titleTextColor);
            }
            a.recycle();
        }
    }

    private void applySubtitleTextAppearanceResource() {
        if (mSubtitleTextAppearanceTypedValue.isTypeNull() || mSubtitleTextAppearanceTypedValue.isDataInvalid()) {
            return;
        }
        if (mSubtitleTextAppearanceTypedValue.isTypeAttr()) {
            TypedArray a = getContext().obtainStyledAttributes(new int[]{mSubtitleTextAppearanceTypedValue.data});
            int ap = a.getResourceId(0, INVALID_ID);
            a.recycle();
            if (ap != INVALID_ID) {
                applySubtitleTextAppearance(ap);
            }
        } else if (mSubtitleTextAppearanceTypedValue.isTypeRes()) {
            applySubtitleTextAppearance(mSubtitleTextAppearanceTypedValue.data);
        }
    }

    private void applySubtitleTextAppearance(int ap) {
        if (ap != INVALID_ID) {
            final TypedArray a = SkinCompatResources.getInstance()
                    .obtainStyledAttributes(getContext(), ap, R.styleable.SkinTextAppearance);
            int subtitleTextColor = a.getColor(R.styleable.SkinTextAppearance_android_textColor, 0);
            if (subtitleTextColor != 0) {
                setSubtitleTextColor(subtitleTextColor);
            }
            a.recycle();
        }
    }

    private void applyTitleTextColor() {
        if (mTitleTextColorTypedValue.isDataInvalid() || mTitleTextColorTypedValue.isTypeNull()) {
            return;
        }
        if (mTitleTextColorTypedValue.isTypeAttr()) {
            TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(
                    getContext(), new int[]{mTitleTextColorTypedValue.data});
            int titleTextColor = a.getColor(R.styleable.SkinTextAppearance_android_textColor, 0);
            if (titleTextColor != 0) {
                setTitleTextColor(titleTextColor);
            }
            a.recycle();
        } else if (mTitleTextColorTypedValue.isTypeRes()) {
            setTitleTextColor(SkinCompatResources.getInstance().getColor(mTitleTextColorTypedValue.data));
        }
    }

    private void applySubtitleTextColor() {
        if (mSubtitleTextColorTypedValue.isDataInvalid() || mSubtitleTextColorTypedValue.isTypeNull()) {
            return;
        }
        if (mSubtitleTextColorTypedValue.isTypeAttr()) {
            TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(
                    getContext(), new int[]{mSubtitleTextColorTypedValue.data});
            int subtitleTextColor = a.getColor(R.styleable.SkinTextAppearance_android_textColor, 0);
            if (subtitleTextColor != 0) {
                setSubtitleTextColor(subtitleTextColor);
            }
            a.recycle();
        } else if (mSubtitleTextColorTypedValue.isTypeRes()) {
            setSubtitleTextColor(SkinCompatResources.getInstance().getColor(mSubtitleTextColorTypedValue.data));
        }
    }

    private void applyNavigationIcon() {
        if (mNavigationIconTypedValue.isDataInvalid() || mNavigationIconTypedValue.isTypeNull()) {
            return;
        }
        if (mNavigationIconTypedValue.isTypeAttr()) {
            TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(
                    getContext(), new int[]{mNavigationIconTypedValue.data});
            setNavigationIcon(a.getDrawable(0));
        } else if (mNavigationIconTypedValue.isTypeRes()) {
            setNavigationIcon(SkinCompatResources.getInstance().getDrawable(mNavigationIconTypedValue.data));
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
    public void setNavigationIcon(@DrawableRes int resId) {
        super.setNavigationIcon(resId);
        mNavigationIconTypedValue.type = SkinCompatTypedValue.TYPE_RESOURCES;
        mNavigationIconTypedValue.data = resId;
        applyNavigationIcon();
    }

    @Override
    public void applySkin() {
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.applySkin();
        }

        if (!SkinCompatManager.getInstance().isCompatibleMode()) {
            applyTitleTextAppearanceResource();
            applySubtitleTextAppearanceResource();
        }

        applyTitleTextColor();
        applySubtitleTextColor();

        applyNavigationIcon();
    }

}
