import java.util.*;
import java.io.*;

public class Thiseas {
    public static void main(String[] args) {
        try {
            File Labyrinth = new File(args[0]);                 // create a File object from the .txt file given by the user
            Scanner myReader = new Scanner(Labyrinth);          // create a Scanner object in order to read the file

            String line = myReader.nextLine().trim();           // read the first line

            String temp = readDim(line, 0);                // read the Labyrinth's length
            int length = Integer.parseInt(temp);                // save it as integer
            int width = Integer.parseInt(readDim(line + " ", temp.length() + 1));  // read the Labyrinth's width and save it as integer

            char[][] myArray = new char[length][width];         // create a 2 dimensional character array with the given dimensions

            line = myReader.nextLine().trim();                  // read the second line

            temp = readDim(line, 0);                         // read the Entrance's coordinates
            int Ei = Integer.parseInt(temp);
            int Ej = Integer.parseInt(readDim(line + " ", temp.length() + 1));

            int Ecount = 0;              // this variable checks if there have been given multiple entrances in the Labyrinth

            int l = 0;
            while (myReader.hasNextLine()) {                    // read the Labyrinth from the .txt file line by line
                int w = 0;
                line = myReader.nextLine().trim();
                for (int n = 0; n < line.length(); n=n+2) {
                    char c = line.charAt(n);                        // save the character in c (variable)
                    if ((c == '1') || (c == '0') || (c == 'E')){    // check if c is a valid character
                        if (c == 'E'){                              // case c is the entrance
                            if (Ecount == 0){                       // case if c is the first given entrance
                                Ecount++;                           // increase the found entrances number by 1
                                if ((Ei != l) || (Ej != w)){        // check if the entrance is at the coordinates given in line 2
                                     System.out.println("The coordinates given for the Entrance do not match the Labyrinth's depiction.");
                                    return;
                                }
                            } else {                                // case if  there have been given more than 1 entrances
                                System.out.println("You've given 2 or more Entrances.");
                                return;
                            }

                        }
                        myArray[l][n/2] = c;                        // insert the character to the array
                    } else {                                        // case invalid character given
                        System.out.println("Invalid Character.");
                        return;
                    }
                    w++;
                }
                if (w != width){                 // check if there have been given columns than the dimensions in line 2 suggest
                    System.out.println("The given dimensions do not match the Labyrinth's.");
                    return;
                }
                l++;
            }
            if (l != length){                    // check if there have been given less rows than the dimensions in line 2 suggest
                System.out.println("The given dimensions do not match the Labyrinth's.");
            }
            myReader.close();                    // close the Scanner object

            // The Labyrinth has been read successfully without throwing an exception

            StringStackImpl stack = new StringStackImpl();          // create stack in order to use backtracking

            char[][] bigArray = new char[length+2][width+2];        // create bigArray
            // the bigArray has the contents of myArray with one extra line surrounding them on the top, the bottom etc.
            // this utilises the padding method and allows for a much simpler solution.
            // as padding we use the blank character (' ').

            for (int g = 0; g < length + 2; g++) {
                bigArray[g][0] = ' ';
                bigArray[g][width + 1] = ' ';
            }

            for (int g = 1; g<width+1; g++){
                bigArray[0][g]=' ';
                bigArray[length+1][g]=' ';
            }

            for (int y=0; y < length;y++){
                for (int g=0; g < width; g++){
                    bigArray[y+1][g+1] = myArray[y][g];
                }
            }

            Ei++;               // change the entrances coordinates to suit bigArray
            Ej++;
            int i = Ei;         // set starting position
            int j = Ej;

            while (true){
                boolean move = false;                    // this variable helps check if a new move has been made
                if (bigArray[i+1][j] == '0'){            // check if "down" is a possible move
                    move = true;
                    stack.push("down");             // put the move "down" to the stack
                    bigArray[i+1][j] = 'B';              // change the current character to 'B', to show we have Been there
                    i++;
                    if (CheckW( bigArray, i,j)){         // check if we found the exit
                        i--;
                        j--;
                        System.out.println("You have exited the labyrinth. The exit's coordinates are: "+i+", "+j +".");
                        break;
                    }
                }

                if (bigArray[i][j + 1] == '0') {            // check if "right" is a possible move
                    move = true;
                    stack.push("right");               // put the move "right" to the stack
                    bigArray[i][j+1] = 'B';                 // change the current character to 'B', to show we have Been there
                    j++;
                    if (CheckW( bigArray, i,j)){            // check if we found the exit
                        i--;
                        j--;
                        System.out.println("You have exited the labyrinth. The exit's coordinates are: "+i+", "+j +".");
                        break;
                    }
                }

                if (bigArray[i][j - 1] == '0') {            // check if "left" is a possible move
                    move = true;
                    stack.push("left");                // put the move "left" to the stack
                    bigArray[i][j-1] = 'B';                 // change the current character to 'B', to show we have Been there
                    j--;
                    if (CheckW( bigArray, i,j)){            // check if we found the exit
                        i--;
                        j--;
                        System.out.println("You have exited the labyrinth. The exit's coordinates are: "+ i +", "+j +".");
                        break;
                    }
                }

                if (bigArray[i-1][j] == '0') {              // check if "up" is a possible move
                    move = true;
                    stack.push("up");                  // put the move "up" to the stack
                    bigArray[i-1][j] = 'B';                 // change the current character to 'B', to show we have Been there
                    i--;
                    if (CheckW( bigArray, i,j)){            // check if we found the exit
                        i--;
                        j--;
                        System.out.println("You have exited the labyrinth. The exit's coordinates are: "+i+", "+j +".");
                        break;
                    }
                }

                if (!move){                                 // check if no move was made
                    bigArray[i][j] = '1';                   // replace the character we are on with '1'
                    String temp1 = stack.pop();             // use backtracking and do the reverse move from the one on the top of the stack
                    if (Objects.equals(temp1, "right")){
                        j--;
                    } else if (Objects.equals(temp1, "left")){
                        j++;
                    } else if (Objects.equals(temp1, "down")){
                        i--;
                    } else {
                        i++;
                    }
                    if (CheckL(bigArray,i,j, Ei, Ej)){              // check if there is no exit
                        System.out.println("There is no exit.");
                        break;
                    }
                }
            }

        } catch (Exception e) {         // catch exception during the reading the .txt file
            System.out.println("The given dimensions do not match the Labyrinth's.");
        }

    }

