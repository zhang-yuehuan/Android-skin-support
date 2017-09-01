package skin.support.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;

import java.lang.reflect.Field;

import skin.support.R;
import skin.support.content.res.SkinCompatResources;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;
import static skin.support.widget.SkinCompatHelper.checkResourceId;

public class SkinCompatSwitchCompat extends SwitchCompat implements SkinCompatSupportable {
    private int mThumbResId = INVALID_ID;
    private int mTrackResId = INVALID_ID;
    private int mThumbTintListResId = INVALID_ID;
    private int mTrackTintListResId = INVALID_ID;
    private int mTextColorResId = INVALID_ID;
    private SkinCompatBackgroundHelper mBackgroundTintHelper;

    public SkinCompatSwitchCompat(Context context) {
        this(context, null);
    }

    public SkinCompatSwitchCompat(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.switchStyle);
    }

    public SkinCompatSwitchCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwitchCompat, defStyleAttr, 0);
        mThumbResId = a.getResourceId(R.styleable.SwitchCompat_android_thumb, INVALID_ID);
        mTrackResId = a.getResourceId(R.styleable.SwitchCompat_track, INVALID_ID);
        mThumbTintListResId = a.getResourceId(R.styleable.SwitchCompat_thumbTint, INVALID_ID);
        mTrackTintListResId = a.getResourceId(R.styleable.SwitchCompat_trackTint, INVALID_ID);

        final int appearance = a.getResourceId(R.styleable.SwitchCompat_switchTextAppearance, 0);
        if (appearance != 0) {
            setSwitchTextAppearance(context, appearance);
        }

        a.recycle();
        if (applyThumbResources()
                || applyTrackResources()
                || applyThumbTintListResources()
                || applyTrackTintListResources()) {
            refreshDrawableState();
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
    public void setSwitchTextAppearance(Context context, int resid) {
        super.setSwitchTextAppearance(context, resid);
        final TypedArray appearance = context.obtainStyledAttributes(resid, R.styleable.TextAppearance);

        mTextColorResId = appearance.getResourceId(R.styleable.TextAppearance_android_textColor, INVALID_ID);
        appearance.recycle();
        applyTextColorResources();
    }

    @Override
    public void setTrackResource(int resId) {
        mTrackResId = resId;
        applyTrackResources();
    }

    @Override
    public void setThumbResource(int resId) {
        mThumbResId = resId;
        applyThumbResources();
    }

    private boolean applyTextColorResources() {
        mTextColorResId = checkResourceId(mTextColorResId);
        if (mTextColorResId != INVALID_ID) {
            ColorStateList colorStateList = SkinCompatResources.getInstance().getColorStateList(mTextColorResId);
            if (colorStateList != null) {
                try {
                    Field textColors = SwitchCompat.class.getDeclaredField("mTextColors");
                    textColors.setAccessible(true);
                    textColors.set(this, colorStateList);
                    return true;
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    private boolean applyTrackTintListResources() {
        mTrackTintListResId = checkResourceId(mTrackTintListResId);
        if (mTrackTintListResId != INVALID_ID) {
            setTrackTintList(SkinCompatResources.getInstance().getColorStateList(mTrackTintListResId));
            return true;
        }
        return false;
    }

    private boolean applyThumbTintListResources() {
        mThumbTintListResId = checkResourceId(mThumbTintListResId);
        if (mThumbTintListResId != INVALID_ID) {
            setThumbTintList(SkinCompatResources.getInstance().getColorStateList(mThumbTintListResId));
            return true;
        }
        return false;
    }

    private boolean applyTrackResources() {
        mTrackResId = checkResourceId(mTrackResId);
        if (mTrackResId != INVALID_ID) {
            setTrackDrawable(SkinCompatResources.getInstance().getDrawable(mTrackResId));
            return true;
        }
        return false;
    }

    private boolean applyThumbResources() {
        mThumbResId = checkResourceId(mThumbResId);
        if (mThumbResId != INVALID_ID) {
            setThumbDrawable(SkinCompatResources.getInstance().getDrawable(mThumbResId));
            return true;
        }
        return false;
    }

    @Override
    public void applySkin() {
        if (applyThumbResources()
                || applyTrackResources()
                || applyThumbTintListResources()
                || applyTrackTintListResources()
                || applyTextColorResources()) {
            refreshDrawableState();
        }
    }
}
