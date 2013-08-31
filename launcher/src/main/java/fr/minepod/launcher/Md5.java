package fr.minepod.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.util.encoders.Hex;

public class Md5 {
  private FileInputStream fis;

  public String Get(File f) {
    if ((f.exists()) && (f.length() > 0L)) {
      try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        this.fis = new FileInputStream(f);
        byte[] dataBytes = new byte[1024];
        int nread = 0;

        while ((nread = this.fis.read(dataBytes)) != -1) {
        	md.update(dataBytes, 0, nread);
        }

        byte[] mdbytes = md.digest();
        
        return new String(Hex.encode(mdbytes));
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
	return null;
  }
}