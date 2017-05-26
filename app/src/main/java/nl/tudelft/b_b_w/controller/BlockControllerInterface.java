package nl.tudelft.b_b_w.controller;

import java.util.List;

import nl.tudelft.b_b_w.model.Block;

/**
 * Interface for BlockController class
 */
interface BlockControllerInterface {

    /**
     * Adding a block to the blockchain
     *
     * @param block Block you want to add
     * @return returns the block you added
     */
    List<Block> addBlock(Block block);

    /**
     * Get all blocks that are not revoked
     *
     * @return List of all the blocks
     */
    List<Block> getBlocks(String owner);

    /**
     * Get the latest block of a specific owner
     *
     * @return a Block object, which is the newest block of the owner
     */
    Block getLatestBlock(String owner);

    /**
     * Get the latest sequence number of the chain of a specific owner
     *
     * @return an integer which is the latest sequence number of the chain
     */
    int getLatestSeqNumber(String owner);

    /**
     *
     * Revoke a block from the blockchain by adding the same
     * block but setting revoked on true
     *
     * @param block The block you want to revoke
     * @return the revoked block
     */
    List<Block> revokeBlock(Block block);

    /**
     * Method for removing a certain block from a given list
     *
     * @param list  The list of all the blocks
     * @param block The revoke block
     * @return List without the revoked block
     */
    List<Block> removeBlock(List<Block> list, Block block);

    /**
     * verifyIBAN method
     * updates the trust value of the block to the set trust value for a verified IBAN
     * @param block given block to update
     * @return block that is updated
     */
    Block verifyIBAN(Block block);

    /**
     * successfulTransaction method
     * updates the trust value of the block to the set trust value for a succesful transaction
     * @param block given block to update
     * @return block that is updated
     */
    Block successfulTransaction(Block block);

    /**
     * failedTransaction method
     * updates the trust value of the block to the set trust value for a failed transaction
     * @param block given block to update
     * @return block that is updated
     */
    Block failedTransaction(Block block);

    /**
     * revokedTrustValue method
     * updates the trust value of the block to the set trust value for a revoked block
     * @param block given block to update
     * @return block that is updated
     */
    Block revokedTrustValue(Block block);

    /**
     * backTrace method
     * @param block given input block to backtrace
     * @return block which is the true ancestor
     */
    Block backTrace(Block block);
}
