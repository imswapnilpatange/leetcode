import java.util.*;

class Solution {

    public String generateString(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();

        int len = n + m - 1;
        char[] word = new char[len];
        Arrays.fill(word, 'a');

        // Step 1: Apply 'T'
        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'T') {
                for (int j = 0; j < m; j++) {
                    word[i + j] = str2.charAt(j);
                }
            }
        }

        // Step 2: Validate 'T'
        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'T') {
                for (int j = 0; j < m; j++) {
                    if (word[i + j] != str2.charAt(j)) {
                        return "";
                    }
                }
            }
        }

        // Step 3: Handle 'F'
        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'F') {

                boolean match = true;

                for (int j = 0; j < m; j++) {
                    if (word[i + j] != str2.charAt(j)) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    boolean broken = false;

                    for (int j = m - 1; j >= 0; j--) {
                        int idx = i + j;

                        for (char c = 'a'; c <= 'z'; c++) {
                            if (c != word[idx]) {
                                char original = word[idx];
                                word[idx] = c;

                                if (isValid(word, str1, str2)) {
                                    broken = true;
                                    break;
                                }

                                word[idx] = original;
                            }
                        }

                        if (broken) break;
                    }

                    if (!broken) return "";
                }
            }
        }

        return new String(word);
    }

    private boolean isValid(char[] word, String str1, String str2) {
        int n = str1.length();
        int m = str2.length();

        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'T') {
                for (int j = 0; j < m; j++) {
                    if (word[i + j] != str2.charAt(j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}