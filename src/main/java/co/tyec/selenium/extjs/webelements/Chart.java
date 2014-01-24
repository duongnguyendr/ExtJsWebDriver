package co.tyec.selenium.extjs.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Chart extends ExtJSComponent {
	
	String FUNCTION_convertStoreToCSV = "SExt.convertStoreToCSV = function(store) {"
			+ "  var fields = Ext.Array.map(store.model.getFields(), function(field) {"
			+ "    return field.name;"
			+ "  });"
			+ "  var csvLines = ['\"' + fields.join('\",\"') + '\"'];"
			+ "  Ext.each(store.getRange(), function(modelObj) {"
			+ "    var propList = [];"
			+ "    Ext.Array.forEach(fields, function(fieldName) {"
			+ "      propList.push(modelObj.data[fieldName]);"
			+ "    });"
			+ "    csvLines.push('\"' + propList.join('\",\"') + '\"');"
			+ "  });"
			+ "  return csvLines.join(\"\\n\");"
			+ "}";
	
	String FUNCTION_convertStoreToJSON = "SExt.convertStoreToJSON = function (store) {"
			+ "  if(!store){"
			+ "	   return \"\";"
			+ "  }"
			+ "  var dataList = Ext.Array.map(store.getRange(), function(entry, list) {"
			+ "    var data = entry.data;"
			+ "    return data;"
			+ "  });"
			// Write the first only
			+ "  return Ext.encode(dataList);"
			+ "}";
	
	public Chart(WebDriver driver, ExtJSQueryType queryType, String query) {
		super(driver, queryType, query);
	}
	
	public Chart(WebDriver driver, WebElement topElement) {
		super(driver, topElement);
	}
	
	public String getChartAsJSON() {
		execScriptClean(FUNCTION_convertStoreToJSON);
		final String jsCode = "return SExt.convertStoreToJSON(extCmp.getStore());";
		return (String) execScriptOnExtJsCmp(jsCode);
	}
	
	public String getChartAsCSV() {
		execScriptClean(FUNCTION_convertStoreToCSV);
		final String jsCode = "return SExt.convertStoreToCSV(extCmp.getStore());";
		return (String) execScriptOnExtJsCmp(jsCode);
	}
	
}
