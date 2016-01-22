package ace;

public interface IPageTable {

	public void setFrameNumber(int value);
	public int getFrameNumber(int pageNumber);
	public int getCurrentPageNumber();
	public int getValue(int pageNumber, int offset);
	public float getPageFaultCount();
}
