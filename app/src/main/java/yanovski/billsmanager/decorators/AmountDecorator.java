package yanovski.billsmanager.decorators;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarCellView;
import com.squareup.timessquare.MonthCellDescriptor;

import java.util.Date;

import yanovski.billsmanager.dao.DataManager;

/**
 * Created by Samuil on 2/8/2015.
 */
public class AmountDecorator implements CalendarCellDecorator {

    @Override
    public void decorate(CalendarCellView calendarCellView,
        MonthCellDescriptor monthCellDescriptor) {
        Date day = monthCellDescriptor.getDate();
        double amount = DataManager.getTotalExpensesForDay(day);

        String date = Integer.toString(monthCellDescriptor.getValue());
        String formattedAmount = DataManager.getFormattedTotalExpensesForDay(amount);

        SpannableString text =
            new SpannableString(date + System.getProperty("line.separator") + formattedAmount);
        text.setSpan(new RelativeSizeSpan(0.6f), 0, text.length(),
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        calendarCellView.setText(text);
    }
}
