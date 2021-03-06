package yanovski.billsmanager.daogen;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import yanovski.billsmanager.daogen.Currency;
import yanovski.billsmanager.daogen.CurrentCurrency;
import yanovski.billsmanager.daogen.Category;
import yanovski.billsmanager.daogen.Expense;

import yanovski.billsmanager.daogen.CurrencyDao;
import yanovski.billsmanager.daogen.CurrentCurrencyDao;
import yanovski.billsmanager.daogen.CategoryDao;
import yanovski.billsmanager.daogen.ExpenseDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig currencyDaoConfig;
    private final DaoConfig currentCurrencyDaoConfig;
    private final DaoConfig categoryDaoConfig;
    private final DaoConfig expenseDaoConfig;

    private final CurrencyDao currencyDao;
    private final CurrentCurrencyDao currentCurrencyDao;
    private final CategoryDao categoryDao;
    private final ExpenseDao expenseDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        currencyDaoConfig = daoConfigMap.get(CurrencyDao.class).clone();
        currencyDaoConfig.initIdentityScope(type);

        currentCurrencyDaoConfig = daoConfigMap.get(CurrentCurrencyDao.class).clone();
        currentCurrencyDaoConfig.initIdentityScope(type);

        categoryDaoConfig = daoConfigMap.get(CategoryDao.class).clone();
        categoryDaoConfig.initIdentityScope(type);

        expenseDaoConfig = daoConfigMap.get(ExpenseDao.class).clone();
        expenseDaoConfig.initIdentityScope(type);

        currencyDao = new CurrencyDao(currencyDaoConfig, this);
        currentCurrencyDao = new CurrentCurrencyDao(currentCurrencyDaoConfig, this);
        categoryDao = new CategoryDao(categoryDaoConfig, this);
        expenseDao = new ExpenseDao(expenseDaoConfig, this);

        registerDao(Currency.class, currencyDao);
        registerDao(CurrentCurrency.class, currentCurrencyDao);
        registerDao(Category.class, categoryDao);
        registerDao(Expense.class, expenseDao);
    }
    
    public void clear() {
        currencyDaoConfig.getIdentityScope().clear();
        currentCurrencyDaoConfig.getIdentityScope().clear();
        categoryDaoConfig.getIdentityScope().clear();
        expenseDaoConfig.getIdentityScope().clear();
    }

    public CurrencyDao getCurrencyDao() {
        return currencyDao;
    }

    public CurrentCurrencyDao getCurrentCurrencyDao() {
        return currentCurrencyDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public ExpenseDao getExpenseDao() {
        return expenseDao;
    }

}
