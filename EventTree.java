
/*The class 'EventTree' class is the RedBlackTree 
that holds all the events.*/

class Event 
{

	int eventID;
	int count;
	boolean color;
	
	public static final boolean BLACK = false;
	public static final boolean RED = true;
	public static Event nilNode;

	Event parent;
	Event left;
	Event right;
	
	Event(int id, int count) 
	{
		
		this.eventID = id;
		this.count = count;
		this.color = RED;
		
		this.parent = null;
		this.left = null;
		this.right = null;
	}
}

public class EventTree 
{
	
	Event root;
	Event nilNode;
	
	public static final boolean BLACK = false;
	public static final boolean RED = true;

		
	EventTree() 
	{
		nilNode = new Event(-7,-7);
		nilNode.color = BLACK;
		nilNode.left = nilNode;
		nilNode.right = nilNode;
		nilNode.parent = nilNode;
		
		this.root = nilNode;
	}
	
	/*The function increase increases the 'count' of the event
	*specified by the id by 'm'
	*/
	void increase(int id, int m)
	{
		Event x = locateEvent(id);
		if(x != nilNode) {
			x.count = x.count + m;
			System.out.println(x.count);
		} else {
			insert(new Event(id,m));
			System.out.println(m);
		}
	}

	/*
	*The insert method inserts an event whose key is assumed to have
	*already been filled in, into the red-black tree.
	*/
	void insert(Event n)
	{
		Event y = nilNode;
		Event x = this.root;
		
		while(x != nilNode) 
		{
			y = x;
			if(n.eventID < x.eventID) 
			{
				x = x.left;
			} 
			else 
			{
				x = x.right;
			}
		}
		
		n.parent = y;
		if(y == nilNode) 
		{
			this.root = n;
		} 
		else if(n.eventID < y.eventID) 
		{
			y.left = n;
		} 
		else 
		{
			y.right = n;
		}
		
		n.left = nilNode;
		n.right = nilNode;
		n.color = RED;
		insertFixup(n);
	}
	
	/*
	*The insertFixup method deals with the following cases
	*Case 1: z's uncle y is red
	*Case 2: z's uncle y is black and z is a right child
	*Case 3: z's uncle y is black and z is a left child
	*/
	void insertFixup(Event n) 
	{
		while(isRed(n.parent)) 
		{
			Event y = nilNode;
			if(n.parent == n.parent.parent.left) 
			{
				y = n.parent.parent.right;
				if(isRed(y)) 
				{
					n.parent.color = BLACK;
					y.color = BLACK;
					n.parent.parent.color = RED;
					n = n.parent.parent;
				} 
				else if(n == n.parent.right) 
				{
					n = n.parent;
					leftRotate(n);
					n.parent.color = BLACK;
					n.parent.parent.color = RED;
					rightRotate(n.parent.parent);
				} 
				else 
				{
					n.parent.color = BLACK;
					n.parent.parent.color = RED;
					rightRotate(n.parent.parent);					
				}
			} 
			else 
			{
				y = n.parent.parent.left;
				if(isRed(y)) 
				{
					n.parent.color = BLACK;
					y.color = BLACK;
					n.parent.parent.color = RED;
					n = n.parent.parent;
				} 
				else if(n == n.parent.left) 
				{
					n = n.parent;
					rightRotate(n);
					n.parent.color = BLACK;
					n.parent.parent.color = RED;
					leftRotate(n.parent.parent);
				} 
				else 
				{
					n.parent.color = BLACK;
					n.parent.parent.color = RED;
					leftRotate(n.parent.parent);					
				}
			}
		}
		this.root.color = BLACK;
	}
	
	
	/*The function reduce decreases the 'count' of the event
	*specified by the id by 'm'
	*/
	void reduce(int id, int m)
	{
		Event x = locateEvent(id);
		if(x != nilNode) 
		{
			x.count = x.count - m;
			if(x.count < 1) 
			{
				delete(x);
				System.out.println(0);
				return;
			}
			System.out.println(x.count);
		} 
		else 
		{
			System.out.println(0);
		}
	}

	//This method deletes the Event passed as input to it
	void delete(Event n) 
	{
		Event y = n;
		Event x = nilNode;
		boolean ycolor = y.color;
		if(n.left == nilNode) {
			x = n.right;
			transplant(n, n.right);
		} else if(n.right == nilNode) {
			x = n.left;
			transplant(n, n.left);
		} else {
			y = treeMin(n.right);
			ycolor = y.color;
			x = y.right;
			if(y.parent == n) {
				x.parent = y;
			} else {
				transplant(y, y.right);
				y.right = n.right;
				y.right.parent = y;
			}
			transplant(n, y);
			y.left = n.left;
			y.left.parent = y;
			y.color = n.color;
		}
		
		if(ycolor == BLACK){
			delete_fixup(x);
		}
	}

