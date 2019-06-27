/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.tree;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface TreeDao<N extends TreeNode> {
    int UNDEFINED_POSITION = -1;

    boolean isPersistent(N var1);

    N find(Serializable var1);

    void update(N var1) throws UniqueConstraintViolationException;

    boolean isRoot(N var1);

    N createRoot(N var1) throws UniqueConstraintViolationException;

    int size(N var1);

    List<N> getRoots();

    void removeAll();

    List<N> getTree(N var1);

    List<N> getTreeCacheable(N var1);

    List<N> findSubTree(N var1, List<N> var2);

    List<N> findDirectChildren(List<N> var1);

    boolean isLeaf(N var1);

    int getChildCount(N var1);

    List<N> getChildren(N var1);

    N getRoot(N var1);

    N getParent(N var1);

    List<N> getPath(N var1);

    int getLevel(N var1);

    boolean isEqualToOrChildOf(N var1, N var2);

    boolean isChildOf(N var1, N var2);

    N addChild(N var1, N var2) throws UniqueConstraintViolationException;

    N addChildAt(N var1, N var2, int var3) throws UniqueConstraintViolationException;

    N addChildBefore(N var1, N var2) throws UniqueConstraintViolationException;

    void remove(N var1);

    void move(N var1, N var2) throws UniqueConstraintViolationException;

    void moveTo(N var1, N var2, int var3) throws UniqueConstraintViolationException;

    void moveBefore(N var1, N var2) throws UniqueConstraintViolationException;

    void moveToBeRoot(N var1) throws UniqueConstraintViolationException;

    N copy(N var1, N var2, N var3) throws UniqueConstraintViolationException;

    N copyTo(N var1, N var2, int var3, N var4) throws UniqueConstraintViolationException;

    N copyBefore(N var1, N var2, N var3) throws UniqueConstraintViolationException;

    N copyToBeRoot(N var1, N var2) throws UniqueConstraintViolationException;

    void setCopiedNodeRenamer(TreeDao.CopiedNodeRenamer<N> var1);

    List<N> find(N var1, Map<String, Object> var2);

    void setUniqueTreeConstraint(UniqueTreeConstraint<N> var1);

    void setCheckUniqueConstraintOnUpdate(boolean var1);

    void checkUniqueConstraint(N var1, N var2, N var3) throws UniqueConstraintViolationException;

    public interface CopiedNodeRenamer<N extends TreeNode> {
        void renameCopiedNode(N var1);
    }
}
