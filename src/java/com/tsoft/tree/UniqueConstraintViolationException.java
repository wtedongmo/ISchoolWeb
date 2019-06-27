/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.tree;

public class UniqueConstraintViolationException extends Exception {
    private final TreeNode originator;

    public UniqueConstraintViolationException(String message, TreeNode originator) {
        super(message);
        this.originator = originator;
    }

    public TreeNode getOriginator() {
        return this.originator;
    }
}
