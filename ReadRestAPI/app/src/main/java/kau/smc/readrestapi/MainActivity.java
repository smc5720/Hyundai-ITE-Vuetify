package kau.smc.readrestapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyAsyncTask mProcessTask = new MyAsyncTask();
        mProcessTask.execute();
    }

    //AsyncTask 생성 - 모든 네트워크 로직을 여기서 작성해 준다.
    public class MyAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        //OkHttp 객체생성
        OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("\t로딩중...");
            //show dialog
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            //파라미터를 더해 주거나 authentication header를 추가할 수 있다.
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=강남구 자곡로 101").newBuilder();
            urlBuilder.addQueryParameter("X-NCP-APIGW-API-KEY-ID","fbjwqr85b8");
            urlBuilder.addQueryParameter("X-NCP-APIGW-API-KEY","QXU2NDyV3x1vyqHeOFaiNAffka71Ybw6ZxAIzjmp");
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            //요청결과를 여기서 처리한다. 화면에 출력하기등...
            Log.d("Result:", result);

            String target_x = ",\"x\":\"";
            String target_y = ",\"y\":\"";

            int idx_x = result.indexOf(target_x);
            int idx_y = result.indexOf(target_y);

            String real_x = result.substring(idx_x+6, idx_x+17);
            String real_y = result.substring(idx_y+6, idx_y+16);

            Log.d("Your coordinate", real_x + ", " + real_y);
        }
    }
}