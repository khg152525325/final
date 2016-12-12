package com.example.lin.afinal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class PagerAdapter extends FragmentStatePagerAdapter {
    int nNumOfTabs;
    public PagerAdapter(FragmentManager fm, int nNumOfTabs)
    {
        super(fm);
        this.nNumOfTabs=nNumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                TabFragment1 tab1=new TabFragment1();
                return tab1;
            case 1:
                TabFragment2 tab2=new TabFragment2();
                return tab2;
        }
        return null;
    }

    @Override
    public int getCount() {
        return nNumOfTabs;
    }
}
