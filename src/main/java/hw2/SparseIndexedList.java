package hw2;

import exceptions.IndexException;
import exceptions.LengthException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An implementation of an IndexedList designed for cases where
 * only a few positions have distinct values from the initial value.
 *
 * @param <T> Element type.
 */
public class SparseIndexedList<T> implements IndexedList<T> {

  private final int length;
  private final Node<T> head;
  private final T defaultVal;

  /**
   * Constructs a new SparseIndexedList of length size
   * with default value of defaultValue.
   *
   * @param size Length of list, expected: size > 0.
   * @param defaultValue Default value to store in each slot.
   * @throws LengthException if size <= 0.
   */
  public SparseIndexedList(int size, T defaultValue) throws LengthException {

    if (size <= 0) {
      throw new LengthException();
    }

    length = size;
    head = new Node<>();
    head.index = -1;
    defaultVal = defaultValue;
  }


  /** Insert a node in the sparse linked list at the appropriate index position, resulting in a sorted list.
   *
   * @param index the position at which the node will be placed.
   * @param t the value stored at the specified position
   */
  public void insert(int index, T t) {

    Node<T> target = head;
    if (head.next == null) {
      //if there are no non-default values in the list, just append the list with the first node
      append(index, t);
      return;
    }
    while (target.next != null && index > target.next.index) {
      //find the largest index before the specified index
      target = target.next;
    }
    Node<T> node = new Node<>();//create a new node with the appropriate data, index, and pointer
    node.data = t;
    node.next = target.next;
    node.index = index;
    target.next = node;
  }

  /** Return the length of the list.
   *
   * @return length of the sparse linked list
   */
  @Override
  public int length() {
    return length;
  }

  /** Return the data stored at the given position in the
   *  list or return the default value if no node exists at specified position.
   *
   * @param index representing a position in this list.
   * @return an entry of the list of type T
   * @throws IndexException if the index > 0 or index > length - 1
   */
  @Override
  public T get(int index) throws IndexException {
    if (index < 0 || index > length - 1) {
      throw new IndexException();
    }
    Node<T> node = find(index);//determine if the node exists
    if (node == null) {
      //the node does not exist
      return defaultVal;
    } else { //the node exist, so the non-default value is returned
      return node.data;
    }
  }

  /** Determine if the node exists in the list.
   *
   * @param index the position of the node in the list.
   * @return the node at the specified position in the list.
   */
  private Node<T> find(int index) {
    Node<T> target = head;
    for (int i = 0; i <= index + 1; i++) {
      //iterate through each node until the node of the given index is found
      if  (target.index == index) {
        return target;
      }
      if  (target.index > index) { //if nodes of an index greater than the given index is found, the node does not exist
        return null;
      }
      if (target.next == null) { //if there are no more nodes, the node does not exist
        return null;
      }
      target = target.next;
    }
    return target;
  }

  /** Add a node to the end of the list.
   *
   * @param index the position where the node will be placed
   * @param t the data to be stored at that position.
   */
  private void append(int index, T t) {
    Node<T> tail = head;
    while (tail.next != null) {
      //iterate through each node find the end of the list
      tail = tail.next;
    }
    tail.next = new Node<>();//create a new node to be added with the specified data entry
    tail.next.data = t;
    tail.next.index = index;
  }

  /** Delete the node from the list.
   *
   * @param index the position of the node that will be deleted
   */
  private void delete(int index) {
    Node<T> beforeTarget = head;

    while (beforeTarget.next != null) { //iterate through the list to find the node before the node to be deleted
      if (beforeTarget.next.index == index) {
        break;
      }
      beforeTarget = beforeTarget.next;
    }
    if (beforeTarget.next != null && beforeTarget.next.next != null) {
      beforeTarget.next = beforeTarget.next.next;//point the node before the "deleted node" to the following node
    } else {
      beforeTarget.next = null;
    }

  }

  /** Add a node containing a non-default value to the list or remove node from list to restore value to default value.
   *
   * @param index representing a position in this list.
   * @param value to be written at the given index.
   *              Post: this.get(index) == value
   * @throws IndexException if index < 0 or index > length -1
   */

  @Override
  public void put(int index, T value) throws IndexException {
    if (index < 0 || index > length - 1) {
      throw new IndexException();
    }
    Node<T> node = find(index);//determine if the node exists
    if (node == null) {
      //if the node does not exist, insert a node with a non-default value
      if (value != defaultVal) {
        insert(index, value);//insert, even though there might not be nodes at certain indices
      }
    } else {
      //if T value is the default value, remove from list
      //otherwise, give this node a new value
      if (value == defaultVal) {
        delete(index);
      } else {
        node.data = value;
      }
    }
  }

  /** A node that contains a piece of data, the index position of that node, and a pointer to the next node.
   *
   * @param <T> the data stored at that node.
   */
  private static class Node<T> {
    T data;
    Node<T> next;
    int index;
  }


  @Override
  public Iterator<T> iterator() {
    return new SparseIndexedListIterator();
  }

  // An iterator to traverse the sparse linked list from front (head) to back.
  private class SparseIndexedListIterator implements Iterator<T> {
    private Node<T> current;
    private int counter;

    SparseIndexedListIterator() {
      current = head;
      counter = -1;//begin at "index" of head
    }

    @Override
    public boolean hasNext() {
      return counter <= length - 2;//ensure that the length of the list has not been surpassed
    }


    @Override
    public T next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      counter++;
      if (counter == 0 && current.next == null) {
        return defaultVal;
      } else if (counter == 0) {
        current = current.next;
      }
      if (counter == current.index) {
        T t = current.data;
        if (current.next != null) {
          current = current.next;
        }
        return t;
      } else {
        return defaultVal;
      }
    }
  }
}
