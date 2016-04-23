package co.hkm.soltag;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;

/**
 * Created by zJJ on 4/24/2016.
 */
public class helper {
    public static Typeface getTypface(@Nullable String font_name, Context mContext) {
        if (font_name == null) return Typeface.DEFAULT;
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/" + font_name);
    }
}
