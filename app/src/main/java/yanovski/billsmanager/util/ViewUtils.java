package yanovski.billsmanager.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.TypedValue;
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

    public static void setCheckableBackground(View target) {
        // Attribute array
        int[] attrs = new int[]{android.R.attr.selectableItemBackground};

        Context context = BillsManagerApplication.context;
        TypedArray a = context.getTheme()
            .obtainStyledAttributes(attrs);
        // Drawable held by attribute 'selectableItemBackground' is at index '0'
        Drawable d = a.getDrawable(0);
        a.recycle();

        int[] colorAttrs = new int[]{android.R.attr.colorPrimary};
        TypedArray c = context.getTheme()
            .obtainStyledAttributes(colorAttrs);
        int originalColor = c.getColor(0, context.getResources()
            .getColor(R.color.primary));

        float[] hsv = new float[3];
        Color.colorToHSV(originalColor, hsv);
        hsv[2] = 1.0f - 0.1f * (1.0f - hsv[2]);
        int color = Color.HSVToColor(hsv);

        if (d instanceof StateListDrawable) {
            ((StateListDrawable) d).addState(new int[]{android.R.attr.state_activated},
                new ColorDrawable(color));
            ((StateListDrawable) d).addState(new int[]{android.R.attr.state_selected},
                new ColorDrawable(color));
            ((StateListDrawable) d).addState(new int[]{android.R.attr.state_checked},
                new ColorDrawable(color));
        }
        StateListDrawable dd = (StateListDrawable) d;

        StateListDrawable sld = new StateListDrawable();
        dd.setState(new int[]{android.R.attr.state_pressed});
        sld.addState(new int[]{android.R.attr.state_pressed}, dd.getCurrent());
        dd.setState(new int[]{android.R.attr.state_focused});
        sld.addState(new int[]{android.R.attr.state_focused}, dd.getCurrent());


//                Drawable selectedBackground = DrawableUtils.getTintedDrawable(context.getResources(), R.drawable.selected_item_background, R.color.primary);
        GradientDrawable selectedBackground = (GradientDrawable) context.getResources()
                    .getDrawable(R.drawable.selected_item_background);
                selectedBackground.setStroke((int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources()
                        .getDisplayMetrics()), originalColor);


        //        Shape s = new RectShape();
        //        ShapeDrawable sd = new ShapeDrawable(s);
        //        Paint paint = sd.getPaint();
        //        paint.setStyle(Paint.Style.STROKE);
        //        paint.setColor(originalColor);
        //        paint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics()));

        //        selectedBackground = sd;
        sld.addState(new int[]{android.R.attr.state_activated}, selectedBackground);
        sld.addState(new int[]{android.R.attr.state_selected}, selectedBackground);
        sld.addState(new int[]{android.R.attr.state_checked}, selectedBackground);
        sld.addState(new int[0], new ColorDrawable(context.getResources()
            .getColor(android.R.color.transparent)));

        int paddingBottom = target.getPaddingBottom();
        int paddingLeft = target.getPaddingLeft();
        int paddingRight = target.getPaddingRight();
        int paddingTop = target.getPaddingTop();

        // Set the background to 'ld'
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            target.setBackground(sld);
        } else {
            target.setBackgroundDrawable(sld);
        }
        target.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }
}
