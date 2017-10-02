package com.example.aluno.prova1_imc;

import com.example.aluno.prova1_imc.dominio.Dados;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiEndpoint {

    @GET("json")
    Call<Dados> obterDados();

}
