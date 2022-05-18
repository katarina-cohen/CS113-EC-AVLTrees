package edu.miracosta.cs113;

import java.util.Random;

public class Driver {
	public static void main(String[] args) {
		int[] data = randomNumbers();
		printBST(data);
		printAVL(data);
	}
	
	public static int[] randomNumbers() {
		Random random = new Random();
		int[] numbers = new int[20];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = random.nextInt(100);
		}
		return numbers;
	}
	
	public static void printBST(int[] array) {
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		
		for (int i = 0; i < array.length; i++) {
			bst.add(array[i]);
		}
		
		System.out.println(bst.toString2());
		System.out.print("Inorder traversal of BST: ");
		bst.inorderTraverse(bst.root);
		System.out.print("\nHeight of BST: " + bst.height(bst.root));
	}
	
	public static void printAVL(int[] array) {
		AVLTree<Integer> avl = new AVLTree<Integer>();
		
		for (int i = 0; i < array.length; i++) {
			avl.add(array[i]);
		}
		
		System.out.println(avl.toString2());
		System.out.print("Inorder traversal of AVL tree: ");
		avl.inorderTraverse(avl.root);
		System.out.print("Height of AVL Tree: " + avl.height(avl.root));		
	}

}
