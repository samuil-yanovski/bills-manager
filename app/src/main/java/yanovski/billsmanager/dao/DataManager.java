package yanovski.billsmanager.dao;

import android.content.Context;

import yanovski.billsmanager.BillsManagerApplication;
import yanovski.billsmanager.Constants;
import yanovski.billsmanager.R;
import yanovski.billsmanager.daogen.Category;
import yanovski.billsmanager.daogen.Currency;
import yanovski.billsmanager.daogen.CurrentCurrency;
import yanovski.billsmanager.daogen.Expense;

/**
 * Created by Samuil on 1/19/2015.
 */
public class DataManager {

    public static void init() {
        if (0 == BillsManagerApplication.currentCurrencyDao.count()) {
            Context context = BillsManagerApplication.context;

            insertCategory(context.getString(R.string.category_car));
            insertCategory(context.getString(R.string.category_clothes));
            insertCategory(context.getString(R.string.category_electronics));
            insertCategory(context.getString(R.string.category_entertainment));
            insertCategory(context.getString(R.string.category_food));
            insertCategory(context.getString(R.string.category_furniture));
            insertCategory(context.getString(R.string.category_holidays));
            insertCategory(context.getString(R.string.category_medical));
            insertCategory(context.getString(R.string.category_sport));

            Currency usd = insertCurrency(context.getString(R.string.currency_name_usd),
                context.getString(R.string.currency_symbol_usd));
            insertCurrency(context.getString(R.string.currency_name_euro),
                context.getString(R.string.currency_symbol_euro));

            insertCurrentCurrency(usd);
        }
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

    public static Category insertCategory(String name) {
        Category category = new Category();
        category.setName(name);

        BillsManagerApplication.categoryDao.insert(category);
        return category;
    }

    public static Expense insertExpense(Expense expense) {
        Long id = expense.getId();
        if (Constants.DEFAULT_ID == id) {
            expense.setId(null);
            BillsManagerApplication.expenseDao.insert(expense);
        } else {
            BillsManagerApplication.expenseDao.update(expense);
        }
        return expense;
    }
}
