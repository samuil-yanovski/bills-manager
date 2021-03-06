package yanovski.billsmanager.dao;

import android.content.Context;
import android.database.Cursor;

import java.util.Date;
import java.util.List;

import yanovski.billsmanager.BillsManagerApplication;
import yanovski.billsmanager.Constants;
import yanovski.billsmanager.R;
import yanovski.billsmanager.daogen.Category;
import yanovski.billsmanager.daogen.Currency;
import yanovski.billsmanager.daogen.CurrentCurrency;
import yanovski.billsmanager.daogen.Expense;
import yanovski.billsmanager.daogen.ExpenseDao;
import yanovski.billsmanager.util.CollectionsUtil;

/**
 * Created by Samuil on 1/19/2015.
 */
public class DataManager {

    private static CurrentCurrency sCurrentCurrency;

    public static CurrentCurrency getCurrentCurrency() {
        return sCurrentCurrency;
    }

    public static double getDayAverage() {
        Cursor cursor = BillsManagerApplication.db.rawQuery(
            "SELECT SUM(amount) / COUNT(DISTINCT date) FROM expense ", null);

        return cursor.getDouble(0);
    }

    public static String formatAmount(double amount) {
        return String.format("%.2f%s", amount, null != sCurrentCurrency ?
            sCurrentCurrency.getCurrency()
                .getSymbol() : "");
    }

    public static String getFormattedTotalExpensesForDay(Date day) {
        double total = 0;
        List<Expense> expensesForDay = getExpensesForDay(day);
        if (!CollectionsUtil.isEmpty(expensesForDay)) {
            for (Expense expense : expensesForDay) {
                total += expense.getAmount();
            }
        }

        return 0 < total ? formatAmount(total) : "";
    }

    public static String getFormattedTotalExpensesForDay(double total) {
        return 0 < total ? formatAmount(total) : "";
    }

    public static double getTotalExpensesForDay(Date day) {
        double total = 0;
        List<Expense> expensesForDay = getExpensesForDay(day);
        if (!CollectionsUtil.isEmpty(expensesForDay)) {
            for (Expense expense : expensesForDay) {
                total += expense.getAmount();
            }
        }

        return total;
    }

    public static List<Expense> getExpensesForDay(Date day) {
        return BillsManagerApplication.expenseDao.queryBuilder()
            .where(ExpenseDao.Properties.Date.eq(day))
            .list();
    }

    public static void init() {
        if (0 == BillsManagerApplication.currentCurrencyDao.count()) {
            Context context = BillsManagerApplication.context;

            insertCategory(context.getString(R.string.category_car), R.drawable.ic_car,
                context.getResources()
                    .getInteger(R.integer.category_car), 60);
            insertCategory(context.getString(R.string.category_clothes), R.drawable.ic_clothes,
                context.getResources()
                    .getInteger(R.integer.category_clothes), 60);
            insertCategory(context.getString(R.string.category_gadgets), R.drawable.ic_gadgets,
                context.getResources()
                    .getInteger(R.integer.category_gadgets), 50);
            insertCategory(context.getString(R.string.category_hobby), R.drawable.ic_hobby,
                context.getResources()
                    .getInteger(R.integer.category_hobby), 70);
            insertCategory(context.getString(R.string.category_food), R.drawable.ic_food,
                context.getResources()
                    .getInteger(R.integer.category_food), 90);
            insertCategory(context.getString(R.string.category_furniture), R.drawable.ic_furniture,
                context.getResources()
                    .getInteger(R.integer.category_furniture), 10);
            insertCategory(context.getString(R.string.category_holidays), R.drawable.ic_holidays,
                context.getResources()
                    .getInteger(R.integer.category_holidays), 20);
            insertCategory(context.getString(R.string.category_medical), R.drawable.ic_medical,
                context.getResources()
                    .getInteger(R.integer.category_medical), 10);
            insertCategory(context.getString(R.string.category_sport), R.drawable.ic_sport,
                context.getResources()
                    .getInteger(R.integer.category_sport), 10);
            insertCategory(context.getString(R.string.category_other), R.drawable.ic_other,
                context.getResources()
                    .getInteger(R.integer.category_other), 0);

            Currency usd = insertCurrency(context.getString(R.string.currency_name_usd),
                context.getString(R.string.currency_symbol_usd));
            insertCurrency(context.getString(R.string.currency_name_euro),
                context.getString(R.string.currency_symbol_euro));

            insertCurrentCurrency(usd);
        }

        sCurrentCurrency = BillsManagerApplication.currentCurrencyDao.loadAll()
            .get(0);
    }


    public static Currency insertCurrency(String name, String symbol) {
        Currency currency = new Currency();
        currency.setName(name);
        currency.setSymbol(symbol);

        BillsManagerApplication.currencyDao.insert(currency);
        return currency;
    }

    public static CurrentCurrency insertCurrentCurrency(Currency current) {
        CurrentCurrency currentCurrency = new CurrentCurrency();
        currentCurrency.setCurrency(current);

        BillsManagerApplication.currentCurrencyDao.deleteAll();
        BillsManagerApplication.currentCurrencyDao.insert(currentCurrency);
        return currentCurrency;
    }

    public static Category insertCategory(String name, int iconId, int customId, int priority) {
        Category category = new Category();
        category.setName(name);
        category.setIconId(iconId);
        category.setCustomId(customId);
        category.setPriority(priority);

        BillsManagerApplication.categoryDao.insert(category);
        return category;
    }

    public static Expense insertExpense(Expense expense) {
        Long id = expense.getId();
        if (null == id || Constants.DEFAULT_ID == id) {
            expense.setId(null);
            BillsManagerApplication.expenseDao.insert(expense);
        } else {
            BillsManagerApplication.expenseDao.update(expense);
        }
        return expense;
    }
}
