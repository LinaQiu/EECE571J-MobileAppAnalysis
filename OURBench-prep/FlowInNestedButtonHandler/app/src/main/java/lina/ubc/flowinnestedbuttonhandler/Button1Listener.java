package lina.ubc.flowinnestedbuttonhandler;

import android.view.View;
import android.widget.Button;

/**
 * Created by lina on 2017-12-06.
 */

public class Button1Listener implements View.OnClickListener {

    private final MainActivity act;

    public Button1Listener(MainActivity parentActivity) {
        this.act = parentActivity;
    }

    @Override
    public void onClick(View arg0) {

        Button button2 = (Button) act.findViewById(R.id.button2);
        button2.setOnClickListener(new Button2Listener(act));
    }
}
