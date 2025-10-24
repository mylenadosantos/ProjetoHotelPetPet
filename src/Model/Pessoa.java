package Model;

import Util.Logger; // Importa a classe Logger

public abstract class Pessoa {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Pessoa(String nome, String cpf) {
        
        
        StringBuilder cpfDigitos = new StringBuilder(); 
        
        // Remove caracteres não numéricos do CPF
        for (char c : cpf.toCharArray()) {
            if (Character.isDigit(c)) {
                cpfDigitos.append(c); 
            }
        }
        
        String cpfLimpo = cpfDigitos.toString();
        
        // Validação básica do CPF (apenas contagem de dígitos)
        if (cpfLimpo.length() != 11) {
            // Log de Erro Crítico: Falha na validação do CPF
            Logger.log("ERROR", "Tentativa de criar Pessoa com CPF inválido: " + cpf);
            throw new IllegalArgumentException("CPF inválido: deve conter 11 dígitos numéricos.");
        }
   
        
        this.nome = nome;
        this.cpf = cpfLimpo;
        // Log de Evento: Criação bem-sucedida
        Logger.log("EVENT", "Pessoa criada com sucesso. Nome: " + nome + ", CPF: " + cpfLimpo);
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Retorna uma string com informações detalhadas da pessoa.
     * Deve ser implementado pelas subclasses (Tutor, Funcionario).
     * @return String com as informações
     */
    public abstract String getInfo();
}
