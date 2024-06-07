package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.xml.transform.Source;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Main extends Application {
	boolean selection = false ;
	static AnchorPane leftpane = new AnchorPane();
	Graph graph = new Graph();
	static LinkedList<Country> countries = new LinkedList<Country>();
	static ArrayList<ArrayList<Country>> paths = new ArrayList<ArrayList<Country>>();
	static Text[] texts;
	TextArea ta1 = new TextArea();

	public double findDistance(double lat1, double lon1, double lat2, double lon2) {

		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		return (dist);
	}

	public double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	public double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	public static void read_Countries() throws FileNotFoundException {

		File myObj = new File("country.txt");
	//	System.out.println(myObj.exists());
		if (myObj.exists()) {
			Scanner myReader = new Scanner(myObj);
			myReader.nextLine();
			while (myReader.hasNext()) {
				Country c = new Country(myReader.nextLine());
				countries.add(c);
			//	System.out.println(c);
			}
			myReader.close();
		}

	}

	public static void read_paths() throws FileNotFoundException {

		File myObj = new File("paths.txt");
		//System.out.println(myObj.exists());
		if (myObj.exists()) {
			Scanner myReader = new Scanner(myObj);
			myReader.nextLine();

			while (myReader.hasNext()) {
				ArrayList<Country> temp_paths = new ArrayList<Country>();
				String p[] = myReader.nextLine().split(",");
				// trim
				for (int i = 0; i < p.length; i++) {
					p[i] = p[i].trim();

				}
				// search for index
				for (int j = 0; j < p.length; j++) {
					int x = -1;
					if (Arrays.binarySearch(countries.toArray(), new Country(p[j], 0)) > -1) {
						x = Arrays.binarySearch(countries.toArray(), new Country(p[j], 0));
						temp_paths.add(countries.get(x));
					}

				}
				paths.add(temp_paths);

			}
			//System.out.println(Arrays.deepToString(paths.toArray()));
			;
			myReader.close();
		}

	}

	public static void fillcountries(Circle[] circles) {
		for (int i = 0; i < countries.size(); i++) {
			circles[i] = new Circle(((countries.get(i).lontude) * 2.666666) + 499,
					(-(countries.get(i).latitude) * 2.666666) + 330,

					3);
			texts[i] = new Text(countries.get(i).name);
			texts[i].setFont(new Font(10));
			texts[i].setLayoutX(((countries.get(i).lontude) * 2.666666) +500);
			texts[i].setLayoutY((-(countries.get(i).latitude) * 2.666666) + 330 + 15);

			leftpane.getChildren().addAll(circles[i], texts[i]);
		//	System.out.println(countries.get(i));
//		    System.out.println(linkedList.get(i));
		}
//		leftpane.d

	}

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
//		graph.getShortestPath(start, finish);
		read_Countries();
		read_paths();
		// add vertices
		for (int i = 0; i < countries.size(); i++) {
			graph.addVertex(countries.get(i).name);
		}
		for (int i = 0; i < paths.size(); i++) {
			for (int j = 0; j < paths.get(i).size(); j++) {
				double distance = findDistance(paths.get(i).get(0).latitude, paths.get(i).get(0).lontude,
						paths.get(i).get(j).latitude, paths.get(i).get(j).lontude);
				int dist = (int) Math.round(distance);
				graph.addEdge(paths.get(i).get(0).name, paths.get(i).get(j).name, dist);
			}

		}
///
		texts = new Text[countries.size()];

		try {
			

			SplitPane root = new SplitPane();
			root.setDividerPositions(0.7753510140405616);
			root.setPrefSize(1284, 652);

			ta1.setLayoutX(19);
			ta1.setLayoutY(170);
			ta1.setPrefSize(200, 360);

			AnchorPane rightpane = new AnchorPane();
			rightpane.setPrefSize(383, 621);
			leftpane.setPrefSize(100, 160);
			;
			leftpane.setPadding(new Insets(-1, 0, 0, 0));

			Image img = new Image(new File("worldr.png").toURI().toString());

			ImageView imgV = new ImageView(img);
		
//
//			imgV.setFitWidth(1000);
//			imgV.setFitHeight(534);
			Circle[] cir = new Circle[countries.size()];

			Text[] t = new Text[50];
			// left pane
			leftpane.getChildren().addAll(new Label("123"), imgV);
			// adding cricles

//							test			Circle c = new Circle(487, 336, 6);
//			leftpane.getChildren().addAll(fillcountries(cir));

			fillcountries(cir);
			// right pane

			Label lsource = new Label("source");
			Label ldestination = new Label("destination");
			
//			Line l = new Line(0.0, 0.0, cir[getinde("Albania")].getCenterX(), cir[getinde("Albania")].getCenterY());
//			leftpane.getChildren().add(l);

			ComboBox<Country> source = new ComboBox<Country>();

			ComboBox<Country> destination = new ComboBox<Country>();
			// filling comboboxes
			source.getItems().addAll(countries);
			destination.getItems().addAll(countries);
			source.getSelectionModel().selectFirst();
			destination.getSelectionModel().selectFirst();

			source.setLayoutX(19);
			source.setLayoutY(47);
			source.setPrefWidth(150);
			destination.setPrefWidth(150);
			destination.setLayoutX(19);
			destination.setLayoutY(100);

			Button run = new Button("RUN");
			run.setStyle("-fx-background-color:  #00c3ff");
			
			Button clear = new Button("Clear");
			clear.setStyle("-fx-background-color:  #00c3ff");
			clear.setOnAction(y ->{
				imgV.setImage(null);
				imgV.setImage(img);
			
				
				leftpane.getChildren().clear();
				
				leftpane.getChildren().addAll(imgV);
				fillcountries(cir);
				for (Circle circle : cir) {
					circle.setOnMouseClicked(z -> {
						circle.setFill(Color.DARKRED);
						if (selection == false ) {
							source.setValue(countries.get(findIndex(cir, circle)));
							selection = true;
						}
						else {
							destination.setValue(countries.get(findIndex(cir, circle)));
							selection = false;
						}
					});
				}
				
				
				
			});
			clear.setLayoutX(90);
			clear.setLayoutY(140);
			run.setLayoutX(19);
			run.setLayoutY(140);

			lsource.setLayoutX(40);
			lsource.setLayoutY(23);
			ldestination.setLayoutX(40);
			ldestination.setLayoutY(80);

			

			run.setOnAction(y -> {
//				new Alert(AlertType.WARNING, "the total distance is equal to " + graph.distance + "KM").show();
				int dist = 0;
				String x = "";
				List<Vertex> list = graph.getShortestPath(destination.getValue().name, source.getValue().name);
				list.add(new Vertex(destination.getValue().name));
				for (Vertex vertex : list) {
					dist += vertex.getDistance();
					x += vertex.getCountry() + "---->";
				

				}
				//System.out.println("List From Dijkstra: "+list.toString());
				
				for (int i = 0; i < list.size() -1 ; i++) {
					Line line = new Line(cir[getinde(list.get(i).getCountry())].getCenterX(),
							cir[getinde(list.get(i).getCountry())].getCenterY(),
							cir[getinde(list.get(i + 1).getCountry())].getCenterX(),
							cir[getinde(list.get(i + 1).getCountry())].getCenterY());
					//System.out.println(getinde(list.get(i).getCountry()));
					line.setFill(Color.RED);
					System.out.println();
					line.setStrokeWidth(4.0);
					leftpane.getChildren().add(line);
				}
				ta1.setText(x + destination.getSelectionModel().getSelectedItem() + " " + "\n the total distance is "
						+ dist);

			});
			for (Circle circle : cir) {
				circle.setOnMouseClicked(y -> {
					circle.setFill(Color.DARKRED);
					if (selection == false ) {
						source.setValue(countries.get(findIndex(cir, circle)));
						selection = true;
					}
					else {
						destination.setValue(countries.get(findIndex(cir, circle)));
						selection = false;
					}
				});
			}
			rightpane.getChildren().addAll(source, destination, run,clear, ta1, ldestination, lsource);
			root.getItems().addAll(leftpane, rightpane);
			// testing

			Scene scene = new Scene(root, 1200, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int getinde(String cname) {
		for (int i = 0; i < countries.size(); i++) {
			if (countries.get(i).name.equals(cname )) {
				//System.out.println(cname + "===" + i);
				return i;

			}

		}

		return -1;
	}

	public  int findIndex(Circle arr[], Circle t)
    {
 
        // if array is Null
        if (arr == null) {
            return -1;
        }
 
        // find length of array
        int len = arr.length;
        int i = 0;
 
        // traverse in the array
        while (i < len) {
 
            // if the i-th element is t
            // then return the index
            if (arr[i] == t) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    }

	public static void main(String[] args) {
		Application.launch(args);

	}
}
