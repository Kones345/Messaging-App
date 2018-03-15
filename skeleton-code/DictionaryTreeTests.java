import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Adam Kona
 */
public class DictionaryTreeTests {
    
    //Method to check that all children of leaves are empty and not null
    private boolean allChildrenOLeavesAreEmptyNotNull(DictionaryTree d){
        Optional<Boolean> emptyChildren = Optional.empty();
        if(d.getChildren().size() == 0){
            if(d.getChildren().isEmpty() && d.getChildren() != null){
                return true;
            }else{
                return false;
            }
        }else{
            Optional <Boolean> isEmpty = emptyChildren;
            for (Map.Entry<Character, DictionaryTree> e : d.getChildren().entrySet()){
                isEmpty =  Optional.of(allChildrenOLeavesAreEmptyNotNull(e.getValue()));
            }
            return isEmpty.get();
        }
    }
    
    //Method to check that all children of leaves have completed words
    private boolean allLeavesHaveCompleteWords(DictionaryTree d){
        Optional<Boolean> emptyChildren = Optional.empty();
        if(d.getChildren().size() == 0){
            if(d.myWord.completeWord.isPresent()){
                return true;
            }else{
                return false;
            }
        }else{
            Optional <Boolean> isEmpty = emptyChildren;
            for (Map.Entry<Character, DictionaryTree> e : d.getChildren().entrySet()){
                isEmpty =  Optional.of(allChildrenOLeavesAreEmptyNotNull(e.getValue()));
            }
            return isEmpty.get();
        }
    }
    //Method to chek all words have popularities
    private boolean allLeavesHaveCompleteWordsAndPopularities(DictionaryTree d){
        Optional<Boolean> emptyChildren = Optional.empty();
        if(d.getChildren().size() == 0){
            if(d.myWord.completeWord.isPresent() && d.myWord.popularity.isPresent()){
                return true;
            }else{
                return false;
            }
        }else{
            Optional <Boolean> isEmpty = emptyChildren;
            for (Map.Entry<Character, DictionaryTree> e : d.getChildren().entrySet()){
                isEmpty =  Optional.of(allChildrenOLeavesAreEmptyNotNull(e.getValue()));
            }
            return isEmpty.get();
        }
    }
    //Method to check that a word is not empty
    private static boolean notEmpty(String word){
        Optional<String> optWord = Optional.ofNullable(word);
        return word.length() > 0 && optWord.isPresent();
    }
    //Method to check that a tree is not empty
    private boolean treeIsEmpty(DictionaryTree d){
        return d.height() == 0;
    }
    //Test to check that the height of a single dictionary tree node is 0
    @Test
    public void heightOfRootShouldBeZero() {
        //Pre-condition: tree is initially empty
        
        DictionaryTree unit = new DictionaryTree();
        //pre-condition
        assert treeIsEmpty(unit);
        Assertions.assertEquals(0, unit.height());
    }
    //Test to check that the height of a tree is the length of the longest word
    @Test
    public void heightOfWordShouldBeWordLength() {
        //Pre-condition: height is initially 0
        //Invariant: Children of leaves are empty and not null
        //Invariant: All leaves have complete words
        //Post-condition: tree is non-empty
        DictionaryTree unit = new DictionaryTree();
        //pre-condition
        assert treeIsEmpty(unit);
        unit.insert("word", 0);
        Assertions.assertEquals("word".length(), unit.height());
    
        //post condition
        for(String s: unit.allWords()){
            assert notEmpty(s);
        }
        assert !treeIsEmpty(unit);
        //Invariants
        assert allChildrenOLeavesAreEmptyNotNull(unit);
        assert allLeavesHaveCompleteWords(unit);
    }
    //Test to check that the maximum branching is returned
    @Test
    public void maxBranching(){
        //pre-condition: tree is initially empty
        //Invariant: Children of leaves are empty and not null
        //Invariant: All leaves have complete words
        //post-condition: tree is non-empty
        DictionaryTree unit = new DictionaryTree();
        //pre-condition
        assert treeIsEmpty(unit);
        unit.insert("a");
        unit.insert("b");
        unit.insert("c");
        unit.insert("d");
        unit.insert("e");
        unit.insert("abba");
        unit.insert("abbacus");
        unit.insert("amazing");
        unit.insert("absolute");
        Assertions.assertEquals(5, unit.maximumBranching());
    
        //post condition
        for(String s: unit.allWords()){
            assert notEmpty(s);
        }
        assert !treeIsEmpty(unit);
        //Invariants
        assert allChildrenOLeavesAreEmptyNotNull(unit);
        assert allLeavesHaveCompleteWords(unit);
        
    }
    //Test to check that the size changes correctly with respect to removal
    @Test
    public void sizeBeforeAndAfterRemoval(){
        //pre-condition: tree is initially empty
        //Invariant: Children of leaves are empty and not null
        //Invariant: All leaves have complete words
        //post-condition: tree is non-empty
        //post-condition: all words is five less after removal
        //post-condition: height is zero
        //post-condition: max branching is now 1;
        //post-condition: tree is empty
        
        DictionaryTree unit = new DictionaryTree();
        //pre-condition
        assert treeIsEmpty(unit);
        
        //test
        unit.insert("a");
        unit.insert("b");
        unit.insert("c");
        unit.insert("d");
        unit.insert("e");
        int sizeBefore = unit.allWords().size();
        int heightBefore = unit.height();
        Assertions.assertEquals(6, unit.size());
        unit.remove("a");
        unit.remove("b");
        unit.remove("c");
        unit.remove("d");
        unit.remove("e");
        int sizeAfter = unit.allWords().size();
        int heightAfter = unit.height();
        Assertions.assertEquals(1, unit.size());
    
        //post conditions
        for(String s: unit.allWords()){
            assert notEmpty(s);
        }
        assert treeIsEmpty(unit);
        assert sizeAfter == sizeBefore-5;
        assert heightBefore - heightAfter == 1;
        //Invariants
        assert allChildrenOLeavesAreEmptyNotNull(unit);
    }
    //Test to check that the correct longest word is returned
    @Test
    public void longestWord(){
        //pre-condition: tree is initially empty
        //Invariant: Children of leaves are empty and not null
        //Invariant: All leaves have complete words
        //post-condition: tree is non-empty
        DictionaryTree unit = new DictionaryTree();
        //pre-condition
        assert treeIsEmpty(unit);
        unit.insert("And");
        unit.insert("Andover");
        unit.insert("carry");
        unit.insert("dictionary");
        unit.insert("ears");
        unit.insert("abba");
        unit.insert("abbacus");
        unit.insert("amazing");
        unit.insert("absolutely");
        Assertions.assertEquals("dictionary", unit.longestWord());
    
        //post condition
        for(String s: unit.allWords()){
            assert notEmpty(s);
        }
        assert !treeIsEmpty(unit);
        //Invariants
        assert allChildrenOLeavesAreEmptyNotNull(unit);
        assert allLeavesHaveCompleteWords(unit);
    }
    //Test to check that the correct number of leaves is returned
    @Test
    public void numLeaves(){
        //pre-condition: tree is initially empty
        //Invariant: All leaves have empty children and not null children.
        //Invariant: All leaves have complete words
        //post-condition: tree is non-empty
        DictionaryTree unit = new DictionaryTree();
        
        //pre-condition
        assert treeIsEmpty(unit);
        unit.insert("And");
        unit.insert("Andover");
        unit.insert("carry");
        unit.insert("dictionary");
        unit.insert("ears");
        unit.insert("abba");
        unit.insert("abbacus");
        unit.insert("amazing");
        unit.insert("absolutely");
        
        Assertions.assertEquals(7,unit.numLeaves());
    
        //post condition
        for(String s: unit.allWords()){
            assert notEmpty(s);
        }
    
        //Invariants
        assert allChildrenOLeavesAreEmptyNotNull(unit);
        assert allLeavesHaveCompleteWords(unit);
    }
    //Test to check words removed are not contained
    @Test
    public void containsAndRemove(){
        //pre-condition: tree is initially empty
        //Invariant: Children of leaves are empty and not null
        //Invariant: All leaves have complete words
        //post-condition: tree is non-empty
        DictionaryTree unit = new DictionaryTree();
        //pre-condition
        assert treeIsEmpty(unit);
        unit.insert("And");
        unit.insert("Andover");
        unit.insert("carry");
        unit.insert("dictionary");
        unit.insert("ears");
        unit.insert("abba");
        unit.insert("abbacus");
        unit.insert("amazing");
        unit.insert("absolutely");
        assert unit.contains("And");
        unit.remove("And");
        assert !unit.contains("And");
        
        
        //post condition
        for(String s: unit.allWords()) {
            assert notEmpty(s);
        }
        assert !treeIsEmpty(unit);
        //Invariants
        assert allChildrenOLeavesAreEmptyNotNull(unit);
        assert allLeavesHaveCompleteWords(unit);
    }
    
