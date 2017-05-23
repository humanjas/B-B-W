package nl.tudelft.b_b_w.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import nl.tudelft.b_b_w.R;
import nl.tudelft.b_b_w.view.TestSubjects.TestSubject1;

public class PairActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair);
    }


    public void onTestSubject1(View view) {
        Intent intent = new Intent(this, TestSubject1.class);
        startActivity(intent);
    }

    public void onTestSubject2(View view) {
        Intent intent = new Intent(this, TestSubject1.class);
        startActivity(intent);
    }


    public void onTestSubject3(View view) {
        Intent intent = new Intent(this, TestSubject1.class);
        startActivity(intent);
    }

}
