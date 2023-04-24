package com.example.lilly.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lilly.ClassificationResultDialog;
import com.example.lilly.MainActivity;
import com.example.lilly.R;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment {
    private ImageView micIV;
    private EditText symptomsInput;
    public static final Integer RecordAudioRequestCode = 1;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private boolean isListening = false;
    private SpeechRecognizer speechRecognizer;
    private Intent speechIntent;
    AlertDialog.Builder alertSpeechDialog;
    AlertDialog alertDialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) getContext(), permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        }
        micIV = view.findViewById(R.id.micIV);
        symptomsInput = view.findViewById(R.id.symptomsInput);
        Button enterButton = view.findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classifyParagraph();
            }
        });

        checkPermission();
        speechRecognizer=speechRecognizer.createSpeechRecognizer(getContext());
        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                Log.e("status", "ready for speech");
            }

            @Override
            public void onBeginningOfSpeech() {
            }

            @Override
            public void onRmsChanged(float v) {
                Log.e("status", "rms change");
            }

            @Override
            public void onBufferReceived(byte[] bytes) {
                Log.e("status", "test");
            }

            @Override
            public void onEndOfSpeech() {
                Log.e("status", "end");
            }

            @Override
            public void onError(int i) {
                Log.e("status", "error");
            }

            @Override
            public void onResults(Bundle bundle) {
//                micIV.setImageResource(R.drawable.ic_baseline_mic_24);
//                ArrayList<String> arrayList = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//                symptomsInput.setText(arrayList.get(0));
//                alertDialog.dismiss();
//                Log.e("result", arrayList.get(0));
//            }
                ArrayList<String> results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (results != null) {
                    String recognizedText = results.get(0);
                    symptomsInput.setText(recognizedText);
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {
                Log.e("status", "pr");
            }

            @Override
            public void onEvent(int i, Bundle bundle) {
                Log.e("status", "event");
            }
        });
        micIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isListening) {
                    speechRecognizer.startListening(speechIntent);
                    isListening = true;
                    ViewGroup viewGroup = view.findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.alertcustom,viewGroup,false);
                    alertSpeechDialog = new AlertDialog.Builder(getContext());
                    alertSpeechDialog.setMessage("Listening...");
                    alertSpeechDialog.setView(dialogView);
                    alertDialog = alertSpeechDialog.create();
                    Button doneButton = dialogView.findViewById(R.id.done_button);
                    doneButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isListening) {
                                speechRecognizer.stopListening();
                                isListening = false;
                            }
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
        });
        return view;
    }

    private void classifyParagraph() {
        Map<String, Integer> wordCategories = readCsv();
        String input = symptomsInput.getText().toString().toLowerCase(); // convert the input to lowercase
        String[] keywords = wordCategories.keySet().toArray(new String[0]); // get the keywords from the wordCategories map
        StringBuilder result = new StringBuilder();
        boolean keywordFound = false; // keep track if at least one keyword was found
        result.append("Symptom: ").append(input);
        for (String keyword : keywords) {
            if (input.contains(keyword.toLowerCase())) { // check if the keyword is in the input
                keywordFound = true;
                int category = wordCategories.get(keyword); // get the category of the keyword
                String categoryName = getCategoryName(category);
                result.append("\nKeyword: ").append(keyword).append("\nCategory: ").append(categoryName).append("\n\n");
            }
        }
        if (!keywordFound) { // if no keyword was found, ask for more info
            result.append("\nClassification: General or no keyword found in the input. Please provide more information.");
        }
        String resultString = result.toString();
// Create a bundle and add the result to it
        Bundle bundle = new Bundle();
        bundle.putString("result", resultString);
// Create a new instance of the dialog box and set the bundle as an argument
        ClassificationResultDialog dialog = new ClassificationResultDialog();
        dialog.setArguments(bundle);
// Show the dialog box
        dialog.show(getFragmentManager(), "dialog");

    }

    private Map<String, Integer> readCsv() {
        Map<String, Integer> wordCategories = new HashMap<>();
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.medical);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            CSVReader reader = new CSVReader(new StringReader(new String(b)));
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                if (row.length >= 2 && !row[1].isEmpty()) { // check if the second column is not empty
                    wordCategories.put(row[0], Integer.parseInt(row[1]));
                }
            }
            reader.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return wordCategories;
    }

    private String getCategoryName(int categoryNumber) {
        switch (categoryNumber) {
            case 1:
                return "Immunology and Virology";
            case 2:
                return "Microbiology";
            case 3:
                return "Cardiovascular";
            case 4:
                return "Endocrine";
            case 5:
                return "Gastrointestinal";
            case 6:
                return "Hematology and Oncology";
            case 7:
                return "Musculoskeletal, Skin and Connective Tissue";
            case 8:
                return "Neurology and Special Senses";
            case 9:
                return "Psychiatry";
            case 10:
                return "Renal";
            case 11:
                return "Reproductive and Genetics";
            case 12:
                return "Respiratory";
            case 13:
                return "Pharmacology";
            case 14:
                return "Biochemistry";
            default:
                return "General";
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String requiredPermission = Manifest.permission.RECORD_AUDIO;

            // If the user previously denied this permission then show a message explaining why
            // this permission is needed
            if (getContext().checkCallingOrSelfPermission(requiredPermission) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{requiredPermission}, 101);
            }
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==RecordAudioRequestCode && grantResults.length>0){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(),"Permission Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}