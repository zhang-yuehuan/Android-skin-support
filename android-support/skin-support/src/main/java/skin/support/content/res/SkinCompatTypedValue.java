package skin.support.content.res;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;

public class SkinCompatTypedValue {
    public static final int TYPE_NULL = 0;
    public static final int TYPE_ATTR = 1;
    public static final int TYPE_RES = 2;
    protected Context context;
    protected AttributeSet set;
    protected int defStyleAttr = INVALID_ID;
    protected int defStyleRes = INVALID_ID;
    protected int[] attrs;
    protected int index;
    protected int type = TYPE_NULL;
    protected int data = INVALID_ID;

    public int getType() {
        return type;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
        this.type = TYPE_RES;
    }

    public void setData(int type, int data) {
        this.type = type;
        this.data = data;
    }

    public boolean isTypeNull() {
        return type == TYPE_NULL;
    }

    public boolean isTypeRes() {
        return type == TYPE_RES;
    }

    public boolean isTypeAttr() {
        return type == TYPE_ATTR;
    }

    public boolean isDataInvalid() {
        return data == INVALID_ID;
    }

    public void reset() {
        type = TYPE_NULL;
        data = INVALID_ID;
    }

    public int getColor() {
        int color = 0;
        if (isTypeNull()) {
            if (defStyleAttr != INVALID_ID || defStyleRes != INVALID_ID) {
                TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(context, set, attrs, defStyleAttr, defStyleRes);
                color = a.getColor(index, 0);
                a.recycle();
            }
        } else if (isTypeAttr()) {
            if (data != INVALID_ID) {
                TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(context, new int[]{data});
                color = a.getColor(0, 0);
                a.recycle();
            }
        } else if (isTypeRes()) {
            if (data != INVALID_ID) {
                color = SkinCompatResources.getInstance().getColor(data);
            }
        }
        return color;
    }

    public ColorStateList getColorStateList() {
        ColorStateList colorStateList = null;
        if (isTypeNull()) {
            if (defStyleAttr != INVALID_ID || defStyleRes != INVALID_ID) {
                TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(context, set, attrs, defStyleAttr, defStyleRes);
                colorStateList = a.getColorStateList(index);
                a.recycle();
            }
        } else if (isTypeAttr()) {
            if (data != INVALID_ID) {
                TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(context, new int[]{data});
                colorStateList = a.getColorStateList(0);
                a.recycle();
            }
        } else if (isTypeRes()) {
            if (data != INVALID_ID) {
                colorStateList = SkinCompatResources.getInstance().getColorStateList(data);
            }
        }
        return colorStateList;
    }

    public Drawable getDrawable() {
        Drawable drawable = null;
        if (isTypeNull()) {
            if (defStyleAttr != INVALID_ID || defStyleRes != INVALID_ID) {
                TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(context, set, attrs, defStyleAttr, defStyleRes);
                drawable = a.getDrawable(index);
                a.recycle();
            }
        } else if (isTypeAttr()) {
            if (data != INVALID_ID) {
                TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(context, new int[]{data});
                drawable = a.getDrawable(0);
                a.recycle();
            }
        } else if (isTypeRes()) {
            if (data != INVALID_ID) {
                drawable = SkinCompatResources.getInstance().getDrawable(data);
            }
        }
        return drawable;
    }

    public TypedArray obtainStyledAttributes(int[] as) {
        int resourceId = INVALID_ID;
        if (type == TYPE_NULL) {
            TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(context, set, attrs, defStyleAttr, defStyleRes);
            resourceId = a.getResourceId(this.index, INVALID_ID);
            a.recycle();
            return SkinCompatResources.getInstance().obtainStyledAttributes(context, resourceId, false, as, true);
        } else if (data != INVALID_ID) {
            if (type == TYPE_ATTR) {
                TypedArray a = SkinCompatResources.getInstance().obtainStyledAttributes(context, new int[]{data});
                resourceId = a.getResourceId(0, INVALID_ID);
                a.recycle();
                return SkinCompatResources.getInstance().obtainStyledAttributes(context, resourceId, false, as, true);
            } else if (type == TYPE_RES) {
                resourceId = data;
                return SkinCompatResources.getInstance().obtainStyledAttributes(context, resourceId, as);
            }
        }
        return SkinCompatResources.getInstance().obtainStyledAttributes(context, resourceId, as);
    }
}