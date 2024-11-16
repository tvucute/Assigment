package fpoly.vuntph53431.assignmentgd1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.vuntph53431.assignmentgd1.Database.DBHelper;
import fpoly.vuntph53431.assignmentgd1.Model.Product;
import fpoly.vuntph53431.assignmentgd1.Model.User;

public class ManagerDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public ManagerDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean addProduct(Product product){
        ContentValues values = new ContentValues();
        values.put("NAME",product.getName());
        values.put("PRICE",product.getPrice());
        values.put("SIZE",product.getSize());
        long add = database.insert("PRODUCT",null,values);
        if (add>0)
            return true;
        return false;
    }

    public boolean updateProduct(Product product){
        ContentValues values = new ContentValues();
        values.put("NAME",product.getName());
        values.put("PRICE",product.getPrice());
        values.put("SIZE",product.getSize());
        long update = database.update("PRODUCT",values,"ID=?", new String[]{String.valueOf(product.getId())});
        if (update > 0)
            return true;
        return false;
    }

    public boolean deleteProduct(int id){
        long delete = database.delete("PRODUCT","ID=?",new String[]{String.valueOf(id)});
        if (delete>0)
            return true;
        return false;
    }

    public ArrayList<Product> getProduct(){
        ArrayList<Product> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM PRODUCT",null);
        while (cursor.moveToNext()){
            Product product = new Product(cursor.getString(1),cursor.getInt(2),cursor.getString(3));
            product.setId(cursor.getInt(0));
            list.add(product);
        }
        return list;
    }

    public boolean addUser(User user){
        ContentValues values = new ContentValues();
        values.put("USER", user.getUser());
        values.put("PASSWORD",user.getPassword());
        values.put("ROLE",user.getRole());
        long adduser = database.insert("ACCOUNT",null,values);
        if (adduser>0)
            return true;
        return false;
    }

    public boolean updateUser(User user){
        ContentValues values = new ContentValues();
        values.put("USER",user.getUser());
        values.put("PASSWORD",user.getPassword());
        values.put("ROLE",user.getRole());
        long update = database.update("ACCOUNT",values,"ID=?", new String[]{String.valueOf(user.getId())});
        if (update > 0)
            return true;
        return false;
    }

    public boolean deleteUser(int id){
        long delete = database.delete("ACCOUNT","ID=?",new String[]{String.valueOf(id)});
        if (delete>0)
            return true;
        return false;
    }

    public ArrayList<User> getAllUser(){
        ArrayList<User> listuser = new ArrayList<>();
        String selectQuery = "SELECT * FROM ACCOUNT WHERE ROLE = 'User'";
        Cursor mCursor = database.rawQuery(selectQuery, null);
        while (mCursor.moveToNext()){
            User user = new User(mCursor.getString(1),mCursor.getString(2),mCursor.getString(3));
            user.setId(mCursor.getInt(0));
            listuser.add(user);
        }
        return listuser;
    }

    public User getAdminaccount(){
        Cursor cursor = database.rawQuery("SELECT * FROM ACCOUNT",null);
        while (cursor.moveToNext()){
            User admin = new User(cursor.getString(1),cursor.getString(2),cursor.getString(3));
            admin.setId(cursor.getInt(0));
            return admin;
        }
        return null;
    }
}
