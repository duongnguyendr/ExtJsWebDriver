package co.tyec.selenium.extjs.webelements;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.seleniumemulation.JavascriptLibrary;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.outbrain.selenium.util.ThreadUtils;

public class Component {
	protected WebDriver driver;
	
	protected String extJsCmpId = null;
	
	private String defineSExt = "if(typeof SExt === \"undefined\") {SExt = function(){};};";
	
	JavascriptExecutor js;
	
	/**
	 * This is meant to be a general way to convert a specific id to the general id ( Ex. td[@id="combobox-1009-bodyEl"] to comboBox-1009)
	 */
	protected String TOP_ELEMENT_TO_EXT_JS_CMP_FUNCTION = "var id = arguments[0].id"
			// + ".replace('-inputEl', '')"
			// + ".replace('-bodyEl','')"
			+ ".replace(/-\\w*El$/,'')" /*-anythingEl */
			+ ".replace('-triggerWrap','');"
			+ "var extCmp = Ext.getCmp(id);";
	// + "if(console && console.log){ console.log('arguments[0]: ' + arguments[0]) }"
	// + "if(console && console.log){ console.log('id: ' + id) }"
	// + "if(console && console.log){ console.log('el.xtype: ' + el.xtype) }";
	
	protected String TOP_ELEMENT_TO_EXT_JS_ID_FUNCTION = TOP_ELEMENT_TO_EXT_JS_CMP_FUNCTION
			+ ";return extCmp.getId()"; // should this be .id?
	
	protected WebElement topElement;
	
	protected WebDriverWait wait;
	
	/**
	 * @param driver
	 * @param getCmpQuery
	 *            - ExtJS Query that would be used in Ext.getCmp('combobox-1001');
	 */
	public Component(WebDriver driver, ExtJSQueryType queryType, String query) {
		setDriver(driver);
		setElementFromQuery(queryType, query);
	}
	
	/**
	 * @param driver
	 * @param topElement
	 *            - locator of either parent topElement which wraps text input and drop down button or text input
	 */
	public Component(WebDriver driver, WebElement topElement) {
		setDriver(driver);
		setElement(topElement);
	}
	
	/**
	 * Method blur.
	 */
	public void blur() {
		fireEvent("blur");
	}
	
	/**
	 * Method cleanEvalTrue.
	 * 
	 * @param expr
	 *            String
	 * @return boolean
	 */
	public boolean cleanEvalTrue(final String expr) {
		try {
			if ("true".equals(getCleanEval(expr))) {
				return true;
			}
			if ("undefined".equals(getCleanEval(expr))
					|| "null".equals(getCleanEval(expr))
					|| "false".equals(getCleanEval(expr))) {
				return false;
			}
		} catch (final Exception e) {
			return false;
		}
		return false;
	}
	
	/**
	 * Method click.
	 */
	public void click() {
		topElement.click();
	}
	
	/**
	 * return true if the the component is disabled
	 * 
	 * @return boolean
	 */
	public boolean disabled() {
		return evalTrue("return el.disabled");
	}
	
	/**
	 * Method evalNullComponent.
	 * 
	 * @param expr
	 *            String
	 * @return boolean
	 */
	public boolean evalNullComponent(final String expr) {
		try {
			return "null".equals(execScriptOnExtJsComponent(expr));
		} catch (final Exception e) {
			return false;
		}
	}
	
