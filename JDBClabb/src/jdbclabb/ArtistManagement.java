package jdbclabb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ArtistManagement {

    public static Scanner sc = new Scanner(System.in);
    String url = "jdbc:mysql://localhost/mydb";
    String user = "root";
    String pw = "Smedvagen28";
    Connection conn;
    PreparedStatement printAll, searchByID, addArtist;

    public ArtistManagement() throws SQLException {
        try {
            conn = DriverManager.getConnection(url, user, pw);
            printAll = conn.prepareStatement("select * from artists;");
            searchByID = conn.prepareStatement("select * from artists where Artist_id = ?");
            addArtist = conn.prepareStatement("insert into artists (first_name, last_name, birth_year) values(?,?,?)");
        } catch (SQLException e) {
            System.out.println("ERROR104: Can't connect!");
            e.printStackTrace();
        }
    }

    public void addArtistToDatabase(Artist a) throws SQLException {
        try {
            addArtist.setString(1, a.getFirstName());
            addArtist.setString(2, a.getLastName());
            addArtist.setInt(3, a.getAge());
            addArtist.execute();
        } catch (Exception e) {
            System.out.println("ERROR! SOMETHING HAS GONE WRONG");
            e.printStackTrace();
        }
    }

    public void searchByID(String id) {
        try {
            searchByID.setString(1, id);
            ResultSet myRs = searchByID.executeQuery();
            while (myRs.next()) {
                System.out.println("ID: " + myRs.getInt("Artist_ID") + " " + myRs.getString("first_Name") + " " + myRs.getString("last_Name") + " " + myRs.getInt("birth_year"));
            }
        } catch (Exception e) {
            System.out.println("ERROR103: SOMETHING HAS GONE WRONG!");
            e.printStackTrace();
        }
    }

    public void showAllFromDatabase() {
        try {
            ResultSet myRs = printAll.executeQuery();
            while (myRs.next()) {
                System.out.println("ID: " + myRs.getInt("Artist_ID") + " " + myRs.getString("first_Name") + " " + myRs.getString("last_Name") + " " + myRs.getInt("birth_year"));
            }
        } catch (Exception e) {
            System.out.println("ERROR103: SOMETHING HAS GONE WRONG!");
            e.printStackTrace();
        }

    }

    public void deleteArtistFromDatabase(String query, String parameter) throws SQLException {
        try {
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1, parameter);
            p.executeUpdate();
            System.out.println("The artist has been removed!");
        } catch (Exception e) {
            System.out.println("ERROR101: The artist doesn't exists in the database");
            e.printStackTrace();
        }
    }

    public void updateArtist(String query, String parameter, String id) throws SQLException {
        try {
            PreparedStatement p = conn.prepareStatement(query);
            p.setString(1, parameter);
            p.setString(2, id);
            p.executeUpdate();
            System.out.println("The artist has been updated!");
        } catch (Exception e) {
            System.out.println("ERROR102 SOMETHING HAS GONE WRONG!");
            e.printStackTrace();
        }
    }

    public void mainMenu() throws SQLException {
        boolean loop = true;
        while (loop) {
            System.out.println("WELCOME TO THE ARTIST DATABASE");
            System.out.println("------------------------------");
            System.out.println(" 1. Add an artist"
                    + "\n 2. Delete"
                    + "\n 3. Update"
                    + "\n 4. Show all"
                    + "\n 5. Search by ID");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("First name:");
                    String firstName = sc.nextLine();
                    System.out.println("Last name:");
                    String lastName = sc.nextLine();
                    System.out.println("Birthyear:");
                    int age = sc.nextInt();
                    sc.nextLine();
                    Artist a = new Artist(firstName, lastName, age);
                    addArtistToDatabase(a);
                    System.out.println("The artist has been added!");
                    break;
                case "2":
                    deleteMenu();
                    break;
                case "3":
                    updateMenu();
                    break;
                case "4":
                    showAllFromDatabase();
                    break;
                case "5":
                    System.out.println("Enter ID: ");
                    String idChoice = sc.nextLine();
                    searchByID(idChoice);
                    break;
                default:
            }
        }

    }

    private void deleteMenu() throws SQLException {
        System.out.println("Delete artist menu");
        System.out.println("------------------");
        System.out.println("1. Delete by first name");
        System.out.println("2. Delete by last name");
        System.out.println("3. Delete by id");
        String deleteChoice = sc.nextLine();
        switch (deleteChoice) {
            case "1":
                System.out.println("Enter first name:");
                String fNameDelete = sc.nextLine();
                deleteArtistFromDatabase("DELETE FROM ARTISTS WHERE FIRST_NAME = ?", fNameDelete);
                break;
            case "2":
                System.out.println("Enter last name:");
                String lNameDelete = sc.nextLine();
                deleteArtistFromDatabase("DELETE FROM ARTISTS WHERE LAST_NAME = ?", lNameDelete);
                break;
            case "3":
                System.out.println("Enter ID:");
                String idChoice = sc.nextLine();
                deleteArtistFromDatabase("DELETE FROM ARTISTS WHERE ARTIST_ID = ?", idChoice);
                break;
            default:

        }
    }

    private void updateMenu() throws SQLException {
        System.out.println("Delete artist menu");
        System.out.println("------------------");
        System.out.println("1. Update first name");
        System.out.println("2. Update last name");
        String deleteChoice = sc.nextLine();
        switch (deleteChoice) {
            case "1":
                System.out.println("Enter ID on the artist you want to update:");
                String idChoice = sc.nextLine();
                System.out.println("Enter new first name:");
                String newFirstName = sc.nextLine();
                updateArtist("UPDATE ARTISTS SET FIRST_NAME = ? WHERE Artist_ID = ?", newFirstName, idChoice);
                break;
            case "2":
                System.out.println("Enter ID on the artist you want to update:");
                String idChoice2 = sc.nextLine();
                System.out.println("Enter new last name:");
                String newLastName = sc.nextLine();
                updateArtist("UPDATE ARTISTS SET LAST_NAME = ? WHERE Artist_ID = ?", newLastName, idChoice2);
                break;
            case "3":
            default:
                System.out.println("wrong input!");
                break;

        }
    }

}
