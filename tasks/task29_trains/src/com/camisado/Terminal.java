package com.camisado;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.*;

public class Terminal extends Application {

    void initdata() {
        railway = new Railway();
        Station kyiv = railway.addStation("Kyiv", new Station.Coordinates(0,0));
        Station lviv = railway.addStation("Lviv", new Station.Coordinates(0,0));
        Station kharkiv = railway.addStation("Kharkiv", new Station.Coordinates(0,0));
        Station odesa = railway.addStation("Odesa", new Station.Coordinates(0,0));
        Station dnipro = railway.addStation("Dnipro", new Station.Coordinates(0,0));
        Station uman = railway.addStation("Uman", new Station.Coordinates(0,0));
        railway.addConnection(kyiv, uman, 350);
        railway.addConnection(uman, odesa, 400);
        railway.addConnection(dnipro, kharkiv, 200);
        railway.addConnection(dnipro, odesa, 400);
        railway.addConnection(lviv, kyiv, 500);
        railway.addConnection(dnipro, kyiv, 400);
        railway.addConnection(dnipro, uman, 400);

        PassengerCarriageBuilder builderEconomy = new PassengerCarriageBuilder(70, 30, PassengerCarriage.CarriageType.economy);
        PassengerCarriageBuilder builderCompartment = new PassengerCarriageBuilder(50, 85, PassengerCarriage.CarriageType.compartment);
        PassengerCarriageBuilder builderSeat = new PassengerCarriageBuilder(120, 45, PassengerCarriage.CarriageType.seat);

        LocalDateTime start = LocalDateTime.of(2018, 11, 20, 9,30,0);

        List<List<Railway.Point>> routes = new ArrayList<>();
        routes.add(new ArrayList<>(
                Arrays.asList(
                        Railway.Point.of(lviv, start.plusHours(1), start.plusHours(2)),
                        Railway.Point.of(kyiv, start.plusHours(3), start.plusHours(4)),
                        Railway.Point.of(dnipro, start.plusHours(5), start.plusHours(6)),
                        Railway.Point.of(kharkiv, start.plusHours(7), start.plusHours(8))
                )
        ));

        routes.add(new ArrayList<>(
                Arrays.asList(
                        Railway.Point.of(odesa, start.plusHours(1), start.plusHours(2)),
                        Railway.Point.of(uman, start.plusHours(3), start.plusHours(4)),
                        Railway.Point.of(dnipro, start.plusHours(5), start.plusHours(6)),
                        Railway.Point.of(kyiv, start.plusHours(7), start.plusHours(8))
                )
        ));


        List<List<Carriage>> carriages = new ArrayList<>();
        builderCompartment.addPhone();
        builderSeat.addTV();

        carriages.add(new ArrayList<>(
                Arrays.asList(
                        builderEconomy.build(1),
                        builderCompartment.build(2),
                        builderSeat.build(3)
                )
        ));

        builderCompartment.addPhone();
        builderCompartment.addTV();

        carriages.add(new ArrayList<>(
                Arrays.asList(
                        builderEconomy.build(4),
                        builderCompartment.build(5),
                        builderSeat.build(6)
                )
        ));


        for (int i=0; i < 2; ++i) {
            Train train = new Train(
                    i,
                    railway,
                    routes.get(i),
                    carriages.get(i)
            );

            trains.add(new Train(train));
        }
        fromBox.getItems().addAll(railway.getStations());
        toBox.getItems().addAll(railway.getStations());
    }

    class CarriageHandler implements EventHandler<ActionEvent> {
        Station from;
        Station to;
        Train train;
        AbstractPassengerCarriage carriage;
        Button button;

        public CarriageHandler(Station from, Station to, Train train, Button button, AbstractPassengerCarriage carriage) {
            this.carriage=carriage;
            this.button=button;
            this.from=from;
            this.to=to;
            this.train=train;
            this.carriage=carriage;
        }

        @Override
        public void handle(ActionEvent event) {
            carriageDesc.setText(carriage.toString()+"price: " + carriage.price(from, to)/100+"UAH\n");
            buyButton.setOnAction(event1 -> {
                if (carriage==null) {
                    successInfo.setText("Not enough data to buy ticket");
                    return;
                }
                boolean success = carriage.reservePlace();
                if (success)
                    successInfo.setText("Successfully bought ticket.");
                else
                    successInfo.setText("Not enough tickets.");
                button.setText("№"+Integer.toString(carriage.number) + "("+Integer.toString(carriage.getPlacesLeft())+" left)");
            });
        }
    }

    class TrainHandler implements EventHandler<ActionEvent>
    {
        Station from;
        Station to;
        Train train;

        public TrainHandler(Station from, Station to, Train train) {
            this.train=train;
            this.from=from;
            this.to=to;
        }

