package com.example.test;

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.AssetManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.amitshekhar.DebugDB;
//import com.amitshekhar.utils.DatabaseHelper;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.IOException;
//import java.util.List;
//
//import static java.sql.DriverManager.println;
//
//public class MainActivity extends AppCompatActivity {
//    //ExampleThread thread;
//
//    public static final String EXTRA_MESSAGE = "com.example.test.MESSAGE";
//    private Context mContext = null;
//    SQLiteDatabase database;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        try {
//            // 1번 isCheckDB 함수 : DB가 있는지 확인
//            boolean bResult = isCheckDB();    // DB가 있는지?
//            Log.d("MiniApp", "DB Check=" + bResult);
//            if (!bResult) {    // DB가 없으면 복사
//                // 2번 copyDB 함수 : DB를 local에서 device로 복사
//                copyDB(this);
//            } else {
//
//            }
//        } catch (Exception e) {
//        }
//
//        Intent intent = new Intent(this, CheckActivity.class);
//        Button button = (Button) findViewById(R.id.btn_url);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                database = openOrCreateDatabase("anti_url.db", MODE_PRIVATE, null);
//                Log.d("db","createDatabase 호출됨.");
//                if(database == null) {
//                    println("createDatabase 호출 실패");
//                }
//                executeQuery();
//                TextView editText = (TextView) findViewById(R.id.text_ur);
//                String message = editText.getText().toString();
//                intent.putExtra(EXTRA_MESSAGE, message);
//
//                startActivity(intent);
//
//            }
//    });
//    }
//
//    public boolean isCheckDB(){
//        String filePath = "/data/data/com.example.test/databases/anti_url.db";
//        File file = new File(filePath);
//        if (file.exists()) {
//            Log.d("db","데이터베이스가 존재함 true" ); // debug
//            return true;
//        }
//        println("데이터베이스가 존재함 false" ); // debug
//        return false;
//    }
//
//    public void copyDB(Context mContext) {
//        Log.d("MiniApp", "copyDB");
//        AssetManager manager = mContext.getAssets();
//        String folderPath = "/data/data/com.example.test/databases";
//        String filePath = "/data/data/com.example.test/databases/anti_url.db";
//        File folder = new File(folderPath);
//        File file = new File(filePath);
//
//        FileOutputStream fos = null;
//        BufferedOutputStream bos = null;
//        try {
//            Log.d("db","db복사시작");
//            InputStream is = manager.open("url.db");
//            BufferedInputStream bis = new BufferedInputStream(is);
//
//            if (folder.exists()) {
//                Log.d("db","폴더가있으면그냥넘어감");
//            } else {
//                Log.d("db","폴더가없어서만들어줌");
//                folder.mkdirs();
//            }
//
//            if (file.exists()) {
//                Log.d("db","파일이있어서삭제후재생성");
//                file.delete();
//                file.createNewFile();
//            }
//            Log.d("db","파일을 만들자");
//            fos = new FileOutputStream(file);
//            bos = new BufferedOutputStream(fos);
//            int read = -1;
//            byte[] buffer = new byte[1024];
//            while ((read = bis.read(buffer, 0, 1024)) != -1) {
//                bos.write(buffer, 0, read);
//            }
//
//            bos.flush();
//
//            bos.close();
//            fos.close();
//            bis.close();
//            is.close();
//
//        } catch (IOException e) {
//            Log.d("db","error나서 하다 못함//");
//            Log.e("ErrorMessage : ", e.getMessage());
//        }
//    }
//
//    public void executeQuery() {
//        Log.d("db", "executeQuery 호출됨.");
//
//        //본인의 columns name and table name
//        Cursor cursor = database.rawQuery("select mid, malurl from mal_url", null);
//        int recordCount = cursor.getCount();
//        Log.d("db", "레코드 개수 : " + recordCount);
//
//        //for (int i = 0; i < recordCount; i++) {
//        //10개 레코드만 출력해보기
//        for (int i = 0; i < 10; i++) {
//            cursor.moveToNext();
//
//            //본인의 데이터 타입이 string 인지 int인지에 맞게
//            String id = cursor.getString(0);
//            String url = cursor.getString(1);
//            //int age = cursor.getInt(3); // int 예시
//            if (url.equals("elamurray.com"))
//                Log.d("db", "일치: " + id);
//
//            Log.d("db", "레코드 #" + i + " : " + id + ", " + url);
//        }
//        cursor.close();
//    }
//
//
//
////    public void sendMessage(View view) {
////        database = openOrCreateDatabase("anti_url.db", MODE_PRIVATE, null);
////        Log.d("db","createDatabase 호출됨.");
////        if(database == null) {
////            println("createDatabase 호출 실패");
////        }
////        executeQuery();
////
////        Intent intent = new Intent(this, CheckActivity.class);
////        EditText editText = (EditText) findViewById(R.id.text_ur);
////        String message = editText.getText().toString();
////
////        intent.putExtra(EXTRA_MESSAGE, message);
////        startActivity(intent);
////    }
//
//    public void search_url() {
//
//
//    }
//}

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

