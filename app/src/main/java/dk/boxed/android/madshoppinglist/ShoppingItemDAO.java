package dk.boxed.android.madshoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * Data Access Object for ShoppingItem
 * Encapsulates Helper and DB operations
 * Shopping Item object abstraction has own ShoppingItem class
 */
public class ShoppingItemDAO {

    private ShoppingListDbHelper helper;
    private SQLiteDatabase db;
    private SQLiteStatement countSql;

    public ShoppingItemDAO(Context context) {
        helper = new ShoppingListDbHelper(context);
    }

    public void open() {
        db = helper.getWritableDatabase();
        countSql = db.compileStatement(ShoppingListContract.SQL_COUNT_ENTRIES);
    }

    public void close() {
        countSql = null;
        helper.close();
    }

    public long insertItem(String name, int amount) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListContract.ShoppingItem.COLUMN_NAME_NAME, name);
        values.put(ShoppingListContract.ShoppingItem.COLUMN_NAME_AMOUNT, amount);
        return db.insert(ShoppingListContract.ShoppingItem.TABLE_NAME, null, values);
    }

    public void updateAmount(long id, int amount) {
        if (amount > 0) {
            ContentValues values = new ContentValues();
            values.put(ShoppingListContract.ShoppingItem.COLUMN_NAME_AMOUNT, amount);

            String selection = ShoppingListContract.ShoppingItem._ID + " LIKE ?";
            String[] selectionArgs = {String.valueOf(id)};

            db.update(
                    ShoppingListContract.ShoppingItem.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
        }
        else {
            deleteItem(id);
        }
    }

    public void deleteItem(long id) {
        String selection = ShoppingListContract.ShoppingItem._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};

        db.delete(
                ShoppingListContract.ShoppingItem.TABLE_NAME,
                selection,
                selectionArgs
        );
    }

    public Cursor getItems() {
        Cursor cursor = db.query(ShoppingListContract.ShoppingItem.TABLE_NAME,
                new String[] {ShoppingListContract.ShoppingItem._ID,
                ShoppingListContract.ShoppingItem.COLUMN_NAME_NAME,
                ShoppingListContract.ShoppingItem.COLUMN_NAME_AMOUNT},
                null, null, null, null,
                ShoppingListContract.ShoppingItem._ID);
        return cursor;
    }

    public long getTotal() {
        // need to call open
        if (countSql != null) {
            Long total = countSql.simpleQueryForLong();
            return total;
        }
        else {
            return -1;
        }
    }

}
