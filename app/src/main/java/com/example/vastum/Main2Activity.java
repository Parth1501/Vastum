package com.example.vastum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main2Activity extends AppCompatActivity {
    BottomNavigationView bnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar tb=(Toolbar) findViewById(R.id.inc1);
        setSupportActionBar(tb);
        bnv=findViewById(R.id.nav);

        AppBarConfiguration abc=new AppBarConfiguration.Builder(
                R.id.home,R.id.sell,R.id.redeem,R.id.profile
        ).build();
        NavController navc= Navigation.findNavController(this,R.id.fragment2);
        NavigationUI.setupActionBarWithNavController(this,navc,abc);
        NavigationUI.setupWithNavController(bnv,navc);
    }
}
