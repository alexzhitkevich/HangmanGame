import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

/**
 ** HANGMAN
 ** Hangman game: A secret word exists. Player must guess the secret word either letter by letter or by inputting the entire word. If guess is incorrect, parts of the hang man will appear.
 */

public class Hangman {
    public static void main(String[] args) {
        boolean playAgain = true;

        do {
            String sports[] = {"SPORTS", "BOBBY ORR", "BASKETBALL", "RACQUETBALL", "SIDNEY CROSBY"};
            String movies[] = {"MOVIES", "AVATAR", "STAR WARS", "THE GODFATHER", "TERMINATOR"};
            String tvShows[] = {"TV SHOWS", "SPONGEBOB SQUAREPANTS", "FAMILY GUY", "LOST", "HOUSE", "SURVIVOR"};
            String categoryTypes[] = {"SPORTS", "MOVIES", "TV SHOWS"};
            String categories[][] = {sports, movies, tvShows};

            int max = 1;
            int min = 0;
            double randWord1 = 0;
            String secretWord = "";

            System.out.println("WELCOME TO HANGMAN");
            System.out.println("------------------");

            double randCategory1 = (Math.random() * ((max - min) + 1) + min);
            int randCategory = (int) (Math.round(randCategory1 * 10 / 10.0));
            System.out.println("CATEGORY: " + categoryTypes[randCategory]);

            int randWord = randWordGenerator(randWord1, randCategory);

            secretWord = categories[randCategory][randWord];

            checkWord(secretWord.toLowerCase());
            //playAgain(playAgain);

            Scanner s = new Scanner(System.in);
            System.out.println("Would you like to play again? Yes/No");
            String userInput = s.nextLine();

            if (userInput.equalsIgnoreCase("Yes")) {
                System.out.println("------------------------------");
                playAgain = true;
            } else if (userInput.equalsIgnoreCase("No")) {
                playAgain = false;
                System.out.println("------------------------------");
                System.out.println("Thank you for playing Hangman!");
            }

        } while (playAgain != false);

    }

    /**
     * Description: Method 'checkWord' prompts the user for their guess and evaluates it in comparison to the secret word.
     * Precondition: For the method to run, the variable 'secretWord' must be initialized to a word (done using the array).
     * Post condition: When the method is complete, the user will know if they won or lost the game based on their guesses.
     * @param secretWord - This parameter is the secret word that is initialized that the user must guess.
     * @return Nothing is returned.
     **/

