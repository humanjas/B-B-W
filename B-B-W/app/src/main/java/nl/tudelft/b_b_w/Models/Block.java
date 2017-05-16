package nl.tudelft.b_b_w.Models;

/**
 * Block represents
 *
 */

public class Block {

    //properties of a block
    private String owner;
    private int sequenceNumber;
    private String ownHash;
    private String previousHashChain;
    private String previousHashSender;
    private String publicKey;
    private boolean isRevoked;

    /**
     * Constructor to initialize a block
     * @param _owner owner of a block
     * @param _sequenceNumber sequence number of the block
     * @param _previousHashChain the hash value of the block before in the chain
     * @param _previousHashChain the hash value of the block before of the sender
     * @param _publicKey public key of the owner of the block
     * @param _isRevoked boolean to check whether a block is revoked or not
     */
    public Block(String _owner, int _sequenceNumber, String _ownHash, String _previousHashChain, String _previousHashSender, String _publicKey, boolean _isRevoked) {
        this.owner = _owner;
        this.sequenceNumber = _sequenceNumber;
        this.ownHash = _ownHash;
        this.previousHashChain = _previousHashChain;
        this.previousHashSender = _previousHashSender;
        this.publicKey = _publicKey;
        this.isRevoked = _isRevoked;
    }

    /**
     * Default getter for owner
     * @return owner of the block
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Default getter for own block hash
     * @return own hash
     */
    public String getOwnHash() {
        return ownHash;
    }

    /**
     * Default getter for previous block hash of chain
     * @return previous hash of chain
     */
    public String getPreviousHashChain() {
        return previousHashChain;
    }

    /**
     * Default getter for previous block hash of chain
     * @return previous hash of chain
     */
    public String getPreviousHashSender() {
        return previousHashSender;
    }

    /**
     * Default getter for public key
     * @return public key of the block
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * Default getter for sequence number
     * @return the sequence number of the block
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Default getter for checking whether a block is revoked
     * @return true or false
     */
    public boolean isRevoked() {
        return isRevoked;
    }

    /**
     * Equals method to check whether two blocks match
     * @param o The block you want to match with the current block
     * @return true if blocks match, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Block block = (Block) o;

        if (!owner.equals(block.owner)) return false;
        return publicKey.equals(block.getPublicKey());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Block{" +
                "owner='" + owner + '\'' +
                ", sequenceNumber=" + sequenceNumber +
                ", ownHash='" + ownHash + '\'' +
                ", previousHashChain='" + previousHashChain + '\'' +
                ", previousHashSender='" + previousHashSender + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", isRevoked=" + isRevoked +
                '}';
    }
}
