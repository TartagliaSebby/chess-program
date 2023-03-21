package ch.solaris.chess.view

import scalafxml.core.macros.sfxml
import scalafx.scene.control.{Button,Label}
import scalafx.scene.layout.{Pane, BackgroundFill, Background, CornerRadii, BackgroundImage}
import javafx.scene.layout.BackgroundRepeat.NO_REPEAT
import scalafx.scene.paint.Color
import scalafx.scene.image.Image
import scalafx.event.ActionEvent
import scala.collection.immutable.Vector
import scala.collection.mutable.ArrayBuffer
import scalafx.Includes._

import ch.solaris.chess.Main
import ch.solaris.chess.model._

@sfxml
class GamePageController(private val turnDisplayLabel: Label, p1NameLabel: Label, private val p2NameLabel: Label, private val p1Pane: Pane, private val p2Pane: Pane,
    private val space1: Pane, private val space2: Pane, private val space3: Pane, private val space4: Pane, private val space5: Pane, private val space6: Pane, private val space7: Pane, private val space8: Pane,
    private val space9: Pane, private val space10: Pane, private val space11: Pane, private val space12: Pane, private val space13: Pane, private val space14: Pane, private val space15: Pane, private val space16: Pane,
    private val space17: Pane, private val space18: Pane, private val space19: Pane, private val space20: Pane, private val space21: Pane, private val space22: Pane, private val space23: Pane, private val space24: Pane,
    private val space25: Pane, private val space26: Pane, private val space27: Pane, private val space28: Pane, private val space29: Pane, private val space30: Pane, private val space31: Pane, private val space32: Pane,
    private val space33: Pane, private val space34: Pane, private val space35: Pane, private val space36: Pane, private val space37: Pane, private val space38: Pane, private val space39: Pane, private val space40: Pane,
    private val space41: Pane, private val space42: Pane, private val space43: Pane, private val space44: Pane, private val space45: Pane, private val space46: Pane, private val space47: Pane, private val space48: Pane,
    private val space49: Pane, private val space50: Pane, private val space51: Pane, private val space52: Pane, private val space53: Pane, private val space54: Pane, private val space55: Pane, private val space56: Pane,
    private val space57: Pane, private val space58: Pane, private val space59: Pane, private val space60: Pane, private val space61: Pane, private val space62: Pane, private val space63: Pane, private val space64: Pane
) {
    //allows the program to access a specific pane in the using its coresponding boardnum (external/visual representation board)
    val boardSpaces = Vector(
        space1, space2, space3, space4, space5, space6, space7,space8, space9, space10, space11, space12, space13, space14, space15, space16,
        space17, space18, space19, space20, space21, space22, space23,space24, space25, space26, space27, space28, space29, space30, space31, space32,
        space33, space34, space35, space36, space37, space38, space39,space40, space41, space42, space43, space44, space45, space46, space47, space48,
        space49, space50, space51, space52, space53, space54, space55,space56, space57, space58, space59, space60, space61, space62, space63, space64
        )

    //generates board that contains all the pieces in an arrayBuffer
    val board = new Board()
    //last selected piece and its possible moves, variables outside the method to keep track of selected pieces
    var lSPiece: Piece = new Pawn(0,0)
    var lPosMoves = ArrayBuffer[Int]()
    // state of the game 
    //0 = player has not selected a piece, 1= player selected a piece
    var state = 0  
    var playerTurn = 1
    var winner = 0
    //pop up for entering players name, then initialise the turn indicator and player name
    val names = Main.showPreGameDialog()
    p1NameLabel.text = names(0).toString
    p2NameLabel.text = names(1).toString
    updateTurn(playerTurn)
    //arraybuffer for storing moves made during the game
    var movesMade = ArrayBuffer[Int]()
    var piecesMoved = ArrayBuffer[Piece]()

    //handleClickOnSpace handles the logic of the game
    def handleClickOnSpace (e: ActionEvent) = {
        var reloop = true                          
        while (reloop){                              
            //highlights the possible moves for the selected piece
            if(state == 0){
                /*
                get ID
                https://stackoverflow.com/questions/24302636/better-way-for-getting-id-of-the-clicked-object-in-javafx-controller/42430200
                */
                //determines the space and piece that was selected 
                var selectedBN = (e.source.asInstanceOf[javafx.scene.control.Button].getId).tail.toInt
                lSPiece = board.pieces(Piece.findPieceInd(selectedBN, board.pieces))

                //makes sure that the piece the player selected belongs to them
                 if(lSPiece.player == playerTurn){
                    //generates the possible moves and highlight selected piece and possible move spaces
                    lPosMoves ++= lSPiece.genPosMoves(board.pieces)
                    addHL(lPosMoves, lSPiece, board.pieces)
                    state = 1
                }
                reloop = false
            }
            else if(state == 1){
                //determines the space and piece that was selected 
                var selectedBN = (e.source.asInstanceOf[javafx.scene.control.Button].getId).tail.toInt
                var pieceIndex = Piece.findPieceInd(selectedBN, board.pieces)
        
                //if the user selects a possible move, move the piece and reflect it visually on the gui
                if(lPosMoves.contains(selectedBN)){
                    //record moves made (boardnumber of the piece that was moved and the space it was moved to)
                    movesMade ++= Array(lSPiece.boardNum, selectedBN)
                    piecesMoved ++= Array(board.pieces(Piece.findPieceInd(lSPiece.boardNum, board.pieces)), 
                                            board.pieces(Piece.findPieceInd(selectedBN, board.pieces))
                                            )
                    //remove highlight of the space that the moved piece was on
                    boardSpaces(lSPiece.boardNum - 1).background = genBackGround(Board.getSpaceColour(lSPiece.boardNum), new Pawn(0,0))
                    //if the piece is owned by the opponent capture it
                    if(board.pieces(pieceIndex).player != 0 && board.pieces(pieceIndex).player != playerTurn){
                        winner = lSPiece.capture(selectedBN, board.pieces)
                    }
                    lSPiece.move(selectedBN, board.pieces)
                    removeHL(lPosMoves, lSPiece, board.pieces)
                    lPosMoves.clear
                    state = 0
                    reloop = false
                    /* 
                    ternary operator
                    https://alvinalexander.com/scala/scala-ternary-operator-syntax/
                    */
                    //switch player turn
                    playerTurn = if(playerTurn == 1) 2 else 1
                    updateTurn(playerTurn)
                    
                }
                //when the user selects a space that is not a possible move
                else{
                    //if user selects an empty space 
                    if(board.pieces(pieceIndex).player != playerTurn){
                        removeHL(lPosMoves, lSPiece, board.pieces)
                        lPosMoves.clear
                        reloop = false
                    }
                    /*if user selects another piece that they own while one is already selected 
                        remove the highlight for the old piece and highlight the new piece*/
                    else if(board.pieces(pieceIndex).player == playerTurn){
                        removeHL(lPosMoves, lSPiece, board.pieces)
                        lPosMoves.clear
                    }
                    state = 0
                }
            }   
            //shows the endgame screen when a king piece is captured
            if( winner == 1 || winner == 2){
                val winnerName = if(winner == 1) names(0) else names(1)
                Main.showEndGamePage(winnerName)
                Match.matchHistory += new Match(names(0), names(1), winnerName, movesMade, piecesMoved)
            }
        }
    }
    def returnToMenu() = {
        Main.showMenu
    }
    //methods for modifying GUI
    def addHL(posMovesBN: ArrayBuffer[Int], selectedPiece: Piece, pieces: ArrayBuffer[Piece]): Unit = {
        //highlights the piece that the player selected
        boardSpaces(selectedPiece.boardNum - 1).background = genBackGround(Color.rgb(255,100,0), selectedPiece)
        //highlights all the possible move spaces, if the space is empty change the background colour, else if a piece occupies that space change background colour and image
        for(boardNum <- posMovesBN){
            var pieceIndex = Piece.findPieceInd(boardNum,pieces)
            boardSpaces(boardNum - 1).background = genBackGround(Color.rgb(174, 243, 89), pieces(pieceIndex))
        }
    }
    def removeHL(posMovesBN: ArrayBuffer[Int], selectedPiece: Piece, pieces: ArrayBuffer[Piece]): Unit = {
        boardSpaces(lSPiece.boardNum - 1).background = genBackGround(Board.getSpaceColour(lSPiece.boardNum), pieces(Piece.findPieceInd(selectedPiece.boardNum, pieces)))
        //change the previously highlighted possible move space's background colour back to default 
        for(boardNum <- posMovesBN){
            var pieceIndex = Piece.findPieceInd(boardNum,pieces)
            boardSpaces(boardNum - 1).background = genBackGround(Board.getSpaceColour(boardNum), pieces(pieceIndex))
        }
    }
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
    def updateTurn(playerTurn: Int) = {
        turnDisplayLabel.text = if(playerTurn == 1) "White Player's Turn" else "Black Player's Turn"
        if(playerTurn == 1){
            p1Pane.setStyle("-fx-border-color:black;-fx-background-color:BlanchedAlmond;-fx-border-width:5")
            p2Pane.setStyle("-fx-border-color:null;-fx-background-color:BlanchedAlmond;")
        }
        else if(playerTurn == 2){
            p1Pane.setStyle("-fx-border-color:null;-fx-background-color:BlanchedAlmond;")
            p2Pane.setStyle("-fx-border-color:black;-fx-background-color:BlanchedAlmond;-fx-border-width:5")
        }
    }    
} 