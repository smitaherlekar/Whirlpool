
/*
 * This file is licensed under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.whirlpool.dastm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
//import org.apache.log4j.Logger;

import com.jcraft.jsch.JSch;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
* A SFTP client program that provides easy methods to 
* -file put and delete
* -creation of remote folders
* -deep creation of remote folders (such as /parent/child1/child1.1 ) 
*/
public class SFTPClient {
	private Logger log = LoggerFactory.getLogger(SFTPClient.class);
	private String username;
	private String password = null;
	private String hostname;
	private Session session = null;
	private ChannelSftp sftp = null;

	public SFTPClient(String hostname, String username, String password
			) throws IOException {
		
		log.info("inside constructor");
		this.hostname = hostname;
		this.username = username;
		this.password = password;

	}
	
	public void connect() throws JSchException, SftpException {
		log.info("Insiade connect");
		JSch jSch = new JSch();
		//jSch.addIdentity(this.username, // String userName
				//this.privatekey, // byte[] privateKey
				//null, // byte[] publicKey
				//this.passphrase // byte[] passPhrase
				//);
		log.info("identity added");
	
		
		this.session = jSch.getSession(this.username, this.hostname, 22);
		//this.session.setUserInfo(this.userinfo);
		
		this.session.setPassword(this.password);
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		log.info("session Connecting to sftp server");
		this.session.connect();
		log.info("session connected to sftp server");
		Channel channel = session.openChannel("sftp");
		this.sftp = (ChannelSftp) channel;
		log.info("sftp connect started");
		this.sftp.connect();
		//log.debug("Copying csv to /Users/pursingh ");
		//this.sftp.put("/content/dam/Import_Document_Template.csv", "/Users/pursingh");
		
		log.info("sft connect finished");
		log.info("Connected to Server via SFTP...");
	}
	
	private byte[] str2byte(String str) {
		return str2byte(str, "UTF-8");
	}
	
	private byte[] str2byte(String str, String encoding) {
		if (str == null)
			return null;
		try {
			return str.getBytes(encoding);
		} catch (java.io.UnsupportedEncodingException e) {
			return str.getBytes();
		}
	}
	
	public void putFile(InputStream assetData, String assetName, String destinationPath) throws SftpException {
		log.debug("Copying [" + assetName + "] to [" + destinationPath + "]");
		this.sftp.put(assetData, destinationPath);
		//this.disconnect(this.sftp);
	}

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

public void uploadFile(ChannelSftp sftp,InputStream assetData, String assetName, String destinationPath) {
	try {
		log.debug("Uploading file to server");
		sftp.put(assetData, destinationPath);
		log.info("Upload successfull.");
		}catch (SftpException e) {
			log.error(e.getMessage());;   
        }
	}
	}

	

