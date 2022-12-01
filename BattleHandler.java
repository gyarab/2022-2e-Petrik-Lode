package shipspack.ships;

import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashSet;

import static shipspack.ships.BotAlgorithm.AfterHit;
import static shipspack.ships.BotAlgorithm.AfterSunk;

public class BattleHandler extends ShipsController {
    static boolean wasHit;
    static boolean destroyShip;

    static void Shoot(ArrayList<ArrayList<coordinates>> tempShipPosition, boolean AIon) {
        //System.out.println("destroyShip = " + destroyShip);

        tryAgain = true;
        wasHit = false;

        if (ContainsThisCoords(tempBlockedList, currentCoordinates)) {
            FeedbackString = "Nelze znovu strilet na zasazena pole";
            return;
        }
        tryAgain = false;


        /** Kontrola zásahu*/
        for (int i = 0; i < tempShipPosition.size(); i++) {
            for (int j = 0; j < tempShipPosition.get(i).size(); j++) {
                if (tempShipPosition.get(i).get(j).x == currentCoordinates.x && tempShipPosition.get(i).get(j).y == currentCoordinates.y) {

                    wasHit = true;
                    break;
                }
            }
        }
        tempBlockedList.add(new coordinates(currentCoordinates.x, currentCoordinates.y));


        if (wasHit) {
            //
            if(AIon){AfterHit();}


            FeedbackString = "Zásah nepřátelké lodě!";
            ImageView hit = new ImageView("Hit.png");
            hit.setFitHeight(tempGrid.getPrefHeight() / tempGrid.getColumnCount());
            hit.setFitWidth(tempGrid.getPrefWidth() / tempGrid.getRowCount());
            hit.setStyle("-fx-background-color:transparent; -fx-background-size: cover; -fx-opacity: 1;");
            tempGrid.add(hit, currentCoordinates.x, currentCoordinates.y);

            tryAgain = true;

            int sunkNum = 0;
            int Num = -1;
            boolean sunken = false;

            /**Vyhledání pozice arraylistu v arraylistu a potopeni lode*/
            for (int i = 0; i < tempShipPosition.size(); i++) {
                for (int j = 0; j < tempShipPosition.get(i).size(); j++) {
                    if (currentCoordinates.x == tempShipPosition.get(i).get(j).x && currentCoordinates.y == tempShipPosition.get(i).get(j).y) {
                        Num = i;
                        break;
                    }
                }
            }


            for (int k = 0; k < tempShipPosition.get(Num).size(); k++) {
                for (int l = 0; l < tempBlockedList.size(); l++) {
                    //System.out.println(tempShipPosition.get(Num).get(k).toString()+ " "+ tempBlockedList.toString());
                    if (tempBlockedList.get(l).x == tempShipPosition.get(Num).get(k).x && tempBlockedList.get(l).y == tempShipPosition.get(Num).get(k).y) {
                        sunkNum++;
                        if (sunkNum == tempShipPosition.get(Num).size()) {
                            sunken = true;
                        }
                    }
                }
            }


            if (sunken) {
                if(AIon){AfterSunk();}

                FeedbackString = "Potopená loď!";


                tempSunken += tempShipPosition.get(Num).size();

                for (int i = 0; i < tempShipPosition.get(Num).size(); i++) {

                    ImageView sunk = new ImageView("Sunk.png");
                    sunk.setFitHeight(tempGrid.getPrefHeight() / tempGrid.getColumnCount());
                    sunk.setFitWidth(tempGrid.getPrefWidth() / tempGrid.getRowCount());
                    sunk.setStyle("-fx-background-color:transparent; -fx-background-size: cover; -fx-opacity: 1;");
                    tempGrid.add(sunk, tempShipPosition.get(Num).get(i).x, tempShipPosition.get(Num).get(i).y);
                    VictoryCheck();
                }

            }

        } else {
            FeedbackString = "Nebyla trefena žádná loď.";
            ImageView miss = new ImageView("Miss.png");
            miss.setFitHeight(tempGrid.getPrefHeight() / tempGrid.getColumnCount());
            miss.setFitWidth(tempGrid.getPrefWidth() / tempGrid.getRowCount());
            miss.setStyle("-fx-background-color:transparent; -fx-background-size: cover; -fx-opacity: 1;");
            tempGrid.add(miss, currentCoordinates.x, currentCoordinates.y);

        }
    }


    static void VictoryCheck() {

        if (tempSunken == totalShips) {
            FeedbackString = "Vítězství";
        }
    }
}












