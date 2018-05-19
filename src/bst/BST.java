/******************************************************************************
    BST.java : Class implementing a generic binary search tree.
	
    Copyright (C) 2018  Samuel Wegner (samuelwegner@hotmail.com)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
******************************************************************************/

/* Project: binary-search-tree-java
 * This: BST.java
 * Date: 18-May-2018
 * Author: Sam Wegner
 * 
 * Class implementing a generic binary search tree.
 *
 * The binary search tree (BST) stores elements hierarchically for efficient
 * searching. However, search efficiency is dependent on the tree height, which
 * in turn is dependent on the order that elements are inserted; if elements
 * are added in sorted order, search performance will be equivalent to a
 * simple linked list. This BST implementation is not self-balancing.
 *
 * Optimal tree height can be restored by calling the balance() method. This is
 * recommended after making a large number of insertions (especially of
 * pre-sorted elements) and/or deletions.
 *
 * Note that null pointers and duplicate elements cannot be stored in the BST.
 */
package bst;

import java.util.*;

public final class BST<E extends Comparable<E>> {
    private class TreeNode {
        public E value; // Stored element
        public TreeNode left; // Left (lesser) child node
        public TreeNode right; // Right (greater) child node
        
        /**
         * Instantiate a binary search tree node containing a given element.
         * @param value Element to be stored
         */
        public TreeNode(E value) {
            this.value = value;
        }
    }
    
    private TreeNode root; // Base node of tree
    private int size; // Count of elements in tree
    
    /**
     * Instantiate an empty binary search tree.
     */
    public BST() {}
    
    /**
     * Instantiate a binary search tree with a shallow copy of elements from
     * an array. Duplicate elements and null pointers will be ignored.
     * @param elements Array of elements to be added
     */
    public BST(E[] elements) {
        if (null == elements) throw new NullPointerException();
        
        for (int i = 0; elements.length > i; ++i) {
            if (null != elements[i]) add(elements[i]);
        }
    }
    
    /**
     * Instantiate a binary search tree with a shallow copy of elements from
     * an iterable collection. Duplicate elements and null pointers will be
     * ignored.
     * @param elements Collection of elements to be added
     */
    public BST(Iterable<E> elements) {
        if (null == elements) throw new NullPointerException();
        
        Iterator<E> itr = elements.iterator();
        while (itr.hasNext()) {
            E elem = itr.next();
            if (null != elem) add(elem);
        }
    }
    
    /**
     * Get the count of elements contained in the binary search tree.
     * @return Number of elements
     */
    public int size() {
        return size;
    }
    
    /**
     * Check whether the binary search tree contains any elements.
     * @return True if tree contains no elements
     */
    public boolean isEmpty() {
        return 0 == size;
    }
    
    /**
     * Remove all elements from the binary search tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }
    
    /**
     * Insert an element into the binary search tree.
     * Note that a tree cannot contain duplicate elements; this method will
     * return false if the argument equals an existing element in the tree.
     * Null pointers cannot be inserted.
     * @param element Object to be added
     * @return True if element was added
     */
    public boolean add(E element) {
        if (null == element) throw new NullPointerException();
        
        TreeNode parent = null;
        TreeNode curr = root;
        while (null != curr) {
            int cmp = element.compareTo(curr.value);
            if (0 > cmp) {
                parent = curr;
                curr = curr.left;
            }
            else if (0 < cmp) {
                parent = curr;
                curr = curr.right;
            }
            else {
                return false; // Duplicate element
            }
        }
        
        if (null == parent) {
            root = new TreeNode(element);
        }
        else if (0 > element.compareTo(parent.value)) {
            parent.left = new TreeNode(element);
        }
        else {
            parent.right = new TreeNode(element);
        }
        
        ++size;
        return true;
    }
    
    /**
     * Remove an elements from the binary search tree.
     * @param element Object to be removed
     * @return True if element was removed
     */
    public boolean remove(E element) {
        if (null == element) throw new NullPointerException();
        
        TreeNode parent = null;
        TreeNode curr = root;
        while (null != curr) {
            int cmp = element.compareTo(curr.value);
            if (0 > cmp) {
                parent = curr;
                curr = curr.left;
            }
            else if (0 < cmp) {
                parent = curr;
                curr = curr.right;
            }
            else {
                break;
            }
        }
        
        if (null == curr) return false;
        
        if (null == curr.left) {
            if (null == parent) {
                root = curr.right;
            }
            else {
                if (0 > element.compareTo(parent.value)) {
                    parent.left = curr.right;
                }
                else {
                    parent.right = curr.right;
                }
            }
        }
        else {
            TreeNode parentRight = curr;
            TreeNode mostRight = curr.left;
            while (null != mostRight.right) {
                parentRight = mostRight;
                mostRight = mostRight.right;
            }
            
            curr.value = mostRight.value;
            if (parentRight.right.equals(mostRight)) {
                parentRight.right = mostRight.left;
            }
            else {
                parentRight.left = mostRight.left;
            }
        }
        
        --size;
        return true;
    }
    
