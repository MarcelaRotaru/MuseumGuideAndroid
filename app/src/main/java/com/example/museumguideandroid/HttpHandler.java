package com.example.museumguideandroid;

import android.util.Log;
import java.io.IOException;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHandler {
    ReceiverResponseListener listener;
    String url;

    public HttpHandler(String url){
        this.url = url;
    }

    public void setListener(ReceiverResponseListener listener){
        this.listener = listener;
    }

    public void processRequest(){
        try{

            OkHttpClient client = new OkHttpClient.Builder()
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e);
                    call.cancel();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String myResponse = response.body().string();
                    listener.onReceived(myResponse);
                }
            });

        }catch(Exception e){
            System.out.println(e);
            Log.e("Err", Objects.requireNonNull(e.getMessage()));
        }
    }

}
