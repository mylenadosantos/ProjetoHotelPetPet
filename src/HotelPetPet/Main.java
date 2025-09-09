package HotelPetPet;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            Hotel hotel = new Hotel("Pet Hotel Feliz");
            
            Funcionario funcBanho = new Funcionario("João", "999.888.777-66", "Esteticista", 2000.0, 0.0);
            Funcionario funcGeral = new Funcionario("Maria", "111.222.333-44", "Gerente", 3500.0, 0.0);
            
            // --- PRIMEIRA HOSPEDAGEM ---
            Tutor tutor1 = new Tutor("Ana", "123.456.789-00");
            tutor1.setTelefone("41 99876-5432");
            tutor1.setEmail("ana@email.com");
            
            Pet cachorro1 = new Cachorro("Rex", 3);
            cachorro1.setRaca("Golden Retriever");
            cachorro1.setDieta("Ração especial para cães adultos");
            cachorro1.setVacinacao(true);
            cachorro1.setCooperativo(true);
            tutor1.adicionarPet(cachorro1);

            LocalDate dataEntrada1 = LocalDate.of(2025, 10, 1);
            LocalDate dataSaida1 = LocalDate.of(2025, 10, 5);
            Hospedagem hospedagem1 = new Hospedagem(cachorro1, tutor1, dataEntrada1, dataSaida1);
            hospedagem1.setValorDiaria(50.0);

            Servico servicoBanho = new Servico("Banho", "Banho completo", 35.0, funcBanho);
            hospedagem1.adicionarServico(servicoBanho);

            hotel.registrarHospedagem(hospedagem1);

            System.out.println("--- DADOS DA PRIMEIRA HOSPEDAGEM ---");
            System.out.println(tutor1.getInfo());
            System.out.println("--------------------");
            System.out.println(cachorro1.getInfo());
            System.out.println("--------------------");
            System.out.println("Dias de hospedagem: " + hospedagem1.getDias());
            System.out.println("Valor total: R$" + hospedagem1.calcularValorTotal());
            
            System.out.println("\n---------------------\n");

            // --- SEGUNDA HOSPEDAGEM ---
            Tutor tutor2 = new Tutor("João", "987.654.321-11");
            tutor2.setTelefone("41 99123-4567");
            tutor2.setEmail("joao@email.com");
            
            Pet gato1 = new Gato("Miau", 2);
            gato1.setRaca("Persa");
            gato1.setDieta("Ração para gatos castrados");
            gato1.setVacinacao(true);
            gato1.setCooperativo(false);
            tutor2.adicionarPet(gato1);

            LocalDate dataEntrada2 = LocalDate.of(2025, 11, 10);
            LocalDate dataSaida2 = LocalDate.of(2025, 11, 20);
            Hospedagem hospedagem2 = new Hospedagem(gato1, tutor2, dataEntrada2, dataSaida2);
            hospedagem2.setValorDiaria(40.0);
            
            Servico servicoTosa = new Servico("Tosa", "Tosa especial", 50.0, funcBanho);
            hospedagem2.adicionarServico(servicoTosa);
            
            hotel.registrarHospedagem(hospedagem2);

            System.out.println("--- DADOS DA SEGUNDA HOSPEDAGEM ---");
            System.out.println(tutor2.getInfo());
            System.out.println("--------------------");
            System.out.println(gato1.getInfo());
            System.out.println("--------------------");
            System.out.println("Dias de hospedagem: " + hospedagem2.getDias());
            System.out.println("Valor total: R$" + hospedagem2.calcularValorTotal());


        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}