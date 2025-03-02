import java.sql.*;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        employees emp = new employees();
        departments depart = new departments();
        employees_contacts ec = new employees_contacts();
        showingTable st = new showingTable();

        Scanner sc = new Scanner(System.in);
        System.out.print("Press 1 for showing table | press 2 for CRUD operation: ");
        int first_check = sc.nextInt();
        if(first_check == 1){
            st.loadData();

        } else if (first_check == 2) {
            System.out.println("1 for Employees table | 2 for Departments table | 3 for Employees_Contacts Table");
            int check = sc.nextInt();

            switch (check){
                case 1: emp.showData();
                    break;
                case 2:
                    depart.showData();
                    break;
                case 3:
                    ec.showData();
                    break;
                default:
                    System.out.println("Enter a Valid data!!!");
            }
        }else{
            System.out.println("Oops Enter a valid data!!!");
        }

    }
}