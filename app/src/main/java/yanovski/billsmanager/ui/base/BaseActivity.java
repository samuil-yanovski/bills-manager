package yanovski.billsmanager.ui.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
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

    protected ViewGroup mDrawerContainer;
    @Optional
    @InjectView(R.id.home)
    protected View mHome;
    @Optional
    @InjectView(R.id.calendar)
    protected View mCalendar;
    @InjectView(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;
    @InjectView(R.id.main_toolbar)
    protected Toolbar mToolbar;

    private ActionBarDrawerToggle mDrawerToggle;

    protected int getLayoutId() {
        return R.layout.activity_generic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mDrawerContainer = (ViewGroup) findViewById(R.id.drawer);
        View.inflate(this, R.layout.drawer_generic, mDrawerContainer);
        ButterKnife.inject(this);

        ViewUtils.setFieldBackground(mHome);
        ViewUtils.setFieldBackground(mCalendar);

        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
            R.string.open_drawer_description, R.string.close_drawer_description);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
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
