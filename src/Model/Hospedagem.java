package Model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Hospedagem implements ServicosAdd {
    private Pet pet;
    private Tutor tutor;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private List<Servico> servicosExtras;
    private int id;
    private double valorDiaria;

    //Impede a data de saída ser maior que a entrada
    public Hospedagem(Pet pet, Tutor tutor, LocalDate dataEntrada, LocalDate dataSaida) {
        if (dataEntrada.isAfter(dataSaida) || dataEntrada.isEqual(dataSaida)) {
            throw new IllegalArgumentException("A data de entrada deve ser anterior à data de saída.");
        }
        
        this.pet = pet;
        this.tutor = tutor;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.servicosExtras = new ArrayList<>();
    }
    
    //Consta o número de dias entre as datas
    public int getDias() {
        return (int) ChronoUnit.DAYS.between(dataEntrada, dataSaida);
    }

    //Adição e remoção de serviços extra
    @Override
    public void adicionarServico(Servico servico) {
        servicosExtras.add(servico);
    }

    public void removerServicoExtra(Servico servico) {
        servicosExtras.remove(servico);
    }
    
    //Calcula total da estadia
    public double calcularValorTotal() {
        double valorEstadia = this.valorDiaria * getDias();
        
        double valorServicos = 0.0;
        for (Servico s : servicosExtras) {
            valorServicos += s.getValor();
        }
        
        return valorEstadia + valorServicos;
    }

    public List<Servico> getServicosExtras() {
        return servicosExtras;
    }

    public Pet getPet() {
        return pet;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }
    
    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}