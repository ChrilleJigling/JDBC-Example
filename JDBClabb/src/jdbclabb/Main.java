package jdbclabb;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        ArtistManagement am = new ArtistManagement();
        am.mainMenu();   
    }
}
