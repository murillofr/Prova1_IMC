package com.example.aluno.prova1_imc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aluno.prova1_imc.dominio.Dados;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    double peso = 106;
    double altura = 1.80;
    double imc = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("teste","iniciando...");
        //Retrofit
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.56.1/json/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Log.i("teste","objeto retrofit criado...");
        ApiEndpoint apiService = retrofit.create(ApiEndpoint.class);
        Log.i("teste","chamando api...");
        Call<Dados> call = apiService.obterDados();
        //chamada assíncrona
        call.enqueue(new Callback<Dados>() {
            @Override
            public void onResponse(Call<Dados> call, Response<Dados> response) {
                int statusCode = response.code();
                Dados dados = response.body();
                peso = dados.getPeso();
                altura = dados.getAltura();
                imc = peso/(altura*altura);
                DecimalFormat formato = new DecimalFormat("0.00");
                ((TextView)findViewById(R.id.imc_id)).setText(formato.format(imc)+"");
                ImageView img = (ImageView)findViewById(R.id.imagem);
                if(imc<18.5)
                    img.setImageResource(R.drawable.images1);
                else if(imc>=18.5 && imc<25)
                    img.setImageResource(R.drawable.images2);
                else if(imc>=25 && imc<30)
                    img.setImageResource(R.drawable.images3);
                else
                    img.setImageResource(R.drawable.images4);

            }
            @Override
            public void onFailure(Call<Dados> call, Throwable t) {
                // Log error here since request failed
                Log.i("teste",t.toString());
            }
        });

    }

    public void calcula(View v) {
        peso = Double.parseDouble(((EditText)findViewById(R.id.peso_id)).getText().toString());
        altura = Double.parseDouble(((EditText)findViewById(R.id.altura_id)).getText().toString());
        imc = peso/(altura*altura);
        DecimalFormat formato = new DecimalFormat("0.00");
        ((TextView)findViewById(R.id.imc_id)).setText(formato.format(imc)+"");
        ImageView img = (ImageView)findViewById(R.id.imagem);
        if(imc<18.5)
            img.setImageResource(R.drawable.images1);
        else if(imc>=18.5 && imc<25)
            img.setImageResource(R.drawable.images2);
        else if(imc>=25 && imc<30)
            img.setImageResource(R.drawable.images3);
        else
            img.setImageResource(R.drawable.images4);

    }

}