package vn.thudlm.hometestjava.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.thudlm.hometestjava.R;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Integer> mFragmentIconList = new ArrayList<>();

    public ViewPagerAdapter(Context context, FragmentManager manager) {
        super(manager);
        mContext = context;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title, int icon) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        mFragmentIconList.add(icon);
    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.tab_title);
        tv.setText(mFragmentTitleList.get(position));
        ImageView img = v.findViewById(R.id.tab_icon);
        img.setImageResource(mFragmentIconList.get(position));
        if (position == 0) {
            tv.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            img.setColorFilter(mContext.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        }
        return v;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}