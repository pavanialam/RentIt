package apprentit.myapplication;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    private static Toolbar toolbar;
    private static ViewPager viewPager;
    private static TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        addPages(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);//setting tab over viewpager
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //tabLayout.setOnTabSelectedListener(tabSelectedListener(viewPager));
        //Implementing tab selected listener over tablayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("TAG","position is changed");
                viewPager.setCurrentItem(tab.getPosition());//setting current selected item over viewpager
                switch (tab.getPosition()) {
                    case 0:
                        Log.e("TAG", "TAB1");
                        break;
                    case 1:
                        Log.e("TAG","TAB2");
                        break;
                    case 2:
                        Log.e("TAG","TAB3");
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        }
    // Add pages
            private void addPages(ViewPager viewPager)
            {
                ViewPagerAdapter myPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
                myPagerAdapter.addPage(new Tab1());
                myPagerAdapter.addPage(new Tab2());
                myPagerAdapter.addPage(new Tab3());
                // set adapter to pager
                viewPager.setAdapter(myPagerAdapter);
            }

           // private TabLayout.OnTabSelectedListener tabSelectedListener(final  ViewPager pager)
           // {
             //   return  new TabLayout.OnTabSelectedListener() {
               //     @Override
                 //   public void onTabSelected(TabLayout.Tab tab) {
                   //     pager.setCurrentItem(tab.getPosition());
                    //}
                    //@Override
                    //public void onTabUnselected(TabLayout.Tab tab) {
                  //  }
                    //@Override
                  //  public void onTabReselected(TabLayout.Tab tab) {
                   // }
                //};
            //}

        }