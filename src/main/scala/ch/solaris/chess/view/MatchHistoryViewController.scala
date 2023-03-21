package ch.solaris.chess.view

import scalafxml.core.macros.sfxml
import scalafx.scene.layout.{Pane,BackgroundFill,Background,CornerRadii,BackgroundImage}
import scalafx.scene.control.Label
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scala.collection.immutable.Vector
import scala.collection.mutable.ArrayBuffer
import scalafx.scene.layout.{ BackgroundFill, Background, CornerRadii, BackgroundImage}
import javafx.scene.layout.BackgroundRepeat.NO_REPEAT
import scalafx.scene.image.Image
import scalafx.scene.paint.Color
import scalafx.Includes._

import ch.solaris.chess.Main
import ch.solaris.chess.model._

@sfxml
class MatchHistoryViewController(
    private val moveCounterLabel: Label,
    private val space1: Pane, private val space2: Pane, private val space3: Pane, private val space4: Pane, private val space5: Pane, private val space6: Pane, private val space7: Pane, private val space8: Pane,
    private val space9: Pane, private val space10: Pane, private val space11: Pane, private val space12: Pane, private val space13: Pane, private val space14: Pane, private val space15: Pane, private val space16: Pane,
    private val space17: Pane, private val space18: Pane, private val space19: Pane, private val space20: Pane, private val space21: Pane, private val space22: Pane, private val space23: Pane, private val space24: Pane,
    private val space25: Pane, private val space26: Pane, private val space27: Pane, private val space28: Pane, private val space29: Pane, private val space30: Pane, private val space31: Pane, private val space32: Pane,
    private val space33: Pane, private val space34: Pane, private val space35: Pane, private val space36: Pane, private val space37: Pane, private val space38: Pane, private val space39: Pane, private val space40: Pane,
    private val space41: Pane, private val space42: Pane, private val space43: Pane, private val space44: Pane, private val space45: Pane, private val space46: Pane, private val space47: Pane, private val space48: Pane,
    private val space49: Pane, private val space50: Pane, private val space51: Pane, private val space52: Pane, private val space53: Pane, private val space54: Pane, private val space55: Pane, private val space56: Pane,
    private val space57: Pane, private val space58: Pane, private val space59: Pane, private val space60: Pane, private val space61: Pane, private val space62: Pane, private val space63: Pane, private val space64: Pane
){
    var matchIndex = Main.matchIndex
    var selMatchMoves = Match.matchHistory(matchIndex).moves
    var selMatchPieces = Match.matchHistory(matchIndex).pieces
    var moveNum = 0 

    //displays the move number
    moveCounterLabel.text = (moveNum) + "/" + (selMatchMoves.length/2)

    val boardSpaces = Vector(
        space1, space2, space3, space4, space5, space6, space7,space8, space9, space10, space11, space12, space13, space14, space15, space16,
        space17, space18, space19, space20, space21, space22, space23,space24, space25, space26, space27, space28, space29, space30, space31, space32,
        space33, space34, space35, space36, space37, space38, space39,space40, space41, space42, space43, space44, space45, space46, space47, space48,
        space49, space50, space51, space52, space53, space54, space55,space56, space57, space58, space59, space60, space61, space62, space63, space64
        )
    def updateBoard(from: Int, to: Int): Unit = {
        var fromSpace = boardSpaces(from - 1)
        val toSpace = boardSpaces(to - 1)
        toSpace.background = fromSpace.background.value
        fromSpace.background = null
    }
    //overloaded method
    def updateBoard(from: Int, to: Int, fromPieceBg: Background,toPieceBg: Background): Unit = {
        var fromSpace = boardSpaces(from - 1)
        val toSpace = boardSpaces(to - 1)
        toSpace.background = toPieceBg
        fromSpace.background = fromPieceBg
    }
    def returnToMenu() = {
        Main.showMenu
    }
    def nextMove() = {
        if(moveNum < selMatchMoves.length/2){
            val from = selMatchMoves( 0 + (2*moveNum) )
            val to = selMatchMoves( 1 + (2*moveNum) )
            updateBoard(from, to)
            moveNum += 1
            //updates the move number label
            moveCounterLabel.text = (moveNum) + "/" + (selMatchMoves.length/2)
    
        }
        else{
            val alert = new Alert(Alert.AlertType.Warning){
            initOwner(Main.stage)
            title = "Cannot go further!"
            headerText = "At the end of match"
            contentText = "You are already in the end of the match!"
            }.showAndWait()
        }
        
    }

    def prevMove(){
        if (moveNum > 0 ){
            moveNum -= 1
            var fromPiece = selMatchPieces(0+(2*moveNum))
            var toPiece = selMatchPieces(1+(2*moveNum))
            val from = selMatchMoves( 0 + (2*moveNum) )
            val to = selMatchMoves( 1 + (2*moveNum) )
            
            val fromPieceBg =  genBackGround(Board.getSpaceColour(from), fromPiece)
            val toPieceBg = genBackGround(Board.getSpaceColour(to), toPiece)
            updateBoard(from, to, fromPieceBg, toPieceBg)
            //updates the move number label
            moveCounterLabel.text = (moveNum) + "/" + (selMatchMoves.length/2)
        }
        else{
            val alert = new Alert(Alert.AlertType.Warning){
            initOwner(Main.stage)
            title = "Cannot go back!"
            headerText = "At the start of match"
            contentText = "You are already in the start of the match!"
            }.showAndWait()
        }
    }
    
    //generates the background with the correct piece image
    def genBackGround(bgColor: Color, pieceForBg: Piece): Background = {
        var background = new Background( Seq[BackgroundFill](), Array[BackgroundImage]())
        //if the space is empty dont add background image
        if (pieceForBg.player == 0){
            background = new Background( Seq[BackgroundFill](new  BackgroundFill(bgColor, new CornerRadii(0.0), null)),
                    Array[BackgroundImage]() )
        }
        else{
            background = new Background( Seq[BackgroundFill](new  BackgroundFill(bgColor, new CornerRadii(0.0), null)),
                    Array[BackgroundImage](new BackgroundImage(new Image(getClass().getResourceAsStream(pieceForBg.findImgUrl(pieceForBg))), NO_REPEAT, NO_REPEAT, null, null)) )
        }
        return background
    }
}