package org.nic.lmd.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import org.nic.lmd.entities.MarketInspectionTab;
import org.nic.lmd.fragments.MarketInspectionEntryFragment;
import org.nic.lmd.fragments.RevenueEntryFragment;


import java.util.ArrayList;

public class MarketInspectionEntryAdapter extends FragmentStateAdapter {
    ArrayList<MarketInspectionTab> marketInspectionTabs;
    String flag;
    public MarketInspectionEntryAdapter(FragmentActivity fa,String flag, ArrayList<MarketInspectionTab> marketInspectionTabs) {
        super(fa);
        this.marketInspectionTabs=marketInspectionTabs;
        this.flag=flag;
    }

    @Override
    public Fragment createFragment(int position) {
        Fragment fragment=null;
        switch (flag){
            case "0":
                fragment= new MarketInspectionEntryFragment().newInstance(marketInspectionTabs.get(position),position);
                break ;
            case "1":
                fragment= new RevenueEntryFragment();
                break ;
            default:
                fragment= new MarketInspectionEntryFragment().newInstance(marketInspectionTabs.get(position),position);
                break ;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return marketInspectionTabs.size();
    }
}