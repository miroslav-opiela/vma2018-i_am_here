package sk.upjs.vma.iamhere;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Definovane REST API. Interface, ktore vyuzije Retrofit.
 */
public interface Api {

    /**
     * POST .../available-users/username
     *
     * @param username parameter username - to co ide na konci URL
     * @return retrofit zabezpeci callable object, pomocou ktoreho sa vykona HTTP spojenie.
     */

    @POST("available-users/{username}")
    Call<Void> register(@Path("username") String username);

    /**
     * GET .../available-users
     *
     * @return callable object, ocakava sa zoznam pouzivatelov.
     */
    @GET("available-users")
    Call<List<User>> getUsers();

}
