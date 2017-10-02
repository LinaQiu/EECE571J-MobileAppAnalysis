package lina.ubc.sensitivitytestallinone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent fieldIntent = new Intent(this, FieldSensitivity.class);
        startActivity(fieldIntent);
    }
}