	/*Performs the rotations and coloring required to balance the tree if required after the deletion.
	*Case 1: x’s sibling w is red.
	*Case 2: x’s sibling w is black, and both of w’s children are black
	*Case 3: x’s sibling w is black, w’s left child is red, and w’s right child is black.
	*Case 4: x’s sibling w is black, and w’s right child is red
	*/
	void delete_fixup(Event x) 
	{
		Event w = nilNode;
		while( x != nilNode && x != this.root && x.color == BLACK) 
		{
			if( x == x.parent.left) 
			{
				w = x.parent.right;
				if(isRed(w)) 
				{
					w.color = BLACK;
					x.parent.color = RED;
					leftRotate(x.parent);
					w = x.parent.right;
				}
				if(w.left.color == BLACK && w.right.color == BLACK) 
				{
					w.color = RED;
					x = x.parent;
				} 
				else if(w.right.color == BLACK) 
				{
					w.left.color = BLACK;
					w.color = RED;
					rightRotate(w);
					w = x.parent.right;
					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.right.color = BLACK;
					leftRotate(x.parent);
					x = this.root;
				} 
				else 
				{	
					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.right.color = BLACK;
					leftRotate(x.parent);
					x = this.root;
				}
				
			} 
			else 
			{
				w = x.parent.left;
				if(isRed(w)) 
				{
				  w.color = BLACK;
				  x.parent.color = RED;
				  rightRotate(x.parent);
				  w = x.parent.left;
				}
				if(w.right.color == BLACK && w.left.color == BLACK) 
				{
				  w.color = RED;
				  x = x.parent;
				} 
				else if(w.left.color == BLACK) 
				{
				  w.right.color = BLACK;
				  w.color = RED;
				  leftRotate(w);
				  w = x.parent.left;
				  w.color = x.parent.color;
				  x.parent.color = BLACK;
				  w.left.color = BLACK;
				  rightRotate(x.parent);
				  x = this.root;
				} 
				else 
				{  
				  w.color = x.parent.color;
				  x.parent.color = BLACK;
				  w.left.color = BLACK;
				  rightRotate(x.parent);
				  x = this.root;
				}
			}
		}
		x.color = BLACK;
	}
	
	/*Transplant replaces one subtree as a child of its parent with
	another subtree.
	*/
	void transplant(Event u, Event v) {
		if(u.parent == nilNode) {
			this.root = v;
		} else if(u == u.parent.left) {
			u.parent.left = v;
		} else {
			u.parent.right = v;
		}
		v.parent = u.parent;
	}
	
	/*
	*The left rotation function allows the 
	*rebalancing of an unbalanced tree/subtree 
	*/
	
