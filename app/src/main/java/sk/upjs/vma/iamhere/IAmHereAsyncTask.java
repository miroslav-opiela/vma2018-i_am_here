package sk.upjs.vma.iamhere;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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
     *                V tomto pripade pride URL na REST.
     * @return true ak je spojenie uspesne.
     */
    @Override
    protected Boolean doInBackground(String... strings) {
        // url vytiahnuty z parametra metody
        String urlString = strings[0];

        // reprezentuje HTTP spojenie
        HttpURLConnection connection = null;
        try {
            // URL sa vytvori zo stringu ktory prisiel cez parameter
            URL url = new URL(urlString);
            // vytvorenie spojenia, je potrebne precastovat na HttpURLConnection
            connection = (HttpURLConnection) url.openConnection();
            // nastavenie prislusnej HTTP metody podla REST API
            connection.setRequestMethod("POST");
            // vrati true ak HTTP response code je 200, teda HTTP_OK
            return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            // zatvorenie spojenia
            if (connection != null) {
                connection.disconnect();
            }
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
