package shipspack.ships;


import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import static shipspack.ships.BotAlgorithm.AfterHit;
import static shipspack.ships.BotAlgorithm.AfterSunk;

public class BattleHandler extends ShipsController {
    static boolean wasHit;
    static boolean destroyShip;

    @FXML
    static void Shoot(ArrayList<ArrayList<coordinates>> tempShipPosition, boolean AIon) {

        tryAgain = true;
        wasHit = false;

        if (ContainsThisCoords(tempBlockedList, currentCoordinates)) {
            FeedbackString = "Nelze znovu střílet na zasažená pole";
            return;
        }
        tryAgain = false;







        if(AIon){statsArr.set(0,statsArr.get(0)+1);}
        else {statsArr.set(1,statsArr.get(1)+1);}


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
            if(AIon){AfterHit();}

            if (AIon){statsArr.set(2, statsArr.get(2)+1);}
            else {statsArr.set(3, statsArr.get(3)+1);}

            FeedbackString = "Zásah nepřátelké lodě!";
            ImageView hit = new ImageView("Hit.png");
            hit.setFitHeight(tempGrid.getPrefHeight() / tempGrid.getColumnCount());
            hit.setFitWidth(tempGrid.getPrefWidth() / tempGrid.getRowCount());
            hit.setStyle("-fx-background-color:transparent; -fx-background-size: cover; -fx-opacity: 1;");
            tempGrid.add(hit, currentCoordinates.x, currentCoordinates.y);

            tryAgain = true;

            int sunkNum = 0;
            int num = -1;
            boolean sunken = false;

            /**Vyhledání pozice arraylistu v arraylistu a potopeni lode*/
            for (int i = 0; i < tempShipPosition.size(); i++) {
                for (int j = 0; j < tempShipPosition.get(i).size(); j++) {
                    if (currentCoordinates.x == tempShipPosition.get(i).get(j).x && currentCoordinates.y == tempShipPosition.get(i).get(j).y) {
                        num = i;
                        break;
                    }
                }
            }


            for (int k = 0; k < tempShipPosition.get(num).size(); k++) {
                for (int l = 0; l < tempBlockedList.size(); l++) {
                    //System.out.println(tempShipPosition.get(Num).get(k).toString()+ " "+ tempBlockedList.toString());
                    if (tempBlockedList.get(l).x == tempShipPosition.get(num).get(k).x && tempBlockedList.get(l).y == tempShipPosition.get(num).get(k).y) {
                        sunkNum++;
                        if (sunkNum == tempShipPosition.get(num).size()) {
                            sunken = true;
                        }
                    }
                }
            }


            if (sunken) {
                if(AIon){AfterSunk();}
                FeedbackString = "Potopená loď!";
                sunkPos = num;

                tempSunken += tempShipPosition.get(num).size();

                for (int i = 0; i < tempShipPosition.get(num).size(); i++) {
                    /** Sprite potopení*/
                    ImageView sunk = new ImageView("Sunk.png");
                    sunk.setFitHeight(tempGrid.getPrefHeight() / tempGrid.getColumnCount());
                    sunk.setFitWidth(tempGrid.getPrefWidth() / tempGrid.getRowCount());
                    sunk.setStyle("-fx-background-color:transparent; -fx-background-size: cover; -fx-opacity: 1;");
                    tempGrid.add(sunk, tempShipPosition.get(num).get(i).x, tempShipPosition.get(num).get(i).y);

                }
                VictoryCheck(AIon);
            }

        } else {
            if(!AIon) {FeedbackString = "Nebyla trefena žádná loď.";}
            ImageView miss = new ImageView("Miss.png");
            miss.setFitHeight(tempGrid.getPrefHeight() / tempGrid.getColumnCount());
            miss.setFitWidth(tempGrid.getPrefWidth() / tempGrid.getRowCount());
            miss.setStyle("-fx-background-color:transparent; -fx-background-size: cover; -fx-opacity: 1;");
            tempGrid.add(miss, currentCoordinates.x, currentCoordinates.y);
        }
    }


    static void VictoryCheck(boolean AIon) {

        if (tempSunken == totalShips && AIon) {
            FeedbackString = "Prohra";
            tryAgain=false;
            result = -1;
        }


        if (tempSunken == totalShips && !AIon ) {
            FeedbackString = "Vítězství";
            tryAgain=false;
            result = 1;
        }
    }
}












