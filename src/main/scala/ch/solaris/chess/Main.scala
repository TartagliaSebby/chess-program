package ch.solaris.chess

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import scalafx.stage.{Stage, Modality}
import scalafx.scene.image.Image
import javafx.{scene => jfxs}
import scala.collection.mutable.ArrayBuffer
import scalafx.Includes._
import ch.solaris.chess.model.Piece
import ch.solaris.chess.view.PawnPromotionController

object Main extends JFXApp{
  val rootResource = getClass.getResource("view/RootLayout.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load()
  val roots =loader.getRoot[jfxs.layout.BorderPane]

  stage = new PrimaryStage {
      icons += new  Image(getClass.getResourceAsStream("/images/Icon.png"))
      title = "Chess Program"
      scene = new Scene {
          root = roots
      }
   }

  def showMenu() = {
  val resource = getClass.getResource("view/Menu.fxml")
  val loader = new FXMLLoader(resource, NoDependencyResolver)
  loader.load();
  val roots = loader.getRoot[jfxs.layout.AnchorPane]
  this.roots.setCenter(roots)
  }
  def showPreGameDialog(): ArrayBuffer[String] = {
    val resource = getClass.getResourceAsStream("view/PreGamePage.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[ch.solaris.chess.view.PreGamePageController#Controller]
    val dialog = new Stage() {
      title = "Enter Player Names"
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots2
      }
    }
    control.dialogStage = dialog
    dialog.showAndWait() 
    control.names
  }
  def showGamePage() = {
    val resource = getClass.getResource("view/GamePage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }
  var winner = ""
  def showEndGamePage(winnerName: String) = {
    winner = winnerName
    val resource = getClass.getResource("view/EndGamePage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val control = loader.getController[ch.solaris.chess.view.EndGamePageController#Controller]
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showPromotionDialog(pawn: Piece): String = {
    val resource = getClass.getResourceAsStream("view/PawnPromotion.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[ch.solaris.chess.view.PawnPromotionController#Controller]
    val dialog = new Stage() {
      title = "Pawn Promotion"
      icons += new Image(getClass.getResourceAsStream("/images/pawnPromotionIcon.png"))
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        stylesheets = List(getClass.getResource("/StyleSheets/PawnPromotion.css").toString)
        root = roots2
      }
    }
    control.dialogStage = dialog
    control.pawn = pawn
    control.initImageView()
    dialog.showAndWait() 
    control.selectedPromotion
  }
  
  def showMatchHistorySelect(){
      val resource = getClass.getResource("view/MatchHistorySelect.fxml")
      val loader = new FXMLLoader(resource, NoDependencyResolver)
      loader.load();
      val roots = loader.getRoot[jfxs.layout.AnchorPane]
      this.roots.setCenter(roots)
  }
  var matchIndex = -100
  def showMatchHistoryView(index: Int){
      matchIndex = index
      val resource = getClass.getResource("view/MatchHistoryView.fxml")
      val loader = new FXMLLoader(resource, NoDependencyResolver)
      loader.load();
      val control = loader.getController[ch.solaris.chess.view.MatchHistoryViewController#Controller]
      val roots = loader.getRoot[jfxs.layout.AnchorPane]
      this.roots.setCenter(roots)
  }
  
  showMenu()
}
 
    
