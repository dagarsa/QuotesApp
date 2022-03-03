package dialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import upv.dadm.quotesapp.R;

public class ConfirmationDialogFragment extends DialogFragment {

    public ConfirmationDialogFragment(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder alerta = new MaterialAlertDialogBuilder(requireContext());
        alerta.setMessage(getString(R.string.confirmationAll));
        alerta.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getParentFragmentManager().setFragmentResult("remove_all",new Bundle());
            }
        });
        alerta.setNegativeButton(getString(R.string.no), null);
        return alerta.create();
    }
}
