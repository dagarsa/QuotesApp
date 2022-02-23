package threads;

import java.lang.ref.WeakReference;
import java.util.List;

import POJO.Quotation;
import databases.AbstractQuotation;
import upv.dadm.quotesapp.QuotationActivity;

public class BackgroundThreadQuotation extends Thread{

    private WeakReference<QuotationActivity> reference;

    public BackgroundThreadQuotation(QuotationActivity quotationActivity){
        reference = new WeakReference<>(quotationActivity);
    }

    @Override
    public void run() {
        QuotationActivity quotationActivity = reference.get();
        if (quotationActivity != null){
            if(quotationActivity.getTvQuotation()!=null) {
                String quotationString = quotationActivity.getTvQuotation().getText().toString();
                Quotation quotation = AbstractQuotation.getInstace(quotationActivity).getQuotationDao().findByString(quotationString);
                reference.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        quotationActivity.callAdapterMethod(quotation);
                    }
                });
            }
        }
        super.run();
    }
}
