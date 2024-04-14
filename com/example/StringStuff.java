package com.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Program with a bunch of string algorithms.
 *
 * @author Christopher Di Bert
 * @version 1.0
 * @since 2024-04-09
 */

// StringStuff class
public final class StringStuff {

    /** Private constructor to prevent instantiation. */
    private StringStuff() {
        throw new UnsupportedOperationException("Cannot instantiate");
    }

    /**
     * This is the main method.
     *
     * @param args Unused
     */
    public static void main(final String[] args) {
        // File name of the input file.
        File inputFile = new File("input.txt");

        // All output files.
        String[] outputFiles = {"blowUp.txt", "maxRun.txt", "shrink.txt"};

        /* Reads input file, sends each line to each string method,
         * then outputs them to their respective output file.
         */
        for (String outputFile : outputFiles) {
            try {
                Scanner sc = new Scanner(inputFile);
                FileWriter fileWriter = new FileWriter(outputFile);
                BufferedWriter writer = new BufferedWriter(fileWriter);
                // Passes input lines to the string reverser then writes them to output.
                while (sc.hasNextLine()) {
                    String inputString = sc.nextLine();
                    writer.write(caller(outputFile, inputString));
                    writer.newLine();
                }
                writer.close();
                sc.close();
            } catch (Exception e) {
                System.out.println("Invalid write path");
            }
        }
    }

    /**
     * Method to decide which string method to call based on output file name
     *
     * @param writeName passed.
     * @param value passed.
     * @return outputString.
     */
    private static String caller(final String writeName, final String value) {
        switch (writeName) {
            case "blowUp.txt":
                return BlowUp(value);
            case "maxRun.txt":
                return String.valueOf(MaxRun(value));
            default:
                return shrink(value);
        }
    }

    /**
     * Shrinks strings.
     *
     * @param str passed.
     * @return shrinkString.
     */
    private static String shrink(final String str) {
        String shrinkString = "";
        // List to keep track of character repetitions.
        ArrayList<Integer> charCounts = new ArrayList<Integer>();
        // Creates character array from the input string and sorts it.
        char[] sortedString = str.toCharArray();

        // Sets the previous character to the first character in the array.
        char previousChar = sortedString[0];

        // Initialize repetition counter.
        int repetitions = 0;

        // Iterates over every character in the char array.
        for (int i = 0; i < sortedString.length; i++) {
            /* If the previous character is the same as the current,
             * increment the number of repetitions.
             */
            if (sortedString[i] == previousChar) {
                repetitions++;
            } else {
                /* If the number of repetitions is not equal to one,
                 * then add the number of repetitions followed by its
                 * respective character to the shrunken string.
                 */
                if (repetitions != 1) {
                    shrinkString += Integer.toString(repetitions - 1) + previousChar;
                    /* If the number of repetitions is only one,
                     * then simply add the previous character to the
                     * shrunken string.
                     */
                } else {
                    shrinkString += previousChar;
                }
                /* Set the number of repetitions to 1 to account for the
                 * current character.
                 */
                repetitions = 1;
            }
            // Sets the previous character equal to the current character.
            previousChar = sortedString[i];
        }
        /* Same block of code that was in the above for loop. This ensures
         * that the last character of the array is not ignored in the
         * shrunken string.
         */
        if (repetitions != 1) {
            shrinkString += Integer.toString(repetitions - 1) + previousChar;
            return shrinkString;
        } else {
            shrinkString += previousChar;
            return shrinkString;
        }
    }

    /**
     * Method to find the maximum run of a string.
     *
     * @param str passed.
     * @return maxRun.
     */
    private static int MaxRun(final String str) {
        // List to keep track of character repetitions.
        ArrayList<Integer> charCounts = new ArrayList<Integer>();
        // Creates character array from the input string and sorts it.
        char[] sortedString = str.toCharArray();
        Arrays.sort(sortedString);
        // Initialize repetition counter.
        int repetitions = 0;
        // Sets the previous character to the first character in the array.
        char previousChar = sortedString[0];

        // Iterates over every character in the char array.
        for (int i = 0; i < sortedString.length; i++) {
            /* If the previous character is equal to the current
             * character, increment the repetitions.
             */
            if (sortedString[i] == previousChar) {
                repetitions++;
                /* If the previous character is not equal to the
                 * current character, then add the number of repetitions
                 * to the list of repetitions.
                 */
            } else {
                charCounts.add(repetitions);
                // Sets the repetitions to 1 to account for the current char.
                repetitions = 1;
            }
            // Sets the previous character to the current character.
            previousChar = sortedString[i];
        }

        /* Once finished iterating, sort the number of repetitions in reverse
         * order, then return the first index (the largest number of reps).
         */
        Collections.sort(charCounts, Collections.reverseOrder());
        return charCounts.get(0);
    }

    /**
     * Blow up string method.
     *
     * @param str passed.
     * @return blownUpString.
     */
    private static String BlowUp(final String str) {
        String blownUpString = "";

        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                // Gets the number of repetitions.
                int repetitions = Character.getNumericValue(str.charAt(i));

                /* Gets the string value of the selected character. This is done
                 * so the repeat method can be called in order to repeat
                 * the character however many times needed.
                 */
                String blownUpSegment = String.valueOf(str.charAt(i + 1)).repeat(repetitions);

                // Appends the blown up segment to the new string.
                blownUpString += blownUpSegment;

                /* If there is no digit in the string at the index,
                 * simply add the character to the blown up string.
                 */
            } else {
                blownUpString += str.charAt(i);
            }
        }
        // Returns the modified string.
        return blownUpString;
    }
}
