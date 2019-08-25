package com.gigatech.ekyc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.gigatech.ekyc.model.NidData;
import com.google.android.material.textfield.TextInputEditText;

public class ProvideAdditionalInformation extends AppCompatActivity {
    Spinner genderspinnerid;
    TextInputEditText phone_edt, perma_add_edt, profession_edt,
            nominee_edt, relation_edt;
    Button backStepFour_ButtonId;

    NidData nidData = new NidData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_information);

        //income=findViewById(R.id.income_spinner);
        //occupation=findViewById(R.id.occupation_spinner2);
        //salary=findViewById(R.id.income_edt);
        genderspinnerid = findViewById(R.id.genderspinnerid);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderspinnerid.setAdapter(adapter);
        genderspinnerid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getChildAt(0) != null) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(ContextCompat.getColor(ProvideAdditionalInformation.this, android.R.color.white));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        phone_edt = findViewById(R.id.phone_edt);
        perma_add_edt = findViewById(R.id.perma_add_edt);
        profession_edt = findViewById(R.id.profession_edt);
        nominee_edt = findViewById(R.id.nominee_edt);
        relation_edt = findViewById(R.id.relation_edt);

        backStepFour_ButtonId = findViewById(R.id.backStepFour_ButtonId);
        //aMAR CODE
        //String incomesource[]={"Salary","Bonus","Pension","Etc"};
        //String occupationstring[]={"Service Holder","Student","Etc"};
        //ArrayAdapter<String> income_adapter=new ArrayAdapter<>(this,R.layout.spinner_text_item,incomesource);
        //ArrayAdapter<String> occupation_adapter=new ArrayAdapter<>(this,R.layout.spinner_text_item,occupationstring);
        //income.setAdapter(income_adapter);
        //occupation.setAdapter(occupation_adapter);

        if (getIntent().getParcelableExtra("NidData") != null) {
            nidData = getIntent().getParcelableExtra("NidData");
        }


        backStepFour_ButtonId.setOnClickListener(view -> finish());

    }


    public void takephoto_btn(View view) {

        nidData.setMobileNumber(phone_edt.getText().toString());
        nidData.setPermAddress(perma_add_edt.getText().toString());
        nidData.setGender(genderspinnerid.getSelectedItem().toString());
        nidData.setProfession(profession_edt.getText().toString());
        nidData.setNominee(nominee_edt.getText().toString());
        nidData.setNomineeRelation(relation_edt.getText().toString());

        Intent intent = new Intent(getApplicationContext(), CaptureCustomerPhotoActivity.class);
        intent.putExtra("NidData", nidData);
        startActivity(intent);

    }
}
