package ge.apex.nika.library1.Data;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Nika on 13/02/2018.
 * This class should create table in database.
 */

@DatabaseTable(tableName = "genres")
public class Genre {
    @DatabaseField(generatedId = true)
    private int genreId;
    @DatabaseField
    private String genreName;

    public Genre() {

    }

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return genreName;
    }

    public void setName(String name) {
        this.genreName = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Genre id=").append(genreId);
        sb.append(", ").append("Category=").append(genreName);
        return sb.toString();
    }
}
