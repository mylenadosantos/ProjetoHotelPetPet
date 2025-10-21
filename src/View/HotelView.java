package View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import Model.Hospedagem; 
import Model.Servico; 



public class HotelView {
    
    private Scanner scanner;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public HotelView() {
        this.scanner = new Scanner(System.in);
    }

  
    public int pedirOpcaoMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Registrar nova Hospedagem");
        System.out.println("2. Ver Hospedagens Ativas");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); 
            return opcao;
        } catch (InputMismatchException e) {
            scanner.nextLine(); 
            exibirErro("Entrada inválida. Por favor, digite um número.");
            return -1; 
        }
    }

   
    public String pedirString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }
    
  
    public int pedirInteiro(String mensagem) {
        System.out.print(mensagem);
        try {
            int valor = scanner.nextInt();
            scanner.nextLine();
            return valor;
        } catch (InputMismatchException e) {
            scanner.nextLine(); 
            exibirErro("Entrada inválida. Por favor, digite um número inteiro.");
            return -1;
        }
    }

   
    public LocalDate pedirData(String mensagem) {
        System.out.print(mensagem + " (formato DD/MM/AAAA): ");
        String dataStr = scanner.nextLine();
        try {
            return LocalDate.parse(dataStr, DATE_FORMAT);
        } catch (Exception e) {
            exibirErro("Formato de data inválido. Tente novamente.");
            return null;
        }
    }


   
    public void exibirMensagemBoasVindas() {
        System.out.println("=====================================");
        System.out.println("| BEM-VINDO AO GESTOR HOTEL PETPET |");
        System.out.println("=====================================");
    }

    
    public void exibirRelatorioHospedagem(Hospedagem h) { 
        System.out.println("\n--- DETALHES DA HOSPEDAGEM ---");
        System.out.println("ID: " + h.getId());
        System.out.println("Tutor: " + h.getTutor().getNome());
        System.out.println("Pet: " + h.getPet().getNome());
        System.out.println("Período: " + h.getDataEntrada().format(DATE_FORMAT) + " a " + h.getDataSaida().format(DATE_FORMAT));
        System.out.println("Dias: " + h.getDias());

        System.out.println("\nServiços Contratados:");
        if (h.getServicosExtras().isEmpty()) {
            System.out.println("Nenhum serviço extra.");
        } else {
            for (Servico s : h.getServicosExtras()) {
                System.out.printf("- %s (R$%.2f)\n", s.getDescricao(), s.getValor());
            }
        }

        System.out.printf("\nVALOR TOTAL A PAGAR: R$%.2f\n", h.calcularValorTotal());
        System.out.println("-------------------------------------");
    }
    
  
    public void exibirListaServicosDisponiveis(List<Servico> servicos) {
        System.out.println("\n--- CATÁLOGO DE SERVIÇOS DO HOTEL ---");
        for (int i = 0; i < servicos.size(); i++) {
            Servico s = servicos.get(i);
            System.out.printf("%d. %s (R$%.2f)\n", (i + 1), s.getTipo(), s.getValor());
        }
        System.out.println("-------------------------------------");
    }

    
    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

  
    public void exibirErro(String mensagem) {
        System.err.println("\n--- ERRO ---");
        System.err.println("Ocorreu um erro: " + mensagem);
        System.err.println("------------------");
    }

   
    public void fechar() {
        if (scanner != null) {
            scanner.close();
        }
    }
}