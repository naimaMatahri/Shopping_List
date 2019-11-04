package com.example.list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;
    PageAdapter(FragmentManager fm, int numOfTabs) {

        super(fm);
        this.numberOfTabs = numOfTabs;
    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ShoppingListFragment();
            case 1:
                return new ShoppingCartFragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return numberOfTabs;
    }


}
