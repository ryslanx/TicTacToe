package com.example.mypackage;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cells = "_________";
        char[] chars = cells.toCharArray();
        printGame(chars);
        boolean turnX = true;
        HashMap<int[], Integer> hashMap = initializeMap();

        while (true) {
            System.out.println("Enter the coordinates: ");
            int x, y;
            String line = scanner.nextLine();
            String[] numbers = line.split(" ");
            try {
                x = Integer.parseInt(numbers[0]);
                y = Integer.parseInt(numbers[1]);
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
                continue;
            }
            int[] coordinates = {x, y};
            if (coordinates[0] > 3 || coordinates[1] > 3 || coordinates[0] < 0 || coordinates[1] < 0) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            int position = findElement(coordinates, hashMap);
            if (chars[position] == '_' && turnX) {
                chars[position] = 'X';
                turnX = false;
                printGame(chars);
            } else if (chars[position] == '_' && !turnX) {
                turnX = true;
                chars[position] = 'O';
                printGame(chars);
            } else {
                System.out.println("This cell is occupied! Choose another one!");
            }
            if (checkState(chars).equals("X wins") || checkState(chars).equals("O wins")
                    || checkState(chars).equals("Draw")) {
                System.out.println(checkState(chars));
                break;
            }
        }

    }

    public static int findElement(int[] coordinates, HashMap<int[], Integer> hashMap) {
        for(Map.Entry<int[], Integer> entry: hashMap.entrySet()) {
            if (Arrays.equals(entry.getKey(), coordinates)) {
                return entry.getValue();
            }
        }
        return -1;
    }

    public static void printGame(char[] chars) {
        System.out.println("---------");
        int counter = 0;
        for(char character : chars) {
            if (counter == 0) {
                System.out.print("| " + character);
                counter++;
            } else if (counter == 1) {
                System.out.print(" " + character + " ");
                counter++;
            } else if (counter == 2) {
                System.out.println(character + " |");
                counter = 0;
            }
        }
        System.out.println("---------");
    }


    public static HashMap<int[], Integer> initializeMap() {
        HashMap<int[], Integer> hashMap = new HashMap<>();
        int[][] array = {{1, 3}, {2, 3}, {3, 3}, {1, 2}, {2, 2}, {3, 2}, {1, 1}, {2, 1}, {3, 1}};
        int i = 0;
        for (int[] coordinates : array) {
            hashMap.put(coordinates, i);
            i++;
        }
        return hashMap;
    }

    public static String checkState(char[] chars) {
        // game not finished 0 1 2, 3 4 5, 6 7 8, 0 3 6, 1 4 7, 2 5 8, 0 4 8, 2 4 6;
        int[][] combinations = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}
        };
        boolean winsX = checkWinChar(combinations, chars, 'X');
        boolean winsO = checkWinChar(combinations, chars, 'O');
        int countX = checkCharQuantity(chars, 'X');
        int countO = checkCharQuantity(chars, 'O');
        boolean freeChar = checkEmptyCells(chars);
        if (winsX && winsO || Math.abs(countX - countO) > 1 ) {
            return "Impossible";
        }
        if (winsX) {
            return "X wins";
        } else if (winsO) {
            return "O wins";
        }
        if (freeChar) {
            return "Game not finished";
        } else {
            return "Draw";
        }
    }

    public static int checkCharQuantity(char[] chars, char character) {
        int count = 0;
        for (char letter : chars) {
            if (letter == character) {
                count++;
            }
        }
        return count;
    }

    public static boolean checkWinChar(int[][] combinations, char[] chars, char character) {
        int i = 0;
        for (int[] combination : combinations) {
            if (chars[combination[i]] == chars[combination[i+1]] &&
                    chars[combination[i + 1]] == chars[combination[i + 2]] && chars[combination[i]] == character) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkEmptyCells(char[] chars) {
        for (char character : chars) {
            if (character == '_') {
                return true;
            }
        }
        return false;
    }
}
