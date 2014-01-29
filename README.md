# Selenium Extend (Name tbd)

[![Build Status](https://secure.travis-ci.org/tayloryork/SeleniumExtend.png)](http://travis-ci.org/tayloryork/SeleniumExtend)

This project was forked from SeleniumExtend at https://github.com/asaflevy/SelenuimExtend .  SeleniumExtend was written for Selenium 1.0, and did not match the new paradigms of Selenium 2.0/Webdriver. As such, there were a few major changes:
 - Removed all references to Selenium, and replaced with WebDriver/JavascriptExecutor
 - (In Progress) Remove all fireEvent logic. This was removed from WebDriver (Though you can still do it, it's just harder) because a user is not able to fireEvents. A user can only use the mouse or keyboard.

 The new WebElement paradigm is taken from http://www.roboqa.net/2013/03/element-object-extjs-combobox.html
 
# Travis-CI

[![Build Status](https://api.travis-ci.org/tayloryork/SelenuimExtend.png)](http://travis-ci.org/tayloryork/SeleniumExtend)

# TODO:
- [x] Set up maven-surefire for "mvn integration test" and create a few tests
- [x] Set up a build system (travis-ci)
- [ ] Decide if we want to pre-inject the page with our javascript methods or do everything on the fly.
- [ ] Remove all methods that fire events.
- [ ] Make Chart class and merge in tests from another project.
- [ ] Publish artifacts? http://datumedge.blogspot.com/2012/05/publishing-from-github-to-maven-central.html

# Team Decisions
## JavaScript Code Managment - How should we store the JS code?
* Should the JS just be saved in regular Strings? IE String code = "doSomeComplexCode()" + "lotsOfComplexLinesOfCode();
* Or should we use another technique of Multi-Line Strings in JS like this - https://github.com/benelog/multiline
* Or Should the JS be saved per file - /src/main/resources/.../findElement.js = "namespace.function = ...."
* Or Should the JS be saved all together - /src/main/resources/.../namespace.js = "namespace = {function1... function2... function3...}"
- Looks like Just saving JS as strings wins. Everything else was complicated and non-standard. I was scared about the comments and java docs being formatted and having *'s added in the middle of the code.  

## A single Javascript method to funnel all javaascript through.
* Should you be able to do ".getEL()"; and expect it to return a stuff OR do we force "return extCmp.getEl();"
* right now the answer is force "return..."

## Do we need to load any basic utility functions? Any of them?
* Load the Entire SExt object
* Load the functions just before they are called, everytime.
* Load the functions just before they are called, but check to see if it's required (ie return (SExt.method typeof 'function') ? true : false;)

### If we need to load any basic utilities, how do we do it, and do it well?
* No idea right now.
