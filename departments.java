import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;

public class departments {
    private static final String url = "jdbc:mysql://localhost:3307/employee";
    private static final String userName = "root";
    private static final String passWord = "admin";
    public static void main(String[] args){
        showData();
    }

    public static  void showData(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection connection = DriverManager.getConnection(url, userName, passWord);
            Scanner sc = new Scanner(System.in);
            System.out.println("Departments Table Open");
            System.out.println("Enter 1 For Create | 2 for Read | 3 for Update | 4 for Delete");
            int n = sc.nextInt();
            switch (n){
                case 1: create(connection);
                    break;
                case 2: read(connection);
                    break;
                case 3: update(connection);
                    break;
                case 4: delete(connection);
                    break;
                default:
                    System.out.println("Please Enter valid data!!!");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static void create(Connection connection){
        try{
            String query = "INSERT INTO departments (d_name, manager) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            boolean check = true;
            Scanner sc = new Scanner(System.in);
            while (check){
                System.out.print("Department Name: ");
                String d_name = sc.nextLine();
                System.out.print("Manager: ");
                int manager = sc.nextInt();
                sc.nextLine();
                System.out.print("Can You Enter More Data(Y/N): ");
                String chk = sc.next();
                preparedStatement.setString(1, d_name);
                preparedStatement.setInt(2, manager);
                preparedStatement.addBatch();
                if(chk.toUpperCase().equals("N")){
                    System.out.println("Existing....");
                    check = false;
                }
            }
            int[] arr = preparedStatement.executeBatch();

            for(int i=0; i< arr.length; i++){
                if(arr[i] == 0){
                    System.out.println("Failed to Insert data at: "+i+" Number Query!!!");
                }
            }
            read(connection);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void read(Connection connection){
        try{
            String query = "SELECT * FROM departments";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("=== Departments Table Data ===");
            while(resultSet.next()){
                int id = resultSet.getInt("d_id");
                String departmentName = resultSet.getString("d_name");
                int manager = resultSet.getInt("manager");
                // Print employee details
                System.out.printf("ID: %d | Department Name: %s  | Manager_id: %d\n",
                        id, departmentName, manager);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static void update(Connection connection){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the id which you want to Update: ");
        int n = sc.nextInt();
        try{
            String query = "UPDATE departments SET d_name=?, manager=? WHERE d_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            System.out.print("Department Name: ");
            String Dname = sc.next();
            sc.nextLine();
            System.out.print("Manager: ");
            int manager_id = sc.nextInt();
            preparedStatement.setString(1, Dname);
            preparedStatement.setInt(2, manager_id);
            preparedStatement.setInt(3, n);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("ID: "+n+" Updated Successfully");
                read(connection);
            }else {
                System.out.println("Oops Not Found ID: "+n);
                read(connection);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static void delete(Connection connection){
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the id name which you want to Delete: ");
    int n = sc.nextInt();
    try{
        String query = "DELETE FROM departments WHERE d_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, n);
        int rowsEffected = preparedStatement.executeUpdate();
        if(rowsEffected > 0){
            System.out.println("ID: "+n+" Deleted Successfully!!");
            read(connection);
        }else{
            System.out.println("Oops Not Found ID: "+n);
            read(connection);
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    }
}
