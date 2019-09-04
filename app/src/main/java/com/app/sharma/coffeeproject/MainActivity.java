package com.app.sharma.coffeeproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    int prise = 15;
    TextView textView, textView2;
    String orderSummary;
    String name2;
    EditText namefeild;
    CheckBox whipped, icy, oreo, choco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonplus = (Button) findViewById(R.id.buttonPlus);
        Button buttonminus = (Button) findViewById(R.id.buttonMinus);

        namefeild = (EditText) findViewById(R.id.name);


        // topping getiing toppings
        whipped = (CheckBox) findViewById(R.id.whipped);
        icy = (CheckBox) findViewById(R.id.icy);
        oreo = (CheckBox) findViewById(R.id.oreo);
        choco = (CheckBox) findViewById(R.id.choco);

    }

    public void increment(View v){
        quantity = quantity+1;
        display(quantity);
    }
    public void decrement(View v){
        quantity = quantity-1;
        if(quantity < 0){
            Toast.makeText(this,"Quantity cant be less than 0",Toast.LENGTH_SHORT).show();
            quantity = 0;
        }
        display(quantity);
    }
    private void display(int quantity){
        textView = (TextView) findViewById(R.id.textViewQuantitiesShow);
        textView.setText(""+quantity);
    }



    public void increment2(View v){
        prise = prise+1;
        display2(prise);
    }
    public void decrement2(View v){
        prise = prise-1;
        if(prise < 0){
            Toast.makeText(this,"Prise cant be less than 0",Toast.LENGTH_SHORT).show();
            prise = 0;
        }
        display2(prise);
    }
    private void display2(int quantity2){
        textView2 = (TextView) findViewById(R.id.textViewQuantitiesShow2);
        textView2.setText("" + prise);
    }


    public void order(View v){

        //empty name



        TextView textView2 = (TextView) findViewById(R.id.textViewPriceShow);
        int num = quantity*prise;
        name2 =  namefeild.getText().toString();
        orderSummary = "" + "Name: " + name2; // having name here
        orderSummary += "\nQuantities: " + quantity;
        orderSummary += "\nPrice per coffee: " + prise;
        //here below i need to add toppings to email
        if(whipped.isChecked()) {
            num += quantity * 5;
            orderSummary += "\nExtra added topping : Whipped Cream";
        }
        if(icy.isChecked()) {
            num += quantity * 2;
            orderSummary += "\nExtra added topping is: Icy Sugar";
        }
        if(oreo.isChecked()) {
            num += quantity * 7;
            orderSummary += "\nExtra added topping is: Oreo Ice Blended";
        }
        if(choco.isChecked()) {
            num += quantity * 12;
            orderSummary += "\nExtra added topping is: Choco glaze";
        }

        orderSummary += "\nTotal bill of: " + num;


        textView2.setText(NumberFormat.getCurrencyInstance().format(num));
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                        intent.putExtra(Intent.EXTRA_EMAIL, "coffeeWala@gmail.com");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary for "+ name2+ "'s Coffee");
                        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);

                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                },
                2000
        );
    }
}
