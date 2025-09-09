package HotelPetPet;

public abstract class Pet {
    private String nome;
    private int idade;
    private String raca;
    private Tutor tutor;
    private boolean vacinacao;
    private String dieta;
    private boolean cooperativo;
    

    public Pet(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public abstract void emitirSom();

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public Tutor getTutor() {
		return tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	public boolean isVacinacao() {
		return vacinacao;
	}

	public void setVacinacao(boolean vacinacao) {
		this.vacinacao = vacinacao;
	}

	public String getDieta() {
		return dieta;
	}

	public void setDieta(String dieta) {
		this.dieta = dieta;
	}

	public boolean isCooperativo() {
		return cooperativo;
	}

	public void setCooperativo(boolean cooperativo) {
		this.cooperativo = cooperativo;
	}
    
    public String getInfo() {
        String statusVacinacao = this.vacinacao ? "Sim" : "Não";
        String statusCooperativo = this.cooperativo ? "Sim" : "Não";

        return "Nome: " + this.nome + "\n" +
               "Idade: " + this.idade + "\n" +
               "Raça: " + this.raca + "\n" +
               "Dieta: " + this.dieta + "\n" +
               "Vacinado: " + statusVacinacao + "\n" +
               "Cooperativo: " + statusCooperativo;
    }
}