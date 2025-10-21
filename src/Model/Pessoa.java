package Model;

public abstract class Pessoa {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Pessoa(String nome, String cpf) {
        String cpfLimpo = cpf.replace(".", "").replace("-", ""); 
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

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    public abstract String getInfo();
}