package Model;

public abstract class Pessoa {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Pessoa(String nome, String cpf) {
        
        // Lógica de limpeza do CPF (já existente)
        StringBuilder cpfDigitos = new StringBuilder(); 
        for (char c : cpf.toCharArray()) {
            if (Character.isDigit(c)) {
                cpfDigitos.append(c); 
            }
        }
        
        String cpfLimpo = cpfDigitos.toString();
        
        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException("CPF inválido: deve conter 11 dígitos numéricos.");
        }
   
        
        this.nome = nome;
        this.cpf = cpfLimpo; 
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

	// NOVO: Validação do Telefone (11 dígitos numéricos)
	public void setTelefone(String telefone) {
	    // Remove quaisquer espaços, parênteses ou traços para a validação
	    String telefoneLimpo = telefone.replaceAll("[^0-9]", "");
	    
	    if (telefoneLimpo.length() != 11) {
	        throw new IllegalArgumentException("Telefone inválido: deve conter exatamente 11 dígitos numéricos (com DDD).");
	    }
	    
		this.telefone = telefoneLimpo;
	}

	public String getEmail() {
		return email;
	}

	// NOVO: Validação do Email (formato básico)
	public void setEmail(String email) {
	    // Expressão regular básica para formato de e-mail (ex: algo@dominio.com)
	    if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$")) {
	        throw new IllegalArgumentException("E-mail inválido: utilize o formato padrao (ex: nome@dominio.com).");
	    }
	    
		this.email = email;
	}
    
    public abstract String getInfo();
}