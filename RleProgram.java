import java.util.Scanner;

public class RleProgram {
    // Create a method for the main menu
    public static void Menu() {
        System.out.println("RLE Menu\n--------\n0. Exit\n1. Load File\n2. Load Test Image\n3. Read RLE String\n4. Read RLE Hex String\n5. Read Data Hex String\n6. Display Image\n7. Display RLE String\n8. Display Hex RLE Data\n9. Display Hex Flat Data");
    }

    public static String toHexString(byte[] data) {
        // create a null string and then a for loop that adds an element to the string.
        int i;
        String hex = "";
        for (i = 0; i < data.length; i++) {
            //The if else statements convert from decimal to hexadecimal
            if (data[i] == 0) {
                hex = hex.concat("0");
            } else if (data[i] == 1) {
                hex = hex.concat("1");
            } else if (data[i] == 2) {
                hex = hex.concat("2");
            } else if (data[i] == 3) {
                hex = hex.concat("3");
            } else if (data[i] == 4) {
                hex = hex.concat("4");
            } else if (data[i] == 5) {
                hex = hex.concat("5");
            } else if (data[i] == 6) {
                hex = hex.concat("6");
            } else if (data[i] == 7) {
                hex = hex.concat("7");
            } else if (data[i] == 8) {
                hex = hex.concat("8");
                //the rest of the statements use the same logic.
            } else if (data[i] == 9) {
                hex = hex.concat("9");
            } else if (data[i] == 10) {
                hex = hex.concat("a");
            } else if (data[i] == 11) {
                hex = hex.concat("b");
            } else if (data[i] == 12) {
                hex = hex.concat("c");
            } else if (data[i] == 13) {
                hex = hex.concat("d");
            } else if (data[i] == 14) {
                hex = hex.concat("e");
            } else if (data[i] == 15) {
                hex = hex.concat("f");
            }
        }
        // a string is returned
        return hex;
    }

    public static int countRuns(byte[] flatData) {
        //variables are made to count the number of runs and the number of times a variable continuously appears in the input array
        int i;
        int runCount = 1;
        int streakCount = 1;
        for (i = 0; i < flatData.length; i++) {
            //if the element is the last element in the array, then runcount remains the same since if the previous element is the same, then runcount is the same and if the previous element is different, runcount is immediately increased before the next iteration.
            if (i == flatData.length - 1) {
                runCount = runCount;
            }
            else if (streakCount < 15) {
                if (flatData[i] == flatData[i + 1]) {
                    runCount = runCount;
                    streakCount++;
                } else {
                    runCount++;
                    streakCount = 1;
                }
            }
            // when a streak longer than or equal to 15 elements is reached, a new run is started
            else if (streakCount >= 15) {
                runCount++;
                streakCount = 1;
            }


        }
        //an int is returned
        return runCount;
    }

    public static byte[] encodeRle(byte[] flatData) {
        int i;
        byte[] b;
        // the previous method is used to define the length of the output array, b
        countRuns(flatData);

        b = new byte[2 * countRuns(flatData)];
        byte numCount = 1;
        int b_index = 0;
        //a for loop is created that increases the numcount variable (tracks number of variables in a run) and b_index, which is the index of the output array
        for (i = 0; i < flatData.length; i++) {
            if (i != flatData.length - 1) {
                if (numCount < 15) {
                    if (flatData[i] == flatData[i + 1]) {
                        // if consecutive variables are alike, then numcount increases, the index of b is equated to numcount, and the next index of b is the value of the byte from flatData in the iteration
                        numCount++;
                        b[b_index] = numCount;
                        b[b_index + 1] = flatData[i];
                    }
                    else {
                        // otherwise the numcount variable is reset and then the value of the b_index is increased by 2 to represent the next run
                        b[b_index] = numCount;
                        numCount = 1;
                        b[b_index + 1] = flatData[i];
                        b_index = b_index + 2;
                    }
                }
                else if (numCount >= 15) {
                    // numcount must reset if the run length exceeds 15 as well
                    numCount = 1;
                    b_index = b_index + 2;
                    b[b_index + 1] = flatData[i];
                }
            }
// if the element in the current iteration is the last variable, then the following commands are carried out
            else if (i == flatData.length - 1) {
                if (flatData[i] != flatData[i - 1]) {
                    //the current index of b is updated and then the next index equals the value of the byte in the current iteration
                    b[b_index] = numCount;
                }
                b[b_index + 1] = flatData[i];
                break;
            }
        }
        // a byte array is returned
        return b;
    }

    public static int getDecodedLength(byte[] rleData) {
        // this method uses a for loop that sums up every other index starting with the first one
        int i;
        int sizeCount = 0;
        for (i = 0; i < rleData.length; i = i + 2) {
            sizeCount = sizeCount + rleData[i];
        }
        return sizeCount;
    }

