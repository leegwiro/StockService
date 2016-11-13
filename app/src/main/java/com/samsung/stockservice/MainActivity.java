package com.samsung.stockservice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String strUrl, strCookie, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AsyncTask<Void,Void,Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                strUrl = "http://finance.yahoo.com/webservice/v1/symbols/005930.KS/quote?format=json&view=detail";
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    URL Url = new URL(strUrl);  // URL화 한다
                    HttpURLConnection conn = (HttpURLConnection) Url.openConnection(); // URL을 연결한 객체 생성
                    conn.setRequestMethod("GET"); // get방식 통신
                    //conn.setDoOutput(true);       // 쓰기모드 지정
                    //conn.setDoInput(true);        // 읽기모드 지정
                    conn.setUseCaches(false);     // 캐싱데이터를 받을지 안받을지
                    conn.setDefaultUseCaches(false); // 캐싱데이터 디폴트 값 설정
                    conn.setRequestProperty ("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; MotoE2(4G-LTE) Build/MPI24.65-39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.81 Mobile Safari/537.36");

                    strCookie = conn.getHeaderField("Set-Cookie"); //쿠키데이터 보관

                    InputStream is = conn.getInputStream();        //input스트림 개방

                    StringBuilder builder = new StringBuilder();   //문자열을 담기 위한 객체
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));  //문자열 셋 세팅
                    String line;

                    while ((line = reader.readLine()) != null) {
                        builder.append(line+ "\n");
                    }

                    result = builder.toString();
                }catch(MalformedURLException | ProtocolException exception) {
                    exception.printStackTrace();
                }catch(IOException io){
                    io.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                System.out.println(result);
            }
        }.execute();
    }
}
