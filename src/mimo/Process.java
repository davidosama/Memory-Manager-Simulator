/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mimo;

/**
 *
 * @author David
 */
public class Process {
    String name;
    int start;
    int end;
    int size;
    
    Process (String name, int size){
        this.name = name;
        this.size = size;
    }
    Process(){}
}
