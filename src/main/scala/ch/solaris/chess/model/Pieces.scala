package ch.solaris.chess.model
import scala.collection.mutable.ArrayBuffer
import ch.solaris.chess.Main
abstract class Piece(var boardNum:Int, var player:Int){
    def genPosMoves(pieces:ArrayBuffer[Piece]): ArrayBuffer[Int]
    def move( to: Int, pieces: ArrayBuffer[Piece]): Unit = {
        this.boardNum = to
    }
    def capture(bn: Int, pieces: ArrayBuffer[Piece]): Int = {// add victory screen
        var index = Piece.findPieceInd(bn,pieces)
        val piece = pieces(index)
        var winner = 0
        piece.boardNum  = -100
        piece match {
            case _:King =>  winner = if(piece.player == 1)  2 else 1
            case _ =>
        }
        winner
    }
    def findImgUrl(piece:Piece): String = {
        val player = piece.player
        var url = ""
        //match piece to its url depending on its type and player
        if(player == 1){
           url = piece match{
               case _: Pawn => "/images/wPawn.png"
               case _: Bishop => "/images/wBishop.png"
               case _: Rook => "/images/wRook.png"
               case _: Knight => "/images/wKnight.png"
               case _: Queen => "/images/wQueen.png"
               case _: King => "/images/wKing.png"
           }
        }
        else if(player == 2){
            url = piece match{
                case _: Pawn => "/images/bPawn.png"
                case _: Bishop => "/images/bBishop.png"
                case _: Rook => "/images/bRook.png"
                case _: Knight => "/images/bKnight.png"
                case _: Queen => "/images/bQueen.png"
                case _: King => "/images/bKing.png"
           }
        }
        //return url
        url
    }
}
object Piece{ 
    //check if the space is within the board
    def chkIfInBoard(x: Int, y: Int):Boolean = {
        if(x>0 && x<9 && y>0 && y<9){
            return true
        }
        else{
            return false
        }
    }
    //calculates the board number based on the XY coordinates
    def convToBoardNum(x: Int, y: Int): Int = {
        val boardNum = x+(8*(y-1))
        boardNum
    }
    //calculates the XY coordinates based on bordNum and returns either X or Y depending on the index (0 for x and 1 for y)
    def convToXY(boardNum: Int, index: Int): Int = {
        var x = boardNum%8
        var y = boardNum/8
        if(x == 0){
            x = 8
        }
        else{
            y += 1
        }
        var XY = ArrayBuffer(x, y)
        XY(index)
    }
    //searches the arraybuffer for a piece on the Board Number returns the index, and if piece is not found returns 32 
    def findPieceInd(bn: Int, pieces: ArrayBuffer[Piece]): Int = {
        var index = 0
        for (p <- pieces){
           if(p.boardNum == bn){
               return index
           }
           index += 1
        }
        return 32
    }
}

class Bishop(var bn: Int, var p: Int) extends Piece(bn,p) with MovesInStraightLine{
    override def genPosMoves(pieces:ArrayBuffer[Piece]): ArrayBuffer[Int] = {
        //converts board number to xy coordinates to allow checking of if piece is inside the board
        var x = Piece.convToXY(this.boardNum,0)
        var y = Piece.convToXY(this.boardNum,1)
        var moves=ArrayBuffer[Int]()
        //Possible moves top right of the piece
        moves ++= genMovesIn1D(x, y, 1, 1, this, pieces)
        //moves top left of the piece
        moves ++= genMovesIn1D(x, y, -1, 1, this, pieces)
        //moves bottom right of the piece
        moves ++= genMovesIn1D(x, y, 1, -1, this, pieces)
        //moves bottom left of the piece
        moves ++= genMovesIn1D(x, y, -1, -1, this, pieces)
        //return the possible moves
        moves
    }
}

class Rook(var bn:Int, var p:Int) extends Piece(bn,p) with MovesInStraightLine{
    override def genPosMoves(pieces:ArrayBuffer[Piece]): ArrayBuffer[Int] = {
        //converts the piece's board number to XY to allow checking of if piece is inside the board
        var x = Piece.convToXY(this.boardNum,0)
        var y = Piece.convToXY(this.boardNum,1)
        var moves = ArrayBuffer[Int]()
        //Possible moves  right of the piece
        moves ++= genMovesIn1D(x, y, 1, 0, this, pieces)
        //moves left of the piece
        moves ++= genMovesIn1D(x, y, -1, 0, this, pieces)
        //moves top of the piece
        moves ++= genMovesIn1D(x, y, 0, 1, this, pieces)
        //moves bottom  of the piece
        moves ++= genMovesIn1D(x, y, 0, -1, this, pieces)
        //return the possible moves
        moves
    }
}

