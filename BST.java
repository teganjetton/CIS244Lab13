/*
 * Tegan Jetton
 * CIS 244 IK
 * Lab 13
 * 05/14/23
 * This program builds a Binary Search Tree that can be searched
 * and edited by the user. It allows the user to search if a node
 * exists (returns boolean), insert a new node, delete a node, 
 * and print out minimum/maximum values, PostOrder and InOrder.
 */

import java.util.Scanner;

// CREATE A HELPER CLASS
class Node {

	int data;
	Node left, right;

	public Node(int item) {
		data = item;
		left = right = null;
	}
}

public class BST {
	Node root; // root of BST

	BST() {
		root = null;
	}
	
	// insert a node
	Node insertRec(Node root, int data) {
		// insert into tree
		if (root == null) {
			root = new Node(data);
			return root;
		}
		if (data < root.data) // insert left child
			root.left = insertRec(root.left, data);
		else if (data > root.data) // insert right child
			root.right = insertRec(root.right, data);

		return root;
	}
	
	// search to see if a node is in the tree
	boolean searchRec(Node root, int data) {
		if (root == null) {
			return false;
		}
		else if (root.data == data) {
			return true;
		}
		else if (root.data < data) {
			return searchRec(root.right, data);
		}
		return searchRec(root.left, data);
	}
	
	// delete a node
	Node deleteRec(Node root, int data) {
		if (root == null) {
			return root;
		}
		if (data < root.data) // delete left child
			root.left = deleteRec(root.left, data);
		else if (data > root.data) // delete right child
			root.right = deleteRec(root.right, data);
		else {
			if (root.left == null) {
				return root.right;
			}
			else if (root.right == null) {
				return root.left;
			}
			
			root.data = findMin(root.right);
			root.right= deleteRec(root.right, root.data);
		}
		return root;
	}

	// helper functions
	// call insertRec from main
	void insert(int data) {
		root = insertRec(root, data);
		System.out.println("New item inserted into tree => " + 
                               data);
	}

	// perform an inorder traversal
	void inorderRec(Node root) {
		if(root != null) {
			inorderRec(root.left);
			System.out.print(root.data + " ");
			inorderRec(root.right);
		}
	}
	
     // perform a postorder traversal
	void postorderRec(Node root) {
		if (root != null) {
			postorderRec(root.left);
			postorderRec(root.right);
			System.out.print(root.data + " ");
		}
	}

	// Returns the max value in a binary tree
	static int findMax(Node node) {
		if (node == null)
		 return Integer.MIN_VALUE;
		int res = node.data;
		int lres = findMax(node.left);
		int rres = findMax(node.right);
		if (lres > res)
			res = lres;
		if (rres > res)
			res = rres;
		return res;
	}
	
	// Returns the min value in a binary tree
	static int findMin(Node node) {
		if (node == null)
		 return Integer.MAX_VALUE;
		int res = node.data;
		int lres = findMin(node.left);
		int rres = findMin(node.right);
		if (lres < res)
			res = lres;
		if (rres < res)
			res = rres;
		return res;
	}

	// Prints the node values of the binary tree
	static int printNodes(Node node) {
		if (node == null) {
		 System.out.println("No nodes to print");
		 //return Integer.MAX_VALUE;
		}
		int res = node.data;
		int lres = printNodes(node.left);
		int rres = printNodes(node.right);
		return res;
	}
		
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
      	BST tree = new BST(); // create tree object
		/* Tree operations ************************/
		// build the tree
		// insert into tree
		tree.insert(27);
		tree.insert(13);
		tree.insert(42);
		tree.insert(6);
		tree.insert(17);
		tree.insert(33);
		tree.insert(48);
		
		int choice=-1;
		while(choice != 0) {
			System.out.println("\n\nSelect an operation:\n1. Search for a node (returns boolean)\n2. Insert a node\n3. Delete a node\n4. Print Operations\n\n0. Exit");
			System.out.print("Selection: ");
			choice = sc.nextInt();
			
			if(choice == 1) {
				System.out.print("Input a number to search for: ");
				int num = sc.nextInt();
				if(tree.searchRec(tree.root, num)) {
					System.out.println(num+" is in the BST");
				}
				else{
					System.out.println(num+" is not in the BST");
				}
			}
			else if(choice ==2) {
				System.out.print("Input a number to insert: ");
				int num = sc.nextInt();
				tree.insert(num);
			}
			else if(choice ==3) {
				System.out.print("Input a number to delete: ");
				int num = sc.nextInt();
				// searches tree first, if number is found calls deleteRec, 
				// if not found informs user that number is not in tree, then prints out nums in tree
				if(tree.searchRec(tree.root, num)) {
					System.out.print("Tree before deletion: ");
					tree.inorderRec(tree.root);
					// call deleteRec method
					tree.deleteRec(tree.root, num);
					
					System.out.print("\nTree after deletion: ");
					tree.inorderRec(tree.root);
				}
				else {
					System.out.println("\n"+num+" is not in the BST, cannot delete.");
				}
			}
			else if(choice == 4) {
				int choice2 = -1;
				while(choice2 != 0) {
					System.out.println("\n----Print Operations----\n\n1. Max Element\n2. Min Element \n3. PostOrder\n4. InOrder\n\n0. Return to main menu");
					System.out.print("Selection: ");
					choice2 = sc.nextInt();
					if(choice2 == 1) {
						// print max element of tree
						System.out.println("\nMaximum element is: " + tree.findMax(tree.root));
					}
					else if(choice2 == 2) {
						// print min element of tree
						System.out.println("\nMinimum element is: " + tree.findMin(tree.root));
					}
					else if(choice2 == 3) {
						// print postorder of tree
						System.out.println("\nPostorder: ");
						tree.postorderRec(tree.root);
					}
					else if(choice2 == 4) {
						// print inorder of tree
						System.out.println("\nInorder: ");
						tree.inorderRec(tree.root);
					}
				}
			}
		}
		// always close your scanner :)
		sc.close();
	}
}