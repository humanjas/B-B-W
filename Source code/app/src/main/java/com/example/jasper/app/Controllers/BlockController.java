package com.example.jasper.app.Controllers;

import android.content.Context;

import com.example.jasper.app.Models.Block;
import com.example.jasper.app.Models.DatabaseHandler;
import com.example.jasper.app.Models.User;

import java.util.List;

/**
 * Performs the actions of the blockchain
 * Created by jasper on 11/05/2017.
 */

public class BlockController {

    private Context context;
    private User user;
    private DatabaseHandler databaseHandler;

    /**
     * contructor to initialize all the involved entities
     * @param _context the instance
     */
    public BlockController(Context _context) {
        this.context = _context;
        this.user = User.getUser();
        this.databaseHandler = DatabaseHandler.getInstance(this.context);
    }

    /**
     * adding a block to the blockchain
     * @param block Block you want to add
     * @return returns the block you added
     */
    public Block addBlock(Block block) {
        // Check if the block already exists

        Block latest = databaseHandler.getLatestBlock(block.getOwner(), block.getPublic_key());

        if (latest == null) {
            databaseHandler.addBlock(block);
        } else {
            if (latest.isRevoked()) throw new RuntimeException("Error - Block is already revoked");
            else {
                if (block.isRevoked()) databaseHandler.addBlock(block);
                else throw new RuntimeException("Error - Block already exists");
            }
        }

        return block;
    }

    public List<Block> getBlocks() {
        // TODO check blocks on revoke

        return databaseHandler.getAllBlocks();
    }

}
