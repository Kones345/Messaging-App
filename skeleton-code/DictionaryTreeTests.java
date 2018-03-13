import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Kelsey McKenna
 */
public class DictionaryTreeTests {
    
    
    static boolean notEmpty(String word){
        Optional<String> optWord = Optional.ofNullable(word);
        return word.length() > 0 && optWord.isPresent();
    }
    
    static boolean treeIsEmpty(DictionaryTree d){
        return d.height() == 0;
    }
    
    @Test
    public void heightOfRootShouldBeZero() {
        //Pre-condition: tree is initially empty
        
        DictionaryTree unit = new DictionaryTree();
        //pre-condition
        assert treeIsEmpty(unit);
        Assertions.assertEquals(0, unit.height());
    }

    @Test
    public void heightOfWordShouldBeWordLength() {
        //Pre-condition: height is initially 0
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
    }
    
    @Test
    public void maxBranching(){
        //pre-condition: tree is initially empty
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
        
    }
    
    @Test
    public void sizeBeforeAndAfterRemoval(){
        //pre-condition: tree is initially empty
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
    }
    
    @Test
    public void longestWord(){
        //pre-condition: tree is initially empty
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
    }
    
    @Test
    public void numLeaves(){
        //pre-condition: tree is initially empty
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
    }
    
    @Test
    public void containsAndRemove(){
        //pre-condition: tree is initially empty
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
    }
    
    
    @Test
    public void allWordsInsertedAreReturned(){
        //pre-condition: tree is initially empty
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
    }
    
    @Test
    public void insertedWordsAreContained(){
        //pre-condition: tree is initially empty
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
    }
    
    @Test
    public void predictReturnsValidWordWithoutPopularity(){
        //pre-condition: tree is initially empty
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
    }
    
    @Test
    public void predictReturnsValidWordWithPopularity(){
        //pre-condition: tree is initially empty
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
        
        List<String> predictions = new ArrayList<String>();
        predictions.add("and");
        predictions.add("andover");
        predictions.add("abba");
        Assertions.assertEquals(predictions, unit.predict("a",3));
    
        //post condition
        for(String s: unit.allWords()){
            assert notEmpty(s);
        }
    
        assert !treeIsEmpty(unit);
    }
}
