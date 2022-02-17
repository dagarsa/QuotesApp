package databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import POJO.Quotation;

@Dao
public interface QuotationDAO {

    @Insert
    void addQuote(Quotation quotation);

    @Delete
    void deleteQuote(Quotation quotation);

    @Query("SELECT * FROM quotation")
    List<Quotation> findAllQuotes();

    @Query("SELECT * FROM quotation WHERE quoteText = :quote")
    Quotation findByString(String quote);

    @Query("DELETE FROM quotation")
    void deleteAllQuotes();
}
