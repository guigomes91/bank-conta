package br.com.gomes.bankconta.enums;

public enum SituacaoConta {

	EXCLUIDO(0, "EXCLUIDA"), 
	ATIVO(1, "ATIVA"),
	BLOQUEADA(2, "BLOQUEADA");
	
	private Integer codigo;
	private String descricao;
	
	private SituacaoConta(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static SituacaoConta toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		
		for (SituacaoConta situacaoConta : SituacaoConta.values()) {
			if (codigo.equals(situacaoConta.getCodigo())) {
				return situacaoConta;
			}
		}
		
		throw new IllegalArgumentException("Situação conta inválida");
	}
}
