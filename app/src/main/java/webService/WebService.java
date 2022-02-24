package webService;

import POJO.Quotation;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebService {

    @GET("api/1.0/?method=getQuote&format=json")
    Call<Quotation> getQuotationWeb(@Query("lang") String language);

    @FormUrlEncoded
    @POST("api/1.0/")
    Call<Quotation> postQuotation(@Field("method") String method, @Field("format") String format, @Field("lang") String lang);
}
