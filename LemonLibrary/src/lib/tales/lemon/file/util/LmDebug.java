package lib.tales.lemon.file.util;

import java.nio.charset.StandardCharsets;

import lib.tales.lemon.file.LmHeader;
import lib.tales.lemon.file.LmIndex;

public class LmDebug {
	
	public static void Print(Object obj){
		System.out.print("Debug: "+obj+" ");
	}
	public static void Error(Object obj){
		System.err.print("Error!: "+obj.toString()+" ");
	}
	public static void PrintUTF(byte[] buff)
	{
		Print(new String(buff,StandardCharsets.UTF_8));
	}
	public static void ErrorUTF(byte[] buff)
	{
		Error(new String(buff,StandardCharsets.UTF_8));
	}
	public static void Header(LmHeader header){
		Print(header.iHeader);
		Print(header.iVersion);
		Print(header.iFile_Number);
		Print(header.iFile_Type);
		Print(header.strPass);
		for(int i = 0;i<header.aIndex.size();i++)
		{
			System.out.println();
			Index(header.aIndex.get(i));
		}
	}
	public static void Index(LmIndex index)
	{
		System.out.println("Index Read");
		Print(index.iPos);
		Print(index.iSize);
		Print(index.iEtc);
		
		Print(index.iEx1);
		Print(index.iEx2);
		Print(index.iEx3);
		Print(index.iEx4);
		Print(index.iEx5);
		
		System.out.println();
		
		PrintUTF(index.strName);
	}
}
