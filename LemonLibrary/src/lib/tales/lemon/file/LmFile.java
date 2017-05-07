package lib.tales.lemon.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import lib.tales.lemon.file.util.LmDebug;

public class LmFile
{
	public LmHeader m_header;
	
	public LmFile(LmHeader header)
	{
		this.m_header = header;
	}
	
	public boolean isValidFile()
	{
		return this.m_header.iHeader == LmInfo.LEMON_HEADER;
	}
	
	public static LmFile newLemon(File file)
	{
		DataInputStream stream;
		LmHeader header;
		LmFile lmfile;
        
        try {
        	stream = new DataInputStream(new FileInputStream(file));
    		header = new LmHeader();
    		header.iHeader = LmIO.readInt(stream);
    		header.iVersion = LmIO.readInt(stream);
    		header.iFile_Number = LmIO.readInt(stream);
    		header.iFile_Type = LmIO.readInt(stream);
    		header.strPass = LmIO.readBuffer(stream, LmInfo.LEMON_HEADER_SIZE_PASS);
            LmIO.readData(header, stream);
            
            lmfile = new LmFile(header);
			stream.close();
		} catch (IOException e) {
			LmDebug.Error(e.toString());
			return null;
		}
        return lmfile;
    }
	
	//Add Header to Raw Script
	/*
	 * @file - Raw Script
	 * @lFile - Lemon File to Read Header
	 * @result - Result file
	 */
	public static void Header_Add( File file, LmHeader header, File result )
	{
		byte buffer[];
		DataInputStream stream;
		DataOutputStream stream_r;
		
		try {
			stream = new DataInputStream(new FileInputStream(file)); //Init Stream
			buffer = new byte[stream.available()];
			for(int i = 0; i < header.iFile_Number; i++)
	        {
				header.aIndex.get(i).iSize = stream.available();
				header.aIndex.get(i).iEtc = stream.available();
	        }
			stream.read(buffer);
			stream.close();
			
			stream_r = new DataOutputStream(new FileOutputStream(result)); //Init Stream
			LmIO.writeInt(stream_r, header.iHeader);
			LmIO.writeInt(stream_r, header.iVersion);
			LmIO.writeInt(stream_r, header.iFile_Number);
			LmIO.writeInt(stream_r, header.iFile_Type);
			LmIO.writeBuffer(stream_r, header.strPass);
			LmIO.writeData(stream_r, header);
			stream_r.write(buffer, 0, buffer.length);
			stream.close();
			stream_r.close();
		} catch (IOException e) {
			LmDebug.Error(e.toString());
			return;
		}
	}
	
	//Remove Header from Lemon File
	/*
	 * @file - Lemon File
	 * @result - Result file
	 */
	public static void Header_Remove(File file, File result)
	{
		byte buffer[];
		DataInputStream stream;
		DataOutputStream iStream;
		
		try {
			LmFile lFile = newLemon(file);
			if(lFile.isValidFile())
			{
				stream = new DataInputStream(new FileInputStream(file)); //Init Stream
				
				//skip all header
				stream.skipBytes(LmInfo.LEMON_HEADER_SIZE_INFO + (LmInfo.LEMON_HEADER_SIZE_DATA * lFile.m_header.iFile_Number));
				
				buffer = new byte[stream.available()];
				stream.read(buffer);
				stream.close();
				
				iStream = new DataOutputStream(new FileOutputStream(result)); //Init Stream
				iStream.write(buffer, 0, buffer.length); //Write
				iStream.close(); //Close Stream
			}
		} catch (IOException e) {
			LmDebug.Error(e.toString());
			return;
		}
	}
}
