package SPLT_A4;


public class SPLT implements SPLT_Interface {
	public BST_Node root;
	int size;
	  
	public SPLT(){
		size = 0;
		root = null;
	}
	  
	@Override
	//used for testing, please leave as is
	public BST_Node getRoot(){
		return root;
	}

	@Override
	public void insert(String s) { //It was me
		if (root == null) {
			root = new BST_Node(s);
			size += 1;
		}
		
		else {
			root = root.insertNode(s);
			if (root.newNodeToIns) { // allowing not duplicated insertion
				size += 1;
			}
		}
	}

	@Override
	public void remove(String s) {  //DIO
		root = root.containsNode(s);
		if (root.data == s) { // if the node we want to delete, proceed
			BST_Node leftTree = root.left;
			BST_Node rightTree = root.right;
			
			if (size() == 1) // removing the root node
				root = null;
			else{
				root.right = null;
				root.left = null;
			}
			
			if (leftTree != null) { // while left subtree exists. If right subtree exists, find max in left subtree and assign as root, then link. 
				leftTree.par = null;
				root = leftTree;
				if (rightTree != null) {
					rightTree.par = null;
					root = leftTree.findMax();
					root.right = rightTree;
					rightTree.par = root;
				}
			}
			else if (rightTree != null) { // if left tree does not exists, then right subtree will be our new tree
				root = rightTree;
				root.par = null;
				
			}
			size--;

		}
	}

	@Override
	public String findMin() {
		if (empty()) {
			return null;
		} else {
			root = root.findMin();
			return root.data;
		}
	}

	@Override
	public String findMax() {
		if (empty()) {
			return null;
		} else {
			root = root.findMax();
			return root.data;
		}
	}

	@Override
	public boolean empty() {
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean contains(String s) {
		if (!empty()) { 
			root = root.containsNode(s);
			if(root.justMade == false) { // utilizing helper field to know if we are dealing with a duplicate
				return true;
			}
		}
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int height() {
		if (empty()) {
			return -1;
		} else {
			return root.getHeight();
		}
	}
}
