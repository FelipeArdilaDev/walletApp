package com.example.bancoprueba.historial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.bancoprueba.HistorialFragment.DepositoFragment;
import com.example.bancoprueba.HistorialFragment.RetiroFragment;
import com.example.bancoprueba.R;
import com.google.android.material.tabs.TabLayout;

public class HistorialActivity extends AppCompatActivity {
    private TabLayout tabLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        tabLayout1 = findViewById(R.id.tb1);
        ViewPager2 viewPager2 = findViewById(R.id.vp1);

        viewPager2.setAdapter(new AdaptadorFragmen(getSupportFragmentManager(),getLifecycle()));
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout1.selectTab(tabLayout1.getTabAt(position));

            }
        });
    }

    class AdaptadorFragmen extends FragmentStateAdapter {

        public AdaptadorFragmen(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:return new RetiroFragment();
                case 1:return new DepositoFragment();
                default:return new Fragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}