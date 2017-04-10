package liuhongliang2017410.weektest410;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.TabPageIndicator;

import static liuhongliang2017410.weektest410.Urls.arr;

public class MainActivity extends FragmentActivity {
    private static final String[] CONTENT = new String[] { "头条", "社会", "国内", "娱乐", "体育", "军事","科技","财经","时尚","国际" };
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            findView();

        }

        private void findView() {
            TabPageIndicator indicator= (TabPageIndicator) findViewById(R.id.indicator);
            ViewPager viewPager= (ViewPager) findViewById(R.id.main_viewpager);
            viewPager.setAdapter(new WYNewsAdapter(getSupportFragmentManager()));
            indicator.setViewPager(viewPager);
        }
        class WYNewsAdapter extends FragmentPagerAdapter {

            public WYNewsAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                return TestFragment.newInstance(arr[position % CONTENT.length]);
            }

            @Override
            public int getCount() {
                return CONTENT.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return CONTENT[position % CONTENT.length].toUpperCase();
            }
        }
}
