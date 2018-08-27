package com.whirlpool.dastm;

import com.jcraft.jsch.UserInfo;
/**
* A patch to jsch.UserInfo to allow passphrase
*/
public class JSchUserInfo implements UserInfo{

		private String password;
		private String passphrase;
		
		public JSchUserInfo(String password, String passphrase) {
			this.password = password;
			this.passphrase = passphrase;
		}

		public String getPassphrase() {
			return this.passphrase;
		}

		public String getPassword() {
			return this.password;
		}

		public boolean promptPassphrase(String s) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean promptPassword(String s) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean promptYesNo(String s) {
			// TODO Auto-generated method stub
			return false;
		}

		public void showMessage(String s) {
			// TODO Auto-generated method stub
			
		}

}