package threads;

import java.lang.ref.WeakReference;
import java.util.List;

import POJO.Quotation;
import databases.AbstractQuotation;
import upv.dadm.quotesapp.FavouriteActivity;

public class BackgroundThreadFavourite extends Thread{

    private WeakReference<FavouriteActivity> reference;

    public BackgroundThreadFavourite(FavouriteActivity favouriteActivity){
        reference = new WeakReference<>(favouriteActivity);
    }

    @Override
    public void run() {
        FavouriteActivity favouriteActivity = reference.get();
        if (favouriteActivity != null){
            List<Quotation> list = AbstractQuotation.getInstace(favouriteActivity).getQuotationDao().findAllQuotes();
            reference.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    favouriteActivity.callAdapterMethod(list);
                }
            });
        }
        super.run();
    }
}
