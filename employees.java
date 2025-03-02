import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class employees {
    private static final String url = "jdbc:mysql://localhost:3307/employee";
    private static final String userName = "root";
    private static final String passWord = "admin";
    public static  void main(String[] args){
        //        driver Import
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
            System.out.println("Employees Table Open");
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
        try {
            String query = "INSERT INTO employees(first_name, last_name, position, salary, hire_date, depart_id) VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            Scanner sc = new Scanner(System.in);
            boolean check = true;
            while(check){
                System.out.print("First Name: ");
                String first_name = sc.next();
                System.out.print("Last Name: ");
                String last_name = sc.next();
                System.out.print("Position: ");
                String position = sc.next();
                System.out.print("Salary: ");
                int salary = sc.nextInt();
                sc.nextLine();
                System.out.print("Hire Date(yyyy-mm-dd): ");
                String hire_date = sc.next();
                sc.nextLine();
                System.out.print("Department_id: ");
                int depat_id = sc.nextInt();
                sc.nextLine();
                System.out.print("Can You Enter More Data(Y/N): ");
                String chck = sc.nextLine();
                preparedStatement.setString(1, first_name);
                preparedStatement.setString(2, last_name);
                preparedStatement.setString(3, position);
                preparedStatement.setInt(4, salary);
                preparedStatement.setString(5, hire_date);
                preparedStatement.setInt(6, depat_id);
                preparedStatement.addBatch();
                if (chck.toUpperCase().charAt(0) == 'N') {
                    System.out.println("Exiting...");
                    check = false;
                }
            }
            int[] arr = preparedStatement.executeBatch();
            for(int i = 0; i< arr.length; i++){
                if(arr[i] == 0){
                    System.out.println("Query failed at "+i+"no query");
                }
            }
            read(connection);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static void read(Connection connection){
        try{
            String query = "SELECT * FROM employees";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("=== Employees Table Data ===");
            boolean check = false;
            while(resultSet.next()){
                check = true;
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String position = resultSet.getString("position");
                int salary = resultSet.getInt("salary");
                String hire_date = resultSet.getString("hire_date");
                int depart_id = resultSet.getInt("depart_id");

                // Print employee details
                System.out.printf("ID: %d | Name: %s %s | Position: %s | Salary: %d | Hire Date: %s | Department_Id: %d\n",
                        id, first_name, last_name, position, salary, hire_date, depart_id);
            }
            if(!check){
                System.out.println("No Data Found");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static void update(Connection connection){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the id which you want to update: ");
        int n = sc.nextInt();
        try{
            String query = "UPDATE employees SET first_name=?, last_name=?, position=?, salary=?, hire_date=?, depart_id=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            System.out.print("Update First Name: ");
            String first_name = sc.next();
            System.out.print("Update Last Name: ");
            String last_name = sc.next();
            System.out.print("Update Position: ");
            String position = sc.next();
            sc.nextLine();
            System.out.print("Update Salary: ");
            int salary = sc.nextInt();
            sc.nextLine();
            System.out.print("Update Hire Date(yyyy-mm-dd): ");
            String hire_date = sc.next();
            sc.nextLine();
            System.out.print("Update Department Id: ");
            int depart_id = sc.nextInt();
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, position);
            preparedStatement.setInt(4, salary);
            preparedStatement.setString(5, hire_date);
            preparedStatement.setInt(6, depart_id);
            preparedStatement.setInt(7, n);

//            execute Update
            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Record Updated Successfully");
                read(connection);
            }else{
                System.out.println("Unable to Update Record");
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
            String query = "DELETE FROM employees WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, n);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0){
                System.out.println("✅ Id No: "+n+" deleted successfully!");
            }else{
                System.out.println("❌ Unable to delete!!! Don't Exist id No: "+n);
            }
            read(connection);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}