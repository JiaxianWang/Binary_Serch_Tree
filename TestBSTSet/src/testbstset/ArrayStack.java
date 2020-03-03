


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
public class ArrayStack<E> implements Stack<E> {
    private E[] items;
    private int top = -1;
    
    public ArrayStack()
    {
        this(100);
    }
    
    public ArrayStack(int n) 
    {
        items = (E[]) new Object[n];
    }
            
    @Override
    public boolean isEmpty()
    {
        return (top<0);
    }
    
    @Override
    public void push(E e)
    {
        if(top == items.length-1) 
        {
            E[] newArray = (E[]) new Object[2*items.length];
            System.arraycopy(items, 0, newArray, 0, items.length);
            items = newArray;
        }
        items[++top] = e;
    }

    @Override
    public E top() throws EmptyStackException 
    {
        if(isEmpty())
            throw new EmptyStackException("Stack underflow");
        else
            return (items[top]);
    }

    @Override
    public E pop() //throws EmptyStackException 
    {
        //if(isEmpty())
            //throw new EmptyStackException("Stack underflow");
        //else 
        {
            E tempE = items[top];
            items[top--] = null;
            return(tempE);
        }
    }
}





