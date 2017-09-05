package skin.support.widget;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.SeekBar;

import skin.support.R;
import skin.support.SkinCompatManager;
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

        SkinCompatTypedValue.getValue(attrs, R.styleable.AppCompatSeekBar, R.styleable.AppCompatSeekBar_android_thumb, mThumbTypedValue);
        applySkin();
    }

    @Override
    public void applySkin() {
        super.applySkin();
        if (!mThumbTypedValue.isDataInvalid()) {
            if (mThumbTypedValue.isTypeAttr()) {
                TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(
                        mView.getContext(), new int[]{mThumbTypedValue.data});
                mView.setThumb(a.getDrawable(0));
                a.recycle();
            } else if (mThumbTypedValue.isTypeRes()) {
                mView.setThumb(SkinCompatResources.getInstance().getDrawable(mThumbTypedValue.data));
            }
        }
    }
}