    public static String readDim(String line, int start){
        // read 1 of the 2 dimensions that are given for the Labyrinth
        // the purpose of this method is to read if the given dimension is more than one character (ex. 12, 345, 6789 etc.)
        String s = "";
        String it = Character.toString(line.charAt(start));
        int r = start + 1;
        while ((r<=line.length()) && (!it.equals(" "))){
            s +=  it;
            it = Character.toString(line.charAt(r));
            r++;
        }
        return s;
    }

    public static boolean CheckW(char[][] Arr,int i, int j){
        // check the exit has been found
        return Arr[i][j] == 'B' && (Arr[i + 1][j] == ' ') || (Arr[i - 1][j] == ' ') || (Arr[i][j + 1] == ' ') || (Arr[i][j - 1] == ' ');

    }
    public static boolean CheckL(char[][] Arr,int i,int j, int Ei, int Ej){
        //Check if there is no exit
        return (i == Ei) && (j == Ej)
                && ((Arr[i + 1][j] == '1') || (Arr[i + 1][j] == ' '))
                && ((Arr[i][j + 1] == '1') || (Arr[i][j + 1] == ' '))
                && ((Arr[i][j - 1] == '1') || (Arr[i][j - 1] == ' '))
                && ((Arr[i - 1][j] == '1') || (Arr[i - 1][j] == ' '));
    }
}
