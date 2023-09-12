package my.app.complainregapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;


import my.app.complainregapp.model.GetComplain;

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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private LottieAnimationView animationView;
    private List<Complain> notificationList;
    View rootView;
    private OnFragmentInteractionListener listener;
CharSequence search=" ";
    ComplainAdapter adapter = null;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    EditText searchView;
    String token4;
    Retrofit retrofit;


    TextView wait;

    private ArrayList<ComplainItem> arrayList = null;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_second, container, false);

        animationView = (LottieAnimationView) rootView.findViewById(R.id.animation_view);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.notificationRecycler);
        searchView=rootView.findViewById(R.id.searchSecond);
        wait = rootView.findViewById(R.id.wait);
        notificationList = new ArrayList<>();
        getNotification();
        searchView.setText(" ");

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



        return rootView;
    }

    private void getNotification() {
        animationView.playAnimation();
        sharedPrefs = getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();
        token4 = sharedPrefs.getString("token", "");

        OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpbuilder.addInterceptor(logging);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://nicproject.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();
        for_login login = retrofit.create(for_login.class);
        Call<GetComplain> call = login.getNoti(token4);
        call.enqueue(new Callback<GetComplain>() {
            @Override
            public void onResponse(Call<GetComplain> call, Response<GetComplain> response) {
                if (response.isSuccessful()) {

                    Log.e("Response", "" + response.message());
                    if (response.body() != null) {
                        Log.e("Content", "" + response.body().getComplain());
                    }
                    GetComplain notification = response.body();

                    if (notification != null) {
                        arrayList = notification.getComplain();
                    }


                    if (arrayList.size()>0) {
                        Collections.reverse(arrayList);
                        int length = arrayList.size();
                        Log.e("Length", "" + length);
                        for (ComplainItem item : arrayList) {

                            String title = item.getTitle();
                            String status=item.getStatus();
                            Log.e("status",item.getStatus() + item.getTitle());
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

                            notificationList.add(new Complain("Subject : "+ title, "Current Status : " +status ,"ID : "+compID,""+ formattedTime+"." , "" +formattedTime1,"" ,"",
                                    "","","","Complaint Type : "+item.getType()));
                        }

                        adapter = new ComplainAdapter(getContext(), notificationList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        wait.setVisibility(View.GONE);
                        animationView.pauseAnimation();
                        animationView.setVisibility(View.GONE);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    } else {
                        notificationList.add(new Complain("Subject : Not Available", "Current Status : "+"NO STATUS", "No complaint registered yet","" + "N/A","00:00","00:00","","",""
                        ,"","N/A"));
                       adapter = new ComplainAdapter(getContext(), notificationList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

                        wait.setVisibility(View.GONE);
                        animationView.pauseAnimation();
                        animationView.setVisibility(View.GONE);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetComplain> call, Throwable t) {
                Toast.makeText(getContext(), "Weak or No Internet", Toast.LENGTH_SHORT).show();
                Log.e("error", "" + t.getMessage());
            }
        });


    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        void onClicked();
    }
}
