package skin.support.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;

import skin.support.content.res.SkinCompatResources;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;

/**
 * Created by ximsfei on 2017/3/25.
 */

public class SkinCompatThemeUtils {
    private static final int[] APPCOMPAT_COLOR_PRIMARY_ATTRS = {
            android.support.v7.appcompat.R.attr.colorPrimary
    };
    private static final int[] APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS = {
            android.support.v7.appcompat.R.attr.colorPrimaryDark
    };
    private static final int[] APPCOMPAT_COLOR_ACCENT_ATTRS = {
            android.support.v7.appcompat.R.attr.colorAccent
    };

    public static int getColorPrimaryResId(Context context) {
        return getResId(context, APPCOMPAT_COLOR_PRIMARY_ATTRS);
    }

    public static int getColorPrimaryDarkResId(Context context) {
        return getResId(context, APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS);
    }

    public static int getColorAccentResId(Context context) {
        return getResId(context, APPCOMPAT_COLOR_ACCENT_ATTRS);
    }

    public static int getTextColorPrimaryResId(Context context) {
        return getResId(context, new int[]{android.R.attr.textColorPrimary});
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static int getStatusBarColorResId(Context context) {
        return getResId(context, new int[]{android.R.attr.statusBarColor});
    }

    public static int getWindowBackgroundResId(Context context) {
        return getResId(context, new int[]{android.R.attr.windowBackground});
    }

    public static int getStatusBarColor(Context context) {
        int color = 0;
        TypedArray a;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            a = SkinCompatResources.getInstance()
                    .obtainStyledAttributes(context, new int[]{android.R.attr.statusBarColor});
            if (a.hasValue(0)) {
                color = a.getColor(0, 0);
            }
            a.recycle();
        }
        if (color == 0) {
            a = SkinCompatResources.getInstance()
                    .obtainStyledAttributes(context, APPCOMPAT_COLOR_PRIMARY_ATTRS);
            if (a.hasValue(0)) {
                color = a.getColor(0, 0);
            }
            a.recycle();
        }
        return color;
    }

    public static Drawable getWindowBackgroundDrawable(Context context) {
        Drawable drawable = null;
        TypedArray a = SkinCompatResources.getInstance()
                .obtainStyledAttributes(context, new int[]{android.R.attr.windowBackground});
        if (a.hasValue(0)) {
            drawable = a.getDrawable(0);
        }
        a.recycle();
        return drawable;
    }

    private static int getResId(Context context, int[] attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs);
        final int resId = a.getResourceId(0, INVALID_ID);
        a.recycle();
        return resId;
    }
}
