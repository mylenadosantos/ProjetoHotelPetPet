package Main;

import Controller.HotelController;
import View.HotelView;
import Util.Logger; // Garante que o Logger está sendo importado

public class Main {
    public static void main(String[] args) {
        
        // Mensagem de log para iniciar a aplicação, sem poluir o console
        Logger.log("INFO", "Aplicação Hotel PetPet iniciada.");
        
        // 1. Cria a View (a interface de entrada/saída)
        HotelView view = new HotelView();
        
        // 2. Cria o Controller, injetando a View
        HotelController controller = new HotelController(view);

        // 3. Inicia o loop principal da aplicação
        controller.iniciarAplicacao();

        // 4. Fecha o scanner da View ao sair (opção 6 do menu)
        view.fechar(); 
        
        Logger.log("INFO", "Aplicação encerrada.");
    }
}
