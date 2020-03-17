package michael.vdw.bxlartwalk.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import michael.vdw.bxlartwalk.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritListFragment extends Fragment {
    //TODO search uitschrijven omvat(OnQueryTextListener, onQueryTextSubmit, onQueryTextChange( bevat adapter dus aanmaken en gebruiken))
//    private SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
//        //pas filteren na zoeken
//        @Override
//        public boolean onQueryTextSubmit(String query) {
//            return false;
//        }
//        //per letter filteren automatisch
//        @Override
//        public boolean onQueryTextChange(String newText) {
//            adapter .getFilter().filter(newText);
//            return false;
//        }
//    };
//    private ... adapter;

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
        //TODO adapter, nodig om data om te zetten in iets visueel(in dit geval card)

        //TODO verwijzing naar viewModel, waar staat alle data
        return rootView;
    }
    //TODO menu aanmaken en dan onCreateOptionsMenu

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//
//        inflater.inflate(R.menu. ... , menu);
//
//        SearchView searchView = (SearchView) menu.findItem(R.id. ... ).getActionView();
//        searchView.setOnQueryTextListener(searchListener);
//
//        super.onCreateOptionsMenu(menu, inflater);

}