	void leftRotate(Event x) 
	{
		Event y = x.right;
		x.right = y.left;
		if(y.left != nilNode) 
		{
			y.left.parent = x;
		}
		y.parent = x.parent;
		
		if(x.parent == nilNode)
		{
			this.root = y;
		} 
		else if( x == x.parent.left)
		{
			x.parent.left = y;
		} 
		else 
		{
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;
	}
	
	/*
	*The right rotation function allows the 
	*rebalancing of an unbalanced tree/subtree 
	*/
	void rightRotate(Event x) {

		Event y = x.left;
		x.left = y.right;
		if(y.right != nilNode) 
		{
		  y.right.parent = x;
		}
		y.parent = x.parent;

		if(x.parent == nilNode)
		{
		  this.root = y;
		} 
		else if( x == x.parent.right)
		{
		  x.parent.right = y;
		} 
		else 
		{
		  x.parent.left = y;
		}
		y.right = x;
		x.parent = y;		
	}
	/*The function count prints the'count' of the event
	*/
	void count(int id) 
	{
		Event x = locateEvent(id);
		if(x != nilNode) 
		{
			System.out.println(x.count);
		} 
		else 
		{
			System.out.println(0);
		}
	}
	

	/*The function inrange prints the total count of the events
	* between the specified ids
	*/
	void inrange(int id1, int id2) 
	{
		int total_count = totalKeyCount(id1,id2,this.root);
		System.out.println(total_count);
	}
	
	/*The function next prints the 'count'  the count of the event with
	* the lowest ID that is greater that theID and prints
	* '0 0', if there is no next ID
	*/
	void next(int id) 
	{
		Event next = nilNode;
		
		
		if(this.root == nilNode) 
		{
			System.out.println("0 0");
		}
		
		
		next = treeMin(this.root);
		if(id < next.eventID) 
		{
			System.out.println(next.eventID + " " + next.count);
			return;
		}
		next = nilNode;
		

		next = treeMax(this.root);
		
		if(id > next.eventID) 
		{
			System.out.println("0 0");
			return;
		}
		next = nilNode;
		
		Event current = locateEvent(id);
		if(current != nilNode) 
		{
			
			next = findSuccessor(current);
			if(next == nilNode) 
			{
				
				System.out.println("0 0");
			}
			else 
			{
				System.out.println(next.eventID + " " + next.count);
			}
		} 
		else 
		{
			closestNextEvent(id, this.root);
		}
	}
	
	/* Auxiliary function to help calculate 'next'
	*/
	void closestNextEvent(int id, Event n) {
		if( id < n.eventID) {
			Event predeccessor = findPredecessor(n);
			if( id >= predeccessor.eventID) {
				System.out.println(n.eventID + " " + n.count);
				return;
			} else {
				closestNextEvent(id,n.left);
			}
		} else {
			Event successor = findSuccessor(n);
			if( id < successor.eventID) {
				System.out.println(successor.eventID + " " + successor.count);
				return;
			} else {
				closestNextEvent(id, n.right);
			}
		}
	}
	
	/* The 'previous' function, prints the ID and the 
	*count of the event with greatest key that is 
	*less that theID and prints '0 0', if there is no previous ID.
	*/
	void previous(int id) 
	{
		Event predeccessor = nilNode;
		
		
		if(this.root == nilNode) 
		{
			System.out.println("0 0");
		}
		
		
		predeccessor = treeMin(this.root);
		if(id < predeccessor.eventID) 
		{
			System.out.println("0 0");
			return;
		}
		predeccessor = nilNode;
		
		
		predeccessor = treeMax(this.root);
		if(id > predeccessor.eventID) 
		{
			System.out.println(predeccessor.eventID + " " + predeccessor.count);
			return;
		}
		predeccessor = nilNode;
		
		
		Event current = locateEvent(id);
		if(current != nilNode) 
		{
			
			predeccessor = findPredecessor(current);
			if(predeccessor == nilNode) 
			{	
				System.out.println("0 0");
			} else 
			{	
				System.out.println(predeccessor.eventID + " " + predeccessor.count);
			}
		} 
		else
		{			
			closestPreviousEvent(id, this.root);
		}
	}
	/*
	*Determines the closest event with a lower eventID 
	*/
	void closestPreviousEvent(int id, Event n) 
	{
		if( id <= n.eventID) 
		{
			Event predeccessor = findPredecessor(n);
			if( id > predeccessor.eventID) 
			{
				System.out.println(predeccessor.eventID + " " + predeccessor.count);
				return;
			} 
			else 
			{
				closestPreviousEvent(id,n.left);
			}
		}
		else 
		{
			Event successor = findSuccessor(n);
			if( id <= successor.eventID) 
			{
				System.out.println(n.eventID + " " + n.count);
				return;
			} 
			else 
			{
				closestPreviousEvent(id, n.right);
			}
		}
	}
	/*
	*Determines the total keycount 
	*within the specified range
	*/
	int totalKeyCount(int start, int end, Event r) 
	{
		int total_count = 0;
		
		if(r == nilNode) 
		{
			return total_count;
		}
		
		if(start < r.eventID) 
		{
			total_count = total_count + totalKeyCount(start,end,r.left);
		}
		
		if(start <= r.eventID && end >= r.eventID) 
		{
			total_count = total_count + r.count;
		}
		
		if(end > r.eventID) 
		{
			total_count = total_count + totalKeyCount(start,end,r.right);
		}
		return total_count;
	}

	/*
	*Finds the predecessor of the node 
	*passed to it as an argument
	*/

	Event findSuccessor(Event current) 
	{
		Event next = nilNode;
		
		
		if(current.right != nilNode) 
		{
			next = treeMin(current.right);
		} 
		else 
		{
			
			Event p = current.parent;
			while(p != nilNode && current == p.right) 
			{
				current = p;
				p = p.parent;
			}
			next = p;
		}
		return next;
	}
	
	/*
	*Finds the predecessor of the node 
	*passed to it as an argument
	*/
	Event findPredecessor(Event current) 
	{
		Event predeccessor = nilNode;
		if(current.left != nilNode) {
			predeccessor = treeMax(current.left);
		} else {

			Event p = current.parent;
			while(p != nilNode && current == p.left) {
				current = p;
				p = p.parent;
			}
			predeccessor = p;
		}
		return predeccessor;
	}
	
	/*
	*Finds the event by using the id specified to 
	*locate the respective evnet
	*/
	Event locateEvent(int id) 
	{
		Event r = this.root;
		while(r != nilNode && r.eventID != id) 
		{
			if(id < r.eventID) 
			{
				r = r.left;
			} 
			else 
			{
				r = r.right;
			}
		}
		return r;
	}	
	

	boolean isRed(Event n) 
	{
		if(n != nilNode) 
		{
			return n.color == RED;
		} 
		else 
		{
			return false;
		}
	}
	
	/*Returns the leftmost node of the tree i.e. the
	*event with the lowest eventID
	*/
	Event treeMin(Event x) 
	{
		while(x.left != nilNode) 
		{
			x = x.left;
		}
		return x;
	}

	Event treeMax(Event x) 
	{
		while(x.right != nilNode) 
		{
			x = x.right;
		}
		return x;
	}
	

} 
