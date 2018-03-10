import java.util.Comparator;
import java.util.Optional;

public class Word implements Comparator<Word>{
    public Optional <String> completeWord = Optional.empty();
    public Integer popularity = null;
    
    @Override
    public int compare(Word o1, Word o2){
        return o1.popularity - o2.popularity;
    }
}
