package my.app.complainregapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class complhistorymain extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LottieAnimationView animationView;
    private List<ComplainHistory> notificationList;
    Button back;




    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    String token4;
    Retrofit retrofit;


    TextView wait;
    Complain complain;

    private ArrayList<ComplainHistoryItem> arrayList = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complhistorymain);

        sharedPrefs = getSharedPreferences("app", Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().hide();




        animationView = (LottieAnimationView)findViewById(R.id.animation_view);
        recyclerView = (RecyclerView)findViewById(R.id.notificationRecycler);
        back=findViewById(R.id.buttonBackComp);
        wait = findViewById(R.id.wait);
                notificationList = new ArrayList<>();

        getNotification();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });


    }

    private void getNotification() {


        animationView.playAnimation();


        complain = (Complain) getIntent().getSerializableExtra("complaintAdmin");


        if (complain != null) {
            token4=complain.get_id();
            token4=token4.substring(5, token4.length());
            Log.e("Response", "" + token4);
        }


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okhttpbuilder = new OkHttpClient.Builder() .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(200, TimeUnit.SECONDS)
                .writeTimeout(200, TimeUnit.SECONDS).addInterceptor(logging)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://nicproject.herokuapp.com/")
                .client(okhttpbuilder)
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();
        for_login login = retrofit.create(for_login.class);
        Call<GetComplainHistory> call = login.getHistory(token4);
        call.enqueue(new Callback<GetComplainHistory>() {
            @Override
            public void onResponse(Call<GetComplainHistory> call, Response<GetComplainHistory> response) {
                if (response.isSuccessful()) {

                    Log.e("Response", "" + response.message());
                    if (response.body() != null) {
                        Log.e("Content", "" + response.body().getComplainHistory());
                    }
                    GetComplainHistory notification = response.body();

                    if (notification != null) {
                        arrayList = notification.getComplainHistory();
                    }


                    if (arrayList.size()>0) {

                        int length = arrayList.size();
                        Log.e("Length", "" + length);
                        for (ComplainHistoryItem item : arrayList) {

                           Integer action=item.getAction();

                            String to=item.getTo();
                            String from=item.getFrom();
                            String compID=item.get_id();
                            long time = item.getTime();
                            time=time+(5*60*60*1000)+(30*60*1000);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM d");
                            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh:mma");
                            Date date = new Date(time);
                            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                            simpleDateFormat1.setTimeZone(TimeZone.getTimeZone("UTC"));
                            String formattedTime = simpleDateFormat.format(date);
                            String formattedTime1 = simpleDateFormat1.format(date);
                            String actionText=" ";
                            Log.e("Action",action.toString());
                            if(action==0)
                            {
                                actionText="Complaint Disposed Off";
                            }
                            else if(action==1)
                            {
                                actionText="Complaint Forwarded ";
                            }

                            else if(action==2)
                            {
                                actionText="Complaint Sent for permission ";
                            }
                            else if(action==-1)
                            {
                                actionText="Complaint is sent for approval  ";
                            }
                            else if(action==3)
                            {
                                actionText="Complaint is approved by the person intended  ";
                            }


                            notificationList.add(new ComplainHistory( formattedTime+"." , "" +formattedTime1,"ID : "+compID ,"Action : "+actionText,
                                    ""+to,""+from));
                        }

                        ComplainAdpterHistory adapter = new ComplainAdpterHistory(getApplication(), notificationList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        wait.setVisibility(View.GONE);
                        animationView.pauseAnimation();
                        animationView.setVisibility(View.GONE);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    } else {
                        notificationList.add(new ComplainHistory("00:00","00:00","Action Yet To be Taken","N/A","N/A"
                                ,"N/A"));
                        ComplainAdpterHistory adapter = new ComplainAdpterHistory(getApplicationContext(), notificationList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

                        wait.setVisibility(View.GONE);
                        animationView.pauseAnimation();
                        animationView.setVisibility(View.GONE);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetComplainHistory> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Weak or No Internet", Toast.LENGTH_SHORT).show();
                Log.e("error", "" + t.getMessage());
            }
        });


    }
}
