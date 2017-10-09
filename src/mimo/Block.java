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
public class Block {
    int start = 1;
    int end = 1000;
    int size = start-end;
    String Pname;
    
    Block (int start, int end){
        this.start = start;
        this.end = end;
        this.size = (end - start)+1;
    }
    Block (int start, int end, String name){
        this.Pname = name;
        this.start = start;
        this.end = end;
        this.size = (end - start)+1;
    }
    
    static Block merge(Block b1, Block b2){
        if (b1.start < b2.start){
            return new Block (b1.start, b2.end);
        }
        else return new Block (b2.start, b1.end);
    }
}
