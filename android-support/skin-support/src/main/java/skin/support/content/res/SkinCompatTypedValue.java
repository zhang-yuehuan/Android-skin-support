package skin.support.content.res;

import android.util.AttributeSet;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;

public class SkinCompatTypedValue {
    public static final int TYPE_NULL = 0;
    public static final int TYPE_ATTR = 1;
    public static final int TYPE_RESOURCES = 2;
    public int type = TYPE_NULL;
    public int data = INVALID_ID;


    static public SkinCompatTypedValue getValue(AttributeSet set, int[] attrs, int index) {
        SkinCompatTypedValue outValue = new SkinCompatTypedValue();
        if (attrs == null || index >= attrs.length)
            return outValue;

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
        return outValue;
    }
}
