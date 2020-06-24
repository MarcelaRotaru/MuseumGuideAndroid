package com.example.museumguideandroid;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import static com.example.museumguideandroid.Globals.IPAndPortAPI;
import static com.example.museumguideandroid.Globals.IPAndPortWeb;

public class ResponseActivity extends AppCompatActivity implements ReceiverResponseListener{
    public static String  URLTelefon = IPAndPortAPI+"/api/pictures/";
    Picture picture;
    private String pictureId;
    private String url=IPAndPortWeb+"/Painting/Details/";
    Button urlButton;
    TextView tvName;
    TextView tvDesc;
    boolean valid = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        initViews();
        validateScannedQR();
    }

    private void validateScannedQR() {

        URLTelefon = IPAndPortAPI+"/api/pictures/";
        pictureId = getIntent().getStringExtra("PictureID");
        String[] result = pictureId.split("-");
        System.out.println("Length:" + result.length);

        if(result.length > 3) {
            URLTelefon += pictureId;
            requestToAPI();
        }
        else {
                setInvalidMessage();
        }
    }

    private void setInvalidMessage() {
        tvDesc.setText("Invalid QR Code");
        valid = false;
        urlButton.setText("Back");

    }

    private void initViews() {
        tvName = (TextView) findViewById(R.id.tv_name);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        urlButton = (Button)findViewById(R.id.btnUrl);

        urlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid)
                {
                    Intent intent = new Intent(getBaseContext(), WebActivity.class);
                    intent.putExtra("URL", url);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void requestToAPI() {
        HttpHandler okHttpHandler = new HttpHandler(URLTelefon);
        okHttpHandler.setListener(this);
        okHttpHandler.processRequest();
    }


    @Override
    public void onReceived(String response) {
        picture = parseResponse(response);
        if(picture.author != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvDesc.setText(picture.getTitle());
                    tvName.setText(" Access the link to see more details");
                    url = url + pictureId;
                    urlButton.setText(url);
                }
            });
        }
        else
            setInvalidMessage();
    }
    private Picture parseResponse(String response){
        try {
            JSONObject obj = new JSONObject(response);
                 picture = new Picture(
                         IPAndPortWeb+"/Painting/Details/"+obj.getString("id"),
                          obj.getString("title"),
                          obj.getString("description"),
                          obj.getString("authorId"));
            }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
        return picture;
    }
    



}
