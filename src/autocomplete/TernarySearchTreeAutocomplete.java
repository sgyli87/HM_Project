package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;

    private TST tree;
    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
        tree = new TST(overallRoot);
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: Replace with your code
        List<CharSequence> data = new ArrayList<>();
        data.addAll(terms);

        for(CharSequence d: data){
            tree.insert(d);
        }

    }
    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: Replace with your code
        List<CharSequence> result = new ArrayList<>();

        tree.startsWith(prefix, result);

        return result;
    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private final char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data) {
            this.data = data;
            this.isTerm = false;
            this.left = null;
            this.mid = null;
            this.right = null;
        }

        public char getData() {
            return data;
        }

        public Node getLeft() {
            return left;
        }

        public Node getMid() {
            return mid;
        }

        public Node getRight() {
            return right;
        }

        public boolean isTerm() {
            return isTerm;
        }
        public void setTerm(boolean isTerm) {
            this.isTerm = isTerm;
        }
    }

    private static class TST{
        private Node root;

        public TST(Node root){
            this.root = root;
        }

        public Node getRoot(){
            return root;
        }
        public TST(final List<CharSequence> words){
            for(CharSequence word: words){

            }
        }

        //insert
        public Node insert(final CharSequence word){
            if(word == null || word.length()==0){
                return null;
            }
            insert(this.root, word.toString().toUpperCase().toCharArray(), 0);
            return getRoot();
        }

        private Node insert(Node node, final char[] word, int idx){

            char currChar = word[idx];

            if(node == null){
                node = new Node(currChar);
            }

            if(currChar < node.getData()){
                node.left = insert(node.getLeft(), word, idx);
            }else if(currChar > node.getData()){
                node.right = insert(node.getRight(), word, idx);
            }else{

                if(idx + 1 < word.length){
                    node.mid = insert(node.getMid(), word, idx + 1 );
                }
                else {
                    node.setTerm(true);
                }
            }
            return node;
        }

        public void startsWith(final CharSequence prefix, List<CharSequence> result){

            Node subTST = startsWith(root, prefix.toString(), 0);

            if(subTST == null) return;

            List<String> temp = new ArrayList<>();
            StringBuilder sb = new StringBuilder(prefix.toString());


            collect(subTST.getMid(), sb, temp);

            result.addAll(temp);

            //throw new UnsupportedOperationException("Not implemented yet");
        }

        private Node startsWith(Node node, String prefix, int idx){

            if(node == null) return null;

            char currChar = prefix.charAt(idx);

            if(currChar < node.getData()){
                startsWith(node.getLeft(), prefix, idx);
            }
            else if(currChar > node.getData()){
                startsWith(node.getRight(), prefix, idx);
            }
            else if (idx + 1 < prefix.length()){
                startsWith(node.getMid(), prefix, idx + 1);
            }

            return node;
        }

        private void collect(Node node, StringBuilder prefix, List<String> result){

            if(node==null) return;

            collect(node.getLeft(), prefix, result);

            if(node.isTerm()) result.add(prefix.toString() + node.getData());

            collect(node.getMid(), prefix.append(node.getData()), result);

            prefix.deleteCharAt(prefix.length()-1);

            collect(node.getRight(), prefix, result);
        }


    }
}
