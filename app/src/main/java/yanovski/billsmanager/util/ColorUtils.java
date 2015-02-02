package yanovski.billsmanager.util;

import android.content.Context;
import android.content.res.TypedArray;

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
}
