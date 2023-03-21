package ch.solaris.chess.view

import scalafxml.core.macros.sfxml
import scalafx.beans.property.{StringProperty} 
import scalafx.scene.control.{TableView, TableColumn, Label}
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scala.collection.mutable.ArrayBuffer
import scalafx.Includes._

import ch.solaris.chess.Main
import ch.solaris.chess.model.Match

@sfxml
class MatchHistorySelectController(
    private val matchTable: TableView[Match],
    private val matchColumn: TableColumn[Match, String],
    private val player1Label: Label,
    private val player2Label: Label,
    private val winnerLabel: Label
){
    /*
    code for MatchHistorySelectController based on tutorial
    */
    //initialise table
    matchTable.items = Match.matchHistory
    //initialise cell values
    matchColumn.cellValueFactory = {_.value.matchNum}

    private def showMatchDetails (selMatch: Option[Match]){
        selMatch match{
            case Some(selMatch)=>
            player1Label.text <== selMatch.player1
            player2Label.text <== selMatch.player2
            winnerLabel.text <== selMatch.winnerName + new StringProperty(" Victory")
            case None =>
            player1Label.text.unbind()
            player2Label.text.unbind()
            winnerLabel.text.unbind()
            player1Label.text = ""
            player2Label.text = ""
            winnerLabel.text = ""
        }
    }
    showMatchDetails(None)

    matchTable.selectionModel().selectedItem.onChange(
        (_, _, newValue) => showMatchDetails(Option(newValue))
    )
    //event handlers for buttons
    def returnToMenu() = {
         Main.showMenu()
    }
    def replayMatch() = {
        if(Match.matchHistory.length > 0){
            Main.showMatchHistoryView(Match.matchHistory.indexOf(matchTable.selectionModel().selectedItem.value))
        }
        else{
            val alert = new Alert(Alert.AlertType.Warning){
            initOwner(Main.stage)
            title = "No matches"
            headerText = "There are no previous matches"
            contentText = "Play a match"
            }.showAndWait()
        }
        
    }
}