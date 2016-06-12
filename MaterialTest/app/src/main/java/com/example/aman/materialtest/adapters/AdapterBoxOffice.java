package com.example.aman.materialtest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.aman.materialtest.R;
import com.example.aman.materialtest.anim.AnimationUtils;
import com.example.aman.materialtest.extras.Constants;
import com.example.aman.materialtest.pojo.Movie;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Network.VolleySingleton;

/**
 * Created by aman on 6/6/16.
 */
public class AdapterBoxOffice extends RecyclerView.Adapter<AdapterBoxOffice.ViewHolderBoxOffice> {
    // now make a constructor for this adapter and we are going to neeed a
    // layout inflate by the help of which we are going to inflate this layout in code.

    public LayoutInflater mLayoutInflater;
    //we create an arraylist which can contain movie objects in itself.
    private ArrayList<Movie> mListMovies = new ArrayList<>();
    private VolleySingleton mVolleySingleton;
    private int previousPosition=0;
    private ImageLoader mImageLoader;
    private DateFormat mDateFomater = new SimpleDateFormat("yyyy-MM-dd");
    public AdapterBoxOffice(Context context) {
        // intialize the inflator in constructor
        mLayoutInflater= LayoutInflater.from(context);
        mVolleySingleton=VolleySingleton.getInstance();
        mImageLoader=mVolleySingleton.getImageLoader();
    }
    //we create a setter method over here to set our movie list
    //where we take argument of type arrayList that stores movie objects
    public void setMovies(ArrayList<Movie> listMovies) {
        this.mListMovies = listMovies;
        //update the adapter to reflect the new set of movies
        notifyDataSetChanged();
    }
    // nOw go to the createview Holder method and create an object of the
    //viewHolderBoxOffice that we have created
    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        // it needs an argument where we need to pass a view which we just inflated
        // with the help of our layoutInflator.
        View view=mLayoutInflater.inflate(R.layout.custom_movie_box_office,parent,false);
        ViewHolderBoxOffice viewHolderBoxOffice = new ViewHolderBoxOffice(view);
        return viewHolderBoxOffice;
        // next step is to work with bindviewHolder
    }

    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
//we need access to a data structure inside the onBindViewHolder on that data
        //structure if we supply the position, it gonna return us the movie
        //at current position within the list or the recyclerView and we gonna
        // use the array list we created in Fragement class.

        //Now take the viewHolder object and set data on its individual items
        //get the current movie and check whether somthing is null or not.
        // set the appropriate values or set the default values.
        Movie currentMovie = mListMovies.get(position);

        holder.movieTitle.setText(currentMovie.getTitle());
        Date movieReleaseDate = currentMovie.getReleaseDateTheater();
        if(movieReleaseDate !=null){
            String formattedDate=mDateFomater.format(movieReleaseDate);
            holder.movieReleaseDate.setText(formattedDate);
        }else {
            holder.movieReleaseDate.setText(Constants.NA);
        }
        int audienceScore = currentMovie.getAudienceScore();
        if(audienceScore==-1){
            // we plan to fade out the rating bar and display 0
            // rating on it.
            holder.movieAudienceScore.setRating(0.0F);
            holder.movieAudienceScore.setAlpha(0.5F);
        }else {
            holder.movieAudienceScore.setRating(audienceScore / 20.0F);
            holder.movieAudienceScore.setAlpha(1.0F);
        }

        if (position > previousPosition){
           AnimationUtils.animate(holder, true);
        }
        else {
            AnimationUtils.animate(holder, false);
        }
        previousPosition=position;


        String urlThumbNail=currentMovie.getUrlThumbnail();
        //Now we gonna make sure this is not null
        loadImages(urlThumbNail,holder);

    }
    private void loadImages(String urlThumbnail, final ViewHolderBoxOffice holder){
        if(!urlThumbnail.equals(Constants.NA)){
            //Load the image
            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    // incase of response we gonna set the imageView with that particular
                    //image.

                    holder.movieThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    // incase of error we gonna set a default image indicating
                    // the imageLoader was not able to load anything.

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListMovies.size();
        // Last step after here is to go back to the fragment_box_office
        //and set the data for the adapter at the right moment.
    }
    // create a viewHolder

    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder{
        //inside this class you are required to make the objects of imageView,
        //textView etc. that we are using inside our custom layout.So once the
        //layouts are created over here we gonna go to the onBindViewHolder method
        //for the given position inside the recyclerView we gonna take the data from
        //from the data structure an arrayList or a list and then fill the data
        //inside that view.
        private ImageView movieThumbnail;
        private TextView movieTitle;
        private TextView movieReleaseDate;
        private RatingBar movieAudienceScore;
        public ViewHolderBoxOffice(View itemView) {
            super(itemView);
            movieThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            movieAudienceScore = (RatingBar) itemView.findViewById(R.id.movieAudienceScore);
            // After this comes most important part that is how to store or get the data in
            // this AdpaterBoxOffice
        }
    }
}
