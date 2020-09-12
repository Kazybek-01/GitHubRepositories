package com.example.githubrepos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button request;
    RecyclerView recyclerView;
    EditText username;
    GitHubApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        request = (Button) findViewById(R.id.request);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        username = (EditText) findViewById(R.id.username);

        //JSON идет с базы данных, его надо обернуть в OBJECT -> а для этого мы используем библиотеку GSON
        //обращение через Retrofit

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/users/")
                .addConverterFactory(GsonConverterFactory.create()) //конвертер, чтобы ретрофит переманила объекты напрямую
                .build();

        api = retrofit.create(GitHubApi.class); //создаем интерфейс, связка хвоста и базового URL
        //этот api позволяет вызвать метод getRepos()

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                makeRequest();
            }
        });
    }
    private void makeRequest(){
        if(!TextUtils.isEmpty(username.getText())){
            Call<List<Repo>> call = api.getRepos(username.getText().toString());
            call.enqueue(new Callback<List<Repo>>() {  //идет http запрос (new обратный запрос, в котором 2 метода)
                @Override
                //при получении объекта
                public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                    progressBar.setVisibility(View.GONE);
                    if(response.isSuccessful()){
                        List<Repo> repoList = response.body(); //сопоставляет все данные
                        RepoAdapter adapter = new RepoAdapter(MainActivity.this,repoList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.setAdapter(adapter);
                    }
                }
                //при ошибке
                @Override
                public void onFailure(Call<List<Repo>> call, Throwable t) {
                }
            });
        }
    }
}
