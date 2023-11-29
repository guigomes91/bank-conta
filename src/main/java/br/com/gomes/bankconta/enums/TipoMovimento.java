package br.com.gomes.bankconta.enums;

public enum TipoMovimento {

	DEBITO(0, "DEBITO_EM_CONTA"), 
	CREDITO(1, "CREDITO_EM_CONTA");
	
	private Integer codigo;
	private String descricao;
	
	private TipoMovimento(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoMovimento toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		
		for (TipoMovimento tm : TipoMovimento.values()) {
			if (codigo.equals(tm.getCodigo())) {
				return tm;
			}
		}
		
		throw new IllegalArgumentException("Tipo de conta inválido");
	}
}
