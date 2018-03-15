
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author Kelsey McKenna
 */
public class CLI {

    /**
     * Loads words (lines) from the given file and inserts them into
     * a dictionary.
     *
     * @param f the file from which the words will be loaded
     * @return the dictionary with the words loaded from the given file
     * @throws IOException if there was a problem opening/reading from the file
     */
    
    static int popularity = -1;
    static DictionaryTree loadWords(File f) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String word;
            DictionaryTree d = new DictionaryTree();
            while ((word = reader.readLine()) != null) {
                d.insert(word,popularity);
                popularity--;
            }
            return d;
        }
    }
    
    static DictionaryTree testLoader(){
        DictionaryTree d = new DictionaryTree();
        String [] testers = new String [3];
        testers[0] = "hello";
        testers[1] = "ham";
        testers[2] = "getOut";
//        testers[3] = "adam";
//        testers[4] = "kona";
//        testers[5] = "wagwan";
//        testers[6] = "homie";
//        testers[7] = "migos";
//        testers[8] = "chance";
//        testers[9] = "kanye";
        
        for(String s: testers){
            d.insert(s);
        }
        return d;
    }
    
    public static void main(String[] args) throws IOException {
        System.out.print("Loading dictionary ... ");
        DictionaryTree d = loadWords(new File(args[0]));
//        DictionaryTree d = testLoader();
        System.out.println("done");
        System.out.println("Height: " + d.height());
        System.out.println("Size: " + d.size());
        System.out.println("Longest Word: " + d.longestWord());
        System.out.println("Max branching: " + d.maximumBranching());
        System.out.println("Num Leaves: " + d.numLeaves());
        System.out.println("Number of words: " + d.allWords().size());
        System.out.println("Trying to predict: chir " + d.predict("ph",5));

//

//        System.out.println("Enter prefixes for prediction below.");
//
//        try (BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in))) {
//            while (true) {
//                System.out.println("---> " + d.contains(fromUser.readLine()));
//            }
//        }
    }

}
