package Application;

//  Created by Clayton Paplaczyk
//  Description: A linked list is a sequence of nodes with efficient
//		element insertion and removal.
//		This class contains a subset of the methods of the
//		standard java.util.LinkedList class.

import java.util.NoSuchElementException;

public class LinkedList
{
//nested class to represent a node
private class Node
{
       public Object data;
       public Node next;
}

//only instance variable that points to the first node.
private Node first;

// Constructs an empty linked list.
public LinkedList()
{
   first = null;
}

// Returns the first element in the linked list.
public Object getFirst()
{
   if (first == null)
    {
      NoSuchElementException ex = new NoSuchElementException();
      throw ex;
    }
   else
      return first.data;
}

// Removes the first element in the linked list.
public Object removeFirst()
{
   if (first == null)
    {
      NoSuchElementException ex = new NoSuchElementException();
      throw ex;
    }
   else
    {
      Object element = first.data;
      first = first.next;  //change the reference since it's removed.
      return element;
    }
}

// Adds an element to the front of the linked list.
public void addFirst(Object element)
{
   //create a new node
   Node newNode = new Node();
   newNode.data = element;
   newNode.next = first;
   //change the first reference to the new node.
   first = newNode;
}

// Returns an iterator for iterating through this list.
public ListIterator listIterator()
{
   return new LinkedListIterator();
}

// toString method concatenates strings in the linked list and returns in the following format.
public String toString()
{
	LinkedListIterator iterator = new LinkedListIterator();
	String result = "{ ";
	while(iterator.hasNext())
		result+=(iterator.next()+" ");
	result += "}\n";
	return result;
}

// isEmpty method returns true if the link list is empty and returns false otherwise
public boolean isEmpty()
{
	LinkedListIterator it = new LinkedListIterator();
	
	if(!it.hasNext())
		return true; //link list is empty
	else
		return false; //link list is not empty
}

// Returns an element at the given index by its parameter.
public Object getElement(int index)
{
	LinkedListIterator it = new LinkedListIterator();
	
	for(int i = 0; i<index; i++){
		it.next();
	}
	return it.next();
}

// Sets the parameter element at the parameter specified index by replacing the element at the index.
public void setElement(int index, Object element)
{
	LinkedListIterator iterator = new LinkedListIterator();
	
	if(index < 0){ //if no element is found
		IndexOutOfBoundsException ex = new IndexOutOfBoundsException();
		throw ex;
	}
	
	if(index <= size()){
	for(int i = 0; i<index; i++){
			iterator.next();
		}
		iterator.next();
		iterator.set(element);
	}
	else{
		IndexOutOfBoundsException ex = new IndexOutOfBoundsException();
		throw ex;
	}
}

// Adds the parameter element at the parameter specified index. 
public void addElement(int index, Object element)
{
	if(index < 0){ //if no element is found
		IndexOutOfBoundsException ex = new IndexOutOfBoundsException();
		throw ex;
	}
	
	LinkedListIterator iterator = new LinkedListIterator();
	
	for(int i = 0; i<index; i++){
		if(iterator.hasNext())
			iterator.next();
		else{
			NoSuchElementException ex = new NoSuchElementException();
			throw ex;
		}
	}
	if(iterator.hasNext()){
		Object a = iterator.next();
		iterator.set(element);
		while(iterator.hasNext()){
			Object b = iterator.next();
			iterator.set(a);
			a = b;
		}
		iterator.add(a);
	}
	else
		iterator.add(element);
}

// Adds the parameter element at the beginning of the linked list of the number of times specified
public void addFewAtBeginning(int howMany, Object element)
{
	for(int i=0; howMany > i; i++){ // For loop enters the new element howMany time(s).
		Node newNode = new Node();
		newNode.data = element;
		newNode.next = first;
		//change the first reference to the new node.
		first = newNode;
	}
}

// Searches the parameter element in the linked list and removes all occurrences of the element in the linked list.
public Object searchAndRemove(Object element)
{
	// need to convert to String..********************************************************************************
	//************************************************************************************************************
	int b = (int)element; //casts element as an integer.
	
	if(b == 0){
		NoSuchElementException ex = new NoSuchElementException();
		throw ex;
	}
	if(b > (size()-1)){
		NoSuchElementException ex = new NoSuchElementException();
		throw ex;
	}
	
	Object result = null;
	LinkedListIterator it = new LinkedListIterator();
	result = getElement(b);
	
	if(b < size()-1){
		for(int i = 0; i< b +1; i++)
			it.next();
		while(b < size()-2){
			it.set(getElement(b +1));
			it.next();
			b++;
		}
		it.remove();
	}
	else{
		for(int i=0; i<b+1; i++)
			it.next();
		it.remove();
	}
	result = (Object)b;
	return result;
}

// Gets size of linked list.
public int size(){
	LinkedListIterator iterator = new LinkedListIterator();
	
	int a =0;
	while(iterator.hasNext()){
		iterator.next();
		a++;
	}
	return a;
}

//nested class to define its iterator
private class LinkedListIterator implements ListIterator
{
   private Node position; //current position
   private Node previous; //it is used for remove() method

   // Constructs an iterator that points to the front
   // of the linked list.

   public LinkedListIterator()
   {
      position = null;
      previous = null;
   }

  // Tests if there is an element after the iterator position.
  public boolean hasNext()
   {
      if (position == null) //not traversed yet
       {
          if (first != null)
             return true;
          else
             return false;
       }
      else
        {
           if (position.next != null)
              return true;
           else
              return false;
        }
   }

   // Moves the iterator past the next element, and returns
   // the traversed element's data.
   public Object next()
   {
      if (!hasNext())
       {
        NoSuchElementException ex = new NoSuchElementException();
        throw ex;
       }
      else
       {
         previous = position; // Remember for remove

         if (position == null)
            position = first;
         else
            position = position.next;

         return position.data;
       }
   }

   // Adds an element before the iterator position
   // and moves the iterator past the inserted element.
   public void add(Object element)
   {
      if (position == null) //never traversed yet
      {
         addFirst(element);
         position = first;
      }
      else
      {
         //making a new node to add
         Node newNode = new Node();
         newNode.data = element;
         newNode.next = position.next;
         //change the link to insert the new node
         position.next = newNode;
         //move the position forward to the new node
         position = newNode;
      }
      //this means that we cannot call remove() right after add()
      previous = position;
   }

   // Removes the last traversed element. This method may
   // only be called after a call to the next() method.
   public void remove()
   {
      if (previous == position)  //not after next() is called
       {
         IllegalStateException ex = new IllegalStateException();
         throw ex;
       }
      else
       {
        if (position == first)
           removeFirst();
        else
           previous.next = position.next; //removing
        //stepping back
        //this also means that remove() cannot be called twice in a row.
        position = previous;
       }
   }

   // Sets the last traversed element to a different value.
   public void set(Object element)
   {
      if (position == null)
       {
         NoSuchElementException ex = new NoSuchElementException();
         throw ex;
       }
      else
       position.data = element;
   }
} //end of LinkedListIterator class
} //end of LinkedList class
