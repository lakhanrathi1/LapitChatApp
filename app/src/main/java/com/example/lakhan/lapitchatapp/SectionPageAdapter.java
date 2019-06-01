package com.example.lakhan.lapitchatapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by lakhan on 24/8/17.
 */

class SectionPageAdapter extends FragmentPagerAdapter{
    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                request requestFragment = new request();
                return requestFragment;

            case 1:
                Chat chatFragment = new Chat();
                return chatFragment;

            case 2:
                Friends friendFragment = new Friends();
                return friendFragment;

            default:
                return null;

        }


        //return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

public CharSequence getPageTitle(int position){


    switch (position) {

        case 0:
            // request requestFragment = new request();
            return "Requests";

        case 1:
            // Chat chatFragment = new Chat();
            return "Chats";

        case 2:
//            Friends friendFragment = new Friends();
            return "Friends";

        default:
            return null;


    }
    }

}
