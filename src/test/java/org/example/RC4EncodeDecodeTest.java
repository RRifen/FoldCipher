package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class RC4EncodeDecodeTest {
    public Encoder encoder = new Encoder();
    public Decoder decoder = new Decoder();
    @Test
    void functionalTestRC4() {
        List<String> listStrings = new ArrayList<>();
        List<String> encoded;
        List<String> result;
        listStrings.add("stringone");
        listStrings.add("stringtwo");
        encoded = encoder.RC4(listStrings, "tests");
        result = decoder.RC4(encoded, "tests");
        assertEquals(listStrings.size(), result.size());
        for(int i = 0; i < result.size(); i++) {
            assertEquals(result.get(i), listStrings.get(i));
        }
    }

    @Test
    void offsetLimitsTestRot() {
        List<String> listStrings = new ArrayList<>();
        assertThrowsExactly(IllegalArgumentException.class, () -> encoder.RC4(listStrings, ""));
    }
}
