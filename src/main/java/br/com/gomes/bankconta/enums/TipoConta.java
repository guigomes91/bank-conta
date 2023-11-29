package br.com.gomes.bankconta.enums;

public enum TipoConta {

	CC(0, "CONTA_CORRENTE"), 
	CP(1, "CONTA_POUPANCA"),
	CF(2, "COFRINHO"),
	CS(3, "CONTA_SALARIO");
	
	private Integer codigo;
	private String descricao;
	
	private TipoConta(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoConta toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		
		for (TipoConta tc : TipoConta.values()) {
			if (codigo.equals(tc.getCodigo())) {
				return tc;
			}
		}
		
		throw new IllegalArgumentException("Tipo de conta inválido");
	}
}
