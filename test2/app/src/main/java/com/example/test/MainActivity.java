package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.amitshekhar.DebugDB;
import com.amitshekhar.utils.DatabaseHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;

import static java.sql.DriverManager.println;

public class MainActivity extends AppCompatActivity {
    //ExampleThread thread;

    public static final String EXTRA_MESSAGE = "com.example.test.MESSAGE";
    private Context mContext = null;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        String filePath = "/data/data/com.example.test/databases/maketest.db";
        File file = new File(filePath);
        if (file.exists()) {
            Log.d("db","데이터베이스가 존재함 true" ); // debug
            return true;
        }
        println("데이터베이스가 존재함 false" ); // debug
        return false;
    }

    public void copyDB(Context mContext) {
        Log.d("MiniApp", "copyDB");
        AssetManager manager = mContext.getAssets();
        String folderPath = "/data/data/com.example.test/databases";
        String filePath = "/data/data/com.example.test/databases/maketest.db";
        File folder = new File(folderPath);
        File file = new File(filePath);

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            Log.d("db","db복사시작");
            InputStream is = manager.open("ttest.db");
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

    public void executeQuery() {
        Log.d("db","executeQuery 호출됨.");

        // 본인의 columns name and table name
        Cursor cursor = database.rawQuery("select uid, malurl from ttest", null);
        int recordCount = cursor.getCount();
        Log.d("db","레코드 개수 : " + recordCount);

//        for (int i = 0; i < recordCount; i++) {
        // 10개 레코드만 출력해보기
        for (int i = 0; i < 100; i++) {
            cursor.moveToNext();

            // 본인의 데이터 타입이 string 인지 int인지에 맞게
            String id = cursor.getString(0);
            String url = cursor.getString(1);
//            int age = cursor.getInt(3); // int 예시
            if(url.equals("elamurray.com"))
                Log.d("db", "일치: "+id);

            //Log.d("db","레코드 #" + i + " : " + id + ", " + url);
        }
        cursor.close();
    }


    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = editText.getText().toString();

        database = openOrCreateDatabase("maketest.db", MODE_PRIVATE, null);
        Log.d("db","createDatabase 호출됨.");
        if(database == null) {
            println("createDatabase 호출 실패");
        }
        executeQuery();

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}