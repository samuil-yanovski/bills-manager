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

import com.squareup.timessquare.CalendarPickerView;

import org.joda.time.DateTime;

import java.lang.ref.WeakReference;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import yanovski.billsmanager.R;
import yanovski.billsmanager.ui.base.BaseActivity;

public class DateActivity extends BaseActivity {

    public static final String SELECTED_DATE_KEY = "selected_date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            long current = 0;
            if (intent.hasExtra(SELECTED_DATE_KEY)) {
                current = intent.getLongExtra(SELECTED_DATE_KEY, current);
            } else {
                current = DateTime.now()
                    .getMillis();
            }

            PlaceholderFragment placeholderFragment = new PlaceholderFragment();
            placeholderFragment.setCurrentDate(current);
            getSupportFragmentManager().beginTransaction()
                .add(R.id.container, placeholderFragment)
                .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_date, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public static final String CURRENT_DATE_MILISECONDS_KEY = "current_date_miliseconds";

        @InjectView(R.id.calendar_view)
        CalendarPickerView calendar;

        private long currentDate = 0;

        public long getCurrentDate() {
            return currentDate;
        }

        public void setCurrentDate(long currentDate) {
            this.currentDate = currentDate;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (null != savedInstanceState) {
                currentDate = savedInstanceState.getLong(CURRENT_DATE_MILISECONDS_KEY);
            } else {
                currentDate = DateTime.now()
                    .getMillis();
            }
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putLong(CURRENT_DATE_MILISECONDS_KEY, currentDate);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_date, container, false);
            ButterKnife.inject(this, rootView);

            DateTime dateTime = new DateTime(currentDate);
            calendar.init(dateTime.minusMonths(6)
                .toDate(), dateTime.plusDays(1)
                .toDate())
                .withSelectedDate(dateTime.toDate());
            calendar.setOnDateSelectedListener(new DefaultOnDateSelectedListener(getActivity()));
            return rootView;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            ButterKnife.reset(this);
        }
    }

    public static class DefaultOnDateSelectedListener implements
        CalendarPickerView.OnDateSelectedListener {
        private WeakReference<Activity> activityReference;

        public DefaultOnDateSelectedListener(Activity activity) {
            activityReference = new WeakReference<Activity>(activity);
        }


        @Override
        public void onDateSelected(Date date) {
            Activity activity = activityReference.get();
            if (null != activity) {
                Intent intent = new Intent();
                intent.putExtra(DateActivity.SELECTED_DATE_KEY, date.getTime());

                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }
        }

        @Override
        public void onDateUnselected(Date date) {

        }
    }
}
