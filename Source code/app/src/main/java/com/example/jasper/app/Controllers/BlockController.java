package com.example.jasper.app.Controllers;

import android.content.Context;

import com.example.jasper.app.Models.Block;
import com.example.jasper.app.Models.DatabaseHandler;
import com.example.jasper.app.Models.User;

/**
 * Created by jasper on 11/05/2017.
 */

public class BlockController {

    private Context context;

    public BlockController(Context _context) {
        this.context = _context;
    }

    public Block addBlock(Block block) {
        User user = User.getUser();

        DatabaseHandler databaseHandler = DatabaseHandler.getInstance(this.context);

        databaseHandler.addBlock(block);

        return block;
    }
}
