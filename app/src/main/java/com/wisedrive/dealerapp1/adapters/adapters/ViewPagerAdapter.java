package com.wisedrive.dealerapp1.adapters.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.wisedrive.dealerapp1.DealerProfile;
import com.wisedrive.dealerapp1.fragments.HomeFragment;
import com.wisedrive.dealerapp1.fragments.CustomerFragment;
import com.wisedrive.dealerapp1.fragments.PackageFragment;
import com.wisedrive.dealerapp1.fragments.ProfileFragment;

public class ViewPagerAdapter extends FragmentStateAdapter
{
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 1:
                return new PackageFragment();
            case 2:
                return new CustomerFragment();
//            case 3:
//                return new DealerProfile();
            case 0:
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
