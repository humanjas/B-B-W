package nl.tudelft.b_b_w;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import nl.tudelft.b_b_w.Models.DatabaseHandler;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addBlock(View view) {
        Intent intent = new Intent(this, AddBlockActivity.class);
        startActivity(intent);
    }

    public void deleteBlock(View view) {
        Intent intent = new Intent(this, DeleteBlockActivity.class);
        startActivity(intent);
    }

    public void displayChain(View view) {
    }
}
