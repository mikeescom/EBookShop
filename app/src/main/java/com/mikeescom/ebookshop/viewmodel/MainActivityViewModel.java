package com.mikeescom.ebookshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mikeescom.ebookshop.model.Book;
import com.mikeescom.ebookshop.model.Category;
import com.mikeescom.ebookshop.model.EBookShopRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private EBookShopRepository eBookShopRepository;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Book>> booksOfSelectedCategory;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        eBookShopRepository = new EBookShopRepository(application);
    }

    public LiveData<List<Category>> getAllCategories() {
        allCategories = eBookShopRepository.getAllCategories();
        return allCategories;
    }

    public LiveData<List<Book>> getBooksOfSelectedCategory(int categoryId) {
        booksOfSelectedCategory = eBookShopRepository.getBooks(categoryId);
        return booksOfSelectedCategory;
    }

    public void addNewCategory(Category category) {
        eBookShopRepository.insertCategory(category);
    }

    public void addNewBook(Book book) {
        eBookShopRepository.insertBook(book);
    }

    public void updateCategory(Category category) {
        eBookShopRepository.updateCategory(category);
    }

    public void updateBook(Book book) {
        eBookShopRepository.updateBook(book);
    }

    public void deleteCategory(Category category) {
        eBookShopRepository.deleteCategory(category);
    }

    public void deleteBook(Book book) {
        eBookShopRepository.deleteBook(book);
    }
}
