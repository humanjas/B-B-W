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

public class BlockController implements BlockControllerInterface {

    /**
     * Class attributes
     */
    private Context context;
    private User user;
    private GetDatabaseHandler getDatabaseHandler;
    private MutateDatabaseHandler mutateDatabaseHandler;

    /**
     * Constructor to initialize all the involved entities
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
     * @inheritDoc
     */
    @Override
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
     * @inheritDoc
     */
    @Override
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
     * @inheritDoc
     */
    @Override
    public Block getLatestBlock(String owner) {
        return getDatabaseHandler.getLatestBlock(owner);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getLatestSeqNumber(String owner) {
        return getDatabaseHandler.lastSeqNumberOfChain(owner);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Block> revokeBlock(Block block) {
        String owner = block.getOwner();
        Block newBlock = BlockFactory.getBlock("REVOKE", block.getOwner(),
                block.getOwnHash(), block.getPreviousHashChain(), block.getPreviousHashSender(),
                block.getPublicKey(), block.getIban(), block.getTrustValue());
        addBlock(newBlock);
        return getBlocks(owner);
    }

    /**
     * @inheritDoc
     */
    @Override
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
     * @inheritDoc
     */
    @Override
    public Block verifyIBAN(Block block) {
        block.setTrustValue(TrustValues.VERIFIED.getValue());
        return block;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Block successfulTransaction(Block block) {
        block.setTrustValue(block.getTrustValue() + TrustValues.SUCCESFUL_TRANSACTION.getValue());
        return block;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Block failedTransaction(Block block) {
        block.setTrustValue(block.getTrustValue() + TrustValues.FAILED_TRANSACTION.getValue());
        return block;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Block revokedTrustValue(Block block) {
        block.setTrustValue(TrustValues.REVOKED.getValue());
        return block;
    }



}
