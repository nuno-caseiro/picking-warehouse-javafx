package ipleiria.estg.dei.ei.pi.gui;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.net.URL;
import java.util.*;

public class SimulationFrameController implements Initializable {

    public StackPane simulationPane;
    public Group group1 = new Group();

    HashMap<String, Rectangle> picks = new HashMap<>();
    HashMap<String, StackPane> nodes = new HashMap<>();
    HashMap<Integer, StackPane> agents = new HashMap<>();

    Circle a1= new Circle(10, Color.RED);
    Circle a2= new Circle(10, Color.GOLD);

    public StackPane a1a = new StackPane();
    public StackPane a2a = new StackPane();
    public List<Timeline> timeLines = new ArrayList<>();

    public SequentialTransition st;
    public boolean stFirst = false;

    private MainFrameController main;

    public void init(MainFrameController mainFrameController){
        main= mainFrameController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        createNode("1", 50,50,10,Color.YELLOW);
        createNode("2", 150,50,10,Color.YELLOW);
        createNode("3", 250,50,10,Color.YELLOW);
        createNode("4", 50,150,10,Color.YELLOW);
        createNode("5",150,150,10,Color.YELLOW);
        createNode("5",250,150,10,Color.YELLOW);

        Text text1 = new Text("A1");
        a1a.getChildren().addAll(a1,text1);
        a1a.setLayoutX(70);
        a1a.setLayoutY(50);
        a1a.toFront();


        Text text2 = new Text("A2");
        a2a.getChildren().addAll(a2,text2);
        a2a.setLayoutX(200);
        a2a.setLayoutY(150);
        a2a.toFront();

        agents.put(1,a1a);
        agents.put(2,a2a);

        createEdge(nodes.get("50-50"),nodes.get("150-50"));
        createEdge(nodes.get("150-50"),nodes.get("250-50"));
        createEdge(nodes.get("50-150"),nodes.get("150-150"));
        createEdge(nodes.get("150-150"),nodes.get("250-150"));
        createEdge(nodes.get("50-50"),nodes.get("50-150"));
        createEdge(nodes.get("150-50"),nodes.get("150-150"));
        createEdge(nodes.get("250-50"),nodes.get("250-150"));

        for (String s : picks.keySet()) {
            group1.getChildren().add(picks.get(s));
            System.out.println("----"+s+"-----");
        }

        for (String s : nodes.keySet()) {
            group1.getChildren().add(nodes.get(s));
        }

        group1.getChildren().add(a1a);
        group1.getChildren().add(a2a);

        KeyFrame k = new KeyFrame(Duration.millis(1000),new KeyValue(a1a.layoutXProperty(),nodes.get("150-50").getLayoutX()), new KeyValue(a2a.layoutXProperty(),nodes.get("150-150").getLayoutX()));
        addTimeLine(k);
        addTimeLine(new KeyFrame(Duration.millis(1000),new KeyValue(a1a.layoutXProperty(),nodes.get("250-50").getLayoutX())));
        addTimeLine(new KeyFrame(Duration.millis(1000),new KeyValue(a1a.layoutYProperty(),picks.get("240-110").getY())));
        addTimeLine(new KeyFrame(Duration.millis(1),new KeyValue(picks.get("240-110").fillProperty(),Color.BLACK)));
        addTimeLine(new KeyFrame(Duration.millis(1000),new KeyValue(a1a.layoutYProperty(),nodes.get("250-150").getLayoutY()),new KeyValue(a2a.layoutYProperty(),nodes.get("150-50").getLayoutY()) ));
        addTimeLine(new KeyFrame(Duration.millis(1000),new KeyValue(a1a.layoutXProperty(),nodes.get("150-150").getLayoutX())));


        simulationPane.getChildren().add(group1);

    }

    public void createEdge(StackPane node1, StackPane node2){
        Line l = new Line(node1.getLayoutX()+10,node1.getLayoutY()+10,node2.getLayoutX()+10,node2.getLayoutY()+10);

        createPicks(l);
        group1.getChildren().add(l);
    }

    public void createPicks(Line l){
        for (int i = (int) (l.getStartY()+5); i < l.getEndY()-5; i+=15) {
            Rectangle rL = new Rectangle(l.getStartX()+10,i,10,10);
            rL.setStroke(Color.BLACK);
            rL.setStrokeType(StrokeType.INSIDE);
            rL.setFill(Color.WHITE);
            Rectangle rR = new Rectangle(l.getStartX()-20,i,10,10);
            rR.setStroke(Color.BLACK);
            rR.setStrokeType(StrokeType.INSIDE);
            rR.setFill(Color.WHITE);
            picks.put((int)rL.getX()+"-"+(int)rL.getY(),rL);
            picks.put((int)rR.getX()+"-"+(int)rR.getY(),rR);

        }
    }


    public void createNode(String textToNode, int x, int y, int radius, Color color){
        Text text = new Text(textToNode);
        Circle circle = new Circle(radius, color);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(circle,text);
        stackPane.setLayoutX(x);
        stackPane.setLayoutY(y);
        nodes.put(x+"-"+y,stackPane);
    }

    public void setEmptyPick(String position){
        picks.get(position).setFill(Color.WHITE);
    }

    public void addTimeLine(KeyFrame kv) {
        Timeline t= new Timeline();
        t.getKeyFrames().addAll(kv);

        t.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                main.slider.setValue(st.getCurrentTime().toMillis());
            }
        });


        timeLines.add(t);
    }

    public void start() {
        if(!stFirst){
            st= new SequentialTransition();
            st.getChildren().addAll(timeLines);
            stFirst=true;
        }
        if(st.getStatus() == Animation.Status.PAUSED || st.getStatus() ==Animation.Status.STOPPED){
            st.play();
        }else{
            st.pause();
        }
    }

    public void startFromSlider(Double time){
        st.jumpTo(Duration.millis(time));
        st.play();
    }



}
