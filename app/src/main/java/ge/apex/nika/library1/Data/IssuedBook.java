package ge.apex.nika.library1.Data;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by NATIA on 13/02/2018.
 */

@DatabaseTable(tableName = "issuedbooks")
public class IssuedBook {
    @DatabaseField(generatedId = true)
    private int issuedBookId;
    @DatabaseField
    private int bookId;
    @DatabaseField
    private int issueDate;
    @DatabaseField
    private int ISBNCode;


    public IssuedBook() {

    }

    public IssuedBook(int bookId, int issueDate, int ISBNCode) {
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.ISBNCode = ISBNCode;
    }

    public int getIssuedBookId() {
        return issuedBookId;
    }

    public void setIssuedBookId(int issuedBookId) {
        this.issuedBookId = issuedBookId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(int issueDate) {
        this.issueDate = issueDate;
    }

    public int getISBNCode() {
        return ISBNCode;
    }

    public void setISBNCode(int ISBNCode) {
        this.ISBNCode = ISBNCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Issued Book id=").append(issuedBookId);
        sb.append(", ").append("Book id=").append(bookId);
        sb.append(", ").append("Issue Date=").append(issueDate);
        sb.append(", ").append("ISBN Code=").append(ISBNCode);
        return sb.toString();
    }
}
