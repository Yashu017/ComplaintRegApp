package my.app.complainregapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;


public class BottomNavActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, SecondFragment.OnFragmentInteractionListener
        ,FourthFragment.OnFragmentInteractionListener{
    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.item1);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().hide();






        sharedPref = getSharedPreferences("app", MODE_PRIVATE);
        editor = sharedPref.edit();




        // Checking whether user has logged in or not
        if (sharedPref.getBoolean("loginStatus", false) == false) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();

        }

        //Checking whether user has created profile or not
        else if (sharedPref.getBoolean("profileStatus", false) == false) {
            Intent i = new Intent(this, LogIn.class);
            editor.putBoolean("firstTime",true);
            editor.putBoolean("firstTimeMap",true);
            editor.commit();
            startActivity(i);
            finish();
        }
        else if(sharedPref.getBoolean("AdminEnter",false)==true)
        {
            Intent i = new Intent(this, AdminScreen.class);
            startActivity(i);
            finish();
        }





    }

    FirstFragment firstFragment = new FirstFragment();
    SecondFragment secondFragment = new SecondFragment();
    FourthFragment thirdFragment = new FourthFragment();



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, firstFragment).commit();
                return true;
            case R.id.item2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, secondFragment).commit();

                return true;
            case R.id.item3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, thirdFragment).commit();
                return true;



            default:
                //   getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).addToBackStack(null).replace(R.id.fragment_container, firstFragment).commit();
                return true;
        }

    }

    @Override
    public void onClicked() {

    }


}
