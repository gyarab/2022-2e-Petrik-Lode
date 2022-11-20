module shipspack.ships {
    requires javafx.controls;
    requires javafx.fxml;


    opens shipspack.ships to javafx.fxml;
    exports shipspack.ships;
}