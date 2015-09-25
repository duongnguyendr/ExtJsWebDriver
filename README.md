# ExtJS WebDriver (Formerly Selenium Extend)

This project was forked from SeleniumExtend at https://github.com/asaflevy/SelenuimExtend .  SeleniumExtend was written for Selenium 1.0, and did not match the new paradigms of Selenium 2.0/Webdriver. As such, there were a few major changes:
 - Removed all references to Selenium, and replaced with WebDriver/JavascriptExecutor
 - (In Progress) Remove all fireEvent logic. This was removed from WebDriver (Though you can still do it, it's just harder) because a user is not able to fireEvents. A user can only use the mouse or keyboard.

 The new WebElement paradigm is taken from http://www.roboqa.net/2013/03/element-object-extjs-combobox.html
 The new ByExtJsComp, ByExtJsQuery etc should be made in a likes of ByAngular from https://github.com/paul-hammant/ngWebDriver

# Travis-CI
[![Build Status](https://travis-ci.org/tayloryork/ExtJsWebDriver.png?branch=master)](https://travis-ci.org/tayloryork/ExtJsWebDriver)

# TODO:
- [x] Set up maven-surefire for "mvn integration test" and create a few tests
- [x] Set up a build system (travis-ci)
- [x] Decide if we want to pre-inject the page with our javascript methods or do everything on the fly. (Decided - on the fly)
- [ ] Create and implement ByExtJsComp, ByExtJsQuery a la ngWebDriver
- [ ] Inject all Javascript on the fly
- [ ] Remove all methods that fire events.
- [ ] Make Chart class and merge in tests from another project.
- [ ] Publish artifacts? http://datumedge.blogspot.com/2012/05/publishing-from-github-to-maven-central.html

# Team Decisions
## JavaScript Code Managment - How should we store the JS code?
* Should the JS just be saved in regular Strings? Ex. String code = "doSomeComplexCode()" + "lotsOfComplexLinesOfCode();
* Or Should the JS be saved per file - /src/main/resources/.../findElement.js = "namespace.function = ...."

> Save the JS in java strings for most/small functions. If they are very large, use JS Files.

## A single Javascript method to funnel all javaascript through.
* Should you be able to do ".getEL()"; and expect it to return a stuff OR do we force "return extCmp.getEl();"

> right now the answer is force "return..."

## Do we need to load any basic utility functions? Any of them?
> Load as needed.

### If we need to load any basic utilities, how do we do it, and do it well?
> Load as needed.
