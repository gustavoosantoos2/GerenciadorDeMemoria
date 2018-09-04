package core;

public class ManagedProcess {
	private MemoryProcess process;
	private int startPosition;

	public ManagedProcess(MemoryProcess process, int startPosition) {
		this.process = process;
		this.startPosition = startPosition;
	}

	public MemoryProcess getProcess() {
		return process;
	}

	public int getStartPosition() {
		return startPosition;
	}
}
