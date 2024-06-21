package org.example;

public class CorrectChecker {
    public boolean isCorrect(String str1, String str2) {
        while (!str1.isEmpty()) {
            char symbol = str1.charAt(0);
            str1 = str1.substring(1);

            boolean found = false;
            for (int i = 0; i < str2.length(); i++) {
                if (str2.charAt(i) == symbol) {
                    found = true;
                    //System.out.println(i + ": " + str2.substring(0, i) + "     " + str2.substring(i + 1));
                    //System.out.println(str1 + "     " + str2);
                    str2 = str2.substring(0, i) + str2.substring(i + 1);
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
}

