package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class ROTDecodeTest {
    public Encoder encoder = new Encoder();
    public Decoder decoder = new Decoder();
    @Test
    void functionalTestROTDecode() {
        List<String> listStrings = new ArrayList<>();
        List<String> result = new ArrayList<>();
        listStrings.add("Vwulqj#rqh");
        listStrings.add("Vwulqj#wzr");
        result.add("String one");
        result.add("String two");

        listStrings = decoder.ROT(listStrings, 3);
        assertEquals(listStrings.size(), result.size());
        for(int i = 0; i < result.size(); i++) {
            assertEquals(listStrings.get(i), result.get(i));
        }
    }

    @Test
    void offsetLimitsTestRotDecode() {
        List<String> listStrings = new ArrayList<>();
        assertThrowsExactly(IllegalArgumentException.class, () -> decoder.ROT(listStrings, 256));
    }
}
