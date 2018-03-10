import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.BiFunction;

public class DictionaryTree {

    private Map<Character, DictionaryTree> children = new LinkedHashMap<>();
    
    public Word myWord = new Word();
    
    /**
     * Inserts the given word into this dictionary.
     * If the word already exists, nothing will change.
     *
     * @param word the word to insert
     */
    void insert(String word) { //DONE
        insertionHelper(word,word);
    }
    
    void insertionHelper(String currentPortion, String fullWord){
        if(currentPortion.length() == 1){
            if(!children.containsKey(currentPortion.charAt(0))){
                this.children.put(currentPortion.charAt(0), new DictionaryTree());
            }
            children.get(currentPortion.charAt(0)).myWord.completeWord = Optional.of(fullWord);
        }else{
            if(children.containsKey(currentPortion.charAt(0))){
                children.get(currentPortion.charAt(0)).insertionHelper(currentPortion.substring(1), fullWord);
            }else{
                DictionaryTree a = new DictionaryTree();
                a.insertionHelper(currentPortion.substring(1), fullWord);
                children.put(currentPortion.charAt(0), a);
            }
        }
        
    }

    /**
     * Inserts the given word into this dictionary with the given popularity.
     * If the word already exists, the popularity will be overriden by the given value.
     *
     * @param word       the word to insert
     * @param popularity the popularity of the inserted word
     */
    void insert(String word, int popularity) {
        popInsertionHelper(word,word,popularity);
    }
    
    void popInsertionHelper(String currentPortion, String fullWord, Integer popularity){
        if(currentPortion.length() == 1){
            if(!children.containsKey(currentPortion.charAt(0))){
                this.children.put(currentPortion.charAt(0), new DictionaryTree());
            }
            children.get(currentPortion.charAt(0)).myWord.completeWord = Optional.of(fullWord);
            children.get(currentPortion.charAt(0)).myWord.popularity = popularity;
        }else{
            if(children.containsKey(currentPortion.charAt(0))){
                children.get(currentPortion.charAt(0)).popInsertionHelper(currentPortion.substring(1), fullWord, popularity);
            }else{
                DictionaryTree a = new DictionaryTree();
                a.popInsertionHelper(currentPortion.substring(1), fullWord, popularity);
                children.put(currentPortion.charAt(0), a);
            }
        }
        
    }
    /**
     * Removes the specified word from this dictionary.
     * Returns true if the caller can delete this node without losing
     * part of the dictionary, i.e. if this node has no children after
     * deleting the specified word.
     *
     * @param word the word to delete from this dictionary
     * @return whether or not the parent can delete this node from its children
     */
    boolean remove(String word) { //DONE

        if(contains(word)){
            int counter = word.length() - 1;
            for (int i = counter; i >= 0; i--) {
                removalHelper(word.substring(0, i + 1), word.charAt(i), word);
            }
            return true;
        }else{
            return false;
        }
        
        
    }
    
    void removalHelper(String currentPortion,Character letter,  String fullWord){
        if(currentPortion.length() == 1){
            if(children.get(letter).myWord.completeWord.equals(Optional.of(fullWord)) && children.get(letter).children.size() == 0){
                children.remove(letter);
         }else if(children.get(letter).myWord.completeWord.equals(Optional.of(fullWord)) && children.get(letter).children.size() > 0){
                children.get(letter).myWord.completeWord = Optional.empty();
                children.get(letter).myWord.popularity = null;
         }else if(!children.get(letter).myWord.completeWord.isPresent() && children.get(letter).children.size() > 0){
                //do nothing
         }else if(!children.get(letter).myWord.completeWord.isPresent() && children.get(letter).children.size() == 0){
                children.remove(letter);
             
             
         }
         
        }else{
            children.get(currentPortion.charAt(0)).removalHelper(currentPortion.substring(1), letter, fullWord);
        }
    }
    

    /**
     * Determines whether or not the specified word is in this dictionary.
     *
     * @param word the word whose presence will be checked
     * @return true if the specified word is stored in this tree; false otherwise
     */
    boolean contains(String word) { //DONE
        if(word.length() == 1){
            return children.containsKey(word.charAt(0)) && children.get(word.charAt(0)).myWord.completeWord.isPresent();
        }else{
            if(children.containsKey(word.charAt(0))){
                return children.get(word.charAt(0)).contains(word.substring(1));
            }else{
                return false;
            }
        }
    }

    /**
     * @param prefix the prefix of the word returned
     * @return a word that starts with the given prefix, or an empty optional
     * if no such word is found.
     */
    Optional<String> predict(String prefix) { //DONE
        if(prefix == null){
            return Optional.empty();
        }
        if(prefix.length() == 1){
            return children.get(prefix.charAt(0)).predictionHelper();
        }else{
            return children.get(prefix.charAt(0)).predict(prefix.substring(1));
        }
    }
    
