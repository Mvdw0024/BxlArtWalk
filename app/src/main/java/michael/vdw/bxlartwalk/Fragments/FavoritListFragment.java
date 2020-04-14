package michael.vdw.bxlartwalk.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import michael.vdw.bxlartwalk.Models.ArtViewModel;
import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.Models.StreetArt;
import michael.vdw.bxlartwalk.R;
import michael.vdw.bxlartwalk.Utils.CbArtAdapter;
import michael.vdw.bxlartwalk.Utils.FavoritesAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritListFragment extends Fragment {

    private FavoritesAdapter adapter;


    //nodig voor de tab
    public static FavoritListFragment newInstance() {
        return new FavoritListFragment();
    }



    public FavoritListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favorit_list, container, false);
//        //noodzakelijk omp search in te voegen
//        setHasOptionsMenu(true);

        RecyclerView rvFavorit = rootView.findViewById(R.id.rv_favorit);
        rvFavorit.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        //adapter, nodig om data om te zetten in iets visueel(in dit geval card)
        adapter = new FavoritesAdapter(getActivity());
        //noodzakelijk omp search in te voegen
        setHasOptionsMenu(true);
        rvFavorit.setAdapter(adapter);

        ArtViewModel model = new ViewModelProvider(this).get(ArtViewModel.class);
        model.fetchAllFavoriteCbArtFromDatabase().observe(getViewLifecycleOwner(), new Observer<List<CbArt>>(){

            @Override
            public void onChanged(List<CbArt> cbFavorites) {
                adapter.addCbFavorites(cbFavorites);
                adapter.notifyDataSetChanged();
            }

        });
        model.fetchAllFavoriteStreetArtFromDatabase().observe(getViewLifecycleOwner(), new Observer<List<StreetArt>>() {
            @Override
            public void onChanged(List<StreetArt> streetArtFavorites) {
                adapter.addStreetArtFavorites(streetArtFavorites);
                adapter.notifyDataSetChanged();
            }
        });

        return rootView;

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu. search_menu,menu);

//        SearchView searchView = (SearchView) menu.findItem(R.id.mi_search).getActionView();
//        searchView.setOnQueryTextListener(searchListener);
        MenuItem item = menu.findItem(R.id.mi_search);
        SearchView sv = (SearchView) item.getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}
