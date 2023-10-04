package org.example;

import java.util.ArrayList;
import java.util.List;

public class Encoder {
    public List<String> ROT(List<String> strings, int offset) {
        if (offset < 0 || offset > 255) throw new IllegalArgumentException("Illegal argument #offset");
        List<String> result = new ArrayList<>();
        for(String line : strings) {
            char[] res = new char[line.length()];
            for(int i = 0; i < line.length(); i++) {
                res[i] = (char) (line.charAt(i) + (char)offset);
            }
            result.add(String.valueOf(res));
        }
        return result;
    }

}
