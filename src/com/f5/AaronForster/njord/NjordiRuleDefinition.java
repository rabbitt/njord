package com.f5.AaronForster.njord;

import iControl.LocalLBRuleRuleDefinition;

import org.fife.ui.rsyntaxtextarea.TextEditorPane;

/**
 * This will hold the iRule object we've pulled in from the BigIP as well as some attributes that are specific to Njord
 * 
 *
 * @author Aaron Forster @date 20120601
 * @version 0.5.1
 * @Since 0.5
 */
public class NjordiRuleDefinition extends NjordObject {
//No longer extending LocalLBRuleDefinition so I might have to get stuff from the iRule.
	/*#################################################################################
	 * ATTRIBUTES
	 * #################################################################################*/
//	/**
//	 * Is this object new? AKA does not exist on the bigip.
//	 */
//	private boolean isNew;
//	/**
//	 * Has the object been modified and thus needs to be saved on the bigip.
//	 */
//	private boolean isModified;
//	/**
//	 * The BigIP this object is actually attached to. I might not use this. I will probably have a list of things like iRules, iApps, whatever that we've pulled in.
//	 * Currently there is only one BigIP so this attr isn't in use.
//	 */
//	private String BigIP;
//	/**
//	 * The name of the object. This is the fully qualified (full path) name.
//	 */
//	private String myName;
	
	/**
	 * Depricated: The iRule we pulled in from the BigIP
	 * Depricated as of Njord 0.8 we are storing TextEditorPanes which contain the iRule now.
	 */
	private LocalLBRuleRuleDefinition iRule; 

	/**
	 * Depricated: iRule definition text is stored in here.
	 * Depricated as of Njord 0.8 we are storing TextEditorPanes which contain the iRule now.
	 */
	private String iRuleDefinition = null;
	
	/**
	 * Store the TextEditorPane for this iRule. We're storing TextEditorPane instead of the iRule definition because having a 
	 * separate editor pane for every rule will allow it to keep it's own clean/dirty status as well as it's own undo history 
	 * and a number of other items.
	 */
	public TextEditorPane editorPane = null;

	/*#################################################################################
	 * CONSTRUCTORS
	 *#################################################################################*/	
	/**
	 * I can't imagine too many cases where you'd want an iRule object without things in it but I don't like not having one of these
	 */
	public NjordiRuleDefinition() {
		isNew = true;
		isModified = true;
		myName = Integer.toString(this.hashCode()); //We don't have a real name so let's use the hash.
	}
	
	/**
	 * Depricated: Create a new NjordiRuleDefinition specifying an iRule object to contain.
	 * Depricated as of Njord 0.8
	 * 
	 * @param iRule
	 */
	public NjordiRuleDefinition(LocalLBRuleRuleDefinition iRule) {
		this.iRule = iRule;
		isNew = false;
		isModified = false;
		myName = iRule.getRule_name();
		iRuleDefinition = iRule.getRule_definition();
		
	}

	/**
	 * If created with only a string we will create a new iRule.
	 * @param name
	 */
	public NjordiRuleDefinition(String name) {
		isNew = true; //Depricated
		isModified = true; //Depricated
		myName = name; 
		iRuleDefinition = "when CLIENT_ACCEPTED {\n\n\n}" ; //Make this be a temp thing I stuff into the TextEditorPane
		iRule = new LocalLBRuleRuleDefinition(myName, iRuleDefinition);
		//TODO: Check to ensure it's a full path.
	}
	
	/**
	 * Create a new NjordiRuleDefinition specifying a TextEditorPane for it to contain
	 * @param iRule
	 */
	public NjordiRuleDefinition(TextEditorPane editorPane) {
		this.editorPane = editorPane;
	}
	
	/**
	 * Duplicate GetRule_name() from the iRule definition.
	 * 
	 * @return
	 */
	public String getRule_name() {
		return getName();
	}
	
	/**
	 * Return the iRule definition.
	 * 
	 * 
	 * @return
	 */
	public String getRule_definition() {
		return iRuleDefinition;
	}
	
	/**
	 * Write the rule definition.
	 * Currently saves to both the local object and the iRule itself. Which means that new iRule still won't work. I need to handle this properly
	 * 
	 * @param ruleDefinition
	 */
	public void setRule_definition(String ruleDefinition) {
		//TODO: Handle this properly so that we don't save to the iRule automatically because we probably don't have one. 
		isModified = true;
		iRuleDefinition = ruleDefinition;
		iRule.setRule_definition(iRuleDefinition);
	}
	/*#################################################################################
	 * GETTERS AND SETTERS
	 *#################################################################################*/

	public LocalLBRuleRuleDefinition getiRule() {
		return iRule;
	}
	
	public void setiRule(LocalLBRuleRuleDefinition newiRule) {
		iRule = newiRule;
		iRuleDefinition = iRule.getRule_definition();
	}
	
	/*#################################################################################
	 * OTHER METHODS
	 *#################################################################################*/
}

//Irules will want to have:
//Attributes:
//Stuff from icontrol
//Module ie gtm irule ltm irule, etc.
//Attributes URL an optional URL to a reference or documentation for the rule? Where this would be stored I don't know. I think I was thinking of the commands and devcentral linkage this might be garbage.
//Even later perhaps add some generic comments at the top of irules where I can store things like tmos version validated and such.