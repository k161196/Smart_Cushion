package com.example.kms.kiran_c;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class bluetooth_data extends Activity {
    private static final int REQUEST_ENABLE_BT = 1;
    public String msgRecived;

    private ListView lv;
    private ArrayList<String> strArr;
    private ArrayAdapter<String> adapter;


    BluetoothAdapter bluetoothAdapter;

    ArrayList<BluetoothDevice> pairedDeviceArrayList;

    TextView textInfo, textStatus, statusinfo,infoDis,infoHr,time,user;
    ListView listViewPairedDevice,listView;
    LinearLayout inputPane;
    EditText inputField;
    Button statusb;
    Button nextpg;
    private TextView timerValue,timerValue2;
    private long startTime = 0L;
    ImageView imgslide,states;

    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    long temp,millis2;
    long millis,m;
    boolean stopTimer = false;


    ArrayAdapter<BluetoothDevice> pairedDeviceAdapter;
    private UUID myUUID;
    private final String UUID_STRING_WELL_KNOWN_SPP =
            "00001101-0000-1000-8000-00805F9B34FB";

    ThreadConnectBTdevice myThreadConnectBTdevice;
    ThreadConnected myThreadConnected;
    private static Button button_start1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private long tr=5000;
    ArrayList<String> arrayListpaired;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        //getActionBar().hide();
        setContentView(R.layout.bluetooth_data);
        textInfo = (TextView) findViewById(R.id.info);
        textStatus = (TextView) findViewById(R.id.status);
        statusinfo = (TextView) findViewById(R.id.st);
        listViewPairedDevice = (ListView) findViewById(R.id.pairedlist);
        listView = (ListView) findViewById(R.id.listView);

        inputPane = (LinearLayout) findViewById(R.id.inputpane);
        timerValue = (TextView) findViewById(R.id.timmer);
        timerValue2 = (TextView) findViewById(R.id.timmer2);
        infoDis = (TextView) findViewById(R.id.infoDis);
        infoHr = (TextView) findViewById(R.id.infoHr);
        statusb = (Button) findViewById(R.id.nextpg);
        //user =(TextView) findViewById(R.id.user);
        //time = (TextView) findViewById(R.id.timmer);
        imgslide=(ImageView)findViewById(R.id.imgslide);
        states=(ImageView)findViewById(R.id.states);
        String username = getIntent().getStringExtra("Username");
        arrayListpaired = new ArrayList<String>();


        lv = (ListView) findViewById(R.id.listView);

        strArr = new ArrayList<String>();
        for (int i = 0; i < 2; i++) {
            strArr.add("Row:" + i);
        }
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, strArr);
        lv.setAdapter(adapter);
        //tv.setText(username);
        // inputField = (EditText)findViewById(R.id.input);
       /* btnSend = (Button)findViewById(R.id.send);
        btnSend.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(myThreadConnected!=null){
                    byte[] bytesToSend = inputField.getText().toString().getBytes();
                    myThreadConnected.write(bytesToSend);
                }
            }});*/

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            Toast.makeText(this,
                    "FEATURE_BLUETOOTH NOT support",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        //using the well-known SPP UUID
        myUUID = UUID.fromString(UUID_STRING_WELL_KNOWN_SPP);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this,
                    "Bluetooth is not supported on this hardware platform",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        String stInfo = bluetoothAdapter.getName() + "\n" +
                bluetoothAdapter.getAddress();
        textInfo.setText(stInfo);
        OnClickButtonLIstener();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();





        final Handler mhandler = new Handler();
        mhandler.postDelayed(new Runnable() {
            public void run() {
                Log.d("ki", "runnning");
                tr=tr-1000;
                if(tr>0){
                    mhandler.postDelayed(this,3000);

                }

            }
        }, 2000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();

        //Turn ON BlueTooth if it is OFF
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        setup();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "bluetooth_data Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.kms.kiran_c/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    private void setup() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            pairedDeviceArrayList = new ArrayList<BluetoothDevice>();

            for (BluetoothDevice device : pairedDevices) {
                arrayListpaired.add(device.getName()+"\n"+device.getAddress());
                pairedDeviceArrayList.add(device);

            }

            pairedDeviceAdapter = new ArrayAdapter<BluetoothDevice>(this,
                    android.R.layout.simple_list_item_single_choice, pairedDeviceArrayList);
            listViewPairedDevice.setAdapter(pairedDeviceAdapter);

            listViewPairedDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    BluetoothDevice device =
                            (BluetoothDevice) parent.getItemAtPosition(position);
                    Toast.makeText(bluetooth_data.this,
                            "Name: " + device.getName() + "\n"
                                    + "Address: " + device.getAddress() + "\n"
                                    + "BondState: " + device.getBondState() + "\n"
                                    + "BluetoothClass: " + device.getBluetoothClass() + "\n"
                                    + "Class: " + device.getClass(),
                            Toast.LENGTH_LONG).show();

                    textStatus.setText("start ThreadConnectBTdevice");
                    myThreadConnectBTdevice = new ThreadConnectBTdevice(device);
                    myThreadConnectBTdevice.start();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (myThreadConnectBTdevice != null) {
            myThreadConnectBTdevice.cancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                setup();
            } else {
                Toast.makeText(this,
                        "BlueTooth NOT enabled",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    //Called in ThreadConnectBTdevice once connect successed
    //to start ThreadConnected
    private void startThreadConnected(BluetoothSocket socket) {

        myThreadConnected = new ThreadConnected(socket);
        myThreadConnected.start();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "bluetooth_data Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.kms.kiran_c/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /*
    ThreadConnectBTdevice:
    Background Thread to handle BlueTooth connecting
    */
    private class ThreadConnectBTdevice extends Thread {

        private BluetoothSocket bluetoothSocket = null;
        private final BluetoothDevice bluetoothDevice;


        private ThreadConnectBTdevice(BluetoothDevice device) {
            bluetoothDevice = device;

            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(myUUID);
                textStatus.setText("bluetoothSocket: \n" + bluetoothSocket);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            boolean success = false;
            try {
                bluetoothSocket.connect();
                success = true;
            } catch (IOException e) {
                e.printStackTrace();

                final String eMessage = e.getMessage();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        textStatus.setText("something wrong bluetoothSocket.connect(): \n" + eMessage);
                    }
                });

                try {
                    bluetoothSocket.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            if (success) {
                //connect successful
                final String msgconnected = "connect successful:\n"
                        + "BluetoothSocket: " + bluetoothSocket + "\n"
                        + "BluetoothDevice: " + bluetoothDevice;

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        textStatus.setText(msgconnected);
                        imgslide.setImageResource(R.drawable.sitted);
                        LinearLayout lLayout = (LinearLayout) findViewById(R.id.lc1);
                        LinearLayout lLayout1 = (LinearLayout) findViewById(R.id.lc);
                        lLayout1.setBackgroundColor(Color.parseColor("#a4000000"));
                        lLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));


                        listViewPairedDevice.setVisibility(View.GONE);
                        inputPane.setVisibility(View.VISIBLE);
                    }
                });

                startThreadConnected(bluetoothSocket);
            } else {
                //fail
            }
        }

        public void cancel() {

            Toast.makeText(getApplicationContext(),
                    "close bluetoothSocket",
                    Toast.LENGTH_LONG).show();

            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    /*
    ThreadConnected:
    Background Thread to handle Bluetooth data communication
    after connected
     */
    private class ThreadConnected extends Thread {
        private final BluetoothSocket connectedBluetoothSocket;
        private final InputStream connectedInputStream;
        private final OutputStream connectedOutputStream;

        public ThreadConnected(BluetoothSocket socket) {
            connectedBluetoothSocket = socket;
            InputStream in = null;
            OutputStream out = null;

            try {
                in = socket.getInputStream();
                out = socket.getOutputStream();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            connectedInputStream = in;
            connectedOutputStream = out;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = connectedInputStream.read(buffer);
                    String strReceived = new String(buffer, 0, bytes);
                    final String msgRecived = strReceived;

                    //int a =Integer.parseInt(msgReceived);
                    //if (a<20){
                    //    statusinfo.setText("Sittted");

                    // }


                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            textStatus.setText(msgRecived);
                            String text = textStatus.getText().toString();
                            int n;
                            /*Scanner fromStr=new Scanner(text);
                            while (fromStr.hasNextInt()){
                                infoDis.setText(fromStr.nextInt());
                                infoHr.setText( fromStr.nextInt());



                            }*/
                            // infoDis.setText(java.util.Arrays.toString(text.split(".")));
                            // infoHr.setText(text);
                           if (text.matches("\\d+")) //check if only digits. Could also be text.matches("[0-9]+")
                           {
                               // infoDis.setText(text);

                               /*Scanner fromStr=new Scanner(text);
                                while (fromStr.hasNextInt()){
                                    infoDis.setText(fromStr.nextInt());
                                    infoHr.setText( fromStr.nextInt());


                                }*/
                               m=millis;



                               if (millis > 1) {

                                   n = Integer.parseInt(text);
                                   String start = infoDis.getText().toString();
                                   String stop = infoHr.getText().toString();
                                   SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                   try {
                                       Date date1 = format.parse(start);
                                       Date date2 = format.parse(stop);

                                       //'String millis = ""+diff;
                                       millis2 = date1.getTime() - date2.getTime();

                                       //'String millis = ""+diff;
                                       String hms2 = String.format("%02d:%02d:%02d",
                                               TimeUnit.MILLISECONDS.toHours(millis2),
                                               TimeUnit.MILLISECONDS.toMinutes(millis2) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis2)),
                                               TimeUnit.MILLISECONDS.toSeconds(millis2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis2)));
                                       // System.out.println(hms);



                                       ;
                                       int l = 1;
                                       do {
                                           l = l + 1;
                                           strArr.add(timerValue.getText().toString());
                                           adapter.notifyDataSetChanged();
                                       } while (l < 2);


                                       if (n < 2) {
                                           statusb.setText("sitted");
                                           startTime = SystemClock.uptimeMillis();
                                           // customHandler.postDelayed(updateTimerThread, 0);
                                           DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                                           Calendar cal = Calendar.getInstance();
                                           infoDis.setText(dateFormat.format(cal.getTime()));
                                           imgslide.setImageResource(R.drawable.up1);
                                           timerValue2.setText(hms2);

                                           new Handler().postDelayed(new Runnable() {

                                               @Override
                                               public void run() {

                                                   millis=m+500;


                                                   String strLong = Long.toString(millis);
                                                   TextView us = (TextView) findViewById(R.id.user);
                                                   us.setText(strLong);

                                           String hms = String.format("%02d:%02d:%02d",
                                                   TimeUnit.MILLISECONDS.toHours(millis),
                                                   TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                                                   TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                                           // System.out.println(hms);
                                           timerValue.setText(hms);

                                               }
                                           }, 0);
                                           String h = (timerValue.getText()).toString();
                                           String[] h1 = h.split(":");

                                           int hour = Integer.parseInt(h1[0]);
                                           int minute = Integer.parseInt(h1[1]);
                                           int second = Integer.parseInt(h1[2]);


                                           temp = second + (60 * minute) + (3600 * hour);
                                           millis=temp;




                                           if (temp > 5 && temp < 10) {
                                               states.setImageResource(R.drawable.exe1);
                                           } else if (temp > 10 && temp < 20) {
                                               states.setImageResource(R.drawable.exe2);
                                           } else if (temp > 20 && temp < 30) {
                                               states.setImageResource(R.drawable.exe3);
                                           } else if (temp > 30 && temp < 40) {
                                               states.setImageResource(R.drawable.exe4);
                                           } else if (temp > 40 && temp < 50) {
                                               states.setImageResource(R.drawable.exe5);
                                           } else if (temp > 50 && temp < 60) {
                                               states.setImageResource(R.drawable.exe6);
                                           } else if (temp > 60 && temp < 70) {
                                               states.setImageResource(R.drawable.exe7);
                                           } else if (temp > 70 && temp < 80) {
                                               states.setImageResource(R.drawable.exe8);
                                           } else if (temp > 80 && temp < 90) {
                                               states.setImageResource(R.drawable.exe9);
                                           } else if (temp > 90 && temp < 100) {
                                               states.setImageResource(R.drawable.exe10);
                                           } else if (temp > 100&& temp < 150) {
                                               states.setImageResource(R.drawable.exe11);
                                           } else if (temp > 150 && temp < 200) {
                                               states.setImageResource(R.drawable.exe12);
                                           } else {
                                               states.setImageResource(R.drawable.sitted);
                                           }


                                       } else {
                                           statusb.setText("not sitted");
                                           DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                                           Calendar cal = Calendar.getInstance();
                                           infoHr.setText(dateFormat.format(cal.getTime()));
                                           imgslide.setImageResource(R.drawable.tosit1);
                                           timeSwapBuff += timeInMilliseconds;
                                           customHandler.removeCallbacks(updateTimerThread);


                                       }


                                   } catch (ParseException e) {
                                       e.printStackTrace();
                                   }
                               }
                               else
                               {


                               n = Integer.parseInt(text);
                               String start = infoDis.getText().toString();
                               String stop = infoHr.getText().toString();
                               SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                               try {
                                   Date date1 = format.parse(start);
                                   Date date2 = format.parse(stop);

                                   millis = date1.getTime() - date2.getTime();

                                   //'String millis = ""+diff;
                                   String hms = String.format("%02d:%02d:%02d",
                                           TimeUnit.MILLISECONDS.toHours(millis),
                                           TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                                           TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                                   // System.out.println(hms);
                                   if (statusb.getText().equals("SITTED")) {

                                   }
                                   ;
                                   int l = 1;
                                   do {
                                       l = l + 1;
                                       strArr.add(timerValue.getText().toString());
                                       adapter.notifyDataSetChanged();
                                   } while (l < 2);


                                   if (n < 2) {
                                       statusb.setText("sitted");
                                       startTime = SystemClock.uptimeMillis();
                                       // customHandler.postDelayed(updateTimerThread, 0);
                                       DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                                       Calendar cal = Calendar.getInstance();
                                       infoDis.setText(dateFormat.format(cal.getTime()));
                                       imgslide.setImageResource(R.drawable.up1);
                                       timerValue.setText(hms);
                                       String strLong = Long.toString(millis);
                                       TextView us = (TextView) findViewById(R.id.user);
                                       us.setText(strLong);


                                       if (temp > 5 && temp < 10) {
                                           states.setImageResource(R.drawable.exe1);
                                       } else if (temp > 10 && temp < 20) {
                                           states.setImageResource(R.drawable.exe2);
                                       } else if (temp > 20 && temp < 30) {
                                           states.setImageResource(R.drawable.exe3);
                                       } else if (temp > 30 && temp < 40) {
                                           states.setImageResource(R.drawable.exe4);
                                       } else if (temp > 40 && temp < 50) {
                                           states.setImageResource(R.drawable.exe5);
                                       } else if (temp > 50 && temp < 60) {
                                           states.setImageResource(R.drawable.exe6);
                                       } else if (temp > 60 && temp < 70) {
                                           states.setImageResource(R.drawable.exe7);
                                       } else if (temp > 70 && temp < 80) {
                                           states.setImageResource(R.drawable.exe8);
                                       } else if (temp > 80 && temp < 90) {
                                           states.setImageResource(R.drawable.exe9);
                                       } else if (temp > 90 && temp < 100) {
                                           states.setImageResource(R.drawable.exe10);
                                       } else if (temp > 100&& temp < 150) {
                                           states.setImageResource(R.drawable.exe11);
                                       } else if (temp > 150 && temp < 200) {
                                           states.setImageResource(R.drawable.exe12);
                                       } else {
                                           states.setImageResource(R.drawable.sitted);
                                       }


                                   } else {
                                       statusb.setText("not sitted");
                                       DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                                       Calendar cal = Calendar.getInstance();
                                       infoHr.setText(dateFormat.format(cal.getTime()));
                                       imgslide.setImageResource(R.drawable.tosit1);
                                       timeSwapBuff += timeInMilliseconds;
                                       customHandler.removeCallbacks(updateTimerThread);


                                   }


                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               //timerValue.setText((int) diff);
                           }


                        }

                            /*
                            else
                            {
                               // System.out.println("not a valid number");
                                statusb.setText("not a valid number");
                            }*/


                           }
                    });

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                    final String msgConnectionLost = "Connection lost:\n"
                            + e.getMessage();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            textStatus.setText(msgConnectionLost);
                        }
                    });
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                connectedOutputStream.write(buffer);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public void cancel() {
            try {
              connectedBluetoothSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

           }
        }
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            String localtime = "" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds);
            time.setText(localtime);
            if (mins == 1) {
                stopTimer = true;
                Toast.makeText(bluetooth_data.this, "You guys are awesome", Toast.LENGTH_SHORT).show();
            }
            if (!stopTimer)
                customHandler.postDelayed(this, 0);
        }

    };
    public void OnClickButtonLIstener() {

        statusb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.kms.kiran_c.datatest");
                        intent.putExtra("fname", textStatus.getText().toString());
                        startActivity(intent);
                    }
                }
        );
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
                finish();
            }
        }, 2000);
    }

}
