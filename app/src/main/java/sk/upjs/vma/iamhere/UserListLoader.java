package sk.upjs.vma.iamhere;

import android.content.Context;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;

/**
 * Loader, ktory zabezpecuje nacitanie zoznamu pouzivatelov
 */
public class UserListLoader
        extends AbstractObjectLoader<List<User>> {

    public UserListLoader(Context context) {
        super(context);
    }

    /**
     * Pomocou Retrofitu sa nacitava zoznam pouzivatelov.
     * @return
     */
    @Override
    public List<User> loadInBackground() {
        try {
            // implementacia nami definovaneho interface pre REST
            Api api = RetrofitFactory.getApi();
            // callable object
            Call<List<User>> call = api.getUsers();
            // vykonanie volania + z odpovede sa na return posiela body, teda telo spravy.
            // Retrofit + Jackson zabezpecia parsovanie z JSON a namapovanie na List<User>
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // v pripade zlyhania to vrati prazdny zoznam
        return Collections.emptyList();
    }
}
