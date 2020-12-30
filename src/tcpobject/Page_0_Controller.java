package tcpobject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Page_0_Controller {
    private Client main;

    @FXML
    public TextField playerid;

    @FXML
    public void methodStartGame(ActionEvent event){
        try{
            main.startGame1();
        } catch (Exception e){
            e.printStackTrace();
        }
        /*String name1 = playerid.getText();
        main.setPlayerName(name1);*/
    }

    @FXML
    public void enterGame(ActionEvent event){
        try{
            String name1 = playerid.getText();
            main.setPlayerName(name1);
            main.startGame2();
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    public void methodRules(){

    }

    @FXML
    public void methodSelectBoard(ActionEvent event) {
        try{
            System.out.println("board will be seleted");
            //main.selectBoard();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void methodBoardSpeed(){

    }

    @FXML
    public void methodCredit(){

    }

    @FXML
    public void methodExit(){

    }

    void setMain(Client main){
        this.main = main;
    }

}