    public static byte[] decodeRle(byte[] rleData) {
        // this method uses a nested for loop that takes every other element and then iteratively prints the value of the next element since the even indexes have run count and the odd elements are the value of the elements in the run.
        int i;
        int j;
        int b_index = 0;
        byte[] b = new byte[getDecodedLength(rleData)];
        for (i = 1; i < rleData.length; i = i + 2) {
            for (j = 1; j <= rleData[i - 1]; j++) {
                b[b_index] = rleData[i];
                b_index++;
            }
        }
        return b;
    }

    public static byte[] stringToData(String dataString) {
        // This method introduces a null byte array equal to the length of the input string
        byte[] b = new byte[dataString.length()];
        int i;
        // A for loop is used to convert each element of the string individually from hexadecimal to decimal
        for (i = 0; i < b.length; i++) {
            if (dataString.charAt(i) == 0) {
                b[i] = 0;
            } else if (dataString.charAt(i) == '1') {
                b[i] = 1;
            } else if (dataString.charAt(i) == '2') {
                b[i] = 2;
            } else if (dataString.charAt(i) == '3') {
                b[i] = 3;
            } else if (dataString.charAt(i) == '4') {
                b[i] = 4;
            } else if (dataString.charAt(i) == '5') {
                b[i] = 5;
            } else if (dataString.charAt(i) == '6') {
                b[i] = 6;
            } else if (dataString.charAt(i) == '7') {
                b[i] = 7;
            } else if (dataString.charAt(i) == '8') {
                b[i] = 8;
                //The if else if statements are used to convert each individual element from the string and add it to the output array
            } else if (dataString.charAt(i) == '9') {
                b[i] = 9;
            } else if (dataString.charAt(i) == 'a') {
                b[i] = 10;
            } else if (dataString.charAt(i) == 'b') {
                b[i] = 11;
            } else if (dataString.charAt(i) == 'c') {
                b[i] = 12;
            } else if (dataString.charAt(i) == 'd') {
                b[i] = 13;
            } else if (dataString.charAt(i) == 'e') {
                b[i] = 14;
            } else if (dataString.charAt(i) == 'f') {
                b[i] = 15;
            }
        }
        // a byte array is returned
        return b;
    }

    public static String toRleString(byte[] rleData) {
        int i;
        String s = "";
        for (i = 0; i < rleData.length; i = i + 2) {
            // this method uses a for loop that first adds elements with even indexes from the input array to the output string
            s = s + rleData[i];
            // then if else if statements are used to convert the value of odd elements from decimal to hexadecimal values. These are added to the output string.
            if (rleData[i + 1] == 0) {
                s = s + "0";
            } else if (rleData[i + 1] == 1) {
                s = s + "1";
            } else if (rleData[i + 1] == 2) {
                s = s + "2";
            } else if (rleData[i + 1] == 3) {
                s = s + "3";
            } else if (rleData[i + 1] == 4) {
                s = s + "4";
            } else if (rleData[i + 1] == 5) {
                s = s + "5";
            } else if (rleData[i + 1] == 6) {
                s = s + "6";
            } else if (rleData[i + 1] == 7) {
                s = s + "7";
            } else if (rleData[i + 1] == 8) {
                s = s + "8";
                // the rest of the else if statements are the same.
            } else if (rleData[i + 1] == 9) {
                s = s + "9";
            } else if (rleData[i + 1] == 10) {
                s = s + "a";
            } else if (rleData[i + 1] == 11) {
                s = s + "b";
            } else if (rleData[i + 1] == 12) {
                s = s + "c";
            } else if (rleData[i + 1] == 13) {
                s = s + "d";
            } else if (rleData[i + 1] == 14) {
                s = s + "e";
            } else if (rleData[i + 1] == 15) {
                s = s + "f";
            }
            // at the end of the iteration, a delimiter is added and then the value of i is increased by 2 to get to the next even index
            s = s + ":";

        }
        // since a delimiter is added to the end of each iteration, the following command removes the last delimiter which is unnecessary.
        s = s.substring(0, s.length() - 1);
        // a string is returned.
        return s;
    }

