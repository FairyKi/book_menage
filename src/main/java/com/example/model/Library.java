package com.example.model;

import com.example.model.Book;
import com.example.model.User;
import java.io.*;
import java.util.*;

public class Library implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Book> books;
    private List<User> users;
    private static final String DATA_FILE = "library_data.ser";

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        loadData(); // 启动时加载数据
    }

    // 保存数据到文件
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(books);
            oos.writeObject(users);
            System.out.println("💾 数据已保存到文件");
        } catch (IOException e) {
            System.out.println("❌ 保存数据失败: " + e.getMessage());
        }
    }

    // 从文件加载数据
    @SuppressWarnings("unchecked")
    public void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("📋 数据文件不存在，使用空数据");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            books = (List<Book>) ois.readObject();
            users = (List<User>) ois.readObject();
            System.out.println("📚 数据已从文件加载");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ 加载数据失败: " + e.getMessage());
            books = new ArrayList<>();
            users = new ArrayList<>();
        }
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("✅ 图书《" + book.getTitle() + "》已添加。");
        saveData(); // 添加后保存
    }

    public void addUser(User user) {
        users.add(user);
        System.out.println("✅ 用户 " + user.getName() + " 已注册。");
        saveData(); // 添加后保存
    }

    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("📚 当前无图书。");
            return;
        }
        System.out.println("\n--- 所有图书 ---");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
    }

    public void borrowBook(String userName, String bookTitle) {
        User user = findUserByName(userName);
        Book book = findBookByTitle(bookTitle);

        if (user == null) {
            System.out.println("❌ 用户不存在！");
            return;
        }
        if (book == null) {
            System.out.println("❌ 图书不存在！");
            return;
        }
        if (!book.isAvailable()) {
            System.out.println("❌ 该书已被借出！");
            return;
        }

        user.borrowBook(book);
        System.out.println("🎉 " + userName + " 成功借阅《" + bookTitle + "》！");
        saveData(); // 借阅后保存
    }

    public void returnBook(String userName, String bookTitle) {
        User user = findUserByName(userName);
        if (user == null) {
            System.out.println("❌ 用户不存在！");
            return;
        }

        Book book = null;
        for (Book b : user.getBorrowedBooks()) {
            if (b.getTitle().equalsIgnoreCase(bookTitle)) {
                book = b;
                break;
            }
        }

        if (book == null) {
            System.out.println("❌ 该用户未借阅此书！");
            return;
        }

        user.returnBook(book);
        System.out.println("✅ " + userName + " 已归还《" + bookTitle + "》。");
        saveData(); // 归还后保存
    }

    private User findUserByName(String name) {
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }

    private Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    // 用于初始化测试数据的方法
    public List<Book> getAllBooks() {
        return books;
    }
}
