package Controller; 

import Model.*; 
import View.HotelView; 
import java.time.LocalDate;

public class HotelController {
    private Hotel hotel;
    private HotelView view; 

    public HotelController(HotelView view) {
        this.hotel = new Hotel("Hotel PetPet Central");
        this.view = view;
   
        // Inicializa o catálogo de serviços adicionais
        Funcionario funcBanho = new Funcionario("João", "99988877766", "Esteticista", 2000.0, 0.0);
        Funcionario funcTosa = new Funcionario("Maria", "11122233344", "Tosador", 3500.0, 0.0);
        Funcionario funcVet = new Funcionario("Graça", "89034567843", "Veterinária", 4500.0, 0.0);
        Funcionario funcTreinador = new Funcionario("Pedro", "44455566677", "Treinador", 3000.0, 0.0);
        
        Servico servicoBanhoDisponivel = new Servico("Banho", "Banho completo", 35.0, funcBanho);
        Servico servicoTosaDisponivel = new Servico("Tosa", "Tosa higiênica", 50.0, funcTosa);
        Servico servicoConsultaDisponivel = new Servico("Consulta", "Consulta de check-in", 60.0, funcVet);
        Servico servicoAdestramento = new Servico("Treinamento", "Sessão de adestramento básica", 75.0, funcTreinador);
        Servico servicoPasseio = new Servico("Passeio", "Passeio extra de 30 minutos", 20.0, funcTreinador);

        hotel.adicionarServicoDisponivel(servicoBanhoDisponivel);
        hotel.adicionarServicoDisponivel(servicoTosaDisponivel);
        hotel.adicionarServicoDisponivel(servicoConsultaDisponivel);
        hotel.adicionarServicoDisponivel(servicoAdestramento);
        hotel.adicionarServicoDisponivel(servicoPasseio);
    }