	/**
	 * Method evalTrue.
	 * 
	 * @param expr
	 *            String
	 * @return boolean
	 */
	protected boolean evalTrue(final String expr) {
		try {
			Object jsOut = execScriptOnExtJsComponent(expr);
			Boolean jsBoolean = false;
			if (jsOut instanceof Boolean) {
				jsBoolean = (Boolean) jsOut;
			} else {
				jsBoolean = Boolean.parseBoolean((String) jsOut);
			}
			return jsBoolean == true;
		} catch (final Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * This is used to run javascript scrits on the ExtJS Component. For Example, execScriptOnExtJsComponent("return extCmp.getValue()"); will run the
	 * JavaScript method getValue on the ExtJS component object.
	 * 
	 * @param jsCode
	 *            Javscript code to execute.
	 * @return
	 */
	public Object execScriptOnExtJsComponent(String jsCode) {
		String finalScript = String.format("var extCmp = Ext.getCmp('%s'); %s;", getComponentId(), jsCode);
		return js.executeScript(finalScript);
	}
	
	/**
	 * This is used to run javascript on the Top level HTML element of the Ext JS compoenet, such as the top level DIV or TABLE. For Example,
	 * execScriptOnTopLevelElement("return el.tagName"); will return the tagName for the top level HTML element.
	 * 
	 * @param jsCode
	 *            Javscript code to execute.
	 * @return
	 */
	public Object execScriptOnTopLevelElement(String jsCode) {
		String finalScript = String.format("var el = arguments[0]; %s;", jsCode);
		return js.executeScript(finalScript, topElement);
	}
	
	void fireEvent(String event) {
		JavascriptLibrary lib = new JavascriptLibrary();
		lib.callEmbeddedSelenium(driver, "triggerEvent", topElement, event);
	}
	
	/**
	 * Method focus.
	 */
	public void focus() {
		fireEvent("focus");
	}
	
	/**
	 * Method getCleanEval.
	 * 
	 * @param expr
	 *            String
	 * @return String
	 */
	protected String getCleanEval(final String expr) {
		waitForFinishAjaxRequest();
		return (String) js.executeScript(expr);
	}
	
	/**
	 * return the componentId
	 * 
	 * @return String
	 */
	public String getComponentId() {
		if (extJsCmpId == null) {
			// well then we better have the WebElement!
			if (topElement == null)
				throw new RuntimeException("Neither extJsCmpId or topElement has been set");
			extJsCmpId = (String) js.executeScript(String.format("%s; return extCmp.getId();", TOP_ELEMENT_TO_EXT_JS_CMP_FUNCTION), topElement);
		}
		return extJsCmpId;
	}
	
	/**
	 * Returns the Ext.Element which encapsulates this Component.
	 * 
	 * @return String
	 */
	public WebElement getElDom() {
		return (WebElement) execScriptOnExtJsComponent("return el.getEl().dom");
	}
	
	/**
	 * Executes "return getExpression()+expr"
	 * 
	 * @param expr
	 *            String
	 * @return String
	 */
	protected String getEval(final String expr) {
		final String fullExpr = String.format("return %s%s", getExpression(), expr);
		
		return getCleanEval(fullExpr);
	}
	
	/**
	 * Returns the absolute expression that resolves this proxy's Ext component.
	 * 
	 * @return String
	 */
	public String getExpression() {
		return String.format("window.Ext.getCmp('%s')", getComponentId());
	}
	
	/**
	 * Returns an XPath to the Ext component, which contains the ID provided by getId()
	 * 
	 * @return String
	 */
	public String getXPath() {
		return "//*[@id='"
				+ getComponentId()
				+ "']";
	}
	
	/**
	 * Gets the xtype for this component as registered with Ext.ComponentMgr ------------
	 * 
	 * @return String
	 */
	public String getXType() {
		return (String) execScriptOnExtJsComponent("return el.getXType()");
	}
	
	/**
	 * Returns this Component's xtype --hierarchy-- as a slash-delimited string ------------
	 * 
	 * @return List<String>
	 */
	public String getXTypes() {
		return (String) execScriptOnExtJsComponent("return el.getXTypes()");
	}
	
	/**
	 * Method hidden.
	 * 
	 * @return boolean
	 */
	public boolean hidden() {
		return evalTrue(" == null")
				|| evalTrue(".hidden");
	}
	
	/**
	 * return true if the component is Disabled
	 * 
	 * @return boolean
	 */
	public boolean isDisabled() {
		return evalTrue(".disabled");
	}
	
	private void setDriver(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 5);
	}
	
	private void setElement(WebElement el) {
		topElement = el;
	}
	
	/**
	 * ExtJS Query that would be used in Ext.ComponentQuery.query("[name='myCheckbox']");
	 */
	protected WebElement setElementFromQuery(ExtJSQueryType queryType, String query) {
		String queryScript = null;
		int timeoutSec = 5; // Need to implement a timeout
		if (queryType == null) {
			return null;
		} else if (queryType.equals(ExtJSQueryType.ComponentQuery)) {
			queryScript = String.format("%s; %s; return SExt.findVisibleComponentElement(\"%s\");", defineSExt, findVisibleComponentElement, query);
		} else if (queryType.equals(ExtJSQueryType.GetCmp)) {
			extJsCmpId = query;
			queryScript = String.format("return Ext.getCmp(\"%s\").getEl().dom;", query);
		}
		
		try {
			topElement = (WebElement) js.executeScript(queryScript);
			getComponentId();
		} catch (ClassCastException e) {
			//
			topElement = null;
		}
		return topElement;
	}
	
	/**
	 * Method visible.
	 * 
	 * @return boolean
	 */
	public boolean visible() {
		return !hidden();
	}
	
	/**
	 * Method waitEvalTrue.
	 * 
	 * @param fullExpr
	 *            String
	 */
	protected void waitEvalTrue(final String fullExpr) {
		for (int second = 0;; second++) {
			if (second >= 15) {
				throw new RuntimeException("Timeout");
			}
			
			try {
				if ("true".equals(execScriptOnExtJsComponent(fullExpr))) {
					break;
				}
			} catch (final Exception e) {
				// ignore
			}
			
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				// ignore
			}
		}
	}
	
	/**
	 * Method waitFor msg dialog when has an error/failure .
	 */
	public void waitForDialogFailure() {
		js.executeScript("window.Ext.Msg.getDialog()");
		
		try {
			waitEvalTrue("window.Ext.Msg.isVisible() == false");
		} catch (final RuntimeException e) {
			throw e;
		}
	}
	
