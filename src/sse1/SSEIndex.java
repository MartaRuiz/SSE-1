/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sse1;

/**
 *
 * @author Marta
 */
public class SSEIndex {
    public static void main(String[] args){
        int b = 128/8;
        byte[] aB = new byte[b];
        for(int i=0;i<b;i++){
            aB[i]=1;
        }
        System.out.println(aB.toString());
    }
}
