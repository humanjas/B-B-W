package com.example.jasper.app.Controllers;

import android.content.Context;

import com.example.jasper.app.Models.Block;
import com.example.jasper.app.Models.DatabaseHandler;
import com.example.jasper.app.Models.User;

import java.util.List;

/**
 * Created by jasper on 11/05/2017.
 */

public class BlockController {

    private Context context;
    private User user;
    private DatabaseHandler databaseHandler;

    public BlockController(Context _context) {
        this.context = _context;
        this.user = User.getUser();
        this.databaseHandler = DatabaseHandler.getInstance(this.context);
    }

    public Block addBlock(Block block) {
        databaseHandler.addBlock(block);

        return block;
    }

    public List<Block> getBlocks() {
        // TODO GETBLOCKS function
        return null;
    }

}
