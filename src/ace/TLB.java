package ace;

import java.util.LinkedList;
import java.util.Queue;

public class TLB implements ITLB {

	private int[] pageNumber, frameNumber;
	private IPhysicalMemory physicalMemory;
	private IPageTable pageTable;
	private int currentPageNumber = 0;
	private int counter = 0;
	private Queue<Integer> fifo;

	public TLB() {
		this.pageNumber = new int[16];
		this.frameNumber = new int[16];
		initArrays();
		physicalMemory = new PhysicalMemory(this);
		pageTable = new PageTable(physicalMemory);
		fifo = new LinkedList<>();
	}

	private void initArrays() {
		for (int i = 0; i < pageNumber.length; i++) {
			pageNumber[i] = -1;
			frameNumber[i] = -1;
		}
	}

	public void setFrameNumber(int frameNumber) {
		if (fifo.size() < pageNumber.length) {
			fifo.add(counter);
			pageNumber[counter] = currentPageNumber;
			this.frameNumber[counter] = frameNumber;
			pageTable.setFrameNumber(frameNumber);
			counter++;
		} else {
			int head = fifo.remove();
			pageNumber[head] = currentPageNumber;
			this.frameNumber[head] = frameNumber;
			pageTable.setFrameNumber(frameNumber);
			fifo.add(head);
		}
	}

	public int getFrameNumber(int pageNumber) {
		currentPageNumber = pageNumber;
		return pageTable.getFrameNumber(pageNumber);
	}

	public int getValue(int pageNum, int offset) {
		this.currentPageNumber = pageNum;
		for (int i = 0; i < this.pageNumber.length; i++) {
			if (this.pageNumber[i] == pageNum) {
				return physicalMemory.getFrameValue(this.frameNumber[i], offset);
			}
		}
		return pageTable.getValue(pageNum, offset);
	}

	public float getPageFaultCount() {
		return pageTable.getPageFaultCount();
	}

	public void removeFrames(int frameNumber) {
		for (int i = 0; i < this.frameNumber.length; i++) {
			if (this.frameNumber[i] == frameNumber) {
				pageNumber[i] = -1;
				this.frameNumber[i] = -1;
				break;
			}
		}
		pageTable.removeFrameNumber(frameNumber);
	}
}
