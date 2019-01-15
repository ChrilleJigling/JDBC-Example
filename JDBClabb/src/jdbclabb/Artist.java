
package jdbclabb;

public class Artist {
    int ID;
    String firstName;
    String lastName;
    int age;

    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public int getAge() {
        return age;
    }
    
    public Artist(String firstName, String lastName, int age ) {
        ID++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
 }
