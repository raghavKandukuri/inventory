package com.raghav.inventory.controllers;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raghav.inventory.models.Inventory;
import com.raghav.inventory.models.Item;
import com.raghav.inventory.models.Report;
import com.raghav.inventory.models.ReportItem;
import com.raghav.repositories.InventoryRepository;
import com.raghav.repositories.ItemRepository;


@RestController
@RequestMapping(path="/api")
@CrossOrigin("*")
public class InventoryRestCotroller {

	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	MongoOperations mongoOperations;
	
	@PostMapping("/createItem")
	public Item createItem(@Valid @RequestBody Item item) {
		item.setCreatedAt(new Date());
		return itemRepository.save(item);
	}
	
	@GetMapping("/fetchItems")
	public List<Item> getItems() {
		return itemRepository.findAll();
	}
	
	@PostMapping("/updateBuy")
	public Inventory updateBuy(@Valid @RequestBody Inventory inventory) {
		inventory.setBuyOrSell("B");
		inventory.setCreatedAt(new Date());
		return inventoryRepository.save(inventory);
	}
	
	@PostMapping("/updateSell")
	public Inventory updateSell(@Valid @RequestBody Inventory inventory) {
		inventory.setBuyOrSell("S");
		inventory.setCreatedAt(new Date());
		return inventoryRepository.save(inventory);
	}
	
	@PostMapping("/deleteItem")
	public String deleteItem(@Valid @RequestBody Item item){
		String returnVal = "Successfully Deleted";
		boolean isInventory = false;
		Inventory inventory = new Inventory();
		Item itm = mongoOperations.findOne(query(where("name").is(item.getName())), Item.class, "item");
		//Item itm = itemRepository.findAll().stream().filter(o -> o.getName().equalsIgnoreCase(item.getName())).findFirst().get();
		//Item itm = itemRepository.findOne(item.getName());
		if(itm != null) {
			itm.setIsdeleted(Boolean.TRUE);
			Inventory inv = mongoOperations.findOne(query(where("itemName").is(item.getName())), Inventory.class, "inventory");
			//Inventory inv = inventoryRepository.findAll().stream().filter(o -> o.getItemName().equalsIgnoreCase(item.getName())).findFirst().get();
			if(inv != null){
				inventory.setItemName(item.getName());
				inventory.setQuantity(inv.getQuantity());
				inventory.setBuyOrSell("D");// Delete
				inventory.setCreatedAt(new Date());
				inventoryRepository.save(inventory);
				isInventory = true;
			}
		} 
		if(isInventory){
			itm.setIsdeleted(Boolean.TRUE);
			Update update = new Update();
			mongoOperations.updateFirst(query(where("name").is(item.getName())), Update.update("isdeleted", Boolean.TRUE), "item");
		} else
			returnVal = "Item is not found";
		
		return returnVal;
	}
	
	@GetMapping("/report")
	public Report report() {
		return generateReport();
	}

	@PostMapping("updateSellPrice")
	public Item updateSellPrice(@Valid @RequestBody Item item){
		mongoOperations.updateFirst(query(where("name").is(item.getName())), Update.update("newSellingPrice", item.getNewSellingPrice()), "item");
		Item itm = mongoOperations.findOne(query(where("name").is(item.getName())), Item.class, "item");
		return itm;
	}
	
	private Report generateReport() {
		Report report = new Report();
		
		Sort sortByName = new Sort(Sort.Direction.ASC, "name");
		List<Item> items = itemRepository.findAll(sortByName);
		Map<String, Item> itemsMap = items.stream()
									.collect(Collectors.toMap(Item::getName, item -> item));
		
		List<Inventory> inventoryList = inventoryRepository.findAll();
		Map<String, List<Inventory>> inventoryMap = inventoryList.stream().
									collect(Collectors.groupingBy(Inventory::getItemName));
		Double totalValue = 0.0;
		Double profitValue = 0.0;
		ReportItem reportItem;
		List<ReportItem> itemsList = new ArrayList<ReportItem>();
		Integer sellQty;
		Integer deleteQty;
		List<Inventory> nameList;
		
		for (Item item : items) {
			reportItem = new ReportItem();
			reportItem.setItemName(item.getName());
			reportItem.setBoughtAt(item.getCostPrice());
			if(null != item.getNewSellPrCreatedAt() && 
					item.getNewSellPrCreatedAt().compareTo(new Date()) >= 0 ){
				reportItem.setSoldAt(item.getNewSellingPrice());
			} else {
				reportItem.setSoldAt(item.getSellingPrice());
			}
			sellQty=0;
			deleteQty=0;
			if(inventoryMap.containsKey(item.getName())) {
				nameList = inventoryMap.get(item.getName());
				Integer buyQty = nameList.stream()
						.filter(o -> "B".equalsIgnoreCase(o.getBuyOrSell()))
						.mapToInt(o -> o.getQuantity())
						.sum();
				sellQty = nameList.stream()
						.filter(o-> "S".equalsIgnoreCase(o.getBuyOrSell()))
						.mapToInt(o -> o.getQuantity())
						.sum();
				deleteQty = nameList.stream()
						.filter(o -> "D".equalsIgnoreCase(o.getBuyOrSell()))
						.mapToInt(o -> o.getQuantity())
						.sum();
				reportItem.setAvailableQty(buyQty - sellQty);
			}
			reportItem.setValue(reportItem.getBoughtAt() * reportItem.getAvailableQty());
			profitValue = profitValue + (
							((reportItem.getSoldAt()-reportItem.getBoughtAt()) * sellQty) 
							  - (deleteQty * reportItem.getBoughtAt()));
			if(item.getIsdeleted() == null || !item.getIsdeleted()) {
				totalValue = totalValue + reportItem.getValue();				
				itemsList.add(reportItem);
			}
		}
		report.setItemsList(itemsList);
		report.setTotalValue(Math.round(totalValue * 100.0)/100.0);
		report.setProfit(Math.round(profitValue * 100.0)/100.0);
		
		return report;
	}
}
