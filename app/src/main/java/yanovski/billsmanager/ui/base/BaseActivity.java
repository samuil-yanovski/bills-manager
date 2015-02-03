package yanovski.billsmanager.ui.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import yanovski.billsmanager.R;
import yanovski.billsmanager.util.ViewUtils;

/**
 * Created by Samuil on 2/3/2015.
 */
public class BaseActivity extends ActionBarActivity {

    @InjectView(R.id.drawer)
    protected ViewGroup drawer;
    @Optional
    @InjectView(R.id.home)
    protected ViewGroup home;
    @Optional
    @InjectView(R.id.calendar)
    protected ViewGroup calendar;
    @InjectView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    protected int getLayoutId() {
        return R.layout.activity_generic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.inject(this);

        View.inflate(this, R.layout.drawer_generic, drawer);

        ViewUtils.setFieldBackground(home);
        ViewUtils.setFieldBackground(calendar);

        mDrawerToggle =
            new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer_description,
                R.string.close_drawer_description);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // <---- added
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) { // <---- added
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState(); // important statetment for drawer to
        // identify
        // its state
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) { // <---- added
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Optional
    @OnClick(R.id.home)
    protected void onHomePressed() {

    }

    @Optional
    @OnClick(R.id.calendar)
    protected void onCalendarPressed() {

    }
}
