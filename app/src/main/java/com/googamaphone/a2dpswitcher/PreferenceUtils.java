package com.googamaphone.a2dpswitcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.preference.PreferenceManager;

public class PreferenceUtils {
    private static final int[] ATTRS_DIALOG_THEME = new int[] { android.R.attr.dialogTheme };

    public static final String PREF_THEME = "theme";

    public static final int THEME_PLATFORM = -1;
    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;

    public static void setUseDarkTheme(SharedPreferences prefs, boolean useDarkTheme) {
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(PREF_THEME, useDarkTheme ? THEME_DARK : THEME_LIGHT);
        editor.apply();
    }

    public static boolean getUseDarkTheme(SharedPreferences prefs) {
        int theme = prefs.getInt(PREF_THEME, THEME_PLATFORM);
        if (theme == THEME_PLATFORM) {
            if (Build.VERSION.SDK_INT > 19) {
                theme = THEME_LIGHT;
            } else {
                theme = THEME_DARK;
            }
        }

        final boolean useDarkTheme = theme == THEME_DARK;
        return useDarkTheme;
    }

    public static void applyActivityTheme(Context context) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final int theme = getUseDarkTheme(prefs) ? R.style.Theme_Dark : R.style.Theme_Light;
        context.setTheme(theme);
    }

    public static void applyDialogTheme(Context context) {
        applyActivityTheme(context);

        final TypedArray a = context.obtainStyledAttributes(ATTRS_DIALOG_THEME);
        final int theme = a.getResourceId(0, 0);
        context.setTheme(theme);
    }
}
