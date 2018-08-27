package com.whirlpool.dastm.services;

import java.io.InputStream;

import com.jcraft.jsch.ChannelSftp;

public interface SFTPClientService {
	public ChannelSftp getSFTPConnection();
	public void uploadFile(ChannelSftp sftp,InputStream assetData, String assetName, String destinationPath);
	public void retrieveFile(ChannelSftp sftp,String sourceFile, String destinationFile);
	public void disconnect(ChannelSftp sftp);
}
