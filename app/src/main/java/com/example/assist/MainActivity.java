package com.example.assist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Camera;
import android.media.AudioManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public void bol(String com) {
        tospeech.speak(com, TextToSpeech.QUEUE_FLUSH, null);
    }

    private final int requestCode = 10;
    AudioManager manager;
    TextView text;
    Button b;
    TextToSpeech tospeech;
    String tresult = "";
    int index = 0;
    int c_index = 0;
    Integer fvalue;
    // Camera camera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.textView);
        b = (Button) (findViewById(R.id.button));
        //for torch

        tospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tospeech.setLanguage(Locale.US);
                }
            }
        });
        manager = (AudioManager) getSystemService(AUDIO_SERVICE);

    }

    public void speak(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "please speak");
        try {
            startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            Toast.makeText(this, "your device not supported", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String cmd = result.get(0).toString();
                    String Text = cmd.toLowerCase();
                    if (Text.equals("hello")) {
                        text.setText("hello nitesh");
                        bol("hello nitesh singh");
                        //tospeech.speak(text.getText().toString(), TextToSpeech.QUEUE_FLUSH,null);
                    } else if (Text.contains("camera")) {
                        bol("wait camera is opening");
                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        startActivity(camera);
                    } else if (Text.contains("selfie")) {
                        Intent selfie = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        selfie.putExtra("android.intent.extras.CAMERA_FACING", 1);
                        startActivity(selfie);

                    } else if (Text.contains("call")) {
                        bol("this command is incomplete to work");
                    } else if (Text.equals("how are you")) {
                        bol("i am fine");
                    } else if (Text.contains("increase volume")) {
                        int current = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        try{


                        if(Text.contains("at")) {
                            String[] value = Text.split("at", 2);
                            String Tvalue = value[1].replaceAll("\\s", "");
                            fvalue = 10 * Integer.valueOf(Tvalue) / 100;

                        } }
                        catch(Exception e)
                        {
                            fvalue= current+3;
                        }



                        //int max=manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

                        String vol = Integer.toString(fvalue);

                        manager.setStreamVolume(
                                AudioManager.STREAM_MUSIC, // Stream type
                                fvalue, // Index
                                AudioManager.FLAG_SHOW_UI);// Flags
                        text.setText(vol);
                        bol("volume increased by" + vol+" points");

                    } else if (Text.contains("decrease volume") || Text.contains("silent")) {
                        int current = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        if(Text.contains("at"))
                        {
                            String[] value=Text.split("at",2);
                            String Tvalue=value[1].replaceAll("\\s","");
                            fvalue=10*Integer.valueOf(Tvalue)/100;

                        }
                        else
                        {
                            fvalue= current-3;
                        }
                        String vol = Integer.toString(fvalue);
                        manager.setStreamVolume(
                                AudioManager.STREAM_MUSIC, // Stream type
                                fvalue, // Index
                                AudioManager.FLAG_SHOW_UI);
                        text.setText(vol);
                        bol("volume decreased by" + vol+" points");
                    } else if (Text.contains("browser")) {
                        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                        startActivity(browser);
                    } else if (Text.contains("send whatsapp message") || Text.contains("send message")) {

                        bol("ok sir");
                        String url = "https://api.whatsapp.com/send?phone=917077626024";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    } else if (Text.contains("open youtube")) {
                        text.setText(Text);
                        bol("youtube is open");

                        Intent youtube = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:My4VK8iI900"));
                        startActivity(youtube);
                    }else if (Text.contains("play video") || Text.contains("on youtube")) {
                        text.setText(Text);
                        bol(Text+" on youtube");

                        Intent youtube = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query="+Text));
                        startActivity(youtube);

                    } else if (Text.contains("send mail") || Text.contains("send email")) {

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/html");
                        intent.putExtra(Intent.EXTRA_TEXT, "hello assist.");
                        startActivity(Intent.createChooser(intent, "this is title"));

                    } else if (Text.contains("set wallpaper") || Text.contains("wallpaper")) {
                        Intent wall = new Intent(Intent.ACTION_SET_WALLPAPER);
                        startActivity(wall);
                    } else if (Text.contains("alarm")) {
                        bol("please set hour and minute value");

                        Intent alarm = new Intent(AlarmClock.ACTION_SET_ALARM);
                        // alarm.putExtra(AlarmClock.EXTRA_HOUR,5);
                        // alarm.putExtra(AlarmClock.EXTRA_MINUTES,48);
                        startActivity(alarm);
                    } else if (Text.contains("facebook")) {
                        Intent youtube = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com"));
                        startActivity(youtube);
                        bol("please select browser");
                    } else if (Text.contains("check battery percentage") || Text.contains("battery percentage")) {
                        BatteryManager bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
                        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                        String level = Integer.toString(batLevel);
                        bol(level + "percentage");
                        if (batLevel < 30) {
                            bol("please connect your charger   because your battery level is very low");
                        }

                        text.setText(level);
                    } else if (Text.contains("play songs") || Text.contains("song")) {
                        bol("playing songs on gaana.com");
                        Intent gaana = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gaana.com/album/ishq-tera-hindi-1"));
                        startActivity(gaana);
                    } else if (Text.contains("listen")) {
                        bol("not working");
                    } else if (Text.contains("encrypt")) {
                        String[] plain_TEXT = {"c", "o", "m", "e", "h", "o", "m", "e", "t", "o", "m", "o", "r", "r", "o", "w"};
                        String table[][] = new String[5][5];
                        int[] random = new int[]{0, 3, 2, 4, 1};

                        for (int n = 0; n < 25; n++) {

                            index = 0;
                            c_index = 0;
                            for (int i = 0; i < 5; i++) {
                                for (int j = 0; j < 5; j++) {
                                    if (index == 16) {
                                        break;
                                    }
                                    table[i][j] = plain_TEXT[index];
                                    index = index + 1;
                                    //System.out.print(table[i][j]);

                                }
                            }
                            for (int m = 0; m < 5; m++) {
                                int cur_index = random[m];
                                for (int k = 0; k < 4; k++) {

                                    try {
                                        if (table[k][cur_index] != null) {
                                            plain_TEXT[c_index] = table[k][cur_index];
                                            c_index = c_index + 1;
                                        }
                                    } catch (Exception e) {
                                        break;

                                    }

                                }
                            }
                        }
                        for (int l = 0; l < plain_TEXT.length; l++) {

                            tresult = tresult+"    "+plain_TEXT[l];
                        }
                        bol("encryption of come  home  tomorrow is after 25 iterations    "+tresult+"       and code is visible on your screen");


                        text.setMovementMethod(new ScrollingMovementMethod());
                        text.setText("String[] plain_TEXT = {\"c\", \"o\", \"m\", \"e\",\"h\",\"o\",\"m\",\"e\",\"t\",\"o\",\"m\",\"o\",\"r\",\"r\",\"o\",\"w\"};\n" +
                                "\t    String table [][]=new String[5][5];\n" +
                                "\t    int[] random= new int[]{0,3,2,4,1};\n" +
                                "\t    String result=\"\";\n" +
                                "\t    for(int n=0;n<2;n++)\n" +
                                "\t    {\n" +
                                "\n" +
                                "\tint index=0;\n" +
                                "\tint c_index=0;\n" +
                                "\tfor(int i=0;i<5;i++)\n" +
                                "\t{\n" +
                                "\t\tfor(int j=0;j<5;j++)\n" +
                                "\t\t{\n" +
                                "\t\t\tif(index==16)\n" +
                                "\t\t\t{\n" +
                                "\t\t\t\tbreak;\n" +
                                "\t\t\t}\n" +
                                "\t\t\ttable[i][j]=plain_TEXT[index];\n" +
                                "\t\t\tindex=index+1;\n" +
                                "\t\t\t//System.out.print(table[i][j]);\n" +
                                "\t\t\t\n" +
                                "\t\t}\n" +
                                "\t}\n" +
                                "\tfor(int m=0;m<5;m++)\n" +
                                "\t{\n" +
                                "\t\tint cur_index=random[m];\n" +
                                "\tfor(int k=0;k<4;k++)\n" +
                                "\t{\n" +
                                "\t\t \n" +
                                "\t\t try\n" +
                                "\t\t {\n" +
                                "\t\t\t if(table[k][cur_index]!=null)\n" +
                                "\t\t\t {\n" +
                                "\t\t plain_TEXT[c_index]=table[k][cur_index];\n" +
                                "\t\t c_index=c_index+1;\n" +
                                "\t\t\t }\n" +
                                "\t\t }\n" +
                                "\t\t catch(Exception e)\n" +
                                "\t\t {\n" +
                                "\t\t\t break;\n" +
                                "\t\t\t \n" +
                                "\t     }\n" +
                                "\n" +
                                "\t}}\n" +
                                "\t    }\n" +
                                "\tfor(int l=0;l<plain_TEXT.length;l++)\n" +
                                "\t{\n" +
                                "     System.out.println(plain_TEXT[l]);\n" +
                                "     //System.out.println(plain_TEXT.length);\n" +
                                "\t result=result+plain_TEXT[l];\n" +
                                "\t}\n" +
                                "\tSystem.out.println(result);\n" +
                                "\t\n" +
                                "\t}"+tresult);


                    }
                    else if(Text.contains("what")|| Text.contains("explain"))
                    {
                       Intent search=new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.co.in/search?sxsrf=ALeKk01iCTXwruj7ia1lVosGpJBiZKPnbw%3A1582732369640&source=hp&ei=UZRWXp6KJaqM4-EPotSucA&q="+Text));
                        startActivity(search);
                    }
                    else if(Text.contains("command") || Text.contains("commands"))
                    {
                        bol("all commands are visible on your screen");
                        text.setText("\"camera\" =for open rear camera\n" +
                                "\"selfie\" = for open front camera\n" +
                                "\"call\" = for phone calls\n" +
                                "\"increase volume\" =for increase volume by 3 points\n" +
                                "\"increase volume at '0-100'\" = for inicrease volume by desired points\n" +
                                "\"decrese volume\"= for decrese volume by 3 points\n" +
                                "\"decrease volume at '0-100'\" = for decrease volume by desired points\n" +
                                "\"browser\" = for opening browser\n" +
                                "\"send mail\" or\"send email\"=for send mail\n" +
                                "\"set wallpaper\" or change wallpaper\"=for changing wallpaper\n" +
                                "\"facebook\"=for opening facebook\n" +
                                "\"play songs\" or \"song\"=playing songs online on gaana.com\n" +
                                "\"any question having \"what\" \"explain\" \"why\" \"how\"=opening answer in browser\n" +
                                "\"open youtube\"=for opening youtube;\n" +
                                "\"set alarm\"=for setting alarm;\n" +
                                "\"send  whatsapp message\"=for sending whatsapp merssage;\n" +
                                "\"battery percentage\"=for checking battery level\n");
                    }
                    else if(Text.contains("add"))
                    {
                        Intent add=new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.co.in/search?sxsrf=ALeKk01iCTXwruj7ia1lVosGpJBiZKPnbw%3A1582732369640&source=hp&ei=UZRWXp6KJaqM4-EPotSucA&q="+Text));
                        startActivity(add);
                        bol("sum is display on your screen");
                        text.setText(Text);

                    }
                    else {
                        bol("i am not programmed for this command");
                        text.setText(Text);
                    }

                    break;
                }


            }
        }


    }
}