package br.com.gomes.bankconta.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.gomes.bankconta.entities.ClienteEntity;
import br.com.gomes.bankconta.enums.Perfil;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClienteDTO {

	private UUID id;
	
	@NotBlank(message = "Campo nome é requerido")
	@Length(min = 3, max = 60)
	private String nome;
	
	@Min(value = 11, message = "Valor minimo para o cpf é 11")
	private long cpf;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascimento;
	
	@NotBlank(message = "Campo email é requerido")
	@Column(unique = true)
	private String email;
	
	@NotBlank(message = "Campo endereco é requerido")
	private String endereco;
	
	@NotBlank(message = "Campo bairro é requerido")
	private String bairro;
	
	@NotBlank(message = "Campo cidade é requerido")
	private String cidade;
	
	@Min(value = 8, message = "Valor permitido de 8 digitos")
	private int cep;
	
	@NotBlank(message = "Campo estado é requerido")
	@Length(min = 2, max = 2, message = "Permitido 2 caracteres")
	private String estado;
	
	@NotBlank(message = "Campo telefone é requerido")
	private String telefone;
	
	@NotBlank(message = "Campo senha é requerido")
	private String senha;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	@Builder.Default
	protected Set<Integer> perfis = new HashSet<>();
	
	public ClienteDTO() {
		addPerfil(Perfil.CLIENTE);
	}

	public ClienteDTO(ClienteEntity obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.bairro = obj.getBairro();
		this.endereco = obj.getEndereco();
		this.cidade = obj.getCidade();
		this.cep = obj.getCep();
		this.dataNascimento = obj.getDataNascimento();
		this.estado = obj.getEstado();
		this.perfis = obj.getPerfis().stream().map(p -> p.getCodigo()).collect(Collectors.toSet());
		addPerfil(Perfil.CLIENTE);
	}
	
	public List<ClienteDTO> entityToListDTO(List<ClienteEntity> clientes) {
		return clientes.stream().map(entity -> new ClienteDTO(entity)).collect(Collectors.toList());
	}
	
	public Set<Perfil> getPerfis() {
		return perfis.stream().map(p -> Perfil.toEnum(p)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}
}
