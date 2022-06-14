package com.example.test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static java.sql.DriverManager.println;

import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MainActivity extends AppCompatActivity {

    static String search_message = null;
    public static final String EXTRA_MESSAGE = "com.example.test.MESSAGE";
    private Context mContext = null;
    SQLiteDatabase database;


    String result = null;
    //String result = "https://www.naver.com";
    Thread workingthread;
    HttpURLConnection conn = null;
    static String message = null;
    static String finalurl = null;
    String tmp_url = null;
    int flag = 0;
    char [] alp_start_ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '.'};
    int [] alp_start_safe = {-2, 857, 5874, 8511, 11129, 12779, 14779, 15619, 16805, 17786,
            19473, 87853, 141166, 209115, 251820, 291999, 332867, 370629, 405209, 443291,
            461551, 490159, 525500, 597118, 629111, 652273, 708922, 714360, 748777, 838373,
            903099, 919683, 942708, 969953, 979193, 990082, 999998};
    int [] alp_start_mal = {-2, 14, 67, 1332, 1629, 1789, 2007, 2153, 2258, 2329, 2451,
            2576, 4558, 5805, 7151, 8289, 9059, 9797, 10457, 11069, 11700,
            12009, 12434, 13082, 14605, 15133, 15534, 16511, 16597, 17327, 19088,
            20017, 20285, 20668, 21323, 21456, 21605, 21753};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.INTERNET"}, 0);

        start();

        try {
            // 1번 isCheckDB 함수 : DB가 있는지 확인
            boolean bResult = isCheckDB();    // DB가 있는지?
            Log.d("MiniApp", "DB Check=" + bResult);
            if (!bResult) {    // DB가 없으면 복사
                // 2번 copyDB 함수 : DB를 local에서 device로 복사
                copyDB(this);
            } else {

            }
        } catch (Exception e) {
        }
    }

    public boolean isCheckDB(){
        String filePath = "/data/data/com.example.test/databases/anti_url.db";
        File file = new File(filePath);
        if (file.exists()) {
            Log.d("db","데이터베이스가 존재함 true" ); // debug
            return true;
        }
        println("데이터베이스가 없음 false" ); // debug
        return false;
    }

    public void copyDB(Context mContext) {
        Log.d("MiniApp", "copyDB");
        AssetManager manager = mContext.getAssets();
        String folderPath = "/data/data/com.example.test/databases";
        String filePath = "/data/data/com.example.test/databases/anti_url.db";
        File folder = new File(folderPath);
        File file = new File(filePath);

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            Log.d("db","db복사시작");
            InputStream is = manager.open("url.db");
            BufferedInputStream bis = new BufferedInputStream(is);

            if (folder.exists()) {
                Log.d("db","폴더가있으면그냥넘어감");
            } else {
                Log.d("db","폴더가없어서만들어줌");
                folder.mkdirs();
            }

            if (file.exists()) {
                Log.d("db","파일이있어서삭제후재생성");
                file.delete();
                file.createNewFile();
            }
            Log.d("db","파일을 만들자");
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            int read = -1;
            byte[] buffer = new byte[1024];
            while ((read = bis.read(buffer, 0, 1024)) != -1) {
                bos.write(buffer, 0, read);
            }

            bos.flush();

            bos.close();
            fos.close();
            bis.close();
            is.close();

        } catch (IOException e) {
            Log.d("db","error나서 하다 못함//");
            Log.e("ErrorMessage : ", e.getMessage());
        }
    }

    void start() {
        setContentView(R.layout.activity_main);
        final TextView editText = (TextView) findViewById(R.id.text_ur);
        //final EditText editText = (EditText) findViewById(R.id.text_ur);
        final Button check1 = (Button) findViewById(R.id.btn_url);
        final TextView messagebox = (TextView) findViewById(R.id.text_message);
        final Button check2 = (Button) findViewById(R.id.btn_message);

        check1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                database = openOrCreateDatabase("anti_url.db", MODE_PRIVATE, null);
                Log.d("db","createDatabase 호출됨.");
                //executeQuery();
                message = editText.getText().toString();
                Log.d("mes","message : " + message);
                Log.d("mes","message[0] : " + message.charAt(0));
                get_final_url();
                if(finalurl != null)
                    search_safe();
                check();
            }
        });

        check2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                database = openOrCreateDatabase("anti_url.db", MODE_PRIVATE, null);
                Log.d("db","createDatabase 호출됨.");
                //executeQuery();
                message = editText.getText().toString();
                Log.d("mes","message : " + message);
                Log.d("mes","message[0] : " + message.charAt(0));
                get_final_url();
                if(finalurl != null)
                    search_safe();
                check();
            }
        });

    }

    public void check() {
        setContentView(R.layout.activity_check);
        final TextView url = (TextView) findViewById(R.id.text_url);
        final TextView messagebox = (TextView) findViewById(R.id.text_infor);
        final Button view = (Button) findViewById(R.id.btn_webview);
        final Button prev = (Button) findViewById(R.id.btn_prev);
        url.setText(message);
        messagebox.setText("url 메시지 파싱 결과안내\n\n" + search_message);

        view.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            public void onClick(View v) {
                //AsyncTask thr = new RealTask().execute();
                new RealTask().execute();
                //web_view();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { start(); }
        });}

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void web_view() {
        ////
        setContentView(R.layout.activity_webview);

        //final ImageView iv = (ImageView) findViewById(R.id.iv_web);
        final Button prev2 = (Button) findViewById(R.id.btn_prev2);
        final WebView ex = (WebView) findViewById(R.id.testWeb);

        ex.getSettings().setUseWideViewPort(true);
        ex.getSettings().setLoadWithOverviewMode(true);

        ex.loadUrl("file:///" + getExternalFilesDir(null) + "/htmlfile/testhtml.html");
        Log.d("html", ex.getUrl());
        //ex.loadUrl(getExternalFilesDir(null) + "/htmlfile/testhtml.html");

        database.close();

        prev2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                check();
            }
        });
    }

    public void get_final_url() {
        finalurl = null;

        if(message.contains("https://")) {
            finalurl = message.split("://")[1].split("/")[0];
            String[] exefile = message.split("://")[1].split("/");

            if(exefile.length != 1 && exefile[exefile.length-1].contains(".")) {
                finalurl = null;
                Log.d("error", "확장자 파일 인식됨");
                search_message = "확장자 파일, 위험 사이트";
                return;
            }
            else if(finalurl.contains("www")){
                finalurl = finalurl.substring(4);
            }
            Log.d("mes", "정상사이트: " + finalurl);
        }
        else if(message.contains("http://")) {
            finalurl = null;
            search_message = "http이므로 악성사이트";
            Log.d("mes", "http이므로 악성으로 판단");
        }
        else {
            finalurl = null;
            search_message = "프로토콜을 입력해주세요.";
            Log.d("mes", "프로토콜 입력이 필요함");
        }
    }

    public void search_safe() {
        if(flag == 0)
            tmp_url = finalurl;

        else if (flag == 1 && tmp_url.split("\\.").length > 2) {
            tmp_url = tmp_url.substring(tmp_url.split("\\.")[0].length()+1);
            Log.d("mes","message_split : " + tmp_url);
        }

        else {
            flag = 0;
            return;
            //search_mal();
        }

        Cursor cursor1 = database.rawQuery("select sid, safeurl from safe_url", null);
        //safe_url : sid, safeurl / mal_url : mid, malurl
        int recordCount = cursor1.getCount();
        int c = 0;
        int count = alp_start_ch.length;
        int start_count = 0;
        int end_count = recordCount;

        for (c = 0; c < count; c++) {
            if (alp_start_ch[c] == tmp_url.charAt(0)) {
                start_count = alp_start_safe[c];
                cursor1.moveToPosition(start_count);
                Log.d("mes","배열 체크1 " + alp_start_ch[c]);
                Log.d("mes","배열 체크2 " + alp_start_safe[c]);
                end_count = alp_start_safe[c+1];
                Log.d("mes","start 체크 "+ start_count +", end count 체크 " + end_count);
            }
        }
        try {
            for (int i = start_count; i <= end_count; i++) {
                cursor1.moveToNext();
                String id = cursor1.getString(0);
                String url = cursor1.getString(1);

                if(tmp_url.equals(url)) {
                    Log.d("db","정상레코드 #" + (i+2) + " : " + id + ", " + url);
                    search_message = "일치합니다! \n레코드 #" + (i+2) + " : " + id + ", " + url;
                    flag = 0;
                    break;
                }
                if(i == end_count) {
                    flag++;
                    Log.d("db"," flag체크 : " + flag + ", 마지막 정상레코드 #" + i + " 해당 url를 찾지못했습니다!");
                    search_safe();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            flag = 0;
            Log.d("db","찾지못했습니다!");
            search_message = "인덱스범위초과";
        }


        cursor1.close();
    }

    public void search_mal() {
        tmp_url = finalurl;
        Cursor cursor2 = database.rawQuery("select mid, malurl from mal_url", null);
        //safe_url : sid, safeurl / mal_url : mid, malurl
        int recordCount = cursor2.getCount();
        int c = 0;
        int count = alp_start_ch.length;
        int start_count = 0;
        int end_count = recordCount;

        for (c = 0; c < count; c++) {
            if (alp_start_ch[c] == tmp_url.charAt(0)) {
                start_count = alp_start_mal[c];

                cursor2.moveToPosition(start_count);
                Log.d("mes","배열 체크1 " + alp_start_ch[c]);
                Log.d("mes","배열 체크2 " + alp_start_mal[c]);
                end_count = alp_start_mal[c+1];
                Log.d("mes","start 체크 "+ start_count +", end count 체크 " + end_count);
            }
        }

        try {
            for (int i = start_count; i <= end_count; i++) {
                cursor2.moveToNext();
                // 본인의 데이터 타입이 string 인지 int인지에 맞게
                String id = cursor2.getString(0);
                String url = cursor2.getString(1);

                if(tmp_url.equals("url")) {
                    search_message = "올바른 url를 입력해주세요!!";
                    break;
                }

                if(url.equals(tmp_url)) {
                    Log.d("db","악성레코드 #" + (i+2) + " : " + id + ", " + url);
                    search_message = " 악성 url로 의심됩니다  \n레코드 #" + (i+2) + " : " + id + ", " + url;
                    break;
                }

                if(i == end_count) {
                    Log.d("db","마지막 악성레코드 #" + i + " 해당 url를 찾지못했습니다!");
                    search_message = "결과가 없습니다";
                    return;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            Log.d("db","찾지못했습니다!");
            search_message = "인덱스범위초과";
        }

        cursor2.close();
    }


    class RealTask extends AsyncTask<Void,Void,String> {
            @Override
            protected String doInBackground(Void... voids) {
                StringBuilder sb=new StringBuilder();
                BufferedWriter bw = null;
                //실시간 검색어를 가져오기위한 String객체(String 과는 차이가 있음)
                try {
                    Document doc= Jsoup.connect(message).get();
                    File testhtml = new File(getExternalFilesDir(null) + "/htmlfile/testhtml.html");
                    bw = new BufferedWriter(new FileWriter(testhtml, false));
                    Log.d("error", "파일 쓰기 준비 완료");
                    String gethtml = doc.toString();
                    Log.d("error", "파일 문자열 변환 완료");
                    if(gethtml.contains("document.location.href")) {
                        gethtml = gethtml.replaceAll("document.location.href", " ");
                    }
                    bw.write(gethtml);
                    Log.d("html", doc.toString());
                    //sb.append(doc.toString());
                } catch (IOException e) {
                    try {
                        File testhtml = new File(getExternalFilesDir(null) + "/htmlfile/testhtml.html");
                        bw = new BufferedWriter(new FileWriter(testhtml, false));
                        bw.write("<html><head></head><body><h1>302 FOUND<hr><h2>해당 사이트는 없는 사이트입니다.</body>");
                    }
                    catch(Exception e2) {}
                }
                try {
                    bw.close();
                }
                catch(Exception e) {}
                return sb.toString();
            }

            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                web_view();
            }
        }
    }
