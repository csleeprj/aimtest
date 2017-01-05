package com.appinsightor.android.myapplication;

import android.content.Context;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //private Context context_;
//    private static String sID = null;
//    private static final String INSTALLATION = "INSTALLATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String strTmp = getDeviceSerialNumber();
        Log.i("~~~",strTmp);
        String strTmp1 = Settings.Secure.ANDROID_ID;
        Log.i("===",strTmp1);

        String strTmp2 = id(this);
        Log.i("+++",strTmp2);
        Log.i("---","---");

        //
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        StackTraceElement currentStack = stacks[0];
        System.out.println("Class : "+currentStack.getClassName());
        System.out.println("Method : "+currentStack.getMethodName());

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for(StackTraceElement element : elements) {

                System.out.println("------:::--:"+element.getMethodName());
        }

    }

    private static String getDeviceSerialNumber() {
        try {
            Log.i("--------","---");

            return (String) Build.class.getField("SERIAL").get(null);
        } catch (Exception ignored) {
            return null;
        }
    }

    public synchronized static String id(Context context) {
        String sID = null;
        final String INSTALLATION = "INSTALLATION";
        if (sID == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists())
                    writeInstallationFile(installation);
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        return sID;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }
}
