
public class LinkList 
{
	//maximum number of fish in the tank at any time
	private final int MAX_FISH = 10;

	private Node head ;			//first node in the link list
	private Node current;		//current node 

	public LinkList()
	{
		head = new Node();
		current = head;
	}

	/**
	 * 
	 * @return current node
	 */
	public Node getCurrent()
	{
		return current;
	}

	/**
	 * checks to see if there is an other node following the current node
	 * @return true if not the end of the list
	 */
	public boolean hasNext()
	{
		return current.getNext()!= null;
	}

	/**
	 *moves the current node positon to the next node 
	 */
	public void next()
	{
		current=current.getNext();
	}

	
	/**
	 * resets the current position to the start of the list
	 */
	public void reset()
	{
		current = head;
	}

	/**
	 * checks if the list is empty
	 * @return true if head of the list is null
	 */
	public boolean isEmpty()
	{
		return head == null;
	}

	/**
	 * gets the total number of nodes in the list
	 * @return number of nodes
	 */
	public int length()
	{
		return length(head);
	}

	/**
	 * recursivly counts the number of nodes in the list defined by the parameter passed
	 * @param node defines the start of the link list
	 * @return the number of nodes in the list
	 */
	public int length(Node node)
	{
		int result;

		if(node == null)
			result = 0;
		else
			return 1 + length(node.getNext());

		return result;
	}

	/**
	 * gets the item contained in the current node 
	 * @return current nodes item
	 */
	public Object getItem()
	{
		return current.getItem();
	}

	//Adds a new fish to the list
	public void add(Object someObject)
	{
		//cant have more than 8 fish in the tank at one time
		if(length() < MAX_FISH)
		{
			Node placeHolder = new Node();

			placeHolder.setItem(someObject);

			placeHolder.setNext(head);

			head = placeHolder;
		}
	}

	//removes a selected fish and returns that fish object
	public Object remove(Object obj)
	{
		Node current = head.getNext();
		Node previous = head;

		while(current != null)
		{
			if(current.getItem()==obj)
			{
				if(current.getNext() != null)
					previous.setNext(current.getNext());
				else
					previous.setNext(null);

				return current.getItem();
			}
			current = current.getNext();
		}

		return null;
	}

	public void sortName()
	{
		sortName(head);
	}

	/***
	 * sorts the list by name
	 * @param someHead head of the list to sort
	 * @return
	 */
	public Node sortName(Node someHead)
	{
		Node result= null;
		Node newHead = null;
		Node tail = newHead;
		Node min = someHead;

		if(someHead!=null)
		{
			Node current = someHead.getNext();
			Node prev = someHead;
			Node before = null;

			while(current != null)
			{
				TileIcon t = ((TileIcon)current.getItem());
				if(t != null)
				{
					if(t.getName().compareTo(t.getName())<0)
					{
						min=current;				
						before = prev;
					}
				}

				current = current.getNext();
				prev= prev.getNext();
			}

			if(before==null)
			{
				someHead=someHead.getNext();
			}
			else
				before.setNext(min.getNext());

			min.setNext(sortName(someHead));
			result = min;
		}

		return result;
	}

}
