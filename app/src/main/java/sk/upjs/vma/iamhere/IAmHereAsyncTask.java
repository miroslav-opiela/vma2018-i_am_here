package sk.upjs.vma.iamhere;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit2.Call;

public class IAmHereAsyncTask extends AsyncTask<String, Void, Boolean> {

    /**
     * Referencia na aktivitu, z ktorej sa volal dany task, kvoli notifikacii.
     * Tu by malo zmysel dat WeakReference.
     */

    private Activity activity;

    public IAmHereAsyncTask(Activity activity) {
        this.activity = activity;
    }

    /**
     * Tato metoda sa vykonava v inom vlakne.
     *
     * @param strings tri bodky oznacuju lubovolny pocet stringov, pracuje sa s tym ako s polom.
     *                V tomto pripade pride username.
     * @return true ak je spojenie uspesne.
     */
    @Override
    protected Boolean doInBackground(String... strings) {
        // username vytiahnuty z parametra metody
        String username = strings[0];

        try {
            // ziskanie implementacie API interface
            Api api = RetrofitFactory.getApi();
            // callable object
            Call<Void> call = api.register(username);
            // vykonanie volania, do premennej sa vlozi navratovy kod volania
            int code = call.execute().code();
            // ak je kod rovny HTTP_OK cize 200
            return code == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Tato metoda sa vykonava v hlavnom vlakne a tu sa da aktualizovat UI.
     * Tu sa uz nesmu vykonavat dlhotrvajuce operacie.
     *
     * @param aBoolean  vysledok z metody doInBackground.
     */
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        // Toast nofifikacia podla uspesnosti spojenia
        if (aBoolean) {
            Toast.makeText(activity, "Spojenie uspesne", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, "Spojenie zlyhalo", Toast.LENGTH_LONG).show();
        }
    }

}
