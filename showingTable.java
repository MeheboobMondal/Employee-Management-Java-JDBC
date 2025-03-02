import java.sql.*;
import java.util.Scanner;

public class showingTable {
    private static final String url = "jdbc:mysql://localhost:3307/employee";
    private static final String userName = "root"  ;
    private static  final String passWord = "admin"   ;

    public static void main(String[] args){
        loadData();

    }
    public static void loadData(){
        Scanner sc = new Scanner(System.in);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try{
            Connection connection = DriverManager.getConnection(url, userName, passWord);

            System.out.println("1 for manager | 2 for Employees with Department | 3 for Employees With Contact  ");
            int check = sc.nextInt();
            switch (check){
                case 1: showManager(connection);
                    break;
                case 2: empWithDepartment(connection);
                    break;
                case 3: empWithContact(connection);
                    break;

                default:
                    System.out.println("Oops Enter a valid Data!!!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void showManager(Connection connection){
        try{
            String query = "SELECT employees.first_name, employees.last_name, departments.d_name, departments.manager FROM departments INNER JOIN employees ON employees.id = departments.manager";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String departmentName = resultSet.getString("d_name");
                String manager = firstName+" "+lastName;

                System.out.printf(" DepartmentName: %s | Manager: %s\n",  departmentName, manager);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void empWithDepartment(Connection connection){
        try{
            String query = "SELECT employees.id, employees.first_name, employees.last_name, employees.position, employees.salary, departments.d_name from employees LEFT JOIN departments ON employees.depart_id = departments.d_id";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String position = resultSet.getString("position");
                int salary = resultSet.getInt("salary");
                String d_name = resultSet.getString("d_name");
                String FullName = firstName+" "+lastName;

                System.out.printf("Name: %s | Position: %s | Salary: %d | Department Name: %s\n", FullName, position, salary, d_name);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void empWithContact(Connection connection){
        try{
            String query = "SELECT employees.first_name, employees.last_name, employees.position, employees.salary, employees_contacts.phone, employees_contacts.email, employees_contacts.city, employees_contacts.blood_grp FROM employees_contacts LEFT JOIN employees ON employees.id = employees_contacts.emp_id  ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String position = resultSet.getString("position");
                int salary = resultSet.getInt("salary");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String city = resultSet.getString("city");
                String bloodGrp = resultSet.getString("blood_grp");
                String fullName = firstName + " " + lastName;

                System.out.printf("FullName: %s | Position: %s | Salary: %d | Phone: %s | Email: %s | City: %s | BloodGroup: %s\n",fullName, position, salary, phone, email, city, bloodGrp);

            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

//    public static void allData(Connection connection){
//        System.out.println("work on progress");
//    }
}
