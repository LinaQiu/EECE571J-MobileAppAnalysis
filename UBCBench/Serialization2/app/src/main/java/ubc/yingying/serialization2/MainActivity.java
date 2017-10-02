package ubc.yingying.serialization2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import android.telephony.TelephonyManager;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.util.Log;

// Serialization in java is a mechanism of writing the state of an object into a byte stream.


/**
 * @testcase_name Serialization2
 *
 * @description Test whether the analysis tool can handle serialization.
 * This testcase is used as an extension to the testcase DroidBench-GeneralJava-Serialization1 in DroidBench
 * Based on DroidBench - Serialization1, we replaced `ByteArrayInputStream/ByteArrayOutputStream` to `FileInputStream/FileOutputStream` in this testcase.
 *
 * @expected_source: getDeviceId()
 * @ecpected_sink:  Log.i()
 * @number_of_leaks: 1
 * @flow_path:
 * line 42: onCreate() -->
 * line 47: String imei = mgr.getDeviceId();  -->
 * line 48: S s1 = new S(imei); -->
 * line 52: out.writeObject(s1) -->
 * line 56: ObjectInputStream in -->
 * line 57: S s2=(S)in.readObject(); -->
 * line 60: Log.i("DroidBench", s2.toString()); [sink]
 */



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager mgr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        String imei = mgr.getDeviceId(); //source
        S s1 = new S(imei);
        try {
            FileOutputStream fout = new FileOutputStream("file.txt");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(s1);
            out.flush();
            System.out.println("success");

            ObjectInputStream in=new ObjectInputStream(new FileInputStream("f.txt"));
            S s2=(S)in.readObject();
            in.close();

            Log.i("DroidBench", s2.toString()); //sink

        } catch (Exception e) {
        }
    }

    // S is a serializable class, its object can be converted to stream
    class S implements Serializable {

        private static final long serialVersionUID = -1155152173616606359L;

        private String message;

        public S(String message) {
            this.message = message;
        }

        public String toString() {
            return message;
        }

    }
}

