package ace;

import java.util.LinkedList;
import java.util.Queue;

public class PhysicalMemory implements IPhysicalMemory{
	IPageTable pageTable;
	private Frame[] frameTable = new Frame[128];
	private int freeFrame;
	TLB tlb;
	Queue<Integer> fifo;
	private int fifoCounter = 0;

	public PhysicalMemory(TLB tlb) {
		this.tlb = tlb;
		freeFrame = 0;
		fillArray();
		fifo = new LinkedList<>();
	}

	private void fillArray() {
		for (int i = 0; i < frameTable.length; i++) {
			frameTable[i] = new Frame();
		}
	}

	public void setFrame(byte[] frame) {
		if(freeFrame < 128){
			fifo.add(freeFrame);
			frameTable[freeFrame].setPage(frame);
			tlb.setFrameNumber(freeFrame);
			freeFrame++;
		}else {
			
			int head = fifo.remove();
			tlb.removeFrames(head);
			frameTable[head].setPage(frame);
			tlb.setFrameNumber(head);
			fifo.add(head);
		}
			
		
	}

	public byte getFrameValue(int frameNumber, int offset) {
		return frameTable[frameNumber].getValue(offset);
	}

	// internal class..
	private class Frame {
		private byte[] page = new byte[265];

		public void setPage(byte[] b) {
			for (int i = 0; i < b.length; i++) {
				page[i] = b[i];
			}
		}

		public byte getValue(int offset) {
			return page[offset];
		}
	}
}
