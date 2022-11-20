package shipspack.ships;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class BotAlgorithm extends BattleHandler {

    static coordinates Pivot = new coordinates(-1,-1);
    static coordinates Pivot2 = new coordinates(-1, -1);
    static coordinates Offset = new coordinates(0, 0);

    static boolean left;
    static boolean right;
    static boolean up;
    static boolean down;


    static void BotAttack() {
        if (destroyShip) {
            DestroyShip();

            return;
        }


        RandomGuess();


    }

    static void RandomGuess() {
        Random r = new Random();
        currentCoordinates.x = r.nextInt(0, 10);
        currentCoordinates.y = r.nextInt(0, 10);
        System.out.println(currentCoordinates.toString());
    }

    static void DestroyShip() {

        /** Krok II.b - oznacit pivot 2 */
        if (Pivot2.x == -1 && Pivot2.y == -1 && Pivot.x != -1 && Pivot.y != -1 && wasHit) {
            Pivot2.set(currentCoordinates.x, currentCoordinates.y);
        }


        /** Krok III. - strilet ve sneru lode */
        if (Pivot2.x != -1 && Pivot2.y != -1 && Pivot.x != -1 && Pivot.y != -1) {

            if (Pivot2.x - Pivot.x != 0) {
                // poradi
                if (Pivot2.x > Pivot.x) {
                    //left
                    /** zmena poradi pivotů */
                    coordinates temp = new coordinates(Pivot2.x, Pivot2.y);
                    System.out.println("provedena zmena - X");
                    Pivot2.set(Pivot.x, Pivot.y);
                    Pivot.set(temp.x, temp.y);

                }
                if (Pivot2.x < Pivot.x) {
                    //left

                    if (!ContainsThisCoords(tempBlockedList, new coordinates(Pivot2.x - 1, Pivot2.y)) && Pivot2.x - 1 != -1) {
                        //Pivot2.set(Pivot2.x - 1, Pivot2.y);
                        currentCoordinates.set(Pivot2.x - 1, Pivot2.y);
                        return;

                    }
                    //right

                    if (!ContainsThisCoords(tempBlockedList, new coordinates(Pivot.x + 1, Pivot.y)) && Pivot.x + 1 != 10) {
                        //Pivot.set(Pivot.x + 1, Pivot.y);
                        currentCoordinates.set(Pivot.x + 1, Pivot.y);
                        return;

                    }
                }


            } else {
                /** zmena poradi pivotů */
                if (Pivot2.y > Pivot.y) {
                    coordinates temp = new coordinates(Pivot2.x, Pivot2.y);
                    System.out.println("provedena zmena - Y");
                    Pivot2.set(Pivot.x, Pivot.y);
                    Pivot.set(temp.x, temp.y);
                }

                if (Pivot2.y < Pivot.y) {
                    //up
                    if (!ContainsThisCoords(tempBlockedList, new coordinates(Pivot2.x, Pivot2.y - 1)) && Pivot2.y - 1 != -1) {
                        currentCoordinates.set(Pivot2.x, Pivot2.y - 1);
                        return;
                    }

                    //down
                    if (!ContainsThisCoords(tempBlockedList, new coordinates(Pivot.x, Pivot.y + 1)) && Pivot.y + 1 != 10) {
                        currentCoordinates.set(Pivot.x, Pivot.y + 1);
                        return;
                    }
                }
            }
        }



        /** Krok I. najít pivot */
        if (Pivot.x == -1 && Pivot.y == -1) {
            Pivot.set(currentCoordinates.x, currentCoordinates.y);
        }


        /** Krok II. - najít pivot 2 */
        if (Pivot2.x == -1 && Pivot2.y == -1 && Pivot.x != -1 && Pivot.y != -1) {
            left = false;
            right = false;
            up = false;
            down = false;

            currentCoordinates.set(Pivot.x, Pivot.y);

            /** find 2nd pivot */
            for (int i = 0; i < tempBlockedList.size(); i++) {

                /** right */
                if (currentCoordinates.x + 1 > 9 || (tempBlockedList.get(i).x == currentCoordinates.x + 1 && tempBlockedList.get(i).y == currentCoordinates.y)) {
                    right = true;

                }
                /** left */
                if (currentCoordinates.x - 1 < 0 || (tempBlockedList.get(i).x == currentCoordinates.x - 1 && tempBlockedList.get(i).y == currentCoordinates.y)) {
                    left = true;
                }

                /** up */
                if (currentCoordinates.y - 1 < 0 || (tempBlockedList.get(i).x == currentCoordinates.x && tempBlockedList.get(i).y == currentCoordinates.y - 1)) {
                    up = true;

                }
                /** down */
                if (currentCoordinates.y + 1 > 9 || (tempBlockedList.get(i).x == currentCoordinates.x && tempBlockedList.get(i).y == currentCoordinates.y + 1)) {
                    down = true;

                }
            }

            /** direction */
            if (left && right) {
                if (up) {
                    currentCoordinates.set(Pivot.x, Pivot.y + 1);
                } else {
                    currentCoordinates.set(Pivot.x, Pivot.y - 1);
                }
                return;
            }
            if (up && down) {
                if (left) {
                    currentCoordinates.set(Pivot.x + 1, Pivot.y);
                } else {
                    currentCoordinates.set(Pivot.x - 1, Pivot.y);
                }

                return;
            }

            if ((up && right) || (up && left)) {
                currentCoordinates.set(Pivot.x, Pivot.y + 1);
                return;
            }
            if ((down && right) || (down && left)) {
                currentCoordinates.set(Pivot.x, Pivot.y - 1);
                return;
            }


            if (down || up) {
                currentCoordinates.set(Pivot.x - 1, Pivot.y);
            } else {
                currentCoordinates.set(Pivot.x, Pivot.y + 1);
            }
        }

    }


    public static void AfterHit()
    {
        System.out.println("Hit at currentCoordinates = " + currentCoordinates);
        destroyShip = true;

        // Zmena pivotu na zasahnute pole
        if (Pivot.x != -1 && Pivot.y != -1 && Pivot2.x != -1 && Pivot2.y != -1 ) {
            if (Pivot.x - Pivot2.x != 0) {
                if (currentCoordinates.x - Pivot.x == 1) {
                    Pivot.set(currentCoordinates.x, currentCoordinates.y);
                } else {
                    Pivot2.set(currentCoordinates.x, currentCoordinates.y);
                }
            } else {
                if (currentCoordinates.y - Pivot.y == 1) {
                    Pivot.set(currentCoordinates.x, currentCoordinates.y);
                } else {
                    Pivot2.set(currentCoordinates.x, currentCoordinates.y);
                }
            }
        }
    }

    public static void AfterSunk() {
        /** Krok II.b - DOoznacit pivot 2 */
        if (Pivot2.x == -1 && Pivot2.y == -1 && Pivot.x != -1 && Pivot.y != -1 && wasHit) {
            Pivot2.set(currentCoordinates.x, currentCoordinates.y);
            System.out.println(Pivot2 + " pivot 2 is finaly hereeeeeee");
            System.out.println("Pivot = " + Pivot);
        }



        System.out.println("Pivot AS= " + Pivot);
        System.out.println("Pivot2 AS= " + Pivot2);

        if (Pivot.x - Pivot2.x != 0) {
            shipLength = Pivot.x - Pivot2.x + 1;
            rotated = true;
        } else {
            shipLength = Pivot.y - Pivot2.y + 1;
            rotated = false;
        }


        /** Vytvoreni zablokovanych poli */
        for (int g = 0; g < 3; g++) {
            for (int j = 0; j < (shipLength + 2); j++) {


              if(g== 0 && j == 0){
                    //System.out.println("top left coords - " + new coordinates(Pivot2.x - 1 + j, Pivot2.y - 1 + g));
                }

                if (!rotated) {
                    int temp = g;
                    g = j;
                    j = temp;
                }

                /** blokace policka mimo desku*/
                if ((Pivot2.x - 1 + j) <= row - 1 && (Pivot2.x - 1 + j) >= 0 && (Pivot2.y - 1 + g) <= row - 1 && (Pivot2.y - 1 + g) >= 0) {
                    if (!ContainsThisCoords(tempBlockedList, new coordinates(Pivot2.x - 1 + j, Pivot2.y - 1 + g))) {
                        tempBlockedList.add(new coordinates(Pivot2.x - 1 + j, Pivot2.y - 1 + g));
                        System.out.println("blocking coords - " + new coordinates(Pivot2.x - 1 + j, Pivot2.y - 1 + g));
                    }
                }

                if (!rotated) {
                    int temp = j;
                    j = g;
                    g = temp;
                }

            }

        }

        //reset bot algo values
        destroyShip = false;
        Pivot.set(-1, -1);
        Pivot2.set(-1, -1);
    }

}
