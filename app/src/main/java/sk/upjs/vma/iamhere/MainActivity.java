package sk.upjs.vma.iamhere;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

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

        getPreferences(Activity.MODE_PRIVATE).edit()
                .putString("name", editText.getText().toString()).apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String name = getPreferences(Activity.MODE_PRIVATE).getString("name", "");
        editText.setText(name);
    }
}
