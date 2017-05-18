# rbtree_implementation
This is an implementation of a red black tree coded in Java.

DESCRIPTION [1]

A red-black tree is a balanced binary search tree and is characterized by the following properties.
Every node is colored red or black.
Every leaf is a NIL node, and is colored black.
If a node is red, then both its children are black.
Every simple path from a node to a descendant leaf contains the same number of black nodes.

TIME COMPLEXITY [2]
Algorithm		Average	Worst Case
Space		        O(n)	    O(n)
Search		O(log n)[1]	O(log n)[1]
Insert		O(log n)[1]	O(log n)[1]
Delete		O(log n)[1]	O(log n)[1]

#REFERENCES
1.https://www.cs.auckland.ac.nz/~jmor159/PLDS210/niemann/s_rbt.htm
2.https://en.wikipedia.org/wiki/Red%E2%80%93black_tree
