package no.ogekle.jakob.priscounter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvNumOfPrises;
    private TextView tvNumOfBoxes;
    private TextView tvAvgNumOfPrices;
    private int numOfPrises = 1;
    private int numOfBoxes = 1;
    private int totNumBoxes = 0;
    private int totNumPrices = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrieve();

        Button buttonAddPris = (Button) findViewById(R.id.btn_add_pris);
        buttonAddPris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrNumOfPrises();
                setAvg();
            }
        });
        Button buttonAddBox = (Button) findViewById(R.id.btn_new_box);
        buttonAddBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrNumbOfBoxes();
                setAvg();
            }
        });

        findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        tvNumOfBoxes = (TextView) findViewById(R.id.tv_antall_esker);
        tvNumOfPrises = (TextView) findViewById(R.id.tv_priser_per_eske);
        tvAvgNumOfPrices = (TextView) findViewById(R.id.tv_snitt_antall_esker);

        setViews();
        setAvg();
    }

    @Override
    protected void onPause() {
        super.onPause();
        save();
    }

    private void incrNumOfPrises() {
        numOfPrises++;
        tvNumOfPrises.setText(numOfPrises + "");
    }

    private void incrNumbOfBoxes() {
        numOfBoxes++;
        tvNumOfBoxes.setText(numOfBoxes + "");
        numOfPrises = 0;
        tvAvgNumOfPrices.setText(numOfPrises + "");
    }

    private void save() {
        SharedPreferences.Editor sharedPreferences = getSharedPreferences("Saved", MODE_PRIVATE).edit();
        sharedPreferences.putInt("numOfPrices", numOfPrises);
        sharedPreferences.putInt("numOfBoxes", numOfBoxes);
        sharedPreferences.apply();
    }

    private void retrieve() {
        SharedPreferences preferences = getSharedPreferences("Saved", MODE_PRIVATE);
        numOfPrises = preferences.getInt("numOfPrices", 1);
        numOfBoxes = preferences.getInt("numOfBoxes", 1);
    }

    private void setViews() {
        tvNumOfBoxes.setText(numOfBoxes + "");
        tvNumOfPrises.setText(numOfPrises + "");
    }

    private void setAvg() {
        int avg;
        try {
            avg = totNumPrices / totNumBoxes;
        } catch (ArithmeticException e) {
            tvAvgNumOfPrices.setText("Ikke beregnet");
            return;
        }
        tvAvgNumOfPrices.setText(avg + " ");
    }

    private void reset() {
        SharedPreferences.Editor sharedPreferences = getSharedPreferences("Saved", MODE_PRIVATE).edit();
        sharedPreferences.putInt("numOfPrices", 0);
        sharedPreferences.putInt("numOfBoxes", 0);
        sharedPreferences.apply();
        retrieve();
        setViews();
    }
}
