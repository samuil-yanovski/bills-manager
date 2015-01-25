package yanovski.billsmanager.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import yanovski.billsmanager.BillsManagerApplication;
import yanovski.billsmanager.Constants;
import yanovski.billsmanager.R;
import yanovski.billsmanager.dao.DataManager;
import yanovski.billsmanager.daogen.Category;
import yanovski.billsmanager.daogen.Expense;
import yanovski.billsmanager.util.DateUtils;
import yanovski.billsmanager.util.ViewUtils;

public class EditExpenseActivity extends ActionBarActivity {

    public static final String EXPENSE_ID_KEY = "expense_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        if (savedInstanceState == null) {
            PlaceholderFragment placeholderFragment = new PlaceholderFragment();
            Intent intent = getIntent();
            if (intent.hasExtra(EXPENSE_ID_KEY)) {
                long expenseId = intent.getLongExtra(EXPENSE_ID_KEY, Constants.DEFAULT_ID);
                if (Constants.DEFAULT_ID != expenseId) {
                    Expense expense = BillsManagerApplication.expenseDao.load(expenseId);
                    placeholderFragment.setExpense(expense);
                }
            }

            if (intent.hasExtra(Constants.POSITION_KEY)) {
                int position =
                    intent.getIntExtra(Constants.POSITION_KEY, (int) Constants.DEFAULT_ID);
                if (Constants.DEFAULT_ID != position) {
                    placeholderFragment.setPosition(position);
                }
            }
            getSupportFragmentManager().beginTransaction()
                .add(R.id.container, placeholderFragment)
                .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_expense, menu);
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

        private static final String SELECTED_DATE_KEY = "selected_date";
        private static final String EXPENSE_KEY = "expense";
        private static final int DATE_REQUEST_CODE = 100;

        @InjectView(R.id.category)
        Spinner category;
        @InjectView(R.id.name)
        EditText name;
        @InjectView(R.id.amount)
        EditText amount;
        @InjectView(R.id.comment)
        EditText comment;
        @InjectView(R.id.date)
        TextView date;
        @InjectView(R.id.delete)
        TextView delete;

        private DateTime selectedDate = DateTime.now();
        private Expense expense;
        private int position = (int) Constants.DEFAULT_ID;

        public Expense getExpense() {
            return expense;
        }

        public void setExpense(Expense expense) {
            this.expense = expense;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (null != savedInstanceState) {
                if (savedInstanceState.containsKey(SELECTED_DATE_KEY)) {
                    selectedDate = new DateTime(savedInstanceState.getLong(SELECTED_DATE_KEY));
                }
                if (savedInstanceState.containsKey(EXPENSE_KEY)) {
                    expense = (Expense) savedInstanceState.getSerializable(EXPENSE_KEY);
                }
                if (savedInstanceState.containsKey(Constants.POSITION_KEY)) {
                    position = savedInstanceState.getInt(Constants.POSITION_KEY);
                }
            }
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putLong(SELECTED_DATE_KEY, selectedDate.getMillis());
            outState.putSerializable(EXPENSE_KEY, expense);
            outState.putInt(Constants.POSITION_KEY, position);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_add_expense, container, false);
            ButterKnife.inject(this, rootView);

            List<Category> categories = BillsManagerApplication.categoryDao.loadAll();
            ArrayAdapter<Category> categoryArrayAdapter =
                new ArrayAdapter<Category>(getActivity(), android.R.layout.simple_list_item_1,
                    categories);
            categoryArrayAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line);
            category.setAdapter(categoryArrayAdapter);

            if (null != expense) {
                Double expenseAmount = expense.getAmount();
                amount.setText(Double.toString(expenseAmount));

                Category expenseCategory = expense.getCategory();
                int position = categoryArrayAdapter.getPosition(expenseCategory);
                category.setSelection(position);

                String expenseComment = expense.getComment();
                comment.setText(expenseComment);

                selectedDate = new DateTime(expense.getDate());

                String expenseName = expense.getName();
                name.setText(expenseName);
            } else {
                delete.setVisibility(View.GONE);
                expense = new Expense();
                expense.setId(Constants.DEFAULT_ID);
            }

            showDate();
            ViewUtils.setFieldBackground(date);

            return rootView;
        }


        protected void showDate() {
            String s = DateUtils.formatShowDate(selectedDate);
            date.setText(s);
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            ButterKnife.reset(this);
        }

        @OnClick(R.id.date)
        public void onDateRequested() {
            Intent intent = new Intent(getActivity(), DateActivity.class);
            intent.putExtra(DateActivity.SELECTED_DATE_KEY, selectedDate.getMillis());
            startActivityForResult(intent, DATE_REQUEST_CODE);
        }

        @OnClick(R.id.submit)
        public void submit() {
            Category selectedCategory = (Category) category.getSelectedItem();

            expense.setDate(selectedDate.toDate());
            expense.setName(name.getText()
                .toString());
            expense.setComment(comment.getText()
                .toString());
            expense.setCategory(selectedCategory);
            String amountText = amount.getText()
                .toString();

            try {
                expense.setAmount(Double.parseDouble(amount.getText()
                    .toString()));
            } catch (NumberFormatException nfe) {
                amount.setError(getString(R.string.amount_format_error));
                return;
            }
            DataManager.insertExpense(expense);

            close(false);
        }

        @OnClick(R.id.delete)
        public void delete() {
            BillsManagerApplication.expenseDao.delete(expense);
            close(true);
        }

        public void close(boolean deleted) {
            FragmentActivity activity = getActivity();
            Intent data = new Intent();
            if (Constants.DEFAULT_ID != position) {
                data.putExtra(Constants.POSITION_KEY, position);
            }
            data.putExtra(EditExpenseActivity.EXPENSE_ID_KEY, expense.getId());
            data.putExtra(Constants.DATE_KEY, selectedDate.toDate());
            data.putExtra(Constants.DELETED_KEY, deleted);
            activity.setResult(Activity.RESULT_OK, data);
            activity.finish();
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (Activity.RESULT_OK == resultCode) {
                if (DATE_REQUEST_CODE == requestCode) {
                    if (data.hasExtra(DateActivity.SELECTED_DATE_KEY)) {
                        selectedDate = new DateTime(
                            data.getLongExtra(DateActivity.SELECTED_DATE_KEY,
                                selectedDate.getMillis()));
                        showDate();
                    }
                }
            }
        }
    }
}
