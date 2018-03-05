package ge.apex.nika.library1.Data;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Nika on 13/02/2018.
 * This should create table in database
 */

@DatabaseTable(tableName = "authors")
public class Author {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String FName;
    @DatabaseField
    private String LName;
    @DatabaseField
    private int dateBorn;

    public Author(){

    }
    public Author(String firstName, String lastName, int dBirth){
        FName = firstName;
        LName = lastName;
        dateBorn = dBirth;
    }

    public int getId() {
        return id;
    }

    public String getFName() {
        return FName;
    }

    public String getLName() {
        return LName;
    }

    public int getDateBorn() { return dateBorn; }

    public void setId(int newId) {
        id = newId;
    }

    public void setFName(String firstName) {
        FName = firstName;
    }

    public void setLName(String lastName) {
        LName = lastName;
    }

    public void setDateBorn(int dBirth) { dateBorn=dBirth; }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("id=").append(id);
        sb.append(", ").append("first name=").append(FName);
        sb.append(", ").append("last name=").append(LName);
        sb.append(", ").append("Date of Birth=").append(dateBorn);
        return sb.toString();
    }
}
