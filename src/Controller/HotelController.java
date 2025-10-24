package Controller; 

import Model.*; 
import View.HotelView; 
import Util.Logger; // Importar a classe Logger
import java.time.LocalDate;

public class HotelController {
    private Hotel hotel;
    private HotelView view; 

    public HotelController(HotelView view) {
        this.hotel = new Hotel("Hotel PetPet Central");
        this.view = view;
   
        // Log: Inicialização do Controller
        Logger.log("INFO", "Controller inicializado. Carregando dados iniciais.");
   
        // Inicializa o catálogo de serviços e funcionários
        Funcionario funcBanho = new Funcionario("João", "99988877766", "Esteticista", 2000.0, 0.0);
        Funcionario funcTosa = new Funcionario("Maria", "11122233344", "Tosador", 3500.0, 0.0);
        Funcionario funcVet = new Funcionario("Graça", "89034567843", "Veterinária", 4500.0, 0.0);
        Funcionario funcTreinador = new Funcionario("Pedro", "44455566677", "Treinador", 3000.0, 0.0);
        
        // O Model já loga o registro de funcionário
        this.hotel.registrarFuncionario(funcBanho);
        this.hotel.registrarFuncionario(funcTosa);
        this.hotel.registrarFuncionario(funcVet);
        this.hotel.registrarFuncionario(funcTreinador);
        
        Servico servicoBanhoDisponivel = new Servico("Banho", "Banho completo", 35.0, funcBanho);
        Servico servicoTosaDisponivel = new Servico("Tosa", "Tosa higiênica", 50.0, funcTosa);
        Servico servicoConsultaDisponivel = new Servico("Consulta", "Consulta de check-in", 60.0, funcVet);
        Servico servicoAdestramento = new Servico("Treinamento", "Sessão de adestramento básica", 75.0, funcTreinador);
        Servico servicoPasseio = new Servico("Passeio", "Passeio extra de 30 minutos", 20.0, funcTreinador);

        this.hotel.adicionarServicoDisponivel(servicoBanhoDisponivel);
        this.hotel.adicionarServicoDisponivel(servicoTosaDisponivel);
        this.hotel.adicionarServicoDisponivel(servicoConsultaDisponivel);
        this.hotel.adicionarServicoDisponivel(servicoAdestramento);
        this.hotel.adicionarServicoDisponivel(servicoPasseio);
    }

