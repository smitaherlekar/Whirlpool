package com.whirlpool.dastm.services.impl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "SFTPClientServiceConfiguration", description = "SFTPClientServiceConfiguration")
public @interface SFTPClientServiceConfiguration {
	
	public static String mftusername = "mftLegHold";
	public static String mftpassword = "SjxF4hde7";
	public static String mfthostname = "158.52.2.60";
	public static int mftport = 22;
	
	@AttributeDefinition(name = "mftUsername", description = "MFT Username")
	String getMFTUsername()default mftusername;
	
	@AttributeDefinition(name = "mftPassword", description = "MFT Password" , type=AttributeType.PASSWORD )
	String getMFTPassword()default mftpassword;
	
	@AttributeDefinition(name = "mftHostname", description = "MFT Hostname")
	String getHostName()default mfthostname;
	
	@AttributeDefinition(name = "mftPort", description = "MFT Port" )
	int getMftPort() default mftport;
	
}