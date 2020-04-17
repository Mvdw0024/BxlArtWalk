package michael.vdw.bxlartwalk.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class deleteDialoog extends DialogFragment {

    /*Wordt voorlopig niet gebruikt , bedoeling is dat deze geimplementeerd wordt wanneer je het vuilbakje aanklikt in de favoritelist.
    --Begin 2.0 versie :-) */

    private DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int i) {
            Toast.makeText(getActivity(),"Thanks for deleting", Toast.LENGTH_LONG).show();       }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to delete this favorit?");
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(android.R.string.ok, okListener);
        return builder.create();
    }
}
