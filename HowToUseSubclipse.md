# Introduction #

This document covers how to install subclipse, check out code from google code, and then commit changes back to google code.

# Details #

Subclipse Installation:
  * Install Eclipse.
  * Run Eclipse
  * Go to "Help" -> "Install Additional Software"
  * Click on "Add..."
  * In the name field, type "subclipse" and in the location field type `http://subclipse.tigris.org/update_1.6.x`
  * Click "OK"
  * Check all three things and then click "Next"
  * Click "Next" again
  * Agree to the license and then click "Finish"
  * Click "OK" if it asks you if you trust this content
  * Click "Yes" when it asks you to restart

Checking out code:
  * Click on "File" -> "Import"
  * Double-click the "SVN" folder
  * Double-click on "Checkout Projects from SVN"
  * Choose "Create a new repository location:"
  * Type in `https://gt-parser-project-3240.googlecode.com/svn/trunk/` in the URL field and click "Next"
  * Select `ParserProject` from the list and then click "Next"
  * Choose "Check out as a project in the workspace" and then click "Next"
  * Click "Finish"

Commiting code:
  * Right-Click on the project in the project explorer
  * Choose "Team" -> "Commit"
  * Enter a comment and then choose OK
  * It should ask you for your username and password
  * Your username is your gmail account without "@gmail.com" and your password is available here: https://code.google.com/hosting/settings