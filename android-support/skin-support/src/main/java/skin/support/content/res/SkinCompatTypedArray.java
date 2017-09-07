package skin.support.content.res;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.StyleRes;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;

import static skin.support.content.res.SkinCompatTypedValue.TYPE_ATTR;
import static skin.support.content.res.SkinCompatTypedValue.TYPE_NULL;
import static skin.support.content.res.SkinCompatTypedValue.TYPE_RES;
import static skin.support.widget.SkinCompatHelper.INVALID_ID;

public class SkinCompatTypedArray {
    private Context context;
    private AttributeSet set;
    private int defStyleAttr = INVALID_ID;
    private int defStyleRes = INVALID_ID;
    private int[] attrs;

    private int[] types;
    private int[] data;

    private SkinCompatTypedArray(Context context, AttributeSet set,
                                 @StyleableRes int[] attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes,
                                 int[] types, int[] data) {
        this.context = context;
        this.set = set;
        this.defStyleAttr = defStyleAttr;
        this.defStyleRes = defStyleRes;
        this.attrs = attrs;
        this.types = types;
        this.data = data;
    }

    public SkinCompatTypedArray getValue(int index, SkinCompatTypedValue outValue) {
        if (index < types.length && index < data.length) {
            outValue.context = context;
            outValue.set = set;
            outValue.defStyleAttr = defStyleAttr;
            outValue.defStyleRes = defStyleRes;
            outValue.attrs = attrs;
            outValue.index = index;
            outValue.type = types[index];
            outValue.data = data[index];
        }
        return this;
    }

    public static SkinCompatTypedArray obtain(Context context, AttributeSet set,
                                              @StyleableRes int[] attrs, @AttrRes int defStyleAttr) {
        return obtain(context, set, attrs, defStyleAttr, 0, -1);
    }

    public static SkinCompatTypedArray obtain(Context context, AttributeSet set,
                                              @StyleableRes int[] attrs, @AttrRes int defStyleAttr, int index) {
        return obtain(context, set, attrs, defStyleAttr, 0, index);
    }

    public static SkinCompatTypedArray obtain(Context context, AttributeSet set,
                                              @StyleableRes int[] attrs, @AttrRes int defStyleAttr,
                                              @StyleRes int defStyleRes, int index) {
        int styleValue = set.getStyleAttribute();
        if (styleValue != INVALID_ID) {
            String type = context.getResources().getResourceTypeName(styleValue);
            if ("attr".equals(type)) {
                defStyleAttr = styleValue;
                defStyleRes = INVALID_ID;
            } else if ("style".equals(type)) {
                defStyleAttr = INVALID_ID;
                defStyleRes = styleValue;
            }
        }

        int[] types = new int[attrs.length];
        int[] data = new int[attrs.length];

        if (index >= 0 && index < attrs.length) {
            resolveResource(set, attrs, types, data, index);
        } else {
            for (int attrIndex = 0; attrIndex < attrs.length; attrIndex++) {
                resolveResource(set, attrs, types, data, attrIndex);
            }
        }

        return new SkinCompatTypedArray(context, set, attrs, defStyleAttr, defStyleRes, types, data);
    }

    private static void resolveResource(AttributeSet set,
                                        int[] attrs, int[] types, int[] data, int index) {
        types[index] = TYPE_NULL;
        data[index] = INVALID_ID;
        for (int setIndex = 0; setIndex < set.getAttributeCount(); setIndex++) {
            if (set.getAttributeNameResource(setIndex) == attrs[index]) {
                String attrValue = set.getAttributeValue(setIndex);
                if (attrValue.startsWith("?")) {
                    types[index] = TYPE_ATTR;
                    data[index] = Integer.valueOf(attrValue.substring(1));
                } else if (attrValue.startsWith("@")) {
                    types[index] = TYPE_RES;
                    data[index] = Integer.valueOf(attrValue.substring(1));
                }
                break;
            }
        }
    }
}
