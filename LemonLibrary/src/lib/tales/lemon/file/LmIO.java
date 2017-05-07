package lib.tales.lemon.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lib.tales.lemon.file.util.LmDebug;

public class LmIO {
	public static void readData(LmHeader header, InputStream sInput)
	{
		if(!header.aIndex.isEmpty())
			header.aIndex.clear();
		
		LmIndex iIndex;
		byte buffer[];
		DataInputStream stream;
		try {
			for(int i = 0; i < header.iFile_Number; i++)
	        {
				iIndex = new LmIndex();
				buffer = new byte[LmInfo.LEMON_HEADER_SIZE_DATA];
				buffer = readBuffer(sInput, LmInfo.LEMON_HEADER_SIZE_DATA);
				stream = new DataInputStream(new ByteArrayInputStream(buffer));
	            
				buffer = XOR(buffer);
	            
				iIndex.iPos = readInt(stream);
				iIndex.iSize = readInt(stream);
				iIndex.iEtc = readInt(stream);
				
				iIndex.iEx1 = readInt(stream);
				iIndex.iEx2 = readInt(stream);
				iIndex.iEx3 = readInt(stream);
				iIndex.iEx4 = readInt(stream);
				iIndex.iEx5 = readInt(stream);
				
				iIndex.strName = readBuffer(stream, 992);
				
				header.aIndex.add(i,iIndex);
				stream.close();
	        }
		}
		catch (IOException e)
		{
			LmDebug.Error(e.toString());
		}
	}
	
	public static void writeData(OutputStream sOutput, LmHeader header)
	{
		LmIndex iIndex;
		ByteArrayOutputStream tOutput = new ByteArrayOutputStream();
		try {
			for(int i = 0; i < header.iFile_Number; i++)
		    {
			
	            iIndex = header.aIndex.get(i);
				writeInt(tOutput, iIndex.iPos);
				writeInt(tOutput, iIndex.iSize);
				writeInt(tOutput, iIndex.iEtc);
				
				writeInt(tOutput, iIndex.iEx1);
				writeInt(tOutput, iIndex.iEx2);
				writeInt(tOutput, iIndex.iEx3);
				writeInt(tOutput, iIndex.iEx4);
				writeInt(tOutput, iIndex.iEx5);
				
				writeBuffer(tOutput, iIndex.strName);
				
				byte buffer[] = XOR(tOutput.toByteArray());
				tOutput.close();
				sOutput.write(buffer);
			}
        }
		catch (IOException e) {
			LmDebug.Error(e.toString());
		}
	}
	
	public static byte[] readBuffer(InputStream stream, int unit)
	{
		byte buffer[] = new byte[unit];
		try {
			for(int i = 0; i < unit ; i++)
			{
				buffer[i] = (byte) stream.read();
			}
		}
		catch (IOException e)
		{
			LmDebug.Error(e.toString());
		}
		
		return buffer;
	}
	
	public static boolean writeBuffer(OutputStream stream, byte[] buffer)
	{
		try {
			for(int i = 0; i < buffer.length ; i++)
			{
				stream.write(buffer[i]);
			}
		}
		catch (IOException e)
		{
			LmDebug.Error(e.toString());
			return false;
		}
		return true;
	}
	
	public static int readInt(InputStream stream)
	{
		int i = -1;
		try
		{
			i = stream.read();
			i += stream.read() * 256;
			i += stream.read() * 256 * 256;
			i += stream.read() * 256 * 256 * 256;
		}
		catch(IOException e)
		{
			LmDebug.Error(e.toString());
			i = -1;
		}
		return i;
    }
	
	public static boolean writeInt(OutputStream stream, int iData)
	{
		int i = iData % 256;
		try {
			stream.write(i);
			iData = (int) Math.floor(iData / 256);
			i = iData % 256;
			stream.write(i);
			iData = (int) Math.floor(iData / 256);
			i = iData % 256;
			stream.write(i);
			iData = (int) Math.floor(iData / 256);
			i = iData % 256;
			stream.write(i);
		}
		catch (IOException e)
		{
			LmDebug.Error(e.toString());
			return false;
		}
		return true;
	}
	
	public static byte[] XOR(byte buffer[]){
		for(int i = 0; i < buffer.length; i++)
        {
        	buffer[i] ^= 83;
        }
		return buffer;
	}
}
