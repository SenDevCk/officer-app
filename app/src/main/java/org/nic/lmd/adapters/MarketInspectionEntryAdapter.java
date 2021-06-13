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
    boolean flag2;
    String subDiv;
    public MarketInspectionEntryAdapter(FragmentActivity fa,String flag, ArrayList<MarketInspectionTab> marketInspectionTabs,String subDiv,boolean flag2) {
        super(fa);
        this.marketInspectionTabs=marketInspectionTabs;
        this.flag=flag;
        this.flag2=flag2;
        this.subDiv=subDiv;
    }

    @Override
    public Fragment createFragment(int position) {
        Fragment fragment=null;
        switch (flag){
            case "0":
                fragment= new MarketInspectionEntryFragment().newInstance(marketInspectionTabs.get(position),position,subDiv,flag2);
                break ;
            case "1":
                fragment= new RevenueEntryFragment();
                break ;
            default:
                fragment= new MarketInspectionEntryFragment().newInstance(marketInspectionTabs.get(position),position,subDiv,flag2);
                break ;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return marketInspectionTabs.size();
    }
}