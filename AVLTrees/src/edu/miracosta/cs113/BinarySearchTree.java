package edu.miracosta.cs113;

public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> implements SearchTree<E> {
	//Data Fields
	/**
	 * Return value from the public add method.
	 */
	protected boolean addReturn;
	/**
	 * Return value from the public delete method.
	 */
	protected E deleteReturn;
	
	/**
	 * Starter method find. The target object must implement the Comparable interface.
	 * @param target The Comparable object being sought
	 * @return The object, if found, otherwise null
	 */
	@Override
	public E find(E target) {
		return find(root, target);
	}
	
	/**
	 * Recursive find method.
	 * @param localRoot The local subtree's root
	 * @param target The object being sought
	 * @return The object, if found, otherwise null
	 */
	private E find(Node<E> localRoot, E target) {
		if (localRoot == null) {
			return null;
		}
		
		//Compare the target with the data field at the root.
		int compResult = target.compareTo(localRoot.data);
		if (compResult == 0) {
			return localRoot.data;
		}
		else if (compResult < 0) {
			return find(localRoot.left, target);
		}
		else {
			return find(localRoot.right, target);
		}
	}
	
	/**
	 * Starter method add. The object to insert must implement the Comparable interface.
	 * 
	 */
	@Override
	public boolean add(E item) {
		root = add(root, item);
		return addReturn;
	}
	
	/**
	 * Recursive add method. The data field addReturn is set true if the item is added to
	 * the tree, false if the item is already in the tree.
	 * @param localRoot The local root of the subtree
	 * @param item The object to be inserted
	 * @return The new local root that now contains the inserted item
	 */
	private Node<E> add(Node<E> localRoot, E item) {
		if (localRoot == null) {
			//Item is not in the tree -- insert it
			addReturn = true;
			return new Node<>(item);
		}
		else if (item.compareTo(localRoot.data) == 0) {
			//Item is equal to localRoot.data
			addReturn = false;
			return localRoot;
		}
		else if (item.compareTo(localRoot.data) < 0) {
			//Item is less than localRoot.data
			localRoot.left = add(localRoot.left, item);
			return localRoot;
		}
		else {
			//Item is greater than localRoot.data
			localRoot.right = add(localRoot.right, item);
			return localRoot;
		}
	}
	
	/**
	 * Method sees if tree contains the target object. 
	 * @param target The object to be searched for
	 * @return Returns true if the object is in the tree, else returns false
	 */
	@Override
	public boolean contains(E target) {
		return (find(target) != null);
	}
	
	/**
	 * Starter method delete. The object will be removed from the tree.
	 * @param target The object to be deleted
	 * @return The object deleted from the tree or null if the object was not in the tree
	 */
	@Override
	public E delete(E target) {
		root = delete(root, target);
		return deleteReturn;
	}
	
	/**
	 * Recursive delete method. The item is removed from the tree. deleteReturn is equal to 
	 * the deleted item as it was stored in the tree or null if the item was not found.
	 * @param localRoot The root of the current subtree
	 * @param item The item to be deleted
	 * @return The modified local root that does not contain the item
	 */
	private Node<E> delete(Node<E> localRoot, E item) {
		if (localRoot == null) {
			//Item is not in the tree
			deleteReturn = null;
			return localRoot;
		}
		
		//Search for item to delete
		int compResult = item.compareTo(localRoot.data);
		if (compResult < 0) {
			//Item is smaller than localRoot.data
			localRoot.left = delete(localRoot.left, item);
			return localRoot;
		}
		else if (compResult > 0) {
			//Item is larger than localRoot.data
			localRoot.right = delete(localRoot.right, item);
			return localRoot;
		}
		else {
			//Item is at local root
			deleteReturn = localRoot.data;
			if (localRoot.left == null) {
				//If there is no left child, return right child, which could also be null
				return localRoot.right;
			}
			else if (localRoot.right == null) {
				//If there is no right child, return left child
				return localRoot.left;
			}
			else {
				//Node being deleted has 2 children, replace the data with inorder predecessor
				if (localRoot.left.right == null) {
					//The left child has no right child
					//Replace the data with the data in the left child
					localRoot.data = localRoot.left.data;
					//Replace the left child with its left child
					localRoot.left = localRoot.left.left;
					return localRoot;
				}
				else {
					//Search for the inorder predecessor and replaced deleted node's data with it
					localRoot.data = findLargestChild(localRoot.left);
					return localRoot;
				}
			}
		}
	}
	
	/**
	 * Removes the object from the tree.
	 * @param target The object to be removed from the tree
	 * @return Returns true if the target is found and removed, else returns false.
	 */
	@Override
	public boolean remove(E target) {
		return (delete(target) != null);
	}
	
	/**
	 * Find the node that is the inorder predecessor and replace it with its left child (if any). The 
	 * inorder predecessor is removed from the tree.
	 * @param parent The parent of possible inorder predecessor
	 * @return The data in the inorder predecessor
	 */
	private E findLargestChild(Node<E> parent) {
		//If the right child has no right child, it is the inorder predecessor.
		if (parent.right.right == null) {
			E returnValue = parent.right.data;
			parent.right = parent.right.left;
			return returnValue;
		}
		else {
			return findLargestChild(parent.right);
		}
	}
}