        @Override
        public void handle (ActionEvent e){
            vBoxAdd.setManaged(true);
//            vBoxAdd.getChildren().add(new Label(train.getFullDesc()));
            List<AbstractPassengerCarriage> carriages = train.getAvailableCarriages();
            if (carriages.size()==0)
                carriagesInfo.setText("No places left");
            else {
                carriagesInfo.setText("Select carriage");
            }
            vBoxAdd.setVisible(true);
            carriagesPane.getChildren().clear();
            int i = 0;
            for (AbstractPassengerCarriage carriage : carriages) {
                Button cButton = new Button("№"+Integer.toString(carriage.number) + "("+Integer.toString(carriage.getPlacesLeft())+" left)");
                carriagesPane.add(cButton, i % 4, i / 4);
                ++i;
                cButton.setOnAction(new CarriageHandler(from,to,train,cButton, carriage));
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initdata();
        tmp.prefWidthProperty().bind(primaryStage.widthProperty());
        tmp.prefHeightProperty().bind(primaryStage.heightProperty());
        tmp.setAlignment(Pos.CENTER);
        root.getChildren().add(tmp);
        tmp.getChildren().add(gridPane);
        tmp.getChildren().add(buyButton);
        tmp.getChildren().add(successInfo);
        buyButton.setMinHeight(20);
        buyButton.setMinWidth(100);
        buyButton.setOnAction(e-> successInfo.setText("Not enough data to buy ticket"));
        gridPane.prefWidthProperty().bind(tmp.prefWidthProperty());
        gridPane.hgapProperty().bind(tmp.prefWidthProperty().divide(8));

        gridPane.setAlignment(Pos.TOP_CENTER);
        tmp.setBackground(new Background(new BackgroundFill(new Color(0.5,0.6, 0.8, 1), null, null)));
        gridPane.add(vBoxLeft, 0,0);
        gridPane.add(vBoxRight,1,0);
        gridPane.add(vBoxAdd,2,0);
        vBoxLeft.setPadding(new Insets(10, 30, 10, 30));
        vBoxLeft.setSpacing(20);
        vBoxLeft.getChildren().add(new Label("From:"));
        vBoxLeft.getChildren().add(fromBox);
        vBoxLeft.getChildren().add(new Label("To:"));
        vBoxLeft.getChildren().add(toBox);
        vBoxLeft.getChildren().add(new Label("Select date:"));
        vBoxLeft.getChildren().add(datePicker);

        findButton.setOnAction(e -> {
            trainsBox.getChildren().clear();
            Station from = fromBox.getSelectionModel().getSelectedItem();
            Station to = toBox.getSelectionModel().getSelectedItem();
            ArrayList<Train> trains = from.findAvailableTrains(datePicker.getValue(), to);
            if (trains ==null|| trains.size()==0) {
                scrollPane.setVisible(false);
                vBoxAdd.setVisible(false);
                trainsInfo.setText("Trains not found.");
                return;
            }
            else {
                scrollPane.setVisible(true);
                trainsInfo.setText("Trains found: " + trains.size());
            }
            for (Train train: trains) {
                Button trainButton = new Button(train.toString() + "trip duration: " + train.getDuration(from, to) + "\n");
                trainButton.setOnAction(new TrainHandler(from, to, train));
                scrollPane.prefWidthProperty().bind(trainButton.prefWidthProperty());
                trainsBox.getChildren().add(trainButton);
            }
        });

        vBoxLeft.prefHeightProperty().bind(tmp.prefHeightProperty().divide(3));
        vBoxLeft.prefWidthProperty().bind(tmp.prefWidthProperty().divide(3));
        vBoxRight.prefHeightProperty().bind(tmp.prefHeightProperty().divide(3));
        vBoxRight.prefWidthProperty().bind(scrollPane.prefWidthProperty());
        vBoxAdd.prefHeightProperty().bind(tmp.prefHeightProperty().divide(3));
        vBoxAdd.prefWidthProperty().bind(tmp.prefWidthProperty().divide(3));
        vBoxLeft.setAlignment(Pos.CENTER);
        vBoxRight.setAlignment(Pos.CENTER);
        vBoxAdd.setAlignment(Pos.CENTER);
        vBoxLeft.getChildren().add(findButton);
        vBoxLeft.getChildren().add(trainsInfo);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setContent(trainsBox);
        scrollPane.setVisible(false);
        vBoxRight.getChildren().add(scrollPane);
        vBoxAdd.getChildren().add(carriagesInfo);
        vBoxAdd.getChildren().add(carriagesPane);
        vBoxAdd.getChildren().add(carriageDesc);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Trains");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    Group root = new Group();

    final private int WIDTH = 1000;
    final private int HEIGHT = 600;

    ScrollPane scrollPane = new ScrollPane();
    Button findButton = new Button("Find available trains");
    Button buyButton = new Button("Buy");
    ComboBox<Station> fromBox = new ComboBox<>();
    ComboBox<Station> toBox = new ComboBox<>();
    ArrayList<Train> trains = new ArrayList<>();
    Label trainsInfo = new Label();
    Label carriagesInfo = new Label();
    Label carriageDesc = new Label();
    Label successInfo = new Label();
    VBox vBoxLeft = new VBox();
    VBox vBoxRight = new VBox();
    VBox vBoxAdd = new VBox();
    VBox trainsBox = new VBox();
    DatePicker datePicker = new DatePicker();
    GridPane gridPane = new GridPane();
    VBox tmp = new VBox();
    GridPane carriagesPane = new GridPane();
    Railway railway;
}
