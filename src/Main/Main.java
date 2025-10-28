package Main;

import Controller.HotelController;
import View.HotelView;
import Util.Logger; 

public class Main {
    public static void main(String[] args) {
        
        Logger.log("INFO", "Aplicação Hotel PetPet iniciada.");
        
        HotelView view = new HotelView();
        
        HotelController controller = new HotelController(view);

        controller.iniciarAplicacao();

        view.fechar(); 
        
        Logger.log("INFO", "Aplicação encerrada.");
    }
}