    public void iniciarAplicacao() {
        Logger.log("INFO", "Aplicação iniciada."); // Log de Início da Aplicação
        
        this.view.exibirMensagemBoasVindas();
        int opcao = -1;

        while (opcao != 6) { 
            opcao = this.view.pedirOpcaoMenu();
            
            if (opcao != -1) {
                Logger.log("INFO", "Opção de menu selecionada: " + opcao); // Log de Seleção de Menu
            }

            switch (opcao) {
                case 1:
                    registrarNovaHospedagem();
                    break;
                case 2:
                    exibirHospedagensAtivas();
                    break;
                case 3: 
                    finalizarHospedagem(); 
                    break;
                case 4: 
                    adicionarServicosExistente();
                    break;
                case 5: // Editar Hospedagem
                    editarHospedagem();
                    break;
                case 6: 
                    this.view.exibirMensagem("Obrigado por usar o sistema. Até logo!");
                    Logger.log("INFO", "Aplicação encerrada."); // Log de Encerramento
                    break;
                default:
                    if (opcao != -1) {
                        this.view.exibirErro("Opção inválida.");
                        Logger.log("WARN", "Opção de menu inválida digitada: " + opcao); // Log de Alerta
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
                tutor = new Tutor(nomeTutor, cpfTutor); // O MODELO (Pessoa/Tutor) já loga sucesso ou falha
                Logger.log("USER", "Dados do Tutor coletados com sucesso.");
                
            } catch (IllegalArgumentException e) { 
                this.view.exibirErro(e.getMessage()); 
                // O Logger de ERRO já está no construtor de Pessoa. Aqui só alertamos.
                Logger.log("WARN", "Falha na criação de Tutor: " + e.getMessage()); 
            }
        }


        // 2. Coleta de dados do Pet (COM TRATAMENTO DE ERRO DE NÚMERO)
        String nomePet = this.view.pedirString("Nome do Pet: ");
        int idadePet = -1;
        
        while (idadePet < 0) { 
            try {
                idadePet = this.view.pedirInteiro("Idade do Pet: ");
                if (idadePet < 0) {
                    this.view.exibirErro("A idade não pode ser negativa.");
                    Logger.log("WARN", "Idade negativa inserida para Pet: " + idadePet); // Log: Idade negativa
                    idadePet = -1;
                }
            } catch (IllegalArgumentException e) { 
                this.view.exibirErro(e.getMessage());
                Logger.log("WARN", "Input inválido (não-numérico) para idade do Pet."); // Log: Input não numérico
                idadePet = -1;
            }
        }
        
        int tipoPetOpcao = -1;
        
        while (tipoPetOpcao != 1 && tipoPetOpcao != 2) {
            try { 
                this.view.exibirMensagem("Tipos de Pet disponíveis: 1. Cachorro, 2. Gato");
                tipoPetOpcao = this.view.pedirInteiro("Digite o número do tipo do Pet: ");
                
                if (tipoPetOpcao != 1 && tipoPetOpcao != 2) {
                    this.view.exibirErro("Opção de tipo de pet inválida. Digite 1 ou 2.");
                    Logger.log("WARN", "Opção de Pet inválida inserida: " + tipoPetOpcao); // Log: Opção de Pet inválida
                }
            } catch (IllegalArgumentException e) {
                 this.view.exibirErro(e.getMessage());
                 Logger.log("WARN", "Input inválido (não-numérico) para tipo de Pet."); // Log: Input não numérico
                 tipoPetOpcao = -1; 
            }
        }
        
        Pet pet;
        if (tipoPetOpcao == 1) { 
            pet = new Cachorro(nomePet, idadePet);
        } else { 
            pet = new Gato(nomePet, idadePet);
        }
        
        // 3. PERGUNTAS AO PET (Coleta de dados)
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
        
        Logger.log("USER", "Dados do Pet coletados. Nome: " + pet.getNome() + ", Tipo: " + pet.getClass().getSimpleName());


        // 4. Coleta e validação de datas
        LocalDate entrada = null;
        LocalDate saida = null;
        while (entrada == null || saida == null || (entrada != null && saida != null && !entrada.isBefore(saida))) {
            
            LocalDate tempEntrada = null;
            while(tempEntrada == null) {
                try {
                    tempEntrada = this.view.pedirData("Data de Entrada");
                    Logger.log("USER", "Data de entrada inserida: " + tempEntrada); 
                } catch (IllegalArgumentException e) {
                    this.view.exibirErro(e.getMessage());
                    Logger.log("WARN", "Formato de data inválido para entrada."); // Log: Formato de data inválido
                }
            }
            entrada = tempEntrada; 
            
            LocalDate tempSaida = null;
            while(tempSaida == null) {
                 try {
                    tempSaida = this.view.pedirData("Data de Saída");
                    Logger.log("USER", "Data de saída inserida: " + tempSaida);
                } catch (IllegalArgumentException e) {
                    this.view.exibirErro(e.getMessage());
                    Logger.log("WARN", "Formato de data inválido para saída."); // Log: Formato de data inválido
                }
            }
            saida = tempSaida; 
            
            if (entrada != null && saida != null && !entrada.isBefore(saida)) {
                this.view.exibirErro("A data de saída deve ser posterior à data de entrada.");
                Logger.log("WARN", "Datas inválidas: Saída não é posterior à entrada."); // Log: Regra de negócio falhou
            }
        }


        // 5. Criação da Hospedagem - O MODELO JÁ LOGA O EVENTO DE CRIAÇÃO
        Hospedagem novaHospedagem = this.hotel.criarHospedagem(pet, tutor, entrada, saida);
        novaHospedagem.setValorDiaria(65.0);
        novaHospedagem.setId(this.hotel.getHospedagensAtivas().size()); 
        
        Logger.log("INFO", "Nova Hospedagem ID " + novaHospedagem.getId() + " inicializada com diária R$65.0.");

        // 6. ADICIONAR MÚLTIPLOS SERVIÇOS EM LOOP (Lógica de interação com o usuário)
        if (!this.hotel.getServicosDisponiveis().isEmpty()) {

            this.view.exibirListaServicosDisponiveis(this.hotel.getServicosDisponiveis());
            
            int totalServicos = this.hotel.getServicosDisponiveis().size();
            int opcaoServico = -1;

            while (opcaoServico != 0) {
                 try {
                    opcaoServico = this.view.pedirInteiro(
                        String.format("Digite o NÚMERO do serviço para adicionar (1-%d) ou 0 para finalizar: ", totalServicos)
                    );
                    
                    if (opcaoServico >= 1 && opcaoServico <= totalServicos) {
                        Servico servicoSelecionado = this.hotel.getServicosDisponiveis().get(opcaoServico - 1);
                        novaHospedagem.adicionarServico(servicoSelecionado);
                        this.view.exibirMensagem("Serviço '" + servicoSelecionado.getTipo() + "' adicionado!");
                        Logger.log("USER", "Serviço adicionado à ID " + novaHospedagem.getId() + ": " + servicoSelecionado.getTipo());
                        
                    } else if (opcaoServico != 0) {
                        this.view.exibirErro("Opção inválida. Digite um número de 1 a " + totalServicos + " ou 0 para sair.");
                        Logger.log("WARN", "Opção de serviço inválida: " + opcaoServico); // Log: Opção de serviço inválida
                    }
                 } catch (IllegalArgumentException e) {
                      this.view.exibirErro(e.getMessage());
                      Logger.log("WARN", "Input inválido (não-numérico) na seleção de serviços."); // Log: Input não numérico
                      opcaoServico = -1;
                 }
            }
        }

        // 7. Exibir o Relatório
        this.view.exibirRelatorioHospedagem(novaHospedagem);
    }

    // =========================================================================
    // MÉTODO AUXILIAR REQUERIDO PELO MENU (exibirHospedagensAtivas)
    // =========================================================================
    private void exibirHospedagensAtivas() {
        if (this.hotel.getHospedagensAtivas().isEmpty()) {
            this.view.exibirMensagem("\nNão há hospedagens ativas no momento.");
        } else {
            this.view.exibirMensagem("\n--- LISTA DE HOSPEDAGENS ATIVAS ---");
            for (Hospedagem h : this.hotel.getHospedagensAtivas()) {
                this.view.exibirMensagem(String.format("ID: %d | Pet: %s | Tutor: %s", 
                    h.getId(), h.getPet().getNome(), h.getTutor().getNome()
                ));
            }
            this.view.exibirMensagem("-----------------------------------");
        }
    }
    
    // MÉTODO AUXILIAR para adicionar serviços a uma hospedagem que já está ativa (OPÇÃO 4)
    private void adicionarServicosExistente() {
        this.view.exibirMensagem("\n--- ADICIONAR SERVIÇOS A HOSPEDAGEM ATIVA ---");
        
        if (this.hotel.getHospedagensAtivas().isEmpty()) {
            this.view.exibirMensagem("Não há hospedagens ativas para adicionar serviços.");
            return;
        }

        this.exibirHospedagensAtivas(); 

        int idParaAtualizar = -1;
        Hospedagem hospedagemParaAtualizar = null;
        
        while (hospedagemParaAtualizar == null) {
            try {
                idParaAtualizar = this.view.pedirInteiro("Digite o ID da hospedagem para adicionar serviços: ");
                
                for (Hospedagem h : this.hotel.getHospedagensAtivas()) {
                    if (h.getId() == idParaAtualizar) {
                        hospedagemParaAtualizar = h;
                        break;
                    }
                }

                if (hospedagemParaAtualizar == null) {
                    this.view.exibirErro("ID " + idParaAtualizar + " não encontrado. Tente novamente.");
                    Logger.log("WARN", "Tentativa de adicionar serviço a ID não encontrado: " + idParaAtualizar); // Log: ID não encontrado
                }
                
            } catch (IllegalArgumentException e) {
                this.view.exibirErro(e.getMessage());
                Logger.log("WARN", "Input inválido (não-numérico) para ID de hospedagem."); // Log: Input não numérico
            }
        }
        
        this.view.exibirMensagem("\nAtualizando serviços para Pet: " + hospedagemParaAtualizar.getPet().getNome());
        
        if (!this.hotel.getServicosDisponiveis().isEmpty()) {

            this.view.exibirListaServicosDisponiveis(this.hotel.getServicosDisponiveis());
            
            int totalServicos = this.hotel.getServicosDisponiveis().size();
            int opcaoServico = -1;

            while (opcaoServico != 0) {
                 try {
                    opcaoServico = this.view.pedirInteiro(
                        String.format("Digite o NÚMERO do serviço para adicionar (1-%d) ou 0 para finalizar: ", totalServicos)
                    );
                    
                    if (opcaoServico >= 1 && opcaoServico <= totalServicos) {
                        Servico servicoSelecionado = this.hotel.getServicosDisponiveis().get(opcaoServico - 1);
                        hospedagemParaAtualizar.adicionarServico(servicoSelecionado);
                        this.view.exibirMensagem("Serviço '" + servicoSelecionado.getTipo() + "' adicionado! Valor atualizado.");
                        Logger.log("USER", "Serviço adicionado à ID " + hospedagemParaAtualizar.getId() + ": " + servicoSelecionado.getTipo());
                        
                    } else if (opcaoServico != 0) {
                        this.view.exibirErro("Opção inválida. Digite um número de 1 a " + totalServicos + " ou 0 para sair.");
                        Logger.log("WARN", "Opção de serviço inválida: " + opcaoServico); // Log: Opção de serviço inválida
                    }
                 } catch (IllegalArgumentException e) {
                      this.view.exibirErro(e.getMessage());
                      Logger.log("WARN", "Input inválido (não-numérico) na seleção de serviços."); // Log: Input não numérico
                      opcaoServico = -1;
                 }
            }
        }
        
        this.view.exibirMensagem("\nRelatório Atualizado:");
        this.view.exibirRelatorioHospedagem(hospedagemParaAtualizar);
    }
    
    // MÉTODO para Finalizar Hospedagem (Check-out) - OPÇÃO 3
    private void finalizarHospedagem() {
        this.view.exibirMensagem("\n--- FINALIZAR HOSPEDAGEM (Check-out) ---");
        
        if (this.hotel.getHospedagensAtivas().isEmpty()) {
            this.view.exibirMensagem("Não há hospedagens ativas para finalizar.");
            return;
        }

        this.exibirHospedagensAtivas(); 

        int idParaFinalizar = -1;
        boolean sucesso = false;
        
        while (idParaFinalizar == -1 || !sucesso) {
            try {
                idParaFinalizar = this.view.pedirInteiro("Digite o ID da hospedagem para check-out: ");

                Hospedagem hospedagemFinal = null;
                for (Hospedagem h : this.hotel.getHospedagensAtivas()) {
                    if (h.getId() == idParaFinalizar) {
                        hospedagemFinal = h;
                        break;
                    }
                }

                if (hospedagemFinal != null) {
                    this.view.exibirRelatorioHospedagem(hospedagemFinal);
                    sucesso = this.hotel.finalizarHospedagem(idParaFinalizar); // O MODELO já loga o sucesso/falha

                    if (sucesso) {
                        this.view.exibirMensagem("\n--- CHECK-OUT CONCLUÍDO ---");
                        // O MODELO JÁ LOGA O EVENTO
                    }
                    
                } else {
                    this.view.exibirErro("ID " + idParaFinalizar + " não encontrado. Tente novamente.");
                    Logger.log("WARN", "Tentativa de check-out com ID não encontrado: " + idParaFinalizar); // Log: ID não encontrado
                }
                
            } catch (IllegalArgumentException e) {
                this.view.exibirErro(e.getMessage());
                Logger.log("WARN", "Input inválido (não-numérico) para ID de check-out."); // Log: Input não numérico
                idParaFinalizar = -1;
            }
        }
    }
    
    // =========================================================================
    // MÉTODO para Editar/Atualizar Dados da Hospedagem (OPÇÃO 5)
    // =========================================================================
    private void editarHospedagem() {
        this.view.exibirMensagem("\n--- EDITAR DADOS DA HOSPEDAGEM ---");
        
        if (this.hotel.getHospedagensAtivas().isEmpty()) {
            this.view.exibirMensagem("Não há hospedagens ativas para editar.");
            return;
        }

        this.exibirHospedagensAtivas(); 

        int idParaEditar = -1;
        Hospedagem hospedagem = null;
        
        // Loop de validação para garantir que o ID seja um número e exista
        while (hospedagem == null) {
            try {
                idParaEditar = this.view.pedirInteiro("Digite o ID da hospedagem para editar: ");
                
                for (Hospedagem h : this.hotel.getHospedagensAtivas()) {
                    if (h.getId() == idParaEditar) {
                        hospedagem = h;
                        break;
                    }
                }
                
                if (hospedagem == null) {
                    this.view.exibirErro("ID não encontrado.");
                    Logger.log("WARN", "Tentativa de editar com ID não encontrado: " + idParaEditar); // Log: ID não encontrado
                }
                
            } catch (IllegalArgumentException e) {
                this.view.exibirErro(e.getMessage());
                Logger.log("WARN", "Input inválido (não-numérico) para ID de edição."); // Log: Input não numérico
            }
        }
        
        // 2. Apresenta o Submenu de Edição
        int opcaoEdicao = -1;
        while (opcaoEdicao != 0) {
            this.view.exibirMensagem("\n--- EDITAR HOSPEDAGEM ID: " + hospedagem.getId() + " ---");
            this.view.exibirMensagem("1. Editar Dados do Tutor (Telefone/Email)");
            this.view.exibirMensagem("2. Editar Dados do Pet (Raça/Dieta/Remédios)");
            this.view.exibirMensagem("3. Alterar Data de Saída");
            this.view.exibirMensagem("0. Finalizar Edição");
            
            // Captura o erro aqui para garantir repetição
            try {
                opcaoEdicao = this.view.pedirInteiro("Escolha o campo para editar: ");
                Logger.log("INFO", "Submenu Edição ID " + hospedagem.getId() + " - Opção: " + opcaoEdicao); // Log: Opção de edição
                
            } catch (IllegalArgumentException e) {
                this.view.exibirErro(e.getMessage());
                Logger.log("WARN", "Input inválido (não-numérico) para opção de edição."); // Log: Input não numérico
                opcaoEdicao = -1; // Garante que o loop repita
                continue; // Volta ao início do while
            }
            
            switch (opcaoEdicao) {
                case 1:
                    editarTutor(hospedagem.getTutor());
                    break;
                case 2:
                    editarPet(hospedagem.getPet());
                    break;
                case 3:
                    alterarDataSaida(hospedagem);
                    break;
                case 0:
                    this.view.exibirMensagem("Edição finalizada. Novo relatório:");
                    this.view.exibirRelatorioHospedagem(hospedagem);
                    break;
                default:
                    if (opcaoEdicao != -1) this.view.exibirErro("Opção inválida.");
                    break;
            }
        }
    }
    
    // Sub-método para editar dados do Tutor
    private void editarTutor(Tutor tutor) {
        this.view.exibirMensagem("\n--- EDITANDO TUTOR: " + tutor.getNome() + " ---");
        String novoTelefone = this.view.pedirString("Novo Telefone (" + (tutor.getTelefone() == null ? "vazio" : tutor.getTelefone()) + "): ");
        String novoEmail = this.view.pedirString("Novo E-mail (" + (tutor.getEmail() == null ? "vazio" : tutor.getEmail()) + "): ");
        tutor.setTelefone(novoTelefone);
        tutor.setEmail(novoEmail);
        this.view.exibirMensagem("Dados do Tutor atualizados.");
        Logger.log("EVENT", "Dados do Tutor atualizados. CPF: " + tutor.getCpf() + ", Novo Email: " + novoEmail); // Log: Atualização
    }
    
    // Sub-método para editar dados do Pet
    private void editarPet(Pet pet) {
        this.view.exibirMensagem("\n--- EDITANDO PET: " + pet.getNome() + " ---");
        String novaRaca = this.view.pedirString("Nova Raça (" + pet.getRaca() + "): ");
        pet.setRaca(novaRaca);
        
        // ... Lógica de Dieta e Remédios omitida por brevidade...
        String temDieta = this.view.pedirString("Pet possui Dieta Específica (s/n)? ").toLowerCase();
        if (temDieta.equals("s")) {
            pet.setDieta(this.view.pedirString("Descreva a Nova Dieta: "));
        } else {
            pet.setDieta("Padrão do Hotel");
        }
        
        String temRemedios = this.view.pedirString("Pet precisa de Remédios/Medicação (s/n)? ").toLowerCase();
        if (temRemedios.equals("s")) {
            pet.setRemedios(this.view.pedirString("Descreva Remédios e Dosagens: "));
        } else {
            pet.setRemedios("Nenhum");
        }
        this.view.exibirMensagem("Dados do Pet atualizados.");
        Logger.log("EVENT", "Dados do Pet atualizados. Nome: " + pet.getNome() + ", Nova Raça: " + novaRaca); // Log: Atualização
    }
    
    // Sub-método para alterar a Data de Saída
    private void alterarDataSaida(Hospedagem hospedagem) {
        LocalDate novaSaida = null;
        LocalDate entrada = hospedagem.getDataEntrada();
        
        while (novaSaida == null || !novaSaida.isAfter(entrada)) {
            try {
                // Pede a nova data de saída
                novaSaida = this.view.pedirData("Nova Data de Saída (Atual: " + hospedagem.getDataSaida() + ")");
                Logger.log("USER", "Tentativa de nova data de saída: " + novaSaida); // Log: Tentativa de data

                // Se for válida, checa a regra de negócio (Saída > Entrada)
                if (novaSaida != null && !novaSaida.isAfter(entrada)) {
                    this.view.exibirErro("A nova data de saída deve ser posterior à data de entrada (" + entrada + ").");
                    Logger.log("WARN", "Tentativa de data inválida (anterior à entrada): " + novaSaida); // Log: Regra falhou
                } else if (novaSaida != null) {
                    // Se for válida e posterior à entrada, atualiza o Model
                    hospedagem.setDataSaida(novaSaida);
                    this.view.exibirMensagem("Data de saída atualizada com sucesso!");
                    Logger.log("EVENT", "Data de saída atualizada para ID " + hospedagem.getId() + ": " + novaSaida); // Log: Sucesso
                }
            } catch (IllegalArgumentException e) {
                this.view.exibirErro(e.getMessage());
                Logger.log("WARN", "Formato de data inválido para alteração de saída."); // Log: Formato de data inválido
            }
        }
    }
}
