import java.util.Comparator;
import java.util.Optional;


//Word object which stores the characteristics of any word which we would insert
public class Word implements Comparator<Word>{
    public Optional <String> completeWord = Optional.empty();
    public Optional<Integer> popularity = Optional.empty();
    
    @Override
    public int compare(Word o1, Word o2){
        return o2.popularity.get() - o1.popularity.get();
    }
}
