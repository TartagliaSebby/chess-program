package ch.solaris.chess.view
import scalafxml.core.macros.sfxml
import ch.solaris.chess.Main

@sfxml
class MenuController(){
   def startGame() = {
        Main.showGamePage()
    }
    def matchHistorySelect() ={
        Main.showMatchHistorySelect()
    }
}