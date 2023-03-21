package ch.solaris.chess.view

import scalafxml.core.macros.sfxml
import scalafx.scene.control.{TextField}
import scalafx.event.ActionEvent
import scalafx.stage.Stage
import ch.solaris.chess.Main
import scala.collection.mutable.ArrayBuffer
import scalafx.Includes._
import ch.solaris.chess.Main

@sfxml
class PreGamePageController(private val p1TextField: TextField, private val p2TextField: TextField){
    var dialogStage: Stage = null
    var names = ArrayBuffer[String]("Player1", "Player 2")
    def startGame(e: ActionEvent) = {
        var p1Name = p1TextField.text.value.toString 
        var p2Name = p2TextField.text.value.toString
       //if no value is entered give default name
        if(p1Name == "")
        p1Name = "Player 1"
        if(p2Name == "")
        p2Name = "Player 2"
        names.prepend(p2Name)
        names.prepend(p1Name)
        dialogStage.close
    }
}
