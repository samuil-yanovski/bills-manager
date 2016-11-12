package yanovski.billsmanager.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.lucasr.twowayview.ItemSelectionSupport;
import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.widget.GridLayoutManager;
import org.lucasr.twowayview.widget.SpacingItemDecoration;
import org.lucasr.twowayview.widget.TwoWayView;

import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import yanovski.billsmanager.BillsManagerApplication;
import yanovski.billsmanager.Constants;
import yanovski.billsmanager.R;
import yanovski.billsmanager.adapter.CategoriesAdapter;
import yanovski.billsmanager.dao.DataManager;
import yanovski.billsmanager.daogen.Category;
import yanovski.billsmanager.daogen.CategoryDao;
import yanovski.billsmanager.daogen.Expense;
import yanovski.billsmanager.ui.base.BaseActivity;
import yanovski.billsmanager.util.DateUtils;
import yanovski.billsmanager.util.ViewUtils;

public class EditExpenseActivity extends BaseActivity {

    public static final String EXPENSE_ID_KEY = "expense_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Intent intent = getIntent();
        if (intent.hasExtra(EXPENSE_ID_KEY)) {
            getMenuInflater().inflate(R.menu.menu_edit_expense, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_add_expense, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        PlaceholderFragment placeholderFragment =
            (PlaceholderFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (id == R.id.action_save) {
            placeholderFragment.submit();
            return true;
        } else if (id == R.id.action_delete) {
            placeholderFragment.delete();
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

        @InjectView(R.id.name)
        EditText name;
        @InjectView(R.id.amount)
        EditText amount;
        @InjectView(R.id.comment)
        EditText comment;
        @InjectView(R.id.date)
        TextView date;
        @InjectView(R.id.list)
        TwoWayView list;

        private DateTime selectedDate;
        private Expense expense;
        private int position = (int) Constants.DEFAULT_ID;
        private ItemSelectionSupport selectionSupport;

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
            DateTime now = DateTime.now();
            selectedDate =
                new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0);
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
            if (null != expense) {
                outState.putSerializable(EXPENSE_KEY, expense);
            }
            outState.putInt(Constants.POSITION_KEY, position);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_add_expense, container, false);
            ButterKnife.inject(this, rootView);

            List<Category> categories = BillsManagerApplication.categoryDao.queryBuilder()
                .orderDesc(CategoryDao.Properties.Priority)
                .list();

            CategoriesAdapter categoriesAdapter = new CategoriesAdapter();
            categoriesAdapter.setCategories(categories);
            list.setAdapter(categoriesAdapter);

            int columnsCount = 5;
            int rowsCount = 2;

            GridLayoutManager gridLayoutManager =
                new GridLayoutManager(TwoWayLayoutManager.Orientation.VERTICAL, columnsCount,
                    rowsCount);

            int spacing = (int) getResources().getDimension(R.dimen.item_spacing);
            SpacingItemDecoration decoration = new SpacingItemDecoration(spacing, spacing);

            list.setLayoutManager(gridLayoutManager);
            list.addItemDecoration(decoration);

            selectionSupport = ItemSelectionSupport.addTo(list);
            selectionSupport.setChoiceMode(ItemSelectionSupport.ChoiceMode.SINGLE);
            selectionSupport.setItemChecked(0, true);

            if (null != expense) {
                Double expenseAmount = expense.getAmount();
                if (null != expenseAmount) {
                    amount.setText(Double.toString(expenseAmount));
                }


                Category expenseCategory = expense.getCategory();
                if (null != expenseCategory) {
                    int categoryPosition =
                        categoriesAdapter.getItemPosition(expenseCategory.getId());
                    selectionSupport.setItemChecked(categoryPosition, true);
                }

                String expenseComment = expense.getComment();
                comment.setText(expenseComment);

                Date expenseDate = expense.getDate();
                if (null != expenseDate) {
                    selectedDate = new DateTime(expenseDate);
                }

                String expenseName = expense.getName();
                name.setText(expenseName);
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

        public void submit() {
            int checkedItemPosition = selectionSupport.getCheckedItemPosition();
            CategoriesAdapter adapter = (CategoriesAdapter) list.getAdapter();
            Category selectedCategory = adapter.getItem(checkedItemPosition);

            if (null == expense) {
                expense = new Expense();
                expense.setId(Constants.DEFAULT_ID);
            }

            expense.setDate(selectedDate.toDate());
            expense.setName(name.getText()
                .toString());
            expense.setComment(comment.getText()
                .toString());
            expense.setCategory(selectedCategory);
            String amountText = amount.getText()
                .toString();

            try {
                expense.setAmount(Double.parseDouble(amountText));
            } catch (NumberFormatException nfe) {
                amount.setError(getString(R.string.amount_format_error));
                return;
            }
            DataManager.insertExpense(expense);

            close(false);
        }

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
            long expenseId = Constants.DEFAULT_ID;
            if (null != expense) {
                expenseId = expense.getId();
            }
            data.putExtra(EditExpenseActivity.EXPENSE_ID_KEY, expenseId);
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