    public static void checkWord(String secretWord) {
        Scanner s = new Scanner(System.in);
        StringBuilder userWord = new StringBuilder("");
        String guessOfWord = "!";
        boolean done = false;
        boolean guess = false;
        int wrongGuesses = 0;
        ArrayList<String> letterGuesses = new ArrayList<String>();

        //To print the dashes to show the length of the secret word
        for (int i = 0; i < secretWord.length(); i++) {
            userWord.append("-");
        }
        //Checks if the secretWord is more than one word. If true, then it replaces the dash with a space
        if (secretWord.indexOf(" ") > 0) {
            System.out.println("Number of words: 2");
            String space = " ";
            userWord.deleteCharAt(secretWord.indexOf(" "));
            userWord.insert(secretWord.indexOf(" "), space);
        } else {
            System.out.println("Number of words: 1");
        }

        while (!done) {

            System.out.println(userWord);

            wrongGuesses(wrongGuesses, secretWord);

            //Prompts the user to enter a letter
            System.out.println("Enter a letter: (! to guess entire word)");
            String currentInput = (s.nextLine()).toLowerCase();
            char letter = currentInput.charAt(0);
            boolean validInput = false;

            //if more than one letter is entered or a symbol, the program will reprompt the user to enter only one letter or not a symbol
            if (currentInput.length() > 1) {
                System.out.println("Invalid input. Please enter one letter");
            } else if (!(Character.isLetter(letter)) && (letter != '!')) {
                System.out.println("Invalid input. Please enter a letter");
            } else {
                validInput = true;
            }

            if (validInput) {

                if (letterGuesses.size() > 0) {
                    if (letterGuesses.contains(currentInput)) {
                        System.out.println("This letter has already been guessed");
                    } else {
                        letterGuesses.add(currentInput);
                    }
                } else {
                    letterGuesses.add(currentInput);
                }

                System.out.println("Letters guessed: ");

                for (int i = 0; i < letterGuesses.size(); i++) {
                    System.out.print(letterGuesses.get(i) + ", ");
                    System.out.print("");
                }

                System.out.println("");

                //substring will search for if the letter inputted appears more than once in the secret word
                if (currentInput.equalsIgnoreCase(guessOfWord)) {
                    done = true;
                    guess = true;
                } else {
                    int index = -1;
                    do {
                        index = secretWord.indexOf(letter, index + 1);

                        //if the letter is greater than or equal to zero, then the letter guessed will be set at that index in userWord
                        if (index >= 0) {
                            userWord.setCharAt(index, letter);
                        }
                        //if all letters are guessed correctly and secretWord equals to userWord, then the program will break after it shows that you have won
                        if (secretWord.equals(userWord.toString())) {
                            done = true;
                            won(secretWord, letterGuesses);
                            break;
                        }

                    } while (index >= 0);

                    index = secretWord.indexOf(letter, index + 1);

                    if (index < 0) {
                        wrongGuesses++;
                        wrongGuesses(wrongGuesses, secretWord);

                        if (wrongGuesses == 7) {
                            lost(secretWord, letterGuesses);
                            break;
                        }
                    }
                }
                if (guess == true) {
                    checkGuess(secretWord, letterGuesses);
                }

            }
        }

    }

    /**
     * Description: Method 'randWordGenerator' generates a random word from the category specific array once a category is chosen.
     * Precondition: For the method to run, the variable 'randWord1' must be declared and initialized to zero & variable 'randCategory' must be declared and randomized.
     * Post condition: When the method is complete, a random word from the array category will be chosen and can then be initialized to the secretWord in the main method.
     * @param randWord1 - This parameter is needed to randomize the number of the index for the word chosen. It is then rounded to an integer value.
     * @param randCategory - This parameter allows for the game to randomize the category of the secretWord.
     * @return Integer value of randWord is returned.
     **/

    public static int randWordGenerator(double randWord1, double randCategory) {

        int randWord = 0;
        int max = 3;
        int max2 = 4;
        int min = 0;

        if (randCategory == 0) {
            randWord1 = (Math.random() * ((max - min) + 1) + min);
            randWord = (int) (Math.round(randWord1 * 10 / 10.0));

        } else if (randCategory == 1) {
            randWord1 = (Math.random() * ((max - min) + 1) + min);
            randWord = (int) (Math.round(randWord1 * 10 / 10.0));

        } else if (randCategory == 2) {
            randWord1 = (Math.random() * ((max2 - min) + 1) + min);
            randWord = (int) (Math.round(randWord1 * 10 / 10.0));
        }

        return (randWord);

    }

    /**
     * Description: Method 'wrongGuesses' evaluates how many wrong guesses the user has made and prints out the corresponding number of hangman body parts.
     * Precondition: For the method to run, the variable 'wrongGuesses' must be initialized and be greater than zero and 'secretWord' must be declared and initialized.
     * Post condition: When the method is complete and if the user won the game, it will print out that the user has won the game.
     * @param secretWord - This parameter is the secret word that is initialized that the user must guess.
     * @param wrongGuesses - This parameter is the counter for the number of times that the user inputs a wrong guess.
     * @return Nothing is returned.
     **/

