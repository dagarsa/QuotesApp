package POJO;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "quotation")
public class Quotation {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "quoteText")
    @NonNull
    private String quoteText;

    @ColumnInfo(name = "quoteAuthor")
    private String quoteAuthor;

    public Quotation() {
    }

    public Quotation(String quoteText, String quoteAuthor) {
        this.quoteText = quoteText;
        this.quoteAuthor = quoteAuthor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }
}
