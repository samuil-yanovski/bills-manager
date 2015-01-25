package yanovski.billsmanager;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import net.danlew.android.joda.JodaTimeAndroid;

import yanovski.billsmanager.dao.DataManager;
import yanovski.billsmanager.daogen.CategoryDao;
import yanovski.billsmanager.daogen.CurrencyDao;
import yanovski.billsmanager.daogen.CurrentCurrencyDao;
import yanovski.billsmanager.daogen.DaoMaster;
import yanovski.billsmanager.daogen.DaoSession;
import yanovski.billsmanager.daogen.ExpenseDao;

/**
 * Created by Samuil on 1/19/2015.
 */
public class BillsManagerApplication extends Application {

    private static DaoMaster.DevOpenHelper helper;
    private static SQLiteDatabase db;
    public static DaoMaster daoMaster;
    public static DaoSession daoSession;

    public static CurrencyDao currencyDao;
    public static CurrentCurrencyDao currentCurrencyDao;
    public static CategoryDao categoryDao;
    public static ExpenseDao expenseDao;

    public static BillsManagerApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = this;

        helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        currencyDao = daoSession.getCurrencyDao();
        currentCurrencyDao = daoSession.getCurrentCurrencyDao();
        categoryDao = daoSession.getCategoryDao();
        expenseDao = daoSession.getExpenseDao();

        JodaTimeAndroid.init(this);
        DataManager.init();
    }
}
