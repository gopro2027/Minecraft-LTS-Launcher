package com.main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class CreateJarFile {
	
	private String SOURCE_FOLDER = "";
	private List <String> fileList;
	CreateJarFile(String folder, String outputFile) {
		SOURCE_FOLDER = folder;
		fileList = new ArrayList < String > ();
		generateFileList(new File(folder));
		File[] arr = new File[fileList.size()];
		for (int i = 0; i < fileList.size(); i++) {
			arr[i] = new File(fileList.get(i));
			System.out.println(arr[i].getAbsolutePath());
		}
		createJarArchive(new File(outputFile),arr);
	}
	
    public void generateFileList(File node) {
        // add file only
        if (node.isFile()) {
            fileList.add(generateZipEntry(node.toString()));
            //System.out.println(generateZipEntry(node.toString()));
        }

        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename: subNote) {
                generateFileList(new File(node, filename));
            }
        }
    }

    private String generateZipEntry(String file) {
        return file.substring(SOURCE_FOLDER.length() + 1, file.length());
    }
	
  public static int BUFFER_SIZE = 10240;
  protected void createJarArchive(File archiveFile, File[] tobeJared) {
    try {
      byte buffer[] = new byte[BUFFER_SIZE];
      // Open archive file
      FileOutputStream stream = new FileOutputStream(archiveFile);
      JarOutputStream out = new JarOutputStream(stream, new Manifest());

      for (int i = 0; i < tobeJared.length; i++) {
        /*if (tobeJared[i] == null || !tobeJared[i].exists()
            || tobeJared[i].isDirectory())
          continue; // Just in case...
        */
        //System.out.println("Adding " + tobeJared[i].getName());

        // Add archive entry
        JarEntry jarAdd = new JarEntry(tobeJared[i].getPath());
        jarAdd.setTime(tobeJared[i].lastModified());
        out.putNextEntry(jarAdd);

        // Write file to archive
        FileInputStream in = new FileInputStream(SOURCE_FOLDER+"/"+tobeJared[i]);
        while (true) {
          int nRead = in.read(buffer, 0, buffer.length);
          if (nRead <= 0)
            break;
          out.write(buffer, 0, nRead);
        }
        in.close();
      }

      out.close();
      stream.close();
      System.out.println("Adding completed OK");
    } catch (Exception ex) {
      ex.printStackTrace();
      System.out.println("Error: " + ex.getMessage());
    }
  }
}