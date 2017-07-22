package apprentit.myapplication;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;


public class ViewPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> pages=new ArrayList<>();
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Log.e("TAG","you entered getitem()");
            return pages.get(position);
        }
        @Override
        public int getCount() {
            return pages.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return pages.get(position).toString();
        }
        public void addPage(Fragment f)

        {
            Log.e("TAG","you entered addPage()");
            pages.add(f);
        }

    }
