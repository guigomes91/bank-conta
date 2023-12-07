package br.com.gomes.bankconta.dto.cliente;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.gomes.bankconta.entities.cliente.ClienteEntity;
import br.com.gomes.bankconta.enums.Perfil;
import br.com.gomes.bankconta.enums.SituacaoCliente;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	
	private SituacaoCliente situacao;
	
    private Date dataCriacao = new Date();
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	protected Set<Integer> perfis = new HashSet<>();
	
	public ClienteDTO() {
		addPerfil(Perfil.CLIENTE);
	}

	public ClienteDTO(ClienteEntity clienteEntity) {
		this.id = clienteEntity.getId();
		this.nome = clienteEntity.getNome();
		this.cpf = clienteEntity.getCpf();
		this.email = clienteEntity.getEmail();
		this.senha = clienteEntity.getSenha();
		this.bairro = clienteEntity.getBairro();
		this.endereco = clienteEntity.getEndereco();
		this.cidade = clienteEntity.getCidade();
		this.cep = clienteEntity.getCep();
		this.dataNascimento = clienteEntity.getDataNascimento();
		this.estado = clienteEntity.getEstado();
		this.situacao = SituacaoCliente.toEnum(clienteEntity.getSituacao().getCodigo());
		this.telefone = clienteEntity.getTelefone();
		this.perfis = clienteEntity.getPerfis().stream().map(p -> p.getCodigo()).collect(Collectors.toSet());
		this.dataCriacao = clienteEntity.getDataCriacao();
		addPerfil(Perfil.CLIENTE);
	}
	
	public List<ClienteDTO> entityToListDTO(List<ClienteEntity> clientes) {
		return clientes.stream().map(entity -> new ClienteDTO(entity)).collect(Collectors.toList());
	}
	
	public Page<ClienteDTO> entityPageToDTO(Page<ClienteEntity> pageEntity) {
		Page<ClienteDTO> clienteDTO = pageEntity.map(entity -> {
			ClienteDTO dto = new ClienteDTO(entity);
	        return dto;
	    });
		
		return clienteDTO;
	}
	
	public Set<Perfil> getPerfis() {
		return perfis.stream().map(p -> Perfil.toEnum(p)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}
}
