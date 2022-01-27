package com.example.myapplication.helperclasses;

import java.util.Arrays;
import java.util.List;

public class CheckForIllegalChars {

    public static boolean checkForIllegalCharacters(String s) {
        List<String> illegalChars = Arrays.asList(".", "$", "[", "]" , "#", "/");
        for (String c : illegalChars) {
            if (s.contains(c)) {
                return false;
            }
        }
        return true;
    }
}
