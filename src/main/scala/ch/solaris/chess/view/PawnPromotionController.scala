package ch.solaris.chess.view

import scalafxml.core.macros.sfxml
import scalafx.scene.image.{ImageView, Image}
import scalafx.event.ActionEvent
import scalafx.stage.Stage
import ch.solaris.chess.Main
import ch.solaris.chess.model.{Piece, Pawn, Knight, Bishop, Rook, Queen}

@sfxml
class PawnPromotionController(private val knightImg: ImageView, private val bishopImg: ImageView, 
private val queenImg: ImageView, private val rookImg:ImageView){
    var dialogStage: Stage = null
    var pawn: Piece = new Pawn(0,0)
    var selectedPromotion = ""

    //change the image of the button based on the player who triggered the pawn promotion
    def initImageView() = {
        if(pawn.player == 1){
        knightImg.image = new Image(getClass().getResourceAsStream("/images/wKnight.png"))
        bishopImg.image = new Image(getClass().getResourceAsStream("/images/wBishop.png"))
        queenImg.image = new Image(getClass().getResourceAsStream("/images/wQueen.png"))
        rookImg.image = new Image(getClass().getResourceAsStream("/images/wRook.png"))
    }
    else{
        knightImg.image = new Image(getClass().getResourceAsStream("/images/bKnight.png"))
        bishopImg.image = new Image(getClass().getResourceAsStream("/images/bBishop.png"))
        queenImg.image = new Image(getClass().getResourceAsStream("/images/bQueen.png"))
        rookImg.image = new Image(getClass().getResourceAsStream("/images/bRook.png"))
    }
    }
    

    def handleSelect(e: ActionEvent) = {
        //if user selects a button it will return a string based on their selection
        selectedPromotion = e.source.asInstanceOf[javafx.scene.control.Button].getId
        dialogStage.close
    }


}