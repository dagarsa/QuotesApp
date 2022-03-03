package threads;

import java.lang.ref.WeakReference;
import java.util.List;

import POJO.Quotation;
import databases.AbstractQuotation;
import fragments.FavouriteFragment;

public class BackgroundThreadFavourite extends Thread{

    private WeakReference<FavouriteFragment> reference;

    public BackgroundThreadFavourite(FavouriteFragment favouriteFragment){
        reference = new WeakReference<>(favouriteFragment);
    }

    @Override
    public void run() {
        FavouriteFragment favouriteFragment = reference.get();
        if (favouriteFragment != null){
            List<Quotation> list = AbstractQuotation.getInstace(favouriteFragment.requireContext()).getQuotationDao().findAllQuotes();
            favouriteFragment.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    favouriteFragment.callAdapterMethod(list);
                }
            });
        }
        super.run();
    }
}
