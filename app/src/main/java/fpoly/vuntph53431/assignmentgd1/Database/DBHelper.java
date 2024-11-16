package fpoly.vuntph53431.assignmentgd1.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String NAME_DB = "MANAGER.db";
    public static final int VERSION_DB = 1;

    public DBHelper(@Nullable Context context) {
        super(context,NAME_DB,null,VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableAccount = "CREATE TABLE ACCOUNT(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "USER TEXT NOT NULL," +
                "PASSWORD TEXT NOT NULL," +
                "ROLE TEXT NOT NULL)";
        db.execSQL(createTableAccount);

        String data = "INSERT INTO ACCOUNT VALUES(0,'Admin','admin','Superadmin')";
        db.execSQL(data);

        String createTableProduct = "CREATE TABLE PRODUCT(" +
                "ID INTEGER PRIMARY KEY," +
                "NAME TEXT Not null," +
                "PRICE INTEGER NOT NULL," +
                "SIZE TEXT NOT NULL)";
        db.execSQL(createTableProduct);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
