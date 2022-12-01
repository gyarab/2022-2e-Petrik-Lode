package shipspack.ships;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class ShipBuilder extends ShipsController {
//int x, int y, int shipLength, Boolean rotated, Boolean AI


    static int error;

    public static void CheckPosition(ArrayList<Integer> tempShipNumberList, ArrayList<coordinates> tempBlockedList) {
        error = 0;
        int freeSpace = 0;
        boolean blocked = false;


        if (tempShipNumberList.get(shipLength - 1) == 0) {
            error = 1;
            FeedbackString = "Použity veškeré lodě tohoto typu";
            return;
        }


        /** kontrola pred polozenim lode */
        if (rotated) {
            if (currentCoordinates.x + shipLength > row) {
                //System.out.println("Lod nelze polozit mimo hraci desku");
                FeedbackString = "Lod nelze polozit mimo hraci desku";
                error = 2;
                return;
            } else {


                for (int j = 0; j < shipLength; j++) {
                    for (int i = 0; i < tempBlockedList.size(); i++) {
                        if (tempBlockedList.get(i).x == currentCoordinates.x + j && tempBlockedList.get(i).y == currentCoordinates.y) {
                            blocked = true;
                            //System.out.println("Lod nelze polozit vedle jinych lodi");
                            FeedbackString = "Lod nelze polozit vedle jinych lodi";
                            error = 3;
                            break;
                        } else {
                            blocked = false;
                        }
                    }
                    if (!blocked) {
                        freeSpace++;
                    }
                }
                if (tempBlockedList.size() == 0) {
                    freeSpace = shipLength;
                }
            }
        } else {
            if (currentCoordinates.y + shipLength > row) {
                //System.out.println("mimo pole");
                FeedbackString = "Lod nelze polozit mimo hraci desku";
                error = 2;
                return;
            } else {

                /**  Kontrola        */
                for (int j = 0; j < shipLength; j++) {
                    for (int i = 0; i < tempBlockedList.size(); i++) {
                        if (tempBlockedList.get(i).x == currentCoordinates.x && tempBlockedList.get(i).y == currentCoordinates.y + j) {
                            blocked = true;
                            //System.out.println("Lod nelze polozit vedle jinych lodi");
                            FeedbackString = "Lod nelze polozit vedle jinych lodi";
                            error = 3;
                            break;
                        } else {
                            blocked = false;
                        }
                    }
                    if (!blocked) {
                        freeSpace++;
                    }
                }
                if (tempBlockedList.size() == 0) {
                    freeSpace = shipLength;
                }
            }
        }

        if (freeSpace == shipLength) {
            tempShipNumberList.set(shipLength - 1, tempShipNumberList.get(shipLength - 1) - 1);
            error = 0;

        }
    }

    public static void BuildShip(Boolean AIon) {

        /** Vytvoreni zablokovanych poli */
        for (int g = 0; g < 3; g++) {
            for (int j = 0; j < (shipLength + 2); j++) {

                /** blokace policka mimo desku*/
                if (!rotated) {
                    int temp = g;
                    g = j;
                    j = temp;
                }
                    if ((currentCoordinates.x - 1 + j) <= row - 1 && (currentCoordinates.x - 1 + j) >= 0 && (currentCoordinates.y - 1 + g) <= row - 1 && (currentCoordinates.y - 1 + g) >= 0)
                    {
                        tempBlockedList.add(new coordinates(currentCoordinates.x - 1 + j, currentCoordinates.y - 1 + g));
                        if (!AIon) {
                            /** indikator blokace */
                            Rectangle rect = new Rectangle(5, 5);
                            rect.setFill(Color.RED);
                            GridPane.setHalignment(rect, HPos.CENTER);
                            GridPane.setValignment(rect, VPos.CENTER);
                            tempGrid.add(rect, currentCoordinates.x - 1 + j, currentCoordinates.y - 1 + g);
                        }
                    }

                if (!rotated) {
                    int temp = j;
                    j = g;
                    g = temp;
                }

            }
        }


        ArrayList<coordinates> thisShipCoords = new ArrayList<>();

        /** Vytvorení policek lodi*/
        for (int i = 0; i < shipLength; i++) {
            if (rotated) {
                thisShipCoords.add(new coordinates(currentCoordinates.x + i, currentCoordinates.y));
            } else thisShipCoords.add(new coordinates(currentCoordinates.x, currentCoordinates.y + i));
        }

        tempShipPosition.add(thisShipCoords);

        if (!AIon) {
            /** Vytvoreni spritu */
            String s = "ImgShip" + shipLength;
            if (!rotated) s += "R.png";
            else s += ".png";
            int x = 40;
            int y = 26;
            Image imgShip5 = new Image(s);
            ImageView imageView = new ImageView(imgShip5);

            if (rotated) {
                imageView.setFitWidth(x * shipLength); //182
                imageView.setFitHeight(y + 4 * shipLength);
                tempGrid.add(imageView, currentCoordinates.x, currentCoordinates.y);
            } else {
                imageView.setFitHeight(x * shipLength); //182
                imageView.setFitWidth(y + 4 * shipLength);
                tempGrid.add(imageView, currentCoordinates.x, currentCoordinates.y + (shipLength - 1) / 2);
            }
        }

    }

}







