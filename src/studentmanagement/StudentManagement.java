package studentmanagement;

import java.sql.*;
import java.util.Scanner;

public class StudentManagement {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    displayStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    updateStudent();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 6:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);
    }

    // ADD STUDENT
    static void addStudent() {

        String sql = "INSERT INTO students "
                + "(student_id,student_name, department, email, marks) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
        	
        	System.out.print("Enter Id: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Department: ");
            String dept = sc.nextLine();

            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            System.out.print("Enter Marks: ");
            double marks = Double.parseDouble(sc.nextLine());

            ps.setInt(1,id);
            ps.setString(2, name);
            ps.setString(3, dept);
            ps.setString(4, email);
            ps.setDouble(5, marks);

            int rows = ps.executeUpdate();

            System.out.println(rows + " Student Added Successfully!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // DISPLAY STUDENTS
    static void displayStudents() {

        String sql = "SELECT * FROM students";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\nID | NAME | DEPARTMENT | EMAIL | MARKS");

            while (rs.next()) {

                System.out.println(
                    rs.getInt("student_id") + " | " +
                    rs.getString("student_name") + " | " +
                    rs.getString("department") + " | " +
                    rs.getString("email") + " | " +
                    rs.getDouble("marks")
                );
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // SEARCH STUDENT
    static void searchStudent() {

        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(sc.nextLine());

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println("Name: " +
                        rs.getString("student_name"));
                    System.out.println("Department: " +
                        rs.getString("department"));
                    System.out.println("Email: " +
                        rs.getString("email"));
                    System.out.println("Marks: " +
                        rs.getDouble("marks"));
                } else {
                    System.out.println("Student Not Found!");
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // UPDATE STUDENT
    static void updateStudent() {

        String sql = "UPDATE students SET student_name=?, "
                + "department=?, email=?, marks=? "
                + "WHERE student_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Enter New Name: ");
            String name = sc.nextLine();

            System.out.print("Enter New Department: ");
            String dept = sc.nextLine();

            System.out.print("Enter New Email: ");
            String email = sc.nextLine();

            System.out.print("Enter New Marks: ");
            double marks = Double.parseDouble(sc.nextLine());

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, dept);
            ps.setString(4, email);
            ps.setDouble(5, marks);

            int rows = ps.executeUpdate();

            System.out.println(rows > 0
                    ? "Student Updated Successfully!"
                    : "Student ID Not Found!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // DELETE STUDENT
    static void deleteStudent() {

        String sql = "DELETE FROM students WHERE student_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(sc.nextLine());

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            System.out.println(rows > 0
                    ? "Student Deleted Successfully!"
                    : "Student ID Not Found!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
