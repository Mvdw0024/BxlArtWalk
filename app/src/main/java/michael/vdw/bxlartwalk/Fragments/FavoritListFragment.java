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

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import michael.vdw.bxlartwalk.Models.ArtViewModel;
import michael.vdw.bxlartwalk.Models.CbArt;
import michael.vdw.bxlartwalk.R;
import michael.vdw.bxlartwalk.Utils.CbArtAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritListFragment extends Fragment {

    private CbArtAdapter adapter;

    //nodig voor de tab
    public static FavoritListFragment newInstance() {
        return new FavoritListFragment();
    }
    //search uitschrijven
    private SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
        //pas filteren na zoeken
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        //per letter filteren automatisch
        @Override
        public boolean onQueryTextChange(String newText) {
            adapter.getFilter().filter(newText);
            return false;
        }
    };

    public FavoritListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorit_list, container, false);
//        //noodzakelijk omp search in te voegen
//        setHasOptionsMenu(true);

        //verwijzing UI
        RecyclerView rvFavorit = rootView.findViewById(R.id.rv_favorit);
        //opvulling rv
        rvFavorit.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        //adapter, nodig om data om te zetten in iets visueel(in dit geval card)
        adapter = new CbArtAdapter(getActivity());
        rvFavorit.setAdapter(adapter);

        //TODO verwijzing naar viewModel, waar staat alle data
        //enkel data dat op de lijst gezed dient te worden

//        ArtViewModel model = new ViewModelProvider(this).get(ArtViewModel.class);
//        model.getCbRouteArt().observe(getViewLifecycleOwner(), new Observer<ArrayList<CbArt>>() {
//            @Override
//            public void onChanged(ArrayList<CbArt> cbArts) {
//                adapter.addItems(cbArts);
//                adapter.notifyDataSetChanged();
//            }
//        });
        return rootView;

  }
        @Override
        public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

            inflater.inflate(R.menu. search_menu,menu);

            SearchView searchView = (SearchView) menu.findItem(R.id.mi_search).getActionView();
            searchView.setOnQueryTextListener(searchListener);

            super.onCreateOptionsMenu(menu, inflater);
        }
}
