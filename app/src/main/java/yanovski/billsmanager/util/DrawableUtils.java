package yanovski.billsmanager.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v7.graphics.Palette;

import yanovski.billsmanager.BillsManagerApplication;
import yanovski.billsmanager.R;

/**
 * Created by Samuil on 1/26/2015.
 */
public class DrawableUtils {
    public static Drawable getTintedDrawable(Resources res, @DrawableRes int drawableResId,
        @ColorRes int colorResId) {
        Drawable drawable = res.getDrawable(drawableResId);
        int color = res.getColor(colorResId);
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    public static Drawable getTintedSelector() {
        StateListDrawable selector = new StateListDrawable();

        Context context = BillsManagerApplication.context;
        int primaryColor = ColorUtils.getPrimaryColor();

        GradientDrawable selectedBackground = (GradientDrawable) context.getResources()
            .getDrawable(R.drawable.selected_item_background);
        selectedBackground.setStroke(Math.round(context.getResources()
            .getDimension(R.dimen.selector_stroke_width)), primaryColor);

        Bitmap fillImage = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        fillImage.eraseColor(primaryColor);
        Palette palette = Palette.generate(fillImage);

        GradientDrawable pressedBackground = (GradientDrawable) context.getResources()
            .getDrawable(R.drawable.pressed_item_background);
        pressedBackground.setColor(palette.getLightMutedColor(primaryColor));

        GradientDrawable focusedBackground = (GradientDrawable) context.getResources()
            .getDrawable(R.drawable.pressed_item_background);
        focusedBackground.setColor(palette.getLightVibrantColor(primaryColor));

        selector.addState(new int[]{android.R.attr.state_pressed}, pressedBackground);
        selector.addState(new int[]{android.R.attr.state_focused}, focusedBackground);
        selector.addState(new int[]{android.R.attr.state_activated}, selectedBackground);
        selector.addState(new int[]{android.R.attr.state_selected}, selectedBackground);
        selector.addState(new int[]{android.R.attr.state_checked}, selectedBackground);
        return selector;
    }
}
