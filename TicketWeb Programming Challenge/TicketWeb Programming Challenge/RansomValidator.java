import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;


//Run main method in RansomValidator class for Test Results
//main method is the final method in the class
public class RansomValidator {

    //class instance of wordDictionary for bonus challenge
    private static final Map<String, String> wordDictionary = new HashMap<>();


    //helper method to create a hashmap of how often each word is in text
    //prevents words from being used multiple times in ransom note
    //takes output of process helper method as input
    //outputs word frequency hashmap
    private static Map<String, Integer> wordUseCounter(String text){
         //creates output of list of words
        List<String> words = new ArrayList<>();

        //initializes each word
        String word = "";

        //loops through text incrementing by character
        for (int i = 0; i < text.length(); i ++){

            //retrieve the character
            char character = text.charAt(i);

            //if character is a letter of number add that character to word
            if (Character.isLetterOrDigit(character)){
                word += Character.toLowerCase(character);
            }

            // if character is not letter of number it must be space of punctuation
            // the word is complete and is added to words
            //re-initialize word
            else if (!word.isEmpty()){
                words.add(word);
                word = "";
            }
        }
        //adds final word in text to words
        if (!word.isEmpty()){
            words.add(word);
        }

        Map<String, Integer> wordFrequency = new HashMap<>();

        // loops through each word
        for (String wordInList : words){
            //retrieve current word frequency and increase count
            //if word not in hashmap already add word as key and 1 as value
            if (!wordInList.isEmpty()) {
                wordFrequency.put(wordInList, wordFrequency.getOrDefault(wordInList, 0) + 1);
            }
        }

        //return word frequency hashmap
        return wordFrequency;
    }

    //method to determine if ransom note can be made from magazine
    //takes input ransom and magazine as parameters
    //output true if ransom can be made from magazine
    //outputs false if ransom cannot be made from magazine
    public static boolean validateRansom(String ransomLetter, String magazine){


        //creates word list and word frequency hashmap for both magazine and ransom note
        Map<String, Integer> ransomWordFrequency = wordUseCounter(ransomLetter);
        Map<String, Integer> magazineWordFrequency = wordUseCounter(magazine);

        //loop through each word in ransom note
        for (String word : ransomWordFrequency.keySet()) {

            //if there are more occurrences of a word in the ransom note than magazine return false
            //if word is not in magazine default is 0, will result in returning false
            if (magazineWordFrequency.getOrDefault(word, 0) < ransomWordFrequency.get(word)){
                return false;
            }
        }

        return true;
    }


    //helper method for bons challenge
    //sorts words by letters
    //returns sorted String
    private static String sortWordByLetter(String word){
        //array of characters of word
        char[] characters = word.toUpperCase().toCharArray();

        //sort words by letter
        Arrays.sort(characters);

        //return new sorted string
        return new String(characters);
    }

    //method called in main to populate word dictionary
    private static void populateDictionary(){
        //list of words in dictionary
        //current Dictionary is list of given test message + most common english words + words associated with ransom notes
          List<String> words = Arrays.asList(
                  "please", "leave", "the", "money", "under", "bench",
                  "help", "save", "run", "plan", "pay", "be", "to",
                  "of", "and", "a", "in", "that", "have", "I", "it", "for",
                  "not", "on", "with", "he", "as", "you", "do", "at", "this",
                  "but", "his", "by", "from", "they", "we", "say", "her", "she",
                  "or", "an", "will", "my", "one", "all", "would", "there", "their",
                  "what", "so", "up", "out", "if", "about", "who", "get",
                  "which", "go", "me", "pay", "now", "time", "you", "will", "send", "contact", "kill", "not",
                  "do", "your", "life", "we", "have", "if", "want", "meet", "must", "take", "done",
                  "see", "dead", "wait", "deadly", "demands", "safe", "here", "no", "call",
                  "instructions", "nothing", "hurt", "payment", "threat", "action", "disappear",
                  "decision", "nowhere", "place", "drop", "don't", "keep", "willing", "go",
                  "watch", "threaten", "expect", "final"
        );

          // goes through each word in list
        for (String word : words) {
            //sorts the word by letter
            String wordLettersSorted = sortWordByLetter(word);

            //adds word to dictionary with the word sorted by letter as the key
            // and the uppercase word the value
            wordDictionary.put(wordLettersSorted, word.toUpperCase());
        }
    }


