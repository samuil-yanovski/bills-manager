package yanovski.billsmanager.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.View;

import yanovski.billsmanager.BillsManagerApplication;
import yanovski.billsmanager.R;

/**
 * Created by Samuil on 1/25/2015.
 */
public class ViewUtils {

    public static void setSelectableBackground(View target) {
        // Attribute array
        int[] attrs = new int[]{android.R.attr.selectableItemBackground};

        Context context = BillsManagerApplication.context;
        TypedArray a = context.getTheme()
            .obtainStyledAttributes(attrs);
        // Drawable held by attribute 'selectableItemBackground' is at index '0'
        Drawable d = a.getDrawable(0);
        a.recycle();

        int paddingBottom = target.getPaddingBottom();
        int paddingLeft = target.getPaddingLeft();
        int paddingRight = target.getPaddingRight();
        int paddingTop = target.getPaddingTop();

        // Set the background to 'ld'
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            target.setBackground(d);
        } else {
            target.setBackgroundDrawable(d);
        }
        target.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public static void setFieldBackground(View target) {
        // Attribute array
        int[] attrs = new int[]{android.R.attr.selectableItemBackground};

        Context context = BillsManagerApplication.context;
        TypedArray a = context.getTheme()
            .obtainStyledAttributes(attrs);
        // Drawable held by attribute 'selectableItemBackground' is at index '0'
        Drawable d = a.getDrawable(0);
        a.recycle();
        LayerDrawable ld = new LayerDrawable(new Drawable[]{

            // Nine Path Drawable
            context.getResources()
                .getDrawable(R.drawable.field_background),

            // Drawable from attribute
            d});

        int paddingBottom = target.getPaddingBottom();
        int paddingLeft = target.getPaddingLeft();
        int paddingRight = target.getPaddingRight();
        int paddingTop = target.getPaddingTop();

        // Set the background to 'ld'
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            target.setBackground(ld);
        } else {
            target.setBackgroundDrawable(ld);
        }
        target.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }
}