    //Test to check that all words contains all the words that were inserted
    @Test
    public void allWordsInsertedAreReturned(){
        //pre-condition: tree is initially empty
        //Invariant: Children of leaves are empty and not null
        //Invariant: All leaves have complete words
        //post-condition: tree is non-empty
        DictionaryTree unit = new DictionaryTree();
        //pre-condition
        assert treeIsEmpty(unit);
        unit.insert("And");
        unit.insert("Andover");
        unit.insert("carry");
        unit.insert("dictionary");
        unit.insert("ears");
        unit.insert("abba");
        unit.insert("abbacus");
        unit.insert("amazing");
        unit.insert("absolutely");
        unit.insert("");
        List<String> allWords = unit.allWords();
        assert allWords.contains("And") && allWords.contains("Andover") && allWords.contains("carry") && allWords.contains("dictionary") && allWords.contains("dictionary");
        assert allWords.contains("ears") && allWords.contains("abba") && allWords.contains("abbacus") && allWords.contains("amazing") && allWords.contains("absolutely");
        //post condition
        for(String s: allWords){
            assert notEmpty(s);
        }
        assert !treeIsEmpty(unit);
        //Invariants
        assert allChildrenOLeavesAreEmptyNotNull(unit);
        assert allLeavesHaveCompleteWords(unit);
    }
    //Test to check that all inserted words are contained in the tree
    @Test
    public void insertedWordsAreContained(){
        //pre-condition: tree is initially empty
        //Invariant: Children of leaves are empty and not null
        //Invariant: All leaves have complete words
        //post-condition: tree is non-empty and has no empty strings
        DictionaryTree unit = new DictionaryTree();
        //pre-condition
        assert treeIsEmpty(unit);
        unit.insert("And");
        unit.insert("Andover");
        unit.insert("carry");
        unit.insert("dictionary");
        unit.insert("ears");
        unit.insert("abba");
        unit.insert("abbacus");
        unit.insert("amazing");
        unit.insert("absolutely");
        assert unit.contains("And") && unit.contains("Andover") && unit.contains("carry") && unit.contains("dictionary") && unit.contains("dictionary");
        assert unit.contains("ears") && unit.contains("abba") && unit.contains("abbacus") && unit.contains("amazing") && unit.contains("absolutely");
    
        //post condition
        for(String s: unit.allWords()){
            assert notEmpty(s);
        }
        assert !treeIsEmpty(unit);
        //Invariants
        assert allChildrenOLeavesAreEmptyNotNull(unit);
        assert allLeavesHaveCompleteWords(unit);
    }
    //Test to check that we can predict a word without popularity
    @Test
    public void predictReturnsValidWordWithoutPopularity(){
        //pre-condition: tree is initially empty
        //Invariant: Children of leaves are empty and not null
        //Invariant: All leaves have complete words
        //post-condition: tree is non-empty
        DictionaryTree unit = new DictionaryTree();
        //pre-condition
        assert treeIsEmpty(unit);
        unit.insert("And");
        unit.insert("Andover");
        unit.insert("carry");
        unit.insert("dictionary");
        unit.insert("ears");
        unit.insert("abba");
        unit.insert("abbacus");
        unit.insert("amazing");
        unit.insert("absolutely");
        Assertions.assertEquals(Optional.of("dictionary"), unit.predict("dict"));
    
        //post condition
        for(String s: unit.allWords()){
            assert notEmpty(s);
        }
        assert !treeIsEmpty(unit);
        //Invariants
        assert allChildrenOLeavesAreEmptyNotNull(unit);
        assert allLeavesHaveCompleteWords(unit);
    }
    //Test to check that we can predict words with popularity
    @Test
    public void predictReturnsValidWordWithPopularity(){
        //pre-condition: tree is initially empty
        //Invariant: Children of leaves are empty and not null
        //Invariant: All leaves have complete words
        //Invariant: All leave have complete words and popularity
        //post-condition: tree is non-empty
        DictionaryTree unit = new DictionaryTree();
        //pre-condition
        assert treeIsEmpty(unit);
        unit.insert("and",10);
        unit.insert("andover",9);
        unit.insert("carry",8);
        unit.insert("dictionary",7);
        unit.insert("ears",6);
        unit.insert("abba",5);
        unit.insert("abbacus",4);
        unit.insert("amazing",3);
        unit.insert("absolutely",2);
        
        List<String> predictions = new ArrayList<>();
        predictions.add("and");
        predictions.add("andover");
        predictions.add("abba");
        Assertions.assertEquals(predictions, unit.predict("a",3));
    
        //post condition
        for(String s: unit.allWords()){
            assert notEmpty(s);
        }
    
        assert !treeIsEmpty(unit);
        //Invariants
        assert allChildrenOLeavesAreEmptyNotNull(unit);
        assert allLeavesHaveCompleteWordsAndPopularities(unit);;
        
    }
}
