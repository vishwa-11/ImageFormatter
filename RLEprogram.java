import java.util.Scanner;

public class RLEprogram {
    // Create a method for the main menu
    public static void Menu () {
        System.out.println("RLE Menu\n--------\n0. Exit\n1. Load File\n2. Load Test Image\n3. Read RLE String\n4. Read RLE Hex String\n5. Read Data Hex String\n6. Display Image\n7. Display RLE String\n8. Display Hex RLE Data\n9. Display Hex Flat Data");
    }
    public static void main(String[] args) {
        int userInput;
        String userInput1;

        Scanner scnr = new Scanner(System.in);
        // 1. Display welcome message
        System.out.println("Welcome to the RLE image encoder!");

        // 2. Display color test with the message
        ConsoleGfx.displayImage(ConsoleGfx.testRainbow);
        byte[] imageData = null;
        // 3. Display the menu - Part A: while loop or if-else chains
        Menu();
        // option 1, option 2 and option 6
        // 3.1 - option 1
        // ConsoleGfx.loadFile(userInput) and you want to store the returned byte[] into imageData array
        // 3.2 - option 2
        // store ConsoleGfx.testImage into the imageData array
        // 3.6 - option 6
        // display image stored inside of imageData array
        // user might first enter 1 -> 6 or 2 -> 6
        // complete option 0,3,4,5,7,8,9
        // 4. Prompt for input    }
        System.out.print("Select a Menu Option: ");
        userInput = scnr.nextInt();

        while (userInput > 0 || userInput <= 0) {
            if (userInput == 1) {
                System.out.print("Enter name of file to load:");
                userInput1 = scnr.next();
                imageData = ConsoleGfx.loadFile(userInput1);
                Menu();
                System.out.print("Select a Menu Option: ");
                userInput = scnr.nextInt();
            }
            else if (userInput == 2) {
                imageData = ConsoleGfx.testImage;
                Menu();
                System.out.print("Select a Menu Option: ");
                userInput = scnr.nextInt();
            }
            else if (userInput == 6) {
                ConsoleGfx.displayImage(imageData);
                Menu();
                System.out.print("Select a Menu Option: ");
                userInput = scnr.nextInt();
            }
        }

    }
}