class Queen(var bn: Int, var p: Int) extends Piece(bn,p) with MovesInStraightLine{
    override def genPosMoves(pieces:ArrayBuffer[Piece]): ArrayBuffer[Int] = {
        //converts board number to xy coordinates to allow checking of if piece is inside the board
        var x = Piece.convToXY(this.boardNum,0)
        var y = Piece.convToXY(this.boardNum,1)
        var moves = ArrayBuffer[Int]()
        //Possible diagonal moves
        moves ++= genMovesIn1D(x, y, 1, 1, this, pieces)
        moves ++= genMovesIn1D(x, y, -1, 1, this, pieces)
        moves ++= genMovesIn1D(x, y, 1, -1, this, pieces)
        moves ++= genMovesIn1D(x, y, -1, -1, this, pieces)

        //Possible horizontal moves
        moves ++= genMovesIn1D(x, y, 1, 0, this, pieces)
        moves ++= genMovesIn1D(x, y, -1, 0, this, pieces)
        moves ++= genMovesIn1D(x, y, 0, 1, this, pieces)
        moves ++= genMovesIn1D(x, y, 0, -1, this, pieces)
        //return the possible moves
        moves
   }
}

class King(var bn: Int, var p: Int) extends Piece(bn,p) {
    override def genPosMoves(pieces:ArrayBuffer[Piece]): ArrayBuffer[Int] = {
        //converts board number to xy coordinates to allow checking of if piece is inside the board
        var x = Piece.convToXY(this.boardNum,0)
        var y = Piece.convToXY(this.boardNum,1)
        var moves = ArrayBuffer[Int]()
        //generates possible moves from the buttom left to the top right
        for (i<- -1 to 1){
            for (j<- -1 to 1){
                var move = Piece.convToBoardNum(x+i,y+j)
                var player = pieces(Piece.findPieceInd(move,pieces)).player
                //checks if the move is inside the board then checks if the move is an empty space or opponent piece
                if(Piece.chkIfInBoard(x+i,y+j)){
                    if(player == 0 || player != this.player){
                    moves += move
                    }
                }
                
            }
        }
        moves
    }
}

class Knight(var bn: Int, var p: Int) extends Piece(bn,p) {
    override def genPosMoves(pieces:ArrayBuffer[Piece]): ArrayBuffer[Int] = {
        //converts board number to xy coordinates to allow checking of if piece is inside the board
        var x = Piece.convToXY(this.boardNum,0)
        var y = Piece.convToXY(this.boardNum,1)
        var moves = ArrayBuffer[Int]()
        // for moves in the horizontal axis
        for(i <- -1 to 1 by 2){
            for(j<- -2 to 2 by 4){
                var move = Piece.convToBoardNum(x+i,y+j)
                var player = pieces(Piece.findPieceInd(move,pieces)).player
                //checks if the move is inside the board then checks if the move is an empty space or opponent piece
                if(Piece.chkIfInBoard(x+i,y+j)) {
                    if(player == 0 || player!=this.player){
                    moves+=move
                    }
                }
            }
        }
        //for move in the horizontal axis
        for(i <- -2 to 2 by 4){
            for(j<- -1 to 1 by 2) {
                var move = Piece.convToBoardNum(x+i,y+j)
                var player = pieces(Piece.findPieceInd(move,pieces)).player
                //checks if the move is inside the board then checks if the move is an empty space or opponent piece
                if(Piece.chkIfInBoard(x+i,y+j)){
                    if(player == 0 || player!=this.player) {
                    moves += move
                    }
                }
            }
        }
        moves
    }
}

