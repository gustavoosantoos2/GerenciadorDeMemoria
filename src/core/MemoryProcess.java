package core;

public class MemoryProcess {
	private int processId;
	private int size;

	public MemoryProcess(int processId, int size) {
		this.processId = processId;
		this.size = size;
	}

	public int getProcessId() {
		return processId;
	}

	public int getSize() {
		return size;
	}
}
