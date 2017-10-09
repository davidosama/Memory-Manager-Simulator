package mimo;

public class MIMO {

    public static void main(String[] args) {
        Memory.FreeBlocks.add(Memory.firstBlock);
        GUI window = new GUI();
        window.setVisible(true);
    }
    
}
