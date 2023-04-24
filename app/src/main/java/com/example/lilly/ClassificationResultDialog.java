package com.example.lilly;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ClassificationResultDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String result = getArguments().getString("result");

        // Create a new dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the dialog title
        builder.setTitle("Classification Result");

        // Set the dialog message
        builder.setMessage(result);

        // Add a button to dismiss the dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dismiss();
            }
        });

        // Return the built dialog
        return builder.create();
    }
}
