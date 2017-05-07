

import java.io.File;
import java.io.IOException;

import lib.tales.lemon.file.LmFile;

public class HeaderViewer {
	public static void main(String[] args) throws IOException {
		String baseDir = "E://Test/";
		File baseFile = new File(baseDir+"test.lm");
		if(baseFile.isFile())
		{
			LmFile lFile = LmFile.newLemon(baseFile);
			
			if(lFile.isValidFile())
			{
				System.out.println(lFile.m_header.toString());
				File exportFile = new File(baseDir+"extra.dlm");
				File resultFile = new File(baseDir+"r_test.lm");	
				LmFile.Header_Remove( baseFile, exportFile );
				
				System.out.println();
				System.out.print("say something if you done edit");
				System.in.read();
				
				LmFile.Header_Add( exportFile, lFile.m_header, resultFile );
			}
		}
	}
}
