package com.example.aman.materialtest.fragments;


//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.aman.materialtest.R;
//import com.example.aman.materialtest.extras.SortListener;
//import com.example.aman.materialtest.logging.L;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link FragmentSearch#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class FragmentSearch extends Fragment implements SortListener {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//
//    public FragmentSearch() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FragmentSearch.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static FragmentSearch newInstance(String param1, String param2) {
//        FragmentSearch fragment = new FragmentSearch();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_search, container, false);
//    }
//
//    @Override
//    public void onSortByName() {
//        L.t(getActivity(), "Sort by name Fragment search");
//    }
//
//    @Override
//    public void onSortByDate() {
//
//    }
//
//    @Override
//    public void onSortByRating() {
//
//    }
//}

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aman.materialtest.R;
import com.example.aman.materialtest.adapters.AdapterBoxOffice;
import com.example.aman.materialtest.extras.Constants;
import com.example.aman.materialtest.extras.MovieSorter;
import com.example.aman.materialtest.extras.SortListener;
import com.example.aman.materialtest.materialtest.MyApplication;
import com.example.aman.materialtest.pojo.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Network.VolleySingleton;

import static com.example.aman.materialtest.extras.Keys.EndpointBoxOffice.KEY_AUDIENCE_SCORE;
import static com.example.aman.materialtest.extras.Keys.EndpointBoxOffice.KEY_ID;
import static com.example.aman.materialtest.extras.Keys.EndpointBoxOffice.KEY_MOVIES;
import static com.example.aman.materialtest.extras.Keys.EndpointBoxOffice.KEY_POSTERS;
import static com.example.aman.materialtest.extras.Keys.EndpointBoxOffice.KEY_RATINGS;
import static com.example.aman.materialtest.extras.Keys.EndpointBoxOffice.KEY_RELEASE_DATES;
import static com.example.aman.materialtest.extras.Keys.EndpointBoxOffice.KEY_SYNOPSIS;
import static com.example.aman.materialtest.extras.Keys.EndpointBoxOffice.KEY_THEATER;
import static com.example.aman.materialtest.extras.Keys.EndpointBoxOffice.KEY_THUMBNAIL;
import static com.example.aman.materialtest.extras.Keys.EndpointBoxOffice.KEY_TITLE;
import static com.example.aman.materialtest.extras.UrlEndpoints.URL_BOX_OFFICE;
import static com.example.aman.materialtest.extras.UrlEndpoints.URL_CHAR_AMEPERSAND;
import static com.example.aman.materialtest.extras.UrlEndpoints.URL_CHAR_QUESTION;
import static com.example.aman.materialtest.extras.UrlEndpoints.URL_PARAM_API_KEY;
import static com.example.aman.materialtest.extras.UrlEndpoints.URL_PARAM_LIMIT;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSearch extends Fragment implements SortListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public  static  final String URL_ROTTEN_TOMATOES_BOX_OFFICE="http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json";
    private static final String STATE_MOVIES ="state_movies";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton mVolleySingleton;
    private MovieSorter mMovieSorter= new MovieSorter();
    private TextView mTextVolleyError;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    //Contruct an ArrayList that contains movie objects and we gonna
    //initialize this arraylist by adding a new movie object for
    //every movie we recover from the json feed.

    private ArrayList<Movie> listMovies = new ArrayList<>();

    //create a variable for formating our day.So a simpledateFormat gonna take
    //a constructor which is going to have a string specyfing the format in which
    // our date gonna appear.Keep MM capital coz for mm it considers as minutes.
    private DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //initalize this adapter inside on createview where we have created our
    //recyclerView
    private AdapterBoxOffice mAdapterBoxOffice;


    //Initalize our recyclerView we have implemented in our layout.Go
    //to oncreate method and initialize it over there.

    private RecyclerView listMovieHits;

    public FragmentSearch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // Now we make a utitlity method to append the apiKey and limit to which we
    //need the data.

    private static String getRequestUrl(int limit){
        return URL_BOX_OFFICE
                +URL_CHAR_QUESTION
                +URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES
                +URL_CHAR_AMEPERSAND
                +URL_PARAM_LIMIT + limit;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // Inside the on create method of this fragment we gonna send
        //our volley json request.1st step initialize the volley singleton
        mVolleySingleton=VolleySingleton.getInstance();
        // initilaize the requestQueue
        mRequestQueue= mVolleySingleton.getRequestQueue();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOVIES,listMovies);
    }

    public void sendJsonRequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                getRequestUrl(30),(String)null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mTextVolleyError.setVisibility(View.GONE);
                listMovies=parseJSONResponse(response);
                mAdapterBoxOffice.setMovies(listMovies);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleVolleyError(error);
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

    private void handleVolleyError(VolleyError error){
        mTextVolleyError.setVisibility(View.VISIBLE);
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            mTextVolleyError.setText(R.string.error_timeout);

        } else if (error instanceof AuthFailureError) {
            mTextVolleyError.setText(R.string.error_auth_failure);
            //TODO
        } else if (error instanceof ServerError) {
            mTextVolleyError.setText(R.string.error_auth_failure);
            //TODO
        } else if (error instanceof NetworkError) {
            mTextVolleyError.setText(R.string.error_network);
            //TODO
        } else if (error instanceof ParseError) {
            mTextVolleyError.setText(R.string.error_parser);
            //TODO
        }
    }
    public ArrayList<Movie> parseJSONResponse(JSONObject response){
        // for making the json parsing bulletproof let's create default values to
        // all the fields we are storing for a given movie. So inside the for loop
        //we gonna meet the default values again and again
        listMovies = new ArrayList<>();
        if(response!=null || response.length()>0) {

            try {
//            StringBuilder data = new StringBuilder();
                JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES);
                // as it is an array. Use the for loop to loop through
                //the different elements of the array
                for (int i = 0; i < arrayMovies.length(); i++) {

                    long id =0;
                    String title = Constants.NA;
                    String releaseDate= Constants.NA;
                    int audence_score = -1;
                    String synopsis=Constants.NA;
                    String urlThumbnail=Constants.NA;
                    JSONObject currentMovie = arrayMovies.getJSONObject(i);
                    // int the next step we gonna check whether each key exist and
                    // its value is not null.We do it by placing if statement around
                    //the value where we extract that key.
                    if(currentMovie.has(KEY_ID) && !currentMovie.isNull(KEY_ID)) {
                        id = currentMovie.getLong(KEY_ID);
                    }
                    if(currentMovie.has(KEY_TITLE) && !currentMovie.isNull(KEY_TITLE)) {
                        title = currentMovie.getString(KEY_TITLE);
                    }
                    JSONObject objectReleaseDates = currentMovie.getJSONObject(KEY_RELEASE_DATES);
                    // Now we check if this object hsa got the key theatre, if yes we extract
                    //the value. and we convert our string releaseDate to our DateObject.and this
                    //can be done coz we have specific format of the date. yyyy-mm-dd
                    if ( objectReleaseDates !=null &&
                            objectReleaseDates.has(KEY_THEATER) &&
                            !objectReleaseDates.isNull(KEY_THEATER)) {
                        releaseDate = objectReleaseDates.getString(KEY_THEATER);
                    }
                    // get the audience score for the current movie, now
                    //the ratings is also an object

                    JSONObject objectRatings = currentMovie.getJSONObject(KEY_RATINGS);
                    // give value of -1 so that the rating is not zero and we can process
                    // it in some different way.
                    if (objectRatings.has(KEY_AUDIENCE_SCORE)
                            && !currentMovie.isNull(KEY_RATINGS)) {
                        if(objectRatings!=null
                                && objectRatings.has(KEY_AUDIENCE_SCORE)
                                && !objectRatings.isNull(KEY_AUDIENCE_SCORE)) {
                            audence_score = objectRatings.getInt(KEY_AUDIENCE_SCORE);
                        }
                    }
                    // get the synopsis
                    if (currentMovie.has(KEY_SYNOPSIS) &&
                            !currentMovie.isNull(KEY_SYNOPSIS)) {
                        synopsis = currentMovie.getString(KEY_SYNOPSIS);
                    }
                    // Last thing we want to extract over here is the Image
                    //posters is the object in the api
                    if(currentMovie.has(KEY_POSTERS)&&
                            !currentMovie.isNull(KEY_POSTERS)) {
                        JSONObject objectPosters = currentMovie.getJSONObject(KEY_POSTERS);
                        urlThumbnail = null;
                        if (objectPosters != null
                                && objectPosters.has(KEY_THUMBNAIL)
                                && !objectPosters.isNull(KEY_THUMBNAIL)) {
                            urlThumbnail = objectPosters.getString(KEY_THUMBNAIL);
                        }
                    }
                    // now one or more of these values can be null,hence
                    //so we need to make sure we consider this while
                    //populating our recyclerView.
//                    data.append(id+"\n");
                    // now in a for loop we can add our new movie object
                    // there are two ways you can initialize this movie object
                    // either by using constructor from movie class or by setters methods
                    // that exist out there.we gonna use the setter methods

                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setTitle(title);
                    // now simply take the date object and call the parse method on
                    //it, pass the normal string that you wanna parse and  construct
                    //that date object.
                    Date date = null;
                    try {
                        date = mDateFormat.parse(releaseDate);
                    }catch (ParseException e){}
                    movie.setReleaseDateTheater(date);
                    movie.setAudienceScore(audence_score);
                    movie.setSynopsis(synopsis);
                    movie.setUrlThumbnail(urlThumbnail);

                    // add this movie object to our arrayList called
                    //listmovie
                    // after validating we just need to make sure when to
                    //add movie in our list.
                    if(id!=-1 && !title.equals(Constants.NA)) {
                        listMovies.add(movie);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listMovies;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_box_office, container, false);
        mTextVolleyError=(TextView) view.findViewById(R.id.textVolleyError);
        listMovieHits=(RecyclerView) view.findViewById(R.id.listMovieHits);
        // also set the layout manager coz without this this the app gonna crash
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapterBoxOffice= new AdapterBoxOffice(getActivity());
        // set that adapter on the recyclerView
        listMovieHits.setAdapter(mAdapterBoxOffice);
        if (savedInstanceState != null) {
            //if this fragment starts after a rotation or configuration change, load the existing movies from a parcelable
            listMovies = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
            mAdapterBoxOffice.setMovies(listMovies);
        }else {
            sendJsonRequest();
        }
        return view;
        // next step is go to the parse json response method where we got
        //the response
    }

    @Override
    public void onSortByName() {
        mMovieSorter.sortMoviesByName(listMovies);
        mAdapterBoxOffice.notifyDataSetChanged();
    }

    @Override
    public void onSortByDate() {
        mMovieSorter.sortMoviesByDate(listMovies);
        mAdapterBoxOffice.notifyDataSetChanged();
    }

    @Override
    public void onSortByRating() {
        mMovieSorter.sortMoviesByRatings(listMovies);
        mAdapterBoxOffice.notifyDataSetChanged();
    }
}
