package my.app.complainregapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import my.app.complainregapp.model.UserComplaint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    SharedPreferences sharedPrefs;
    String branchForward;
    SharedPreferences.Editor editor;
    View rootView;
    TextView userName,branchText;
    RelativeLayout launch, sendButt,send,loginBig, rlCustom;
    ProgressBar sendPB,progressBar;
    EditText desc,location,branch,title,Currentdate,getPass;
    Spinner forwardTo,complainType;
    String token1,complaintTyped;
    Retrofit retrofit;
    TextView tx,adminText,branchpass,desigpass;
    Animation fadeIn,fadeOut;
    ImageView menu,logout;
    private boolean REQUEST_OK=true;
    private int RESULT_OK=1;
    private String SendToken;
    String complainId;
    Button yespass,cancelpass;


    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==100&&resultCode==RESULT_OK)
//        {
//            Place place=Autocomplete.getPlaceFromIntent(data);
//            location.setText(place.getAddress());
//            editor.putString("location",location.getText().toString());
//            editor.commit();
//        }
//        else if(resultCode==AutocompleteActivity.RESULT_ERROR)
//        {
//            Status status=Autocomplete.getStatusFromIntent(data);
//            Toast.makeText(getContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();
//            Log.e("PLacesError",status.getStatusMessage());
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPrefs = getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();

        rootView = inflater.inflate(R.layout.fragment_first, container, false);

        userName=rootView.findViewById(R.id.userNameFirst);
        branchText=rootView.findViewById(R.id.notificationBellfirst);
        launch=rootView.findViewById(R.id.cardLFirstYes);
        loginBig=rootView.findViewById(R.id.typeTemp);
        rlCustom = new RelativeLayout(getActivity());
        title=rootView.findViewById(R.id.titleComp);
        send=rootView.findViewById(R.id.typeTempfirst);
        sendButt=rootView.findViewById(R.id.cardLFirst);
        tx=rootView.findViewById(R.id.txtLfirst);
        sendPB=rootView.findViewById(R.id.progressBarfirst);
        desc=rootView.findViewById(R.id.Complainfirst);
        location=rootView.findViewById(R.id.addressfirst);
        menu=rootView.findViewById(R.id.menu);
       // adminBig=rootView.findViewById(R.id.adminFirst);
        branch=rootView.findViewById(R.id.branchMainfirst);
        forwardTo=rootView.findViewById(R.id.forwardTofirst);
        logout=rootView.findViewById(R.id.logout);
        adminText=rootView.findViewById(R.id.AdminTExt);
        Currentdate=rootView.findViewById(R.id.timeMain);
      //  adminLogin=rootView.findViewById(R.id.AdminControl);
        complainType=rootView.findViewById(R.id.complintTypeSpinner);
        fadeOut= AnimationUtils.loadAnimation(getContext(),R.anim.fade_out);
        fadeIn= AnimationUtils.loadAnimation(getContext(),R.anim.fade_in);
        branch.setText(sharedPrefs.getString("branch", ""));
        branch.setEnabled(false);

        token1 = sharedPrefs.getString("token", "");
        userName.setText(sharedPrefs.getString("name", ""));
        branchText.setText(sharedPrefs.getString("branch",""));
        editor.putString("BranchAdmin",branchText.getText().toString());
        editor.commit();

        Log.e("admin",sharedPrefs.getString("adminChoose",""));
        if(!sharedPrefs.getString("adminChoose","").equals("Admin"))
        {
            logout.setVisibility(View.VISIBLE);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logoutToMain();
                }
            });
            adminText.setText("Non-Admin");
            menu.setVisibility(View.GONE);
           // adminBig.setVisibility(View.GONE);
          //  adminLogin.setVisibility(View.GONE);

        }
        Currentdate.setEnabled(false);



