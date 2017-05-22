package nl.tudelft.b_b_w.controller;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class EncryptionControllerUnitTest {

    @Test
    public void hashCheck() throws Exception {
        EncryptionController encryptionController = new EncryptionController();

        assertEquals(check, co.hashKey());
    }



}