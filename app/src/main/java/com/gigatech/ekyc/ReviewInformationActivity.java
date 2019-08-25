package com.gigatech.ekyc;

import android.app.DatePickerDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.gigatech.ekyc.model.NidData;
import com.gigatech.ekyc.model.NidResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReviewInformationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText nidid, dobid, applicationsnameenglishid,
            applicationsnamebanglaid, fathersnameid, mothersnameid, presentaddressid;
    //DatePickerDialog datePickerDialog;
    Button nextButton, backStepThree_ButtonId;
    //final Calendar myCalendar = Calendar.getInstance();

    NidData nidData = new NidData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide_addi__information);

        //Spinner spinner = findViewById(R.id.spinnerid);
        nextButton = findViewById(R.id.nextButtonId);
        backStepThree_ButtonId = findViewById(R.id.backStepThree_ButtonId);

        dobid=findViewById(R.id.dobid);
        nidid=findViewById(R.id.nidid);
        applicationsnameenglishid=findViewById(R.id.applicationsnameenglishid);
        applicationsnamebanglaid=findViewById(R.id.applicationsnamebanglaid);
        fathersnameid=findViewById(R.id.fathersnameid);
        mothersnameid=findViewById(R.id.mothersnameid);
        presentaddressid=findViewById(R.id.presentaddressid);

        setViews();


//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.numbers, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);
//
//
//        final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
//            // TODO Auto-generated method stub
//            myCalendar.set(Calendar.YEAR, year);
//            myCalendar.set(Calendar.MONTH, monthOfYear);
//            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//            updateLabel();
//        };
//        date1.setOnClickListener(v -> new DatePickerDialog(ReviewInformationActivity.this,android.R.style.Theme_Holo_Dialog_MinWidth, date, myCalendar
//                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        nextButton.setOnClickListener(v -> {

            nidData.setCustomerNameEng(applicationsnameenglishid.getText().toString());
            nidData.setCustomerNameBen(applicationsnamebanglaid.getText().toString());
            nidData.setMotherName(mothersnameid.getText().toString());
            nidData.setFatherName(fathersnameid.getText().toString());
            nidData.setAddress(presentaddressid.getText().toString());

            Intent intent = new Intent(getApplicationContext(),ProvideAdditionalInformation.class);
            intent.putExtra("NidData", nidData);
            startActivity(intent);
        });

        backStepThree_ButtonId.setOnClickListener(view -> finish());

    }


    void setViews(){

        if(getIntent().getParcelableExtra("NidResponse") != null){

            NidResponse nidResponse = getIntent().getParcelableExtra("NidResponse");

            nidData = nidResponse.getNidData();

            nidid.setText(nidData.getNidNo());
            dobid.setText(nidData.getDob());
            applicationsnamebanglaid.setText(nidData.getCustomerNameBen());
            applicationsnameenglishid.setText(nidData.getCustomerNameEng());
            fathersnameid.setText(nidData.getFatherName());
            mothersnameid.setText(nidData.getMotherName());
            presentaddressid.setText(nidData.getAddress());

        }

    }

//    private void updateLabel() {
//        String myFormat = "dd/MM/yy"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//        date1.setText(sdf.format(myCalendar.getTime()));
//    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
