package CPS_Utilities;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class Pdf_Builder {
	
	private String path;
	
	public Pdf_Builder(String path)
	{
		this.path=path;
	}
	
	/*public boolean build(StatusReport statusReport)
	{
		PDDocument doc = new PDDocument();
		
		doc.save(path);
		
    	doc.close();
	}*/
}
