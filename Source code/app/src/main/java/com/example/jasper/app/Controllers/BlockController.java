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
        List<Block> blocks = databaseHandler.getAllBlocks();

        for (int i=0; i<blocks.size(); i++) {
            Block loop_block = blocks.get(i);
            if (loop_block.isRevoked()) {
                for (int j=0; j<i; j++) {
                    if (blocks.get(j).equals(loop_block)) {
                        blocks.remove(j);
                        blocks.remove(i);
                        break;
                    }
                }
            }
        }

        return blocks;
    }

    public Block removeBlock(Block block) {
        return addBlock(new Block(block.getOwner(), block.getSequence_number(),
                block.getPrevious_hash(), block.getPublic_key(), true));
    }

}
