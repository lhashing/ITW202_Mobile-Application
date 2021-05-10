package edu.gcit.todo_15;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    int mnumberOftab;
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mnumberOftab = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
//        switch (position){
//            case 0: return new TabFragment();
//            case 1: return new TabFragment2();
//            case 3: return new TabFragment3();
//        }
//        return null;
        if (position == 0) {
            TabFragment tab1 = new TabFragment();
            return tab1;
        }
        if (position == 1) {
            TabFragment2 tab2 = new TabFragment2();
            return tab2;
        } else {
            TabFragment3 tab3 = new TabFragment3();
            return tab3;
        }
    }

    @Override
    public int getCount() {
        return mnumberOftab;
    }
}
