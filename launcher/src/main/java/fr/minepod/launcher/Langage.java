package fr.minepod.launcher;

public enum Langage {
	LAUNCHBUTTON("Jouer"),
	DEBUGINFORMATIONS("Informations de debug"),
	ERROR("Erreur: "),
	WHEN("En \u00E9tant en train "),
	WHENDOWNLOADING("En t\u00E9l\u00E9chargeant "),
	COMPILEDON("compil\u00E9 le "),
	DEVELOPMENTVERSION("version de d\u00E9veloppement"),
	DOINGMAINTHREADTASKS("d'ex\u00E9cuter les t\u00E2ches principales");
	
	private String name = "";
	    
	Langage(String name){
		this.name = name;
	}
	    
	public String toString(){
	    return name;
	}
}
