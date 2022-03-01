import javax.swing.filechooser.FileFilter;
import java.io.File;
public class FileSaver extends FileFilter{
	public String extension;
	public String description;
	
	public FileSaver(String extension, String description) {
		this.extension = extension;
		this.description = description;
	}

	@Override
	public boolean accept(File f) {
		if(f.isDirectory()) {
			return true;
		}
		return f.getName().endsWith(extension);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description +String.format(" (*%s)", extension);
	}
	
	
	
	
}