    public void iniciarAplicacao() {
        this.view.exibirMensagemBoasVindas();
        int opcao = -1;

        while (opcao != 3) {
            opcao = this.view.pedirOpcaoMenu();

            switch (opcao) {
                case 1:
                    registrarNovaHospedagem();
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

    private void registrarNovaHospedagem() {
        this.view.exibirMensagem("\n--- REGISTRAR NOVA HOSPEDAGEM ---");

        // 1. Coleta e VALIDAÇÃO do Tutor (CPF isolado)
        String nomeTutor = this.view.pedirString("Nome do Tutor: "); 
        Tutor tutor = null;
        
        while (tutor == null) { 
            try {
                String cpfTutor = this.view.pedirString("CPF do Tutor (apenas números, 11 dígitos): ");
                tutor = new Tutor(nomeTutor, cpfTutor); 
            } catch (IllegalArgumentException e) { 
                this.view.exibirErro(e.getMessage()); 
            }
        }


        // =======================================================
        // 2. Coleta de dados do Pet (AGORA COM SELEÇÃO NUMÉRICA)
        // =======================================================
        String nomePet = this.view.pedirString("Nome do Pet: ");
        int idadePet = this.view.pedirInteiro("Idade do Pet: ");
        
        int tipoPetOpcao = -1;
        
        // Loop para garantir que o usuário digite 1 (Cachorro) ou 2 (Gato)
        while (tipoPetOpcao != 1 && tipoPetOpcao != 2) {
            this.view.exibirMensagem("Tipos de Pet disponíveis: 1. Cachorro, 2. Gato");
            tipoPetOpcao = this.view.pedirInteiro("Digite o número do tipo do Pet: ");
            
            if (tipoPetOpcao != 1 && tipoPetOpcao != 2) {
                this.view.exibirErro("Opção de tipo de pet inválida. Digite 1 ou 2.");
            }
        }
        
        Pet pet;
        if (tipoPetOpcao == 1) { // 1 = Cachorro
            pet = new Cachorro(nomePet, idadePet);
        } else { // 2 = Gato
            pet = new Gato(nomePet, idadePet);
        }
        // =======================================================


        // 3. PERGUNTAS AO PET
        String racaPet = this.view.pedirString("Raça do Pet: ");
        pet.setRaca(racaPet);
        
        String temDieta = this.view.pedirString("O Pet possui Dieta Específica (s/n)? ").toLowerCase();
        if (temDieta.equals("s")) {
            String dieta = this.view.pedirString("Descreva a Dieta Específica: ");
            pet.setDieta(dieta);
        } else {
            pet.setDieta("Padrão do Hotel"); 
        }
        
        String temRemedios = this.view.pedirString("O Pet precisa de Remédios/Medicação (s/n)? ").toLowerCase();
        if (temRemedios.equals("s")) {
            String remedios = this.view.pedirString("Descreva Remédios e Dosagens: ");
            pet.setRemedios(remedios);
        } else {
            pet.setRemedios("Nenhum"); 
        }
        
        String estaVacinado = this.view.pedirString("O Pet está com a vacinação em dia (s/n)? ").toLowerCase();
        pet.setVacinacao(estaVacinado.equals("s"));
        
        String eCooperativo = this.view.pedirString("O Pet é cooperativo/dócil (s/n)? ").toLowerCase();
        pet.setCooperativo(eCooperativo.equals("s"));


        // 4. Coleta e validação de datas
        LocalDate entrada = null;
        LocalDate saida = null;
        while (entrada == null || saida == null || (entrada != null && saida != null && !entrada.isBefore(saida))) {
            
            // Loop de validação de FORMATO para Data de Entrada
            LocalDate tempEntrada = null;
            while(tempEntrada == null) {
                try {
                    tempEntrada = this.view.pedirData("Data de Entrada");
                } catch (IllegalArgumentException e) {
                    this.view.exibirErro(e.getMessage());
                }
            }
            entrada = tempEntrada; 
            
            // Loop de validação de FORMATO para Data de Saída
            LocalDate tempSaida = null;
            while(tempSaida == null) {
                 try {
                    tempSaida = this.view.pedirData("Data de Saída");
                } catch (IllegalArgumentException e) {
                    this.view.exibirErro(e.getMessage());
                }
            }
            saida = tempSaida; 
            
            // Verifica a REGRA DE NEGÓCIO (saída > entrada)
            if (entrada != null && saida != null && !entrada.isBefore(saida)) {
                this.view.exibirErro("A data de saída deve ser posterior à data de entrada.");
            }
        }


        // 5. Criação da Hospedagem
        Hospedagem novaHospedagem = this.hotel.criarHospedagem(pet, tutor, entrada, saida);
        novaHospedagem.setValorDiaria(65.0);
        novaHospedagem.setId(this.hotel.getHospedagensAtivas().size()); 

        // 6. ADICIONAR MÚLTIPLOS SERVIÇOS EM LOOP
        if (!this.hotel.getServicosDisponiveis().isEmpty()) {

            this.view.exibirListaServicosDisponiveis(this.hotel.getServicosDisponiveis());
            
            int totalServicos = this.hotel.getServicosDisponiveis().size();
            int opcaoServico = -1;

            while (opcaoServico != 0) {
                
                opcaoServico = this.view.pedirInteiro(
                    String.format("Digite o NÚMERO do serviço para adicionar (1-%d) ou 0 para finalizar: ", totalServicos)
                );
                
                if (opcaoServico >= 1 && opcaoServico <= totalServicos) {
                    Servico servicoSelecionado = this.hotel.getServicosDisponiveis().get(opcaoServico - 1);
                    novaHospedagem.adicionarServico(servicoSelecionado);
                    this.view.exibirMensagem("Serviço '" + servicoSelecionado.getTipo() + "' adicionado!");
                    
                } else if (opcaoServico != 0) {
                    this.view.exibirErro("Opção inválida. Digite um número de 1 a " + totalServicos + " ou 0 para sair.");
                }
            }
        }

        // 7. Exibir o Relatório
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