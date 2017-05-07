package lib.tales.lemon.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import lib.tales.lemon.file.util.LmDebug;

public class LmHeader
{
	public int iHeader;
	public int iVersion;
	public int iFile_Number;
	public int iFile_Type;
	public byte strPass[];
	public List<LmIndex> aIndex = new ArrayList<LmIndex>();
	
	public LmHeader()
	{
		
	}
	
	public String toString()
	{
		OutputStream stream;
		
		try {
			stream = new ByteArrayOutputStream(); //Init Stream
			LmIO.writeInt(stream, iHeader);
			LmIO.writeInt(stream, iVersion);
			LmIO.writeInt(stream, iFile_Number);
			LmIO.writeInt(stream, iFile_Type);
			LmIO.writeBuffer(stream, strPass);
			LmIO.writeData(stream, this);
			stream.close();
		} catch (IOException e) {
			LmDebug.Error(e.toString());
			return null;
		}
		return stream.toString();
	}
}
