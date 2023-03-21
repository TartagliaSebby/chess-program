package ch.solaris.chess.model

import scala.collection.mutable.ArrayBuffer
import scalafx.collections.ObservableBuffer
import scalafx.beans.property.{StringProperty} 

class Match(val player1l: String, val player2l: String, val winnerNamel: String, val moves: ArrayBuffer[Int], val pieces: ArrayBuffer[Piece]) {
    var player1 = new StringProperty(player1l)
    var player2 = new StringProperty(player2l)
    var winnerName = new StringProperty(winnerNamel)
    def matchNum(): StringProperty = {
        val matchNum =  Match.matchHistory.indexOf(this) +1
        new StringProperty("Match " + matchNum)
    }
}
object Match{
    var matchHistory = ObservableBuffer[Match]()
}