
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jdbclabb.Artist;
import jdbclabb.ArtistManagement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class Testing {

    String url = "jdbc:mysql://localhost/mydb";
    String user = "root";
    String pw = "Smedvagen28";
    Connection conn;
    static PreparedStatement printAll, searchByID, addArtist, deleteArtistByLastName, deleteArtistByID, updateLastName, updateFirstName;
    public Testing() {

    }

    @BeforeClass
    public static void setUpClass() throws SQLException {
        String url = "jdbc:mysql://localhost/mydb";
        String user = "root";
        String pw = "Smedvagen28";
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, user, pw);
            updateLastName = conn.prepareStatement("update from artists set last_name = ? where artist_id = ?");
            updateFirstName = conn.prepareStatement("update from artists set first_name = ? where artist_id = ?");
            deleteArtistByID = conn.prepareStatement("delete from artists where artist_id = ?");
            deleteArtistByLastName = conn.prepareStatement("delete from artists where last_name = ?");
            printAll = conn.prepareStatement("select * from artists;");
            searchByID = conn.prepareStatement("select * from artists where Artist_id = ?");
            addArtist = conn.prepareStatement("insert into artists (first_name, last_name, birth_year) values(?,?,?)");
        } catch (SQLException e) {
            System.out.println("THE TEST HAS FAILED!");

        }
    }

    @Test
    public void addArtist() throws SQLException {
        ArtistManagement a = new ArtistManagement();
        Artist newA = new Artist("Chrille", "Jigling", 1994);
        a.addArtistToDatabase(newA);
    }

    @Test
    public void showAll() throws SQLException {
        
        try {
            ResultSet myRs = printAll.executeQuery();
            while (myRs.next()) {
                System.out.println(myRs.getInt("Artist_ID") + " " + myRs.getString("first_Name") + " " + myRs.getString("last_Name") + " " + myRs.getInt("birth_year"));
            }
        } catch (Exception e) {
            System.out.println("ERROR103: SOMETHING HAS GONE WRONG!");
            e.printStackTrace();
        }
    }

    @Test
    public void deleteArtist() {
        try {
            deleteArtistByID.setString(1, "2");
            deleteArtistByID.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR101: The artist doesn't exists in the database");
            e.printStackTrace();
        }  
    }
    @Test
    public void deleteByLastName() {
        try {
            deleteArtistByLastName.setString(1, "Jigling");
            deleteArtistByLastName.executeUpdate();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    @Test
    public void updateLastName() {
        try{
        updateLastName.setString(1, "TESTSHIT");
        updateLastName.setString(2, "3");
        }
        catch(Exception e) {
            System.out.println("ERROR102 SOMETHING HAS GONE WRONG!");
            e.printStackTrace();
        }
    }
    @Test
    public void updateFirstName() {
        try{
        updateFirstName.setString(1, "TESTSKIT");
        updateFirstName.setString(2, "4");
        }
        catch(Exception e) {
            System.out.println("ERROR102 SOMETHING HAS GONE WRONG!");
            e.printStackTrace();
        }
    }

   
}
