import java.sql.*;
import java.util.Scanner;

public class employees_contacts {
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
            System.out.println("Employees Contacts Table Open");
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
            String query = "INSERT INTO employees_contacts(phone, dob, email, city, state, zip_code, country, blood_grp, emp_id) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            boolean check = true;
            Scanner sc = new Scanner(System.in);
            while(check){
                System.out.print("Phone: ");
                String phone = sc.next();
                System.out.print("DOB(yyyy-mm-dd): ");
                String dob = sc.next();
                System.out.print("Email: ");
                String email = sc.next();
                sc.nextLine();
                System.out.print("City: ");
                String city = sc.nextLine();
                System.out.print("State: ");
                String state = sc.nextLine();
                System.out.print("Zip Code: ");
                String zip = sc.next();
                sc.nextLine();
                System.out.print("Country: ");
                String country = sc.nextLine();
                System.out.print("Blood Group: ");
                String blood_grp = sc.nextLine();
                System.out.print("Employee Id: ");
                int emp_id = sc.nextInt();
                System.out.print("Can You Enter More Data(Y/N): ");
                String chk = sc.next();
                preparedStatement.setString(1, phone);
                preparedStatement.setString(2, dob);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, city);
                preparedStatement.setString(5, state);
                preparedStatement.setString(6, zip);
                preparedStatement.setString(7, country);
                preparedStatement.setString(8, blood_grp);
                preparedStatement.setInt(9, emp_id);

                preparedStatement.addBatch();

                if(chk.toUpperCase().equals("N")){
                    System.out.println("Existing...");
                    check = false;
                }



            }
            int[] arr = preparedStatement.executeBatch();
            for(int i=0; i< arr.length; i++){
                if(arr[i] == 0){
                    System.out.println("Insert Data Failed at batch No: "+ i);
                }
            }
            read(connection);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void read(Connection connection){
        try{
            String query = "SELECT * FROM employees_contacts";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("=== Employees Contacts Table Data ===");
            while(resultSet.next()){
                int id = resultSet.getInt("eid");
                String phone = resultSet.getString("phone");
                String dob = resultSet.getString("dob");
                String email = resultSet.getString("email");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String zip_code = resultSet.getString("zip_code");
                String country = resultSet.getString("country");
                String blood_grp = resultSet.getString("blood_grp");
                int emp_id = resultSet.getInt("emp_id");

                // Print employee details
                System.out.printf("ID: %d | Phone: %s  | DOB: %s | Email: %s | City: %s | State: %s | Zip Code: %s | Country: %s | Blood Group: %s | Emp_id: %d\n",
                        id, phone, dob, email, city, state, zip_code, country, blood_grp, emp_id);
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void update(Connection connection){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the id which you want to Update: ");
        int n = sc.nextInt();
        try{
            String query = "UPDATE employees_contacts SET phone=?, dob=?, email=?, city=?, state=?, zip_code=?, country=?, blood_grp=?, emp_id=? WHERE eid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            System.out.print("Phone: ");
            String phone = sc.next();
            System.out.print("DOB(yyyy-mm-dd): ");
            String dob = sc.next();
            System.out.print("Email: ");
            String email = sc.next();
            sc.nextLine();
            System.out.print("City: ");
            String city = sc.nextLine();
            System.out.print("State: ");
            String state = sc.nextLine();
            System.out.print("Zip Code: ");
            String zip = sc.next();
            sc.nextLine();
            System.out.print("Country: ");
            String country = sc.nextLine();
            System.out.print("Blood Group: ");
            String blood_grp = sc.nextLine();
            System.out.print("Employee Id: ");
            int emp_id = sc.nextInt();
            preparedStatement.setString(1, phone);
            preparedStatement.setString(2, dob);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, city);
            preparedStatement.setString(5, state);
            preparedStatement.setString(6, zip);
            preparedStatement.setString(7, country);
            preparedStatement.setString(8, blood_grp);
            preparedStatement.setInt(9, emp_id);
            preparedStatement.setInt(10, n);

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0){
                System.out.println("Data Updated Successfully!");
                read(connection);
            }else{
                System.out.println("Oops No data Found in id number: "+ n);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void delete(Connection connection){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the id which you want to Delete: ");
        int n = sc.nextInt();
        try{
            String query = "DELETE FROM employees_contacts WHERE eid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, n);

            int effectedRows = preparedStatement.executeUpdate();
            if(effectedRows > 0){
                System.out.println("data Deleted Successfully!");
                read(connection);
            }else{
                System.out.println("Oops No data Found in id number: "+ n);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
