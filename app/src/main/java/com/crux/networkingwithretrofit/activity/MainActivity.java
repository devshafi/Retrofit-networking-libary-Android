package com.crux.networkingwithretrofit.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.crux.networkingwithretrofit.R;
import com.crux.networkingwithretrofit.adapter.MoviesAdapter;
import com.crux.networkingwithretrofit.model.Movie;
import com.crux.networkingwithretrofit.model.MoviesResponse;
import com.crux.networkingwithretrofit.rest.ApiClient;
import com.crux.networkingwithretrofit.rest.ApiInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.movieRV)
    RecyclerView movieRV;


    private static final String TAG = "MainActivity";
    // api key for the movie_db database access
    private static final String API_KEY = "ed496d9e8fac51711118b50441ff41fe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // binding this activity with butter knife data binding library
        ButterKnife.bind(this);

        // setting layout of recycler view
        movieRV.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);

        Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                List<Movie> movies = response.body().getResults();
                Log.d(TAG, "onResponse: "+movies.size());

                // setting adapter to recycler view
                movieRV.setAdapter(new MoviesAdapter(movies,R.layout.list_item_movie,getApplicationContext()));

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.toString());
            }
        });

    }
}
