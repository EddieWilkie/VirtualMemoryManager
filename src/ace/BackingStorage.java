package ace;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BackingStorage implements IBackingStorage{
	private IPhysicalMemory physicalMem;
	private File fileName;
	private RandomAccessFile backingStore;

	public BackingStorage(IPhysicalMemory pm) throws FileNotFoundException {
		this.physicalMem = pm;
		fileName = new File("BACKING_STORE");
		backingStore = new RandomAccessFile(fileName, "r");
	}

	public void readFrame(int pageNumber) throws IOException{
		byte[] test = new byte[256];
		backingStore.seek(pageNumber << 8);
		backingStore.readFully(test);
		physicalMem.setFrame(test);
	}
}
