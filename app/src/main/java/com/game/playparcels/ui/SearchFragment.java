package com.game.playparcels.ui;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;


import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.game.playparcels.Activity.GameSearchRecyclerAdapter;
import com.game.playparcels.Activity.MainActivity;
import com.game.playparcels.Activity.SearchActivity;
import com.game.playparcels.ModelClasses.SearchGamesModelClass;
import com.game.playparcels.OnBackPressListener;
import com.game.playparcels.R;
import com.game.playparcels.SevicesApi.ApiClient;
import com.game.playparcels.SevicesApi.ApiInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements OnBackPressListener,View.OnClickListener {

    EditText searchedit;
    String TAG = "SearchActivityTAG";
    ImageView backbtn;
    Boolean Cross = false;

    GameSearchRecyclerAdapter gameSearchRecyclerAdapter;
    RecyclerView game_category_recycler;
    ProgressBar imageView;
    Call<SearchGamesModelClass> call;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }
    String se, sr;
    Handler handler = new Handler();

    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(/*String param1, String param2*/) {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_search, container, false);


    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);


        ((MainActivity) getActivity()).stopVideo();

        searchedit = root.findViewById(R.id.search_edit_text);
        searchedit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, 0,0);
        // dataa=root.findViewById(R.id.dataa);
        backbtn = root.findViewById(R.id.searchbackbtn);
        game_category_recycler=root.findViewById(R.id.game_category_recycler);

       /* GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.drawable.loader78px).into(imageViewTarget);*/
        imageView.setVisibility(View.GONE);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        searchedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                se = s.toString();



                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    public void run() {

                        if(call != null && call.isExecuted()) {
                            call.cancel();
                            searchpageapi(se);
                        }else {searchpageapi(se);}

                    }
                }, 1000);




                Log.d(TAG, "onTextChanged: "+ count);
                if (count==0){

                    Cross=false;
                    searchedit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, 0,0);
                    imageView.setVisibility(View.GONE);
                }
                else if (count>2){
                    Cross=true;
                    searchedit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, 0,0);
                    imageView.setVisibility(View.VISIBLE);
                }
                else {
                    imageView.setVisibility(View.GONE);
                    Cross=true;
                    searchedit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, R.drawable.ic_cancel_black_24dp,0);
                    //dataa.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                // searchpageapi(s.toString());

            }
        });

        searchedit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                Log.d(TAG, "onTouchrrrrrrrr: "+v+"         "+ event);


                if (Cross){

                    if(event.getAction() == MotionEvent.ACTION_UP ||event.getAction() ==  MotionEvent.ACTION_UP) {
                        if(event.getRawX() >= (searchedit.getRight() - searchedit.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            // your action here
                            searchedit.setText("");
                            return true;
                        }
                    }}
                return false;
            }

        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onBackPressed() {
        getFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onClick(View v) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void searchpageapi(String s) {
        //  progressBar.setVisibility(View.VISIBLE);
        //viewDialog.showDialog();

        Cross=true;
        searchedit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, 0,0);
        imageView.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
         call = apiInterface.searchgames(s);
        call.enqueue(new Callback<SearchGamesModelClass>() {
            @Override
            public void onResponse(Call<SearchGamesModelClass> call, Response<SearchGamesModelClass> response) {

                if (getActivity()!=null) {
                    //  dummy.setVisibility(View.GONE);

                    SearchGamesModelClass homeGameListClass = response.body();
                    Log.d(TAG, "onResponse H: " + new Gson().toJson(response.body()));

                    assert homeGameListClass != null;
                    if (homeGameListClass.status) {
                        RecyclerView.LayoutManager ee = new GridLayoutManager(getContext(), 1);
                        game_category_recycler.setLayoutManager(ee);
                        Log.d(TAG, "onResponse: homeGameListClass " + homeGameListClass.categories + " size " + homeGameListClass.categories.size());
                        gameSearchRecyclerAdapter = new GameSearchRecyclerAdapter(getContext(), homeGameListClass.categories, SearchFragment.this);
                        game_category_recycler.setAdapter(gameSearchRecyclerAdapter);
                        game_category_recycler.setHasFixedSize(true);
                        game_category_recycler.setItemViewCacheSize(5);
                    } /*else {
                     //   Toast.makeText(getContext(), "Email Or Password Incorrect", Toast.LENGTH_SHORT).show();
                    }*/

                    if (call.isExecuted()) {
                        imageView.setVisibility(View.GONE);
                        searchedit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, R.drawable.ic_cancel_black_24dp, 0);
                    }

                }
            }


            @Override
            public void onFailure(Call<SearchGamesModelClass> call, Throwable t) {
                // PowerPreference.getDefaultFile().putBoolean("loggedin",false);
                Log.d(TAG, "onFailure H:"+t);
                //   progressBar.setVisibility(View.GONE);
                //   viewDialog.hideDialog();
                //   Toast.makeText(getContext(), ""+t, Toast.LENGTH_SHORT).show();
                imageView.setVisibility(View.GONE);
                searchedit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, R.drawable.ic_cancel_black_24dp,0);
            }


        });
    }
}
