package yanovski.billsmanager.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

import yanovski.billsmanager.BillsManagerApplication;
import yanovski.billsmanager.R;

/**
 * Created by Samuil on 2/2/2015.
 */
public class ColorUtils {
    public static int getPrimaryColor() {
        Context context = BillsManagerApplication.context;

        int[] colorAttrs = new int[]{android.R.attr.colorPrimary};
        TypedArray colorAttributes = context.getTheme()
            .obtainStyledAttributes(colorAttrs);
        int color = colorAttributes.getColor(0, context.getResources()
            .getColor(R.color.primary));
        colorAttributes.recycle();

        return color;
    }

    /**
     * Lightens a color by a given factor.
     *
     * @param color
     *            The color to lighten
     * @param factor
     *            The factor to lighten the color. 0 will make the color unchanged. 1 will make the
     *            color white.
     * @return lighter version of the specified color.
     */
    public static int lighter(int color, float factor) {
        int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
        return Color.argb(Color.alpha(color), red, green, blue);
    }
}
