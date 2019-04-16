
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;

public class ColorTrap extends Application{
    private Scene scene;
    private BorderPane borderPane;
    private Text txtCountDown = new Text("");
    private Timeline timeline;
    private final int TIMER = 15;
    private int count = 0;
    private HBox trapBox;
    private Text trapWord;

    private FlowPane flowPane;
    private HBox bottomBox;
    private Text score;
    private int scoreVal = 0;
    private Text[] colorNameTxtTop;

    Text orangeTxt;Text purpleTxt;Text blackTxt; Text blueTxt;
    Text yellowTxt; Text redTxt;  Text brownTxt;

    Color orangeCol;Color purpleCol;Color blackCol;Color blueCol;
    Color yellowCol; Color redCol;  Color brownCol;
    Color[] colArr;

    Text black; Text  orange; Text red;;Text  purple;
    Text brown;Text  blue;  Text yellow;
    Text[] topTextAr;

    ArrayList <Integer> randomNum = new ArrayList<>();
    ArrayList<Text> colorNameTxt = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: lightgrey");
        scene = new Scene(borderPane, 600, 300);
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(600);
        initializeGame();
        startPlay();

        primaryStage.setTitle("Color Trap");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startPlay()
    {
        chooseTrapWordAndColor();
        colorNameOptions();
        count = TIMER;
        txtCountDown.setText(TIMER + "");
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000), e -> {

                    if(count >= 0){
                        txtCountDown.setText(count + "");
                        count--;
                    }
                    else{
                        endOfGame();
                    }
                }));
        //<<< To change background color every .25 second >>>\\
        String[] backGround = {"-fx-background-color: pink", "-fx-background-color: beige",
            "-fx-background-color: burlywood", "-fx-background-color: cyan",
            "-fx-background-color: lavender" };
        Timeline timeline2 = new Timeline(new KeyFrame(
            Duration.millis(250), a -> {

            int rand = (int)(Math.random()*5);
            borderPane.setStyle(backGround[rand]);
        }));
        timeline2.setCycleCount(TIMER * 4 + 2);
        timeline2.play();
        //<<<<<<<<<< >>>>>>>>>>\\
        timeline.setCycleCount(TIMER + 2);
        timeline.play();
    }

    public void endOfGame()
    {
        borderPane.getChildren().remove(trapBox);
        borderPane.getChildren().remove(flowPane);
        borderPane.getChildren().remove(bottomBox);

        Text scored = new Text("Your score: " + scoreVal);
        scored.setFont(Font.font("Marker Felt", FontWeight.NORMAL, 30));
        Button playAgain = new Button("Play Again");
        VBox vBox = new VBox(30);
        vBox.getChildren().addAll(playAgain, scored);
        vBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(vBox);
        borderPane.setStyle("-fx-background-color: lightgrey");

        playAgain.setOnMouseClicked(e -> {
            borderPane.setTop(trapBox);
            borderPane.setCenter(flowPane);
            scoreVal = 0;
            score.setText("Score: 0");
            borderPane.setBottom(bottomBox);
            startPlay();
        });
    }

    public void checkChoice(Text choice)
    {
        for(int i = 0; i < 7; i++ ){
          randomNum.add(i);
        }

        Collections.shuffle(randomNum);
        Collections.shuffle(colorNameTxt);

        // *** creating dictionary with corresponding color values *** \\
        HashMap<String, String> dictionary = new HashMap<String, String>()
        {{
            put("YELLOW", "0xffff00ff"); put("PURPLE", "0x800080ff");
            put("RED", "0xff0000ff"); put("BLUE", "0x0000ffff");
            put("BROWN", "0xa52a2aff"); put("ORANGE", "0xffa500ff");
            put("BLACK", "0x000000ff");
        }};

        String temp = choice.getText();
        if(dictionary.get(temp).equals(trapWord.getFill().toString())){
            scoreVal++;
            score.setText("Score: " + scoreVal);
        }

        ArrayList <Text> tempList = new ArrayList<>();
        tempList.add(new Text("ORANGE")); tempList.add(new Text("RED"));
        tempList.add(new Text("BLACK")); tempList.add(new Text("PURPLE"));
        tempList.add(new Text("BROWN")); tempList.add(new Text("BLUE"));
        tempList.add(new Text("YELLOW"));
        Collections.shuffle(tempList);

        for(int i = 0; i < 7; i++){
            colorNameTxt.get(i).setText(tempList.get(i).getText());
            colorNameTxt.get(i).setFill(colArr[i]);//[randomNum.get(i)]);
        }

        //Choose a new trap word and options list
        chooseTrapWordAndColor();
        colorNameOptions();
    }

    public void chooseTrapWordAndColor()
    {
        int rand = (int)(Math.random()*7);

        trapWord.setText(topTextAr[randomNum.get(rand)].getText());
        trapWord.setFill(colArr[randomNum.get(rand)]);
    }

    public void colorNameOptions()
    {
        orangeTxt.setOnMouseClicked(e -> checkChoice(orangeTxt));
        blackTxt.setOnMouseClicked(e -> checkChoice(blackTxt));
        redTxt.setOnMouseClicked(e -> checkChoice(redTxt));
        brownTxt.setOnMouseClicked(e -> checkChoice(brownTxt));
        purpleTxt.setOnMouseClicked(e -> checkChoice(purpleTxt));
        yellowTxt.setOnMouseClicked(e -> checkChoice(yellowTxt));
        blueTxt.setOnMouseClicked(e -> checkChoice(blueTxt));
    }

    public void initializeGame()
    {
        // ******* random numbers ******* \\
        for(int i = 0; i < 7; i++ )
            randomNum.add(i);
        Collections.shuffle(randomNum);
        // *** creating colors and storing in an Array *** \\
        orangeCol = Color.ORANGE;purpleCol = Color.PURPLE;
        blackCol = Color.BLACK; blueCol = Color.BLUE;
        yellowCol = Color.YELLOW; redCol = Color.RED;
        brownCol = Color.BROWN;
        colArr = new Color[7];
        colArr[0] = orangeCol;colArr[1] = purpleCol;
        colArr[2] = blackCol;colArr[3] = blueCol;
        colArr[4] = yellowCol;colArr[5] = redCol;colArr[6] = brownCol;

        //**** creating Text Nodes for Trap Word and setting on Top  **** \\
        black = new Text("BLACK"); orange = new Text("ORANGE");
        red = new Text("RED"); purple = new Text("PURPLE");blue = new Text("BLUE");
        brown = new Text("BROWN"); yellow = new Text("YELLOW");
        topTextAr = new Text[7];
        topTextAr[0] = black; topTextAr[1] = orange;topTextAr[2] = red;
        topTextAr[3] = purple; topTextAr[4] = brown;topTextAr[5] = blue;
        topTextAr[6] = yellow;

        for (Text topTextAr1 : topTextAr) {
            topTextAr1.setFont(Font.font("Marker Felt", FontWeight.NORMAL, 60));
        }
        int rand = (int)(Math.random()*7);
        trapWord = topTextAr[rand];
        trapWord.setFill(colArr[rand]);
        trapBox = new HBox(); trapBox.setAlignment(Pos.CENTER);
        trapBox.getChildren().add(trapWord);
        borderPane.setTop(trapBox);

        //**** creating Text for Option Word  and setting in the middle ****\\
        orangeTxt = new Text("ORANGE"); purpleTxt = new Text("PURPLE");
        blackTxt = new Text("BLACK"); blueTxt = new Text("BLUE");
        yellowTxt = new Text("YELLOW"); redTxt = new Text("RED");
        brownTxt = new Text("BROWN");

        colorNameTxt.add(orangeTxt); colorNameTxt.add(purpleTxt);
        colorNameTxt.add(blackTxt); colorNameTxt.add(blueTxt);
        colorNameTxt.add(yellowTxt); colorNameTxt.add(redTxt);
        colorNameTxt.add(brownTxt);

        for(int i = 0; i < 7; i++){
            colorNameTxt.get(i).setFont(Font.font("Marker Felt", FontWeight.NORMAL, 40));
        }

        HBox centerBox1 = new HBox(20); centerBox1.setAlignment(Pos.CENTER);
        HBox centerBox2 = new HBox(20); centerBox2.setAlignment(Pos.CENTER);
        HBox centerBox3 = new HBox(20); centerBox3.setAlignment(Pos.CENTER);

        Collections.shuffle(randomNum);
        Collections.shuffle(colorNameTxt);

        for(int i = 0; i < 7; i++){
            colorNameTxt.get(i).setFill(colArr[randomNum.get(i)]);

            switch (i) {
                case 0: case 1:
                    centerBox1.getChildren().add(colorNameTxt.get(i));
                    break;
                case 2: case 3: case 4:
                    centerBox2.getChildren().add(colorNameTxt.get(i));
                    break;
                default:
                    centerBox3.getChildren().add(colorNameTxt.get(i));
                    break;
            }
        }

        flowPane = new FlowPane(); flowPane.setAlignment(Pos.CENTER);
        flowPane.getChildren().addAll(centerBox1, centerBox2, centerBox3);
        flowPane.setOrientation(Orientation.VERTICAL);
        borderPane.setCenter(flowPane);

        // **** setting score and timer at the bottom **** \\
        score = new Text("Score: " + scoreVal);
        bottomBox = new HBox(150); bottomBox.setAlignment(Pos.CENTER);
        bottomBox.getChildren().addAll(score, txtCountDown);
        borderPane.setBottom(bottomBox);
        // ****** binding the properties ****** \\
        trapBox.prefHeightProperty().bind(borderPane.heightProperty().multiply(0.35));
        trapBox.prefWidthProperty().bind(borderPane.widthProperty());
        flowPane.prefHeightProperty().bind(borderPane.heightProperty().multiply(0.55));
        flowPane.prefWidthProperty().bind(borderPane.widthProperty());
        bottomBox.prefHeightProperty().bind(borderPane.heightProperty().multiply(0.10));
        bottomBox.prefWidthProperty().bind(borderPane.widthProperty());
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
