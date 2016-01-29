package ace;

public interface IPageTable {

	public void setFrameNumber(int value);
	public int getFrameNumber(int pageNumber);
	public int getCurrentPageNumber();
	public int getValue(int pageNumber, int offset);
	public int getPageFaultCount();
	public void removeFrameNumber(int pageNumber);
	public int getPageHitCount();
}
