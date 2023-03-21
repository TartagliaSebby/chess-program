package ch.solaris.chess.model
import scala.collection.mutable.ArrayBuffer
import scalafx.scene.paint.Color
import ch.solaris.chess.model._
class Board(){
    val pieces: ArrayBuffer[Piece] = genPieces()

    def genPieces():ArrayBuffer[Piece] = {
        var board = ArrayBuffer[Piece]()
        //generate pawns
        for (x <- 0 to 7 ){
            val p1 = new Pawn(9+x, 1)
            val p2 = new Pawn(49+x, 2)
            board +=(p1,p2)
        }
        //generate rooks
        for (x <- 0 to 7 by 7){
            val r1 = new Rook(1+x, 1)
            val r2 = new Rook(57+x, 2)
            board += (r1,r2)
        }
        //generate knights
        for (x <- 0 to 5 by 5){
            val k1 = new Knight(2+x, 1)
            val k2 = new Knight(58+x, 2)
            board += (k1,k2)
        }
        //generate bishops
        for (x <- 0 to 3 by 3){
            val b1 = new Bishop(3+x, 1)
            val b2 = new Bishop(59+x, 2)
            board += (b1,b2)
        }
        //generate Kings
        val ki1 = new King(5, 1)
        val ki2 = new King(61, 2)
        board += (ki1,ki2)
        //generate Queens
        val q1 = new Queen(4, 1)
        val q2 = new Queen(60, 2)
        board += (q1,q2)
        //generate empty piece
        board += (new Pawn(0,0))
        //return board
        board
    }
}
object Board{
    //determines the default color of the space
    def getSpaceColour(bn: Int): Color = {
        val p = new Pawn(0,0)
        val x = Piece.convToXY(bn, 0)
        val y = Piece.convToXY(bn, 1)
        var colour = Color.White
        if(x%2 == y%2){
            colour = Color.rgb(255,248,220)
        }
        else{
            colour = Color.rgb(231, 184, 141) 
        }
        //return colour
        colour
    }
}



