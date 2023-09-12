package my.app.complainregapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import my.app.complainregapp.model.PostAction;

import java.util.ArrayList;
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

public class WatchActivity extends AppCompatActivity {

    private TextView id,desc,address,status,to ,from,date,remark;
    Admin admin;
    private Button dispose,forward,permisson,submitDialog,cancelDialog,backButton,submitReport,cancelReport;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    Spinner Branch;
    EditText report;
    String BranchName,ReportString=" ";
    String complID;
    Retrofit retrofit;
    String branchTo,branchFrom,target;
    Integer action=4;
    ProgressBar pbReport;
    private ArrayList<RemarkItem> arrayList = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);

        sharedPrefs = getSharedPreferences("app",Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();


        id=findViewById(R.id.ComplIDAddmin);
        desc=findViewById(R.id.ComplainDescripAdmin);
        backButton=findViewById(R.id.buttonBackWatch);
        address=findViewById(R.id.complainLocationAdmin);
        status=findViewById(R.id.statusAdmin);
        to=findViewById(R.id.complainTOAdmin);
        remark=findViewById(R.id.remarkAdmin);
        from=findViewById(R.id.complainFromAdmin);
        date=findViewById(R.id.ComplainDateAdmin);
        dispose=findViewById(R.id.Dispose);
        forward=findViewById(R.id.transfer);
        permisson=findViewById(R.id.permission);
        editor.putBoolean("disposed",false);
        editor.putBoolean("forwarded",false);
        editor.putBoolean("permission",false);
        editor.putString("reportString","");
        editor.commit();



        admin = (Admin) getIntent().getSerializableExtra("complaintAdmin");
        Log.e("admin",admin.toString());
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().hide();

        if(admin.getLocation().isEmpty()) {
           dispose.setVisibility(View.GONE);
           forward.setVisibility(View.GONE);
           permisson.setVisibility(View.GONE);

        }
        getTalkDetail();
//        if(status.getText().toString().equals("Current Status : " + "active"))
//        {
//            dispose.setVisibility(View.GONE);
//        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WatchActivity.this,AdminScreen.class);
                startActivity(intent);
                finish();
            }
        });

        if(!admin.getLocation().isEmpty()) {
            getRemark();
        }
        else
        {
            remark.setVisibility(View.GONE);
        }

        dispose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action=0;
                target=" ";
                editor.putInt("action",0);
                editor.putBoolean("disposed",true);
                editor.putBoolean("forwarded",false);
                editor.putBoolean("permission",false);
                editor.commit();

                action=sharedPrefs.getInt("action",0);
                branchTo=" ";
                forward.setVisibility(View.GONE);
                permisson.setVisibility(View.GONE);
                dispose.setVisibility(View.GONE);


                new AlertDialog.Builder(WatchActivity.this)
                        .setTitle("Dispose Off")
                        .setMessage("This complaint has been disposed off. Thank You")
                        .setIcon(R.drawable.ic_done_black_24dp)
                        .setNeutralButton("OK", null)
                        .show();



                DialogReport();


            }
        });



        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                action=0;
                target=" ";
                target="all";
                showDialog();
                editor.putInt("action",1);
                editor.putBoolean("forwarded",true);
                editor.putBoolean("disposed",false);
                editor.putBoolean("permission",false);
                editor.commit();
                action=sharedPrefs.getInt("action",0);
                forward.setVisibility(View.GONE);
                permisson.setVisibility(View.GONE);
                dispose.setVisibility(View.GONE);
                DialogReport();






            }
        });


        permisson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action=0;
                target=" ";

                showDialogTarget();
                Log.e("targer",target);
                branchTo=admin.getTo();
                editor.putInt("action",2);
                editor.putBoolean("permission",true);
                editor.putBoolean("forwarded",false);
                editor.putBoolean("disposed",false);
                editor.commit();
                action=sharedPrefs.getInt("action",0);
                forward.setVisibility(View.GONE);
                permisson.setVisibility(View.GONE);
                dispose.setVisibility(View.GONE);
                DialogReport();

            }
        });





    }

    private void getRemark() {
        //Toast.makeText(this,"yes",Toast.LENGTH_LONG).show();

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
        Call<GetRemark> call = login.getRemark(complID);
        call.enqueue(new Callback<GetRemark>() {
            @Override
            public void onResponse(Call<GetRemark> call, Response<GetRemark> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    GetRemark remark1 = response.body();

                    Log.e("Response1", "" + response.message());
                    if (response.body() != null) {
                        Log.e("Content", "" + response.body().getRemark());
                    }


                    if (remark1 != null) {
                        arrayList = remark1.getRemark();

                    }



                    if (arrayList.size()>0) {
                        int length = arrayList.size();
                        Log.e("Length", "" + length);
                        for (RemarkItem item : arrayList) {
                            String remarkeds=item.getRemark();
                            remark.setText("  Remark   : "+ remarkeds);
                        }

                        }


                    }
                else
                {
                    Log.e("err",response.code()+"");
                }
                }


            @Override
            public void onFailure(Call<GetRemark> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Weak or No Internet", Toast.LENGTH_SHORT).show();
                Log.e("error", "" + t.getMessage());
            }
        });





    }

    private void DialogReport() {
        ReportString=" ";
        androidx.appcompat.app.AlertDialog.Builder  alert;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
        {
            alert=new androidx.appcompat.app.AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog_Alert);

        }
        else {
            alert = new androidx.appcompat.app.AlertDialog.Builder(this);
        }
        LayoutInflater layoutInflater=getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.report_dialog,null);
        report=view.findViewById(R.id.enterpassreport);
        submitReport=view.findViewById(R.id.yesPassreport);
        cancelReport=view.findViewById(R.id.cancelPassReport);
        pbReport=view.findViewById(R.id.progress_passReprot);
        alert.setView(view);
        alert.setCancelable(false);

        androidx.appcompat.app.AlertDialog dialog=alert.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ReportString.isEmpty())
                {
                    ReportString=report.getText().toString();
                      }
                dialog.dismiss();
                editor.putString("reportString",ReportString);
                editor.commit();

                submitReport.setVisibility(View.GONE);
                cancelReport.setVisibility(View.GONE);
                pbReport.setVisibility(View.VISIBLE);
                postAction();



            }
        });
        cancelReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                pbReport.setVisibility(View.GONE);
                submitReport.setVisibility(View.VISIBLE);
                cancelReport.setVisibility(View.VISIBLE);

            }
        });








    }

    private void showDialogTarget() {

        androidx.appcompat.app.AlertDialog.Builder  alert;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
        {
            alert=new androidx.appcompat.app.AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog_Alert);

        }
        else {
            alert = new androidx.appcompat.app.AlertDialog.Builder(this);
        }
        LayoutInflater layoutInflater=getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.adminaction,null);


        Branch=view.findViewById(R.id.branchPassw);
        submitDialog=view.findViewById(R.id.yesPass);
        cancelDialog=view.findViewById(R.id.cancelPass);
        alert.setView(view);
        alert.setCancelable(false);
        String[] branches = new String[0];
        if(permisson.isPressed()) {

            branches = getResources().getStringArray(R.array.designation);

        }

        ArrayAdapter desAdap = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, branches);
        desAdap.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Branch.setAdapter(desAdap);
        Branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getId()==R.id.branchPassw)
                {
                    BranchName=parent.getItemAtPosition(position).toString();
                    target=BranchName;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        androidx.appcompat.app.AlertDialog dialog=alert.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        submitDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDialog.setVisibility(View.GONE);
                cancelDialog.setVisibility(View.GONE);

                String DesUpdate=target;

                 if(sharedPrefs.getBoolean("permission",false)==true) {
                    editor.putString("DesUpdate",DesUpdate);
                    editor.commit();

                    Toast.makeText(WatchActivity.this, "Designation Selected : " +DesUpdate, Toast.LENGTH_LONG).show();
                }



                if(!DesUpdate.isEmpty())
                {
                    postAction();
                    dialog.dismiss();

                }
                else {

                    submitDialog.setVisibility(View.VISIBLE);
                    cancelDialog.setVisibility(View.VISIBLE);
                    Toast.makeText(WatchActivity.this, "Please fill the field", Toast.LENGTH_SHORT).show();
                }
            }
        });



        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dispose.setVisibility(View.VISIBLE);
                permisson.setVisibility(View.VISIBLE);
                forward.setVisibility(View.VISIBLE);

            }
        });









    }

    private void showDialog() {

        androidx.appcompat.app.AlertDialog.Builder  alert;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)
        {
            alert=new androidx.appcompat.app.AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog_Alert);

        }
        else {
            alert = new androidx.appcompat.app.AlertDialog.Builder(this);
        }
        LayoutInflater layoutInflater=getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.adminaction,null);


        Branch=view.findViewById(R.id.branchPassw);
        submitDialog=view.findViewById(R.id.yesPass);
        cancelDialog=view.findViewById(R.id.cancelPass);
        alert.setView(view);
        alert.setCancelable(false);
        String[] branches = new String[0];
        if(forward.isPressed()) {
            branches = getResources().getStringArray(R.array.forwardTo);
        }

        ArrayAdapter branchAdap = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, branches);
        branchAdap.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Branch.setAdapter(branchAdap);
        Branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getId()==R.id.branchPassw)
                {
                    BranchName=parent.getItemAtPosition(position).toString();
                    branchTo=BranchName;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        androidx.appcompat.app.AlertDialog dialog=alert.create();
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

        submitDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDialog.setVisibility(View.GONE);
                cancelDialog.setVisibility(View.GONE);

                String BranchUpdate=BranchName;
                if(sharedPrefs.getBoolean("forwarded",false)==true) {
                    editor.putString("BranchUpdate", BranchUpdate);
                    editor.commit();

                    Toast.makeText(WatchActivity.this, "Branch Selected : " + BranchUpdate, Toast.LENGTH_LONG).show();
                }
                else if(sharedPrefs.getBoolean("permission",false)==true) {
                    editor.putString("BranchUpdate", BranchUpdate);
                    editor.commit();

                    Toast.makeText(WatchActivity.this, "Designation Selected : " + BranchUpdate, Toast.LENGTH_LONG).show();
                }



                if(!BranchUpdate.isEmpty())
                {
                    postAction();
                    dialog.dismiss();

                }
                else {

                    submitDialog.setVisibility(View.VISIBLE);
                    cancelDialog.setVisibility(View.VISIBLE);
                    Toast.makeText(WatchActivity.this, "Please fill the field", Toast.LENGTH_SHORT).show();
                }
            }
        });



        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dispose.setVisibility(View.VISIBLE);
                permisson.setVisibility(View.VISIBLE);
                forward.setVisibility(View.VISIBLE);

            }
        });









    }

    private void postAction() {





        final AlertDialog.Builder builderA = new AlertDialog.Builder(WatchActivity.this);
                builderA.setTitle("Please Wait")
                .setMessage("We are updating")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false);

        AlertDialog alert = builderA.create();
                alert.show();


        ;

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
       params.put("action",sharedPrefs.getInt("action",0));
       params.put("from",branchFrom);
       params.put("to",branchTo);
       params.put("target",target);
       params.put("remark"," " + sharedPrefs.getString("reportString",""));
        Log.e("action", String.valueOf(sharedPrefs.getInt("action",0)));
        Log.e("from",branchFrom);
        Log.e("action",branchTo);
        Log.e("id",complID);

       Log.e("remark",sharedPrefs.getString("reportString","") + "    hello ");

        Call<PostAction> post=login.postAction(complID,params);
        post.enqueue(new Callback<PostAction>() {
            @Override
            public void onResponse(Call<PostAction> call, Response<PostAction> response) {

                if (response.body() != null) {
                    String message=response.body().getMessage();
                    Log.e("msg",message);

                }


                if (response.isSuccessful() && response.code() == 200) {

                    Toast.makeText(WatchActivity.this, "Complaint Updated", Toast.LENGTH_SHORT).show();
                    alert.dismiss();


                }
                else
                {
                    Toast.makeText(WatchActivity.this, "Error : "+response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("Error : " , String.valueOf(response.code()));
                    alert.dismiss();
                }

            }

            @Override
            public void onFailure(Call<PostAction> call, Throwable t) {

                Toast.makeText(WatchActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                Log.e("SUBMIT COMP", "" + t.getMessage());
                alert.dismiss();

            }
        });




    }

    private void getTalkDetail() {




        if (!admin.toString().equals(" ")) {
           // Toast.makeText(this,"yes7"+ admin.getFrom(),Toast.LENGTH_SHORT).show();
            id.setText( admin.get_id());
            desc.setText("Description :  "+ admin.getComplaint());
           address.setText("Location    :  "+admin.getLocation());
           status.setText("Curr "+admin.getStatus().substring(8,admin.getStatus().length()));
           to.setText("Sent To :  "+admin.getTo());
           from.setText("Sent From   :  "+admin.getFrom());
           branchFrom=admin.getFrom().toString();
           if(!admin.getLocation().isEmpty()) {
             complID = id.getText().toString().substring(5, id.getText().toString().length());
           }
            date.setText("Date&Time : "+ admin.getTime()+"\n"+admin.getTime1());



        }
        else
        {
            Toast.makeText(this,"Nothing to show : No complaints associated",Toast.LENGTH_SHORT).show();
        }

    }


}
