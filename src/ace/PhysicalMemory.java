package ace;

public class PhysicalMemory implements IPhysicalMemory{
	IPageTable pageTable;
	private Frame[] frameTable = new Frame[256];
	private int freeFrame;

	public PhysicalMemory(IPageTable pageTable) {
		this.pageTable = pageTable;
		freeFrame = 0;
		fillArray();
	}

	private void fillArray() {
		for (int i = 0; i < frameTable.length; i++) {
			frameTable[i] = new Frame();
		}
	}

	public void setFrame(byte[] frame) {
		frameTable[freeFrame].setPage(frame);
		pageTable.setFrameNumber(freeFrame);
		freeFrame++;
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
