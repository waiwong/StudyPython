package util.windowsOS;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WinUtil {
	
	public boolean createDirectory(String dirName) {
		boolean rslt = false;
		File dir = new File(dirName);
		if (!dir.exists()) {
			if (dir.mkdir()) {
				rslt = dir.exists();
			}
		}
		
		return rslt;
	}
	
	public void ListRootDirectories() {
		Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
		for (Path name : dirs) {
			System.out.println(name);
		}
	}
	
	public String getCurrentUserDesktopPath() {
		String rslt = "";
		if (System.getProperty("os.name").toLowerCase().indexOf("win") < 0) {
			System.err.println("Sorry, Windows only!");
		} else {
			File desktopDir = new File(System.getProperty("user.home"), "Desktop");
			if (desktopDir.exists())
				rslt = desktopDir.getPath();
		}
		return rslt;
	}
	
	public List<String> listFilesForFolderByExt(File folder, String fileExt) {
		
		List<String> lstRslt = new ArrayList<String>();
		GenericExtFilter filter = new GenericExtFilter(fileExt);
		
		String[] files = folder.list(filter);
		for (String file : files) {
			String temp = new StringBuffer(folder.getAbsolutePath()).append(File.separator).append(file)
					.toString();
			System.out.println("file : " + temp);
			lstRslt.add(temp);
		}
		
		File[] dirs = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory();
			}
		});
		
		for (File fileEntry : dirs) {
			if (fileEntry.isDirectory()) {
				System.out.println("Reading files under the folder " + fileEntry.getAbsolutePath());
				lstRslt.addAll(listFilesForFolderByExt(fileEntry, fileExt));
			} else {
				if (fileEntry.isFile()) {
					// String tmpName = fileEntry.getName();
					System.out.println("File= " + folder.getAbsolutePath() + "\\" + fileEntry.getName());
					// lstRslt.add(tmpName);
				}
			}
		}
		
		return lstRslt;
	}
	
	public List<String> listFilesForFolder(File folder) {
		
		// File[] files = folder.listFiles(new FileFilter() {
		// @Override
		// public boolean accept(File file) {
		// return file.isFile();
		// }
		// });
		
		List<String> lstRslt = new ArrayList<String>();
		// List<File> filesInFolder = Files.walk(Paths.get("/path/to/folder"))
		// .filter(Files::isRegularFile)
		// .map(Path::toFile)
		// .collect(Collectors.toList());
		String tmpName;
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				// System.out.println("Reading files under the folder "+folder.getAbsolutePath());
				lstRslt.addAll(listFilesForFolder(fileEntry));
			} else {
				if (fileEntry.isFile()) {
					tmpName = fileEntry.getName();
					System.out.println("File= " + folder.getAbsolutePath() + "\\" + fileEntry.getName());
					lstRslt.add(tmpName);
				}
			}
		}
		
		return lstRslt;
	}
	
	// inner class, generic extension filter
	public class GenericExtFilter implements FilenameFilter {
		
		private String ext;
		
		public GenericExtFilter(String ext) {
			this.ext = ext;
		}
		
		public boolean accept(File dir, String name) {
			return name.endsWith(ext);
		}
	}
}
