package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EncoderTest {
    public Encoder encoder = new Encoder();
    @Test
    void functionalTestROT() {
        List<String> listStrings = new ArrayList<>();
        List<String> result = new ArrayList<>();
        listStrings.add("String one");
        listStrings.add("String two");
        result.add("Vwulqj#rqh");
        result.add("Vwulqj#wzr");

        listStrings = encoder.ROT(listStrings, 3);
        assertEquals(listStrings.size(), result.size());
        for(int i = 0; i < result.size(); i++) {
            assertEquals(listStrings.get(i), result.get(i));
        }
    }

    @Test
    void offsetLimitsTestRot() {
        List<String> listStrings = new ArrayList<>();
        assertThrowsExactly(IllegalArgumentException.class, () -> encoder.ROT(listStrings, 256));
    }
}