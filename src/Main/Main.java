package Main;

import Controller.HotelController;
import View.HotelView;
// Remova todos os imports antigos de Model que podem ter voltado

public class Main {
    public static void main(String[] args) {
        // Manter APENAS o código minimalista do MVC
        HotelView view = new HotelView();
        HotelController controller = new HotelController(view);

        controller.iniciarAplicacao();

        view.fechar(); 
    }
}