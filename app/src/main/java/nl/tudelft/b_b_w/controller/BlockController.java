package nl.tudelft.b_b_w.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.b_b_w.model.Block;
import nl.tudelft.b_b_w.model.BlockFactory;
import nl.tudelft.b_b_w.model.GetDatabaseHandler;
import nl.tudelft.b_b_w.model.MutateDatabaseHandler;
import nl.tudelft.b_b_w.model.TrustValues;
import nl.tudelft.b_b_w.model.User;

/**
 * Performs the actions of the blockchain
 */

public class BlockController {

    private Context context;
    private User user;
    private GetDatabaseHandler getDatabaseHandler;
    private MutateDatabaseHandler mutateDatabaseHandler;

    /**
     * contructor to initialize all the involved entities
     *
     * @param _context the instance
     */
    public BlockController(Context _context) {
        this.context = _context;
        this.user = new User();
        this.getDatabaseHandler = new GetDatabaseHandler(this.context);
        this.mutateDatabaseHandler = new MutateDatabaseHandler(this.context);
    }

    /**
     * adding a block to the blockchain
     *
     * @param block Block you want to add
     * @return returns the block you added
     */
    public List<Block> addBlock(Block block) {
        // Check if the block already exists
        String owner = block.getOwner();
        Block latest = getDatabaseHandler.getLatestBlock(owner);

        if (latest == null) {
            mutateDatabaseHandler.addBlock(block);
        } else if (latest.isRevoked()) {
            throw new RuntimeException("Error - Block is already revoked");
        } else {
            if (block.isRevoked()) {
                revokedTrustValue(latest);
                mutateDatabaseHandler.updateBlock(latest);
                mutateDatabaseHandler.addBlock(block);
            }
            else throw new RuntimeException("Error - Block already exists");
        }

        return getBlocks(owner);
    }

    /**
     * Get all blocks that are not revoked
     *
     * @return List of all the blocks
     */
    public List<Block> getBlocks(String owner) {
        List<Block> blocks = getDatabaseHandler.getAllBlocks(owner);
        List<Block> res = new ArrayList<>();
        for (Block block : blocks) {
            if (block.isRevoked()) {
                res = removeBlock(res, block);
            } else {
                res.add(block);
            }
        }
        return res;
    }

    /**
     * Get the latest block of a specific owner
     *
     * @return a Block object, which is the newest block of the owner
     */
    public Block getLatestBlock(String owner) {
        return getDatabaseHandler.getLatestBlock(owner);
    }

    /**
     * Get the latest sequence number of the chain of a specific owner
     *
     * @return an integer which is the latest sequence number of the chain
     */
    public int getLatestSeqNumber(String owner) {
        return getDatabaseHandler.lastSeqNumberOfChain(owner);
    }

    /**
     *
     * Revoke a block from the blockchain by adding the same
     * block but setting revoked on true
     *
     * @param block The block you want to revoke
     * @return the revoked block
     */
    public List<Block> revokeBlock(Block block) {
        String owner = block.getOwner();
        Block newBlock = BlockFactory.getBlock("REVOKE", block.getOwner(),
                block.getOwnHash(), block.getPreviousHashChain(), block.getPreviousHashSender(),
                block.getPublicKey(), block.getIban(), block.getTrustValue());
        addBlock(newBlock);
        return getBlocks(owner);
    }

    /**
     * Method for removing a certain block from a given list
     *
     * @param list  The list of all the blocks
     * @param block The revoke block
     * @return List without the revoked block
     */
    public List<Block> removeBlock(List<Block> list, Block block) {
        List<Block> res = new ArrayList<>();
        for (Block blc : list) {
            if (!(blc.getOwner().equals(block.getOwner()) && blc.getPublicKey().equals(block.getPublicKey()))) {
                res.add(blc);
            }
        }
        return res;
    }

    /**
     * verifyIBAN method
     * updates the trust value of the block to the set trust value for a verified IBAN
     * @param block given block to update
     * @return block that is updated
     */
    public Block verifyIBAN(Block block) {
        block.setTrustValue(TrustValues.VERIFIED.getValue());
        return block;
    }

    /**
     * successfulTransaction method
     * updates the trust value of the block to the set trust value for a succesful transaction
     * @param block given block to update
     * @return block that is updated
     */
    public Block successfulTransaction(Block block) {
        block.setTrustValue(block.getTrustValue() + TrustValues.SUCCESFUL_TRANSACTION.getValue());
        return block;
    }

    /**
     * failedTransaction method
     * updates the trust value of the block to the set trust value for a failed transaction
     * @param block given block to update
     * @return block that is updated
     */
    public Block failedTransaction(Block block) {
        block.setTrustValue(block.getTrustValue() + TrustValues.FAILED_TRANSACTION.getValue());
        return block;
    }

    /**
     * revokedTrustValue method
     * updates the trust value of the block to the set trust value for a revoked block
     * @param block given block to update
     * @return block that is updated
     */
    public Block revokedTrustValue(Block block) {
        block.setTrustValue(TrustValues.REVOKED.getValue());
        return block;
    }



}
