package yanovski.billsmanager.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;

import org.joda.time.DateTime;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import yanovski.billsmanager.R;
import yanovski.billsmanager.decorators.AmountDecorator;
import yanovski.billsmanager.ui.base.BaseActivity;

public class CalendarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new PlaceholderFragment())
                .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        @InjectView(R.id.calendar_view)
        CalendarPickerView calendar;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
            ButterKnife.inject(this, rootView);

            List<CalendarCellDecorator> decorators = new ArrayList<>();
            decorators.add(new AmountDecorator());
            calendar.setDecorators(decorators);

            DateTime dateTime = DateTime.now();
            calendar.init(dateTime.minusMonths(12)
                .toDate(), dateTime.plusDays(1)
                .toDate());
            calendar.scrollToDate(dateTime.toDate());
            calendar.setOnDateSelectedListener(new OnExpenseDateSelectedListener(getActivity()));
            calendar.setOnInvalidDateSelectedListener(null);
            return rootView;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            ButterKnife.reset(this);
        }
    }

    private static class OnExpenseDateSelectedListener implements
        CalendarPickerView.OnDateSelectedListener {

        private WeakReference<Activity> activityReference;

        public OnExpenseDateSelectedListener(Activity activity) {
            activityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void onDateSelected(Date date) {
            Activity activity = activityReference.get();
            if (null != activity) {
                Intent intent = new Intent(activity, ExpensesActivity.class);
                intent.putExtra(ExpensesActivity.SELECTED_DATE_KEY, date);
                activity.startActivity(intent);
            }
        }

        @Override
        public void onDateUnselected(Date date) {

        }
    }
}
