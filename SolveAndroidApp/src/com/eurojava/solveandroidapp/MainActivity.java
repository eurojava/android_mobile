package com.eurojava.solveandroidapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends Activity {
    
    // stałe używane przy przywracaniu aplikacji
    private static final String TOTAL_BILL = "TOTAL_BILL";
    private static final String CURRENT_TAX = "CURRENT_TAX";
    private static final String BILL_WITHOUT_TAX = "BILL_WITHOUT_TAX";
    
    private double billBeforeTax;
    private double taxAmount;
    private double finalBill;
    
    EditText billBeforeTaxET;
    EditText taxAmountET;
    EditText finalBillET;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (savedInstanceState == null) {
            // po uruchomieniu
            storeDataToStream();
            storeDataToMedia();
            billBeforeTax = 0.0;
            taxAmount = .23;
            finalBill = 0.0;
        } else {
            // przy wznowieniu
            billBeforeTax = savedInstanceState.getDouble(BILL_WITHOUT_TAX);
            taxAmount = savedInstanceState.getDouble(CURRENT_TAX);
            finalBill = savedInstanceState.getDouble(TOTAL_BILL);
        }

        billBeforeTaxET = (EditText) findViewById(R.id.billEditText);
        taxAmountET = (EditText) findViewById(R.id.taxEditText);
        finalBillET = (EditText) findViewById(R.id.finalEditText);

        billBeforeTaxET.addTextChangedListener(billBeforeTaxListener);
    }
    
    private TextWatcher billBeforeTaxListener = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable arg0) {
        }
        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {
        }
        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {
            try {
                billBeforeTax = Double.parseDouble(arg0.toString());
            } catch (NumberFormatException e) {
                billBeforeTax = 0.0;
            }
            updateTaxAndFinalBill();
        }
    };
    
    private void storeDataToStream() {
        FileOutputStream fos;
        try {
            fos = openFileOutput("test.txt", Context.MODE_PRIVATE);
            fos.write(TOTAL_BILL.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();  
        }
    }
    
    private void storeDataToMedia() {
      FileWriter fw;
      try {
          fw = new FileWriter("/sdcard/test.txt");
          fw.append("test test test");
          fw.flush();
          fw.close();                 
      } catch (IOException e) {
          e.printStackTrace();  
        } 
          
    }

    private void updateTaxAndFinalBill() {
        double taxAmount = Double.parseDouble(taxAmountET.getText().toString());
        double finalBill = billBeforeTax + (billBeforeTax * taxAmount);
        // konwersja - dokładność do 2 miejsc
        finalBillET.setText(String.format("%.02f", finalBill));
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(TOTAL_BILL, finalBill);
        outState.putDouble(CURRENT_TAX, taxAmount);
        outState.putDouble(BILL_WITHOUT_TAX, billBeforeTax);
    }
}
