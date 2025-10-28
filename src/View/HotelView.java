package View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import Model.Hospedagem; 
import Model.Servico; 

//Responsável pela interação com o usuário

public class HotelView {
    
    private Scanner scanner;
    // Define um formatador padrão para garantir que as datas sejam lidas e exibidas consistentemente.
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public HotelView() {
        this.scanner = new Scanner(System.in);
    }

  
   //Exibe o menu principal para o usuario
    public int pedirOpcaoMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Registrar nova Hospedagem");
        System.out.println("2. Ver Hospedagens Ativas");
        System.out.println("3. Finalizar Hospedagem (Check-out)");
        System.out.println("4. Adicionar Serviços Extras");
        System.out.println("5. Editar/Atualizar Dados da Hospedagem");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
        
        try { 
            int opcao = scanner.nextInt();
            scanner.nextLine(); 
            return opcao;
        } catch (InputMismatchException e) {
            scanner.nextLine(); 
            exibirErro("Entrada inválida. Por favor, digite um número.");
            return -1; // Retorna um valor inválido para o Controller tratar
        }
    }

   
    public String pedirString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }
    
    
     // Solicita um número inteiro ao usuário.
     // Se o usuário digitar algo que não seja um inteiro, este método
     // lança uma exceção para ser tratada pelo Controller.
    
    public int pedirInteiro(String mensagem) {
        System.out.print(mensagem);
        try {
            int valor = scanner.nextInt();
            scanner.nextLine(); 
            return valor;
        } catch (InputMismatchException e) {
            scanner.nextLine(); 
            throw new IllegalArgumentException("Entrada inválida. Por favor, digite um número inteiro.");
        }
    }


   
  
    // Solicita uma data ao usuário no formato DD/MM/AAAA.
    // Se o formato estiver incorreto, lança uma exceção para ser tratada pelo Controller.
    
    public LocalDate pedirData(String mensagem) {
        System.out.print(mensagem + " (formato DD/MM/AAAA): ");
        String dataStr = scanner.nextLine();
        try {
            return LocalDate.parse(dataStr, DATE_FORMAT);
        } catch (Exception e) {
            // Retorna o  erro de formato para o Controller
            throw new IllegalArgumentException("Formato de data inválido. Tente novamente.");
        }
    }


   
    public void exibirMensagemBoasVindas() {
        System.out.println("=====================================");
        System.out.println("| BEM-VINDO AO GESTOR HOTEL PETPET |");
        System.out.println("=====================================");
    }

    
    
     // Formata e exibe os dados da Hospedagem
     
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

        // Chama o método de calcular da Model
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

  
    
   //  Centraliza a exibição de mensagens de erro.

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