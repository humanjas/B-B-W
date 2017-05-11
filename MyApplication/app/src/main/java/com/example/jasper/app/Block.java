package com.example.jasper.app;

/**
 * Block represents
 */

public class Block {

    private String owner, previous_hash, public_key;
    private boolean isRevoked;
    private int sequence_number;

    public Block(String _owner, String _previous_hash, String _public_key, boolean _isRevoked, int _sequence_number) {
        this.owner = _owner;
        this.previous_hash = _previous_hash;
        this.public_key = _public_key;
        this.isRevoked = _isRevoked;
        this.sequence_number = _sequence_number;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String _owner) {
        this.owner = _owner;
    }

    public String getPrevious_hash() {
        return previous_hash;
    }

    public void setPrevious_hash(String _previous_hash) {
        this.previous_hash = _previous_hash;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String _public_key) {
        this.public_key = _public_key;
    }

    public boolean isRevoked() {
        return isRevoked;
    }

    public void setRevoked(boolean _revoked) {
        isRevoked = _revoked;
    }

    public int getSequence_number() {
        return sequence_number;
    }

    public void setSequence_number(int _sequence_number) {
        this.sequence_number = _sequence_number;
    }
}
