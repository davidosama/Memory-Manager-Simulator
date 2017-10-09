/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mimo;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author David
 */
public class Memory {
    public static ArrayList <Process> Processes = new ArrayList ();
    public static ArrayList <Block> FreeBlocks = new ArrayList ();
    public static ArrayList <Block> FinalList;
    
    public static Block firstBlock = new Block(0, 1000);
    
    public static void FirstFit(Process p){
        boolean NoSpace = true;
        for (int i=0; i<(FreeBlocks.size()-1); i++){
            for(int j=1; j<(FreeBlocks.size()-i); j++){
                if (FreeBlocks.get(j-1).start > FreeBlocks.get(j).start){
                    Collections.swap (FreeBlocks , j, j-1 );
                }
            }
        }
        for(int i=0; i<FreeBlocks.size(); i++){
            
            if (FreeBlocks.get(i).size >= p.size){
                NoSpace = false;
                p.start = FreeBlocks.get(i).start;
                p.end = p.start + p.size;
                Processes.add(p);
                FreeBlocks.get(i).start = p.end;
                
                if(FreeBlocks.get(i).size == p.size){
                    FreeBlocks.remove(i);
                }
                else
                    FreeBlocks.get(i).size -= p.size;
                break;
            }
        }
        if(NoSpace){
            JOptionPane optionPane = new JOptionPane("NO ENOUGH SPACE!", JOptionPane.ERROR_MESSAGE);    
            JDialog dialog = optionPane.createDialog("Error");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            return;
        }
        createFinalList();
//        SortLists();
    }
    
    public static void BestFit(Process p){
        int minSize = 10000;
        int index = 0;
        boolean NoSpace = true;
        for (int i=0; i<(FreeBlocks.size()-1); i++){
            for(int j=1; j<(FreeBlocks.size()-i); j++){
                if (FreeBlocks.get(j-1).size > FreeBlocks.get(j).size){
                    Collections.swap (FreeBlocks , j, j-1 );
                }
            }
        }
        for(int i=0; i<FreeBlocks.size(); i++){
            if (FreeBlocks.get(i).size <= minSize && FreeBlocks.get(i).size >= p.size){
                NoSpace = false;
                minSize = FreeBlocks.get(i).size;
                index = i;
                break;
            }
        }
        if(NoSpace){
            JOptionPane optionPane = new JOptionPane("NO ENOUGH SPACE!", JOptionPane.ERROR_MESSAGE);    
            JDialog dialog = optionPane.createDialog("Error");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            return;
        }
        p.start = FreeBlocks.get(index).start;
        p.end = p.start + p.size;
        Processes.add(p);
        FreeBlocks.get(index).size -= p.size;
        if(FreeBlocks.get(index).size == 0){
            FreeBlocks.remove(index);
        }
        else
            FreeBlocks.get(index).start = p.end;
        createFinalList();
//        SortLists();
    }
    
