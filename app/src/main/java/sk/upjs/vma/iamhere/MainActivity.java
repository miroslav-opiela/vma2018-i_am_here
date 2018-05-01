package sk.upjs.vma.iamhere;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

/**
 * Hlavna aktivita implementuje OnRefreshListener - reaguje na refresh potiahnutim prsta na Swipe Layoute
 * a LoaderCallback - reaguje na udalosti pri nacitavani dat do adaptera pomocou UserListLoader.
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        LoaderManager.LoaderCallbacks<List<User>> {

    /**
     * EditText na vlozenie username.
     */
    private EditText editText;

    /**
     * View zobrazujuci zoznam pouzivatelov.
     */
    private ListView listView;
    /**
     * Model reprezentujuci zoznam pouzivatelov.
     */
    private ArrayAdapter<User> listAdapter;

    /**
     * Layout, na ktorom sa daju robit swipe-to-refresh gesta.
     */
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        // vytvara sa model pre listView, ktory je zatial prazdny.
        listAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1);

        listView = findViewById(R.id.usersList);
        listView.setAdapter(listAdapter);

        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        // data sa nacitavaju aj po prvom spusteni aktivity
        loadData();
    }

    /**
     * Nacita data do modelu pre list view.
     */
    private void loadData() {
        getLoaderManager().restartLoader(0, Bundle.EMPTY, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // ulozi sa obsah editText widgetu do preferences (v style key-value)
        getPreferences(Activity.MODE_PRIVATE).edit()
                .putString("name", editText.getText().toString()).apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // nacita sa ulozeny obsah editTextu
        String name = getPreferences(Activity.MODE_PRIVATE).getString("name", "");
        editText.setText(name);
    }

    public void sendLogin(View view) {
        // username sa vytiahne z editText input widgetu
        String username = editText.getText().toString();
        // vytvori sa novy async task
        IAmHereAsyncTask task = new IAmHereAsyncTask(this);
        // spusti sa async task, ktory asynchronne odosle informaciu cez REST API
        task.execute(username);
    }

    @Override
    public void onRefresh() {
        Log.d("REFRESH", "refresh");
        // po swipe-to-refresh geste sa nacitaju data
        loadData();
        // nastavi sa refreshing na false. Ide o pripad ked sme refresh vyvolali gestom. Bez toho sa to snazi opakovane refreshovat.
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Pri vytvarani loadera sa vytvori UserListLoader.
     */
    @Override
    public Loader<List<User>> onCreateLoader(int i, Bundle bundle) {
        return new UserListLoader(this);
    }

    /**
     * Po nacitani dat loaderom sa doterajsie data v adapteri vymazu a naplnia aktualnymi.
     */
    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> users) {
        listAdapter.clear();
        listAdapter.addAll(users);
    }

    /**
     * onLoaderReset vymaze data v adapteri.
     */
    @Override
    public void onLoaderReset(Loader<List<User>> loader) {
        listAdapter.clear();
    }
}
