package com.example.kms.kiran_c.kms_swip;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

public class ma_pager_adapter extends FragmentPagerAdapter {
    public ma_pager_adapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                tab1 t1 = new tab1();
                return t1;
            case 1:
                tab2 t2 = new tab2();
                return t2;
            case 2:
                tab3 t3 = new tab3();
                return t3;
            case 3:
                tab4 t4 = new tab4();
                return t4;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }//set the number of tabs

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "Top stories";
            case 1:

                return "Members";
            case 2:

                return "Setting";
            case 3:

                return "DEMO";
        }
        return null;
    }



}
