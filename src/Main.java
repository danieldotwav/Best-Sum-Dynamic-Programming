// Prompt: Write a function 'bestSum(targetSum, numbers)' that takes in a targetSum and an array of numbers as arguments.
//         The function should return an array containing the shortest combination of numbers that add up to exactly the targetSum
//         If there is a tie for the shortest combination, you may return any one of the shortest
//         You may assume that all input numbers are nonnegative.
//         If the sum is not possible, return null.

import java.util.HashMap;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Typical Case
        printList(8, bestSum(8, new Integer[] { 2, 3, 5 }, new HashMap<Integer, Integer[]>())); // Expected: [3, 5]

        // targetSum is Within Array
        printList(15, bestSum(15, new Integer[] {1, 4, 10, 15 }, new HashMap<Integer, Integer[]>())); // Expected: [15]

        // Large targetSum, Small Array
        printList(100, bestSum(100, new Integer[] { 1, 2, 5, 25 }, new HashMap<Integer, Integer[]>())); // Expected: []

        // Large targetSum, Large Array
        printList(514, bestSum(514, new Integer[] { 1, 2, 3, 4, 5, 10, 15, 25, 50, 100 }, new HashMap<Integer, Integer[]>())); // Expected: [ 100, 100, 100, 100, 100, 10, 4 ]
    }

    // Recursion with Memoization
    static Integer[] bestSum(int targetSum, Integer[] numbers, HashMap<Integer, Integer[]> memo) {
        if (memo.containsKey(targetSum)) {
            return memo.get(targetSum);
        }
        if (targetSum == 0) {
            return new Integer[0];
        }
        if (targetSum < 0) {
            return null;
        }

        Integer[] shortestCombination = null;

        for (int element : numbers) {
            int updatedSum = targetSum - element;
            Integer[] remainderCombination = bestSum(updatedSum, numbers, memo);

            // If we get to this if statement, we have determined it is possible to generate the sum
            if (remainderCombination != null) {
                int len = remainderCombination.length;

                Integer[] combination = Arrays.copyOf(remainderCombination, len + 1);
                combination[len] = element;

                // Check to see if the copied array is shorter than the "shortestCombination"
                if (shortestCombination == null || (combination.length < shortestCombination.length)) {
                    shortestCombination = combination;
                }
            }
        }

        
        memo.put(targetSum, shortestCombination);
        return shortestCombination;
    }

    static void printList(int targetSum, Integer[] container) {
        String resultString = (container != null)
            ? Arrays.stream(container).map(String::valueOf).collect(Collectors.joining(", ", "[ ", " ]"))
            : "Could not find sum";
        
        System.out.println("\nTarget Sum: " + targetSum + "\nResult: " + resultString);
    }
}
