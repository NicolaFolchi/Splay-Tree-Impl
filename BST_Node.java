package SPLT_A4;

public class BST_Node {
	String data;
	BST_Node left;
	BST_Node right;
	BST_Node par; // parent...not necessarily required, but can be useful in splay tree
	boolean justMade; // could be helpful if you change some of the return types on your BST_Node insert.
	boolean newNodeToIns = true; // helper field to prevent inserting duplicates
	
	BST_Node(String data) {
		this.data = data;
		this.justMade = true;
	}

	BST_Node(String data, BST_Node left, BST_Node right, BST_Node par) { // feel free to modify this constructor to suit
																			// your needs
		this.data = data;
		this.left = left;
		this.right = right;
		this.par = par;
		this.justMade = true;
	}

	// --- used for testing ----------------------------------------------
	//
	// leave these 3 methods in, as is (meaning also make sure they do in fact
	// return data,left,right respectively)

	public String getData() {
		return data;
	}

	public BST_Node getLeft() {
		return left;
	}

	public BST_Node getRight() {
		return right;
	}

	// --- end used for testing -------------------------------------------

	public BST_Node containsNode(String s) { 
		if (data.equals(s)) {
			justMade = false;		//justMade identifies if the node already exists
			return splay(this);
			
		}
		if (data.compareTo(s) > 0) {
			if (left == null)
				return splay(this);
			return left.containsNode(s);
		}
		if (data.compareTo(s) < 0) {
			if (right == null)
				return splay(this);
			return right.containsNode(s);
		}
		return splay(this); // shouldn't hit
		
	}

	public BST_Node insertNode(String s) {
		
		if (data.compareTo(s) == 0) {  //comparing if already exists.
			this.newNodeToIns = false;
			return splay(this);
		}

		if (data.compareTo(s) > 0) {
			if (left == null) {
				left = new BST_Node(s);
				left.par = this;
				return splay(left);
			}
			return left.insertNode(s);
		}
		if (data.compareTo(s) < 0) {
			if (right == null) {
				right = new BST_Node(s);
				right.par = this;
				return splay(right);
			}
			return right.insertNode(s);
		}
		return splay(this);// ie we have a duplicate
	}

	public boolean removeNode(String s) { // DIO
		//data.contains(s);
		BST_Node found = this.containsNode(s);
		if(found.data == s) {
			return true;
		} else {
			return false;
		}
	}

	public BST_Node findMin() {
		if (left != null)
			return left.findMin();
		splay(this);
		return this;
	}

	public BST_Node findMax() {
		if (right != null)
			return right.findMax();
		splay(this);
		return this;
	}

	public int getHeight() {
		int l = 0;
		int r = 0;
		if (left != null)
			l += left.getHeight() + 1;
		if (right != null)
			r += right.getHeight() + 1;
		return Integer.max(l, r);
	}

	public String toString() {
		return "Data: " + this.data + ", Left: " + ((this.left != null) ? left.data : "null") + ",Right: "
				+ ((this.right != null) ? right.data : "null");
	}

	private void rotateRight(BST_Node k2) {
	
		BST_Node k1 = k2.left;
		
		if (k2.par == null) {		//rotation involving changes to two nodes only
			if (k1.right == null) {
				k2.left = null;
			} else {
				k2.left = k1.right;
				k1.right.par = k2;
			}
			k1.par = k2.par;
			k1.right = k2;
			k2.par = k1;
			
		}
		
		else if (k2.par.left == k2) {	//rotation when tree is bushier, dealing with grandparents
			if (k1.right == null) {
				k2.left = null;
			} else {
				k2.left = k1.right;
			}
			k1.par = k2.par;
			k1.right = k2;
			k2.par.left = k1;
			k2.par = k1;

		}
		
		else if (k2.par.right == k2) {
			if (k1.right == null) {
				k2.left = null;
			} else {
				k2.left = k1.right;
			}
			k1.par = k2.par;
			k1.right = k2;
			k2.par.right = k1;
			k2.par = k1;

		}
	}

	private void rotateLeft(BST_Node k2) {
	
		BST_Node k1 = k2.right;
		if (k2.par == null) {		//rotation involving changes to two nodes only
			if (k1.left == null) {
				k2.right = null;
			} else {
				k2.right = k1.left;
				k1.left.par = k2;
			}
			k1.par = k2.par;
			k1.left = k2;
			k2.par = k1;
		}
		
		else if (k2.par.right == k2) {	//rotation when tree is bushier, dealing with grandparents
			if (k1.left == null) {
				k2.right = null;
			} else {
				k2.right = k1.left;
				k1.left.par = k2;
			}
			k1.par = k2.par;
			k1.left = k2;
			k2.par.right = k1;
			k2.par = k1;

		}
		
		else if (k2.par.left == k2) {
			if (k1.left == null) {
				k2.right = null;
			} else {
				k2.right = k1.left;
				k1.left.par = k2;
			}
			k1.par = k2.par;
			k1.left = k2;
			k2.par.left = k1;
			k2.par = k1;
		}
	}

	private BST_Node splay(BST_Node toSplay) {
		while (toSplay.par != null) {

			if (toSplay.par.par == null) { // one rotation only (zig)
				if (toSplay.par.left == toSplay) {
					rotateRight(toSplay.par);
				} else {
					rotateLeft(toSplay.par);
				}
			}

			else if (toSplay.par.left == toSplay && toSplay.par.par.left == toSplay.par) { // in line two rotations (zig-zig)
				rotateRight(toSplay.par.par);
				rotateRight(toSplay.par);
			} else if (toSplay.par.right == toSplay && toSplay.par.par.right == toSplay.par) {
				rotateLeft(toSplay.par.par);
				rotateLeft(toSplay.par);
			}

			else if (toSplay.par.left == toSplay && toSplay.par.par.right == toSplay.par) { // zig zag rotation
				rotateRight(toSplay.par);
				rotateLeft(toSplay.par);
			} else if (toSplay.par.right == toSplay && toSplay.par.par.left == toSplay.par) {
				rotateLeft(toSplay.par);
				rotateRight(toSplay.par);
			}
		}
		return toSplay;
	}
}