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
   
        // Inicializa o catálogo de serviços e funcionários
        Funcionario funcBanho = new Funcionario("João", "99988877766", "Esteticista", 2000.0, 0.0);
        Funcionario funcTosa = new Funcionario("Maria", "11122233344", "Tosador", 3500.0, 0.0);
        Funcionario funcVet = new Funcionario("Graça", "89034567843", "Veterinária", 4500.0, 0.0);
        Funcionario funcTreinador = new Funcionario("Pedro", "44455566677", "Treinador", 3000.0, 0.0);
        
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
        this.view.exibirMensagemBoasVindas();
        int opcao = -1;

        // O loop principal agora usa a opção 6 para sair
        while (opcao != 6) { 
            opcao = this.view.pedirOpcaoMenu();

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
                case 5: // NOVA OPÇÃO: Editar Hospedagem
                    editarHospedagem();
                    break;
                case 6: // MUDANÇA: Sair é a opção 6
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


        // 2. Coleta de dados do Pet (COM TRATAMENTO DE ERRO DE NÚMERO)
        String nomePet = this.view.pedirString("Nome do Pet: ");
        int idadePet = -1; // Inicializa para entrar no loop
        
        // Loop para validar Idade do Pet
        while (idadePet < 0) { 
            try {
                idadePet = this.view.pedirInteiro("Idade do Pet: ");
                if (idadePet < 0) {
                    this.view.exibirErro("A idade não pode ser negativa.");
                    idadePet = -1; // Mantém a repetição
                }
            } catch (IllegalArgumentException e) { 
                this.view.exibirErro(e.getMessage());
                idadePet = -1; // Garante que o loop repita após o erro de tipo
            }
        }
        
        int tipoPetOpcao = -1;
        
        while (tipoPetOpcao != 1 && tipoPetOpcao != 2) {
            try { // Captura exceção para Opção de Pet
                this.view.exibirMensagem("Tipos de Pet disponíveis: 1. Cachorro, 2. Gato");
                tipoPetOpcao = this.view.pedirInteiro("Digite o número do tipo do Pet: ");
                
                if (tipoPetOpcao != 1 && tipoPetOpcao != 2) {
                    this.view.exibirErro("Opção de tipo de pet inválida. Digite 1 ou 2.");
                }
            } catch (IllegalArgumentException e) {
                 this.view.exibirErro(e.getMessage());
                 tipoPetOpcao = -1; // Garante que o loop repita
            }
        }
        
        Pet pet;
        if (tipoPetOpcao == 1) { // 1 = Cachorro
            pet = new Cachorro(nomePet, idadePet);
        } else { // 2 = Gato
            pet = new Gato(nomePet, idadePet);
        }
        
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
                 try {
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
                 } catch (IllegalArgumentException e) {
                      this.view.exibirErro(e.getMessage());
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
                }
                
            } catch (IllegalArgumentException e) {
                this.view.exibirErro(e.getMessage());
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
                        
                    } else if (opcaoServico != 0) {
                        this.view.exibirErro("Opção inválida. Digite um número de 1 a " + totalServicos + " ou 0 para sair.");
                    }
                 } catch (IllegalArgumentException e) {
                      this.view.exibirErro(e.getMessage());
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
                    sucesso = this.hotel.finalizarHospedagem(idParaFinalizar);

                    if (sucesso) {
                        this.view.exibirMensagem("\n--- CHECK-OUT CONCLUÍDO ---");
                        this.view.exibirMensagem("Hospedagem ID " + idParaFinalizar + " finalizada e removida da lista ativa.");
                    }
                    
                } else {
                    this.view.exibirErro("ID " + idParaFinalizar + " não encontrado. Tente novamente.");
                }
                
            } catch (IllegalArgumentException e) {
                this.view.exibirErro(e.getMessage());
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
                }
                
            } catch (IllegalArgumentException e) {
                this.view.exibirErro(e.getMessage());
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
            } catch (IllegalArgumentException e) {
                this.view.exibirErro(e.getMessage());
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
        tutor.setTelefone(this.view.pedirString("Novo Telefone (" + (tutor.getTelefone() == null ? "vazio" : tutor.getTelefone()) + "): "));
        tutor.setEmail(this.view.pedirString("Novo E-mail (" + (tutor.getEmail() == null ? "vazio" : tutor.getEmail()) + "): "));
        this.view.exibirMensagem("Dados do Tutor atualizados.");
    }
    
    // Sub-método para editar dados do Pet
    private void editarPet(Pet pet) {
        this.view.exibirMensagem("\n--- EDITANDO PET: " + pet.getNome() + " ---");
        pet.setRaca(this.view.pedirString("Nova Raça (" + pet.getRaca() + "): "));
        
        // Lógica de Dieta e Remédios (s/n) pode ser repetida aqui
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
    }
    
    // Sub-método para alterar a Data de Saída
    private void alterarDataSaida(Hospedagem hospedagem) {
        LocalDate novaSaida = null;
        LocalDate entrada = hospedagem.getDataEntrada();
        
        while (novaSaida == null || !novaSaida.isAfter(entrada)) {
            try {
                // Pede a nova data de saída
                novaSaida = this.view.pedirData("Nova Data de Saída (Atual: " + hospedagem.getDataSaida() + ")");

                // Se for válida, checa a regra de negócio (Saída > Entrada)
                if (novaSaida != null && !novaSaida.isAfter(entrada)) {
                    this.view.exibirErro("A nova data de saída deve ser posterior à data de entrada (" + entrada + ").");
                } else if (novaSaida != null) {
                    // Se for válida e posterior à entrada, atualiza o Model
                    hospedagem.setDataSaida(novaSaida);
                    this.view.exibirMensagem("Data de saída atualizada com sucesso!");
                }
            } catch (IllegalArgumentException e) {
                this.view.exibirErro(e.getMessage());
            }
        }
    }
}