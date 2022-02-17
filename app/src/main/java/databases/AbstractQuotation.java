package databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import POJO.Quotation;

@Database(version = 1, entities = {Quotation.class})
public abstract class AbstractQuotation extends RoomDatabase {

    private static AbstractQuotation instancia;

    public static synchronized AbstractQuotation getInstace(Context context){
        if(instancia == null){
            instancia = Room.databaseBuilder(context, AbstractQuotation.class, "quotation").allowMainThreadQueries().build();
        }
        return instancia;
    }

    public abstract QuotationDAO getQuotationDao();

}
