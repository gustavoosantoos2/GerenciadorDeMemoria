package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProcessManager {
	private int size;
	private MemoryBlock[] blocks;
	private List<ManagedProcess> processOnMemory;
	
	public ProcessManager(int size) {
		if (size < 0)
			throw new IllegalArgumentException("O tamanho deve maior do que zero.");

		this.size = size;
		this.processOnMemory = new ArrayList<>();
		
		initializeMemory();
	}

	public void createProcess(MemoryProcess process) throws Exception {
		if (process.getSize() < 0) {
			System.err.println("O tamanho do processo deve ser maior do que zero.");
			return;
		}
		
		Optional<Integer> memoryBlockPosition = findPositionToStartProcess(process);
		if (!memoryBlockPosition.isPresent()) {
			System.err.println("Não há espaço suficiente em memória para o processo.");
			return;
		}
		
		ManagedProcess managedProcess = new ManagedProcess(process, memoryBlockPosition.get());
		this.processOnMemory.add(managedProcess);
		
		putProcessOnMemory(managedProcess.getProcess(), managedProcess.getStartPosition());
	}

	public void removeProcess(int processId) {
		for (int i = 0; i < this.blocks.length; i++) {
			MemoryBlock block = this.blocks[i];

			if (!block.isFreeBlock() && block.getProcessId().get() == processId) {
				this.blocks[i] = new MemoryBlock();
			}
		}
	}

	public void describeMemory() {
		MemoryBlock lastBlock = null;
		List<MemoryBlock> currentProcessBlock = new ArrayList<>();
		
		System.out.print("*-");
				
		for (int i = 0; i < this.blocks.length; i++) {
			
			MemoryBlock block = this.blocks[i];
			
			if (lastBlock == null) {
				currentProcessBlock.add(block);
				lastBlock = block;
				continue;
			}
			
			if (block.equals(lastBlock) && i != this.blocks.length - 1) {
				currentProcessBlock.add(block);
			} else {
				if (block.equals(lastBlock))
					currentProcessBlock.add(block);
				
				String processIdOrFreeBlock = lastBlock.isFreeBlock() ? "L" : "P" + lastBlock.getProcessId().get(); 
				int indexOfFirstBlock = Arrays.asList(this.blocks).indexOf(currentProcessBlock.get(0));
				
				System.out.println(String.format(" [{0}|{1}|{2}] -", processIdOrFreeBlock, indexOfFirstBlock, currentProcessBlock.size()));
				
				currentProcessBlock.clear();
				currentProcessBlock.add(block);
			}
			
			lastBlock = block;
		} 
		
		System.out.println(".");
	}
	
	public int getNextPID() {
		return this.processOnMemory.size() + 1;
	}

	private void initializeMemory() {
		this.blocks = new MemoryBlock[size];

		for (int i = 0; i < size; i++) {
			this.blocks[i] = new MemoryBlock();
		}
	}

	private Optional<Integer> findPositionToStartProcess(MemoryProcess process) {
		for (int i = 0; i < this.blocks.length; i++) {
			
			if (i + process.getSize() >= this.blocks.length)
				return Optional.ofNullable(null);
			
			List<MemoryBlock> subBlock = Arrays.asList(this.blocks).subList(i, i + process.getSize());
			
			if (subBlock.stream().allMatch(b -> b.isFreeBlock())) {
				return Optional.of(i);
			}
		}
		
		return Optional.ofNullable(null);
	}
	
	private void putProcessOnMemory(MemoryProcess process, int startPosition) {
		for (int i = startPosition; i < startPosition + process.getSize(); i++) {
			this.blocks[i] = new MemoryBlock(process.getProcessId());
		}
	}
}
