package com.example.practica5_materialdesign;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0: return new Fragment1();
            case 1: return new Fragment2();
            case 2: return new Fragment3();
            default: return new Fragment1();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0: return "Pestaña 1";
            case 1: return "Pestaña 2";
            case 2: return "Pestaña 3";
            default: return "";
        }
    }
}
