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
        System.out.println("3. Finalizar Hospedagem (Check-out)");
        System.out.println("4. Adicionar Serviços Extras");
        System.out.println("5. Editar/Atualizar Dados da Hospedagem");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
        
        // CORREÇÃO: Bloco try/catch restaurado
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
    
    // Mostra a mensagem pro usuário no console (ex: "Digite a idade:")
    public int pedirInteiro(String mensagem) {
        System.out.print(mensagem);
        try {
        	// Tenta ler o número digitado pelo usuário
            int valor = scanner.nextInt();
            scanner.nextLine();
            return valor;
        } catch (InputMismatchException e) {
            scanner.nextLine();
         // Lança um erro dizendo que o que ele digitou não é um número inteiro
            throw new IllegalArgumentException("Entrada inválida. Por favor, digite um número inteiro.");
        }
    }


   
    // MÉTODO PEDIR DATA CORRIGIDO: Lança exceção para o Controller tratar
    public LocalDate pedirData(String mensagem) {
        System.out.print(mensagem + " (formato DD/MM/AAAA): ");
        String dataStr = scanner.nextLine();
        try {
            // Se a data for válida, retorna.
            return LocalDate.parse(dataStr, DATE_FORMAT);
        } catch (Exception e) {
            // Se falhar (formato errado), LANÇA A EXCEÇÃO.
            throw new IllegalArgumentException("Formato de data inválido. Tente novamente.");
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