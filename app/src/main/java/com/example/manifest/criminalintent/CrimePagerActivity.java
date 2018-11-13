package com.example.manifest.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    public static final String EXTRA_CRIME_ID = "com.example.manifest.criminalintent.crime_id";

    private ViewPager crimePager;
    private List<Crime> crimes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        crimes = CrimeLab.get(this).getCrimes();

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        crimePager = (ViewPager) findViewById(R.id.crime_view_pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        crimePager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = crimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return crimes.size();
            }
        });
        // отображение элемента, на котором был клик
        for (int i = 0; i < crimes.size(); i++) {
            if (crimeId.equals(crimes.get(i).getId())) {
                crimePager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }
}
