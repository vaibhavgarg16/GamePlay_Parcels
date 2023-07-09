package com.game.playparcels.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.game.playparcels.Activity.MainActivity;
import com.game.playparcels.ModelClasses.SignupModelClass;
import com.game.playparcels.R;
import com.game.playparcels.SevicesApi.ApiClient;
import com.game.playparcels.SevicesApi.ApiInterface;
import com.game.playparcels.ViewDialog;
import com.google.gson.Gson;
import com.preference.PowerPreference;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class RegisterTwoActivity extends AppCompatActivity {

    TextView spinnertxtview;
    Button Register;
    String password,email;
    EditText forename,surname,postcode,door,street,town;
    String TAG= "RegisterTwoActivity";
   // ViewDialog viewDialog;

    ListView lissl;

    RelativeLayout progressBar;
    ListView lv;
    String sptext ="Main Console";
    LinearLayout consolelayout;

    TSnackbar snackbar;
    CoordinatorLayout coo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);
        spinnertxtview=findViewById(R.id.spinnertxt);

        //viewDialog = new ViewDialog(this);
        progressBar=findViewById(R.id.progressBar);

         lv = (ListView)findViewById(R.id.listView1);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
      /*  Toast.makeText(this, ""+email+" "+password, Toast.LENGTH_SHORT).show();*/

        Register=findViewById(R.id.Register);
        forename=findViewById(R.id.forename);
        surname=findViewById(R.id.surname);
        postcode=findViewById(R.id.postode);
        door=findViewById(R.id.door);
        street=findViewById(R.id.street);
        town=findViewById(R.id.town);
        consolelayout=findViewById(R.id.consolelayout);
        coo = findViewById(R.id.coo);



        ArrayList<String> members = new ArrayList<String>();
        members.add("PLAYSTATION 4");
        members.add("XBOX");
        members.add("NINTENDO SWITCH");
        members.add("XBOX SERIES X");
        members.add("PlayStation 5");


        CustomList  ad = new CustomList (this,members);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                sptext=parent.getItemAtPosition(position).toString();
               // Toast.makeText(RegisterTwoActivity.this, "Clicked "+parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

            }
        });





        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  forenametxt,surnametxt,postcodetxt,doortxt,streettxt,towntxt,spinnertxt;

                forenametxt=forename.getText().toString().trim();
                surnametxt=surname.getText().toString().trim();
                postcodetxt=postcode.getText().toString().trim();
                doortxt=door.getText().toString().trim();
                streettxt=street.getText().toString().trim();
                towntxt =town.getText().toString().trim();
                spinnertxt=sptext;//spinnertxtview.getText().toString().trim();

                Log.d(TAG, "onClick:sssss "+sptext);


                if (forenametxt.equals("") || surnametxt.equals("") || postcodetxt.equals("") || doortxt.equals("") || streettxt.equals("") || towntxt.equals(""))
                {
                  //  Toast.makeText(RegisterTwoActivity.this, "All Fields Are Reuired", Toast.LENGTH_SHORT).show();
                    ShowToast("All Fields Are Reuired");
                    return;
                }
                if (spinnertxt.equalsIgnoreCase("Main Console")){
                  //  Toast.makeText(RegisterTwoActivity.this, "Choose Main Console", Toast.LENGTH_SHORT).show();
                    ShowToast("Choose Main Console");
                    return;
                }
               registerapi(email,password,forenametxt,surnametxt,postcodetxt,doortxt,streettxt,towntxt,spinnertxt);

            }
        });

        spinnertxtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             // showPopup(v);
                if (consolelayout.getVisibility()== View.VISIBLE) {
                    consolelayout.setVisibility(View.GONE);
                }else {
                    consolelayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void registerapi(String email, String password, String forenametxt, String surnametxt, String postcodetxt, String doortxt, String streettxt, String towntxt, String spinnertxt) {


              int index = email.indexOf('@');
        String username = email.substring(0,index);
        Log.d(TAG, "loginapi email password: "+ email +" "+ password);



        String base64email= null,base64password= null,base64username = null,base64tforenametxt= null,base64surnametxt= null,base64postcodetxt= null
        ,base64doortxt= null,base64streettxt= null,base64towntxt= null,base64spinnertxt= null;
        try {

            //username
            byte[] data0 = username.getBytes("UTF-8");

            base64username = Base64.encodeToString(data0, Base64.DEFAULT);

            Log.i("Base 64 ", base64username);

            //email
            byte[] data = email.getBytes("UTF-8");

            base64email = Base64.encodeToString(data, Base64.DEFAULT);

            Log.i("Base 64 ", base64email);

            //password
            byte[] data1 = password.getBytes("UTF-8");

            base64password = Base64.encodeToString(data1, Base64.DEFAULT);

            Log.i("Base 64 ", base64password);



            //forenametxt
            byte[]  data2 = forenametxt.getBytes("UTF-8");

            base64tforenametxt = Base64.encodeToString(data2, Base64.DEFAULT);

            Log.i("Base 64 ", base64tforenametxt);



            //surnametxt
            byte[]  data3 = surnametxt.getBytes("UTF-8");

            base64surnametxt = Base64.encodeToString(data3, Base64.DEFAULT);

            Log.i("Base 64 ", base64surnametxt);


            //postcodetxt
            byte[]  data4= postcodetxt.getBytes("UTF-8");

            base64postcodetxt = Base64.encodeToString(data4, Base64.DEFAULT);

            Log.i("Base 64 ", base64postcodetxt);



            //doortxt
            byte[] data5 = doortxt.getBytes("UTF-8");

            base64doortxt = Base64.encodeToString(data5, Base64.DEFAULT);

            Log.i("Base 64 ", base64doortxt);




            //streettxt
            byte[] data6 = streettxt.getBytes("UTF-8");

            base64streettxt = Base64.encodeToString(data6, Base64.DEFAULT);

            Log.i("Base 64 ", base64streettxt);




            //towntxt
            byte[] data7 = towntxt.getBytes("UTF-8");

            base64towntxt = Base64.encodeToString(data7, Base64.DEFAULT);

            Log.i("Base 64 ", base64towntxt);



            //spinnertxt
            byte[] data8 = spinnertxt.getBytes("UTF-8");

            base64spinnertxt = Base64.encodeToString(data8, Base64.DEFAULT);

            Log.i("Base 64 ", base64spinnertxt);


        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        }


/*

        byte[] usernameE = Base64.encode(username.getBytes(), Base64.DEFAULT);
        String usernameEn = usernameE.toString();

        byte[] emailE = Base64.encode(email.getBytes(), Base64.DEFAULT);
        String emailEn = emailE.toString();

        byte[] passwordE = Base64.encode(password.getBytes(), Base64.DEFAULT);
        String passwordEn = passwordE.toString();

        byte[] surnametxtE = Base64.encode(surnametxt.getBytes(), Base64.DEFAULT);
        String surnametxtEn = surnametxtE.toString();

        byte[] forenametxtE = Base64.encode(forenametxt.getBytes(), Base64.DEFAULT);
        String forenametxtEn = forenametxtE.toString();

        byte[] postcodetxtE = Base64.encode(postcodetxt.getBytes(), Base64.DEFAULT);
        String postcodetxtEn = postcodetxtE.toString();

        byte[] doortxtE = Base64.encode(doortxt.getBytes(), Base64.DEFAULT);
        String doortxtEn = doortxtE.toString();

        byte[] streettxtE = Base64.encode(streettxt.getBytes(), Base64.DEFAULT);
        String streettxtEn = streettxtE.toString();

        byte[] towntxtE = Base64.encode(towntxt.getBytes(), Base64.DEFAULT);
        String towntxtEn = towntxtE.toString();

        byte[] spinnertxtE = Base64.encode(spinnertxt.getBytes(), Base64.DEFAULT);
        String spinnertxtEn = spinnertxtE.toString();
*/



        progressBar.setVisibility(View.VISIBLE);
       // viewDialog.showDialog();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<SignupModelClass> call = apiInterface.signup(base64username,base64email, base64password,base64password,base64surnametxt,base64tforenametxt,base64postcodetxt,
                base64doortxt,base64streettxt,base64towntxt,base64spinnertxt);
        call.enqueue(new Callback<SignupModelClass>() {
            @Override
            public void onResponse(Call<SignupModelClass> call, Response<SignupModelClass> response) {

                progressBar.setVisibility(View.GONE);
                //viewDialog.hideDialog();
                SignupModelClass signupModelClass = response.body();
                Log.d(TAG, "onResponse: "+new Gson().toJson(response.body()));

                if (signupModelClass != null && signupModelClass.status.equalsIgnoreCase("true")){
                    PowerPreference.getDefaultFile().putBoolean("loggedin",true);
                    PowerPreference.getDefaultFile().putInt("userid",signupModelClass.userdetail.iD);
                    PowerPreference.getDefaultFile().putString("displayname",signupModelClass.userdetail.displayName);
                    PowerPreference.getDefaultFile().putString("email",signupModelClass.userdetail.email);
                    PowerPreference.getDefaultFile().putString("username",signupModelClass.userdetail.userLogin);
                    PowerPreference.getDefaultFile().putString("doornumber",signupModelClass.userExtraDetail.doorNumber);
                    PowerPreference.getDefaultFile().putString("forename",signupModelClass.userExtraDetail.forename);
                    PowerPreference.getDefaultFile().putString("mainconsole",signupModelClass.userExtraDetail.mainConsole);
                    PowerPreference.getDefaultFile().putString("postcode",signupModelClass.userExtraDetail.postcode);
                    PowerPreference.getDefaultFile().putString("streetaddress",signupModelClass.userExtraDetail.streetAddress);
                    PowerPreference.getDefaultFile().putString("towncity",signupModelClass.userExtraDetail.townCity);
                    PowerPreference.getDefaultFile().putString("surname",signupModelClass.userExtraDetail.surname);
                    PowerPreference.getDefaultFile().putString("subscription","no subscription");

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("frag","profile");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }



               /* if (SignupModelClass.status.equalsIgnoreCase("true")){
                    PowerPreference.getDefaultFile().putBoolean("loggedin",true);
                    PowerPreference.getDefaultFile().putInt("userid",SignupModelClass.loginUserDetails.get(0).userid);
                    PowerPreference.getDefaultFile().putString("role",SignupModelClass.loginUserDetails.get(0).role);

                    startActivity(new Intent(getApplicationContext(),MainActivity.class));



                      Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                }*/
               /* assert signupModelClass != null;
                Toast.makeText(RegisterTwoActivity.this, "Msg"+signupModelClass.message, Toast.LENGTH_SHORT).show();*/

            }

            @Override
            public void onFailure(Call<SignupModelClass> call, Throwable t) {
                PowerPreference.getDefaultFile().putBoolean("loggedin",false);
                progressBar.setVisibility(View.GONE);
                //viewDialog.hideDialog();
              //  Toast.makeText(getApplicationContext(), ""+t, Toast.LENGTH_SHORT).show();
                ShowToast(t.getMessage());


            }
        });

    }


    private void showPopup(View v) {
        Context wrapper = new ContextThemeWrapper(this, R.style.YOURSTYLE);
        PopupMenu popup = new PopupMenu(wrapper, v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.playstation:
                      //  Toast.makeText(getApplicationContext(), "PLAYSTATION 4", Toast.LENGTH_SHORT).show();
                        spinnertxtview.setText("PLAYSTATION 4");
                        return true;
                    case R.id.xbox:
                     ///   Toast.makeText(getApplicationContext(), "XBOX", Toast.LENGTH_SHORT).show();
                        spinnertxtview.setText("XBOX");
                        return true;

                    case R.id.ninswitch:
                       /// Toast.makeText(getApplicationContext(), "NINTENDO SWITCH", Toast.LENGTH_SHORT).show();
                        spinnertxtview.setText("NINTENDO SWITCH");
                        return true;

                    case R.id.xbox_series_x:
                      //  Toast.makeText(getApplicationContext(), "XBOX SERIES X", Toast.LENGTH_SHORT).show();
                        spinnertxtview.setText("XBOX SERIES X");
                        return true;

                    case R.id.playstation_5:
                      //  Toast.makeText(getApplicationContext(), "PlayStation 5", Toast.LENGTH_SHORT).show();
                        spinnertxtview.setText("PlayStation 5");
                        return true;

                    default:
                        return false;
                }
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        super.onBackPressed();
    }

    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;
        ArrayList<String> members = new ArrayList<String>();
        public CustomList(Activity context,
                          ArrayList<String> web) {
            super(context, R.layout.list_single, web);
            this.context = context;
            this.members = web;
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.list_single, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
            txtTitle.setText(members.get(position));
            return rowView;
        }
    }

    public void ShowToast(String s){
        snackbar = TSnackbar.make(coo, s, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundResource(R.drawable.roundborderyellow);

        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.BLACK);
        snackbar.show();
    }
}
