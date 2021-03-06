# Splay-Tree-Impl

## Splay Tree Implementation containing a Binary Search Tree

The goal for this project is to build an abstract data structure (ADT) to bring a form of balance to a BST. Therefore, this implementation is based upon a BST, where its elements are being splayed to the root when inserting a new element, removing an existing element if found or the last accessed element if not, searching for an existing element and finding the minimum or maximum element in the tree. We move a node to the root position on the assumption that more accesses to that node are likely in the near future; that node will be near the root when those subsequent accesses happen.
