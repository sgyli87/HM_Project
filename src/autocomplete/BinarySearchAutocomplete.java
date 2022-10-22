package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        this.terms = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        //throw new UnsupportedOperationException("Not implemented yet");
        this.terms.addAll(terms);
        Collections.sort(this.terms, CharSequence::compare);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        //throw new UnsupportedOperationException("Not implemented yet");
        List<CharSequence> result = new ArrayList<>();

        if(prefix == null || prefix.length() == 0){
            return result;
        }

        int pos = Collections.binarySearch(terms, prefix, CharSequence::compare);

        int startindex = -1;

        if(pos >= 0){
            startindex = pos;
        }
        else{
            startindex = -(pos+1);
        }

        for(int i = startindex; i < terms.size(); i++) {
            if (Autocomplete.isPrefixOf(prefix, terms.get(i))) {
                result.add(terms.get(i));
            } else {
                break;
            }
        }
        return result;
    }
}
