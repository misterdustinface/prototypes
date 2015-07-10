import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;



public class Analysis {

	//0x7FFFFFFF; is max positive int value?
	
	public static void testFourByFour() throws IOException{
		//final int MAX = 0xFFFF; // 16 bit positive number
		//boolean[][] possibilityTable = new boolean[MAX][MAX];
		
		HashSet<Integer> possibilitySet = new HashSet<Integer>();
		
		Board A = new Board(0x0, 4);
		Board B = new Board(0x1, 4);
		Board C = new Board(0x2, 4);
		Board D = new Board(0x3, 4);
		
		possibilitySet.add(A.singleton());
		possibilitySet.add(B.singleton());
		possibilitySet.add(C.singleton());
		possibilitySet.add(D.singleton());
		//possibilityTable[A.primarySingleton()][A.secondarySingleton()] = true;
		//possibilityTable[B.primarySingleton()][B.secondarySingleton()] = true;
		//possibilityTable[C.primarySingleton()][C.secondarySingleton()] = true;
		//possibilityTable[D.primarySingleton()][D.secondarySingleton()] = true;
//		long startTime = System.currentTimeMillis();
//		long stopWatch = startTime + 20000;
		
		long stopTime  = System.currentTimeMillis() + 20000;
		
		//(Integer.MAX_VALUE >> 1)
		//for(int i = 0; i < 2000; ++i){
		while(System.currentTimeMillis() < stopTime){
			A.inverseFlipRandom();
			B.inverseFlipRandom();
			C.inverseFlipRandom();
			D.inverseFlipRandom();
			
			possibilitySet.add(A.singleton());
			possibilitySet.add(B.singleton());
			possibilitySet.add(C.singleton());
			possibilitySet.add(D.singleton());
			//possibilityTable[A.primarySingleton()][A.secondarySingleton()] = true;
			//possibilityTable[B.primarySingleton()][B.secondarySingleton()] = true;
			//possibilityTable[C.primarySingleton()][C.secondarySingleton()] = true;
			//possibilityTable[D.primarySingleton()][D.secondarySingleton()] = true;
//			if(System.currentTimeMillis() >= stopWatch){
//				printInfo(possibilitySet);
//				stopWatch = System.currentTimeMillis() + 20000;
//			}
			
			
			//TODO fill possibilityTable for 8 combinations of same thing. Mirrored + Turning board 90 degrees in a combination of ways should give 8 I think.
		}

		printInfo(possibilitySet);
	}
	
	private static void printInfo(HashSet<Integer> possibilitySet) throws IOException{
		
		System.out.println("Writing Decimal Data...");
		FileWriter fw = new FileWriter("DecimalData.txt");
		fw.write(possibilitySet.size() + " found out of 4294967296. " + ((double)possibilitySet.size()/4294967296d) + "%" + System.lineSeparator());
		for(Integer i : possibilitySet.toArray(new Integer[]{})){
			fw.write(String.format("%12d",i) + System.lineSeparator());
		}
		fw.flush();
		fw.close();
		
		System.out.println("Writing Binary Data...");
		fw = new FileWriter("BinaryData.txt");
		fw.write(possibilitySet.size() + " found out of 4294967296. " + ((double)possibilitySet.size()/4294967296d) + "%" + System.lineSeparator());
		for(Integer i : possibilitySet.toArray(new Integer[]{})){
			fw.write(String.format("%32s", Integer.toBinaryString(i)) + System.lineSeparator());
		}
		fw.flush();
		fw.close();
	}
	
	public static void testSingleton(){
		Board A = new Board(0x0, 4);
		for(byte i = 0; i < 100; ++i){
			A.inverseFlipRandom();
		}
		
		System.out.println(A.toString());
		int singleton = A.singleton();
		System.out.println(singleton + "\n");
		System.out.println(Board.inverseSingleton(singleton));
	}
	
	public static void main(String[] args) throws IOException{
		//testSingleton();
		testFourByFour();
		System.out.println("Done!");
	}
	
}
