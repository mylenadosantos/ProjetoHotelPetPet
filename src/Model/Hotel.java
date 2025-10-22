package Model;

import java.time.LocalDate;
// Importando as classes necessárias para Collections
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap; 

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
    }

    
     //Adiciona um funcionário ao Map usando o CPF como chave.
   
    public void registrarFuncionario(Funcionario funcionario) {
        if (funcionario != null && funcionario.getCpf() != null) {
            this.funcionarios.put(funcionario.getCpf(), funcionario);
        }
    }

    
     //Busca um funcionário de forma rápida usando o Map (chave: CPF).
  
    public Funcionario getFuncionario(String cpf) {
        return this.funcionarios.get(cpf);
    }

    public Hospedagem criarHospedagem(Pet pet, Tutor tutor, LocalDate dataEntrada, LocalDate dataSaida) {
        Hospedagem novaHospedagem = new Hospedagem(pet, tutor, dataEntrada, dataSaida);
        this.hospedagensAtivas.add(novaHospedagem);
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

    public void registrarHospedagem(Hospedagem hospedagem) {
        this.hospedagensAtivas.add(hospedagem);
    }
    public boolean finalizarHospedagem(int idHospedagem) {
        Hospedagem hospedagemParaRemover = null;
        // Percorre a lista para encontrar a hospedagem pelo ID
        for (Hospedagem h : hospedagensAtivas) {
            if (h.getId() == idHospedagem) {
                hospedagemParaRemover = h;
                break;
            }
        }
        
        if (hospedagemParaRemover != null) {
            // Remove a hospedagem ativa
            return hospedagensAtivas.remove(hospedagemParaRemover);
            // Em um sistema real, aqui você moveria ela para uma lista de 'hospedagensFinalizadas'
        }
        return false;
    }
}
