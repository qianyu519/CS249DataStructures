package p9_package;

/**
 * Array-based generic min heap class used as a priority queue for generic data
 * 
 * @author Qian Yu
 */
public class OpCodeHeapClass
   {
      /**
       * Management data for array
       */
      private int arrayCapacity;

      /**
       * Management data for array
       */
      private int arraySize;

      /**
       * Initial array capacity
       */
      public final int DEFAULT_ARRAY_CAPACITY = 10;

      /**
       * Display flag can be set to observe bubble up and trickle down
       * operations
       */
      private boolean displayFlag;

      /**
       * Array for heap
       */
      private OpCodeClass[] heapArray;

      /**
       * Default constructor sets up array management conditions and default
       * display flag setting
       */
      public OpCodeHeapClass()
         {
            arraySize = 0;
            arrayCapacity = DEFAULT_ARRAY_CAPACITY;
            displayFlag = false;
            heapArray = new OpCodeClass[DEFAULT_ARRAY_CAPACITY];
         }

      /**
       * Copy constructor copies array and array management conditions and
       * default display flag setting
       * 
       * @param copied - GenericHeapClass object to be copied
       */
      public OpCodeHeapClass(OpCodeHeapClass copied)
         {
            arraySize = copied.arraySize;
            arrayCapacity = copied.arrayCapacity;
            displayFlag = false;
            heapArray = new OpCodeClass[copied.arrayCapacity];

            int index;
            OpCodeClass temp;
            for (index = 0; index < arraySize; index++)
               {
                  temp = new OpCodeClass(copied.heapArray[index]);
                  heapArray[index] = temp;
               }

         }

      /**
       * Accepts OpCodeClass item and adds it to heap
       * <p>
       * Note: uses bubbleUpArrayHeap to resolve unbalanced heap after data
       * addition
       * <p>
       * Note: must check for resize before attempting to add an item
       * 
       * @param newItem - OpCodeClass item to be added
       */
      public void addItem(OpCodeClass newItem)
         {
            checkForResize();

            if (displayFlag)
               {
                  System.out
                        .println("\nAdding new Process: " + newItem.toString());
               }

            heapArray[arraySize] = newItem;

            bubbleUpArrayHeap(arraySize);

            arraySize += 1;

         }

      /**
       * Recursive operation to reset data in the correct order for the min heap
       * after new data addition
       * 
       * @param currentIndex - index of current item being assessed, and moved
       *                     up as needed
       */
      private void bubbleUpArrayHeap(int currentIndex)
         {
            OpCodeClass workingOpCode;
            int parentLocation;
            int thisPriorityValue;
            int parentPriorityValue;

            if (currentIndex != 0)
               {
                  parentLocation = (currentIndex - 1) / 2;
                  parentPriorityValue = heapArray[parentLocation]
                        .getPriorityValue();
                  thisPriorityValue = heapArray[currentIndex]
                        .getPriorityValue();

                  if (parentPriorityValue > thisPriorityValue)
                     {
                        if (displayFlag)
                           {
                              System.out.println("   - Bubble Up:");
                              System.out.println("     - Swapping parent: "
                                    + heapArray[parentLocation].toString()
                                    + " with child: "
                                    + heapArray[currentIndex].toString());
                           }
                        
                        workingOpCode = heapArray[currentIndex];
                        heapArray[currentIndex] = heapArray[parentLocation];
                        heapArray[parentLocation] = workingOpCode;
                        bubbleUpArrayHeap(parentLocation);
                     }
               }
         }

      /**
       * Automatic resize operation used prior to any new data addition in the
       * heap
       * <p>
       * Tests for full heap array, and resizes to twice the current capacity as
       * required
       */
      private void checkForResize()
         {
            int index;
            OpCodeClass[] temporaryArray;
            OpCodeClass workingOpCode;

            if (arrayCapacity == arraySize)
               {
                  arrayCapacity = arrayCapacity * 2;
                  temporaryArray = new OpCodeClass[arrayCapacity];

                  index = 0;
                  while (index < arraySize)
                     {
                        workingOpCode = new OpCodeClass(heapArray[index]);
                        temporaryArray[index] = workingOpCode;

                        index++;
                     }
                  heapArray = temporaryArray;
               }
         }

      /**
       * Tests for empty heap
       * 
       * @return boolean result of test
       */
      public boolean isEmpty()
         {
            return arraySize == 0;
         }

      /**
       * Removes GenericData item from top of min heap, thus being the operation
       * with the lowest priority value
       * <p>
       * Note: Uses trickleDownArrayHeap to resolve unbalanced heap after data
       * removal
       * 
       * @return OpCodeClass item removed
       */
      public OpCodeClass removeItem()
         {
            OpCodeClass itemRemoved;

            if (isEmpty())
               {
                  return null;
               }

            arraySize -= 1;
            itemRemoved = heapArray[0];
            heapArray[0] = heapArray[arraySize];

            if (displayFlag)
               {
                  System.out.println("\nRemoving process: " + itemRemoved);
               }

            trickleDownArrayHeap(0);
            return itemRemoved;
         }

      /**
       * Utility method to set the display flag for displaying internal
       * operations of the heap bubble and trickle operations
       * 
       * @param setState - flag to be used to set the state to display, or not
       */
      public void setDisplayFlag(boolean setState)
         {
            displayFlag = setState;
         }

      /**
       * Dumps Array to screen as is, no filtering or management
       */
      public void showArray()
         {
            int index;
            index = 0;
            while (index < arraySize)
               {
                  System.out.print(heapArray[index].toString());
                  if (index + 1 != arraySize)
                     {
                        System.out.print(", ");
                     }
                  index += 1;
               }
            System.out.print("\n\n");
         }

      /**
       * Recursive operation to reset data in the correct order for the min heap
       * after data removal
       * 
       * @param currentIndex - index of current item being assessed, and moved
       *                     down as required
       */
      private void trickleDownArrayHeap(int currentIndex)
         {
            OpCodeClass workingOpCode;
            int leftPriorityValue;
            int rightPriorityValue;
            int thisPriorityValue;
            int leftIndex;
            int rightIndex;

            thisPriorityValue = heapArray[currentIndex].getPriorityValue();
            rightIndex = (currentIndex * 2) + 2;
            leftIndex = (currentIndex * 2) + 1;

            if (arraySize > rightIndex)
               {
                  rightPriorityValue = heapArray[rightIndex].getPriorityValue();
                  leftPriorityValue = heapArray[leftIndex].getPriorityValue();

                  if (rightPriorityValue > leftPriorityValue
                        && thisPriorityValue > leftPriorityValue)
                     {

                        if (displayFlag)
                           {
                              System.out.println("   - Trickle down:");
                              System.out.println("     - Swapping parent: "
                                    + heapArray[currentIndex].toString()
                                    + " with left child: "
                                    + heapArray[leftIndex].toString());
                           }

                        workingOpCode = heapArray[currentIndex];
                        heapArray[currentIndex] = heapArray[leftIndex];
                        heapArray[leftIndex] = workingOpCode;
                        trickleDownArrayHeap(leftIndex);
                     }

                  else if (thisPriorityValue > rightPriorityValue)
                     {
                        if (displayFlag)
                           {
                              System.out.println("   - Trickle down:");
                              System.out.println("     - Swapping parent: "
                                    + heapArray[currentIndex].toString()
                                    + " with right child: "
                                    + heapArray[rightIndex].toString());
                           }
                        workingOpCode = heapArray[currentIndex];
                        heapArray[currentIndex] = heapArray[rightIndex];
                        heapArray[rightIndex] = workingOpCode;
                        trickleDownArrayHeap(rightIndex);
                     }
               }

            else if (arraySize > leftIndex)
               {
                  leftPriorityValue = heapArray[leftIndex].getPriorityValue();
                  if (thisPriorityValue > leftPriorityValue)
                     {
                        if (displayFlag)
                           {
                              System.out.println("   - Trickle down:");
                              System.out.println("     - Swapping parent: "
                                    + heapArray[currentIndex].toString()
                                    + " with left child "
                                    + heapArray[leftIndex].toString());
                           }
                        workingOpCode = heapArray[currentIndex];
                        heapArray[currentIndex] = heapArray[leftIndex];
                        heapArray[leftIndex] = workingOpCode;
                        trickleDownArrayHeap(leftIndex);
                     }
               }
         }
   }
