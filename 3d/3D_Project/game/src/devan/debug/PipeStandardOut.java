package devan.debug;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

public class PipeStandardOut {
	private static PipedInputStream  pipe;
	private static PrintStream       systemOut;
	private static OutputStream      fileOutput;
	
	/**
	 * Redirects standard out so it can be controlled by this class
	 * 
	 * @throws IOException
	 */
	public static void startPipe() throws IOException {
		systemOut = System.out;
		PipedOutputStream pipedOut = new PipedOutputStream();
		System.setOut(new PrintStream(pipedOut));
		pipe = new PipedInputStream(pipedOut);
	}
		
	/**
	 * Whenever a read is called in addition to returning a string
	 * it will also send the string to this outputStream
	 * 
	 * @param outputStream the output stream to send to
	 */
	public static void setFileWriter(OutputStream outputStream) {
		fileOutput = outputStream;
	}
	
	/**
	 * Read one char
	 * 
	 * @return the char
	 * @throws IOException
	 */
	public static char readChar() throws IOException {
		String c = "" + (char) pipe.read();
		
		systemOut.append(c);
		writeToFile(c);
		return c.charAt(0);
	}
	
	/**
	 * Read until a new line or until the stream is empty
	 * 
	 * @return what was read
	 * @throws IOException
	 */
	public static String readLine() throws IOException {
		if(isEmpty()) {
			return "";
		}
		
		char c;
		StringBuffer buffer = new StringBuffer();
		
		do {
			c = readChar();
			buffer.append(c);
		} while(!isEmpty() && c != '\n');
		
		return buffer.toString();
	}
	

	
	private static void writeToFile(String string) throws IOException {
		if(fileOutput != null) {
			fileOutput.write(string.getBytes());
		}
	}
	
	private static boolean isEmpty() throws IOException {
		return pipe.available() == 0;
	}
}
