package co.tyec.selenium.extjs.webelements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class GridComponentTest {
	WebDriver driver;
	
	JavascriptExecutor js;
	
	@Before
	public void setUp() throws Exception {
		driver = Mockito.mock(WebDriver.class, Mockito.withSettings().extraInterfaces(JavascriptExecutor.class));
		js = (JavascriptExecutor) driver;
		// Global Mocks
		Mockito.when(((JavascriptExecutor) driver).executeScript(Mockito.contains("Ext.Ajax.isLoading()"))).thenReturn("false");
	}
	
	/**
	 * testCheckBox Method
	 */
	
	@Test
	public void testGridTypeLocator() {
		// chang this line to allow some creation of Grid. see Checkbox.
		Mockito.when(js.executeScript("window.findComponentByText(null,'grid')")).thenReturn("grid_id");
		Grid grid = new Grid(driver, ExtJSQueryType.ComponentQuery, "[text='grid']");
		assertNotNull(grid);
		
		Mockito.when(js.executeScript("window.findComponentByText(null,'grid')")).thenReturn("grid_id");
		assertEquals("Grid id is incorrect", "grid_id", grid.getComponentId());
	}
	
	// /**
	// * Checking Grid getCellDomObject method
	// */
	// @Test
	// public void testGridGetCellDomObject() {
	//
	// final ComponentLocator locator = loc.createLocator(Xtype.GRID, 0);
	// Mockito.when(sel.getEval("window.findComponentByText(null,'grid')")).thenReturn("grid_id");
	// assertTrue("locator has the wrong type", locator instanceof TypeLocator);
	// final Grid grid = new Grid(locator);
	// Assert.assertEquals("window.Ext.fly(window.Ext.getCmp('grid_id').view.getCell(0,0)).dom", grid.getCellDomObjectExpression(0, 0));
	//
	// }
	//
	// /**
	// * checking gird getCellValue method
	// */
	//
	// @Test
	// public void testGridGetCellValue() {
	// final ComponentLocator locator = loc.createLocator(Xtype.GRID, 0);
	// Mockito.when(sel.getEval("window.findComponentByText(null,'grid')")).thenReturn("grid_id");
	// assertTrue("locator has the wrong type", locator instanceof TypeLocator);
	// final Grid grid = new Grid(locator);
	// Mockito.when(sel.getEval("window.Ext.fly(window.Ext.getCmp('grid_id').view.getCell(0,0)).dom.textContent")).thenReturn("get_cell_value_exp");
	// Assert.assertEquals("get_cell_value_exp", grid.getCellValue(0, 0));
	//
	// }
	//
	// /**
	// * checking gird getColumnHeader method
	// */
	// @Test
	// public void testGridGetColumnHeader() {
	// final ComponentLocator locator = loc.createLocator(Xtype.GRID, 0);
	// Mockito.when(sel.getEval("window.findComponentByText(null,'grid')")).thenReturn("grid_id");
	// assertTrue("locator has the wrong type", locator instanceof TypeLocator);
	// final Grid grid = new Grid(locator);
	// Mockito.when(sel.getEval("window.Ext.getCmp('grid_id').getColumnModel().getColumnHeader(1)")).thenReturn("get_Column_Header_value_exp");
	// Assert.assertEquals("get_Column_Header_value_exp", grid.getColumnHeader(1));
	//
	// }
	//
	// /**
	// * checking gird getGridStoreCount method
	// */
	// @Test
	// public void testGridGetGridStoreCount() {
	// final ComponentLocator locator = loc.createLocator(Xtype.GRID, 0);
	// Mockito.when(sel.getEval("window.findComponentByText(null,'grid')")).thenReturn("grid_id");
	// assertTrue("locator has the wrong type", locator instanceof TypeLocator);
	// final Grid grid = new Grid(locator);
	// Mockito.when(sel.getEval("window.Ext.getCmp('grid_id').disabled != true")).thenReturn("true");
	// Mockito.when(sel.getEval("window.Ext.getCmp('grid_id').getStore().data.length")).thenReturn("1");
	// Assert.assertEquals((Integer) 1, grid.getGridStoreCount());
	// }
	//
	// /**
	// * checking gird clickOnCell method
	// */
	// @Test
	// public void testGridClickOnCell() {
	// Mockito.when(sel.getEval("window.Ext.fly(window.Ext.getCmp('grid_id').view.getCell(0,0)).dom.textContent")).thenReturn("get_cell_value");
	// final ComponentLocator locator = loc.createLocator(Xtype.GRID, 0);
	// Mockito.when(sel.getEval("window.findComponentByText(null,'grid')")).thenReturn("grid_id");
	// assertTrue("locator has the wrong type", locator instanceof TypeLocator);
	// final Grid grid = new Grid(locator);
	// grid.clickOnCell(0, 0);
	// Mockito.verify(sel).click("//a[contains(text(),'get_cell_value')]");
	// }
	//
	// /**
	// * checking gird getKeys method
	// */
	// @Test
	// public void testGridGetKeys() {
	// final ComponentLocator locator = loc.createLocator(Xtype.GRID, 0);
	// Mockito.when(sel.getEval("window.findComponentByText(null,'grid')")).thenReturn("grid_id");
	// assertTrue("locator has the wrong type", locator instanceof TypeLocator);
	// final Grid grid = new Grid(locator);
	// Mockito.when(sel.getEval("window.Ext.getCmp('grid_id').getStore().data.keys")).thenReturn("get_grid_keys");
	// Assert.assertEquals("get_grid_keys", grid.getKeys()[0]);
	// }
	
}
