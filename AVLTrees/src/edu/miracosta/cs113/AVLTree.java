package edu.miracosta.cs113;

import edu.miracosta.cs113.BinaryTree.Node;

/**
 * Self-balancing binary search tree using the algorithm defined by Adelson-
 * Velskii and Landis.
 */

public class AVLTree<E extends Comparable<E>> extends BinarySearchTreeWithRotate<E> {
	/**
	 * Class to represent an AVL Node. It extends the BinaryTree.Node by adding 
	 * the balance field.
	 */
	private static class AVLNode<E> extends Node<E> {
		/** Constant to indicate left-heavy */
		public static final int LEFT_HEAVY = -1;
		/** Constant to indicate balanced */
		public static final int BALANCED = 0;
		/** Constant to indicate right-heavy */
		public static final int RIGHT_HEAVY = 1;
		/** Balance is right subtree height - left subtree height */
		private int balance;
		
		/**
		 * Construct a node with the given item as the data field.
		 * @param item The data field
		 */
		public AVLNode(E item) {
			super(item);
			balance = BALANCED;
		}
		
		/**
		 * Return a string representation of this object. The balance is 
		 * appended to the contents.
		 * @return String representation of this object
		 */
		@Override
		public String toString() {
			return balance + ": " + super.toString();
		}
	}
	
	/** Flag to indicate that height of the tree has increased */
	private boolean increase;
	
	/**
	 * add starter method calls recursive add method with root as argument.
	 * @param item The item being inserted
	 * @return true if the object is insert; false if it already exists in the tree
	 */
	@Override
	public boolean add(E item) {
		increase = false;
		root = add((AVLNode<E>) root, item);
		return addReturn;
	}
	
	/**
	 * Recursive add method. Inserts the given object into the tree. 
	 * @param localRoot The local root of the subtree
	 * @param item The object to be inserted
	 * @return The new local root of the subtree with the item inserted
	 */
	private AVLNode<E> add(AVLNode<E> localRoot, E item) {
		//If the local root is null
		if (localRoot == null) { 
			addReturn = true;
			increase = true;
			return new AVLNode<E>(item);
		}
		//Item is already in the tree, increase = false
		if (item.compareTo(localRoot.data) == 0) { 
			increase = false;
			addReturn = false;
			return localRoot;
		}
		//Item is less than the data
		else if (item.compareTo(localRoot.data) < 0) {
			localRoot.left = add((AVLNode<E>) localRoot.left, item);
			
			if (increase) {
				decrementBalance(localRoot);
				if (localRoot.balance < AVLNode.LEFT_HEAVY) {
					increase = false;
					return rebalanceLeft(localRoot);
				}
			}
		}
		//Item is greater than the data
		else if (item.compareTo(localRoot.data) > 0) {
			localRoot.right = add((AVLNode<E>) localRoot.right, item);
			
			if (increase) {
				incrementBalance(localRoot);
				if (localRoot.balance < AVLNode.RIGHT_HEAVY) {
					increase = false;
					return rebalanceRight(localRoot);
				}
			}
		}
		//If rebalance is unnecessary
		return localRoot;
	}
	
	/**
	 * Method to rebalance left.
	 * @param localRoot Root of the AVL subtree that needs rebalancing
	 * @return A new localRoot
	 */
	private AVLNode<E> rebalanceLeft(AVLNode<E> localRoot) {
		//Obtain reference to left child
		AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;
		//See whether left-right heavy
		if (leftChild.balance > AVLNode.BALANCED) {
			//Obtain reference to left-right child
			AVLNode<E> leftRightChild = (AVLNode<E>) leftChild.right;
			/**
			 * Adjust the balances to be their new values after the rotations are 
			 * performed.
			 */
			if (leftRightChild.balance < AVLNode.BALANCED) {
				leftChild.balance = AVLNode.BALANCED;
				leftRightChild.balance = AVLNode.BALANCED;
				localRoot.balance = AVLNode.RIGHT_HEAVY;
			} else if (leftRightChild.balance > AVLNode.BALANCED){
				leftChild.balance = AVLNode.LEFT_HEAVY;
				leftRightChild.balance = AVLNode.BALANCED;
				localRoot.balance = AVLNode.BALANCED;
			} else {
				leftChild.balance = AVLNode.BALANCED;
				localRoot.balance = AVLNode.BALANCED;
			}
			//Perform left rotation
			localRoot.left = rotateLeft(leftChild);
		} else {
			/**
			 * Left-left case. In this case, the leftChild (the new root) and the 
			 * root (new right child) will both be balanced after the rotation.
			 */
			leftChild.balance = AVLNode.BALANCED;
			localRoot.balance = AVLNode.BALANCED;
		}
		//Rotate the local root right
		return (AVLNode<E>) rotateRight(localRoot);
	}
	
	/**
	 * Method to rebalance right.
	 * @param localRoot Root of the AVL subtree that needs rebalancing
	 * @return A new localRoot
	 */
	private AVLNode<E> rebalanceRight(AVLNode<E> localRoot) {
		//Obtain reference to right child
		AVLNode<E> rightChild = (AVLNode<E>) localRoot.right;
		//See whether left-right heavy
		if (rightChild.balance < AVLNode.BALANCED) {
			//Obtain reference to right-left child
			AVLNode<E> rightLeftChild = (AVLNode<E>) rightChild.left;
			/**
			 * Adjust the balances to be their new values after the rotations are 
			 * performed.
			 */
			if (rightLeftChild.balance < AVLNode.BALANCED) {
				rightChild.balance = AVLNode.RIGHT_HEAVY;
				rightLeftChild.balance = AVLNode.BALANCED;
				localRoot.balance = AVLNode.BALANCED;
			} else if (rightLeftChild.balance > AVLNode.BALANCED){
				rightChild.balance = AVLNode.BALANCED;
				rightLeftChild.balance = AVLNode.BALANCED;
				localRoot.balance = AVLNode.LEFT_HEAVY;
			} else {
				rightChild.balance = AVLNode.BALANCED;
				localRoot.balance = AVLNode.BALANCED;
			}
			//Perform right rotation
			localRoot.right = rotateRight(rightChild);
		} else {
			/**
			 * Right-right case. In this case, the rightChild (the new root) and the 
			 * root (new left child) will both be balanced after the rotation.
			 */
			rightChild.balance = AVLNode.BALANCED;
			localRoot.balance = AVLNode.BALANCED;
		}
		//Rotate the local root right
		return (AVLNode<E>) rotateLeft(localRoot);
	}
	
	/**
	 * Decrement balance of the node as we return from an insertion into a node's 
	 * left subtree. Also indicate if subtree height at that node has increased.
	 * @param node Node to decrement balance
	 */
	private void decrementBalance(AVLNode<E> node) {
		//Decrement the balance
		node.balance--;
		if(node.balance == AVLNode.BALANCED) {
			/** If now balanced, overall height has not increased */
			increase = false;
		}
	}
	
	/**
	 * Increments the balance of the node.
	 * @param node Node to increment balance
	 */
	private void incrementBalance(AVLNode<E> node) {
		//Increment the balance
		node.balance++;
		if(node.balance == AVLNode.BALANCED) {
			/** If now balanced, overall height has not increased */
			increase = false;
		}
	}
	
	
}
