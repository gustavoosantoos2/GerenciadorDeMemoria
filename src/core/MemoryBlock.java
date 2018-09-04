package core;

import java.util.Optional;

public class MemoryBlock {
	private Optional<Integer> processId;

	public MemoryBlock() {
		this.processId = Optional.ofNullable(null);
	}
	
	public MemoryBlock(int processId) {
		this.processId = Optional.of(processId);
	}
	
	public Optional<Integer> getProcessId() {
		return processId;
	}
	
	public void setProcessId(int processId) {
		if (processId < 0)
			throw new IllegalArgumentException("O id do processo não deve ser menor do que zero.");
		
		this.processId = Optional.of(processId);
	}

	public boolean isFreeBlock() {
		return !this.processId.isPresent();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MemoryBlock == false)
			return false;
		
		MemoryBlock other = (MemoryBlock) obj;
		
		return 	(this.isFreeBlock() == true && other.isFreeBlock() == true) || 
				(this.getProcessId().orElse(-1) == other.getProcessId().orElse(-1));
	}
}
