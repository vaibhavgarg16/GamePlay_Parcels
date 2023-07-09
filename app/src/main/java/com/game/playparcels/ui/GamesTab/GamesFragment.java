package com.game.playparcels.ui.GamesTab;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.game.playparcels.Activity.MainActivity;
import com.game.playparcels.CustomSwipeRefreshLayout;
import com.game.playparcels.ModelClasses.HomeGameList.CategoriesItem;
import com.game.playparcels.ModelClasses.HomeGameList.HomeGameList1;
import com.game.playparcels.ModelClasses.HomeGameList.PostsItem;
import com.game.playparcels.ModelClasses.SliderImage.SliderImageResponse;
import com.game.playparcels.R;
import com.game.playparcels.SevicesApi.ApiClient;
import com.game.playparcels.SevicesApi.ApiInterface;
import com.game.playparcels.SingleCategory.GameSingleFragment;
import com.game.playparcels.SpeedyLinearLayoutManager;
import com.game.playparcels.ui.Adapter.SliderAdapter;
import com.game.playparcels.ui.SearchFragment;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.gson.Gson;
import com.preference.PowerPreference;
import net.webilisim.webslider.IndicatorAnimations;
import net.webilisim.webslider.WEBSliderAnimations;
import net.webilisim.webslider.WEBSliderView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GamesFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "DashboardFragment";
    ImageButton searrr, menuicon;
    RecyclerView game_category_recycler;
    GameCategoryRecycler gameCategoryRecycler;
    Context context;
    LinearLayout /*dummy,*/ recyclerlinearlayout;
    View root;
    CustomSwipeRefreshLayout swipeRefreshLayoutOrg;
    WEBSliderView sliderView;
    ArrayList<CategoriesItem> categoriesItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        context = getActivity();
        return inflater.inflate(R.layout.fragment_dashboard, container, false);

    }


    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        menuicon = root.findViewById(R.id.menuicon);
        game_category_recycler = root.findViewById(R.id.game_category_recycler);

        game_category_recycler.setNestedScrollingEnabled(false);
//        dummy = root.findViewById(R.id.dummy);
        recyclerlinearlayout = root.findViewById(R.id.recycler);
