/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sse1;

import java.util.*;

/**
 *
 * @author Marta
 */
public class Index {
    String longest = "";
    Map<String, SortedSet<String>> map;

    public Index(){
        map = new TreeMap<String, SortedSet<String>>();
    }
}
