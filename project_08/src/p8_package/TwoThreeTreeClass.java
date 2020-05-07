package p8_package;

/**
 * 2-3 Tree class that stores integers
 *
 * @author Qian Yu
 */
public class TwoThreeTreeClass
   {
      /**
       * constant used for identifying one data item stored
       */
      private final int ONE_DATA_ITEM = 1;

      /**
       * Used for acquiring ordered tree visitations in String form
       */
      private String outputString;

      /**
       * root of tree
       */
      private TwoThreeNodeClass root;

      /**
       * constant used for identifying three data items stored
       */
      private final int THREE_DATA_ITEMS = 3;

      /**
       * constant used for identifying two data items stored
       */
      private final int TWO_DATA_ITEMS = 2;

      /**
       * Default 2-3 tree constructor
       */
      public TwoThreeTreeClass()
         {
            outputString = null;
            root = null;
         }

      /**
       * Copy 2-3 tree constructor
       *
       * @param copied - TwoThreeTreeClass object to be duplicated; data is
       *               copied, but new nodes and references must be created
       */
      public TwoThreeTreeClass(TwoThreeTreeClass copied)
         {
            if (copied.root == null)
               {
                  root = null;
                  outputString = null;
               }

            else
               {
                  root = copyConstructorHelper(copied.root);
                  outputString = null;
               }
         }

      /**
       * Implements tree duplication effort with recursive method; copies data
       * into newly created nodes and creates all new references to child nodes
       *
       * @param workingCopiedRef - TwoThreeNodeClass reference that is updated
       *                         to lower level children with each recursive
       *                         call
       *
       * @return TwoThreeNodeClass reference to next higher level node; last
       *         return is to root node of THIS object
       */
      private TwoThreeNodeClass copyConstructorHelper(
            TwoThreeNodeClass workingCopiedRef)
         {
            TwoThreeNodeClass current;

            if (workingCopiedRef != null)
               {
                  current = new TwoThreeNodeClass(workingCopiedRef.centerData);

                  current.leftChildRef = copyConstructorHelper(
                        workingCopiedRef.leftChildRef);
                  if (current.leftChildRef != null)
                     {
                        current.leftChildRef.parentRef = current;
                     }
                  if (workingCopiedRef.numItems == TWO_DATA_ITEMS)
                     {
                        current.centerChildRef = copyConstructorHelper(
                              workingCopiedRef.centerChildRef);
                        if (current.centerChildRef != null)
                           {
                              current.centerChildRef.parentRef = current;
                           }
                        current.numItems++;
                        current.rightData = workingCopiedRef.rightData;
                        current.leftData = workingCopiedRef.leftData;
                        current.centerData = 0;
                     }

                  current.rightChildRef = copyConstructorHelper(
                        workingCopiedRef.rightChildRef);

                  if (current.rightChildRef != null)
                     {
                        current.rightChildRef.parentRef = current;
                     }

                  return current;
               }
            return null;
         }

      /**
       * Method is called when addItemHelper arrives at the bottom of the 2-3
       * search tree (i.e., all node's children are null);
       * <p>
       * Assumes one- or two- value nodes and adds one more to the appropriate
       * one resulting in a two- or three- value node
       *
       * @param localRef - TwoThreeNodeClass reference has original node data
       *                 and contains added value when method completes; method
       *                 does not manage any node links
       * @param itemVal  - integer value to be added to 2-3 node
       */
      private void addAndOrganizeData(TwoThreeNodeClass localRef, int itemVal)
         {
            if (localRef.numItems == ONE_DATA_ITEM)
               {
                  localRef.numItems++;
                  if (itemVal > localRef.centerData)
                     {
                        localRef.rightData = itemVal;
                        localRef.leftData = localRef.centerData;
                     }

                  else
                     {
                        localRef.leftData = itemVal;
                        localRef.rightData = localRef.centerData;
                     }
                  localRef.centerData = 0;
               }

            else if (localRef.numItems == TWO_DATA_ITEMS)
               {
                  localRef.numItems++;
                  if (itemVal < localRef.leftData)
                     {
                        localRef.centerData = localRef.leftData;
                        localRef.leftData = itemVal;
                     }

                  else if (itemVal > localRef.rightData)
                     {
                        localRef.centerData = localRef.rightData;
                        localRef.rightData = itemVal;
                     }

                  else
                     {
                        localRef.centerData = itemVal;
                     }
               }
         }

      /**
       * Adds item to 2-3 tree using addItemHelper as needed
       *
       * @param itemVal - integer value to be added to the tree
       */
      public void addItem(int itemVal)
         {
            if (root != null)
               {
                  addItemHelper(root, itemVal);
               }

            else
               {
                  root = new TwoThreeNodeClass(itemVal);
               }
         }

      /**
       * Helper method searches from top of tree to bottom using divide and
       * conquer strategy to find correct location (node) for new added value;
       * once location is found, item is added to node using addAndOrganizeData
       * and then fixUpInsert is called in case the updated node has become a
       * three-value node
       *
       * @param localRef - TwoThreeNodeClass reference to the current item at
       *                 the same given point in the recursion process
       * @param itemVal  - integer value to be added to the tree
       */
      private void addItemHelper(TwoThreeNodeClass localRef, int itemVal)
         {
            boolean hasLeftChild = localRef.leftChildRef != null;
            boolean hasRightChild = localRef.rightChildRef != null;
            boolean hasCenterChild = localRef.centerChildRef != null;

            if (hasLeftChild || hasRightChild || hasCenterChild)
               {
                  if (localRef.numItems == TWO_DATA_ITEMS)
                     {
                        if (itemVal < localRef.leftData)
                           {
                              addItemHelper(localRef.leftChildRef, itemVal);
                           }

                        else if (itemVal > localRef.rightData)
                           {
                              addItemHelper(localRef.rightChildRef, itemVal);
                           }

                        else
                           {
                              addItemHelper(localRef.centerChildRef, itemVal);
                           }
                     }

                  else if (localRef.numItems == ONE_DATA_ITEM)
                     {
                        if (itemVal < localRef.centerData)
                           {
                              addItemHelper(localRef.leftChildRef, itemVal);
                           }

                        else if (itemVal > localRef.centerData)
                           {
                              addItemHelper(localRef.rightChildRef, itemVal);
                           }
                     }
               }

            else
               {
                  addAndOrganizeData(localRef, itemVal);
                  fixUpInsert(localRef);
               }
         }

      /**
       * Method clears tree so that new items can be added again
       */
      public void clear()
         {
            root = null;
         }

      /**
       * Method used to fix tree any time a three-value node has been added to
       * the bottom of the tree; it is always called but decides to act only if
       * it finds a three-value node
       * <p>
       * Resolves current three-value node which may add a value to the node
       * above; if the node above becomes a three-value node, then this is
       * resolved with the next recursive call
       * <p>
       * Recursively climbs from bottom to top of tree resolving any three-value
       * nodes found
       *
       * @param localRef - TwoThreeNodeClas reference initially given the
       *                 currently updated node, then is given parent node
       *                 recursively each time a three-value node was resolved
       */
      private void fixUpInsert(TwoThreeNodeClass localRef)
         {
            TwoThreeNodeClass localParent;
            if (localRef.numItems == THREE_DATA_ITEMS)
               {
                  localParent = localRef.parentRef;
                  if (localParent != null)
                     {
                        if (localParent.numItems == ONE_DATA_ITEM)
                           {
                              localParent.numItems = TWO_DATA_ITEMS;
                              if (localRef == localParent.rightChildRef)
                                 {
                                    localParent.rightData = localRef.centerData;
                                    localParent.leftData = localParent.centerData;
                                    localParent.rightChildRef = new TwoThreeNodeClass(
                                          TwoThreeNodeClass.RIGHT_CHILD_SELECT,
                                          localRef);
                                    localParent.centerChildRef = new TwoThreeNodeClass(
                                          TwoThreeNodeClass.LEFT_CHILD_SELECT,
                                          localRef);
                                 }

                              else if (localRef == localParent.leftChildRef)
                                 {
                                    localParent.leftData = localRef.centerData;
                                    localParent.rightData = localParent.centerData;
                                    localParent.centerChildRef = new TwoThreeNodeClass(
                                          TwoThreeNodeClass.RIGHT_CHILD_SELECT,
                                          localRef);
                                    localParent.leftChildRef = new TwoThreeNodeClass(
                                          TwoThreeNodeClass.LEFT_CHILD_SELECT,
                                          localRef);
                                 }
                           }

                        else if (localParent.numItems == TWO_DATA_ITEMS)
                           {
                              localParent.numItems = THREE_DATA_ITEMS;

                              if (localRef == localParent.centerChildRef)
                                 {
                                    localParent.centerData = localRef.centerData;
                                    localParent.auxRightRef = new TwoThreeNodeClass(
                                          TwoThreeNodeClass.RIGHT_CHILD_SELECT,
                                          localRef);
                                    localParent.auxLeftRef = new TwoThreeNodeClass(
                                          TwoThreeNodeClass.LEFT_CHILD_SELECT,
                                          localRef);
                                 }

                              else if (localRef == localParent.leftChildRef)
                                 {
                                    localParent.centerData = localParent.leftData;
                                    localParent.leftData = localRef.centerData;
                                    localParent.auxRightRef = localParent.centerChildRef;
                                    localParent.auxLeftRef = new TwoThreeNodeClass(
                                          TwoThreeNodeClass.RIGHT_CHILD_SELECT,
                                          localRef);
                                    localParent.leftChildRef = new TwoThreeNodeClass(
                                          TwoThreeNodeClass.LEFT_CHILD_SELECT,
                                          localRef);
                                 }

                              else if (localRef == localParent.rightChildRef)
                                 {
                                    localParent.centerData = localParent.rightData;
                                    localParent.rightData = localRef.centerData;
                                    localParent.auxLeftRef = localParent.centerChildRef;
                                    localParent.rightChildRef = new TwoThreeNodeClass(
                                          TwoThreeNodeClass.RIGHT_CHILD_SELECT,
                                          localRef);
                                    localParent.auxRightRef = new TwoThreeNodeClass(
                                          TwoThreeNodeClass.LEFT_CHILD_SELECT,
                                          localRef);
                                 }
                              fixUpInsert(localParent);
                           }
                     }

                  else
                     {
                        localRef.parentRef = new TwoThreeNodeClass(
                              localRef.centerData);
                        root = localRef.parentRef;
                        localRef.parentRef.rightChildRef = new TwoThreeNodeClass(
                              TwoThreeNodeClass.RIGHT_CHILD_SELECT, localRef);
                        localRef.parentRef.leftChildRef = new TwoThreeNodeClass(
                              TwoThreeNodeClass.LEFT_CHILD_SELECT, localRef);
                     }
               }
         }

      /**
       * Tests center value if single node, tests left and right values if
       * two-value node; returns true if specified data is found in any given
       * node
       * <p>
       * Note: Method does not use any branching operations such as if/else/etc.
       *
       * @param localRef  - TwoThreeNodeClass reference to node with one or two
       *                  data items in it
       * @param searchVal - integer value to be found in given node
       *
       * @return boolean result of test
       */
      private boolean foundInNode(TwoThreeNodeClass localRef, int searchVal)
         {
            boolean isOneNode = localRef.numItems == ONE_DATA_ITEM;
            boolean isInOneNode = searchVal == localRef.centerData && isOneNode;

            boolean isTwoNode = localRef.numItems == TWO_DATA_ITEMS;
            boolean isOnRight = searchVal == localRef.rightData && isTwoNode;
            boolean isOnLeft = searchVal == localRef.leftData && isTwoNode;
            boolean isInTwoNode = isOnLeft || isOnRight;

            return isInTwoNode || isInOneNode;
         }

      /**
       * Public method called by user to display data in order
       *
       * @return String result to be displayed and/or analyzed
       */
      public String inOrderTraversal()
         {
            outputString = "";

            inOrderTraversalHelper(root);

            return outputString;
         }

      /**
       * Helper method conducts in order traversal with 2-3 tree
       * <p>
       * Shows location of each value in a node: "C" at center of single node
       * "L" or "R" at left or right of two-value node
       *
       * @param localRef - TwoThreeNodeClass reference to current location at
       *                 any given point in the recursion process
       */
      private void inOrderTraversalHelper(TwoThreeNodeClass localRef)
         {
            if (localRef != null)
               {
                  inOrderTraversalHelper(localRef.leftChildRef);

                  if (localRef.numItems == ONE_DATA_ITEM)
                     {
                        outputString += "C" + localRef.centerData + " ";
                     }

                  else if (localRef.numItems == TWO_DATA_ITEMS)
                     {
                        outputString += "L" + localRef.leftData + " ";
                        inOrderTraversalHelper(localRef.centerChildRef);
                        outputString += "R" + localRef.rightData + " ";
                     }

                  inOrderTraversalHelper(localRef.rightChildRef);
               }
         }

      /**
       * Search method used by programmer to find specified item in 2-3 tree
       *
       * @param searchVal - integer value to be found
       *
       * @return boolean result of search effort
       */
      public boolean search(int searchVal)
         {
            return searchHelper(root, searchVal);
         }

      /**
       * Search helper method that traverses through tree in a recursive divide
       * and conquer search fashion to find given integer in 2-3 tree
       *
       * @param localRef  - TwoThreeNodeClass reference to given node at any
       *                  point during the recursive process
       * @param searchVal - integer value to be found in tree
       *
       * @return boolean result of search effort
       */
      private boolean searchHelper(TwoThreeNodeClass localRef, int searchVal)
         {
            if (localRef == null)
               {
                  return false;
               }

            if (foundInNode(localRef, searchVal))
               {
                  return true;
               }

            if (localRef.numItems == ONE_DATA_ITEM)
               {
                  if (searchVal < localRef.centerData)
                     {
                        return searchHelper(localRef.leftChildRef, searchVal);
                     }

                  else
                     {
                        return searchHelper(localRef.rightChildRef, searchVal);
                     }
               }

            else if (localRef.numItems == TWO_DATA_ITEMS)
               {
                  if (searchVal < localRef.leftData)
                     {
                        return searchHelper(localRef.leftChildRef, searchVal);
                     }

                  else if (searchVal > localRef.rightData)
                     {
                        return searchHelper(localRef.rightChildRef, searchVal);
                     }

                  else
                     {
                        return searchHelper(localRef.centerChildRef, searchVal);
                     }
               }

            return false;
         }
   }
