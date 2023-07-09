package com.game.playparcels.ui.QueueTab;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.game.playparcels.Activity.MainActivity;
import com.game.playparcels.ModelClasses.QueueListModelClass;
import com.game.playparcels.ModelClasses.QueueReturnModelClass;
import com.game.playparcels.ModelClasses.ReturnPhotoModelClass;
import com.game.playparcels.R;
import com.game.playparcels.SevicesApi.ApiClient;
import com.game.playparcels.SevicesApi.ApiInterface;
import com.google.gson.Gson;
import com.preference.PowerPreference;


import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QueueFragment extends Fragment implements View.OnClickListener {

    RelativeLayout layoutgone;
    ImageView crossx/*,que1*//*,que2*/;
    TextView guestalert;
    RecyclerView game_category_recycler;
    QueueRecyclerAdapter gameCategoryRecycler;

    boolean isUp;
    Integer userid;
    int orderidimage;

    Call<QueueReturnModelClass> call;
    CoordinatorLayout coo;
    TSnackbar snackbar;
    Handler handler = new Handler();
    String se;

    public static QueueFragment newInstance() {
        return new QueueFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        crossx = root.findViewById(R.id.cross);
        layoutgone = root.findViewById(R.id.layoutgone);
        guestalert = root.findViewById(R.id.guestalert);

        coo = root.findViewById(R.id.coo);
        // que1=root.findViewById(R.id.que1);
        // que2=root.findViewById(R.id.que2);
        layoutgone.setVisibility(View.INVISIBLE);
        game_category_recycler = root.findViewById(R.id.game_category_recycler);

        isUp = false;
        boolean loggedin = PowerPreference.getDefaultFile().getBoolean("loggedin", false);
        if (loggedin) {
            userid = PowerPreference.getDefaultFile().getInt("userid");
            getQueueapi(userid);
        } else { guestalert.setVisibility(View.VISIBLE); }


        crossx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSlideViewButtonClick(v);
                // layoutgone.setVisibility(View.GONE);
            }
        });


    }

    public void getQueueapi(Integer userid) {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showloader();
        }
        Log.d("tagg", "getQueueapi: userid" + userid);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<QueueListModelClass> call = apiInterface.getQueue(userid);
        call.enqueue(new Callback<QueueListModelClass>() {
            @Override
            public void onResponse(Call<QueueListModelClass> call, Response<QueueListModelClass> response) {
                if (getActivity() != null) {
                    QueueListModelClass queueListModelClass = response.body();
                    Log.d("tagg", "getQueueapi: response" + new Gson().toJson(response.body()));
                    assert queueListModelClass != null;
                    if (queueListModelClass.status) {

                        RecyclerView.LayoutManager ee = new GridLayoutManager(getContext(), 1);
                        game_category_recycler.setLayoutManager(ee);
                        Log.d("tagg", "onResponse:homeGameListClass " + queueListModelClass.categories + " size " + queueListModelClass.categories.size());
                        gameCategoryRecycler = new QueueRecyclerAdapter(getContext(), queueListModelClass.categories, QueueFragment.this, QueueFragment.this);
                        game_category_recycler.setAdapter(gameCategoryRecycler);
                        game_category_recycler.setNestedScrollingEnabled(true);

                    }
                }
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).hideloader();
                }
            }


            @Override
            public void onFailure(Call<QueueListModelClass> call, Throwable t) {
                // PowerPreference.getDefaultFile().putBoolean("loggedin",false);
                //viewDialog.hideDialog();
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).hideloader();
                }
                Toast.makeText(getContext(), "" + t, Toast.LENGTH_SHORT).show();
            }
        });

    }

    // slide the view from below itself to the current position
    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void onSlideViewButtonClick(View view) {
        if (isUp) {
            slideDown(layoutgone);
            // myButton.setText("Slide up");
        } else {
            slideUp(layoutgone);
            // myButton.setText("Slide down");
        }
        isUp = !isUp;
    }

    @Override
    public void onClick(View v) {
        int tagKey = "YourSimpleKey".hashCode();
        int tagKey1 = "YourSimpleKey1".hashCode();

        int Id = v.getId();
        // Toast.makeText(getContext(), "Id "+Id+"  "+R.id.question , Toast.LENGTH_SHORT).show();
        if (Id == R.id.question) {
            onSlideViewButtonClick(v);
        } else if (Id == R.id.returnbtn) {
            Object o = v.getTag(tagKey);
            int orderid = Integer.parseInt(String.valueOf(o));

            Object p = v.getTag(tagKey1);
            int pos = Integer.parseInt(String.valueOf(p));

            Log.d("TAG", "onClick: returnbtn " + orderid + " " + pos);

            removeQueueProductapi(userid, orderid);

        } else if (Id == R.id.returnbtnphoto) {
            Object o = v.getTag();
            orderidimage = Integer.parseInt(String.valueOf(o));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {

                Intent in = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in, 111);
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {


            case 111:
                if (resultCode == Activity.RESULT_OK) {

                    final Uri selectedImage = data.getData();

                    if (selectedImage != null)
                        uploadFileAudio(orderidimage, userid, selectedImage);
                    else
                        Toast.makeText(getContext(), "Please Select Image", Toast.LENGTH_SHORT).show();


                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Log.d("dataa", "user cancelled");
                }


                break;
        }
    }

    private void uploadFileAudio(int orderidimage, Integer userid, @NonNull Uri selectedAudioUri) {

        File file = new File(getRealPathFromURI(selectedAudioUri));
        file.getName();

        RequestBody requestFile = RequestBody.create(MediaType.parse(Objects.requireNonNull(getContext().getContentResolver().getType(selectedAudioUri))), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("proof_image", file.getName(), requestFile);
        Log.d("dataaaudio", "body " + body);
        Log.d("dataaaudio", "request file " + requestFile);
        ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<ReturnPhotoModelClass> call = apiInterface.returnWithPhoto(orderidimage, userid, body);

        call.enqueue(new Callback<ReturnPhotoModelClass>() {
            @Override
            public void onResponse(Call<ReturnPhotoModelClass> call, Response<ReturnPhotoModelClass> response) {
                if (getActivity() != null) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        Toast.makeText(getContext(), response.body().message, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReturnPhotoModelClass> call, Throwable t) {
                Log.d("dataewq", "url " + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void removeQueueProductapi(final Integer useridd, Integer orderid) {
        ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        call = apiInterface.returnProduct(orderid, useridd);
        call.enqueue(new Callback<QueueReturnModelClass>() {
            @Override
            public void onResponse(Call<QueueReturnModelClass> call, Response<QueueReturnModelClass> response) {
                if (getActivity() != null) {
                    QueueReturnModelClass queueReturnModelClass = response.body();

                    Log.d("poipoipoi", "onResponse:queueReturnModelClass " + queueReturnModelClass.message + " " + queueReturnModelClass.errorCode
                            + "  " + queueReturnModelClass.status);
                    assert queueReturnModelClass != null;
                    if (queueReturnModelClass.status) {
                        Toast.makeText(getContext(), "" + queueReturnModelClass.message, Toast.LENGTH_SHORT).show();
                        getQueueapi(useridd);
                    } else {
                        Toast.makeText(getContext(), "" + queueReturnModelClass.message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<QueueReturnModelClass> call, Throwable t) {
                // PowerPreference.getDefaultFile().putBoolean("loggedin",false);
                //viewDialog.hideDialog();
                Toast.makeText(getContext(), "" + t, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getRealPathFromURI(@NonNull Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public void getQueueapii(Integer userid) {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showloader();
        }
        Log.d("tagg", "getQueueapi: userid" + userid);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<QueueListModelClass> call = apiInterface.getQueue(userid);
        call.enqueue(new Callback<QueueListModelClass>() {
            @Override
            public void onResponse(Call<QueueListModelClass> call, Response<QueueListModelClass> response) {
                if (getActivity() != null) {
                    QueueListModelClass queueListModelClass = response.body();

                    assert queueListModelClass != null;
                    if (queueListModelClass.status) {

                        RecyclerView.LayoutManager ee = new GridLayoutManager(getContext(), 1);
                        game_category_recycler.setLayoutManager(ee);
                        Log.d("tagg", "onResponse:homeGameListClass " + queueListModelClass.categories + " size " + queueListModelClass.categories.size());
                        gameCategoryRecycler = new QueueRecyclerAdapter(getContext(), queueListModelClass.categories, QueueFragment.this, QueueFragment.this);
                        game_category_recycler.setAdapter(gameCategoryRecycler);
                        game_category_recycler.setNestedScrollingEnabled(true);

                    }
                }
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).hideloader();
                }
            }


            @Override
            public void onFailure(Call<QueueListModelClass> call, Throwable t) {
                // PowerPreference.getDefaultFile().putBoolean("loggedin",false);
                //viewDialog.hideDialog();
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).hideloader();
                }
                Toast.makeText(getContext(), "" + t, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void showToast(String S) {

        se = S;


        handler.postDelayed(new Runnable() {
            public void run() {

                snackbar = TSnackbar.make(coo, se, TSnackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundResource(R.drawable.roundbordergreen);
                //snackbarView.setBackgroundColor(Color.parseColor("#0C910C"));

                TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();

            }
        }, 1000);
        handler.removeCallbacksAndMessages(null);
    }

}