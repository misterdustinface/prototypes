

public class Board {
	
	private 			byte[][] 	board;
	private 			byte 		currentMax;
	
	public Board(final int INITIAL_VALUE, final int SIZE){
		currentMax 	= (byte)(SIZE-1);
		reset((byte)INITIAL_VALUE);
	}
	
	public void reset(final byte INITIAL_VALUE){
		board 		= new byte[currentMax+1][currentMax+1];
		for(int row = 0; row < board.length; ++row){
			for(byte col = 0; col < board[row].length; ++col){
				board[row][col] = INITIAL_VALUE;
			}
		}
	}
	
	public void inverseFlipRandom(){
		inverseFlip((int)(Math.random() * board.length), (int)(Math.random() * board[0].length));
	}
	
	public void inverseFlip(final int ROW, final int COL){
		flipSub(ROW, COL);
		flipAdd(ROW, COL+1);
		flipAdd(ROW, COL-1);
		flipAdd(ROW+1, COL);
		flipAdd(ROW-1, COL);
	}
	
	/**
	 * Makes a 16 bit # that represents half of the board, with positive numbers.
	 * @return partial singleton
	 */
	public int primarySingleton(){
		return (((((board[0][0] & 0x3) << 2) | (board[0][1] & 0x3)) & 0xf) << 12)	
			|  (((((board[0][2] & 0x3) << 2) | (board[0][3] & 0x3)) & 0xf) << 8)
			|  (((((board[1][0] & 0x3) << 2) | (board[1][1] & 0x3)) & 0xf) << 4)
			|  (((((board[1][2] & 0x3) << 2) | (board[1][3] & 0x3)) & 0xf));
	}
	/**
	 * Makes a 16 bit # that represents the other half of the board, with positive numbers.
	 * @return partial singleton
	 */
	public int secondarySingleton(){
		return (((((board[2][0] & 0x3) << 2) | (board[2][1] & 0x3)) & 0xf) << 12)
			|  (((((board[2][2] & 0x3) << 2) | (board[2][3] & 0x3)) & 0xf) << 8)
			|  (((((board[3][0] & 0x3) << 2) | (board[3][1] & 0x3)) & 0xf) << 4)
			|  (((((board[3][2] & 0x3) << 2) | (board[3][3] & 0x3)) & 0xf));
	}
	
	/**
	 * Makes a 32 bit # that represents the board as a singleton
	 * Can not be used for indexing an array (negative numbers)
	 * @return singleton
	 */
	public int singleton(){
		return (primarySingleton() << 16) | secondarySingleton();
	}
	
	public static Board inverseSingleton(int singleton){
		Board temp 		= new Board(0x0,4);
		String bitstr 	= Integer.toBinaryString(singleton);
		
		int zeroesToAdd = 32 -  bitstr.length();
		String zeroes = new String();
		for(int i = 0; i < zeroesToAdd; ++i){
			zeroes += "0";
		}
		bitstr = zeroes + bitstr;
		
		temp.board[0][0] = Byte.parseByte(bitstr.substring(0, 2), 2);
		temp.board[0][1] = Byte.parseByte(bitstr.substring(2, 4), 2);
		temp.board[0][2] = Byte.parseByte(bitstr.substring(4, 6), 2);
		temp.board[0][3] = Byte.parseByte(bitstr.substring(6, 8), 2);
		temp.board[1][0] = Byte.parseByte(bitstr.substring(8, 10),2);
		temp.board[1][1] = Byte.parseByte(bitstr.substring(10, 12),2);
		temp.board[1][2] = Byte.parseByte(bitstr.substring(12, 14),2);
		temp.board[1][3] = Byte.parseByte(bitstr.substring(14, 16),2);
		
		temp.board[2][0] = Byte.parseByte(bitstr.substring(16, 18),2);
		temp.board[2][1] = Byte.parseByte(bitstr.substring(18, 20),2);
		temp.board[2][2] = Byte.parseByte(bitstr.substring(20, 22),2);
		temp.board[2][3] = Byte.parseByte(bitstr.substring(22, 24),2);
		temp.board[3][0] = Byte.parseByte(bitstr.substring(24, 26),2);
		temp.board[3][1] = Byte.parseByte(bitstr.substring(26, 28),2);
		temp.board[3][2] = Byte.parseByte(bitstr.substring(28, 30),2);
		temp.board[3][3] = Byte.parseByte(bitstr.substring(30, 32),2);
		
		return temp;
	}
	
	private void flipAdd(final int ROW, final int COL){
		if(ROW >= 0 && ROW < board.length && COL >= 0 && COL < board[ROW].length){
			++board[ROW][COL];
			if(board[ROW][COL] > currentMax){
				board[ROW][COL] = 1;
			}
		}
	}
	
	private void flipSub(final int ROW, final int COL){
		if(ROW >= 0 && ROW < board.length && COL >= 0 && COL < board[ROW].length){
			--board[ROW][COL];
			if(board[ROW][COL] < 0){
				board[ROW][COL] = currentMax;
			}
		}
	}
	
	public String toString(){
		String str = new String();
		for(int row = 0; row < board.length; ++row){
			for(int col = 0; col < board[row].length; ++col){
				str += board[row][col] + " ";
			}
			str += System.lineSeparator();
		}
		return str;
	}

}
