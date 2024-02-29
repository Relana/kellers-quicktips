package main.java;

import java.util.ArrayList;

/**
 * The Generator interface contains methods for generating quicktips.
 */
public interface Generator {
    /**
     * generateTip generates a quicktip of the size specified by tipSetSize and
     * within the bounds of 1-[scope] (both are inclusive)
     * @param tipSetSize - the amount of numbers to be generated
     * @param scope - the upper bound of possible numbers
     * @return - a list of the generated, ordered numbers
     */
    ArrayList<Integer> generateTip(int tipSetSize, int scope);

    /**
     * checkForDuplicates makes sure the latest generated number is not already
     * part of the list of generated numbers.
     * In case the number is found to be a duplicate, a new number is generated and
     * checked recursively.
     * @param number - the current number to be checked
     * @param quicktip - the list of already generated numbers
     * @param scope - the upper bound of possible numbers
     * @return - a number which is not already in the quicktip list
     */
    int checkForDuplicates(int number, ArrayList<Integer> quicktip, int scope);
}
