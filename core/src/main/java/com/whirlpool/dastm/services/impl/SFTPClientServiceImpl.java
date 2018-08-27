package com.whirlpool.dastm.services.impl;

import java.io.InputStream;

import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import com.whirlpool.dastm.services.SFTPClientService;


@Component(service = SFTPClientService.class,configurationPolicy=ConfigurationPolicy.REQUIRE)
@Designate(ocd = SFTPClientServiceConfiguration.class)
public class SFTPClientServiceImpl implements SFTPClientService {
	
	private final static Logger log = LoggerFactory.getLogger(SFTPClientService.class);
	
    // to use the OSGi annotations
	// use version 3.2.0 of maven-bundle-plugin

	private SFTPClientServiceConfiguration config;
	
	@Reference
	private ResourceResolverFactory resolverFactory;

	@Activate
	public void activate(SFTPClientServiceConfiguration config) {
		this.config = config;
	}
	
	 /**
     * Connects to the server and does some commands.
     */
	public ChannelSftp getSFTPConnection()  {
		
		ChannelSftp sftp = null;
		try {
		
			JSch jSch = new JSch();
			Session session = null;
			Channel channel = null;
			session = jSch.getSession(config.getMFTUsername(), config.getHostName(), config.getMftPort());
			session.setPassword(config.getMFTPassword());
			java.util.Properties utilConfig = new java.util.Properties();
			utilConfig.put("StrictHostKeyChecking", "no");
			session.setConfig(utilConfig);
			session.connect();
			channel = session.openChannel("sftp");
			sftp = (ChannelSftp) channel;
			sftp.connect();
			return sftp;
        	} catch (JSchException e) {
        		log.error(e.getMessage());;
        	}
			return sftp; 
    	}
	
	/**
     * Uploads a file to the sftp server
     * @param sourceFile String path to sourceFile
     * @param destinationFile String path on the remote server
     * @throws InfinItException if connection and channel are not available or if an error occurs during upload.
     */
	public void uploadFile(ChannelSftp sftp,InputStream assetData, String assetName, String destinationPath) {
		try {
			log.debug("Uploading file to server");
			sftp.put(assetData, destinationPath);
			log.info("Upload successfull.");
			}catch (SftpException e) {
				log.error(e.getMessage());;   
	        }
		}
	
	/**
     * Retrieves a file from the sftp server
     * @param destinationFile String path to the remote file on the server
     * @param sourceFile String path on the local fileSystem
     * @throws InfinItException if connection and channel are not available or if an error occurs during download.
     */
    public void retrieveFile(ChannelSftp sftp,String sourceFile, String destinationFile) {
       
    	try {
        	if (sftp == null || sftp.getSession() == null || !sftp.getSession().isConnected() || !sftp.isConnected()) {
            	log.error("Connection to server is closed. Open it first.");
            }
        	log.debug("Downloading file to server");
            sftp.get(sourceFile, destinationFile);
            log.info("Download successfull.");
        } catch (JSchException e) {
    		log.error(e.getMessage());;
    	} catch (SftpException e) {
        	log.error(e.getMessage());
        }
    }
    
	/**
	 * Disconnects from the server.
	 */
	public void disconnect(ChannelSftp sftp) {
		
        if (sftp != null) {
            log.debug("Disconnecting sftp channel");
            sftp.disconnect();
        }
        try {
			if (sftp.getSession() != null) {
			    log.debug("Disconnecting session");
			    sftp.getSession().disconnect();
			}
		} catch (JSchException e) {
			log.error(e.getMessage());
		}
    }
}
	
	
  
	
	