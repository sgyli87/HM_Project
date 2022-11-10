package autocomplete.cities;

import autocomplete.Autocomplete;
import autocomplete.TernarySearchTreeAutocomplete;
import autocomplete.TreeSetAutocomplete;

public class TreeSetAuto extends AutocompleteTests {
    @Override
    public Autocomplete createAutocomplete() {
        return new TreeSetAutocomplete();
    }
}
