package com.outbrain.selenium.extjs.core.locators;

import com.outbrain.selenium.util.ExtjsUtils.Xtype;
import com.thoughtworks.selenium.Selenium;

/**
 * @author Asaf Levy
 * @version $Revision: 1.0
 */
public class TextOrLableLocator extends ComponentLocator {

  Integer index = null;

  /**
   * Field textOrLable.
   */
  String textOrLable;

  /**
   * Constructor for TextOrLableLocator.
   * @param sel Selenium
   * @param nameOrLable String
   * @param xtype Xtype
   */
  public TextOrLableLocator(final Selenium sel, final String nameOrLable, final Xtype xtype) {
    super(sel, xtype);
    textOrLable = nameOrLable;
  }
  
  public TextOrLableLocator(final Selenium sel, final String nameOrLable, final Xtype xtype, final boolean incInvisible) {
	    super(sel, xtype);
	    textOrLable = nameOrLable;
	    this.incInvisible = incInvisible;
	  }

  /**
   * 
   * @param sel
   * @param nameOrLable
   * @param xtype
   * @param index
   */
  public TextOrLableLocator(final Selenium sel, final String nameOrLable, final Xtype xtype, final Integer index) {
    super(sel, xtype);
    textOrLable = nameOrLable;
    this.index = index;
  }

  /**
   * Method getComponentId.
  
   * @return String */
  @Override
  public String getComponentId() {
    String[] component;
    String incInv = (this.incInvisible)?"true":"false";
    if (textOrLable != null) {
      component = waitCmpNotNull(String.format("window.findComponentByText('%s','%s',%s)", textOrLable, getXtype().getName(),incInv)).split(",");
    } else {
      component = waitCmpNotNull(String.format("window.findComponentByText(null,'%s',%s)", getXtype().getName(),incInv)).split(",");
    }

    if (component.length == 0) {
      return null;
    }
    if (index == null) {
      return component[0];
    }
    return component[index];

  }

}
