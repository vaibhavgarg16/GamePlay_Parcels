package com.game.playparcels.SingleCategory;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.bumptech.glide.Glide;

import com.game.playparcels.Activity.MainActivity;
import com.game.playparcels.LockableNestedScrollView;
import com.game.playparcels.ModelClasses.AdToQueuesModelClass;
import com.game.playparcels.ModelClasses.HomeGameList.CategoriesItem;
import com.game.playparcels.ModelClasses.HomeGameList.HomeGameList1;
import com.game.playparcels.ModelClasses.HomeGameList.PostsItem;
import com.game.playparcels.ModelClasses.HomeGameListClass;
import com.game.playparcels.ModelClasses.ProductByTagModelClass;
import com.game.playparcels.ModelClasses.RelatedGamesModelClass;
import com.game.playparcels.OnBackPressListener;
import com.game.playparcels.R;
import com.game.playparcels.RecyclerViewDisabler;
import com.game.playparcels.SevicesApi.ApiClient;
import com.game.playparcels.SevicesApi.ApiInterface;
import com.game.playparcels.ModelClasses.SliderImagesModelClass;
import com.google.gson.Gson;
import com.preference.PowerPreference;


import net.webilisim.webslider.IndicatorAnimations;
import net.webilisim.webslider.WEBSliderAnimations;
import net.webilisim.webslider.WEBSliderView;
import net.webilisim.webslider.WEBSliderViewAdapter;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullCategoryFragment extends Fragment implements OnBackPressListener, View.OnClickListener {

    ImageView imgone;
    String TAG = "FullCategoryFragmentTAG";
    CategoriesItem category;
    //    PostsItem postsItem;
    RelatedGamesModelClass Rcat;
    RecyclerView recyclerfullcat;
    TextView categoryname;
    //    Integer idd;
    int amt = 12;
    FullCategoryRecycler fullCategoryRecycler;
    FullCategoryRecycle fullCategoryRecycle;
    FullCategoryRecycl fullCategoryRecycl;

    ProductByTagModelClass productByTagModelClass;

    ProgressBar more;


    TSnackbar snackbar;
    CoordinatorLayout coo;
    LockableNestedScrollView nscroll;

    int itemcountt = 0;
    RecyclerView.OnItemTouchListener disabler;

    String listViewImage1, listViewImage2, listViewImage3;

    WEBSliderView WEBSliderView;

    String type;


    public static FullCategoryFragment newInstance() {

        return new FullCategoryFragment();
    }

    public FullCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_full_category, container, false);
        imgone = root.findViewById(R.id.imgone);

        recyclerfullcat = root.findViewById(R.id.recyclerfullcat);
        disabler = new RecyclerViewDisabler();

        /*SimpleItemAnimator itemAnimator = (SimpleItemAnimator) recyclerfullcat.getItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);*/

        categoryname = root.findViewById(R.id.categoryname);
        coo = root.findViewById(R.id.coo);
        more = root.findViewById(R.id.more);
        more.setVisibility(View.GONE);
        more.getIndeterminateDrawable().setColorFilter(0xFF446084, android.graphics.PorterDuff.Mode.MULTIPLY);


        WEBSliderView = root.findViewById(R.id.webSliderView);

        listViewImage1 = PowerPreference.getDefaultFile().getString("listViewImage1", null);
        listViewImage2 = PowerPreference.getDefaultFile().getString("listViewImage2", null);
        listViewImage3 = PowerPreference.getDefaultFile().getString("listViewImage3", null);
        WEBSliderView.setSliderAdapter(new SliderAdapterExample(getContext()));

        WEBSliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        WEBSliderView.setSliderTransformAnimation(WEBSliderAnimations.FADETRANSFORMATION);
        WEBSliderView.setAutoCycleDirection(net.webilisim.webslider.WEBSliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        WEBSliderView.setIndicatorSelectedColor(Color.WHITE);
        WEBSliderView.setIndicatorUnselectedColor(Color.GRAY);
        WEBSliderView.setScrollTimeInSec(4);
        WEBSliderView.startAutoCycle();

        getsliderImage();


        nscroll = root.findViewById(R.id.nscroll);
        PowerPreference.getDefaultFile().putInt("itemcount", 12);

        Bundle bundle = this.getArguments();
        Log.d(TAG, "onCreateView: " + bundle);
        if (bundle != null) {
            Log.d(TAG, "onCreateView:budle not null ");
            type = bundle.getString("adapter");
            String index = bundle.getString("index", "0");
            String tag = bundle.getString("tag", "00");
            String categoryId = bundle.getString("categoryId", "00");


            Log.d(TAG, "onCreateView:RLayout index " + index + " tag " + tag + " categoryId " + categoryId);
            if (type != null && type.equals("categories")) {
                Log.d(TAG, "onCreateView: type not nuill");
                category = (CategoriesItem) bundle.getSerializable("data");
//                idd = postsItem.getCategoryId();
                categoryname.setText(category.getName());

                Log.d("iougyik", "getItemCount:1 " + amt);
                RecyclerView.LayoutManager ee = new LinearLayoutManager(getContext());
                recyclerfullcat.setLayoutManager(ee);
                fullCategoryRecycler = new FullCategoryRecycler(getContext(), category.getPosts(), FullCategoryFragment.this/*, idd*/);
                recyclerfullcat.setAdapter(fullCategoryRecycler);
                recyclerfullcat.setHasFixedSize(true);
                recyclerfullcat.setItemViewCacheSize(5);
                fullCategoryRecycler.notifyDataSetChanged();


            } else if (type != null && type.equals("Relatedtext")) {
                Log.d(TAG, "onCreateView: type not nuill");
                Rcat = (RelatedGamesModelClass) bundle.getSerializable("data");
//                idd = 00;
                categoryname.setText("Related Games");

                RecyclerView.LayoutManager ee = new LinearLayoutManager(getContext());
                recyclerfullcat.setLayoutManager(ee);
                fullCategoryRecycle = new FullCategoryRecycle(getContext(), (ArrayList<RelatedGamesModelClass.Post>) Rcat.posts, FullCategoryFragment.this/*, idd*/);
                recyclerfullcat.setAdapter(fullCategoryRecycle);
                recyclerfullcat.setHasFixedSize(true);
                recyclerfullcat.setItemViewCacheSize(5);
                fullCategoryRecycle.notifyDataSetChanged();

            } else if (!index.equals("0")) {
                Toast.makeText(getContext(), "Index", Toast.LENGTH_SHORT).show();
                homePageCat(index);
            } else if (!tag.equals("00")) {
                type = "tagcat";
                Toast.makeText(getContext(), "Tag", Toast.LENGTH_SHORT).show();
                categoryname.setText(tag);
                tagcatApi(tag, categoryId);
            }
        }


        nscroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
                        Log.d("TAG", "onScrollChange: " + scrollY);

                        int itemcount = PowerPreference.getDefaultFile().getInt("itemcount");

                        if (type != null && type.equals("Relatedtext")) {
                            if (itemcount + 12 < Rcat.posts.size()) {
                                itemcount = itemcount + 12;
                                itemcountt = itemcount;
                                PowerPreference.getDefaultFile().putInt("itemcount", itemcount);
                                more.setVisibility(View.VISIBLE);
                                nscroll.setScrollingEnabled(false);
                                recyclerfullcat.setNestedScrollingEnabled(false);
                                recyclerfullcat.addOnItemTouchListener(disabler);

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        more.setVisibility(View.GONE);
                                        fullCategoryRecycle = new FullCategoryRecycle(getContext(), (ArrayList<RelatedGamesModelClass.Post>) Rcat.posts, FullCategoryFragment.this);
                                        recyclerfullcat.setAdapter(fullCategoryRecycle);
                                        recyclerfullcat.setHasFixedSize(true);
                                        recyclerfullcat.setItemViewCacheSize(5);
                                        fullCategoryRecycle.notifyDataSetChanged();
                                        //recyclerfullcat.scrollToPosition(itemcountt-12);
                                  /*  nscroll.setScrollingEnabled(true);
                                    recyclerfullcat.setNestedScrollingEnabled(true);
                                    recyclerfullcat.removeOnItemTouchListener(disabler);*/
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                nscroll.setScrollingEnabled(true);
                                                recyclerfullcat.setNestedScrollingEnabled(true);
                                                recyclerfullcat.removeOnItemTouchListener(disabler);
                                            }
                                        }, 200);
                                    }
                                }, 150);


                            } else if (itemcount == Rcat.posts.size()) {
                                Toasty.info(getActivity(), "No more games.", Toast.LENGTH_LONG).show();
                                more.setVisibility(View.GONE);

                            } else {
                                itemcount = Rcat.posts.size();
                                PowerPreference.getDefaultFile().putInt("itemcount", itemcount);
                                more.setVisibility(View.VISIBLE);
                                itemcountt = itemcount;
                                nscroll.setScrollingEnabled(false);
                                recyclerfullcat.setNestedScrollingEnabled(false);
                                recyclerfullcat.addOnItemTouchListener(disabler);

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        more.setVisibility(View.GONE);
                                        fullCategoryRecycle = new FullCategoryRecycle(getContext(), Rcat.posts, FullCategoryFragment.this);
                                        recyclerfullcat.setAdapter(fullCategoryRecycle);
                                        recyclerfullcat.setHasFixedSize(true);
                                        recyclerfullcat.setItemViewCacheSize(5);
                                        fullCategoryRecycle.notifyDataSetChanged();
                                        //  recyclerfullcat.scrollToPosition(itemcountt-12);
                                   /* nscroll.setScrollingEnabled(true);
                                    recyclerfullcat.setNestedScrollingEnabled(true);

                                    recyclerfullcat.removeOnItemTouchListener(disabler);*/

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                nscroll.setScrollingEnabled(true);
                                                recyclerfullcat.setNestedScrollingEnabled(true);
                                                recyclerfullcat.removeOnItemTouchListener(disabler);
                                            }
                                        }, 200);
                                    }
                                }, 150);
                                //  Toast.makeText(getActivity(), "Loading.... Please wait", Toast.LENGTH_SHORT).show();

                            }
                        }
                        //if else main
                        else if (type != null && type.equals("tagcat")) {
                            if (itemcount + 12 < productByTagModelClass.posts.size()) {
                                itemcount = itemcount + 12;
                                itemcountt = itemcount;
                                PowerPreference.getDefaultFile().putInt("itemcount", itemcount);
                                more.setVisibility(View.VISIBLE);
                                nscroll.setScrollingEnabled(false);
                                recyclerfullcat.setNestedScrollingEnabled(false);
                                recyclerfullcat.addOnItemTouchListener(disabler);

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        more.setVisibility(View.GONE);
                                        fullCategoryRecycl = new FullCategoryRecycl(getContext(), (ArrayList<ProductByTagModelClass.Post>) productByTagModelClass.posts, FullCategoryFragment.this);
                                        recyclerfullcat.setAdapter(fullCategoryRecycl);
                                        recyclerfullcat.setHasFixedSize(true);
                                        recyclerfullcat.setItemViewCacheSize(5);
                                        fullCategoryRecycl.notifyDataSetChanged();
                                        //recyclerfullcat.scrollToPosition(itemcountt-12);
                                  /*  nscroll.setScrollingEnabled(true);
                                    recyclerfullcat.setNestedScrollingEnabled(true);
                                    recyclerfullcat.removeOnItemTouchListener(disabler);*/
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                nscroll.setScrollingEnabled(true);
                                                recyclerfullcat.setNestedScrollingEnabled(true);
                                                recyclerfullcat.removeOnItemTouchListener(disabler);
                                            }
                                        }, 200);
                                    }
                                }, 150);


                            } else if (itemcount == productByTagModelClass.posts.size()) {
                                Toasty.info(getActivity(), "No more games.", Toast.LENGTH_LONG).show();
                                more.setVisibility(View.GONE);

                            } else {
                                itemcount = productByTagModelClass.posts.size();
                                PowerPreference.getDefaultFile().putInt("itemcount", itemcount);
                                more.setVisibility(View.VISIBLE);
                                itemcountt = itemcount;
                                nscroll.setScrollingEnabled(false);
                                recyclerfullcat.setNestedScrollingEnabled(false);
                                recyclerfullcat.addOnItemTouchListener(disabler);

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        more.setVisibility(View.GONE);
                                        fullCategoryRecycl = new FullCategoryRecycl(getContext(), (ArrayList<ProductByTagModelClass.Post>) productByTagModelClass.posts, FullCategoryFragment.this);
                                        recyclerfullcat.setAdapter(fullCategoryRecycl);
                                        recyclerfullcat.setHasFixedSize(true);
                                        recyclerfullcat.setItemViewCacheSize(5);
                                        fullCategoryRecycl.notifyDataSetChanged();
                                        //  recyclerfullcat.scrollToPosition(itemcountt-12);
                                   /* nscroll.setScrollingEnabled(true);
                                    recyclerfullcat.setNestedScrollingEnabled(true);

                                    recyclerfullcat.removeOnItemTouchListener(disabler);*/

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                nscroll.setScrollingEnabled(true);
                                                recyclerfullcat.setNestedScrollingEnabled(true);
                                                recyclerfullcat.removeOnItemTouchListener(disabler);
                                            }
                                        }, 200);
                                    }
                                }, 150);
                                //  Toast.makeText(getActivity(), "Loading.... Please wait", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            if (itemcount + 12 < category.getPosts().size()) {
                                itemcount = itemcount + 12;
                                itemcountt = itemcount;
                                PowerPreference.getDefaultFile().putInt("itemcount", itemcount);
                                more.setVisibility(View.VISIBLE);
                                nscroll.setScrollingEnabled(false);
                                recyclerfullcat.setNestedScrollingEnabled(false);
                                recyclerfullcat.addOnItemTouchListener(disabler);

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        more.setVisibility(View.GONE);
                                        fullCategoryRecycler = new FullCategoryRecycler(getContext(), category.getPosts(), FullCategoryFragment.this);
                                        recyclerfullcat.setAdapter(fullCategoryRecycler);
                                        recyclerfullcat.setHasFixedSize(true);
                                        recyclerfullcat.setItemViewCacheSize(5);
                                        fullCategoryRecycler.notifyDataSetChanged();
                                        //recyclerfullcat.scrollToPosition(itemcountt-12);
                                  /*  nscroll.setScrollingEnabled(true);
                                    recyclerfullcat.setNestedScrollingEnabled(true);
                                    recyclerfullcat.removeOnItemTouchListener(disabler);*/
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                nscroll.setScrollingEnabled(true);
                                                recyclerfullcat.setNestedScrollingEnabled(true);
                                                recyclerfullcat.removeOnItemTouchListener(disabler);
                                            }
                                        }, 200);
                                    }
                                }, 150);


                            } else if (itemcount == category.getPosts().size()) {
                                Toasty.info(getActivity(), "No more games.", Toast.LENGTH_LONG).show();
                                more.setVisibility(View.GONE);

                            } else {
                                itemcount = category.getPosts().size();
                                PowerPreference.getDefaultFile().putInt("itemcount", itemcount);
                                more.setVisibility(View.VISIBLE);
                                itemcountt = itemcount;
                                nscroll.setScrollingEnabled(false);
                                recyclerfullcat.setNestedScrollingEnabled(false);
                                recyclerfullcat.addOnItemTouchListener(disabler);

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        more.setVisibility(View.GONE);
                                        fullCategoryRecycler = new FullCategoryRecycler(getContext(), category.getPosts(), FullCategoryFragment.this);
                                        recyclerfullcat.setAdapter(fullCategoryRecycler);
                                        recyclerfullcat.setHasFixedSize(true);
                                        recyclerfullcat.setItemViewCacheSize(5);
                                        fullCategoryRecycler.notifyDataSetChanged();
                                        //  recyclerfullcat.scrollToPosition(itemcountt-12);
                                   /* nscroll.setScrollingEnabled(true);
                                    recyclerfullcat.setNestedScrollingEnabled(true);

                                    recyclerfullcat.removeOnItemTouchListener(disabler);*/

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                nscroll.setScrollingEnabled(true);
                                                recyclerfullcat.setNestedScrollingEnabled(true);
                                                recyclerfullcat.removeOnItemTouchListener(disabler);
                                            }
                                        }, 200);
                                    }
                                }, 150);
                            }
                        }
                    }
                }
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

    }

    private void tagcatApi(String tag, String categoryId) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ProductByTagModelClass> call = apiInterface.getProductByTag(categoryId, tag);
        call.enqueue(new Callback<ProductByTagModelClass>() {
            @Override
            public void onResponse(Call<ProductByTagModelClass> call, Response<ProductByTagModelClass> response) {
                if (getActivity() != null) {


                    productByTagModelClass = response.body();
                    Log.d(TAG, "onResponse H:v  " + new Gson().toJson(response.body()));

                    assert productByTagModelClass != null;
                    if (productByTagModelClass.errorCode.equals("0")) {

                        assert productByTagModelClass != null;
                        if (productByTagModelClass.status) {
                            Log.d("iougyik", "getItemCount:1 " + amt);
                            RecyclerView.LayoutManager ee = new LinearLayoutManager(getContext());
                            recyclerfullcat.setLayoutManager(ee);
                            fullCategoryRecycl = new FullCategoryRecycl(getContext(), productByTagModelClass.posts, FullCategoryFragment.this);
                            recyclerfullcat.setAdapter(fullCategoryRecycl);
                            recyclerfullcat.setHasFixedSize(true);
                            recyclerfullcat.setItemViewCacheSize(5);
                            fullCategoryRecycl.notifyDataSetChanged();
                        }

                    }
                    //progressBar.setVisibility(View.GONE);
                    if (getActivity() != null) {
                        ((MainActivity) getActivity()).hideloader1();
                        ((MainActivity) getActivity()).hideloader();
                    }
                    //viewDialog.hideDialog();
                }
            }


            @Override
            public void onFailure(Call<ProductByTagModelClass> call, Throwable t) {
                // PowerPreference.getDefaultFile().putBoolean("loggedin",false);
                Log.d(TAG, "onFailure H:" + t);
                // progressBar.setVisibility(View.GONE);
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).hideloader1();
                    ((MainActivity) getActivity()).hideloader();
                }
                //viewDialog.hideDialog();
                //  Toast.makeText(getContext(), ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void GotoGameDetail() {
        GameSingleFragment fragment1 = GameSingleFragment.newInstance();
        FragmentTransaction ft1 = getFragmentManager().beginTransaction().addToBackStack("tag");
        ft1.replace(R.id.container, fragment1);
        ft1.commit();
        //startActivity(new Intent(getContext(), GameSingleActivity.class));

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.addtoqueue) {
            Object productid = v.getTag();
            Log.d(TAG, "onClick: productid.iD " + Integer.parseInt(String.valueOf(productid)));
            addQueueProduct(Integer.parseInt(String.valueOf(productid)));
        }
    }

    private void addQueueProduct(int productid) {
        Boolean loggedin = PowerPreference.getDefaultFile().getBoolean("loggedin", false);
        if (loggedin) {
            Integer userid = PowerPreference.getDefaultFile().getInt("userid");
            addtoQueueapi(userid, productid);
        } else {

            snackbar = TSnackbar.make(coo, "Not logged in", TSnackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();

            snackbarView.setBackgroundResource(R.drawable.roundborderred);
            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setTextColor(Color.BLACK);
            snackbar.show();

        }


    }


    public void addtoQueueapi(int userid, int productid) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<AdToQueuesModelClass> call = apiInterface.addToQueue(userid, productid);
        call.enqueue(new Callback<AdToQueuesModelClass>() {
            @Override
            public void onResponse(Call<AdToQueuesModelClass> call, Response<AdToQueuesModelClass> response) {
                AdToQueuesModelClass adToQueuesModelClass = response.body();
                Log.d(TAG, "onResponse:adToQueuesModelClass " + adToQueuesModelClass.message + " " + adToQueuesModelClass.status);

                assert adToQueuesModelClass != null;
                if (adToQueuesModelClass.message.equals("This game is already in your queue.")) {

                    Toasty.warning(getActivity(), "This game is already in your queue.", Toast.LENGTH_LONG).show();

                } else if (adToQueuesModelClass.message.equals("Added to Queue")) {
                    snackbar = TSnackbar.make(coo, "Added to Queue", TSnackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundResource(R.drawable.roundbordergreen);
                    //snackbarView.setBackgroundColor(Color.parseColor("#0C910C"));
                    snackbar.setAction("VIEW", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.putExtra("frag", "queue");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                        }
                    });
                    TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                    textView.setTextColor(Color.BLACK);
                    snackbar.show();
                } else if (adToQueuesModelClass.message.equals("Invalid Subscription")) {
                    snackbar = TSnackbar.make(coo, "Invalid Subscription", TSnackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundResource(R.drawable.roundborderred);
                    snackbar.setAction("VIEW", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.putExtra("frag", "queue");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                        }
                    });
                    TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                    textView.setTextColor(Color.BLACK);
                    snackbar.show();
                } else if (adToQueuesModelClass.message.equals("Queue is full")) {
                    snackbar = TSnackbar.make(coo, "Queue is full", TSnackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundResource(R.drawable.roundborderyellow);
                    //snackbarView.setBackgroundColor(Color.parseColor("#D6CD09"));
                    snackbar.setAction("VIEW", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.putExtra("frag", "queue");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                        }
                    });
                    TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                    textView.setTextColor(Color.BLACK);
                    snackbar.show();
                } else {
                    //  Toast.makeText(getContext(), "" + adToQueuesModelClass.message, Toast.LENGTH_SHORT).show();
                    ShowToast(adToQueuesModelClass.message);
                    Log.d(TAG, "onResponse:adToQueuesModelClassmsg " + adToQueuesModelClass.message);
                }
            }

            @Override
            public void onFailure(Call<AdToQueuesModelClass> call, Throwable t) {
                Log.d(TAG, "onFailure H:" + t);
                //  Toast.makeText(getContext(), "" + t, Toast.LENGTH_SHORT).show();
                ShowToast(t.getMessage());
            }
        });
    }


    private void homePageCat(String index) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<HomeGameList1> call = apiInterface.getHomeGameListClass(index);
        call.enqueue(new Callback<HomeGameList1>() {
            @Override
            public void onResponse(Call<HomeGameList1> call, Response<HomeGameList1> response) {
                HomeGameList1 homeGameList1 = response.body();
                if (response.body() != null) {
                    if (!response.body().equals("")) {

                        if (homeGameList1.isStatus()) {
                            if (getActivity() != null) {
                                ((MainActivity) getActivity()).hideloader1();
                                ((MainActivity) getActivity()).hideloader();

                                category = homeGameList1.getCategories().get(0);
                                categoryname.setText(category.getName());

                                Log.d("iougyik", "getItemCount:1 " + amt);
                                if (category.getPosts() != null) {
                                    RecyclerView.LayoutManager ee = new LinearLayoutManager(getContext());
                                    recyclerfullcat.setLayoutManager(ee);
                                    fullCategoryRecycler = new FullCategoryRecycler(getContext(), category.getPosts(), FullCategoryFragment.this);
                                    recyclerfullcat.setAdapter(fullCategoryRecycler);
                                    recyclerfullcat.setHasFixedSize(true);
                                    recyclerfullcat.setItemViewCacheSize(5);
                                    fullCategoryRecycler.notifyDataSetChanged();
                                }
                            }
                            //progressBar.setVisibility(View.GONE);
                        }
                    }
                }
            }


            @Override
            public void onFailure(Call<HomeGameList1> call, Throwable t) {
                Log.d(TAG, "onFailure H:" + t);
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).hideloader1();
                    ((MainActivity) getActivity()).hideloader();
                }
            }
        });

    }

    public void getsliderImage() {
        Log.d(TAG, "listViewImage: old " + listViewImage1 + " " + listViewImage2 + " " + listViewImage3);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<SliderImagesModelClass> call = apiInterface.getSliderImages();
        call.enqueue(new Callback<SliderImagesModelClass>() {
            @Override
            public void onResponse(Call<SliderImagesModelClass> call, Response<SliderImagesModelClass> response) {

                SliderImagesModelClass cancelReactivateGetModelClass = response.body();

                assert cancelReactivateGetModelClass != null;

                listViewImage1 = cancelReactivateGetModelClass.listViewImage1;
                PowerPreference.getDefaultFile().putString("listViewImage1", listViewImage1);

                listViewImage2 = cancelReactivateGetModelClass.listViewImage2;
                PowerPreference.getDefaultFile().putString("listViewImage2", listViewImage2);

                listViewImage3 = cancelReactivateGetModelClass.listViewImage3;
                PowerPreference.getDefaultFile().putString("listViewImage3", listViewImage3);

                Log.d(TAG, "listViewImage: new " + listViewImage1 + " " + listViewImage2 + " " + listViewImage3);

                WEBSliderView.setSliderAdapter(new SliderAdapterExample(getContext()));


            }

            @Override
            public void onFailure(Call<SliderImagesModelClass> call, Throwable t) {
                // PowerPreference.getDefaultFile().putBoolean("loggedin",false);
                //viewDialog.hideDialog();
                //   Toast.makeText(getContext(), ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class SliderAdapterExample extends WEBSliderViewAdapter<SliderAdapterExample.AdapterViewHolder> {

        private Context context;

        SliderAdapterExample(Context context) {
            this.context = context;
        }

        @Override
        public AdapterViewHolder onCreateViewHolder(ViewGroup parent) {
            @SuppressLint("InflateParams")
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_webslider_adapter_layout, null);
            return new AdapterViewHolder(inflate);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(AdapterViewHolder viewHolder, int position) {
            viewHolder.textView.setText("Here is slider item " + position);

            switch (position) {
                case 0:
                    Glide.with(context)
                            .load(listViewImage1)
                            .into(viewHolder.imageView);
                    break;
                case 1:
                    Glide.with(context)
                            .load(listViewImage2)
                            .into(viewHolder.imageView);
                    break;
                case 2:
                    Glide.with(context)
                            .load(listViewImage3)
                            .into(viewHolder.imageView);
                    break;


            }

        }

        @Override
        public int getCount() {
            return 3;
        }

        class AdapterViewHolder extends WEBSliderViewAdapter.ViewHolder {

            View itemView;
            ImageView imageView;
            TextView textView;

            AdapterViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.webslider_image);
                textView = itemView.findViewById(R.id.webslider_textview);
                this.itemView = itemView;
            }
        }
    }

    public void ShowToast(String s) {
        snackbar = TSnackbar.make(coo, s, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundResource(R.drawable.roundborderyellow);

        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.BLACK);
        snackbar.show();
    }

}