//        pullRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayoutOrg = root.findViewById(R.id.swipeRefreshLayoutOrg);
        sliderView = root.findViewById(R.id.imageSlider);
        categoriesItems = new ArrayList<>();
        getsliderImage();

        //Listener for swiperefreshlayout.
        swipeRefreshLayoutOrg.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homepageapi();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayoutOrg.isRefreshing()) {
                        }
                    }
                }, 2000);
            }
        });

        menuicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

        Gson gson = new Gson();
        String json = PowerPreference.getDefaultFile().getString("home", "");
        Bundle args = getArguments();
        if (args != null) {
            String index = args.getString("index", "0");
            Log.d(TAG, "onCreateView: index " + index);
            if (!index.equals("0")) {
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).showloader();
                }
                homePageCat(index);
            } else {
                Log.d(TAG, "onCreateView: no ");
                if (!json.equals("")) {
                    HomeGameList1 homeGameList1 = gson.fromJson(json, HomeGameList1.class);
                    assert homeGameList1 != null;
                    if (homeGameList1.isStatus()) {
                        if (getActivity() != null) {
                            RecyclerView.LayoutManager ee = new GridLayoutManager(getContext(), 1);
                            game_category_recycler.setLayoutManager(ee);
                            gameCategoryRecycler = new GameCategoryRecycler(getContext(), homeGameList1.getCategories(), GamesFragment.this);
                            game_category_recycler.setAdapter(gameCategoryRecycler);
                            game_category_recycler.setHasFixedSize(true);
                            game_category_recycler.setItemViewCacheSize(5);
                            gameCategoryRecycler.notifyDataSetChanged();
                        }
                    } else {
                        homepageapi();
                    }
                } else {
                    homepageapi();
                }
            }
        } else {
            if (!json.equals("")) {

                HomeGameList1 homeGameList1 = gson.fromJson(json, HomeGameList1.class);
                assert homeGameList1 != null;
                if (homeGameList1.isStatus()) {
                    if (getActivity() != null) {
//                        dummy.setVisibility(View.GONE);
                        RecyclerView.LayoutManager ee = new GridLayoutManager(getContext(), 1);
                        game_category_recycler.setLayoutManager(ee);
                        gameCategoryRecycler = new GameCategoryRecycler(getContext(), homeGameList1.getCategories(), GamesFragment.this);
                        game_category_recycler.setAdapter(gameCategoryRecycler);
                        game_category_recycler.setHasFixedSize(true);
                        game_category_recycler.setItemViewCacheSize(5);

                        gameCategoryRecycler.notifyDataSetChanged();
                    }
                } else {
                    homepageapi();
                }

            } else {
                homepageapi();
            }
        }
        searrr = root.findViewById(R.id.searr);
        searrr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {/*startActivity(new Intent(getContext(), SearchActivity.class))*/
                SearchFragment fragment1 = SearchFragment.newInstance();
                FragmentTransaction ft1 = getFragmentManager().beginTransaction().addToBackStack("tag");
                ft1.replace(R.id.container, fragment1);
                ft1.commit();
            }
        });

    }

    private void homePageCat(String index) {
        ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<HomeGameList1> call = apiInterface.getHomeGameListClass(index);
        call.enqueue(new Callback<HomeGameList1>() {
            @Override
            public void onResponse(Call<HomeGameList1> call, Response<HomeGameList1> response) {
                if (response.body() != null) {
                    HomeGameList1 homeGameList1 = response.body();
                    if (homeGameList1.isStatus()) {
//                        RecyclerView.LayoutManager ee = new GridLayoutManager(getContext(), 1);
//                        game_category_recycler.setLayoutManager(ee);

                        game_category_recycler.setLayoutManager(new SpeedyLinearLayoutManager(context, SpeedyLinearLayoutManager.VERTICAL, false));

                        gameCategoryRecycler = new GameCategoryRecycler(getContext(), homeGameList1.getCategories(), GamesFragment.this);
                        game_category_recycler.setAdapter(gameCategoryRecycler);
                        game_category_recycler.setHasFixedSize(true);
                        game_category_recycler.setItemViewCacheSize(5);

                        gameCategoryRecycler.notifyDataSetChanged();

                        if (getActivity() != null) {
                            ((MainActivity) getActivity()).hideloader();
                            ((MainActivity) getActivity()).hideloader1();
                        }
                    }
                }
            }


            @Override
            public void onFailure(Call<HomeGameList1> call, Throwable t) {
                Log.d(TAG, "onFailure H:" + t);
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).hideloader();
                    ((MainActivity) getActivity()).hideloader1();
                }
            }
        });

    }

    private void showPopup(View v) {

        PopupMenu popup = new PopupMenu(getContext(), v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.playstation:
                        //       Toast.makeText(getContext(), "PLAYSTATION 4", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.xbox:
                        //      Toast.makeText(getContext(), "XBOX", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.ninswitch:
                        //       Toast.makeText(getContext(), "NINTENDO SWITCH", Toast.LENGTH_SHORT).show();
                        return true;

                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    /*public void homepageapi() {
        if (getActivity() != null) {
        }
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<HomeGameListClass> call = apiInterface.homepagegame();
        call.enqueue(new Callback<HomeGameListClass>() {
            @Override
            public void onResponse(Call<HomeGameListClass> call, Response<HomeGameListClass> response) {
                swipeRefreshLayoutOrg.setRefreshing(false);
                if (response.body() != null) {
                    HomeGameListClass homeGameListClass = response.body();
                    Log.d(TAG, "onResponse H: 1- " + new Gson().toJson(response.body()));
                    Log.d(TAG, "onResponse H: 2- " + new Gson().toJson(response.code()));
                    if (homeGameListClass.status) {
                        PowerPreference.getDefaultFile().putString("home", new Gson().toJson(response.body()));
                        RecyclerView.LayoutManager ee = new GridLayoutManager(getContext(), 1);
                        game_category_recycler.setLayoutManager(ee);
                        Log.d(TAG, "onResponse:homeGameListClass " + homeGameListClass.categories + " size " + homeGameListClass.categories.size());
                        gameCategoryRecycler = new GameCategoryRecycler(getContext(), homeGameListClass.categories, GamesFragment.this);
                        game_category_recycler.setAdapter(gameCategoryRecycler);
                    } else {
                         Toast.makeText(getContext(), homeGameListClass.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), response.body().message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HomeGameListClass> call, Throwable t) {
                Log.d(TAG, "onFailure H:" + t);
                swipeRefreshLayoutOrg.setRefreshing(false);
            }
        });
    }*/

    public void homepageapi() {
        if (getActivity() != null) {
            ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);
            Call<HomeGameList1> call = apiInterface.homepagegame1();
            call.enqueue(new Callback<HomeGameList1>() {
                @Override
                public void onResponse(Call<HomeGameList1> call, Response<HomeGameList1> response) {
                    swipeRefreshLayoutOrg.setRefreshing(false);
                    if (response.body() != null) {
                        HomeGameList1 homeGameList1 = response.body();
//                    assert homeGameList1 != null;
                        if (homeGameList1.isStatus()) {
                            PowerPreference.getDefaultFile().putString("home", new Gson().toJson(homeGameList1));
                            categoriesItems = homeGameList1.getCategories();
                            RecyclerView.LayoutManager ee = new GridLayoutManager(getContext(), 1);
                            game_category_recycler.setLayoutManager(ee);
                            gameCategoryRecycler = new GameCategoryRecycler(getContext(), categoriesItems, GamesFragment.this);
                            game_category_recycler.setAdapter(gameCategoryRecycler);
                            game_category_recycler.setHasFixedSize(true);
                            game_category_recycler.setItemViewCacheSize(5);

                            gameCategoryRecycler.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), homeGameList1.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<HomeGameList1> call, Throwable t) {
                    Log.d(TAG, "onFailure H:" + t);
                    swipeRefreshLayoutOrg.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: Dashboard");
        int id = v.getId();

        if (id == R.id.holderid) {
            int tagKey = "YourSimpleKey".hashCode();
            int tagKey1 = "YourSimpleKey1".hashCode();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run: Thread" + Thread.currentThread().getId());
                }
            });

            Bundle bundle = new Bundle();
            bundle.putSerializable("data", (PostsItem) v.getTag(tagKey)); //.get(getPosition()
            bundle.putString("adapter", "child");
            bundle.putInt("idd", Integer.parseInt(String.valueOf(v.getTag(tagKey1))));
            GameSingleFragment fragment1 = GameSingleFragment.newInstance();
            fragment1.setArguments(bundle);
            FragmentTransaction ft1 = ((YouTubeBaseActivity) context).getFragmentManager().beginTransaction().addToBackStack("tag");
            ft1.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            ft1.replace(R.id.container, fragment1);
            ft1.commit();
        }

    }

    class BackgroundThread extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }
    }

    public void getsliderImage() {


        ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<SliderImageResponse> call = apiInterface.getSliderImages1();
        call.enqueue(new Callback<SliderImageResponse>() {
            @Override
            public void onResponse(Call<SliderImageResponse> call, Response<SliderImageResponse> response) {
                if (response.body() != null) {
                    SliderImageResponse sliderImageResponse = response.body();
                   List<String> imageUrl = sliderImageResponse.getImageUrl();
                    sliderView.setSliderAdapter(new SliderAdapter(context,imageUrl));
                    sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
                    sliderView.setSliderTransformAnimation(WEBSliderAnimations.FADETRANSFORMATION);
                    sliderView.setAutoCycleDirection(net.webilisim.webslider.WEBSliderView.AUTO_CYCLE_DIRECTION_RIGHT);
                    sliderView.setIndicatorSelectedColor(Color.WHITE);
                    sliderView.setIndicatorUnselectedColor(Color.GRAY);
                    sliderView.setScrollTimeInSec(4);
                    sliderView.startAutoCycle();
                } else {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SliderImageResponse> call, Throwable t) {

            }
        });
    }
}