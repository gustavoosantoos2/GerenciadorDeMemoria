package program;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import core.MemoryProcess;
import core.ProcessManager;

public class Program {

	public static void main(String[] args) throws Exception {
		try (Scanner scanner = new Scanner(System.in)) {

			System.out.print("Informe o tamanho da memória: ");
			int tamanho = scanner.nextInt();
			ProcessManager manager = new ProcessManager(tamanho);
			
			Optional<Actions> action = printActionsAndGetSelected(scanner);
			
			while (!action.isPresent() || action.get() != Actions.Sair) {
				
				if (!action.isPresent()) {
					System.err.println("Opção inválida!");
				} else {
					
					switch (action.get()) {
					case CriarProcesso:
						System.out.print("Informe o tamanho do processo: ");
						int tamanhoNovoProcesso = scanner.nextInt();
						int novoProcessId = manager.getNextPID();
						manager.createProcess(new MemoryProcess(novoProcessId, tamanhoNovoProcesso));
						break;
					case RemoverProcesso:
						System.out.print("Informe o PID a ser removido: ");
						int pidToRemove = scanner.nextInt();
						manager.removeProcess(pidToRemove);
						break;
					case ImprimirMemoria:
						manager.describeMemory();
						break;
					default:
						break;
					}
				}

				action = printActionsAndGetSelected(scanner);
			}
		}
	}

	public static Optional<Actions> printActionsAndGetSelected(Scanner scanner) {
		List<String> acoes = Arrays
				.asList(Actions.values())
				.stream()
				.map(act -> act.getValor() + " - " + act.getName())
				.collect(Collectors.toList());

		acoes.forEach(System.out::println);

		System.out.print("Ação: ");

		return Actions.fromInt(scanner.nextInt());
	}
}
