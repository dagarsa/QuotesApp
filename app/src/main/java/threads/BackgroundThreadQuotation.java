package threads;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import POJO.Quotation;
import databases.AbstractQuotation;
import fragments.QuotationFragment;

public class BackgroundThreadQuotation extends Thread{

    private WeakReference<QuotationFragment> reference;

    public BackgroundThreadQuotation(QuotationFragment quotationFragment){
        reference = new WeakReference<>(quotationFragment);
    }

    @Override
    public void run() {
        QuotationFragment quotationFragment = reference.get();
        FragmentActivity fragmentActivity =  quotationFragment.getActivity();
        String quotationString = quotationFragment.getTvQuotation().getText().toString();
        Quotation quotation = AbstractQuotation.getInstace(quotationFragment.requireContext()).getQuotationDao().findByString(quotationString);
        fragmentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                quotationFragment.callAdapterMethod(quotation);
            }
        });
    }
}
