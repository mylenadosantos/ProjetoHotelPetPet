package HotelPetPet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String nome;
    private List<Hospedagem> hospedagensAtivas;
    private List<Servico> servicosDisponiveis;
    private String endereco;
    private String telefone;

    public Hotel(String nome) {
        this.nome = nome;
        this.hospedagensAtivas = new ArrayList<>();
        this.servicosDisponiveis = new ArrayList<>();
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
}