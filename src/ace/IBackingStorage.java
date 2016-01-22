package ace;

import java.io.IOException;

public interface IBackingStorage {
	public void readFrame(int pageNumber) throws IOException;
}
