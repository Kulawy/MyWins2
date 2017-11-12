package com.theandroiddev.mywins.successslider;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.theandroiddev.mywins.R;
import com.theandroiddev.mywins.data.models.Success;
import com.theandroiddev.mywins.data.models.SuccessImage;
import com.theandroiddev.mywins.data.repositories.DatabaseSuccessesRepository;
import com.theandroiddev.mywins.utils.DrawableSelector;

import java.util.ArrayList;

/**
 * Created by jakub on 27.10.17.
 */

public class SuccessSliderFragment extends Fragment {

    TextView titleTv, dateStartedTv, dateEndedTv, categoryTv, descriptionTv;
    ImageView categoryIv, importanceIv;
    RecyclerView recyclerView;
    DrawableSelector drawableSelector;
    Success s;
    ArrayList<SuccessImage> successImageList;

    SuccessSliderContract.SuccessImageLoader successImageLoader;

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        ImageLoader.getInstance().init(config.build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_show_success, container, false);

        //((MyWinsApplication)getContext()).getAppComponent().inject(this);

        drawableSelector = new DrawableSelector(getContext());
        titleTv = view.findViewById(R.id.item_title);
        dateStartedTv = view.findViewById(R.id.item_date_started);
        dateEndedTv = view.findViewById(R.id.item_date_ended);
        categoryTv = view.findViewById(R.id.item_category);
        descriptionTv = view.findViewById(R.id.show_description);
        categoryIv = view.findViewById(R.id.item_category_iv);
        importanceIv = view.findViewById(R.id.item_importance_iv);
        recyclerView = view.findViewById(R.id.show_image_recycler_view);

//        FloatingActionButton fab = view.findViewById(R.id.show_fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO editSuccess();
//            }
//        });
        successImageLoader = new SuccessImageLoader();
        successImageLoader.setRepository(new DatabaseSuccessesRepository(getContext()));


        initImageLoader(getContext());
        initRecycler();
        initViews();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

//    private void getSuccessImages(int successId) {
//
//
//        successImages = new ArrayList<>();
//        successImages.clear();
//        dbAdapter.openDB();
//        successImages.addAll(dbAdapter.retrieveSuccessImages(successId));
//        dbAdapter.closeDB();
//        successImageAdapter = new SuccessImageAdapter(successImages, this, R.layout.success_image_layout, this);
//        recyclerView.setAdapter(successImageAdapter);
//        successImageAdapter.notifyDataSetChanged();
//
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

//    }

    private void initViews() {

//        Bundle bundle = this.getArguments();
//        if(bundle != null) {
//            s  = bundle.getParcelable("success");
//

        Bundle bundle = this.getArguments();
        int id = bundle.getInt("id");

        s = successImageLoader.getSuccess(id);

        successImageList = successImageLoader.getSuccessImages(id);

        titleTv.setTag(s.getId());
        titleTv.setText(s.getTitle());
        categoryTv.setText(s.getCategory());
        descriptionTv.setText(s.getDescription());
        dateStartedTv.setText(s.getDateStarted());
        dateEndedTv.setText(s.getDateEnded());
        importanceIv.setTag(s.getImportance());

        drawableSelector.selectCategoryImage(categoryIv, s.getCategory(), categoryTv);
        drawableSelector.selectImportanceImage(importanceIv, s.getImportance());

        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        descriptionTv.startAnimation(fadeIn);
        fadeIn.setDuration(375);
        fadeIn.setFillAfter(true);
    }

    private void initRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

    }

}