    //solves anagram string
    //returns decoded massage
    public static String anagramSolver(String anagram){
        //covering edge case
        if (anagram.trim().isEmpty()){
            return "No words in anagram message";
        }
        //splits anagram up into list of words
        String[] words = anagram.split(" ");

        // initializes final decoded string
        String message = "";

        //loops through each word
        for (String word : words){
            //calls helper method to sort word
            String sortedWord = sortWordByLetter(word);

            //retrieving proper word of the anagram from dictionary
            //if word is not in dictionary default behavior is to say the word is not in the dictionary
            String decodedWord = wordDictionary.getOrDefault(sortedWord, sortedWord + " is not in dictionary");

            //adding newly decoded word to message
            message += decodedWord + " ";
        }
        // returns message with no extra whitespace
        return message.trim();

    }



    public static void main(String[] args) {
        //ransom note test cases
        //run main method to see test results of ransom note challenge and bonus challenge
        String ransom = """
                We have your dog Sir Snugglesworth. If you ever want to see him again,
                leave 10,000 in small bills under the city park bench at 2:00 pm.
                If you really really love your dog, DO NOT GO TO THE POLICE.
                """;
        Path magazine2Path = Paths.get("magazine2.txt");
        Path magazine1Path = Paths.get("magazine1.txt");
        try {
            int i = 0;
            String magazine2 = Files.readString(magazine2Path);
            String magazine1 = Files.readString(magazine1Path);
            boolean valid1 = validateRansom(ransom, magazine1);
            boolean valid2 = validateRansom(ransom, magazine2);
            System.out.println("Ransom Note Challenge Testing Results:");

              //test 1
            System.out.println("\n    Test 1- Magazine 1:");
            if (valid1){
                System.out.println("        Magazine 1 can create ransom");
                System.out.println("        Test Pass");
                i += 1;
            }
            else{
                System.out.println("        Magazine 1 cannot create ransom");
                System.out.println("        Test Fail");
            }

            //test 2
            System.out.println("\n    Test 2- Magazine 2:");
            if (valid2){
                System.out.println("        Magazine 2 can create ransom");
                System.out.println("        Test Fail");
            }
            else{
                System.out.println("        Magazine 2 cannot create ransom");
                System.out.println("        Test Pass");
                i += 1;
            }

            //test 3
            System.out.println("\n    Test 3- Empty Magazine:");
            String magazine3 = "";
            boolean valid3 = validateRansom(ransom, magazine3);
            if (valid3){
                System.out.println("        Magazine can create ransom");
                System.out.println("        Test Fail");
            }
            else{
                System.out.println("        Magazine cannot create ransom");
                System.out.println("        Test Pass");
                i += 1;
            }

            //test 4
            System.out.println("\n    Test 4- Empty Note:");
            boolean valid4 = validateRansom("", magazine1);
            if (valid4){
                System.out.println("        Magazine can create ransom");
                System.out.println("        Test Pass");
                i += 1;
            }
            else{
                System.out.println("        Magazine cannot create ransom");
                System.out.println("        Test Fail");
            }

            //test 5
            System.out.println("\n    Test 5- Only Space in Ransom Note");
            boolean valid5 = validateRansom(" ", magazine1);
            if (valid5){
                System.out.println("        Magazine can create ransom");
                System.out.println("        Test Pass");
                i += 1;
            }
            else{
                System.out.println("        Magazine cannot create ransom");
                System.out.println("        Test Fail");
            }

            //test 6
            System.out.println("\n    Test 6- Only Space in Magazine");
            boolean valid6 = validateRansom(ransom, " ");
            if (valid6){
                System.out.println("        Magazine can create ransom");
                System.out.println("        Test Fail");
            }
            else{
                System.out.println("        Magazine cannot create ransom");
                System.out.println("        Test Pass");
                i += 1;
            }

            //test 7
            System.out.println("\n    Test 7- Ransom Note and Magazine is the Same");
            boolean valid7 = validateRansom("Hello Give Me Money", "Hello Give Me Money");
            if (valid7){
                System.out.println("        Magazine can create ransom");
                System.out.println("        Test Pass");
                i += 1;
            }
            else{
                System.out.println("        Magazine cannot create ransom");
                System.out.println("        Test Fail");
            }

            //test 8
            System.out.println("\n    Test 8- Duplicate Words in Ransom Note");
            boolean valid8 = validateRansom("Hi Hi", "Hi");
            if (valid8){
                System.out.println("        Magazine can create ransom");
                System.out.println("        Test Fail");
            }
            else{
                System.out.println("        Magazine cannot create ransom");
                System.out.println("        Test Pass");
                i += 1;
            }

            //test 9
            System.out.println("\n    Test 9- Duplicate Words in Magazine");
            boolean valid9 = validateRansom("Hi", "Hi Hi");
            if (valid9){
                System.out.println("        Magazine can create ransom");
                System.out.println("        Test Pass");
                i += 1;
            }
            else{
                System.out.println("        Magazine cannot create ransom");
                System.out.println("        Test Fail");
            }

             //test 10
            System.out.println("\n    Test 10- Digits");
            boolean valid10 = validateRansom("10,000", "10,000");
            if (valid10) {
                System.out.println("        Magazine can create ransom");
                System.out.println("        Test Pass");
                i += 1;
            }


            //test 11
            System.out.println("\n    Test 11- Multiple Digits");
            boolean valid11 = validateRansom("10,000 10,000", "10,000 10,000");
            if (valid11){
                System.out.println("        Magazine can create ransom");
                System.out.println("        Test Pass");
                i += 1;
            }

            else{
                System.out.println("        Magazine cannot create ransom");
                System.out.println("        Test Fail");
            }




            //bonus challenge test cases
            int j = 0;
            System.out.println("\nBonus Challenge Test Results:");
            populateDictionary();

            //test 1
            String inputMessage ="SLPEAE EAVLE HET MNEOY NDURE HET ENCHB";
            String actualOutput = anagramSolver(inputMessage);
            String expectedOutput = "PLEASE LEAVE THE MONEY UNDER THE BENCH";
            System.out.println("\n    Test 1- Given Test");
            if (actualOutput.equals(expectedOutput)){
                System.out.println("        Correct Result: " + expectedOutput);
                System.out.println("        Test Pass");
                j += 1;
            }else{
                System.out.println("        Program got ("+ actualOutput+ ") when correct solution was ("+ expectedOutput+")");
                System.out.println("        Test Fail");
            }

            //test 2
            System.out.println("\n    Test 2- Word not in dictionary");
            String inputMessage2 ="SLPEAE EAVLE HET MNEOY NDURE HET lwo";
            String actualOutput2 = anagramSolver(inputMessage2);
            System.out.println("        Result: " + actualOutput2);

            if (actualOutput2.equals("PLEASE LEAVE THE MONEY UNDER THE LOW is not in dictionary")){
                j += 1;
                System.out.println("        Test Pass");
            }else{
                System.out.println("        Test Fail");
            }

            //test 3
            System.out.println("\n    Test 3- Empty String input");
            String inputMessage3 ="";
            String actualOutput3 = anagramSolver(inputMessage3);
            System.out.println("        Result: " + actualOutput3);
            if (actualOutput3.equals("No words in anagram message")){
                j += 1;
                System.out.println("        Test Pass");
            }else{
                System.out.println("        Test Fail");
            }

            //test 4
            System.out.println("\n    Test 4- Only Space String input");
            String inputMessage4 =" ";
            String actualOutput4 = anagramSolver(inputMessage4);
            System.out.println("        Result: " + actualOutput4);
            if (actualOutput3.equals("No words in anagram message")){
                j += 1;
                System.out.println("        Test Pass");
            }else{
                System.out.println("        Test Fail");
            }

            //checks if all tests pass Random Note Challenge
            if (i ==11){
                System.out.println("\nAll Test Pass for Random Note Challenge");
            }
            else{
                System.out.println("\nAt Least One Test Did Not Pass Random Note Challenge");
            }

            //checks all test pass Bonus Challenge
            if (j == 4){
                 System.out.println("\nAll Test Pass for  Bonus Challenge");
            }
            else{
                System.out.println("\nAt Least One Test Did Not Pass Bonus Challenge");
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
