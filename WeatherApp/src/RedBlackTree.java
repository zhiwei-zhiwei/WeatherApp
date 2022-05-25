// Name: Zhiwei Cao
// Email: zcao72@wisc.edu
// Team: JB
// TA: Harper
// Lecturer: lecture 002
//package BinaryTree;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Binary Search Tree implementation with a Node inner class for representing
 * the nodes within a binary search tree.  You can use this class' insert
 * method to build a binary search tree, and its toString method to display
 * the level order (breadth first) traversal of values in that tree.
 */
public class RedBlackTree<T extends Comparable<T>> {

//    private static final boolean RED = true;
//    private static final boolean BLACK = false;

    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always be maintained.
     */
    protected static class Node<T> {
        public T data;
        public boolean isBlack = false;
        public Node<T> parent; // null for root node
        public Node<T> leftChild;
        public Node<T> rightChild;


        public Node(T data) {
            this.data = data;
        }


        /**
         * @return true when this node has a parent and is the left child of
         * that parent, otherwise return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }

        /**
         * This method performs a level order traversal of the tree rooted
         * at the current node.  The string representations of each data value
         * within this tree are assembled into a comma separated string within
         * brackets (similar to many implementations of java.util.Collection).
         *
         * @return string containing the values of this tree in level order
         */
        @Override
        public String toString() { // display subtree in order traversal
            String output = "[";
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this);
            while (!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if (next.leftChild != null) q.add(next.leftChild);
                if (next.rightChild != null) q.add(next.rightChild);
                output += next.data.toString();
                if (!q.isEmpty()) output += ", ";
            }
            return output + "]";
        }
    }

    protected Node<T> root; // reference to root node of tree, null when empty


    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree.  After
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     *
     * @param data to be added into this binary search tree
     * @throws NullPointerException     when the provided data argument is null
     * @throws IllegalArgumentException when the tree already contains data
     */
    public void insert(T data) throws NullPointerException,
            IllegalArgumentException {
        // null references cannot be stored within this tree
        if (data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if (root == null) {
            root = newNode;
        } // add first node to an empty tree
        else {
            insertHelper(newNode, root);
        }// recursively insert into subtree

        this.root.isBlack = true;
    }

    private void enforceRBTreePropertiesAfterInsert(Node<T> node) {

//            Node<T> child; // initialize the position of node
        Node<T> parent;
        Node<T> brother; // the sibling of child node
//        when every child node is red
//            while (!child.isBlack) {
//                parent = child.parent;
        // Case A: child is parent's left child node
        try {
            if (node.parent.isLeftChild() && !node.parent.isBlack) {
                parent = node.parent.parent;
                // as a result, the sibling node is right child of parent
                brother = parent.rightChild;
//                    System.out.println("----" + node.data);
//                System.out.println("++++"+child.leftChild.data);
                // node is child's left node
                if (node.isLeftChild()) {
                    node.isBlack = false;
                    // when brother, child and node are all red --- case 1
                    if (brother != null && !brother.isBlack) {
                        parent.isBlack = false;
                        node.parent.isBlack = true;
                        brother.isBlack = true;
                        node.isBlack = false;
                        enforceRBTreePropertiesAfterInsert(parent);
                    }
                    // only brother is black --- case 2
                    else if (brother == null || brother.isBlack) {
                        node.parent.isBlack = true;
                        parent.isBlack = false;
                        rotate(node.parent, parent);
                        enforceRBTreePropertiesAfterInsert(parent);
                    }
                }
                // node is child's right node
                else {
                    node.isBlack = false;
                    // all node are red --- Case 1
                    if (brother != null && !brother.isBlack) {
                        parent.isBlack = false;
                        node.parent.isBlack = true;
                        brother.isBlack = true;
                        node.isBlack = false;
                        if (!parent.parent.isBlack) {
                            enforceRBTreePropertiesAfterInsert(parent);
                        }
//                            continue;
                    }
                    // brther node is black --- Case 3
                    else if (brother == null || brother.isBlack) {
                        node.isBlack = true;
                        parent.isBlack = false;
                        rotate(node, node.parent);
                        rotate(node, parent);
                        enforceRBTreePropertiesAfterInsert(parent);
                    }
                }
//                        enforceRBTreePropertiesAfterInsert(parent);
            }
            // Case B, child is parent's right child node
            else if (!node.parent.isLeftChild() && !node.parent.isBlack) {
                parent = node.parent.parent;
                brother = parent.leftChild;
                if (node.isLeftChild() == false) {
                    node.isBlack = false;
                    // case1
                    if (brother != null && !brother.isBlack) {
                        parent.isBlack = false;
                        node.parent.isBlack = true;
                        brother.isBlack = true;
                        node.isBlack = false;
                        enforceRBTreePropertiesAfterInsert(parent);
                    }
                    // case 2
                    else {
                        node.parent.isBlack = true;
                        parent.isBlack = false;
                        rotate(node.parent, parent);
                        enforceRBTreePropertiesAfterInsert(parent);
                    }
                } else if (node.isLeftChild()) {
                    node.isBlack = false;
                    // case 1
                    if (brother != null && !brother.isBlack) {
                        parent.isBlack = false;
                        node.parent.isBlack = true;
                        brother.isBlack = true;
                        node.isBlack = false;
                        enforceRBTreePropertiesAfterInsert(node.parent.parent);
                    }
                    // case 3
                    else {
                        node.isBlack = true;
                        parent.isBlack = false;
                        rotate(node, node.parent);
                        rotate(node, parent);
                        enforceRBTreePropertiesAfterInsert(parent);
                    }
                }
//                    enforceRBTreePropertiesAfterInsert(parent);
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) throws IOException {
        RedBlackTree<Integer> redBlackTree = new RedBlackTree<Integer>();
        redBlackTree.insert(50);
        redBlackTree.insert(20);
        redBlackTree.insert(10);
        redBlackTree.insert(12);
        redBlackTree.insert(19);
        redBlackTree.insert(40);
        redBlackTree.insert(30);
        redBlackTree.insert(13);
        redBlackTree.insert(14);
        redBlackTree.insert(5);
        redBlackTree.insert(8);
//
////        redBlackTree.insert(30);
////        redBlackTree.insert(16);
////        redBlackTree.insert(40);
////        redBlackTree.insert(45);
////        redBlackTree.insert(43);
////        redBlackTree.insert(31);
////        redBlackTree.insert(32);
//
//        redBlackTree.insert(45);
//        redBlackTree.insert(72);
//        redBlackTree.insert(26);
//        redBlackTree.insert(68);
//        redBlackTree.insert(18);
//        redBlackTree.insert(31);
//        redBlackTree.insert(28);
//        redBlackTree.insert(91);
//        redBlackTree.insert(13);
//        redBlackTree.insert(88);
//        redBlackTree.insert(19);
//        redBlackTree.insert(20);
//        redBlackTree.insert(21);
//        redBlackTree.insert(22);
//        redBlackTree.insert(32);
//        redBlackTree.insert(33);
//        redBlackTree.insert(34);
//        redBlackTree.insert(35);
        System.out.println(redBlackTree.toString());
    }

    /**
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree
     * by the newNode in that position.
     *
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the
     *                newNode should be inserted as a descenedent beneath
     * @throws IllegalArgumentException when the newNode and subtree contain
     *                                  equal data references (as defined by Comparable.compareTo())
     */
    private void insertHelper(Node<T> newNode, Node<T> subtree) {
        int compare = newNode.data.compareTo(subtree.data);
        // do not allow duplicate values to be stored within this tree
        if (compare == 0) throw new IllegalArgumentException(
                "This RedBlackTree already contains that value.");

            // store newNode within left subtree of subtree
        else if (compare < 0) {
            if (subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                // otherwise continue recursive search for location to insert
            } else {
                insertHelper(newNode, subtree.leftChild);
//                enforceRBTreePropertiesAfterInsert(newNode);
            }

        }

        // store newNode within the right subtree of subtree
        else {
            if (subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                // otherwise continue recursive search for location to insert
            } else {
                insertHelper(newNode, subtree.rightChild);
//                enforceRBTreePropertiesAfterInsert(newNode);
            }
        }
    }

    /**
     * This method performs a level order traversal of the tree. The string
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     *
     * @return string containing the values of this tree in level order
     */
    @Override
    public String toString() {
        return root.toString();
    }

    /**
     * Performs the rotation operation on the provided nodes within this BST.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation (sometimes called a left-right
     * rotation).  When the provided child is a rightChild of the provided
     * parent, this method will perform a left rotation (sometimes called a
     * right-left rotation).  When the provided nodes are not related in one
     * of these ways, this method will throw an IllegalArgumentException.
     *
     * @param child  is the node being rotated from child to parent position
     *               (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *               (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *                                  node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent)
            throws IllegalArgumentException {
        // TODO: Implement this method.
        //when the provided child is a leftChild of the provided parent
        // (left-right rotation)
        if (parent.leftChild == child) {
            // let child's right children be parent's left child
            parent.leftChild = child.rightChild;
            if (child.rightChild != null) {
                // child's left child is parent
                child.rightChild.parent = parent;
            }
            // replace position of parent between child
            child.parent = parent.parent;
            // only if the parent node is the root of the tree
            if (parent.parent == null) {
                this.root = child;
            } else {
                if (parent.parent.rightChild == parent) {
                    parent.parent.rightChild = child;
                } else {
                    parent.parent.leftChild = child;
                }
            }
            child.rightChild = parent;
            parent.parent = child;
        }
        if (parent.rightChild == child) {
            // right -left rotation
            // let child's left child be parent's right child
            parent.rightChild = child.leftChild;
            if (child.leftChild != null) {
                // if child's left child is not null, move it to parent's child
                child.leftChild.parent = parent;
            }
            // replace position of parent between child
            child.parent = parent.parent;
            // only if the parent node is the root of the tree
            if (parent.parent == null) {
                this.root = child;
            } else {
                if (parent.parent.leftChild == parent) {
                    parent.parent.leftChild = child;
                } else {
                    parent.parent.rightChild = child;
                }
            }
            child.leftChild = parent;
            parent.parent = child;
        }
    }

    // For the next two test methods, review your notes from the Module 4: Red
    // Black Tree Insertion Activity.  Questions one and two in that activity
    // presented you with an initial BST and then asked you to trace the
    // changes that would be applied as a result of performing different
    // rotations on that tree. For each of the following tests, you'll first
    // create the initial BST that you performed each of these rotations on.
    // Then apply the rotations described in that activity: the right-rotation
    // in the Part1 test below, and the left-rotation in the Part2 test below.
    // Then ensure that these tests fail if and only if the level ordering of
    // tree values dose not match the order that you came up with in that
    // activity.

//    @Test
//    public void week04ActivityTestPart1() {
//        // TODO: Implement this method to recreate the example from:
//        // Module 04: Red Black Tree Insert Activity - Step 1 (right rotation).
//        RedBlackTree binarySearchTree = new RedBlackTree();
//        binarySearchTree.insert(42);
//        binarySearchTree.insert(23);
//        binarySearchTree.insert(66);
//        binarySearchTree.insert(16);
//        binarySearchTree.insert(32);
//        binarySearchTree.insert(57);
//        binarySearchTree.insert(82);
//        RedBlackTree binary = new RedBlackTree();
//        System.out.println(binarySearchTree);
//        binary.insert(42);
//        binary.insert(23);
//        binary.insert(66);
//        binary.insert(16);
//        binary.insert(32);
//        binary.insert(57);
//        binary.insert(82);
//        binary.rotate(binary.root.leftChild, binary.root);
//        System.out.println(binary);
//        if (binary.root.getData()==binarySearchTree.root.leftChild.getData() &&
//                binary.root.leftChild.getData() == binarySearchTree.root.leftChild.leftChild.getData() &&
//                binary.root.rightChild.getData() == binarySearchTree.root.getData() &&
//                binary.root.rightChild.leftChild.getData() == binarySearchTree.root.leftChild.rightChild.getData()) {
//            System.out.println("Step 1 (right rotation) SUCCESS!!!");
//        } else {
//            fail("This test has not been implemented.");
//        }
//    }
//
//    @Test
//    public void week04ActivityTestPart2() {
//        // TODO: Implement this method to recreate the example from:
//        // Module 04: Red Black Tree Insert Activity - Step 2 (left rotation).
//        RedBlackTree binarySearchTree = new RedBlackTree();
//        binarySearchTree.insert(42);
//        binarySearchTree.insert(23);
//        binarySearchTree.insert(66);
//        binarySearchTree.insert(16);
//        binarySearchTree.insert(32);
//        binarySearchTree.insert(57);
//        binarySearchTree.insert(82);
//        RedBlackTree binary = new RedBlackTree();
//        binary.insert(42);
//        binary.insert(23);
//        binary.insert(66);
//        binary.insert(16);
//        binary.insert(32);
//        binary.insert(57);
//        binary.insert(82);
//        binary.rotate(binary.root.leftChild.rightChild, binary.root.leftChild);
//        if (binary.root.leftChild.leftChild.getData() != binarySearchTree.root.leftChild.getData()
//        && binary.root.leftChild.getData() != binarySearchTree.root.leftChild.rightChild.getData()
//        && binary.root.leftChild.leftChild.leftChild.getData() != binarySearchTree.root.leftChild.leftChild.getData()){
//            fail("This test has not been implemented.");
//        } else {
//            System.out.println("Step 2 (left rotation) SUCCESS!!!");
//        }
//    }
}
