/*
 * Copyright (C) 2018 by George Vrynios
 * This project was made under the supervision of Udacity
 * in the Android Developer Nanodegree Program
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aiassoft.capstone.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiassoft.capstone.MyApp;
import com.aiassoft.capstone.R;
import com.aiassoft.capstone.model.VideosListItem;
import com.aiassoft.capstone.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gvryn on 01/08/18.
 *
 * {@link VideosListAdapter} exposes a list of videos to a
 * {@link android.support.v7.widget.RecyclerView}
 */
public class VideosListAdapter extends RecyclerView.Adapter<VideosListAdapter.VideosAdapterViewHolder> {

    private static final String LOG_TAG = MyApp.APP_TAG + VideosListAdapter.class.getSimpleName();

    /* This array holds a list of video objects */
    private ArrayList<VideosListItem> mVideosData = new ArrayList<>();

    /**
     * Defining an on-click handler to make it easy for an Activity
     * to interface with the RecyclerView
     */
    private final VideosAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives OnClick messages
     */
    public interface VideosAdapterOnClickHandler {
        void onClick(int videoId);
    }

    /**
     * Creates a VideosAdapter
     *
     * @param clickHandler The on-click handler for this adapter. This single handler
     *                     is called when an item is clicked
     */
    public VideosListAdapter(VideosAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for a video list item
     */
    public class VideosAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        /* This ImageView is used to display the Video's Poster */
        @BindView(R.id.iv_video_poster) ImageView mVideoPoster;

        @BindView(R.id.video_title) TextView mVideoTitle;

        public VideosAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            int selectedVideo = mVideosData.get(adapterPosition).getId();
            mClickHandler.onClick(selectedVideo);
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout.
     * @return A new VideoVideosAdapterViewHolder that holds the View for each list item
     */
    @Override
    public VideosAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_list_video;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new VideosAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the video
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param videosAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                contents of the item at the given position in the data set.
     * @param position                The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(VideosAdapterViewHolder videosAdapterViewHolder, int position) {
        String s = mVideosData.get(position).getPosterPath();
        /*
        Picasso.with(videosAdapterViewHolder.mVideoPoster.getContext())
                .load(NetworkUtils.buildPosterUrl(s))
                .into(videosAdapterViewHolder.mVideoPoster);
        */
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our videos list
     */
    @Override
    public int getItemCount() {
        if (null == mVideosData) return 0;
        return mVideosData.size();
    }

    /**
     * This method is used to set the video on a VideosAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new VideosAdapter to display it.
     *
     * @param videosData The new videos data to be displayed.
     */
    public void setVideosData(List<VideosListItem> videosData) {
        if (videosData == null) return;
        mVideosData.addAll(videosData);
        notifyDataSetChanged();
    }

    /**
     * This method is used when we are resetting data
     */
    public void invalidateData() {
        mVideosData = new ArrayList<VideosListItem>();
        notifyDataSetChanged();
    }

}
