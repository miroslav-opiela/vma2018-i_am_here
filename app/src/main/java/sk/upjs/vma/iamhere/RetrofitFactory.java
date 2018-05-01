package sk.upjs.vma.iamhere;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitFactory {

    /**
     * Vybuilduje Retrofit objekt pre prislusnu REST API URL spolu s definovanim Jackson konvertera, ktory parsuje JSON.
     */
    public static Retrofit getRetrofit() {
        String urlString = "http://ics.upjs.sk/~opiela/rest/index.php/";

        return new Retrofit.Builder()
                .baseUrl(urlString)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    /**
     * Vrati implementaciu pozadovaneho interface pre REST API.
     */
    public static Api getApi() {
        return getRetrofit().create(Api.class);
    }

}