//        adminLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.putInt("action",4);
//                editor.commit();
//
//
//                showDialog();
//            }
//        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), menu);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().equals("Admin Login"))
                        {
                            editor.putInt("action",4);
                            editor.commit();
                            showDialog();

                        }
                        else {
                           logoutToMain();
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu

            }
        });

        if(sharedPrefs.getBoolean("AdminEnter",false)==true)
        {
            Intent i = new Intent(getContext(), AdminScreen.class);
            startActivity(i);
            getActivity().finish();
        }

        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDateTime();
                editor.putInt("launchClicked", 1);
                editor.commit();
                launch.setAnimation(fadeOut);
                launch.setVisibility(View.GONE);
                send.setAnimation(fadeIn);
                send.setVisibility(View.VISIBLE);

            }
        });


        String[] complainTypes=getResources().getStringArray(R.array.complaint_type);
        ArrayAdapter complTypeAdap=new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,complainTypes){
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
        complTypeAdap.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        complainType.setAdapter(complTypeAdap);


        complainType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getId() == R.id.complintTypeSpinner) {
                    complaintTyped = parent.getItemAtPosition(position).toString();
                    editor.putString("ComplaintType", complaintTyped);
                    editor.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





            String[] forwardto = getResources().getStringArray(R.array.forwardTo);
            ArrayAdapter forwardToAdap = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, forwardto){
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
            forwardToAdap.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            forwardTo.setAdapter(forwardToAdap);

            forwardTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (parent.getId() == R.id.forwardTofirst) {
                        branchForward = parent.getItemAtPosition(position).toString();
                        editor.putString("brancFor", branchForward);
                        editor.commit();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

//            Places.initialize(getContext(), "AIzaSyCwZVJlf2mrglfb5LEhwKEc0lz6k4Ke5jw");
//            location.setFocusable(false);
//            location.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
//                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(getContext());
//                    startActivityForResult(intent, 100);
//                }
//
//            });



        sendButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (!desc.getText().toString().isEmpty() && !location.getText().toString().isEmpty() && !branchForward.isEmpty()&&!complaintTyped.isEmpty()
                        && !branch.getText().toString().isEmpty()&&!title.getText().toString().isEmpty()){

                    buttonActivated();

                    submitComplaint(
                           desc.getText().toString(),
                            location.getText().toString(),
                            branchForward,
                            complaintTyped,
                            branch.getText().toString(),
                            title.getText().toString()
                            );


                } else {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }


            }
        });


        return rootView;
    }

    private void logoutToMain() {

        Intent i = new Intent(getContext(), MainActivity.class);
        editor.putBoolean("loginStatus",false);
        editor.putBoolean("passCorrect",false);
        editor.commit();
        startActivity(i);
        getActivity().finish();

    }

    private void getDateTime() {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("EEEE, MMMM d, hh:mma", Locale.getDefault());
        String formattedDate = df.format(c);

        Currentdate.setText(formattedDate+" ");

    }

    private void showDialog() {

        AlertDialog.Builder  alert;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
        {
            alert=new AlertDialog.Builder(getContext(),android.R.style.Theme_Material_Dialog_Alert);

        }
        else {
            alert = new AlertDialog.Builder(getContext());
        }
        LayoutInflater layoutInflater=getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.admindialogpass,null);

        desigpass=view.findViewById(R.id.designationText);
        branchpass=view.findViewById(R.id.branchText);
        yespass=view.findViewById(R.id.yesPass);
        cancelpass=view.findViewById(R.id.cancelPass);
        progressBar=view.findViewById(R.id.progress_pass);
        getPass=view.findViewById(R.id.enterpass);
        if(sharedPrefs.getBoolean("passCorrect",false)==true)
        {
            getPass.setEnabled(false);
            getPass.setVisibility(View.GONE);
            getPass.setText("AEOUI");

        }
        alert.setView(view);
        alert.setCancelable(false);
        branchpass.setText("Branch : " + sharedPrefs.getString("branch",""));
        desigpass.setText("Designation : "+sharedPrefs.getString("designation",""));



        yespass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yespass.setVisibility(View.GONE);
                cancelpass.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                String password= getPass.getText().toString();
                String BranchSele=sharedPrefs.getString("branch","");
                String DesSele=sharedPrefs.getString("designation","");




                editor.putString("BranchAdmin",BranchSele);
                editor.putString("Designation",DesSele);
                editor.commit();
                Boolean pa=false;

                if(password.equals("AEOUI"))
                {

                    pa=true;
                }

                if(!password.isEmpty()&&!BranchSele.isEmpty()&& pa&&!DesSele.isEmpty())
                {
                    editor.putBoolean("passCorrect",true);
                    editor.commit();
                    Intent intent=new Intent(getContext(),AdminScreen.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                    editor.putBoolean("AdminEnter",true);
                    editor.commit();
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    yespass.setVisibility(View.VISIBLE);
                    cancelpass.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Please fill all fields or Please Check your password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog dialog=alert.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        cancelpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    private void submitComplaint(String desc, String location, String branchForward,String complaintTyped, String toString2,String title) {


        editor.putInt("launchClicked", 0);
        editor.commit();




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
        params.put("complaint", desc);
        params.put("location", location);
        params.put("to", branchForward);
        params.put("from", toString2);
        params.put("type",complaintTyped);
        params.put("title",title);
        Call<UserComplaint> call = login.userNotify(token1, params);
        call.enqueue(new Callback<UserComplaint>() {

            @Override
            public void onResponse(Call<UserComplaint> call, Response<UserComplaint> response) {
                String error;
                if (response.isSuccessful() && response.code() == 200) {
                    if (response.body().getErrorCode() != null) {
                        error = response.body().getErrorCode();
                        if (error.equals("2")) {
                            Toast.makeText(getContext(), "User not found.", Toast.LENGTH_SHORT).show();
                            failedButton();
                        }
                        else
                        {
                            buttonFinished();



                        }

                    }
                    SendToken = response.body().getToken();
                    complainId=response.body().getComplaint_id();
                    Toast.makeText(getContext(), "Success!!Your Complaint Has been launched", Toast.LENGTH_SHORT).show();
                    buttonFinished();
                    new android.app.AlertDialog.Builder(getContext())
                            .setTitle("Your Complaint has been launched")
                            .setMessage("Your complaint id is : "+complainId)
                            .setIcon(R.drawable.ic_check_black_24dp)
                            .setCancelable(false)
                            .setNeutralButton("OK", null)
                            .show();

                }
            }

            @Override
            public void onFailure(Call<UserComplaint> call, Throwable t) {
                Toast.makeText(getContext(), "Failed" + " : Weak or No Internet", Toast.LENGTH_SHORT).show();
                Log.e("SUBMIT COMP", "" + t.getMessage());
                failedButton();
            }
        });
    }
    void buttonActivated()
    {
        sendPB.setVisibility(View.VISIBLE);
        sendPB.setAnimation(fadeIn);
        tx.setText("Please Wait...");
        tx.setAnimation(fadeIn);
    }
    void buttonFinished()
    {
        sendPB.setVisibility(View.GONE);
        sendPB.setAnimation(fadeIn);
        sendButt.setBackgroundResource(R.drawable.buttobgreen);
        tx.setText("DONE");
        tx.setAnimation(fadeIn);
        if(sharedPrefs.getInt("launchClicked",0)==0)
        {

            send.setAnimation(fadeOut);
            send.setVisibility(View.GONE);
            launch.setAnimation(fadeIn);
            launch.setVisibility(View.VISIBLE);
            sendButt.setBackgroundResource(R.drawable.buttob);
            tx.setText("Submit");
            location.setText("");
            desc.setText("");
            title.setText("");

        }


    }
    void failedButton()
    {
        sendPB.setVisibility(View.GONE);
        sendPB.setAnimation(fadeIn);
        sendButt.setBackgroundResource(R.drawable.buttobred);

        tx.setText("Failed");
        tx.setAnimation(fadeIn);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendButt.setBackgroundResource(R.drawable.buttob);
                tx.setText("Submit");
                tx.setAnimation(fadeIn);
            }
        },3000);

    }


}