    /**
     * Check whether the binary search tree contains a given element.
     * @param element Object to find
     * @return True if tree contains specified element
     */
    public boolean contains(E element) {
        if (null == element) throw new NullPointerException();

        TreeNode curr = root;
        while (null != curr) {
            int cmp = element.compareTo(curr.value);
            if (0 > cmp) {
                curr = curr.left;
            }
            else if (0 < cmp) {
                curr = curr.right;
            }
            else {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Find the least-value element stored in the binary search tree.
     * @return Minimum element, or null if tree is empty
     */
    public E getMin() {
        if (null == root) return null;
        
        TreeNode curr = root;
        while (null != curr.left) curr = curr.left;
        return curr.value;
    }
    
    /**
     * Find the greatest-value element stored in the binary search tree.
     * @return Maximum element, or null if tree is empty
     */
    public E getMax() {
        if (null == root) return null;
        
        TreeNode curr = root;
        while (null != curr.right) curr = curr.right;
        return curr.value;
    }
    
    /**
     * Rebuild the binary search tree to minimize tree height given the
     * currently contained elements. Re-balancing after numerous insertions
     * and deletions will improve the tree's search performance.
     */
    public void balance() {
        if (2 >= size) return;
        
        root = build(toListInorder(), 0, size - 1);
    }
    
    /**
     * Recursively build a binary search tree from a sorted list of elements.
     * The list is assumed to be sorted in ascending order and contain no
     * duplicate elements.
     * @param elements Elements to be added
     * @param low Lowest index
     * @param high Highest index
     * @return 
     */
    private TreeNode build(List<E> elements, int low, int high) {
        if (low > high) return null;
        
        int mid = (high - low) / 2 + low;
        TreeNode curr = new TreeNode(elements.get(mid));
        curr.left = build(elements, low, mid - 1);
        curr.right = build(elements, mid + 1, high);
        return curr;
    }
    
    /**
     * Create a list containing a shallow copy of elements from the binary
     * search tree using in-order traversal.
     * @return List of tree elements
     */
    public List<E> toListInorder() {
        List<E> result = new ArrayList<>(size);
        toListInorder(result, root);
        return result;
    }
    
    /**
     * Recursively build a list of tree elements using in-order traversal.
     * @param result List to store traversed elements
     * @param curr Current tree node
     */
    private void toListInorder(List<E> result, TreeNode curr) {
        if (null == curr) return;
        
        toListInorder(result, curr.left);
        result.add(curr.value);
        toListInorder(result, curr.right);
    }

    /**
     * Create a list containing a shallow copy of elements from the binary
     * search tree using pre-order traversal.
     * @return List of tree elements
     */
    public List<E> toListPreorder() {
        List<E> result = new ArrayList<>(size);
        toListPreorder(result, root);
        return result;
    }
    
    /**
     * Recursively build a list of tree elements using pre-order traversal.
     * @param result List to store traversed elements
     * @param curr Current tree node
     */
    private void toListPreorder(List<E> result, TreeNode curr) {
        if (null == curr) return;
        
        result.add(curr.value);
        toListPreorder(result, curr.left);
        toListPreorder(result, curr.right);
    }

    /**
     * Create a list containing a shallow copy of elements from the binary
     * search tree using post-order traversal.
     * @return List of tree elements
     */
    public List<E> toListPostorder() {
        List<E> result = new ArrayList<>(size);
        toListPostorder(result, root);
        return result;
    }
    
    /**
     * Recursively build a list of tree elements using post-order traversal.
     * @param result List to store traversed elements
     * @param curr Current tree node
     */
    private void toListPostorder(List<E> result, TreeNode curr) {
        if (null == curr) return;
        
        toListPostorder(result, curr.left);
        toListPostorder(result, curr.right);
        result.add(curr.value);
    }
    
    /**
     * Create a list containing a shallow copy of elements from the binary
     * search tree using breadth-first traversal.
     * @return List of tree elements
     */
    public List<E> toListBreadthFirst() {
        List<E> result = new ArrayList<>(size);
        if (null != root) {
            List<TreeNode> currLvl = new ArrayList<>(1);
            currLvl.add(root);
            toListBreadthFirst(result, currLvl);
        }
        return result;
    }
    
    /**
     * Recursively build a list of tree elements using breadth-first traversal.
     * @param result List to store traversed elements
     * @param currLvl Current level of tree nodes
     */
    private void toListBreadthFirst(List<E> result, List<TreeNode> currLvl) {
        List<TreeNode> nextLvl = new ArrayList<>();
        
        for (int i = 0; currLvl.size() > i; ++i) {
            TreeNode curr = currLvl.get(i);
            result.add(curr.value);
            if (null != curr.left) nextLvl.add(curr.left);
            if (null != curr.right) nextLvl.add(curr.right);
        }
        
        if (!nextLvl.isEmpty()) toListBreadthFirst(result, nextLvl);
    }
    
    /**
     * Create a string representation of elements from the binary search tree
     * using in-order traversal.
     * @return String of tree elements
     */
    public String toStringInorder() {
        StringBuilder sb = new StringBuilder("[");
        toStringInorder(sb, root);
        return sb.append("]").toString();
    }
    
    /**
     * Recursively build a string of tree elements using in-order traversal.
     * @param sb String builder for traversed elements
     * @param curr Current tree node
     */
    private void toStringInorder(StringBuilder sb, TreeNode curr) {
        if (null == curr) return;
        
        toStringInorder(sb, curr.left);
        if (1 < sb.length()) sb.append(", ");
        sb.append(curr.value);
        toStringInorder(sb, curr.right);
    }
    
    /**
     * Create a string representation of elements from the binary search tree
     * using pre-order traversal.
     * @return String of tree elements
     */
    public String toStringPreorder() {
        StringBuilder sb = new StringBuilder("[");
        toStringPreorder(sb, root);
        return sb.append("]").toString();
    }
    
    /**
     * Recursively build a string of tree elements using pre-order traversal.
     * @param sb String builder for traversed elements
     * @param curr Current tree node
     */
    private void toStringPreorder(StringBuilder sb, TreeNode curr) {
        if (null == curr) return;
        
        if (1 < sb.length()) sb.append(", ");
        sb.append(curr.value);
        toStringPreorder(sb, curr.left);
        toStringPreorder(sb, curr.right);
    }
    
    /**
     * Create a string representation of elements from the binary search tree
     * using post-order traversal.
     * @return String of tree elements
     */
    public String toStringPostorder() {
        StringBuilder sb = new StringBuilder("[");
        toStringPostorder(sb, root);
        return sb.append("]").toString();
    }
    
    /**
     * Recursively build a string of tree elements using post-order traversal.
     * @param sb String builder for traversed elements
     * @param curr Current tree node
     */
    private void toStringPostorder(StringBuilder sb, TreeNode curr) {
        if (null == curr) return;
        
        toStringPostorder(sb, curr.left);
        toStringPostorder(sb, curr.right);
        if (1 < sb.length()) sb.append(", ");
        sb.append(curr.value);
    }
    
    /**
     * Create a string representation of elements from the binary search tree
     * using breadth-first traversal.
     * @return String of tree elements
     */
    public String toStringBreadthFirst() {
        StringBuilder sb = new StringBuilder("[");
        if (null != root) {
            List<TreeNode> currLvl = new ArrayList<>(1);
            currLvl.add(root);
            toStringBreadthFirst(sb, currLvl);
        }
        return sb.append("]").toString();
    }
    
    /**
     * Recursively build a string of tree elements using breadth-first
     * traversal.
     * @param sb String builder for traversed elements
     * @param currLvl Current level of tree nodes
     */
    private void toStringBreadthFirst(StringBuilder sb, List<TreeNode> currLvl)
    {
        List<TreeNode> nextLvl = new ArrayList<>();
        
        for (int i = 0; currLvl.size() > i; ++i) {
            TreeNode curr = currLvl.get(i);
            if (1 < sb.length()) sb.append(", ");
            sb.append(curr.value);
            if (null != curr.left) nextLvl.add(curr.left);
            if (null != curr.right) nextLvl.add(curr.right);
        }
        
        if (!nextLvl.isEmpty()) toStringBreadthFirst(sb, nextLvl);
    }
    
    @Override
    public String toString() {
        return toStringInorder();
    }
}
