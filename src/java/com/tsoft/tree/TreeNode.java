/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.tree;


import java.io.Serializable;

public interface TreeNode extends Cloneable {
    Serializable getId();

    TreeNode clone();
}
