 package com.example.main;

import com.example.model.Book;
import com.example.model.User;
import com.example.model.Library;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        // 初始化测试数据
        library.addBook(new Book("Java核心技术", "Cay S. Horstmann", "978-7-111-63375-2"));
        library.addBook(new Book("算法导论", "Thomas H. Cormen", "978-7-111-13017-7"));
        library.addUser(new User("张三"));
        library.addUser(new User("李四"));

        while (true) {
            System.out.println("\n========== 图书管理系统 ==========");
            System.out.println("1. 查看所有图书");
            System.out.println("2. 添加图书");
            System.out.println("3. 注册用户");
            System.out.println("4. 借阅图书");
            System.out.println("5. 归还图书");
            System.out.println("0. 退出系统");
            System.out.print("请选择操作：");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ 请输入有效数字！");
                continue;
            }

            switch (choice) {
                case 1 -> library.displayAllBooks();
                case 2 -> {
                    System.out.print("书名：");
                    String title = scanner.nextLine();
                    System.out.print("作者：");
                    String author = scanner.nextLine();
                    System.out.print("ISBN：");
                    String isbn = scanner.nextLine();
                    library.addBook(new Book(title, author, isbn));
                }
                case 3 -> {
                    System.out.print("用户名：");
                    String name = scanner.nextLine();
                    library.addUser(new User(name));
                }
                case 4 -> {
                    System.out.print("用户名：");
                    String uName = scanner.nextLine();
                    System.out.print("书名：");
                    String bTitle = scanner.nextLine();
                    library.borrowBook(uName, bTitle);
                }
                case 5 -> {
                    System.out.print("用户名：");
                    String retUser = scanner.nextLine();
                    System.out.print("归还的书名：");
                    String retBook = scanner.nextLine();
                    library.returnBook(retUser, retBook);
                }
                case 0 -> {
                    System.out.println("👋 感谢使用图书管理系统！");
                    scanner.close();
                    return;
                }
                default -> System.out.println("⚠️ 无效选项，请重新选择。");
            }
        }
    }
}


