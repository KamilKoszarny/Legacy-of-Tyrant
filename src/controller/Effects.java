package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import model.character.Character;

public class Effects {

    static void addButtonShadow(Button button){
        DropShadow shadow = new DropShadow();
//Adding the shadow when the mouse cursor is on
        button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        button.setEffect(shadow);
                    }
                });
//Removing the shadow when the mouse cursor is off
        button.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        button.setEffect(null);
                    }
                });
    }

    static void refreshTable(TableView<Character> table){
        for (TableColumn<Character, ?> column: table.getColumns().sorted()) {
            column.setVisible(false);
            column.setVisible(true);
        }
    }
}
