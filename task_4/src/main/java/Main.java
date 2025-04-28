import controller.*;
import model.*;
import viewier.*;
public class Main {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {
            MainController mainController = new controller.MainController();
            mainController.showMenu();
        });
    }
}