package p10_package;

/**
 * Simple generic hash class
 * 
 * @author Qian Yu
 *
 */
public class ProbingHashClass
   {
      /**
       * Table size default
       */
      private final int DEFAULT_TABLE_SIZE = 11;

      /**
       * Constant for returning item not found with search
       */
      public final int ITEM_NOT_FOUND = -1;

      /**
       * Constant for setting linear probing
       */
      public static final int LINEAR_PROBING = 101;

      /**
       * Flag for setting linear or quadratic probing
       */
      private int probeFlag;

      /**
       * Constant for setting quadratic probing
       */
      public static final int QUADRATIC_PROBING = 102;

      /**
       * Array for hash table
       */
      private StudentClass[] tableArray;

      /**
       * Size of the array table
       */
      private int tableSize;

      /**
       * Default Constructor
       * <p>
       * Initializes to default table size with probe flag set to linear probing
       */
      public ProbingHashClass()
         {
            tableSize = DEFAULT_TABLE_SIZE;
            probeFlag = LINEAR_PROBING;
            tableArray = new StudentClass[tableSize];
         }

      /**
       * Initialization constructor
       * <p>
       * Initializes to default table size with probe flag set to probe flag
       * parameter
       * 
       * @param inProbeFlag - sets linear or quadratic probing
       */
      public ProbingHashClass(int inProbeFlag)
         {
            tableSize = DEFAULT_TABLE_SIZE;
            probeFlag = inProbeFlag;
            tableArray = new StudentClass[tableSize];
         }

      /**
       * Initialization constructor
       * 
       * @param inTableSize - sets table size (capacity) but does not allow
       *                    table size to be less than default capacity
       * @param inProbeFlag - sets linear or quadratic probing
       */
      public ProbingHashClass(int inTableSize, int inProbeFlag)
         {
            tableSize = inTableSize;
            probeFlag = inProbeFlag;
            tableArray = new StudentClass[tableSize];
         }

      /**
       * Adds GenericData item to hash table
       * <p>
       * Note: Uses hash index value from generateHash
       * <p>
       * Note: Shows probed index with data at the point of insertion
       * <p>
       * Note: Probe attempts are limited to the current size (capacity) of the
       * table
       * 
       * @param newItem - GenericData item
       * 
       * @return Boolean success of operation
       */
      public boolean addItem(StudentClass newItem)
         {
            int arrayIndex, startingIndex = generateHash(newItem),
                  wkgIndex = startingIndex;
            String displayOutput = "Indices probed: ";

            for (arrayIndex = 1; tableArray[wkgIndex] != null
                  && arrayIndex <= tableSize; arrayIndex += 1)
               {
                  displayOutput += wkgIndex + ", ";

                  if (probeFlag == QUADRATIC_PROBING)
                     {
                        wkgIndex = (startingIndex + toPower(arrayIndex, 2))
                              % tableSize;
                     }

                  else if (probeFlag == LINEAR_PROBING)
                     {
                        wkgIndex = (startingIndex + arrayIndex) % tableSize;
                     }
               }

            if (tableSize > arrayIndex)
               {
                  tableArray[wkgIndex] = newItem;

                  displayOutput += wkgIndex + "\n" + newItem + ", "
                        + startingIndex + " -> " + wkgIndex;
                  System.out.println(displayOutput);
                  return true;
               }
            return false;
         }

      /**
       * Clears hash table by setting all bins to null
       */
      public void clearHashTable()
         {
            int arrayIndex = 0;

            while (tableSize > arrayIndex)
               {
                  tableArray[arrayIndex] = null;
                  arrayIndex += 1;
               }
         }

      /**
       * Returns item found
       * 
       * @param searchItem - GenericData value to be found; uses findItemIndex
       * 
       * @return GenericData item found
       */
      public StudentClass findItem(StudentClass searchItem)
         {
            int searchItemIndex = findItemIndex(searchItem);
            if (searchItemIndex != ITEM_NOT_FOUND)
               {
                  return tableArray[searchItemIndex];
               }
            return null;

         }

      /**
       * Searches for item index in hash table
       * <p>
       * Note: Uses linear or quadratic probing as configured
       * <p>
       * Note: probing attempts limited to table size (capacity)
       * 
       * @param searchItem - GenericData value to be found
       * 
       * @return integer index location of search item
       */
      private int findItemIndex(StudentClass searchItem)
         {
            boolean continueRun = false;
            int arrayIndex, startingIndex = generateHash(searchItem),
                  searchIndex = startingIndex;
            String indicesOutput = "Indices probed: ";

            for (arrayIndex = 0; !continueRun
                  && arrayIndex < tableSize; arrayIndex++)
               {
                  if (probeFlag == QUADRATIC_PROBING)
                     {
                        searchIndex = (startingIndex + toPower(arrayIndex, 2))
                              % tableSize;
                     }

                  else if (probeFlag == LINEAR_PROBING)
                     {
                        searchIndex = (startingIndex + arrayIndex) % tableSize;
                     }

                  if (tableArray[searchIndex] != null
                        && searchItem.compareTo(tableArray[searchIndex]) == 0)
                     {
                        continueRun = true;
                     }

                  if (!continueRun)
                     {
                        indicesOutput = indicesOutput + searchIndex + ", ";
                     }
               }
            indicesOutput += searchIndex;
            System.out.println(indicesOutput);

            if (arrayIndex > tableSize)
               {
                  return ITEM_NOT_FOUND;
               }
            return searchIndex;
         }

      /**
       * Method converts GenericData hash value to index for use in hash table
       * 
       * @param item - GenericData value to be converted to hash value
       *             <p>
       *             Note: gets data from object via hashCode, then calculates
       *             index
       *             <p>
       *             Note: Uses hashCode from object
       * 
       * @return integer hash value
       */
      public int generateHash(StudentClass item)
         {
            int hash = item.hashCode();
            return hash % tableSize;
         }

      /**
       * Removes item from hash table
       * 
       * @param toBeRemoved - GenericData value used for requesting data uses
       *                    findItemIndex
       * 
       * @return GenericData item removed, or null if not found
       */
      public StudentClass removeItem(StudentClass toBeRemoved)
         {
            StudentClass removedData;
            int dataIndex = findItemIndex(toBeRemoved);
            if (dataIndex != ITEM_NOT_FOUND)
               {
                  removedData = tableArray[dataIndex];
                  tableArray[dataIndex] = null;
                  return removedData;
               }
            return null;
         }

      /**
       * traverses through all array bins, finds min and max number of
       * contiguous elements, and number of empty nodes; also shows table
       * loading
       * <p>
       * NOTE: Generates string of used and unused bins in addition to
       * displaying results on screen
       * 
       * @return String result of hash table analysis
       */
      public String showHashTableStatus()
         {
            int arrayIndex, numEmptyBins = 0, minContBins = tableSize,
                  maxContBins = 0, wkgContBins = 0;
            String arrayOutput = "", binsOutput = "\nHash Table Status: ";

            for (arrayIndex = 0; arrayIndex < tableSize; arrayIndex++)
               {
                  arrayOutput += "\n";
                  if (tableArray[arrayIndex] != null)
                     {
                        wkgContBins += 1;
                        binsOutput += "D";
                        arrayOutput += tableArray[arrayIndex];

                        if (maxContBins < wkgContBins)
                           {
                              maxContBins = wkgContBins;
                           }
                     }

                  else
                     {
                        arrayOutput += "null";
                        binsOutput += "N";
                        numEmptyBins += 1;
                        if (wkgContBins < minContBins && wkgContBins != 0)
                           {
                              minContBins = wkgContBins;
                           }
                        wkgContBins = 0;
                     }

               }
            System.out.println(binsOutput);
            System.out.println();
            System.out.println("     Minimum contiguous bins: " + minContBins);
            System.out.println("     Maximum contiguous bins: " + maxContBins);
            System.out.println("        Number of empty bins: " + numEmptyBins);
            System.out.println("\nArray Dump:" + arrayOutput);

            return binsOutput;
         }

      /**
       * Local recursive method to calculate exponentiation with integers
       * 
       * @param base     - base of exponentiation
       * @param exponent - exponent of exponentiation
       * 
       * @return result of exponentiation calculation
       */
      private int toPower(int base, int exponent)
         {
            if (exponent == 0)
               {
                  return 1;
               }
            return base * toPower(base, exponent - 1);
         }
   }
