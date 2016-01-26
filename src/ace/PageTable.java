package ace;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PageTable implements IPageTable {
	private int[] pageTable = new int[256];
	private IPhysicalMemory physicalMem;
	private IBackingStorage backingStore;
	private int pageHitCount, pageFaultCount;
	private int currentPageNumber;

<<<<<<< HEAD
	public PageTable(TLB tlb,IPhysicalMemory physicalMemory) {
=======
	public PageTable(IPhysicalMemory physicalMemory) {
>>>>>>> TLB
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

	public int getFrameNumber(int pageNumber) {
		if (pageTable[pageNumber] == -1)
			pageFault(pageNumber);
		

		return pageTable[pageNumber];
	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public int getValue(int pageNumber, int offset) {
		if (pageTable[pageNumber] == -1)
			pageFault(pageNumber);
		
		return physicalMem.getFrameValue(pageTable[pageNumber], offset);
	}

	private void pageFault(int pageNumber) {
		try {
			pageFaultCount++;
			currentPageNumber = pageNumber;
			backingStore.readFrame(pageNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public float getPageFaultCount() {
		return pageFaultCount;
	}
}
