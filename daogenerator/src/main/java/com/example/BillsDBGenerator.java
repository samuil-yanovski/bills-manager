package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class BillsDBGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "yanovski.billsmanager.daogen");
        schema.enableKeepSectionsByDefault();
        addNote(schema);
        new DaoGenerator().generateAll(schema, "../app/src/main/java");
    }

    private static void addNote(Schema schema) {

        Entity currency = schema.addEntity("Currency");
        currency.addIdProperty();
        currency.addStringProperty("name")
            .notNull();
        currency.addStringProperty("symbol")
            .notNull();
        currency.implementsSerializable();

        Entity currentCurrency = schema.addEntity("CurrentCurrency");
        currentCurrency.addIdProperty();
        Property currencyId = currentCurrency.addLongProperty("currencyId")
            .getProperty();
        currentCurrency.addToOne(currency, currencyId);
        currentCurrency.implementsSerializable();

        Entity category = schema.addEntity("Category");
        category.addIdProperty();
        category.addStringProperty("name")
            .notNull();
        category.implementsSerializable();

        Entity expense = schema.addEntity("Expense");
        expense.addIdProperty();
        expense.addStringProperty("name");
        expense.addStringProperty("comment");
        expense.addDateProperty("date");
        expense.addDoubleProperty("amount");
        Property categoryId = expense.addLongProperty("categoryId")
            .getProperty();
        expense.addToOne(category, categoryId);
        expense.implementsSerializable();
    }
}
