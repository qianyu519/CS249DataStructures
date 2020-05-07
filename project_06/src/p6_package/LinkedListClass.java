package p6_package;

/**
 * Base linked list class that emulates an array with bounds checking and
 * management for storing boxes
 * 
 * @author Qian Yu
 */
public class LinkedListClass
   {
      /**
       * Reference to head of linked list
       */
      private BoxClass headRef;

      /**
       * Default constructor
       */
      public LinkedListClass ()
         {
            headRef = null;
         }

      /**
       * Initializing constructor, fills all elements with specified size value
       * up to given size; overwrites any data already in list
       * 
       * @param size      - sets the number of items to be filled in linked list
       * @param fillValue - value to be placed in all elements of initialized
       *                  linked list up to the size
       */
      public LinkedListClass ( int size, BoxClass fillValue )
         {
            int index = 1;
            BoxClass wkgRef;
            
            headRef = new BoxClass(fillValue);
            wkgRef = headRef;
            
            while(index < size)
               {
                  wkgRef.nextRef = new BoxClass(fillValue);
                  wkgRef = wkgRef.nextRef;
                  index ++;
               }
         }

      /**
       * Copy constructor, initializes linked list to the size of copied linked
       * list, then copies only the elements up to the given size
       * 
       * @param copied - LinkedListClass object to be copied
       */
      public LinkedListClass ( LinkedListClass copied )
         {
            BoxClass localWkgRef, copiedWkgRef;
            
            headRef = null;
            
            if (copied.headRef != null)
               {
                  headRef = new BoxClass(copied.headRef);
                  localWkgRef = headRef;
                  copiedWkgRef = copied.headRef.nextRef;
                  while(copiedWkgRef != null)
                     {
                        localWkgRef.nextRef = new BoxClass(copiedWkgRef);
                        localWkgRef = localWkgRef.nextRef;
                        copiedWkgRef = copiedWkgRef.nextRef;
                     }
               }
         }

      /**
       * Accesses item in linked list at specified index if index within linked
       * list bounds
       * 
       * @param accessIndex - index of requested element value
       * @return accessed value if successful, null if not
       */
      public BoxClass accessItemAt ( int accessIndex )
         {
            int index;
            int size = getCurrentSize ();
            BoxClass workingRef = headRef;
            
            if ( accessIndex >= 0 && accessIndex < size )
               {
                  for (index = 0; index < accessIndex; index++)
                     {
                        workingRef = workingRef.nextRef;
                     }
                  return workingRef;
               }
            return null;
         }

      /**
       * Appends item to end of linked list, if linked list is not full, e.g.,
       * no more values can be added
       * 
       * @param newValue
       */
      public void appendItem ( BoxClass newValue )
         {
            if ( headRef == null )
               {
                  headRef = newValue;
               }

            else
               {
                  BoxClass lastRef = findLastItemRef ();
                  lastRef.nextRef = newValue;
               }
         }

      /**
       * Clears linked list of all valid values by setting linked list size to
       * zero, values remain in linked list but are not accessible
       */
      public void clear ()
         {
            headRef = null;
         }

      /**
       * Simple linked list dump for testing purposes
       */
      public void dump ()
         {
            BoxClass currentRef = headRef;
            int index = 0;
            
            System.out.println ( "LinkedListClass Data Dump:" );
            while (currentRef != null)
               {
                  System.out
                        .println ( index + ". " + currentRef.toString () );
                  currentRef = currentRef.nextRef;
                  index++;
               }
         }

      /**
       * Finds reference to last node in linked list
       * 
       * @return BoxClass reference to last item
       */
      private BoxClass findLastItemRef ()
         {
            BoxClass wkgRef = headRef;

            while ( wkgRef.nextRef != null )
               {
                  wkgRef = wkgRef.nextRef;
               }
            return wkgRef;
         }

      /**
       * Description: Gets current size of linked list
       * <p>
       * Note: size of linked list indicates number of valid or viable values in
       * the linked list
       * 
       * @return size of linked list
       */
      public int getCurrentSize ()
         {
            return getCurrentSizeHelper ( headRef );
         }

      /**
       * Recursive helper method finds length of linked list
       * 
       * @param workingRef - BoxClass reference used for recursion
       * @return integer value with size of linked list at a given point in the
       *         recursion
       */
      public int getCurrentSizeHelper ( BoxClass workingRef )
         {
            if ( workingRef == null )
               {
                  return 0;
               }
            return getCurrentSizeHelper ( workingRef.nextRef ) + 1;
         }

      /**
       * Description: Inserts item into linked list at specified index
       * <p>
       * Note: Value is inserted at given index which is inserted into the
       * linked list at that point
       * <p>
       * Note: Value can be inserted after the last valid element but not at any
       * index past that point
       * 
       * @param insertIndex - index of element into which value is to be
       *                    inserted
       * @param newValue    - value to be inserted into linked list
       * @return boolean success if inserted, or failure if linked list was full
       */
      public boolean insertItemAt ( int insertIndex, BoxClass newValue )
         {
            int currentSize;
            BoxClass wkgRef;
            
            currentSize = getCurrentSize ();

            if ( insertIndex == 0 )
               {
                  newValue.nextRef = headRef;
                  headRef = newValue;
                  return true;
               }
            
            else if ( insertIndex > 0 && insertIndex <= currentSize )
               {
                  wkgRef = accessItemAt ( insertIndex - 1 );
                  newValue.nextRef = wkgRef.nextRef;
                  wkgRef.nextRef = newValue;

                  return true;
               }
            return false;
         }

      /**
       * Tests for size of linked list equal to zero, no valid values stored in
       * linked list
       * 
       * @return boolean result of test for empty
       */
      public boolean isEmpty ()
         {
            return headRef == null;
         }

      /**
       * Tests for value found in object linked list; returns true if value
       * within linked list, false otherwise
       * 
       * @param testVal - value to be tested
       * @return boolean true if value is found in linked list, false otherwise
       */
      public boolean isInlinkedList ( BoxClass testVal )
         {
            BoxClass wkgRef;
            
            for (wkgRef = headRef; wkgRef.nextRef != null;
                  wkgRef = wkgRef.nextRef)
               {
                  if ( wkgRef.compareTo ( testVal ) == 0 )
                     {
                        return true;
                     }
               }
            return false;
         }

      /**
       * Description: Removes item from linked list at specified index if index
       * within linked list size bounds
       * 
       * @param removeIndex - index of element value to be removed
       * @return removed value if successful, null if not
       */
      public BoxClass removeItemAt ( int removeIndex )
         {
            int currentSize;
            BoxClass previousRef;
            BoxClass wkgRef;
            
            currentSize = getCurrentSize ();

            if ( removeIndex > 0 && removeIndex < currentSize )
               {
                  previousRef = accessItemAt ( removeIndex - 1 );
                  wkgRef = previousRef.nextRef;

                  previousRef.nextRef = wkgRef.nextRef;
                  return wkgRef;
               }

            else if ( removeIndex == 0 && currentSize > 0 )
               {
                  wkgRef = headRef;
                  headRef = headRef.nextRef;
                  return wkgRef;
               }
            return null;
         }
   }
