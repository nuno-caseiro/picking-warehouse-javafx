package ipleiria.estg.dei.ei.pi.gui;

import ipleiria.estg.dei.ei.pi.model.picking.*;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.net.URL;
import java.util.*;

public class SimulationFrameController implements Initializable, EnvironmentListener {

    public StackPane simulationPane;
    public Group group;
  
    private List<Node> graphDecisionNodes;
    private List<PickNode> graphPicks;
    private List<Node> graphAgents;
    private HashMap<Integer,Edge<Node>> graphEdges;
    
    
    private static final int NODE_SIZE = 10;


    //HashMap<String, Rectangle> picks = new HashMap<>();
    HashMap<Integer, StackPane> nodes = new HashMap<>();
    HashMap<Integer, StackPane> agents = new HashMap<>();

    //Circle a1= new Circle(10, Color.RED);
    //Circle a2= new Circle(10, Color.GOLD);

    //public StackPane a1a = new StackPane();
    //public StackPane a2a = new StackPane();
    public List<Timeline> timeLines = new ArrayList<>();

    public SequentialTransition st;
    public boolean stFirst = false;

    private MainFrameController main;

    public void init(MainFrameController mainFrameController){
        main= mainFrameController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         group = new Group();


//        createNode("1", 50,50,10,Color.YELLOW);
//        createNode("2", 150,50,10,Color.YELLOW);
//        createNode("3", 250,50,10,Color.YELLOW);
//        createNode("4", 50,150,10,Color.YELLOW);
//        createNode("5",150,150,10,Color.YELLOW);
//        createNode("5",250,150,10,Color.YELLOW);
//
//        Text text1 = new Text("A1");
//        a1a.getChildren().addAll(a1,text1);
//        a1a.setLayoutX(70);
//        a1a.setLayoutY(50);
//        a1a.toFront();
//
//
//        Text text2 = new Text("A2");
//        a2a.getChildren().addAll(a2,text2);
//        a2a.setLayoutX(200);
//        a2a.setLayoutY(150);
//        a2a.toFront();
//
//        agents.put(1,a1a);
//        agents.put(2,a2a);
//
//        createEdge(nodes.get("50-50"),nodes.get("150-50"));
//        createEdge(nodes.get("150-50"),nodes.get("250-50"));
//        createEdge(nodes.get("50-150"),nodes.get("150-150"));
//        createEdge(nodes.get("150-150"),nodes.get("250-150"));
//        createEdge(nodes.get("50-50"),nodes.get("50-150"));
//        createEdge(nodes.get("150-50"),nodes.get("150-150"));
//        createEdge(nodes.get("250-50"),nodes.get("250-150"));
//
//        for (String s : picks.keySet()) {
//            group1.getChildren().add(picks.get(s));
//            System.out.println("----"+s+"-----");
//        }
//
//        for (String s : nodes.keySet()) {
//            group1.getChildren().add(nodes.get(s));
//        }
//
//        group1.getChildren().add(a1a);
//        group1.getChildren().add(a2a);
//
//        KeyFrame k = new KeyFrame(Duration.millis(1000),new KeyValue(a1a.layoutXProperty(),nodes.get("150-50").getLayoutX()), new KeyValue(a2a.layoutXProperty(),nodes.get("150-150").getLayoutX()));
//        addTimeLine(k);
//        addTimeLine(new KeyFrame(Duration.millis(1000),new KeyValue(a1a.layoutXProperty(),nodes.get("250-50").getLayoutX())));
//        addTimeLine(new KeyFrame(Duration.millis(1000),new KeyValue(a1a.layoutYProperty(),picks.get("240-110").getY())));
//        addTimeLine(new KeyFrame(Duration.millis(1),new KeyValue(picks.get("240-110").fillProperty(),Color.BLACK)));
//        addTimeLine(new KeyFrame(Duration.millis(1000),new KeyValue(a1a.layoutYProperty(),nodes.get("250-150").getLayoutY()),new KeyValue(a2a.layoutYProperty(),nodes.get("150-50").getLayoutY()) ));
//        addTimeLine(new KeyFrame(Duration.millis(1000),new KeyValue(a1a.layoutXProperty(),nodes.get("150-150").getLayoutX())));




    }

    public void createEdge(List<Node> nodes){
        Line l = new Line(this.nodes.get(nodes.get(0).getIdentifier()).getLayoutX()+10,this.nodes.get(nodes.get(0).getIdentifier()).getLayoutY()+10
                ,this.nodes.get(nodes.get(1).getIdentifier()).getLayoutX()+10,this.nodes.get(nodes.get(1).getIdentifier()).getLayoutY()+10);
        l.setViewOrder(1.0);

        //createPicks(l);
        group.getChildren().add(l);
    }

    public void createPicks(Edge e){
//        for (int i = (int) (l.getStartY()+5); i < l.getEndY()-5; i+=15) {
//            Rectangle rL = new Rectangle(l.getStartX()+10,i,10,10);
//            rL.setStroke(Color.BLACK);
//            rL.setStrokeType(StrokeType.INSIDE);
//            rL.setFill(Color.WHITE);
//            Rectangle rR = new Rectangle(l.getStartX()-20,i,10,10);
//            rR.setStroke(Color.BLACK);
//            rR.setStrokeType(StrokeType.INSIDE);
//            rR.setFill(Color.WHITE);
//            picks.put((int)rL.getX()+"-"+(int)rL.getY(),rL);
//            picks.put((int)rR.getX()+"-"+(int)rR.getY(),rR);
//
//        }
    }

    public void createLayout(){
        for (Node decisionNode : graphDecisionNodes) {
            createNode(decisionNode);
        }

        for (Integer integer : graphEdges.keySet()) {
           createEdge(graphEdges.get(integer).getNodes());
        }

        for (Node graphAgent : graphAgents) {
            createNode(graphAgent);
        }


        simulationPane.getChildren().add(group);
    }


    public void createNode(Node node){

        Text text = new Text(String.valueOf(node.getIdentifier()));
        Circle circle = new Circle();
        StackPane stackPane = new StackPane();

        if(graphDecisionNodes.contains(node)){
            circle = new Circle(NODE_SIZE, Color.WHITE);
            stackPane.setViewOrder(-1.0);
        }

        if(graphAgents.contains(node)){
            circle = new Circle(NODE_SIZE, Color.RED);
            stackPane.setViewOrder(-2.0);
        }
            circle.setStroke(Color.BLACK);
            stackPane.getChildren().addAll(circle,text);
            stackPane.setLayoutX((node.getLine()*20));
            stackPane.setLayoutY(node.getColumn()*35);
            stackPane.setViewOrder(-1.0);
            nodes.put(node.getIdentifier(),stackPane);

            this.group.getChildren().add(stackPane);
    }


    public void addTimeLine(KeyFrame kv) {
        Timeline t= new Timeline();
        t.getKeyFrames().addAll(kv);

        t.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                main.getSlider().setValue(st.getCurrentTime().toMillis());
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


    @Override
    public void updateEnvironment() {

    }

    @Override
    public void createEnvironment(List<Node> decisionNodes,List<PickNode> pickNodes, HashMap<Integer,Edge<Node>> edges, List<Node> agents) {
        this.graphDecisionNodes= decisionNodes;
        this.graphPicks= pickNodes;
        this.graphEdges=edges;
        this.graphAgents=agents;
        createLayout();
    }


    @Override
    public void createSimulationPicks() {

    }
}
