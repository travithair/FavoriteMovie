package com.tugasmobile.searchmovieapplication.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tugasmobile.searchmovieapplication.BuildConfig;
import com.tugasmobile.searchmovieapplication.ItemMovieModel;
import com.tugasmobile.searchmovieapplication.R;
import com.tugasmobile.searchmovieapplication.adapter.MovieAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private EditText textSearch;
    private Button search;
    private RecyclerView recyclerView;
    private ArrayList<ItemMovieModel> movieList;
    private MovieAdapter movieAdapter;

    private static String URL = "";

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        textSearch = view.findViewById(R.id.etTextSearch);
        search = view.findViewById(R.id.btSearch);
        recyclerView = view.findViewById(R.id.rv_list_movie);
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(getActivity(), movieList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == search) {
                    String check = textSearch.getText().toString().trim();
                    URL = "https://api.themoviedb.org/3/search/movie?api_key=" + BuildConfig.TMDB_API_KEY + "&language=en-US&query=" + check;
                    if (check.isEmpty()) {
                        Toast.makeText(getContext(), "Harap memberikan input", Toast.LENGTH_SHORT).show();
                    } else {
                        fetchMovieItems();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("film", new ArrayList<>(movieAdapter.getMovieModels()));
    }

    private void fetchMovieItems() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray aray = response.getJSONArray("results");

                            for (int i = 0; i < aray.length(); i++) {
                                JSONObject object = aray.getJSONObject(i);

                                ItemMovieModel model = new ItemMovieModel();

                                model.setId(object.getInt("id"));
                                model.setJudul(object.getString("title"));
                                model.setDeskripsi(object.getString("overview"));
                                model.setImageMovie(object.getString("poster_path"));
                                model.setBackground(object.getString("backdrop_path"));
                                model.setTanggalRilis(object.getString("release_date"));
                                model.setPopularitas(object.getLong("popularity"));
                                model.setRating(object.getDouble("vote_average"));
                                movieList.add(model);
                            }
                            movieAdapter = new MovieAdapter(getContext(), movieList);
                            recyclerView.setAdapter(movieAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        movieAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            ArrayList<ItemMovieModel> movies;
            movies = savedInstanceState.getParcelableArrayList("film");
            movieAdapter.setMovieModels(movies);
            recyclerView.setAdapter(movieAdapter);
        }
    }

}
