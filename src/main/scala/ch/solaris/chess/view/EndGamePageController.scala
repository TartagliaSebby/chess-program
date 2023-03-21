package ch.solaris.chess.view

import scalafxml.core.macros.sfxml
import scalafx.scene.control.{Label}
import scalafx.event.ActionEvent
import scalafx.stage.Stage
import ch.solaris.chess.Main
import scala.collection.mutable.ArrayBuffer
import scalafx.Includes._
import ch.solaris.chess.Main

@sfxml
class EndGamePageController(private val winnerLabel: Label){
    winnerLabel.text = Main.winner + " Won!" 
    def returnToMenu() = {
        Main.showMenu()
    }

}