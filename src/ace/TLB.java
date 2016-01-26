package ace;

import java.util.Hashtable;
import java.util.Random;

public class TLB implements ITLB{

	private int[] pageNumber, frameNumber;
	private IPhysicalMemory physicalMemory;
	private IPageTable pageTable;
	private int currentPageNumber = 0;
	private int counter = 0;
	
	public TLB(){
	this.pageNumber = new int[16];
	this.frameNumber = new int[16];
	initArrays();
	physicalMemory = new PhysicalMemory(this);
	pageTable = new PageTable(physicalMemory);
	
	}
	
	private void initArrays(){
		for(int i = 0; i < pageNumber.length; i++){
			pageNumber[i] = -1;
			frameNumber[i] = -1;
		}
	}

	public void setFrameNumber(int frameNumber){
		if(counter > 15){
		Random r = new Random();
		int Low = 0;
		int High = 16;
		int Result = r.nextInt(High-Low) + Low;
		this.pageNumber[Result] = currentPageNumber;
		this.frameNumber[Result] = frameNumber;
		pageTable.setFrameNumber(frameNumber);
		}else {
		pageNumber[counter] = currentPageNumber;
		this.frameNumber[counter] = frameNumber;
		pageTable.setFrameNumber(frameNumber);
		counter++;
		}
	}
	
	public int getFrameNumber(int pageNumber){
		currentPageNumber = pageNumber;
		return pageTable.getFrameNumber(pageNumber);
	}
	
	public int getValue(int pageNum, int offset){
		this.currentPageNumber = pageNum;
		for(int i = 0; i < this.pageNumber.length; i++){
			if(this.pageNumber[i] == pageNum){
				return physicalMemory.getFrameValue(this.frameNumber[i],offset);
			}
		}
		return pageTable.getValue(pageNum, offset);
	}
	
	public float getPageFaultCount(){
		return pageTable.getPageFaultCount();
		
	}
}
