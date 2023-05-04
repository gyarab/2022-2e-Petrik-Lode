package shipspack.ships;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class ShipsController {

    @FXML
    public GridPane grid;
    @FXML
    public GridPane enemyGrid;
    @FXML
    private Label Ships1;
    @FXML
    private Label Ships2;
    @FXML
    private Label Ships3;
    @FXML
    private Label Ships4;
    @FXML
    private Label Ships5;
    @FXML
    public Label Ships1AI;
    @FXML
    public Label Ships2AI;
    @FXML
    public Label Ships3AI;
    @FXML
    public Label Ships4AI;
    @FXML
    public Label Ships5AI;
    @FXML
    public Label Feedback;
    @FXML
    public Button RotateButton;
    @FXML
    public Button ModeButton;
    @FXML
    private Label Preview4;
    @FXML
    private Label Preview3;
    @FXML
    private Label Preview2;
    @FXML
    private Label Preview1;
    @FXML
    private Label Preview0;
    @FXML
    private Button spy_button;

    @FXML
    private Button sh1plus;
    @FXML
    private Button sh2plus;
    @FXML
    private Button sh3plus;
    @FXML
    private Button sh4plus;
    @FXML
    private Button sh5plus;
    @FXML
    private Button sh1min;
    @FXML
    private Button sh2min;
    @FXML
    private Button sh3min;
    @FXML
    private Button sh4min;
    @FXML
    private Button sh5min;
    @FXML
    private Button initializeBut;
    @FXML
    private Button toMenuBut;
    @FXML
    private ProgressBar fullnessBar;
    @FXML
    private Label fullnessLabel;
    @FXML
    private Pane enemyPane;
    @FXML
    private HBox buildBox;

    //STATS
    @FXML
    private Button statsBut;
    @FXML
    private Pane statsPane;
    @FXML
    private Label statLab0;
    @FXML
    private Label statLab1;
    @FXML
    private Label statLab2;
    @FXML
    private Label statLab3;
    @FXML
    private Label statLab4;
    @FXML
    private Label statLab5;
    @FXML
    private Label statLab6;
    @FXML
    private Label statLabResult;
    @FXML
    private ImageView BotIco;
    @FXML
    private Button BotStratBut;



    //temp
    static ArrayList<coordinates> tempBlockedList = new ArrayList<>();
    ArrayList<Integer> tempShipNumberList = new ArrayList<>();
    static ArrayList<ArrayList<coordinates>> tempShipPosition = new ArrayList<>();

    // Player
    ArrayList<coordinates> blockedList = new ArrayList<>();
    ArrayList<Integer> shipNumberList = new ArrayList<>();
    static ArrayList<ArrayList<coordinates>> shipPosition = new ArrayList<>();

    //AI enemy
    ArrayList<ArrayList<coordinates>> AIShipPosition = new ArrayList<>();
    public ArrayList<coordinates> AIBlockedList = new ArrayList<>();
    public ArrayList<Integer> AIShipNumberList = new ArrayList<>();

    //Default
    static int row = 10;
    private Stage stage;
    private Scene scene;
    private Parent root;
    static int totalShips;
    static ArrayList<Double> statsArr = new ArrayList<>();
    static GridPane tempGrid = new GridPane();
    static coordinates currentCoordinates = new coordinates(-1, -1);
    static String FeedbackString;
    static ArrayList<coordinates> targets = new ArrayList<>();
    int fullness;

    //Values
    static int shipLength = 4;
    static Boolean rotated = true;

    static Boolean visible = false;
    static Boolean tryAgain = true;
    static Boolean spyMode = false;

    static int result = 0;
    int AISunken = 0;
    int PlayerSunken = 0;
    static int tempSunken = 0;
    int selectedMode = 0;
    boolean firstStart = true;
    boolean AIon = false;
    int gameMode = 0;
    static int ships1 = 0;
    static int ships2 = 3;
    static int ships3 = 1;
    static int ships4 = 2;
    static int ships5 = 1;

    static int xOffset = 0;
    static int yOffset = 0;


    static int botStratInt = 0;


    public void ToGame(ActionEvent event) throws IOException, InterruptedException {
        System.out.println("Changing scene to game");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ShipBoard.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1600, 1000);
        stage.setScene(scene);
        stage.show();

        stage.setTitle("Ships - Game");
        stage.getIcons().add(new Image("Ships_ico.png"));
        stage.setMaximized(false);
        stage.resizableProperty().set(true);

    }

    public void ToMenu(ActionEvent event) throws IOException {
        statsArr.clear();
        result = 0;
        enemyGrid.getChildren().clear();
        grid.getChildren().clear();
        rotated = true;

        shipNumberList.clear();
        blockedList.clear();
        shipPosition.clear();

        AIShipPosition.clear();
        AIBlockedList.clear();
        AIShipNumberList.clear();

        ships1 = 0;
        ships2 = 3;
        ships3 = 1;
        ships4 = 2;
        ships5 = 1;

        botStratInt = 0;
        targets.clear();
        spyMode = false;

        System.out.println("Changing scene to menu");
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Menu&Settings.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1600, 1000);
        stage.setScene(scene);
        stage.show();

        stage.setTitle("Ships - Menu (beta v0.5)");
        stage.getIcons().add(new Image("Ships_ico.png"));
        stage.setMaximized(false);
        stage.resizableProperty().set(true);


    }

    @FXML
    private Button switchBut;

    public void ToStats() {
        if (!statsPane.isVisible()) {
            statsBut.setText("Zavřít statistiky");
        } else statsBut.setText("Podívat se na statistiky");
        statsPane.setVisible(!statsPane.isVisible());

        switch (result) {
            case -1 -> {
                statLabResult.setText("Vítězství - Počítač");
                switchBut.setVisible(true);
            }
            case 0 -> {
                statLabResult.setText("Hra v průběhu");
                switchBut.setVisible(false);
            }
            case 1 -> {
                statLabResult.setText("Vítězství - Hráč");
                switchBut.setVisible(true);
            }
            case 2 -> {
                statLabResult.setText("Remíza");
                switchBut.setVisible(true);
            }
        }

        statLab0.setText(String.valueOf(Math.round(statsArr.get(0))));
        statLab1.setText(String.valueOf(Math.round(statsArr.get(1))));
        statLab2.setText(String.valueOf(Math.round(statsArr.get(2))));
        statLab3.setText(String.valueOf(Math.round(statsArr.get(3))));
        statLab4.setText("Tah " + Math.round(statsArr.get(4)));
        statLab5.setText(Math.round(statsArr.get(2) / statsArr.get(0) * 100) + "%");
        statLab6.setText(Math.round(statsArr.get(3) / statsArr.get(1) * 100) + "%");

    }


    public void CustomGame(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        char num = sourceButton.getId().charAt(2);
        char change = sourceButton.getId().charAt(3);

        switch (num) {
            case '1':
                if (change == 'm') {
                    ships1 -= 1;
                } else ships1 += 1;
                break;
            case '2':
                if (change == 'm') {
                    ships2 -= 1;
                } else ships2 += 1;
                break;
            case '3':
                if (change == 'm') {
                    ships3 -= 1;
                } else ships3 += 1;
                break;
            case '4':
                if (change == 'm') {
                    ships4 -= 1;
                } else ships4 += 1;
                break;
            case '5':
                if (change == 'm') {
                    ships5 -= 1;
                } else ships5 += 1;
                break;
        }


        //zaplnenost (n+2)*3
        fullness = ships1 * 6 + ships2 * 9 + ships3 * 12 + ships4 * 15 + ships5 * 18;
        System.out.println(fullness);
        toMenuBut.setDisable(fullness <= 0);

        sh1min.setDisable(ships1 == 0);
        sh2min.setDisable(ships2 == 0);
        sh3min.setDisable(ships3 == 0);
        sh4min.setDisable(ships4 == 0);
        sh5min.setDisable(ships5 == 0);

        //kontrola zaplnenosti
        sh5plus.setDisable(fullness + 18 >= 100);
        sh4plus.setDisable(fullness + 15 >= 100);
        sh3plus.setDisable(fullness + 12 >= 100);
        sh2plus.setDisable(fullness + 9 >= 100);
        sh1plus.setDisable(fullness + 6 >= 100);

        fullnessLabel.setText(fullness + "%");
        fullnessBar.setProgress((double) fullness / 100);

        Preview0.setText(String.valueOf(ships1));
        Preview1.setText(String.valueOf(ships2));
        Preview2.setText(String.valueOf(ships3));
        Preview3.setText(String.valueOf(ships4));
        Preview4.setText(String.valueOf(ships5));

    }


    @FXML
    public void BotStrat() {

        BotIco.setStyle("-fx-background-size: cover;");
        if (botStratInt == 1) {
            botStratInt = -1;
        }

        botStratInt++;

        if (botStratInt == 0) {
            BotIco.setImage(new Image("BotR.png"));
            BotStratBut.setText("Počítač: Náhodný");
        }
        if (botStratInt == 1) {
            BotIco.setImage(new Image("BotS.png"));
            BotStratBut.setText("Počítač: Strategický");
        }

    }


    @FXML
    public void modeControll() {

        selectedMode++;

        if (selectedMode > 5) {
            selectedMode = 0;
        }
        switch (selectedMode) {
            case 0 -> {
                gameMode = 0;
                ModeButton.setText("Herní mód: Flotila");
            }
            case 1 -> {
                gameMode = 1;
                ModeButton.setText("Herní mód: Tradiční");
            }
            case 2 -> {
                gameMode = 2;
                ModeButton.setText("Herní mód: Tradiční - M. Bradley");
            }
            case 3 -> {
                gameMode = 3;
                ModeButton.setText("Herní mód: Ruská");
            }
            case 4 -> {
                gameMode = 4;
                ModeButton.setText("Herní mód: Loterie");
            }
            case 5 -> {
                gameMode = 5;
                ModeButton.setText("Herní mód: Vlastní");
            }
        }


        if (selectedMode != 5) {
            sh1min.setDisable(true);
            sh2min.setDisable(true);
            sh3min.setDisable(true);
            sh4min.setDisable(true);
            sh5min.setDisable(true);
            sh1plus.setDisable(true);
            sh2plus.setDisable(true);
            sh3plus.setDisable(true);
            sh4plus.setDisable(true);
            sh5plus.setDisable(true);
        } else {
            sh1min.setDisable(false);
            sh2min.setDisable(false);
            sh3min.setDisable(false);
            sh4min.setDisable(false);
            sh5min.setDisable(false);
            sh1plus.setDisable(false);
            sh2plus.setDisable(false);
            sh3plus.setDisable(false);
            sh4plus.setDisable(false);
            sh5plus.setDisable(false);
        }


        //Mody hry: FLotila, Tradicni, Tradicni M. Bradley 1990, Ruská, Loterie, Vlastní

        switch (gameMode) {
            case 0 -> {
                ships1 = 0;
                ships2 = 3;
                ships3 = 1;
                ships4 = 2;
                ships5 = 1;
            }
            case 1 -> {
                ships1 = 2;
                ships2 = 4;
                ships3 = 2;
                ships4 = 1;
                ships5 = 0;
            }
            case 2 -> {
                ships1 = 0;
                ships2 = 1;
                ships3 = 1;
                ships4 = 1;
                ships5 = 1;
            }
            case 3 -> {
                ships1 = 4;
                ships2 = 3;
                ships3 = 2;
                ships4 = 1;
                ships5 = 0;
            }
            case 4 -> {
                ships1 = 1;
                ships2 = 0;
                ships3 = 0;
                ships4 = 0;
                ships5 = 0;
            }
            case 5 -> {
                ships1 = 0;
                ships2 = 0;
                ships3 = 0;
                ships4 = 0;
                ships5 = 0;
            }
        }


        fullness = ships1 * 6 + ships2 * 9 + ships3 * 12 + ships4 * 15 + ships5 * 18;
        fullnessLabel.setText(fullness + "%");
        fullnessBar.setProgress((double) fullness / 100);

        if (ships1 == 0) {
            sh1min.setDisable(true);
        }
        if (ships2 == 0) {
            sh2min.setDisable(true);
        }
        if (ships3 == 0) {
            sh3min.setDisable(true);
        }
        if (ships4 == 0) {
            sh4min.setDisable(true);
        }
        if (ships5 == 0) {
            sh5min.setDisable(true);
        }
        toMenuBut.setDisable(fullness <= 0);

        Preview0.setText(String.valueOf(ships1));
        Preview1.setText(String.valueOf(ships2));
        Preview2.setText(String.valueOf(ships3));
        Preview3.setText(String.valueOf(ships4));
        Preview4.setText(String.valueOf(ships5));

    }


    @FXML
    void firstStart() {

        if (!firstStart) {
            return;
        }
        firstStart = false;
        initializeBut.setVisible(false);
        RotateButton.setText("");
        //grid.setStyle("-fx-background-image: url('Water.png')");


        totalShips = ships1 + 2 * ships2 + 3 * ships3 + 4 * ships4 + 5 * ships5;
        System.out.println("total ships =" + totalShips);

        for (int i = 0; i < 5; i++) {
            statsArr.add(i, 0.0);
        }


        /** Pocet lodi*/
        shipNumberList.add(0, ships1);
        shipNumberList.add(1, ships2);
        shipNumberList.add(2, ships3);
        shipNumberList.add(3, ships4);
        shipNumberList.add(4, ships5);

        for (int i = 0; i < shipNumberList.size(); i++) {
            AIShipNumberList.add(i, shipNumberList.get(i));
        }


        /** vytvoreni tlacitek na gridu */
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Button PlaceBut = new Button("  ");   //"Button " + x + " " + y
                int finalX = x;
                int finalY = y;

                /** Button Style  */
                PlaceBut.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: transparent;");
                PlaceBut.setOnMouseEntered(e -> PlaceBut.setStyle("-fx-background-color: #92f238; -fx-opacity: 0.7;"));
                PlaceBut.setOnMouseExited(e -> PlaceBut.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: transparent;"));

                PlaceBut.setPrefHeight(grid.getWidth());
                PlaceBut.setPrefWidth(grid.getWidth());
                /** Button event */
                PlaceBut.setOnAction(e -> {
                    currentCoordinates.set(finalX, finalY);
                    CreateShip();
                });
                GridPane.setHalignment(PlaceBut, HPos.CENTER);
                GridPane.setValignment(PlaceBut, VPos.CENTER);
                grid.add(PlaceBut, x, y);
            }
        }


        /** Vytvoreni tlacitek na "enemy gridu" */
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Button ShootBut = new Button("  ");   //"Button " + x + " " + y

                int finalX = x;
                int finalY = y;


                /** Button Style  */

                ShootBut.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: transparent;");
                ShootBut.setOnMouseEntered(e -> ShootBut.setStyle("-fx-background-image: url('target.png'); -fx-background-color:transparent; -fx-background-size: cover; -fx-opacity: 0.8;"));
                ShootBut.setOnMouseExited(e -> ShootBut.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: transparent;"));

                ShootBut.setPrefHeight(grid.getWidth());
                ShootBut.setPrefWidth(grid.getWidth());

                /** Button event */
                ShootBut.setOnAction(e -> {
                    currentCoordinates.set(finalX, finalY);
                    BattleController();
                });
                GridPane.setHalignment(ShootBut, HPos.CENTER);
                GridPane.setValignment(ShootBut, VPos.CENTER);
                enemyGrid.add(ShootBut, x, y);
            }
        }

        enemyGrid.setDisable(true);
        Start();
    }


    @FXML
    void Start() {

        spy_button.setDisable(true);


        //after battle change
        enemyPane.setVisible(false);
        spy_button.setVisible(false);
        statsBut.setVisible(false);

        //Rotate button CSS
        if (rotated) {
            RotateButton.setStyle("-fx-background-image: url('rotateShip.png'); -fx-background-size: contain; -fx-rotate: 0; -fx-background-repeat: no-repeat; -fx-background-position: center; -fx-background-color:  #70d1ef ");
        } else
            RotateButton.setStyle("-fx-background-image: url('rotateShip.png'); -fx-background-size: contain; -fx-rotate: 90; -fx-background-repeat: no-repeat; -fx-background-position: center; -fx-background-color:  #70d1ef  ");

        /** Nastavi pocet lodi*/
        shipNumberList.set(0, ships1);
        shipNumberList.set(1, ships2);
        shipNumberList.set(2, ships3);
        shipNumberList.set(3, ships4);
        shipNumberList.set(4, ships5);


        for (int i = 0; i < shipNumberList.size(); i++) {
            AIShipNumberList.set(i, shipNumberList.get(i));
        }


        Random r = new Random();
        xOffset = r.nextInt(0, 2);
        yOffset = r.nextInt(0, 2);


        Refresh();
    }


    @FXML
    protected void Rotate() {
        RotateButton.setText("");
        rotated = !rotated;
        if (rotated) {
            RotateButton.setStyle("-fx-background-image: url('rotateShip.png'); -fx-background-size: contain; -fx-rotate: 0; -fx-background-repeat: no-repeat; -fx-background-position: center; -fx-background-color:  #70d1ef ");
        } else
            RotateButton.setStyle("-fx-background-image: url('rotateShip.png'); -fx-background-size: contain; -fx-rotate: 90; -fx-background-repeat: no-repeat; -fx-background-position: center; -fx-background-color:  #70d1ef ");


    }


    public void SelectShip(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        char number = sourceButton.getId().charAt(6);
        shipLength = Character.getNumericValue(number);
    }

    public void CreateShip() {

        if (AIon) {
            tempGrid = enemyGrid;
            tempShipNumberList = AIShipNumberList;
            tempBlockedList = AIBlockedList;
            tempShipPosition = AIShipPosition;
        } else {
            tempGrid = grid;
            tempShipNumberList = shipNumberList;
            tempBlockedList = blockedList;
            tempShipPosition = shipPosition;
        }

        ShipBuilder.CheckPosition(tempShipNumberList, tempBlockedList);
        if (ShipBuilder.error == 0) {
            ShipBuilder.BuildShip(AIon);
        }

        /** Vrácení hodnot do daných listů*/
        if (AIon) {
            enemyGrid = tempGrid;
            AIBlockedList = tempBlockedList;
            AIShipNumberList = tempShipNumberList;
            AIShipPosition = tempShipPosition;
        } else {
            grid = tempGrid;
            blockedList = tempBlockedList;
            shipNumberList = tempShipNumberList;
            shipPosition = tempShipPosition;
        }
        Refresh();
    }


    @FXML
    public void Switch() {
        visible = !visible;
        int totalShips = 0;

        if (visible) {
            for (int i = 0; i < AIShipPosition.size(); i++) {
                for (int j = 0; j < AIShipPosition.get(i).size(); j++) {

                    ImageView indikator = new ImageView("Indicator.png");
                    indikator.setFitHeight((tempGrid.getPrefHeight() / tempGrid.getColumnCount()) * 0.5);
                    indikator.setFitWidth((tempGrid.getPrefWidth() / tempGrid.getRowCount()) * 0.5);
                    indikator.setStyle("-fx-background-color:transparent; -fx-background-size: cover; -fx-opacity: 1;");
                    GridPane.setValignment(indikator, VPos.CENTER);
                    GridPane.setHalignment(indikator, HPos.CENTER);
                    enemyGrid.add(indikator, AIShipPosition.get(i).get(j).x, AIShipPosition.get(i).get(j).y);
                }
            }
        } else {

            for (int i = 0; i < AIShipPosition.size(); i++) {
                totalShips = totalShips + AIShipPosition.get(i).size();

            }
            enemyGrid.getChildren().remove(row * row + 1 + blockedList.size() + PlayerSunken, row * row + 1 + totalShips + blockedList.size() + PlayerSunken);
        }
    }


    @FXML
    public void Refresh() {

        Feedback.setText(FeedbackString);
        if (!grid.isDisabled()) {
            Ships1.setText(String.valueOf(shipNumberList.get(0)));
            Ships2.setText(String.valueOf(shipNumberList.get(1)));
            Ships3.setText(String.valueOf(shipNumberList.get(2)));
            Ships4.setText(String.valueOf(shipNumberList.get(3)));
            Ships5.setText(String.valueOf(shipNumberList.get(4)));
        }
        FeedbackString = "";
    }

    @FXML
    public void ClearBoard() {

        grid.getChildren().remove(row * row + 1, grid.getChildren().size());
        blockedList.clear();
        AIBlockedList.clear();

        for (int i = 0; i < shipPosition.size(); i++) {
            shipPosition.get(i).clear();
        }

        Start();
    }

    static int sunkPos = -1;


    void SwitchTemp(boolean back) {

        if (!back) {
            if (AIon) {
                tempGrid = grid;
                tempShipPosition = shipPosition;
                tempBlockedList = AIBlockedList;
                tempSunken = AISunken;

            } else {
                tempGrid = enemyGrid;
                tempShipPosition = AIShipPosition;
                tempBlockedList = blockedList;
                tempSunken = PlayerSunken;
            }
        } else {
            /** Vrácení hodnot do daných listů*/
            if (AIon) {
                grid = tempGrid;
                shipPosition = tempShipPosition;
                AIBlockedList = tempBlockedList;
                AISunken = tempSunken;

            } else {
                enemyGrid = tempGrid;
                AIShipPosition = tempShipPosition;
                blockedList = tempBlockedList;
                PlayerSunken = tempSunken;

            }
        }
    }


    void ShootShip() {


        SwitchTemp(false);


        /** Spies order in grid*/
        if (visible) {
            Switch();
            BattleHandler.Shoot(tempShipPosition, AIon);
            Switch();
        } else BattleHandler.Shoot(tempShipPosition, AIon);


        //zmena zbyvajicich lodi a statistik
        if (sunkPos != -1) {
            //System.out.println("potopeno velikost " + tempShipPosition.get(sunkPos).size() + " hrace " + AIon);
            if (AIon) {
                switch (tempShipPosition.get(sunkPos).size()) {
                    case 1 -> {
                        Ships1.setText(String.valueOf(Integer.parseInt(Ships1.getText()) - 1));
                    }
                    case 2 -> {
                        Ships2.setText(String.valueOf(Integer.parseInt(Ships2.getText()) - 1));
                    }
                    case 3 -> {
                        Ships3.setText(String.valueOf(Integer.parseInt(Ships3.getText()) - 1));
                    }
                    case 4 -> {
                        Ships4.setText(String.valueOf(Integer.parseInt(Ships4.getText()) - 1));
                    }
                    case 5 -> {
                        Ships5.setText(String.valueOf(Integer.parseInt(Ships5.getText()) - 1));
                    }
                }
            } else {
                switch (tempShipPosition.get(sunkPos).size()) {
                    case 1 -> {
                        Ships1AI.setText(String.valueOf(Integer.parseInt(Ships1AI.getText()) - 1));
                    }
                    case 2 -> {
                        Ships2AI.setText(String.valueOf(Integer.parseInt(Ships2AI.getText()) - 1));
                    }
                    case 3 -> {
                        Ships3AI.setText(String.valueOf(Integer.parseInt(Ships3AI.getText()) - 1));
                    }
                    case 4 -> {
                        Ships4AI.setText(String.valueOf(Integer.parseInt(Ships4AI.getText()) - 1));
                    }
                    case 5 -> {
                        Ships5AI.setText(String.valueOf(Integer.parseInt(Ships5AI.getText()) - 1));
                    }
                }

            }
        }
        sunkPos = -1;


        SwitchTemp(true);

        Refresh();

    }


    @FXML
    void StartBattle() {

        if (tempShipNumberList.size() < 4) {
            FeedbackString = "Vyberte velikost lodě a kliknete na políčko modré desky";
            Refresh();
            return;
        }

        int nepostaveneLode = 0;

        for (int num = 4; num >= 0; num--) {
            nepostaveneLode += tempShipNumberList.get(num);
        }

        System.out.println(nepostaveneLode);
        if (nepostaveneLode != 0) {
            FeedbackString = "Musíte postavit všechny lodě na desku";
            Refresh();
            return;
        }


        AIon = true;

        RandomBuild();

        blockedList.clear();
        AIBlockedList.clear();

        AIon = false;
        spy_button.setDisable(!spyMode);


        /**prechod z build modu do battle modu*/

        enemyGrid.setDisable(false);
        statsBut.setVisible(true);
        spy_button.setVisible(true);
        enemyPane.setVisible(true);

        RotateButton.setVisible(false);
        grid.setDisable(true);
        buildBox.setVisible(false);

        Ships1.setText(String.valueOf((ships1)));
        Ships2.setText(String.valueOf((ships2)));
        Ships3.setText(String.valueOf((ships3)));
        Ships4.setText(String.valueOf((ships4)));
        Ships5.setText(String.valueOf((ships5)));

        Ships1AI.setText(String.valueOf((ships1)));
        Ships2AI.setText(String.valueOf((ships2)));
        Ships3AI.setText(String.valueOf((ships3)));
        Ships4AI.setText(String.valueOf((ships4)));
        Ships5AI.setText(String.valueOf((ships5)));
    }


    @FXML
    void RandomBuild() {
        Random r = new Random();

        if (AIon) {
            tempShipNumberList = AIShipNumberList;
        } else {
            tempShipNumberList = shipNumberList;
        }

        for (int num = 4; num >= 0; num--) {
            while (tempShipNumberList.get(num) != 0) {
                rotated = r.nextBoolean();
                shipLength = num + 1;
                currentCoordinates.x = r.nextInt(10);
                currentCoordinates.y = r.nextInt(10);
                CreateShip();
            }
        }
        if (AIon) {
            AIShipNumberList = tempShipNumberList;
        } else {
            shipNumberList = tempShipNumberList;
        }
    }


    void BattleController() {

        //PLAYER TURN
        ShootShip();
        if (tryAgain) {
            return;
        }
        statsArr.set(4, statsArr.get(4) + 1);

        //WIN CHECK
        if (result != 0) {
            System.out.println(result);
            enemyGrid.setDisable(true);
            ToStats();
            return;
        }

        //AI TURN
        AIon = true;
        tryAgain = true;
        while (tryAgain) {
            SwitchTemp(false);
            BotAlgorithm.BotAttack();
            SwitchTemp(true);
            ShootShip();
        }

        //SET TO DEFAULT
        AIon = false;
        statsArr.set(4, statsArr.get(4) + 1);
        System.out.println(statsArr);


        //WIN CHECK
        if (result != 0) {
            System.out.println(result);
            ToStats();
            enemyGrid.setDisable(true);
        }
    }


    public static Boolean ContainsThisCoords(ArrayList<coordinates> coordsArray, coordinates finding) {
        for (shipspack.ships.coordinates coordinates : coordsArray) {
            if (finding.x == coordinates.x && finding.y == coordinates.y) {
                return true;
            }
        }
        return false;
    }


    @FXML
    public void SpymodeBool() {
        spyMode = !spyMode;
        System.out.println(spyMode);
    }


    public void CloseApp() {
        System.exit(0);
    }

}

