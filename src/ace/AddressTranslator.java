package ace;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class AddressTranslator {
	FileReader fr;
	BufferedReader br;
	String address = "";
	int pageNumber, offset;
	IPageTable pageTable;
	

	public AddressTranslator() throws FileNotFoundException {
		fr = new FileReader("InputFile.txt");
		br = new BufferedReader(fr);
		pageTable = new PageTable();
	}

	public void begin() throws IOException {
		while ((address = br.readLine()) != null) {
			int i = (Integer.parseInt(address) & 0xFFFF);
			pageNumber = (i >> 8);
			offset = (i & 0xFF);
			System.out.print("Virtual address: " + i + " ");
			System.out.print("Physical Address: " + ((pageTable.getFrameNumber(pageNumber) << 8) + offset) + " ");
			System.out.println("Value: " + pageTable.getValue(pageNumber, offset));
		}
			System.out.println("PageFaultPercent: " + (pageTable.getPageFaultCount()/256)*100+ "%");
			System.out.println("Updated Version using GitHub");
	}

	public static void main(String[] args) {
		try {
			AddressTranslator VMM = new AddressTranslator();
			VMM.begin();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}