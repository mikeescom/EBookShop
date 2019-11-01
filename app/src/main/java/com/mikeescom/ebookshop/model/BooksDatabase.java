package com.mikeescom.ebookshop.model;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Category.class, Book.class}, version = 1)
public abstract class BooksDatabase extends RoomDatabase {

    public abstract CategoryDAO categoryDAO();
    public abstract BookDAO bookDAO();

    public static BooksDatabase instance;

    public static synchronized BooksDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , BooksDatabase.class
                    , "books_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitialDataAsyncTask(instance).execute();
        }
    };

    private static class InitialDataAsyncTask extends AsyncTask<Void, Void, Void> {

        private static final String TAG = InitialDataAsyncTask.class.getSimpleName();
        private CategoryDAO categoryDAO;
        private BookDAO bookDAO;

        public InitialDataAsyncTask(BooksDatabase booksDatabase) {
            this.categoryDAO = booksDatabase.categoryDAO();
            this.bookDAO = booksDatabase.bookDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Category category1 = new Category();
            category1.setCategoryName("Text Books");
            category1.setCategoryDescription("Text Books Descriptions");

            Category category2 = new Category();
            category2.setCategoryName("Novels");
            category2.setCategoryDescription("Novels Descriptions");

            Category category3 = new Category();
            category3.setCategoryName("Other Books");
            category3.setCategoryDescription("Other Books Descriptions");

            try {
                categoryDAO.insert(category1);
                categoryDAO.insert(category2);
                categoryDAO.insert(category3);
            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage());
            }

            Book book1 = new Book();
            book1.setBookName("High School Java");
            book1.setUnitPrice("$150");
            book1.setCategoryId(1);

            Book book2 = new Book();
            book2.setBookName("OCP Java");
            book2.setUnitPrice("$100");
            book2.setCategoryId(1);

            Book book3 = new Book();
            book3.setBookName("Python from zero to hero");
            book3.setUnitPrice("$120");
            book3.setCategoryId(1);

            Book book4 = new Book();
            book4.setBookName("12 Rules fro live");
            book4.setUnitPrice("$50");
            book4.setCategoryId(3);

            Book book5 = new Book();
            book5.setBookName("Game of Thrones");
            book5.setUnitPrice("$75");
            book5.setCategoryId(2);

            Book book6 = new Book();
            book6.setBookName("Machine Learning for engineers");
            book6.setUnitPrice("$30");
            book6.setCategoryId(1);

            Book book7 = new Book();
            book7.setBookName("El Paciente Ingles");
            book7.setUnitPrice("$20");
            book7.setCategoryId(2);


            try {
                bookDAO.insert(book1);
                bookDAO.insert(book2);
                bookDAO.insert(book3);
                bookDAO.insert(book4);
                bookDAO.insert(book5);
                bookDAO.insert(book6);
                bookDAO.insert(book7);
            } catch (SQLiteConstraintException e) {
                Log.e(TAG, "Error: " + e.getMessage());
            }

            return null;
        }
    }

}
