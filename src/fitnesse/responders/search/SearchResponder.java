// Copyright (C) 2003-2009 by Object Mentor, Inc. All rights reserved.
// Released under the terms of the CPL Common Public License version 1.0.
package fitnesse.responders.search;

import static java.util.regex.Pattern.*;

import java.util.regex.Pattern;

import fitnesse.components.RegularExpressionWikiPageFinder;
import fitnesse.components.SearchObserver;
import fitnesse.components.TitleWikiPageFinder;

public class SearchResponder extends ResultResponder {

  private String getSearchString() {
    return (String) request.getInput("searchString");
  }

  private String getSearchType() {
    String searchType = (String) request.getInput("searchType");

    if (searchType == null || searchType.toLowerCase().indexOf("title") != -1)
      return "Title";
    else
      return "Content";
  }

  protected String getPageFooterInfo(int hits) {
    return "Found " + hits + " results for your search.";
  }

  protected String getTitle() {
    return getSearchType() + " Search Results for '" + getSearchString() + "'";
  }

  @Override
  protected void startSearching(SearchObserver observer) {
    String searchString = getSearchString();
    if (!"".equals(searchString)) {
      String searchType = getSearchType();
      if ("Title".equals(searchType))
        new TitleWikiPageFinder(searchString, observer).search(root);
      else {
        Pattern regularExpression = Pattern.compile(searchString, CASE_INSENSITIVE + LITERAL);
        new RegularExpressionWikiPageFinder(regularExpression, observer).search(root);
      }
    }
  }

  protected boolean shouldRespondWith404() {
    return false;
  }

}