    public static byte[] stringToRle(String rleString) {
        // the input is turned into a string array split by the delimiter
        String[] b = rleString.split(":");
        int i;
        // the output array, c, is introduced with length equal to 2 times the length of b
        byte[] c = new byte[b.length * 2];
        int c_index = 0;
        for (i = 0; i < b.length; i++) {
            // each string in b either has a length of 2 when the first number is single digit, or 3 when the first number is double digit.
            // if length is 2, we introduce a substring including the first element of the string and then parse it into a byte and store it in c. The same is done with the next element and we use a radix of 16 to convert from hexadecimal to decimal
            if (b[i].length() == 2) {
                byte d = Byte.parseByte(b[i].substring(0, 1));
                c[c_index] = d;
                c_index++;
                byte e = Byte.parseByte(b[i].substring(1,2), 16);
                c[c_index] = e;
                c_index++;
            }
            // the only difference here is for the first element, we parse a substring of length 2 instead.
            else if (b[i].length() == 3) {
                byte f = Byte.parseByte(b[i].substring(0, 2));
                c[c_index] = f;
                c_index++;
                byte g = Byte.parseByte(b[i].substring(2,3), 16);
                c[c_index] = g;
                c_index++;
            }
        }
        // a byte array is returned
        return c;
    }


    public static void main(String[] args) {
        // userInput is for the scanner input of the option. userInput1 is for the data input. data1 is for the processed data in each option.
        int userInput;
        String userInput1;
        byte[] data1 = null;

        // the welcome message is printed
        Scanner scnr = new Scanner(System.in);
        System.out.println("Welcome to the RLE image encoder!");
        System.out.println();
        System.out.println("Displaying Spectrum Image:");

        // the testRainbow image is printed
        ConsoleGfx.displayImage(ConsoleGfx.testRainbow);
        byte[] imageData = null;

        // the menu is printed and an input option is given
        Menu();
        System.out.print("Select a Menu Option: ");
        userInput = scnr.nextInt();

        // a while loop is started with a condition that is always true because the while loop should only be exited when the user inputs option 0.
        while (userInput > 0 || userInput <= 0) {
            // this breaks the loop
            if (userInput == 0) {
                break;
            }
            // uses ConsoleGfx to load the image inputted by the user.
            if (userInput == 1) {
                System.out.print("Enter name of file to load:");
                userInput1 = scnr.next();
                imageData = ConsoleGfx.loadFile(userInput1);
            }
            // ConsoleGfx loads the test image
            else if (userInput == 2) {
                imageData = ConsoleGfx.testImage;
                System.out.println("Test image data loaded.");
            }
            // the RLE string inputted becomes converted to an RLE array and then converted to a flat array with the mentioned methods that are fitted one inside of another. This is stored in data1
            else if (userInput == 3) {
                System.out.print("Enter an RLE string to be decoded: ");
                userInput1 = scnr.next();
                stringToRle(userInput1);
                data1 = decodeRle(stringToRle(userInput1));
            }
            // a conversion is made using two methods like the last option converting from a non-delimited string to a flat array. stored in data1
            else if (userInput == 4) {
                System.out.print("Enter the hex string holding RLE data: ");
                userInput1 = scnr.next();
                data1 = decodeRle(stringToData(userInput1));
            }
            // converts straight to a flat array with one method and is stored in data1
            else if (userInput == 5) {
                System.out.print("Enter the hex string holding flat data: ");
                userInput1 = scnr.next();
                stringToData(userInput1);
                data1 = stringToData(userInput1);
            }
            // displays image given from data inputted in option 1. otherwise it prints "no data"
            else if (userInput == 6) {
                System.out.println("Displaying image...");
                if (imageData == null) {
                    System.out.print("(no data)");
                }
                else {
                    ConsoleGfx.displayImage(imageData);
                }
            }
            // converts to an RLE array and then a string that is delimited. uses data from data1. if data1 is null, then "no data" is printed
            else if (userInput == 7) {
                System.out.print("RLE representation: ");
                if (data1 == null) {
                    System.out.print("(no data)");
                }
                else {
                    System.out.print(toRleString(encodeRle(data1)));
                }
            }
            // does the same thing as option 7 except outputs without delimiters. uses data1 as well. if data1 is null, then "no data" is printed
            else if (userInput == 8) {
                System.out.print("RLE hex values: ");
                if (data1 == null) {
                    System.out.print("(no data)");
                }
                else {
                    System.out.print(toHexString(encodeRle(data1)));
                }
            }
            // converts to a flat string using one method. Uses data1, and if this is null, then "no data" is returned.
            else if (userInput == 9) {
                System.out.print("Flat hex values: ");
                if (data1 == null) {
                    System.out.print("(no data)");
                }
                else {
                    System.out.print(toHexString(data1));
                }
            }
            // if something outside of 0-9 is inputted, an error statement is printed
            else {
                System.out.println("Error! Invalid input.");
            }
            // after each option is completed, a menu statement is printed again and the loop restarts.
            System.out.println();
            Menu();
            System.out.print("Select a Menu Option: ");
            userInput = scnr.nextInt();

        }


    }
}

