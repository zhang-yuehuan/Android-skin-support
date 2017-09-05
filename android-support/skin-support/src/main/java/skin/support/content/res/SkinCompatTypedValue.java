package skin.support.content.res;

import android.util.AttributeSet;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;

public class SkinCompatTypedValue {
    public static final int TYPE_NULL = 0;
    public static final int TYPE_ATTR = 1;
    public static final int TYPE_RESOURCES = 2;
    public int type = TYPE_NULL;
    public int data = INVALID_ID;

    public boolean isTypeNull() {
        return type == TYPE_NULL;
    }

    public boolean isTypeRes() {
        return type == TYPE_RESOURCES;
    }

    public boolean isTypeAttr() {
        return type == TYPE_ATTR;
    }

    public boolean isDataInvalid() {
        return data == INVALID_ID;
    }

    static public void getValue(AttributeSet set, int[] attrs, int index, SkinCompatTypedValue outValue) {
        if (outValue == null || attrs == null || index >= attrs.length) return;

        for (int i = 0; i < set.getAttributeCount(); i++) {
            int attrResource = set.getAttributeNameResource(i);
            if (attrResource == attrs[index]) {
                String attrValue = set.getAttributeValue(i);
                if (attrValue != null) {
                    if (attrValue.startsWith("@")) {
                        outValue.type = TYPE_RESOURCES;
                        outValue.data = Integer.valueOf(attrValue.substring(1));
                    } else if (attrValue.startsWith("?")) {
                        outValue.type = TYPE_ATTR;
                        outValue.data = Integer.valueOf(attrValue.substring(1));
                    }
                }
                break;
            }
        }
    }
}
