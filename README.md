# binary-search-tree-java
This project contains a Java class (BST) implementing a binary search tree
data structure for storing generic elements.

## Description
The BST class can store any type of Comparable object. Storage of duplicate
elements or null pointers is not supported. This implementation of BST is
not self-balancing, but a balance() method is provided to allow reorganizing
the tree for optimal height.

The worst-case performance for inserting, searching, or deleting an element is
dependent on the tree's height--i.e., the longest path from the "root" element
to some other element in the tree. If the tree is balanced, these operations
have logarithmic time complexity. For an unbalanced tree, these operations have
linear time complexity in the worst case.

This data structure is useful for storing a list of sorted elements and
maintaining sorted order when adding or removing elements. Unlike
array-based data structures, the BST can efficiently insert or remove elements
from any position in the list. The major disadvantage is that adding elements
in pre-sorted order will result in worst-case performance until the balance()
method is called to restore optimal tree height.

## Installing
This project was developed for Java 8, but may be compatible with previous
Java versions.

The project does not contain a main() method. To use the BST class, simply
copy the [bst](/src/bst/) directory into your project's source directory and
import the "bst" package in relevant source files.

## Authors
[Samuel Wegner](https://github.com/samuelwegner/) - Project owner

## License
This project is licensed under the [GPL v3](LICENSE).
