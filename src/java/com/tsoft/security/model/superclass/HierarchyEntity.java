/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.security.model.superclass;

import com.tsoft.tree.nestedsets.NestedSetsTreeNode;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class HierarchyEntity extends LifeCycleEntity implements NestedSetsTreeNode {
    public HierarchyEntity() {
    }

    public int getLeft() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setLeft(int left) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getRight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setRight(int right) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NestedSetsTreeNode getTopLevel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setTopLevel(NestedSetsTreeNode topLevel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NestedSetsTreeNode clone() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
