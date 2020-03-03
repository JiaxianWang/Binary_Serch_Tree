/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testbstset;

/**
 *
 * @author BRUCE WANG
 * @param <E>
 */
public interface Stack<E> {
    public boolean isEmpty();
    public E top() throws EmptyStackException;
    public void push(E e);
    public E pop(); //throws EmptyStackException;
}
