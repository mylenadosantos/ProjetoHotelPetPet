package HotelPetPet;

public class Funcionario extends Pessoa {
    private String cargo;
    private double salario;
    private double comissao;

    public Funcionario(String nome, String cpf, String cargo, double salario, double comissao) {
        super(nome, cpf);
        this.cargo = cargo;
        this.salario = salario;
        this.comissao = comissao;
    }

    public String getCargo() {
        return cargo;
    }

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public double getComissao() {
		return comissao;
	}

	public void setComissao(double comissao) {
		this.comissao = comissao;
	}
    
    public void addComissao(double valor) {
        if (valor > 0) {
            this.comissao += valor;
        }
    }

    public double getPgto() {
        double pagamentoTotal = this.salario + this.comissao;
        this.comissao = 0.0;
        return pagamentoTotal;
    }

    @Override
    public String getInfo() {
        return "Informações do Funcionário:\n" +
               "Nome: " + getNome() + "\n" +
               "CPF: " + getCpf() + "\n" +
               "Telefone: " + getTelefone() + "\n" +
               "Email: " + getEmail() + "\n" +
               "Cargo: " + this.cargo + "\n" +
               "Salário: R$" + this.salario;
    }
}