    public static void Deallocate (String Pname){
        Process p = new Process();
        boolean found = false;
        for(int i=0; i<Processes.size(); i++){
            if(Processes.get(i).name.equals(Pname)){
                found = true;
                p = Processes.get(i);
                Processes.remove(i);
                break;
            }
        }
        if (!found){
            JOptionPane optionPane = new JOptionPane("Process NOT FOUND", JOptionPane.ERROR_MESSAGE);    
            JDialog dialog = optionPane.createDialog("Error");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            return;
        }
        Block newFreeBlock = new Block (p.start, p.end);
        newFreeBlock.size = p.size;
//        boolean blocksMerged = false;
//        for(int i=0; i<FreeBlocks.size(); i++){
//            if (FreeBlocks.get(i).start == (p.end) || FreeBlocks.get(i).end == (p.start)){
//                blocksMerged = true;
//                newFreeBlock = Block.merge(FreeBlocks.get(i), newFreeBlock);
//                FreeBlocks.remove(i);
//                FreeBlocks.add(i, newFreeBlock);
//                break;
//                int i1 = i-1;
//                int i2 = i+1;
//                if (FreeBlocks.get(i1).start == newFreeBlock.end || FreeBlocks.get(i1).end == newFreeBlock.start){
//                    blocksMerged = true;
//                    Block block2 = Block.merge(FreeBlocks.get(i1), newFreeBlock);
//                    FreeBlocks.remove(i1);
//                    FreeBlocks.add(i1, block2);
//                    for(int j=i1; j<FreeBlocks.size(); j++){
//                        if (FreeBlocks.get(j).start < FreeBlocks.get(j-1).start && FreeBlocks.get(j).end == FreeBlocks.get(j-1).end){
//                            FreeBlocks.remove(j-1);
//                            break;
//                        }
//                    }
//                    break;
//                }
//                else if (FreeBlocks.get(i2).start == newFreeBlock.end || FreeBlocks.get(i2).end == newFreeBlock.start){
//                    blocksMerged = true;
//                    Block block2 = Block.merge(FreeBlocks.get(i2), newFreeBlock);
//                    FreeBlocks.remove(i2);
//                    FreeBlocks.add(i2, block2);
//                    for(int j=i2; j<FreeBlocks.size(); j++){
//                        if (FreeBlocks.get(j).start < FreeBlocks.get(j-1).start && FreeBlocks.get(j).end == FreeBlocks.get(j-1).end){
//                            FreeBlocks.remove(j-1);
//                            break;
//                        }
//                    }
//                    break;
//                }
//                else break;
//            }
//        }
//        if (!blocksMerged){
            FreeBlocks.add(newFreeBlock);
//        }
        MergeFreeBlocks();
        createFinalList();
//        SortLists();
    }
    public static void MergeFreeBlocks(){
        for (int i=0; i<(FreeBlocks.size()-1); i++){
            for(int j=1; j<(FreeBlocks.size()-i); j++){
                if (FreeBlocks.get(j-1).start > FreeBlocks.get(j).start){
                    Collections.swap (FreeBlocks , j, j-1 );
                }
            }
        }
//        if(FreeBlocks.size() > 1){
            int i=0;
            for(int j=1; j<FreeBlocks.size(); ){
                if(FreeBlocks.get(i).end == FreeBlocks.get(j).start){
                    FreeBlocks.get(i).end = FreeBlocks.get(j).end;
                    FreeBlocks.remove(j);
                    FreeBlocks.get(i).size = FreeBlocks.get(i).end - FreeBlocks.get(i).start;
                }
                else{
                    i++;
                    j++;
                }
//            }
        }
    }
    public static void SortLists (){
       for (int i=0; i<(Processes.size()-1); i++){
            for(int j=1; j<(Processes.size()-i); j++){
                if (Processes.get(j-1).start < Processes.get(j).start){
                    Collections.swap (Processes , j, j-1 );
                }
            }
        }
       for (int i=0; i<(FreeBlocks.size()-1); i++){
            for(int j=1; j<(FreeBlocks.size()-i); j++){
                if (FreeBlocks.get(j-1).start < FreeBlocks.get(j).start){
                    Collections.swap (FreeBlocks , j, j-1 );
                }
            }
        }
    }
    
    public static void createFinalList (){
        FinalList = new ArrayList ();
        System.out.println("Processes size: "+Processes.size());
        for(int i=0; i<Processes.size(); i++){
            Block b = new Block (Processes.get(i).start, Processes.get(i).end, Processes.get(i).name);
            FinalList.add(b);
        }
        
        System.out.println("FreeBlocks size: "+FreeBlocks.size());
        for(int i=0; i<FreeBlocks.size(); i++){
            Block b = FreeBlocks.get(i);
            b.Pname = "free";
            FinalList.add(b);
        }
        
        System.out.println("FinalList size: "+FinalList.size());
        for (int i=0; i<(FinalList.size()-1); i++){
            for(int j=1; j<(FinalList.size()-i); j++){
                if (FinalList.get(j-1).start > FinalList.get(j).start){
                    Collections.swap (FinalList , j, j-1 );
                }
            }
        }
    }
}
