package nl.tudelft.b_b_w.Models;

/**
 * Created by jasper on 17/05/2017.
 */

public class BlockFactory {

    public static Block getBlock(String type, String _owner, int _sequenceNumber, String _ownHash, String _previousHashChain, String _previousHashSender, String _publicKey, boolean _isRevoked) {
        switch (type) {
            case "Block":
                return new Block(_owner, _sequenceNumber, _ownHash, _previousHashChain, _previousHashSender, _publicKey, _isRevoked);
            default:
                throw new IllegalArgumentException("Invalid type of block: " + type);
        }
    }
}
