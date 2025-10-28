package Model;

import java.util.ArrayList;
import java.util.List;

public class Tutor extends Pessoa {
    private List<Pet> pets;

    public Tutor(String nome, String cpf) {
        super(nome, cpf);
        this.pets = new ArrayList<>();
    }
    
    public Tutor(String nome, String cpf, String telefone, String email) {
        super(nome, cpf);
        setTelefone(telefone); 
        setEmail(email);
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