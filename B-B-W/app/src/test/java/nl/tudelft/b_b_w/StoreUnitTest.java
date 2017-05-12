package nl.tudelft.b_b_w;


import org.junit.Test;

import java.io.File;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StoreUnitTest {

    /**
     * Force IOException in read method
     */
    @Test(expected = RuntimeException.class)
    public void testRead() {
        Store store = new Store();
        store.read();
    }

    /**
     * Force IOException in save method
     */
    @Test(expected = RuntimeException.class)
    public void testSaveIO() {
        Store store = new Store();
        new File("keys.txt").mkdir();
        store.save();
        File file = new File("keys.txt");
        file.delete();
    }

    /**
     * Test Save method.
     */
    @Test
    public void testSave(){
        String key = "pub_key";
        Store store = new Store();

        store.addKey(key);
        store.save();
        store.read();

        Set<String> result = new HashSet<String>();
        result.add(key);

        File file = new File("keys.txt");
        file.delete();

        assertEquals(result, store.getKeys());
    }
}