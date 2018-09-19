package controller.javaFX;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import model.character.Character;

import java.util.Comparator;

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

    public static void createEditableDoubleColumn(TableView tableView, String propertyName){
        TableColumn column = new TableColumn(propertyName);
        column.setPrefWidth(50);
        column.setCellValueFactory(new PropertyValueFactory<Character, String>(propertyName));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Character, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Character, String> cEE) {
                        (cEE.getTableView().getItems().get(cEE.getTablePosition().getRow())).
                                setDoubleValue(propertyName, cEE.getNewValue());
                    }
                }
        );

        column.setComparator(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                if (s1 == null && s2 == null) return 0;
                if (s1 == null) return -1;
                if (s2 == null) return 1;

                Double d1 =null;
                try{ d1 =Double.parseDouble(s1); } catch(NumberFormatException ignored){}
                Double d2 =null;
                try{ d2 =Double.parseDouble(s2); } catch(NumberFormatException ignored){}

                if(d1 ==null && d2 ==null) return s1.compareTo(s2);
                if(d1 ==null) return -1;
                if(d2 ==null) return 1;

                return Double.compare(d1, d2);
            }
        });

        tableView.getColumns().add(column);
    }

    public static void forceNumericTextField(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d+\\.\\d+") && !newValue.matches("\\d")
                        && !newValue.matches("\\.") && !newValue.matches("\\d+\\.")
                        && !newValue.matches("\\.\\d+")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
}
