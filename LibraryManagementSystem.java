import java.sql.*;
import java.util.Scanner;

public class LibraryManagementSystem {

    static final String DB_URL = "jdbc:mysql://localhost:3306/library_db";
    static final String USER = "root";
    static final String PASS = "your_password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();

            // Create table if not exists
            String sql = "CREATE TABLE IF NOT EXISTS books (" +
                         "id INT PRIMARY KEY AUTO_INCREMENT," +
                         "title VARCHAR(100)," +
                         "author VARCHAR(50)," +
                         "year INT)";
            stmt.executeUpdate(sql);

            while (true) {
                System.out.println("\nLibrary Management System");
                System.out.println("1. Add Book");
                System.out.println("2. View Books");
                System.out.println("3. Update Book");
                System.out.println("4. Delete Book");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Book Title: ");
                        sc.nextLine(); // Consume newline
                        String title = sc.nextLine();
                        System.out.print("Enter Author: ");
                        String author = sc.nextLine();
                        System.out.print("Enter Year Published: ");
                        int year = sc.nextInt();

                        sql = "INSERT INTO books (title, author, year) VALUES ('" + title + "', '" + author + "', " + year + ")";
                        stmt.executeUpdate(sql);
                        System.out.println("Book added successfully.");
                        break;

                    case 2:
                        ResultSet rs = stmt.executeQuery("SELECT * FROM books");
                        System.out.println("\nID | Title | Author | Year");
                        while (rs.next()) {
                            System.out.println(rs.getInt("id") + " | " + rs.getString("title") + " | " + rs.getString("author") + " | " + rs.getInt("year"));
                        }
                        break;

                    case 3:
                        System.out.print("Enter Book ID to Update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine(); // Consume newline
                        System.out.print("Enter New Title: ");
                        String newTitle = sc.nextLine();
                        System.out.print("Enter New Author: ");
                        String newAuthor = sc.nextLine();
                        System.out.print("Enter New Year: ");
                        int newYear = sc.nextInt();

                        sql = "UPDATE books SET title='" + newTitle + "', author='" + newAuthor + "', year=" + newYear + " WHERE id=" + updateId;
                        stmt.executeUpdate(sql);
                        System.out.println("Book updated successfully.");
                        break;

                    case 4:
                        System.out.print("Enter Book ID to Delete: ");
                        int deleteId = sc.nextInt();
                        sql = "DELETE FROM books WHERE id=" + deleteId;
                        stmt.executeUpdate(sql);
                        System.out.println("Book deleted successfully.");
                        break;

                    case 5:
                        stmt.close();
                        conn.close();
                        sc.close();
                        System.out.println("Exiting...");
                        System.exit(0);

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}