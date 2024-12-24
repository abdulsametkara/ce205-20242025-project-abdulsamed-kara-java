/**
 * @file BPlusTreeNode.java
 * @brief Represents a node in a B+ Tree structure.
 *
 * This class defines the structure and behavior of a node in a B+ Tree.
 * It supports both leaf and internal nodes, storing keys, associated tasks,
 * and child node references for hierarchical organization.
 *
 * @author User
 * @date 2024-12-24
 * @version 1.0
 */

package com.abdul.fatma.hamza.sahan.task;

import java.util.*;

/**
 * @class BPlusTreeNode
 * @brief A node in a B+ Tree data structure.
 *
 * This class represents a node in a B+ Tree. Nodes can either be leaf nodes
 * or internal nodes. Leaf nodes store task references, while internal nodes
 * maintain pointers to child nodes.
 */
public class BPlusTreeNode {

    /**
     * @brief Indicates whether the node is a leaf.
     *
     * `true` if the node is a leaf, otherwise `false`.
     */
    boolean isLeaf;

    /**
     * @brief List of keys stored in the node.
     *
     * Each key acts as a separator or identifier for child nodes or tasks.
     */
    List<Integer> keys;

    /**
     * @brief List of tasks associated with the node.
     *
     * Leaf nodes store tasks corresponding to the keys.
     */
    List<ScheduledTask> tasks;

    /**
     * @brief List of child nodes.
     *
     * Internal nodes maintain references to their child nodes.
     */
    List<BPlusTreeNode> children;

    /**
     * @brief Pointer to the next leaf node.
     *
     * Provides linked list-like access between leaf nodes for range queries.
     */
    BPlusTreeNode next;

    /**
     * @brief Constructs a BPlusTreeNode.
     *
     * Initializes the node as either a leaf or an internal node.
     *
     * @param isLeaf Specifies whether the node is a leaf.
     */
    public BPlusTreeNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.children = new ArrayList<>();
        this.next = null;
    }
}
