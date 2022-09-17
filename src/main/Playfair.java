package main;

import java.util.LinkedHashSet;

public class Playfair extends AbstractCypher {
    private final String key;
    private String table;


    public Playfair(String key) {
        this.key = (key.toLowerCase()).replaceAll("[^a-z]", "");
        this.table = generateTable();
    }

    @Override
    public String encrypt(String msg) {
        return encrypt(msg, true);
    }

    @Override
    public String decrypt(String msg) {
        return encrypt(msg, false);
    }

    private String encrypt(String msg, boolean encrypt) {
        // Requires a key
        // Requires 4 rules
        //1. if both letters are the same, add X after first letter
        //2. if letters are on the same row, replace them with immediate right respectively
        //3. If the letters appear on the same column of your table, replace them with the letters immediately below respectively
        //4. If the letters are not on the same row or column, replace them with the letters on the same row respectively but at the other pair of corners of the rectangle defined by the original pair.
        // Create 5x5 square

        String preparedMsg = (encrypt) ? prepareMsg(msg) : msg;
        char[] preparedMsgChars = preparedMsg.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (int pair = 0; pair < preparedMsg.length(); pair += 2) {
            int[] fchar = getCharPosInTable(preparedMsgChars[pair]);
            int[] lchar = getCharPosInTable(preparedMsgChars[pair + 1]);

            if (fchar[0] != lchar[0] && fchar[1] != lchar[1]) {
                // case rectangle
                sb.append(getCornerChars(fchar, lchar));
            } else if (fchar[0] == lchar[0]) {
                // case same row
                if (encrypt) {
                    sb.append(getRightChar(preparedMsgChars[pair]));
                    sb.append(getRightChar(preparedMsgChars[pair + 1]));
                } else {
                    sb.append(getLeftChar(preparedMsgChars[pair]));
                    sb.append(getLeftChar((preparedMsgChars[pair + 1])));
                }
            } else if (fchar[1] == lchar[1]) {
                // case same column
                if (encrypt) {
                    sb.append(getCharBelow(preparedMsgChars[pair]));
                    sb.append(getCharBelow((preparedMsgChars[pair + 1])));
                } else {
                    sb.append(getCharAbove(preparedMsgChars[pair]));
                    sb.append(getCharAbove((preparedMsgChars[pair + 1])));
                }
            }
        }

        return sb.toString();
    }


    public String generateTable() {
        LinkedHashSet<String> table = new LinkedHashSet<>();
        for (char c : (key + alphabet).replaceAll("[^a-zA-Z]|[j]", "").toCharArray()) {
            table.add(Character.toString(c));
        }

        return String.join("", table);
    }

    public String prepareMsg(String msg) {
        StringBuilder sb = new StringBuilder();
        char[] chars = msg.toLowerCase().replaceAll("[^a-zA-Z]", "").toCharArray();

        for (int i = 0; i < chars.length; i++) {
            String pair = "" + chars[i];

            // replace if same letters or only one left
            if (i == (msg.length() - 1) || chars[i] == chars[i + 1]) {
                pair += "x";
            } else {
                pair += chars[i + 1];
                i++;
            }

            sb.append(pair);
        }
        return sb.toString();
    }

    public int[] getCharPosInTable(char ch) {
        int idx = table.indexOf(ch);

        int i = Math.floorDiv(idx, 5);
        int j = idx % 5;

        return new int[]{i, j};
    }

    private char getRightChar(char root) {
        int idx = table.indexOf(root);
        return (idx % 5 == 4) ? table.charAt(idx - 4) : table.charAt(idx + 1);
    }

    private char getLeftChar(char root) {
        int idx = table.indexOf(root);
        return (idx % 5 == 0) ? table.charAt(idx + 4) : table.charAt(idx - 1);
    }

    private char getCharBelow(char root) {
        int idx = table.indexOf(root);
        return (Math.floorDiv(idx, 5) == 4) ? table.charAt(idx - 4 * 5) : table.charAt(idx + 5);
    }

    private char getCharAbove(char root) {
        int idx = table.indexOf(root);
        return (Math.floorDiv(idx, 5) == 0) ? table.charAt(idx + 4 * 5) : table.charAt(idx - 5);
    }

    private char[] getCornerChars(int[] first, int[] second) {
        char cornerFirst = table.charAt(first[0] * 5 + second[1]);
        char cornerSecond = table.charAt(second[0] * 5 + first[1]);

        return new char[]{cornerFirst, cornerSecond};
    }

}
