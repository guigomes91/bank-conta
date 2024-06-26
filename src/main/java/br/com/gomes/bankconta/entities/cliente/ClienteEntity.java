package br.com.gomes.bankconta.entities.cliente;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.gomes.bankconta.auditoria.Auditavel;
import br.com.gomes.bankconta.auditoria.listener.AuditoriaListener;
import br.com.gomes.bankconta.auditoria.model.Auditoria;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import br.com.gomes.bankconta.dto.cliente.ClienteDTO;
import br.com.gomes.bankconta.enums.Perfil;
import br.com.gomes.bankconta.enums.SituacaoCliente;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditoriaListener.class)
@AllArgsConstructor
@Table(name = "cliente")
public class ClienteEntity implements Auditavel {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@NotBlank
	@Length(min = 3, max = 60)
	private String nome;
	
	@Column(unique = true)
	private long cpf;

	@JsonIgnore
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;
	
	@Column(unique = true)
	private String email;
	
	private String endereco;
	
	private String bairro;
	
	private String cidade;
	
	private int cep;
	
	private String estado;
	
	private String telefone;
	
	private String senha;
	
	private SituacaoCliente situacao;

    private Date dataCriacao;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	protected Set<Integer> perfis = new HashSet<>();

	@Embedded
	private Auditoria auditoria;
	
	public ClienteEntity() {
		addPerfil(Perfil.CLIENTE);
	}
	
	public ClienteEntity(ClienteDTO dto) {
		this.id = dto.getId();
		this.nome = dto.getNome();
		this.cpf = dto.getCpf();
		this.dataNascimento = dto.getDataNascimento();
		this.email = dto.getEmail();
		this.endereco = dto.getEndereco();
		this.bairro = dto.getBairro();
		this.cidade = dto.getCidade();
		this.cep = dto.getCep();
		this.estado = dto.getEstado();
		this.telefone = dto.getTelefone();
		this.senha = dto.getSenha();
		this.situacao = dto.getSituacao();
		this.dataCriacao = dto.getDataCriacao();
		this.perfis = dto.getPerfis().stream().map(p -> p.getCodigo()).collect(Collectors.toSet());
	}
	
	public Set<Perfil> getPerfis() {
		return perfis.stream().map(p -> Perfil.toEnum(p)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCodigo());
	}

	@Override
	public Auditoria getAudit() {
		return this.auditoria;
	}

	@Override
	public void setAudit(Auditoria auditoria) {
		this.auditoria = auditoria;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cpf, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteEntity other = (ClienteEntity) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(id, other.id);
	}
}