    public static void wrongGuesses(int wrongGuesses, String secretWord) {
        String hangman[] = {"   O", " /", " |", " \\", "   |", " /", "  \\"};

        if (wrongGuesses == 1) {
            System.out.println(hangman[0]);
            System.out.println("");
        }
        if (wrongGuesses == 2) {
            System.out.println(hangman[0]);
            System.out.print(hangman[1]);
            System.out.println("");
        }
        if (wrongGuesses == 3) {
            System.out.println(hangman[0]);
            System.out.print(hangman[1]);
            System.out.print(hangman[2]);
            System.out.println("");
        }
        if (wrongGuesses == 4) {
            System.out.println(hangman[0]);
            System.out.print(hangman[1]);
            System.out.print(hangman[2]);
            System.out.print(hangman[3]);
            System.out.println("");
        }
        if (wrongGuesses == 5) {
            System.out.println(hangman[0]);
            System.out.print(hangman[1]);
            System.out.print(hangman[2]);
            System.out.print(hangman[3]);
            System.out.println("");
            System.out.print(hangman[4]);
            System.out.println("");
        }
        if (wrongGuesses == 6) {
            System.out.println(hangman[0]);
            System.out.print(hangman[1]);
            System.out.print(hangman[2]);
            System.out.print(hangman[3]);
            System.out.println("");
            System.out.print(hangman[4]);
            System.out.println("");
            System.out.print(hangman[5]);
            System.out.println("");
        }
        if (wrongGuesses == 7) {
            System.out.println(hangman[0]);
            System.out.print(hangman[1]);
            System.out.print(hangman[2]);
            System.out.print(hangman[3]);
            System.out.println("");
            System.out.print(hangman[4]);
            System.out.println("");
            System.out.print(hangman[5]);
            System.out.print(hangman[6]);
            System.out.println("");
        }

    }

    /**
     * Description: Method 'won' prints all the information that is needed to be shown if the player wins the game.
     * Precondition: For the method to run, the variable 'secretWord' must be initialized to a word and 'letterGuesses' must have a valid value.
     * Post condition: When the method is complete and if the user won the game, it will print out that the user has won the game.
     * @param secretWord - This parameter is the secret word that is initialized that the user must guess.
     * @param letterGuesses - This parameter shows the number of times that the user inputs a guess.
     * @return Nothing is returned.
     **/

    public static void won(String secretWord, ArrayList<String> letterGuesses) {
        System.out.println(secretWord.toUpperCase());
        System.out.println("Congratulations! You guessed correctly");
        System.out.println("Total guesses: " + letterGuesses.size());

    }

    /**
     * Description: Method 'lost' prints all the information that is needed to be shown if the player loses the game.
     * Precondition: For the method to run, the variable 'secretWord' must be initialized to a word and 'letterGuesses' must have a valid value.
     * Post condition: When the method is complete and if the user lost the game, it will print out that the user has lost the game .
     * @param secretWord - This parameter is the secret word that is initialized inputted that the user must guess.
     * @param letterGuesses - This parameter shows the number of times that the user inputs a guess.
     * @return Nothing is returned.
     **/

    public static void lost(String secretWord, ArrayList<String> letterGuesses) {
        System.out.println(secretWord.toUpperCase());
        System.out.println("You lost :( ");
        System.out.println("Total guesses: " + letterGuesses.size());
    }

    /**
     * Description: Method 'checkGuess' evaluates if the word guessed by the user is correct or not. If correct, goes to won method. If incorrect, goes to lost method.
     * Precondition: For the method to run, the variable 'secretWord' must be initialized to a word and 'guesses' must have a valid value.
     * Post condition: When the method is complete, it will print out if the user has won or lost the game and the game ends.
     * @param secretWord - This parameter is the secret word that is initialized inputted that the user must guess.
     * @param letterGuesses - This parameter is used to count the number of times that the user inputs a guess.
     * @return Nothing is returned.
     **/

    public static void checkGuess(String secretWord, ArrayList<String> letterGuesses) {
        Scanner t = new Scanner(System.in);

        System.out.println("What is your guess?");
        String userGuess = (t.nextLine()).toLowerCase();
        if (userGuess.equals(secretWord)) {
            won(secretWord, letterGuesses);
        } else {
            lost(secretWord, letterGuesses);

        }
    }
}
