package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Decoder {

    public void applyForDirectoryFiles(String directoryPath, CipherAlgoType cipherAlgoType, Map<String, String> params) throws IllegalFormatException {
        File directory = new File(directoryPath);
        System.out.println(directory.toURI());
        if (!directory.isDirectory()) throw new IllegalArgumentException();
        for(File fileInDirectory : Objects.requireNonNull(directory.listFiles())) {
            try {
                invokeByAlgoType(fileInDirectory, params, cipherAlgoType);
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    public void invokeByAlgoType(File fileInDirectory, Map<String, String> params, CipherAlgoType cipherAlgoType) throws IOException {
        switch(cipherAlgoType) {
            case ROT -> {
                List<String> result = ROT(Files.readAllLines(fileInDirectory.toPath()),
                        Integer.parseInt(params.get("offset")));
                Files.write(Paths.get(fileInDirectory.toURI()), result);
            }
        }
    }

    public List<String> ROT(List<String> strings, int offset) throws IllegalArgumentException {
        if (offset < 0 || offset > 255) throw new IllegalArgumentException("Illegal argument #offset");
        List<String> result = new ArrayList<>();
        for(String line : strings) {
            char[] res = new char[line.length()];
            for(int i = 0; i < line.length(); i++) {
                res[i] = (char) (line.charAt(i) - (char)offset);
            }
            result.add(String.valueOf(res));
        }
        return result;
    }

}
