package com.dipangestu.uascrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.dipangestu.uascrud.model.GetUser;
import com.dipangestu.uascrud.model.User;
import com.dipangestu.uascrud.services.ApiClient;
import com.dipangestu.uascrud.services.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private UsersAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("loading");
        progressDialog.show();

        ApiInterface service = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<GetUser> call = service.getUsersList();

        call.enqueue(new Callback<GetUser>() {
            @Override
            public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                progressDialog.dismiss();
                List<User> userList = response.body().getData();
                generateDataList(userList);
            }

            @Override
            public void onFailure(Call<GetUser> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }


    private void generateDataList(List<User> userList){
        recyclerView = findViewById(R.id.CsRecyclerView);
        adapter      = new UsersAdapter(this, userList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}

