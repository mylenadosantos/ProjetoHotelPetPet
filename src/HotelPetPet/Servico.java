package HotelPetPet;

public class Servico {
    private String tipo;
    private String descricao;
    private double valor;
    private Funcionario funcionarioResponsavel;

    public Servico(String tipo, String descricao, double valor, Funcionario funcionarioResponsavel) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.funcionarioResponsavel = funcionarioResponsavel;
    }

    public String getTipo() {
        return tipo;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public Funcionario getFuncionario() {
        return funcionarioResponsavel;
    }

    public void setFuncionario(Funcionario funcionarioResponsavel) {
        this.funcionarioResponsavel = funcionarioResponsavel;
    }
}