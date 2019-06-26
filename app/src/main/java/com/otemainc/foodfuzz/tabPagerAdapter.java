package com.otemainc.foodfuzz;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class tabPagerAdapter  extends FragmentStatePagerAdapter {
    String[] tabArray = new String[]{"Food","Drinks","Restaurant","Cart"};
    Integer tabno = 4;

    public tabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabArray[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Food food = new Food();
                return  food;
            case 1:
                Drink drink = new Drink();
                return drink;
            case 2:
                Restaurant restaurant = new Restaurant();
                return restaurant;
            case 3:
                Cart cart = new Cart();
                return cart;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabno;
    }
}
