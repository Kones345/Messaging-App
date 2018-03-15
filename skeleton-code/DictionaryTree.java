import java.util.*;
import java.util.function.BiFunction;

public class DictionaryTree {

    private Map<Character, DictionaryTree> children = new LinkedHashMap<>();
    
    
    //A word object which sores the complete word and popularity of this dictionary tree
    public Word myWord = new Word();
    
    /**
     * Inserts the given word into this dictionary.
     * If the word already exists, nothing will change.
     *
     * @param word the word to insert
     */
    void insert(String word) { //DONE
        Optional<String> optWord = Optional.ofNullable(word);
        if(!optWord.isPresent() || optWord.get().length() == 0){
            return;
        }
        
        insertionHelper(optWord.get(),optWord.get());
    }
    
    
    /**
     * Inserts each letter of the word into the tree by checking if ot already exists there or not
     * @param currentPortion The current portion of the word which we are considering deleting.
     * @param fullWord The reference to the full word which we want to store in the tree.
     */
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
        Optional<String> optWord = Optional.ofNullable(word);
        Optional<Integer> optPop = Optional.ofNullable(popularity);
        if(!optWord.isPresent() || optWord.get().length() == 0 || !optPop.isPresent()){
            return;
        }
        popInsertionHelper(optWord.get(),optWord.get(),optPop.get());
    }
    
    /** Helper function for inserting a word of a given poularity
    * Takes the popularity of a given word and will put each letter into the tree if it is not contained.
    * When we get to the end we insert the reference to the full word and the popularity
    *
    * @param currentPortion the current portion of the full word which we are inserting.
     * @param fullWord the fullword which we want to insert into the tree
     * @param popularity the popularity of the word to be inserted.
     *
    */
    void popInsertionHelper(String currentPortion, String fullWord, Integer popularity){
        if(currentPortion.length() == 1){
            if(!children.containsKey(currentPortion.charAt(0))){
                this.children.put(currentPortion.charAt(0), new DictionaryTree());
            }
            children.get(currentPortion.charAt(0)).myWord.completeWord = Optional.of(fullWord);
            children.get(currentPortion.charAt(0)).myWord.popularity = Optional.of(popularity);
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
     * If the word passed is contained in the dictionary then we loop thorugh every character starting from the end and try to delete
     * it from the tree.
     *
     * @param word the word to delete from this dictionary
     * @return whether or not the parent can delete this node from its children
     */
    boolean remove(String word) { //DONE
        Optional<String> optWord = Optional.ofNullable(word);
        if(!optWord.isPresent() || optWord.get().length() == 0){
            return false;
        }
        if(contains(optWord.get())){
            int counter = word.length() - 1;
            for (int i = counter; i >= 0; i--) {
                removalHelper(word.substring(0, i + 1), word.charAt(i), word);
            }
            return true;
        }else{
            return false;
        }
        
        
    }
    
    /**
     * When given the word we want to remove from the dictionary, this method will traverse the tree and  starting from the last
     * character in the word, remove each element of the word in the appropriate manner depending on the elements around it.
     *
     * @param currentPortion the current Portion of the  portion of the word we want to get to delete from this dictionary
     *                       This starts as the full word and each iteration in the for loop removes a letter off of the end of the word
     *                       so that we can assess what to do with that letter.
     * @param letter the letter which we are currently considering for deletion from the tree.
     * @param fullWord the full word which we want to dellete
     * @return whether or not the parent can delete this node from its children
     *
     */
    void removalHelper(String currentPortion,Character letter,  String fullWord){
        if(currentPortion.length() == 1){
            if(children.get(letter).myWord.completeWord.equals(Optional.of(fullWord)) && children.get(letter).children.size() == 0){
                children.remove(letter);
         }else if(children.get(letter).myWord.completeWord.equals(Optional.of(fullWord)) && children.get(letter).children.size() > 0){
                children.get(letter).myWord.completeWord = Optional.empty();
                children.get(letter).myWord.popularity = Optional.empty();
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
    
        Optional<String> optWord = Optional.ofNullable(word);
        if(!optWord.isPresent() || optWord.get().length() == 0){
            return false;
        }
        
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
        Optional<String> optPrefix = Optional.ofNullable(prefix);
        if(!optPrefix.isPresent() || optPrefix.get().length() == 0 || !prefixIsContained(prefix)){
            return Optional.empty();
        }
        if(prefix.length() == 1){
            return children.get(prefix.charAt(0)).predictionHelper();
        }else{
            return children.get(prefix.charAt(0)).predict(prefix.substring(1));
        }
    }
    
    //Checks if a prefix we want to predict in the tree is contained.
    boolean prefixIsContained(String prefix){
        if (prefix.length() == 1){
            return children.containsKey(prefix.charAt(0));
        }else{
            if(children.containsKey(prefix.charAt(0))){
                return children.get(prefix.charAt(0)).prefixIsContained(prefix.substring(1));
            }else {
                return false;
            }
        }
    }
    
    /**
     * When the helper function is called, we have already traversed the tree up to a point where we are at the end of the string we want to predict
     * This allows us to get all the words that exist after the end of the prefix and return any one of them.
     * @return the optional string of the word we want to return.
     */
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
    
        Optional<String> optPrefix = Optional.ofNullable(prefix);
        Optional<Integer> optPop = Optional.ofNullable(n);
        if(!optPrefix.isPresent() || optPrefix.get().length() == 0 || !optPop.isPresent()|| !prefixIsContained(prefix)){
            return new ArrayList<>();
        }

        if(prefix.length() == 1){
            return children.get(prefix.charAt(0)).predictPopHelper(n);
        }else{
            return children.get(prefix.charAt(0)).predict(prefix.substring(1), n);
        }
    }
    
    
    /**
     * Acts in the same way as the previous prediction helper method however this time we sort the list of all the words by their popularities
     * The higher the popularity, the more popular the word and we return the first n words
     * @param n the n most popular words we want to return
     * @return the list of popular words.
     */
    List<String> predictPopHelper(int n){
        List<Word> allWords = popAllWords();
        List<String> finals = new ArrayList<>();
        if(allWords.size() == 0){
            return null;
        }else{
            Collections.sort(allWords, new Word());
            if(allWords.size() < n){
                for(Word w : allWords){
                    finals.add(w.completeWord.get());
                }
            }else{
                for(int i = 0; i < n; i++){
                    finals.add(allWords.get(i).completeWord.get());
                }
            }
            return finals;
        }
    }
    
    
    /**
     * Acts similarly to the allWords() method however here we return a list of word objects which we can use to sort by popularity later.
     * @return the list of word objects.
     */
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
        return fold((tree, result) -> {
            int numberOfLeaves = 0;
            
            for (int i : result) {
                numberOfLeaves += i;
            }
            if(tree.children.isEmpty()){
                numberOfLeaves+=1;
            }
            return numberOfLeaves;
        });
    
        // My implementation before folding
        
//        Integer numberOfLeaves = 0;
//        if(children.size() == 0){
//            numberOfLeaves = 1;
//        }else{
//            for (Map.Entry<Character, DictionaryTree> e : children.entrySet()){
//                numberOfLeaves += e.getValue().numLeaves();
//            }
//        }
//        return numberOfLeaves;
    }

    /**
     * @return the maximum number of children held by any node in this tree
     */
    int maximumBranching() { //DONE
    
        return fold((tree, result) -> {
            int maximum = Integer.MIN_VALUE;
        
            for (int i : result) {
               maximum = Math.max(tree.children.size(), i);
            }
        
            return maximum;
        });
    
    
    
        // My implementation before folding
        
//        int maximum = Integer.MIN_VALUE;
//
//        for (Map.Entry<Character, DictionaryTree> e : children.entrySet())
//            maximum = Math.max(maximum, e.getValue().children.size());
//
//        return maximum;
    }

    /**
     * @return the height of this tree, i.e. the length of the longest branch
     */
    int height() { //DONE
    
        // My implementation before folding
        
//        return fold((tree, result) -> {
//            int height = -1;
//
//            for (int i : result) {
//                height = Math.max(height, i);
//            }
//
//            return height + 1;
//        });
    
    
        return fold((tree, result) -> {
            int height = -1;
        
            for (int i : result) {
                height = Math.max(height,i);
            }
            
            
            return height + 1;
        });
        
        //My initial implementation
//        int height = -1;
//
//        for (Map.Entry<Character, DictionaryTree> e : children.entrySet())
//            height = Math.max(height,e.getValue().height());
//
//        return 1+height;
    }

    /**
     * @return the number of nodes in this tree
     */
    
    int size(){ //DONE
        
        // My implementation before folding
        
//        int size = 1;
//
//        for (Map.Entry<Character, DictionaryTree> child : children.entrySet()){
//            size += child.getValue().size();
//
//        }
//
//        return size;
        
        return fold((tree, result) -> {
            int size = 0;
            
            for (int i : result) {
                size += i;
            }
            
            return size + 1;
        });
        
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
     * are stored in a collection, Results, say, then the final
     * result is calculated using f.apply(this, cResults).
     *
     * @param f   the summarising function, which is passed the result of invoking the given function
     * @param <A> the type of the folded value
     * @return the result of folding the tree using f
     */
    <A> A fold(BiFunction<DictionaryTree, Collection<A>, A> f) {

        List<A> a = new ArrayList<>();
        for (Map.Entry<Character, DictionaryTree> child : children.entrySet()){
            A result = child.getValue().fold(f);
            a.add(result);

        }
        return f.apply(this,a);
    }
    
    public Map<Character, DictionaryTree> getChildren(){
        return this.children;
    }
    
}
