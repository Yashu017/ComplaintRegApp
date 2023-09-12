package my.app.complainregapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import my.app.complainregapp.model.User;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogIn extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SharedPreferences sharedPrefs;
    public static Retrofit retrofit;
    private SharedPreferences.Editor editor;
    private EditText name, phoneNumber;
    private Spinner branch , designation , admin;
    MaterialCheckBox agree;
    TextView agreed;
    String branchName,designationName,adminName;
    boolean ok;



    String token;
    String sendToken;
    private boolean editProfile;
    private boolean A = true;




    int temp = 2;


    private static final String TAG = "LogIn";



    RelativeLayout cd;
    TextView tx;
    ConstraintLayout cl;
    ProgressBar pg;
    Animation fadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        sharedPrefs = getSharedPreferences("app", MODE_PRIVATE);
        editor = sharedPrefs.edit();

        getUI();

        phoneNumber.setText(sharedPrefs.getString("phoneNumber", ""));
        phoneNumber.setEnabled(false);


        String text = "By clicking, I agree to terms of use and privacy policy.";
        SpannableString s = new SpannableString(text);

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://complaint-monitoring.flycricket.io/privacy.html"));
                startActivity(browserIntent);
            }
        };

        s.setSpan(clickableSpan2, 41, 55, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        agreed.setText(s);
        agreed.setMovementMethod(LinkMovementMethod.getInstance());


        agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                agree.setChecked(true);
                editor.putInt("agreed", 1);
                editor.apply();
            }
        });


        String[] admins=getResources().getStringArray(R.array.admin);
        ArrayAdapter adminAdap=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,admins){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        adminAdap.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        admin.setAdapter(adminAdap);
        admin.setOnItemSelectedListener(this);





        String[] branches = getResources().getStringArray(R.array.branch);
        ArrayAdapter branchAdap = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, branches){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        branchAdap.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        branch.setAdapter(branchAdap);
        branch.setOnItemSelectedListener(this);


        String[] designations = getResources().getStringArray(R.array.designation);
        ArrayAdapter designationAdap = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, designations){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        designationAdap.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        designation.setAdapter(designationAdap);
        designation.setOnItemSelectedListener(this);


        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pg.setVisibility(View.VISIBLE);




                if (!name.getText().toString().isEmpty() && !branchName.isEmpty() && !designationName.isEmpty()&&!adminName.isEmpty()
                        && agree.isChecked()){
                   // buttonActivated();
                    Intent intent = new Intent();
                    editProfile = false;
                    if (intent.hasExtra("editProfile")) {
                        editProfile = getIntent().getExtras().getBoolean("editProfile");
                        Log.e("status", "" + editProfile);
                    }

                    submitProfile(
                            name.getText().toString(),
                            phoneNumber.getText().toString(),
                            branchName,
                            designationName,
                           adminName,
                           editProfile

                    );


                } else {
                    Toast.makeText(LogIn.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }


            }
        });

       ActionBar actionBar = getSupportActionBar();
       getSupportActionBar().hide();
     getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3399fe")));





    }

    private void submitProfile(final String name, String phoneNum, final String branchName, final String designationName, final String adminName, final boolean editProfile) {



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
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("mobile", phoneNum);
        params.put("branch", branchName);
        params.put("designation", designationName);
        params.put("userType",adminName);



        Call<User> call=login.createAccount(token,params);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String error1;

                String res = "";
                String res1;
                try {
                    if (response.isSuccessful() && response.code() == 200) {
                        Log.e("err","errrjerr");
//                    res=new Gson().toJson(response.body());
//
                        if (response.body().getErrorCode() != null) {
                            error1 = response.body().getErrorCode();
                            Log.e("error", error1 + "");

                            if (error1.equals("0")) {
                                Toast.makeText(LogIn.this, "User already exist.Please try with another credentials", Toast.LENGTH_SHORT).show();
                                //failedButton();

                            }
                        } else {
                            ok = true;

                            // Toast.makeText(LogIn.this, res + "", Toast.LENGTH_LONG).show();
                            Log.e("response", res + "");
                            sendToken = response.body().gettoken();
                            Log.e("tk", sendToken);

                            if (!editProfile) {
                                editor.putString("token", sendToken);
                                editor.apply();
                            }

                            if (ok == true) {
                                editor.putString("name", name);
                                editor.putString("branch", branchName);
                                editor.putString("designation", designationName);
                                editor.putString("adminChoose",adminName);
                                editor.putBoolean("profileStatus", true);
                                editor.commit();
                                pg.setVisibility(View.GONE);
                                if (editProfile) {
                                    finish();
                                } else {


                                    Intent i = new Intent(LogIn.this, BottomNavActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    finish();


//
                                }
                            }
                        }
                    } else {
                        res = response.errorBody().string();
                        Log.e("res", res);
                         Toast.makeText(LogIn.this, "Enter", Toast.LENGTH_SHORT).show();
                        pg.setVisibility(View.GONE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LogIn.this, t.toString(), Toast.LENGTH_SHORT).show();
                Log.e("err",t.toString());

                pg.setVisibility(View.GONE);

                if (t instanceof SocketTimeoutException)
                {
                    Log.e("errq","connection timwout");
                    // "Connection Timeout";
                }
                else if (t instanceof IOException)
                {
                    Log.e("errqg"," timwout");
                    // "Timeout";
                }
                else
                {
                    //Call was cancelled by user
                    if(call.isCanceled())
                    {
                        System.out.println("Call was cancelled forcefully");
                    }
                    else
                    {
                        //Generic error handling
                        System.out.println("Network Error :: " +
                                t.getLocalizedMessage());
                    }
                }

            }
        });




    }

    void buttonActivated()
    {
        pg.setVisibility(View.VISIBLE);
        pg.setAnimation(fadeIn);
        tx.setText("Please Wait...");
        Toast.makeText(LogIn.this, "Activ", Toast.LENGTH_SHORT).show();
        tx.setAnimation(fadeIn);
    }
    void buttonFinished()
    {
        pg.setVisibility(View.GONE);
        pg.setAnimation(fadeIn);
        cd.setBackgroundResource(R.drawable.buttobgreen);
        Toast.makeText(LogIn.this, "Finiigh", Toast.LENGTH_SHORT).show();
        tx.setText("DONE");
        tx.setAnimation(fadeIn);
    }
    void failedButton()
    {
        pg.setVisibility(View.GONE);
        pg.setAnimation(fadeIn);
        cd.setBackgroundResource(R.drawable.buttobred);

        Toast.makeText(LogIn.this, "Fail", Toast.LENGTH_SHORT).show();
        tx.setText("Failed");
        tx.setAnimation(fadeIn);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cd.setBackgroundResource(R.drawable.buttob);
                tx.setText("Submit");
                tx.setAnimation(fadeIn);
            }
        },3000);

    }



    private void getUI() {
        name = findViewById(R.id.nameL);
        phoneNumber = findViewById(R.id.mobileL);
        branch= findViewById(R.id.branchL);
        designation = findViewById(R.id.designationL);
        cd=findViewById(R.id.cardL);
        cl=findViewById(R.id.consL);
        tx=findViewById(R.id.txtL);
        pg=findViewById(R.id.progressBar);
        agree=findViewById(R.id.checkbox);
        agreed=findViewById(R.id.PrivacyTitle);
        admin=findViewById(R.id.AdminChoose);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.branchL)
        {
             branchName=parent.getItemAtPosition(position).toString();

        }
        if(parent.getId()==R.id.designationL)
        {
            designationName=parent.getItemAtPosition(position).toString();
        }
        if(parent.getId()==R.id.AdminChoose)
        {
            adminName=parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
