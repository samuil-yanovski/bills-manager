package yanovski.billsmanager.adapter;

import android.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import yanovski.billsmanager.BillsManagerApplication;
import yanovski.billsmanager.R;
import yanovski.billsmanager.adapter.vh.ExpenseViewHolder;
import yanovski.billsmanager.dao.DataManager;
import yanovski.billsmanager.daogen.Expense;
import yanovski.billsmanager.util.DateUtils;
import yanovski.billsmanager.util.ViewUtils;

/**
 * Created by Samuil on 1/19/2015.
 */
public class ExpensesAdapter extends RecyclerView.Adapter<ExpenseViewHolder> {

    private List<Expense> expenses;

    public ExpensesAdapter() {
        setHasStableIds(true);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = View.inflate(BillsManagerApplication.context, R.layout.expense_row, null);
        ViewUtils.setSelectableBackground(rootView);
        ViewGroup.LayoutParams params = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(params);
        return new ExpenseViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
        Expense expense = expenses.get(position);

        holder.date.setText(DateUtils.format(expense.getDate()
            .getTime(), DateUtils.FORMAT_SHORT_DATE));
        holder.description.setText(expense.getDisplayName());
        holder.amount.setText(DataManager.formatAmount(expense.getAmount()));

    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    @Override
    public long getItemId(int position) {
        return expenses.get(position)
            .getId();
    }

    public void addItem(int position, Expense expense) {
        expenses.add(position, expense);
    }

    public void removeItem(int position) {
        expenses.remove(position);
    }
}
