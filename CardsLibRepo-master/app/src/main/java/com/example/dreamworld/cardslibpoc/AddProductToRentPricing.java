package com.example.dreamworld.cardslibpoc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.github.florent37.materialtextfield.MaterialTextField;
import java.util.Calendar;

public class AddProductToRentPricing extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    ImageButton buttonDate;
    TextView dateTextView;
    MaterialTextField PricePerDay;
    MaterialTextField PricePerMonth;
    MaterialTextField PricePerWeek;
    MaterialTextField Securitydeposit;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_to_rent_pricing);

        buttonDate = (ImageButton)findViewById(R.id.imageButtonCalender);
        dateTextView= (TextView) findViewById(R.id.textViewCalender);
        PricePerDay=(MaterialTextField) findViewById(R.id.materialTextField3);
        PricePerWeek=(MaterialTextField) findViewById(R.id.materialTextField4);
        PricePerMonth=(MaterialTextField) findViewById(R.id.materialTextField5);
        Securitydeposit=(MaterialTextField) findViewById(R.id.materialTextField6);
        button=(Button)findViewById(R.id.button6);
        final String final_url = getIntent().getStringExtra("Image_url");
        final String Itemname = getIntent().getStringExtra("Item name").toString();
        final String ItemDescription = getIntent().getStringExtra("Item description").toString();

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                try {
                    CardInfo object = new CardInfo();
                    object.cardName = Itemname;
                    object.cardDescription = ItemDescription;
                    object.ImageUrl = final_url;
                    object.dateTextView=dateTextView.getText().toString();
                    object.PricePerDay = Float.parseFloat(PricePerDay.getEditText().getText().toString());
                    object.PricePerWeek = Float.parseFloat(PricePerWeek.getEditText().getText().toString());
                    object.PricePerMonth = Float.parseFloat(PricePerWeek.getEditText().getText().toString());
                    object.Securitydeposit = Float.parseFloat(Securitydeposit.getEditText().getText().toString());
                    new StoreToDB().execute(object).get();
                    Toast.makeText(getApplicationContext(), "New Product added", Toast.LENGTH_LONG).show();
                    finish();
                } catch (Exception e) {

                }
            }
        });

        // Show a datepicker when the dateButton is clicked
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        AddProductToRentPricing.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                //  dpd.setAutoHighlight(mAutoHighlight);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth,int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        String date = "Tada!! My Product will be Available From "+dayOfMonth+"/"+(++monthOfYear)+"/"+year+" To "+dayOfMonthEnd+"/"+(++monthOfYearEnd)+"/"+yearEnd;
        dateTextView.setText(date);

        //String datatostore="+dayOfMonth+'/'+(++monthOfYear)+"/"+year+";
        //Log.e("date set",dateStr);

        Toast.makeText(AddProductToRentPricing.this, "Selected Item: " + date, Toast.LENGTH_LONG).show();
    }
}
