package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Chart extends ExtJSComponent {
	
	public Chart(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
	}
	
	public Chart(WebDriver driver, WebElement topElement) {
		super(driver, topElement);
	}
	
	String getSvs = "SExt.prototype.getSvgs = function (itemId){"
			+ "var domEl = document.getElementById(itemId);"
			+ "if (domEl) {"
			+ "	return domEl.getElementsByTagName('svg') || [];"
			+ "}"
			+ "return [];"
			+ "}";
	
	String convertStoreToJSON = "SExt.prototype.convertStoreToJSON = function (store) {"
			+ "if(!store){"
			+ "	return \"\";"
			+ "}"
			+ "var dataList = Ext.Array.map(store.getRange(), function(entry, list) {"
			+ "    var data = entry.data;"
			+ "    return data;"
			+ "});"
			// Write the first only
			+ "return Ext.encode(dataList);"
			+ "}";
	
	String convertStoreToCSV = "SExt.prototype.convertStoreToCSV = function(store) {"
			+ "var fields = Ext.Array.map(store.model.getFields(), function(field) {"
			+ "    return field.name;"
			+ "}), csvLines = [ fields.join(',') ];"
			+ "Ext.each(store.getRange(), function(modelObj) {"
			+ "    var propList = [];"
			+ "    Ext.Array.forEach(fields, function(fieldName) {"
			+ "	    propList.push(modelObj.data[fieldName]);"
			+ "    });"
			+ "    csvLines.push(propList.join(','));"
			+ "});"
			+ "return csvLines.join('\r\n');"
			+ "}";
	
	String getFirstSVGStore = "SExt.prototype.getFirstSVGStore = function (itemId){"
			+ "var store = store;"
			+ "var svgs = this.getSvgs(itemId);"
			+ "if(!svgs.length){"
			+ "	return null;"
			+ "}"
			+ "Ext.each(svgs, function(el) {"
			+ "    var container = el.parentElement,"
			+ "    	extCmp = Ext.getCmp(container.id);"
			// ExtJSComponent doesnt exist or doesn't have a store
			+ "    if(!extCmp || !extCmp.getStore){"
			+ "    	return;"
			+ "    }"
			+ "    store = extCmp.getStore();"
			+ "    return true;"
			+ "});"
			+ "return store;"
			+ "}";
	/**
	 * Returns the first store found
	 * 
	 * @param itemId
	 *            The first store found under the itemID (ex, Panel-1012, or Chart-1021) is returned
	 * @param uuid
	 *            The store data is saved to this uuid for silk4j to read
	 */
	String getChartAsJSON = "SExt.prototype.getChartAsJSON : function (itemId, uuid) {"
			// find the chart & store
			+ "var store = this.getFirstSVGStore(itemId);"
			// convert store to string
			+ "var json = this.convertStoreToJSON(store);"
			// write string to unique id
			+ "writeDataToDiv(json, uuid);"
			+ "}";
	
	/**
	 * Returns the first store found as a CSV object
	 * 
	 * @param itemId
	 *            The first store found under the itemID (ex, Panel-1012, or Chart-1021) is returned
	 * @param uuid
	 *            The store data is saved to this uuid for silk4j to read
	 */
	String getChartAsCSV = "SExt.prototype.getChartAsCSV = function (itemId, uuid) {"
			// find the chart & store
			+ "var store = this.getFirstSVGStore(itemId);"
			// convert store to string
			+ "var csv = this.convertStoreToCSV(store);"
			// write string to unique id
			+ "writeDataToDiv(csv, uuid);"
			+ "}";
	
}
