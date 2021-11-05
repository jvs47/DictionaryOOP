package app.controllers.panes;

import app.actions.AddAct;
import app.actions.EditAct;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditPaneController {
    private ContainerController stage;
    private final AddAct addAct = new AddAct();
    private final EditAct editAct = new EditAct();

    @FXML
    private Button addCheckButton;

    @FXML
    private Label trueAddCheckLabel;

    @FXML
    private Label falseAddCheckLabel;

    @FXML
    private TextArea addDescriptionTextField;

    @FXML
    private TextField addPronounceTextField;

    @FXML
    private Button addSummitButton;

    @FXML
    private TextField wordTextField;

    @FXML
    private Button editCheckButton;

    @FXML
    private Label trueEditCheckLabel;

    @FXML
    private Label falseEditCheckLabel;

    @FXML
    private TextArea editDescriptionTextField;

    @FXML
    private TextField editPronounceTextField;

    @FXML
    private Button editSummitButton;

    @FXML
    private TextField newWordTextField;

    @FXML
    private TextField oldWordTextField;

    @FXML
    void handleAddButtonAction(ActionEvent event) {
        if (event.getSource() == addSummitButton) {
            String word = wordTextField.getText();
            String pronounce = addPronounceTextField.getText();
            String description = addDescriptionTextField.getText();
            addAct.addNewWordToDatabase(word, pronounce, description);
        }
    }

    @FXML
    void handleAddCheckButton(ActionEvent event) {
        if (event.getSource() == addCheckButton) {
            if (addAct.checkValidWord(wordTextField.getText())) {
                trueAddCheckLabel.setVisible(true);
                falseAddCheckLabel.setVisible(false);
                addSummitButton.setDisable(false);
            } else {
                falseAddCheckLabel.setVisible(true);
                trueAddCheckLabel.setVisible(false);
                addSummitButton.setDisable(true);
            }
        }
    }

    @FXML
    void handleEditButtonAction(ActionEvent event) {
        if (event.getSource() == editSummitButton) {
            String oldWord = oldWordTextField.getText();
            String newWord = newWordTextField.getText();
            String pronounce = editPronounceTextField.getText();
            String description = editDescriptionTextField.getText();
            editAct.editWordInDatabase(oldWord, newWord, pronounce, description);
        }
    }

    @FXML
    void handleEditCheckButton(ActionEvent event) {
        if (event.getSource() == editCheckButton) {
            if (editAct.checkValidWord(oldWordTextField.getText())) {
                trueEditCheckLabel.setVisible(true);
                falseEditCheckLabel.setVisible(false);
                editSummitButton.setDisable(false);
            } else {
                falseEditCheckLabel.setVisible(true);
                trueEditCheckLabel.setVisible(false);
                editSummitButton.setDisable(true);
            }
        }
    }


    public void initData(ContainerController stage) {
        this.stage = stage;
        addAct.loadWordsFromDatabase();
        editAct.loadWordsFromDatabase();
    }
}
