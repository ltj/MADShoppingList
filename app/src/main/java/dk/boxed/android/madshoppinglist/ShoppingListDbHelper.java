package dk.boxed.android.madshoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lars on 19/03/15.
 */
public class ShoppingListDbHelper extends SQLiteOpenHelper {

    // Remember to increment version on schema update.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ShoppingList.db";


    public ShoppingListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ShoppingListContract.SQL_CREATE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ShoppingListContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
