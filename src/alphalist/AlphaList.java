/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alphalist;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 *
 * @author leonardo
 * @param <T>
 */
public class AlphaList<T> implements Iterable {

    Object elements[];
    int size;
    int last;

    public AlphaList() {
        size = 0;
        elements = new Object[1];
        last = -1;
    }

    public void add(T element) {
        size++;
        last++;
        int ver = 0;
        if (elements.length < size) {
            Object e[] = new Object[size * 2];
            for (int i = 0; i < elements.length; i++) {
                e[i] = elements[i];
            }
            e[last] = element;
            this.elements = e;
        } else {
            elements[last] = element;
        }

    }

    public void print() {
        for (int j = 0; j <= last; j++) {
            System.out.print(elements[j] + "   ");
        }
        System.out.println("");
    }

    public T get(int i) {
        return (T) elements[i];
    }

    public int indexOf(T element) {
        int i = 0;
        while (i < elements.length && elements[i] != element) {
            i++;
        }
        if (elements[i] == element) {
            return i;
        }
        return -1;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator it = new IterAlpha();
        return it;
    }

    @Override
    public void forEach(Consumer cnsmr) {
        Iterable.super.forEach(cnsmr); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Spliterator spliterator() {
        return Iterable.super.spliterator(); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean hasNext() {
        return true;
    }

    
     //Esto puede fallar deberia hacerla converion al objeto pasado por parametro
    public String[] toArray(String[] data) {
        data = new String[last];
        for (int i = 0; i <= last; i++) {
            data[i] = String.valueOf(elements[i]);
        }
        return data;
    }

    public Object[] toArray() {
        Object object[] = new Object[last];
        for (int i = 0; i < last; i++) {
            object[i] = elements[i];
        }
        return object;
    }

    class IterAlpha<T> implements Iterator {

        protected int posArray;

        public IterAlpha() {
            posArray = 0;
        }

        @Override
        public boolean hasNext() {
            if (posArray < elements.length) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public T next() {
            posArray++;
            return (T) elements[posArray - 1];
        }

    }

}
