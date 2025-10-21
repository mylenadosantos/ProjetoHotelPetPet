package Main;

import Controller.HotelController;
import View.HotelView;

public class Main {
    public static void main(String[] args) {
        // A Main agora só conecta os pontos
        HotelView view = new HotelView();
        HotelController controller = new HotelController(view);
        
        // Manda o Controller iniciar o fluxo
        controller.iniciarAplicacao();
        
        // Boa prática: fecha o scanner da view
        view.fechar(); 
    }
}