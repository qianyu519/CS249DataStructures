package p7_package;

/**
 * Binary Search Tree (BST) class for managing generic data
 * <p>
 * Note: Data used must have implemented Comparable interface
 *
 * @author Qian Yu
 *
 */
public class Generic_BST_Class<GenericData extends Comparable<GenericData>>
   {
      /**
       * Binary Search Tree node class for managing generic data
       *
       * @author Qian Yu
       */
      private class BST_Node
         {
            /**
             * Member value GenericData node
             */
            private GenericData nodeData;

            /**
             * Member value left child reference
             */
            BST_Node leftChildRef;

            /**
             * Member value right child reference
             */
            BST_Node rightChildRef;

            /**
             * Initialization constructor for data
             *
             * @param inData - GenericData quantity
             */
            public BST_Node(GenericData inData)
               {
                  nodeData = inData;
                  leftChildRef = null;
                  rightChildRef = null;
               }

            /**
             * Copy constructor for data
             * <p>
             * Note: Not used in class but available to user
             *
             * @param copied - GenericData quantity
             */
            @SuppressWarnings("unused")
            public BST_Node(BST_Node copied)
               {
                  nodeData = copied.nodeData;
               }
         }

      /**
       * Root of BST
       */
      private BST_Node BST_Root;

      /**
       * Returned value reference for remove operation
       */
      private GenericData removed;

      /**
       * Default class constructor, initializes BST
       */
      public Generic_BST_Class()
         {
            removed = null;
            BST_Root = null;
         }

      /**
       * Clears tree
       */
      public void clearTree()
         {
            BST_Root = null;
         }

      /**
       * Provides inOrder traversal action
       * <p>
       * Note: Calls displayInOrderHelper
       */
      public void displayInOrder()
         {

            if (!isEmpty())
               {
                  System.out.println("Tree Display - Inorder");
                  displayInOrderHelper(BST_Root);
                  System.out.println("");
               }

            else
               {
                  System.out.println("No data in the tree\n");
               }

         }

      /**
       * Provides inOrder traversal action using recursion
       *
       * @param localRoot - BST_Node tree root reference at the current
       *                  recursion level
       */
      private void displayInOrderHelper(BST_Node localRoot)
         {
            if (localRoot != null)
               {
                  displayInOrderHelper(localRoot.leftChildRef);
                  System.out.println(localRoot.nodeData);
                  displayInOrderHelper(localRoot.rightChildRef);
               }
         }

      /**
       * Provides postOrder traversal action
       * <p>
       * Note: Calls displayPostOrderHelper
       */
      public void displayPostOrder()
         {
            if (!isEmpty())
               {
                  System.out.println("Tree Display - Postorder");
                  displayPostOrderHelper(BST_Root);
                  System.out.print("");
               }

            else
               {
                  System.out.println("No data in the tree\n");
               }
         }

      /**
       * Provides postOrder traversal action using recursion
       *
       * @param localRoot - BST_Node tree root reference at the current
       *                  recursion level
       */
      private void displayPostOrderHelper(BST_Node localRoot)
         {
            if (localRoot != null)
               {
                  displayPostOrderHelper(localRoot.leftChildRef);
                  displayPostOrderHelper(localRoot.rightChildRef);
                  System.out.println(localRoot.nodeData);
               }
         }

      /**
       * Provides preOrder traversal action
       * <p>
       * Note: Calls displayPreOrderHelper
       */
      public void displayPreOrder()
         {
            if (!isEmpty())
               {
                  System.out.println("Tree Display - Preorder");
                  displayPreOrderHelper(BST_Root);
                  System.out.print("");
               }

            else
               {
                  System.out.println("No data in the tree");
               }
         }

      /**
       * Provides preOrder traversal action using recursion
       *
       * @param localRoot - BST_Node tree root reference at the current
       *                  recursion level
       */
      private void displayPreOrderHelper(BST_Node localRoot)
         {
            if (localRoot != null)
               {
                  System.out.println(localRoot.nodeData);
                  displayPreOrderHelper(localRoot.leftChildRef);
                  displayPreOrderHelper(localRoot.rightChildRef);
               }
         }

      /**
       * Insert method for BST
       * <p>
       * Note: uses insert helper method which returns root reference
       *
       * @param inData - GenericData data to be added to BST
       */
      public void insert(GenericData inData)
         {
            BST_Root = insertHelper(BST_Root, inData);
         }

      /**
       * Insert helper method for BST insert action
       * <p>
       * Note: Recursive method returns updated local root to maintain tree
       * linkage
       * <p>
       * Note: Must not allow duplicate keys (names)
       *
       * @param localRoot - BST_Node tree root reference at the current
       *                  recursion level
       * @param inData    - GenericData item to be added to BST
       *
       * @return BST_Node reference used to maintain tree linkage
       */
      private BST_Node insertHelper(BST_Node localRoot, GenericData inData)
         {
            if (localRoot == null)
               {
                  return new BST_Node(inData);
               }

            if (inData.compareTo(localRoot.nodeData) > 0)
               {
                  localRoot.rightChildRef = insertHelper(
                        localRoot.rightChildRef, inData);
               }

            else if (inData.compareTo(localRoot.nodeData) < 0)
               {
                  localRoot.leftChildRef = insertHelper(localRoot.leftChildRef,
                        inData);
               }

            return localRoot;
         }

      /**
       * Test for empty tree
       *
       * @return Boolean result of test
       */
      public boolean isEmpty()
         {
            return BST_Root == null;
         }

      /**
       * Searches tree from given node to maximum value node below it, stores
       * data value found, and then unlinks the node
       *
       * @param maxParent - BST_Node reference to current node
       * @param maxLoc    - BST_Node reference to child node to be tested
       *
       * @return BST_Node reference containing removed node
       */
      private BST_Node removeFromMax(BST_Node maxParent, BST_Node maxLoc)
         {
            if (maxLoc == null)
               {
                  return maxParent;
               }

            if (maxLoc.rightChildRef != null)
               {
                  return removeFromMax(maxLoc, maxLoc.rightChildRef);
               }

            else if (maxLoc.leftChildRef != null)
               {
                  maxParent.rightChildRef = maxLoc.leftChildRef;
                  return maxLoc;
               }

            maxParent.rightChildRef = null;
            return maxLoc;
         }

      /**
       * Removes data node from tree using given key
       * <p>
       * Note: uses remove helper method
       * <p>
       * Note: uses search initially to get value, if it is in tree; if value
       * found, remove helper method is called, otherwise returns null
       *
       * @param inData - GenericData that includes the necessary key
       *
       * @return GenericData result of remove action
       */
      public GenericData removeItem(GenericData inData)
         {
            if (!isEmpty() && search(inData) != null)
               {
                  removeItemHelper(BST_Root, inData);
                  return removed;
               }
            return null;
         }

      /**
       * Remove helper for BST remove action
       * <p>
       * Note: Recursive method returns updated local root to maintain tree
       * linkage
       * <p>
       * Note: Sets member value removed to removed data for return by
       * removeItem call
       * <p>
       * Note: uses removeFromMax method
       *
       * @param localRoot - BST_Node tree root reference at the current
       *                  recursion level
       * @param outData   - GenericData item that includes the necessary key
       *
       * @return BST_Node reference result of remove helper action
       */
      private BST_Node removeItemHelper(BST_Node localRoot, GenericData outData)
         {
            if (localRoot != null)
               {
                  if (localRoot.nodeData.compareTo(outData) < 0)
                     {
                        localRoot.rightChildRef = removeItemHelper(
                              localRoot.rightChildRef, outData);
                     }

                  else if (localRoot.nodeData.compareTo(outData) > 0)
                     {
                        localRoot.leftChildRef = removeItemHelper(
                              localRoot.leftChildRef, outData);
                     }

                  else
                     {
                        removed = localRoot.nodeData;

                        if (localRoot.rightChildRef == null)
                           {
                              return localRoot.leftChildRef;
                           }

                        else if (localRoot.leftChildRef == null)
                           {
                              return localRoot.rightChildRef;
                           }

                        BST_Node temporaryNode = removeFromMax(
                              localRoot.rightChildRef,
                              localRoot.rightChildRef.leftChildRef);

                        localRoot.nodeData = temporaryNode.nodeData;

                        if (temporaryNode.nodeData
                              .compareTo(localRoot.rightChildRef.nodeData) == 0)
                           {
                              localRoot.rightChildRef = temporaryNode.rightChildRef;
                           }
                     }
               }
            return localRoot;
         }

      /**
       * Searches for data in BST given GenericData with necessary key
       *
       * @param searchData - GenericData item containing key s
       * @return GenericData reference to found data
       */
      public GenericData search(GenericData searchData)
         {
            return searchHelper(BST_Root, searchData);
         }

      /**
       * Helper method for BST search action
       *
       * @param localRoot  - BST_Node tree root reference at the current
       *                   recursion level
       * @param searchData - GenericData item containing key
       *
       * @return GenericData item found
       */
      private GenericData searchHelper(BST_Node localRoot,
            GenericData searchData)
         {
            if (localRoot != null)
               {
                  if (localRoot.nodeData.compareTo(searchData) == 0)
                     {
                        return (GenericData) localRoot.nodeData;
                     }

                  else if (localRoot.nodeData.compareTo(searchData) > 0)
                     {
                        return searchHelper(localRoot.leftChildRef, searchData);
                     }

                  else if (localRoot.nodeData.compareTo(searchData) < 0)
                     {
                        return searchHelper(localRoot.rightChildRef,
                              searchData);
                     }
               }
            return null;
         }

   }
