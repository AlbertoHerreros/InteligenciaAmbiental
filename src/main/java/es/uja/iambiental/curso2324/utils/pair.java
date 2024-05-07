/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uja.iambiental.curso2324.utils;

/**
 *
 * @author Alberto Limas
 */
public class pair{
   int first,second;

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }
   
   // constructor for assigning values
   public pair(int first,int second){
       this.first = first;
       this.second = second;
   }
   
   // function which returns a
   public pair values(){
       return new pair(first,second);
   }
   
   // printing the pair class
   @Override
   public String toString(){
       return "("+first+","+second+")";
   }
}