class Pawn(var bn: Int, var p: Int) extends Piece(bn,p) {
    var moved = false
    override def genPosMoves(pieces: ArrayBuffer[Piece]): ArrayBuffer[Int] = {
        //converts board number to xy coordinates to allow checking of if piece is inside the board
        var x = Piece.convToXY(this.boardNum,0)
        var y = Piece.convToXY(this.boardNum,1)
        var moves = ArrayBuffer[Int]()
        //modifiers to change direction and distance of moves
        var movedCHK = 0
        var playerCHK = 1
        moves ++= pawnCap(x, y, pieces)
        //if the pawn has been moved increase the modifier (this reduces the distance the pawn can move by 1)
        if(this.moved)
            movedCHK=1
        // if the pawn belongs to player 2 change the modifier to -1 ( this reverses the direction of the pawn)
        if(this.player == 2)
            playerCHK = -1
        var i = 1
        var complete = false
        while(Piece.chkIfInBoard(x,y+i*playerCHK) && i < 3-movedCHK && complete == false) {
            //println(Piece.convToBoardNum(x,y+i*playerCHK))
            if(Piece.findPieceInd(Piece.convToBoardNum(x,y+i*playerCHK),pieces) == 32){
                moves += Piece.convToBoardNum(x,y+i*playerCHK) 
            }
            else{
                complete = true
            }
            i += 1
        }
        moves
    }
    def pawnCap(x: Int, y: Int, pieces: ArrayBuffer[Piece]): ArrayBuffer[Int] = {
        var left = 0
        var right = 0
        var moves = ArrayBuffer[Int]()
        var playerCHK = 1
        //checks player and reverses the direction if piece belongs to player 2
        if(this.player == 2)
        playerCHK = -1
        //checks if the left and right capture spaces are inside the board
        if(Piece.chkIfInBoard(x-1, y+1*playerCHK))
        left = Piece.convToBoardNum(x-1, y+1*playerCHK)
        if(Piece.chkIfInBoard(x+1, y+1*playerCHK))
        right = Piece.convToBoardNum(x+1, y+1*playerCHK)
        //checks if the spaces are occupied by an opponent's piece and add it to moves if it is
        if(pieces(Piece.findPieceInd(left, pieces)).player!= 0 && pieces(Piece.findPieceInd(left,pieces)).player!= this.player)
            moves += left
        if((pieces(Piece.findPieceInd(right, pieces)).player!= 0 && pieces(Piece.findPieceInd(right,pieces)).player!= this.player))
            moves += right
        //returns the pawn capture moves
        moves
    }
    override def move(to: Int, pieces: ArrayBuffer[Piece]): Unit = {
        this.boardNum = to
        if(Piece.convToXY(to, 1) == 8 && this.player == 1){
            var promotion = Main.showPromotionDialog(this)
            this.pawnPromotion(promotion,pieces)
        }
        if(Piece.convToXY(to, 1) == 1 && this.player == 2){
            var promotion = Main.showPromotionDialog(this)
            this.pawnPromotion(promotion,pieces)
        }
        this.moved = true
    }
    // removes pawn from the array buffer and adds a new piece based on the promotion selection
    def pawnPromotion(promotion: String, pieces: ArrayBuffer[Piece]): Unit = {
        pieces.remove(Piece.findPieceInd(this.boardNum, pieces))
        promotion match{
            case "Knight" => pieces.prepend(new Knight(this.boardNum, this.player))
            case "Bishop" => pieces.prepend(new Bishop(this.boardNum, this.player))
            case "Queen" => pieces.prepend(new Queen(this.boardNum, this.player))
            case "Rook" => pieces.prepend(new Rook(this.boardNum, this.player))
            //if no selection is made a queen piece will be created 
            case _ =>pieces.prepend(new Queen(this.boardNum, this.player))
        }
    }
}
trait MovesInStraightLine{
    //checks and generates possible moves starting from the piece and going in 1 direction 
    //modifier controls the direction of generation and checking (-1= down,1=up,0=no change)
    def genMovesIn1D(x: Int, y: Int, xModifier: Int, yModifier: Int, piece:Piece, pieces: ArrayBuffer[Piece]): ArrayBuffer[Int] = {  
        var moves = ArrayBuffer[Int]()
        var i=1
        var complete=false
        while (Piece.chkIfInBoard(x+i * xModifier, y+i * yModifier) && complete==false) {
            var move = Piece.convToBoardNum(x+i * xModifier,y+i * yModifier)
            //checks which player owns the piece on a given space 
            val player = pieces(Piece.findPieceInd(move,pieces)).player
            if(player == 0){
                moves += move
            }
            else if(player != piece.player){
                moves += move
                complete = true
            }
            else if(player == piece.player){
                complete = true
            }
            i += 1
        }
        moves
    }
}