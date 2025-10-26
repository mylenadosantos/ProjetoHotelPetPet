package Model;

import java.util.ArrayList;
import java.util.List;

public class Tutor extends Pessoa {
    private List<Pet> pets;

    public Tutor(String nome, String cpf) {
        super(nome, cpf);
        this.pets = new ArrayList<>();
    }
    
    // Você pode usar este construtor para criar o Tutor já com email e telefone:
    public Tutor(String nome, String cpf, String telefone, String email) {
        super(nome, cpf);
        setTelefone(telefone); // Usa o setter validado da classe Pessoa
        setEmail(email);       // Usa o setter validado da classe Pessoa
        this.pets = new ArrayList<>();
    }


    public void adicionarPet(Pet pet) {
        this.pets.add(pet);
    }

    public List<Pet> getPets() {
        return pets;
    }
    
    @Override
    public String getInfo() {
        return "Informações do Tutor:\n" +
               "Nome: " + getNome() + "\n" +
               "CPF: " + getCpf() + "\n" +
               "Telefone: " + getTelefone() + "\n" +
               "Email: " + getEmail();
    }
}