package autocomplete;

import java.util.*;

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
        Set<CharSequence> record = new HashSet<>();

        if(prefix == null || prefix.length() == 0){
            return result;
        }

        int pos = Collections.binarySearch(terms, prefix, CharSequence::compare);

        int start = pos >= 0 ? pos : -(pos+1);

        for(int i = start; i < terms.size(); i++) {
            CharSequence tmp = terms.get(i);
            if (!Autocomplete.isPrefixOf(prefix, tmp)) {
                if(!record.contains(tmp)) {
                    record.add(tmp);
                    result.add(tmp);
                }
            } else {
                break;
            }
        }
        return result;
    }
}