//import com.amitshekhar.DebugDB;
//import com.amitshekhar.utils.DatabaseHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static java.sql.DriverManager.println;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static String search_message = null;
    public static final String EXTRA_MESSAGE = "com.example.test.MESSAGE";
    private Context mContext = null;
    SQLiteDatabase database;

    String result = null;
    //String result = "https://www.naver.com";
    Thread workingthread;
    static String message = null;
    static String finalurl = null;
    char [] alp_start_ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '.'};
    int [] alp_start_num = {-2, 857, 5874, 8511, 11129, 12779, 14779, 15619, 16805, 17786,
            19473, 87853, 141166, 209115, 251820, 291999, 332867, 370629, 405209, 443291,
            461551, 490159, 525500, 597118, 629111, 652273, 708922, 714360, 748777, 838373,
            903099, 919683, 942708, 969953, 979193, 990082, 999998};

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

/*
    public void executeQuery() {
        Log.d("db","executeQuery 호출됨.");

        // 본인의 columns name and table name
        Cursor cursor = database.rawQuery("select sid, safeurl from safe_url", null);
        //safe_url : sid, safeurl / mal_url : mid, malurl
        int recordCount = cursor.getCount();
        Log.d("db","레코드 개수 : " + recordCount);

//        for (int i = 0; i < recordCount; i++) {
        // 10개 레코드만 출력해보기
        for (int i = 0; i < 5; i++) {
            cursor.moveToNext();

            // 본인의 데이터 타입이 string 인지 int인지에 맞게
            String id = cursor.getString(0);
            String url = cursor.getString(1);
//            int age = cursor.getInt(3); // int 예시
//            if(url.equals("elamurray.com"))
//                Log.d("db", "일치: "+id);

            Log.d("db","레코드 #" + i + " : " + id + ", " + url);
        }
        cursor.close();
    }


 */

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
                finalurl = null;
                //if(finalurl != null)
                //    search_url();
                check(message);
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
                search_url();
                check(message);
            }
        });

    }

    public void check(String enter_url) {
        setContentView(R.layout.activity_check);
        final TextView url = (TextView) findViewById(R.id.text_url);
        final TextView messagebox = (TextView) findViewById(R.id.text_infor);
        final Button view = (Button) findViewById(R.id.btn_webview);
        final Button prev = (Button) findViewById(R.id.btn_prev);
        url.setText(enter_url);
        messagebox.setText("url 메시지 파싱 결과안내\n\n" + search_message);

        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { web_view(); }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { start(); }
        });}

    public void web_view() {
        setContentView(R.layout.activity_webview);
        final ImageView iv = (ImageView) findViewById(R.id.iv_web);
        final Button prev2 = (Button) findViewById(R.id.btn_prev2);

        database.close();

        prev2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                check(message);
            }
        });
    }
