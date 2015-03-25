package dk.boxed.android.madshoppinglist;

import android.provider.BaseColumns;

/**
 * Created by lars on 19/03/15.
 */
public final class ShoppingListContract {

    public ShoppingListContract() {}

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ITEMS =
            "CREATE TABLE " + ShoppingItem.TABLE_NAME + " (" +
                    ShoppingItem._ID + " INTEGER PRIMARY KEY," +
                    ShoppingItem.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    ShoppingItem.COLUMN_NAME_AMOUNT + INT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ShoppingItem.TABLE_NAME;

    public static final String SQL_COUNT_ENTRIES =
            "SELECT SUM(" + ShoppingItem.COLUMN_NAME_AMOUNT +
                    ") FROM " + ShoppingItem.TABLE_NAME;

    /* Inner class that defines the table contents */
    public static abstract class ShoppingItem implements BaseColumns {
        public static final String TABLE_NAME = "item";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AMOUNT = "amount";
    }

}
