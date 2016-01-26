package ace;

import java.util.Hashtable;
import java.util.Random;

public class TLB implements ITLB{

	private int[] pageNumber, frameNumber;
	private IPhysicalMemory physicalMemory;
	private IPageTable pageTable;
	private int currentPageNumber = 0;
	
	public TLB(){
	initArrays();
	this.pageNumber = new int[16];
	this.frameNumber = new int[16];
	pageTable = new PageTable(this,physicalMemory);
	}
	
	private void initArrays(){
		for(int i = 0; i < pageNumber.length; i++){
			pageNumber[i] = -1;
			frameNumber[i] = -1;
		}
	}

	public void setFrameNumber(int frameNumber){
		Random r = new Random();
		int Low = 0;
		int High = 15;
		int Result = r.nextInt(High-Low) + Low;
		this.pageNumber[Result] = currentPageNumber;
		this.frameNumber[Result] = frameNumber;
	}
	
	public int getValue(int pageNum, int offset){
		this.currentPageNumber = pageNum;
		System.out.println("test");
//		for(int i = 0; i < this.pageNumber.length; i++){
//			if(this.pageNumber[i] == pageNum){
//				System.out.println(this.pageNumber[i]);
//				return physicalMemory.getFrameValue(this.frameNumber[i],offset);
//			}
//		}
		return 1;
		//return pageTable.getValue(pageNum, offset);
	}
}
