package ace;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PageTable implements IPageTable {
	private int[] pageTable = new int[256];
	private IPhysicalMemory physicalMem;
	private IBackingStorage backingStore;
	private int pageFaultCount;
	private int pageHitCount;
	private int currentPageNumber;

	public PageTable(IPhysicalMemory physicalMemory) {
		initArray();
		pageFaultCount = 0;
		currentPageNumber = 0;
		physicalMem = physicalMemory;
		try {
			backingStore = new BackingStorage(physicalMemory);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void initArray() {
		for (int i = 0; i < pageTable.length; i++)
			pageTable[i] = -1;
	}

	public void setFrameNumber(int value) {
		pageTable[currentPageNumber] = value;
	}
	
	public void removeFrameNumber(int frameNumber){
		for(int i = 0; i < this.pageTable.length; i++){
			if(this.pageTable[i] == frameNumber){
				pageTable[i] = -1;
				break;
			}
		}
	}

	public int getFrameNumber(int pageNumber) {
		if (pageTable[pageNumber] == -1)
			return pageFault(pageNumber);
		
			pageHitCount++;
			System.out.print(" pageHit: " + pageHitCount);
			System.out.print(" pageNumber: " + pageNumber);
		return pageTable[pageNumber];
	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public int getValue(int pageNumber, int offset) {
		
		return physicalMem.getFrameValue(pageTable[pageNumber], offset);
	}

	private int pageFault(int pageNumber) {
		try {
			pageFaultCount++;
			System.out.print(" pageFault: " + pageFaultCount);
			System.out.print(" pageNumber: " + pageNumber);
			currentPageNumber = pageNumber;
			backingStore.readFrame(pageNumber);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pageTable[pageNumber];
	}

	public int getPageFaultCount() {
		return pageFaultCount;
	}
	
	public int getPageHitCount(){
		return pageHitCount;
	}
}
