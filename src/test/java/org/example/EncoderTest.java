package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncoderTest {

    private final String FILE_NAME = "src/test/resources/input.txt";

    public Encoder encoder = new Encoder();

    @BeforeEach
    public void cleanUpFiles() {
        File targetFile = new File(FILE_NAME);

        String str = "abcdef\n123456\n!@#$%^";
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(targetFile));
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void functionalTestApplyForDirectoryFiles() {
        Map<String, String> params = new HashMap<>();
        params.put("offset", "3");
        encoder.applyForDirectoryFiles("src/test/resources", CipherAlgoType.ROT, params);

        List<String> result = new ArrayList<>(List.of("defghi", "456789", "$C&'(a"));

        try {
            List<String> check = Files.readAllLines(Path.of(FILE_NAME));
            assertEquals(result.size(), check.size());
            for(int i = 0; i < result.size(); i++) {
                assertEquals(check.get(i), result.get(i));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testRotInvokeByAlgoType() {
        Map<String, String> params = new HashMap<>();
        params.put("offset", "3");
        List<String> result = new ArrayList<>(List.of("defghi", "456789", "$C&'(a"));
        try {
            encoder.invokeByAlgoType(new File(FILE_NAME), params, CipherAlgoType.ROT);
            List<String> check = Files.readAllLines(Path.of(FILE_NAME));
            assertEquals(result.size(), check.size());
            for(int i = 0; i < result.size(); i++) {
                assertEquals(check.get(i), result.get(i));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
