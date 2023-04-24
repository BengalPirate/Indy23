//package com.example.lilly.models;
//
//import android.app.Activity;
//import android.inputmethodservice.Keyboard;
//import android.os.Bundle;
//import android.widget.EditText;
//
//import androidx.annotation.Nullable;
//
//import com.example.lilly.R;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class MedicalTextClassifier extends Activity {
//
//    private EditText symptomsInput;
//    private static final String EXCEL_FILE_PATH = "Medical Classification.xlsx";
//    //private static final String PARAGRAPH = symp;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        symptomsInput = findViewById(R.id.symptomsInput);
//        String paragraph = symptomsInput.toString();
//    }
//
//    public static void main(String[] args) throws IOException {
//        Map<String, Integer> medicalWords = readMedicalWordsFromExcel(EXCEL_FILE_PATH);
//
//        String[] sentences = PARAGRAPH.split("\\.\\s*");
//        for (String sentence : sentences) {
//            int category = classifySentence(sentence, medicalWords);
//            System.out.println("Sentence: " + sentence);
//            System.out.println("Category: " + category);
//        }
//    }
//
//    private static Map<String, Integer> readMedicalWordsFromExcel(String excelFilePath) throws IOException {
//        Map<String, Integer> medicalWords = new HashMap<>();
//        try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
//             Workbook workbook = new XSSFWorkbook(fis)) {
//
//            Sheet sheet = workbook.getSheetAt(0);
//            for (Row row : sheet) {
//                String word = row.getCell(0).getStringCellValue();
//                int category = (int) row.getCell(1).getNumericCellValue();
//                medicalWords.put(word.toLowerCase(), category);
//            }
//        }
//
//        return medicalWords;
//    }
//
//    private static int classifySentence(String sentence, Map<String, Integer> medicalWords) {
//        int generalCategory = 15;
//        Pattern pattern = Pattern.compile("\\b\\w+\\b");
//        Matcher matcher = pattern.matcher(sentence);
//
//        while (matcher.find()) {
//            String word = matcher.group().toLowerCase();
//            if (medicalWords.containsKey(word)) {
//                return medicalWords.get(word);
//            }
//        }
//
//        return generalCategory;
//    }
//}
