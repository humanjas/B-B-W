package nl.tudelft.b_b_w;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import nl.tudelft.b_b_w.Models.DatabaseHandler;

public class MainActivity extends Activity {
    private DatabaseHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new DatabaseHandler(this);
        checkGenesis();
    }

    private void checkGenesis() {
        if (handler.getAllBlocks().isEmpty()) {
            // add genesis
            // TODO create genesis block and add to database
        }
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
