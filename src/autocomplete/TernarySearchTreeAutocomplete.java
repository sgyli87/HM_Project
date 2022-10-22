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
    //private Node overallRoot;

    private TST tree;
    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        //overallRoot = null;
        tree = new TST();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: Replace with your code
        List<CharSequence> data = new ArrayList<>();
        data.addAll(terms);

        for(CharSequence d: data){
            tree.insert(d.toString());
        }

    }
    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: Replace with your code

        return tree.startsWith(prefix);

    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private char data;
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

        public Node(){

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

        public TST(){
            root = new Node('*');
        }

        public Node getRoot(){
            return root;
        }

        //insert
        public void insert(final String word){

            if(word == null || word.length()==0) return;

            insert(root, word.toUpperCase().toCharArray(), 0);
        }

        private Node insert(Node node, final char[] word, int idx){

            char currChar = word[idx];

            if(node == null){
                node = new Node(currChar);
            }

            if(currChar < node.getData()){
                node.left = insert(node.left, word, idx);
            }else if(currChar > node.getData()){
                node.right = insert(node.right, word, idx);
            }else if(idx + 1 < word.length){
                node.mid = insert(node.mid, word, idx + 1 );
            }
            else {
                node.isTerm = true;
            }
            return node;
        }
        public List<CharSequence> startsWith(final CharSequence prefix){
            List<CharSequence> res = new ArrayList<>();

            Node subTST = startsWith(this.root, prefix.toString(), 0);

            if(subTST == null) return res;

            if(subTST.isTerm()) res.add(prefix);

            collect(subTST.mid, new StringBuilder(prefix), res);

            return res;
        }

        private Node startsWith(Node node, String prefix, int idx){
            if(node == null) return null;

            char currChar = prefix.charAt(idx);

            if(currChar < node.getData()){
                return startsWith(node.left, prefix, idx);
            }
            else if(currChar > node.getData()){
                return startsWith(node.right, prefix, idx);
            }
            else if (idx < prefix.length() - 1){
                return startsWith(node.mid, prefix, idx + 1);
            }
            else return node;
        }

        private void collect(Node node, StringBuilder prefix, List<CharSequence> result){
            if(node==null) return;
            collect(node.left, prefix, result);
            if(node.isTerm()) {
                result.add(prefix.toString() + node.getData());
            }
            collect(node.mid, prefix.append(node.getData()), result);
            prefix.deleteCharAt(prefix.length()-1);
            collect(node.right, prefix, result);
        }
    }
}
