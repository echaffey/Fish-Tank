
public class Node 
{
	Node next;			//next node in the link list
	Object item;		//item contained in the node
	
	/**
	 * creates a new empty node
	 */
	public Node()
	{
		setNext(null);
		setItem(null);
	}
	
	public void setNext(Node nextNode)
	{
		next = nextNode;
	}
	
	public void setItem(Object newItem)
	{
		item = newItem;
	}
	
	public Node getNext()
	{
		return next;
	}
	
	public Object getItem()
	{
		return item;		
	}

}
