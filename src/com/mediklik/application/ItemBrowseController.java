package com.mediklik.application;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mediklik.db.Connect;
import com.mediklik.models.Item;
import com.mediklik.models.ItemBrowseData;
import com.mediklik.models.ItemDisplay;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ItemBrowseController implements Initializable {
	@FXML
	private Label saldoLabel;
	@FXML
	private GridPane itemGridPane;
	@FXML
	private Button cartButton;
	@FXML
	private Button topUpButton;
	@FXML
	private Button prevButton;	
	@FXML
	private Button nextButton;
	@FXML
	private Button allButton;
	@FXML
	private Button syrupButton;
	@FXML
	private Button tabletButton;
	@FXML
	private Button capsuleButton;
	@FXML
	private Button olesButton;
	@FXML
	private Button otherButton;
	
	private Image cartButtonImage;
	private Image topUpButtonImage;
	private ImageView cartButtonIV;
	private ImageView topUpButtonIV;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		SessionController itemBrowseSession = SessionController.getSession();
		Connect itemBrowseConnect = Connect.getConnection();
		int saldo = itemBrowseSession.getUser().getBalance();
		cartButtonImage = new Image("/media/basket.png");
		topUpButtonImage = new Image("/media/money.png");
		cartButtonIV = new ImageView(cartButtonImage);
		topUpButtonIV = new ImageView(topUpButtonImage);
		cartButtonIV.setFitWidth(50);
		topUpButtonIV.setFitWidth(50);
		cartButton.setGraphic(cartButtonIV);
		topUpButton.setGraphic(topUpButtonIV);
		
		if (saldo <= 0) {
			saldoLabel.setText("Kosong");
		}
		else {
			saldoLabel.setText("Rp" + Integer.toString(saldo));
		}
		
		int itemPageCount = itemBrowseSession.getItemDisplayList().size();

		if (itemPageCount - ItemBrowseData.getPage() * 9 > 9)
			itemPageCount = 9;
		else
			itemPageCount = itemPageCount - ItemBrowseData.getPage() * 9;
		
		ItemDisplay itemDisplayTMP;
		
		for (int i = 0; i < itemPageCount; i++) {
			itemDisplayTMP = itemBrowseSession.getItemDisplayList().get(i + ItemBrowseData.getPage() * 9);
			itemGridPane.add(itemDisplayTMP.getVbox(), i%3, i/3);
			GridPane.setMargin(itemDisplayTMP.getVbox(), new Insets(0, 0, 25, 0));
		}
		
		if (ItemBrowseData.getPage() == 0) {
			prevButton.setDisable(true);
		}
		
		if (ItemBrowseData.getPage() == ItemBrowseData.getMax()) {
			nextButton.setDisable(true);
		}
	}
	
	public void handleSaldo() {
		try {
			Parent registerSceneRoot = FXMLLoader.load(getClass().getResource("saldo.fxml"));
			Stage stage = (Stage) itemGridPane.getScene().getWindow();
			Scene registerScene = new Scene(registerSceneRoot);
			stage.setScene(registerScene);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleCart() {
		try {
			Parent registerSceneRoot = FXMLLoader.load(getClass().getResource("cart.fxml"));
			Stage stage = (Stage) itemGridPane.getScene().getWindow();
			Scene registerScene = new Scene(registerSceneRoot);
			stage.setScene(registerScene);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handlePrev() {
		itemGridPane.getChildren().clear();
		ItemBrowseData.decPage();
		SessionController itemBrowseSession = SessionController.getSession();
		
		int itemPageCount = itemBrowseSession.getItemDisplayList().size();

		if (itemPageCount - ItemBrowseData.getPage() * 9 > 9)
			itemPageCount = 9;
		else
			itemPageCount = itemPageCount - ItemBrowseData.getPage() * 9;
		
		ItemDisplay itemDisplayTMP;
		
		for (int i = 0; i < itemPageCount; i++) {
			itemDisplayTMP = itemBrowseSession.getItemDisplayList().get(i + ItemBrowseData.getPage() * 9);
			itemGridPane.add(itemDisplayTMP.getVbox(), i%3, i/3);
			GridPane.setMargin(itemDisplayTMP.getVbox(), new Insets(0, 0, 25, 0));
		}
		
		if (ItemBrowseData.getPage() == 0) {
			prevButton.setDisable(true);
		}
		else {
			prevButton.setDisable(false);
		}
		
		if (ItemBrowseData.getPage() == ItemBrowseData.getMax()) {
			nextButton.setDisable(true);
		}
		else {
			nextButton.setDisable(false);
		}
	}
	
	public void handleNext() {
		itemGridPane.getChildren().clear();
		ItemBrowseData.incPage();
		SessionController itemBrowseSession = SessionController.getSession();
		
		int itemPageCount = itemBrowseSession.getItemDisplayList().size();

		if (itemPageCount - ItemBrowseData.getPage() * 9 > 9)
			itemPageCount = 9;
		else
			itemPageCount = itemPageCount - ItemBrowseData.getPage() * 9;
		
		ItemDisplay itemDisplayTMP;
		
		for (int i = 0; i < itemPageCount; i++) {
			itemDisplayTMP = itemBrowseSession.getItemDisplayList().get(i + ItemBrowseData.getPage() * 9);
			itemGridPane.add(itemDisplayTMP.getVbox(), i%3, i/3);
			GridPane.setMargin(itemDisplayTMP.getVbox(), new Insets(0, 0, 25, 0));
		}
		
		if (ItemBrowseData.getPage() == 0) {
			prevButton.setDisable(true);
		}
		else {
			prevButton.setDisable(false);
		}
		
		if (ItemBrowseData.getPage() == ItemBrowseData.getMax()) {
			nextButton.setDisable(true);
		}
		else {
			nextButton.setDisable(false);
		}
	}
	
	public void handleAllButton() {
		itemGridPane.getChildren().clear();
		SessionController itemBrowseSession = SessionController.getSession();
		ArrayList<Item> itemList = itemBrowseSession.getItemList();
		ArrayList<ItemDisplay> itemDisplayList = itemBrowseSession.getItemDisplayList();
		itemDisplayList.clear();
		for (Item i : itemList) {
			itemDisplayList.add(new ItemDisplay(i));
		}
		
		ItemBrowseData.setPage(0);
		
		ItemDisplay itemDisplayTMP;
		
		int itemPageCount = itemDisplayList.size();
		if (itemPageCount - ItemBrowseData.getPage() * 9 > 9)
			itemPageCount = 9;
		else
			itemPageCount = itemPageCount - ItemBrowseData.getPage() * 9;
		
		for (int i = 0; i < itemPageCount; i++) {
			itemDisplayTMP = itemBrowseSession.getItemDisplayList().get(i + ItemBrowseData.getPage() * 9);
			itemGridPane.add(itemDisplayTMP.getVbox(), i%3, i/3);
			GridPane.setMargin(itemDisplayTMP.getVbox(), new Insets(0, 0, 25, 0));
		}
		
		if (ItemBrowseData.getPage() == 0) {
			prevButton.setDisable(true);
		}
		else {
			prevButton.setDisable(false);
		}
		
		if (ItemBrowseData.getPage() == ItemBrowseData.getMax()) {
			nextButton.setDisable(true);
		}
		else {
			nextButton.setDisable(false);
		}
		
	}
	
	public void handleSyrupButton() {
		itemGridPane.getChildren().clear();
		SessionController itemBrowseSession = SessionController.getSession();
		ArrayList<Item> itemList = itemBrowseSession.getItemList();
		ArrayList<ItemDisplay> itemDisplayList = itemBrowseSession.getItemDisplayList();
		itemDisplayList.clear();
		
		for (Item i : itemList) {
			if (i.getCategoryID() == 1)
				itemDisplayList.add(new ItemDisplay(i));
		}
		
		ItemBrowseData.setPage(0);
		ItemBrowseData.setMax((itemDisplayList.size() / 9) + ((itemDisplayList.size() % 9 > 0) ? 1 : 0) - 1);
		
		ItemDisplay itemDisplayTMP;
		
		int itemPageCount = itemDisplayList.size();
		if (itemPageCount - ItemBrowseData.getPage() * 9 > 9)
			itemPageCount = 9;
		else
			itemPageCount = itemPageCount - ItemBrowseData.getPage() * 9;
		
		for (int i = 0; i < itemPageCount; i++) {
			itemDisplayTMP = itemDisplayList.get(i + ItemBrowseData.getPage() * 9);
			itemGridPane.add(itemDisplayTMP.getVbox(), i%3, i/3);
			
			GridPane.setMargin(itemDisplayTMP.getVbox(), new Insets(0, 0, 25, 0));
		}
		
		if (ItemBrowseData.getPage() == 0) {
			prevButton.setDisable(true);
		}
		else {
			prevButton.setDisable(false);
		}
		
		if (ItemBrowseData.getPage() == ItemBrowseData.getMax()) {
			nextButton.setDisable(true);
		}
		else {
			nextButton.setDisable(false);
		}
		
	}
	
	public void handleTabletButton() {
		itemGridPane.getChildren().clear();
		SessionController itemBrowseSession = SessionController.getSession();
		ArrayList<Item> itemList = itemBrowseSession.getItemList();
		ArrayList<ItemDisplay> itemDisplayList = itemBrowseSession.getItemDisplayList();
		itemDisplayList.clear();
		for (Item i : itemList) {
			if (i.getCategoryID() == 2)
				itemDisplayList.add(new ItemDisplay(i));
		}
		
		ItemBrowseData.setPage(0);
		ItemBrowseData.setMax((itemDisplayList.size() / 9) + ((itemDisplayList.size() % 9 > 0) ? 1 : 0) - 1);
		
		ItemDisplay itemDisplayTMP;
		
		int itemPageCount = itemDisplayList.size();
		System.out.println(itemPageCount);
		if (itemPageCount - ItemBrowseData.getPage() * 9 > 9)
			itemPageCount = 9;
		else
			itemPageCount = itemPageCount - ItemBrowseData.getPage() * 9;
		
		for (int i = 0; i < itemPageCount; i++) {
			itemDisplayTMP = itemDisplayList.get(i + ItemBrowseData.getPage() * 9);
			itemGridPane.add(itemDisplayTMP.getVbox(), i%3, i/3);
			GridPane.setMargin(itemDisplayTMP.getVbox(), new Insets(0, 0, 25, 0));
		}
		
		if (ItemBrowseData.getPage() == 0) {
			prevButton.setDisable(true);
		}
		else {
			prevButton.setDisable(false);
		}
		
		if (ItemBrowseData.getPage() == ItemBrowseData.getMax()) {
			nextButton.setDisable(true);
		}
		else {
			nextButton.setDisable(false);
		}
		
	}

	public void handleCapsuleButton() {
		itemGridPane.getChildren().clear();
		SessionController itemBrowseSession = SessionController.getSession();
		ArrayList<Item> itemList = itemBrowseSession.getItemList();
		ArrayList<ItemDisplay> itemDisplayList = itemBrowseSession.getItemDisplayList();
		itemDisplayList.clear();
		for (Item i : itemList) {
			if (i.getCategoryID() == 3)
				itemDisplayList.add(new ItemDisplay(i));
		}
		
		ItemBrowseData.setPage(0);
		ItemBrowseData.setMax((itemDisplayList.size() / 9) + ((itemDisplayList.size() % 9 > 0) ? 1 : 0) - 1);
		
		ItemDisplay itemDisplayTMP;
		
		int itemPageCount = itemDisplayList.size();
		if (itemPageCount - ItemBrowseData.getPage() * 9 > 9)
			itemPageCount = 9;
		else
			itemPageCount = itemPageCount - ItemBrowseData.getPage() * 9;
		
		for (int i = 0; i < itemPageCount; i++) {
			itemDisplayTMP = itemDisplayList.get(i + ItemBrowseData.getPage() * 9);
			itemGridPane.add(itemDisplayTMP.getVbox(), i%3, i/3);
			GridPane.setMargin(itemDisplayTMP.getVbox(), new Insets(0, 0, 25, 0));
		}
		
		if (ItemBrowseData.getPage() == 0) {
			prevButton.setDisable(true);
		}
		else {
			prevButton.setDisable(false);
		}
		
		if (ItemBrowseData.getPage() == ItemBrowseData.getMax()) {
			nextButton.setDisable(true);
		}
		else {
			nextButton.setDisable(false);
		}		
	}

	public void handleOlesButton() {
		itemGridPane.getChildren().clear();
		SessionController itemBrowseSession = SessionController.getSession();
		ArrayList<Item> itemList = itemBrowseSession.getItemList();
		ArrayList<ItemDisplay> itemDisplayList = itemBrowseSession.getItemDisplayList();
		itemDisplayList.clear();
		for (Item i : itemList) {
			if (i.getCategoryID() == 4)
				itemDisplayList.add(new ItemDisplay(i));
		}
		
		ItemBrowseData.setPage(0);
		ItemBrowseData.setMax((itemDisplayList.size() / 9) + ((itemDisplayList.size() % 9 > 0) ? 1 : 0) - 1);
		
		ItemDisplay itemDisplayTMP;
		
		int itemPageCount = itemDisplayList.size();
		if (itemPageCount - ItemBrowseData.getPage() * 9 > 9)
			itemPageCount = 9;
		else
			itemPageCount = itemPageCount - ItemBrowseData.getPage() * 9;
		
		for (int i = 0; i < itemPageCount; i++) {
			itemDisplayTMP = itemDisplayList.get(i + ItemBrowseData.getPage() * 9);
			itemGridPane.add(itemDisplayTMP.getVbox(), i%3, i/3);
			GridPane.setMargin(itemDisplayTMP.getVbox(), new Insets(0, 0, 25, 0));
		}
		
		if (ItemBrowseData.getPage() == 0) {
			prevButton.setDisable(true);
		}
		else {
			prevButton.setDisable(false);
		}
		
		if (ItemBrowseData.getPage() == ItemBrowseData.getMax()) {
			nextButton.setDisable(true);
		}
		else {
			nextButton.setDisable(false);
		}
		
	}
	
	public void handleOtherButton() {
		itemGridPane.getChildren().clear();
		SessionController itemBrowseSession = SessionController.getSession();
		ArrayList<Item> itemList = itemBrowseSession.getItemList();
		ArrayList<ItemDisplay> itemDisplayList = itemBrowseSession.getItemDisplayList();
		itemDisplayList.clear();
		for (Item i : itemList) {
			if (i.getCategoryID() == 5)
				itemDisplayList.add(new ItemDisplay(i));
		}
		
		ItemBrowseData.setPage(0);
		ItemBrowseData.setMax((itemDisplayList.size() / 9) + ((itemDisplayList.size() % 9 > 0) ? 1 : 0) - 1);
		
		ItemDisplay itemDisplayTMP;
		
		int itemPageCount = itemDisplayList.size();
		if (itemPageCount - ItemBrowseData.getPage() * 9 > 9)
			itemPageCount = 9;
		else
			itemPageCount = itemPageCount - ItemBrowseData.getPage() * 9;
		
		for (int i = 0; i < itemPageCount; i++) {
			itemDisplayTMP = itemDisplayList.get(i + ItemBrowseData.getPage() * 9);
			itemGridPane.add(itemDisplayTMP.getVbox(), i%3, i/3);
			GridPane.setMargin(itemDisplayTMP.getVbox(), new Insets(0, 0, 25, 0));
		}
		
		if (ItemBrowseData.getPage() == 0) {
			prevButton.setDisable(true);
		}
		else {
			prevButton.setDisable(false);
		}
		
		if (ItemBrowseData.getPage() == ItemBrowseData.getMax()) {
			nextButton.setDisable(true);
		}
		else {
			nextButton.setDisable(false);
		}
		
	}
}

