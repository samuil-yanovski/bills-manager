package yanovski.billsmanager.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;

import org.lucasr.twowayview.ItemClickSupport;
import org.lucasr.twowayview.widget.DividerItemDecoration;
import org.lucasr.twowayview.widget.TwoWayView;

import java.lang.ref.WeakReference;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import yanovski.billsmanager.BillsManagerApplication;
import yanovski.billsmanager.Constants;
import yanovski.billsmanager.R;
import yanovski.billsmanager.adapter.ExpensesAdapter;
import yanovski.billsmanager.daogen.Expense;
import yanovski.billsmanager.daogen.ExpenseDao;


public class ExpensesActivity extends ActionBarActivity {

    public static final int EDIT_EXPENSE_REQUEST_CODE = 101;
    public static final int ADD_EXPENSE_REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new PlaceholderFragment())
                .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_expenses, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
            if (EDIT_EXPENSE_REQUEST_CODE == requestCode) {
                if (data.hasExtra(Constants.POSITION_KEY)) {
                    int position =
                        data.getIntExtra(Constants.POSITION_KEY, (int) Constants.DEFAULT_ID);

                    boolean deleted = data.getBooleanExtra(Constants.DELETED_KEY, false);

                    PlaceholderFragment fragment =
                        (PlaceholderFragment) getSupportFragmentManager().findFragmentById(
                            R.id.container);
                    if (Constants.DEFAULT_ID != position) {
                        if (deleted) {
                            fragment.deleteItem(position);
                        } else {
                            fragment.refreshItem(position);
                        }
                    }
                }
            }
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        @InjectView(R.id.expenses)
        TwoWayView expensesList;
        @InjectView(R.id.add)
        FloatingActionButton add;

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_expenses, container, false);
            ButterKnife.inject(this, rootView);

            ExpensesAdapter adapter = new ExpensesAdapter();
            adapter.setExpenses(BillsManagerApplication.expenseDao.loadAll());
            //            expensesList.setLayoutManager(new LinearLayoutManager(getActivity()));
            expensesList.setAdapter(adapter);
            ItemClickSupport itemClickSupport = ItemClickSupport.addTo(expensesList);
            itemClickSupport.setOnItemClickListener(new OnExpenseClickListener(getActivity()));

            expensesList.addItemDecoration(new DividerItemDecoration(
                BillsManagerApplication.context.getResources()
                    .getDrawable(R.drawable.divider)));

            add.attachToRecyclerView(expensesList);
            return rootView;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            ButterKnife.reset(this);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (Activity.RESULT_OK == resultCode) {
                if (ADD_EXPENSE_REQUEST_CODE == requestCode) {
                    if (data.hasExtra(Constants.DATE_KEY) &&
                        data.hasExtra(EditExpenseActivity.EXPENSE_ID_KEY)) {
                        Date expenseDate = (Date) data.getSerializableExtra(Constants.DATE_KEY);
                        long expenseId = data.getLongExtra(EditExpenseActivity.EXPENSE_ID_KEY,
                            Constants.DEFAULT_ID);

                        if (null != expenseDate && Constants.DEFAULT_ID != expenseId) {
                            Expense expense = BillsManagerApplication.expenseDao.load(expenseId);

                            int position = (int) BillsManagerApplication.expenseDao.queryBuilder()
                                .where(ExpenseDao.Properties.Date.gt(expenseDate))
                                .count();

                            insertItem(position, expense);
                        }
                    }
                }
            }
        }

        @OnClick(R.id.add)
        public void add() {
            Intent intent = new Intent(getActivity(), EditExpenseActivity.class);
            startActivityForResult(intent, ExpensesActivity.ADD_EXPENSE_REQUEST_CODE);
        }

        public void refreshItem(int position) {
            expensesList.getAdapter()
                .notifyItemChanged(position);
        }

        public void deleteItem(int position) {
            ExpensesAdapter adapter = (ExpensesAdapter) expensesList.getAdapter();
            adapter.removeItem(position);
            adapter.notifyItemRemoved(position);
        }

        public void insertItem(int position, Expense expense) {
            ExpensesAdapter adapter = (ExpensesAdapter) expensesList.getAdapter();
            adapter.addItem(position, expense);
            adapter.notifyItemInserted(position);
        }
    }

    public static class OnExpenseClickListener implements ItemClickSupport.OnItemClickListener {
        private WeakReference<Activity> activityReference;

        public OnExpenseClickListener(Activity activity) {
            activityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void onItemClick(RecyclerView recyclerView, View view, int position, long id) {
            Activity activity = activityReference.get();
            if (null != activity) {
                long expenseId = recyclerView.getAdapter()
                    .getItemId(position);

                Intent intent = new Intent(activity, EditExpenseActivity.class);
                intent.putExtra(EditExpenseActivity.EXPENSE_ID_KEY, expenseId);
                intent.putExtra(Constants.POSITION_KEY, position);
                activity.startActivityForResult(intent, EDIT_EXPENSE_REQUEST_CODE);
            }
        }
    }
}
