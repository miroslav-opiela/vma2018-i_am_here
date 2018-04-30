package sk.upjs.vma.iamhere;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String REST_URL =  "http://ics.upjs.sk/~opiela/rest/index.php/available-users";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
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
        task.execute(REST_URL + "/" + username);
    }
}
