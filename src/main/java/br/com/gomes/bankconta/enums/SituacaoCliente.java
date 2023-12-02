package br.com.gomes.bankconta.enums;

public enum SituacaoCliente {

	ATIVO(0, "ATIVO"), 
	EXCLUIDO(1, "EXCLUIDO"),
	INATIVO(2, "INATIVO");
	
	private Integer codigo;
	private String descricao;
	
	private SituacaoCliente(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static SituacaoCliente toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		
		for (SituacaoCliente tc : SituacaoCliente.values()) {
			if (codigo.equals(tc.getCodigo())) {
				return tc;
			}
		}
		
		throw new IllegalArgumentException("Situação inválida");
	}
}
