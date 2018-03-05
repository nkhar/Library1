package ge.apex.nika.library1.Data;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Nika on 13/02/2018.
 * This class should create a table in database
 */

@DatabaseTable(tableName = "books")
public class Book {
    @DatabaseField(generatedId = true)
    private int bookId;
    @DatabaseField
    private String title;
    @DatabaseField(foreign = true, foreignAutoRefresh=true)
    private Author authorId;
    @DatabaseField(foreign = true, foreignAutoRefresh=true)
    private Genre genreId;
    @DatabaseField
    private int date;
    @DatabaseField
    private String lang;

    public Book() {

    }

    public Book(String title, Author authorId, Genre genreId, int date, String lang) {

        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
        this.date = date;
        this.lang = lang;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Author authorId) {
        this.authorId = authorId;
    }

    public Genre getGenreId() {
        return genreId;
    }

    public void setGenreId(Genre genreId) {
        this.genreId = genreId;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Book id=").append(bookId);
        sb.append(", ").append("Title=").append(title);
        sb.append(", ").append("Author id=").append(authorId);
        sb.append(", ").append("Genre id=").append(genreId);
        sb.append(", ").append("Date published=").append(date);
        sb.append(", ").append("Language=").append(lang);
        return sb.toString();
    }
}
