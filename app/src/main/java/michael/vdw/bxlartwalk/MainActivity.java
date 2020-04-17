package michael.vdw.bxlartwalk;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import michael.vdw.bxlartwalk.Fragments.AboutUsFragment;
import michael.vdw.bxlartwalk.Fragments.ArtListFragment;
import michael.vdw.bxlartwalk.Fragments.FavoritListFragment;
import michael.vdw.bxlartwalk.Fragments.MapFragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private TabLayout.BaseOnTabSelectedListener tabListener = new TabLayout.OnTabSelectedListener() {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    MapFragment mapFragment = MapFragment.newInstance();
                    navigateToFragment(mapFragment);
                    break;
                case 1:
                    ArtListFragment artListFragment = ArtListFragment.newInstance();
                    navigateToFragment(artListFragment);
                    break;
                case 2:
                    FavoritListFragment favoritListFragment = FavoritListFragment.newInstance();
                    navigateToFragment(favoritListFragment);
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //om tab bar te kunnen gebruiken
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(tabListener);

    }

    public void navigateToFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_fragment, f)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tabLayout.getTabAt(1).select();
        tabLayout.getTabAt(0).select();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutUs:
                AboutUsFragment af = new AboutUsFragment();
                navigateToFragment(af);
        }

        return super.onOptionsItemSelected(item);
    }
}