////
    public void geturl(String m) {
        workingthread = new Thread() {
            public void run() {
                HttpURLConnection con = null;
                con.setInstanceFollowRedirects(false);
                try {
                    con = (HttpURLConnection)new URL(m).openConnection();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("error");
                    con.disconnect();
                }
                //con.setInstanceFollowRedirects(true);
                //con.setRequestProperty("User-Agent","");
                try {
                    if (con.getResponseCode()/100 == 3)
                    {
                        String target = con.getHeaderField("Location");
                        if (target != null) {
                            result = target;
                            Log.d("mes", "result: " + result);
                            con.disconnect();
                            return;
                        }
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("error");
                    con.disconnect();
                }
                result= con.getURL().toString();
                Log.d("mes", "result: " +result);
                con.disconnect();
                return;

//                HttpURLConnection con = null;
//                try {
//                    con = (HttpURLConnection) new URL(m).openConnection();
//                    if (con.getResponseCode() / 100 == 3) {
//                        String target = con.getHeaderField("Location");
//                        if (target != null) {
//
//                            result = target;
//                            Log.d("mes", "result: " + result);
//                            return;
//                        }
//                    }
//                } catch (IOException e) {
//                    Log.d("error", "error!");
//                }
//                result = con.getURL().toString();
//                Log.d("mes", "result: " +result);
//                return;
            }
        };
        workingthread.start();
    }

    public void get_final_url() {
        finalurl = null;
        if(message.contains("https")) {
            geturl(message);
            try {
                workingthread.join(); // sumThread가 종료될때까지 메인 스레드를 정지시킴
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("mes", "result url 출력: " + result);
            finalurl = result;
            //finalurl = geturl(message);
            //search_message = "https, 정상 사이트";
            search_message = "정상, 다음단계로!";
            Log.d("mes", "결과 url:" + finalurl);
        }
        else if(message.contains("http")) {
            Log.d("error", "http라서 악성의심");
            search_message = "http임, 위험 사이트";
            Log.d("error", "finalurl 출력:"+finalurl);
            return;
        }
        else {
            try{ geturl("https://" + message);
                try {
                    workingthread.join(); // sumThread가 종료될때까지 메인 스레드를 정지시킴
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finalurl = result;
                search_message = "정상, 다음단계로!";
            }
                //finalurl = geturl("https://" + message); }
            catch(Exception e) {
                Log.d("error", "접속 불가!");
                search_message = "접속 불가, 위험 사이트";
                return;
            }
        }
        try {
            workingthread.join(); // sumThread가 종료될때까지 메인 스레드를 정지시킴
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(finalurl.contains("www") ) {
            finalurl = finalurl.substring(finalurl.split("\\.")[0].length()+1);
            Log.d("mes", "www들어옴:" + finalurl);
        }
        else { finalurl = finalurl.substring(8); }
        String[] finalurl_spl = finalurl.split("/");
        Log.d("mes", "0번" + finalurl_spl[0]);
        if(finalurl_spl[finalurl_spl.length-1].contains(".") && finalurl_spl.length != 1) {
            Log.d("error", "확장자 파일 인식됨");
            search_message = "확장자 파일, 위험 사이트";
            return;
        }
        finalurl = finalurl_spl[0];
        Log.d("mes", "!!finalurl 출력 : " + finalurl);
    }

    ////

    public void search_url() {
        Cursor cursor = database.rawQuery("select sid, safeurl from safe_url", null);
        //safe_url : sid, safeurl / mal_url : mid, malurl
        int recordCount = cursor.getCount();
        int c = 0;
        int count = alp_start_ch.length;
        int start_count = 0;
        int end_count = recordCount;

        for (c = 0; c < count; c++) {
            if (alp_start_ch[c] == message.charAt(0)) {
                start_count = alp_start_num[c];

                cursor.moveToPosition(start_count);
                Log.d("mes","배열 체크1 " + alp_start_ch[c]);
                Log.d("mes","배열 체크2 " + alp_start_num[c]);
                end_count = alp_start_num[c+1];
                Log.d("mes","start 체크 "+ start_count +", end count 체크 " + end_count);
            }
        }

        //cursor.moveToPosition(990000);
        //cursor.moveToPosition(alp_start_num[count]);
        //Log.d("db","레코드 개수 : " + recordCount);

        try {
            //for (int i = 0; i < recordCount; i++) {
            //for (int i = start_count; i <= end_count; i++) {
            for (int i = start_count; i <= end_count; i++) {
                cursor.moveToNext();
                // 본인의 데이터 타입이 string 인지 int인지에 맞게
                String id = cursor.getString(0);
                String url = cursor.getString(1);

                if(url.equals(message)) {
                    Log.d("db","레코드 #" + i + " : " + id + ", " + url);
                    search_message = "일치합니다! \n레코드 #" + i + " : " + id + ", " + url;
                    break;
                }

                if(i == end_count) {
                    Log.d("db","마지막레코드 #" + i + " 해당 url를 찾지못했습니다!");
                    search_message = "해당 url를 찾지못했습니다ㅠㅠ";
                }
            }
        } catch (IndexOutOfBoundsException e) {
            Log.d("db","찾지못했습니다!");
            search_message = "인덱스범위초과";
        }

        cursor.close();
    }

}