    Optional<String> predictionHelper(){
        Optional<String> empty = Optional.of("No Prediction");
        List<String> allWords = allWords();
        List<String> potentials = new ArrayList<>();
        for(String word : allWords){
            potentials.add(word);
        }
        if(potentials.size() == 0){
            return empty;
        }else{
            return Optional.of(potentials.get(0));
        }
    }

    /**
     * Predicts the (at most) n most popular full English words based on the specified prefix.
     * If no word with the specified prefix is found, an empty Optional is returned.
     *
     * @param prefix the prefix of the words found
     * @return the (at most) n most popular words with the specified prefix
     */
    List<String> predict(String prefix, int n) {
        if(prefix == null){
            return new ArrayList<>();
        }
        if(prefix.length() == 1){
            return children.get(prefix.charAt(0)).predictPopHelper(n);
        }else{
            return children.get(prefix.charAt(0)).predict(prefix.substring(1), n);
        }
    }
    
    List<String> predictPopHelper(int n){
        Optional<String> empty = Optional.of("No Prediction");
        List<Word> allWords = popAllWords();
        List<String> finals = new ArrayList<>();
        if(allWords.size() == 0){
            return null;
        }else{
            Collections.sort(allWords, new Word());
            for(int i = 0; i < n; i++){
                finals.add(allWords.get(i).completeWord.get());
            }
            return finals;
        }
    }
    
    List<Word> popAllWords(){ //DONE
        
        List<Word> a = new ArrayList<>();
        if(myWord.completeWord.isPresent()) {
            a.add(myWord);
        }
        for(char child : children.keySet()){
            
            List<Word> renew = children.get(child).popAllWords();
            
            a.addAll(renew);
        }
        
        return a;
    }
    
    /**
     * @return the number of leaves in this tree, i.e. the number of words which are
     * not prefixes of any other word.
     */
    int numLeaves() { //DONE
        Integer numberOfLeaves = 0;
        if(children.size() == 0){
            numberOfLeaves = 1;
        }else{
            for (Map.Entry<Character, DictionaryTree> e : children.entrySet()){
                numberOfLeaves += e.getValue().numLeaves();
            }
        }
        return numberOfLeaves;
    }

    /**
     * @return the maximum number of children held by any node in this tree
     */
    int maximumBranching() { //DONE
    
        int maximum = Integer.MIN_VALUE;
    
        for (Map.Entry<Character, DictionaryTree> e : children.entrySet())
            maximum = Math.max(maximum, e.getValue().children.size());
    
        return maximum;
    }

    /**
     * @return the height of this tree, i.e. the length of the longest branch
     */
    int height() { //DONE
        int height = -1;
    
        for (Map.Entry<Character, DictionaryTree> e : children.entrySet())
            height = Math.max(height,e.getValue().height());
    
        return 1+height;
    }

    /**
     * @return the number of nodes in this tree
     */
    
    int size(){ //DONE
        int size = 1;
    
        for (Map.Entry<Character, DictionaryTree> child : children.entrySet()){
            size += child.getValue().size();
        
        }
    
        return size;
    }

    /**
     * @return the longest word in this tree
     */
    
    String longestWord() { //DONE
        if (children.size() == 0){
            return "";
        }else{
            Character longestChar = Character.MIN_VALUE;
            int longest = 0;
            for(Map.Entry<Character, DictionaryTree> e : children.entrySet()){
                if(e.getValue().height() > longest){
                    longest = e.getValue().height();
                    longestChar = e.getKey();
                    //System.out.println(longestChar);
                }
                
            }
            if(longest == 0){
                Character randomName = (Character) children.keySet().toArray()[0];
                return  randomName.toString();
            }
            return Character.toString(longestChar) + children.get(longestChar).longestWord();
        }
        
    }
    
    
    /**
     * @return all words stored in this tree as a list
     */
    List<String> allWords(){ //DONE
        
        List<String> a = new ArrayList<>();
        if(myWord.completeWord.isPresent()) {
            a.add(myWord.completeWord.get());
        }
        for(char child : children.keySet()){
            
            List<String> renew = children.get(child).allWords();
            
            a.addAll(renew);
        }
        
        return a;
    }
    /**
     * Folds the tree using the given function. Each of this node's
     * children is folded with the same function, and these results
     * are stored in a collection, cResults, say, then the final
     * result is calculated using f.apply(this, cResults).
     *
     * @param f   the summarising function, which is passed the result of invoking the given function
     * @param <A> the type of the folded value
     * @return the result of folding the tree using f
     */
    <A> A fold(BiFunction<DictionaryTree, Collection<A>, A> f) {
        throw new RuntimeException("DictionaryTree.fold not implemented yet");
    }
    
}
