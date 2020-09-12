package com.example.githubrepos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

//Интерфейс для хвостов
public interface GitHubApi {

    @GET("{username}/repos")
    Call<List<Repo>> getRepos (@Path("username") String username); //Path - дорожка

    //при вызове этого метода мы будем передавать логин нашего пользователя

    //Query

}
