package skin.support.widget;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;

import skin.support.R;
import skin.support.content.res.SkinCompatResources;
import skin.support.content.res.SkinCompatTypedValue;

/**
 * Created by ximsfei on 17-1-21.
 */
public class SkinCompatSeekBarHelper extends SkinCompatProgressBarHelper {
    private final SeekBar mView;

    private SkinCompatTypedValue mThumbTypedValue = new SkinCompatTypedValue();

    public SkinCompatSeekBarHelper(SeekBar view) {
        super(view);
        mView = view;
    }

    @Override
    void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        super.loadFromAttributes(attrs, defStyleAttr);
        SkinCompatTypedValue.getValue(
                mView.getContext(),
                attrs,
                defStyleAttr,
                R.styleable.AppCompatSeekBar,
                R.styleable.AppCompatSeekBar_android_thumb,
                mThumbTypedValue);
        applySkin();
    }

    @Override
    public void applySkin() {
        super.applySkin();
        Drawable drawable = mThumbTypedValue.getDrawable();
        if (drawable != null) {
            mView.setThumb(drawable);
        }
    }
}
