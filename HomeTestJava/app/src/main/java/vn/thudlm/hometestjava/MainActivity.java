package vn.thudlm.hometestjava;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vn.thudlm.hometestjava.adapters.ViewPagerAdapter;
import vn.thudlm.hometestjava.fragments.BlankFragment;
import vn.thudlm.hometestjava.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPagerAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private boolean isDoubleBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViewPager();
    }

    //region Viewpager
    private void setupViewPager() {
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);

        adapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Trang chủ", R.drawable.ic_home);
        adapter.addFragment(new BlankFragment(), "Danh mục", R.drawable.ic_category);
        adapter.addFragment(new BlankFragment(), "Tìm kiếm", R.drawable.ic_search);
        adapter.addFragment(new BlankFragment(), "Thông báo", R.drawable.ic_notification);
        adapter.addFragment(new BlankFragment(), "Cá nhân", R.drawable.ic_user);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        View tabView = tab.getCustomView();
                        changeTabState(tabView, true);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        View tabView = tab.getCustomView();
                        changeTabState(tabView, false);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );

    }

    private void changeTabState(View view, boolean isSelected){
        TextView tab_label = view.findViewById(R.id.tab_title);
        ImageView tab_icon = view.findViewById(R.id.tab_icon);
        if(isSelected) {
            tab_label.setTextColor(getResources().getColor(R.color.colorPrimary));
            tab_icon.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        }else {
            tab_label.setTextColor(getResources().getColor(android.R.color.darker_gray));
            tab_icon.setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }
    }
    //endregion


    @Override
    public void onBackPressed() {
        if (isDoubleBack) {
            super.onBackPressed();
            return;
        }

        this.isDoubleBack = true;
        Toast.makeText(this, "Nhấn back lần nữa để thoát ứng dụng", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                isDoubleBack=false;
            }
        }, 2000);
    }
}
