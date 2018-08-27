package com.whirlpool.dastm;


import org.osgi.service.component.annotations.Activate;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.osgi.service.component.annotations.Reference;
//import org.osgi.service.component.annotations.serv;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.whirlpool.dastm.services.SFTPClientService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedInputStream;


import javax.annotation.Nonnull;
import javax.servlet.Servlet;

//Sling Imports
import org.apache.sling.api.resource.ResourceResolverFactory ; 
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource; 
import org.apache.sling.api.resource.Resource; 

//DAM API
import com.day.cq.dam.api.Asset ; 

import javax.jcr.Session;
import javax.jcr.Node; 

@Component(
        service = { Servlet.class },
        name = "SFTPServlet",
        property = {
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/whirlpool/SFTPServlet",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=GET"
        }
)
public class SFTPServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    private Logger log = LoggerFactory.getLogger(SFTPServlet.class);
    private Session session;
    
    //Inject a Sling ResourceResolverFactory
    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    private SFTPClientService sFTPClientService;

    @Override
    protected void doGet(@Nonnull SlingHttpServletRequest request, @Nonnull SlingHttpServletResponse response) throws IOException {
    	
    	log.info("SFTP Servlet Started");
    	 
    	SFTPClient client = null;
    	//Map<String, Object> param = new HashMap<String, Object>();
		//param.put(ResourceResolverFactory.SUBSERVICE, "writeService");
    	//try {
    		
    		//ResourceResolver resourceResolver = resolverFactory.getAdministrativeResourceResolver(null);
    		//ResourceResolver resourceResolver = resolverFactory.getServiceResourceResolver(null);
    		//session = resourceResolver.adaptTo(Session.class);
    		ResourceResolver resourceResolver=request.getResourceResolver();
            Resource rs = resourceResolver.getResource("/content/dam/Import_Document_Template.csv");
            //Resource rs = resourceResolver.g
            Asset asset = rs.adaptTo(Asset.class);   
               
            //We have the File Name and the inputstream
            InputStream assetData = asset.getOriginal().getStream();
            String assetName =asset.getName(); 
            
			
            /*client = new SFTPClient(
					"192.168.1.3",
					"pursingh",
					"Sonudada20#@!4"
					);
            /*client = new SFTPClient(
					"158.52.2.60",
					"mftLegHold",
					"SjxF4hde7"
					);*/
			//client.connect();
	//client.putFile(assetData,assetName,"/Users/pursingh/Documents/sftp/test.csv");
	//client.putFile(assetData,assetName,"/Import_Document_Template.csv");
			

			//client.deleteRemoteFolder("/parentfoldername", "relativepath/delete/thisfolder");
	ChannelSftp sftp=sFTPClientService.getSFTPConnection();	
	sFTPClientService.uploadFile(sftp, assetData, assetName, "/Users/pursingh/Documents/sftp/test.csv");
	sFTPClientService.disconnect(sftp);
	log.info("SFTP Servlet inside try");

			//client.disconnect();
		/*} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//e.getMessage();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//e.getMessage();
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//e.getMessage();
		} //catch (LoginException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//} 
    	finally {
			try {
				//if (client != null)
					//client.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				//e.getMessage();
			}
		}*/
        log.info("SFTP Servlet after try");
    	
    	
    	response.getWriter().write("in servlet");	
    	
    	
    }

    @Activate
    protected void doActivate(ComponentContext componentContext) {
    	
    }
}
