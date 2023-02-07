package hw2;

import exceptions.IndexException;
import exceptions.LengthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for any class implementing the IndexedList interface.
 */
public abstract class IndexedListTest {
  protected static final int LENGTH = 10;
  protected static final int INITIAL = 7;
  private IndexedList<Integer> indexedList;

  public abstract IndexedList<Integer> createArray();

  @BeforeEach
  public void setup() {
    indexedList = createArray();
  }

  @Test
  @DisplayName("get() returns the default value after IndexedList is instantiated.")
  void testGetAfterConstruction() {
    for (int index = 0; index < indexedList.length(); index++) {
      assertEquals(INITIAL, indexedList.get(index));
    }
  }

  @Test
  @DisplayName("get() throws exception if index is below the valid range.")
  void testGetWithIndexBelowRangeThrowsException() {
    try {
      indexedList.get(-10);
      fail("IndexException was not thrown for index < 0");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("put() throws exception if index is below the valid range.")
  void testPutWithIndexBelowRangeThrowsException() {
    try {
      indexedList.put(-10, 4);
      fail("IndexException was not thrown for index < 0");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("get() returns new value at specified position after new value is added to list.")
  void testGetWhenReturningNewVal() {
    int newVal = 8;
    int newIndex = 4;
    indexedList.put(newIndex, newVal);
    assertEquals(newVal, indexedList.get(newIndex));
  }

  @Test
  @DisplayName("put() adds a new value to the list of default values")
  void testPutWhenAddingNewValues() {
    int newVal = 6;
    int newIndex = 0;
    indexedList.put(newIndex, newVal);
    assertEquals(newVal, indexedList.get(newIndex));
  }

  @Test
  @DisplayName("put() removes a node after a new value is changed to default value.")
  void testPutWhenRemovingNode() {
    int newVal = 9;
    int newIndex = 2;
    indexedList.put(newIndex, newVal);
    indexedList.put(newIndex, INITIAL);
    assertEquals(INITIAL, indexedList.get(newIndex));
  }

  @Test
  @DisplayName("put() changes the value of a node to a value that is not equal to default value.")
  void testPutWhenChangingNodeValue() {
    int firstVal = 10;
    int secondVal = 12;
    int newIndex = 2;
    indexedList.put(newIndex, firstVal);
    indexedList.put(newIndex, secondVal);
    assertEquals(secondVal, indexedList.get(newIndex));
  }

  @Test
  @DisplayName("get() returns a series of new and default values")
  void testGetWhenOutputtingEntireList() {
    int val1 = 0;
    int val2 = 4;
    int val3 = 5;
    int val4 = 9;
    int index1 = 0;
    int index2 = 3;
    int index3 = 4;
    int index4 = 6;
    indexedList.put(index1, val1);
    indexedList.put(index2, val2);
    indexedList.put(index3, val3);
    indexedList.put(index4, val4);
    assertEquals(val1, indexedList.get(index1));
    assertEquals(INITIAL, indexedList.get(1));
    assertEquals(INITIAL, indexedList.get(2));
    assertEquals(val2, indexedList.get(index2));
    assertEquals(val3, indexedList.get(index3));
    assertEquals(INITIAL, indexedList.get(5));
    assertEquals(val4, indexedList.get(index4));
  }

  @Test
  @DisplayName("list is outputted in order even when nodes are entered out of order")
  void testOrderOfNodes() {
    int val1 = 0;
    int val2 = 4;
    int val3 = 5;
    int val4 = 9;
    int index1 = 1;
    int index2 = 0;
    int index3 = 3;
    int index4 = 2;
    indexedList.put(index1, val1);
    indexedList.put(index2, val2);
    indexedList.put(index3, val3);
    indexedList.put(index4, val4);
    int[] orderedVal = {4, 0, 9, 5};
    for(int i = 0; i < 4; i++) {
      assertEquals(orderedVal[i], indexedList.get(i));
    }
  }

  @Test
  @DisplayName("Iterable.iterator() method allows for a functional for-each loop.")
  void testIterator() {
    int counter = 0;
    indexedList.put(0, 0);
    indexedList.put(3, 6);
    indexedList.put(2, 5);
    indexedList.put(4, 5);
    for (int element: indexedList) {
      System.out.println(element);
      counter++;
    }
    assertEquals(LENGTH, counter);
  }

  @Test
  @DisplayName("hasNext() method is functional")
  void testHasNext() {
    Iterator itr = indexedList.iterator();
    assertTrue(itr.hasNext());
    for(int i = 0; i < LENGTH; i++) {
      itr.next();
    }
    assertFalse(itr.hasNext());
  }

  @Test
  @DisplayName("next() method is functional")
  void testNext() {
    Iterator itr = indexedList.iterator();
    for(int i = 0; i < LENGTH; i++) {
      assertEquals(INITIAL, itr.next());
    }
  }



  @Test
  @DisplayName("next() method throws IndexException when hasNext() is false")
  void testNextThrowIndexExcpetion() {
    Iterator itr = indexedList.iterator();
    for(int i = 0; i < LENGTH; i++) {
      itr.next();
    }
    try {
      itr.next();
      fail("next() did not throw NoSuchElementException");
    } catch (NoSuchElementException ex) {
      return;
    }
  }

  @Test
  @DisplayName("Iterable.iterator() method allows for a functional for-each loop printing default values.")
  void testIteratorDefault() {
    for (int element : indexedList) {
      assertEquals(INITIAL, element);
    }
  }

}
