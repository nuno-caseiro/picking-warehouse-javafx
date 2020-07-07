package ipleiria.estg.dei.ei.pi.gui;

import ipleiria.estg.dei.ei.pi.model.picking.*;
import ipleiria.estg.dei.ei.pi.utils.PickLocation;
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

public class SimulationFrameController implements Initializable, EnvironmentListener {

    public AnchorPane simulationPane;
    public Group group;


    private List<Node> graphDecisionNodes;
    private List<PickingPick> graphPicks;
    private List<PickingAgent> graphAgents;
    private HashMap<Integer,Edge<Node>> graphEdges;


    private static final int NODE_SIZE = 10;
    private static final int PADDING = 25;

    private HashMap<String, Rectangle> picks = new HashMap<>();
    private HashMap<Integer, StackPane> nodes = new HashMap<>();
    private HashMap<Integer, StackPane> agents = new HashMap<>();

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


     /*   addTimeLine(new KeyFrame(Duration.millis(1000),new KeyValue(a1a.layoutXProperty(),nodes.get("250-50").getLayoutX())));
        addTimeLine(new KeyFrame(Duration.millis(1000),new KeyValue(a1a.layoutYProperty(),picks.get("240-110").getY())));
        addTimeLine(new KeyFrame(Duration.millis(1),new KeyValue(picks.get("240-110").fillProperty(),Color.BLACK)));
        addTimeLine(new KeyFrame(Duration.millis(1000),new KeyValue(a1a.layoutYProperty(),nodes.get("250-150").getLayoutY()),new KeyValue(a2a.layoutYProperty(),nodes.get("150-50").getLayoutY()) ));
        addTimeLine(new KeyFrame(Duration.millis(1000),new KeyValue(a1a.layoutXProperty(),nodes.get("150-150").getLayoutX())));*/

    }

    public void createEdge(List<Node> nodes){
        Line l = new Line(this.nodes.get(nodes.get(0).getIdentifier()).getLayoutX()+10,this.nodes.get(nodes.get(0).getIdentifier()).getLayoutY()+10
                ,this.nodes.get(nodes.get(1).getIdentifier()).getLayoutX()+10,this.nodes.get(nodes.get(1).getIdentifier()).getLayoutY()+10);
        l.setViewOrder(1.0);
        simulationPane.getChildren().add(l);
        // simulationPane.getChildren().add(l);

    }

    public void createShelf(Edge e){
        Node node1 = (Node) e.getNodes().get(0);
        List<Rectangle> nodeList = new ArrayList<>();

        for (int i = 1; i < (int) e.getLength(); i++) {
            nodeList.clear();
            Rectangle rL = new Rectangle((node1.getColumn() *PADDING)-25,((node1.getLine()*PADDING)+(i*PADDING)),20,20);
            rL.setStroke(Color.BLACK);
            rL.setStrokeType(StrokeType.INSIDE);
            rL.setFill(Color.WHITE);

            Rectangle rR = new Rectangle((node1.getColumn() *PADDING)+25,(node1.getLine()*PADDING)+(i*PADDING),20,20);

            rR.setStroke(Color.BLACK);
            rR.setStrokeType(StrokeType.INSIDE);
            rR.setFill(Color.WHITE);
            rR.setId("1-1-R");

            simulationPane.getChildren().add(rL);
            simulationPane.getChildren().add(rR);
            picks.put((node1.getLine()+(i)+"-"+node1.getColumn()+"L"),rL);
            picks.put((node1.getLine()+(i)+"-"+node1.getColumn()+"R"),rR);
        }
    }

    public void createLayout(){
        for (Node decisionNode : graphDecisionNodes) {
            createNode(decisionNode);
        }

        for (Integer integer : graphEdges.keySet()) {

            createEdge(graphEdges.get(integer).getNodes());

            if(graphEdges.get(integer).getNodes().get(0).getColumn()==graphEdges.get(integer).getNodes().get(1).getColumn()){
                createShelf(graphEdges.get(integer));
            }
        }

        for (Node graphAgent : graphAgents) {
            createNode(graphAgent);
        }

    }

    private void createPicks() {

        for (PickingPick graphPick : graphPicks) {

            String strBuilder = graphPick.getLine()+"-"+graphPick.getColumn();
            if(graphPick.getPickLocation()== PickLocation.LEFT){
                strBuilder=strBuilder+"L";
                picks.get(strBuilder).setFill(Color.GREEN);

            }
            if(graphPick.getPickLocation()== PickLocation.RIGHT){
                strBuilder=strBuilder+"R";
                picks.get(strBuilder).setFill(Color.GREEN);
            }
        }
    }


    public void createNode(Node node){

        Text text = new Text(String.valueOf(node.getIdentifier()));
        Circle circle = new Circle();
        StackPane stackPane = new StackPane();

        if(graphDecisionNodes.contains(node)){
            circle = new Circle(NODE_SIZE, Color.WHITE);
            stackPane.setViewOrder(-1.0);
            nodes.put(node.getIdentifier(),stackPane);
        }

        if(graphAgents.contains(node)){
            circle = new Circle(NODE_SIZE, Color.RED);
            stackPane.setViewOrder(-2.0);
            agents.put(node.getIdentifier(),stackPane);
        }
        circle.setStroke(Color.BLACK);
        stackPane.getChildren().addAll(circle,text);
        stackPane.setLayoutY(node.getLine()*PADDING);
        stackPane.setLayoutX(node.getColumn()*PADDING);
        stackPane.setViewOrder(-1.0);

        this.simulationPane.getChildren().add(stackPane);
        //TODO offload cor diferente
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

    public void start(PickingIndividual individual) {

        for (PickingAgentPath path : individual.getPaths()) {
            
        }

        KeyFrame k = new KeyFrame(Duration.millis(1000),new KeyValue(agents.get(37).layoutYProperty(),nodes.get(7).getLayoutY()));
        KeyFrame k1 = new KeyFrame(Duration.millis(500),new KeyValue(agents.get(38).layoutYProperty(),nodes.get(15).getLayoutY()));

        Timeline t = new Timeline();
        t.getKeyFrames().add(k);

        Timeline t1 = new Timeline();
        t.getKeyFrames().add(k1);

        ParallelTransition p = new ParallelTransition(t,t1);

        st= new SequentialTransition();
        st.getChildren().add(p);
        stFirst=true;
        st.play();
       /* if(st.getStatus() == Animation.Status.PAUSED || st.getStatus() ==Animation.Status.STOPPED){
            st.play();
        }else{
            st.pause();
        }*/
    }

    public void startFromSlider(Double time){
        st.jumpTo(Duration.millis(time));
        st.play();
    }


    @Override
    public void updateEnvironment() {

    }

    @Override
    public void createEnvironment(List<Node> decisionNodes, HashMap<Integer,Edge<Node>> edges, List<PickingAgent> agents) {
        this.graphDecisionNodes= decisionNodes;
        this.graphEdges=edges;
        this.graphAgents=agents;
        createLayout();
    }


    @Override
    public void createSimulationPicks(List<PickingPick> pickNodes) {
        this.graphPicks= pickNodes;
        createPicks();
    }
}
