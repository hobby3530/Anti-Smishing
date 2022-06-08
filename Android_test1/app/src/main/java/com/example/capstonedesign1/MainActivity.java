package com.example.capstonedesign1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

//import com.amitshekhar.DebugDB;
//import com.amitshekhar.utils.DatabaseHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
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

    public static final String EXTRA_MESSAGE = "com.example.capstonedesign1.MESSAGE";
    private Context mContext = null;
    SQLiteDatabase database;

    int flag = 0;
    //int lda = message.length();
    static String message = null;
    static String finalurl = null;
    static String search_message = null;
    int minLDA1 = 0;
    int minLDA2 = 0;
    int sameid1 = 0;
    int sameid2 = 0;
    String sameurl1 = null;
    String sameurl2 = null;
    String tmp_url = null;
    String[] s = null;
    char [] alp_start_ch = {'.', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '-'};
    int [] alp_start_safe = {-2, -2, 857, 5874, 8511, 11129, 12779, 14779, 15619, 16805, 17786,
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
        String filePath = "/data/data/com.example.capstonedesign1/databases/anti_url.db";
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
        String folderPath = "/data/data/com.example.capstonedesign1/databases";
        String filePath = "/data/data/com.example.capstonedesign1/databases/anti_url.db";
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
        //warn.setBackgroundColor(Color.parseColor("#000000"));
//        final WebView wv = (WebView) findViewById(R.id.webview);

        url.setText(message);
        //messagebox.setText("url 메시지 파싱 결과안내");
        messagebox.setText("<url 메시지 파싱 결과안내>\n\n" + search_message);

        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //wv.loadUrl("file:///android_asset/naver.html");
                web_view();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { start(); }
        });}

    public void web_view() {
        setContentView(R.layout.activity_webview);
        final ImageView iv = (ImageView) findViewById(R.id.iv_web);
        final Button prev2 = (Button) findViewById(R.id.btn_prev2);
        final WebView wv = (WebView) findViewById(R.id.webview);
        wv.loadUrl("file:///android_asset/naver.html");
        //wv.loadUrl("file:///android_asset/clc.html");
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
            if(finalurl.contains("www")){
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
        if (flag == 0 || flag == -2)
            tmp_url = finalurl;
        int minLDA = finalurl.length();
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

                if(tmp_url.equals(url) && flag != -2) {
                    Log.d("db","정상레코드 #" + (i+2) + " : " + id + ", " + url);
                    search_message = "일치합니다! \n레코드 #" + (i+2) + " : " + id + ", " + url;
                    flag = 0;
                    break;
                }

                if(i == end_count) {
                    flag++;
                    Log.d("db"," flag체크 : " + flag + ", 마지막 정상레코드 #" + i + " 해당 url를 찾지못했습니다!");

                    if (flag == 1 && tmp_url.split("\\.").length > 2) {
                        tmp_url = tmp_url.substring(tmp_url.split("\\.")[0].length()+1);
                        Log.d("mes","message_split : " + tmp_url);
                        search_safe();
                    }
                    else{
                        flag = 0;
                        search_mal();
                    }
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
        String tmp_url = finalurl;
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
//                    search_message = "** !!악성 url로 의심됩니다!! ** \n레코드 #" + (i+2) + " : " + id + ", " + url;
                    search_message = "** !!악성 url로 의심됩니다!! ** \n" + url;
                    break;
                }

                if(i == end_count) {
                    Log.d("db","마지막 악성레코드 #" + i + " 해당 url를 찾지못했습니다!");
                    search_message = "결과가 없습니다";
                    flag = -2;
                    search_LDA();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            Log.d("db","찾지못했습니다!");
            search_message = "인덱스범위초과";
        }

        cursor2.close();
    }

    public int getDistance(String s1, String s2) {
        int longStrLen = s1.length() + 1;
        int shortStrLen = s2.length() + 1; // 긴 단어 만큼 크기가 나올 것이므로, 가장 긴단어 에 맞춰 Cost를 계산
        int[] cost = new int[longStrLen];
        int[] newcost = new int[longStrLen]; // 초기 비용을 가장 긴 배열에 맞춰서 초기화 시킨다.
        for (int i = 0; i < longStrLen; i++) { cost[i] = i; } // 짧은 배열을 한바퀴 돈다.
        for (int j = 1; j < shortStrLen; j++) {
            // 초기 Cost는 1, 2, 3, 4...
            newcost[0] = j; // 긴 배열을 한바퀴 돈다.
            for (int i = 1; i < longStrLen; i++) {
                // 원소가 같으면 0, 아니면 1
                int match = 0;
                if (s1.charAt(i - 1) != s2.charAt(j - 1)) { match = 1; }
                // 대체, 삽입, 삭제의 비용을 계산한다.
                int replace = cost[i - 1] + match;
                int insert = cost[i] + 1;
                int delete = newcost[i - 1] + 1;
                // 가장 작은 값을 비용에 넣는다.
                newcost[i] = Math.min(Math.min(insert, delete), replace);
                //System.out.print(newcost[i] + " ");
            } // 기존 코스트 & 새 코스트 스위칭
            int[] temp = cost;
            cost = newcost;
            newcost = temp;
            //System.out.println();
        }
        // 가장 마지막값 리턴
        return cost[longStrLen - 1];
        //lda = cost[longStrLen - 1];
    }

    public void search_LDA() {
        if (flag == -2)
            tmp_url = finalurl;
        minLDA1 = tmp_url.length();
        Cursor cursor1 = database.rawQuery("select sid, safeurl from safe_url", null);
        //safe_url : sid, safeurl / mal_url : mid, malurl
        int recordCount = cursor1.getCount();
        int c = 0;
        int n = 0;
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
                n++;


//              if (n <= 10) {
                if (tmp_url.length()+5 > url.length() || tmp_url.length()-5 < url.length()) {
                    if (minLDA1 > getDistance(tmp_url, url)) {
                        minLDA1 = getDistance(tmp_url, url);
                        Log.d("mes", "i " + i + ", url: " + url + ", LDA체크 " + minLDA1);
                        sameid1 = i+2;
                        sameurl1 = url;
                    }
                }

                if(i == end_count) {
                    flag++;
                    Log.d("db", " flag체크 : " + flag + ", 마지막 정상레코드 #" + i + ", 횟수: " + n + " 해당 url를 찾지못했습니다!");

                    if (flag == -1 && tmp_url.split("\\.").length > 2) {
                        minLDA2 = minLDA1; sameid2 = sameid1; sameurl2 = sameurl1;
                        tmp_url = tmp_url.substring(tmp_url.split("\\.")[0].length() + 1);
                        Log.d("mes", "LDA_split : " + tmp_url);
                        search_LDA();
                    }
                    else {
                        if (flag == -1) {
                            search_message = "** !!주의하세요!! **\n" + sameurl1 + "의 사칭사이트로 의심됩니다! " + sameid1 + ", " + minLDA1;
                            flag = 0;
                        }
                        else {
                            if (minLDA1 < minLDA2)
                                search_message = "** !!주의하세요!! **\n" + sameurl1 + "의 부가사칭사이트로 의심됩니다! " + sameid1 + ", " + minLDA1;
                            else
                                search_message = "** !!주의하세요!! **\n" + sameurl2 + "의 사칭사이트로 의심됩니다! " + sameid2 + ", " + minLDA2;
                            flag = 0;
                        }
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            flag = 0;
            Log.d("db","찾지못했습니다!");
            search_message = "인덱스범위초과";
        }

        cursor1.close();
    }
}