package autocomplete.cities;

import autocomplete.Autocomplete;
import autocomplete.TreeSetAutocomplete;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        List<CharSequence> terms = new ArrayList<>();
        terms.add("b");
        terms.add("aa");
        terms.add("aa");
        terms.add("ac");
        terms.add("ad");
        terms.add("ae");
        terms.add("af");

// Choose your Autocomplete implementation.
        Autocomplete autocomplete = new TreeSetAutocomplete();
        autocomplete.addAll(terms);
// Choose your prefix string.
        CharSequence prefix = "a";
        List<CharSequence> matches = autocomplete.allMatches(prefix);
        for (CharSequence match : matches) {
            System.out.println(match);
        }

    }
}