	// /**
	// * Returns this Component's xtype --hierarchy-- as a slash-delimited string ------------
	// *
	// *
	// * @return List<String>
	// */
	// public List<String> getXTypes() {
	// ArrayList<String> listString = new ArrayList<String>();
	// @SuppressWarnings("unchecked")
	// List<Object> listObject = (List<Object>) execScriptOnExtJsComponent("return el.getXTypes()");
	// for(Object current : listObject){
	// listString.add(String.valueOf(current));
	// }
	// return listString;
	// }
	
	/**
	 * Method waitForEvalTrue.
	 * 
	 * @param expr
	 *            String
	 */
	protected void waitForEvalTrue(final String expr) {
		final String fullExpr = getExpression()
				+ expr;
		
		waitEvalTrue(fullExpr);
	}
	
	/**
	 * Method what until all ajax requests are finshed .
	 * 
	 * @return boolean return true if there is no ajax request on AIRR
	 */
	protected boolean waitForFinishAjaxRequest() {
		boolean ret = false;
		long timeoutInSecond = 5;
		long start = System.currentTimeMillis();
		long end = start
				+ timeoutInSecond
				* 1000;
		while (System.currentTimeMillis() < end
				&& ret == false) {
			
			try {
				String jsOut = (String) js.executeScript("return Ext.Ajax.isLoading();");
				if ("false".equals(jsOut)) {
					Thread.sleep(200);
					ret = true;
					break;
				}
			} catch (final Exception e) {
				// ignore
			}
			
			try {
				Thread.sleep(300);
			} catch (final InterruptedException e) {
				// ignore
			}
		}
		return ret;
	}
	
	/**
	 * Wait until the div is not viable any more
	 * 
	 * @param componentId
	 *            String
	 * @return boolean
	 */
	
	protected boolean waitForGridLoadingMask(final String componentId) {
		final boolean success = ThreadUtils.waitFor(new ThreadUtils.WaitCondition() {
			@Override
			public boolean checkCondition(final long elapsedTimeInMs) {
				return !cleanEvalTrue(String.format("Ext.getCmp('%s').getEl().isMasked()", componentId));
			}
		}, TimeUnit.SECONDS, 15);
		
		if (!success) {
			throw new RuntimeException("Timeout");
		}
		
		return success;
	}
	
	/**
	 * Method waitForHidden.
	 */
	public void waitForHidden() {
		final boolean success = ThreadUtils.waitFor(new ThreadUtils.WaitCondition() {
			@Override
			public boolean checkCondition(final long elapsedTimeInMs) {
				return hidden();
			}
		}, TimeUnit.SECONDS, 15);
		
		if (!success) {
			throw new RuntimeException("Timeout, could wait no longer for hidden component.");
		}
	}
	
	/**
	 * Method waitForTreeLoadingMask.
	 * 
	 * @param panelMaskId
	 *            String
	 * @return boolean
	 */
	protected boolean waitForTreeLoadingMask(final String panelMaskId) {
		final boolean success = ThreadUtils.waitFor(new ThreadUtils.WaitCondition() {
			@Override
			public boolean checkCondition(final long elapsedTimeInMs) {
				return cleanEvalTrue(String.format("!window.Ext.fly('%s').isVisible()", panelMaskId));
			}
		}, TimeUnit.SECONDS, 15);
		
		if (!success) {
			throw new RuntimeException("Timeout");
		}
		
		return success;
	}
	
	/**
	 * Wait until the component is viable
	 * 
	 * @return boolean
	 */
	public boolean waitForVisible() {
		final boolean success = ThreadUtils.waitFor(new ThreadUtils.WaitCondition() {
			@Override
			public boolean checkCondition(final long elapsedTimeInMs) {
				return visible();
			}
		}, TimeUnit.SECONDS, 15);
		
		if (!success) {
			throw new RuntimeException("Timeout");
		}
		
		return success;
	}
	
	/**
	 * Wait for component. until the component is enable again.
	 */
	public void waitToLoad() {
		waitForEvalTrue("el.disabled != true");
	}
	
	String htmlEscape = "SExt.prototype.htmlEscape = function(str) {"
			+ "return String(str) "
			+ "	.replace(/&/g, '&amp;') "
			+ "	.replace(/\"/g, '&quot;')"
			+ "	.replace(/'/g, '&#39;')"
			+ "	.replace(/</g, '&lt;')"
			+ "	.replace(/>/g, '&gt;');"
			+ "}";
	
	
	String findVisibleComponentElement = "SExt.findVisibleComponentElement = function (query) {"
			+ "  var queryResultArray = (window.frames[0] &&  window.frames[0].Ext) ? window.frames[0].Ext.ComponentQuery.query(query) : Ext.ComponentQuery.query(query);"
			+ "  var single = null;"
			+ "  Ext.Array.every(queryResultArray, function(comp) {"
			+ "	     if (comp != null && comp.isVisible(true)){"
			+ "	       single = comp;"
			+ "	     }"
			+ "	     return (single != null);" /* return false will stop looping through array */
			+ "	   });"
			+ "  var el = (single != null ? single.getEl().dom : null);"
			+ "  return el;"
			+ "}";
	

	

	

}