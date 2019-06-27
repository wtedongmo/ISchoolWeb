/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.tree.nestedsets;

import com.tsoft.tree.TreeNode;

public interface NestedSetsTreeNode extends TreeNode {
    int getLeft();

    void setLeft(int var1);

    int getRight();

    void setRight(int var1);

    NestedSetsTreeNode getTopLevel();

    void setTopLevel(NestedSetsTreeNode var1);

    NestedSetsTreeNode clone();
}
