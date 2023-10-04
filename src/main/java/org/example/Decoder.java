package org.example;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
            case RC4 -> {
                List<String> result = RC4(Files.readAllLines(fileInDirectory.toPath()),
                        params.get("key"));
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

    public List<String> RC4(List<String> strings, String keyString) {
        List<String> result = new ArrayList<>();
        try {
            Cipher RC4 = Cipher.getInstance("RC4");
            RC4.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyString.getBytes(StandardCharsets.UTF_8), "RC4"));
            for(String line : strings) {
                byte[] lineBytes = Base64.getDecoder().decode(line);
                lineBytes = RC4.update(lineBytes);
                result.add(new String(lineBytes, StandardCharsets.UTF_8));
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
