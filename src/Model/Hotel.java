package Model;

import Util.Logger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap; 

	//Classe principal: gerencia e armazena listas importantes e regras de negócios (criar,
	//buscar, etc)
public class Hotel {
    private String nome;
    private List<Hospedagem> hospedagensAtivas;
    private List<Servico> servicosDisponiveis;
    private Map<String, Funcionario> funcionarios; 
    private String endereco;
    private String telefone;

    public Hotel(String nome) {
        this.nome = nome;
        this.hospedagensAtivas = new ArrayList<>();
        this.servicosDisponiveis = new ArrayList<>();
        this.funcionarios = new HashMap<>(); // Inicialização do Map
        Logger.log("INFO", "Hotel '" + nome + "' inicializado.");
    }

    
     //Adiciona um funcionário ao Map usando o CPF como chave.
   
    public void registrarFuncionario(Funcionario funcionario) {
        if (funcionario != null && funcionario.getCpf() != null) {
            this.funcionarios.put(funcionario.getCpf(), funcionario);
            Logger.log("EVENT", "Funcionário registrado: " + funcionario.getNome());
        }
    }

    
     //Busca um funcionário de forma rápida usando o Map (chave: CPF).
  
    public Funcionario getFuncionario(String cpf) {
        return this.funcionarios.get(cpf);
    }

    public Hospedagem criarHospedagem(Pet pet, Tutor tutor, LocalDate dataEntrada, LocalDate dataSaida) {
        if (dataEntrada.isAfter(dataSaida) || dataEntrada.isEqual(dataSaida)) {
            Logger.log("WARN", "Tentativa de criar Hospedagem com datas inválidas: " + dataEntrada + " a " + dataSaida);
            throw new IllegalArgumentException("A data de entrada deve ser anterior à data de saída.");
        }
        
        Hospedagem novaHospedagem = new Hospedagem(pet, tutor, dataEntrada, dataSaida);
        this.hospedagensAtivas.add(novaHospedagem);
        
        Logger.log("EVENT", "Nova hospedagem criada. Pet: " + pet.getNome() + ", Tutor: " + tutor.getNome());
        
        return novaHospedagem;
    }

    public void adicionarServicoDisponivel(Servico servico) {
        this.servicosDisponiveis.add(servico);
    }
    
    public List<Servico> getServicosDisponiveis() {
        return this.servicosDisponiveis;
    }

    public List<Hospedagem> getHospedagensAtivas() {
        return this.hospedagensAtivas;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    //Adiciona hospedagem a lista ativa

    public void registrarHospedagem(Hospedagem hospedagem) {
        this.hospedagensAtivas.add(hospedagem);
    }
    
    
    public boolean finalizarHospedagem(int idHospedagem) {
        Hospedagem hospedagemParaRemover = null;
      //Encontra a hospedagem pelo ID para remover
        	for (Hospedagem h : hospedagensAtivas) {
            if (h.getId() == idHospedagem) {
                hospedagemParaRemover = h;
                break;
            }
        }
        
        if (hospedagemParaRemover != null) {
            // Remove a hospedagem ativa
            boolean sucesso = hospedagensAtivas.remove(hospedagemParaRemover);
            if (sucesso) {
                Logger.log("EVENT", "Check-out concluído para ID: " + idHospedagem + ". Pet: " + hospedagemParaRemover.getPet().getNome());
            }
            return sucesso;
        }
        
        // Retorna erro quando há tentativa de finalizar hospedagem inexistente
        Logger.log("WARN", "Tentativa de finalizar hospedagem com ID não encontrado: " + idHospedagem);
        return false;
    }
}
