# CS113-EC-AVLTrees
AVLTree
AVL Tree (w/o removal)

Complete the `AVLTree` class by coding the missing methods for insertion only (only add, not remove, etc.). Create a driver that insert a collection of more than 20 randomly generated numbers. Insert the same set of numbers in a binary search tree that is not balanced.  Verify that each tree is correct by performing an in-order traversal. Also, display the format of each tree that was built and compare their heights.

Make sure you are building the class inheritance diagram in the reading:
`BinaryTree <-- BinarySearchTree <-- BinarySearchTreeWithRotate <-- AVLTree`

> ***NOTE:***
> - Reading the book helps! Ch. 9 has a lot of information and code on AVL trees and the parts required above!
> - Note the following bug in the text's source code! `rebalanceLeft` on p. 487 does not update the left child's balance when a right - left imbalance occurs. This would also be the case for left - right imbalances with a `rebalanceRight` implementation based on that `rebalanceLeft`. An additional conditional block is required when checking for left - right- right or left - right - left imbalances.
> - The provided BinaryTree class now includes an additional `toString` method, which displays the given binary tree in such a way that is easier to read through console output.
