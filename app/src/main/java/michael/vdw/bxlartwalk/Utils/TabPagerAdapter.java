package michael.vdw.bxlartwalk.Utils;

import android.media.Image;
import android.text.Layout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;

import michael.vdw.bxlartwalk.Fragments.ArtListFragment;
import michael.vdw.bxlartwalk.Fragments.FavoritListFragment;
import michael.vdw.bxlartwalk.Fragments.MapFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments = {MapFragment.newInstance(), ArtListFragment.newInstance(), FavoritListFragment.newInstance()};

    public TabPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        return fragments[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "Map";
        if (position == 1)
            return "List";
        return "Favorit";
    }
}
