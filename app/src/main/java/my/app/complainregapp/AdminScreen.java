package my.app.complainregapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;


import my.app.complainregapp.model.GetComplaintAdmin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LottieAnimationView animationView;
    private List<Admin> notificationList;
    View rootView;
    CharSequence search=" ";
    AdminAdapter adapter = null;
    private SecondFragment.OnFragmentInteractionListener listener;

    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    String BranchAsToken,DesignationAdToken;
    Retrofit retrofit;

    Button logout;
    TextView wait;
    EditText searchView;

    private ArrayList<ComplaintItemAdmin> arrayList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);



        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        recyclerView = (RecyclerView) findViewById(R.id.notificationRecycler);
        searchView=findViewById(R.id.searchSecond1);

        wait=findViewById(R.id.wait);
        logout=findViewById(R.id.logoutAdmin);
        sharedPrefs = getSharedPreferences("app", Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().hide();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#673AB7")));


        notificationList = new ArrayList<>();


         logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(AdminScreen.this, BottomNavActivity.class);
                 editor.putString("BranchAdmin","");
                 editor.putString("Designation","");
                 editor.putBoolean("AdminEnter",false);
                 editor.commit();
                 startActivity(i);
                 finish();
             }
         });

         if(sharedPrefs.getBoolean("AdminEnter",false)==true)
         {
             notificationList = new ArrayList<>();
         }
        if(sharedPrefs.getInt("action",0)==4)
        {
            notificationList = new ArrayList<>();
            getNotification();


        }


       searchView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {





               searchView.addTextChangedListener(new TextWatcher() {
                   @Override
                   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                   }

                   @Override
                   public void onTextChanged(CharSequence s, int start, int before, int count) {

                       adapter.getFilter().filter(s);
                       search=s;



                   }

                   @Override
                   public void afterTextChanged(Editable s) {

                   }
               });

           }

       });
        searchView.setText(" ");





    }

    private void getNotification() {
        animationView.setVisibility(View.VISIBLE);

        animationView.playAnimation();

        BranchAsToken= sharedPrefs.getString("BranchAdmin", "");
        DesignationAdToken=sharedPrefs.getString("Designation","");



        OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpbuilder.addInterceptor(logging);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://nicproject.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();
        for_login login = retrofit.create(for_login.class);
        Call<GetComplaintAdmin> call = login.getLoc(BranchAsToken,DesignationAdToken);
        call.enqueue(new Callback<GetComplaintAdmin>() {
            @Override
            public void onResponse(Call<GetComplaintAdmin> call, Response<GetComplaintAdmin> response) {
                if (response.isSuccessful()) {

                    Log.e("Response", "" + response.message());
                    if (response.body() != null) {
                        Log.e("Content", "" + response.body().getComplaintAdmin());
                    }
                    GetComplaintAdmin complaints = response.body();

                    if (complaints != null) {
                        arrayList = complaints.getComplaintAdmin();
                    }


                    if (arrayList.size()>0) {
                        Collections.reverse(arrayList);
                        int length = arrayList.size();
                        Log.e("Length", "" + length);
                        for (ComplaintItemAdmin item : arrayList) {

                            String status = item.getStatus();
                            String compID = item.get_id();
                            String title=item.getTitle();

                            long time = item.getTime();
                            time = time + (5 * 60 * 60 * 1000) + (30 * 60 * 1000);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM d");
                            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh:mma");
                            Date date = new Date(time);
                            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                            simpleDateFormat1.setTimeZone(TimeZone.getTimeZone("UTC"));
                            String formattedTime = simpleDateFormat.format(date);
                            String formattedTime1 = simpleDateFormat1.format(date);
                            notificationList.add(new Admin("Subject : "+ title, "Current Status : " + status, "ID : " + compID, "" + formattedTime + ".", "" + formattedTime1, ""+item.getDescription(), ""+item.getLocation(),
                                    ""+item.getTo(), ""+item.getFrom(), ""+item.getComplaint(),"Complaint Type : "+item.getType()));
                        }

                      adapter = new AdminAdapter(getApplicationContext(), notificationList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        wait.setVisibility(View.GONE);
                        animationView.pauseAnimation();
                        animationView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    } else if(arrayList.size()==0)  {

                        notificationList.add(new Admin("No Complaint to show", "Current Status : "+"NO STATUS", "N/A","" + "N/A","00:00","00:00","","",""
                                ,"","N/A"));
                         adapter = new AdminAdapter(getApplicationContext(), notificationList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

                        wait.setVisibility(View.GONE);
                        animationView.pauseAnimation();
                        animationView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetComplaintAdmin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Weak or No Internet", Toast.LENGTH_SHORT).show();
                Log.e("error", "" + t.getMessage());
            }
        });




    }

   // @Override
//    protected void onResume() {
//        super.onResume();
//        if(sharedPrefs.getBoolean("AdminEnter",false)==true)
//        {
//
//        }
//
//    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sharedPrefs.getInt("action",0)!=4)
        {
            notificationList = new ArrayList<>();
            recyclerView.setVisibility(View.GONE);

            getNotification();


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}

