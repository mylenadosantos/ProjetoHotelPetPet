package Controller; // C maiúsculo

import Model.*; // M maiúsculo para importar todas as classes do Model
import View.HotelView; // V maiúsculo para importar a View
import java.time.LocalDate;

public class HotelController {
    private Hotel hotel; // A referência ao Model principal
    private HotelView view; // A referência à View

    /**
     * Construtor: Inicializa o Model e recebe a View.
     */
    public HotelController(HotelView view) {
        // 1. Inicializa o Model
        this.hotel = new Hotel("Hotel PetPet Central");
        this.view = view;
        // 2. Popula alguns dados iniciais para teste
        popularDadosIniciais();
    }
    
    /**
     * Popula o hotel com serviços e funcionários para que o registro funcione.
     */
    private void popularDadosIniciais() {
        Funcionario func1 = new Funcionario("João Silva", "111.111.111-11", "Recepcionista", 2500.0, 0.0);
        // Cria um serviço no Model
        Servico banho = new Servico("Banho", "Banho completo com tosa", 50.0, func1);
        // Adiciona o serviço ao Hotel (Model)
        hotel.adicionarServicoDisponivel(banho);
    }

    /**
     * Ponto de entrada do Controller, contém o loop principal da aplicação.
     */
    public void iniciarAplicacao() {
        this.view.exibirMensagemBoasVindas();
        int opcao = -1;

        while (opcao != 3) {
            // O Controller pede para a View mostrar o menu e retorna a opção
            opcao = this.view.pedirOpcaoMenu();

            switch (opcao) {
                case 1:
                    registrarNovaHospedagem(); // Ação principal
                    break;
                case 2:
                    exibirHospedagensAtivas();
                    break;
                case 3:
                    this.view.exibirMensagem("Obrigado por usar o sistema. Até logo!");
                    break;
                default:
                    if (opcao != -1) { 
                        this.view.exibirErro("Opção inválida.");
                    }
                    break;
            }
        }
        this.view.fechar();
    }
    
    /**
     * Lógica de coordenação para registrar uma hospedagem.
     */
    private void registrarNovaHospedagem() {
        this.view.exibirMensagem("\n--- REGISTRAR NOVA HOSPEDAGEM ---");
        
        // 1. Controller pede dados puros para a View
        String nomeTutor = this.view.pedirString("Nome do Tutor: ");
        String cpfTutor = this.view.pedirString("CPF do Tutor: ");
        // 2. Controller cria o objeto Model
        Tutor tutor = new Tutor(nomeTutor, cpfTutor); 

        // 3. Coleta de dados do Pet
        String nomePet = this.view.pedirString("Nome do Pet: ");
        int idadePet = this.view.pedirInteiro("Idade do Pet: ");
        String tipoPet = this.view.pedirString("Tipo do Pet (Cachorro/Gato): ").toLowerCase();
        
        Pet pet;
        if (tipoPet.equals("cachorro")) {
            pet = new Cachorro(nomePet, idadePet);
        } else if (tipoPet.equals("gato")) {
            pet = new Gato(nomePet, idadePet);
        } else {
            this.view.exibirErro("Tipo de pet inválido. Abortando registro.");
            return;
        }

        // 4. Coleta e validação de datas
        LocalDate entrada = null;
        LocalDate saida = null;
        while (entrada == null || saida == null || !entrada.isBefore(saida)) {
            entrada = this.view.pedirData("Data de Entrada");
            saida = this.view.pedirData("Data de Saída");

            if (entrada != null && saida != null && !entrada.isBefore(saida)) {
                this.view.exibirErro("A data de saída deve ser posterior à data de entrada.");
            }
        }
        
        // 5. Controller usa o Model para criar a Hospedagem
        Hospedagem novaHospedagem = this.hotel.criarHospedagem(pet, tutor, entrada, saida);
        novaHospedagem.setValorDiaria(65.0); // Diária fixa para teste
        novaHospedagem.setId(this.hotel.getHospedagensAtivas().size());

        // 6. Adiciona serviço (simples)
        if (!this.hotel.getServicosDisponiveis().isEmpty()) {
             Servico servicoBanho = this.hotel.getServicosDisponiveis().get(0);
             // Pede para a View exibir o catálogo ANTES de perguntar (boa prática)
             this.view.exibirListaServicosDisponiveis(this.hotel.getServicosDisponiveis());
             String querServico = this.view.pedirString("Deseja adicionar o serviço 'Banho'? (s/n): ").toLowerCase();
             
             if (querServico.equals("s")) {
                 novaHospedagem.adicionarServico(servicoBanho);
                 this.view.exibirMensagem("Serviço de Banho adicionado!");
             }
        }

        // 7. Controller pede para a View exibir o resultado do Model
        this.view.exibirRelatorioHospedagem(novaHospedagem);
    }
    
    private void exibirHospedagensAtivas() {
        if (this.hotel.getHospedagensAtivas().isEmpty()) {
            this.view.exibirMensagem("\nNão há hospedagens ativas no momento.");
        } else {
            this.view.exibirMensagem("\n--- LISTA DE HOSPEDAGENS ATIVAS ---");
            for (Hospedagem h : this.hotel.getHospedagensAtivas()) {
                this.view.exibirRelatorioHospedagem(h);
            }
        }
    }
}