package program;

import java.util.Arrays;
import java.util.Optional;

public enum Actions {
	CriarProcesso(1, "Criar Processo"),
	RemoverProcesso(2, "Remover Processo"),
	ImprimirMemoria(3, "Imprimir Memória"),
	Sair(0, "Sair");
	
	private final int action;
	private final String actionName;
	
	Actions(int action, String actionName) {
		this.action = action;
		this.actionName = actionName;
	}
	
	public int getValor() {
		return this.action;
	}
	
	public String getName() {
		return this.actionName;
	}
	
	public static Optional<Actions> fromInt(int number) {
		return Arrays.asList(Actions.values()).stream().filter(x -> x.getValor() == number).findFirst();
	}
}