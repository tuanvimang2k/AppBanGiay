package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.MessAdapter;
import com.example.myapplication.model.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class chat_box extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView welcomeTV;
    EditText messageED;
    ImageButton sendButton;

    List<Message> messageList;

    MessAdapter messAdapter;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        messageList = new ArrayList<>();


        recyclerView = findViewById(R.id.recycle_chatbox);
        welcomeTV = findViewById(R.id.welcome_text);
        messageED = findViewById(R.id.mesage_edit_test);
        sendButton = findViewById(R.id.send_btn);

        //setup adapter
        messAdapter = new MessAdapter(messageList);
        recyclerView.setAdapter(messAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        sendButton.setOnClickListener((v) ->{
            String question = messageED.getText().toString().trim();
            addToChat(question,Message.SENT_BY_ME);
            messageED.setText("");
            callAPI(question);
            welcomeTV.setVisibility(View.GONE);
        });
    }

    void addToChat(String message,String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message,sentBy));
                messAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messAdapter.getItemCount());
            }
        });


    }

    void addResponse(String response){
        messageList.remove(messageList.size()-1);

        addToChat(response,Message.SENT_BY_BOT);
    }


    void callAPI(String question){
        //ok http
        messageList.add(new Message("Đang trả lời....",Message.SENT_BY_BOT));
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model","text-davinci-003");
            jsonBody.put("prompt",question);
            jsonBody.put("max_tokens",4000);
            jsonBody.put("temperature",0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Có lỗi "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result =jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }else{
                    addResponse("Có lỗi"+response.body().toString());
                }
            }
        });

    }

}